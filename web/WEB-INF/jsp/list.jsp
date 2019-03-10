<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
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
        <table class="table-list_resumes">
            <caption>База данных резюме</caption>
            <tr>
                <th width="400px" colspan="2">Имя</th>
                <th width="200px" colspan="2">Контакты</th>
            </tr>
            <c:forEach items="${resumes}" var="resume">
                <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
                <tr>
                    <td width="64">
                        <img src="img/photo.png" height="64" style="border-radius: 5px" alt="photo"/>
                    </td>
                    <td>
                        <a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a>
                    </td>
                    <td>
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
        </table>
    </section>
    <jsp:include page="fragments/footer.jsp"/>
</div>
</body>
</html>