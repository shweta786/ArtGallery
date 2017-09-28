<%-- 
    Document   : showArtist
    Created on : Sep 6, 2017, 1:36:19 PM
    Author     : SHWETA
--%>

<%@page import="java.util.List"%>
<%@page import="com.art.model.Usr"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <title>Artist List</title>
    </head>
    <body>
        <div class="navbar-fixed">
            <nav>
                <div class="nav-wrapper blue darken-1">
                    <a class="brand-logo"><img src="resources/Images/logo2.jpg" height="70" style="margin-left: 10px"></a>
                    <a style="margin-left: 130px; font-family: cursive; font-size: 25px">Spread the ART</a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li><a href="/ArtGallery" ><i class="material-icons right">home</i></a></li>
                    </ul>
                </div>
            </nav>
        </div>
        <div class="carousel carousel-slider" style="height: 400px">
            <a class="carousel-item"><img src="resources/Images/banner3.jpg"></a>
            <a class="carousel-item"><img src="resources/Images/banner4.jpg"></a>
            <a class="carousel-item"><img src="resources/Images/artist1.jpg"></a>
        </div>
        <br>
        <div class="center">
            <h4>Artists</h4>
        </div>
        <% List<Usr> usr = (List<Usr>) request.getAttribute("users");
            int c = 0; %>

        <div class="container row " >
            <div class="col s12 ">
                <div class="card">

                    <div class="row" style="padding: 20px">
                        <% for (Usr u : usr) {
                                c++;
                                if (c % 5 == 0) {
                        %>
                    </div><div class="row" style="padding: 20px">
                        <%
                            }
                        %>
                        <div class="col s3">
                            <div class="card">
                                <div class="card-image">
                                    <img class="materialboxed" style="height: 220px" src="<%= u.getPic() %>" style="height: 200px;">
                                </div>
                                <div class="card-action">
                                    <a href="artistPaint?uid=<%= u.getUser_id() %>"><%= u.getName() %></a>
                                </div>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>

                </div>
            </div>
        </div>

        <%@include file="footer.jsp" %>
        
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script type="text/javascript" src="resources/js/myjs.js"></script>
    </body>
</html>
