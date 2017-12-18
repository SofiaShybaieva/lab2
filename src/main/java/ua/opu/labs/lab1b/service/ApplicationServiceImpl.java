package ua.opu.labs.lab1b.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ua.opu.labs.lab1b.domain.model.Payment;
import ua.opu.labs.lab1b.domain.repository.PaymentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void addWordForPaymentNameWhichStartsWith(String word, String prefix) {
        validateParam(word, "word");
        validateParam(word, "prefix");
        paymentRepository.getPayments()
                .stream()
                .filter(payment -> StringUtils.hasText(payment.getName()))
                .filter(payment -> payment.getName().startsWith(prefix))
                .forEach(payment -> {
                            payment.setName(word + payment.getName());
                            paymentRepository.updatePayment(payment);
                        }
                );
    }

    @Override
    @Transactional
    public List<Payment> getPaymentsWithPriceMoreThan(double price) {
        return paymentRepository.getPayments()
                .stream()
                .filter(payment -> payment.getPrice() > price)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void savePayment(String name, String payer, String recipient, double price) {
        paymentRepository.savePayment(new Payment(name, payer, recipient, price));
    }

    private void validateParam(String param, String paramName) {
        if (!StringUtils.hasText(param)) {
            throw new IllegalArgumentException(String.format("Param %s can't be blank.", paramName));
        }
    }
}
