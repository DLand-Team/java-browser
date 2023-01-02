# java-browser
使用Java开发的浏览器，基于Chromium

#### 使用JetBrains Runtime 17，JCEF版本为Chromium 104

#### 何为JetBrains Runtime
- JetBrains Runtime是一个运行时环境，用于在 Windows，MacOS和Linux上运行IntelliJ平台的各种产品。JetBrains Runtime基于OpenJDK项目，并进行了一些修改。这些修改包括：抗锯齿，Linux 上增强的字体渲染，HiDPI支持，对Swing的部分优化与扩展以及其他小的增强功能
- JetBrains Runtime 17（JDK 17）中集成了JCEF

#### 何为JCEF
- Java Chromium嵌入式框架（JCEF）。 一个简单的框架，用于使用Java编程语言在桌面应用程序中嵌入基于Chromium的浏览器
- JCEF项目地址：https://bitbucket.org/chromiumembedded/java-cef/src/master/

#### 获取JetBrains Runtime
- https://github.com/JetBrains/JetBrainsRuntime/releases
- 在Binaries for developers这一栏中下载“JBR with JCEF”，文件名以“jbrsdk_jcef”开头的

#### 运行方法
- 该项目为普通Java项目，未使用Maven或Gradle，IDE配置文件已一并提交，直接导入Eclipse或IDEA中即可运行，需导入lib目录下的全部jar
