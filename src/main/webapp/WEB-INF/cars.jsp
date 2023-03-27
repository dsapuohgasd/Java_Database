<%@ page import="java.util.List" %>
<%@ page import="step.learning.entities.Car" %>
<%@ page contentType="text/html;charset=UTF-8" %><%
    String home = request.getContextPath() ;
    List<Car> cars = (List<Car>) request.getAttribute( "cars" ) ;
    String addError = (String) request.getAttribute( "addError" ) ;

%>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css" integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g==" crossorigin="anonymous" referrerpolicy="no-referrer">

    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/jsquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/js/bootstrap.min.js" integrity="sha512-UR25UO94eTnCVwjbXozyeVd6ZqpaAE9naiEUBK/A+QDbfSTQFhPGj5lOR6d8tsgbBk84Ggb5A3EkjsOgPRPcKA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

</head>
<body>

    <% if( addError != null ) { %>
    <h3 class="card-title text-center reg-error"><%=addError%></h3>
    <% } %>

<div style="width: 82%;  margin-left: auto; margin-right: auto; margin-top: 50px;">
    <form class="form-inline" method="post" >
        <input name="carModel" class="form-control mr-sm-2" type="text" placeholder="Model">
        <input  name="carBrand" class="form-control mr-sm-2" type="text" placeholder="Brand">
        <input  name="carYear" class="form-control mr-sm-2" type="number" placeholder="Year">
        <input  name="carColor" class="form-control mr-sm-2" type="text" placeholder="Color">
        <input  name="carPrice" class="form-control mr-sm-2" type="number" placeholder="Price">
        <input  name="carDescription" class="form-control mr-sm-2" type="text" placeholder="Description">
        <br>
        <br>
        <br>
        <input type="submit" class="btn btn-outline-success my-2 my-sm-0 " id="add-button" value="Add">
    </form>

</div>
<div style="width: 82%;  margin-left: auto; margin-right: auto; margin-top: 50px;">

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th>Model</th>
            <th>Brand</th>
            <th>Year</th>
            <th>Color</th>
            <th>Price</th>
            <th>Description</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <% if(cars!=null){
            for( Car i : cars ){ %>
                 <tr><td><%= i.getModel() %></td>
                <td><%= i.getBrand() %></td>
                <td><%= i.getYear() %></td>
                <td><%= i.getColor() %> </td>
                <td><%= i.getPrice() %> UAH</td>
                <td><%= i.getDescription() %></td>
                </tr>
        <%} }else {%>
            <h1>Error!(</h1>
        <%}%>


        </tbody>
    </table>
</div>
</body>
