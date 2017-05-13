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
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/administration/dashboard.css" />
    <!-- JavaScript -->
    <script type="text/javascript" src="${cdnUrl}/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/md5.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/pace.min.js"></script>
</head>
<body>
    <div id="wrapper">
        <!-- Sidebar -->
        <%@ include file="/WEB-INF/views/administration/include/sidebar.jsp" %>
        <div id="container">
            <!-- Header -->
             <div id="header">
                <a id="sidebar-toggle" href="javascript:void(0);"><i class="fa fa-bars"></i></a>
                <ul class="nav inline">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" role="button" href="javascript:void(0);">
                            ${myProfile.userName} <img src="${cdnUrl}/img/avatar.jpg" alt="avatar">
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li class="divider"></li>
                            <li><a href="<c:url value="/accounts/login?logout=true" />"><i class="fa fa-sign-out"></i> 注销</a></li>
                        </ul>
                    </li>
                </ul>
            </div> <!-- #header -->
            <!-- Content -->
            <div id="content">
                <h2 class="page-header">
                    <i class="fa fa-dashboard"></i> 预览
                </h2>
                <div id="overview" class="row-fluid">
                    <div class="span3">
                        <div id="overview-users" class="widget">
                            <div class="row-fluid glance">
                                <div class="span4 text-center">
                                    <i class="fa fa-users"></i>
                                </div> <!-- .span4 -->
                                <div class="span8">
                                    <span class="text-uppercase">总用户量</span>
                                    <h2>${totalUsers}</h2>
                                </div> <!-- .span8 -->
                            </div> <!-- .row-fluid -->
                            <div class="row-fluid">
                                <div class="span6 border-right">
                                    <span class="text-uppercase">今日注册</span>
                                    <h4>${newUsersToday}</h4>
                                </div> <!-- .span6 -->
                                <div class="span6">
                                    <span class="text-uppercase">在线用户量</span>
                                    <h4>${onlineUsers}</h4>
                                </div> <!-- .span6 -->
                            </div> <!-- .row-fluid -->
                        </div> <!-- #overview-users -->
                    </div> <!-- .span3 -->
                    <div class="span3">
                        <div id="overview-problems" class="widget">
                            <div class="row-fluid glance">
                                <div class="span4 text-center">
                                    <i class="fa fa-question-circle"></i>
                                </div> <!-- .span4 -->
                                <div class="span8">
                                    <span class="text-uppercase">总测试案例</span>
                                    <h2>${totalTestcases}</h2>
                                </div> <!-- .span8 -->
                            </div> <!-- .row-fluid -->
                            <div class="row-fluid">
                               
                                <div class="span6">
                                    <span class="text-uppercase">公开测试案例</span>
                                    <h4>${totalPublicTestcases}</h4>
                                </div> <!-- .span6 -->
                            </div> <!-- .row-fluid -->
                        </div> <!-- #overview-problems -->
                    </div> <!-- .span3 -->
                   
                    <div class="span3">
                        <div id="overview-submissions" class="widget">
                            <div class="row-fluid glance">
                                <div class="span4 text-center">
                                    <i class="fa fa-code"></i>
                                </div> <!-- .span4 -->
                                <div class="span8 text-right">
                                    <span class="text-uppercase">今日提交</span>
                                    <h2>${submissionsToday}</h2>
                                </div> <!-- .span8 -->
                            </div> <!-- .row-fluid -->
                            <a href="<c:url value="/admin/all-submissions" />" class="more">
                                更多提交 <i class="fa fa-arrow-circle-right"></i> 
                            </a>
                        </div> <!-- #overview-submissions -->
                    </div> <!-- .span3 -->
                </div> <!-- #overview -->
                <div class="row-fluid">
            
                    <div class="span4">
                        <div id="system-panel" class="panel">
                            <div class="header">
                                <h5>
                                    <i class="fa fa-info-circle"></i> 
                                   系统信息
                                </h5>
                            </div> <!-- .header -->
                            <div class="body">
                                <div class="row-fluid">
                                    <div class="span4">版本</div> <!-- .span4 -->
                                    <div id="product-version" class="span8">${productVersion}</div> <!-- .span8 -->
                                </div> <!-- .row-fluid -->
                                <div class="row-fluid">
                                    <div class="span4">内存占用</div> <!-- .span4 -->
                                    <div class="span8">${memoryUsage} MB</div> <!-- .span8 -->
                                </div> <!-- .row-fluid -->
                                <div class="row-fluid">
                                    <div class="span4">在线测评机</div> <!-- .span4 -->
                                    <div class="span8">${onlineJudgers}</div> <!-- .span8 -->
                                </div> <!-- .row-fluid -->
                            </div> <!-- .body -->
                        </div> <!-- #system-panel -->
                    </div> <!-- .span4 -->
                </div> <!-- .row-fluid -->
            </div> <!-- #content -->
        </div> <!-- #container -->
    </div> <!-- #wrapper -->
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <%@ include file="/WEB-INF/views/administration/include/footer-script.jsp" %>
    <script type="text/javascript">
        $.getScript('${cdnUrl}/js/highcharts.min.js', function() {
            return getSubmissionsOfUsers(7);
        });
    </script>
    <script type="text/javascript">
        $('#submission-period').change(function() {
            var period = $(this).val();
            return getSubmissionsOfUsers(period);
        });
    </script>
    <script type="text/javascript">
        function getSubmissionsOfUsers(period) {
            var pageRequests = {
                'period': period
            };

            $.ajax({
                type: 'GET',
                url: '<c:url value="/administration/getNumberOfSubmissions.action" />',
                data: pageRequests,
                dataType: 'JSON',
                success: function(result){
                    return processSubmissionOfUsers(result['acceptedSubmissions'], result['totalSubmissions']);
                }
            });
        }
    </script>
    <script type="text/javascript">
        function processSubmissionOfUsers(acceptedSubmissionsMap, totalSubmissionsMap) {
            var categories          = [],
                totalSubmissions    = [],
                acceptedSubmissions = [];
            
            $.each(totalSubmissionsMap, function(key, value){
                categories.push(key);
                totalSubmissions.push(value);
            });
            $.each(acceptedSubmissionsMap, function(key, value){
                acceptedSubmissions.push(value);
            });
            displaySubmissionsOfUsers(categories, acceptedSubmissions, totalSubmissions);
        }
    </script>
    <script type="text/javascript">
        function displaySubmissionsOfUsers(categories, acceptedSubmissions, totalSubmissions) {
            Highcharts.setOptions({
                colors: ['#34495e', '#e74c3c']
            });

            $('#submissions-calendar').highcharts({
                chart: {
                    backgroundColor: null,
                },
                title: {
                    text: null
                },
                xAxis: {
                    categories: categories
                },
                yAxis: {
                    allowDecimals: false,
                    title: {
                        text: '<spring:message code="voj.administration.index.number-of-submissions" text="Number of Submissions" />'
                    }
                },
                tooltip: {
                    shared: true,
                    crosshairs: true
                },
                series: [
                    {
                        name: '<spring:message code="voj.administration.index.total-submissions" text="Total Submissions" />',
                        lineWidth: 4,
                        marker: {
                            radius: 4
                        },
                        data: totalSubmissions
                    },
                    {
                        name: '<spring:message code="voj.administration.index.accepted-submissions" text="Accepted Submissions" />',
                        data: acceptedSubmissions
                    }
                ]
            });
        }
    </script>
    <script type="text/javascript">
        $(function() {
            var currentVersion = $('#product-version').html(),
                newVersion     = '0.0.0';

            $.ajax({
                type: 'GET',
                url: 'https://api.github.com/repos/zjhzxhz/voj/releases/latest',
                dataType: 'jsonp',
                success: function(result){
                    newVersion = result['data']['name'];
                    if ( newVersion > currentVersion ) {
                        $('#product-version').html('<spring:message code="voj.administration.index.new-version-available" text="New version available" />: ' + newVersion);
                    }
                }
            });
        });
    </script>
</body>
</html>