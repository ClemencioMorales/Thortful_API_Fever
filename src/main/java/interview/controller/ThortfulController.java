package interview.controller;

import interview.service.AuthenticationService;
import interview.service.CitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * REST controller for returning some cities with login and logout.
 *
 */
@RestController
public class ThortfulController implements ErrorController{

	private static final String ERROR_PATH = "/error";
	public static final String IS_USER_LOGGED_IN = "isUserLoggedIn";

	@Autowired
	private HttpSession httpSession;
	@Autowired
	CitiesService citiesService;
	@Autowired
	AuthenticationService authenticationService;

	@RequestMapping(value = "/login/{user}/{password}", method = RequestMethod.GET)
	public String login(@PathVariable(value = "user") String user,
						@PathVariable(value = "password") String password) {
		String result = authenticationService.authenticate(user, password);
		String message = null;
		if(result != null){
			this.httpSession.setAttribute(IS_USER_LOGGED_IN, true);
			message = "Welcome "+user+"! Session created at " +new Date();
		} else {
			message = "User/Password incorrect...";
		}
		return message;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		String message = null;
		boolean isUserLoggedIn = false;

		if(this.httpSession.getAttribute(IS_USER_LOGGED_IN) != null){
			isUserLoggedIn = (boolean) this.httpSession.getAttribute(IS_USER_LOGGED_IN);
		}
		if (!isUserLoggedIn){
			message = "You have to initiate your session for logging out...";
		} else {
			message = "You have closed your session, bye!";
			this.httpSession.setAttribute(IS_USER_LOGGED_IN, false);
		}
		return message;
	}

	@RequestMapping(value = "/retrieveCities", method = RequestMethod.GET)
	public List<String> retrieveCities() {
		List<String> result = null;
		boolean isUserLoggedIn = (boolean) this.httpSession.getAttribute(IS_USER_LOGGED_IN);
		if(isUserLoggedIn){
			result = citiesService.retrieveCities();
		}
		return  result;
	}

	@RequestMapping(value = ERROR_PATH)
	public String error() {
		return "You have to login to retrieve the list of cities...";
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	public HttpSession getHttpSession(){
		return this.httpSession;
	}
}
