<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" type="text/css"
	href="${cdnUrl}/css/administration/style.css" />
<link rel="stylesheet" type="text/css"
	href="${cdnUrl}/css/administration/language-settings.css" />
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
					<i class="fa fa-cc"></i> 语言设置
				</h2>
				<form id="language-form" onSubmit="onSubmit(); return false;">

					<div class="alert alert-error hide"></div>
					<!-- .alert-error -->
					<div class="alert alert-success hide">保存成功</div>
					<!-- .alert-success -->
					<p>
						<a id="new-language" href="javascript:void(0);"> <i
							class="fa fa-plus-circle"></i> 新增
						</a>
					</p>
					<p id="no-languages"
						class="<c:if test="${languages.size() != 0}">hide</c:if>">暂无语言</p>
					<ul id="languages">
						<c:forEach items="${languages}" var="language">
							<li class="language">
								<div class="header">
									<h5>${language.languageName}</h5>
									<ul class="inline">
										<li><a href="javascript:void(0);"><i
												class="fa fa-edit"></i></a></li>
										<li><a href="javascript:void(0);"><i
												class="fa fa-trash"></i></a></li>
									</ul>
								</div> <!-- .header -->
								<div class="body hide">

									<div class="row-fluid">
										<div class="span4">
											<label>语言名字</label>
										</div>
										<!-- .span4 -->
										<div class="span8">
											<div class="control-group">
												<input class="language-id span8" type="hidden"
													value="${language.languageId}" /> <input
													class="language-name span8" type="text"
													value="${language.languageName}" maxlength="16" />
											</div>
											<!-- .control-group -->
										</div>
										<!-- .span8 -->
									</div>
									<!-- .row-fluid -->
									<div class="row-fluid">
										<div class="span4">
											<label>执行构建文件</label>
										</div>
										<!-- .span4 -->
										<div class="span8">
											<div class="control-group">
												<input class="compile-command span8" type="text"
													value="${language.buildFile}" maxlength="128" />
											</div>
											<!-- .control-group -->
										</div>
										<!-- .span8 -->
									</div>
									<!-- .row-fluid -->
									<div class="row-fluid" id="formatdiv">
										<label for="user-group">格式类型</label> <select id="format"
											name="category">
											<option value=''>格式类型</option>
											<c:forEach var="format" items="${formats}">
												<option value="${format.formatId}"
													<c:if test="${format.formatId == language.format.formatId}">selected</c:if>>${format.formatText}</option>
											</c:forEach>
										</select>
									</div>
								</div> <!-- .body -->
							</li>
						</c:forEach>
					</ul>
					<div class="row-fluid">
						<button class="btn btn-primary" type="submit">提交</button>
					</div>
					<!-- .row-fluid -->
				</form>
				<!-- #language-form -->
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
		$('#languages').on(
				'click',
				'i.fa-edit',
				function() {
					var languageContainer = $(this).parent().parent().parent()
							.parent().parent(), isBodyUnfolded = $('.body',
							$(languageContainer)).is(':visible');

					if (isBodyUnfolded) {
						$('.body', $(languageContainer)).addClass('hide');
					} else {
						$('.body', $(languageContainer)).removeClass('hide');
					}
				});
	</script>
	<script type="text/javascript">
		$('#languages').on(
				'click',
				'i.fa-trash',
				function() {
					var languageContainer = $(this).parent().parent().parent()
							.parent().parent(), languages = $('li.language',
							'#languages').length;

					$(languageContainer).remove();

					if (languages == 1) {
						$('#no-languages').removeClass('hide');
					}
				});
	</script>
	<script type="text/javascript">
		$('#languages').on(
				'change',
				'input.language-name',
				function() {
					var languageContainer = $(this).parent().parent().parent()
							.parent().parent(), languageName = $(this).val();

					$('h5', $(languageContainer)).html(languageName);
				});
	</script>
	<script type="text/javascript">
		function onSubmit() {
			$('.alert-success').addClass('hide');
			$('.alert-error').addClass('hide');
			$('button[type=submit]').attr('disabled', 'disabled');
			$('button[type=submit]').html('等待');

			return updateLanguageSettings();
		}
	</script>
	<script type="text/javascript">
		function updateLanguageSettings(languages) {
			var postData = {
				'languages' : getLanguages()
			}

			$.ajax({
				type : 'POST',
				url : '<c:url value="/admin/updateLanguageSettings.action" />',
				data : postData,
				dataType : 'JSON',
				success : function(result) {
					return processResult(result);
				}
			});
		}
	</script>
	<script type="text/javascript">
		function getLanguages() {
			var languages = [];
			var languageFormat = [];

			$('.language')
					.each(
							function() {
								var languageId = $('.language-id', $(this)).val() || 0, 
								languageName = $('.language-name', $(this)).val(), 
								buildFile = $('.compile-command', $(this)).val(), 
								formatId = $('#format',$(this)).val(), 
								language = {
									'languageId' : languageId,
									'languageName' : languageName,
									'buildFile' : buildFile,
									'format' : {'formatId' : formatId}
								};
								languages.push(language);
							});
			return JSON.stringify(languages);
		}
	</script>
	<script type="text/javascript">
		function processResult(result) {
			if (result['isSuccessful']) {
				$('.alert-success').removeClass('hide');
			} else {
				var message = '';

				
				$('.alert-error').html(message);
				$('.alert-error').removeClass('hide');
			}

			$('button[type=submit]')
					.html(
							'提交');
			$('button[type=submit]').removeAttr('disabled');
			$('html, body').animate({
				scrollTop : 0
			}, 100);
		}
	</script>
	<script type="text/javascript">
		function setLanguageId(languageSlug, languageId) {
			var languageSlugInput = null;
			$('.language-slug', '#languages').each(function() {
				if ($(this).val() == languageSlug) {
					languageSlugInput = $(this);
				}
			});

			var languageContainer = $(languageSlugInput).parent().parent()
					.parent().parent(), languageIdInput = $('.language-id',
					$(languageContainer));

			$(languageIdInput).val(languageId);
		}
	</script>
	<script type="text/javascript">
		$('#new-language')
				.click(
						function() {
							var formatdiv=$('#formatdiv').html();
							var languageTemplate=
								'<li class="language">'
								+ '    <div class="header">'
								+ '        <h5></h5>'
								+ '        <ul class="inline">'
								+ '            <li><a href="javascript:void(0);"><i class="fa fa-edit"></i></a></li>'
								+ '            <li><a href="javascript:void(0);"><i class="fa fa-trash"></i></a></li>'
								+ '        </ul>'
								+ '    </div> <!-- .header -->'
								+ '    <div class="body">'
								+ '        <div class="row-fluid">'
								+ '            <div class="span4">'
								+ '                <label>语言名字</label>'
								+ '            </div> <!-- .span4 -->'
								+ '            <div class="span8">'
								+ '                <div class="control-group">'
								+ '                    <input class="language-id span8" type="hidden" value="0" />'
								+ '                    <input class="language-name span8" type="text" maxlength="16" />'
								+ '                </div> <!-- .control-group -->'
								+ '            </div> <!-- .span8 -->'
								+ '        </div> <!-- .row-fluid -->'
								+ '        <div class="row-fluid">'
								+ '            <div class="span4">'
								+ '                <label>执行构建文件命令</label>'
								+ '            </div> <!-- .span4 -->'
								+ '            <div class="span8">'
								+ '                <div class="control-group">'
								+ '                    <input class="compile-command span8" type="text" maxlength="128" />'
								+ '                </div> <!-- .control-group -->'
								+ '            </div> <!-- .span8 -->'
								+ '        </div> <!-- .row-fluid -->'
								+'<div class="row-fluid"'
								+'<div class="row-fluid" id="formatdiv">'
								+'<label for="user-group">格式类型</label> <select id="format"'
								+'name="category">'
								+'<c:forEach var="format" items="${formats}">'
								+'		<option value="${format.formatId}"'
								+'		<c:if test="${format.formatId == language.format.formatId}">selected</c:if>>${format.formatText}</option>'
								+'	</c:forEach>'
								+'</select>'
								+'</div>'
								+'</div>'
								+ '    </div> <!-- .body -->'
								+ '</li>';
								
							$('#languages')
									.append(languageTemplate);
						});
	</script>
</body>
</html>