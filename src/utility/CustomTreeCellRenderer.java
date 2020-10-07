package utility;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.TreeElement;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer
{
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTreeCellRendererComponent(
			JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus
	)
	{
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		this.setOpaque(false);
		this.setBackground(null);

		if (value instanceof TreeElement.Table)
		{
			Image image = new ImageIcon("img/icons8-table-64.png").getImage();
			setIcon(new ImageIcon(image.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
			setForeground(Color.BLACK);
		} else if (value instanceof TreeElement.Package)
		{
			Image image = new ImageIcon("img/icons8-favorite-package-100.png").getImage();
			setIcon(new ImageIcon(image.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
			setForeground(Color.WHITE);
		} else
		{
			Image image = new ImageIcon("img/icons8-database-64.png").getImage();
			setIcon(new ImageIcon(image.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
			setForeground(Color.WHITE);
		}

		Dimension d = new Dimension(300, 35);
		setPreferredSize(d);
		setOpaque(false);

		return this;
	}

	@Override
	public Color getBackgroundNonSelectionColor()
	{
		return null;
	}

	@Override
	public Color getBackgroundSelectionColor()
	{
		return null;
	}

	@Override
	public Color getBorderSelectionColor()
	{
		return null;
	}

	@Override
	public Color getBackground()
	{
		return null;
	}

}
