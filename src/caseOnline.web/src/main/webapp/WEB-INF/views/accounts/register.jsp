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
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/accounts/register.css" />
    <!-- JavaScript -->
    <script type="text/javascript" src="${cdnUrl}/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/md5.min.js"></script>
   
</head>
<body>
    <!-- Header -->
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <!-- Content -->
    <div id="content">
        <div id="register">
            <h2>创建账户</h2>
            <div class="alert alert-error hide"></div>
        <c:choose>
        <c:when test="${!isAllowRegister}">
            <div class="alert alert-warning">
                <h5>注册功能已关闭</h5>
            </div> <!-- .alert -->
        </c:when>
        <c:otherwise>
            <form id="register-form" method="POST" onsubmit="onSubmit(); return false;">
                <p class="row-fluid">
                    <label for="username">用户名</label>
                    <input id="username" name="username" class="span12" type="text" maxlength="16" />
                </p>
                <p class="row-fluid">
                    <label for="email">邮箱</label>
                    <input id="email" name="email" class="span12" type="text" maxlength="64" />
                </p>
                <p class="row-fluid">
                    <label for="password">密码</label>
                    <input id="password" name="password" class="span12" type="password" maxlength="16" />
                </p>          
                <p>
                    <button class="btn btn-primary btn-block" type="submit">注册</button>
                </p>
            </form> <!-- #register-form -->
        </c:otherwise>
        </c:choose>
        <p class="text-center">
            已有账户<br />
            <a href="<c:url value="/accounts/login" />">登录</a>
        </p>
        </div> <!-- #register -->
    </div> <!-- #content -->

    <!-- JavaScript -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
  
       <c:if test="${isAllowRegister}">
    <script type="text/javascript">
        function onSubmit() {
            $('.alert-error').addClass('hide');
            $('button[type=submit]').attr('disabled', 'disabled');
            $('button[type=submit]').html('等待...');
            
            var username            = $('#username').val(),
                password            = $('#password').val(),
                email               = $('#email').val()
              
            
            return doRegisterAction(username, password, email);
        };
    </script>
    <script type="text/javascript">
        function doRegisterAction(username, password, email) {
            var postData = {
                'username': username,
                'password': password,
                'email': email
            };

            $.ajax({
                type: 'POST',
                url: '<c:url value="/accounts/register.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    return processRegisterResult(result);
                }
            });
        }
    </script>
    <script type="text/javascript">
        function processRegisterResult(result) {
            if ( result['isSuccessful'] ) {
                var forwardUrl = '${forwardUrl}' || '<c:url value="/" />';
                window.location.href = forwardUrl;
            } else {
                var errorMessage  = '';

                if ( result['isUsernameEmpty'] ) {
                    errorMessage += '用户名不能为空." /><br>';
                } else if ( !result['isUsernameLegal'] ) {
                    var username = $('#username').val();

                    if ( username.length < 6 || username.length > 16 ) {
                        errorMessage += '用户名长度必须在6-16间<br>';
                    } else if ( !username[0].match(/[a-z]/i) ) {
                        errorMessage += '用户名必须以字母开头<br>';
                    } else {
                        errorMessage += '用户名只能含有字母、数字、以及下划线<br>';
                    }
                } else if ( result['isUsernameExists'] ) {
                    errorMessage += '用户名已存在<br>';
                }
                if ( result['isPasswordEmpty'] ) {
                    errorMessage += '密码不能为空<br>';
                } else if ( !result['isPasswordLegal'] ) {
                    errorMessage += '密码长度必须在6-16个字符<br>';
                }
                if ( result['isEmailEmpty'] ) {
                    errorMessage += '邮箱不能为空<br>';
                } else if ( !result['isEmailLegal'] ) {
                    errorMessage += '此邮箱已经注册<br>';
                } else if ( result['isEmailExists'] ) {
                    errorMessage += '此邮箱已经注册<br>';
                }              

                $('.alert-error').html(errorMessage);
                $('.alert-error').removeClass('hide');
            }

            $('button[type=submit]').html('注册');
            $('button[type=submit]').removeAttr('disabled');
            $('html, body').animate({ scrollTop: 0 }, 100);
        }
    </script>
    </c:if>
    <c:if test="${GoogleAnalyticsCode != ''}">
    ${googleAnalyticsCode}
    </c:if>
</body>
</html>