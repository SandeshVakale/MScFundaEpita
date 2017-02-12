package fr.epita.iam.authentication;

public class AuthenticateUser {
	public boolean authenticate(String username, String password) {
		// authentication method
		if (username .equals("adm") && password .equals("pwd"))
		return true;
		else 
			return false;
	}
}
