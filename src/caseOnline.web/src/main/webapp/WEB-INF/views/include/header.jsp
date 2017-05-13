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
<!-- #header -->
<div id="drawer-nav">
	<span class="close-trigger"><a
		href="javascript:closeDrawerMenu();"><spring:message
				code="voj.include.header.close" text="Close" /> &times;</a></span>
	<div id="accounts" class="section">
		<h4>
			<spring:message code="voj.include.header.my-accounts"
				text="My Accounts" />
		</h4>
		<div id="profile">
			<c:choose>
				<c:when test="${isLogin}">
					<img src="${cdnUrl}/img/avatar.jpg" alt="avatar" class="img-circle" />
					<h5>${myProfile.userName}</h5>
					<p class="email">${myProfile.email}</p>
					<p>
						<spring:message code="voj.include.header.accepted" text="Accepted" />
						/
						<spring:message code="voj.include.header.submit" text="Submit" />
						:
						${mySubmissionStats.get("acceptedSubmission")}/${mySubmissionStats.get("totalSubmission")}(${mySubmissionStats.get("acRate")}%)
					</p>
					
					<ul class="inline">
						<li><a href="<c:url value="/accounts/dashboard" />"><spring:message
									code="voj.include.header.dashboard" text="Dashboard" /></a></li>
						<li><a href="<c:url value="/accounts/login?logout=true" />"><spring:message
									code="voj.include.header.sign-out" text="Sign out" /></a></li>
					</ul>
				</c:when>
				<c:otherwise>
					<p>
						<spring:message code="voj.include.header.not-logged-in"
							text="You are not logged in." />
					</p>
					<ul class="inline">
						<li><a
							href="<c:url value="/accounts/login?forward=" />${requestScope['javax.servlet.forward.request_uri']}"><spring:message
									code="voj.include.header.sign-in" text="Sign in" /></a></li>
						<li><a
							href="<c:url value="/accounts/register?forward=" />${requestScope['javax.servlet.forward.request_uri']}"><spring:message
									code="voj.include.header.sign-up" text="Sign up" /></a></li>
					</ul>
				</c:otherwise>
			</c:choose>
		</div>
		<!-- #profile -->
	</div>
	<!-- .section -->
	<div id="about" class="section">
		<h4>
			<spring:message code="voj.include.header.about" text="About" />
		</h4>
		<ul>
			<li><a href="<c:url value="/judgers" />"><spring:message
						code="voj.include.header.judgers" text="Judgers" /></a></li>
			<li><a href="https://github.com/zjhzxhz/voj/issues"
				target="_blank"><spring:message
						code="voj.include.header.feedback" text="Feedback" /></a></li>
			<li><a href="<c:url value="/about" />"><spring:message
						code="voj.include.header.about-us" text="About us" /></a></li>
		</ul>
	</div>
	<!-- .section -->
</div>
<!-- #drawer-nav -->