<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:eval expression="@propertyConfigurer.getProperty('url.cdn')"
	var="cdnUrl" />
<!DOCTYPE html>
<html>
<head>
<!-- StyleSheets -->
<link rel="stylesheet" type="text/css"
	href="${cdnUrl}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${cdnUrl}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" type="text/css"
	href="${cdnUrl}/css/flat-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="${cdnUrl}/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="${cdnUrl}/css/submissions/submissions.css" />
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
		<div id="main-content" class="row-fluid">
			<div id="submission" class="span12">
				<table class="table table-striped">
					<thead>
						<tr>
							<th class="memory">覆盖率</th>
							<th class="name">案例名字</th>
							<th class="score">用户ID</th>
							<th class="language">语言</th>
							<th class="submit-time">提交时间</th>
							<th>详细信息</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="submission" items="${submissions}">
							<tr data-value="${submission.submissionId}">
								<td class="memory">
								  <fmt:formatNumber type="number"  maxFractionDigits="0" 
								  value="${submission.usecaseCoverage}" />%
								</td>
								<td class="name"><a
									href="<c:url value="/testcase/${submission.testcase.testcaseId}" />">P${submission.testcase.testcaseId} ${submission.testcase.testcaseName}</a></td>
								<td class="user"><a
									href="<c:url value="/accounts/user/${submission.user.userId}" />">${submission.user.userName}</a></td>
								<td class="language">${submission.language.languageName}</td>
								<td class="submit-time"><fmt:formatDate
										value="${submission.submissionSubmitTime}" type="both"
										dateStyle="default" timeStyle="default" /></td>
										<td><a href="<c:url value="/submission/${submission.submissionId}"/>">详情</a>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div id="more-submissions">
					<p class="availble">加载更多</p>
					<img src="${cdnUrl}/img/loading.gif" alt="Loading" class="hide" />
				</div>
			</div>
			<!-- #submission -->
		</div>
		<!-- #main-content -->
	</div>
	<!-- #content -->
	<!-- Java Script -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
	<script type="text/javascript" src="${cdnUrl}/js/date-zh_CN.min.js"></script>
	
	<script type="text/javascript">
		function setLoadingStatus(isLoading) {
			if (isLoading) {
				$('p', '#more-submissions').addClass('hide');
				$('img', '#more-submissions').removeClass('hide');
			} else {
				$('img', '#more-submissions').addClass('hide');
				$('p', '#more-submissions').removeClass('hide');
			}
		}
	</script>
	<script type="text/javascript">
		$('#more-submissions')
				.click(
						function(event) {
							var isLoading = $('img', this).is(':visible'), hasNextRecord = $(
									'p', this).hasClass('availble'), lastSubmissionRecord = $(
									'tr:last-child', '#submission tbody'), lastSubmissionId = parseInt($(
									lastSubmissionRecord).attr('data-value'));

							if (isNaN(lastSubmissionId)) {
								lastSubmissionId = 0;
							}
							if (!isLoading && hasNextRecord) {
								setLoadingStatus(true);
								return getMoreHistorySubmissions(lastSubmissionId - 1);
							}
						});
	</script>
	<script type="text/javascript">
		function getMoreHistorySubmissions(startIndex) {
			var pageRequests = {
				'problemId' : '${param.problemId}',
				'username' : '${param.username}',
				'startIndex' : startIndex
			};

			$.ajax({
				type : 'GET',
				url : '<c:url value="/submission/getSubmissions.action" />',
				data : pageRequests,
				dataType : 'JSON',
				success : function(result) {
					return processHistorySubmissionsResult(result);
				}
			});
		}
	</script>
	<script type="text/javascript">
		function processHistorySubmissionsResult(result) {
			if (result['isSuccessful']) {
				displayHistorySubmissionRecords(result['submissions']);
			} else {
				$('p', '#more-submissions').removeClass('availble');
				$('p', '#more-submissions').html('已加载完毕');
				$('#more-submissions').css('cursor', 'default');
			}
			setLoadingStatus(false);
		}
	</script>
	<script type="text/javascript">
		function displayHistorySubmissionRecords(submissions) {
			for (var i = 0; i < submissions.length; ++i) {
				$('table > tbody', '#submission').append(
						getSubmissionContent(submissions[i]['submissionId'],
								submissions[i]['usecaseCoverage'],
								submissions[i]['testcase'],
								submissions[i]['language'],
								submissions[i]['user'],
								submissions[i]['submissionSubmitTime']));
			}
		}
	</script>
	<script type="text/javascript">
		function getSubmissionContent(submissionId, usecaseCoverage,
				testcase, language, user, submissionSubmitTime) {
			var submissionTemplate = '<tr data-value="%s">'
					+ '    <td class="memory">%s% </td>'
					+ '    <td class="name"><a href="<c:url value="/testcase/%s" />">P%s%s</a></td>'
					+ '    <td class="user"><a href="<c:url value="/accounts/user/%s" />">%s</a></td>'
					+ '    <td class="language">%s</td>'
					+ '    <td class="submit-time">%s</td>'
					+ '	   <td><a href="<c:url value="/submission/%s"/>">详情</a>';

			return submissionTemplate.format(submissionId, usecaseCoverage,
					testcase['testcaseId'], testcase['testcaseId'], testcase['testcaseName'],user["userId"],user["userName"], language["languageName"],
					getFormatedDateString(submissionSubmitTime),submissionId);
		}
	</script>
	<script type="text/javascript">
		function getFormatedDateString(dateTime) {
			var dateObject = new Date(dateTime), dateString = dateObject
					.toString('yyyy-M-dd HH:mm:ss');
			return dateString;
		}
	</script>
	<c:if test="${GoogleAnalyticsCode != ''}">
    ${googleAnalyticsCode}
    </c:if>
</body>
</html>