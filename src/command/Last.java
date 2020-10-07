/***********************************************************************
 * Module:  Last.java
 * Author:  User
 * Purpose: Defines the Class Last
 ***********************************************************************/

package command;

public class Last extends AbstractCommand
{
	public void execute()
	{
		int rowCount = model.getTableDataModel().getRowCount();
		if (rowCount > 0)
		{
			model.setSelectedRow(rowCount - 1);
		}
	}

	public void unexecute()
	{
		// TODO: implement
	}

}