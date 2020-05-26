package com.mlseck.wicduesapi.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class Member implements Serializable {
    private static final long serialVersionUID = -2778409724725780523L;
    private Integer memberId;
    private String firstName;
    private String lastName;
    private String email;
    private Date membershipDate;
    private Date dob;
    private String address;
    private String country;
    private List<Payment> payments;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(Date membershipDate) {
        this.membershipDate = membershipDate;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
        Member member = (Member) o;
        return getMemberId().equals(member.getMemberId()) &&
                getFirstName().equals(member.getFirstName()) &&
                getLastName().equals(member.getLastName()) &&
                getEmail().equals(member.getEmail()) &&
                getMembershipDate().equals(member.getMembershipDate()) &&
                getDob().equals(member.getDob()) &&
                getAddress().equals(member.getAddress()) &&
                getCountry().equals(member.getCountry()) &&
                Objects.equals(getPayments(), member.getPayments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMemberId(), getFirstName(), getLastName(), getEmail(), getMembershipDate(), getDob(), getAddress(), getCountry(), getPayments());
    }
}
