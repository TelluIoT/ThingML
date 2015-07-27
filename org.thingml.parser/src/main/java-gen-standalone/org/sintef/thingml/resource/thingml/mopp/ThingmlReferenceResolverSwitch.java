/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
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
	
	/**
	 * This map stores a copy of the options the were set for loading the resource.
	 */
	private java.util.Map<Object, Object> options;
	
	protected org.sintef.thingml.resource.thingml.analysis.ThingMLModelImportsReferenceResolver thingMLModelImportsReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.ThingMLModelImportsReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.TypedElementTypeReferenceResolver typedElementTypeReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.TypedElementTypeReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.ThingIncludesReferenceResolver thingIncludesReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.ThingIncludesReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.PortReceivesReferenceResolver portReceivesReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.PortReceivesReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.PortSendsReferenceResolver portSendsReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.PortSendsReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.RegionInitialReferenceResolver regionInitialReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.RegionInitialReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.TransitionTargetReferenceResolver transitionTargetReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.TransitionTargetReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.ReceiveMessagePortReferenceResolver receiveMessagePortReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.ReceiveMessagePortReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.ReceiveMessageMessageReferenceResolver receiveMessageMessageReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.ReceiveMessageMessageReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.PropertyAssignPropertyReferenceResolver propertyAssignPropertyReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.PropertyAssignPropertyReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.ConfigIncludeConfigReferenceResolver configIncludeConfigReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.ConfigIncludeConfigReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.InstanceTypeReferenceResolver instanceTypeReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.InstanceTypeReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.ConnectorRequiredReferenceResolver connectorRequiredReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.ConnectorRequiredReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.ConnectorProvidedReferenceResolver connectorProvidedReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.ConnectorProvidedReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.ConfigPropertyAssignPropertyReferenceResolver configPropertyAssignPropertyReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.ConfigPropertyAssignPropertyReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.InstanceRefConfigReferenceResolver instanceRefConfigReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.InstanceRefConfigReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.InstanceRefInstanceReferenceResolver instanceRefInstanceReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.InstanceRefInstanceReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.SendActionPortReferenceResolver sendActionPortReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.SendActionPortReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.SendActionMessageReferenceResolver sendActionMessageReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.SendActionMessageReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.VariableAssignmentPropertyReferenceResolver variableAssignmentPropertyReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.VariableAssignmentPropertyReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.FunctionCallFunctionReferenceResolver functionCallFunctionReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.FunctionCallFunctionReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.StreamOutputPortReferenceResolver streamOutputPortReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.StreamOutputPortReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.StreamOutputMessageReferenceResolver streamOutputMessageReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.StreamOutputMessageReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.StreamOutputParametersReferenceResolver streamOutputParametersReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.StreamOutputParametersReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.EventReferenceMsgRefReferenceResolver eventReferenceMsgRefReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.EventReferenceMsgRefReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.EventReferenceParamRefReferenceResolver eventReferenceParamRefReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.EventReferenceParamRefReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.PropertyReferencePropertyReferenceResolver propertyReferencePropertyReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.PropertyReferencePropertyReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.EnumLiteralRefEnumReferenceResolver enumLiteralRefEnumReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.EnumLiteralRefEnumReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.EnumLiteralRefLiteralReferenceResolver enumLiteralRefLiteralReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.EnumLiteralRefLiteralReferenceResolver();
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.ThingMLModel, org.sintef.thingml.ThingMLModel> getThingMLModelImportsReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getThingMLModel_Imports(), thingMLModelImportsReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.TypedElement, org.sintef.thingml.Type> getTypedElementTypeReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getTypedElement_Type(), typedElementTypeReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.Thing, org.sintef.thingml.Thing> getThingIncludesReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getThing_Includes(), thingIncludesReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.Port, org.sintef.thingml.Message> getPortReceivesReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getPort_Receives(), portReceivesReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.Port, org.sintef.thingml.Message> getPortSendsReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getPort_Sends(), portSendsReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.Region, org.sintef.thingml.State> getRegionInitialReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getRegion_Initial(), regionInitialReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.Transition, org.sintef.thingml.State> getTransitionTargetReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getTransition_Target(), transitionTargetReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.ReceiveMessage, org.sintef.thingml.Port> getReceiveMessagePortReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getReceiveMessage_Port(), receiveMessagePortReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.ReceiveMessage, org.sintef.thingml.Message> getReceiveMessageMessageReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getReceiveMessage_Message(), receiveMessageMessageReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.PropertyAssign, org.sintef.thingml.Property> getPropertyAssignPropertyReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getPropertyAssign_Property(), propertyAssignPropertyReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.ConfigInclude, org.sintef.thingml.Configuration> getConfigIncludeConfigReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getConfigInclude_Config(), configIncludeConfigReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.Instance, org.sintef.thingml.Thing> getInstanceTypeReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getInstance_Type(), instanceTypeReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.Connector, org.sintef.thingml.RequiredPort> getConnectorRequiredReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getConnector_Required(), connectorRequiredReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.Connector, org.sintef.thingml.ProvidedPort> getConnectorProvidedReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getConnector_Provided(), connectorProvidedReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.ConfigPropertyAssign, org.sintef.thingml.Property> getConfigPropertyAssignPropertyReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getConfigPropertyAssign_Property(), configPropertyAssignPropertyReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.InstanceRef, org.sintef.thingml.ConfigInclude> getInstanceRefConfigReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getInstanceRef_Config(), instanceRefConfigReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.InstanceRef, org.sintef.thingml.Instance> getInstanceRefInstanceReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getInstanceRef_Instance(), instanceRefInstanceReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.SendAction, org.sintef.thingml.Port> getSendActionPortReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getSendAction_Port(), sendActionPortReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.SendAction, org.sintef.thingml.Message> getSendActionMessageReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getSendAction_Message(), sendActionMessageReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.VariableAssignment, org.sintef.thingml.Variable> getVariableAssignmentPropertyReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getVariableAssignment_Property(), variableAssignmentPropertyReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.FunctionCall, org.sintef.thingml.Function> getFunctionCallFunctionReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getFunctionCall_Function(), functionCallFunctionReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.StreamOutput, org.sintef.thingml.Port> getStreamOutputPortReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getStreamOutput_Port(), streamOutputPortReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.StreamOutput, org.sintef.thingml.Message> getStreamOutputMessageReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getStreamOutput_Message(), streamOutputMessageReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.StreamOutput, org.sintef.thingml.StreamExpression> getStreamOutputParametersReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getStreamOutput_Parameters(), streamOutputParametersReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.EventReference, org.sintef.thingml.ReceiveMessage> getEventReferenceMsgRefReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getEventReference_MsgRef(), eventReferenceMsgRefReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.EventReference, org.sintef.thingml.Parameter> getEventReferenceParamRefReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getEventReference_ParamRef(), eventReferenceParamRefReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.PropertyReference, org.sintef.thingml.Variable> getPropertyReferencePropertyReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getPropertyReference_Property(), propertyReferencePropertyReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.EnumLiteralRef, org.sintef.thingml.Enumeration> getEnumLiteralRefEnumReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getEnumLiteralRef_Enum(), enumLiteralRefEnumReferenceResolver);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.EnumLiteralRef, org.sintef.thingml.EnumerationLiteral> getEnumLiteralRefLiteralReferenceResolver() {
		return getResolverChain(org.sintef.thingml.ThingmlPackage.eINSTANCE.getEnumLiteralRef_Literal(), enumLiteralRefLiteralReferenceResolver);
	}
	
	public void setOptions(java.util.Map<?, ?> options) {
		if (options != null) {
			this.options = new java.util.LinkedHashMap<Object, Object>();
			this.options.putAll(options);
		}
		thingMLModelImportsReferenceResolver.setOptions(options);
		typedElementTypeReferenceResolver.setOptions(options);
		thingIncludesReferenceResolver.setOptions(options);
		portReceivesReferenceResolver.setOptions(options);
		portSendsReferenceResolver.setOptions(options);
		regionInitialReferenceResolver.setOptions(options);
		transitionTargetReferenceResolver.setOptions(options);
		receiveMessagePortReferenceResolver.setOptions(options);
		receiveMessageMessageReferenceResolver.setOptions(options);
		propertyAssignPropertyReferenceResolver.setOptions(options);
		configIncludeConfigReferenceResolver.setOptions(options);
		instanceTypeReferenceResolver.setOptions(options);
		connectorRequiredReferenceResolver.setOptions(options);
		connectorProvidedReferenceResolver.setOptions(options);
		configPropertyAssignPropertyReferenceResolver.setOptions(options);
		instanceRefConfigReferenceResolver.setOptions(options);
		instanceRefInstanceReferenceResolver.setOptions(options);
		sendActionPortReferenceResolver.setOptions(options);
		sendActionMessageReferenceResolver.setOptions(options);
		variableAssignmentPropertyReferenceResolver.setOptions(options);
		functionCallFunctionReferenceResolver.setOptions(options);
		streamOutputPortReferenceResolver.setOptions(options);
		streamOutputMessageReferenceResolver.setOptions(options);
		streamOutputParametersReferenceResolver.setOptions(options);
		eventReferenceMsgRefReferenceResolver.setOptions(options);
		eventReferenceParamRefReferenceResolver.setOptions(options);
		propertyReferencePropertyReferenceResolver.setOptions(options);
		enumLiteralRefEnumReferenceResolver.setOptions(options);
		enumLiteralRefLiteralReferenceResolver.setOptions(options);
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
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getTypedElement().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Type> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Type>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("type")) {
				typedElementTypeReferenceResolver.resolve(identifier, (org.sintef.thingml.TypedElement) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
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
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getPropertyAssign().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Property> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Property>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("property")) {
				propertyAssignPropertyReferenceResolver.resolve(identifier, (org.sintef.thingml.PropertyAssign) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getConfigInclude().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Configuration> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Configuration>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("config")) {
				configIncludeConfigReferenceResolver.resolve(identifier, (org.sintef.thingml.ConfigInclude) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getInstance().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Thing> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Thing>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("type")) {
				instanceTypeReferenceResolver.resolve(identifier, (org.sintef.thingml.Instance) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getConnector().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.RequiredPort> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.RequiredPort>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("required")) {
				connectorRequiredReferenceResolver.resolve(identifier, (org.sintef.thingml.Connector) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getConnector().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.ProvidedPort> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.ProvidedPort>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("provided")) {
				connectorProvidedReferenceResolver.resolve(identifier, (org.sintef.thingml.Connector) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getConfigPropertyAssign().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Property> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Property>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("property")) {
				configPropertyAssignPropertyReferenceResolver.resolve(identifier, (org.sintef.thingml.ConfigPropertyAssign) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getInstanceRef().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.ConfigInclude> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.ConfigInclude>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("config")) {
				instanceRefConfigReferenceResolver.resolve(identifier, (org.sintef.thingml.InstanceRef) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getInstanceRef().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Instance> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Instance>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("instance")) {
				instanceRefInstanceReferenceResolver.resolve(identifier, (org.sintef.thingml.InstanceRef) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
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
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getVariableAssignment().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Variable> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Variable>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("property")) {
				variableAssignmentPropertyReferenceResolver.resolve(identifier, (org.sintef.thingml.VariableAssignment) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getFunctionCall().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Function> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Function>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("function")) {
				functionCallFunctionReferenceResolver.resolve(identifier, (org.sintef.thingml.FunctionCall) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getStreamOutput().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Port> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Port>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("port")) {
				streamOutputPortReferenceResolver.resolve(identifier, (org.sintef.thingml.StreamOutput) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getStreamOutput().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Message> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Message>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("message")) {
				streamOutputMessageReferenceResolver.resolve(identifier, (org.sintef.thingml.StreamOutput) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getStreamOutput().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.StreamExpression> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.StreamExpression>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("parameters")) {
				streamOutputParametersReferenceResolver.resolve(identifier, (org.sintef.thingml.StreamOutput) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
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
			ThingmlFuzzyResolveResult<org.sintef.thingml.Variable> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Variable>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("property")) {
				propertyReferencePropertyReferenceResolver.resolve(identifier, (org.sintef.thingml.PropertyReference) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getEnumLiteralRef().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.Enumeration> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.Enumeration>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("enum")) {
				enumLiteralRefEnumReferenceResolver.resolve(identifier, (org.sintef.thingml.EnumLiteralRef) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.sintef.thingml.ThingmlPackage.eINSTANCE.getEnumLiteralRef().isInstance(container)) {
			ThingmlFuzzyResolveResult<org.sintef.thingml.EnumerationLiteral> frr = new ThingmlFuzzyResolveResult<org.sintef.thingml.EnumerationLiteral>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("literal")) {
				enumLiteralRefLiteralReferenceResolver.resolve(identifier, (org.sintef.thingml.EnumLiteralRef) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<? extends org.eclipse.emf.ecore.EObject, ? extends org.eclipse.emf.ecore.EObject> getResolver(org.eclipse.emf.ecore.EStructuralFeature reference) {
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getThingMLModel_Imports()) {
			return getResolverChain(reference, thingMLModelImportsReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getTypedElement_Type()) {
			return getResolverChain(reference, typedElementTypeReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getThing_Includes()) {
			return getResolverChain(reference, thingIncludesReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getPort_Receives()) {
			return getResolverChain(reference, portReceivesReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getPort_Sends()) {
			return getResolverChain(reference, portSendsReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getRegion_Initial()) {
			return getResolverChain(reference, regionInitialReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getTransition_Target()) {
			return getResolverChain(reference, transitionTargetReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getReceiveMessage_Port()) {
			return getResolverChain(reference, receiveMessagePortReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getReceiveMessage_Message()) {
			return getResolverChain(reference, receiveMessageMessageReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getPropertyAssign_Property()) {
			return getResolverChain(reference, propertyAssignPropertyReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getConfigInclude_Config()) {
			return getResolverChain(reference, configIncludeConfigReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getInstance_Type()) {
			return getResolverChain(reference, instanceTypeReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getConnector_Required()) {
			return getResolverChain(reference, connectorRequiredReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getConnector_Provided()) {
			return getResolverChain(reference, connectorProvidedReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getConfigPropertyAssign_Property()) {
			return getResolverChain(reference, configPropertyAssignPropertyReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getInstanceRef_Config()) {
			return getResolverChain(reference, instanceRefConfigReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getInstanceRef_Instance()) {
			return getResolverChain(reference, instanceRefInstanceReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getSendAction_Port()) {
			return getResolverChain(reference, sendActionPortReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getSendAction_Message()) {
			return getResolverChain(reference, sendActionMessageReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getVariableAssignment_Property()) {
			return getResolverChain(reference, variableAssignmentPropertyReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getFunctionCall_Function()) {
			return getResolverChain(reference, functionCallFunctionReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getStreamOutput_Port()) {
			return getResolverChain(reference, streamOutputPortReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getStreamOutput_Message()) {
			return getResolverChain(reference, streamOutputMessageReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getStreamOutput_Parameters()) {
			return getResolverChain(reference, streamOutputParametersReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getEventReference_MsgRef()) {
			return getResolverChain(reference, eventReferenceMsgRefReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getEventReference_ParamRef()) {
			return getResolverChain(reference, eventReferenceParamRefReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getPropertyReference_Property()) {
			return getResolverChain(reference, propertyReferencePropertyReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getEnumLiteralRef_Enum()) {
			return getResolverChain(reference, enumLiteralRefEnumReferenceResolver);
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getEnumLiteralRef_Literal()) {
			return getResolverChain(reference, enumLiteralRefLiteralReferenceResolver);
		}
		return null;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})	
	public <ContainerType extends org.eclipse.emf.ecore.EObject, ReferenceType extends org.eclipse.emf.ecore.EObject> org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<ContainerType, ReferenceType> getResolverChain(org.eclipse.emf.ecore.EStructuralFeature reference, org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<ContainerType, ReferenceType> originalResolver) {
		if (options == null) {
			return originalResolver;
		}
		Object value = options.get(org.sintef.thingml.resource.thingml.IThingmlOptions.ADDITIONAL_REFERENCE_RESOLVERS);
		if (value == null) {
			return originalResolver;
		}
		if (!(value instanceof java.util.Map)) {
			// send this to the error log
			new org.sintef.thingml.resource.thingml.util.ThingmlRuntimeUtil().logWarning("Found value with invalid type for option " + org.sintef.thingml.resource.thingml.IThingmlOptions.ADDITIONAL_REFERENCE_RESOLVERS + " (expected " + java.util.Map.class.getName() + ", but was " + value.getClass().getName() + ")", null);
			return originalResolver;
		}
		java.util.Map<?,?> resolverMap = (java.util.Map<?,?>) value;
		Object resolverValue = resolverMap.get(reference);
		if (resolverValue == null) {
			return originalResolver;
		}
		if (resolverValue instanceof org.sintef.thingml.resource.thingml.IThingmlReferenceResolver) {
			org.sintef.thingml.resource.thingml.IThingmlReferenceResolver replacingResolver = (org.sintef.thingml.resource.thingml.IThingmlReferenceResolver) resolverValue;
			if (replacingResolver instanceof org.sintef.thingml.resource.thingml.IThingmlDelegatingReferenceResolver) {
				// pass original resolver to the replacing one
				((org.sintef.thingml.resource.thingml.IThingmlDelegatingReferenceResolver) replacingResolver).setDelegate(originalResolver);
			}
			return replacingResolver;
		} else if (resolverValue instanceof java.util.Collection) {
			java.util.Collection replacingResolvers = (java.util.Collection) resolverValue;
			org.sintef.thingml.resource.thingml.IThingmlReferenceResolver replacingResolver = originalResolver;
			for (Object next : replacingResolvers) {
				if (next instanceof org.sintef.thingml.resource.thingml.IThingmlReferenceCache) {
					org.sintef.thingml.resource.thingml.IThingmlReferenceResolver nextResolver = (org.sintef.thingml.resource.thingml.IThingmlReferenceResolver) next;
					if (nextResolver instanceof org.sintef.thingml.resource.thingml.IThingmlDelegatingReferenceResolver) {
						// pass original resolver to the replacing one
						((org.sintef.thingml.resource.thingml.IThingmlDelegatingReferenceResolver) nextResolver).setDelegate(replacingResolver);
					}
					replacingResolver = nextResolver;
				} else {
					// The collection contains a non-resolver. Send a warning to the error log.
					new org.sintef.thingml.resource.thingml.util.ThingmlRuntimeUtil().logWarning("Found value with invalid type in value map for option " + org.sintef.thingml.resource.thingml.IThingmlOptions.ADDITIONAL_REFERENCE_RESOLVERS + " (expected " + org.sintef.thingml.resource.thingml.IThingmlDelegatingReferenceResolver.class.getName() + ", but was " + next.getClass().getName() + ")", null);
				}
			}
			return replacingResolver;
		} else {
			// The value for the option ADDITIONAL_REFERENCE_RESOLVERS has an unknown type.
			new org.sintef.thingml.resource.thingml.util.ThingmlRuntimeUtil().logWarning("Found value with invalid type in value map for option " + org.sintef.thingml.resource.thingml.IThingmlOptions.ADDITIONAL_REFERENCE_RESOLVERS + " (expected " + org.sintef.thingml.resource.thingml.IThingmlDelegatingReferenceResolver.class.getName() + ", but was " + resolverValue.getClass().getName() + ")", null);
			return originalResolver;
		}
	}
	
}
