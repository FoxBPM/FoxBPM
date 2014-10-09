<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en-us">	
    <!-- 加载头部信箱 -->
    <head>
	 <jsp:include page="../header.jsp"/>
    </head>
	<body class="">
		<jsp:include page="../top.jsp"/>
		

<!-- Bread crumb is created dynamically -->
<!-- row -->
<div class="row">

	<!-- col -->
	<div class="col-xs-12 col-sm-7 col-md-7 col-lg-4">
		<h1 class="page-title txt-color-blueDark">
			<!-- PAGE HEADER -->
			<i class="fa-fw fa fa-file-o"></i> Other Pages <span>> Profile
			</span>
		</h1>
	</div>
	<!-- end col -->
</div>
<!-- end row -->

<!-- row -->

<div class="row">

	<div class="col-sm-12">


		<div class="well well-sm">

			<div class="row">

				<div class="col-sm-12 col-md-12 col-lg-6">
					<div class="well well-light well-sm no-margin no-padding">

						<div class="row">

							<div class="col-sm-12">
								<div id="myCarousel" class="carousel fade profile-carousel">
									<div class="air air-bottom-right padding-10">
										<a href="javascript:void(0);"
											class="btn txt-color-white bg-color-teal btn-sm"><i
											class="fa fa-check"></i> Follow</a>&nbsp; <a
											href="javascript:void(0);"
											class="btn txt-color-white bg-color-pinkDark btn-sm"><i
											class="fa fa-link"></i> Connect</a>
									</div>
									<div class="air air-top-left padding-10">
										<h4 class="txt-color-white font-md">Jan 1, 2014</h4>
									</div>
									<ol class="carousel-indicators">
										<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
										<li data-target="#myCarousel" data-slide-to="1" class=""></li>
										<li data-target="#myCarousel" data-slide-to="2" class=""></li>
									</ol>
									<div class="carousel-inner">
										<!-- Slide 1 -->
										<div class="item active">
											<img src="img/demo/s1.jpg" alt="">
										</div>
										<!-- Slide 2 -->
										<div class="item">
											<img src="img/demo/s2.jpg" alt="">
										</div>
										<!-- Slide 3 -->
										<div class="item">
											<img src="img/demo/m3.jpg" alt="">
										</div>
									</div>
								</div>
							</div>

							<div class="col-sm-12">
								<div class="row">
									<div class="col-sm-3 profile-pic" id="preview">
										<!-- <img width="100" height="100" src="img/avatars/sunny-big.png"
											id="userPicture" /> -->
										<img width="100" height="100"
											id="userPicture" /><br> <a href="javascript:void(0);"
											id="updatePicture" class="btn btn-xs" style="display: none"><small>更换图像</small>
										</a>
										<div class="padding-10">
											<h4 class="font-md">
												<strong>1,543</strong> <br> <small>Followers</small>
											</h4>
											<br>
											<h4 class="font-md">
												<strong>419</strong> <br> <small>Connections</small>
											</h4>
										</div>
									</div>
									<div class="col-sm-6">
										<h1>
											John <span class="semi-bold">Doe</span> <br> <small>
												CEO, SmartAdmin</small>
										</h1>

										<ul class="list-unstyled">
											<li>
												<p class="text-muted">
													<i class="fa fa-phone"></i>&nbsp;&nbsp; <span
														id="tel_label" class="txt-color-darken"></span><input
														id="tel" name="tel" class="input-control input-xs"
														type="text" style="display: none" />
												</p>
											</li>
											<li>
												<p class="text-muted">
													<i class="fa fa-envelope"></i>&nbsp;&nbsp;<a
														id="email_label" href="mailto:simmons@smartadmin"></a><input
														id="email" name="email" class="input-control input-xs"
														style="display: none" type="text" />
												</p>
											</li>
											<li>
												<p class="text-muted">
													<i class="icon-prepend fa fa-skype"></i>&nbsp;&nbsp; <span
														id="name_label" class="txt-color-darken"></span><input
														id="name" name="name" class="input-control input-xs"
														type="text" style="display: none;" />
												</p>
											</li>
											<li>
												<p class="text-muted">
													<i class="fa fa-calendar"></i>&nbsp;&nbsp;<span
														class="txt-color-darken">Free after <a
														href="javascript:void(0);" rel="tooltip" title=""
														data-placement="top"
														data-original-title="Create an Appointment">4:30 PM</a></span>
												</p>
											</li>
										</ul>
										<br>
										<p class="font-md">
											<i>A little about me...</i>
										</p>
										<!-- <textarea class="custom-scroll form-control input-xs" rows="4" cols="5">
										 Et harum quidem rerum facilis est et expedita
											distinctio. Nam libero tempore, cum soluta nobis est eligendi
											optio cumque nihil impedit quo minus id quod maxime placeat
											facere
										</textarea> -->
										<p>Et harum quidem rerum facilis est et expedita
											distinctio. Nam libero tempore, cum soluta nobis est eligendi
											optio cumque nihil impedit quo minus id quod maxime placeat
											facere</p>
										<br> <a href="javascript:void(0);"
											id="updatePersonalInfo" class="btn btn-default btn-xs"
											style="display: none;"><i class="fa fa-envelope-o"></i>
											Send Message</a> <a href="javascript:void(0);"
											id="editPersonalInfo" flag="0" class="btn btn-default btn-xs"
											style="display: none;">Edit</a> <br> <br>

									</div>
									<div class="col-sm-3">
										<h1>
											<small>Connections</small>
										</h1>
										<ul class="list-inline friends-list">
											<li><img src="img/avatars/1.png" alt="friend-1"></li>
											<li><img src="img/avatars/2.png" alt="friend-2"></li>
											<li><img src="img/avatars/3.png" alt="friend-3"></li>
											<li><img src="img/avatars/4.png" alt="friend-4"></li>
											<li><img src="img/avatars/5.png" alt="friend-5"></li>
											<li><img src="img/avatars/male.png" alt="friend-6">
											</li>
											<li><a href="javascript:void(0);">413 more</a></li>
										</ul>

										<h1>
											<small>Recent visitors</small>
										</h1>
										<ul class="list-inline friends-list">
											<li><img src="img/avatars/male.png" alt="friend-1">
											</li>
											<li><img src="img/avatars/female.png" alt="friend-2">
											</li>
											<li><img src="img/avatars/female.png" alt="friend-3">
											</li>
										</ul>

									</div>

								</div>

							</div>

						</div>
						<!-- 
						<div class="row"> 

							<div class="col-sm-12">

								<hr>

								<div class="padding-10">

									<ul class="nav nav-tabs tabs-pull-right">
										<li class="active"><a href="#a1" data-toggle="tab">Recent
												Articles</a></li>
										<li><a href="#a2" data-toggle="tab">New Members</a></li>
										<li class="pull-left"><span
											class="margin-top-10 display-inline"><i
												class="fa fa-rss text-success"></i> Activity</span></li>
									</ul>

									<div class="tab-content padding-top-10">
										<div class="tab-pane fade in active" id="a1">

											<div class="row">

												<div class="col-xs-2 col-sm-1">
													<time datetime="2014-09-20" class="icon">
														<strong>Jan</strong> <span>10</span>
													</time>
												</div>

												<div class="col-xs-10 col-sm-11">
													<h6 class="no-margin">
														<a href="javascript:void(0);">Allice in Wonderland</a>
													</h6>
													<p>Etiam ultricies nisi vel augue. Curabitur
														ullamcorper ultricies nisi Nam eget dui. Etiam rhoncus.
														Maecenas tempus, tellus eget condimentum rhoncus, sem quam
														semper libero, sit amet adipiscing sem neque sed ipsum.
														Nam quam nunc, blandit vel.</p>
												</div>

												<div class="col-sm-12">

													<hr>

												</div>

												<div class="col-xs-2 col-sm-1">
													<time datetime="2014-09-20" class="icon">
														<strong>Jan</strong> <span>10</span>
													</time>
												</div>

												<div class="col-xs-10 col-sm-11">
													<h6 class="no-margin">
														<a href="javascript:void(0);">World Report</a>
													</h6>
													<p>Morning our be dry. Life also third land after first
														beginning to evening cattle created let subdue you'll
														winged don't Face firmament. You winged you're was Fruit
														divided signs lights i living cattle yielding over light
														life life sea, so deep. Abundantly given years bring were
														after. Greater you're meat beast creeping behold he unto
														She'd doesn't. Replenish brought kind gathering Meat.</p>
												</div>

												<div class="col-sm-12">

													<br>

												</div>

											</div>

										</div>
										<div class="tab-pane fade" id="a2">

											<div class="alert alert-info fade in">
												<button class="close" data-dismiss="alert">×</button>
												<i class="fa-fw fa fa-info"></i> <strong>51 new
													members </strong>joined today!
											</div>

											<div class="user" title="email@company.com">
												<img src="img/avatars/female.png"><a
													href="javascript:void(0);">Jenn Wilson</a>
												<div class="email">travis@company.com</div>
											</div>
											<div class="user" title="email@company.com">
												<img src="img/avatars/male.png"><a
													href="javascript:void(0);">Marshall Hitt</a>
												<div class="email">marshall@company.com</div>
											</div>
											<div class="user" title="email@company.com">
												<img src="img/avatars/male.png"><a
													href="javascript:void(0);">Joe Cadena</a>
												<div class="email">joe@company.com</div>
											</div>
											<div class="user" title="email@company.com">
												<img src="img/avatars/male.png"><a
													href="javascript:void(0);">Mike McBride</a>
												<div class="email">mike@company.com</div>
											</div>
											<div class="user" title="email@company.com">
												<img src="img/avatars/male.png"><a
													href="javascript:void(0);">Travis Wilson</a>
												<div class="email">travis@company.com</div>
											</div>
											<div class="user" title="email@company.com">
												<img src="img/avatars/male.png"><a
													href="javascript:void(0);">Marshall Hitt</a>
												<div class="email">marshall@company.com</div>
											</div>
											<div class="user" title="Joe Cadena joe@company.com">
												<img src="img/avatars/male.png"><a
													href="javascript:void(0);">Joe Cadena</a>
												<div class="email">joe@company.com</div>
											</div>
											<div class="user" title="email@company.com">
												<img src="img/avatars/male.png"><a
													href="javascript:void(0);">Mike McBride</a>
												<div class="email">mike@company.com</div>
											</div>
											<div class="user" title="email@company.com">
												<img src="img/avatars/male.png"><a
													href="javascript:void(0);">Marshall Hitt</a>
												<div class="email">marshall@company.com</div>
											</div>
											<div class="user" title="email@company.com">
												<img src="img/avatars/male.png"><a
													href="javascript:void(0);">Joe Cadena</a>
												<div class="email">joe@company.com</div>
											</div>
											<div class="user" title="email@company.com">
												<img src="img/avatars/male.png"><a
													href="javascript:void(0);"> Mike McBride</a>
												<div class="email">mike@company.com</div>
											</div>

											<div class="text-center">
												<ul class="pagination pagination-sm">
													<li class="disabled"><a href="javascript:void(0);">Prev</a>
													</li>
													<li class="active"><a href="javascript:void(0);">1</a>
													</li>
													<li><a href="javascript:void(0);">2</a></li>
													<li><a href="javascript:void(0);">3</a></li>
													<li><a href="javascript:void(0);">...</a></li>
													<li><a href="javascript:void(0);">99</a></li>
													<li><a href="javascript:void(0);">Next</a></li>
												</ul>
											</div>

										</div>
										end tab
									</div>

								</div>

							</div>

						</div> -->
						<!-- end row -->

					</div>

				</div>
				<!-- 
				<div class="col-sm-12 col-md-12 col-lg-6">

					<form method="post" class="well padding-bottom-10"
						onsubmit="return false;">
						<textarea rows="2" class="form-control"
							placeholder="What are you thinking?"></textarea>
						<div class="margin-top-10">
							<button type="submit" class="btn btn-sm btn-primary pull-right">
								Post</button>
							<a href="javascript:void(0);"
								class="btn btn-link profile-link-btn" rel="tooltip"
								data-placement="bottom" title="Add Location"><i
								class="fa fa-location-arrow"></i></a> <a href="javascript:void(0);"
								class="btn btn-link profile-link-btn" rel="tooltip"
								data-placement="bottom" title="Add Voice"><i
								class="fa fa-microphone"></i></a> <a href="javascript:void(0);"
								class="btn btn-link profile-link-btn" rel="tooltip"
								data-placement="bottom" title="Add Photo"><i
								class="fa fa-camera"></i></a> <a href="javascript:void(0);"
								class="btn btn-link profile-link-btn" rel="tooltip"
								data-placement="bottom" title="Add File"><i
								class="fa fa-file"></i></a>
						</div>
					</form>

					<span class="timeline-seperator text-center"> <span>10:30PM
							January 1st, 2013</span>
						<div class="btn-group pull-right">
							<a href="javascript:void(0);" data-toggle="dropdown"
								class="btn btn-default btn-xs dropdown-toggle"><span
								class="caret single"></span></a>
							<ul class="dropdown-menu text-left">
								<li><a href="javascript:void(0);">Hide this post</a></li>
								<li><a href="javascript:void(0);">Hide future posts
										from this user</a></li>
								<li><a href="javascript:void(0);">Mark as spam</a></li>
							</ul>
						</div>
					</span>
					<div class="chat-body no-padding profile-message">
						<ul>
							<li class="message"><img src="img/avatars/sunny.png"
								class="online"> <span class="message-text"> <a
									href="javascript:void(0);" class="username">John Doe <small
										class="text-muted pull-right ultra-light"> 2 Minutes
											ago </small></a> Can't divide were divide fish forth fish to. Was can't
									form the, living life grass darkness very image let unto fowl
									isn't in blessed fill life yielding above all moved
							</span>
								<ul class="list-inline font-xs">
									<li><a href="javascript:void(0);" class="text-info"><i
											class="fa fa-reply"></i> Reply</a></li>
									<li><a href="javascript:void(0);" class="text-danger"><i
											class="fa fa-thumbs-up"></i> Like</a></li>
									<li><a href="javascript:void(0);" class="text-muted">Show
											All Comments (14)</a></li>
									<li><a href="javascript:void(0);" class="text-primary">Edit</a>
									</li>
									<li><a href="javascript:void(0);" class="text-danger">Delete</a>
									</li>
								</ul></li>
							<li class="message message-reply"><img
								src="img/avatars/3.png" class="online"> <span
								class="message-text"> <a href="javascript:void(0);"
									class="username">Serman Syla</a> Haha! Yeah I know what you
									mean. Thanks for the file Sadi! <i
									class="fa fa-smile-o txt-color-orange"></i>
							</span>

								<ul class="list-inline font-xs">
									<li><a href="javascript:void(0);" class="text-muted">1
											minute ago </a></li>
									<li><a href="javascript:void(0);" class="text-danger"><i
											class="fa fa-thumbs-up"></i> Like</a></li>
								</ul></li>
							<li class="message message-reply"><img
								src="img/avatars/4.png" class="online"> <span
								class="message-text"> <a href="javascript:void(0);"
									class="username">Sadi Orlaf </a> Haha! Yeah I know what you
									mean. Thanks for the file Sadi! <i
									class="fa fa-smile-o txt-color-orange"></i>
							</span>

								<ul class="list-inline font-xs">
									<li><a href="javascript:void(0);" class="text-muted">a
											moment ago </a></li>
									<li><a href="javascript:void(0);" class="text-danger"><i
											class="fa fa-thumbs-up"></i> Like</a></li>
								</ul> <input class="form-control input-xs"
								placeholder="Type and enter" type="text"></li>
						</ul>

					</div>

					<span class="timeline-seperator text-center"> <span>11:30PM
							November 27th, 2013</span>
						<div class="btn-group pull-right">
							<a href="javascript:void(0);" data-toggle="dropdown"
								class="btn btn-default btn-xs dropdown-toggle"><span
								class="caret single"></span></a>
							<ul class="dropdown-menu text-left">
								<li><a href="javascript:void(0);">Hide this post</a></li>
								<li><a href="javascript:void(0);">Hide future posts
										from this user</a></li>
								<li><a href="javascript:void(0);">Mark as spam</a></li>
							</ul>
						</div>
					</span>
					<div class="chat-body no-padding profile-message">
						<ul>
							<li class="message"><img src="img/avatars/1.png"
								class="online"> <span class="message-text"> <a
									href="javascript:void(0);" class="username">John Doe <small
										class="text-muted pull-right ultra-light"> 2 Minutes
											ago </small></a> Can't divide were divide fish forth fish to. Was can't
									form the, living life grass darkness very image let unto fowl
									isn't in blessed fill life yielding above all moved
							</span>
								<ul class="list-inline font-xs">
									<li><a href="javascript:void(0);" class="text-info"><i
											class="fa fa-reply"></i> Reply</a></li>
									<li><a href="javascript:void(0);" class="text-danger"><i
											class="fa fa-thumbs-up"></i> Like</a></li>
									<li><a href="javascript:void(0);" class="text-muted">Show
											All Comments (14)</a></li>
									<li><a href="javascript:void(0);" class="text-primary">Hide</a>
									</li>
								</ul></li>
							<li class="message message-reply"><img
								src="img/avatars/3.png" class="online"> <span
								class="message-text"> <a href="javascript:void(0);"
									class="username">Serman Syla</a> Haha! Yeah I know what you
									mean. Thanks for the file Sadi! <i
									class="fa fa-smile-o txt-color-orange"></i>
							</span>

								<ul class="list-inline font-xs">
									<li><a href="javascript:void(0);" class="text-muted">1
											minute ago </a></li>
									<li><a href="javascript:void(0);" class="text-danger"><i
											class="fa fa-thumbs-up"></i> Like</a></li>
								</ul></li>
							<li class="message message-reply"><img
								src="img/avatars/4.png" class="online"> <span
								class="message-text"> <a href="javascript:void(0);"
									class="username">Sadi Orlaf </a> Haha! Yeah I know what you
									mean. Thanks for the file Sadi! <i
									class="fa fa-smile-o txt-color-orange"></i>
							</span>

								<ul class="list-inline font-xs">
									<li><a href="javascript:void(0);" class="text-muted">a
											moment ago </a></li>
									<li><a href="javascript:void(0);" class="text-danger"><i
											class="fa fa-thumbs-up"></i> Like</a></li>
								</ul></li>
							<li class="message message-reply"><img
								src="img/avatars/4.png" class="online"> <span
								class="message-text"> <a href="javascript:void(0);"
									class="username">Sadi Orlaf </a> Haha! Yeah I know what you
									mean. Thanks for the file Sadi! <i
									class="fa fa-smile-o txt-color-orange"></i>
							</span>

								<ul class="list-inline font-xs">
									<li><a href="javascript:void(0);" class="text-muted">a
											moment ago </a></li>
									<li><a href="javascript:void(0);" class="text-danger"><i
											class="fa fa-thumbs-up"></i> Like</a></li>
								</ul></li>
							<li>
								<div class="input-group wall-comment-reply">
									<input id="btn-input" type="text" class="form-control"
										placeholder="Type your message here..."> <span
										class="input-group-btn">
										<button class="btn btn-primary" id="btn-chat">
											<i class="fa fa-reply"></i> Reply
										</button>
									</span>
								</div>
							</li>
						</ul>

					</div>


				</div> -->
			</div>

		</div>


	</div>
</div>

<!-- end row -->

<!-- end widget grid -->

<script type="text/javascript">
	/* DO NOT REMOVE : GLOBAL FUNCTIONS!
	 *
	 * pageSetUp(); WILL CALL THE FOLLOWING FUNCTIONS
	 *
	 * // activate tooltips
	 * $("[rel=tooltip]").tooltip();
	 *
	 * // activate popovers
	 * $("[rel=popover]").popover();
	 *
	 * // activate popovers with hover states
	 * $("[rel=popover-hover]").popover({ trigger: "hover" });
	 *
	 * // activate inline charts
	 * runAllCharts();
	 *
	 * // setup widgets
	 * setup_widgets_desktop();
	 *
	 * // run form elements
	 * runAllForms();
	 *
	 ********************************
	 *
	 * pageSetUp() is needed whenever you load a page.
	 * It initializes and checks for all basic elements of the page
	 * and makes rendering easier.
	 *
	 */
	 pageSetUp();
	 loadScript("portal/js/profile.js", function() {
		personInfo = new PersonInfo({
			userId : _userId
		});
		personInfo.init();
	}); 
</script>
		<jsp:include page="../bottom.jsp"/>
	</body>
</html>