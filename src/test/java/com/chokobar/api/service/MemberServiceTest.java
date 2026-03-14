package com.chokobar.api.service;

import com.chokobar.api.dto.MemberSignupRequest;
import com.chokobar.api.entity.Member;
import com.chokobar.api.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void signupSavesMember() {
        MemberSignupRequest request = new MemberSignupRequest();
        request.setLoginId("tester");
        request.setPassword("password123");
        request.setName("테스터");

        given(memberRepository.existsByLoginId("tester")).willReturn(false);
        given(memberRepository.save(any(Member.class))).willAnswer(invocation -> {
            Member member = invocation.getArgument(0);
            return new Member(member.getLoginId(), member.getPassword(), member.getName());
        });

        Member savedMember = memberService.signup(request);

        assertThat(savedMember.getLoginId()).isEqualTo("tester");
        assertThat(savedMember.getName()).isEqualTo("테스터");
        assertThat(savedMember.getPassword()).isNotEqualTo("password123");
    }

    @Test
    void signupThrowsWhenLoginIdExists() {
        MemberSignupRequest request = new MemberSignupRequest();
        request.setLoginId("tester");
        request.setPassword("password123");
        request.setName("테스터");

        given(memberRepository.existsByLoginId("tester")).willReturn(true);

        assertThatThrownBy(() -> memberService.signup(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("이미 사용 중인 회원 아이디입니다.");
    }
}
