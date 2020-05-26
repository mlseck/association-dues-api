package com.mlseck.wicduesapi.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Component
public class PaymentType implements Serializable {
    private static final long serialVersionUID = 9057226164844004520L;
    private Integer paymentTypeId;
    private String paymentName;
    private List<Payment> payments;

    public Integer getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Integer paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentType that = (PaymentType) o;
        return getPaymentTypeId().equals(that.getPaymentTypeId()) &&
                getPaymentName().equals(that.getPaymentName()) &&
                Objects.equals(getPayments(), that.getPayments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPaymentTypeId(), getPaymentName(), getPayments());
    }
}
