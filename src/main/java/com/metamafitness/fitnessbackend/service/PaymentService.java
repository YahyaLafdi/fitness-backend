package com.metamafitness.fitnessbackend.service;

import com.metamafitness.fitnessbackend.model.Program;
import com.metamafitness.fitnessbackend.model.ProgramEnrollment;
import com.metamafitness.fitnessbackend.model.User;
import com.paypal.orders.Order;

import java.io.IOException;

public interface PaymentService {

    Order createOrder(Program program) throws IOException;

    ProgramEnrollment completeOrder(String orderId, User currentUser) throws IOException;
}
