<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>

</head>
<body>
<div class="breadcrumbs" id="breadcrumbs">

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="#">主页</a>
							</li>
							<li class="active">控制台</li>
						</ul><!-- .breadcrumb -->
					</div>
    <div class="page-content">
						<div class="page-header">
							<h1>
								控制台
								<small>
									<i class="icon-double-angle-right"></i>
									 查看待办事项
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="alert alert-block alert-success">
									<button type="button" class="close" data-dismiss="alert">
										<i class="icon-remove"></i>
									</button>

									<i class="icon-ok green"></i>

									欢迎使用
									<strong class="green">
										Haze
										<small></small>
									</strong>
								</div>

								<div class="hr hr4 hr-dotted"></div>
<div class="row">
									<div class="col-sm-6">
										<div class="widget-box transparent" id="recent-box">
											<div class="widget-header">
												<h4 class="lighter smaller">
													<i class="icon-star orange"></i>
													待办事项
												</h4>

												<div class="widget-toolbar no-border">
													<ul class="nav nav-tabs" id="recent-tab">
														<li class="active">
															<a data-toggle="tab" href="#task-tab">待办1</a>
														</li>

														<li>
															<a data-toggle="tab" href="#member-tab">待办2</a>
														</li>

													</ul>
												</div>
											</div>

											<div class="widget-body">
												<div class="widget-main padding-4">
													<div class="tab-content padding-8 overflow-visible">
														<div id="task-tab" class="tab-pane active">
															<ul id="tasks" class="item-list">
															   <c:if test="${undoDispatchList.size() == 0}">
															      <li class="item-green clearfix">
																	<label class="inline">
																		<span class="lbl">暂无待办发文信息！</span>
																	</label>
																</li>
															   </c:if>
                                                               <c:forEach items="${undoDispatchList}" var="dispatch">
                                                                    <li class="item-red clearfix">
																	<label class="inline">
																		<span class="lbl"> ${dispatch.title}</span>
																	</label>
																	<div class="pull-right action-buttons">
																		<a href="${ctx}/oa/document/dispatch/viewTask/${dispatch.task.id}/${dispatch.id}" class="blue">
																			<i class="icon-pencil bigger-130"></i>办理
																		</a>
																		<span class="vbar"></span>
																	</div>
																</li>
                                                               </c:forEach>
															</ul>
														</div>

														<div id="leave-tab" class="tab-pane">
															<ul id="tasks" class="item-list">
															   <c:if test="${undoLeaveList.size() == 0}">
															      <li class="item-green clearfix">
																	<label class="inline">
																		<span class="lbl">暂无待办请假信息！</span>
																	</label>
																</li>
															   </c:if>
                                                               <c:forEach items="${undoLeaveList}" var="leave">
                                                                    <li class="item-red clearfix">
																	<label class="inline">
																		<span class="lbl">申请人：${leave.applyUser.name}</span>
																	</label>
																	<div class="pull-right action-buttons">
																		<a href="${ctx}/oa/leave/viewTask/${leave.task.id}/${leave.id}" class="blue">
																			<i class="icon-pencil bigger-130"></i>办理
																		</a>
																		<span class="vbar"></span>
																	</div>
																</li>
                                                               </c:forEach>
															</ul>
														</div><!-- member-tab -->
														<div id="member-tab" class="tab-pane">
															<ul id="tasks" class="item-list">
																<c:if test="${undoReceiveList.size() == 0}">
																	<li class="item-green clearfix">
																		<label class="inline">
																			<span class="lbl">暂无待办收文信息！</span>
																		</label>
																	</li>
																</c:if>
																<c:forEach items="${undoReceiveList}" var="dispatch">
																	<li class="item-red clearfix">
																		<label class="inline">
																			<span class="lbl"> ${dispatch.recTitle}</span>
																		</label>
																		<div class="pull-right action-buttons">
																			<a href="${ctx}/oa/document/receive/viewTask/${dispatch.task.id}/${dispatch.id}" class="blue">
																				<i class="icon-pencil bigger-130"></i>办理
																			</a>
																			<span class="vbar"></span>
																		</div>
																	</li>
																</c:forEach>
															</ul>
														</div><!-- member-tab -->
													</div>
												</div><!-- /widget-main -->
											</div><!-- /widget-body -->
										</div><!-- /widget-box -->
                                        	<div class="hr hr4 hr-dotted"></div>
									</div><!-- /span -->

								</div><!-- /row -->
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
</body>
</html>