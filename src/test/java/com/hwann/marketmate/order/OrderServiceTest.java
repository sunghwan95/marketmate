package com.hwann.marketmate.order;

import com.hwann.marketmate.member.Authority;
import com.hwann.marketmate.member.Member;
import com.hwann.marketmate.member.MemberService;
import com.hwann.marketmate.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    @Test
    void createOrder(){
        Long memberId = 1L;
        Member member = new Member(memberId, "member1", Authority.admin);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "item1", 10000);
        Assertions.assertThat(order.getItemPrice()).isEqualTo(10000);
    }
}
