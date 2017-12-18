package ua.opu.labs.lab1b.domain.repository;

import ua.opu.labs.lab1b.domain.model.Payment;

import java.util.List;

public interface PaymentRepository {
    List<Payment> getPayments();

    void savePayment(Payment payment);

    void updatePayment(Payment payment);
}
