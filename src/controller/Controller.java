/***********************************************************************
 * Module:  Controller.java
 * Author:  User
 * Purpose: Defines the Class Controller
 ***********************************************************************/

package controller;

import model.AbstractSubject;
import view.Subscriber;

public class Controller
{
	private java.util.List<Subscriber> view = null;
	@SuppressWarnings("unused")
	private AbstractSubject model = null;

	/** @pdGenerated default getter */
	public java.util.List<Subscriber> getView()
	{
		if (view == null)
			view = new java.util.Vector<Subscriber>();
		return view;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator<Subscriber> getIteratorView()
	{
		if (view == null)
			view = new java.util.Vector<Subscriber>();
		return view.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newView
	 */
	public void setView(java.util.List<Subscriber> newView)
	{
		removeAllView();
		for (java.util.Iterator<Subscriber> iter = newView.iterator(); iter.hasNext();)
			addView((Subscriber) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newSubscriber
	 */
	public void addView(Subscriber newSubscriber)
	{
		if (newSubscriber == null)
			return;
		if (this.view == null)
			this.view = new java.util.Vector<Subscriber>();
		if (!this.view.contains(newSubscriber))
			this.view.add(newSubscriber);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldSubscriber
	 */
	public void removeView(Subscriber oldSubscriber)
	{
		if (oldSubscriber == null)
			return;
		if (this.view != null)
			if (this.view.contains(oldSubscriber))
				this.view.remove(oldSubscriber);
	}

	/** @pdGenerated default removeAll */
	public void removeAllView()
	{
		if (view != null)
			view.clear();
	}

	/**
	 * @param model
	 * @param views
	 */
	public Controller(AbstractSubject model, Subscriber views)
	{
		// TODO: implement
	}

}