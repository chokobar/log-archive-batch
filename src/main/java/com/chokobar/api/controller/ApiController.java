package com.chokobar.api.controller;

import com.chokobar.api.dto.ApiDto;
import com.chokobar.api.dto.MemberSignupRequest;
import com.chokobar.api.dto.MemberSignupResponse;
import com.chokobar.api.entity.Member;
import com.chokobar.api.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiController {

    private final MemberService memberService;

    @GetMapping("/api/member/{id}")
    public ApiDto getMember(@PathVariable String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }

        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id는 숫자여야 합니다.");
        }

        return new ApiDto(id, "Hello " + id);
    }

    @PostMapping("/api/members/signup")
    public MemberSignupResponse signup(@Valid @RequestBody MemberSignupRequest request) {
        Member member = memberService.signup(request);
        return MemberSignupResponse.from(member);
    }

}
