# RPC Framework

这是一个简洁高效的 RPC (Remote Procedure Call) 框架项目，主要用于实现不同服务之间的远程调用，提供高性能、灵活的服务注册和管理机制。该项目设计为易于扩展和集成，适合用于微服务架构或分布式系统。

## 功能特色

- **支持多种序列化协议**：通过接口灵活选择不同的序列化协议，如 JDK 序列化、fastJson、Protobuf 等。目前正参考Dubbo重构序列化模块
    - [x] JDK 序列化 ;
    - [ ] fastJson ;
    - [ ] Protobuf ;
- **服务注册与发现**：支持服务注册和实例管理，便于动态负载均衡和服务扩展。
- **异常处理**：提供详细的错误处理和异常抛出机制，确保服务调用的可靠性。
- **高并发支持**：轻量级、高效的框架设计，适合于高并发场景。
- **日志记录**：支持多级别的日志记录，便于问题跟踪和调试。

## 主要模块

- `ServiceRegistry`：用于管理服务的注册和发现。
- `MessageProtocol`：用于定义序列化和反序列化协议。
- `RpcRequest` 与 `RpcResponse`：请求和响应对象的封装。
- `RpcClient` 与 `RpcServer`：用于建立客户端和服务端的连接。

## 安装与使用

### 1. 克隆项目

```bash
git clone https://github.com/HaibaraPeng/easy-rpc.git
```

### 2. 引入依赖
在项目的 pom.xml 中引入以下依赖：
```xml
<dependency>
  <groupId>org.example.rpc</groupId>
  <artifactId>easy-rpc-spring-boot-starter</artifactId>
  <version>${easy-rpc.version}</version>
</dependency>
```

### 3. 使用
见`easy-rpc-example`