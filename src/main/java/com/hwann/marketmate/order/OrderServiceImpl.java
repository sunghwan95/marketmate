package com.hwann.marketmate.order;

import com.hwann.marketmate.member.Member;
import com.hwann.marketmate.member.MemberRepository;

public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository;

    public OrderServiceImpl(MemberRepository MemberRepository) {
        this.memberRepository = MemberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);

        return new Order(memberId, itemName, itemPrice);
    }
}
