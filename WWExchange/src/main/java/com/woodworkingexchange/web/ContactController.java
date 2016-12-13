package com.woodworkingexchange.web;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.woodworkingexchange.command.ContactForm;
import com.woodworkingexchange.service.MailService;

@Controller
public class ContactController
{

  @RequestMapping(value="/contact", method=RequestMethod.GET)
  public String showContactPage (ModelMap model)
  {
    ContactForm form = new ContactForm();
    model.put("contactForm", form);
    
    return "contact";
  }
  
  @RequestMapping(value="/contact", method=RequestMethod.POST)
  public String contactPost (@ModelAttribute("contactForm") ContactForm form, ModelMap model) throws UnsupportedEncodingException
  {
    String message = "Name: " + form.getFirstName() + "<br/>Email: " + form.getEmail() + "<br/><br/>" + form.getMessage();
    form.setMessage(message);
    MailService.sendContactMessage(form);
    model.put("success", "Your email was successfully sent.");
    form = new ContactForm();
    model.put("contactForm", form);
    return "contact";
  }
}
