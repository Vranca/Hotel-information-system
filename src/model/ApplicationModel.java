/***********************************************************************
 * Module:  ApplicationModel.java
 * Author:  User
 * Purpose: Defines the Class ApplicationModel
 ***********************************************************************/

package model;

import applicationState.ApplicationState;
import applicationState.IdleState;

public class ApplicationModel extends AbstractSubject
{
	private int width;
	private int height;
	private int minWidth;
	private int minHeight;

	private ApplicationState appState = null;
	private UserDataModel userDataModel = null;
	private TableDataModel privilegesDataModel = null;
	private XMLModel xmlModel;

	public ApplicationModel()
	{
		width = 1000;
		height = 600;
		minWidth = 600;
		minHeight = 400;

		appState = new IdleState();
		xmlModel = new XMLModel(null);
	}

	public void getPrivileges(int oznakaGrupe)
	{
		privilegesDataModel = new TableDataModel();
		TreeElement.Table privilegeTable = xmlModel.getTable(TreeElement.Privileges.getTableCode());
		privilegesDataModel.executeStoredProcedure(privilegeTable, TreeElement.Privileges.getPrivilegesProcedure(), oznakaGrupe);
	}

	public void setXmlModel(XMLModel xmlModel)
	{
		this.xmlModel = xmlModel;
	}

	public boolean login(String username, String string)
	{
		userDataModel = new UserDataModel();
		TreeElement.Table userTable = xmlModel.getTable(TreeElement.LoginData.getTableCode());
		userDataModel.executeStoredProcedure(userTable, TreeElement.LoginData.getLoginProcedure(), username, string);

		if (userDataModel.getTableCells().size() > 0)
		{
			userDataModel.getPrivileges();

			return true;
		} else
		{
			return false;
		}
	}

	/** @param state */
	public void setState(ApplicationState state)
	{
		appState = state;
		notifyAllSubscribers();
	}

	public final ApplicationState getAppState()
	{
		return appState;
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

	public final int getMinWidth()
	{
		return minWidth;
	}

	public final int getMinHeight()
	{
		return minHeight;
	}

	public final UserDataModel getUserDataModel()
	{
		return userDataModel;
	}

	public final XMLModel getXmlModel()
	{
		return xmlModel;
	}

	public Object getSystemId()
	{
		return userDataModel.getRealValueAt(0, 0);
	}

	public TableDataModel getPrivileges()
	{
		return privilegesDataModel;
	}

	public void setPrivileges(TableDataModel privileges)
	{
		this.privilegesDataModel = privileges;
	}

}