package com.metamafitness.fitnessbackend.service.impl;

import static com.metamafitness.fitnessbackend.common.CoreConstant.Exception.TRANSACTION_ALREADY_COMPLETED;

import com.metamafitness.fitnessbackend.exception.CurrencyConversionException;
import com.metamafitness.fitnessbackend.exception.TransactionAlreadyCompletedException;
import com.metamafitness.fitnessbackend.model.*;
import com.metamafitness.fitnessbackend.repository.ProgramEnrollmentRepository;
import com.metamafitness.fitnessbackend.service.CurrencyConversionService;
import com.metamafitness.fitnessbackend.service.PaymentService;
import com.metamafitness.fitnessbackend.service.ProgramService;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${paypal.currency.code}")
    private String paypalCurrencyCode;

    @Value("${app.currency.code}")
    private String appCurrencyCode;

    private final PayPalHttpClient payPalHttpClient;

    private final ProgramService programService;

    private final CurrencyConversionService currencyConversionService;

    @Value("${paypal.order.return-url}")
    private String returnUrl;
    @Value("${paypal.order.cancel-url}")
    private String cancelUrl;
    private final ProgramEnrollmentRepository programEnrollmentRepository;

    public PaymentServiceImpl(PayPalHttpClient payPalHttpClient, ProgramService programService,
                              CurrencyConversionService currencyConversionService, ProgramEnrollmentRepository programEnrollmentRepository) {
        this.payPalHttpClient = payPalHttpClient;
        this.programService = programService;
        this.currencyConversionService = currencyConversionService;
        this.programEnrollmentRepository = programEnrollmentRepository;
    }

    @Override
    public Order createOrder(Program program) throws IOException, CurrencyConversionException {
        final BigDecimal priceInPaypalCurrency = currencyConversionService.convert(appCurrencyCode,
                paypalCurrencyCode, program.getPrice());
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName(program.getName())
                .landingPage("BILLING")
                .userAction("PAY_NOW")
                .cancelUrl(cancelUrl)
                .returnUrl(returnUrl);

        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnits = new ArrayList<>();
        PurchaseUnitRequest purchaseUnit = new PurchaseUnitRequest()
                .amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode(paypalCurrencyCode)
                        .value(priceInPaypalCurrency.toString()))
                .referenceId(program.getId().toString());

        purchaseUnits.add(purchaseUnit);
        orderRequest.purchaseUnits(purchaseUnits);

        request.requestBody(orderRequest);
        return payPalHttpClient.execute(request).result();
    }

    @Override
    public ProgramEnrollment completeOrder(String orderId, User currentUser) throws IOException {
        if(programEnrollmentRepository.existsByPayment_transactionId(orderId)) {
            throw new TransactionAlreadyCompletedException(new TransactionAlreadyCompletedException(), TRANSACTION_ALREADY_COMPLETED,  new Object[]{orderId});
        }
        OrdersGetRequest request = new OrdersGetRequest(orderId);
        request.header("Prefer", "return=representation");
        HttpResponse<Order> response = payPalHttpClient.execute(request);
        Order order = response.result();

        String programId = order.purchaseUnits().get(0).referenceId();

        Program program = programService.findById(Long.parseLong(programId));

        PaymentUser paymentUser = PaymentUser.builder()
                .userId(order.payer().payerId())
                .firstName(order.payer().name().givenName())
                .lastName(order.payer().name().surname())
                .email(order.payer().email())
                .payerId(order.payer().payerId())
                .build();
        Payment transactionDetails = Payment.builder()
                .transactionId(order.id())
                .creationDate(LocalDateTime.now())
                .paymentAmount(new BigDecimal(order.purchaseUnits().get(0).amountWithBreakdown().value()))
                .currencyCode(order.purchaseUnits().get(0).amountWithBreakdown().currencyCode())
                .paymentStatus(GenericEnum.PaymentStatus.SUCCEED)
                .paymentMethod(GenericEnum.PaymentMethod.PAYPAL)
                .paymentDescription(order.purchaseUnits().get(0).description())
                .paymentNote(order.purchaseUnits().get(0).customId())
                .paymentInvoiceId(order.id())
                .paymentIntent(order.checkoutPaymentIntent())
                .paymentUser(paymentUser)
                .build();
        return ProgramEnrollment.builder()
                .program(program)
                .user(currentUser)
                .payment(transactionDetails)
                .progress(ProgramProgress.builder().progress(0).build())
                .build();
    }
}
