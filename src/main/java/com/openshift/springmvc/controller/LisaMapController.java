package com.openshift.springmvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openshift.springmvc.model.Album;
import com.openshift.springmvc.model.BlogMessage;
import com.openshift.springmvc.model.Country;
import com.openshift.springmvc.model.Photo;
import com.openshift.springmvc.model.UploadItem;
import com.openshift.springmvc.model.User;
import com.openshift.springmvc.service.AlbumManager;
import com.openshift.springmvc.service.BlogMessageManager;
import com.openshift.springmvc.service.CountryManager;
import com.openshift.springmvc.service.PhotoManager;
import com.openshift.springmvc.service.UserManager;

@Controller
public class LisaMapController {
    
    private User authenticatedUser;
    private int visitUserId;
    
    private AlbumManager albumManager = new AlbumManager();
    private PhotoManager photoManager = new PhotoManager();
    private BlogMessageManager blogMessageManager = new BlogMessageManager();
    private UserManager userManager = new UserManager();
    private CountryManager countryManager = new CountryManager();
    
    @Autowired
    @Qualifier("albumValidator")
    private Validator albumValidator;
    
    @Autowired
    @Qualifier("uploadValidator")
    private Validator uploadValidator;
    
    @Autowired
    @Qualifier("signupValidator")
    private Validator signupValidator;
    
    @Autowired
    @Qualifier("loginValidator")
    private Validator loginValidator;
    
    @InitBinder(value="albumItem")
    protected void initAlbumBinder(final WebDataBinder binder) {
        binder.addValidators(albumValidator);
    }
    
    @InitBinder(value="uploadItem")
    protected void initPhotoBinder(final WebDataBinder binder) {
        binder.addValidators(uploadValidator);
    }
    
    @InitBinder(value="userSignup")
    protected void initUserSignupBinder(final WebDataBinder binder) {
        binder.addValidators(signupValidator);
    }
    
    @InitBinder(value="userLogin")
    protected void initUserLoginBinder(final WebDataBinder binder) {
        binder.addValidators(loginValidator);
    }
    
    // TODO: view photo by map, default USA
    @RequestMapping(value="/map", method=RequestMethod.GET)
    public final String myMap() {
        // Redirect to login page if not authenticated yet
        if(authenticatedUser == null) {
            return "redirect:/login";
        }
        return "map";
    }
    
    @RequestMapping(value="/photo/album", method=RequestMethod.GET)
    public final String myAlbum(final Model model) {
        // Redirect to login page if not authenticated yet
        if(authenticatedUser == null) {
            return "redirect:/login";
        }
        
        // Get albums from database
        final List<Album> albumList = albumManager.getPhotoAlbumsByUserId(authenticatedUser.getUserid());
        final List<Country> countryList = countryManager.listAllCountries();
        
        if(!model.containsAttribute("albumItem")) {
            model.addAttribute("albumItem", new Album());
        }
        model.addAttribute("albumList", albumList);
        model.addAttribute("countryList", countryList);
        return "album";
    }
    
    @RequestMapping(value="/photo/create-album", method=RequestMethod.POST)
    public final String createAlbum(@ModelAttribute("albumItem") @Validated final Album albumItem, final BindingResult result, final RedirectAttributes reAttr) {
        // Validate
        if (result.hasErrors()) {
            reAttr.addFlashAttribute("org.springframework.validation.BindingResult.albumItem", result);
            reAttr.addFlashAttribute("albumItem", albumItem);
            return "redirect:/photo/album";
        }
        
        // Save album to database
        // TODO: handle unique(albumname, userid) constraint
        albumItem.setType(Album.AlbumType.PHOTO.getTypeValue());
        albumItem.setSize(0);
        albumItem.setUserid(authenticatedUser.getUserid());
        albumManager.createAlbum(albumItem);
        
        // Redirect to view my albums
        return "redirect:/photo/album";
    }
    
    @RequestMapping(value="/photo/album/{albumId}", method=RequestMethod.GET)
    public final String myPhoto(@PathVariable("albumId") final int albumId, final Model model) {
        // Get all photos in album from database
        // TODO: only owner can view...?
        final List<Photo> photoList = photoManager.getPhotosByAlbumId(albumId);
        
        // Generate photo url list
        final List<String> photoUrls = new ArrayList<>(); 
        for(final Photo photo : photoList) {
            final StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("data:image/jpeg;base64,");
            strBuilder.append(DatatypeConverter.printBase64Binary(photo.getImage()));
            photoUrls.add(strBuilder.toString());
        }
        
        if (!model.containsAttribute("uploadItem")) {
            model.addAttribute("uploadItem", new UploadItem());
        }
        model.addAttribute("photoUrls", photoUrls);
        return "photo";
    }
    
    @RequestMapping(value="/photo/album/{albumId}/save-photo", method=RequestMethod.POST)
    public final String uploadPhoto(@PathVariable("albumId") final int albumId, @ModelAttribute("uploadItem") @Validated final UploadItem uploadItem, final BindingResult result, final RedirectAttributes reAttr) throws IOException {
        // Validate
        if (result.hasErrors()) {
            reAttr.addFlashAttribute("org.springframework.validation.BindingResult.uploadItem", result);
            reAttr.addFlashAttribute("uploadItem", uploadItem);
            return ("redirect:/photo/album/" + albumId);
        }
        
        // Create photo objects from uploaded item
        final List<Photo> photosToSave = new ArrayList<>();
        for(final MultipartFile file : uploadItem.getFiles()) {
            final Photo photo = new Photo();
            photo.setAlbumId(albumId);
            photo.setPhotoName(uploadItem.getName());
            photo.setDescription(uploadItem.getDescription());
            photo.setSavedTime(new Date());
            photo.setImage(file.getBytes());
            photosToSave.add(photo);
        }
        
        // Save uploaded photos to database
        for(final Photo eachPhoto : photosToSave) {
            photoManager.createPhoto(eachPhoto);
        }
        
        // Get album size from database
        final Album album = albumManager.getAlbumById(albumId);
        if(album != null) {
            final int currSize = album.getSize();
            final int incSize = photosToSave.size();
            // Update album size in database
            album.setSize(currSize + incSize);
            albumManager.updateAlbum(album);
            // Update album size by HQL
            //     final Session dbSession = SessionFactoryUtil.getSessionFactory().openSession();
            //     final Transaction tx= dbSession.beginTransaction();
            //     final String hqlUpdate = "update Album set size = :newSize where albumId = :albumId";
            //     dbSession.createQuery(hqlUpdate).setInteger("newSize", currSize + incSize).setInteger("albumId", albumId).executeUpdate();
            //     tx.commit();
            //     dbSession.close();
        }
        
        return ("redirect:/photo/album/" + albumId);
    }
    
    @RequestMapping(value="/blog", method=RequestMethod.GET)
    public final String myBlog() {
        // Redirect to login page if not authenticated yet
        if(authenticatedUser == null) {
            return "redirect:/login";
        }
        // Redirect to my blog
        visitUserId = authenticatedUser.getUserid();
        return ("redirect:/blog/" + visitUserId);
    }
    
    @RequestMapping(value="/blog/{userId}", method=RequestMethod.GET)
    public final String userBlog(@PathVariable("userId") int userId, final Model model) {
        // Redirect to login page if not authenticated yet
        if(authenticatedUser == null) {
            return "redirect:/login";
        }
        
        // Get all blog messages to user from database
        // TODO: print well formated messages for front end
        // TODO: edit and delete message
        final List<BlogMessage> messageList = blogMessageManager.getBlogMessagesByUserIdTo(userId);
        final StringBuilder messagesBuilder = new StringBuilder();
        for(final BlogMessage msg : messageList) {
            messagesBuilder.append("Subject: ").append(msg.getSubject()).append("<br>");
            messagesBuilder.append("Body: ").append(msg.getBody()).append("<br>");
            
            // Get userNameFrom by userIdFrom from database for each message
            final User userFrom = userManager.getUserById(msg.getUserIdFrom());
            final String usernameFrom = userFrom.getUsername();
            
            messagesBuilder.append("Posted by ").append(usernameFrom).append(" at ").append(msg.getSavedTime()).append("<br>");
            messagesBuilder.append("<br>");
        }
        
        if(!model.containsAttribute("blogMessage")) {
            model.addAttribute("blogMessage", new BlogMessage());
        }
        model.addAttribute("messages", messagesBuilder.toString());
        return "blog";
    }
    
    @RequestMapping(value="/blog/add-message", method=RequestMethod.POST)
    public final String addBlogMessage(@ModelAttribute("blogMessage") final BlogMessage blogMessage, final BindingResult result) {
        // Validate
        // if(result.hasErrors()) {
        //     // TODO
        // }
        
        // Save blog message to database
        final int fromUserId = (authenticatedUser == null)? 0 : authenticatedUser.getUserid();
        blogMessage.setUserIdFrom(fromUserId);
        blogMessage.setUserIdTo(visitUserId);
        blogMessage.setSavedTime(new Date());
        blogMessageManager.createBlogMessage(blogMessage);
        
        // Redirect to visit user blog
        return ("redirect:/blog/" + visitUserId);
    }
    
    @RequestMapping(value="/blog/search", method=RequestMethod.GET)
    public final String searchBlog(final Model model) {
        // Redirect to login page if not authenticated yet
        if(authenticatedUser == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("findUser", new User());
        return "searchUser";
    }
    
    @RequestMapping(value="/blog/search-user", method=RequestMethod.POST)
    public final String searchUser(final Model model, @ModelAttribute("findUser") final User findUser, final BindingResult result) {
        // Validate
        // TODO
        
        // Search user data from database.
        // TODO: support search by either criterion
        final User foundUser = userManager.getUserByUsernameEmail(findUser.getUsername(), findUser.getEmail());
        final StringBuilder resultBuilder = new StringBuilder();
        if(foundUser == null) {
            resultBuilder.append("Result not found.<br>");
            model.addAttribute("findUser", new User());
            model.addAttribute("searchResult", resultBuilder.toString());
            return "searchUser";
        } else {
            // Redirect to found user's blog.
            visitUserId = foundUser.getUserid();
            return ("redirect:/blog/" + visitUserId);
        }
    }
    
    @RequestMapping(value="/about", method=RequestMethod.GET)
    public final String aboutUs(final Model model) {
        String message = "<h3>********** We built a zoo! **********</h3>";
        model.addAttribute("message", message);
        return "about";
    }
    
    @RequestMapping(value="/signup", method=RequestMethod.GET)
    public final String signUp(final Model model) {
        if (!model.containsAttribute("userSignup")) {
            model.addAttribute("userSignup", new User());
        }
        return "signup";
    }
    
    // TODO: user authentication with Spring Security
    @RequestMapping(value="/create-user", method=RequestMethod.POST)
    public final String createUser(final HttpSession clientSession, @ModelAttribute("userSignup") @Validated final User userSignup, final BindingResult result, final RedirectAttributes reAttr) {
        // Validate
        if (result.hasErrors()) {
            reAttr.addFlashAttribute("org.springframework.validation.BindingResult.userSignup", result);
            reAttr.addFlashAttribute("userSignup", userSignup);
            return "redirect:/signup";
        }
        
        // Save signed up user to database
        // TODO: handle username, email already exist
        userManager.createUser(userSignup);
        authenticatedUser = userSignup;
        clientSession.setAttribute("username", authenticatedUser.getUsername());
        return "redirect:/";
    }
    
    // TODO: my profile & database schema
    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public final String myProfile() {
        return null;
    }
    
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public final String logIn(final Model model) {
        if (!model.containsAttribute("userLogin")) {
            model.addAttribute("userLogin", new User());
        }
        return "login";
    }
    
    // TODO: user authentication with Spring Security
    @RequestMapping(value="/auth-user", method=RequestMethod.POST)
    public final String authenticateUser(final HttpSession clientSession, @ModelAttribute("userLogin") @Validated final User userLogin, final BindingResult result, final RedirectAttributes reAttr) {
        // Validate
        if (result.hasErrors()) {
            reAttr.addFlashAttribute("org.springframework.validation.BindingResult.userLogin", result);
            reAttr.addFlashAttribute("userLogin", userLogin);
            return "redirect:/login";
        }
        
        // Check username password match in database
        authenticatedUser = userManager.authenticateUser(userLogin.getUsername(), userLogin.getPassword());
        if(authenticatedUser != null) {
            // authentication success
            clientSession.setAttribute("username", authenticatedUser.getUsername());
            clientSession.setAttribute("userid", authenticatedUser.getUserid());
            return "redirect:/";
        } else {
            // username & password not correct.
            return "redirect:/login";
        }
    }
    
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public final String logOut(final HttpSession clientSession) {
        authenticatedUser = null;
        clientSession.setAttribute("username", null);
        clientSession.setAttribute("userid", null);
        return "redirect:/";
    }
    
}