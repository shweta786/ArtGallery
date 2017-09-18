<%-- 
    Document   : cart
    Created on : Sep 9, 2017, 4:21:53 PM
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
        <link rel="stylesheet" href="resources/css/mycss.css">
        <title>Cart</title>
    </head>
    <body bgcolor="#f5f5f5" >

        <div class="navbar-fixed">
            <nav>
                <div class="nav-wrapper blue darken-1">
                    <a class="brand-logo"><img src="resources/Images/logo2.jpg" height="70" style="margin-left: 10px"></a>
                    <a style="margin-left: 130px; font-family: cursive; font-size: 25px">Spread the ART</a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li><a href="myOrder">My Orders</a></li>
                        <li><a href="/ArtGallery">Home<i class="material-icons left">home</i></a></li>
                        <li><a class="waves-effect waves-light btn" href="logout">LogOut</a></li>
                    </ul>
                </div>
            </nav>
        </div>

        <h5 class="center">My Cart</h5>

        <% List<Orders> orders = (List<Orders>) request.getAttribute("orders");
            List<Painting> paintings = (List<Painting>) request.getAttribute("paintings");
            List<String> names = (List<String>) request.getAttribute("names");

            if (orders != null && paintings != null && names != null) {
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
                        Your cart is empty!  <i class="material-icons">sentiment_very_dissatisfied</i>
                    </p>
                </div>
            </div>
        </div>

        <div class="center"  style="min-height: 40vh;" >
            <a href="/ArtGallery"><u>Continue Shopping</u></a>
        </div>
        <%
        } else {
        %>

        <div style="display: flex;">

            <div class="container row" style="flex: 1" >
                <div class="card">
                    <%
                        int c = 0;
                        float price = 0;
                        while (o.hasNext() && p.hasNext()) {
                            c++;
                    %>

                    <%  Painting pn = p.next(); 
                        Orders or = o.next();
                        String nm = n.next();
                    %>
                    <div class="row" style="padding: 20px">
                        <div class="col s12">
                            <div class="card horizontal">
                                <div class="card-image">
                                    <img class="materialboxed" src="<%= pn.getThumbnail_add()%>" style="height: 200px; width: 200px;">
                                </div>
                                <div class="card-stacked">
                                    <div class="card-content">
                                        <a class="right" href="delete?id=<%= or.getOrder_id()%>" title="Remove from cart" ><i class="material-icons">delete</i></a>
                                        <p><b>Price:</b> <%= pn.getPrice()%> Rs/-</p>                            
                                    </div>
                                    <div class="card-action">
                                        <b>By Artist:</b> <a href="artistPaint?uid=<%= pn.getUser_id() %>"><%= nm%></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <%
                            price += Float.valueOf(pn.getPrice());
                        }
                    %>
                </div>
            </div>

            <div style="width: 420px;">
                <div class=" row ">
                    <div class="col s12">
                        <div class="card horizontal">
                            <div class="card-stacked">
                                <div class="card-content">
                                    <span class="card-title center">Price Details:</span>
                                    <p>Total Item:&nbsp;&nbsp;&nbsp;<b><%= c%></b> </p>
                                    <p>Total Price of items:&nbsp;&nbsp;&nbsp;<b><%= price%></b></p>
                                    <p>Shipping Charges: &nbsp;&nbsp;&nbsp;<b>Free</b></p>                                    
                                    <p class="line"></p>   <br>                                 
                                    <p>Amount to Pay:&nbsp;&nbsp;&nbsp;<b><%= price%></b></p>
                                </div>
                                <div class="card-action center">
                                    <a href="orderConfirm">Proceed to confirm order</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>      
        </div>
        <%
                }
            }
        %>

        <footer class="page-footer blue darken-1">
            <div class="footer-copyright blue darken-1">
                <div class="container">
                    © 2014 Copyright Text
                    <a class="grey-text text-lighten-4 right" href="#!">More Links</a>
                </div>
            </div>
        </footer>
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script type="text/javascript" src="resources/js/myjs.js"></script> 
    </body>
</html>
