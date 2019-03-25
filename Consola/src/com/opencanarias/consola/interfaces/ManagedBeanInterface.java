/**
 * 
 */
package com.opencanarias.consola.interfaces;

/**
 * @author Tom�s Delgado.
 *
 */
public interface ManagedBeanInterface {
	
	public void findAction();
	public void newAction();
	public void editAction(Object identification);
	public void saveAction();
	public void returnAction();
	public void deleteAction(Object identification);

}
