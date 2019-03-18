<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список резюме</title>
</head>
<body>
<div class="page">
    <jsp:include page="fragments/header.jsp"/>
    <section>
        <div class="button-center">
            <div class="button"><a href="resume?action=add">Добавить</a></div>
        </div>
        <table class="table-list_resumes">
            <caption>База данных резюме</caption>
            <thead>
            <tr>
                <th colspan="2" width="120">Имя</th>
                <th colspan="2">Контакты</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="resume" items="${resumes}">
                <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
                <tr>
                    <td width="65">
                        <a href="resume?uuid=${resume.uuid}&action=view" title="${resume.fullName}">
                            <img src="img/photo.png" height="64" style="border-radius: 5px" alt="photo"/>
                        </a>
                    </td>
                    <td width="350px">
                        <a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a>
                        <c:set var="section" value="<%=resume.getSections().get(SectionType.OBJECTIVE)%>"/>
                        <c:if test="${section != null}">
                            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
                            <p style="font-size: 12px"><b><%=((TextSection) section).getContent()%></b></p>
                        </c:if>
                    </td>
                    <td class="td-img" width="200">
                        <%=HtmlUtil.viewContactToHtml(ContactType.PHONE, resume.getContact(ContactType.PHONE))%><br>
                        <%=HtmlUtil.viewContactToHtml(ContactType.EMAIL, resume.getContact(ContactType.EMAIL))%>
                    </td>
                    <td align="center" width="100px">
                        <a href="resume?uuid=${resume.uuid}&action=edit" title="изменить"><img src="img/edit.png"
                                                                                               height="32"
                                                                                               alt="изменить"></a>&nbsp;
                        <a href="resume?uuid=${resume.uuid}&action=delete" title="удалить"><img src="img/delete.png"
                                                                                                height="32"
                                                                                                alt="удалить"></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
    <jsp:include page="fragments/footer.jsp"/>
</div>
</body>
</html>