package fr.epita.iam.authentication;

public class AuthenticateUser {
	public boolean authenticate(String username, String password) {
		// authentication method
		return ("adm".equals(username) && "pwd".equals(password));
		
	}
}
