/***********************************************************************
 * Module:  DBConnection.java
 * Author:  User
 * Purpose: Defines the Class DBConnection
 ***********************************************************************/

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class DBConnection
{
	private static Connection connection = null;

	private static Connection createConnection(String adress, String port, String dbName, String user, String password)
	{
		Connection conn = null;

		String fullAdress;
		if (port != null)
		{
			fullAdress = adress.trim() + ":" + port.trim();
		} else
		{
			fullAdress = adress.trim();
		}
		String url = "jdbc:sqlserver://" + fullAdress + ";";

		try
		{
			Properties connectionProperties = new Properties();
			connectionProperties.put("databaseName", dbName.trim());
			connectionProperties.put("user", user.trim());
			connectionProperties.put("password", password.trim());

			conn = DriverManager.getConnection(url, connectionProperties);

			return conn;
		} catch (SQLException e)
		{
			new Error(e.getMessage(), e);
		}

		return conn;
	}

	public static Connection getConnection()
	{
		if (connection == null)
		{
			connection = createConnection("78.28.157.8", "1433", "PIS2020", "EtfPIS2020G6", "EtfPIS2020G63648");
			return connection;
		} else
		{
			try
			{
				if (connection.isClosed())
				{
					connection = createConnection("78.28.157.8", "1433", "PIS2020", "EtfPIS2020G6", "EtfPIS2020G63648");
					return connection;
				}
			} catch (SQLException e)
			{
				JOptionPane.showMessageDialog(null, "Nije moguce konektovati se na bazu. " + e.getMessage(), "Hotelski informacioni sistem",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		return connection;
	}

}