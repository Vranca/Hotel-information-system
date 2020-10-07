package command;

import model.ComponentModel;

public class AbstractCommand implements Command, CommandFactory
{
	protected ComponentModel model = null;

	public AbstractCommand()
	{
		// Do nothing
	}

	public AbstractCommand(ComponentModel model)
	{
		this.model = model;
	}

	@Override
	public final AbstractCommand create()
	{
		Class<? extends AbstractCommand> commandClass = getClass();
		try
		{
			return commandClass.newInstance();
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void setModel(ComponentModel model)
	{
		this.model = model;
	}

	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void unexecute()
	{
		// TODO Auto-generated method stub

	}

}
