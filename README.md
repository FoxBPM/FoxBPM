#简介

FoxBPM(fixflow6.0 6.0版本开始改名为FoxBPM)是一款开源的基于BPMN2.0标准的工作流引擎,引擎底层直接支持BPMN2.0国际标准,
吸纳了 jBPM3 、 Activiti5、BonitaBPM 等国际开源流程引擎的精髓,
同时提供了强大的中国式流程流转处理,引擎采用微内核+插件形式设计,提供灵活的扩展模式, 建模采 用基于BPMN2.0标准的Eclipse设计器和基于Web的流程设计器,不仅仅为审批流程提供了解决方案, 同时还为复杂业务流程编排提供了强大的支持。

FoxBPM本身并不具备完整的开发平台功能,它的定位是专门用于集成到现有系统的引擎。




#未来的版本
### 6.0.0（2014-12）
功能方向：
 
全新版本,重写引擎内核和设计器,BPMN2.0规范全方位支持,同时引入  Dubbo、MuleEsb、ActiveMQ 支持

1.全新的流程微内核构架,kernel项目支持多流程定义。

2.引入mybatis完成引擎持久化层工作。

3.引入运行轨迹，可用于监控和前端运行轨迹回放。

4.引入全局事件注入机制。

5.提供多种更加简单的事务整合方式。

6.全新的处理人选择器,方便开发人员选择处理者,积累处理者选择规则。

7.全新的eclipse流程设计器,基于webserver请求数据，并可以离线运行。

8.可扩展的实现事件支持(Message、Error、Signal、Link、Compensation、Escalation、Conditional)

9.事务节点、事件子流程、事件网关

10.可扩展的规则引擎引入(Drools)

11.功能更加完善的web流程设计器

12.这次文档一定很详细：）




#历史的脚印


### 5.2.1 hotfix（2014-4）

1.修复了性能问题



### 5.2.0（2014-3）

1.引入Maven方式构建,重新调整项目结构。

2.调整配置文件位置,流程系统文件位置可自由配置。

3.重构了异常体系,引入异常国际化支持,更加合理的异常管理。

4.重新设计了日志体系引入slf4j,流程的流转过程通过日志框架输出。

5.修复5.2里程碑中的Issues


### 5.1.0（2014-1）

#### Web流程设计器(预览版)

1. 新增流程资源管理器

2. 新增web流程编辑器,支持流程加载、保存、发布,暂不支持连接器


#### Eclipse设计器新增功能

1. 设计器插件新增支持支持kepler版本的eclipse.插件将支持主流的 Indigo (3.7) Juno (4.2)kepler(4.3).

2. 新的表达式编写方式,支持手写表达式不需要弹出编辑框.

3. 流程创建模板功能,选中一个节点创建模板,可以在任何流程中来使用创建的模板.

4. 全新连接器,支持更多控件模型,兼容老连接器可以继续使用.


#### 流程引擎新增功能

1. 引擎支持子流程终止退回主流程指定节点.

2. 新增全局操作表单、全局浏览表单.

3. 数据库语句外置,开发人员可自行修改.

4. 新增执行Rule配置。

### 5.0.0（2013-10）
1. FixFlow引擎成为独立项目,从CS SOA中间件中剥离,贡献给开源社区。


### 4.7.0（2013-6）
1. CS SOA中间件 4.7版本发布.内置fixflow4.7版本流程引擎。


### 4.0.0（2012-3）
1. CS SOA中间件 4.0版本发布.内置fixflow4.0版本流程引擎
2. 开始支持BPMN标准,设计器改为基于Eclipse插件方式。

### 3.5.0（2010-5）
1. CS SOA中间件 3.5版本发布.内置fixflow3.5版本流程引擎。


### 3.0.0（2009-11）
1. CS SOA中间件 3.0版本发布.内置fixflow3.0版本流程引擎。



### 2.5.0（2007-06）
1. ES平 2.5版本发布.内置fixflow2.5版本流程引擎,分.net、java两个版本。


### 1.0.0（2000-05）
1. ES平台 1.0版本发布.内置fixflow1.0版本流程引擎
