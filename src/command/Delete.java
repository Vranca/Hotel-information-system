/***********************************************************************
 * Module:  Delete.java
 * Author:  User
 * Purpose: Defines the Class Delete
 ***********************************************************************/

package command;

import model.TableDataModel;
import model.TreeElement.Table;

public class Delete extends AbstractCommand
{
	public void execute()
	{
		Table table = model.getTableDataModel().getXmlTable();
		TableDataModel tdm = new TableDataModel();

		String sProc = table.getDeleteSProc();
		int paramCount = 0;
		for (int i = 0; i < sProc.length(); i++)
		{
			if (sProc.charAt(i) == '?')
			{
				paramCount++;
			}
		}

		Object[] params = new Object[paramCount];
		for (int i = 0; i < paramCount; i++)
		{
			params[i] = model.getTableDataModel().getRealValueAt(model.getTableDataModel().getSelectedRow(), i);
		}

		tdm.executeStoredProcedure(table, table.getDeleteSProc(), params);

		model.refresh();
	}

	public void unexecute()
	{
		// TODO: implement
	}

}