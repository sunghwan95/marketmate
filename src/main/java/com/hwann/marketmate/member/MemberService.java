package com.hwann.marketmate.member;

public interface MemberService {
    void join(Member member);

    Member findMember(Long memberId);
}
