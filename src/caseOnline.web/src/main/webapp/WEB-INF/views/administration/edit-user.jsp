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
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/administration/edit-user.css" />
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
                <h2 class="page-header"><i class="fa fa-user"></i> 编辑用户</h2>
                <form id="profile-form" onSubmit="onSubmit(); return false;">
                     <div class="alert alert-error hide"></div> <!-- .alert-error -->
                    <div class="alert alert-success hide">更新完成</div> <!-- .alert-success -->
                    <div class="control-group row-fluid">
                        <label for="username">用户名</label>
                        <input id="username" class="span12" type="text" value="${user.userName}" disabled="disabled" />
                    </div> <!-- .control-group -->
                    <div class="control-group row-fluid">
                        <label for="password">密码</label>
                        <input id="password" class="span12" type="password" maxlength="16" />
                    </div> <!-- .control-group -->
                    <div class="control-group row-fluid">
                        <label for="email">邮箱</label>
                        <input id="email" class="span12" type="text" maxlength="64" value="${user.email}" />
                    </div> <!-- .control-group -->
                    <div class="control-group row-fluid">
                        <label for="user-group">角色</label>
                        <select id="user-group" name="userGroup">
                        <c:forEach var="role" items="${userRoles}">
                            <option value="${role.roleType}" <c:if test="${role.roleType == user.role.roleType}">selected</c:if> >${role.roleName}</option>
                        </c:forEach>
                        </select>
                    </div> <!-- .control-group --> 

                    <div class="row-fluid">
                        <div class="span12">
                            <button class="btn btn-primary" type="submit">更新</button>
                        </div> <!-- .span12 -->
                    </div> <!-- .row-fluid -->
                </form> <!-- #profile-form -->
            </div> <!-- #content -->
        </div> <!-- #container -->
    </div> <!-- #wrapper -->
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
<%--     <%@ include file="/WEB-INF/views/administration/include/footer-script.jsp" %> --%>
 
    <script type="text/javascript">
        function onSubmit() {
             $('.alert-success', '#profile-form').addClass('hide');
            $('.alert-error', '#profile-form').addClass('hide');
            $('button[type=submit]', '#profile-form').attr('disabled', 'disabled');
            $('button[type=submit]', '#profile-form').html('等待。。。');

            var userId             = '${user.userId}',
                password        = $('#password').val(),
                email           = $('#email').val(),
                userRole       = $('#user-group').val(); 
            return doEditUserAction(userId, password, email, userRole);
        }
    </script>
    <script type="text/javascript">
        function doEditUserAction(userId, password, email, userRole) {
            var postData = {
                'userId': userId,
                'password': password,
                'email': email,
                'userRole': userRole
            };
            
            $.ajax({
                type: 'POST',
                url: '<c:url value="/admin/editUser.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    return processEditUserResult(result);
                }
            });
        }
    </script>
    <script type="text/javascript">
         function processEditUserResult(result) {
            if ( result['isSuccessful'] ) {
                $('.alert-success').removeClass('hide');
            }
             else {
                var errorMessage  = '';
                if ( result['isEmailEmpty'] ) {
                    errorMessage += '邮箱为空<br>';
                } else if ( !result['isEmailLegal'] ) {
                    errorMessage += '邮箱不合法<br>';
                } else if ( result['isEmailExists'] ) {
                    errorMessage += '邮箱已存在<br>';
                }
                
                $('.alert-error').html(errorMessage);
                $('.alert-error').removeClass('hide');
            }
            $('button[type=submit]').html('跟新');
            $('button[type=submit]').removeAttr('disabled');
            $('html, body').animate({ scrollTop: 0 }, 100); 
        }  
    </script>
</body>
</html>