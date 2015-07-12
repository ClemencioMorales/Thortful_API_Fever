package interview.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationService {

	private Map<String, String> credentials;

	public AuthenticationService(){
		loadCredentials();
	}

	private void loadCredentials() {
		this.setCredentials(new HashMap<String, String>());
		this.getCredentials().put("User", "P4ssw0rd");
		this.getCredentials().put("UserOther", "pAsswd");
	}

	public String authenticate(final String username, final String password) throws IllegalArgumentException {
		loadCredentials();
		String result = null;
		Iterator it = this.getCredentials().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			if (pair.getKey().equals(username)){
				if(pair.getValue().equals(password)){
					result = username;
				}
			}
			it.remove(); // avoids a ConcurrentModificationException
		}
		return  result;
	}

	public void setCredentials(Map<String, String> credentials) {
		this.credentials = credentials;
	}

	public Map<String, String> getCredentials(){
		return this.credentials;
	}
}
