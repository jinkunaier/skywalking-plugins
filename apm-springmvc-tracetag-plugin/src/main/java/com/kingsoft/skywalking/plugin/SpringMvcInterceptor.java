package com.kingsoft.skywalking.plugin;

import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.context.tag.StringTag;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Controller方法拦截器，在该方法中，如果发现有xtraceId参数，则在当前span中添加标识
 *
 * @Auther: Jack
 * @Date: 2021/11/8 15:01
 * @Description:
 */
public class SpringMvcInterceptor implements InstanceMethodsAroundInterceptor {

    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, MethodInterceptResult result) throws Throwable {
        //do nothings
    }

    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        //上方法后执行，因为当前插件其实依赖官方spring-mvc-annotation-x插件创建的span，
        //放在方法后执行保证当前已经存在span
        HttpServletRequest request = getRequest();
        if (request != null) {
            String[] traceParams = SpringMvcTraceConfig.Plugin.SpringMVC.TRACE_PARAMs.split(",");
            for (String traceParam : traceParams) {
                String traceValue = request.getParameter(traceParam);
                if (traceValue != null && !"".equals(traceValue.trim())) {
                    AbstractSpan span = ContextManager.activeSpan();
                    span.tag(new StringTag(traceParam), traceValue);
                }
            }
        }
        return ret;
    }

    @Override
    public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {

    }

    private HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }
}