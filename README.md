#简介

FoxBPM(6.0版本开始fixflow改名为FoxBPM)是一款开源的基于BPMN2.0标准的工作流引擎,引擎底层直接支持BPMN2.0国际标准,
吸纳了 jBPM3 、 Activiti5、BonitaBPM 等国际开源流程引擎的精髓,
同时提供了强大的中国式流程流转处理,引擎采用微内核+插件形式设计,提供灵活的扩展模式, 建模采用基于BPMN2.0标准的Eclipse设计器,不仅仅为审批流程提供了解决方案, 同时还为复杂业务流程编排提供了强大的支持。

FoxBPM本身并不具备完整的开发平台功能,它的定位是专门用于集成到现有系统的引擎。


#其他Git仓库
国内访问速度比较慢的朋友可以考虑从国内的Git仓库拉取代码:

**开源中国社区-中国**:(https://git.oschina.net/kenshinnet/fixflow)

**GitHub-美国**:(https://github.com/FoxBPM/FoxBPM)

**csdn_code -中国**:(https://code.csdn.net/fixflow/fixflow)  


#为什么选择FoxBPM？
• 开源以及强大的社区支持

• 基于国际业务流程标准BPMN2.0

• 支持复杂式的中国流程流转处理

• 强大的基于BPMN2.0建模的Eclipse插件设计器

• 强大灵活的扩展模式

• 基于图形化设计的外部系统调用连接器

• 专门用于集成的BPM产品

• 支持Groovy等多种动态脚本引擎


#资源打包下载
包含数据库脚本、jar包、用户向导、设计器插件、war包、内核源码，全部源码。
* [FoxBPM Release 6.0(百度网盘)](http://pan.baidu.com/s/1hqkqoNM)

#流程设计器
(国内最强大的BPMN设计器)
设计器提供两种版本,完整Eclipse版本、单一插件版本。
* [完整版设计器下载（win32）(百度网盘)](http://pan.baidu.com/s/1eQAcKAe)
* [完整版设计器下载（win64）(百度网盘)](http://pan.baidu.com/s/1pJv7ql1)
* [插件下载 6.0(百度网盘)](http://pan.baidu.com/s/1eQIkpP4)



#资源介绍

**开发者交流社区QQ群**: 434527452

**用户向导**: [http://foxbpm.github.io/FoxBPM/userguide.html](http://foxbpm.github.io/FoxBPM/userguide.html)

**用户向导下载**: [http://pan.baidu.com/s/1bnniqB1](http://pan.baidu.com/s/1bnniqB1)

**常见问题整理（FAQ）**:(http://yang-ch.iteye.com/blog/2171370)


#分支介绍
* develop 最新开发版
* master 最新稳定版
* release-*  发布分支为准备新的产品版本发布做支持
* hotfix-*   当产品版本的重大bug需要立即解决的时候，我们从对应版本的标签创建出一个热补丁分支。
* feature-*  特性分支是用来为下一发布版本开发新特性

#项目介绍

* foxbpm-kernel:Foxbpm流程微内核项目
* 
* foxbpm-engine:Foxbpm流程引擎项目
* 
* foxbpm-bpmn-model:Foxbpm的模型定义
* 
* foxbpm-bpmn-converter:Foxbpm的bpmn的转换器项目
* 
* foxbpm-plugin:Foxbpm的官方扩展插件包项目
* 
* foxbpm-rest:Foxbpm Rest项目
* 
* foxbpm-root:Foxbpm 根项目
* 
* foxbpm-webapps-common:Foxbpm web根项目
* 
* foxbpm-webapps-portal:Foxbpm流程门户


#如何选择？
* FoxBPM提供两种方式的集成:
* 1.完整集成版本,提供任务处理中心、流程管控中心、引擎内核、扩展项目、Junit测试库
* 2.核心集成版本,只提供引擎内核、扩展项目、Junit测试库

* 第一种适合不想在自己开发任务处理和管控中心的用户,用户可以将Fixflow的web和自己的web集成到一起。
* 第二种适合对界面需要大量订制的用户,通过流程提供的Api自己开发任务处理、管控界面。

* 注意：FoxBPM自带的任务处理中心,在应用到实际项目中之前需要对其进行集成开发来使用用户各自系统的要求。


#Eclipse设计器界面
![Eclipse设计器界面](http://images.cnitblog.com/blog/20120/201401/231630266632.png)


#如何提交bug或者问题
在FoxBPM项目上点击Issues->New Issue提交bug,在标签栏选择bug、优先级、状态（未解决）三个标签，然后提交。我们会根据bug内容反馈相关信息给您。
![系统截图](https://github.com/fixteam/fixflow/wiki/images/Bug20130917093746.png)




#新的版本
### 6.0.0（2014-12）
引擎端

1.全新设计的引擎架构，增加了引擎事件体系。

2.模型转换层去除了emf的依赖，采用dom4j转换流程文件及配置文件。

3.采用了mybatis作为持久化层。

4.增加了spring的支持。

5.采用的dataSource的数据库配置方式。

6.新增rest服务项目，提供默认的rest服务。

7.优化一些影响性能的代码，如groovy的转换等。

8.采用bootstrap+springMvc重新开发演示示例。

9.暂时没有提供管控中心和web设计器。
	


设计器端

1.修改创建连接器的方式，直接右键菜单创建。

2.采用rest服务方式同步资源和发布流程，使运行时的资源和设计时资源实时同步。

3.完善引擎的代码提示。

4.全新的任务分配界面，使用封装的选择器代替原来纯代码输入。

5.全新的任务命令参数配置，使用户更加直观的配置参数。

6.将流程启动人权限配置移动到流程定义属性上。




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

