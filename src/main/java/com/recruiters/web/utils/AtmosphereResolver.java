package com.recruiters.web.utils;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Meteor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Resolver for Atmosphere
 * (Websockets technology with fallback implementation)
 */
public class AtmosphereResolver implements HandlerMethodArgumentResolver {

    /**
     * Whether the given {@linkplain org.springframework.core.MethodParameter method parameter} is
     * supported by this resolver.
     *
     * @param parameter the method parameter to check
     * @return {@code true} if this resolver supports the supplied parameter;
     *         {@code false} otherwise
     */
    public boolean supportsParameter(final MethodParameter parameter) {
        return AtmosphereResource.class.isAssignableFrom(parameter.getParameterType());
    }

    /**
     * Resolves a method parameter into an argument value from a given request.
     * A {@link org.springframework.web.method.support.ModelAndViewContainer} provides access to the model for the
     * request. A {@link org.springframework.web.bind.support.WebDataBinderFactory} provides a way to create
     * a {@link org.springframework.web.bind.WebDataBinder} instance when needed for data binding and
     * type conversion purposes.
     *
     * @param parameter     the method parameter to resolve. This parameter must
     *                      have previously been passed to
     *                      {@link #supportsParameter(org.springframework.core.MethodParameter)}
     *                      and it must have returned {@code true}
     * @param mavContainer  the ModelAndViewContainer for the current request
     * @param webRequest    the current request
     * @param binderFactory a factory for creating {@link org.springframework.web.bind.WebDataBinder} instances
     * @return the resolved argument value, or {@code null}.
     * @throws Exception in case of errors with the preparation of argument values
     */
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        Meteor m = Meteor.build(webRequest.getNativeRequest(HttpServletRequest.class));
        return m.getAtmosphereResource();
    }
}
