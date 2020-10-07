package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import command.AbstractCommand;
import command.Cancel;
import command.CommandFactory;
import command.Delete;
import command.Edit;
import command.Filter;
import command.First;
import command.Last;
import command.New;
import command.Next;
import command.Previous;
import command.Report;
import model.ComponentModel;
import view.ViewComponent;

public class CommandActionListener implements ActionListener
{
	ComponentModel model;
	ViewComponent view;

	HashMap<String, CommandFactory> commandMap = null;

	public CommandActionListener(ComponentModel model)
	{
		this.model = model;

		commandMap = new HashMap<String, CommandFactory>();

		commandMap.put("First", new First());
		commandMap.put("Previous", new Previous());
		commandMap.put("Next", new Next());
		commandMap.put("Last", new Last());

		commandMap.put("New", new New());
		commandMap.put("Edit", new Edit());
		commandMap.put("Delete", new Delete());

		commandMap.put("Filter", new Filter());
		commandMap.put("Cancel", new Cancel());

		commandMap.put("Report", new Report());
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		CommandFactory commandFactory = commandMap.get(e.getActionCommand());
		if (commandFactory != null)
		{
			AbstractCommand command = commandFactory.create();
			command.setModel(model);
			command.execute();
		}
	}

}
