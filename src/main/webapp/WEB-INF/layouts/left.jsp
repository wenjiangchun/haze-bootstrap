<%@ page language="java" pageEncoding="UTF-8" %>
<div id="sidebar" class="sidebar responsive">
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
	</script>

	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
		<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
			<button class="btn btn-success">
				<i class="ace-icon fa fa-signal"></i>
			</button>

			<button class="btn btn-info">
				<i class="ace-icon fa fa-pencil"></i>
			</button>

			<!-- #section:basics/sidebar.layout.shortcuts -->
			<button class="btn btn-warning">
				<i class="ace-icon fa fa-users"></i>
			</button>

			<button class="btn btn-danger">
				<i class="ace-icon fa fa-cogs"></i>
			</button>

			<!-- /section:basics/sidebar.layout.shortcuts -->
		</div>

		<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
			<span class="btn btn-success"></span>

			<span class="btn btn-info"></span>

			<span class="btn btn-warning"></span>

			<span class="btn btn-danger"></span>
		</div>
	</div><!-- /.sidebar-shortcuts -->

	<ul class="nav nav-list">
		<li class="active">
			<a href="${ctx}">
				<i class="menu-icon fa fa-tachometer"></i>
				<span class="menu-text"> 主页 </span>
			</a>

			<b class="arrow"></b>
		</li>

		<li class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-desktop"></i>
							<span class="menu-text">
								系统管理
							</span>

				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li class="">
					<a href="${ctx}/system/user/view" id="viewUser_Menu">
						<i class="menu-icon fa fa-caret-right"></i>
						<i class="fa fa-user"></i>
						用户管理
					</a>
					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="${ctx}/system/role/view" id="viewRole_Menu">
						<i class="menu-icon fa fa-caret-right"></i>
						<i class="fa fa-tag"></i>
						角色管理
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="${ctx}/system/group/view" id="viewGroup_Menu">
						<i class="menu-icon fa fa-caret-right"></i>
						<i class="fa fa-group"></i>
						机构管理
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="${ctx}/system/dictionary/view" id="viewDictionary_Menu">
						<i class="menu-icon fa fa-caret-right"></i>
						<i class="fa fa-book"></i>
						字典管理
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="${ctx}/system/resource/view" id="viewResource_Menu">
						<i class="menu-icon fa fa-caret-right"></i>
						<i class="fa fa-database"></i>
						资源管理
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="${ctx}/system/config/view" id="viewConfig_Menu">
						<i class="menu-icon fa fa-caret-right"></i>
						<i class="fa fa-cog"></i>
						配置管理
					</a>

					<b class="arrow"></b>
				</li><li class="">
					<a href="${ctx}/system/support/view" id="viewSupport_Menu">
						<i class="menu-icon fa fa-caret-right"></i>
						<i class="fa fa-wrench"></i>
						系统辅助
					</a>

					<b class="arrow"></b>
				</li>
			</ul>
		</li>

		<li class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-exchange"></i>
				<span class="menu-text"> 流程管理 </span>

				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li class="">
					<a href="${ctx}/workflow/processDefinition/view/" id="viewProcessDefinition_Menu">
						<i class="menu-icon fa fa-caret-right"></i>
						流程定义
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="${ctx}/workflow/processInstance/view/" id="viewProcessInstance_Menu">
						<i class="menu-icon fa fa-caret-right"></i>
						流程实例
					</a>

					<b class="arrow"></b>
				</li>
			</ul>
		</li>

		<li class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-exchange"></i>
				<span class="menu-text"> 任务管理 </span>
				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li class="">
					<a href="${ctx}/quartz/jobDetail/view/" id="viewJobDetail_Menu">
						<i class="menu-icon fa fa-caret-right"></i>
						作业管理
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="${ctx}/schedule/trigger/view/" id="viewTrigger_Menu">
						<i class="menu-icon fa fa-caret-right"></i>
						触发器管理
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
		</li>


		<li class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon"></i>
				<span class="menu-text"> Demo演示 </span>

				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li class="">
					<a href="${ctx}/demo/matrix/view/" id="viewBarcode_Menu">
						<i class="menu-icon fa fa-caret-right"></i>
						<i class="fa fa-barcode"></i>
						二维码演示
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
		</li>
	</ul><!-- /.nav-list -->

	<!-- #section:basics/sidebar.layout.minimize -->
	<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
		<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
	</div>

	<!-- /section:basics/sidebar.layout.minimize -->
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
	</script>
	<script type="text/javascript">
		/**
		 * 菜单初始化
         * @param menuId 菜单ID
		 */
		function initMenu(menuId) {
			var menu = $("#"+menuId);
			menu.parent().parent().parent().parent().find("li").removeClass("active");
			menu.parent().addClass("active");
			menu.parent().parent().css({"display":"block"});
			menu.parent().parent().parent().addClass("open");
		}
	</script>
</div>