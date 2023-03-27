<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String email= (String) request.getAttribute("email");%>
<% String psw= (String) request.getAttribute("psw");%>
<% String pswrepeat= (String) request.getAttribute("psw-repeat");%>
<html>
<head>
  <title>Registration</title>
<style>
  * {box-sizing: border-box}

  /* Add padding to containers */
  .container {
    padding: 16px;
  }

  /* Full-width input fields */
  input[type=text], input[type=password] {
    width: 100%;
    padding: 15px;
    margin: 5px 0 22px 0;
    display: inline-block;
    border: none;
    background: #f1f1f1;
  }

  input[type=text]:focus, input[type=password]:focus {
    background-color: #ddd;
    outline: none;
  }

  /* Overwrite default styles of hr */
  hr {
    border: 1px solid #f1f1f1;
    margin-bottom: 25px;
  }

  /* Set a style for the submit/register button */
  .registerbtn {
    background-color: #04AA6D;
    color: white;
    padding: 16px 20px;
    margin: 8px 0;
    border: none;
    cursor: pointer;
    width: 100%;
    opacity: 0.9;
  }

  .registerbtn:hover {
    opacity:1;
  }

  /* Add a blue text color to links */
  a {
    color: dodgerblue;
  }

  /* Set a grey background color and center the text of the "sign in" section */
  .signin {
    background-color: #f1f1f1;
    text-align: center;
  }
</style>
</head>
<body>
<form method="post">
  <div class="container">
    <h1>Register</h1>
    <p>Please fill in this form to create an account.</p>
    <hr>

    <label for="email"><b>Email</b></label>
    <input type="text" placeholder="Enter Email" name="email" id="email" >

    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="psw" id="psw" >

    <label for="psw-repeat"><b>Repeat Password</b></label>
    <input type="password" placeholder="Repeat Password" name="psw-repeat" id="psw-repeat" >
    <hr>

    <p>By creating an account you agree to our <a href="#">Terms & Privacy</a>.</p>
    <input type="submit" class="registerbtn" value="Register"/>
  </div>

  <div class="container signin">
    <p>Already have an account? <a href="#">Sign in</a>.</p>
  </div>
</form>
<p>
<%if(email!=null&&psw!=null&&pswrepeat!=null){%>
  <%if(! psw.equals(pswrepeat)) {%>
  Passwords do not match!
  <%} else{%>
  Email: <b><%=email%></b>
    Password: <b><%=psw%></b>
  <%}%>
  <%}%>
</p>


<jsp:include page="footer.jsp"/>

</body>
</html>