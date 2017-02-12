/**
 * 
 */
package fr.epita.iam.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.*;


import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.launcher.ConsoleLauncher;
import fr.epita.iam.problem.*;


/**
 * @author Sandesh Vakale
 *
 */
public class JDBCIdentityisConnect {

	//instance of logger to show 
	private static final Logger logger =  LogManager.getLogger(ConsoleLauncher.class);
	private Connection connection;
	
	/**
	 * @throws SQLException 
	 * 
	 */
	public JDBCIdentityisConnect() throws SQLException {
		
		//connect to database
		
		try{
		this.connection = DriverManager.getConnection("jdbc:mysql://66.147.244.85:3306/checkmyg_Epita?"+"user=checkmyg_Sandesh&password=SandeshV");
		
		logger.trace("This is online database");
		}catch(SQLException e)
		{
			logger.trace("May be you are not connected to internet, Please connect to internet and try again");
			System.exit(1);
		}
	}
	
	
	public void writeIdentity(Identity identity) throws SQLException {
		
		//write in identity table with query
		
		String insertStatement = "insert into IDENTITIES (IDENTITIES_DISPLAYNAME, IDENTITIES_EMAIL, IDENTITIES_BIRTHDATE) "
				+ "values(?, ?, ?)";
		PreparedStatement pstmtinsert = connection.prepareStatement(insertStatement);
		pstmtinsert.setString(1, identity.getDisplayName());
		pstmtinsert.setString(2, identity.getEmail());
		Date now = new Date();
		pstmtinsert.setDate(3, new java.sql.Date(now.getTime()));

		pstmtinsert.execute();
		
		logger.trace("you succesfully created this identity :" +identity.getDisplayName()+" "+identity.getEmail()+"\n");
		pstmtinsert.close();

	}

	public List<Identity> readAll() throws SQLException {
		
		//list of identities
		List<Identity> identities = new ArrayList<Identity>();

		PreparedStatement pstmtselect = connection.prepareStatement("select * from IDENTITIES");
		ResultSet rs = pstmtselect.executeQuery();
		while (rs.next()) {
			String displayName = rs.getString("IDENTITIES_DISPLAYNAME");
			String uid = String.valueOf(rs.getString("IDENTITIES_UID"));
			String email = rs.getString("IDENTITIES_EMAIL");
			Identity identity = new Identity(uid, displayName, email);
			identities.add(identity);
			
		}
		pstmtselect.close();
		return identities;

	}

	public void verify(Identity identity) throws SQLException{
		// to check that entered UID is available or not in database table	
		
	int count = 0;
		
		PreparedStatement pstmtselect = connection.prepareStatement("(select IDENTITIES_UID from IDENTITIES)");
		ResultSet rs = pstmtselect.executeQuery();
		logger.trace(rs);
		while (rs.next()) {
		    String em = rs.getString("IDENTITIES_UID");
		    logger.trace("em="+em);
		    if(em.equals(identity.getUid()))
		    {
		    	count++;
		    	logger.trace("count="+count);
		    }
		}
		
		logger.trace("count="+count);
		if(count==1)
		{
			logger.trace("uid found ");
			
			
		}else{
			
			logger.trace("uid not found, Please check list of available uids and try again");
		}
		pstmtselect.close();
		

	}
		
		
		
	
	public void update(Identity identity) throws UpdateException{
		 { 
			 // to update identity
			 
		try {
			
						
			int count = 0;
			
			PreparedStatement pstmtselect = connection.prepareStatement("(select IDENTITIES_UID from IDENTITIES)");
			ResultSet rs = pstmtselect.executeQuery();
			
			while (rs.next()) {
			    String em = rs.getString("IDENTITIES_UID");
			   
			    if(em.equals(identity.getUid()))
			    {
			    	count++;
			    	
			    }
			}
			
			pstmtselect.close();
			if(count==1)
			{
				logger.trace("uid found ");

				PreparedStatement prepareStatement = connection.prepareStatement("update IDENTITIES set IDENTITIES_EMAIL=?,IDENTITIES_DISPLAYNAME=? where IDENTITIES_UID=?");
				prepareStatement.setString(1, identity.getEmail() );
				prepareStatement.setString(2, identity.getDisplayName() );
				prepareStatement.setString(3, identity.getUid());
				prepareStatement.execute();
				prepareStatement.close();
				logger.trace("Identity updated");
				logger.trace("New Identity display name = "+identity.getDisplayName());
				logger.trace("New Identity email address = "+identity.getEmail()+"\n");
				
			}else{
				
				logger.trace("uid not found, Please check list of available uids and try again");
				
			}
			
		
			
		} catch (SQLException e) {
			UpdateException due = new UpdateException("Program cannot update the file");
			due.initCause(e);
			throw due;
		}
		
	}

}


	public void delete(Identity identity) throws fr.epita.iam.problem.DeleteException {
		
		try {
			
			
			//to delete identity
			
			
			 int count = 0;
				
				PreparedStatement prepareStatement = connection.prepareStatement("(select IDENTITIES_UID from IDENTITIES)");
				ResultSet rs = prepareStatement.executeQuery();
				prepareStatement.close();
				
				
				while (rs.next()) {
				    String em = rs.getString("IDENTITIES_UID");
				   
				    if(em.equals(identity.getUid()))
				    {
				    	count++;
				    	
				    }
				}
				
				
				if(count==1)
				{
					logger.trace("uid found ");

					PreparedStatement prepareStatement1 = connection.prepareStatement("delete from IDENTITIES where IDENTITIES_UID=?");
					logger.trace(identity.getUid());
					prepareStatement1.setString(1, identity.getUid());
					prepareStatement1.execute();
					prepareStatement1.close();
					logger.trace("Identity deleted\n");
					
				}else{
					
					logger.trace("uid not found, Please check list of available uids and try again");
					
				}
			
		
			
			
		} catch (SQLException e) {
			DeleteException dde = new DeleteException("Unable to delete!");
			dde.initCause(e);
			throw dde;
		}
		
	}
}
