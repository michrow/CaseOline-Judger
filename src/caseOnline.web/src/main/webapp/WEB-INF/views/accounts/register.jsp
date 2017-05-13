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
                <h5><spring:message code="voj.accounts.register.registration-closed" text="Online Registration Closed" /></h5>
                <p><spring:message code="voj.accounts.register.registration-closed-message" text="Online registration is now closed. If you would like to register onsite or have questions about your registration, please contact webmaster." /></p>
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
            $('button[type=submit]').html('<spring:message code="voj.accounts.register.please-wait" text="Please wait..." />');
            
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
                    errorMessage += 'Username empty." /><br>';
                } else if ( !result['isUsernameLegal'] ) {
                    var username = $('#username').val();

                    if ( username.length < 6 || username.length > 16 ) {
                        errorMessage += 'The length of Username must between 6 and 16 characters<br>';
                    } else if ( !username[0].match(/[a-z]/i) ) {
                        errorMessage += 'Username must start with a letter(a-z)<br>';
                    } else {
                        errorMessage += 'Username can only contain letters(a-z), numbers, and underlines(_)<br>';
                    }
                } else if ( result['isUsernameExists'] ) {
                    errorMessage += 'Someone already has that username<br>';
                }
                if ( result['isPasswordEmpty'] ) {
                    errorMessage += 'You can&apos;t leave Password empty<br>';
                } else if ( !result['isPasswordLegal'] ) {
                    errorMessage += 'The length of Password must between 6 and 16 characters<br>';
                }
                if ( result['isEmailEmpty'] ) {
                    errorMessage += 'leave Email empty<br>';
                } else if ( !result['isEmailLegal'] ) {
                    errorMessage += 'The Email seems invalid.<br>';
                } else if ( result['isEmailExists'] ) {
                    errorMessage += 'Someone already use that email.<br>';
                }
                if ( !result['isLanguageLegal'] ) {
                    errorMessage += 'leave Language Preference empty.<br>';
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