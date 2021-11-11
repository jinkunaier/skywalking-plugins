package com.kingsoft.skywalking.plugin;

import org.apache.skywalking.apm.agent.core.logging.api.ILog;
import org.apache.skywalking.apm.agent.core.logging.api.LogManager;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassAnnotationMatch;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;

/**
 * 把URL请求中的指定的参数自动添加到链路追踪的标记中，便于在链路追踪时定位具体的请求。
 * 可通过启动参数-Dskywalking.plugin.springmvc.trace_param=xxxx开定义要记录的参数名称，默认为xtraceId
 * 即当请求中有xtraceId的参数时，会给链路上打上xtraceId=vvv的标记,(vvv为参数值)，可在skywalking UI中根据此标识来定位。
 * 注意：这个参数名还必须在oap-server的配置文件searchableTracesTags中指定，否则也不能参与查询
 *
 * @Auther: Jack
 * @Date: 2021/11/8 14:28
 * @Description:
 */
public abstract class AbstractSpringMvcInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {

    public static final String WITNESS_CLASSES = "org.springframework.web.context.request.RequestContextHolder";

    //用这种方式记录日志，可以在agent的日志文件中输出，便于调试
    private static final ILog LOGGER = LogManager.getLogger(AbstractSpringMvcInstrumentation.class);

    @Override
    protected ClassMatch enhanceClass() {
        //添加了@RestController注解的类,注意，这里如果写多个，表示同是添加了多个注解的类
        //所以@RestController与@Controller两个注解得分开写
        return ClassAnnotationMatch.byClassAnnotationMatch("org.springframework.web.bind.annotation.RestController");
    }

    public ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        //返回空即可，不关注
        return new ConstructorInterceptPoint[0];
    }

    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        // 指定想要监控的实例方法,给一个匹配集合类
        return new InstanceMethodsInterceptPoint[]{new MatchMethodsInterceptPoint()};
    }

    @Override
    protected final String[] witnessClasses() {
        //拦截器中用到了当前类，做为标识，类路径中不存在时不做当前切面
        return new String[]{WITNESS_CLASSES};
    }

    abstract protected String[] getEnhanceAnnotations();
}