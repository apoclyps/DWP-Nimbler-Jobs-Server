package uk.co.kyleharrison.jobseeker.connectors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import uk.co.kyleharrison.jobseeker.model.YagaPojo;
import uk.co.kyleharrison.jobseeker.structs.YagaStruct;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

public class MySQLConnector {
	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private DataSource datasource;
	private String database_name = "ubiquity";
	private YagaStruct YGS;

	public MySQLConnector() {
		try {
			datasource = (DataSource) new InitialContext()
					.lookup("java:/comp/env/jdbc/" + database_name);
			connection = datasource.getConnection();
		} catch (NamingException ne) {
			System.out.println("Naming Exception in DatabaseConnector.java");
			ne.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Exception in DatabaseConnector.java");
			e.printStackTrace();
		}
	}

	public boolean checkConnection() {
		Context initContext;

		try {
			initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			datasource = (DataSource) envContext
					.lookup("jdbc/" + database_name);
			connection = datasource.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ArrayList<Object> queryDatabase(String queryString) {

		try {

			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			datasource = (DataSource) envContext
					.lookup("jdbc/" + database_name);
			connection = datasource.getConnection();

			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(queryString);
				ArrayList<Object> jobs = new ArrayList<>();

				try {

					while (resultSet.next()) {
						YagaPojo job = new YagaPojo();
						job.setId(Integer.parseInt(resultSet.getString("id")));
						job.setJobBoard(resultSet.getString("jobBoard"));
						job.setSalaryPeriod(resultSet.getString("salaryPeriod"));
						job.setDateFound(resultSet.getString("dateFound"));
						job.setSalaryMinYearly(resultSet
								.getString("salaryMaxYearly"));
						job.setTitle(resultSet.getString("title"));

						job.setDateFound(resultSet.getString("dateFound"));
						job.setDateFound(resultSet.getString("dateFound"));
						job.setDateFound(resultSet.getString("dateFound"));
						job.setDateFound(resultSet.getString("dateFound"));

						job.setAdvertiserName(resultSet
								.getString("advertiserName"));
						jobs.add(job);
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

				return jobs;

			} catch (MySQLSyntaxErrorException se) {
				System.out.println("Read database Syntax Exception : "
						+ se.getStackTrace());
			} catch (Exception e) {
				System.out
						.println("Read database Error : " + e.getStackTrace());
			}

		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			// close();
		}

		return null;

	}

	public boolean query(String queryString) {

		try {

			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			datasource = (DataSource) envContext
					.lookup("jdbc/" + database_name);
			connection = datasource.getConnection();

			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(queryString);

				ResultSetMetaData rsmd;
				try {
					rsmd = resultSet.getMetaData();
					int numColumns = rsmd.getColumnCount();
					if (resultSet.next()) {
						int columnIndex = 1;
						while (columnIndex <= numColumns) {
							System.out
									.println(resultSet.getString(columnIndex));
							columnIndex++;
							return true;
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} catch (MySQLSyntaxErrorException se) {
				System.out.println("Read database Syntax Exception : "
						+ se.getStackTrace());
			} catch (Exception e) {
				System.out
						.println("Read database Error : " + e.getStackTrace());
			}

		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			// close();
		}
		return false;
	}

	public void selectData(String queryString) throws Exception {
		try {

			if (queryString.equals("")) {
				queryString = "select * from login";
			}

			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			datasource = (DataSource) envContext
					.lookup("jdbc/" + database_name);
			connection = datasource.getConnection();

			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(queryString);

				writeMetaData(resultSet);
				writeResultSet(resultSet);

			} catch (MySQLSyntaxErrorException se) {
				System.out.println("Read database Syntax Exception : "
						+ se.getStackTrace());
			} catch (Exception e) {
				System.out
						.println("Read database Error : " + e.getStackTrace());
			}

		} catch (Exception e) {
			throw e;
		} finally {
			// close();
		}

	}

	@SuppressWarnings("deprecation")
	public void createData(String table, String[] parameter) {

		String sqlStatement = "INSERT INTO " + table + " VALUES (";

		int parameterLength = parameter.length;
		for (int i = 0; i < parameterLength; i++) {
			sqlStatement += "?";
			if (i <= (parameterLength - 2)) {
				sqlStatement += ",";
			} else if (i == (parameterLength - 1)) {
				sqlStatement += ");";
			}
		}

		System.out.println("SQL Statement = " + sqlStatement);

		try {
			preparedStatement = this.connection.prepareStatement(sqlStatement);

			preparedStatement.setString(1, parameter[0]);
			preparedStatement.setString(2, parameter[1]);
			preparedStatement.setString(3, parameter[2]);
			preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
			preparedStatement.setDate(5, new java.sql.Date(2009, 12, 11));
			preparedStatement.executeUpdate();

		} catch (MySQLIntegrityConstraintViolationException ivce) {
			ivce.printStackTrace();
			System.out.println("Duplicate Entry");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String GenerateSqlStatement(String table, YagaPojo yagaJobsP) {

		YGS = new YagaStruct(yagaJobsP);

		String sqlStatement = "INSERT INTO " + table + " ( ";
		String[] columns = YGS.getColumns();

		int columnLength = columns.length;
		for (int i = 0; i < columnLength; i++) {
			sqlStatement += columns[i];
			if (i <= (columnLength - 2)) {
				sqlStatement += ",";
			} else if (i == (columnLength - 1)) {
				sqlStatement += ")";
			}
		}

		sqlStatement += " VALUES (";
		String[] parameter = YGS.getParameters();

		int parameterLength = parameter.length;
		for (int i = 0; i < parameterLength; i++) {
			sqlStatement += "?";
			if (i <= (parameterLength - 2)) {
				sqlStatement += ",";
			} else if (i == (parameterLength - 1)) {
				sqlStatement += ");";
			}
		}

		System.out.println("SQL Statement = " + sqlStatement);
		return sqlStatement;
	}

	public void insetProcedure(String sqlStatement, YagaPojo yagaJobsP) {

		YGS = new YagaStruct(yagaJobsP);
		String[] parameters = YGS.getParameters();

		try {
			preparedStatement = this.connection.prepareStatement(sqlStatement);

			for (int i = 1; i <= parameters.length; i++)
				preparedStatement.setString(i, parameters[i - 1]);
		} catch (MySQLIntegrityConstraintViolationException ivce) {
			ivce.printStackTrace();
			System.out.println("Duplicate Entry");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("PRoSTR = " + preparedStatement.toString());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				// e.printStackTrace();
			}
		}
	}

	public String prepareStoredProcString(String table, String[] parameter) {
		String sqlStatement = "INSERT INTO " + table
				+ " (email_address,password) VALUES (";

		int parameterLength = parameter.length;
		for (int i = 0; i < parameterLength; i++) {
			sqlStatement += "?";
			if (i <= (parameterLength - 2)) {
				sqlStatement += ",";
			} else if (i == (parameterLength - 1)) {
				sqlStatement += ");";
			}
		}

		System.out.println("SQL Statement = " + sqlStatement);
		return sqlStatement;
	}

	public void createStoredProcedure(String table, String[] parameter) {

		String sqlStatement = prepareStoredProcString(table, parameter);

		try {
			preparedStatement = this.connection.prepareStatement(sqlStatement);

			for (int i = 1; i <= parameter.length; i++)
				preparedStatement.setString(i, parameter[i - 1]);
		} catch (MySQLIntegrityConstraintViolationException ivce) {
			ivce.printStackTrace();
			System.out.println("Duplicate Entry");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("PRoSTR = " + preparedStatement.toString());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int deleteData(String table, String parameter) {
		int status = -1;

		try {

			String sqlStatement = "delete from " + table + " where "
					+ parameter + "= ? ;";

			preparedStatement = this.connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, "12345678");
			preparedStatement.executeUpdate();

			System.out.println("Delete:" + status);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	public int updateData(String table, String parameter, String ID,
			String value) {

		int updateStatus = -1;

		try {

			String sql = "UPDATE +" + table + " SET " + parameter + " = ? "
					+ " WHERE " + ID + "= " + value + ";";

			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, "John");

			updateStatus = pst.executeUpdate();
			System.out.println("Updated Successfully!");

			// connection.close();
		} catch (SQLException e) {
			System.out.println("Exception 1!");
			e.printStackTrace();
		}

		return updateStatus;
	}

	private void writeMetaData(ResultSet resultSet) throws SQLException {
		System.out.println("The columns in the table are: ");

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " "
					+ resultSet.getMetaData().getColumnName(i));
		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int numColumns = rsmd.getColumnCount();

		if (resultSet.next()) {
			int columnIndex = 1;

			while (columnIndex <= numColumns) {
				System.out.println(resultSet.getString(columnIndex));
				columnIndex++;
			}
		}
	}

	
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}