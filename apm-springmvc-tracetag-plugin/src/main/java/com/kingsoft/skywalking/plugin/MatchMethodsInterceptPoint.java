package com.kingsoft.skywalking.plugin;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.DeclaredInstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.match.MethodInheritanceAnnotationMatcher;

/**
 * 注意，当前类继承的是DeclaredInstanceMethodsInterceptPoint这个标记接口，很重要
 * 不能直接继承InstanceMethodsInterceptPoint，因为标记接口拦截的是类原来定义的方法，
 * 不包括skywalking动态扩展的方法，如果直接继承InstanceMethodsInterceptPoint会报错
 */
public class MatchMethodsInterceptPoint implements DeclaredInstanceMethodsInterceptPoint {
    @Override
    public ElementMatcher<MethodDescription> getMethodsMatcher() {
        //如果方法添加了以下注释，即为要拦截的对象
        return MethodInheritanceAnnotationMatcher.byMethodInheritanceAnnotationMatcher(ElementMatchers.named("org.springframework.web.bind.annotation.RequestMapping"))
                .or(MethodInheritanceAnnotationMatcher.byMethodInheritanceAnnotationMatcher(ElementMatchers.named("org.springframework.web.bind.annotation.GetMapping")))
                .or(MethodInheritanceAnnotationMatcher.byMethodInheritanceAnnotationMatcher(ElementMatchers.named("org.springframework.web.bind.annotation.PostMapping")))
                .or(MethodInheritanceAnnotationMatcher.byMethodInheritanceAnnotationMatcher(ElementMatchers.named("org.springframework.web.bind.annotation.PutMapping")))
                .or(MethodInheritanceAnnotationMatcher.byMethodInheritanceAnnotationMatcher(ElementMatchers.named("org.springframework.web.bind.annotation.DeleteMapping")))
                .or(MethodInheritanceAnnotationMatcher.byMethodInheritanceAnnotationMatcher(ElementMatchers.named("org.springframework.web.bind.annotation.PatchMapping")));
    }

    @Override
    public String getMethodsInterceptor() {
        //拦截器全名
        return "com.kingsoft.skywalking.plugin.SpringMvcInterceptor";
    }

    @Override
    public boolean isOverrideArgs() {
        return false;
    }
}