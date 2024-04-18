package com.hwann.marketmate;

import com.hwann.marketmate.member.Authority;
import com.hwann.marketmate.member.Member;
import com.hwann.marketmate.member.MemberService;
import com.hwann.marketmate.member.MemberServiceImpl;

public class MemberApp {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "member1", Authority.admin);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());
    }
}
