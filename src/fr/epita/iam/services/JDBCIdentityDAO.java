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
		this.connection = DriverManager.getConnection("jdbc:derby://localhost:1527/sample;create=true","SandeshV","SandeshV");
		System.out.println(connection.getSchema());
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


	public void update(Identity identity) throws UpdateException{
		 {
		// TODO Auto-generated method stub
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("update IDENTITIES set IDENTITIES_EMAIL=?,IDENTITIES_DISPLAYNAME=? where IDENTITIES_UID=?");
			prepareStatement.setString(1, identity.getEmail() );
			prepareStatement.setString(2, identity.getDisplayName() );
			prepareStatement.setString(3, identity.getUid());
			prepareStatement.execute();
			
		} catch (SQLException e) {
			UpdateException due = new UpdateException("Program cannot update the file");
			due.initCause(e);
			throw due;
		}
		
	}

}


	public void delete(Identity identity) throws fr.epita.iam.problem.DeleteException {
		
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("delete from IDENTITIES where IDENTITIES_UID=?");
			System.out.println(identity.getUid());
			prepareStatement.setString(1, identity.getUid());
			prepareStatement.execute();
			
		} catch (SQLException e) {
			DeleteException dde = new DeleteException("Unable to delete!");
			dde.initCause(e);
			throw dde;
		}
		
	}
}
