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
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/accounts/login.css" />
    <!-- JavaScript -->
    <script type="text/javascript" src="${cdnUrl}/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/flat-ui.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/md5.min.js"></script>
</head>
<body>
    <!-- Header -->
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <!-- Content -->
    <div id="content">
        <div id="login">
            <h2>登录</h2>
            <div class="alert alert-error hide"></div>
            <c:if test="${isLogout}">
            <div class="alert alert-success">已注销</div>
            </c:if>
            <form id="login-form" method="POST" onsubmit="onSubmit(); return false;">
                <p class="row-fluid">
                    <label for="username">用户名或邮箱</label>
                    <input id="username" name="username" class="span12" type="text" maxlength="32" />
                </p>
                <p class="row-fluid">
                    <label for="password">
                       	 密码
                        <%-- <span class="pull-right">
                            <a href="<c:url value="/accounts/reset-password" />">
                                	忘记密码
                            </a>
                        </span> --%>
                    </label>
                    <input id="password" name="password" class="span12" type="password" maxlength="16" />
                </p>
                <p>
                    <label class="checkbox" for="remember-me">
                        <input id="remember-me" type="checkbox" data-toggle="checkbox" />
                        	保持登录状态
                    </label>
                </p>
                <p>
                    <button class="btn btn-primary btn-block" type="submit">提交</button>
                </p>
            </form> <!-- #login-form -->
        </div> <!-- #login -->
        <p class="text-center">
            	没有账号？
            <a href="<c:url value="/accounts/register" />">注册</a>
        </p>
    </div> <!-- #content -->
    <!-- JavaScript -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
  <script type="text/javascript">
        function onSubmit() {
            $('.alert-success').addClass('hide');
            $('.alert-error').addClass('hide');
            $('button[type=submit]').attr('disabled', 'disabled');
            $('button[type=submit]').html('Please wait...');
            
            var username   = $('#username').val(),
                password   = md5($('#password').val()),
                rememberMe = $('input#remember-me').is(':checked');
            
            $('#password').val(password);
            return doLoginAction(username, password, rememberMe);
        };
    </script>
    <script type="text/javascript">
        function doLoginAction(username, password, rememberMe) {
            var postData = {
                'username': username,
                'password': password,
                'rememberMe': rememberMe
            };
            
            $.ajax({
                type: 'POST',
                url: '<c:url value="/accounts/login.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    return processLoginResult(result);
                }
            });
        }
    </script>
    <script type="text/javascript">
        function processLoginResult(result) {
            if ( result['isSuccessful'] ) {
                var forwardUrl = '${forwardUrl}' || '<c:url value="/" />';
                window.location.href = forwardUrl;
            } else {
                var errorMessage = '';
                if ( !result['isAccountValid'] ) {
                    errorMessage = "用户名或密码不正确";
                } else if ( !result['isAllowedToAccess'] ) {
                    errorMessage = "你暂且不能登录";
                }
                $('#password').val('');
                $('.alert-error').html(errorMessage);
                $('.alert-error').removeClass('hide');
            }

            $('button[type=submit]').html('登录');
            $('button[type=submit]').removeAttr('disabled');
        }
    </script>
</body>
</html>