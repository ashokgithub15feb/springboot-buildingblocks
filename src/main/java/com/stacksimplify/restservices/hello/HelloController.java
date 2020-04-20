package com.stacksimplify.restservices.hello;

import java.util.Locale;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Controller
@RestController
public class HelloController {

	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	//Simple method
	//URI - helloworld
	//GET
	
	//@RequestMapping(method = RequestMethod.GET, path = "helloworld")
	@GetMapping("/helloworld1")
	public String helloWorld()
	{
		return "Hello World";
	}
	
	//user details 
	@GetMapping("/helloworldbean")
	public UserDetails helloWorldBean()
	{
		return new UserDetails("Ajit", "H", "Bangalore");
	}
	
	@GetMapping("/hello-int")
	public String getMessageInI18NFormat(@RequestHeader(name = "Accept-Language", required = false) String local)
	{
		return messageSource.getMessage("lable.hallo", null, new Locale(local));
	}
	
	@GetMapping("/hello-int2")
	public String getMessageInI18NFormatTwo()
	{
		return messageSource.getMessage("lable.hallo", null, LocaleContextHolder.getLocale());
	}
}
