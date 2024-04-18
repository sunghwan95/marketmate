package com.hwann.marketmate.member;

public class Member {
    private Long id;
    private String name;
    private Authority authority;

    public Member(Long id, String name, Authority authority) {
        this.id = id;
        this.name = name;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}
