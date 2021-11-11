## 说明
* Skywalking插件spring-mvc-trace-tag可以将指定的URL请求参数添加到链路追踪的searchableLogsTags中。便于通过Skywalking UI精确定位请求链路。
默认情况下会记录URL请求中的xtraceId参数。如果存在该参数且不为空，则将其记录到请求入口span的标记中。
* 另外可以在启动参数中指定要记录的参数名，配置为：-Dskywalking.plugin.springmvc.trace_param=you_param_name.
并且需要在oap-server配置文件中的searchableLogsTags添加该参数名。
