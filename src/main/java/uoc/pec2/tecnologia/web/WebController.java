package uoc.pec2.tecnologia.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@GetMapping("/")
	public String getApi() {
		return "redirect:swagger-ui.html";
	}
	
	@GetMapping("/home")
	public String getHome() {
		return "redirect:index.html";
	}
}
