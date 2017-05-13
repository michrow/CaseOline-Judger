<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:eval expression="@propertyConfigurer.getProperty('url.cdn')"
	var="cdnUrl" />
<!DOCTYPE html>
<html>
<head>
<!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/bootstrap-responsive.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/flat-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/accounts/user.css" />
    <!-- JavaScript -->
    <script type="text/javascript" src="${cdnUrl}/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/md5.min.js"></script>

</head>
<body>
	<!-- Header -->
	<%@ include file="/WEB-INF/views/include/header.jsp"%>
	<!-- Content -->
	<div id="content" class="container">
		<div class="row-fluid">
			<div id="sidebar" class="span3">
				<div id="vcard" class="section">
					<img src="${cdnUrl}/img/avatar.jpg" alt="User Avatar" />
					<h5>${user.userName}</h5>
				</div>
				<!-- #vcard -->
				<div id="vcard-details" class="section">
					<ul>
						<c:if test="${not empty location}">
							<li><span class="icon"><i class="fa fa-map-marker"></i></span>
								${location}</li>
						</c:if>
						<li><span class="icon"><i class="fa fa-envelope-o"></i></span>
							邮箱:${user.email}</li>

						<li><span class="icon"><i class="fa fa-users"></i></span>
							身份:${user.role.roleName}</li>
						<li><span class="icon"><i class="fa fa-clock-o"></i></span> 注册时间:
								
								<fmt:formatDate
											value="${user.registerTime}" type="both"
											dateStyle="default" timeStyle="default" /></li>
					</ul>
				</div>
				<!-- vcard-details -->
			</div>
			<!-- .span4 -->

			<!-- <div class="section" style="float: left">
			</div> -->
				<div class="header" style="float: left; margin-left:2%; width:70%">
					<h4>提交记录</h4>
					<table class="table table-striped">
						<thead>
							<tr>
								<th class="memory">覆盖率</th>
								<th class="name">案例名字</th>
							<!-- 	<th class="score">用户ID</th> -->
								<th class="language">语言</th>
								<th class="submit-time">提交时间</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="submission" items="${submissions}">
								<tr data-value="${submission.submissionId}">
									<td class="memory"><fmt:formatNumber type="number"
											maxFractionDigits="0" value="${submission.usecaseCoverage}" />%
									</td>
									<td class="name"><a
										href="<c:url value="/testcase/${submission.testcase.testcaseId}" />">P${submission.testcase.testcaseId}
											${submission.testcase.testcaseName}</a></td>
									<%-- <td class="user"><a
										href="<c:url value="/accounts/user/${submission.user.userId}" />">${submission.user.userName}</a></td> --%>
									<td class="language">${submission.language.languageName}</td>
									<td class="submit-time"><fmt:formatDate
											value="${submission.submissionSubmitTime}" type="both"
											dateStyle="default" timeStyle="default" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<!-- .header -->
				<div class="body">
					<c:choose>
						<c:when test="${submissions == null || submissions.size() == 0}">
							<p>
								<spring:message code="voj.accounts.user.no-submissions"
									text="No Submissions." />
							</p>
						</c:when>
						<c:otherwise>

						</c:otherwise>
					</c:choose>
				</div>
				<!-- .body -->
			
			<!-- .section -->
		</div>
		<!-- .span8 -->
	</div>
	<!-- .row-fluid -->
	</div>
	<!-- #content -->
	<!-- JavaScript -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
</body>
</html>