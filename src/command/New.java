/***********************************************************************
 * Module:  New.java
 * Author:  User
 * Purpose: Defines the Class New
 ***********************************************************************/

package command;

import model.ComponentModel;
import model.TableDataModel;
import model.TreeElement.Table;
import view.TableCreateView;
import view.TableDataView;

public class New extends AbstractCommand
{
	public void execute()
	{
		Table table = model.getTableDataModel().getXmlTable();
		TableDataModel tdm = new TableDataModel();

		String sProc = table.getRetrieveSProc();
		int paramCount = 0;
		for (int i = 0; i < sProc.length(); i++)
		{
			if (sProc.charAt(i) == '?')
			{
				paramCount++;
			}
		}

		Object[] params = new Object[paramCount];

		tdm.executeStoredProcedure(table, table.getRetrieveSProc(), params);

		ComponentModel cModel = new ComponentModel(model.getAppModel(), tdm);
		TableDataView cTable = new TableDataView(cModel);
		cModel.addSubscriber(cTable);

		TableCreateView tcv = new TableCreateView(cTable, cModel);
		boolean isApplied = tcv.isUpdated();
		if (isApplied)
			model.refresh();
	}

	public void unexecute()
	{
		// TODO: implement
	}

}