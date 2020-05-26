package com.mlseck.wicduesapi.dao;

import com.mlseck.wicduesapi.entity.Member;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MemberDAO extends BaseDAO implements ResultSetExtractor<List<Member>> {

    public void addMember(Member member) throws SQLException {
        String query = "INSERT INTO tbl_members(member_id, fname, lname, email, membershipdate, dob, address, country) VALUES (?,?,?,?,?,?,?,?)";
        template.update(query, new Object[] {member.getMemberId(), member.getFirstName(), member.getLastName(), member.getEmail(), member.getMembershipDate(), member.getDob(), member.getAddress(), member.getCountry()});
    }

    public void updateMember(Member member) throws SQLException {
        Object[] params = new Object[] {member.getFirstName(), member.getLastName(), member.getEmail(), member.getMembershipDate(), member.getDob(), member.getAddress(), member.getCountry(), member.getMemberId()};
        String query = "UPDATE tbl_members SET fname = ?, lname = ?, email = ?, membershipdate = ?, dob = ?, address = ?, country = ? WHERE member_id = ?";
        template.update(query, params);
    }

    public void deleteMember(Member member) throws SQLException {
        template.update("DELETE FROM tbl_members WHERE member_id = ?",
                new Object[] { member.getMemberId() });
    }

    public Integer getMembersCount() throws SQLException {
        return template.queryForObject("select count(*) as COUNT from tbl_members", Integer.class);
    }

    public List<Member> readAllMembers(Integer pageNo, String searchString)
            throws SQLException {
        setPageNo(pageNo);
        Object[] params = null;
        String query = "SELECT * FROM tbl_members";
        if (searchString != null) {
            searchString = "%" + searchString + "%";
            query += " WHERE fname LIKE ?";
            params = new Object[] { searchString };
        }
        if (pageNo != null && pageNo > 0){
            int index = (getPageNo() - 1)* 10;
            query+=" LIMIT "+index+" , "+getPageSize();
        }
        return template.query(query, params, this);
    }

    public Member getMemberByPK(Integer memberId) throws SQLException {
        List<Member> members = (List<Member>) template.query(
                "SELECT * FROM tbl_members WHERE member_id = ?",
                new Object[] { memberId }, this);
        if (members != null && !members.isEmpty()) {
            return members.get(0);
        }
        return null;
    }

    @Override
    public List<Member> extractData(ResultSet rs) throws SQLException {
        List<Member> members = new ArrayList<>();
        while (rs.next()) {
            Member m = new Member();
            m.setMemberId(rs.getInt("member_id"));
            m.setFirstName(rs.getString("fname"));
            m.setLastName(rs.getString("lname"));
            m.setEmail(rs.getString("email"));
            m.setMembershipDate(rs.getDate("membershipdate"));
            m.setDob(rs.getDate("dob"));
            m.setAddress(rs.getString("address"));
            m.setCountry(rs.getString("country"));
            members.add(m);
        }
        return members;
    }
}
