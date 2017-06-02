<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<spring:eval expression="@propertyConfigurer.getProperty('url.cdn')" var="cdnUrl" />
<!DOCTYPE html>
<html>
<head>
 
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/bootstrap-responsive.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/flat-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/problems/problems.css" />
    <!-- JavaScript -->
    <script type="text/javascript" src="${cdnUrl}/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/md5.min.js"></script>
</head>
<body>
    <!-- Header -->
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <!-- Content -->
    <div id="content" class="container">
        <%-- <div id="locator">
            <ul class="inline">
                <li>快速定位:</li>
                <c:forEach var="locatorID" begin="${startIndexOfTestcase}" end="${startIndexOfTestcase + totalTestcases-1}" step="${numberOfTestcasesPerPage}">
                <li><a href="<c:url value="/testcase?start=${locatorID}" />">T${locatorID}</a></li>
                </c:forEach>
            </ul>
        </div> <!-- #locator --> --%>
        <div id="main-content" class="row-fluid">
            <div id="problems" class="span8">
                <table class="table table-striped">
                    <thead>
                        <tr>
                        <%-- <c:if test="${isLogin}">
                            <th class="flag"></th>
                        </c:if> --%>
                            <th class="name">案例名</th>
                            <th class="submission">总提交提交</th>
                            <th>提交人次</th>
                            <th class="ac-rate">最高覆盖率</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="testcase" items="${testcases}">
                        <tr data-value="${testcase.testcaseId}">
                    <%--     <c:if test="${isLogin}">
                            <c:choose>
                                <c:when test="${submissionOfTestcases[testcase.testcaseId] == null}"><td></td></c:when>
                                <c:otherwise>
                                    <td class="flag-${submissionOfProblems[problem.problemId].judgeResult.judgeResultSlug}">
                                        <a href="<c:url value="/submission/${submissionOfTestcases[testcase.testcaseId].submissionId}" />">
                                            ${submissionOfProblems[problem.problemId].judgeResult.judgeResultSlug}
                                        </a>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                        </c:if> --%>
                            <td class="name"><a href="<c:url value="/testcase/${testcase.testcaseId}" />">P${testcase.testcaseId} ${testcase.testcaseName}</a></td>
                            <td>${testcase.totalSubmission}</td>
                            <td>${testcase.totalUserSubmission}</td>
                            <td>
                            <c:choose>
                                <c:when test="${testcase.totalSubmission == 0}">0%</c:when>
                                <c:otherwise>
                                    <fmt:formatNumber type="number"  maxFractionDigits="0" value="${testcase.maxCoverage}" />%
                                </c:otherwise>
                            </c:choose>
                            </td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="more-problems">
                    <p class="availble">More Testcase...</p>
                    <img src="${cdnUrl}/img/loading.gif" alt="Loading" class="hide" />
                </div>
            </div> <!-- #problems -->
            <div id="sidebar" class="span4">
                <div id="search-widget" class="widget">
                    <h4>搜索</h4>
                    <form id="search-form" action="<c:url value="/testcase" />">
                        <div class="control-group">
                            <input id="keyword" name="keyword" class="span12" type="text" placeholder="关键字" value="${keyword}" />
                        </div>
                        <button class="btn btn-primary btn-block" type="submit">搜索</button>
                    </form> <!-- #search-form -->
                </div> <!-- #search-widget -->
                <div id="categories-widget" class="widget">
                    <h4>分类</h4>
                   <c:forEach var="entry" items="${testcaseCategories}">
                        <h6>
                            <a 
                                <c:if test="${selectedCategoryType == entry.categoryType}">class="active" </c:if>
                                href="<c:url value="/testcase?category=${entry.categoryType}" />">
                                ${entry.categoryName}
                            </a>
                        </h6>
                    </c:forEach> 
                </div> <!-- #categories-widget -->
            </div> <!-- #sidebar -->
        </div> <!-- #main-content -->
    </div> <!-- #content -->
 
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
    <script type="text/javascript">
        $(function() {
            var numberOfProblems = $('tr', '#problems tbody').length;

            if ( numberOfProblems == 0 ) {
                return processProblemsResult(false);
            } 
        });
    </script>
    <script type="text/javascript">
        function setLoadingStatus(isLoading) {
            if ( isLoading ) {
                $('p', '#more-problems').addClass('hide');
                $('img', '#more-problems').removeClass('hide');
            } else {
                $('img', '#more-problems').addClass('hide');
                $('p', '#more-problems').removeClass('hide');
            }
        }
    </script>
    <script type="text/javascript">
        $('#more-problems').click(function(event) {
            var isLoading         = $('img', this).is(':visible'),
                hasNextRecord     = $('p', this).hasClass('availble'),
                lastProblemRecord = $('tr:last-child', '#problems tbody'),
                lastProblemId     = parseInt($(lastProblemRecord).attr('data-value'));

            if ( !isLoading && hasNextRecord ) {
                setLoadingStatus(true);
                return getMoreProblems(lastProblemId + 1);
            }
        });
    </script>
    <script type="text/javascript">
        function getMoreProblems(startIndex) {
            var pageRequests = {
                'startIndex': startIndex,
                'keyword': '${keyword}',
                'category': '${selectedCategoryType}'
            };

            $.ajax({
                type: 'GET',
                url: '<c:url value="/testcase/getTestcase.action" />',
                data: pageRequests,
                dataType: 'JSON',
                success: function(result){
                    return processProblemsResult(result);
                }
            });
        }
    </script>
    <script type="text/javascript">
        function processProblemsResult(result) {
            if ( result['isSuccessful'] ) {
                displayProblemsRecords(result['testcases']);
            } else {
                $('p', '#more-problems').removeClass('availble');
                $('p', '#more-problems').html('已加载完毕');
                $('#more-problems').css('cursor', 'default');
            }
            setLoadingStatus(false);
        }
    </script>
    <script type="text/javascript">
        function displayProblemsRecords(testcases) {
            for ( var i = 0; i < testcases.length; ++ i ) {
                $('table > tbody', '#problems').append(
                    getProblemContent(testcases[i]['testcaseId'], testcases[i]['testcaseName'], 
                    		testcases[i]['totalSubmission'], testcases[i]['totalUserSubmission'],testcases[i]['maxCoverage'] )
                );
            }
        }
    </script>
    <script type="text/javascript">
        function getProblemContent(testcaseId, testcaseName, totalSubmission, totalUserSubmission,maxCoverage) {
        	if (totalSubmission==0){
        		maxCoverage=0;
        	}
            var TestcaseTemplate = '<tr data-value="%s">' +
                                  '    <td class="name"><a href="<c:url value="/testcase/%s" />">P%s %s</a></td>' +
                                  '    <td>%s</td>' +
                                  '    <td>%s</td>' +
                                  '    <td>%s%</td>' +
                                  '</tr>';

            return TestcaseTemplate.format(testcaseId,testcaseId,testcaseId, testcaseName, totalSubmission, totalUserSubmission,maxCoverage);
        }
    </script>
    <script type="text/javascript">
        function getSubmissionOfProblemHtml(problemId, submissionOfProblem) {
            if ( typeof(submissionOfProblem) == 'undefined' ) {
                return '<td></td>';
            }

            var submissionId        = submissionOfProblem['submissionId'],
                judgeResultSlug     = submissionOfProblem['judgeResult']['judgeResultSlug'],
                submissionTemplate  = '<td class="flag-%s">' +
                                      '    <a href="<c:url value="/submission/%s" />">%s</a>' +
                                      '</td>';

            return submissionTemplate.format(judgeResultSlug, submissionId, judgeResultSlug);
        }
    </script>
    <script type="text/javascript">
        function getAcRate(acceptedSubmission, totalSubmission) {
            if ( totalSubmission == 0 ) {
                return 0;
            }
            return Math.round(acceptedSubmission / totalSubmission) * 100;
        }
    </script>
</body>
</html>