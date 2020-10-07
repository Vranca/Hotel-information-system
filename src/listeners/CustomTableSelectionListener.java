package listeners;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.ComponentModel;
import view.TableDataView;

public class CustomTableSelectionListener implements ListSelectionListener
{

	ComponentModel tableDataModel = null;
	TableDataView tableView = null;

	public CustomTableSelectionListener(ComponentModel tableDataModel, TableDataView tableView)
	{
		this.tableDataModel = tableDataModel;
		this.tableView = tableView;
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{

		tableDataModel.setSelectedRow(tableView.getTable().getSelectedRow());

	}

}
