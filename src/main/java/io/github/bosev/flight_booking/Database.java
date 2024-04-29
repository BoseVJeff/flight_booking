package io.github.bosev.flight_booking;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.time.LocalDateTime;

public class Database {
	private Connection connection;

	public Database() throws SQLException {
		System.out.println("Initialising database...");
		OracleDataSource oracleDataSource=new OracleDataSource();

		oracleDataSource.setURL("jdbc:oracle:thin:@//localhost:1521/XE");
		oracleDataSource.setUser(Credentials.getDatabaseUsernameString());
		oracleDataSource.setPassword(Credentials.getDatabsePasswordString());

		this.connection=oracleDataSource.getConnection();
	}

	public void testDatabaseConnection() throws SQLException {
		// Create Oracle DatabaseMetaData object
		DatabaseMetaData meta = this.connection.getMetaData();

		// gets driver info:
		System.out.println("JDBC driver version is " + meta.getDriverVersion());

		String query = "SELECT SYSDATE FROM DUAL";
		Statement statement = connection.createStatement();

		ResultSet rSet = statement.executeQuery(query);

		while (rSet.next()) {
			System.out.println("Current date is " + rSet.getString("SYSDATE"));
		}

		String timeStampQuery = "SELECT LOCALTIMESTAMP FROM DUAL";
		Statement timeStampStatement = connection.createStatement();
		ResultSet getSet = statement.executeQuery(timeStampQuery);

		while (getSet.next()) {
			LocalDateTime localDateTime = LocalDateTime.parse(getSet.getString("LocalTimeStamp"));
			System.out.println(localDateTime.getHour());
		}

		getSet.close();
		timeStampStatement.close();

		rSet.close();

		statement.close();
	}
}
