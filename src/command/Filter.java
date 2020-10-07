package command;

import view.TableDataView;
import view.TableFilterView;

public class Filter extends AbstractCommand
{
	@Override
	public void execute()
	{
		TableDataView cTable = new TableDataView(model);
		model.addSubscriber(cTable);

		TableFilterView tfv = new TableFilterView(cTable, model);
		tfv.isUpdated();
	}

}
