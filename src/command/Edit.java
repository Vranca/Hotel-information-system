/***********************************************************************
 * Module:  Edit.java
 * Author:  User
 * Purpose: Defines the Class Edit
 ***********************************************************************/

package command;

import model.ComponentModel;
import model.TableDataModel;
import model.TreeElement.Table;
import view.TableDataView;
import view.TableEditorView;

public class Edit extends AbstractCommand
{
	public Edit()
	{
		// Do nothing
	}

	public Edit(ComponentModel model)
	{
		this.model = model;
	}

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
		for (int i = 0; i < paramCount; i++)
		{
			params[i] = model.getTableDataModel().getRealValueAt(model.getTableDataModel().getSelectedRow(), i);
		}

		tdm.executeStoredProcedure(table, table.getRetrieveSProc(), params);

		ComponentModel cModel = new ComponentModel(model.getAppModel(), tdm);
		TableDataView cTable = new TableDataView(cModel);
		cModel.addSubscriber(cTable);

		TableEditorView tev = new TableEditorView(cTable, cModel);
		boolean isApplied = tev.isUpdated();
		if (isApplied)
			model.refresh();
	}

	public void unexecute()
	{
		// TODO: implement
	}

}