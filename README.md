# analysis_visualization_system
### 电商评论数据的分析与可视化系统
使用WebMagic爬虫完成数据爬取。
使用MapReduce编程框架编写清洗算法从Json数据中提取评论文本。
使用百度ECharts完成数据可视化。
涉及到的技术有：Hadoop相关、Maven、Spring、SpringMVC、Mybatis、ECharts。
实现的功能：对电商评论数据的采集、清洗、存储、分析与可视化。
### 1.项目介绍
本系统基于Hadoop平台，使用Maven进行项目管理，基于ssm框架开发的项目，使用mysql数据库，前端采用JQuery+Bootstrap+ECharts展示数据。


### 2.开发工具介绍
### 开发工具
系统开发过程中使用到的开发工具及软件版本情况

VMware Workstation Pro   12.0
IntelliJ IDEA 	        2019.1
Xshell	                  5
CentOS	                 6.8
Hadoop	                2.7.2
JDK	                     1.8
MySQL	                   5.5
Tomcat	                 8.5

### 3.部署流程
1. 配置Hadoop集群
Hadoop102（192.168.8.2）、Hadoop103（192.168.8.3）、Hadoop104（192.168.8.4）
集群部署规划如下


2. 使用comments.sql创建数据库表
3. 在Linux环境下安装IDEA并导入analysis_visualization_system项目
4. 利用tomcat启动项目
5. 在浏览器中输入http://hadoop102:8080/analysis_visualization_system_war_exploded/
		


