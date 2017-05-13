      <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
        <div id="sidebar">
            <div id="logo">
                <a href="<c:url value="/" />">
                    <img src="${cdnUrl}/img/logo.png" alt="Logo" />
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
                        <a href="javascript:void(0);"><i class="fa fa-users"></i> 用户 <i class="fa fa-caret-right"></i></a>
                        <ul class="sub-nav nav">
                            <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/all-users" />">所有用户</a></li>
                            <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/new-user" />">创建用户</a></li>
                            <li class="nav-item secondary-nav-item hide"><a href="<c:url value="/admin/edit-user" />">编辑用户</a></li>
                        </ul>
                    </li>
                    <li class="nav-item primary-nav-item nav-item-has-children">
                        <a href="javascript:void(0);"><i class="fa fa-question-circle"></i> 测试案例 <i class="fa fa-caret-right"></i></a>
                        <ul class="sub-nav nav">
                            <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/all-testcases" />">所有案例</a></li>
                            <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/new-testcase" />">新增案例</a></li>
                            <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/testcase-categories" />">案例分类</a></li>
                            <li class="nav-item secondary-nav-item hide"><a href="<c:url value="/admin/edit-testcase" />">编辑分类</a></li>
                        </ul>
                    </li>
                 
                    <li class="nav-item primary-nav-item nav-item-has-children">
                        <a href="javascript:void(0);"><i class="fa fa-code"></i> 提交<i class="fa fa-caret-right"></i></a>
                        <ul class="sub-nav nav">
                            <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/all-submissions" />">所有提交</a></li>
                            <li class="nav-item secondary-nav-item hide"><a href="<c:url value="/admin/edit-submission" />">编辑提交</a></li>
                        </ul>
                    </li>
                    <li class="nav-item primary-nav-item nav-item-has-children">
                        <a href="javascript:void(0);"><i class="fa fa-cogs"></i> <spring:message code="voj.admin.include.sidebar.settings" text="Settings" /> <i class="fa fa-caret-right"></i></a>
                        <ul class="sub-nav nav">
                            <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/general-settings" />">常规设置</a></li>
                            <li class="nav-item secondary-nav-item"><a href="<c:url value="/admin/language-settings" />">语言设置</a></li>
                        </ul>
                    </li>
                </ul>
            </div> <!-- #sidebar-nav -->
        </div> <!-- #sidebar -->