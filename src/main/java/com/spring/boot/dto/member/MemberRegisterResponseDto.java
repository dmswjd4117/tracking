package com.spring.boot.dto.member;

public class MemberRegisterResponseDto {
    private MemberDto member;

    public MemberRegisterResponseDto(MemberDto member){
        this.member = member;
    }

    public MemberDto getMember() {
        return member;
    }
}
