/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
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
	protected org.sintef.thingml.resource.thingml.analysis.EventReferenceMsgRefReferenceResolver eventReferenceMsgRefReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.EventReferenceMsgRefReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.EventReferenceParamRefReferenceResolver eventReferenceParamRefReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.EventReferenceParamRefReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.PropertyReferencePropertyReferenceResolver propertyReferencePropertyReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.PropertyReferencePropertyReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.EnumLiteralRefEnumReferenceResolver enumLiteralRefEnumReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.EnumLiteralRefEnumReferenceResolver();
	protected org.sintef.thingml.resource.thingml.analysis.EnumLiteralRefLiteralReferenceResolver enumLiteralRefLiteralReferenceResolver = new org.sintef.thingml.resource.thingml.analysis.EnumLiteralRefLiteralReferenceResolver();
	
	public org.sintef.thingml.resource.thingml.analysis.ThingMLModelImportsReferenceResolver getThingMLModelImportsReferenceResolver() {
		return thingMLModelImportsReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.TypedElementTypeReferenceResolver getTypedElementTypeReferenceResolver() {
		return typedElementTypeReferenceResolver;
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
	
	public org.sintef.thingml.resource.thingml.analysis.PropertyAssignPropertyReferenceResolver getPropertyAssignPropertyReferenceResolver() {
		return propertyAssignPropertyReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.ConfigIncludeConfigReferenceResolver getConfigIncludeConfigReferenceResolver() {
		return configIncludeConfigReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.InstanceTypeReferenceResolver getInstanceTypeReferenceResolver() {
		return instanceTypeReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.ConnectorRequiredReferenceResolver getConnectorRequiredReferenceResolver() {
		return connectorRequiredReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.ConnectorProvidedReferenceResolver getConnectorProvidedReferenceResolver() {
		return connectorProvidedReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.ConfigPropertyAssignPropertyReferenceResolver getConfigPropertyAssignPropertyReferenceResolver() {
		return configPropertyAssignPropertyReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.InstanceRefConfigReferenceResolver getInstanceRefConfigReferenceResolver() {
		return instanceRefConfigReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.InstanceRefInstanceReferenceResolver getInstanceRefInstanceReferenceResolver() {
		return instanceRefInstanceReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.SendActionPortReferenceResolver getSendActionPortReferenceResolver() {
		return sendActionPortReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.SendActionMessageReferenceResolver getSendActionMessageReferenceResolver() {
		return sendActionMessageReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.VariableAssignmentPropertyReferenceResolver getVariableAssignmentPropertyReferenceResolver() {
		return variableAssignmentPropertyReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.FunctionCallFunctionReferenceResolver getFunctionCallFunctionReferenceResolver() {
		return functionCallFunctionReferenceResolver;
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
	
	public org.sintef.thingml.resource.thingml.analysis.EnumLiteralRefEnumReferenceResolver getEnumLiteralRefEnumReferenceResolver() {
		return enumLiteralRefEnumReferenceResolver;
	}
	
	public org.sintef.thingml.resource.thingml.analysis.EnumLiteralRefLiteralReferenceResolver getEnumLiteralRefLiteralReferenceResolver() {
		return enumLiteralRefLiteralReferenceResolver;
	}
	
	public void setOptions(java.util.Map<?, ?> options) {
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
			return thingMLModelImportsReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getTypedElement_Type()) {
			return typedElementTypeReferenceResolver;
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
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getPropertyAssign_Property()) {
			return propertyAssignPropertyReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getConfigInclude_Config()) {
			return configIncludeConfigReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getInstance_Type()) {
			return instanceTypeReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getConnector_Required()) {
			return connectorRequiredReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getConnector_Provided()) {
			return connectorProvidedReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getConfigPropertyAssign_Property()) {
			return configPropertyAssignPropertyReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getInstanceRef_Config()) {
			return instanceRefConfigReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getInstanceRef_Instance()) {
			return instanceRefInstanceReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getSendAction_Port()) {
			return sendActionPortReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getSendAction_Message()) {
			return sendActionMessageReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getVariableAssignment_Property()) {
			return variableAssignmentPropertyReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getFunctionCall_Function()) {
			return functionCallFunctionReferenceResolver;
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
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getEnumLiteralRef_Enum()) {
			return enumLiteralRefEnumReferenceResolver;
		}
		if (reference == org.sintef.thingml.ThingmlPackage.eINSTANCE.getEnumLiteralRef_Literal()) {
			return enumLiteralRefLiteralReferenceResolver;
		}
		return null;
	}
	
}
