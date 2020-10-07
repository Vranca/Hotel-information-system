package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import applicationState.Disconnected;
import applicationState.WorkingState;
import controller.ComponentConttroller;
import model.ApplicationModel;
import model.ComponentModel;
import model.TreeElement;
import view.ApplicationView;
import view.ViewComponent;

public class CustomTreeSelectionListener extends MouseAdapter implements TreeSelectionListener
{
	Object node = null;
	JTree tree = null;

	private ApplicationView appView = null;
	private ApplicationModel appModel = null;

	public CustomTreeSelectionListener(ApplicationView appView, ApplicationModel appModel)
	{
		this.appView = appView;
		this.appModel = appModel;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e)
	{
		tree = (JTree) e.getSource();
		node = tree.getLastSelectedPathComponent();
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		super.mouseEntered(e);
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		super.mousePressed(e);
		tree = (JTree) e.getSource();
		node = tree.getLastSelectedPathComponent();

		if (tree != null)
		{
			int row = tree.getRowForLocation(e.getX(), e.getY());

			if (row == -1) // Kada se klikne na prazno
			{
				tree.clearSelection();
			} else
			{
				TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
				tree.setSelectionPath(selPath);
			}
		}
		if (e.getSource() instanceof JTree)
		{
			if (e.getClickCount() == 2)
			{
				if (node instanceof TreeElement.Table)
				{
					TreeElement.Table tableNode = (TreeElement.Table) node;

					ViewComponent tableView = appView.getTableView();
					ComponentModel tableModel;
					if (tableView == null)
					{
						tableModel = new ComponentModel(appModel);
						if (tableModel.executeStoredProcedure(tableNode, tableNode.getRetrieveAllSProc(), appModel.getSystemId()))
						{
							appView.constructTable(tableModel);
							appModel.setState(new WorkingState(appView));
							ViewComponent toolbarView = appView.getToolbar();
							ComponentConttroller commandContorller = new ComponentConttroller(tableModel, toolbarView,
									appView.getMenuBar());
							commandContorller.registerEventListener(new CommandActionListener(tableModel));

						} else
						{
							appModel.setState(new Disconnected(appView));
						}
					} else
					{
						tableModel = tableView.getModel();

						if (tableModel.executeStoredProcedure(tableNode, tableNode.getRetrieveAllSProc(), appModel.getSystemId()))
						{
							appModel.setState(new WorkingState(appView));
						} else
						{
							appModel.setState(new Disconnected(appView));
						}
					}
					appModel.notifyAllSubscribers();
				}
			}

		}
	}

}
