package testingApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccav.dao.UserDAO;
import com.ccav.form.User;

@Controller
public class SpringController {
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping("/signin")
	public ModelAndView sayHello() {
		String message = "Welcome to Spring 4.0 !";  
		return new ModelAndView("login", "user", new User());  
	}
	
	@RequestMapping("/verify")
	public ModelAndView verify(@ModelAttribute("user") User user, ModelMap map) {
		System.out.println("User Details: "+user.toString());
		boolean isValid = userDAO.verifyUser(user);
		System.out.println("isValid: " + isValid);
		return new ModelAndView("success", "user", user);  
	}
}
