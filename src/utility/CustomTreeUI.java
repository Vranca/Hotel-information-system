package utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.AbstractLayoutCache.NodeDimensions;
import javax.swing.tree.TreePath;

public class CustomTreeUI extends BasicTreeUI
{
	private Icon expandedIcon = null;
	private Icon collapsedIcon = null;

	public CustomTreeUI()
	{
		super();
		Image image = new ImageIcon("icons/expanded-row.png").getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
		expandedIcon = new ImageIcon(image);
		image = new ImageIcon("icons/collapsed-row.png").getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
		collapsedIcon = new ImageIcon(image);
	}

	@Override
	protected NodeDimensions createNodeDimensions()
	{
		return new NodeDimensionsHandler()
		{
			@Override
			public Rectangle getNodeDimensions(Object value, int row, int depth, boolean expanded, Rectangle size)
			{
				Rectangle dimensions = super.getNodeDimensions(value, row, depth, expanded, size);
				dimensions.width -= getRowX(row, depth);
				return dimensions;
			}
		};
	}

	@Override
	public Icon getCollapsedIcon()
	{
		return collapsedIcon;
	}

	@Override
	public Icon getExpandedIcon()
	{
		return expandedIcon;
	}

	@Override
	protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right)
	{
		// do nothing.
	}

	@Override
	protected void paintVerticalPartOfLeg(Graphics g, Rectangle clipBounds, Insets insets, TreePath path)
	{
		// do nothing.
	}

	@Override
	protected void paintRow(
			Graphics g, Rectangle clipBounds, Insets insets, Rectangle bounds, TreePath path, int row, boolean isExpanded,
			boolean hasBeenExpanded, boolean isLeaf
	)
	{
		Graphics2D g2d = (Graphics2D) g.create();

		if (isExpanded)
		{
			g2d.setColor(Color.DARK_GRAY);
		} else
		{
			g2d.setColor(Color.GRAY);
		}
		if (isLeaf)
			g2d.setColor(Color.LIGHT_GRAY);
		Rectangle r = new Rectangle(0, (int) bounds.getY(), (int) bounds.getWidth() + row * 20, (int) bounds.getHeight());
		g2d.fill(r);
		g2d.setColor(Color.BLACK);
		g2d.drawLine(0, (int) (bounds.getY() + bounds.getHeight()), (int) bounds.getWidth() + row * 20,
				(int) (bounds.getY() + bounds.getHeight()));
		g2d.drawLine(0, (int) bounds.getY(), (int) bounds.getWidth() + row * 20, (int) bounds.getY());

		g2d.dispose();

		super.paintExpandControl(g, clipBounds, insets, bounds, path, row, isExpanded, hasBeenExpanded, isLeaf);
		super.paintRow(g, clipBounds, insets, bounds, path, row, isExpanded, hasBeenExpanded, isLeaf);
	}

}