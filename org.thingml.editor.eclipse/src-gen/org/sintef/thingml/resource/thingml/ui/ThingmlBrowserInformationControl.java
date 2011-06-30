/**
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sintef.thingml.resource.thingml.ui;

/**
 * Displays HTML information in a {@link org.eclipse.swt.browser.Browser} widget.
 * <p>
 * This IInformationControlExtension2 expects {@link #setInput(Object)} to be
 * called with an argument of type BrowserInformationControlInput.
 * </p>
 * <p>Moved into this package from
 * <code>org.eclipse.jface.internal.text.revisions</code>.</p>
 * <p>This class may be instantiated; it is not intended to be subclassed.</p>
 * <p>Current problems:
 * <ul>
 * 	<li>the size computation is too small</li>
 * 	<li>focusLost event is not sent - see
 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=84532</li>
 * </ul>
 * </p>
 * 
 * @since 3.2
 */
public class ThingmlBrowserInformationControl extends org.eclipse.jface.text.AbstractInformationControl implements org.eclipse.jface.text.IInformationControlExtension2, org.eclipse.jface.text.IDelayedInputChangeProvider {
	
	/**
	 * Tells whether the org.eclipse.swt.SWT org.eclipse.swt.browser.Browser widget
	 * and hence this information control is available.
	 * 
	 * @param parent the parent component used for checking or <code>null</code> if
	 * none
	 * 
	 * @return <code>true</code> if this control is available
	 */
	public static boolean isAvailable(org.eclipse.swt.widgets.Composite parent) {
		if (!fgAvailabilityChecked) {
			try {
				org.eclipse.swt.browser.Browser browser= new org.eclipse.swt.browser.Browser(parent, org.eclipse.swt.SWT.NONE);
				browser.dispose();
				fgIsAvailable= true;
				
				org.eclipse.swt.widgets.Slider sliderV= new org.eclipse.swt.widgets.Slider(parent, org.eclipse.swt.SWT.VERTICAL);
				org.eclipse.swt.widgets.Slider sliderH= new org.eclipse.swt.widgets.Slider(parent, org.eclipse.swt.SWT.HORIZONTAL);
				int width= sliderV.computeSize(org.eclipse.swt.SWT.DEFAULT, org.eclipse.swt.SWT.DEFAULT).x;
				int height= sliderH.computeSize(org.eclipse.swt.SWT.DEFAULT, org.eclipse.swt.SWT.DEFAULT).y;
				fgScrollBarSize= new org.eclipse.swt.graphics.Point(width, height);
				sliderV.dispose();
				sliderH.dispose();
			} catch (org.eclipse.swt.SWTError er) {
				fgIsAvailable= false;
			} finally {
				fgAvailabilityChecked= true;
			}
		}
		
		return fgIsAvailable;
	}
	
	
	/**
	 * Minimal size constraints.
	 */
	private static final int MIN_WIDTH = 80;
	private static final int MIN_HEIGHT = 50;
	
	
	/**
	 * Availability checking cache.
	 */
	private static boolean fgIsAvailable = false;
	private static boolean fgAvailabilityChecked = false;
	
	/**
	 * Cached scroll bar width and height
	 */
	private static org.eclipse.swt.graphics.Point fgScrollBarSize;
	
	/**
	 * The control's browser widget
	 */
	private org.eclipse.swt.browser.Browser fBrowser;
	/**
	 * Tells whether the browser has content
	 */
	private boolean fBrowserHasContent;
	/**
	 * Text layout used to approximate size of content when rendered in browser
	 */
	private org.eclipse.swt.graphics.TextLayout fTextLayout;
	/**
	 * Bold text style
	 */
	private org.eclipse.swt.graphics.TextStyle fBoldStyle;
	
	private org.sintef.thingml.resource.thingml.ui.ThingmlDocBrowserInformationControlInput fInput;
	
	/**
	 * <code>true</code> iff the browser has completed loading of the last input set
	 * via {@link #setInformation(String)}.
	 */
	private boolean fCompleted = false;
	
	/**
	 * The listener to be notified when a delayed location changing event happened.
	 */
	private org.eclipse.jface.text.IInputChangedListener fDelayedInputChangeListener;
	
	/**
	 * The listeners to be notified when the input changed.
	 */
	private org.eclipse.core.runtime.ListenerList fInputChangeListeners = new org.eclipse.core.runtime.ListenerList(org.eclipse.core.runtime.ListenerList.IDENTITY);
	
	/**
	 * The symbolic name of the font used for size computations, or <code>null</code>
	 * to use dialog font.
	 */
	private final String fSymbolicFontName;
	
	/**
	 * Creates a browser information control with the given shell as parent.
	 * 
	 * @param parent the parent shell
	 * @param symbolicFontName the symbolic name of the font used for size computations
	 * @param resizable <code>true</code> if the control should be resizable
	 */
	public ThingmlBrowserInformationControl(org.eclipse.swt.widgets.Shell parent, String symbolicFontName, boolean resizable) {
		super(parent, resizable);
		fSymbolicFontName= symbolicFontName;
		create();
	}
	
	/**
	 * Creates a browser information control with the given shell as parent.
	 * 
	 * @param parent the parent shell
	 * @param symbolicFontName the symbolic name of the font used for size computations
	 * @param statusFieldText the text to be used in the optional status field or
	 * <code>null</code> if the status field should be hidden
	 */
	public ThingmlBrowserInformationControl(org.eclipse.swt.widgets.Shell parent, String symbolicFontName, String statusFieldText) {
		super(parent, statusFieldText);
		fSymbolicFontName= symbolicFontName;
		create();
	}
	
	/**
	 * Creates a browser information control with the given shell as parent.
	 * 
	 * @param parent the parent shell
	 * @param symbolicFontName the symbolic name of the font used for size computations
	 * @param toolBarManager the manager or <code>null</code> if toolbar is not desired
	 * 
	 * @since 3.4
	 */
	public ThingmlBrowserInformationControl(org.eclipse.swt.widgets.Shell parent, String symbolicFontName, org.eclipse.jface.action.ToolBarManager toolBarManager) {
		super(parent, toolBarManager);
		fSymbolicFontName= symbolicFontName;
		create();
	}
	
	/**
	 * 
	 * @see
	 * org.eclipse.jface.text.org.eclipse.jface.text.AbstractInformationControl#createC
	 * ontent(org.eclipse.swt.widgets.Composite)
	 */
	protected void createContent(org.eclipse.swt.widgets.Composite parent) {
		fBrowser= new org.eclipse.swt.browser.Browser(parent, org.eclipse.swt.SWT.NONE);
		org.eclipse.swt.widgets.Display display= getShell().getDisplay();
		fBrowser.setForeground(display.getSystemColor(org.eclipse.swt.SWT.COLOR_INFO_FOREGROUND));
		fBrowser.setBackground(display.getSystemColor(org.eclipse.swt.SWT.COLOR_INFO_BACKGROUND));
		fBrowser.addKeyListener(new org.eclipse.swt.events.KeyListener() {
			public void keyPressed(org.eclipse.swt.events.KeyEvent e)  {
				if (e.character == 0x1B) // ESC
				dispose(); // XXX: Just hide? Would avoid constant recreations.
			}
			
			public void keyReleased(org.eclipse.swt.events.KeyEvent e) {}
		});
		
		fBrowser.addProgressListener(new org.eclipse.swt.browser.ProgressAdapter() {
			public void completed(org.eclipse.swt.browser.ProgressEvent event) {
				fCompleted= true;
			}
		});
		
		fBrowser.addOpenWindowListener(new org.eclipse.swt.browser.OpenWindowListener() {
			public void open(org.eclipse.swt.browser.WindowEvent event) {
				event.required= true; // Cancel opening of new windows
			}
		});
		
		// Replace browser's built-in context menu with none
		fBrowser.setMenu(new org.eclipse.swt.widgets.Menu(getShell(), org.eclipse.swt.SWT.NONE));
		
		createTextLayout();
	}
	
	
	/**
	 * {@inheritDoc} This control can handle {@link String}(no handle) and
	 */
	public void setInput(Object input) {
		org.eclipse.core.runtime.Assert.isLegal(input == null || input instanceof String || input instanceof org.sintef.thingml.resource.thingml.ui.ThingmlDocBrowserInformationControlInput);
		
		if (input instanceof String) {
			setInformation((String)input);
			return;
		}
		
		fInput= (org.sintef.thingml.resource.thingml.ui.ThingmlDocBrowserInformationControlInput) input;
		
		String content= null;
		if (fInput != null)		content= fInput.getHtml();
		
		fBrowserHasContent= content != null && content.length() > 0;
		
		if (!fBrowserHasContent)		content= "<html><body ></html>";
		
		boolean RTL= (getShell().getStyle() & org.eclipse.swt.SWT.RIGHT_TO_LEFT) != 0;
		boolean resizable= isResizable();
		
		// The default "overflow:auto" would not result in a predictable width for the
		// client area and the re-wrapping would cause visual noise
		String[] styles= null;
		if (RTL && resizable) {
			styles= new String[] { "direction:rtl;", "overflow:scroll;", "word-wrap:break-word;" };
		} else if (RTL && !resizable) {
			styles= new String[] { "direction:rtl;", "overflow:hidden;", "word-wrap:break-word;" };
		} else if (!resizable) {
			// XXX: In IE, "word-wrap: break-word;" causes bogus wrapping even in non-broken
			// words :-(see e.g. Javadoc of String). Re-check whether we really still need
			// this now that the Javadoc Hover header already sets this style.
			styles= new String[] { "overflow:hidden;"/*, "word-wrap: break-word;"*/ };
		} else {
			styles= new String[] { "overflow:scroll;" };
		}
		
		StringBuffer buffer= new StringBuffer(content);
		org.sintef.thingml.resource.thingml.ui.ThingmlHTMLPrinter.insertStyles(buffer, styles);
		content= buffer.toString();
		
		// XXX: Should add some JavaScript here that shows something like "(continued...)"
		// or "..." at the end of the visible area when the page overflowed with
		// "overflow:hidden;".
		
		fCompleted= false;
		fBrowser.setText(content);
		
		Object[] listeners= fInputChangeListeners.getListeners();
		for (int i= 0; i < listeners.length; i++) {
			((org.eclipse.jface.text.IInputChangedListener)listeners[i]).inputChanged(fInput);
		}
	}
	
	public void setVisible(boolean visible) {
		org.eclipse.swt.widgets.Shell shell= getShell();
		if (shell.isVisible() == visible) {
			return;
		}
		
		if (!visible) {
			super.setVisible(false);
			setInput(null);
			return;
		}
		
		// The Browser widget flickers when made visible while it is not completely
		// loaded. The fix is to delay the call to setVisible until either loading is
		// completed (see ProgressListener in constructor), or a timeout has been reached.
		final org.eclipse.swt.widgets.Display display = shell.getDisplay();
		
		// Make sure the display wakes from sleep after timeout:
		display.timerExec(100, new Runnable() {
			public void run() {
				fCompleted= true;
			}
		});
		
		while (!fCompleted) {
			// Drive the event loop to process the events required to load the browser
			// widget's contents:
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		shell = getShell();
		if (shell == null || shell.isDisposed()) {
			return;
		}
		
		// Avoids flickering when replacing hovers, especially on Vista in ON_CLICK mode.
		// Causes flickering on GTK. Carbon does not care.
		if ("win32".equals(org.eclipse.swt.SWT.getPlatform())) {
			shell.moveAbove(null);
		}
		
		super.setVisible(true);
	}
	
	/**
	 * 
	 * @see org.eclipse.jface.text.AbstractInformationControl#setSize(int, int)
	 */
	public void setSize(int width, int height) {
		fBrowser.setRedraw(false); // avoid flickering
		try {
			super.setSize(width, height);
		} finally {
			fBrowser.setRedraw(true);
		}
	}
	
	/**
	 * Creates and initializes the text layout used to compute the size hint.
	 * 
	 * @since 3.2
	 */
	private void createTextLayout() {
		fTextLayout= new org.eclipse.swt.graphics.TextLayout(fBrowser.getDisplay());
		
		// Initialize fonts
		String symbolicFontName= fSymbolicFontName == null ? org.eclipse.jface.resource.JFaceResources.DIALOG_FONT : fSymbolicFontName;
		org.eclipse.swt.graphics.Font font = org.eclipse.jface.resource.JFaceResources.getFont(symbolicFontName);
		fTextLayout.setFont(font);
		fTextLayout.setWidth(-1);
		font = org.eclipse.jface.resource.JFaceResources.getFontRegistry().getBold(symbolicFontName);
		fBoldStyle = new org.eclipse.swt.graphics.TextStyle(font, null, null);
		
		// Compute and set tab width
		fTextLayout.setText("    ");
		int tabWidth = fTextLayout.getBounds().width;
		fTextLayout.setTabs(new int[] {tabWidth});
		
		fTextLayout.setText("");
	}
	
	public void dispose() {
		if (fTextLayout != null) {
			fTextLayout.dispose();
			fTextLayout = null;
		}
		fBrowser = null;
		
		super.dispose();
	}
	
	public org.eclipse.swt.graphics.Point computeSizeHint() {
		org.eclipse.swt.graphics.Point sizeConstraints = getSizeConstraints();
		org.eclipse.swt.graphics.Rectangle trim = computeTrim();
		int height = trim.height;
		
		org.eclipse.jface.text.TextPresentation presentation= new org.eclipse.jface.text.TextPresentation();
		String text;
		try {
			text = org.sintef.thingml.resource.thingml.ui.ThingmlHTMLPrinter.html2text(new java.io.StringReader(fInput.getHtml()), presentation);
		} catch (java.io.IOException e) {
			text = "";
		}
		fTextLayout.setText(text);
		fTextLayout.setWidth(sizeConstraints == null ? org.eclipse.swt.SWT.DEFAULT : sizeConstraints.x - trim.width);
		java.util.Iterator<?> iter= presentation.getAllStyleRangeIterator();
		while (iter.hasNext()) {
			org.eclipse.swt.custom.StyleRange sr= (org.eclipse.swt.custom.StyleRange)iter.next();
			if (sr.fontStyle == org.eclipse.swt.SWT.BOLD) {
				fTextLayout.setStyle(fBoldStyle, sr.start, sr.start + sr.length - 1);
			}
		}
		
		org.eclipse.swt.graphics.Rectangle bounds= fTextLayout.getBounds(); // does not return minimum width, see https://bugs.eclipse.org/bugs/show_bug.cgi?id=217446
		int lineCount= fTextLayout.getLineCount();
		int textWidth= 0;
		for (int i= 0; i < lineCount; i++) {
			org.eclipse.swt.graphics.Rectangle rect= fTextLayout.getLineBounds(i);
			int lineWidth= rect.x + rect.width;
			if (i == 0) {
				lineWidth += fInput.getLeadingImageWidth();
			}
			textWidth= Math.max(textWidth, lineWidth);
		}
		bounds.width= textWidth;
		fTextLayout.setText("");
		
		int minWidth= bounds.width;
		height= height + bounds.height;
		
		// Add some air to accommodate for different browser renderings
		minWidth+= 15;
		height+= 15;
		
		
		// Apply max size constraints
		if (sizeConstraints != null) {
			if (sizeConstraints.x != org.eclipse.swt.SWT.DEFAULT) {
				minWidth= Math.min(sizeConstraints.x, minWidth + trim.width);
			}
			if (sizeConstraints.y != org.eclipse.swt.SWT.DEFAULT) {
				height= Math.min(sizeConstraints.y, height);
			}
		}
		
		// Ensure minimal size
		int width= Math.max(MIN_WIDTH, minWidth);
		height= Math.max(MIN_HEIGHT, height);
		org.eclipse.swt.graphics.Point windowSize = new org.eclipse.swt.graphics.Point(width, height);
		return windowSize;
	}
	
	/**
	 * 
	 * @see org.eclipse.jface.text.IInformationControlExtension3#computeTrim()
	 */
	public org.eclipse.swt.graphics.Rectangle computeTrim() {
		org.eclipse.swt.graphics.Rectangle trim = super.computeTrim();
		if (isResizable()) {
			boolean RTL = (getShell().getStyle() & org.eclipse.swt.SWT.RIGHT_TO_LEFT) != 0;
			if (RTL) {
				trim.x-= fgScrollBarSize.x;
			}
			trim.width+= fgScrollBarSize.x;
			trim.height+= fgScrollBarSize.y;
		}
		return trim;
	}
	
	/**
	 * Adds the listener to the collection of listeners who will be notified when the
	 * current location has changed or is about to change.
	 * 
	 * @param listener the location listener
	 * 
	 * @since 3.4
	 */
	public void addLocationListener(org.eclipse.swt.browser.LocationListener listener) {
		fBrowser.addLocationListener(listener);
	}
	
	public void setForegroundColor(org.eclipse.swt.graphics.Color foreground) {
		super.setForegroundColor(foreground);
		fBrowser.setForeground(foreground);
	}
	
	public void setBackgroundColor(org.eclipse.swt.graphics.Color background) {
		super.setBackgroundColor(background);
		fBrowser.setBackground(background);
	}
	
	public boolean hasContents() {
		return fBrowserHasContent;
	}
	
	/**
	 * Adds a listener for input changes to this input change provider. Has no effect
	 * if an identical listener is already registered.
	 * 
	 * @param inputChangeListener the listener to add
	 * 
	 * @since 3.4
	 */
	public void addInputChangeListener(org.eclipse.jface.text.IInputChangedListener inputChangeListener) {
		org.eclipse.core.runtime.Assert.isNotNull(inputChangeListener);
		fInputChangeListeners.add(inputChangeListener);
	}
	
	/**
	 * Removes the given input change listener from this input change provider. Has no
	 * effect if an identical listener is not registered.
	 * 
	 * @param inputChangeListener the listener to remove
	 * 
	 * @since 3.4
	 */
	public void removeInputChangeListener(org.eclipse.jface.text.IInputChangedListener inputChangeListener) {
		fInputChangeListeners.remove(inputChangeListener);
	}
	
	/**
	 * 
	 * @see
	 * org.eclipse.jface.text.IDelayedInputChangeProvider#setDelayedInputChangeListener
	 * (org.eclipse.jface.text.IInputChangedListener)
	 * 
	 * @since 3.4
	 */
	public void setDelayedInputChangeListener(org.eclipse.jface.text.IInputChangedListener inputChangeListener) {
		fDelayedInputChangeListener= inputChangeListener;
	}
	
	/**
	 * Tells whether a delayed input change listener is registered.
	 * 
	 * @return <code>true</code> iff a delayed input change listener is currently
	 * registered
	 * 
	 * @since 3.4
	 */
	public boolean hasDelayedInputChangeListener() {
		return fDelayedInputChangeListener != null;
	}
	
	/**
	 * Notifies listeners of a delayed input change.
	 * 
	 * @param newInput the new input, or <code>null</code> to request cancellation
	 * 
	 * @since 3.4
	 */
	public void notifyDelayedInputChange(Object newInput) {
		if (fDelayedInputChangeListener != null)		fDelayedInputChangeListener.inputChanged(newInput);
	}
	
	/**
	 * 
	 * @see java.lang.Object#toString()
	 * 
	 * @since 3.4
	 */
	public String toString() {
		String style= (getShell().getStyle() & org.eclipse.swt.SWT.RESIZE) == 0 ? "fixed" : "resizeable";
		return super.toString() + " -  style: " + style;
	}
	
	/**
	 * 
	 * @return the current browser input or <code>null</code>
	 */
	public org.sintef.thingml.resource.thingml.ui.ThingmlDocBrowserInformationControlInput getInput() {
		return fInput;
	}
	
	/**
	 * 
	 * @see
	 * org.eclipse.jface.text.IInformationControlExtension5#computeSizeConstraints(int,
	 * int)
	 */
	public org.eclipse.swt.graphics.Point computeSizeConstraints(int widthInChars, int heightInChars) {
		if (fSymbolicFontName == null) {
			return null;
		}
		
		org.eclipse.swt.graphics.GC gc= new org.eclipse.swt.graphics.GC(fBrowser);
		org.eclipse.swt.graphics.Font font= fSymbolicFontName == null ? org.eclipse.jface.resource.JFaceResources.getDialogFont() : org.eclipse.jface.resource.JFaceResources.getFont(fSymbolicFontName);
		gc.setFont(font);
		int width= gc.getFontMetrics().getAverageCharWidth();
		int height= gc.getFontMetrics().getHeight();
		gc.dispose();
		
		return new org.eclipse.swt.graphics.Point(widthInChars * width, heightInChars * height);
	}
	
}
