/***********************************************************************
 * Module:  Previous.java
 * Author:  User
 * Purpose: Defines the Class Previous
 ***********************************************************************/

package command;

public class Previous extends AbstractCommand
{
	public void execute()
	{
		int selectedRow = model.getTableDataModel().getSelectedRow();
		if (selectedRow > 0)
		{
			model.setSelectedRow(selectedRow - 1);
		}
	}

	public void unexecute()
	{
		// TODO: implement
	}

}