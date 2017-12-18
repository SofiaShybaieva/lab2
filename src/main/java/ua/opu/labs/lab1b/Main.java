package ua.opu.labs.lab1b;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.opu.labs.lab1b.config.AppConfig;
import ua.opu.labs.lab1b.service.ApplicationService;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ApplicationService service = context.getBean(ApplicationService.class);

        service.savePayment("test_1", "1", "11", 100.0);
        service.savePayment("E_test", "2", "22", 50.0);
        service.savePayment("test_2", "3", "33", 60.0);

        service.addWordForPaymentNameWhichStartsWith("_3", "E");
        System.out.println(service.getPaymentsWithPriceMoreThan(0));
    }
}
