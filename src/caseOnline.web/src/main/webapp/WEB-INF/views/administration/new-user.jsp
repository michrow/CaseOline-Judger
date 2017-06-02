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
	href="${cdnUrl}/css/administration/new-user.css" />
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
					<i class="fa fa-user"></i>新增
				</h2>
				<form id="profile-form" onSubmit="onSubmit(); return false;">
					<div class="alert alert-error hide"></div>
					<!-- .alert-error -->
					<div class="alert alert-success hide">创建成功</div>
					<!-- .alert-success -->
					<div class="control-group row-fluid">
						<label for="username">用户名</label> <input id="username"
							class="span12" type="text" maxlength="16" />
					</div>
					<!-- .control-group -->
					<div class="control-group row-fluid">
						<label for="password">密码</label> <input id="password"
							class="span12" type="password" maxlength="16" />
					</div>
					<!-- .control-group -->
					<div class="control-group row-fluid">
						<label for="email">邮箱</label> <input id="email" class="span12"
							type="text" maxlength="64" />
					</div>
					<!-- .control-group -->
					<div class="control-group row-fluid">
						<label for="user-group">用户角色</label> <select id="user-group"
							name="userGroup">
							<c:forEach var="role" items="${userRoles}">
								<option value="${role.roleType}">${role.roleName}</option>
							</c:forEach>
						</select>
					</div>
					<!-- .control-group -->

					<div class="row-fluid">
						<div class="span12">
							<button class="btn btn-primary" type="submit">添加</button>
						</div>
						<!-- .span12 -->
					</div>
					<!-- .row-fluid -->
				</form>
				<!-- #profile-form -->
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
            var username        = $('#username').val(),
                password        = $('#password').val(),
                email           = $('#email').val(),
                userRole       = $('#user-group').val();
            
            $('.alert-success', '#profile-form').addClass('hide');
            $('.alert-error', '#profile-form').addClass('hide');
            $('button[type=submit]', '#profile-form').attr('disabled', 'disabled');
            $('button[type=submit]', '#profile-form').html('等待');

            return doCreateUserAction(username, password, email, userRole);
        }
    </script>
	<script type="text/javascript">
        function doCreateUserAction(username, password, email, userRole) {
            var postData = {
                'username': username,
                'password': password,
                'email': email,
                'userRole': userRole
            };
            
            $.ajax({
                type: 'POST',
                url: '<c:url value="/admin/user/newUser.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    return processCreateUserResult(result);
                }
            });
        }
    </script>
	<script type="text/javascript">
        function processCreateUserResult(result) {
            if ( result['isSuccessful'] ) {
                $('input').val('');
                $('.alert-success', '#profile-form').removeClass('hide');
            } else {
                var errorMessage  = '';

                if ( result['isUsernameEmpty'] ) {
                    errorMessage += '用户名不能为空<br>';
                } else if ( !result['isUsernameLegal'] ) {
                    var username = $('#username').val();

                    if ( username.length < 6 || username.length > 16 ) {
                        errorMessage += '用户名要大于6小于16<br>';
                    } else if ( !username[0].match(/[a-z]/i) ) {
                        errorMessage += '用户名必须以字母开头<br>';
                    } else {
                        errorMessage += '用户名不合法<br>';
                    }
                } else if ( result['isUsernameExists'] ) {
                    errorMessage += '用户名已经存在<br>';
                }
                if ( result['isPasswordEmpty'] ) {
                    errorMessage += '密码不能为空<br>';
                } else if ( !result['isPasswordLegal'] ) {
                    errorMessage += '密码不合法<br>';
                }
                if ( result['isEmailEmpty'] ) {
                    errorMessage += '邮箱不能为空<br>';
                } else if ( !result['isEmailLegal'] ) {
                    errorMessage += '邮箱无效<br>';
                } else if ( result['isEmailExists'] ) {
                    errorMessage += '邮箱已经存在<br>';
                }
              
                $('.alert-error', '#profile-form').html(errorMessage);
                $('.alert-error', '#profile-form').removeClass('hide');
            }
            $('button[type=submit]', '#profile-form').removeAttr('disabled');
            $('button[type=submit]', '#profile-form').html('添加');
        }
    </script>
</body>
</html>