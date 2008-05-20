<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="authorityDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='authorityDetail.heading'/>"/>
</head>

<form:form commandName="authority" method="post" action="authorityform.html" id="authorityForm" onsubmit="return validateAuthority(this)">
<form:errors path="*" cssClass="error" element="div"/>
<form:hidden path="id"/>
<ul>
    <li>
        <appfuse:label styleClass="desc" key="authority.authorityName"/>
        <form:errors path="authorityName" cssClass="fieldError"/>
        <form:input path="authorityName" id="authorityName" cssClass="text medium" cssErrorClass="text medium error" maxlength="255"/>
    </li>
    <li>
        <appfuse:label styleClass="desc" key="authority.version"/>
        <form:errors path="version" cssClass="fieldError"/>
        <form:input path="version" id="version" cssClass="text" size="11"/>
        <img src="<c:url value='/images/iconCalendar.gif'/>" alt="" id="versionDatePicker" class="calIcon"/>
    </li>

    <li class="buttonBar bottom">
        <input type="submit" class="button" name="save" value="<fmt:message key="button.save"/>"/>
        <c:if test="${not empty authority.id}">
        <input type="submit" class="button" name="delete" onclick="bCancel=true;return confirmDelete('authority')"
            value="<fmt:message key="button.delete"/>" />
        </c:if>
        <input type="submit" class="button" name="cancel" value="<fmt:message key="button.cancel"/>" onclick="bCancel=true"/>
    </li>
</ul>
</form:form>

<v:javascript formName="authority" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/scripts/validator.jsp'/>"></script>

<script type="text/javascript" src="<c:url value='/scripts/calendar/calendar.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/calendar/calendar-setup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/calendar/lang/calendar-en.js'/>"></script>
<script type="text/javascript">
    Form.focusFirstElement($('authorityForm'));
    Calendar.setup({inputField: "version", ifFormat: "%m/%d/%Y", button: "versionDatePicker"});
</script>