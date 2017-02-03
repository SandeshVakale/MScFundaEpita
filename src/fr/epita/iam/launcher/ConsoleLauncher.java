/**
 * 
 */
package fr.epita.iam.launcher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.JDBCIdentityDAO;

import fr.epita.iam.problem.*;

/**
 * @author tbrou
 *
 */
public class ConsoleLauncher {
	
	private static JDBCIdentityDAO dao;

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, SQLException {
		System.out.println("Hello, welcome to the IAM application");
		Scanner scanner = new Scanner(System.in);
		dao = new JDBCIdentityDAO();
		
		
		
		//authentication
		System.out.println("Please enter your login");
		String login = scanner.nextLine();
		System.out.println("Please enter your password");
		String password = scanner.nextLine();
		
		if(!authenticate(login, password)){
			scanner.close();
			return;
		}
		int t=1;
		while(t==1)
		{
		// menu
		String answer = menu(scanner);
		
		switch (answer) {
		case "a":
			// creation
			createIdentity(scanner);
			System.out.println("Do you want to run again Yes/No");
			
			String check = scanner.nextLine();
			if(check.equals("yes") || check.equals("Yes"))
			{
				t=1;
				
			}else
			{
				t=0;
				System.out.println("thank you for banking with us");
				
			}
			break;
		case "b":
			udateIdentity(scanner);
			System.out.println("Do you want to run again Yes/No");
			
			String check1 = scanner.nextLine();
			if(check1.equals("yes") || check1.equals("Yes"))
			{
				t=1;
				
			}else
			{t=0;
				System.out.println("thank you for banking with us");
				
			}
			break;
		case "c":
			deleteIdentity(scanner);
	System.out.println("Do you want to run again Yes/No");
			
			String check11 = scanner.nextLine();
			if(check11.equals("yes") || check11.equals("Yes"))
			{
				t=1;
				
			}else
			{t=0;
				System.out.println("thank you for banking with us");
				
			}
			break;
		
		case "d":
			listIdentities();
			System.out.println("Do you want to run again Yes/No");
			
			String check111 = scanner.nextLine();
			if(check111.equals("yes") || check111.equals("Yes"))
			{
				t=1;
				
			}else
			{t=0;
				System.out.println("thank you for banking with us");
				
			}
break;
			
		case "e":
			System.out.println("thank you for banking with us");
			t=0;
			break;
			
		default:
			
			System.out.println("This option is not recognized ("+ answer + ")");
			break;
		}
		}
		scanner.close();

	}

	/**
	 * @throws SQLException 
	 * 
	 */
	private static void listIdentities() throws SQLException {
		System.out.println("This is the list of all identities in the system");
		List<Identity> list = dao.readAll();
		int size = list.size();
		for(int i = 0; i < size; i++){
			System.out.println( i+ "." + list.get(i));
		}
	
		
		
	}

	/**
	 * @param scanner
	 * @throws SQLException 
	 */
	private static void createIdentity(Scanner scanner) throws SQLException {
		System.out.println("You've selected : Identity Creation");
				System.out.println("Please enter the Identity display name");
		String displayName = scanner.nextLine();
		System.out.println("Please enter the Identity email");
		String email = scanner.nextLine();
		Identity newIdentity = new Identity(null, displayName, email);
		dao.writeIdentity(newIdentity);
		System.out.println("you succesfully created this identity :" + newIdentity);
	
		
	}
	
	
	
	private static void deleteIdentity(Scanner scanner){
		
		System.out.println("Deletion Activity");
		System.out.print("Please enter the Identity uid");
		String uid = scanner.nextLine();
		
		
		Identity identity = new Identity(uid, null, null);
		try {
			dao.delete(identity);
			System.out.println("Identity deleted\n");
		} catch (DeleteException e) {
			System.out.println(e.getDeleteFault());
		}
		
		
	}
	
	
	private static void udateIdentity(Scanner scanner) throws SQLException{
		
		System.out.println("Modification Activity");
		System.out.print("Please enter the Identity uid");
		String uid = scanner.nextLine();
		System.out.print("Please enter the new Identity display name");
		String displayName = scanner.nextLine();
		System.out.print("Please enter the new Identity email address");
		String email = scanner.nextLine();
		
		Identity Identity = new Identity(uid, displayName, email);
		
		System.out.println("Identity updated");
		System.out.println("New Identity display name = "+displayName);
		System.out.println("New Identity email address = "+email+"\n");

		
		try {
			dao.update(Identity);
		} catch (UpdateException e) {
			System.out.println(e.getUpdateFault());
		}

	
		
	}

	
	private static void startmenu()
	{
		System.out.println("Here are the actions you can perform :");
		System.out.println("a. Create an Identity");
		System.out.println("b. Modify an Identity");
		System.out.println("c. Delete an Identity");
		System.out.println("d. List Identities");
		System.out.println("e. quit");
		System.out.println("your choice (a|b|c|d|e) ? : ");
		
	}
	/**
	 * @param scanner
	 * @return
	 */
	private static String menu(Scanner scanner) {
		System.out.println("You're authenticated");
		startmenu();
		return scanner.nextLine();
		
	}

	/**
	 * @param login
	 * @param password
	 */
	private static boolean authenticate(String login, String password) {

		// TODO replace this hardcoded check by the real authentication method
		return "adm".equals(login) && "pwd".equals(password);
	}

}
