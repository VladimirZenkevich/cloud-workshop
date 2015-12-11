<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>

<label>Hello!</label>

<table>
    <tr>
        <th>ID</th>
        <th>Title</th>
    </tr>
    <c:forEach items="${stockItems}" var="item">
        <tr>
            <td><c:out value="${item.id}"/></td>
            <td><c:out value="${item.title}"/></td>
        </tr>
    </c:forEach>
</table>

</body>