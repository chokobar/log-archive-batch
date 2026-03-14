package com.chokobar.api.dto;

import com.chokobar.api.entity.Member;
import lombok.Getter;

@Getter
public class MemberSignupResponse {

    private final Long memberId;
    private final String loginId;
    private final String name;

    public MemberSignupResponse(Long memberId, String loginId, String name) {
        this.memberId = memberId;
        this.loginId = loginId;
        this.name = name;
    }

    public static MemberSignupResponse from(Member member) {
        return new MemberSignupResponse(member.getId(), member.getLoginId(), member.getName());
    }
}
