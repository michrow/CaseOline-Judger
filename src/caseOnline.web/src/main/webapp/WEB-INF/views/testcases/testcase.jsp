<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/codemirror.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/problems/problem.css" />
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
                <div class="problem">
                    <div class="header">
                  <%--   <c:if test="${isLogin}">
                        <c:if test="${latestSubmission[problem.problemId] != null}">
                            <span class="pull-right">${latestSubmission[problem.problemId].judgeResult.judgeResultName}</span>
                        </c:if>
                    </c:if> --%>
                    <span class="name">P${testcase.testcaseId} ${testcase.testcaseName}</span>
                    <input id="languageId"
									type="hidden" value="${testcase.language.languageId}" />
                     <input id="testcaseId"
									type="hidden" value="${testcase.testcaseId}" />
                    </div> <!-- .header -->
                    <div class="body">
                        <div class="section">
                            <h4>案例描述</h4>
                            <div class="description markdown">${testcase.testcaseDescription}</div> <!-- .description -->
                        </div> <!-- .section -->
                        <div class="section">
                            <h4>格式</h4>
                            <h5>输入</h5>
                     		<div class="description">${testcase.language.format.formatText}</div>  <!-- .description -->
                          
                        </div> <!-- .section -->
                <%--         <div id="io-sample" class="section">
                            <h4>样例</h4>
                            <h5>输入</h5>
                            <div class="description"><pre>${problem.sampleInput}</pre></div> <!-- .description -->                         
                        </div> <!-- .section --> --%>
                       
                        <c:if test="${testcase.testcaseCode != null and testcase.testcaseCode != ''}">
                        <div class="section">
                            <h4>测试案例</h4>
                            <div class="description markdown">${testcase.testcaseCode.replace("<", "&lt;").replace(">", "&gt;")}</div> <!-- .description -->
                        </div> <!-- .section -->
                        </c:if>
                        <form id="code-editor" onsubmit="onSubmit(); return false;" method="POST">
                            <textarea name="codemirror-editor" id="codemirror-editor"></textarea>
                            <div class="row-fluid"> 
                                <div id="submission-error" class="offset1 span3"></div> <!-- #submission-error -->
                                <div id="submission-action" class="span4">
                                    <button type="submit" class="btn btn-primary">提交</button>
                                    <button id="close-submission" class="btn">取消</button>
                                </div> <!-- #submission-action -->
                            </div> <!-- .row-fluid -->
                        </form> <!-- #code-editor-->
                        <div id="mask" class="hide"></div> <!-- #mask -->
                    </div> <!-- .body -->
                </div> <!-- .problem -->
            </div> <!-- #main-content -->
            <div id="sidebar" class="span3">
                <div id="actions" class="section">
                    <h5>操作</h5>
                    <ul>
                    <c:if test="${isLogin}">
                        <li><a id="submit-solution" href="javascript:void(0);">提交</a></li>
                    </c:if>
                        <li><a href="<c:url value="/submission?problemId=${problem.problemId}" />">提交记录</a></li>
                    </ul>
                </div> <!-- #actions -->
                <c:if test="${isLogin}">
                <div id="submission" class="section">
                    <h5>我的提交</h5>
                    <c:if test="${submissions == null || submissions.size() == 0}">
                        <p>暂无提交</p>
                    </c:if>
                    <ul>
                    <c:forEach var="submission" items="${submissions}">
                        <li class="row-fluid">
                            <div class="span4">
                                <a href="<c:url value="/submission/${submission.submissionId}" />">
                                    <fmt:formatDate value="${submission.submitTime}" type="date" dateStyle="short" timeStyle="short"/>
                                </a>
                            </div> <!-- .span4 -->
                            <div class="span8 flag-${submission.judgeResult.judgeResultSlug}">
                                ${submission.judgeResult.judgeResultName}
                            </div> <!-- .span8 -->
                        </li>
                    </c:forEach>
                    </ul>
                </div> <!-- submission -->
                </c:if>
               
            </div> <!-- #sidebar -->
        </div> <!-- .row-fluid -->
    </div> <!-- #content -->

    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
    <script type="text/x-mathjax-config">
        MathJax.Hub.Config({
            tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]}
        });
    </script>
    <script type="text/javascript" async src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
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
    <script type="text/javascript">
        $.getScript('${cdnUrl}/js/codemirror.min.js', function() {
           $.when(
                $.getScript('${cdnUrl}/mode/clike.min.js'),
                $.getScript('${cdnUrl}/mode/go.min.js'),
                $.getScript('${cdnUrl}/mode/pascal.min.js'),
                $.getScript('${cdnUrl}/mode/perl.min.js'),
                $.getScript('${cdnUrl}/mode/php.min.js'),
                $.getScript('${cdnUrl}/mode/python.min.js'),
                $.getScript('${cdnUrl}/mode/ruby.min.js'),
                $.Deferred(function(deferred) {
                    $(deferred.resolve);
                })
            ).done(function() {
                window.codeMirrorEditor = CodeMirror.fromTextArea(document.getElementById('codemirror-editor'), {
                    mode: $('select#languages').val(),
                    tabMode: 'indent',
                    theme: 'neat',
                    tabSize: 4,
                    indentUnit: 4,
                    lineNumbers: true,
                    lineWrapping: true
                });
            }); 
        });
    </script>
    <script type="text/javascript">
        $.getScript('${cdnUrl}/js/highlight.min.js', function() {
            $('code').each(function(i, block) {
                hljs.highlightBlock(block);
            });
        });
    </script>
    <script type="text/javascript">
        $('select#languages').change(function() {
            window.codeMirrorEditor.setOption('mode', $(this).val());
        });
    </script>
    <script type="text/javascript">
        $('#submit-solution').click(function() {
            $('#mask').removeClass('hide');
            $('#code-editor').addClass('fade');
        });
    </script>
    <script type="text/javascript">
        $('#close-submission').click(function(e) {
        	e.preventDefault();
            
            $('#code-editor').removeClass('fade');
            $('#mask').addClass('hide');
        });
    </script>
    <script type="text/javascript">
        function onSubmit() {
            var testcaseId   = $('#testcaseId').val(),
                languageId    = $('#languageId').val(),
                usecase        = window.codeMirrorEditor.getValue();
            $('button[type=submit]', '#code-editor').attr('disabled', 'disabled');
            $('button[type=submit]', '#code-editor').html('等待');

            return createSubmissionAction(testcaseId, languageId, usecase);
        }
    </script>
    <script type="text/javascript">
        function createSubmissionAction(testcaseId, languageId, usecase) {
            var postData = {
                'testcaseId': testcaseId,
                'languageId': languageId,
                'usecase': usecase
            };

            $.ajax({
                type: 'POST',
                url: '<c:url value="/testcase/createSubmission.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    if ( result['isSuccessful'] ) {
                        var submissionId = result['submissionId'];
                        window.location.href = '<c:url value="/submission/" />' + submissionId;
                    } else {
                        var errorMessage = '';

                        if ( !result['isCsrfTokenValid'] ) {
                            errorMessage = '<spring:message code="voj.problems.problem.invalid-token" text="Invalid token." />';
                        } else if ( !result['isUserLogined'] ) {
                            errorMessage = '<spring:message code="voj.problems.problem.user-not-login" text="Please sign in first." />';
                        } else if ( !result['isProblemExists'] ) {
                            errorMessage = '<spring:message code="voj.problems.problem.problem-not-exists" text="The problem not exists." />';
                        } else if ( !result['isLanguageExists'] ) {
                            errorMessage = '<spring:message code="voj.problems.problem.language-not-exists" text="The language not exists." />';
                        } else if ( result['isCodeEmpty'] ) {
                            errorMessage = '<spring:message code="voj.problems.problem.empty-code" text="Please enter the code." />';
                        }
                        $('#submission-error').html(errorMessage);
                    }

                    $('button[type=submit]', '#code-editor').removeAttr('disabled');
                    $('button[type=submit]', '#code-editor').html('<spring:message code="voj.problems.problem.submit" text="Submit" />');
                }
            });
        }
    </script>
    <script type="text/x-mathjax-config">
        MathJax.Hub.Config({
            tex2jax: {
                inlineMath: [
                    ['$','$'], 
                    ['\\(','\\)']
                ]
            }
        });
    </script>
    <script type="text/javascript" src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
  
</body>
