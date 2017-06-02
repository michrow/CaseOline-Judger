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
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/accounts/dashboard.css" />
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
        <div id="sub-nav">
            <ul class="nav nav-tabs">
                <li class="active"><a href="#tab-statistics" data-toggle="tab">统计</a></li>
                <li><a href="#tab-accounts" data-toggle="tab">账户</a></li>
          		  <c:if test="${myProfile.role.roleType == 'admin' or myProfile.role.roleType == 'teacher'}">
                <li><a href="<c:url value="/admin" />">系统管理</a></li>
           		 </c:if>
            </ul>
        </div> <!-- #sub-nav -->
        <div id="main-content" class="tab-content">
            <div class="tab-pane active" id="tab-statistics">
                <div class="section">
                    <div class="header">
                        <h4>提交</h4>
                    </div> <!-- .header -->
                    <div class="body">
                     <table class="table table-striped">
						<thead>
							<tr>
								<th class="memory">覆盖率</th>
								<th class="name">案例名字</th>
							<!-- 	<th class="score">用户ID</th> -->
								<th class="language">语言</th>
								<th class="submit-time">提交时间</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="submission" items="${submissions}">
								<tr data-value="${submission.submissionId}">
									<td class="memory"><fmt:formatNumber type="number"
											maxFractionDigits="0" value="${submission.usecaseCoverage}" />%
									</td>
									<td class="name"><a
										href="<c:url value="/testcase/${submission.testcase.testcaseId}" />">P${submission.testcase.testcaseId}
											${submission.testcase.testcaseName}</a></td>
									<%-- <td class="user"><a
										href="<c:url value="/accounts/user/${submission.user.userId}" />">${submission.user.userName}</a></td> --%>
									<td class="language">${submission.language.languageName}</td>
									<td class="submit-time"><fmt:formatDate
											value="${submission.submissionSubmitTime}" type="both"
											dateStyle="default" timeStyle="default" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
                    </div> <!-- .body -->
                </div> <!-- .section -->
            </div> <!-- #tab-statistics -->

            <div class="tab-pane" id="tab-accounts">
                <form id="password-form" class="section" method="POST" onSubmit="onChangePasswordSubmit(); return false;">
                    <h4>修改密码</h4>
                    <div class="row-fluid">
                        <div class="alert alert-error hide"></div>
                        <div class="alert alert-success hide">密码已修改</div>
                    </div> <!-- .row-fluid -->
                    <div class="row-fluid">
                        <div class="span4">
                            <label for="old-password">旧密码</label>
                        </div> <!-- .span4 -->
                        <div class="span8">
                            <div class="control-group">
                                <input id="old-password" class="span8" type="password" maxlength="16" />
                            </div> <!-- .control-group -->
                        </div> <!-- .span8 -->
                    </div> <!-- .row-fluid -->
                    <div class="row-fluid">
                        <div class="span4">
                            <label for="new-password">新密码</label>
                        </div> <!-- .span4 -->
                        <div class="span8">
                            <div class="control-group">
                                <input id="new-password" class="span8" type="password" maxlength="16" />
                            </div> <!-- .control-group -->
                        </div> <!-- .span8 -->
                    </div> <!-- .row-fluid -->
                    <div class="row-fluid">
                        <div class="span4">
                            <label for="confirm-new-password">确认密码</label>
                        </div> <!-- .span4 -->
                        <div class="span8">
                            <div class="control-group">
                                <input id="confirm-new-password" class="span8" type="password" maxlength="16" />
                            </div> <!-- .control-group -->
                        </div> <!-- .span8 -->
                    </div> <!-- .row-fluid -->
                    <div class="row-fluid">
                        <div class="span12">
                            <button class="btn btn-primary btn-block" type="submit">修改密码</button>
                        </div> <!-- .span12 -->
                    </div> <!-- .row-fluid -->
                </form> <!-- #password-form -->
                <form id="profile-form" class="section" method="POST" onSubmit="onChangeProfileSubmit(); return false;">
                    <h4>个人资料</h4>
                    <div class="row-fluid">
                        <div class="alert alert-error hide"></div>
                        <div class="alert alert-success hide">已经修改</div>
                    </div> <!-- .row-fluid -->
                    <div class="row-fluid">
                        <div class="span4">
                            <label for="email">邮箱</label>
                        </div> <!-- .span4 -->
                        <div class="span8">
                            <div class="control-group">
                                <input id="email" class="span8" type="text" value="${user.email}" maxlength="64" placeholder="you@example.com" />
                            </div> <!-- .control-group -->
                        </div> <!-- .span8 -->
                    </div> <!-- .row-fluid -->
                   
                    <div class="row-fluid">
                        <div class="span12">
                            <button class="btn btn-primary btn-block" type="submit">更新个人资料</button>
                        </div> <!-- .span12 -->
                    </div> <!-- .row-fluid -->
                </form> <!-- #profile-form -->
            </div> <!-- #tab-accounts -->
        </div> <!-- #main-content -->
    </div> <!-- #content -->
    <!-- JavaScript -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
  
    
    <script type="text/javascript">
        function onChangePasswordSubmit() {
            $('.alert-success', '#password-form').addClass('hide');
            $('.alert-error', '#password-form').addClass('hide');
            $('button[type=submit]', '#password-form').attr('disabled', 'disabled');
            $('button[type=submit]', '#password-form').html('等待。。。');

            var oldPassword     = $('#old-password').val(),
                newPassword     = $('#new-password').val(),
                confirmPassword = $('#confirm-new-password').val();

            return doChangePasswordAction(oldPassword, newPassword, confirmPassword);
        }
    </script>
    <script type="text/javascript">
        function doChangePasswordAction(oldPassword, newPassword, confirmPassword) {
            var postData = {
                'oldPassword': oldPassword,
                'newPassword': newPassword,
                'confirmPassword': confirmPassword
            };
            
            $.ajax({
                type: 'POST',
                url: '<c:url value="/accounts/changePassword.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    return processChangePasswordResult(result);
                }
            });
        }
    </script>
    <script type="text/javascript">
        function processChangePasswordResult(result) {
            if ( result['isSuccessful'] ) {
                $('.alert-success', '#password-form').removeClass('hide');
            } else {
                var errorMessage  = '';

                if ( !result['isOldPasswordCorrect'] ) {
                    errorMessage += '旧密码错误<br>';
                }
                if ( result['isNewPasswordEmpty'] ) {
                    errorMessage += '新密码为空<br>';
                }
                if ( !result['isNewPasswordLegal'] ) {
                    errorMessage += '新密码不合法<br>';
                }
                if ( !result['isConfirmPasswordMatched'] ) {
                    errorMessage += '两次密码不一致<br>';
                }
                $('.alert-error', '#password-form').html(errorMessage);
                $('.alert-error', '#password-form').removeClass('hide');
            }
            $('button[type=submit]', '#password-form').html('修改密码');
            $('button[type=submit]', '#password-form').removeAttr('disabled');
        }
    </script>
    
    <script type="text/javascript">
        function onChangeProfileSubmit() {
            $('.alert-success', '#profile-form').addClass('hide');
            $('.alert-error', '#profile-form').addClass('hide');
            $('button[type=submit]', '#profile-form').attr('disabled', 'disabled');
            $('button[type=submit]', '#profile-form').html('等待...');

            var email       = $('#email').val()
               
            return doUpdateProfileAction(email);
        }
    </script>
    <script type="text/javascript">
        function doUpdateProfileAction(email) {
            var postData = {
                'email': email
            };

            $.ajax({
                type: 'POST',
                url: '<c:url value="/accounts/updateProfile.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    return processUpdateProfileResult(result);
                }
            });
        }
    </script>
    <script type="text/javascript">
        function processUpdateProfileResult(result) {
            if ( result['isSuccessful'] ) {
                $('.alert-success', '#profile-form').removeClass('hide');
            } else {
                var errorMessage  = '';

                if ( result['isEmailEmpty'] ) {
                    errorMessage += '邮箱不能为空<br>';
                } else if ( !result['isEmailLegal'] ) {
                    errorMessage += '无效邮箱<br>';
                } else if ( result['isEmailExists'] ) {
                    errorMessage += '邮箱已经注册<br>';
                }
                $('.alert-error', '#profile-form').html(errorMessage);
                $('.alert-error', '#profile-form').removeClass('hide');
            }
            $('button[type=submit]', '#profile-form').html('更新');
            $('button[type=submit]', '#profile-form').removeAttr('disabled');
        }
    </script>
</body>
</html>