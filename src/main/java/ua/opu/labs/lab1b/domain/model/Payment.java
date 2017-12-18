package ua.opu.labs.lab1b.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String payer;
    private String recipient;
    private double price;

    public Payment() {
    }

    public Payment(int id, String name, String payer, String recipient, double price) {
        this(name, payer, recipient, price);
        this.id = id;
    }

    public Payment(String name, String payer, String recipient, double price) {
        this.name = name;
        this.payer = payer;
        this.recipient = recipient;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", payer='" + payer + '\'' +
                ", recipient='" + recipient + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (Double.compare(payment.price, price) != 0) return false;
        if (name != null ? !name.equals(payment.name) : payment.name != null) return false;
        if (payer != null ? !payer.equals(payment.payer) : payment.payer != null) return false;
        return recipient != null ? recipient.equals(payment.recipient) : payment.recipient == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (payer != null ? payer.hashCode() : 0);
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
