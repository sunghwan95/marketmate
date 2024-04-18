package com.hwann.marketmate;

import com.hwann.marketmate.member.Authority;
import com.hwann.marketmate.member.Member;
import com.hwann.marketmate.member.MemberService;
import com.hwann.marketmate.order.Order;
import com.hwann.marketmate.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {
        /*AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();*/
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId=1L;
        Member member = new Member(memberId, "member1", Authority.admin);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "item1", 10000);
    }
}
