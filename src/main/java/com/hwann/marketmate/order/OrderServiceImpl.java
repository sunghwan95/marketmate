package com.hwann.marketmate.order;

import com.hwann.marketmate.member.Member;
import com.hwann.marketmate.member.MemberRepository;
import com.hwann.marketmate.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);

        return new Order(memberId, itemName, itemPrice);
    }
}
