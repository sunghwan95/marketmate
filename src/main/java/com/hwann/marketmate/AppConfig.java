package com.hwann.marketmate;

import com.hwann.marketmate.member.MemberRepository;
import com.hwann.marketmate.member.MemberService;
import com.hwann.marketmate.member.MemberServiceImpl;
import com.hwann.marketmate.member.MemoryMemberRepository;
import com.hwann.marketmate.order.OrderService;
import com.hwann.marketmate.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository());
    }
}
