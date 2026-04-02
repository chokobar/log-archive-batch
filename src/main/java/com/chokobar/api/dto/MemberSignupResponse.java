package com.chokobar.api.dto;

import com.chokobar.api.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSignupResponse {

    private final Long memberId;
    private final String loginId;
    private final String name;

    public static MemberSignupResponse from(Member member) {
        return new MemberSignupResponse(member.getId(), member.getLoginId(), member.getName());
    }
}
