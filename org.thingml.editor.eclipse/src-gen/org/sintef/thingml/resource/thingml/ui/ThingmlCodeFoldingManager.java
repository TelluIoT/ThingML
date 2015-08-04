/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.ui;

/**
 * This manager adds new projection annotations for the code folding and deletes
 * old projection annotations with lines < 3. It is needed to hold the toggle
 * states. It provides the ability to restore the toggle states between Eclipse
 * sessions and after closing, opening as well.
 */
public class ThingmlCodeFoldingManager {
	
	private class FoldingUpdateListener implements org.sintef.thingml.resource.thingml.IThingmlBackgroundParsingListener {
		public void parsingCompleted(org.eclipse.emf.ecore.resource.Resource resource) {
			calculatePositions();
		}
	}
	
	private class EditorOnCloseListener implements org.eclipse.ui.IPartListener2 {
		
		private org.sintef.thingml.resource.thingml.ui.ThingmlEditor editor;
		
		public EditorOnCloseListener(org.sintef.thingml.resource.thingml.ui.ThingmlEditor editor) {
			this.editor = editor;
		}
		
		public void partActivated(org.eclipse.ui.IWorkbenchPartReference partRef) {
		}
		
		public void partBroughtToTop(org.eclipse.ui.IWorkbenchPartReference partRef) {
		}
		
		public void partClosed(org.eclipse.ui.IWorkbenchPartReference partRef) {
			if (partRef.isDirty()) {
				return;
			}
			org.eclipse.ui.IWorkbenchPart workbenchPart = partRef.getPart(false);
			if (workbenchPart instanceof org.sintef.thingml.resource.thingml.ui.ThingmlEditor) {
				org.sintef.thingml.resource.thingml.ui.ThingmlEditor editor = (org.sintef.thingml.resource.thingml.ui.ThingmlEditor) workbenchPart;
				org.eclipse.emf.ecore.resource.Resource editorResource = editor.getResource();
				if (editorResource == null) {
					return;
				}
				String uri = editorResource.getURI().toString();
				org.eclipse.emf.ecore.resource.Resource thisEditorResource = this.editor.getResource();
				org.eclipse.emf.common.util.URI thisEditorResourceURI = thisEditorResource.getURI();
				if (uri.equals(thisEditorResourceURI.toString())) {
					saveCodeFoldingStateFile(uri);
					editor.getSite().getPage().removePartListener(this);
				}
			}
		}
		
		public void partDeactivated(org.eclipse.ui.IWorkbenchPartReference partRef) {
		}
		
		public void partHidden(org.eclipse.ui.IWorkbenchPartReference partRef) {
		}
		
		public void partInputChanged(org.eclipse.ui.IWorkbenchPartReference partRef) {
		}
		
		public void partOpened(org.eclipse.ui.IWorkbenchPartReference partRef) {
		}
		
		public void partVisible(org.eclipse.ui.IWorkbenchPartReference partRef) {
		}
		
	}
	
	private static final String VERIFY_KEY = "verify_key";
	private static final String ANNOTATION = "ANNOTATION";
	private static final String IS_COLLAPSED = "IS_COLLAPSED";
	private static final String OFFSET = "OFFSET";
	private static final String LENGTH = "LENGTH";
	private static final String MODEL = "MODEL";
	protected java.util.List<org.eclipse.jface.text.source.projection.ProjectionAnnotation> oldAnnotations = new java.util.ArrayList<org.eclipse.jface.text.source.projection.ProjectionAnnotation>();
	protected java.util.Map<org.eclipse.jface.text.source.projection.ProjectionAnnotation, org.eclipse.jface.text.Position> additions = new java.util.LinkedHashMap<org.eclipse.jface.text.source.projection.ProjectionAnnotation, org.eclipse.jface.text.Position>();
	protected org.eclipse.jface.text.source.projection.ProjectionAnnotationModel projectionAnnotationModel;
	protected org.eclipse.jface.text.source.projection.ProjectionViewer sourceViewer;
	protected org.sintef.thingml.resource.thingml.ui.ThingmlEditor editor;
	
	/**
	 * Creates a code folding manager to handle the
	 * <code>org.eclipse.jface.text.source.projection.ProjectionAnnotation</code>.
	 * 
	 * @param sourceViewer the source viewer to calculate the element lines
	 */
	public ThingmlCodeFoldingManager(org.eclipse.jface.text.source.projection.ProjectionViewer sourceViewer,org.sintef.thingml.resource.thingml.ui.ThingmlEditor textEditor) {
		this.projectionAnnotationModel = sourceViewer.getProjectionAnnotationModel();
		this.sourceViewer = sourceViewer;
		this.editor = textEditor;
		addCloseListener(textEditor);
		try {
			restoreCodeFoldingStateFromFile(editor.getResource().getURI().toString());
		} catch (Exception e) {
			calculatePositions();
		}
	}
	
	private void addCloseListener(final org.sintef.thingml.resource.thingml.ui.ThingmlEditor editor) {
		editor.getSite().getPage().addPartListener(new EditorOnCloseListener(editor));
		editor.addBackgroundParsingListener(new FoldingUpdateListener());
	}
	
	/**
	 * Checks whether the given positions are in the
	 * <code>org.eclipse.jface.text.source.projection.ProjectionAnnotationModel</code>
	 * or in the addition set. If not it tries to add into <code>additions</code>.
	 * Deletes old org.eclipse.jface.text.source.projection.ProjectionAnnotation with
	 * line count less than 2.
	 * 
	 * @param positions a list of available foldable positions
	 */
	public void updateCodefolding(java.util.List<org.eclipse.jface.text.Position> positions) {
		org.eclipse.jface.text.IDocument document = sourceViewer.getDocument();
		if (document == null) {
			return;
		}
		oldAnnotations.clear();
		java.util.Iterator<?> annotationIterator = projectionAnnotationModel.getAnnotationIterator();
		while (annotationIterator.hasNext()) {
			oldAnnotations.add((org.eclipse.jface.text.source.projection.ProjectionAnnotation) annotationIterator.next());
		}
		// Add new Position with a unique line offset
		for (org.eclipse.jface.text.Position position : positions) {
			if (!isInAdditions(position)) {
				addPosition(position);
			}
		}
		projectionAnnotationModel.modifyAnnotations(oldAnnotations.toArray(new org.eclipse.jface.text.source.Annotation[0]), additions, null);
		additions.clear();
	}
	
	/**
	 * Checks the offset of the given <code>org.eclipse.jface.text.Position</code>
	 * against the <code>org.eclipse.jface.text.Position</code>s in
	 * <code>additions</code> to determine the existence whether the given position is
	 * contained in the additions set.
	 * 
	 * @param position the position to check
	 * 
	 * @return <code>true</code> if it is in the <code>additions</code>
	 */
	private boolean isInAdditions(org.eclipse.jface.text.Position position) {
		for (org.eclipse.jface.text.source.Annotation addition : additions.keySet()) {
			org.eclipse.jface.text.Position additionPosition = additions.get(addition);
			if (position.offset == additionPosition.offset && position.length == additionPosition.length) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tries to add this position into the model. Only positions with more than 3
	 * lines can be taken in. If multiple positions exist on the same line, the
	 * longest will be chosen. The shorter ones will be deleted.
	 * 
	 * @param position the position to be added.
	 */
	private void addPosition(org.eclipse.jface.text.Position position) {
		org.eclipse.jface.text.IDocument document = sourceViewer.getDocument();
		int lines = 0;
		try {
			lines = document.getNumberOfLines(position.offset, position.length);
		} catch (org.eclipse.jface.text.BadLocationException e) {
			e.printStackTrace();
			return;
		}
		if (lines < 3) {
			return;
		}
		
		// if a position to add existed on the same line, the longest one will be chosen
		try {
			for (org.eclipse.jface.text.source.projection.ProjectionAnnotation annotationToAdd : additions.keySet()) {
				org.eclipse.jface.text.Position positionToAdd = additions.get(annotationToAdd);
				if (document.getLineOfOffset(position.offset) == document.getLineOfOffset(positionToAdd.offset)) {
					if (positionToAdd.length < position.length) {
						additions.remove(annotationToAdd);
					} else {
						return;
					}
				}
			}
		} catch (org.eclipse.jface.text.BadLocationException e) {
			return;
		}
		for (org.eclipse.jface.text.source.projection.ProjectionAnnotation annotationInModel : oldAnnotations) {
			org.eclipse.jface.text.Position positionInModel = projectionAnnotationModel.getPosition(annotationInModel);
			if (position.offset == positionInModel.offset && position.length == positionInModel.length) {
				oldAnnotations.remove(annotationInModel);
				return;
			}
		}
		
		additions.put(new org.eclipse.jface.text.source.projection.ProjectionAnnotation(), position);
	}
	
	/**
	 * Saves the code folding state into the given memento.
	 */
	public void saveCodeFolding(org.eclipse.ui.IMemento memento) {
		java.util.Iterator<?> annotationIt = projectionAnnotationModel.getAnnotationIterator();
		while (annotationIt.hasNext()) {
			org.eclipse.jface.text.source.projection.ProjectionAnnotation annotation = (org.eclipse.jface.text.source.projection.ProjectionAnnotation) annotationIt.next();
			org.eclipse.ui.IMemento annotationMemento = memento.createChild(ANNOTATION);
			org.eclipse.jface.text.Position position = projectionAnnotationModel.getPosition(annotation);
			annotationMemento.putBoolean(IS_COLLAPSED, annotation.isCollapsed());
			annotationMemento.putInteger(OFFSET, position.offset);
			annotationMemento.putInteger(LENGTH, position.length);
		}
	}
	
	/**
	 * Restores the code folding state information from the given memento.
	 */
	public void restoreCodeFolding(org.eclipse.ui.IMemento memento) {
		if (memento == null) {
			return;
		}
		org.eclipse.ui.IMemento[] annotationMementos = memento.getChildren(ANNOTATION);
		if (annotationMementos == null) {
			return;
		}
		java.util.Map<org.eclipse.jface.text.source.projection.ProjectionAnnotation, Boolean> collapsedStates = new java.util.LinkedHashMap<org.eclipse.jface.text.source.projection.ProjectionAnnotation, Boolean>();
		for (org.eclipse.ui.IMemento annotationMemento : annotationMementos) {
			org.eclipse.jface.text.source.projection.ProjectionAnnotation annotation = new org.eclipse.jface.text.source.projection.ProjectionAnnotation();
			collapsedStates.put(annotation, annotationMemento.getBoolean(IS_COLLAPSED));
			int offset = annotationMemento.getInteger(OFFSET);
			int length = annotationMemento.getInteger(LENGTH);
			org.eclipse.jface.text.Position position = new org.eclipse.jface.text.Position(offset, length);
			projectionAnnotationModel.addAnnotation(annotation, position);
		}
		// postset collapse state to prevent wrong displaying folding code.
		for (org.eclipse.jface.text.source.projection.ProjectionAnnotation annotation : collapsedStates.keySet()) {
			Boolean isCollapsed = collapsedStates.get(annotation);
			if (isCollapsed != null && isCollapsed.booleanValue()) {
				projectionAnnotationModel.collapse(annotation);
			}
		}
	}
	
	/**
	 * Restores the code folding state from a XML file in the state location.
	 * 
	 * @param uriString the key to determine the file to load the state from
	 */
	public void restoreCodeFoldingStateFromFile(String uriString) {
		final java.io.File stateFile = getCodeFoldingStateFile(uriString);
		if (stateFile == null || !stateFile.exists()) {
			calculatePositions();
			return;
		}
		org.eclipse.core.runtime.SafeRunner.run(new org.eclipse.jface.util.SafeRunnable("Unable to read code folding state. The state will be reset.") {
			public void run() throws Exception {
				java.io.FileInputStream input = new java.io.FileInputStream(stateFile);
				java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(input, "utf-8"));
				org.eclipse.ui.IMemento memento = org.eclipse.ui.XMLMemento.createReadRoot(reader);
				reader.close();
				String sourceText = sourceViewer.getDocument().get();
				if (memento.getString(VERIFY_KEY).equals(makeMD5(sourceText))) {
					restoreCodeFolding(memento);
				} else {
					calculatePositions();
				}
			}
		});
	}
	
	/**
	 * Saves the code folding state to a XML file in the state location.
	 * 
	 * @param uriString the key to determine the file to save to
	 */
	public void saveCodeFoldingStateFile(String uriString) {
		org.eclipse.jface.text.IDocument document = sourceViewer.getDocument();
		if (document == null) {
			return;
		}
		org.eclipse.ui.XMLMemento codeFoldingMemento = org.eclipse.ui.XMLMemento.createWriteRoot(MODEL);
		codeFoldingMemento.putString(VERIFY_KEY, makeMD5(document.get()));
		saveCodeFolding(codeFoldingMemento);
		java.io.File stateFile = getCodeFoldingStateFile(uriString);
		if (stateFile == null) {
			return;
		}
		try {
			java.io.FileOutputStream stream = new java.io.FileOutputStream(stateFile);
			java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(stream, "utf-8");
			codeFoldingMemento.save(writer);
			writer.close();
		} catch (java.io.IOException e) {
			stateFile.delete();
			org.eclipse.jface.dialogs.MessageDialog.openError((org.eclipse.swt.widgets.Shell) null, "Saving Problems", "Unable to save code folding state.");
		}
	}
	
	private java.io.File getCodeFoldingStateFile(String uriString) {
		org.osgi.framework.Bundle bundle = org.eclipse.core.runtime.Platform.getBundle(org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin.PLUGIN_ID);
		org.eclipse.core.runtime.IPath path = org.eclipse.core.runtime.Platform.getStateLocation(bundle);
		if (path == null) {
			return null;
		}
		path = path.append(makeMD5(uriString) + ".xml");
		return path.toFile();
	}
	
	private String makeMD5(String text) {
		java.security.MessageDigest md = null;
		byte[] encryptMsg = null;
		try {
			md = java.security.MessageDigest.getInstance("MD5");
			encryptMsg = md.digest(text.getBytes());
		} catch (java.security.NoSuchAlgorithmException e) {
			org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin.logError("NoSuchAlgorithmException while creating MD5 checksum.", e);
			return "";
		}
		String swap = "";
		String byteStr = "";
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i <= encryptMsg.length - 1; i++) {
			byteStr = Integer.toHexString(encryptMsg[i]);
			switch (byteStr.length()) {
				case 1:				// if hex-number length is 1, add a '0' before
				swap = "0" + Integer.toHexString(encryptMsg[i]);
				break;
				case 2:				// correct hex-letter
				swap = Integer.toHexString(encryptMsg[i]);
				break;
				case 8:				// get the correct substring
				swap = (Integer.toHexString(encryptMsg[i])).substring(6, 8);
				break;
			}
			strBuf.append(swap);
			// appending swap to get complete hash-key
		}
		return strBuf.toString();
	}
	
	protected void calculatePositions() {
		org.sintef.thingml.resource.thingml.IThingmlTextResource textResource = (org.sintef.thingml.resource.thingml.IThingmlTextResource) editor.getResource();
		org.eclipse.jface.text.IDocument document = sourceViewer.getDocument();
		if (textResource == null || document == null) {
			return;
		}
		org.eclipse.emf.common.util.EList<?> errorList = textResource.getErrors();
		if (errorList != null && errorList.size() > 0) {
			return;
		}
		final java.util.List<org.eclipse.jface.text.Position> positions = new java.util.ArrayList<org.eclipse.jface.text.Position>();
		org.sintef.thingml.resource.thingml.IThingmlLocationMap locationMap = textResource.getLocationMap();
		org.eclipse.emf.ecore.EClass[] foldableClasses = textResource.getMetaInformation().getFoldableClasses();
		if (foldableClasses == null) {
			return;
		}
		if (foldableClasses.length < 1) {
			return;
		}
		java.util.List<org.eclipse.emf.ecore.EObject> contents = textResource.getContents();
		org.eclipse.emf.ecore.EObject[] contentArray = contents.toArray(new org.eclipse.emf.ecore.EObject[0]);
		java.util.List<org.eclipse.emf.ecore.EObject> allContents = getAllContents(contentArray);
		for (org.eclipse.emf.ecore.EObject nextObject : allContents) {
			boolean isFoldable = false;
			for (org.eclipse.emf.ecore.EClass eClass : foldableClasses) {
				if (nextObject.eClass().equals(eClass)) {
					isFoldable = true;
					break;
				}
			}
			if (!isFoldable) {
				continue;
			}
			int offset = locationMap.getCharStart(nextObject);
			int length = locationMap.getCharEnd(nextObject) + 1 - offset;
			try {
				int lines = document.getNumberOfLines(offset, length);
				if (lines < 2) {
					continue;
				}
			} catch (org.eclipse.jface.text.BadLocationException e) {
				continue;
			}
			length = getOffsetOfNextLine(document, length + offset) - offset;
			if (offset >= 0 && length > 0) {
				positions.add(new org.eclipse.jface.text.Position(offset, length));
			}
		}
		org.eclipse.swt.widgets.Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				updateCodefolding(positions);
			}
		});
	}
	
	private java.util.List<org.eclipse.emf.ecore.EObject> getAllContents(org.eclipse.emf.ecore.EObject[] contentArray) {
		java.util.List<org.eclipse.emf.ecore.EObject> result = new java.util.ArrayList<org.eclipse.emf.ecore.EObject>();
		for (org.eclipse.emf.ecore.EObject eObject : contentArray) {
			if (eObject == null) {
				continue;
			}
			result.add(eObject);
			java.util.List<org.eclipse.emf.ecore.EObject> contents = eObject.eContents();
			if (contents == null) {
				continue;
			}
			result.addAll(getAllContents(contents.toArray(new org.eclipse.emf.ecore.EObject[0])));
		}
		return result;
	}
	
	private int getOffsetOfNextLine(org.eclipse.jface.text.IDocument document, int offset) {
		int end = document.getLength();
		int nextLineOffset = offset;
		if (offset < 0 || offset > end) {
			return -1;
		}
		while (nextLineOffset < end) {
			String charAtOffset = "";
			try {
				charAtOffset += document.getChar(nextLineOffset);
			} catch (org.eclipse.jface.text.BadLocationException e) {
				return -1;
			}
			if (charAtOffset.matches("\\S")) {
				return nextLineOffset;
			}
			if (charAtOffset.equals("\n")) {
				return nextLineOffset + 1;
			}
			nextLineOffset++;
		}
		return offset;
	}
	
}
