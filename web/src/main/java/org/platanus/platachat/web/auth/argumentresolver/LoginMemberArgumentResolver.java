package org.platanus.platachat.web.auth.argumentresolver;

import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginMemberAnnotation = parameter.getParameterAnnotation(HasMember.class) != null;
        boolean isMemberClass = SessionMemberDto.class.equals(parameter.getParameterType());
        return isLoginMemberAnnotation && isMemberClass;
    }

    /**
     * 세션에서 SessionMember 객체 가져오기 위한 메서드
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        return request.getSession().getAttribute("member");
    }
}
