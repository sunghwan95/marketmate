package com.hwann.marketmate.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class MemberSereviceTest {

    MemberService memberService = new MemberServiceImpl();

    @Test
    void join(){
        //given
        Member member = new Member(1L, "member1", Authority.admin);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
