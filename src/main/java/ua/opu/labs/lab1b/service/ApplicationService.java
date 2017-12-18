package ua.opu.labs.lab1b.service;

import ua.opu.labs.lab1b.domain.model.Payment;

import java.util.List;

public interface ApplicationService {
    void addWordForPaymentNameWhichStartsWith(String word, String prefix);

    List<Payment> getPaymentsWithPriceMoreThan(double price);

    void savePayment(String name, String payer, String recipient, double price);

}
