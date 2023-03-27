
<%@ page contentType="text/html;charset=UTF-8" language="java"
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>JSP basic</title>
    <style>
        label{
            cursor: pointer;
        }
    </style>
</head>
<%String home ="/Webbasics_war_exploded";%>
<body>

<jsp:include page="headerfragment.jsp"/>
<h2>Hello World!</h2>
<br>
<a href="filters">Веб-фильтры</a>
<br>
<a href="guice.jsp">Guice</a>
<br>
<a href="servlets">Servlets</a>
<br>
<a href="cars/">Cars</a>
<br>
<br>
<img src="<%=home%>/img/_________________620660c7b3f01.jpg" alt="">
<img src="<%=home%>/img/7e17f30c62386773bc2c3e24a0e4013d.jpg" alt="">
<p2>
    работает
</p2>
<br>
<br>
&lt;% ... %&gt;
<% int x = 10; %>
&lt;% ... %&gt;
x = <%= x %>
<br>
<br>
<br>
<jsp:include page="fragment.jsp"/>

<% if(x<10) { %>
<b>x<10</b>
<%} else {%>
<b>x>10</b>
<%}%>
<br>
<br>
Массивы
<br>
<% String[] arr = new String[] {"Some","Words", "in","array"};%>

<% for(String str : arr){ %>
    <b><%= str %></b>
<% } %>
<br>
<br>

</body>
</html>
