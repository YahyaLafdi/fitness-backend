package com.metamafitness.fitnessbackend.dto;

import com.metamafitness.fitnessbackend.model.GenericEnum;
import lombok.*;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    private String transactionId;
    private LocalDateTime creationDate;
    private BigDecimal paymentAmount;
    private String currencyCode;
    private GenericEnum.PaymentStatus paymentStatus;
    private GenericEnum.PaymentMethod paymentMethod;
    private String paymentDescription;
    private String paymentNote;
    private String paymentInvoiceId;
    private String paymentIntent;
    private String paymentCartId;

    private PaymentUserDto paymentUser;
}
