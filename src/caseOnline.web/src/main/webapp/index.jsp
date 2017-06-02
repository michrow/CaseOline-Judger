<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
<body>
    <c:redirect url="/"> </c:redirect>
    <c:if test="${GoogleAnalyticsCode != ''}">
    <script type="text/javascript">${GoogleAnalyticsCode}</script>
    </c:if>
</body>
</body>
</html>