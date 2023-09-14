package com.metamafitness.fitnessbackend.model;


import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.metamafitness.fitnessbackend.model.GenericEnum.PaymentStatus;
import com.metamafitness.fitnessbackend.model.GenericEnum.PaymentMethod;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    private String transactionId;
    private LocalDateTime creationDate;
    private BigDecimal paymentAmount;
    private String currencyCode;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private String paymentDescription;
    private String paymentNote;
    private String paymentInvoiceId;
    private String paymentIntent;

    @Embedded
    private PaymentUser paymentUser;

}
