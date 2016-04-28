<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%response.setStatus(200);%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>404 - 页面不存在</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <link rel="shortcut icon" type="image/x-icon" href="${ctx}/resources/styles/images/favicon.png" />
    <link href="${ctx}/resources/ace/assets/css/bootstrap.css" rel="stylesheet" />
    <link rel="stylesheet" href="${ctx}/resources/ace/assets/css/font-awesome.css" />
    <link rel="stylesheet" href="${ctx}/resources/ace/assets/css/ace-fonts.css" />
    <link rel="stylesheet" href="${ctx}/resources/ace/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
</head>

<body>
<div class="main-content">
	<div class="main-content-inner">
		<div class="page-content">
			<!-- #section:settings.box -->
			</div><!-- /.ace-settings-container -->

			<!-- /section:settings.box -->
			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->

					<!-- #section:pages/error -->
					<div class="error-container">
						<div class="well">
							<h1 class="grey lighter smaller">
											<span class="blue bigger-125">
												<i class="ace-icon fa fa-sitemap"></i>
												404
											</span>
								很抱歉：您访问的页面不存在或已删除
							</h1>

							<hr />
							<h3 class="lighter smaller">We looked everywhere but we couldn't find it!</h3>

							<div>
								<form class="form-search">
												<span class="input-icon align-middle">
													<i class="ace-icon fa fa-search"></i>

													<input type="text" class="search-query" placeholder="Give it a search..." />
												</span>
									<button class="btn btn-sm" type="button">Go!</button>
								</form>

								<div class="space"></div>
								<h4 class="smaller">Try one of the following:</h4>

								<ul class="list-unstyled spaced inline bigger-110 margin-15">
									<li>
										<i class="ace-icon fa fa-hand-o-right blue"></i>
										Re-check the url for typos
									</li>

									<li>
										<i class="ace-icon fa fa-hand-o-right blue"></i>
										Read the faq
									</li>

									<li>
										<i class="ace-icon fa fa-hand-o-right blue"></i>
										Tell us about it
									</li>
								</ul>
							</div>

							<hr />
							<div class="space"></div>

							<div class="center">
								<a href="javascript:history.back()" class="btn btn-grey">
									<i class="ace-icon fa fa-arrow-left"></i>
									返回
								</a>

								<a href="<c:url value="/"/>" class="btn btn-primary">
									<i class="ace-icon fa fa-tachometer"></i>
									进入首页
								</a>
							</div>
						</div>
					</div>

					<!-- /section:pages/error -->

					<!-- PAGE CONTENT ENDS -->
				</div><!-- /.col -->
			</div><!-- /.row -->
		</div><!-- /.page-content -->
	</div>
</div><!-- /.main-content -->
</body>
</html>