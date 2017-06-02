<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <div id="sidebar">
      <div id="logo">
          <a href="<c:url value="/" />">
              <img src="${cdnUrl}/img/adminlogo.png" alt="AdminLogo" />
          </a>
      </div> <!-- #logo -->
      <div id="sidebar-user">
          <div class="row-fluid">
              <div class="span3">
                  <img src="${cdnUrl}/img/avatar.jpg" alt="avatar">
              </div> <!-- .span3 -->
              <div class="offset1 span8">
                  	欢迎 <br>${myProfile.userName} <br>
                  <span class="label label-success">在线</span>
              </div> <!-- .span8 -->
          </div> <!-- .row-fluid -->
      </div> <!-- #sidebar-user -->
      <div id="sidebar-nav">
          <ul class="nav">
              <li class="nav-item primary-nav-item">
                  <a href="<c:url value="/admin" />"><i class="fa fa-dashboard"></i> 预览</a>
              </li>
                <li class="nav-item primary-nav-item nav-item-has-children">
                  <a href="javascript:void(0);"><i class="fa fa-question-circle"></i> 测试案例 <i class="fa fa-caret-right"></i></a>
                  <ul class="sub-nav nav">
                      <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/testcase/all-testcases" />">所有案例</a></li>
                      <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/testcase/new-testcase" />">新增案例</a></li>
                       <c:if test="${myProfile.role.roleType == 'admin'}">       
                      <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/testcase/testcase-categories" />">案例分类</a></li>
                      </c:if>
                  </ul>
              </li>
              <c:if test="${myProfile.role.roleType == 'admin'}">         
              <li class="nav-item primary-nav-item nav-item-has-children">
                  <a href="javascript:void(0);"><i class="fa fa-users"></i> 用户 <i class="fa fa-caret-right"></i></a>
                  <ul class="sub-nav nav">
                      <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/user/all-users" />">所有用户</a></li>
                      <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/user/new-user" />">创建用户</a></li>
                      <li class="nav-item secondary-nav-item hide"><a href="<c:url value="/admin/user/edit-user" />">编辑用户</a></li>
                  </ul>
              </li>
               <li class="nav-item primary-nav-item nav-item-has-children">
                  <a href="javascript:void(0);"><i class="fa fa-cloud-upload"></i> 提交<i class="fa fa-caret-right"></i></a>
                  <ul class="sub-nav nav">
                      <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/submission/all-submissions" />">所有提交</a></li>
                      <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/submission/excellence-submissions" />">优秀提交</a></li>
                  </ul>
              </li>
              <li class="nav-item primary-nav-item nav-item-has-children">
                  <a href="javascript:void(0);"><i class="fa fa-cogs"></i> 设置 <i class="fa fa-caret-right"></i></a>
                  <ul class="sub-nav nav">
                      <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/general-settings" />">常规设置</a></li>
                      <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/language-settings" />">语言设置</a></li>
                       <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/judger" />">测评机</a></li>
                  </ul>
              </li>
              </c:if>
            
           
             
          </ul>
      </div> <!-- #sidebar-nav -->
  </div> <!-- #sidebar -->