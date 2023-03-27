<%@ page import="step.learning.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" %><%
  String authError = (String) request.getAttribute( "AuthError" ) ;
  User authUser = (User) request.getAttribute( "AuthUser" ) ;
  String home = request.getContextPath() ;
%>
<div class="auth-fragment">
  <% if( authUser == null ) { %>
  <form method="post" action="" >
    <label>Login: <input type="text" name="userLogin" /></label>
    <label>Password: <input type="password" name="userPassword" /></label>
    <input type="hidden" name="form-id" value="auth-form" />
    <input type="submit" value="Auth" />
    <a class="auth-sign-up" href="<%=home%>/register/">Sign Up</a>
  </form>
  <% if( authError != null ) { %>
  <span class="auth-error"><%= authError %></span>
  <% } } else { %>
  <span>Hello, </span>
  <b><%= authUser.getName() %></b>
  <a href="<%=home%>/profile/" class="auth-profile-a"><img class="auth-fragment-avatar"
                                                          src="<%=home%>/image/<%=authUser.getAvatar()%>"
                                                          alt="<%=authUser.getLogin()%>" /></a>
  <%-- region Если почта требует подтверждения, то выводим ссылку --%>
  <% if( authUser.getEmailCode() != null ) { %>
  <a href="<%=home%>/checkmail/"
     title="Почта не подтверждена, перейти на страницу подтверждения">&#x1F4E7;</a>
  <% } %>
  <%-- endregion --%>
  <a href="?logout=true">Log out</a>
  <% } %>
</div>