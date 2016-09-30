import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Statement;

public class Entry2 {

	
	public static void main(String[] args) throws IOException, SQLException {
		
		// TODO LOAD JDBC PROPERTIES FROM FILE SYSTEM
				Properties props = new Properties();

				FileInputStream fIn = new FileInputStream("dbDetails.properties");
				props.load(fIn);

				// TODO LOAD AND REGISTER JDBC DRIVER

				String driver = props.getProperty("jdbc.driver");
				// Class.forName(driver);

				// TODO GET DATABASE CONENCTION USING JDBC URL

				String url = props.getProperty("jdbc.url");
				Connection dbConnection;

				dbConnection = DriverManager.getConnection(url);
				System.out.println("Connection Successful?" + (dbConnection != null));

				String insertQuery = props.getProperty("jdbc.query.insertMain");
				try(PreparedStatement insertStatement = dbConnection.prepareStatement(insertQuery)){
					
					insertStatement.executeUpdate();
				}
				
				try (Statement selectStatement = (Statement) dbConnection.createStatement()) {

					String selectQuery = props.getProperty("jdbc.query.selectMain");

					ResultSet result;

					result = selectStatement.executeQuery(selectQuery);
					while (result.next()) {

						String message = result.getString(1);
						int age = result.getInt(2);
						System.out.println(message+" "+age);

					}
					Statement stmnt = null;
					stmnt = (Statement) dbConnection.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
	                           ResultSet.CONCUR_UPDATABLE);
					result = stmnt.executeQuery(selectQuery);
					while (result.next()) {

						String name = result.getString(1);
						int age = result.getInt("age");
						result.updateInt("age", age*10);
						result.updateRow();
						System.out.println(name+" "+age);

					}
					
				}
	}
}
