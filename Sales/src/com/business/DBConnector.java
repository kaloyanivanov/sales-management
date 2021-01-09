package com.business;

import java.sql.*;

public class DBConnector implements AutoCloseable {
	private Connection connection;
	private Statement statement;
	private ResultSet results;

	public void getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/business", "business", "test");
		configureStatement();
	}

	public void configureStatement() throws SQLException {
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

	}

	public PreparedStatement configurePreparedStatement(String statement) throws SQLException {
		PreparedStatement prepared = connection.prepareStatement(statement, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		return prepared;

	}

	public void executeQuery(String query) throws SQLException {
		results = statement.executeQuery(query);

	}

	public ResultSet getResults() {

		return results;
	}

	@Override
	public void close() throws Exception {
		if (statement != null)
			statement.close();
		if (connection != null)
			connection.close();
		if (results != null)
			results.close();
	}
}
