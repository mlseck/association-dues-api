package com.mlseck.wicduesapi.service;

import com.mlseck.wicduesapi.dao.MemberDAO;
import com.mlseck.wicduesapi.dao.PaymentDAO;
import com.mlseck.wicduesapi.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.sql.SQLException;

@RestController
@CrossOrigin

public class MemberController {

    @Autowired
    MemberDAO mdao;

    @Autowired
    PaymentDAO pdao;

    @Transactional
    @RequestMapping(value = "/api/members", method = RequestMethod.PUT, consumes = "application/json")
    public void saveMember(@RequestBody Member member) throws SQLException {
        mdao.addMember(member);
    }

    @RequestMapping(value = "/api/members/{memberId}", method = RequestMethod.GET, produces="application/json")
    public Member getMemberByPK(@PathVariable Integer memberId) throws SQLException {
        Member member = mdao.getMemberByPK(memberId);
        member.setPayments(pdao.getPaymentsByMember(memberId));
        return member;
    }

}
