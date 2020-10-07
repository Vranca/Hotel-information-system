/***********************************************************************
 * Module:  First.java
 * Author:  User
 * Purpose: Defines the Class First
 ***********************************************************************/

package command;

public class First extends AbstractCommand
{
	public void execute()
	{
		if (model.getTableDataModel().getRowCount() > 0)
		{
			model.setSelectedRow(0);
		}
	}

	public void unexecute()
	{
		// TODO: implement
	}

}