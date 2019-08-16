# MinBox Logging

<p align="left">
   <a href="https://gitter.im/api-boot/minbox-logging?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge">
     <img src="https://badges.gitter.im/api-boot/minbox-logging.svg" alt"Gitter">
  </a>
    <a href="https://search.maven.org/search?q=a:minbox-logging">
        <img src="https://img.shields.io/maven-central/v/org.minbox.framework/minbox-logging.svg?label=Maven%20Central" alt="Maven Center">
    </a>
    <a href="https://github.com/weibocom/motan/blob/master/LICENSE">
        <img src="https://img.shields.io/badge/License-Apache%202.0-green.svg" alt="Apache License">
    </a>
    <a href="#">
        <img src="https://img.shields.io/badge/JDK-1.8+-green.svg" alt="JDK Version">
  </a>
</p>

`MinBox Logging`是一款分布式、零侵入式的链路日志分析框架，支持`SpringCloud`微服务架构下配置使用，内部封装了`RestTemplate`、`OpenFeign`两种方式透传链路信息。

## 日志客户端

`链路日志`的采集端，也就是具体的业务服务，只需要添加`minbox-logging-client`客户端进行简单的配置就可以实现日志的采集以及日志的异步上报。

## 日志管理端

对外提供`安全的日志上报`功能，通过整合`Spring Security`来完成`Basic Auth`基础认证，管理端接收到采集端上报的日志后会自动将日志存储到数据库，考虑到扩展方便，同样也提供监听`日志上报事件(ReportLogEvent)`的方式来自定义存储日志到`消息队列`、`logstash`、`文件`等存储介质。

## 日志管理界面

`MinBox Logging Admin`提供了界面管理应用程序，通过`VUE`前端框架进行编写界面与管理端的接口通讯，实时展示`链路日志`、`日志采集服务列表`等信息，可查看每条链路日志的详细信息（包含：请求头、请求参数、响应内容、响应头、状态码、异常堆栈信息等）。

### 安全性

`MinBox Logging Admin UI`内部提供了`login.html`登录页面，可以整合`Spring Security`通过配置`loginPageUrl`登录跳转地址来使用内置的登录页面，当然也可以自定义登录页面。

可与`MinBox Logging Admin`共同使用`Spring Security`的相同配置提升安全性。

## 文档

当前版本的文档，请访问[MinBox Logging Wikis](https://gitee.com/minbox-projects/minbox-logging/wikis/Home)

## 快速开始

请访问快速开始的`wikis`文档来查看并快速接入`MinBox Logging`，[快速接入文档](https://gitee.com/minbox-projects/minbox-logging/wikis/quick-start)

## 源码方式构建

`MinBox Logging`使用`Maven`进行构建项目。

### 先决条件

源码拉取需使用`Git`，具体使用方式详见[安装 Git](https://help.github.com/en/articles/set-up-git)

源码采用`JDK1.8`版本进行编写，请注意修改使用项目的`JDK`版本，[JDK 1.8下载地址](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

需本地安装`Maven`环境，[Maven 下载地址](https://maven.apache.org/download.cgi)

因`logging-admin-ui`所需`vue-cli-service`命令支持，本地需要安装`vue-cli`。

###拉取源码到本地

```sh
git clone git@gitee.com:minbox-projects/minbox-logging.git
```

### 安装到本地Maven仓库

```sh
mvn install
```

###编译 & 打包生成jar包 

```sh
mvn clean package
```

## Maven仓库依赖构建

`MinBox Logging`内的模块都已经上传到`Apache Maven Center`中央仓库，添加对应的依赖到`pom.xml`会自动下载到本地。

## 欢迎提交贡献代码

`MinBox Logging`欢迎广大开发者创建`Pull Request`来贡献代码，代码通过审核后会被合并到`master`主分支。

## 开源许可

`MinBox Logging`采用`Apache2`开源许可。