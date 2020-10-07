/***********************************************************************
 * Module:  CommandHistory.java
 * Author:  User
 * Purpose: Defines the Class CommandHistory
 ***********************************************************************/

package command;

import java.util.*;

public class CommandHistory {
   private java.util.List<Command> history = null;
   
   
   /** @pdGenerated default getter */
   public java.util.List<Command> getHistory() {
      if (history == null)
         history = new java.util.Stack<Command>();
      return history;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorHistory() {
      if (history == null)
         history = new java.util.Stack<Command>();
      return history.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newHistory */
   public void setHistory(java.util.List<Command> newHistory) {
      removeAllHistory();
      for (java.util.Iterator iter = newHistory.iterator(); iter.hasNext();)
         addHistory((Command)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newCommand */
   public void addHistory(Command newCommand) {
      if (newCommand == null)
         return;
      if (this.history == null)
         this.history = new java.util.Stack<Command>();
      if (!this.history.contains(newCommand))
         this.history.add(newCommand);
   }
   
   /** @pdGenerated default remove
     * @param oldCommand */
   public void removeHistory(Command oldCommand) {
      if (oldCommand == null)
         return;
      if (this.history != null)
         if (this.history.contains(oldCommand))
            this.history.remove(oldCommand);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllHistory() {
      if (history != null)
         history.clear();
   }
   
   /** @param command */
   public void push(Command command) {
      // TODO: implement
   }
   
   public Command pop() {
      // TODO: implement
      return null;
   }

}