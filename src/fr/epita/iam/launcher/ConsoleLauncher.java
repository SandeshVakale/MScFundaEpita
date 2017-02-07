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
 * @author Sandesh Vakale
 *
 */
public class ConsoleLauncher {
	
	private ConsoleLauncher(){
		//Adding a private constructor to hide the implicit public one.
	}
	
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
		int dountill = 1;
		while(dountill==1)
		{
		// menu
		String answer = menu(scanner);
		
		switch (answer) {
		case "a":
			// creation
			createIdentity(scanner);
			dountill = runAgain(scanner);
			break;
		case "b":
		    //update
			udateIdentity(scanner);
			dountill = runAgain(scanner);
			break;
		case "c":
			//Delete
			deleteIdentity(scanner);
			dountill = runAgain(scanner);
			break;
		
		case "d":
			//List
			listIdentities();
			dountill = runAgain(scanner);
			break;
			
		case "e":
			System.out.println("thank you for banking with us");
			dountill=0;
			break;
			
		default:
			
			System.out.println("This option is not recognized ("+ answer + ") Please try again");
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

	
	private static int runAgain(Scanner scanner){
		System.out.println("Do you want to run again Yes/No");
		int t;
		String check = scanner.nextLine();
		
		if("yes".equals(check) || "Yes".equals(check) || "y".equals(check) || "Y".equals(check))
		{
			t=1;
			
		}else if("No".equals(check) || "no".equals(check) || "N".equals(check) || "n".equals(check))
		{
			t=0;
			System.out.println("thank you for banking with us");
			
		}else
		{   t=0;
			System.out.println("invalid input. Please write Yes or No");
			runAgain(scanner);
		}
		return t;
		
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
		
	
		
	}
	
	
	
	private static void deleteIdentity(Scanner scanner){
		
		System.out.println("Deletion Activity");
		System.out.print("Please enter the Identity uid");
		String uid = scanner.nextLine();
		
		
		Identity identity = new Identity(uid, null, null);
		try {
			dao.delete(identity);
			
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
		
		Identity iDentity = new Identity(uid, displayName, email);
		
		

		
		try {
			dao.update(iDentity);
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

		//authentication method
		return "adm".equals(login) && "pwd".equals(password);
	}

}
