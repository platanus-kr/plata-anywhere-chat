package org.platanus.platachat.web.auth.argumentresolver;

import org.platanus.platachat.web.auth.dto.AuthServiceMemberDto;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class AuthServiceMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasMemberAnnotation = parameter.getParameterAnnotation(HasMember.class) != null;
        boolean isMemberClass = AuthServiceMemberDto.class.equals(parameter.getParameterType());
        return hasMemberAnnotation && isMemberClass;
    }

    /**
     * auth-service 토큰에서 구성한 사용자 컨텍스트를 가져오기 위한 메서드
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AuthServiceMemberDto member) {
            return member;
        }
        return null;
    }
}
