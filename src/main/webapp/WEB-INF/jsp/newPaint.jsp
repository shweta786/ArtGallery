<%-- 
    Document   : newPaint
    Created on : Sep 12, 2017, 4:21:10 PM
    Author     : SHWETA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="resources/css/mycss.css">
        <title>Add Painting</title>
    </head>

    <body bgcolor="#f5f5f5">

        <div class="navbar-fixed">
            <nav>
                <div class="nav-wrapper blue darken-1">
                    <a class="brand-logo"><img src="resources/Images/logo2.jpg" height="70" style="margin-left: 10px"></a>
                    <a style="margin-left: 130px; font-family: cursive; font-size: 25px">Spread the ART</a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li><a href="/ArtGallery">Home<i class="material-icons left">home</i></a></li>
                        <li><a class="waves-effect waves-light btn" href="logout">LogOut</a></li>
                    </ul>
                </div>
            </nav>
        </div>
        <br><br>

        <div class="center">
            <h5>Add your Artwork</h5>
        </div>
        <br>

        <div class="container row">
            <div class="col s12">
                <div class="card">
                    <div class="card-content">
                        <span class="card-title"><b>New painting</b></span>
                        <form enctype="multipart/form-data" action="savePainting" method="post">

                            <div class="row">
                                <div class="input-field col s12">
                                    <input id="first_name" type="text" maxlength="100" name="name" required>
                                    <label for="name">Title/Name(max 100 characters) *</label>
                                </div>
                            </div>

                            <div class="row">
                                <div class="input-field col s12">
                                    <input id="price" type="text" name="price" pattern="^\d{0,8}(\.\d{1,4})?$" oninvalid="setCustomValidity('Enter valid price(max 8 digits before decimal and max 4 after decimal)')"
                                   onchange="try {
                                               setCustomValidity('')
                                           } catch (e) {
                                           }" required>
                                    <label for="price">Price(in Rs/-) *</label>
                                </div>
                            </div>

                            <div class="row">
                                <div class="input-field col s12">
                                    <input id="size" type="text" name="sze" pattern="?<=^| )\d+(\*\d+)?(?=$|" oninvalid="setCustomValidity('Enter valid dimensions like 150*120')"
                                   onchange="try {
                                               setCustomValidity('');
                                           } catch (e) {
                                           }">
                                    <label for="size">size in pixels(width*height)</label>
                                </div>
                            </div>

                            <div class="row">
                                <div class="input-field col s12">
                                    <select name="type" required>
                                        <option value="" disabled selected>Choose your option</option>
                                        <option value="1">Nature</option>
                                        <option value="2">Spiritual</option>
                                        <option value="3">Vehicle</option>
                                        <option value="4">Animal</option>
                                        <option value="5">Sports</option>
                                        <option value="6">Celebration</option>
                                        <option value="7">Travel and World</option>
                                        <option value="8">Animation</option>
                                        <option value="9">Other</option>
                                    </select>
                                    <label>Type of Painting *</label>
                                </div>
                            </div>

                            <input type="file" style="display: none" id="uplp" name="file" required="required">
                            <center><button type="submit" id="savePaint" style="display: none">Save<i class="material-icons right">save</i></button></center>
                        </form>
                    </div>

                    <div class="card-action">                            
                        <a href="" id="uploadPaint">Upload Painting<i class="material-icons left">file_upload</i></a>
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
