<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<link rel="stylesheet" type="text/css"
	href="${cdnUrl}/css/administration/style.css" />
<link rel="stylesheet" type="text/css"
	href="${cdnUrl}/css/administration/new-problem.css" />
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
		<%@ include file="/WEB-INF/views/administration/include/sidebar.jsp"%>
		<div id="container">
			<!-- Header -->
			<%@ include file="/WEB-INF/views/administration/include/header.jsp"%>
			<!-- Content -->
			<div id="content">
				<h2 class="page-header">
					<i class="fa fa-file"></i> 编辑案例
				</h2>
				<form id="problem-form" onSubmit="onSubmit(); return false;">
					<div class="row-fluid">
						<div class="span8">
							<div class="alert alert-error hide"></div>
							<!-- .alert-error -->
							<div class="alert alert-success hide">
								更新成功</a>
							</div>
							<!-- .alert-success -->
							<div class="control-group row-fluid">
								<label for="problem-name">案例名字</label> 
								<input id="problem-name" class="span12" type="text" maxlength="128"
									value="${testcase.testcaseName}" />
									 
								<input id="problem-id"
									type="hidden" value="${testcase.testcaseId}" />
							</div>
							<!-- .control-group -->
							<div class="control-group row-fluid">
								<label for="output-sample">案例</label>
								<textarea id="output-sample" class="span12">${testcase.testcaseCode}</textarea>
							</div>
							<!-- .control-group -->
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
							<!-- .row-fluid -->

							<div class="row-fluid">
								<div class="span12">
									<label for="wmd-input">描述</label>
									<textarea id="wmd-input" class="wmd-input">${testcase.testcaseDescription}</textarea>
									<%-- <div id="markdown-editor">
										<div class="wmd-panel">
											<div id="wmd-button-bar" class="wmd-button-bar"></div>
											<!-- #wmd-button-bar -->
											<textarea id="wmd-input" class="wmd-input">${testcase.testcaseDescription}</textarea>
										</div>
									</div> --%>
									<!-- #markdown-editor -->
								</div>
								<!-- .span12 -->
							</div>
							<!-- .row-fluid -->
							<div class="footer">
								<button class="btn btn-primary" type="submit">
									更新
								</button>
							</div>
							<!-- .footer -->
						</div>
						<!-- .span8 -->

					</div>
					<!-- .row-fluid -->
				</form>
			</div>
			<!-- #content -->
		</div>
		<!-- #container -->
	</div>
	<!-- #wrapper -->
	<!-- Java Script -->
	<!-- Placed at the end of the document so the pages load faster -->
	<%@ include
		file="/WEB-INF/views/administration/include/footer-script.jsp"%>

	<script type="text/javascript">
		function onSubmit() {
			var testcaseId = $('#problem-id').val(), 
				testcaseName = $('#problem-name').val(), 
				testcaseCode = $('#output-sample').val(),
				isPublic = $('#problem-is-public').is(':checked'), 
				testcaseDesc = $('#wmd-input').val();

			$('.alert-success', '#problem-form').addClass('hide');
			$('.alert-error', '#problem-form').addClass('hide');
			$('button[type=submit]', '#problem-form').attr('disabled',
					'disabled');
			$('button[type=submit]', '#problem-form')
					.html(
							'等待');

			return editProblem(testcaseId,testcaseName,testcaseCode,isPublic,testcaseDesc);
		}
	</script>
	
	<script type="text/javascript">
		function editProblem(testcaseId,testcaseName,testcaseCode,isPublic,testcaseDesc) {
			var postData = {
				'testcaseId' : testcaseId,
				'testcaseName' : testcaseName,
				'testcaseCode' : testcaseCode,
				'isPublic' : isPublic,
				'testcaseDesc' : testcaseDesc
			};

			$.ajax({
				type : 'POST',
				url : '<c:url value="/admin/testcase/editTestcase.action" />',
				data : postData,
				dataType : 'JSON',
				success : function(result) {
					return processEditProblemResult(result);
				}
			});
		}
	</script>
	<script type="text/javascript">
		function processEditProblemResult(result) {
			if (result['isSuccessful']) {
				$('.alert-error').addClass('hide');
				$('.alert-success').removeClass('hide');
			} else {
				var errorMessage = '更新出错';
				$('.alert-error', '#problem-form').html(errorMessage);
				$('.alert-error', '#problem-form').removeClass('hide');
			}
			$('button[type=submit]', '#problem-form').removeAttr('disabled');
			$('button[type=submit]', '#problem-form')
					.html(
							'更新');
		}
	</script>
</body>
</html>