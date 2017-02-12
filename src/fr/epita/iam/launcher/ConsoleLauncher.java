/**
 * 
 */
package fr.epita.iam.launcher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.*;

import fr.epita.iam.authentication.*;
import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.JDBCIdentityisConnect;

import fr.epita.iam.problem.*;





/**
 * @author Sandesh Vakale
 *
 */
public class ConsoleLauncher {
	private static final Logger logger =  LogManager.getLogger(ConsoleLauncher.class);
	
	
	private static JDBCIdentityisConnect isConnect;
	private ConsoleLauncher(){
		//Adding a private constructor to hide the implicit public one.
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 */
	
	
	
	
	public static void main(String[] args) throws IOException, SQLException {
		logger.trace("Hello, welcome to the IAM application");
		Scanner scanner = new Scanner(System.in);
		isConnect = new JDBCIdentityisConnect();
		
		
	
		//authentication
		logger.trace("program started");
		
		logger.trace("Please enter your login");
		String login = scanner.nextLine();
		logger.trace("Please enter your password");
		String password = scanner.nextLine();
		
		//check authentication
		AuthenticateUser authService = new AuthenticateUser();
		if(!authService.authenticate(login, password)){
			scanner.close();
			return;
		}
		int dountill = 1;
		
		// check if user want to run code again "dountill"
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
			logger.trace("thank you for banking with us");
			dountill=0;
			break;
			
		default:
			
			logger.trace("This option is not recognized ("+ answer + ") Please try again");
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
		//list identities
		logger.trace("This is the list of all identities in the system");
		List<Identity> list = isConnect.readAll();
		int size = list.size();
		for(int i = 0; i < size; i++){
			logger.trace( i+ "." + list.get(i));
		}
	
		
		
	}

	
	private static int runAgain(Scanner scanner){
		
		//run again if user want to run code again
		logger.trace("Do you want to run again Yes/No");
		int t;
		String check = scanner.nextLine();
		
		if("yes".equals(check) || "Yes".equals(check) || "y".equals(check) || "Y".equals(check))
		{
			t=1;
			
		}else if("No".equals(check) || "no".equals(check) || "N".equals(check) || "n".equals(check))
		{
			t=0;
			logger.trace("thank you for banking with us");
			
		}else
		{   t=0;
			logger.trace("invalid input. Please write Yes or No");
			runAgain(scanner);
		}
		return t;
		
	}
	
	/**
	 * @param scanner
	 * @throws SQLException 
	 */
	private static void createIdentity(Scanner scanner) throws SQLException {
		
		// create identity
		logger.trace("You've selected : Identity Creation");
		logger.trace("Please enter the Identity display name");
		String displayName = scanner.nextLine();
		logger.trace("Please enter the Identity email");
		String email = scanner.nextLine();
		Identity newIdentity = new Identity(null, displayName, email);
		isConnect.writeIdentity(newIdentity);
		
	
		
	}
	
	
	
	private static void deleteIdentity(Scanner scanner){
		//delete identity
		logger.trace("Deletion Activity");
		logger.trace("Please enter the Identity uid");
		String uid = scanner.nextLine();
		
		
		Identity identity = new Identity(uid, null, null);
		try {
			isConnect.delete(identity);
			
		} catch (DeleteException e) {
			logger.trace(e.getDeleteFault());
		}
		
		
	}
	private static void udateIdentity(Scanner scanner) throws SQLException{
		//update identity
		logger.trace("Modification Activity");
		logger.trace("Please enter the Identity uid");
		String uid = scanner.nextLine();
		logger.trace("Please enter the new Identity display name");
		String displayName = scanner.nextLine();
		logger.trace("Please enter the new Identity email address");
		String email = scanner.nextLine();
		
		Identity iDentity = new Identity(uid, displayName, email);
						
		try {
			isConnect.update(iDentity);
		} catch (UpdateException e) {
			logger.trace(e.getUpdateFault());

		}
			
	}

	
	private static void startmenu()
	{
		// show main menu
		logger.trace("Here are the actions you can perform :");
		logger.trace("a. Create an Identity");
		logger.trace("b. Modify an Identity");
		logger.trace("c. Delete an Identity");
		logger.trace("d. List Identities");
		logger.trace("e. quit");
		logger.trace("your choice (a|b|c|d|e) ? : ");
		
	}
	/**
	 * @param scanner
	 * @return
	 */
	private static String menu(Scanner scanner) {
		logger.trace("You're authenticated");
		// calling of main menu
		startmenu();
		return scanner.nextLine();
		
	}

	/**
	 * @param login
	 * @param password
	 */


}
