/***********************************************************************
 * Module:  AbstractSubject.java
 * Author:  User
 * Purpose: Defines the Class AbstractSubject
 ***********************************************************************/

package model;

import view.Subscriber;

public abstract class AbstractSubject implements Subject {
   private java.util.List<Subscriber> subscribers = null;
   
   
   /** @pdGenerated default getter */
   public java.util.List<Subscriber> getSubscribers() {
      if (subscribers == null)
         subscribers = new java.util.Vector<Subscriber>();
      return subscribers;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator<Subscriber> getIteratorSubscribers() {
      if (subscribers == null)
         subscribers = new java.util.Vector<Subscriber>();
      return subscribers.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newSubscribers */
   public void setSubscribers(java.util.List<Subscriber> newSubscribers) {
      removeAllSubscribers();
      for (java.util.Iterator<Subscriber> iter = newSubscribers.iterator(); iter.hasNext();)
         addSubscribers((Subscriber)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newSubscriber */
   public void addSubscribers(Subscriber newSubscriber) {
      if (newSubscriber == null)
         return;
      if (this.subscribers == null)
         this.subscribers = new java.util.Vector<Subscriber>();
      if (!this.subscribers.contains(newSubscriber))
         this.subscribers.add(newSubscriber);
   }
   
   /** @pdGenerated default remove
     * @param oldSubscriber */
   public void removeSubscribers(Subscriber oldSubscriber) {
      if (oldSubscriber == null)
         return;
      if (this.subscribers != null)
         if (this.subscribers.contains(oldSubscriber))
            this.subscribers.remove(oldSubscriber);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllSubscribers() {
      if (subscribers != null)
         subscribers.clear();
   }
   
   /** @param newSubscriber */
   public void addSubscriber(Subscriber newSubscriber) {
	   addSubscribers(newSubscriber);
   }
   
   /** @param subscriber */
   public void removeSubscriber(Subscriber subscriber) {
	   removeSubscribers(subscriber);
   }
   
   public void notifyAllSubscribers() {
	   if ( subscribers != null )
	   {
		   for (Subscriber subscriber : subscribers) 
		   {
			   subscriber.update();
		   }
	   }
   }

}