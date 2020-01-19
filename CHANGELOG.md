## 1.0.2.RELEASE 更新日志

- 升级使用minbox-framework版本为1.0.2.RELEASE
- 添加GlobalLogging全局业务日志的概念
- 支持将GlobalLog日志保存到数据库
- 修改LocalNotice本次日志通知的优先级
- 修改AdminNotice上报日志到Admin的优先级
- 添加支持定时上报到Admin功能支持
- 添加支持debug、info、error等类型的GlobalLog上报
- 添加GlobalLoggingMemoryStorage用于内存临时存储一次请求的GlobalLog列表
- 修改日志上报到Admin时的Content-Type
- 添加logging_global_logs表结构
- 支持采集error等级的GlobalLog堆栈错误信息

## 1.0.1.RELEASE 更新日志

- logging-client支持配置多个指定logging-admin地址
- logging-client支持平滑权重负载均衡上报指定logging-admin
- logging-client支持随机权重负载均衡上报指定logging-admin
- logging-admin支持不配置basic auth用户名、密码信息
- 修改LoggingFactoryBean初始化配置方式为afterPropertiesSet
- logging-client排除强制上报到logging-admin
- logging-admin支持自定义LoggingStorage存储日志
- logging-admin排除强依赖DataSource
- logging-admin删除LoggingAdminUiFactoryBean配置，合并到LoggingAdminFactoryBean内UiSetting
- logging-client修改LoggingInterceptor为LoggingWebInterceptor