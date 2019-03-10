<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
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
<div class="page">
    <jsp:include page="fragments/header.jsp"/>
    <section>
        <h1 align="center">${resume.fullName}&nbsp;
            <a href="resume?uuid=${resume.uuid}&action=edit" title="изменить">
                <img src="img/edit.png" height="16" alt="изменить">
            </a>
        </h1>
        <div>
            <div class="contact-photo">
                <img src="img/photo.png" height="128" style="margin-right: 20px; border-radius: 5px" alt="photo">
            </div>
            <div class="contact-list">
                <c:forEach var="contactEntry" items="${resume.contacts}">
                    <jsp:useBean id="contactEntry"
                                 type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                    <%=HtmlUtil.viewContactToHtml(contactEntry.getKey(), contactEntry.getValue())%><br/>
                </c:forEach>
            </div>
        </div>
        <div class="section">
            <c:forEach var="sectionEntry" items="${resume.sections}">
                <jsp:useBean id="sectionEntry"
                             type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
                <c:set var="type" value="${sectionEntry.key}"/>
                <c:set var="abstractSection" value="${sectionEntry.value}"/>
                <jsp:useBean id="abstractSection" type="com.urise.webapp.model.AbstractSection"/>
                <h2><%=sectionEntry.getKey().getTitle()%></h2>
                <c:choose>
                    <c:when test="${type == 'OBJECTIVE' || type == 'PERSONAL'}">
                        <%=((TextSection) abstractSection).getContent()%>
                    </c:when>
                    <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                        <ul>
                            <c:forEach var="item" items="<%=((ListSection) abstractSection).getItems()%>">
                                <li>${item}</li>
                            </c:forEach>
                        </ul>
                    </c:when>
                    <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                        <div class="organization">
                            <c:forEach var="organization" items="<%=((OrganizationSection) abstractSection).getOrganizations()%>">
                                <c:if test="${organization.homePage == null}">
                                    <h3>${organization.homePage.name}</h3>
                                </c:if>
                                <c:if test="${organization.homePage != null}">
                                    <h3><a href="${organization.homePage.url}">${organization.homePage.name}</a></h3>
                                </c:if>
                                    <c:forEach var="position" items="${organization.positions}>">
                                        <jsp:useBean id="position" type="com.urise.webapp.model.Organization.Position"/>
                                        <div class="date-position">
                                            <%=HtmlUtil.printStartDatePositionToHtml(position.getStartDate())%> - <br>
                                            <%=HtmlUtil.printEndDatePositionToHtml(position.getEndDate())%>
                                        </div>
                                        <div class="description-position">
                                            <h4>${position.title}</h4>
                                            ${position.description}
                                        </div>
                                    </c:forEach>
                            </c:forEach>
                        </div>
                    </c:when>
                </c:choose>
            </c:forEach>
        </div>
    </section>
    <jsp:include page="fragments/footer.jsp"/>
</div>
</body>
</html>