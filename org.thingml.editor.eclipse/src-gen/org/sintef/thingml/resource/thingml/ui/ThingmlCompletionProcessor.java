/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.ui;

public class ThingmlCompletionProcessor implements org.eclipse.jface.text.contentassist.IContentAssistProcessor {
	
	private org.sintef.thingml.resource.thingml.IThingmlResourceProvider resourceProvider;
	private org.sintef.thingml.resource.thingml.ui.IThingmlBracketHandlerProvider bracketHandlerProvider;
	
	public ThingmlCompletionProcessor(org.sintef.thingml.resource.thingml.IThingmlResourceProvider resourceProvider, org.sintef.thingml.resource.thingml.ui.IThingmlBracketHandlerProvider bracketHandlerProvider) {
		this.resourceProvider = resourceProvider;
		this.bracketHandlerProvider = bracketHandlerProvider;
	}
	
	public org.eclipse.jface.text.contentassist.ICompletionProposal[] computeCompletionProposals(org.eclipse.jface.text.ITextViewer viewer, int offset) {
		org.sintef.thingml.resource.thingml.IThingmlTextResource textResource = resourceProvider.getResource();
		if (textResource == null) {
			return new org.eclipse.jface.text.contentassist.ICompletionProposal[0];
		}
		String content = viewer.getDocument().get();
		org.sintef.thingml.resource.thingml.ui.ThingmlCodeCompletionHelper helper = new org.sintef.thingml.resource.thingml.ui.ThingmlCodeCompletionHelper();
		org.sintef.thingml.resource.thingml.ui.ThingmlCompletionProposal[] computedProposals = helper.computeCompletionProposals(textResource, content, offset);
		
		// call completion proposal post processor to allow for customizing the proposals
		org.sintef.thingml.resource.thingml.ui.ThingmlProposalPostProcessor proposalPostProcessor = new org.sintef.thingml.resource.thingml.ui.ThingmlProposalPostProcessor();
		java.util.List<org.sintef.thingml.resource.thingml.ui.ThingmlCompletionProposal> computedProposalList = java.util.Arrays.asList(computedProposals);
		java.util.List<org.sintef.thingml.resource.thingml.ui.ThingmlCompletionProposal> extendedProposalList = proposalPostProcessor.process(computedProposalList);
		if (extendedProposalList == null) {
			extendedProposalList = java.util.Collections.emptyList();
		}
		java.util.List<org.sintef.thingml.resource.thingml.ui.ThingmlCompletionProposal> finalProposalList = new java.util.ArrayList<org.sintef.thingml.resource.thingml.ui.ThingmlCompletionProposal>();
		for (org.sintef.thingml.resource.thingml.ui.ThingmlCompletionProposal proposal : extendedProposalList) {
			if (proposal.getMatchesPrefix()) {
				finalProposalList.add(proposal);
			}
		}
		org.eclipse.jface.text.contentassist.ICompletionProposal[] result = new org.eclipse.jface.text.contentassist.ICompletionProposal[finalProposalList.size()];
		int i = 0;
		for (org.sintef.thingml.resource.thingml.ui.ThingmlCompletionProposal proposal : finalProposalList) {
			String proposalString = proposal.getInsertString();
			String displayString = proposal.getDisplayString();
			String prefix = proposal.getPrefix();
			org.eclipse.swt.graphics.Image image = proposal.getImage();
			org.eclipse.jface.text.contentassist.IContextInformation info;
			info = new org.eclipse.jface.text.contentassist.ContextInformation(image, proposalString, proposalString);
			int begin = offset - prefix.length();
			int replacementLength = prefix.length();
			// if a closing bracket was automatically inserted right before, we enlarge the
			// replacement length in order to overwrite the bracket.
			org.sintef.thingml.resource.thingml.ui.IThingmlBracketHandler bracketHandler = bracketHandlerProvider.getBracketHandler();
			String closingBracket = bracketHandler.getClosingBracket();
			if (bracketHandler.addedClosingBracket() && proposalString.endsWith(closingBracket)) {
				replacementLength += closingBracket.length();
			}
			result[i++] = new org.eclipse.jface.text.contentassist.CompletionProposal(proposalString, begin, replacementLength, proposalString.length(), image, displayString, info, proposalString);
		}
		return result;
	}
	
	public org.eclipse.jface.text.contentassist.IContextInformation[] computeContextInformation(org.eclipse.jface.text.ITextViewer viewer, int offset) {
		return null;
	}
	
	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}
	
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}
	
	public org.eclipse.jface.text.contentassist.IContextInformationValidator getContextInformationValidator() {
		return null;
	}
	
	public String getErrorMessage() {
		return null;
	}
}
