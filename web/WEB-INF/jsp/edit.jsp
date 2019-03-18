<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Редактирование резюме ${resume.fullName}</title>
</head>
<body>
<div class="page">
    <jsp:include page="fragments/header.jsp"/>
    <section>
        <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="uuid" value="${resume.uuid}">
            <div class="block-input">
                <div><b>Ф.И.О.:</b></div>
                <div><input type="text" name="fullName" value="${resume.fullName}"></div>
            </div>
            <div>
                <h2>Контакты</h2>
                <c:forEach var="type" items="<%=ContactType.values()%>">
                    <div class="block-input">
                        <div>${type.title}:</div>
                        <div><input type="text" name="${type.name()}" value="${resume.getContact(type)}">
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="section">
                <c:forEach var="type" items="<%=SectionType.values()%>">
                    <c:set var="section" value="${resume.getSection(type)}"/>
                    <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
                    <div>
                        <div><h2>${type.title}</h2></div>
                        <c:choose>
                            <c:when test="${type == 'OBJECTIVE' || type == 'PERSONAL'}">
                                <div><textarea name="${type}" rows="5"
                                               cols="75"><%=section%></textarea>
                                </div>
                            </c:when>
                            <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                                <div><textarea name="${type}" rows="5"
                                               cols="75"><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                                </div>
                            </c:when>
                            <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                                <c:forEach var="organization"
                                           items="<%=((OrganizationSection) section).getOrganizations()%>"
                                           varStatus="number">
                                    <div style="margin-bottom: 20px">
                                        <div class="block-input">
                                            <div>название организации:</div>
                                            <div><input type="text" name="${type}" value="${organization.homePage.name}"></div>
                                        </div>
                                        <div class="block-input">
                                            <div>сайт организации:</div>
                                            <div><input type="text" name="${type}url" value="${organization.homePage.url}" class="input"></div>
                                        </div>
                                        <c:forEach var="position" items="${organization.positions}">
                                            <jsp:useBean id="position"
                                                         type="com.urise.webapp.model.Organization.Position"/>
                                            <div class="block-input">
                                                <div>дата начала:</div>
                                                <div><input type="text" name="${type}${number.index}startDate" value="<%=HtmlUtil.printDatePositionToHtml(position.getStartDate())%>"
                                                            placeholder="MM/yyyy"></div>
                                            </div>
                                            <div class="block-input">
                                                <div>дата окончания:</div>
                                                <div><input type="text" name="${type}${number.index}endDate" value="<%=HtmlUtil.printDatePositionToHtml(position.getEndDate())%>"
                                                            placeholder="MM/yyyy"></div>
                                            </div>
                                            <c:if test="${type == 'EXPERIENCE'}">
                                                <div class="block-input">
                                                    <div>занимаемая должность:</div>
                                                    <div><input type="text" name="${type}${number.index}title" value="${position.title}"></div>

                                                </div>
                                            </c:if>
                                            <c:if test="${type == 'EDUCATION'}">
                                                <div class="block-input">
                                                    <div>специальность:</div>
                                                    <div><input type="text" name="${type}${number.index}title" value="${position.title}"></div>
                                                </div>
                                            </c:if>
                                            <div>
                                                <div style="margin-bottom: 5px">описание:</div>
                                                <div><textarea name="${type}${number.index}description" rows="8"
                                                               cols="75">${position.description}</textarea></div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
            <div class="button-center">
                <button type="submit">Сохранить</button>
                <button type="button" onclick="window.history.back()">Отменить</button>
            </div>
        </form>
    </section>
    <jsp:include page="fragments/footer.jsp"/>
</div>
</body>
</html>