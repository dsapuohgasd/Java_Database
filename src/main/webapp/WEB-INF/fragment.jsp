<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>This is fragment</div>
<br>
<br>
<br>
Задание
<br>
<br>
<% String[] languages = new String[] {"French","Українська", "Spanish"}; %>
<p>Choose language:</p>
<% for(int i =0; i<languages.length;i++){ %>
<input type="radio" name="lang" id="lang-<%=languages[i]%>"/>
<label for="lang-<%=languages[i]%>"> <%=languages[i]%> </label>
<br>
<% } %>
