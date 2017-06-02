<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:eval expression="@propertyConfigurer.getProperty('url.cdn')"
	var="cdnUrl" />
<div id="header" class="row-fluid">
	<div class="container">
		<div id="logo" class="span6">
			<a href="<c:url value="/" />"> <img src="${cdnUrl}/img/logo.png"
				alt="Logo" />
			</a>
		</div>
		<!-- #logo -->
		<div id="nav" class="span6">
			<ul class="inline">
				<li><a href="<c:url value="/testcase" />">问题</a></li>
				<li><a href="<c:url value="/submission" />">提交</a></li>
				<c:choose>
				<c:when test="${isLogin}">
					<li><a href="<c:url value="/accounts/login?logout=true"/>">注销</a></li>
				</c:when>
				<%-- <c:otherwise>
				<li><a href="<c:url value="/login"/>">登录</a></li>
				<li><a href="<c:url value="/register"/>">注册</a></li>
				</c:otherwise> --%>
				</c:choose>
				<c:if test="${isLogin}">
                        <li><a href="<c:url value="/accounts/dashboard"/>">设置</a></li>
                </c:if>
			</ul>
		</div>
		<!-- #nav -->
		<div class="span5">
				<c:if test="${isLogin}">
					<img src="${cdnUrl}/img/avatar.jpg" alt="avatar" class="img-circle" style="height: 30px"/>
					${myProfile.userName}
				</c:if>				
		</div>
	</div>
	<!-- .container -->
	
</div>