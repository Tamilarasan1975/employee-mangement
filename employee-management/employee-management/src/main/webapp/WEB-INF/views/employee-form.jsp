<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jspf" %>

<h2>Register Employee</h2>

<form:form method="post" action="${pageContext.request.contextPath}/employees" modelAttribute="employee">

    <label for="name">Full Name</label>
    <form:input path="name" id="name"/>
    <form:errors path="name" cssClass="error"/>

    <label for="email">Email</label>
    <form:input path="email" id="email"/>
    <form:errors path="email" cssClass="error"/>

    <label for="phone">Phone (10 digits)</label>
    <form:input path="phone" id="phone"/>
    <form:errors path="phone" cssClass="error"/>

    <label for="department">Department</label>
    <form:select path="department" id="department">
        <form:option value="" label="-- Select Department --"/>
        <form:options items="${departments}"/>
    </form:select>
    <form:errors path="department" cssClass="error"/>

    <label for="salary">Salary</label>
    <form:input path="salary" id="salary" type="number" step="0.01"/>
    <form:errors path="salary" cssClass="error"/>

    <label for="joiningDate">Joining Date</label>
    <form:input path="joiningDate" id="joiningDate" type="date"/>
    <form:errors path="joiningDate" cssClass="error"/>

    <button type="submit" class="btn">Save Employee</button>
    <a class="btn" style="background:#6c757d" href="<c:url value='/employees'/>">Cancel</a>
</form:form>

<%@ include file="footer.jspf" %>
