<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jspf" %>

<h2>Audit Log (written by Spring AOP)</h2>
<p>Every call to the service layer is recorded here by <code>AuditLoggingAspect</code>.</p>

<c:choose>
    <c:when test="${empty entries}">
        <p class="empty">No audit entries yet. Register or list employees to generate some.</p>
    </c:when>
    <c:otherwise>
        <table>
            <thead><tr><th>Time</th><th>Operation</th><th>Status</th><th>Details</th></tr></thead>
            <tbody>
            <c:forEach var="a" items="${entries}">
                <tr>
                    <td>${a.time}</td>
                    <td><c:out value="${a.operation}"/></td>
                    <td class="${a.status}">${a.status}</td>
                    <td><c:out value="${a.details}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

<%@ include file="footer.jspf" %>
