<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('url.cdn')" var="cdnUrl" />
<!DOCTYPE html>
<html>
<head>
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/bootstrap-responsive.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/flat-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/administration/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/administration/all-submissions.css" />
    <!-- JavaScript -->
    <script type="text/javascript" src="${cdnUrl}/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/flat-ui.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/md5.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/pace.min.js"></script>
</head>
<body>
    <div id="wrapper">
        <!-- Sidebar -->
        <%@ include file="/WEB-INF/views/administration/include/sidebar.jsp" %>
        <div id="container">
            <!-- Header -->
            <%@ include file="/WEB-INF/views/administration/include/header.jsp" %>
            <!-- Content -->
            <div id="content">
                <h2 class="page-header"><i class="fa fa-cloud-upload"></i> 提交</h2>
                <div class="alert alert-error hide"></div> <!-- .alert-error -->
                <div id="filters" class="row-fluid">
                 
                    <div class="span6">
                        <form id="serch" class="row-fluid text-right" action="<c:url value="/admin/submission/excellence-submissions" />">
                            <div class="span5">
                                <div class="control-group">
                                    <input id="problem-id" name="testcaseId" class="span12" value="<c:if test="${testcaseId != 0}">${testcaseId}</c:if>" placeholder="案例ID" type="text" />
                                </div> <!-- .control-group -->
                            </div> <!-- .span5 -->
                            <div class="span5">
                                <div class="control-group">
                                    <input id="username" name="usecaseCoverage" class="span12" value="<c:if test="${usecaseCoverage != 0.0}">${usecaseCoverage}</c:if>"  placeholder="覆盖率大于" type="text" />
                                </div> <!-- .control-group -->
                            </div> <!-- .span5 -->
                            <div class="span2">
                                <button class="btn btn-primary">搜索</button>
                            </div> <!-- .span2 -->
                        </form> <!-- .row-fluid -->
                    </div> <!-- .span6 -->
                </div> <!-- .row-fluid -->
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th class="memory">覆盖率</th>
                             <th class="memory">用例个数</th>
							<th class="name">案例名字</th>
							<th class="language">语言</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="submission" items="${exceSubmissions}">
                        <tr data-value="${submission.submissionId}">

                            	<td class="memory">
								  <fmt:formatNumber type="number"  maxFractionDigits="0" 
								  value="${submission.usecaseCoverage}" />%
								</td>
								
								<td class="memory">
								  
								  ${submission.usecaseAmount}
								</td>
								
								<td class="name"><a
									href="<c:url value="/testcase/${submission.testcase.testcaseId}" />">P${submission.testcase.testcaseId} ${submission.testcase.testcaseName}</a></td>
							
								<td class="language">${submission.testcase.language.languageName}</td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
               
            </div> <!-- #content -->
        </div> <!-- #container -->
    </div> <!-- #wrapper -->
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <%@ include file="/WEB-INF/views/administration/include/footer-script.jsp" %>

</body>
</html>