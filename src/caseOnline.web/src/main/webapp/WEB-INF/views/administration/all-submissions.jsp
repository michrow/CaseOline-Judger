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
                <h2 class="page-header"><i class="fa fa-code"></i> 提交</h2>
                <div class="alert alert-error hide"></div> <!-- .alert-error -->
                <div id="filters" class="row-fluid">
                    <div class="span6">
                        <select id="actions">
                            <option value="delete">删除</option>
                            <option value="restart">重置</option>
                        </select>
                        <button class="btn btn-danger">应用</button>
                    </div> <!-- .span6 -->
                    <div class="span6">
                        <form class="row-fluid text-right" action="<c:url value="/admin/all-submissions" />">
                            <div class="span5">
                                <div class="control-group">
                                    <input id="problem-id" name="testcaseId" class="span12" value="<c:if test="${testcaseId != 0}">${testcaseId}</c:if>" placeholder="案例ID" type="text" />
                                </div> <!-- .control-group -->
                            </div> <!-- .span5 -->
                            <div class="span5">
                                <div class="control-group">
                                    <input id="username" name="username" class="span12" placeholder="用户名" type="text" />
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
                            <th class="check-box">
                                <label class="checkbox" for="all-submissions">
                                    <input id="all-submissions" type="checkbox" data-toggle="checkbox">
                                </label>
                            </th>
                            <th class="memory">覆盖率</th>
							<th class="name">案例名字</th>
							<th class="score">用户ID</th>
							<th class="language">语言</th>
							<th class="submit-time">提交时间</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="submission" items="${submissions}">
                        <tr data-value="${submission.submissionId}">
                            <td class="check-box">
                                <label class="checkbox" for="submission-${submission.submissionId}">
                                    <input id="submission-${submission.submissionId}" type="checkbox" value="${submission.submissionId}" data-toggle="checkbox" />
                                </label>
                            </td>
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
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="pagination" class="pagination pagination-centered">
                    <c:set var="lowerBound" value="${currentPage - 5 > 0 ? currentPage - 5 : 1}" />
                    <c:set var="upperBound" value="${currentPage + 5 < totalPages ? currentPage + 5 : totalPages}" />
                    <c:set var="baseUrl" value="/admin/all-submissions?${requestScope['javax.servlet.forward.query_string']}" />
                    <ul>
                        <li class="previous <c:if test="${currentPage <= 1}">disabled</c:if>">
                        <a href="
                        <c:choose>
                            <c:when test="${currentPage <= 1}">javascript:void(0);</c:when>
                            <c:otherwise><c:url value="${baseUrl}&page=${currentPage - 1}" /></c:otherwise>
                        </c:choose>
                        ">&lt;</a>
                        </li>
                        <c:forEach begin="${lowerBound}" end="${upperBound}" var="pageNumber">
                        <li <c:if test="${pageNumber == currentPage}">class="active"</c:if>><a href="<c:url value="${baseUrl}&page=${pageNumber}" />">${pageNumber}</a></li>
                        </c:forEach>
                        <li class="next <c:if test="${currentPage >= totalPages}">disabled</c:if>">
                        <a href="
                        <c:choose>
                            <c:when test="${currentPage >= totalPages}">javascript:void(0);</c:when>
                            <c:otherwise><c:url value="${baseUrl}&page=${currentPage + 1}" /></c:otherwise>
                        </c:choose>
                        ">&gt;</a>
                        </li>
                    </ul>
                </div> <!-- #pagination-->
            </div> <!-- #content -->
        </div> <!-- #container -->
    </div> <!-- #wrapper -->
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <%@ include file="/WEB-INF/views/administration/include/footer-script.jsp" %>
    <script type="text/javascript">
        $('label[for=all-submissions]').click(function() {
            // Fix the bug for Checkbox in FlatUI 
            var isChecked = false;
            setTimeout(function() {
                isChecked = $('label[for=all-submissions]').hasClass('checked');
                
                if ( isChecked ) {
                    $('label.checkbox').addClass('checked');
                } else {
                    $('label.checkbox').removeClass('checked');
                }
            }, 100);
        });
    </script>
    <script type="text/javascript">
        $('button.btn-danger', '#filters').click(function() {
            if ( !confirm('<spring:message code="voj.administration.all-submissions.continue-or-not" text="Are you sure to continue?" />') ) {
                return;
            }
            $('.alert-error').addClass('hide');
            $('button.btn-danger', '#filters').attr('disabled', 'disabled');
            $('button.btn-danger', '#filters').html('<spring:message code="voj.administration.all-submissions.please-wait" text="Please wait..." />');

            var submissions = [],
                action      = $('#actions').val();

            $('label.checkbox', 'table tbody').each(function() {
                if ( $(this).hasClass('checked') ) {
                    var submissionId = $('input[type=checkbox]', $(this)).val();
                    submissions.push(submissionId);
                }
            });

            if ( action == 'delete' ) {
                return doDeleteSubmissionsAction(submissions);
            } else if ( action == 'restart' ) {
                return doRestartSubmissionsAction(submissions);
            }
        });
    </script>
    <script type="text/javascript">
        function doDeleteSubmissionsAction(submissions) {
            var postData = {
                'submissions': JSON.stringify(submissions)
            };

            $.ajax({
                type: 'POST',
                url: '<c:url value="/admin/deleteSubmissions.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    if ( result['isSuccessful'] ) {
                      
                        for ( var i = 0; i < submissions.length; ++ i ) {
                            $('tr[data-value=%s]'.format(submissions[i])).remove();
                        }
                    } else {
                        $('.alert').html('<spring:message code="voj.administration.all-submissions.delete-error" text="Some errors occurred while deleting submissions." />');
                        $('.alert').removeClass('hide');
                    }
                    $('button.btn-danger', '#filters').removeAttr('disabled');
                    $('button.btn-danger', '#filters').html('<spring:message code="voj.administration.all-submissions.apply" text="Apply" />');
                }
            });
        }
    </script>
    <script type="text/javascript">
        function doRestartSubmissionsAction(submissions) {
            var postData = {
                'submissions': JSON.stringify(submissions)
            };

            $.ajax({
                type: 'POST',
                url: '<c:url value="/administration/restartSubmissions.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    if ( result['isSuccessful'] ) {
                        for ( var i = 0; i < submissions.length; ++ i ) {
                            var submission  = $('tr[data-value=%s]'.format(submissions[i])),
                                judgeResult = $('td.flag', $(submission)); 

                            $(judgeResult).removeClass();
                            $(judgeResult).addClass('flag flag-PD');
                            $('a', $(judgeResult)).html('Pending');
                        }
                    } else {
                        $('.alert').html('<spring:message code="voj.administration.all-submissions.restart-error" text="Some errors occurred while restarting judging for submissions." />');
                        $('.alert').removeClass('hide');
                    }
                    $('button.btn-danger', '#filters').removeAttr('disabled');
                    $('button.btn-danger', '#filters').html('<spring:message code="voj.administration.all-submissions.apply" text="Apply" />');
                }
            });
        }
    </script>
</body>
</html>