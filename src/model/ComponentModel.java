/***********************************************************************
 * Module:  ComponentModel.java
 * Author:  User
 * Purpose: Defines the Class ComponentModel
 ***********************************************************************/

package model;

import model.TreeElement.Table;

public class ComponentModel extends AbstractSubject
{
	private int width;
	private int height;
	private ApplicationModel appModel = null;

	private TableDataModel tableDataModel = null;

	public ComponentModel()
	{
		tableDataModel = new TableDataModel();
	}

	public ComponentModel(ApplicationModel appModel)
	{
		this.appModel = appModel;
		tableDataModel = new TableDataModel();
	}

	public ComponentModel(ApplicationModel appModel, TableDataModel tableModel)
	{
		this.appModel = appModel;
		tableDataModel = tableModel;
	}

	public ComponentModel(ApplicationModel appModel, Table table, String sProc, Object... params)
	{
		this.appModel = appModel;
		this.tableDataModel = new TableDataModel();
		this.tableDataModel.executeStoredProcedure(table, sProc, params);
	}

	public int getWidth()
	{
		return width;
	}

	/** @param newWidth */
	public void setWidth(int newWidth)
	{
		width = newWidth;
	}

	public int getHeight()
	{
		return height;
	}

	/** @param newHeight */
	public void setHeight(int newHeight)
	{
		height = newHeight;
	}

	public final TableDataModel getTableDataModel()
	{
		return tableDataModel;
	}

	public final void setTableDataModel(TableDataModel tableDataModel)
	{
		this.tableDataModel = tableDataModel;
	}

	public final ApplicationModel getAppModel()
	{
		return appModel;
	}

	public final void setAppModel(ApplicationModel appModel)
	{
		this.appModel = appModel;
	}

	public final XMLModel getXmlModel()
	{
		return tableDataModel.getXmlModel();
	}

	public void setSelectedRow(int row)
	{
		tableDataModel.setSelectedRow(row);
		notifyAllSubscribers();
	}

	public final boolean executeStoredProcedure(Table table, String procedure, Object... params)
	{
		if (tableDataModel.executeStoredProcedure(table, procedure, params))
		{
			notifyAllSubscribers();
			return true;
		} else
		{
			return false;
		}
	}

	public final boolean executeUpdateProcedure(Object... params)
	{
		Table table = tableDataModel.getXmlTable();
		if (tableDataModel.executeStoredProcedure(table, table.getUpdateSProc(), params))
		{
			notifyAllSubscribers();
			return true;
		}

		return false;
	}

	public final boolean executeCreateProcedure(Object... params)
	{
		Table table = tableDataModel.getXmlTable();
		if (tableDataModel.executeStoredProcedure(table, table.getCreateSProc(), params))
		{
			notifyAllSubscribers();
			return true;
		}

		return false;
	}

	public final boolean executeFilterQuery(String[] paramCodes, Object... params)
	{
		if (tableDataModel.selectWithParams(tableDataModel.getXmlTable(), paramCodes, params))
		{
			notifyAllSubscribers();
			return true;
		}

		return false;
	}

	public boolean refresh()
	{
		return executeStoredProcedure(tableDataModel.getXmlTable(), tableDataModel.getXmlTable().getRetrieveAllSProc(),
				appModel.getSystemId());
	}

}