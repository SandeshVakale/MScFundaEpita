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

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.problem.*;


/**
 * @author tbrou
 *
 */
public class JDBCIdentityDAO {

	
	
	private Connection connection;
	
	/**
	 * @throws SQLException 
	 * 
	 */
	public JDBCIdentityDAO() throws SQLException {
		this.connection = DriverManager.getConnection("jdbc:mysql://66.147.244.85:3306/checkmyg_Epita?"+"user=checkmyg_Sandesh&password=SandeshV");
		//System.out.println(connection.getSchema());
		System.out.println("This is online database");
	}
	
	
	public void writeIdentity(Identity identity) throws SQLException {
		String insertStatement = "insert into IDENTITIES (IDENTITIES_DISPLAYNAME, IDENTITIES_EMAIL, IDENTITIES_BIRTHDATE) "
				+ "values(?, ?, ?)";
		PreparedStatement pstmt_insert = connection.prepareStatement(insertStatement);
		pstmt_insert.setString(1, identity.getDisplayName());
		pstmt_insert.setString(2, identity.getEmail());
		Date now = new Date();
		pstmt_insert.setDate(3, new java.sql.Date(now.getTime()));

		pstmt_insert.execute();
		
		System.out.println("you succesfully created this identity :" +identity.getDisplayName()+" "+identity.getEmail()+"\n");

	}

	public List<Identity> readAll() throws SQLException {
		List<Identity> identities = new ArrayList<Identity>();

		PreparedStatement pstmt_select = connection.prepareStatement("select * from IDENTITIES");
		ResultSet rs = pstmt_select.executeQuery();
		while (rs.next()) {
			String displayName = rs.getString("IDENTITIES_DISPLAYNAME");
			String uid = String.valueOf(rs.getString("IDENTITIES_UID"));
			String email = rs.getString("IDENTITIES_EMAIL");
			Date birthDate = rs.getDate("IDENTITIES_BIRTHDATE");
			Identity identity = new Identity(uid, displayName, email);
			identities.add(identity);
		}
		return identities;

	}

	public void verify(Identity identity) throws SQLException{
			
	int count = 0;
		
		PreparedStatement pstmt_select = connection.prepareStatement("(select IDENTITIES_UID from IDENTITIES)");
		ResultSet rs = pstmt_select.executeQuery();
		System.out.println(rs);
		String arr = null;
		while (rs.next()) {
		    String em = rs.getString("IDENTITIES_UID");
		    System.out.println("em="+em);
		    if(em.equals(identity.getUid()))
		    {
		    	count++;
		    	System.out.println("count="+count);
		    }
		}
		
		System.out.println("count="+count);
		if(count==1)
		{
			System.out.println("uid found ");
			
			
		}else{
			
			System.out.println("uid not found, Please check list of available uids and try again");
		}
			
		

	}
		
		
		
	
	public void update(Identity identity) throws UpdateException{
		 {
		// TODO Auto-generated method stub
			 
			
			 
			 
			 
		try {
			
			
			
			////////////////////
			
			
			int count = 0;
			
			PreparedStatement pstmt_select = connection.prepareStatement("(select IDENTITIES_UID from IDENTITIES)");
			ResultSet rs = pstmt_select.executeQuery();
			//System.out.println(rs);
			String arr = null;
			while (rs.next()) {
			    String em = rs.getString("IDENTITIES_UID");
			   
			    if(em.equals(identity.getUid()))
			    {
			    	count++;
			    	
			    }
			}
			
			
			if(count==1)
			{
				System.out.println("uid found ");

				PreparedStatement prepareStatement = connection.prepareStatement("update IDENTITIES set IDENTITIES_EMAIL=?,IDENTITIES_DISPLAYNAME=? where IDENTITIES_UID=?");
				prepareStatement.setString(1, identity.getEmail() );
				prepareStatement.setString(2, identity.getDisplayName() );
				prepareStatement.setString(3, identity.getUid());
				prepareStatement.execute();
				
				System.out.println("Identity updated");
				System.out.println("New Identity display name = "+identity.getDisplayName());
				System.out.println("New Identity email address = "+identity.getEmail()+"\n");
				
			}else{
				
				System.out.println("uid not found, Please check list of available uids and try again");
				
			}
			
			///////////////////////
			
			
			
			
			
			
			
			
			
		} catch (SQLException e) {
			UpdateException due = new UpdateException("Program cannot update the file");
			due.initCause(e);
			throw due;
		}
		
	}

}


	public void delete(Identity identity) throws fr.epita.iam.problem.DeleteException {
		
		try {
			
			
			////////////////////
			
			
			 int count = 0;
				
				PreparedStatement pstmt_select = connection.prepareStatement("(select IDENTITIES_UID from IDENTITIES)");
				ResultSet rs = pstmt_select.executeQuery();
				//System.out.println(rs);
				String arr = null;
				while (rs.next()) {
				    String em = rs.getString("IDENTITIES_UID");
				   
				    if(em.equals(identity.getUid()))
				    {
				    	count++;
				    	
				    }
				}
				
				
				if(count==1)
				{
					System.out.println("uid found ");

					PreparedStatement prepareStatement = connection.prepareStatement("delete from IDENTITIES where IDENTITIES_UID=?");
					System.out.println(identity.getUid());
					prepareStatement.setString(1, identity.getUid());
					prepareStatement.execute();
					System.out.println("Identity deleted\n");
					
				}else{
					
					System.out.println("uid not found, Please check list of available uids and try again");
					
				}
			
			///////////////////////
			
			
			
			
			
			
			
			
			
			
			
			
		} catch (SQLException e) {
			DeleteException dde = new DeleteException("Unable to delete!");
			dde.initCause(e);
			throw dde;
		}
		
	}
}
