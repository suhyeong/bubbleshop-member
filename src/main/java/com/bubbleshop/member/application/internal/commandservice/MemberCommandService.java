package com.bubbleshop.member.application.internal.commandservice;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.member.domain.command.LoginMemberCommand;
import com.bubbleshop.member.domain.model.aggregate.Member;
import com.bubbleshop.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCommandService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 로그인 처리
     * @param command
     */
    public void login(LoginMemberCommand command) {
        // 아이디로 회원 조회시 데이터 없을 경우 에러
        Member member = memberRepository.findById(command.getId())
                .orElseThrow(() -> new ApiException(ResponseCode.MEMBER_NOT_EXIST));

        // 비밀번호가 일치하지 않을 경우 에러
        if(!passwordEncoder.matches(command.getPassword(), member.getPassword())) {
            throw new ApiException(ResponseCode.MEMBER_PASSWORD_ERROR);
        }

        // todo create token and return / refresh token 은 리턴하지 않고 레디스에 저장?
    }
}
