/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.ui;

/**
 * This custom implementation of a TreeViewer expands the tree automatically up to
 * a specified depth.
 */
public class ThingmlOutlinePageTreeViewer extends org.eclipse.jface.viewers.TreeViewer {
	
	public class TypeFilter extends org.eclipse.jface.viewers.ViewerFilter {
		
		private java.util.Set<org.eclipse.emf.ecore.EClass> filteredTypes = new java.util.LinkedHashSet<org.eclipse.emf.ecore.EClass>();
		
		@Override		
		public boolean select(org.eclipse.jface.viewers.Viewer viewer, Object parentElement, Object element) {
			if (element instanceof org.eclipse.emf.ecore.EObject) {
				org.eclipse.emf.ecore.EObject eObject = (org.eclipse.emf.ecore.EObject) element;
				for (org.eclipse.emf.ecore.EClass filteredType : filteredTypes) {
					if (filteredType.isInstance(eObject)) {
						return false;
					}
				}
			}
			return true;
		}
		
		public java.util.Set<org.eclipse.emf.ecore.EClass> getFilteredTypes() {
			return filteredTypes;
		}
	}
	
	private boolean suppressNotifications = false;
	
	private boolean linkWithEditor = false;
	
	private boolean autoExpand = false;
	
	private TypeFilter typeFilter = new TypeFilter();
	
	public ThingmlOutlinePageTreeViewer(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		addFilter(typeFilter);
		setComparer(new org.eclipse.jface.viewers.IElementComparer() {
			
			public int hashCode(Object element) {
				String s = toString(element);
				if (s != null) {
					return s.hashCode();
				}
				return element.hashCode();
			}
			
			public boolean equals(Object o1, Object o2) {
				String s1 = toString(o1);
				String s2 = toString(o2);
				if (s1 != null) {
					return s1.equals(s2);
				}
				return o1.equals(o2);
			}
			
			private String toString(Object o) {
				if (o instanceof org.eclipse.emf.ecore.EObject) {
					org.eclipse.emf.ecore.EObject e = (org.eclipse.emf.ecore.EObject) o;
					String uri = getURI(e);
					return uri;
				}
				if (o instanceof String) {
					return (String) o;
				}
				if (o instanceof org.eclipse.emf.ecore.resource.Resource) {
					return ((org.eclipse.emf.ecore.resource.Resource) o).getURI().toString();
				}
				return null;
			}
			
			private String getURI(org.eclipse.emf.ecore.EObject eObject) {
				java.util.List<String> uriFragmentPath = getFragmentPath(eObject);
				String uriFragment = org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.explode(uriFragmentPath, "/");
				return uriFragment;
			}
			
			private java.util.List<String> getFragmentPath(org.eclipse.emf.ecore.EObject eObject) {
				org.eclipse.emf.ecore.InternalEObject internalEObject = (org.eclipse.emf.ecore.InternalEObject) eObject;
				java.util.List<String> uriFragmentPath = new java.util.ArrayList<String>();
				for (org.eclipse.emf.ecore.InternalEObject container = internalEObject.eInternalContainer(); container != null; container = internalEObject.eInternalContainer()) {
					uriFragmentPath.add(0, container.eURIFragmentSegment(internalEObject.eContainingFeature(), internalEObject));
					internalEObject = container;
				}
				return uriFragmentPath;
			}
			
		});
		
	}
	
	public void setSelection(org.eclipse.jface.viewers.ISelection selection, boolean reveal) {
		if (!linkWithEditor) {
			return;
		}
		if (selection instanceof org.sintef.thingml.resource.thingml.ui.ThingmlEObjectSelection) {
			suppressNotifications = true;
			super.setSelection(selection, reveal);
			suppressNotifications = false;
		}
		else {
			super.setSelection(selection, reveal);
		}
	}
	
	protected void handleSelect(org.eclipse.swt.events.SelectionEvent event) {
		if (event.item == null) {
			// In the cases of an invalid document, the tree widget in the outline might fire
			// an event (with item == null) without user interaction. We do not want to react
			// to that event.
		} else {
			super.handleSelect(event);
		}
	}
	
	protected void handleInvalidSelection(org.eclipse.jface.viewers.ISelection selection, org.eclipse.jface.viewers.ISelection newSelection) {
		// this may not fire a selection changed event to avoid cyclic events between
		// editor and outline
	}
	
	public void refresh(Object element, boolean updateLabels) {
		super.refresh(element, updateLabels);
		doAutoExpand();
	}
	
	public void refresh(Object element) {
		super.refresh(element);
		doAutoExpand();
	}
	
	public void refresh() {
		super.refresh();
		doAutoExpand();
	}
	
	public void refresh(boolean updateLabels) {
		super.refresh(updateLabels);
		doAutoExpand();
	}
	
	public void setAutoExpand(boolean autoExpand) {
		this.autoExpand = autoExpand;
	}
	
	public void expandToLevel(int level) {
		// we need to catch exceptions here, because refreshing the outline does sometimes
		// cause the LabelProviders to throw exceptions, if the model is in some
		// inconsistent state.
		try {
			super.expandToLevel(level);
		} catch (Exception e) {
			org.sintef.thingml.resource.thingml.mopp.ThingmlPlugin.logError("Exception while refreshing outline view", e);
		}
	}
	
	protected void fireSelectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent event) {
		if (suppressNotifications == true) {
			return;
		}
		super.fireSelectionChanged(event);
	}
	
	public void setLinkWithEditor(boolean on) {
		this.linkWithEditor = on;
	}
	
	private void doAutoExpand() {
		if (!autoExpand) {
			return;
		}
		expandToLevel(getAutoExpandLevel());
	}
	
	public void addTypeToFilter(org.eclipse.emf.ecore.EClass typeToFilter) {
		typeFilter.getFilteredTypes().add(typeToFilter);
	}
	
	public void removeTypeToFilter(org.eclipse.emf.ecore.EClass typeToNotFilter) {
		typeFilter.getFilteredTypes().remove(typeToNotFilter);
	}
	
}
