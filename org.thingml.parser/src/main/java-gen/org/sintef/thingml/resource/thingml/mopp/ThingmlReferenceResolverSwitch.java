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
package org.sintef.thingml.resource.thingml.mopp;

public class ThingmlReferenceResolverSwitch implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolverSwitch {
	
	protected org.sintef.thingml.resource.thingml.analysis.ThingMLModelImportsReferenceResolver thingMLModelImportsReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.ThingMLModelImportsReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.ThingIncludesReferenceResolver thingIncludesReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.ThingIncludesReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.PortReceivesReferenceResolver portReceivesReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.PortReceivesReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.PortSendsReferenceResolver portSendsReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.PortSendsReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.PropertyTypeReferenceResolver propertyTypeReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.PropertyTypeReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.DictionaryIndexTypeReferenceResolver dictionaryIndexTypeReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.DictionaryIndexTypeReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.ParameterTypeReferenceResolver parameterTypeReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.ParameterTypeReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.RegionInitialReferenceResolver regionInitialReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.RegionInitialReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.TransitionTargetReferenceResolver transitionTargetReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.TransitionTargetReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.ReceiveMessagePortReferenceResolver receiveMessagePortReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.ReceiveMessagePortReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.ReceiveMessageMessageReferenceResolver receiveMessageMessageReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.ReceiveMessageMessageReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.SendActionPortReferenceResolver sendActionPortReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.SendActionPortReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.SendActionMessageReferenceResolver sendActionMessageReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.SendActionMessageReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.PropertyAssignmentPropertyReferenceResolver propertyAssignmentPropertyReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.PropertyAssignmentPropertyReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.EventReferenceMsgRefReferenceResolver eventReferenceMsgRefReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.EventReferenceMsgRefReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.EventReferenceParamRefReferenceResolver eventReferenceParamRefReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.EventReferenceParamRefReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.PropertyReferencePropertyReferenceResolver propertyReferencePropertyReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.PropertyReferencePropertyReferenceResolver();
	
	public org.sintef.thingml.resource.thingml.analysis.ThingMLModelImportsReferenceResolver getThingMLModelImportsReferenceResolver() {
		return thingMLModelImportsReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.ThingIncludesReferenceResolver getThingIncludesReferenceResolver() {
		return thingIncludesReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.PortReceivesReferenceResolver getPortReceivesReferenceResolver() {
		return portReceivesReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.PortSendsReferenceResolver getPortSendsReferenceResolver() {
		return portSendsReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.PropertyTypeReferenceResolver getPropertyTypeReferenceResolver() {
		return propertyTypeReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.DictionaryIndexTypeReferenceResolver getDictionaryIndexTypeReferenceResolver() {
		return dictionaryIndexTypeReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.ParameterTypeReferenceResolver getParameterTypeReferenceResolver() {
		return parameterTypeReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.RegionInitialReferenceResolver getRegionInitialReferenceResolver() {
		return regionInitialReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.TransitionTargetReferenceResolver getTransitionTargetReferenceResolver() {
		return transitionTargetReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.ReceiveMessagePortReferenceResolver getReceiveMessagePortReferenceResolver() {
		return receiveMessagePortReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.ReceiveMessageMessageReferenceResolver getReceiveMessageMessageReferenceResolver() {
		return receiveMessageMessageReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.SendActionPortReferenceResolver getSendActionPortReferenceResolver() {
		return sendActionPortReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.SendActionMessageReferenceResolver getSendActionMessageReferenceResolver() {
		return sendActionMessageReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.PropertyAssignmentPropertyReferenceResolver getPropertyAssignmentPropertyReferenceResolver() {
		return propertyAssignmentPropertyReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.EventReferenceMsgRefReferenceResolver getEventReferenceMsgRefReferenceResolver() {
		return eventReferenceMsgRefReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.EventReferenceParamRefReferenceResolver getEventReferenceParamRefReferenceResolver() {
		return eventReferenceParamRefReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.PropertyReferencePropertyReferenceResolver getPropertyReferencePropertyReferenceResolver() {
		return propertyReferencePropertyReferenceResolver;
	}
	
	public void setOptions(java.util.Map<?, ?> options) {
		thingMLModelImportsReferenceResolver.setOptions(options);
		thingIncludesReferenceResolver.setOptions(options);
		portReceivesReferenceResolver.setOptions(options);
		portSendsReferenceResolver.setOptions(options);
		propertyTypeReferenceResolver.setOptions(options);
		dictionaryIndexTypeReferenceResolver.setOptions(options);
		parameterTypeReferenceResolver.setOptions(options);
		regionInitialReferenceResolver.setOptions(options);
		transitionTargetReferenceResolver.setOptions(options);
		receiveMessagePortReferenceResolver.setOptions(options);
		receiveMessageMessageReferenceResolver.setOptions(options);
		sendActionPortReferenceResolver.setOptions(options);
		sendActionMessageReferenceResolver.setOptions(options);
		propertyAssignmentPropertyReferenceResolver.setOptions(options);
		eventReferenceMsgRefReferenceResolver.setOptions(options);
		eventReferenceParamRefReferenceResolver.setOptions(options);
		propertyReferencePropertyReferenceResolver.setOptions(options);
	}
	
	public void resolveFuzzy(String identifier, org.eclipse.emf.ecore.EObject container, org.eclipse.emf.ecore.EReference reference, int position, org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.eclipse.emf.ecore.EObject> result) {
		if (container == null) {
			return;
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getThingMLModel().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.ThingMLModel> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.ThingMLModel>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("imports")) {
				thingMLModelImportsReferenceResolver.resolve(identifier, (org.sintef.thingml.ThingMLModel) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getThing().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Thing> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Thing>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("includes")) {
				thingIncludesReferenceResolver.resolve(identifier, (org.sintef.thingml.Thing) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getPort().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Message> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Message>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("receives")) {
				portReceivesReferenceResolver.resolve(identifier, (org.sintef.thingml.Port) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getPort().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Message> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Message>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("sends")) {
				portSendsReferenceResolver.resolve(identifier, (org.sintef.thingml.Port) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getProperty().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Type> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Type>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("type")) {
				propertyTypeReferenceResolver.resolve(identifier, (org.sintef.thingml.Property) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getDictionary().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Type> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Type>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("indexType")) {
				dictionaryIndexTypeReferenceResolver.resolve(identifier, (org.sintef.thingml.Dictionary) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getParameter().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Type> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Type>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("type")) {
				parameterTypeReferenceResolver.resolve(identifier, (org.sintef.thingml.Parameter) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getRegion().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.State> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.State>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("initial")) {
				regionInitialReferenceResolver.resolve(identifier, (org.sintef.thingml.Region) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getTransition().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.State> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.State>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("target")) {
				transitionTargetReferenceResolver.resolve(identifier, (org.sintef.thingml.Transition) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getReceiveMessage().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Port> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Port>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("port")) {
				receiveMessagePortReferenceResolver.resolve(identifier, (org.sintef.thingml.ReceiveMessage) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getReceiveMessage().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Message> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Message>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("message")) {
				receiveMessageMessageReferenceResolver.resolve(identifier, (org.sintef.thingml.ReceiveMessage) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getSendAction().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Port> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Port>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("port")) {
				sendActionPortReferenceResolver.resolve(identifier, (org.sintef.thingml.SendAction) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getSendAction().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Message> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Message>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("message")) {
				sendActionMessageReferenceResolver.resolve(identifier, (org.sintef.thingml.SendAction) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getPropertyAssignment().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Property> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Property>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("property")) {
				propertyAssignmentPropertyReferenceResolver.resolve(identifier, (org.sintef.thingml.PropertyAssignment) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getEventReference().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.ReceiveMessage> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.ReceiveMessage>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("msgRef")) {
				eventReferenceMsgRefReferenceResolver.resolve(identifier, (org.sintef.thingml.EventReference) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getEventReference().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Parameter> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Parameter>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("paramRef")) {
				eventReferenceParamRefReferenceResolver.resolve(identifier, (org.sintef.thingml.EventReference) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getPropertyReference().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Property> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Property>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("property")) {
				propertyReferencePropertyReferenceResolver.resolve(identifier, (org.sintef.thingml.PropertyReference) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<? extends org.eclipse.emf.ecore.EObject, ? extends org.eclipse.emf.ecore.EObject> getResolver(org.eclipse.emf.ecore.EStructuralFeature reference) {
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getThingMLModel_Imports()) {
			return thingMLModelImportsReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getThing_Includes()) {
			return thingIncludesReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getPort_Receives()) {
			return portReceivesReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getPort_Sends()) {
			return portSendsReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getProperty_Type()) {
			return propertyTypeReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getDictionary_IndexType()) {
			return dictionaryIndexTypeReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getParameter_Type()) {
			return parameterTypeReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getRegion_Initial()) {
			return regionInitialReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getTransition_Target()) {
			return transitionTargetReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getReceiveMessage_Port()) {
			return receiveMessagePortReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getReceiveMessage_Message()) {
			return receiveMessageMessageReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getSendAction_Port()) {
			return sendActionPortReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getSendAction_Message()) {
			return sendActionMessageReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getPropertyAssignment_Property()) {
			return propertyAssignmentPropertyReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getEventReference_MsgRef()) {
			return eventReferenceMsgRefReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getEventReference_ParamRef()) {
			return eventReferenceParamRefReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getPropertyReference_Property()) {
			return propertyReferencePropertyReferenceResolver;
		}
		return null;
	}
	
}
