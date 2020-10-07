package command;

import model.ComponentModel;

public interface CommandFactory
{
	AbstractCommand create();

	void setModel(ComponentModel model);
}
