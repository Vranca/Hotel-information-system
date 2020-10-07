/***********************************************************************
 * Module:  ViewComponent.java
 * Author:  User
 * Purpose: Defines the Class ViewComponent
 ***********************************************************************/

package view;

import java.util.EventListener;

import javax.swing.JComponent;

import model.ComponentModel;

public abstract class ViewComponent implements Subscriber
{
	protected ComponentModel model = null;

	/** @param model */
	public ViewComponent(ComponentModel model)
	{
		this.model = model;
	}

	public ViewComponent()
	{
		// TODO Auto-generated constructor stub
	}

	public JComponent getComponent()
	{
		// TODO: implement
		return null;
	}

	public void update()
	{
		// TODO: implement
	}

	public final ComponentModel getModel()
	{
		return model;
	}

	public final void setModel(ComponentModel model)
	{
		this.model = model;
	}

	abstract public void addEventListener(EventListener listener);

}