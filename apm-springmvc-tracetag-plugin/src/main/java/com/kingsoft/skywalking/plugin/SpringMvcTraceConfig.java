package com.kingsoft.skywalking.plugin;

import org.apache.skywalking.apm.agent.core.boot.PluginConfig;

/**
 * 演示如何读取启动时配置的参数
 */
public class SpringMvcTraceConfig {
    public static class Plugin {
        @PluginConfig(root = SpringMvcTraceConfig.class)
        public static class SpringMVC {
            //参数配置为：-Dskywalking.plugin.springmvc.trace_param=xxxx,
            //不配置时默认为 xtraceId
            public static String TRACE_PARAM = "xtraceId";
        }
    }
}
