<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('url.cdn')" var="cdnUrl" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/bootstrap-responsive.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/flat-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/misc/homepage.css" />
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
        <div id="slogan" class="row-fluid">
            <div class="container">
                <div class="span4 offset4">
                    <h2>Start from here!</h2>
                    <c:choose>
                    <c:when test="${!isLogin}">
                        <p><button class="btn btn-success" onclick="window.location.href='<c:url value="/accounts/register" />'">注册</button></p>
                        <p><a href="<c:url value="/accounts/login" />">登录</a></p>
                    </c:when>
                    <c:otherwise>
                        <p><button class="btn btn-success" onclick="window.location.href='<c:url value="/testcase" />'">开始答题</button></p>
                    </c:otherwise>
                    </c:choose>
                </div> 
            </div> 
        </div> 
    </div> 
    
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
   
</body>
</html>