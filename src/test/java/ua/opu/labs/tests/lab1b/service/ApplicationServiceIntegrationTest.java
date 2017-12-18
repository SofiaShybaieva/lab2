package ua.opu.labs.tests.lab1b.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.opu.labs.lab1b.config.AppConfig;
import ua.opu.labs.lab1b.domain.model.Payment;
import ua.opu.labs.lab1b.domain.repository.PaymentRepository;
import ua.opu.labs.lab1b.service.ApplicationService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@Transactional
public class ApplicationServiceIntegrationTest {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private PaymentRepository paymentRepository;

    private String word = "_3";
    private String prefix = "E";
    private double priceLimit = 100.0;

    @Before
    public void initDB() {
        List<Payment> allPayments = Arrays.asList(
                new Payment("E_payment", "John", "Alex", 100.0),
                new Payment("E_2_payment", "Emma", "Alex", 500.0),
                new Payment("A_payment", "Alex", "Irina", 20.0),
                new Payment("B_payment", "Carry", "Samanta", 305.0)
        );
        allPayments.forEach(payment -> paymentRepository.savePayment(payment));
    }

    @Test
    public void addWordForPaymentNameWhichStartsWithPrefix() {
        applicationService.addWordForPaymentNameWhichStartsWith(word, prefix);

        List<Payment> paymentsAfterUpdate = paymentRepository.getPayments();

        List<Payment> expected = Arrays.asList(
                new Payment( word + "E_payment", "John", "Alex", 100.0),
                new Payment( word + "E_2_payment", "Emma", "Alex", 500.0),
                new Payment("A_payment", "Alex", "Irina", 20.0),
                new Payment("B_payment", "Carry", "Samanta", 305.0)
        );

        assertEquals(expected, paymentsAfterUpdate);
    }

    @Test
    public void getPaymentsPriceMoreThan() {
        List<Payment> result = applicationService.getPaymentsWithPriceMoreThan(priceLimit);
        List<Payment> expected = Arrays.asList(
                new Payment("E_2_payment", "Emma", "Alex", 500.0),
                new Payment("B_payment", "Carry", "Samanta", 305.0)
        );
        assertEquals(expected, result);

    }
}
