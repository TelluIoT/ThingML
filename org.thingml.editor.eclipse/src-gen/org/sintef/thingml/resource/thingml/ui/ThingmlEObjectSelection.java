/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.ui;

public class ThingmlEObjectSelection implements org.eclipse.jface.viewers.IStructuredSelection {
	
	private final org.eclipse.emf.ecore.EObject selectedObject;
	private final boolean highlighting;
	
	public ThingmlEObjectSelection(org.eclipse.emf.ecore.EObject selectedObject, boolean highlighting) {
		super();
		this.selectedObject = selectedObject;
		this.highlighting = highlighting;
	}
	
	public org.eclipse.emf.ecore.EObject getSelectedObject() {
		return selectedObject;
	}
	
	public boolean doHighlighting() {
		return highlighting;
	}
	
	public boolean isEmpty() {
		return false;
	}
	
	public Object getFirstElement() {
		return selectedObject;
	}
	
	public java.util.Iterator<?> iterator() {
		return new java.util.Iterator<org.eclipse.emf.ecore.EObject>() {
			
			private boolean hasNext = true;
			
			public boolean hasNext() {
				return hasNext;
			}
			
			public org.eclipse.emf.ecore.EObject next(){
				hasNext = false;
				return selectedObject;
			}
			
			public void remove() {
			}
		};
	}
	
	public int size() {
		return 1;
	}
	
	public Object[] toArray() {
		return new Object[] {selectedObject};
	}
	
	public java.util.List<?> toList() {
		java.util.ArrayList<org.eclipse.emf.ecore.EObject> list = new java.util.ArrayList<org.eclipse.emf.ecore.EObject>();
		list.add(selectedObject);
		return list;
	}
}
