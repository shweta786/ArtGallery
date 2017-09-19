<%@page import="java.util.Iterator"%>
<%@page import="com.art.model.Painting"%>
<%@page import="java.util.List"%>
<%@page import="com.art.model.Usr"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <title>Art Gallery</title>
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
                        <li><a class="waves-effect waves-light btn modal-trigger" href="#modal1">Sign In</a></li>
                        <li><a class="waves-effect waves-light btn modal-trigger" href="#modal2">Sign Up</a></li>
                    </ul>
                </div>
            </nav>

        </div>

        <div class="carousel carousel-slider" style="height: 420px">
            <a class="carousel-item"><img src="resources/Images/Modern-Art-Banner.jpg"></a>
            <a class="carousel-item"><img src="resources/Images/Fall Festival Email Header.png"></a>
            <a class="carousel-item"><img src="resources/Images/banner1.jpg"></a>
        </div>
        <div id="modal1" class="modal">
            <div class="modal-content">
                <button class="modal-close right" style="background-color: Transparent; border: none;"><i class="material-icons right">close</i></button>
                <h4>LogIn to Art Gallery</h4>
                <form id="signin" method="post" action="signin" class="col s12">
                    <div class="row">
                        <div class="input-field">
                            <input id="email" maxlength="490" type="email" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
                                   oninvalid="setCustomValidity('Please follow pattern like abc@pq.xyz(only 2 or 3 letter after .)')"
                                   onchange="try {
                                               setCustomValidity('');
                                           } catch (e) {
                                           }" required="required" >
                            <label for="email">Registered Email</label>
                        </div>
                        <div class="input-field">
                            <input id="password" maxlength="190" type="password" name="password" required="required">
                            <label for="password">Password</label>
                        </div>   
                        <a class="modal-trigger" href="" id="forgot"><u>Forgot Password?</u></a>
                        <br><br>
                        <button class="center col s12 btn waves-effect waves-light light-blue darken-1 z-depth-2" type="submit" name="action">Log In</button>
                        <br><br><br>
                        <a class="center col s12 modal-trigger" onclick="$('#modal1').modal('close');$('#modal2').modal('open');" href="#modal2"><u>New User? SignUp First</u></a>
                    </div>
                </form>
            </div>
        </div>

        <div id="modal2" class="modal">
            <div class="modal-content">
                <button class="modal-close right" style="background-color: Transparent; border: none;"><i class="material-icons right">close</i></button>
                <h4>Register to Art Gallery</h4>
                <form id="signup" method="post" action="save" class="col s12">
                    <div class="row">
                        <div class="input-field">
                            <input id="name" name="name" type="text" maxlength="190" required="required">
                            <label for="name">Full Name(max. 200 characters)</label>
                        </div>
                        <div class="input-field">
                            <input id="email" maxlength="490" name="email" type="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required="required"
                                   oninvalid="setCustomValidity('Please match format like abc@pq.xyz(only 2 or 3 letter after .)')"
                                   onchange="try {
                                               setCustomValidity('')
                                           } catch (e) {
                                           }">
                            <label for="email">Email</label>
                        </div>
                        <div class="input-field">
                            <input id="contact" name="contact" type="tel" pattern="[0-9]{10}" oninvalid="setCustomValidity('Enter valid mobile number of 10 digits')"
                                   onchange="try {
                                               setCustomValidity('')
                                           } catch (e) {
                                           }">
                            <label for="contact">Phone (optional)</label>
                        </div>
                        <div class="input-field">
                            <input id="password" maxlength="490" name="password" type="password" >
                            <label for="password">Password</label>
                        </div><br>
                        <div>
                            <input type="hidden" name="artist" value="checked">
                            <input type="checkbox" name="artist" id="artist" />
                            <label for="artist">Are you an Artist?</label>
                        </div>                       
                        <div class="center col s12">
                            <button class="btn waves-effect waves-light light-blue darken-1 z-depth-2" type="submit" name="action" style="margin-left: 30px">SignUp
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div id="modal3" class="modal">
            <div class="modal-content">
                <button class="modal-close right" style="background-color: Transparent; border: none;"><i class="material-icons right">close</i></button>
                <h4>Forgot Password</h4>
                <div class="row" id="gParent">
                    <div id="mParent" class="input-field">
                        <input id="mail" type="email" maxlength="490" name="mail" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
                               oninvalid="setCustomValidity('Please follow pattern like abc@pq.xyz(only 2 or 3 letter after .)')"
                               onchange="try {
                                           setCustomValidity('')
                                       } catch (e) {
                                       }" required="required" >
                        <label for="email">Registered Email Id</label>
                    </div><br>
                    <button id="forgotSubmit" class="center col s12 btn waves-effect waves-light light-blue darken-1 z-depth-2">Submit</button>
                </div>
            </div>
        </div>

        <br>
        <% List<Painting> paintings = (List<Painting>) request.getAttribute("paintings");
            List<String> names = (List<String>) request.getAttribute("names");
            if (names != null && paintings != null) {
                Iterator<String> n = names.iterator();
                Iterator<Painting> p = paintings.iterator();

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
        <div class="container row ">
            <div class="col s12 ">
                <div class="card">

                    <div class="row" style="padding: 20px" id="cart_parent">
                        <%
                            while (p.hasNext() && n.hasNext()) {
                                Painting paint = p.next();
                                String na = n.next();
                        %>
                        <div class="col s4">
                            <div class="card">
                                <div class="card-image">
                                    <img class="materialboxed"  src="<%= paint.getThumbnail_add()%>" style="height: 200px">
                                    <span class="card-title">Rs/- <%= paint.getPrice()%></span>
                                </div>

                                <div class="card-content">
                                    <p class="left">Title-&nbsp;&nbsp;&nbsp;<%= paint.getName()%></p><br>
                                    <p class="left">Category-&nbsp;&nbsp;&nbsp;<%= paint.getType()%></p><br>
                                    <p class="left">Size-&nbsp;&nbsp;&nbsp;<%= paint.getSze()%> px</p><br>
                                    <p class="left">By-&nbsp;&nbsp;&nbsp;<a href="artistPaint?uid=<%= paint.getUser_id()%>"><%= na.substring(0, 1).toUpperCase() + na.substring(1)%></a></p>
                                </div>
                                <div class="card-action" id="<%= paint.getPainting_id()%>">
                                    <a href="" class="crt"><i class="material-icons">add_shopping_cart</i>Add To Cart</a>
                                </div>
                            </div>
                        </div>
                        <%
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
        String popup = (String) request.getAttribute("popup");
        if (popup != null) {
    %>
    <script>
                                   Materialize.toast("<%= popup%>", 4000);
    </script>
    <%
    } else if ((String) request.getAttribute("usr") != null) {
    %>
    <script>
        Materialize.toast("<%= (String) request.getAttribute("usr")%>", 4000);
    </script>
    <%
        }
    %>
</body>
</html>
