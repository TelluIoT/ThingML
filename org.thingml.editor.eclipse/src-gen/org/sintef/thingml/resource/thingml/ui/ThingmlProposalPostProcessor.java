/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.ui;

/**
 * A class which can be overridden to customize code completion proposals.
 */
public class ThingmlProposalPostProcessor {
	
	public java.util.List<org.sintef.thingml.resource.thingml.ui.ThingmlCompletionProposal> process(java.util.List<org.sintef.thingml.resource.thingml.ui.ThingmlCompletionProposal> proposals) {
		// the default implementation does returns the proposals as they are
		return proposals;
	}
	
}
