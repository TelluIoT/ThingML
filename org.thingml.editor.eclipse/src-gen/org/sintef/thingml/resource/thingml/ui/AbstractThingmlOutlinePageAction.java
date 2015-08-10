/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.ui;

public abstract class AbstractThingmlOutlinePageAction extends org.eclipse.jface.action.Action {
	
	private String preferenceKey = this.getClass().getSimpleName() + ".isChecked";
	
	private org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageTreeViewer treeViewer;
	
	public AbstractThingmlOutlinePageAction(org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageTreeViewer treeViewer, String text, int style) {
		super(text, style);
		this.treeViewer = treeViewer;
	}
	
	public void initialize(String imagePath) {
		org.eclipse.jface.resource.ImageDescriptor descriptor = org.sintef.thingml.resource.thingml.ui.ThingmlImageProvider.INSTANCE.getImageDescriptor(imagePath);
		setDisabledImageDescriptor(descriptor);
		setImageDescriptor(descriptor);
		setHoverImageDescriptor(descriptor);
		boolean checked = org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin.getDefault().getPreferenceStore().getBoolean(preferenceKey);
		valueChanged(checked, false);
	}
	
	@Override	
	public void run() {
		if (keepState()) {
			valueChanged(isChecked(), true);
		} else {
			runBusy(true);
		}
	}
	
	public void runBusy(final boolean on) {
		org.eclipse.swt.custom.BusyIndicator.showWhile(org.eclipse.swt.widgets.Display.getCurrent(), new Runnable() {
			public void run() {
				runInternal(on);
			}
		});
	}
	
	public abstract void runInternal(boolean on);
	
	private void valueChanged(boolean on, boolean store) {
		setChecked(on);
		runBusy(on);
		if (store) {
			org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin.getDefault().getPreferenceStore().setValue(preferenceKey, on);
		}
	}
	
	public boolean keepState() {
		return true;
	}
	
	public org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageTreeViewer getTreeViewer() {
		return treeViewer;
	}
	
	public org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageTreeViewerComparator getTreeViewerComparator() {
		return (org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageTreeViewerComparator) treeViewer.getComparator();
	}
	
}
