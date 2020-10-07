/***********************************************************************
 * Module:  StatusBarView.java
 * Author:  User
 * Purpose: Defines the Class StatusBarView
 ***********************************************************************/

package view;

import java.awt.Dimension;
import java.util.EventListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import model.ComponentModel;
import net.miginfocom.swing.MigLayout;

public class StatusBarView extends ViewComponent
{

	private JPanel statusBar = null;
	JLabel stateLabel = null;
	JLabel tableName = null;
	JLabel rowCount = null;

	public StatusBarView(ComponentModel componentModel)
	{
		super(componentModel);
		statusBar = new JPanel();
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusBar.setPreferredSize(new Dimension(componentModel.getAppModel().getWidth(), 30));
		statusBar.setLayout(new MigLayout());
		statusBar.setVisible(true);
		stateLabel = new JLabel();
		stateLabel.setText(componentModel.getAppModel().getAppState().toString());

		statusBar.add(stateLabel, "align left, pushx");

		tableName = new JLabel();
		statusBar.add(tableName, "center");

		rowCount = new JLabel();
		statusBar.add(rowCount, "align right, pushx");

		statusBar.repaint();
	}

	public JComponent getComponent()
	{
		return statusBar;
	}

	public void update()
	{
		this.getModel().getAppModel().getAppState().setStatusBar();
		statusBar.repaint();
	}

	public final JLabel getStateLabel()
	{
		return stateLabel;
	}

	public final JLabel getTableName()
	{
		return tableName;
	}

	public final JLabel getRowCount()
	{
		return rowCount;
	}

	@Override
	public void addEventListener(EventListener listener)
	{
		// TODO Auto-generated method stub

	}

}