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
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/submissions/submission.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/highlight.min.css" />
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
        <div class="row-fluid">
            <div id="main-content" class="span9">
                <div class="submission">
                    <div class="header">
                        <span class="pull-right"></span>
                        <span class="name">P${submission.testcase.testcaseId} ${submission.testcase.testcaseName}</span>
                    </div> <!-- .header -->
                    <div class="body">
                        <div class="section">
                            <h4>概况</h4>
                            <div class="description">
                                <table class="table">
                                    <tr>
                                        <td>测评结果</td>
                                        <td id="judge-result" class="flag-${submission.judgeResult.resultName}">${submission.judgeResult.resultName}</td>
                                    </tr>
                                    <tr>
                                        <td>测试案例</td>
                                        <td id="testcase"><a href="<c:url value="/testcase/${submission.testcase.testcaseId}" />">P${submission.testcase.testcaseId} ${submission.testcase.testcaseName}</a></td>
                                    </tr>
                                    <tr>
                                        <td>提交时间</td>
                                        <td id="submit-time"><fmt:formatDate value="${submission.submissionSubmitTime}" type="both" dateStyle="default" timeStyle="default"/></td>
                                    </tr>
                                    <tr>
                                        <td>语言</td>
                                        <td id="language-name">${submission.language.languageName}</td>
                                    </tr>
                                    
                                    <tr>
                                        <td>覆盖率</td>
                                        <td id="coverage">  <fmt:formatNumber type="number"  maxFractionDigits="0" 
								  value="${submission.usecaseCoverage}" />%</td>
                                    </tr>
                                    
                                    <tr>
                                        <td>测评机</td>
                                        <td id="judger-name">${submission.judgerName}</td>
                                    </tr>
                                    
                                </table>
                            </div> <!-- .description -->
                        </div> <!-- .section -->
                         <div class="section">
                            <h4>测评日志</h4>
                            <div id="judge-log" class="description markdown">${submission.judgeLog}</div> <!-- .description -->
                        </div> <!-- .section -->
                        <c:if test="${submission.user.userId == myProfile.userId or myProfile.role.roleType == 'admin' or myProfile.role.roleType=='teacher'}">                        
                        <div class="section">
                            <h4>提交用例</h4>
                            <div class="description">
                                <pre><code>${submission.usecase}</code></pre>
                            </div> <!-- .description -->
                        </div> <!-- .section -->
                        </c:if>
                    </div> <!-- .body -->
                </div> <!-- .submission -->
            </div> <!-- #main-content -->
            <div id="sidebar" class="span3">
                <div id="submit-user" class="section">
                    <h5>用户</h5>
                    <img src="${cdnUrl}/img/avatar.jpg" alt="User Avatar" class="img-circle" />
                    <p>
                       提交 <a href="<c:url value="/accounts/user/${submission.user.userId}" />">${submission.user.userName}</a>
                    </p>
                </div> <!-- #profile -->
                <div id="problem" class="section">
                    <h5>操作</h5>
                    <ul>
                        <li><a href="<c:url value="/testcase/${submission.testcase.testcaseId}" />">问题</a></li>
                        <li><a href="<c:url value="/submission?pid=${submission.testcase.testcaseId}" />">提交</a></li>
                    </ul>
                </div> <!-- problem -->
            </div> <!-- #sidebar -->
        </div> <!-- .row-fluid -->
    </div> <!-- #content -->
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
    <script type="text/javascript">
        $.getScript('${cdnUrl}/js/markdown.min.js', function() {
            converter = Markdown.getSanitizingConverter();

            $('.markdown').each(function() {
                var plainContent    = $(this).text(),
                    markdownContent = converter.makeHtml(plainContent.replace(/\\\n/g, '\\n'));
                
                $(this).html(markdownContent);
            });
        });
    </script>
 <c:if test="${submission.judgeResult.resultName == 'Pending'}">
   <script type="text/javascript">
        $.getScript('${cdnUrl}/js/date-zh_CN.min.js', function() {
            	var currentJudgeResult = 'Pending',
                getterInterval     = setInterval(function() {               	
                    getRealTimeJudgeResult();
                    currentJudgeResult = $('#judge-result').html();

                    if ( currentJudgeResult != 'Pending' ) {
                       
                    	clearInterval(getterInterval);
                    } 
                }, 1000);
        });
    </script>
    <script type="text/javascript">
         $(function() {
            var subscriptionUrl = '<c:url value="/submission/getRealTimeJudgeResult.action?submissionId=${submission.submissionId}" />',
                source          = new EventSource(subscriptionUrl),
                lastMessage     = '';

            source.onmessage    = function(e) {
                var message     = e['data'];

                if ( message == lastMessage ) {
                    return;
                }
                lastMessage     = message;

                if ( message == 'Established' ) {
                    $('#judge-log').append('<p>Connected to Server.</p>');
                    return;
                }
                var mapMessage  = JSON.parse(message),
                    coverage = mapMessage['coverage'],
                    judgeLog    = mapMessage['message'],
                	judgeResult=mapMessage['judgeResult'];
                    
                $('#judge-result').html(judgeResult);
                $('#coverage').html(coverage+"%");
                $('#judge-log').append(converter.makeHtml(judgeLog));
            }
        }); 
    </script>
    <script type="text/javascript">
        function getRealTimeJudgeResult() {
              var pageRequests = {
                'submissionId': ${submission.submissionId}
            };
             
            $.ajax({
                type: 'GET',
                url: '<c:url value="/submission/getSubmission.action" />',
                data: pageRequests,
                dataType: 'JSON',
                success: function(result){
                    if ( result['isSuccessful'] ) {
                        if ( result['submission']['judgeResult']['resultFlag'] != 'PD' ) {
                           /*  $('#judge-result').removeClass();
                            $('#judge-result').addClass("flag-" + result['submission']['judgeResult']['resultName']);
                            $('#judge-result').html(result['submission']['judgeResult']['resultName']);
                            $('#coverage').html(result['submission']['usecaseCoverage'] + " %"); */
                            $('#judger-name').html(result['submission']['judgerName']);
                            $('#judge-log').html(converter.makeHtml(result['submission']['judgeLog'].replace(/\\\n/g, '\\n')));
                        }
                    }
                }
            }); 
        }
    </script>
    <script type="text/javascript">
        function getFormatedDateString(dateTime, locale) {
            var dateObject = new Date(dateTime),
                dateString = dateObject.toString();

            if ( locale == 'en_US' ) {
                dateString = dateObject.toString('MMM d, yyyy h:mm:ss tt');
            } else if ( locale == 'zh_CN' ) {
                dateString = dateObject.toString('yyyy-M-dd HH:mm:ss');
            }

            return dateString;
        }
    </script>
</c:if>
    <script type="text/javascript">
        $.getScript('${cdnUrl}/js/highlight.min.js', function() {
            $('pre code').each(function(i, block) {
                hljs.highlightBlock(block);
            });
        });
    </script>
    <script type="text/javascript">
         $(function() {
            var imageObject       = $('img', '#submit-user'),
                hashCode          = md5('${submission.user.email}'),
                gravatarSeriveUrl = 'https://secure.gravatar.com/';
                
            $.ajax({
                type: 'GET',
                url: gravatarSeriveUrl + hashCode + '.json',
                dataType: 'jsonp',
                success: function(result){
                    if ( result != null ) {
                        var imageUrl    = result['entry'][0]['thumbnailUrl'],
                            requrestUrl = imageUrl + '?s=200';
                        $(imageObject).attr('src', requrestUrl);
                    }
                }
            });
        }); 
    </script>
   
</body>
</html>