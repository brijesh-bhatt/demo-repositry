package testingApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringController {
	
	@RequestMapping("/hello")
	public ModelAndView sayHello() {
		String message = "Welcome to Spring 4.0 !";  
		return new ModelAndView("hello", "message", message);  
	}
}
