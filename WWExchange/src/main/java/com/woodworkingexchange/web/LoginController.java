package com.woodworkingexchange.web;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.woodworkingexchange.domain.Address;
import com.woodworkingexchange.domain.Authorities;
import com.woodworkingexchange.domain.Users;
import com.woodworkingexchange.repositories.UserRepository;
import com.woodworkingexchange.service.AddressService;
import com.woodworkingexchange.service.MailService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class LoginController
{
  @Autowired
  private AddressService addressService;
  @Autowired
  private UserRepository userDao;
  @Autowired
  MailService mailService;
  Logger log = LoggerFactory.getLogger(getClass());
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @RequestMapping(value="/login", method=RequestMethod.GET)
  public String handleLogin(ModelMap model, HttpServletRequest request)
  {
    Users user = new Users();
    model.put("user", user);
    return "login/login";
  }
  
  @RequestMapping(value="login/forgotPassword", method=RequestMethod.POST)
  public @ResponseBody ModelMap handleLoginPost(@RequestParam(value="a") String email, ModelMap model, HttpServletRequest request) throws Exception
  {
    Users user = userDao.findUsersByEmail(email);
    mailService.sendResetPasswordEmail(email, user.getName(), request.getRequestURL().toString());
    model.put("success", true);
    return model;
  }
  
  @RequestMapping(value="login/passwordReset", method=RequestMethod.GET)
  public String resetPassword (ModelMap model, HttpServletRequest request) throws Exception
  {
    String email = request.getParameter("email");
    Users user = userDao.findUsersByEmail(email);
    userDao.resetPassword(user.getId());
    String newPassword = userDao.findOne(user.getId()).getPassword();
    mailService.sendNewPasswordEmail(user, newPassword);
    model.put("passwordReset", true);
    model.put("user", user);
    return "login/login";
  }
  
  @RequestMapping(value="login/register", method=RequestMethod.GET)
  public String handleRegistration(ModelMap model, HttpServletRequest request)
  {
    Users user = new Users();
    Address address = new Address();
    user.setAddress(address);
    model.put("user", user);
    model.put("provinces", addressService.getProvinces());
    model.put("countries", addressService.getCountries());
    return "login/register";
  }

  @RequestMapping(value="login/registerSuccess", method=RequestMethod.GET)
  public String registerSuccess ()
  {
    return "login/registerSuccess";
  }
  
  @RequestMapping(value="login/register", method=RequestMethod.POST)
  public String handleRegistrationPost(@ModelAttribute("User") Users user, ModelMap model, HttpServletRequest request)
  {
    Set<Authorities> authSet = new HashSet<Authorities>();
    Authorities auth = new Authorities();
    auth.setAuthority("ROLE_USER");
    auth.setUsers(user);
    authSet.add(auth);
    user.setAuthorities(authSet);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    
    user.getAddress().setUser(user);
    
    userDao.save(user);
    return "redirect:/login/registerSuccess";
  }
  
  @ExceptionHandler(Exception.class)
  public ModelAndView handleAllExceptions(Exception e,
      HttpServletRequest request)
  {
    ModelAndView model = new ModelAndView("error");
    model.addObject("error", e);
    log.error("Error in ListingController", e);
    return model;
  }
  
}
