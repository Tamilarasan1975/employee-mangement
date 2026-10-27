<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jspf" %>

<h2>Error</h2>
<div class="alert alert-danger"><c:out value="${message}"/></div>
<a class="btn" href="<c:url value='/employees'/>">Back to Employee List</a>

<%@ include file="footer.jspf" %>
