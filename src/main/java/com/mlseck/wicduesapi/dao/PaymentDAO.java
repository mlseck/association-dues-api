package com.mlseck.wicduesapi.dao;

import com.mlseck.wicduesapi.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentDAO extends BaseDAO implements ResultSetExtractor<List<Payment>> {

    @Autowired
    MemberDAO mdao;
    @Autowired
    PaymentTypeDAO ptdao;
    @Autowired
    DueTypeDAO dtdao;

    public void addPayment(Payment payment) throws SQLException{
        String query = "INSERT INTO tbl_payments(date, amount, member_id, duetype_id, paymenttype_id) VALUES (?,?,?,?,?)";
        template.update(query, new Object[] {payment.getDate(), payment.getAmount(), payment.getMember().getMemberId(), payment.getDueType().getDueTypeId(), payment.getPaymentType().getPaymentTypeId()});
    }

    public Integer addPaymentWithId(final Payment payment) throws SQLException {
        KeyHolder holder = new GeneratedKeyHolder();
        final String query = "INSERT INTO tbl_payments(date, amount, member_id, duetype_id, paymenttype_id) VALUES (?,?,?,?,?)";
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setDate(1, (Date) payment.getDate());
                ps.setInt(2, payment.getAmount());
                ps.setInt(3, payment.getMember().getMemberId());
                ps.setInt(4, payment.getDueType().getDueTypeId());
                ps.setInt(5, payment.getPaymentType().getPaymentTypeId());
                return ps;
            }
        }, holder);
        return holder.getKey().intValue();
    }

    public void updatePayment(Payment payment) throws SQLException {
        Object params = new Object[] {payment.getDate(), payment.getAmount(), payment.getMember().getMemberId(), payment.getDueType().getDueTypeId(), payment.getPaymentType().getPaymentTypeId(), payment.getPaymentId()};
        String query = "UPDATE tbl_payments SET date = ?, amount = ?, member_id = ?, duetype_id = ?, paymenttype_id = ? WHERE payment_id = ?";
        template.update(query, params);
    }

    public void deletePayment (Payment payment) throws SQLException {
        template.update("DELETE from tbl_payments WHERE payment_id = ?", new Object[] { payment.getPaymentId() });
    }

    public Integer getPaymentsCount() throws SQLException {
        return template.queryForObject("SELECT count(*) as COUNT from tbl_payments", Integer.class);
    }

    public List<Payment> readAllPayments(Integer pageNo, String searchString) throws SQLException {
        setPageNo(pageNo);
        Object[] params = null;
        String query = "SELECT * FROM tbl_payments";
        if (searchString != null) {
            searchString = "%" + searchString + "%";
            query += " WHERE amount = ?";
            params = new Object[] { searchString };
        }
        if (pageNo != null && pageNo > 0){
            int index = (getPageNo() - 1)* 10;
            query+=" LIMIT "+index+" , "+getPageSize();
        }
        return template.query(query, params, this);
    }

    public Payment getPaymentByPK(Integer paymentId) throws SQLException {
        List<Payment> payments = (List<Payment>) template.query(
                "SELECT * FROM tbl_payments WHERE payment_id = ?",
                new Object[] { paymentId }, this);
        if (payments != null && !payments.isEmpty()) {
            return payments.get(0);
        }
        return null;
    }

    public List<Payment> getPaymentsByMember(Integer memberId) throws SQLException {
        String query = "SELECT * FROM tbl_payments WHERE member_id = ?";
        return (List<Payment>) template.query(query, new Object [] {memberId}, this);
    }

    @Override
    public List<Payment> extractData(ResultSet rs) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        while (rs.next()) {
            Payment p = new Payment();
            p.setPaymentId(rs.getInt("payment_id"));
            p.setAmount(rs.getInt("amount"));
            p.setDate(rs.getDate("date"));
            p.setMember(mdao.getMemberByPK(rs.getInt("member_id")));
            p.setDueType(dtdao.getDueTypeByPK(rs.getInt("duetype_id")));
            p.setPaymentType(ptdao.getPaymentTypeByPK(rs.getInt("paymenttype_id")));
            payments.add(p);
        }
        return payments;
    }
}
