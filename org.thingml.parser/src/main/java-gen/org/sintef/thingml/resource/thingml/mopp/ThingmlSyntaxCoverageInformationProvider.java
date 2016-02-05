/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.mopp;

public class ThingmlSyntaxCoverageInformationProvider {
	
	public org.eclipse.emf.ecore.EClass[] getClassesWithSyntax() {
		return new org.eclipse.emf.ecore.EClass[] {
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getThingMLModel(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getMessage(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getFunction(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getThing(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getRequiredPort(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getProvidedPort(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getInternalPort(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getProperty(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getParameter(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getPrimitiveType(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getObjectType(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getProtocol(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getEnumeration(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getEnumerationLiteral(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getPlatformAnnotation(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getStateMachine(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getState(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getCompositeState(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getParallelRegion(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getTransition(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getInternalTransition(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getReceiveMessage(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getPropertyAssign(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getIncrement(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getDecrement(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getConfiguration(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getInstance(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getConnector(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getExternalConnector(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getConfigPropertyAssign(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getInstanceRef(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getSendAction(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getVariableAssignment(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getActionBlock(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getLocalVariable(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getExternStatement(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getConditionalAction(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getLoopAction(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getPrintAction(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getErrorAction(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getReturnAction(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getFunctionCallStatement(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getOperator(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getMessageParameter(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getOperatorCall(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getFilter(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getLengthWindow(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getTimeWindow(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getSimpleSource(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getJoinSources(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getMergeSources(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getStream(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getSimpleParamRef(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getArrayParamRef(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getLengthArray(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getOrExpression(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getAndExpression(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getLowerExpression(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getGreaterExpression(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getLowerOrEqualExpression(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getGreaterOrEqualExpression(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getEqualsExpression(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getPlusExpression(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getMinusExpression(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getTimesExpression(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getDivExpression(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getModExpression(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getUnaryMinus(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getNotExpression(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getReference(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getExpressionGroup(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getPropertyReference(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getIntegerLiteral(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getStringLiteral(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getBooleanLiteral(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getEnumLiteralRef(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getArrayIndex(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getFunctionCallExpression(),
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getExternExpression(),
		};
	}
	
	public org.eclipse.emf.ecore.EClass[] getStartSymbols() {
		return new org.eclipse.emf.ecore.EClass[] {
			org.sintef.thingml.ThingmlPackage.eINSTANCE.getThingMLModel(),
		};
	}
	
}
