/***********************************************************************
 * Module:  ToolbarView.java
 * Author:  User
 * Purpose: Defines the Class ToolbarView
 ***********************************************************************/

package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import model.ComponentModel;
import model.TableDataModel;
import model.TreeElement;

public class ToolbarView extends ViewComponent
{
	private JToolBar toolBar = null;
	JButton btnFirst;
	JButton btnLast;
	JButton btnPrev;
	JButton btnNext;
	JButton btnNew;
	JButton btnEditRow;
	JButton btnDeleteRow;
	JButton btnFilter;
	JButton btnGenerate;
	JButton reconnectButton;

	public final JButton getReconnectButton()
	{
		return reconnectButton;
	}

	public ToolbarView()
	{
		toolBar = new JToolBar();
		toolBar.setBackground(Color.LIGHT_GRAY);
		toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));

		addControlButtons();
		addTableButtons();
		addConfirmationButtons();
		addReportButtons();

		toolBar.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		toolBar.setFloatable(false);
	}

	public ToolbarView(ComponentModel model)
	{
		super(model);

		toolBar = new JToolBar();
		toolBar.setBackground(Color.LIGHT_GRAY);
		toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));

		addControlButtons();
		addTableButtons();
		addConfirmationButtons();
		addReportButtons();

		toolBar.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		toolBar.setFloatable(false);
	}

	public JComponent getComponent()
	{
		return toolBar;
	}

	public void update()
	{
		if (model != null)
		{
			model.getAppModel().getAppState().setToolbar();
		}
		if (model.getTableDataModel().getXmlTable() != null)
		{
			setUserPrivileges();
		}

		this.toolBar.repaint();
	}

	private void setUserPrivileges()
	{

		TreeElement.Table activeTable = model.getTableDataModel().getXmlTable();
		TreeElement.Package parentPackege = model.getXmlModel().getPackage((TreeElement) model.getXmlModel().getRoot(), activeTable);
		TableDataModel privileges = model.getAppModel().getUserDataModel().getPrivilegesDataModel();
		getBtnDeleteRow().setEnabled(false);
		getBtnEditRow().setEnabled(false);
		getBtnNew().setEnabled(false);
		for (int row = 0; row < privileges.getRowCount(); row++)
		{
			if (parentPackege.getName().equals(privileges.getValueAt(row, 3)))
			{
				if (privileges.getRealValueAt(row, 1).toString().contains("c"))
				{
					getBtnNew().setEnabled(true);
				} else if (privileges.getRealValueAt(row, 1).toString().contains("u"))
				{
					getBtnEditRow().setEnabled(true);
				} else if (privileges.getRealValueAt(row, 1).toString().contains("d"))
				{
					getBtnDeleteRow().setEnabled(true);
				}

			}
		}
	}

	private void addControlButtons()
	{
		btnFirst = new JButton();
		btnFirst.setToolTipText("First");
		btnFirst.setIcon(new ImageIcon(new ImageIcon("icons/first.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
		btnFirst.setBorder(new EmptyBorder(1, 1, 1, 1));
		btnFirst.setOpaque(false);
		btnFirst.setBackground(null);
		btnFirst.setFocusPainted(false);
		btnFirst.setActionCommand("First");

		btnLast = new JButton();
		btnLast.setToolTipText("Last");
		btnLast.setIcon(new ImageIcon(new ImageIcon("icons/last.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
		btnLast.setBorder(new EmptyBorder(1, 1, 1, 1));
		btnLast.setOpaque(false);
		btnLast.setBackground(null);
		btnLast.setFocusPainted(false);
		btnLast.setActionCommand("Last");

		btnPrev = new JButton();
		btnPrev.setToolTipText("Previous");
		btnPrev.setIcon(new ImageIcon(new ImageIcon("icons/previous.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
		btnPrev.setBorder(new EmptyBorder(1, 1, 1, 1));
		btnPrev.setOpaque(false);
		btnPrev.setBackground(null);
		btnPrev.setFocusPainted(false);
		btnPrev.setActionCommand("Previous");

		btnNext = new JButton();
		btnNext.setToolTipText("Next");
		btnNext.setIcon(new ImageIcon(new ImageIcon("icons/next.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
		btnNext.setBorder(new EmptyBorder(1, 1, 1, 1));
		btnNext.setOpaque(false);
		btnNext.setBackground(null);
		btnNext.setFocusPainted(false);
		btnNext.setActionCommand("Next");

		toolBar.add(btnFirst);
		toolBar.add(Box.createHorizontalStrut(2));
		toolBar.add(btnPrev);
		toolBar.add(Box.createHorizontalStrut(2));
		toolBar.add(btnNext);
		toolBar.add(Box.createHorizontalStrut(2));
		toolBar.add(btnLast);
		toolBar.add(Box.createHorizontalStrut(10));
		toolBar.addSeparator();
	}

	private void addTableButtons()
	{
		btnNew = new JButton();
		btnNew.setToolTipText("New");
		btnNew.setIcon(new ImageIcon("icons/add-row.png"));
		btnNew.setBorder(new EmptyBorder(1, 1, 1, 1));
		btnNew.setOpaque(false);
		btnNew.setBackground(null);
		btnNew.setFocusPainted(false);
		btnNew.setActionCommand("New");

		btnEditRow = new JButton();
		btnEditRow.setToolTipText("Edit Row");
		btnEditRow.setIcon(new ImageIcon("icons/edit-row.png"));
		btnEditRow.setBorder(new EmptyBorder(1, 1, 3, 1));
		btnEditRow.setOpaque(false);
		btnEditRow.setBackground(null);
		btnEditRow.setFocusPainted(false);
		btnEditRow.setActionCommand("Edit");

		btnDeleteRow = new JButton();
		btnDeleteRow.setToolTipText("Delete Row");
		btnDeleteRow.setIcon(new ImageIcon("icons/delete-row.png"));
		btnDeleteRow.setBorder(new EmptyBorder(1, 1, 1, 1));
		btnDeleteRow.setOpaque(false);
		btnDeleteRow.setBackground(null);
		btnDeleteRow.setFocusPainted(false);
		btnDeleteRow.setActionCommand("Delete");

		toolBar.add(btnNew);
		toolBar.add(Box.createHorizontalStrut(2));
		toolBar.add(btnEditRow);
		toolBar.add(Box.createHorizontalStrut(2));
		toolBar.add(btnDeleteRow);
		toolBar.add(Box.createHorizontalStrut(10));
		toolBar.addSeparator();
	}

	private void addConfirmationButtons()
	{
		btnFilter = new JButton();
		btnFilter.setToolTipText("Filter");
		btnFilter.setIcon(new ImageIcon("icons/filter.png"));
		btnFilter.setBorder(new EmptyBorder(1, 1, 1, 1));
		btnFilter.setOpaque(false);
		btnFilter.setBackground(null);
		btnFilter.setFocusPainted(false);
		btnFilter.setActionCommand("Filter");

		toolBar.add(btnFilter);
		toolBar.add(Box.createHorizontalStrut(10));
		toolBar.addSeparator();
	}

	private void addReportButtons()
	{
		btnGenerate = new JButton();
		btnGenerate.setToolTipText("Generate report");
		btnGenerate.setIcon(new ImageIcon("icons/report.png"));
		btnGenerate.setBorder(new EmptyBorder(1, 1, 1, 1));
		btnGenerate.setOpaque(false);
		btnGenerate.setBackground(null);
		btnGenerate.setFocusPainted(false);
		btnGenerate.setActionCommand("Report");

		toolBar.add(btnGenerate);
	}

	public final JButton getBtnFirst()
	{
		return btnFirst;
	}

	public final JButton getBtnLast()
	{
		return btnLast;
	}

	public final JButton getBtnPrev()
	{
		return btnPrev;
	}

	public final JButton getBtnNext()
	{
		return btnNext;
	}

	public final JButton getBtnNew()
	{
		return btnNew;
	}

	public final JButton getBtnEditRow()
	{
		return btnEditRow;
	}

	public final JButton getBtnDeleteRow()
	{
		return btnDeleteRow;
	}

	public final JButton getBtnFilter()
	{
		return btnFilter;
	}

	public final JButton getBtnGenerate()
	{
		return btnGenerate;
	}

	@Override
	public void addEventListener(EventListener listener)
	{
		btnFirst.addActionListener((ActionListener) listener);
		btnLast.addActionListener((ActionListener) listener);
		btnPrev.addActionListener((ActionListener) listener);
		btnNext.addActionListener((ActionListener) listener);
		btnNew.addActionListener((ActionListener) listener);
		btnEditRow.addActionListener((ActionListener) listener);
		btnDeleteRow.addActionListener((ActionListener) listener);
		btnFilter.addActionListener((ActionListener) listener);
		btnGenerate.addActionListener((ActionListener) listener);
	}

}