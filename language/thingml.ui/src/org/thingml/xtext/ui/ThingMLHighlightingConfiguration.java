package org.thingml.xtext.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class ThingMLHighlightingConfiguration extends DefaultHighlightingConfiguration {

	
	public static final String DEFAULT_TEXT_ID = "DEFAULT_TEXT_ID";
	public static final String COMMENTS_ID = "COMMENTS_ID";
	public static final String ANNOTATIONS_ID = "ANNOTATIONS_ID";
	public static final String EXTERN_ID = "EXTERN_ID";
	public static final String DEFAULT_LITERAL_ID = "DEFAULT_LITERAL_ID";
	public static final String DEFAULT_BOLD_LITERAL_ID = "DEFAULT_BOLD_LITERAL_ID";
	public static final String TYPES_AND_MESSAGES_ID = "TYPES_AND_MESSAGES_ID";
	public static final String STATEMACHINE_ID = "STATEMACHINE_ID";
	public static final String ACTIONS_ID = "ACTIONS_ID";
	public static final String CONFIGURATION_ID = "CONFIGURATION_ID";
	
	@Override
    public void configure(IHighlightingConfigurationAcceptor acceptor) {
        acceptor.acceptDefaultHighlighting(DEFAULT_TEXT_ID, "DEFAULT_TEXT", defaultText());
        acceptor.acceptDefaultHighlighting(COMMENTS_ID, "COMMENTS", comments());
        acceptor.acceptDefaultHighlighting(ANNOTATIONS_ID, "ANNOTATIONS", annotations());
        
        acceptor.acceptDefaultHighlighting(EXTERN_ID, "EXTERN", extern());
        acceptor.acceptDefaultHighlighting(DEFAULT_LITERAL_ID, "DEFAULT_LITERAL", defaultLiteral());
        acceptor.acceptDefaultHighlighting(DEFAULT_BOLD_LITERAL_ID, "DEFAULT_BOLD_LITERAL", defaultBoldLiteral());
        acceptor.acceptDefaultHighlighting(TYPES_AND_MESSAGES_ID, "TYPES_AND_MESSAGES", types_and_messages());
        
        acceptor.acceptDefaultHighlighting(STATEMACHINE_ID, "STATEMACHINE", statemachine());
        acceptor.acceptDefaultHighlighting(ACTIONS_ID, "ACTIONS", actions());
        acceptor.acceptDefaultHighlighting(CONFIGURATION_ID, "CONFIGURATION", configuration());   
    }
	
	
	protected TextStyle defaultText() {
        TextStyle textStyle = defaultTextStyle().copy();
        textStyle.setColor(new RGB(34, 34, 34));
        textStyle.setStyle(SWT.NORMAL);
        return textStyle;
    }
	
	protected TextStyle comments() {
        TextStyle textStyle = defaultTextStyle().copy();
        textStyle.setColor(new RGB(102, 102, 102));
        textStyle.setStyle(SWT.NORMAL);
        return textStyle;
    }
	
	protected TextStyle annotations() {
        TextStyle textStyle = defaultTextStyle().copy();
        textStyle.setColor(new RGB(0, 85, 187));
        textStyle.setStyle(SWT.BOLD);
        return textStyle;
    }
	
	protected TextStyle extern() {
        TextStyle textStyle = defaultTextStyle().copy();
        textStyle.setColor(new RGB(0, 85, 187));
        textStyle.setStyle(SWT.NORMAL);
        return textStyle;
    }
	
	protected TextStyle defaultLiteral() {
        TextStyle textStyle = defaultTextStyle().copy();
        textStyle.setColor(new RGB(84, 138, 66));
        textStyle.setStyle(SWT.NORMAL);
        return textStyle;
    }
	
	protected TextStyle defaultBoldLiteral() {
        TextStyle textStyle = defaultTextStyle().copy();
        textStyle.setColor(new RGB(84, 138, 66));
        textStyle.setStyle(SWT.BOLD);
        return textStyle;
    }
	
	protected TextStyle types_and_messages() {
        TextStyle textStyle = defaultTextStyle().copy();
        textStyle.setColor(new RGB(204, 128, 0));
        textStyle.setStyle(SWT.BOLD);
        return textStyle;
    }
	
	protected TextStyle statemachine() {
        TextStyle textStyle = defaultTextStyle().copy();
        textStyle.setColor(new RGB(162, 32, 0));
        textStyle.setStyle(SWT.BOLD);
        return textStyle;
    }
	
	protected TextStyle actions() {
        TextStyle textStyle = defaultTextStyle().copy();
        textStyle.setColor(new RGB(68, 68, 68));
        textStyle.setStyle(SWT.BOLD);
        return textStyle;
    }
	
	protected TextStyle configuration() {
        TextStyle textStyle = defaultTextStyle().copy();
        textStyle.setColor(new RGB(0, 127, 85));
        textStyle.setStyle(SWT.BOLD);
        return textStyle;
    }
	
}
