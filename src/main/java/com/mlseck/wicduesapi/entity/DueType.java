package com.mlseck.wicduesapi.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Component
public class DueType implements Serializable {
    private static final long serialVersionUID = -3243559852256680511L;
    private Integer dueTypeId;
    private String dueName;
    private List<Payment> payments;

    public Integer getDueTypeId() {
        return dueTypeId;
    }

    public void setDueTypeId(Integer dueTypeId) {
        this.dueTypeId = dueTypeId;
    }

    public String getDueName() {
        return dueName;
    }

    public void setDueName(String dueName) {
        this.dueName = dueName;
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
        DueType dueType = (DueType) o;
        return Objects.equals(getDueTypeId(), dueType.getDueTypeId()) &&
                Objects.equals(getDueName(), dueType.getDueName()) &&
                getPayments().equals(dueType.getPayments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDueTypeId(), getDueName(), getPayments());
    }
}
