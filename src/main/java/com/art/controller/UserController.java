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
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author SHWETA
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PaintingService paintingService;

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getAllPainting(HttpServletRequest request) {
        ModelAndView model;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            model = new ModelAndView("index");
        } else {
            int type = (int) session.getAttribute("type");
            if (type == 0) {
                model = new ModelAndView("userProfile");
            } else {
                model = new ModelAndView("artistProfile");
            }
        }
        try {
            List<Painting> painting = paintingService.getPainting();
            List<String> names = new ArrayList<>();
            for (Painting p : painting) {
                names.add(userService.getUserById(p.getUser_id()).getName());
            }
            model.addObject("paintings", painting);
            model.addObject("names", names);
        } catch (Exception e) {
        }
        return model;
    }

//    @RequestMapping(value = "/home", method = RequestMethod.GET)
//    public JsonDTO byajax(HttpServletRequest request) {
//       
//        List<Painting> paintings = paintingService.getPainting();
//        List<String> names = new ArrayList<>();
//        for(Painting p:paintings) {
//            names.add(userService.getUserById(p.getUser_id()).getName());
//        }
//        JsonDTO result = new JsonDTO();
//        result.setNames(names);
//        result.setPaintings(paintings);
//        result.setStatus("successfull");
//        return result;
//    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView userLogout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute Usr usr, @RequestParam("artist") String[] value, HttpServletRequest request) {
        ModelAndView model;
        String hashed_pass = BCrypt.hashpw(usr.getPassword(), BCrypt.gensalt());
        usr.setPassword(hashed_pass);
        if (value.length == 2) {
            model = new ModelAndView("artistProfile");
            usr.setPic("resources/Images/profile.jpg");
            usr.setType(1);
        } else {
            model = new ModelAndView("userProfile");
            usr.setType(0);
        }

        userService.addUser(usr);
        HttpSession session = request.getSession();
        session.setAttribute("email", usr.getEmail());
        session.setAttribute("user_id", usr.getUser_id());
        session.setAttribute("type", usr.getType());
        model.addObject("msg", "Welcome " + usr.getName().split(" ", 2)[0].substring(0, 1).toUpperCase() + usr.getName().split(" ", 2)[0].substring(1));
        List<Painting> painting = paintingService.getPainting();
        List<String> names = new ArrayList<>();
        for (Painting p : painting) {
            names.add(userService.getUserById(p.getUser_id()).getName());
        }
        model.addObject("paintings", painting);
        model.addObject("names", names);
        return model;
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public ModelAndView getSave() {
        return new ModelAndView("accessDenied");
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ModelAndView userIn(HttpServletRequest request) {
        ModelAndView model;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Usr usr = userService.findUser(email, password);
        if (usr == null) {
            model = new ModelAndView("index");
            model.addObject("usr", "User doesn't exist");
        } else {
            String pass = usr.getPassword();
            if (BCrypt.checkpw(password, pass)) {

                HttpSession session = request.getSession(false);

                if (session == null || session.getAttribute("email") == null) {
                    session = request.getSession();
                    session.setAttribute("email", usr.getEmail());
                    session.setAttribute("user_id", usr.getUser_id());
                    session.setAttribute("type", usr.getType());
                }
                if (usr.getType() == 0) {
                    model = new ModelAndView("userProfile");
                } else {
                    model = new ModelAndView("artistProfile");
                }

                model.addObject("usr", usr);
                model.addObject("msg", "Welcome " + usr.getName().split(" ", 2)[0].substring(0, 1).toUpperCase() + usr.getName().split(" ", 2)[0].substring(1));

            } else {
                model = new ModelAndView("index");
                model.addObject("usr", "Wrong Password");
            }
        }
        List<Painting> painting = paintingService.getPainting();
        List<String> names = new ArrayList<>();
        for (Painting p : painting) {
            names.add(userService.getUserById(p.getUser_id()).getName());
        }
        model.addObject("paintings", painting);
        model.addObject("names", names);
        return model;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ModelAndView userGetIn() {
        return new ModelAndView("accessDenied");
    }

    @RequestMapping(value = "/artist", method = RequestMethod.GET)
    public ModelAndView getListReader() {
        ModelAndView model = new ModelAndView("showArtist");
        List<Usr> usr = userService.getUser();
        model.addObject("users", usr);
        return model;
    }

    @RequestMapping(value = "/myOrder", method = RequestMethod.GET)
    public ModelAndView orders(HttpServletRequest request) {
        ModelAndView model;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            model = new ModelAndView("redirect:/");
        } else {
            model = new ModelAndView("order");
            List<Orders> orders = orderService.getOrderByUser((int) session.getAttribute("user_id"), 1);
            List<Painting> paintings = new ArrayList<>();
            List<String> names = new ArrayList<>();
            for (Orders o : orders) {
                paintings.add(paintingService.getPaintingById(o.getPainting_id()));
            }
            for (Painting p : paintings) {
                names.add((userService.getUserById(p.getUser_id())).getName());
            }
            model.addObject("names", names);
            model.addObject("orders", orders);
            model.addObject("paintings", paintings);
        }
        return model;
    }

    @RequestMapping(value = "/myCart", method = RequestMethod.GET)
    public ModelAndView cart(HttpServletRequest request) {
        ModelAndView model;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            model = new ModelAndView("redirect:/");
        } else {
            model = new ModelAndView("cart");
            List<Orders> orders = orderService.getOrderByUser((int) session.getAttribute("user_id"), 0);
            List<Painting> paintings = new ArrayList<>();
            for (Orders o : orders) {
                paintings.add(paintingService.getPaintingById(o.getPainting_id()));
            }
            List<String> names = new ArrayList<>();
            for (Painting p : paintings) {
                names.add(userService.getUserById(p.getUser_id()).getName());
            }
            model.addObject("names", names);
            model.addObject("orders", orders);
            model.addObject("paintings", paintings);
        }
        return model;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView remove(HttpServletRequest request) {
        ModelAndView model;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            model = new ModelAndView("redirect:/");
        } else {
            orderService.deleteOrder(Integer.parseInt(request.getParameter("id")), 0);
            return new ModelAndView("redirect:/myCart");
        }
        return model;
    }

    @RequestMapping(value = "/artistPaint", method = RequestMethod.GET)
    public ModelAndView artistProfile(HttpServletRequest request) {

        ModelAndView model = new ModelAndView("artist");
        Usr usr;
        List<Painting> paintings;
        
        if (request.getParameter("uid") != null && request.getParameter("uid") != "") {
            usr = userService.getUserById(Integer.parseInt(request.getParameter("uid")));
            paintings = paintingService.getPaintingByUser(Integer.parseInt(request.getParameter("uid")));
            model.addObject("paintings", paintings);
            model.addObject("usr", usr);
        } else {
            return new ModelAndView("redirect:/");
        }
        
        if (usr == null || usr.getType() == 0) {
            return new ModelAndView("redirect:/");
        }
        return model;
    }

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

    @RequestMapping(value = "/saveDes", method = RequestMethod.GET)
    public ModelAndView saveGet() {
        return new ModelAndView("accessDenied");
    }

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

    @RequestMapping(value = "/picUpload", method = RequestMethod.GET)
    public ModelAndView picGet() {
        return new ModelAndView("accessDenied");
    }

    @RequestMapping(value = "/addPaint", method = RequestMethod.GET)
    public ModelAndView giveForm(HttpServletRequest request) {

        ModelAndView model;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null || (int) session.getAttribute("type") == 0) {
            model = new ModelAndView("redirect:/");
        } else {
            model = new ModelAndView("newPaint");
        }
        return model;
    }

    @RequestMapping(value = "/savePainting", method = RequestMethod.POST)
    public ModelAndView saveP(@ModelAttribute Painting painting, @RequestParam("file") CommonsMultipartFile file, HttpSession session) {
        String path = "C:/Users/SHWETA/Desktop/upload/images";
        String fileName = UUID.randomUUID().toString();
        String filename = file.getOriginalFilename();
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                    new File(path + File.separator + fileName + filename)));
            stream.write(bytes);
            stream.flush();
            stream.close();

            File destinationDir = new File("C:/Users/SHWETA/Desktop/upload/thumbnail");
            Thumbnails.of(path + File.separator + fileName + filename)
                    .size(200, 200)
                    .toFiles(destinationDir, Rename.PREFIX_DOT_THUMBNAIL);

            int typ = Integer.parseInt(painting.getType());
            if (typ == 1) {
                painting.setType("nature");
            }
            if (typ == 2) {
                painting.setType("spiritual");
            }
            if (typ == 3) {
                painting.setType("vehicle");
            }
            if (typ == 4) {
                painting.setType("animal");
            }
            if (typ == 5) {
                painting.setType("sports");
            }
            if (typ == 6) {
                painting.setType("celebration");
            }
            if (typ == 7) {
                painting.setType("travel and world");
            }
            if (typ == 8) {
                painting.setType("animation");
            }
            if (typ == 9) {
                painting.setType("other");
            }

            String picToSave = "upload/images/" + fileName + filename;
            String PicThumbnail = "upload/thumbnail/thumbnail." + fileName + filename;
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String dt = dateFormat.format(date);

            painting.setPainting_add(picToSave);
            painting.setThumbnail_add(PicThumbnail);
            painting.setDt(dt);
            painting.setUser_id((int) session.getAttribute("user_id"));
            paintingService.addPainting(painting);

        } catch (Exception ex) {

        }
//        ModelAndView.setViewName("redirect:welcome");
//        redir.addFlashAttribute("USERNAME",uname);
//        return modelAndView;

        return new ModelAndView("redirect:/artistPaint");
    }

    @RequestMapping(value = "/savePainting", method = RequestMethod.GET)
    public ModelAndView paintGet() {
        return new ModelAndView("accessDenied");
    }

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

    @RequestMapping(value = "/addCart", method = RequestMethod.GET)
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

    @RequestMapping(value = "/orderConfirm", method = RequestMethod.GET)
    public ModelAndView sendMail(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user_id") != null) {
            List<Orders> orders = orderService.getOrderByUser((int) session.getAttribute("user_id"), 0);
            orderService.confirmOrder(orders);

            List<String> filenames = new ArrayList<>();
            String to = (String) session.getAttribute("email");
            final String username = "galleryart1010";
            final String password = "mindfire";
            String host = "smtp.gmail.com";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.user", "galleryart1010@gmail.com");
            props.put("mail.password", password);
            // Get the Session object.
            Session sess = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            for (Orders o : orders) {
                Painting p = paintingService.getPaintingById(o.getPainting_id());
                filenames.add(p.getPainting_add());
            }
            MailUtil.sendAttachmentEmail(sess, to, "Your Order", "Find the attachement for your paintings that you have ordered. Come back soon.", filenames);

            return new ModelAndView("redirect:/myOrder");

        } else {
            return new ModelAndView("redirect:/");
        }

    }

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
                final String username = "galleryart1010";
                final String password = "mindfire";
                String host = "smtp.gmail.com";
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.port", "587");
                props.put("mail.user", "galleryart1010@gmail.com");
                props.put("mail.password", password);
                // Get the Session object.
                Session sess = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
                MailUtil.sendAttachmentEmail(sess, mail, "Forgot Password", "Here is your 6 digit confidential code for verification " + n + ". Enter it and create a new password", null);
                result.setMessage("code sent");
                result.setStatus(Integer.toString(n));
            }
        }
        return result;
    }

    @RequestMapping(value = "/forgotMail", method = RequestMethod.GET)
    public ModelAndView forgetGet() {
        return new ModelAndView("accessDenied");
    }

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
