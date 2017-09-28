<%-- 
    Document   : orderSuccess
    Created on : Sep 21, 2017, 1:31:36 PM
    Author     : SHWETA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <title>Order Confirm</title>
    </head>
    <body bgcolor="#f5f5f5">
        
        <div class="navbar-fixed">
            <nav>
                <div class="nav-wrapper blue darken-1">
                    <a class="brand-logo"><img src="resources/Images/logo2.jpg" height="70" style="margin-left: 10px"></a>
                    <a style="margin-left: 130px; font-family: cursive; font-size: 25px">Spread the ART</a>
                </div>
            </nav>

        </div>
        
        <div class="container row ">
            <div class="col s12 ">
                <div class="card">
                    <div class="card-content">
                        <center class="green-text">Congratulations! Your order is successful. A mail with attachment has been sent to your registered email-id.
                        </center>
                    </div>
                </div>
            </div>
        </div>
    <center>Go to  <a href="myOrder"> My Orders</a></center>
        
    <%@include file="footer.jsp" %>
    
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
    <script type="text/javascript" src="resources/js/myjs.js"></script>
    </body>
</html>
