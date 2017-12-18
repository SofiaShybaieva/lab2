package ua.opu.labs.lab1b.domain.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.opu.labs.lab1b.domain.model.Payment;

import javax.persistence.Query;
import java.util.List;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Payment> getPayments() {
        Query query = getCurrentSession().createQuery("from " + Payment.class.getName());
        return query.getResultList();
    }

    @Override
    public void savePayment(Payment payment) {
        getCurrentSession().persist(payment);
    }

    @Override
    public void updatePayment(Payment payment) {
        getCurrentSession().update(payment);
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
