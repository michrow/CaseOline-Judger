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
<link rel="stylesheet" type="text/css"
	href="${cdnUrl}/css/administration/style.css" />
<link rel="stylesheet" type="text/css"
	href="${cdnUrl}/css/administration/all-problems.css" />
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
					<i class="fa fa-question-circle"></i> 测试案例
				</h2>
				<div class="alert alert-error hide"></div>
				<!-- .alert-error -->
				<div id="filters" class="row-fluid">
					<div class="span4">
						<div class="row-fluid">
							<div class="span8">
								<select id="actions">
									<option value="delete">删除</option>
								</select>
							</div>
							<!-- .span8 -->
							<div class="span4">
								<button class="btn btn-danger">删除</button>
							</div>
							<!-- .span4 -->
						</div>
						<!-- .row-fluid -->
					</div>
					<!-- .span4 -->
					<div class="span8 text-right">
						<form action="<c:url value="/admin/all-testcases" />" method="GET"
							class="row-fluid">
							<div class="span5">
								<div class="control-group">
									<input id="keyword" name="keyword" class="span12" type="text"
										placeholder="关键字" value="${keyword}" />
								</div>
								<!-- .control-group -->
							</div>
							<!-- .span5 -->
							<div class="span5">
								<select name="categoryType" id="problem-category">
									<option value="">问题类型</option>
									<c:forEach var="cate" items="${categories}">
										<option value="${cate.categoryType}"
											<c:if test="${cate.categoryType} == selectedCategory}">selected</c:if>>${cate.categoryName}</option>
									</c:forEach>
								</select>
							</div>
							<!-- .span5 -->
							<div class="span2">
								<button class="btn btn-primary">查询</button>
							</div>
							<!-- .span2 -->
						</form>
						<!-- .row-fluid -->
					</div>
					<!-- .span8 -->
				</div>
				<!-- .row-fluid -->
				<table class="table table-striped">
					<thead>
						<tr>
							<th class="check-box"><label class="checkbox"
								for="all-problems"> <input id="all-problems"
									type="checkbox" data-toggle="checkbox">
							</label></th>
							<th class="problem-id">#</th>
							<th class="problem-is-public">案例属性</th>
							<th class="problem-name">测试案例名字</th>
							<th class="problem-categories">案例类型</th>
							<th class="total-submission">总提交</th>
							<th class="accepted-submission">最高覆盖率</th>
							<th>语言</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="testcase" items="${testcases}">
							<tr data-value="${testcase.testcaseId}">
								<td class="check-box"><label class="checkbox"
									for="testcase-${testcase.testcaseId}"> <input
										id="testcase-${testcase.testcaseId}" type="checkbox"
										value="${testcase.testcaseId}" data-toggle="checkbox" />
								</label></td>
								<td class="problem-id"><a
									href="<c:url value="/admin/edit-testcase/${testcase.testcaseId}" />">${testcase.testcaseId}</a>
								</td>
								<td class="problem-is-public"><c:choose>
										<c:when test="${testcase.isPublic()}">公开</c:when>
										<c:otherwise>未开放</c:otherwise>
									</c:choose></td>
								<td class="problem-name">${testcase.testcaseName}</td>
								<td class="problem-categories"><c:choose>
										<c:when test="${testcaseRelas[testcase.testcaseId] == null}"></c:when>
										<c:otherwise>
											<c:forEach var="testcaseCategory"
												items="${testcaseRelas[testcase.testcaseId]}"
												varStatus="loop">
												<%--  <a href="<c:url value="/admin/all-testcases?testcaseCategory=${testcaseCategory.categoryType}" />">
                                       
                                    </a><c:if test="${!loop.last}">, </c:if> --%>
                                     ${testcaseCategory.categoryName}
                                </c:forEach>
										</c:otherwise>
									</c:choose></td>
								<%--    <td class="problem-tags">
                            <c:choose>
                                <c:when test="${problemTagRelationships[problem.problemId] == null}"></c:when>
                                <c:otherwise>
                                <c:forEach var="problemTag" items="${problemTagRelationships[problem.problemId]}" varStatus="loop">
                                    <a href="<c:url value="/administration/all-problems?problemTag=${problemTag.problemTagSlug}" />">
                                        ${problemTag.problemTagName}
                                    </a><c:if test="${!loop.last}">, </c:if>
                                </c:forEach>
                                </c:otherwise>
                            </c:choose>
                            </td> --%>
								<td class="total-submission">${testcase.totalSubmission}</td>
								<td class="accepted-submission"><fmt:formatNumber
										type="number" maxFractionDigits="0"
										value="${testcase.maxCoverage}" />%</td>
								<td>${testcase.language.languageName}</td> 
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div id="pagination" class="pagination pagination-centered">
					<c:set var="lowerBound"
						value="${currentPage - 5 > 0 ? currentPage - 5 : 1}" />
					<c:set var="upperBound"
						value="${currentPage + 5 < totalPages ? currentPage + 5 : totalPages}" />
					<c:set var="baseUrl"
						value="/admin/all-testcases?keyword=${keyword}&categoryType=${selectedCategory}" />
					<ul>
						<li
							class="previous <c:if test="${currentPage <= 1}">disabled</c:if>">
							<a
							href="
                        <c:choose>
                            <c:when test="${currentPage <= 1}">javascript:void(0);</c:when>
                            <c:otherwise><c:url value="${baseUrl}&page=${currentPage - 1}" /></c:otherwise>
                        </c:choose>
                        ">&lt;</a>
						</li>
						<c:forEach begin="${lowerBound}" end="${upperBound}"
							var="pageNumber">
							<li
								<c:if test="${pageNumber == currentPage}">class="active"</c:if>><a
								href="<c:url value="${baseUrl}&page=${pageNumber}" />">${pageNumber}</a></li>
						</c:forEach>
						<li
							class="next <c:if test="${currentPage >= totalPages}">disabled</c:if>">
							<a
							href="
                        <c:choose>
                            <c:when test="${currentPage >= totalPages}">javascript:void(0);</c:when>
                            <c:otherwise><c:url value="${baseUrl}&page=${currentPage + 1}" /></c:otherwise>
                        </c:choose>
                        ">&gt;</a>
						</li>
					</ul>
				</div>
				<!-- #pagination-->
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
		$('label[for=all-problems]').click(function() {
			// Fix the bug for Checkbox in FlatUI 
			var isChecked = false;
			setTimeout(function() {
				isChecked = $('label[for=all-problems]').hasClass('checked');

				if (isChecked) {
					$('label.checkbox').addClass('checked');
				} else {
					$('label.checkbox').removeClass('checked');
				}
			}, 100);
		});
	</script>
	<script type="text/javascript">
		$('button.btn-danger', '#filters')
				.click(
						function() {
							if (!confirm('确定删除？')) {
								return;
							}
							$('.alert-error').addClass('hide');
							$('button.btn-danger', '#filters').attr('disabled',
									'disabled');
							$('button.btn-danger', '#filters')
									.html(
											'等待。。。');

							var testcases = [], action = $('#actions').val();

							$('label.checkbox', 'table tbody').each(
									function() {
										if ($(this).hasClass('checked')) {
											var testcaseId = $(
													'input[type=checkbox]',
													$(this)).val();
											testcases.push(testcaseId);
										}
									});

							if (action == 'delete') {
								return doDeleteTestcasesAction(testcases);
							}
						});
	</script>
	<script type="text/javascript">
		function doDeleteTestcasesAction(testcases) {
			var postData = {
				'testcases' : JSON.stringify(testcases)
			};

			$
					.ajax({
						type : 'POST',
						url : '<c:url value="/admin/deleteTestcases.action" />',
						data : postData,
						dataType : 'JSON',
						success : function(result) {
							if (result['isSuccessful']) {
								for (var i = 0; i < testcases.length; ++i) {
									$('tr[data-value=%s]'.format(testcases[i]))
											.remove();
								}
							} else {
								$('.alert')
										.html(
												'删除出错');
								$('.alert').removeClass('hide');
							}
							$('button.btn-danger', '#filters').removeAttr(
									'disabled');
							$('button.btn-danger', '#filters')
									.html(
											'<spring:message code="voj.administration.all-problems.apply" text="Apply" />');
						}
					});
		}
	</script>
</body>
</html>