/***********************************************************************
 * Module:  Next.java
 * Author:  User
 * Purpose: Defines the Class Next
 ***********************************************************************/

package command;

public class Next extends AbstractCommand
{
	public void execute()
	{
		int selectedRow = model.getTableDataModel().getSelectedRow();
		if (selectedRow < model.getTableDataModel().getRowCount() - 1)
		{
			model.setSelectedRow(selectedRow + 1);
		}
	}

	public void unexecute()
	{
		// TODO: implement
	}

}