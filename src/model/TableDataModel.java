/***********************************************************************
 * Module:  TableDataModel.java
 * Author:  User
 * Purpose: Defines the Class TableDataModel
 ***********************************************************************/

package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.microsoft.sqlserver.jdbc.SQLServerResultSet;

import model.TreeElement.Column;
import model.TreeElement.Table;
import utility.Tuple;

public class TableDataModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;

	private ResultSet resultSet = null;
	private ResultSetMetaData rsMetaData = null;
	private Connection connection = null;

	private CallableStatement callableStatement = null;
	private Statement statement = null;

	protected XMLModel xmlModel = null;
	protected Table xmlTable = null;
	protected java.util.List<Object> resultCells = null;
	protected java.util.List<Object> tableCells = null;
	protected java.util.List<String> colNames = null;
	protected java.util.List<Class<?>> colClasses = null;

	protected int columnCount = 0;
	protected int rowCount = 0;
	protected int selectedRow;

	protected String tableName = null;

	public TableDataModel()
	{
		connection = DBConnection.getConnection();
		resultCells = new ArrayList<Object>();
		tableCells = new ArrayList<Object>();
		colNames = new ArrayList<>();
		colClasses = new ArrayList<>();
		xmlModel = new XMLModel(null);
	}

	public final boolean executeStoredProcedure(Table table, String procedure, Object... params)
	{

		try
		{
			callableStatement = connection.prepareCall("{ call " + procedure + " }", SQLServerResultSet.TYPE_SCROLL_INSENSITIVE,
					SQLServerResultSet.CONCUR_READ_ONLY);

			if (procedure.contains("?"))
				for (int i = 0; i < params.length; i++)
				{
					callableStatement.setObject(i + 1, params[i]);
				}

			try
			{
				if (callableStatement.execute())
				{
					resultSet = callableStatement.getResultSet();
					rsMetaData = resultSet.getMetaData();

					tableName = table.getName();
					xmlTable = table;

					clearData();
					setData();

					resultSet.close();
					setTableCellsValues(table);
					this.fireTableStructureChanged();
				}
				return true;
			} catch (Exception e)
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Nije moguce izvrsiti komandu", "Hotelski informacioni sistem",
						JOptionPane.ERROR_MESSAGE);
				connection = DBConnection.getConnection();
				return false;
			}
		} catch (Exception e)
		{
			Object[] options = { "Povezi ponovo", "Zatvori" };
			int result = JOptionPane.showOptionDialog(null, "Izgubljena konekcija sa bazom. Uspostavi ponovo?",
					"Hotelski informacioni sistem", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);

			if (result == 0)
			{
				callableStatement = null;
				connection = DBConnection.getConnection();
				return executeStoredProcedure(table, procedure, params);
			} else
			{
				return false;
			}
		}
	}

	private final Object executeUspReferenceValue(Table table, String procedure, Object param)
	{
		Object value = null;

		try
		{

			CallableStatement cs = connection.prepareCall("{ call " + procedure + " }", SQLServerResultSet.TYPE_SCROLL_INSENSITIVE,
					SQLServerResultSet.CONCUR_READ_ONLY);
			if (param != null)
			{
				cs.setObject(1, param);

				ResultSet rs = cs.executeQuery();
				rs.next();
				value = rs.getObject(1);
				rs.close();

				return value;
			}
		} catch (Exception e)
		{
			e.printStackTrace();

		}

		return value;
	}

	/**
	 * @param key    : Column code that has to be in a table.
	 * @param tables : List of tables to be searched for the column code.
	 * @return Table object which contains the specified column.
	 */
	private Table getTableFromKey(String key, ArrayList<Table> tables)
	{
		for (Table table : tables)
		{
			for (Column column : table.getColumns())
			{
				if (key.contains(column.getCode()) && column.getRefrences().isEmpty())
				{
					tables.remove(table);
					return table;
				}
			}
		}

		return null;
	}

	private ArrayList<Table> getPopulatedTable(ArrayList<Table> arrayList)
	{
		ArrayList<Table> popTables = new ArrayList<Table>();
		for (int i = 0; i < arrayList.size(); i++)
		{
			popTables.add(xmlModel.getTable(arrayList.get(i).getCode()));
		}

		return popTables;
	}

	private void setTableCellsValues(Table table)
	{
		for (int row = 0; row < rowCount; row++)
		{
			ArrayList<Table> refTables = getPopulatedTable(table.getAllReferences());

			Vector<Column> columns = table.getColumns();
			for (int col = 0; col < columnCount; col++)
			{
				Table refTable = getTableFromKey(columns.get(col).getCode(), refTables);
				if (!columns.get(col).getRefrences().isEmpty() && refTable != null)
				{
					Object refValues = executeUspReferenceValue(refTable, refTable.getRetreiveRefValueSProc(),
							resultCells.get(col + columnCount * row));
					if (refValues != null)
						tableCells.set(col + columnCount * row, refValues);
				}
			}
		}
	}

	public Table getFkTable(Column fk)
	{
		ArrayList<Table> refTables = getPopulatedTable(xmlTable.getAllReferences());
		Table procTable = getTableFromKey(fk.getCode(), refTables);

		return procTable;
	}

	public boolean executeQuery(Table table, String query)
	{
		try
		{
			statement = connection.createStatement(SQLServerResultSet.TYPE_SCROLL_INSENSITIVE, SQLServerResultSet.CONCUR_READ_ONLY);
			try
			{
				resultSet = statement.executeQuery(query);
				rsMetaData = resultSet.getMetaData();

				tableName = table.getName();
				xmlTable = table;

				clearData();
				setData();

				resultSet.close();
				setTableCellsValues(table);
				this.fireTableStructureChanged();

				return true;
			} catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Nije moguce izvrsiti komandu", "Hotelski informacioni sistem",
						JOptionPane.ERROR_MESSAGE);
				connection = DBConnection.getConnection();
				return false;
			}
		} catch (SQLException e)
		{
			Object[] options = { "Povezi ponovo", "Zatvori" };
			int result = JOptionPane.showOptionDialog(null, "Izgubljena konekcija sa bazom. Uspostavi ponovo?",
					"Hotelski informacioni sistem", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);

			if (result == 0)
			{
				callableStatement = null;
				connection = DBConnection.getConnection();
				return executeQuery(table, query);
			} else
			{
				return false;
			}
		}
	}

	public final boolean selectWithParams(Table table, String[] paramCodes, Object... params)
	{
		String query = "SELECT * FROM [" + table.getCode() + "]";

		if (params.length > 0)
		{
			query = query.concat(" WHERE [" + paramCodes[0] + "] = " + params[0].toString());
			for (int i = 1; i < params.length; i++)
			{
				query = query.concat(" AND [" + paramCodes[i] + "] = " + params[i].toString());
			}
		}

		return executeQuery(table, query);
	}

	@Override
	public int getRowCount()
	{
		return rowCount;
	}

	@Override
	public int getColumnCount()
	{
		return columnCount + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		if (columnIndex == 0)
			return rowIndex + 1;

		return tableCells.get(columnIndex - 1 + rowIndex * columnCount);
	}

	@Override
	public Class<?> getColumnClass(int col)
	{
		if (col == 0)
			return Integer.class;

		return colClasses.get(col - 1);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}

	public boolean isCellPK(int rowIndex, int columnIndex)
	{
		if (columnIndex == 0)
			return true;

		return xmlTable.getColumns().get(columnIndex - 1).isPrimary();
	}

	public boolean isCellFK(int rowIndex, int columnIndex)
	{
		if (columnIndex == 0)
			return false;

		return (xmlTable.getColumns().get(columnIndex - 1).getRefrences().size() > 0);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		tableCells.set(columnIndex - 1 + rowIndex * columnCount, aValue);
	}

	@Override
	public String getColumnName(int index)
	{
		if (index == 0)
			return "#";

		return colNames.get(index - 1);
	}

	public final java.util.List<Object> getTableCells()
	{
		return tableCells;
	}

	public final java.util.List<Object> getResultCells()
	{
		return resultCells;
	}

	public final XMLModel getXmlModel()
	{
		return xmlModel;
	}

	public final java.sql.ResultSet getResultSet()
	{
		return resultSet;
	}

	public final java.sql.ResultSetMetaData getRsMetaData()
	{
		return rsMetaData;
	}

	public final Object getRealValueAt(int row, int column)
	{
		return resultCells.get(column + row * columnCount);
	}

	public final void setXmlModel(XMLModel xmlModel)
	{
		this.xmlModel = xmlModel;
	}

	public final String getTableName()
	{
		return tableName;
	}

	public final int getSelectedRow()
	{
		return selectedRow;
	}

	public final void setSelectedRow(int selectedRow)
	{
		this.selectedRow = selectedRow;
	}

	public final Table getXmlTable()
	{
		return xmlTable;
	}

	public Tuple<Object, Object> getPkTuple(int row)
	{
		Object firstObject;
		Object secondObject;

		for (int i = row * columnCount; i < row * columnCount + columnCount; i++)
		{
			firstObject = resultCells.get(i);
			secondObject = tableCells.get(i);
			if (isCellPK(row, i - row * columnCount) && firstObject.equals(secondObject))
			{
				secondObject = executeUspReferenceValue(xmlTable, xmlTable.getRetreiveRefValueSProc(), firstObject);
				return new Tuple<Object, Object>(firstObject, secondObject);
			}
		}

		return null;
	}

	private void clearData()
	{
		resultCells.clear();
		tableCells.clear();
		colNames.clear();
		colClasses.clear();
		rowCount = 0;
	}

	private void setCellsData()
	{
		try
		{
			while (resultSet.next())
			{
				rowCount++;
				for (int i = 1; i <= columnCount; i++)
				{
					tableCells.add(resultSet.getObject(i));
					resultCells.add(resultSet.getObject(i));
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void setColumnData()
	{
		try
		{
			resultSet.first();
			for (int i = 1; i <= columnCount; i++)
			{

				colNames.add(xmlTable.getColumns().get(i - 1).getName());
				colClasses.add(Class.forName(rsMetaData.getColumnClassName(i)));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void setData()
	{
		try
		{
			columnCount = rsMetaData.getColumnCount();

			setCellsData();
			setColumnData();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean isPkIdentity()
	{
		String sProc = xmlTable.getCreateSProc();
		int paramCount = 0;

		for (Character c : sProc.toCharArray())
		{
			if (c.equals('?'))
				paramCount++;
		}

		return paramCount == xmlTable.getColumns().size();
	}
}