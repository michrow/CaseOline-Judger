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
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/administration/general-settings.css" />
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
                <h2 class="page-header"><i class="fa fa-cog"></i>网站设置</h2>
                <form id="option-form" onSubmit="onSubmit(); return false;">
                    <div class="alert alert-error hide"></div> <!-- .alert-error -->
                    <div class="alert alert-success hide">修改成功</div> <!-- .alert-success -->
                   
                    <div class="control-group row-fluid">
                        <label for="copyright">版本</label>
                        <input id="copyright" class="span12" type="text" value="${options['version']}" /> 
                    </div> <!-- .control-group -->
                      <div class="control-group switch-container row-fluid">
                        <div class="span8">
                            <label for="allow-register">是否允许注册</label>
                        </div> <!-- .span8 -->
                        <div class="span4">
                          <input id="allow-register" type="checkbox" data-toggle="switch"/> 
                        </div> <!-- .span4 -->
                    </div> <!-- .control-group -->
                   
                    <div class="row-fluid">
                        <button class="btn btn-primary" type="submit">保存</button>
                    </div> <!-- .row-fluid -->
                </form> <!-- #option-form -->
            </div> <!-- #content -->
        </div> <!-- #container -->
    </div> <!-- #wrapper -->
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <%@ include file="/WEB-INF/views/administration/include/footer-script.jsp" %>
     <script type="text/javascript">
     var isAllow= ${options['allowUserRegister']}
     if(isAllow){
    	 $('#allow-register').attr("checked",true);
     }
    </script>
    <script type="text/javascript">
        function onSubmit() {
            $('.alert-success').addClass('hide');
            $('.alert-error').addClass('hide');
            $('button[type=submit]').attr('disabled', 'disabled');
            $('button[type=submit]').html('等待');

            var 
                version           = $('#copyright').val(),
                allowUserRegister   = $('#allow-register').is(':checked');
            return doUpdateGeneralSettingsAction(version, allowUserRegister);
        }
    </script>
    <script type="text/javascript">
        function doUpdateGeneralSettingsAction(version, allowUserRegister) {
            var postData = {
                'version': version, 
                'allowUserRegister': allowUserRegister
            };

            $.ajax({
                type: 'POST',
                url: '<c:url value="/admin/updateGeneral.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    return processResult(result);
                }
            });
        }
    </script>
    <script type="text/javascript">
        function processResult(result) {
            if ( result['isSuccessful'] ) {
                $('.alert-success').removeClass('hide');
            } else {
                var errorMessage  = '更新出错';

                $('.alert-error').html(errorMessage);
                $('.alert-error').removeClass('hide');
            }

            $('button[type=submit]').html('更新');
            $('button[type=submit]').removeAttr('disabled');
            $('html, body').animate({ scrollTop: 0 }, 100);
        }
    </script>
</body>
</html>