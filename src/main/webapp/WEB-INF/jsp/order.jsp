<%-- 
    Document   : order
    Created on : Sep 8, 2017, 12:13:02 PM
    Author     : SHWETA
--%>

<%@page import="java.util.Iterator"%>
<%@page import="com.art.model.Painting"%>
<%@page import="com.art.model.Orders"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <title>Orders</title>
    </head>

    <body bgcolor="#f5f5f5">

        <div class="navbar-fixed">
            <nav>
                <div class="nav-wrapper blue darken-1">
                    <a class="brand-logo"><img src="resources/Images/logo2.jpg" height="70" style="margin-left: 10px"></a>
                    <a style="margin-left: 130px; font-family: cursive; font-size: 25px">Spread the ART</a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li><a href="myCart"><i class="fa fa-shopping-cart left" aria-hidden="true"></i>My Cart</a></li>
                        <li><a href="/ArtGallery">Home<i class="material-icons left">home</i></a></li>
                        <li><a class="waves-effect waves-light btn" href="logout">LogOut</a></li>
                    </ul>
                </div>
            </nav>

        </div>

        <h5 class="center">My Orders</h5>

        <%
            List<Orders> orders = (List<Orders>) request.getAttribute("orders");
            List<Painting> paintings = (List<Painting>) request.getAttribute("paintings");
            List<String> names = (List<String>) request.getAttribute("names");
            if (orders == null || paintings == null || names == null) {
            } else {
                Iterator<Orders> o = orders.iterator();
                Iterator<Painting> p = paintings.iterator();
                Iterator<String> n = names.iterator();
                if (o.hasNext() == false) {

        %>

        <br>

        <div class="container row ">
            <div class="col s12">
                <div class="card horizontal">
                    <p class="card-content" style="font-family: cursive; font-size: 25px; color: #ef5350;">
                        You have not placed any order yet!  <i class="material-icons">sentiment_very_dissatisfied</i>
                    </p>
                </div>
            </div>
        </div>

        <div class="center" style="min-height: 40vh;">
            <a href="/ArtGallery"><u>Continue Shopping</u></a>
        </div>
            <%
          
            } else {
        %>

        <div class="container row " >
            <div class="col s12 ">
                <div class="card">

                    <%
                        while (o.hasNext() && p.hasNext()) {
                    %>


                    <div class="row" style="padding: 20px">
                        <div class="col s12">
                            <div class="card horizontal">
                                <div class="card-image">
                                    <%Painting pn = p.next();%>
                                    <img class="materialboxed" src="<%= pn.getThumbnail_add()%>" style="height: 200px; width: 200px;">
                                </div>
                                <div class="card-stacked">
                                    <div class="card-content">
                                        <a href="downloadPainting?pid=<%= pn.getPainting_id()%>" id="download" title="Download Painting"><i class="material-icons right">file_download</i></a>
                                        <%Orders or = o.next();%>
                                        <p>Order Id: <%= or.getOrder_id()%></p><br>
                                        <p>Date of Order: <%= or.getOrder_date()%></p><br>
                                        <p>Price: <%= pn.getPrice()%> Rs/-</p>
                                    </div>
                                    <div class="card-action">
                                        <%String nm = n.next();%>
                                        <a href="artistPaint?uid=<%= pn.getUser_id() %>">By:- <%= nm%></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <%
                        }
                    %>
                </div>
            </div>
        </div>
        <%
                }
            }
        %>

        <%@include file="footer.jsp" %>
        
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script type="text/javascript" src="resources/js/myjs.js"></script>
       
        
    </body>
</html>
