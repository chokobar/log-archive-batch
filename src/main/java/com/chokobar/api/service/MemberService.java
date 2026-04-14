package com.chokobar.api.service;

import com.chokobar.api.dto.MemberSignupRequest;
import com.chokobar.api.entity.Member;
import com.chokobar.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Member signup(MemberSignupRequest request) {
        if (memberRepository.existsByLoginId(request.getLoginId())) {
            throw new ResponseStatusException(CONFLICT, "이미 사용 중인 회원 아이디입니다.");
        }

        Member member = new Member(
                request.getLoginId(),
                passwordEncoder.encode(request.getPassword()),
                request.getName()
        );

        return memberRepository.save(member);
    }
}
