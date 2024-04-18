package com.hwann.marketmate;

import com.hwann.marketmate.member.Authority;
import com.hwann.marketmate.member.Member;
import com.hwann.marketmate.member.MemberService;
import com.hwann.marketmate.member.MemberServiceImpl;
import com.hwann.marketmate.order.Order;
import com.hwann.marketmate.order.OrderService;
import com.hwann.marketmate.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memberId=1L;
        Member member = new Member(memberId, "member1", Authority.admin);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "item1", 10000);
    }
}
