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

public class Entry {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

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

		/*
		 * java.sql.Statement insertStatement = null; try { insertStatement =
		 * dbConnection.createStatement();
		 * 
		 * String insertQuery = props.getProperty("jdbc.query.insert");
		 * 
		 * int rows;
		 * 
		 * rows = insertStatement.executeUpdate(insertQuery);
		 * System.out.println("+" + rows + " rows successfully updated"); }
		 * finally {
		 * 
		 * if (insertStatement != null) insertStatement.close();
		 * 
		 * }
		 */
		String insertQuery = props.getProperty("jdbc.query.insert");
		try (PreparedStatement insertStatement = dbConnection.prepareStatement(insertQuery)) {

			String msg = "Mooti";
			insertStatement.setString(1, msg);
			insertStatement.executeUpdate();
		}

		try (Statement selectStatement = (Statement) dbConnection.createStatement()) {

			String selectQuery = props.getProperty("jdbc.query.select");

			ResultSet result;

			result = selectStatement.executeQuery(selectQuery);
			while (result.next()) {

				String message = result.getString(1);

				System.out.println(message);

			}

		}

	}

}
