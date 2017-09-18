<%-- 
    Document   : artist
    Created on : Sep 10, 2017, 12:44:32 AM
    Author     : SHWETA
--%>

<%@page import="com.art.model.Usr"%>
<%@page import="com.art.model.Painting"%>
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
        <title>Artist</title>
    </head>

    <body bgcolor="#f5f5f5">

        <div class="navbar-fixed">
            <nav>
                <div class="nav-wrapper blue darken-1">
                    <a class="brand-logo"><img src="resources/Images/logo2.jpg" height="70" style="margin-left: 10px"></a>
                    <a style="margin-left: 130px; font-family: cursive; font-size: 25px">Spread the ART</a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li><a href="/ArtGallery">Home<i class="material-icons left">home</i></a></li>
                        <%
                                HttpSession sess = request.getSession(false);
                                if (sess != null && sess.getAttribute("email") != null) {
                            
                        %>
                        <li><a class="waves-effect waves-light btn" href="logout">LogOut</a></li>
                        <%
                        }
                        %>
                    </ul>
                </div>
            </nav>
        </div>

        <%
            List<Painting> paintings = (List<Painting>) request.getAttribute("paintings");
            Usr usr = (Usr) request.getAttribute("usr");
            int c = 0;
        %>

        <div class="container row">
            <div class="col s12">
                <h4 class="header center"><%= usr.getName().substring(0, 1).toUpperCase() + usr.getName().substring(1)%></h4>
                <div class="card horizontal">

                    <div  class="card-image" style="padding: 20px">
                        <div class="card z-depth-3">  
                            <img class="materialboxed" id="image" src=" <%= usr.getPic()%> ">
                                
                                <form method="post" enctype="multipart/form-data" id="formId">  
                                    <input type="file" style="display: none" id="upl" name="files">
                                    <a id="upload" name="upload" style="display: none" title="Upload Profile Picture" class="btn-floating halfway-fab z-depth-5"><i class="material-icons">file_upload</i></a>
                                </form>
                                
                        </div>
                   </div>                    
                    <div class="card-stacked">
                        <div class="card-content">                           
                            <p><h5>About me:</h5><p id="inv"><font color="#9e9e9e"><%= usr.getDescription()%></font></p>
                            <textarea class="materialize-textarea" maxlength="999" id="des" name="des" style="display:none;"><%= usr.getDescription()%></textarea><br><br>
                            <p><h5>Email-id:<input type="text" value="<%= usr.getEmail()%>" id="email" name="email" style="border-bottom-color: white" readonly disabled>                                                              
                            <p><h5>Contact Number:<input type="text" value="<%= usr.getContact()%>" id="contact" name="contact" style="border-bottom-color: white" pattern="[0-9]{10}" oninvalid="setCustomValidity('Enter valid mobile number of 10 digits')"
                                   onchange="try {
                                               setCustomValidity('')
                                           } catch (e) {
                                           }" disabled>
                        </div>
                    </div>
                    <a style="display: none" id="safe" class="btn-floating halfway-fab z-depth-3" title="Save Details"><i class="material-icons">save</i></a>     

                </div>
            </div>
        </div>
        <%
            if (session != null && session.getAttribute("user_id") != null && (int) session.getAttribute("user_id") == usr.getUser_id()) {
        %>
        <div class="fixed-action-btn" style="bottom: 500px ; right: 30px">
            <a id="edit" class="btn-floating halfway-fab" title="Edit Profile"><i class="material-icons">edit</i></a>
        </div>

        <div class="fixed-action-btn" style="bottom: 50px ; right: 30px">
            <a id="add" href="addPaint" class="btn-floating halfway-fab z-depth-3" title="Add Painting"><i class="material-icons">add</i></a>     
        </div>

        <%
            }
        %>

        <br>
        <div class="center">
            <h5>My PAINTINGS</h5>
        </div>

        <div class="container row " >
            <div class="col s12 ">
                <div class="card">

                    <div class="row" style="padding: 20px" id="cart_parent">
                        <% if (paintings != null) {
                            for (Painting paint : paintings) {
                                c++;
                                if (c % 4 == 0) {
                            } else {
                        %>
                        <div class="col s4" id="<%= paint.getPainting_id()%>">
                            <div class="card">
                                <div class="card-image">
                                    <img class="materialboxed" src="<%= paint.getThumbnail_add()%>" style="height: 200px;">
                                    <%
                                        if (session != null && session.getAttribute("user_id") != null && (int) session.getAttribute("user_id") == usr.getUser_id()) {
                                    %>
                                    <a id="delP" class="deleteViewRow btn-floating halfway-fab z-depth-3 " title="Remove"><i class="material-icons">delete</i></a>     
                                    <%
                                        }
                                    %>
                                    <span class="card-title">Rs/- <%= paint.getPrice()%></span>
                                </div>

                                <div class="card-content">
                                    <p class="left">Title-&nbsp;&nbsp;&nbsp;<%= paint.getName()%></p><br>
                                    <p class="left">Category-&nbsp;&nbsp;&nbsp;<%= paint.getType()%></p><br>
                                    <p class="left">Size-&nbsp;&nbsp;&nbsp;<%= paint.getSze()%> px</p><br>
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
     
    </body>
</html>
