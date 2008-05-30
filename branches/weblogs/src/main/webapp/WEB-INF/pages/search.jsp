<%@ include file="/common/taglibs.jsp"%>
<P>
<H2>Compass Search:</H2>

<FORM method="GET"><spring:bind path="command.query">
	<INPUT type="text" size="20" name="query"
		value="<c:out value="${status.value}"/>" />
</spring:bind> <INPUT type="submit" value="Search" /></FORM>

<c:if test="${! empty searchResults}">

  Search took <c:out value="${searchResults.searchTime}" />ms

<br />
	<display:table name="${searchResults.hits}" sort="external"
		class="table" defaultsort="1" pagesize="25" id="row"
		partialList="true" size="${searchResults.totalHits}"
		requestURI="\search.html">
		<display:column sortable="false" title="ID">
			<c:out value="${row.data.id}" />
		</display:column>
		<display:column sortable="false" title="Authority">
			<c:out value="${row.data.authorityName}" />
		</display:column>
		<display:column sortable="false" title="Version">
			<fmt:formatDate value="${row.data.version}" pattern="${datePattern}" />
		</display:column>
		<display:column sortable="false" title="Score">
			<c:out value="${row.score}" />
		</display:column>
	</display:table>
	<br />
	<p />
	<CENTER>
	<TABLE BORDER="5" BORDERCOLOR="#808080" CELLSPACING="1" WIDTH="405">
		<CAPTION><FONT FACE="ARIAL" SIZE="5">Search Query
		Results</FONT></CAPTION>
		<TH>ID</TH>
		<TH>Authority Name</TH>
		<TH>Version Date</TH>
		<TH>Score</TH>

		<c:forEach var="hit" items="${searchResults.hits}">
			<TR ALIGN="CENTER">
				<td><c:out value="${hit.data.id}" /></td>
				<td><c:out value="${hit.data.authorityName}" /></td>
				<td><c:out value="${hit.data.version}" /></td>
				<td><c:out value="${hit.score}" /></td>
			</TR>
		</c:forEach>

		<c:if test="${! empty searchResults.pages}">

			<BR />
			<BR />
			<BR />
			<table>
				<tr>
					<c:forEach var="page" items="${searchResults.pages}"
						varStatus="pagesStatus">
						<td><c:choose>
							<c:when test="${page.selected}">
								<c:out value="${page.from}" />-<c:out value="${page.to}" />
							</c:when>
							<c:otherwise>
								<FORM method="GET"><spring:bind path="command.query">
									<INPUT type="hidden" name="query"
										value="<c:out value="${status.value}"/>" />
								</spring:bind> <spring:bind path="command.page">
									<INPUT type="hidden" name="page"
										value="<c:out value="${pagesStatus.index}"/>" />
								</spring:bind> <INPUT type="submit"
									value="<c:out value="${page.from}" />-<c:out value="${page.to}" />" />
								</FORM>
							</c:otherwise>
						</c:choose></td>
					</c:forEach>
				</tr>
			</table>
		</c:if>
		</c:if>

		<P><BR>