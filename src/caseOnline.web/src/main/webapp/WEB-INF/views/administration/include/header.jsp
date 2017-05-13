           <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
            <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
            <div id="header">
                <a id="sidebar-toggle" href="javascript:void(0);"><i class="fa fa-bars"></i></a>
                <ul class="nav inline">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" role="button" href="javascript:void(0);">
                            ${myProfile.userName} <img src="${cdnUrl}/img/avatar.jpg" alt="avatar">
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li class="divider"></li>
                            <li><a href="<c:url value="/accounts/login?logout=true" />"><i class="fa fa-sign-out"></i> 注销</a></li>
                        </ul>
                    </li>
                </ul>
            </div> <!-- #header -->