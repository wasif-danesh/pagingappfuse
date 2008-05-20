<%@ include file="/common/taglibs.jsp"%> 
<html> 
  <head> 
    <title>Search</title> 
  </head> 
  <body> 
    Search: 
    <form method="GET"> 
      <spring:bind path="command.query"> 
        <input id="query" type="text" name="query" value="<c:out value="${status.value}"/>" /> 
      </spring:bind> 
      <p> 
      <input type="submit" value="Search" /> 
      </p> 
    </form> 
    <c:if test="${! empty searchResults}"> 
      <p>Search took <c:out value="${searchResults.searchTime}" /> ms</p> 
      <table border="2"> 
        <tr> 
          <th>SCORE</th> 
          <th>TYPE</th> 
          <th>ID</th> 
          <th>NAME</th>
          <th>VERSION</th>
        </tr> 
        <c:forEach var="hit" items="${searchResults.hits}"> 
          <tr> 
            <td><fmt:formatNumber type="percent" value="${hit.score}" /></td> 
            <td><c:out value="${hit.alias}" /></td> 
            <td><c:out value="${hit.data.id}" /></td> 
            <td><c:out value="${hit.data.name}" /></td> 
            <td><c:out value="${hit.data.version}" /></td>                         
          </tr> 
        </c:forEach> 
      </table> 
    </c:if> 
  </body> 
</html> 