<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size="30" value="${resume.fullName}"></dd>
        </dl>
        <div>
            <h3>Контакты:</h3>
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <dl>
                    <dt>${type.title}</dt>
                    <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}"></dd>
                </dl>
            </c:forEach>
        </div>
        <div class="section">
            <c:forEach var="type" items="<%=SectionType.values()%>">
                <jsp:useBean id="type" type="com.urise.webapp.model.SectionType"/>
                <dl>
                    <dt><h3>${type.title}</h3></dt>
                    <c:choose>
                        <c:when test="${type == 'OBJECTIVE' || type == 'PERSONAL'}">
                            <dd><textarea name="${type}" rows="5"
                                          cols="70"><%=HtmlUtil.printTextSection(type, resume)%></textarea></dd>
                        </c:when>
                        <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                            <dd>
                                <textarea name="${type}" rows="5"
                                          cols="70"><%=HtmlUtil.printListSection(type, resume)%></textarea>
                            </dd>
                        </c:when>
                        <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                            <dd>
                                <textarea name="${type}" rows="5" cols="70"></textarea>
                            </dd>
                        </c:when>
                    </c:choose>
                </dl>
            </c:forEach>
        </div>
        <div class="center">
            <button type="submit">Сохранить</button>
            <button onclick="window.history.back()">Отменить</button>
        </div>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>