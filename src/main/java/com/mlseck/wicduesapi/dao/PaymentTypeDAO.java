package com.mlseck.wicduesapi.dao;

import com.mlseck.wicduesapi.entity.PaymentType;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentTypeDAO extends BaseDAO implements ResultSetExtractor<List<PaymentType>> {

    public void addPaymentType(PaymentType paymentType) throws SQLException {
        String query = "INSERT INTO tbl_paymenttypes(name) VALUES (?)";
        template.update(query, new Object [] {paymentType.getPaymentName()});
    }

    public Integer addPaymentTypeWithId (final PaymentType paymentType) throws SQLException{
        KeyHolder holder = new GeneratedKeyHolder();
        final String query = "INSERT INTO tbl_paymenttypes(name) VALUES (?)";
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, paymentType.getPaymentName());
                return ps;
            }
        }, holder);
        return holder.getKey().intValue();
    }

    public void updatePaymentType(PaymentType paymentType) throws SQLException {
        template.update(
                "UPDATE tbl_paymenttypes SET name = ? WHERE paymenttype_id = ?",
                new Object[] { paymentType.getPaymentName(), paymentType.getPaymentTypeId() });
    }

    public void deletePaymentType(PaymentType paymentType) throws SQLException {
        String query = "DELETE FROM tbl_paymenttypes WHERE paymenttype_id = ?";
        template.update(query, new Object[] {paymentType.getPaymentTypeId()});
    }

    public Integer getPaymentTypeCount() throws SQLException {
        return template.queryForObject("select count(*) as COUNT from tbl_paymenttypes", Integer.class);
    }

    public List<PaymentType> readAllPaymentTypes(Integer pageNo, String searchString)
            throws SQLException {
        setPageNo(pageNo);
        Object[] params = null;
        String query = "SELECT * FROM tbl_paymenttypes";
        if (searchString != null) {
            searchString = "%" + searchString + "%";
            query += " WHERE name LIKE ?";
            params = new Object[] { searchString };
        }
        if (pageNo != null && pageNo > 0){
            int index = (getPageNo() - 1)* 10;
            query+=" LIMIT "+index+" , "+getPageSize();
        }
        return template.query(query, params, this);
    }

    public PaymentType getPaymentTypeByPK(Integer paymentTypeId) throws SQLException {
        List<PaymentType> paymentTypes = (List<PaymentType>) template.query(
                "SELECT * FROM tbl_paymenttypes WHERE paymenttype_id = ?",
                new Object[] { paymentTypeId }, this);
        if (paymentTypes != null && !paymentTypes.isEmpty()) {
            return paymentTypes.get(0);
        }
        return null;
    }

    @Override
    public List<PaymentType> extractData(ResultSet rs) throws SQLException {
        List<PaymentType> paymentTypes = new ArrayList<>();
        while(rs.next()) {
            PaymentType pt = new PaymentType();
            pt.setPaymentTypeId(rs.getInt("paymenttype_id"));
            pt.setPaymentName(rs.getString("name"));
            paymentTypes.add(pt);
        }
        return paymentTypes;
    }
}
