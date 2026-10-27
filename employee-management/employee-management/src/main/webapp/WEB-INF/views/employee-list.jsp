<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="header.jspf" %>

<h2>Employee List</h2>

<c:if test="${not empty success}">
    <div class="alert alert-success"><c:out value="${success}"/></div>
</c:if>

<c:choose>
    <c:when test="${empty employees}">
        <p class="empty">No employees registered yet.
            <a href="<c:url value='/employees/new'/>">Register the first one</a>.</p>
    </c:when>
    <c:otherwise>
        <table>
            <thead>
            <tr>
                <th>ID</th><th>Name</th><th>Email</th><th>Phone</th>
                <th>Department</th><th>Salary</th><th>Joining Date</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="e" items="${employees}">
                <tr>
                    <td>${e.id}</td>
                    <td><c:out value="${e.name}"/></td>
                    <td><c:out value="${e.email}"/></td>
                    <td><c:out value="${e.phone}"/></td>
                    <td><c:out value="${e.department}"/></td>
                    <td><fmt:formatNumber value="${e.salary}" type="number" minFractionDigits="2"/></td>
                    <td>${e.joiningDate}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

<a class="btn" href="<c:url value='/employees/new'/>">+ Register Employee</a>

<%@ include file="footer.jspf" %>
