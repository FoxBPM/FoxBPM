<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="org.foxbpm.engine.impl.entity.UserEntity" %>
<%
	UserEntity user = (UserEntity)request.getSession().getAttribute("user");
%>
<!-- #HEADER -->
		<header id="header">
			<div id="logo-group">
				<span id="logo"> <img src="img/logo.png" alt="SmartAdmin"> </span>
			</div>

			<!-- #PROJECTS: projects dropdown -->
			<div class="project-context hidden-xs">

				<span class="label">快捷工具:</span>
				<span class="project-selector dropdown-toggle" data-toggle="dropdown">开发者工具 <i class="fa fa-angle-down"></i></span>

				<!-- Suggestion: populate this list with fetch and push technique -->
				<ul class="dropdown-menu">
					<li>
						<a href="javascript:void(0);">更新服务器端缓存，在发布新流程或更新用户后使用此功能清空服务器端缓存!</a>
					</li>
					<li class="divider"></li>
					<li>
						<a href="javascript:void(0);" id="clearCache"><i class="fa fa-power-off"></i> Clear</a>
					</li>
				</ul>
			</div>
			<!-- #TOGGLE LAYOUT BUTTONS -->
			<!-- pulled right: nav area -->
			<div class="pull-right">
				
				<!-- collapse menu button -->
				<div id="hide-menu" class="btn-header pull-right no-margin">
					<span> <a href="javascript:void(0);" data-action="toggleMenu" title="Collapse Menu"><i class="fa fa-reorder"></i></a> </span>
				</div>
				<!-- end collapse menu -->
				
				<!-- #MOBILE -->
				<!-- Top menu profile link : this shows only when top menu is active -->
				<ul id="mobile-profile-img" class="header-dropdown-list hidden-xs no-padding padding-top-10">
					<li class="">
						<a href="#" class="dropdown-toggle no-margin userdropdown" data-toggle="dropdown"> 
							<!-- <img src="img/avatars/sunny.png" alt="John Doe" class="online" /> -->  
							<img alt="John Doe" src="" name="uImg" class="online" style="width:30px;height:30px"/> 
						</a>
						<ul class="dropdown-menu pull-right">
							<li>
								<a href="javascript:void(0);" class="padding-10 padding-top-0 padding-bottom-0" data-action="launchFullscreen"><i class="fa fa-arrows-alt"></i> <strong> 全屏幕</strong></a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="login.action?doLogOut=lock" class="padding-10 padding-top-0 padding-bottom-0"><i class="fa fa-lock fa-lg"></i> <strong> 锁定系统</strong></a>
							</li>
								<li class="divider"></li>
							<li>
								<a href="login.action?doLogOut=true" class="padding-10 padding-top-5 padding-bottom-5" data-action="userLogout"><i class="fa fa-sign-out fa-lg"></i> <strong> 登出系统</strong></a>
							</li>
						</ul>
					</li>
				</ul>
				<!-- search mobile button (this is hidden till mobile view port) -->
				<div id="search-mobile" class="btn-header transparent pull-right">
					<span> <a href="javascript:void(0)" title="Search"><i class="fa fa-search"></i></a> </span>
				</div>
				<!-- multiple lang dropdown : find all flags in the flags page -->
				<ul class="header-dropdown-list hidden-xs">
					<li>
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img src="img/blank.gif" class="flag flag-cn" alt="China"> <span> 中文</span> <i class="fa fa-angle-down"></i> </a>
						<ul class="dropdown-menu pull-right">
							<li>
								<a href="javascript:void(0);"><img src="img/blank.gif" class="flag flag-cn" alt="China"> 中文</a>
							</li>	
						</ul>
					</li>
				</ul>
				<!-- end multiple lang -->

			</div>
			<!-- end pulled right: nav area -->

		</header>
		<!-- END HEADER -->

		<!-- #NAVIGATION -->
		<!-- Left panel : Navigation area -->
		<!-- Note: This width of the aside area can be adjusted through LESS variables -->
		<aside id="left-panel">

			<!-- User info -->
			<div class="login-info">
				<span> <!-- User image size is adjusted inside CSS, it should stay as is --> 
					
					<a href="javascript:void(0);" id="show-shortcut" data-action="toggleShortcut">
						<!-- <img src="img/avatars/sunny.jpg" alt="me" class="online" />  -->
						<img  alt="me" name="uImg" class="online" style="width:30px;height:30px"/> 
						<span>
						<%=user.getUserName() %>
						</span>
						<i class="fa fa-angle-down"></i>
					</a> 
					
				</span>
			</div>
			<!-- end user info -->

			<!-- NAVIGATION : This navigation is also responsive

			To make this navigation dynamic please make sure to link the node
			(the reference to the nav > ul) after page load. Or the navigation
			will not initialize.
			-->
			<nav>
				<ul>
					<li class="">
						<a href="portal/jsp/dashboard.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-tachometer"></i> <span class="menu-item-parent">个 人 面 板</span></a>
					</li>
					<li>
						<a href="portal/jsp/launchProcess.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-arrow-circle-right"></i> <span class="menu-item-parent">发 起 流 程</span></a>
					</li>
					<li>
						<a href="portal/jsp/todoTasks.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-tasks"></i> <span class="menu-item-parent">待 办 任 务</span></a>
					</li>
					<li>
						<a href="portal/jsp/calendarpanel.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-calendar"></i> <span class="menu-item-parent">日 历 面 板</span></a>
					</li>
					<li>
						<a href="portal/jsp/runningTrackTasks.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-check-square"></i> <span class="menu-item-parent">流 程 追 踪</span></a>
					</li>
					
					<li>
						<a href="#"><i class="fa fa-lg fa-fw fa-calendar"></i> <span class="menu-item-parent">报 销 系 统</span></a>
						<ul>
							<li>
								<a href="portal/expense/expenseList.jsp"><i class="fa fa-lg fa-fw fa-calendartype"></i> <span class="menu-item-parent">报销单列表</span></a>
							</li>
						</ul>
					</li>
				</ul>
			</nav>
			<span class="minifyme" data-action="minifyMenu"> <i class="fa fa-arrow-circle-left hit"></i> </span>

		</aside>
		<!-- END NAVIGATION -->

		<!-- #MAIN PANEL -->
		<div id="main" role="main">

			<!-- RIBBON -->
			<div id="ribbon">

				<span class="ribbon-button-alignment"> 
					<span id="refresh" class="btn btn-ribbon" data-action="resetWidgets" data-title="refresh" rel="tooltip" data-placement="bottom" data-original-title="<i class='text-warning fa fa-warning'></i>注意! 此操作会清空的本地样式缓存." data-html="true" data-reset-msg="是否清空本地缓存，恢复初始样式?"><i class="fa fa-refresh"></i></span> 
				</span>

				<!-- breadcrumb -->
				<ol class="breadcrumb">
					<!-- This is auto generated -->
				</ol>
				<!-- end breadcrumb -->
			</div>
			<!-- END RIBBON -->
<div id="content">
<!-- 处理系统设置 -->
<script type="text/javascript">
	//这里处理左侧菜单展现时机
	initApp.menuPos();
	initApp.leftNav();
</script>
