/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.art.controller;

import com.art.dto.JsonDTO;
import com.art.dto.UserJsonDTO;
import com.art.model.Orders;
import com.art.model.Painting;
import com.art.model.Usr;
import com.art.service.OrderService;
import com.art.service.PaintingService;
import com.art.service.UserService;
import com.art.util.MailUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PaintingService paintingService;

    @Autowired
    OrderService orderService;

    @Autowired
    MailUtil mailUtil;
    

    /**
     * 
     * @param usr(Usr object)
     * @param type(type of user- artist or regular)
     * @param request
     * @return Json data holding status, message and Usr object after the user is saved in database
     */
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public UserJsonDTO save(Usr usr, String type, HttpServletRequest request) {
        
        UserJsonDTO result = new UserJsonDTO();
        Usr user = userService.findUser(usr.getEmail(), "");                    //checking whether the email is already in db or not
        
        if(user != null){
           result.setMessage("This email id is already registered with us");
           return result;
        } else {
            String hashed_pass = BCrypt.hashpw(usr.getPassword(), BCrypt.gensalt());      //encrypting password for saving it in db
            usr.setPassword(hashed_pass);
            if (type.equals("1")) {
                result.setMessage("artist");
                usr.setPic("resources/Images/profile.jpg");
                usr.setType(1);
            } else {
                result.setMessage("user");
                usr.setType(0);
            }

            userService.addUser(usr);
            HttpSession session = request.getSession();                 //creating session for new user
            session.setAttribute("email", usr.getEmail());
            session.setAttribute("user_id", usr.getUser_id());
            session.setAttribute("type", usr.getType());
            result.setUsr(usr);
            result.setStatus("Welcome " + usr.getName().split(" ", 2)[0].substring(0, 1).toUpperCase() + usr.getName().split(" ", 2)[0].substring(1)); // welcome msg for first letter of user name as Capital
        } 
        return result;
    }

    //Method for angular UI
    @RequestMapping(value = { "/api/save"}, method = RequestMethod.POST)
    public UserJsonDTO saveAngular(@RequestBody Usr usr,HttpServletRequest request) {
        
        String type = Integer.toString(usr.getType());
        UserJsonDTO result = new UserJsonDTO();
        Usr user = userService.findUser(usr.getEmail(), "");                    //checking whether the email is already in db or not
        
        if(user != null){
           result.setMessage("This email id is already registered with us");
           return result;
        } else {
            String hashed_pass = BCrypt.hashpw(usr.getPassword(), BCrypt.gensalt());      //encrypting password for saving it in db
            usr.setPassword(hashed_pass);
            if (type.equals("1")) {
                result.setMessage("artist");
                usr.setPic("resources/Images/profile.jpg");
                usr.setType(1);
            } else {
                result.setMessage("user");
                usr.setType(0);
            }

            userService.addUser(usr);
            HttpSession session = request.getSession();                 //creating session for new user
            session.setAttribute("email", usr.getEmail());
            session.setAttribute("user_id", usr.getUser_id());
            session.setAttribute("type", usr.getType());
            result.setUsr(usr);
            result.setStatus("Welcome " + usr.getName().split(" ", 2)[0].substring(0, 1).toUpperCase() + usr.getName().split(" ", 2)[0].substring(1)); // welcome msg for first letter of user name as Capital
        } 
        return result;
    }
    
    //method for angular UI
    @RequestMapping(value = "/api/artist", method = RequestMethod.GET)
    public JsonDTO artistProfile(Integer uid) {

        List<Painting> paintings = null;
        JsonDTO result = new JsonDTO();
        Usr usr = null;
        if (!(Integer.toString(uid)).equals("") && uid != null) {
            usr = userService.getUserById(uid);
            if(usr != null) {           
                paintings = paintingService.getPaintingByUser(usr.getUser_id());
            }
            result.setPaintings(paintings);
            result.setUsr(usr);
            result.setMsg("ok");
        } 

        if (usr == null || usr.getType() == 0) {
            result.setMsg("no");
        }
        return result;
    }
    
    @RequestMapping(value = "/api/allArtist", method = RequestMethod.GET)
    public JsonDTO getArtistList() {
        List<Usr> usr = userService.getUser();             //getting all the data of artist
        JsonDTO result =  new JsonDTO();
        result.setUsrs(usr);
        return result;
    }
    
    @RequestMapping(value = "/api/myCart", method = RequestMethod.GET)
    public JsonDTO cart(HttpServletRequest request) {
        JsonDTO result = new JsonDTO();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            result.setMsg("no");
        } else {
            result.setMsg("yes");
            List<Orders> orders = orderService.getOrderByUser((int) session.getAttribute("user_id"), 0);
            List<Painting> paintings = new ArrayList<>();
            for (Orders o : orders) {
                paintings.add(paintingService.getPaintingById(o.getPainting_id()));
            }
            List<String> names = new ArrayList<>();
            for (Painting p : paintings) {
                names.add(userService.getUserById(p.getUser_id()).getName());
            }
            result.setNames(names);
            result.setOrders(orders);
            result.setPaintings(paintings);
        }
        return result;
    }
    
    @RequestMapping(value = "/api/delete", method = RequestMethod.GET)
    public UserJsonDTO remove(int pid, HttpServletRequest request) {
        UserJsonDTO result = new UserJsonDTO();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            result.setMessage("no");
        } else {
            result.setMessage("ok");
            orderService.deleteOrder(pid, 0);
        }
        return result;
    }
    
    @RequestMapping(value = "/api/orderConfirm", method = RequestMethod.GET)
    public UserJsonDTO sendMailUsr(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        UserJsonDTO result = new UserJsonDTO();
        if (session != null && session.getAttribute("user_id") != null) {
            List<Orders> orders = orderService.getOrderByUser((int) session.getAttribute("user_id"), 0);
            orderService.confirmOrder(orders);

            List<String> filenames = new ArrayList<>();
            String to = (String) session.getAttribute("email");
            for (Orders o : orders) {
                Painting p = paintingService.getPaintingById(o.getPainting_id());
                paintingService.changePopularity(o.getPainting_id());
                filenames.add(p.getPainting_add());
            }
            Session sess = mailUtil.mailSess();
            mailUtil.sendAttachmentEmail(sess, to, "Your Order", "Find the attachement for your paintings that you have ordered. Come back soon.", filenames);
            result.setMessage("ok");
        } else {
            result.setMessage("no");
        }
        return result;
    }
    /**
     * 
     * @param email
     * @param password
     * @param request
     * @return Json data holding status, message and Usr object after user is signed in
     */
    @RequestMapping(value = {"/signin", "/api/signin"}, method = RequestMethod.GET)
    public UserJsonDTO userIn(String email, String password, HttpServletRequest request) {

        UserJsonDTO result = new UserJsonDTO();
        Usr usr = userService.findUser(email, password); //finding user in database
        if (usr == null) {
            result.setMessage("User doesn't exist");
        } else {
            String pass = usr.getPassword();
            if (BCrypt.checkpw(password, pass)) {

                HttpSession session = request.getSession(false);

                if (session == null || session.getAttribute("email") == null) {  //creating session for user if it exists
                    session = request.getSession();
                    session.setAttribute("email", usr.getEmail());
                    session.setAttribute("user_id", usr.getUser_id());
                    session.setAttribute("type", usr.getType());
                }
                if (usr.getType() == 0) {
                    result.setMessage("user");
                } else {
                    result.setMessage("artist");
                }

                result.setUsr(usr);
                result.setStatus("Welcome " + usr.getName().split(" ", 2)[0].substring(0, 1).toUpperCase() + usr.getName().split(" ", 2)[0].substring(1));

            } else {
                result.setMessage("Wrong Password");
            }
        }
        return result;
    }

    /**
     * 
     * @param usr
     * @param session
     * @return UserJsonDTO object consisting of status,message and Usr object, after saving description in database
     */
    @RequestMapping(value = "/saveDes", method = RequestMethod.POST)
    public UserJsonDTO save(@ModelAttribute("usr") Usr usr, HttpSession session) {
        usr.setUser_id((int) session.getAttribute("user_id"));
        Usr user = userService.editArtist(usr.getUser_id(), usr.getDescription(), usr.getContact());
        UserJsonDTO result = new UserJsonDTO();
        result.setStatus("202");
        result.setMessage("Saved Successfully");
        result.setUsr(user);
        return result;
    }

    /**
     * 
     * @param usr
     * @param files
     * @param session
     * @return UserJsonDTO object consisting of status,message and Usr object, after saving artist's profile picture
     */
    @RequestMapping(value = "/picUpload", method = RequestMethod.POST)
    public UserJsonDTO picSave(@ModelAttribute("usr") Usr usr, @RequestParam("files") MultipartFile files, HttpSession session) {

        String path = "C:/Users/SHWETA/Desktop/upload/images";
        String fileName = UUID.randomUUID().toString();
        String filename = files.getOriginalFilename();
        UserJsonDTO result = new UserJsonDTO();
        try {
            byte[] bytes = files.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                    new File(path + File.separator + fileName + filename)));
            stream.write(bytes);
            stream.flush();
            stream.close();
            String picToSave = "upload/images/" + fileName + filename;
            Usr user = userService.editPic((int) session.getAttribute("user_id"), picToSave);
            result.setStatus("202");
            result.setMessage("Successfully Updated pic");
            result.setUsr(user);
        } catch (FileNotFoundException ex) {
            result.setMessage("Choose a valid file");
        } catch (IOException ex) {
            result.setMessage("Error Ocuured, try again");
        }
        return result;
    }

    /**
     * 
     * @param pid
     * @param request
     * @return UserJsonDTO object after removing painting
     */
    @RequestMapping(value = "/delPaint", method = RequestMethod.GET)
    public UserJsonDTO deletePainting(String pid, HttpServletRequest request) {
        UserJsonDTO result = new UserJsonDTO();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            result.setMessage("invalid");
        } else {
            paintingService.deletePainting(Integer.parseInt(pid));
            result.setMessage("Successfully Removed");
        }
        return result;
    }

    /**
     * 
     * @param paint_id
     * @param request
     * @return DTO object after adding item in cart of the user
     */
    @RequestMapping(value = {"/addCart", "/api/addCart"}, method = RequestMethod.GET)
    public UserJsonDTO addToCart(String paint_id, HttpServletRequest request) {
        UserJsonDTO result = new UserJsonDTO();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            result.setMessage("SignIn to add item");
        } else {
            List<Orders> orders = orderService.getOrderByUser((int) session.getAttribute("user_id"), 0);
            for (Orders o : orders) {
                if (o.getPainting_id() == Integer.parseInt(paint_id)) {
                    result.setMessage("Already in your cart");
                    return result;
                }
            }
            Orders order = new Orders();

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String dt = dateFormat.format(date);

            order.setPainting_id(Integer.parseInt(paint_id));
            order.setUser_id((int) session.getAttribute("user_id"));
            order.setType(0);
            order.setOrder_date(dt);
            orderService.addOrder(order);
            result.setMessage("Added to your cart");
        }
        return result;
    }

    /**
     * 
     * @param type
     * @param request
     * @return data after selecting painting of some type
     */
    @RequestMapping(value = "/selectPaint", method = RequestMethod.GET)
    public JsonDTO paintByAjax(String type, HttpServletRequest request) {

        List<Painting> paintings = paintingService.getPaintingByType(type);
        List<String> names = new ArrayList<>();
        for (Painting p : paintings) {
            names.add(userService.getUserById(p.getUser_id()).getName());
        }
        JsonDTO result = new JsonDTO();
        if (names.isEmpty()) {
            result.setMsg("Nothing to Show for this type");
        } else {
            result.setMsg("got");
        }
        result.setNames(names);
        result.setPaintings(paintings);
        result.setStatus("successfull");
        return result;
    }
    
    //This method is for angular application
    @RequestMapping("/api/allPainting")
    public JsonDTO getAllPainting(HttpServletRequest request) {
        
            List<Painting> painting = paintingService.getPainting();
            List<String> names = new ArrayList<>();
            for (Painting p : painting) {
                names.add(userService.getUserById(p.getUser_id()).getName());       // adding names of artist who uploaded that painting
            }
            JsonDTO result = new JsonDTO();
            result.setNames(names);
            result.setPaintings(painting);
            result.setStatus("successfull");
        return result;
    }
    @RequestMapping(value = "/api/ifSession", method = RequestMethod.GET)
    public UserJsonDTO ifSession(HttpServletRequest request) {
       UserJsonDTO result = new UserJsonDTO();
      HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            result.setStatus("invalid");
        } else {
            result.setStatus("valid");
        }
        return result;
    }
    @RequestMapping(value ={"/api/logout"}, method = RequestMethod.GET)
    public UserJsonDTO logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return new UserJsonDTO();
    }

    /**
     * 
     * @param criteria
     * @param request
     * @return data after sorting them in particular order
     */
    @RequestMapping(value = {"/api/sortPaint","/sortPaint"}, method = RequestMethod.GET)
    public JsonDTO sortPaintByAjax(String criteria, HttpServletRequest request) {

        List<Painting> paintings = paintingService.getPainting();
        if (criteria.equals("Price")) {
            Collections.sort(paintings, Painting.PaintingPrice);
        }
        if (criteria.equals("Popularity")) {
            Collections.sort(paintings, Painting.PaintingPopularity);
        }
        if (criteria.equals("Time")) {
            Collections.sort(paintings, Painting.paintingDate);
        }
        List<String> names = new ArrayList<>();
        for (Painting p : paintings) {
            names.add(userService.getUserById(p.getUser_id()).getName());
        }
        JsonDTO result = new JsonDTO();
        if (names.isEmpty()) {
            result.setMsg("Nothing to Show");
        } else {
            result.setMsg("got");
        }
        result.setNames(names);
        result.setPaintings(paintings);
        result.setStatus("successfull");
        return result;
    }

    /**
     * 
     * @param mail
     * @param request
     * @return data after sending mail for with OTP for forgot password.
     */
    @RequestMapping(value = "/forgotMail", method = RequestMethod.POST)
    public UserJsonDTO forgotMailPassword(String mail, HttpServletRequest request) {

        UserJsonDTO result = new UserJsonDTO();

        if (mail == null) {
            result.setMessage("Email id cannot be null");
        } else {
            Usr user = userService.findUser(mail, "");
            if (user == null) {
                result.setMessage("User does not exist");
            } else {
                int n = (int) (100000 + new Random().nextDouble() * 900000);
                Session sess = mailUtil.mailSess();
                mailUtil.sendAttachmentEmail(sess, mail, "Forgot Password", "Here is your 6 digit confidential code for verification " + n + ". Enter it and create a new password", null);
                result.setMessage("code sent");
                result.setStatus(Integer.toString(n));
            }
        }
        return result;
    }

    /**
     * 
     * @param email
     * @param pass
     * @param request
     * @return data after changing password in database
     */
    @RequestMapping(value = "/setNewPass", method = RequestMethod.POST)
    public UserJsonDTO SetPassword(String email, String pass, HttpServletRequest request) {

        UserJsonDTO result = new UserJsonDTO();
        String ePass = BCrypt.hashpw(pass, BCrypt.gensalt());
        if (pass == null || pass.equals("")) {
            result.setStatus("Error occured");
        } else {
            userService.changePass(email, ePass);
            result.setStatus("Password changed successfully");
        }
        return result;
    }

    @RequestMapping(value = "/setNewPass", method = RequestMethod.GET)
    public ModelAndView setNewGet() {
        return new ModelAndView("accessDenied");
    }

}
