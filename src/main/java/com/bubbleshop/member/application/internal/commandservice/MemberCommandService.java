package com.bubbleshop.member.application.internal.commandservice;

import com.bubbleshop.config.jwt.TokenProvider;
import com.bubbleshop.config.jwt.TokenView;
import com.bubbleshop.config.properties.NaverConfig;
import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.member.domain.command.CreateAuthorizePageCommand;
import com.bubbleshop.member.domain.command.LoginMemberCommand;
import com.bubbleshop.member.domain.model.aggregate.Member;
import com.bubbleshop.member.domain.model.valueobjects.AuthorizePageInfo;
import com.bubbleshop.member.domain.repository.MemberRepository;
import com.bubbleshop.member.domain.service.AuthorizeService;
import com.bubbleshop.member.domain.service.AuthorizeServiceStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberCommandService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    private final AuthorizeServiceStrategy authorizeServiceStrategy;
    private final AuthorizeService authorizeService;
    private final NaverConfig naverConfig;

    /**
     * 회원 로그인 처리
     * @param command
     */
    public String login(LoginMemberCommand command) {
        // 아이디로 회원 조회시 데이터 없을 경우 에러
        Member member = memberRepository.findById(command.getId())
                .orElseThrow(() -> new ApiException(ResponseCode.MEMBER_NOT_EXIST));

        // 비밀번호가 일치하지 않을 경우 에러
        if(!passwordEncoder.matches(command.getPassword(), member.getPassword())) {
            throw new ApiException(ResponseCode.MEMBER_PASSWORD_ERROR);
        }

        // todo create token and return / refresh token 은 리턴하지 않고 레디스에 저장?
        TokenView view = tokenProvider.createMemberToken(member);
        return view.getAccessToken();
    }

    public AuthorizePageInfo createAuthorizePage(CreateAuthorizePageCommand createAuthorizePageCommand) {
        AuthorizeService authorizeService = authorizeServiceStrategy.getAuthorizeService(createAuthorizePageCommand.getProviderType());
        if (ObjectUtils.isEmpty(authorizeService)) {
            throw new ApiException(ResponseCode.SERVER_ERROR);
        }

        String state = authorizeService.createState();
        String url = authorizeService.getAuthorizeUrl(state);
        return new AuthorizePageInfo(url, state);
    }
}
