/***********************************************************************
 * Module:  ApplicationView.java
 * Author:  User
 * Purpose: Defines the Class ApplicationView
 ***********************************************************************/

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.ListSelectionModel;

import listeners.CustomTableSelectionListener;
import listeners.CustomTreeSelectionListener;
import model.ApplicationModel;
import model.ComponentModel;
import model.XMLModel;

public class ApplicationView implements Subscriber
{
	private ApplicationModel model = null;
	private JFrame frame = null;

	private ViewComponent tableView;
	private ViewComponent applicationBrowser;
	private ViewComponent menuBar;
	private ViewComponent toolbar;
	private ViewComponent statusBar;

	public ApplicationView(ApplicationModel model)
	{
		this.model = model;

		constructFrame();

		/*************************************************************/
		// TEST //
		/*************************************************************/

		XMLModel xmlModel = new XMLModel(null, this.model.getUserDataModel());
		ComponentModel browserModel = new ComponentModel(this.model);
		browserModel.getTableDataModel().setXmlModel(xmlModel);
		applicationBrowser = new ApplicationBrowser(browserModel);
		((ApplicationBrowser) applicationBrowser).addTreeListener(new CustomTreeSelectionListener(this, this.model));

		ComponentModel statusModel = new ComponentModel(this.model);
		ComponentModel toolBarModel = new ComponentModel(this.model);
		ComponentModel menuBarModel = new ComponentModel(this.model);

		menuBar = new MenuBarView(menuBarModel);
		toolbar = new ToolbarView(toolBarModel);
		statusBar = new StatusBarView(statusModel);

		model.addSubscriber(statusBar);
		model.addSubscriber(toolbar);
		model.addSubscriber(menuBar);

		frame.getContentPane().add(toolbar.getComponent(), BorderLayout.NORTH);
		frame.getContentPane().add(applicationBrowser.getComponent(), BorderLayout.WEST);
		frame.setJMenuBar((JMenuBar) menuBar.getComponent());
		frame.getContentPane().add(statusBar.getComponent(), BorderLayout.SOUTH);
		/*************************************************************/
		// TEST //
		/*************************************************************/

		frame.setVisible(true);
	}

	public final ApplicationModel getModel()
	{
		return model;
	}

	private void constructFrame()
	{
		frame = new JFrame();

		frame.setTitle("Hotelski informacioni sistem");
		frame.setLayout(new BorderLayout());
		frame.setSize(model.getWidth(), model.getHeight());
		frame.setMinimumSize(new Dimension(model.getMinWidth(), model.getMinHeight()));
		frame.setLocation(50, 50);
		frame.setIconImage(new ImageIcon("icons/icons8-hotel-building-16.png").getImage());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void constructTable(ComponentModel tableModel)
	{
		tableView = new TableDataView(tableModel);
		tableModel.addSubscriber(tableView);

		ListSelectionModel listSelectionModel = ((TableDataView) tableView).getTable().getSelectionModel();
		listSelectionModel.addListSelectionListener(new CustomTableSelectionListener(tableModel, (TableDataView) tableView));

		statusBar.setModel(tableModel);
		tableModel.addSubscriber(statusBar);
		toolbar.setModel(tableModel);
		tableModel.addSubscriber(toolbar);

		model.addSubscriber(statusBar);

		frame.getContentPane().add(tableView.getComponent(), BorderLayout.CENTER);
		frame.revalidate();
	}

	public void update()
	{
		// TODO: implement
	}

	public ViewComponent getTableView()
	{
		return tableView;
	}

	public ViewComponent getApplicationBrowser()
	{
		return applicationBrowser;
	}

	public ViewComponent getMenuBar()
	{
		return menuBar;
	}

	public ViewComponent getToolbar()
	{
		return toolbar;
	}

	public ViewComponent getStatusBar()
	{
		return statusBar;
	}

	public JFrame getFrame()
	{
		return frame;
	}

}