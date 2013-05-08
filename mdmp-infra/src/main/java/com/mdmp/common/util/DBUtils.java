package com.mdmp.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class DBUtils {

	private static final Logger LOGGER = Logger.getLogger(DBUtils.class);

	static Context context = new Context();
	/**
   * {@value}
   */
	public static final String LOG_FROMAT_FOR_SQL_EXCEPTION = "fail to execute sql : %s for %s";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConnnection() throws Exception {

		return DriverManager.getConnection(context.get("db.address"),
				context.get("db.user"), context.get("db.password"));
	}

	public static boolean execute(String sql, Object[] params) throws Exception {
		Connection connection = getConnnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; params != null && i < params.length; i++)
				preparedStatement.setObject(i + 1, params[i]);

			return preparedStatement.execute(sql);
		} catch (SQLException e) {
			String msg = String.format(LOG_FROMAT_FOR_SQL_EXCEPTION, sql,
					e.getMessage());
			throw new IllegalStateException(msg, e);
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
	}

	public static ResultSet get(String sql) throws Exception {
		Connection connection = getConnnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			String msg = String.format(LOG_FROMAT_FOR_SQL_EXCEPTION, sql,
					e.getMessage());
			throw new IllegalStateException(msg, e);
		} finally {
			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
	}

	public static boolean execute(String sql) throws Exception {
		Connection connection = getConnnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			return statement.execute(sql);
		} catch (SQLException e) {
			String msg = String.format(LOG_FROMAT_FOR_SQL_EXCEPTION, sql,
					e.getMessage());
			throw new IllegalStateException(msg, e);
		} finally {
			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
	}

	public static int update(String sql) throws Exception {
		LOGGER.info("execute sql: " + sql);
		Connection connection = getConnnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			String msg = String.format(LOG_FROMAT_FOR_SQL_EXCEPTION, sql,
					e.getMessage());
			throw new IllegalStateException(msg, e);
		} finally {
			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
	}

	public static int update(String sql, Object[] params) throws Exception {
		Connection connection = getConnnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; params != null && i < params.length; i++) {
				preparedStatement.setObject(i + 1, params[i]);
			}
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
	}

}
