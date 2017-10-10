/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.art.controller;

import com.art.model.Orders;
import com.art.model.Painting;
import com.art.model.Usr;
import com.art.service.OrderService;
import com.art.service.PaintingService;
import com.art.service.UserService;
import com.art.util.MailUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.mail.Session;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author SHWETA
 */
@Controller
public class ViewController {
    
    @Autowired
    UserService userService;

    @Autowired
    PaintingService paintingService;
    
    @Autowired
    OrderService orderService;

    @Autowired
    ServletContext context;
    
    @Autowired
    MailUtil mailUtil;
    
    /**
     * 
     * 
     * @param request
     * @return index page with all painting and their artist details
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getAllPainting(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("index");
        try {
            List<Painting> painting = paintingService.getPainting();
            
            //
            List<String> names = new ArrayList<>();
            for (Painting p : painting) {
                names.add(userService.getUserById(p.getUser_id()).getName());       // adding names of artist who uploaded that painting
            }
            model.addObject("paintings", painting);
            model.addObject("names", names);
        } catch (Exception e) {
        }
        return model;
    }
    
     /**
     * 
     * @param request
     * @return view of index page after invalidating current session for user
     */
    @RequestMapping(value ={"/logout"}, method = RequestMethod.GET)
    public ModelAndView userLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return new ModelAndView("redirect:/");
    }
        
    /**
     * 
     * @return showArtist view having list of all the artists
     */
    @RequestMapping(value = "/artist", method = RequestMethod.GET)
    public ModelAndView getListReader() {
        ModelAndView model = new ModelAndView("showArtist");
        List<Usr> usr = userService.getUser();              //getting all the data of user
        model.addObject("users", usr);
        return model;
    }
    
    /**
     * 
     * @param request
     * @return view of all orders of a user.
     */
    @RequestMapping(value = "/myOrder", method = RequestMethod.GET)
    public ModelAndView orders(HttpServletRequest request) {
        ModelAndView model;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            model = new ModelAndView("redirect:/");
        } else {
            model = new ModelAndView("order");
            List<Orders> orders = orderService.getOrderByUser((int) session.getAttribute("user_id"), 1);    //fetching paintings which are not removed by its artist
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

    
    /**
     * 
     * @param request
     * @return cart view of a particular user
     */
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

    
    /**
     * 
     * @param request
     * @return cart view after deleting an item from user cart
     */
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

    /**
     * 
     * @param request
     * @returns profile of an artist
     */
    @RequestMapping(value = "/artistPaint", method = RequestMethod.GET)
    public ModelAndView artistProfile(HttpServletRequest request) {

        ModelAndView model = new ModelAndView("artist");
        Usr usr;
        List<Painting> paintings;

        if (request.getParameter("uid") != null && !request.getParameter("uid").equals("")) {
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
    
    /**
     * 
     * @param request
     * @return view of form for adding painting by artist
     */
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

    /**
     * 
     * @param painting
     * @param file
     * @param session
     * @return artist profile after saving painting that he/she has uploaded
     */
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
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String dt = dateFormat.format(date);

            painting.setPainting_add(picToSave);
            painting.setThumbnail_add(PicThumbnail);
            painting.setDt(dt);
            painting.setUser_id((int) session.getAttribute("user_id"));
            paintingService.addPainting(painting);
            return new ModelAndView("redirect:/artistPaint?uid="+session.getAttribute("user_id"));
        } catch (Exception ex) {
            return new ModelAndView("newPaint");
        }

    }
    
    /**
     * 
     * @param request
     * @return order success page after confirming order and sending mail of user.
     */
    @RequestMapping(value = "/orderConfirm", method = RequestMethod.GET)
    public ModelAndView sendMail(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
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

            return new ModelAndView("orderSuccess");
        } else {
            return new ModelAndView("redirect:/");
        }

    }

    /**
     * 
     * @param request
     * @param response 
     */
    @RequestMapping(value = "/downloadPainting", method = RequestMethod.GET)
    public void downloadP(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user_id") != null && request.getParameter("pid") != null && !request.getParameter("pid").equals("")) {

            Painting p = paintingService.getPaintingById(Integer.parseInt(request.getParameter("pid")));
            try {
                String filePath = "C:/Users/SHWETA/Desktop/" + p.getPainting_add();
                File file = new File(filePath);
                FileInputStream fin = new FileInputStream(file);
                String mimeType = context.getMimeType(filePath);
                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }
                response.setContentType(mimeType);
                response.setContentLength((int) file.length());

                //forces download
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
                response.setHeader(headerKey, headerValue);

                OutputStream outStream = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int byteRead = -1;
                while ((byteRead = fin.read(buffer)) != -1) {
                    outStream.write(buffer, 0, byteRead);
                }
                fin.close();
                outStream.close();
            } catch (Exception ex) {
                System.out.println("Exception " + ex);
            }
        }

    }



}
