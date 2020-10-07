package utility;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import model.TableDataModel;

public class CustomTable extends JTable
{
	private static final long serialVersionUID = 1L;

	private Border outside = null;
	private Border inside = null;
	private Border highlight = null;
	private Color alternateColor = null;
	private Color selectionColor = null;
	private Color headerBackgroundColor = null;
	private Color headerForegroundColor = null;

	private TableDataModel model = null;

	public CustomTable(TableDataModel model)
	{
		super(model);

		this.model = model;
		outside = new MatteBorder(1, 0, 1, 0, Color.BLUE);
		inside = new EmptyBorder(0, 1, 0, 1);
		highlight = new CompoundBorder(outside, inside);
		alternateColor = new Color(225, 225, 225);
		selectionColor = new Color(170, 170, 220);
		headerBackgroundColor = new Color(65, 65, 65);
		headerForegroundColor = Color.WHITE;

		JTableHeader header = getTableHeader();
		header.setBackground(headerBackgroundColor);
		header.setForeground(headerForegroundColor);

		setColumnSelectionAllowed(false);
		setRowSelectionAllowed(true);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setRowHeight(20);
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
	{
		Component comp = super.prepareRenderer(renderer, row, column);
		JComponent jComp = (JComponent) comp;

		if (row == model.getSelectedRow())
		{
			jComp.setBorder(highlight);
			comp.setBackground(selectionColor);

		} else
		{
			Color c = null;

			c = ((row + 1) % 2 == 0 ? alternateColor : Color.WHITE);

			comp.setBackground(c);
			c = null;
		}
		return comp;
	}

}
