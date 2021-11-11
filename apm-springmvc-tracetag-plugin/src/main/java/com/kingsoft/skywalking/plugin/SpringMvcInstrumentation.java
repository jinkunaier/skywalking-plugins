package com.kingsoft.skywalking.plugin;

/**
 * Controller注解的控制器代码增强类
 *
 * @Auther: Jack
 * @Date: 2021/11/8 14:28
 * @Description:
 */
public class SpringMvcInstrumentation extends AbstractSpringMvcInstrumentation {

    public static final String ENHANCE_ANNOTATION = "org.springframework.stereotype.Controller";

    @Override
    protected String[] getEnhanceAnnotations() {
        return new String[]{ENHANCE_ANNOTATION};
    }
}