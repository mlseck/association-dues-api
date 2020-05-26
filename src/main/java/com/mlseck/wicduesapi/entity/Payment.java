package com.mlseck.wicduesapi.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Component
public class Payment implements Serializable {
    private static final long serialVersionUID = -3151414277084439849L;
    private Integer paymentId;
    private Date date;
    private Integer amount;
    private Member member;
    private DueType dueType;
    private PaymentType paymentType;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public DueType getDueType() {
        return dueType;
    }

    public void setDueType(DueType dueType) {
        this.dueType = dueType;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return getPaymentId().equals(payment.getPaymentId()) &&
                getDate().equals(payment.getDate()) &&
                getAmount().equals(payment.getAmount()) &&
                getMember().equals(payment.getMember()) &&
                getDueType().equals(payment.getDueType()) &&
                getPaymentType().equals(payment.getPaymentType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPaymentId(), getDate(), getAmount(), getMember(), getDueType(), getPaymentType());
    }
}
