# 图书馆管理工具 （Demo）
## 介绍
这是一个模拟图书馆管理工具的Demo。使用简单，基于内存存储。

## 构建
安装Maven，然后构建

```shell
maven package dependency:copy-dependencies -DoutputDirectory=target/lib
```

## 生成目录
生成target目录，可执行的jar为`library_manager.jar`，依赖库为`lib`。
执行方式
```shell
java -jar ./target/library_manager.jar
```

## 打包
可以使用jlink和jpackage打包。
