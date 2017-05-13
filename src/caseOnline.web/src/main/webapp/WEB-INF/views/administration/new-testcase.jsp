<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/administration/new-problem.css" />
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
                <h2 class="page-header"><i class="fa fa-file"></i> 新增案例</h2>
                <form id="problem-form" onSubmit="onSubmit(); return false;">
                    <div class="row-fluid">
                        <div class="span8">
                            <div class="alert alert-error hide"></div> <!-- .alert-error -->
                            <div class="alert alert-success hide">创建成功</div>
                            <div class="control-group row-fluid">
                                <label for="problem-name">案例名称</label>
                                <input id="problem-name" class="span12" type="text" maxlength="128" />
                            </div> <!-- .control-group -->
                           <div class="control-group row-fluid">
								<label for="output-sample">案例代码</label>
								<textarea id="output-sample" class="span12"></textarea>
							</div>
							<div class="row-fluid">
								<div class="span8">
									是否公开
								</div>
								<!--- .span8 -->
								<div class="span4 text-right">
									<input id="problem-is-public" type="checkbox"
										<c:if test="${testcase.isPublic()}">checked="checked"</c:if> />
								</div>
								<!-- .span4 -->
							</div>
							<div class="control-group row-fluid">
						<label for="user-group">案例类型</label> 
						<select id="category" name="category">
							<option value=''>案例类型</option>
							<c:forEach var="cate" items="${categories}">
								<option value="${cate.categoryId}">${cate.categoryName}</option>
							</c:forEach>
						</select>
						</div>
								<div class="control-group row-fluid">
						<label for="user-group">语言类型</label> 
						<select id="language" name="language">
								<option value=''>语言类型</option>
							<c:forEach var="language" items="${languages}">
								<option value="${language.languageId}">${language.languageName}</option>
							</c:forEach>
						</select>
						</div>
							
                            <div class="row-fluid">
                                <div class="span12">
                                    <label for="wmd-input">案例描述</label>    
                                     <textarea id="wmd-input" class="wmd-input"></textarea>
                                </div> <!-- .span12 -->
                            </div> <!-- .row-fluid -->
                            <div class="footer">
								<button class="btn btn-primary" type="submit">
									添加
								</button>
							</div>
                    </div> <!-- .row-fluid -->
                   
                </form>
            </div> <!-- #content -->
        </div> <!-- #container -->
    </div> <!-- #wrapper -->
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <%@ include file="/WEB-INF/views/administration/include/footer-script.jsp" %>

    <script type="text/javascript">
        function onSubmit() {
            var testcaseName         = $('#problem-name').val(),
                testcaseCode        = $('#output-sample').val(),
                isPublic            = $('#problem-is-public').is(':checked'), 
                categoryId      = $('#category').val(),
                languageId      = $('#language').val(),
                testcaseDesc=$('#wmd-input').val();
                

            $('.alert-success', '#problem-form').addClass('hide');
            $('.alert-error', '#problem-form').addClass('hide');
            $('button[type=submit]', '#problem-form').attr('disabled', 'disabled');
            $('button[type=submit]', '#problem-form').html('<spring:message code="voj.administration.new-problem.please-wait" text="Please wait..." />');

            return createProblem(testcaseName,testcaseDesc,testcaseCode,categoryId,isPublic,languageId);
        }
    </script>
    
    <script type="text/javascript">
        function createProblem(testcaseName,testcaseDesc,testcaseCode,categoryId,isPublic,languageId) {
            var postData = {
                'testcaseName': testcaseName,
                'testcaseDescription': testcaseDesc,
                'testcaseCode': testcaseCode,
                'categoryId': categoryId,
                'isPublic': isPublic,
                'languageId': languageId
            };
            $.ajax({
                type: 'POST',
                url: '<c:url value="/admin/createTestcase.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    return processCreateProblemResult(result);
                }
            });
        }
    </script>
    <script type="text/javascript">
        function processCreateProblemResult(result) {
            if ( result['isSuccessful'] ) {
            	  $('input').val('');
            	  $('textarea').val('')
                  $('.alert-success', '#problem-form').removeClass('hide');
            } else {
                var errorMessage  = '出错';
                
                $('.alert-error', '#problem-form').html(errorMessage);
                $('.alert-error', '#problem-form').removeClass('hide');
            }
            $('button[type=submit]', '#problem-form').removeAttr('disabled');
            $('button[type=submit]', '#problem-form').html('添加');
        }
    </script>
</body>
</html>