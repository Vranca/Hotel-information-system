/***********************************************************************
 * Module:  ComponentConttroller.java
 * Author:  User
 * Purpose: Defines the Class ComponentConttroller
 ***********************************************************************/

package controller;

import java.util.EventListener;

import model.ComponentModel;
import view.ViewComponent;

public class ComponentConttroller
{
	protected java.util.List<ViewComponent> viewComponent;
	protected ComponentModel componentModel;

	public ComponentConttroller(ComponentModel model, ViewComponent... views)
	{
		componentModel = model;
		for (ViewComponent vc : views)
		{
			addViewComponent(vc);
			componentModel.addSubscriber(vc);
		}
	}

	public void registerEventListener(EventListener listener)
	{
		for (ViewComponent vc : viewComponent)
		{
			vc.addEventListener(listener);
		}
	}

	/** @pdGenerated default getter */
	public java.util.List<ViewComponent> getViewComponent()
	{
		if (viewComponent == null)
			viewComponent = new java.util.ArrayList<ViewComponent>();
		return viewComponent;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator<ViewComponent> getIteratorViewComponent()
	{
		if (viewComponent == null)
			viewComponent = new java.util.ArrayList<ViewComponent>();
		return viewComponent.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newViewComponent
	 */
	public void setViewComponent(java.util.List<ViewComponent> newViewComponent)
	{
		removeAllViewComponent();
		for (java.util.Iterator<ViewComponent> iter = newViewComponent.iterator(); iter.hasNext();)
			addViewComponent((ViewComponent) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newViewComponent
	 */
	public void addViewComponent(ViewComponent newViewComponent)
	{
		if (newViewComponent == null)
			return;
		if (this.viewComponent == null)
			this.viewComponent = new java.util.ArrayList<ViewComponent>();
		if (!this.viewComponent.contains(newViewComponent))
			this.viewComponent.add(newViewComponent);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldViewComponent
	 */
	public void removeViewComponent(ViewComponent oldViewComponent)
	{
		if (oldViewComponent == null)
			return;
		if (this.viewComponent != null)
			if (this.viewComponent.contains(oldViewComponent))
				this.viewComponent.remove(oldViewComponent);
	}

	/** @pdGenerated default removeAll */
	public void removeAllViewComponent()
	{
		if (viewComponent != null)
			viewComponent.clear();
	}

}