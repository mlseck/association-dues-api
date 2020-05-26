package com.mlseck.wicduesapi.dao;

import com.mlseck.wicduesapi.entity.DueType;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DueTypeDAO extends BaseDAO implements ResultSetExtractor<List<DueType>> {

    public void addDueType(DueType dueType) throws SQLException {
        String query = "INSERT INTO tbl_duetypes(name) VALUES (?)";
        template.update(query, new Object [] {dueType.getDueName()});
    }

    public Integer addDueTypeWithId (final DueType dueType) throws SQLException{
        KeyHolder holder = new GeneratedKeyHolder();
        final String query = "INSERT INTO tbl_duetypes(name) VALUES (?)";
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, dueType.getDueName());
                return ps;
            }
        }, holder);
        return holder.getKey().intValue();
    }

    public void updateDueType(DueType dueType) throws SQLException {
        template.update(
                "UPDATE tbl_duetypes SET name = ? WHERE duetype_id = ?",
                new Object[] { dueType.getDueName(), dueType.getDueTypeId() });
    }

    public void deleteDueType(DueType dueType) throws SQLException {
        String query = "DELETE FROM tbl_duetypes WHERE duetype_id = ?";
        template.update(query, new Object[] {dueType.getDueTypeId()});
    }

    public Integer getDueTypeCount() throws SQLException {
        return template.queryForObject("select count(*) as COUNT from tbl_duetypes", Integer.class);
    }

    public List<DueType> readAllDueTypes(Integer pageNo, String searchString)
            throws SQLException {
        setPageNo(pageNo);
        Object[] params = null;
        String query = "SELECT * FROM tbl_duetypes";
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

    public DueType getDueTypeByPK(Integer dueTypeId) throws SQLException {
        List<DueType> dueTypes = (List<DueType>) template.query(
                "SELECT * FROM tbl_duetypes WHERE duetype_id = ?",
                new Object[] { dueTypeId }, this);
        if (dueTypes != null && !dueTypes.isEmpty()) {
            return dueTypes.get(0);
        }
        return null;
    }


    @Override
    public List<DueType> extractData(ResultSet rs) throws SQLException {
        List<DueType> dueTypes = new ArrayList<>();
        while (rs.next()) {
            DueType dt = new DueType();
            dt.setDueTypeId(rs.getInt("duetype_id"));
            dt.setDueName(rs.getString("name"));
            dueTypes.add(dt);
        }
        return dueTypes;
    }
}
