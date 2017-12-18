package ua.opu.labs.tests.lab1b.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.opu.labs.lab1b.domain.model.Payment;
import ua.opu.labs.lab1b.domain.repository.PaymentRepository;
import ua.opu.labs.lab1b.service.ApplicationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MockedPaymentRepositoryProvider.class})
public class ApplicationServiceModuleTest {

    @Autowired
    private ApplicationService service;

    @Autowired
    private PaymentRepository paymentRepository;

    private String word = "_3";
    private String prefix = "E";
    private double priceLimit = 100.0;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        reset(paymentRepository);
    }

    @Test
    public void addWordForPaymentNameWhichStartsWithPrefix() {

        List<Payment> paymentsMatchCondition = Arrays.asList(
                new Payment(0, "E_payment", "John", "Alex", 100.5),
                new Payment(1, "E_2_payment", "Emma", "Alex", 50.0)
        );
        List<Payment> paymentsNotMatchCondition = Arrays.asList(
                new Payment(2, "A_payment", "Alex", "Irina", 20.0),
                new Payment(3, "B_payment", "Carry", "Samanta", 305.0)
        );

        List<Payment> allPayments = new ArrayList<>();
        allPayments.addAll(paymentsMatchCondition);
        allPayments.addAll(paymentsNotMatchCondition);

        when(paymentRepository.getPayments()).thenReturn(allPayments);

        service.addWordForPaymentNameWhichStartsWith(word, prefix);

        verify(paymentRepository, times(1)).getPayments();
        verify(paymentRepository, times(paymentsMatchCondition.size())).updatePayment(any());
        paymentsMatchCondition.forEach(payment -> {
                    verify(paymentRepository).updatePayment(payment);
                    assertTrue(payment.getName().startsWith(word));
                }
        );
        paymentsNotMatchCondition.forEach(payment -> assertTrue(!payment.getName().startsWith(word)));

    }

    @Test
    public void addWordForPaymentNameWhichStartsWithPrefixNoMatches() {
        List<Payment> paymentsNotMatchCondition = Arrays.asList(
                new Payment(0, "CD_payment", "Tom", "Jerry", 232.0),
                new Payment(1, "PA_payment", "Khaleesi", "Tyrion", 305.0)
        );

        when(paymentRepository.getPayments()).thenReturn(paymentsNotMatchCondition);

        service.addWordForPaymentNameWhichStartsWith(word, prefix);

        verify(paymentRepository, times(1)).getPayments();
        verify(paymentRepository, never()).updatePayment(any());

        paymentsNotMatchCondition.forEach(payment -> assertTrue(!payment.getName().startsWith(word)));
    }


    @Test
    public void addWordForPaymentNameWhichStartsWithPrefixAllMatches() {
        List<Payment> paymentsMatchCondition = Arrays.asList(
                new Payment(0, "E_3_payment", "Mark", "Alex", 133.0),
                new Payment(1, "EE_payment", "Kate", "Martin", 11.0)
        );

        when(paymentRepository.getPayments()).thenReturn(paymentsMatchCondition);

        service.addWordForPaymentNameWhichStartsWith(word, prefix);

        verify(paymentRepository, times(1)).getPayments();
        verify(paymentRepository, times(paymentsMatchCondition.size())).updatePayment(any());

        paymentsMatchCondition.forEach(payment -> {
            verify(paymentRepository).updatePayment(payment);
            assertTrue(payment.getName().startsWith(word));
        });
    }

    @Test
    public void getPaymentsPriceMoreThan() {
        List<Payment> matchCondition = Arrays.asList(
                new Payment(0, "C", "Martin", "Tom", priceLimit + 10.0),
                new Payment(1, "D", "Kate", "Nate", priceLimit + 20.0)
        );
        List<Payment> allPayments = new ArrayList<>(matchCondition);
        allPayments.add(new Payment(2, "C", "Martin", "Tom", priceLimit));
        allPayments.add(new Payment(3, "D", "Kate", "Nate", priceLimit - 30.0));

        when(paymentRepository.getPayments()).thenReturn(allPayments);

        List<Payment> result = service.getPaymentsWithPriceMoreThan(priceLimit);
        verify(paymentRepository, times(1)).getPayments();

        assertEquals(result.size(), matchCondition.size());
        result.forEach(payment -> assertTrue(payment.getPrice() > priceLimit));

    }

    @Test
    public void getPaymentsPriceMoreThanAllMatch() {
        List<Payment> matchCondition = Arrays.asList(
                new Payment(0, "C", "Martin", "Tom", priceLimit + 10.0),
                new Payment(1, "D", "Kate", "Nate", priceLimit + 20.0)
        );
        when(paymentRepository.getPayments()).thenReturn(matchCondition);
        List<Payment> result = service.getPaymentsWithPriceMoreThan(priceLimit);
        verify(paymentRepository, times(1)).getPayments();

        assertEquals(result.size(), matchCondition.size());
        result.forEach(payment -> assertTrue(payment.getPrice() > priceLimit));
    }

    @Test
    public void getPaymentsPriceMoreThanNonMatch() {
        List<Payment> nonMatchCondition = Arrays.asList(
                new Payment(2, "C", "Martin", "Tom", priceLimit),
                new Payment(3, "D", "Kate", "Nate", priceLimit - 30.0)
        );
        when(paymentRepository.getPayments()).thenReturn(nonMatchCondition);
        List<Payment> result = service.getPaymentsWithPriceMoreThan(priceLimit);
        verify(paymentRepository, times(1)).getPayments();

        assertEquals(result.size(), 0);
    }
}

@Configuration
@ComponentScan(value = {
        "ua.opu.labs.lab1b.service",
        "ua.opu.labs.lab1b.domain.model"
})
class MockedPaymentRepositoryProvider {

    @Bean
    public PaymentRepository paymentRepository() {
        return mock(PaymentRepository.class);
    }

}