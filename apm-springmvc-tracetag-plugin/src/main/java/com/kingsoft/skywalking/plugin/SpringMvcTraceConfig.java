package com.kingsoft.skywalking.plugin;

import org.apache.skywalking.apm.agent.core.boot.PluginConfig;

/**
 * 演示如何读取启动时配置的参数
 */
public class SpringMvcTraceConfig {
    public static class Plugin {
        @PluginConfig(root = SpringMvcTraceConfig.class)
        public static class SpringMVC {
            //参数配置为：-Dskywalking.plugin.springmvc.trace_params=pname1,pname2
            //多个参数时用逗号分隔。不配置时默认为 xtraceId。
            // 配置完成后还需要在oap-server配置文件中的searchableLogsTags添加这些参数
            public static String TRACE_PARAMs = "xtraceId";
        }
    }
}