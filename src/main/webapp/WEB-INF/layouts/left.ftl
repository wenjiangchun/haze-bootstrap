<%@ page language="java" pageEncoding="UTF-8" %>
<div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">
            <li class="active"><a href="#">主页</a></li>
          <#list menuResources as menu>
              <li><a href="${ctx}/${menu.url}">${menu.name}</a></li>
          </#list>
          </ul>
</div>
        
        

