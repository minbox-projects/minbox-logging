# MinBox Logging
![Ci Builder](https://github.com/minbox-projects/minbox-logging/workflows/Ci%20Builder/badge.svg?branch=master)
![](https://codecov.io/gh/minbox-projects/minbox-logging/branch/master/graph/badge.svg)
![](https://tokei.rs/b1/github/minbox-projects/minbox-logging?category=lines)
![](https://img.shields.io/maven-central/v/org.minbox.framework/minbox-logging.svg?label=Maven%20Central)
![](https://img.shields.io/badge/License-Apache%202.0-blue.svg)
![](https://img.shields.io/badge/JDK-1.8+-blue.svg)

`MinBox Logging`是一款分布式、零侵入式的链路日志分析框架，支持`SpringCloud`微服务架构下配置使用，内部封装了`RestTemplate`、`OpenFeign`两种方式透传链路信息。

## 零侵入式

`MinBox Logging`无需使用注解配置采集链路日志，只需要添加`依赖`后简单配置`Minbox Loggin Admin`的相关`地址`或`服务名称`即可，每次在收到请求时就会把请求对应的链路日志详细信息自动上报到`MinBox Logging Admin`进行后续分析、告警通知等。

## 链路日志分析图

![](https://apiboot.minbox.org/img/logging/minbox-logging-trace.png)

## 日志客户端

`链路日志`的采集端，也就是具体的业务服务，只需要添加`minbox-logging-client`客户端进行简单的配置就可以实现日志的采集以及日志的异步上报。

## 日志管理端

对外提供`安全的日志上报`功能，通过整合`Spring Security`来完成`Basic Auth`基础认证，管理端接收到采集端上报的日志后会自动将日志存储到数据库，考虑到扩展方便，同样也提供监听`日志上报事件(ReportLogEvent)`的方式来自定义存储日志到`消息队列`、`logstash`、`文件`等存储介质。

## 日志管理界面

`MinBox Logging Admin`提供了界面管理应用程序，通过`VUE`前端框架进行编写界面与管理端的接口通讯，实时展示`链路日志`、`日志采集服务列表`等信息，可查看每条链路日志的详细信息（包含：请求头、请求参数、响应内容、响应头、状态码、异常堆栈信息等）。

### 整合Spring Security后的登录界面

![](https://apiboot.minbox.org/img/logging/logging-admin-login.png)

可以直接整合`Spring Security`来完成`Basic`安全认证。

### 上报日志的服务列表

![](https://apiboot.minbox.org/img/logging/logging-admin-service.png)

请求日志的采集服务在第一次上报时，会自动创建一条服务记录，服务的唯一性是根据：`服务ID` + `服务IP` + `服务端Port` 来进行定义。

> 在每次上报日志成功后会修改服务的最后一次上报时间。

### 链路日志列表

![](https://apiboot.minbox.org/img/logging/logging-admin-logs.png)

链路日志是我们访问的入口服务生成，比如：`bff-user` -> `user-service`，链路日志产生的位置则是`bff-user`服务，链路日志是接口`请求的入口`也是做出`响应的结束位置`，因此我们可以在页面上查看接口的请求`参数详情`、`头信息`以及`响应的内容`，至于链路日志内的`日志单元`目前界面还未做展示，可以根据`traceId`链路日志编号自行去数据库查询。

### 链路日志详情

![](https://apiboot.minbox.org/img/logging/logging-admin-log-detail.png)

每一个请求的链路日志都会包含上图中的全部字段，从请求开始 -> 响应结束一条链路闭合记录。

### 安全性

`MinBox Logging Admin UI`内部提供了`login.html`登录页面，可以整合`Spring Security`通过配置`loginPageUrl`登录跳转地址来使用内置的登录页面，当然也可以自定义登录页面。

可与`MinBox Logging Admin`共同使用`Spring Security`的相同配置提升安全性。

## 文档

当前版本的文档，请访问<a href="https://gitee.com/minbox-projects/minbox-logging/wikis" target="_blank">Wikis</a>

## 快速开始

请访问快速开始的`wikis`文档来查看并快速接入`MinBox Logging`，<a href="https://gitee.com/minbox-projects/minbox-logging/wikis" target="_blank">快速接入文档</a>

## 源码方式构建

`MinBox Logging`使用`Maven`进行构建项目。

### 先决条件

源码拉取需使用`Git`，具体使用方式详见[安装 Git](https://help.github.com/en/articles/set-up-git)

源码采用`JDK1.8`版本进行编写，请注意修改使用项目的`JDK`版本，[JDK 1.8下载地址](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

需本地安装`Maven`环境，[Maven 下载地址](https://maven.apache.org/download.cgi)

因`logging-admin-ui`所需`vue-cli-service`命令支持，本地需要安装`vue-cli`。

### 拉取源码到本地

```sh
git clone git@gitee.com:minbox-projects/minbox-logging.git
```

### 安装到本地Maven仓库

```sh
mvn install
```

### 编译 & 打包生成jar包 

```sh
mvn clean package
```

## Maven仓库依赖构建

`MinBox Logging`内的模块都已经上传到`Apache Maven Center`中央仓库，添加对应的依赖到`pom.xml`会自动下载到本地。

## 欢迎提交贡献代码

`MinBox Logging`欢迎广大开发者创建`Pull Request`来贡献代码，代码通过审核后会被合并到`master`主分支。

## 开源许可

`MinBox Logging`采用`Apache2`开源许可。