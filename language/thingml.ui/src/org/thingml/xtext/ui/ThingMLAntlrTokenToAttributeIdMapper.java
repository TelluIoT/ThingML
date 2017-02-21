package org.thingml.xtext.ui;

import java.util.regex.Pattern;

import org.eclipse.xtext.ide.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;


public class ThingMLAntlrTokenToAttributeIdMapper extends AbstractAntlrTokenToAttributeIdMapper {

	@Override
	protected String calculateId(String tokenName, int tokenType) {
		
		
		final Pattern QUOTED = Pattern.compile("(?:^'(\\w[^']*)'$)|(?:^\"(\\w[^\"]*)\")$", Pattern.MULTILINE);
		
        if(tokenName.equals("RULE_INT")) {
            return ThingMLHighlightingConfiguration.NUMBER_ID;
        }
        else if(QUOTED.matcher(tokenName).matches()) {
            return ThingMLHighlightingConfiguration.KEYWORD_ID;
        }
        return ThingMLHighlightingConfiguration.DEFAULT_ID;

	}

}
