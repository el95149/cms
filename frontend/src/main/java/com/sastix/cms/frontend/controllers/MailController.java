/**
 * 
 */
package com.sastix.cms.frontend.controllers;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sastix.cms.frontend.service.EmailService;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Controller
public class MailController {

	@Autowired
	private EmailService emailService;

	@ExceptionHandler({MailException.class})
	public ModelAndView messagingError(HttpServletRequest req, Exception ex) {
		ModelAndView mav = new ModelAndView();
	    mav.addObject("exception", ex);
	    mav.addObject("url", req.getRequestURL());
	    mav.addObject("errorMsg", "Failed to send E-Mail. Please retry.");
	    mav.setViewName("email");
	    return mav;
	}
	
	@RequestMapping(value = "/sendMail", method = RequestMethod.GET)
	public String showSendMail() {
		return "email";
	}

	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public String sendMail(@RequestParam("senderEmail") String senderEmail,
			@RequestParam("recipientEmail") String recipientEmail, @RequestParam("subject") String subject,
			@RequestParam("message") String message, final Locale locale, Model model) throws MessagingException {

		emailService.sendMail(senderEmail, recipientEmail, subject, message, locale);
		model.addAttribute("successMsg", "E-Mail sent!");
		return "email";

	}

}
