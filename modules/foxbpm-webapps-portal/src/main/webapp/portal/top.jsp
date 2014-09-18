<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- #HEADER -->
		<header id="header">
			<div id="logo-group">

				<!-- PLACE YOUR LOGO HERE -->
				<span id="logo"> <img src="img/logo.png" alt="SmartAdmin"> </span>
				<!-- END LOGO PLACEHOLDER -->

				<!-- Note: The activity badge color changes when clicked and resets the number to 0
					 Suggestion: You may want to set a flag when this happens to tick off all checked messages / notifications -->
				<span id="activity" class="activity-dropdown"> <i class="fa fa-user"></i> <b class="badge"> 21 </b> </span>

				<!-- AJAX-DROPDOWN : control this dropdown height, look and feel from the LESS variable file -->
				<div class="ajax-dropdown">

					<!-- the ID links are fetched via AJAX to the ajax container "ajax-notifications" -->
					<div class="btn-group btn-group-justified" data-toggle="buttons">
						<label class="btn btn-default">
							<input type="radio" name="activity" id="ajax/notify/mail.html">
							消息 (14) </label>
						<label class="btn btn-default">
							<input type="radio" name="activity" id="ajax/notify/notifications.html">
							通知 (3) </label>
						<label class="btn btn-default">
							<input type="radio" name="activity" id="ajax/notify/tasks.html">
							任务 (4) </label>
					</div>

					<!-- notification content -->
					<div class="ajax-notifications custom-scroll">

						<div class="alert alert-transparent">
							<h4>Click a button to show messages here</h4>
							This blank page message helps protect your privacy, or you can show the first message here automatically.
						</div>

						<i class="fa fa-lock fa-4x fa-border"></i>

					</div>
					<!-- end notification content -->

					<!-- footer: refresh area -->
					<span> 最后更新时间 : 12/12/2013 9:43AM
						<button type="button" data-loading-text="<i class='fa fa-refresh fa-spin'></i> Loading..." class="btn btn-xs btn-default pull-right">
							<i class="fa fa-refresh"></i>
						</button> </span>
					<!-- end footer -->

				</div>
				<!-- END AJAX-DROPDOWN -->
			</div>

			<!-- #PROJECTS: projects dropdown -->
			<div class="project-context hidden-xs">

				<span class="label">快捷工具:</span>
				<span class="project-selector dropdown-toggle" data-toggle="dropdown">开发者工具 <i class="fa fa-angle-down"></i></span>

				<!-- Suggestion: populate this list with fetch and push technique -->
				<ul class="dropdown-menu">
					<li>
						<a href="javascript:void(0);">Online e-merchant management system - attaching integration with the iOS</a>
					</li>
					<li>
						<a href="javascript:void(0);">Notes on pipeline upgradee</a>
					</li>
					<li>
						<a href="javascript:void(0);">Assesment Report for merchant account</a>
					</li>
					<li class="divider"></li>
					<li>
						<a href="javascript:void(0);"><i class="fa fa-power-off"></i> Clear</a>
					</li>
				</ul>
				<!-- end dropdown-menu-->

			</div>
			<!-- end projects dropdown -->
			
			<!-- #TOGGLE LAYOUT BUTTONS -->
			<!-- pulled right: nav area -->
			<div class="pull-right">
				
				<!-- collapse menu button -->
				<div id="hide-menu" class="btn-header pull-right">
					<span> <a href="javascript:void(0);" data-action="toggleMenu" title="Collapse Menu"><i class="fa fa-reorder"></i></a> </span>
				</div>
				<!-- end collapse menu -->
				
				<!-- #MOBILE -->
				<!-- Top menu profile link : this shows only when top menu is active -->
				<ul id="mobile-profile-img" class="header-dropdown-list hidden-xs padding-5">
					<li class="">
						<a href="#" class="dropdown-toggle no-margin userdropdown" data-toggle="dropdown"> 
							<!-- <img src="img/avatars/sunny.png" alt="John Doe" class="online" /> -->  
							<img alt="John Doe" src="" name="uImg" class="online" style="width:30px;height:30px"/> 
						</a>
						<ul class="dropdown-menu pull-right">
							<li>
								<a href="javascript:void(0);" class="padding-10 padding-top-0 padding-bottom-0"><i class="fa fa-cog"></i> <strong> 系统设置</strong></a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="portal/jsp/profile.jsp" class="padding-10 padding-top-0 padding-bottom-0"> <i class="fa fa-user"></i> <strong> 个人信息</strong></a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="portal/jsp/profile.html" class="padding-10 padding-top-0 padding-bottom-0"> <i class="fa fa-plane"></i> <strong> 委托授权</strong></a>
							</li>
								<li class="divider"></li>
							<li>
								<a href="javascript:void(0);" class="padding-10 padding-top-0 padding-bottom-0" data-action="toggleShortcut"><i class="fa fa-arrow-down"></i> <strong> 快捷菜单</strong></a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="javascript:void(0);" class="padding-10 padding-top-0 padding-bottom-0" data-action="launchFullscreen"><i class="fa fa-arrows-alt"></i> <strong> 全屏幕</strong></a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="portal/jsp/lock.jsp" class="padding-10 padding-top-0 padding-bottom-0"><i class="fa fa-lock fa-lg"></i> <strong> 锁定系统</strong></a>
							</li>
								<li class="divider"></li>
							<li>
								<a href="portal/jsp/login.jsp" class="padding-10 padding-top-5 padding-bottom-5" data-action="userLogout"><i class="fa fa-sign-out fa-lg"></i> <strong> 登出系统</strong></a>
							</li>
						</ul>
					</li>
				</ul>

				<!-- logout button -->
				<div id="logout" class="btn-header transparent pull-right">
					<span> <a href="login.html" title="Sign Out" data-action="userLogout" data-logout-msg="您确定要退出系统？"><i class="fa fa-sign-out"></i></a> </span>
				</div>
				<!-- end logout button -->

				<!-- search mobile button (this is hidden till mobile view port) -->
				<div id="search-mobile" class="btn-header transparent pull-right">
					<span> <a href="javascript:void(0)" title="Search"><i class="fa fa-search"></i></a> </span>
				</div>
				<!-- end search mobile button -->
				
				<!-- #SEARCH -->
				<!-- input: search field -->
				<form action="#ajax/search.html" class="header-search pull-right">
					<input id="search-fld" type="text" name="param" placeholder="查找待办任务">
					<button type="submit">
						<i class="fa fa-search"></i>
					</button>
					<a href="javascript:void(0);" id="cancel-search-js" title="Cancel Search"><i class="fa fa-times"></i></a>
				</form>
				<!-- end input: search field -->

				<!-- fullscreen button -->
				<div id="fullscreen" class="btn-header transparent pull-right">
					<span> <a href="javascript:void(0);" data-action="launchFullscreen" title="Full Screen"><i class="fa fa-arrows-alt"></i></a> </span>
				</div>
				<!-- end fullscreen button -->

				<!-- #Voice Command: Start Speech -->
				<div id="speech-btn" class="btn-header transparent pull-right hidden-sm hidden-xs">
					<div> 
						<a href="javascript:void(0)" title="Voice Command" data-action="voiceCommand"><i class="fa fa-microphone"></i></a> 
						<div class="popover bottom"><div class="arrow"></div>
							<div class="popover-content">
								<h4 class="vc-title">Voice command activated <br><small>Please speak clearly into the mic</small></h4>
								<h4 class="vc-title-error text-center">
									<i class="fa fa-microphone-slash"></i> Voice command failed
									<br><small class="txt-color-red">Must <strong>"Allow"</strong> Microphone</small>
									<br><small class="txt-color-red">Must have <strong>Internet Connection</strong></small>
								</h4>
								<a href="javascript:void(0);" class="btn btn-success" onclick="commands.help()">See Commands</a> 
								<a href="javascript:void(0);" class="btn bg-color-purple txt-color-white" onclick="$('#speech-btn .popover').fadeOut(50);">Close Popup</a> 
							</div>
						</div>
					</div>
				</div>
				<!-- end voice command -->

				<!-- multiple lang dropdown : find all flags in the flags page -->
				<ul class="header-dropdown-list hidden-xs">
					<li>
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img src="img/blank.gif" class="flag flag-cn" alt="China"> <span> 中文</span> <i class="fa fa-angle-down"></i> </a>
						<ul class="dropdown-menu pull-right">
							<li>
								<a href="javascript:void(0);"><img src="img/blank.gif" class="flag flag-cn" alt="China"> 中文</a>
							</li>	
							<li class="active">
								<a href="javascript:void(0);"><img src="img/blank.gif" class="flag flag-us" alt="United States"> English (US)</a>
							</li>
							<li>
								<a href="javascript:void(0);"><img src="img/blank.gif" class="flag flag-fr" alt="France"> Français</a>
							</li>
							<li>
								<a href="javascript:void(0);"><img src="img/blank.gif" class="flag flag-es" alt="Spanish"> Español</a>
							</li>
							<li>
								<a href="javascript:void(0);"><img src="img/blank.gif" class="flag flag-de" alt="German"> Deutsch</a>
							</li>
							<li>
								<a href="javascript:void(0);"><img src="img/blank.gif" class="flag flag-jp" alt="Japan"> 日本語</a>
							</li>

							<li>
								<a href="javascript:void(0);"><img src="img/blank.gif" class="flag flag-it" alt="Italy"> Italiano</a>
							</li>	
							<li>
								<a href="javascript:void(0);"><img src="img/blank.gif" class="flag flag-pt" alt="Portugal"> Portugal</a>
							</li>
							<li>
								<a href="javascript:void(0);"><img src="img/blank.gif" class="flag flag-ru" alt="Russia"> Русский язык</a>
							</li>
							<li>
								<a href="javascript:void(0);"><img src="img/blank.gif" class="flag flag-kp" alt="Korea"> 한국어</a>
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
							kenshin
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
				<!-- 
				NOTE: Notice the gaps after each icon usage <i></i>..
				Please note that these links work a bit different than
				traditional href="" links. See documentation for details.
				-->

				<ul>
					<li class="">
						<a href="portal/jsp/dashboard.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-tachometer"></i> <span class="menu-item-parent">个 人 面 板</span></a>
					</li>
					<li>
						<a href="portal/jsp/launchProcess.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-arrow-circle-right"></i> <span class="menu-item-parent">发 起 流 程</span></a>
					</li>
					<li>
						<a href="portal/jsp//todotasks.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-tasks"></i> <span class="menu-item-parent">待 办 任 务</span><span class="badge pull-right inbox-badge">14</span></a>
					</li>
					<li>
						<a href="portal/jsp//users.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-tasks"></i> <span class="menu-item-parent">用户列表</span><span class="badge pull-right inbox-badge">14</span></a>
					</li>
					<li>
						<a href="portal/jsp//calendarpanel.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-calendar"></i> <span class="menu-item-parent">日 历 面 板</span></a>
					</li>
					<li>
						<a href="ajax/inbox.html" title="Dashboard"><i class="fa fa-lg fa-fw fa-bullhorn"></i> <span class="menu-item-parent">通 知 中 心</span><span class="badge pull-right inbox-badge">5</span></a>
					</li>
					<li>
						<a href="portal/jsp/donetasks.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-check-square-o"></i> <span class="menu-item-parent">已 办 任 务</span></a>
					</li>
					<li>
						<a href="portal/jsp/runningtrackTasks.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-check-square"></i> <span class="menu-item-parent">流 程 追 踪</span></a>
					</li>
					<li>
						<a href="portal/jsp/profile.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-comments-o"></i> <span class="menu-item-parent">社 交 中 心</span><span class="badge pull-right inbox-badge">8</span></a>
					</li>
					<li>
						<a href="portal/jsp/dashboard.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-truck"></i> <span class="menu-item-parent">流 程 归 档</span><span class="badge bg-color-greenLight pull-right inbox-badge">9</span></a>
					</li>
					<li>
						<a href="portal/jsp/flot.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-bar-chart-o"></i> <span class="menu-item-parent">个 人 绩 效</span></a>
					</li>
					<li>
						<a href="#"><i class="fa fa-lg fa-fw fa-calendar"></i> <span class="menu-item-parent">工 作 日 历</span></a>
						<ul>
							<li>
								<a href="portal/jsp/calendartype.jsp"><i class="fa fa-lg fa-fw fa-calendartype"></i> <span class="menu-item-parent">日 历 类 型</span><span class="badge pull-right inbox-badge"></span></a>
							</li>
							<li>
								<a href="portal/jsp/calendarrule.jsp"><i class="fa fa-lg fa-fw fa-calendarrule"></i> <span class="menu-item-parent">日 历 规 则</span><span class="badge pull-right inbox-badge"></span></a>
							</li>
							<li>
								<a href="portal/jsp/calendarpart.jsp"><i class="fa fa-lg fa-fw fa-calendarpart"></i> <span class="menu-item-parent">日 历 时 间</span><span class="badge pull-right inbox-badge"></span></a>
							</li>
						</ul>
					</li>
					
					<li>
						<a href="#"><i class="fa fa-lg fa-fw fa-bar-chart-o"></i> <span class="menu-item-parent">Graphs</span></a>
						<ul>
							<li>
						<a href="ajax/inbox.html"><i class="fa fa-lg fa-fw fa-inbox"></i> <span class="menu-item-parent">Inbox</span><span class="badge pull-right inbox-badge">14</span></a>
					</li>
					<li>
						<a href="#"><i class="fa fa-lg fa-fw fa-bar-chart-o"></i> <span class="menu-item-parent">Graphs</span></a>
						<ul>
							<li>
								<a href="portal/jsp/flot.jsp">Flot Chart</a>
							</li>
							<li>
								<a href="portal/jsp/morris.jsp">Morris Charts</a>
							</li>
							<li>
								<a href="portal/jsp/inline-charts.jsp">Inline Charts</a>
							</li>
							<li>
								<a href="portal/jsp/dygraphs.jsp">Dygraphs <span class="badge pull-right inbox-badge bg-color-yellow">new</span></a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent">Tables</span></a>
						<ul>
							<li>
								<a href="portal/jsp/table.jsp">Normal Tables</a>
							</li>
							<li>
								<a href="portal/jsp/datatables.jsp">Data Tables <span class="badge inbox-badge bg-color-greenLight">v1.10</span></a>
							</li>
							<li>
								<a href="portal/jsp/jqgrid.jsp">Jquery Grid</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#"><i class="fa fa-lg fa-fw fa-pencil-square-o"></i> <span class="menu-item-parent">Forms</span></a>
						<ul>
							<li>
								<a href="portal/jsp/form-elements.jsp">Smart Form Elements</a>
							</li>
							<li>
								<a href="portal/jsp/form-templates.jsp">Smart Form Layouts</a>
							</li>
							<li>
								<a href="portal/jsp/validation.jsp">Smart Form Validation</a>
							</li>
							<li>
								<a href="portal/jsp/bootstrap-forms.jsp">Bootstrap Form Elements</a>
							</li>
							<li>
								<a href="portal/jsp/plugins.jsp">Form Plugins</a>
							</li>
							<li>
								<a href="portal/jsp/wizard.jsp">Wizards</a>
							</li>
							<li>
								<a href="portal/jsp/other-editors.jsp">Bootstrap Editors</a>
							</li>
							<li>
								<a href="portal/jsp/dropzone.jsp">Dropzone</a>
							</li>
							<li>
								<a href="portal/jsp/image-editor.jsp">Image Cropping <span class="badge pull-right inbox-badge bg-color-yellow">new</span></a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#"><i class="fa fa-lg fa-fw fa-desktop"></i> <span class="menu-item-parent">UI Elements</span></a>
						<ul>
							<li>
								<a href="portal/jsp/general-elements.jsp">General Elements</a>
							</li>
							<li>
								<a href="portal/jsp/buttons.jsp">Buttons</a>
							</li>
							<li>
								<a href="#">Icons</a>
								<ul>
									<li>
										<a href="portal/jsp/fa.jsp"><i class="fa fa-plane"></i> Font Awesome</a>
									</li>
									<li>
										<a href="portal/jsp/glyph.jsp"><i class="glyphicon glyphicon-plane"></i> Glyph Icons</a>
									</li>
									<li>
										<a href="portal/jsp/flags.jsp"><i class="fa fa-flag"></i> Flags</a>
									</li>
								</ul>
							</li>
							<li>
								<a href="portal/jsp/grid.jsp">Grid</a>
							</li>
							<li>
								<a href="portal/jsp/treeview.jsp">Tree View</a>
							</li>
							<li>
								<a href="portal/jsp/nestable-list.jsp">Nestable Lists</a>
							</li>
							<li>
								<a href="portal/jsp/jqui.jsp">JQuery UI</a>
							</li>
							<li>
								<a href="portal/jsp/typography.jsp">Typography</a>
							</li>
							<li>
								<a href="#">Six Level Menu</a>
								<ul>
									<li>
										<a href="#"><i class="fa fa-fw fa-folder-open"></i> Item #2</a>
										<ul>
											<li>
												<a href="#"><i class="fa fa-fw fa-folder-open"></i> Sub #2.1 </a>
												<ul>
													<li>
														<a href="#"><i class="fa fa-fw fa-file-text"></i> Item #2.1.1</a>
													</li>
													<li>
														<a href="#"><i class="fa fa-fw fa-plus"></i> Expand</a>
														<ul>
															<li>
																<a href="#"><i class="fa fa-fw fa-file-text"></i> File</a>
															</li>
															<li>
																<a href="#"><i class="fa fa-fw fa-trash-o"></i> Delete</a></li>
														</ul>
													</li>
												</ul>
											</li>
										</ul>
									</li>
									<li>
										<a href="#"><i class="fa fa-fw fa-folder-open"></i> Item #3</a>
		
										<ul>
											<li>
												<a href="#"><i class="fa fa-fw fa-folder-open"></i> 3ed Level </a>
												<ul>
													<li>
														<a href="#"><i class="fa fa-fw fa-file-text"></i> File</a>
													</li>
													<li>
														<a href="#"><i class="fa fa-fw fa-file-text"></i> File</a>
													</li>
												</ul>
											</li>
										</ul>
		
									</li>
								</ul>
							</li>
						</ul>
					</li>
					<li>
						<a href="portal/jsp/calendar.jsp"><i class="fa fa-lg fa-fw fa-calendar"><em>3</em></i> <span class="menu-item-parent">Calendar</span></a>
					</li>
					<li>
						<a href="portal/jsp/widgets.jsp"><i class="fa fa-lg fa-fw fa-list-alt"></i> <span class="menu-item-parent">Widgets</span></a>
					</li>
					<li>
						<a href="portal/jsp/launchProcess.jsp"><i class="fa fa-lg fa-fw fa-picture-o"></i> <span class="menu-item-parent">Gallery</span></a>
					</li>
					<li>
						<a href="portal/jsp/gmap-xml.jsp"><i class="fa fa-lg fa-fw fa-map-marker"></i> <span class="menu-item-parent">GMap Skins</span><span class="badge bg-color-greenLight pull-right inbox-badge">9</span></a>
					</li>
					<li>
						<a href="#"><i class="fa fa-lg fa-fw fa-windows"></i> <span class="menu-item-parent">Miscellaneous</span></a>
						<ul>
							<li>
								<a href="#"><i class="fa fa-file"></i> Other Pages</a>
								<ul>
									<li>
										<a href="portal/jsp/forum.jsp">Forum Layout</a>
									</li>
									<li>
										<a href="portal/jsp/profile.jsp">Profile</a>
									</li>
									<li>
										<a href="portal/jsp/timeline.jsp">Timeline</a>
									</li>
								</ul>	
							</li>
							<li>
								<a href="portal/jsp/pricing-table.jsp">Pricing Tables</a>
							</li>
							<li>
								<a href="portal/jsp/invoice.jsp">Invoice</a>
							</li>
							<li>
								<a href="login.html" target="_top">Login</a>
							</li>
							<li>
								<a href="register.html" target="_top">Register</a>
							</li>
							<li>
								<a href="lock.html" target="_top">Locked Screen</a>
							</li>
							<li>
								<a href="ajax/error404.html">Error 404</a>
							</li>
							<li>
								<a href="ajax/error500.html">Error 500</a>
							</li>
							<li>
								<a href="ajax/blank_.html">Blank Page</a>
							</li>
							<li>
								<a href="ajax/email-template.html">Email Template</a>
							</li>
							<li>
								<a href="ajax/search.html">Search Page</a>
							</li>
							<li>
								<a href="ajax/ckeditor.html">CK Editor</a>
							</li>
						</ul>
					</li>
						</ul>
					</li>
					
					
					<!-- <li class="top-menu-hidden">
						<a href="#"><i class="fa fa-lg fa-fw fa-cube txt-color-blue"></i> <span class="menu-item-parent">SmartAdmin Intel</span></a>
						<ul>
							<li>
								<a href="ajax/difver.html"><i class="fa fa-stack-overflow"></i> Different Versions</a>
							</li>
							<li>
								<a href="ajax/applayout.html"><i class="fa fa-cube"></i> App Settings</a>
							</li>
							<li>
								<a href="http://bootstraphunter.com/smartadmin/BUGTRACK/track_/documentation/index.html" target="_blank"><i class="fa fa-book"></i> Documentation</a>
							</li>
							<li>
								<a href="http://bootstraphunter.com/smartadmin/BUGTRACK/track_/" target="_blank"><i class="fa fa-bug"></i> Bug Tracker</a>
							</li>
						</ul>
					</li> -->
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
					<span id="refresh" class="btn btn-ribbon" data-action="resetWidgets" data-title="refresh" rel="tooltip" data-placement="bottom" data-original-title="<i class='text-warning fa fa-warning'></i> Warning! This will reset all your widget settings." data-html="true" data-reset-msg="Would you like to RESET all your saved widgets and clear LocalStorage?"><i class="fa fa-refresh"></i></span> 
				</span>

				<!-- breadcrumb -->
				<ol class="breadcrumb">
					<!-- This is auto generated -->
				</ol>
				<!-- end breadcrumb -->

				<!-- You can also add more buttons to the
				ribbon for further usability

				Example below:

				<span class="ribbon-button-alignment pull-right" style="margin-right:25px">
					<span id="search" class="btn btn-ribbon hidden-xs" data-title="search"><i class="fa fa-grid"></i> Change Grid</span>
					<span id="add" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa fa-plus"></i> Add</span>
					<span id="search" class="btn btn-ribbon" data-title="search"><i class="fa fa-search"></i> <span class="hidden-mobile">Search</span></span>
				</span> -->

			</div>
			<!-- END RIBBON -->
<div id="content">