/***********************************************************************
 * Module:  Subject.java
 * Author:  User
 * Purpose: Defines the Interface Subject
 ***********************************************************************/

package model;

import view.Subscriber;

public interface Subject
{
	/** @param newSubscriber */
	void addSubscriber(Subscriber newSubscriber);

	/** @param subscriber */
	void removeSubscriber(Subscriber subscriber);

	void notifyAllSubscribers();

}