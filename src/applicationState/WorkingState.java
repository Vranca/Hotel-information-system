/***********************************************************************
 * Module:  WorkingState.java
 * Author:  User
 * Purpose: Defines the Class WorkingState
 ***********************************************************************/

package applicationState;

import java.awt.Component;

import javax.swing.JButton;

import view.ApplicationView;
import view.MenuBarView;
import view.StatusBarView;
import view.ToolbarView;

public class WorkingState extends ApplicationState
{
	public WorkingState(ApplicationView applicationView)
	{
		super(applicationView);
		// TODO Auto-generated constructor stub
	}

	/** @param applicationView */
	public void setToolbar(ApplicationView applicationView)
	{
		ToolbarView toolbarView = (ToolbarView) applicationView.getToolbar();
		for (Component button : toolbarView.getComponent().getComponents())
		{
			if (button instanceof JButton)
				button.setEnabled(false);

		}
		toolbarView.getBtnDeleteRow().setEnabled(false);
		toolbarView.getBtnEditRow().setEnabled(false);
		toolbarView.getBtnNew().setEnabled(false);

		toolbarView.getComponent().repaint();
	}

	/** @param applicationView */
	public void setStatusBar(ApplicationView applicationView)
	{
		applicationView.getStatusBar().update();
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
		return "Radno Stanje";
	}

	@Override
	public void setToolbar()
	{
		ToolbarView toolbarView = (ToolbarView) applicationView.getToolbar();
		toolbarView.getComponent().setVisible(true);
		for (Component button : toolbarView.getComponent().getComponents())
		{
			if (button instanceof JButton)
				button.setEnabled(true);

		}
		toolbarView.getBtnDeleteRow().setEnabled(false);
		toolbarView.getBtnEditRow().setEnabled(false);
		toolbarView.getBtnNew().setEnabled(false);

	}

	@Override
	public void setStatusBar()
	{
		StatusBarView statusBarView = (StatusBarView) applicationView.getStatusBar();
		statusBarView.getStateLabel().setText(this.toString());
		if (statusBarView.getModel().getTableDataModel().getTableCells().size() > 0)
		{
			statusBarView.getTableName().setText(statusBarView.getModel().getTableDataModel().getTableName());
			statusBarView.getRowCount().setText((statusBarView.getModel().getTableDataModel().getSelectedRow() + 1) + "/"
					+ statusBarView.getModel().getTableDataModel().getRowCount());
		}

	}

	@Override
	public void setMenuBar()
	{
		MenuBarView menuBarView = (MenuBarView) applicationView.getMenuBar();
		menuBarView.getChangeXML().setEnabled(true);
		menuBarView.getChooseXML().setEnabled(false);
		menuBarView.getExit().setEnabled(true);

		menuBarView.getDelete().setEnabled(true);
		menuBarView.getEditMenuItem().setEnabled(true);
		menuBarView.getNewMenuItem().setEnabled(true);

		menuBarView.getFirst().setEnabled(true);
		menuBarView.getLast().setEnabled(true);
		menuBarView.getNext().setEnabled(true);
		menuBarView.getPrevious().setEnabled(true);

		menuBarView.getAbout().setEnabled(true);
		menuBarView.getCancel().setEnabled(true);
		menuBarView.getAccept().setEnabled(true);

		menuBarView.getChangeState().setEnabled(true);

	}

}