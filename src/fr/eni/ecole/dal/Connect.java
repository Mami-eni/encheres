package fr.eni.ecole.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Connect {
	public static boolean DEV_MODE = true;
	
	private static DataSource dataSource;

	
	/**
	 * Au chargement de la classe, la DataSource est recherch�e dans le 
	 * JNDI de Tomcat (context.xml)
	 */
	static {
		Context context;
		try {
			context = new InitialContext();
			Connect.dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool_cnx");
		} catch (NamingException e) {

			e.printStackTrace();

			throw new RuntimeException("Cannot Access The Database");
		}
	}

	
	/**
	 * Getter pour acc�der � la connexion
	 * @throws ClassNotFoundException 
	 */

	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		if(DEV_MODE) {
		return Connect.dataSource.getConnection();}
		else {
			String urldb = "localhost/troc?serverTimezone=UTC";
			String userdb = "root";
			String passworddb ="";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			return DriverManager.getConnection("jdbc:mysql://"+urldb, userdb,passworddb);
		}

	}
}

