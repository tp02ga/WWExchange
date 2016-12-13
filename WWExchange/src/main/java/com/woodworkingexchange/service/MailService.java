package com.woodworkingexchange.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.woodworkingexchange.command.ContactForm;
import com.woodworkingexchange.domain.Listing;
import com.woodworkingexchange.domain.Users;

@Service("mailService")
public class MailService
{
  public void sendResetPasswordEmail(String recipientEmail, String recipientName, String uri)
  {
    // uri = uri.substring(0, uri.indexOf("forgotPassword"));
    // uri += "passwordReset.htm?email="+recipientEmail;
    // HtmlEmail emailTemplate = new HtmlEmail();
    // emailTemplate.setHostName("smtpout.secureserver.net");
    // emailTemplate.setSmtpPort(465);
    // emailTemplate.setSSL(true);
    // emailTemplate.setAuthentication("tpage@ecosim.ca", "Yw27L,+@66<?18WP");
    // emailTemplate.addTo(recipientEmail, recipientName);
    // emailTemplate.setFrom("tpage@ecosim.ca", "Trevor Page");
    // emailTemplate.setSubject("Forgotten Password");
    // emailTemplate.setHtmlMsg("<html>You have indicated that you forgot your
    // password. Please <a href='"+uri+"'>click here</a> to reset your
    // password.<br/>" +
    // "We will send you a follow up email with your new password
    // information.</html>");
    // emailTemplate.send();
  }

  public void sendNewPasswordEmail(Users user, String newPassword)
  {
    // HtmlEmail emailTemplate = new HtmlEmail();
    // emailTemplate.setHostName("smtpout.secureserver.net");
    // emailTemplate.setSmtpPort(465);
    // emailTemplate.setSSL(true);
    // emailTemplate.setAuthentication("tpage@ecosim.ca", "Yw27L,+@66<?18WP");
    // emailTemplate.addTo(user.getEmail(), user.getName());
    // emailTemplate.setFrom("tpage@ecosim.ca", "Trevor Page");
    // emailTemplate.setSubject("Forgotten Password");
    // emailTemplate.setHtmlMsg("<html>Your new password is:
    // "+newPassword+"</html>");
    // emailTemplate.send();
  }

  public void sendMessage(String toEmail, String message, String fromEmail, String fromName, String product)
  {
    // HtmlEmail emailTemplate = new HtmlEmail();
    // if (message != null)
    // {
    // message = message.replace("\n", "<br/>");
    // }
    // emailTemplate.setHostName("smtpout.secureserver.net");
    // emailTemplate.setSmtpPort(465);
    // emailTemplate.setSSL(true);
    // emailTemplate.setAuthentication("tpage@ecosim.ca", "Yw27L,+@66<?18WP");
    // emailTemplate.addTo(toEmail);
    // emailTemplate.setFrom("tpage@ecosim.ca", "Trevor Page");
    // emailTemplate.setSubject("Message from Propective Buyer Re: " + product);
    // emailTemplate.setHtmlMsg("Here's the message you received from a
    // propective buyer:<br/><br/>"+message+"<br/><br/>To respond, please send
    // an email to: <a href='mailto:" + toEmail
    // +"?subject="+product+"'>"+toEmail+"</a><br/><br/>PLEASE DO NOT RESPOND TO
    // THIS EMAIL.");
    // emailTemplate.send();
  }

  public static void sendContactMessage(ContactForm contactForm)
  {
    // HtmlEmail emailTemplate = new HtmlEmail();
    //
    // if (contactForm.getMessage() != null)
    // {
    // contactForm.setMessage(contactForm.getMessage().replace("\n", "<br/>"));
    // }
    //
    // emailTemplate.setHostName("smtpout.secureserver.net");
    // emailTemplate.setSmtpPort(465);
    // emailTemplate.setSSL(true);
    // emailTemplate.setAuthentication("tpage@ecosim.ca", "Yw27L,+@66<?18WP");
    // emailTemplate.addTo("tpage@ecosim.ca");
    // emailTemplate.setFrom("tpage@ecosim.ca", "Trevor Page");
    // emailTemplate.setSubject("Message from Woodworking Exchange");
    // emailTemplate.setHtmlMsg(contactForm.getMessage());
    // emailTemplate.send();
  }

  public void sendRenewalEmail(Map<Users, List<Listing>> usersToEmail)
  {
    // SimpleDateFormat format = new SimpleDateFormat("MMM dd");
    // for (Map.Entry<Users, List<Listing>> entry : usersToEmail.entrySet())
    // {
    // String toEmail = entry.getKey().getEmail();
    // HtmlEmail emailTemplate = new HtmlEmail();
    //
    // emailTemplate.setHostName("smtpout.secureserver.net");
    // emailTemplate.setSmtpPort(465);
    // emailTemplate.setSSL(true);
    // emailTemplate.setAuthentication("tpage@ecosim.ca", "Yw27L,+@66<?18WP");
    // emailTemplate.addTo(toEmail);
    // emailTemplate.setFrom("tpage@ecosim.ca", "Trevor Page");
    // emailTemplate.setSubject("Renew your Woodworking Exchange Listings");
    // String message = "You currently have the following listing" +
    // (entry.getValue().size() > 1 ? "s eligible for renewal:<br/><br/>" : "
    // eligible for renewal:<br/><br/>");
    // for (Listing listing : entry.getValue())
    // {
    // message += listing.getManufacturer() + " - " + listing.getMachineType() +
    // " - listed " + format.format(listing.getDateAdded()) + "<br/>";
    // }
    //
    // message += "<br/>Please visit <a
    // href='http://ecosim.ca/WoodworkingExchange/listings/myListings.htm'>woodworkingexchange.com</a>
    // to renew your listing.";
    //
    // emailTemplate.setHtmlMsg(message);
    // emailTemplate.send();
    // }

  }
}
