<%-- 
    Document   : userProfile
    Created on : Sep 7, 2017, 12:15:11 PM
    Author     : SHWETA
--%>
<%@page import="java.util.Iterator"%>
<%@page import="com.art.model.Painting"%>
<%@page import="java.util.List"%>
<%@page import="com.art.model.Usr"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <title>Profile</title>
    </head>
    <body bgcolor="#f5f5f5">
        <div class="navbar-fixed">
            <ul id="dropdown1" class="dropdown-content"  class="center" >
                <li style="padding: 10px 0px 0px 70px;" class="orange-text">nature</li>
                <li style="padding: 10px 0px 0px 70px;" class="orange-text">spiritual</li>
                <li style="padding: 10px 0px 0px 70px;" class="orange-text">vehicle</li>
                <li style="padding: 10px 0px 0px 70px;" class="orange-text">animal</li>
                <li style="padding: 10px 0px 0px 70px;" class="orange-text">sports</li>
                <li style="padding: 10px 0px 0px 60px;" class="orange-text">celebration</li>
                <li style="padding: 10px 0px 0px 50px;" class="orange-text">travel and world</li>
                <li style="padding: 10px 0px 0px 70px;" class="orange-text">animation</li>
                <li style="padding: 10px 0px 0px 70px;" class="orange-text">other</li>
            </ul>

            <nav>
                <div class="nav-wrapper blue darken-1">
                    <a class="brand-logo"><img src="resources/Images/logo2.jpg" height="70" style="margin-left: 10px"></a>
                    <a style="margin-left: 130px; font-family: cursive; font-size: 25px">Spread the ART</a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li><a href="artist" >Select By Artist</a></li>
                        <li><a class="dropdown-button" data-activates="dropdown1">Select By Category
                                <i class="material-icons right">arrow_drop_down</i></a></li>
                                <li><a href="myCart"><i class="fa fa-shopping-cart right" aria-hidden="true"></i>&nbsp; My Cart</a></li>
                                <li><a href="myOrder"> My Orders</a></li>
                                <li><a class="waves-effect waves-light btn" href="logout">LogOut</a></li>
                    </ul>
                </div>
            </nav>

        </div>

        <div class="carousel carousel-slider" style="height: 425px">
            <a class="carousel-item"><img src="resources/Images/Modern-Art-Banner.jpg"></a>
            <a class="carousel-item"><img src="resources/Images/Fall Festival Email Header.png"></a>
            <a class="carousel-item"><img src="resources/Images/banner1.jpg"></a>
        </div>
        
        <br>
        <% List<Painting> paintings = (List<Painting>) request.getAttribute("paintings");
            List<String> names = (List<String>) request.getAttribute("names");
            if (names != null && paintings != null) {
                Iterator<String> n = names.iterator();
                int c = 0;
        %> 
        
        <ul id="dropdown2" class="dropdown-content">
            <li style="padding: 10px 0px 0px 30px;" class="orange-text">Price</li>
            <li style="padding: 10px 0px 0px 30px;" class="orange-text">Time</li>
            <li style="padding: 10px 0px 0px 20px;" class="orange-text">Popularity</li>
            <li style="padding: 10px 0px 0px 30px;" class="orange-text">Size</li>
        </ul>
        <br>
        
        <div class="row container">
            <div class="col s6">
                <h5 id="headings" class="right">PAINTINGS</h5>
            </div>
            <div class="col s6" style="padding-top: 8px">
                <a href="" class="dropdown-button" data-activates="dropdown2" style="font-size: 20px">Sort By    
                <i class="material-icons" style=" padding-right: 40px;">arrow_drop_down</i>
            </a>
            </div>
        </div>
        <div class="container row " >
            <div class="col s12 ">
                <div class="card">

                    <div class="row" style="padding: 20px" id="cart_parent">
                        <% for (Painting paint : paintings) {
                                c++;
                                String na = n.next();
                                if (c % 4 == 0) {
                                    
                            } else {
                        %>
                        <div class="col s4">
                            <div class="card">
                                <div class="card-image">
                                    <img class="materialboxed" src="<%= paint.getThumbnail_add()%>" style="height: 200px">
                                    <span class="card-title">Rs/- <%= paint.getPrice() %></span>
                                </div>
                                <div class="card-content">
                                    <p class="left">Title-&nbsp;&nbsp;&nbsp;<%= paint.getName()%></p><br>
                                    <p class="left">Category-&nbsp;&nbsp;&nbsp;<%= paint.getType()%></p><br>
                                    <p class="left">Size-&nbsp;&nbsp;&nbsp;<%= paint.getSze()%> px</p><br>
                                    <p class="left">By-&nbsp;&nbsp;&nbsp;<a href="artistPaint?uid=<%= paint.getUser_id()%>"><%= na%></a></p>
                                </div>
                                <div class="card-action" id="<%= paint.getPainting_id()%>">
                                    <a href="" class="crt"><i class="material-icons">add_shopping_cart</i> Add To Cart</a>
                                </div>
                            </div>
                        </div>
                        <%
                                }
                            }
                        }
                        %>
                    </div>



                </div>
            </div>
        </div>

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
        <%
            String msg = (String)request.getAttribute("msg");
            if (msg != null) {
        %>
        <script>
            Materialize.toast("<%= msg%>", 4000);
        </script>
        <%
            }
        %>
    </body>
</html>
