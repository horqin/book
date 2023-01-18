# 简介

一个支持用户注册、登录、登出，文献新增、删除、修改、查询，以及基于 ES 简单搜索的系统。基于 Spring Boot 和 Vue 3 实现，其中，用户部分基于 Sa-Token，ES 与 MySQL 之间的数据同步基于 Canal。

![](./img/book-simple_search.png)

## 技术依赖

### 前端

|名称|简介|版本|
|:-:|:-:|:-:|
|TypeScript|编程语言|4.x|
|Vite|构建工具|3.x|
|Vue 3|框架|3.x|
|Vue Router|组件|4.x|
|Ant Design Vue|组件|2.x|
|Axios|工具类||

## 后端

|名称|简介|版本|
|:-:|:-:|:-:|
|Maven|构建工具|3.x|
|Spring Boot|框架|2.x|
|MyBatis-Plus|组件|3.x|
|Sa-Token|组件|1.x|
|Swagger|接口文档|3.x|
|Lombok|工具类||
|Commons Lang|工具类||
|Gson|工具类||

# 中间件

|名称|简介|版本|
|:-:|:-:|:-:|
|MySQL|数据库|5.x|
|Canal|数据同步|1.x|
|Elasticsearch|搜索引擎|7.x|
|Kafka|消息队列|2.x|
|Nginx|项目部署||

# 工具

|名称|简介|版本|
|:-:|:-:|:-:|
|VMware Workstation Pro|虚拟机||
|XShell|Shell 客户端||
|SQLyog| SQL 客户端||
|Docker|项目部署||
|IntelliJ IDEA|IDE||
|Visual Studio Code|IDE||
|Postman|接口测试||
