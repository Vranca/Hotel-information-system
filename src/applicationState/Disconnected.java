/***********************************************************************
 * Module:  Disconnected.java
 * Author:  User
 * Purpose: Defines the Class Disconnected
 ***********************************************************************/

package applicationState;

import java.awt.Component;

import javax.swing.JButton;

import view.ApplicationView;
import view.MenuBarView;
import view.StatusBarView;
import view.ToolbarView;

public class Disconnected extends ApplicationState
{
	public Disconnected(ApplicationView applicationView)
	{
		super(applicationView);
		// TODO Auto-generated constructor stub
	}

	/** @param applicationView */
	public void setToolbar(ApplicationView applicationView)
	{
		ToolbarView toolbarView = (ToolbarView) applicationView.getToolbar();
		toolbarView.getComponent().setVisible(true);
		for (Component button : toolbarView.getComponent().getComponents())
		{
			if (button instanceof JButton)
				button.setEnabled(false);

		}

		toolbarView.getComponent().add(toolbarView.getReconnectButton());
	}

	/** @param applicationView */
	public void setStatusBar(ApplicationView applicationView)
	{
		// TODO: implement
	}

	/** @param applicationView */
	public void setMenuBar(ApplicationView applicationView)
	{
		// TODO: implement
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "Diskonektovano Stanje";
	}

	@Override
	public void setToolbar()
	{
		ToolbarView toolbarView = (ToolbarView) applicationView.getToolbar();

		toolbarView.getComponent().setVisible(false);

	}

	@Override
	public void setStatusBar()
	{
		StatusBarView statusBarView = (StatusBarView) applicationView.getStatusBar();
		statusBarView.getStateLabel().setText(this.toString());

	}

	@Override
	public void setMenuBar()
	{
		MenuBarView menuBarView = (MenuBarView) applicationView.getMenuBar();
		menuBarView.getChangeXML().setEnabled(false);
		menuBarView.getChooseXML().setEnabled(false);
		menuBarView.getExit().setEnabled(true);

		menuBarView.getDelete().setEnabled(false);
		menuBarView.getEditMenuItem().setEnabled(false);
		menuBarView.getNewMenuItem().setEnabled(false);

		menuBarView.getFirst().setEnabled(false);
		menuBarView.getLast().setEnabled(false);
		menuBarView.getNext().setEnabled(false);
		menuBarView.getPrevious().setEnabled(false);

		menuBarView.getAbout().setEnabled(false);
		menuBarView.getCancel().setEnabled(false);
		menuBarView.getAccept().setEnabled(false);

		menuBarView.getChangeState().setEnabled(true);

	}

}