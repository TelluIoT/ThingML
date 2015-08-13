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

public class ThingmlPrinter implements org.sintef.thingml.resource.thingml.IThingmlTextPrinter {
	
	protected org.sintef.thingml.resource.thingml.IThingmlTokenResolverFactory tokenResolverFactory = new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenResolverFactory();
	
	protected java.io.OutputStream outputStream;
	
	/**
	 * Holds the resource that is associated with this printer. This may be null if
	 * the printer is used stand alone.
	 */
	private org.sintef.thingml.resource.thingml.IThingmlTextResource resource;
	
	private java.util.Map<?, ?> options;
	private String encoding = System.getProperty("file.encoding");
	
	public ThingmlPrinter(java.io.OutputStream outputStream, org.sintef.thingml.resource.thingml.IThingmlTextResource resource) {
		super();
		this.outputStream = outputStream;
		this.resource = resource;
	}
	
	protected int matchCount(java.util.Map<String, Integer> featureCounter, java.util.Collection<String> needed) {
		int pos = 0;
		int neg = 0;
		
		for (String featureName : featureCounter.keySet()) {
			if (needed.contains(featureName)) {
				int value = featureCounter.get(featureName);
				if (value == 0) {
					neg += 1;
				} else {
					pos += 1;
				}
			}
		}
		return neg > 0 ? -neg : pos;
	}
	
	protected void doPrint(org.eclipse.emf.ecore.EObject element, java.io.PrintWriter out, String globaltab) {
		if (element == null) {
			throw new java.lang.IllegalArgumentException("Nothing to write.");
		}
		if (out == null) {
			throw new java.lang.IllegalArgumentException("Nothing to write on.");
		}
		
		if (element instanceof org.sintef.thingml.ThingMLModel) {
			print_org_sintef_thingml_ThingMLModel((org.sintef.thingml.ThingMLModel) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Message) {
			print_org_sintef_thingml_Message((org.sintef.thingml.Message) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Function) {
			print_org_sintef_thingml_Function((org.sintef.thingml.Function) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Thing) {
			print_org_sintef_thingml_Thing((org.sintef.thingml.Thing) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.RequiredPort) {
			print_org_sintef_thingml_RequiredPort((org.sintef.thingml.RequiredPort) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ProvidedPort) {
			print_org_sintef_thingml_ProvidedPort((org.sintef.thingml.ProvidedPort) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Property) {
			print_org_sintef_thingml_Property((org.sintef.thingml.Property) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Parameter) {
			print_org_sintef_thingml_Parameter((org.sintef.thingml.Parameter) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.PrimitiveType) {
			print_org_sintef_thingml_PrimitiveType((org.sintef.thingml.PrimitiveType) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Enumeration) {
			print_org_sintef_thingml_Enumeration((org.sintef.thingml.Enumeration) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.EnumerationLiteral) {
			print_org_sintef_thingml_EnumerationLiteral((org.sintef.thingml.EnumerationLiteral) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.PlatformAnnotation) {
			print_org_sintef_thingml_PlatformAnnotation((org.sintef.thingml.PlatformAnnotation) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.StateMachine) {
			print_org_sintef_thingml_StateMachine((org.sintef.thingml.StateMachine) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.CompositeState) {
			print_org_sintef_thingml_CompositeState((org.sintef.thingml.CompositeState) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ParallelRegion) {
			print_org_sintef_thingml_ParallelRegion((org.sintef.thingml.ParallelRegion) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Transition) {
			print_org_sintef_thingml_Transition((org.sintef.thingml.Transition) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.InternalTransition) {
			print_org_sintef_thingml_InternalTransition((org.sintef.thingml.InternalTransition) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ReceiveMessage) {
			print_org_sintef_thingml_ReceiveMessage((org.sintef.thingml.ReceiveMessage) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.PropertyAssign) {
			print_org_sintef_thingml_PropertyAssign((org.sintef.thingml.PropertyAssign) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Configuration) {
			print_org_sintef_thingml_Configuration((org.sintef.thingml.Configuration) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ConfigInclude) {
			print_org_sintef_thingml_ConfigInclude((org.sintef.thingml.ConfigInclude) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Instance) {
			print_org_sintef_thingml_Instance((org.sintef.thingml.Instance) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Connector) {
			print_org_sintef_thingml_Connector((org.sintef.thingml.Connector) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ConfigPropertyAssign) {
			print_org_sintef_thingml_ConfigPropertyAssign((org.sintef.thingml.ConfigPropertyAssign) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.InstanceRef) {
			print_org_sintef_thingml_InstanceRef((org.sintef.thingml.InstanceRef) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.SendAction) {
			print_org_sintef_thingml_SendAction((org.sintef.thingml.SendAction) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.VariableAssignment) {
			print_org_sintef_thingml_VariableAssignment((org.sintef.thingml.VariableAssignment) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ActionBlock) {
			print_org_sintef_thingml_ActionBlock((org.sintef.thingml.ActionBlock) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.LocalVariable) {
			print_org_sintef_thingml_LocalVariable((org.sintef.thingml.LocalVariable) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ExternStatement) {
			print_org_sintef_thingml_ExternStatement((org.sintef.thingml.ExternStatement) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ConditionalAction) {
			print_org_sintef_thingml_ConditionalAction((org.sintef.thingml.ConditionalAction) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.LoopAction) {
			print_org_sintef_thingml_LoopAction((org.sintef.thingml.LoopAction) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.PrintAction) {
			print_org_sintef_thingml_PrintAction((org.sintef.thingml.PrintAction) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ErrorAction) {
			print_org_sintef_thingml_ErrorAction((org.sintef.thingml.ErrorAction) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ReturnAction) {
			print_org_sintef_thingml_ReturnAction((org.sintef.thingml.ReturnAction) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.FunctionCallStatement) {
			print_org_sintef_thingml_FunctionCallStatement((org.sintef.thingml.FunctionCallStatement) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.SglMsgParamOperator) {
			print_org_sintef_thingml_SglMsgParamOperator((org.sintef.thingml.SglMsgParamOperator) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.MessageParameter) {
			print_org_sintef_thingml_MessageParameter((org.sintef.thingml.MessageParameter) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.SglMsgParamOperatorCall) {
			print_org_sintef_thingml_SglMsgParamOperatorCall((org.sintef.thingml.SglMsgParamOperatorCall) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.StreamExpression) {
			print_org_sintef_thingml_StreamExpression((org.sintef.thingml.StreamExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.StreamOutput) {
			print_org_sintef_thingml_StreamOutput((org.sintef.thingml.StreamOutput) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Filter) {
			print_org_sintef_thingml_Filter((org.sintef.thingml.Filter) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.LengthWindow) {
			print_org_sintef_thingml_LengthWindow((org.sintef.thingml.LengthWindow) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.TimeWindow) {
			print_org_sintef_thingml_TimeWindow((org.sintef.thingml.TimeWindow) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.SimpleSource) {
			print_org_sintef_thingml_SimpleSource((org.sintef.thingml.SimpleSource) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.JoinSources) {
			print_org_sintef_thingml_JoinSources((org.sintef.thingml.JoinSources) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.MergeSources) {
			print_org_sintef_thingml_MergeSources((org.sintef.thingml.MergeSources) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Stream) {
			print_org_sintef_thingml_Stream((org.sintef.thingml.Stream) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.SimpleParamRef) {
			print_org_sintef_thingml_SimpleParamRef((org.sintef.thingml.SimpleParamRef) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ArrayParamRef) {
			print_org_sintef_thingml_ArrayParamRef((org.sintef.thingml.ArrayParamRef) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.LengthArray) {
			print_org_sintef_thingml_LengthArray((org.sintef.thingml.LengthArray) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.OrExpression) {
			print_org_sintef_thingml_OrExpression((org.sintef.thingml.OrExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.AndExpression) {
			print_org_sintef_thingml_AndExpression((org.sintef.thingml.AndExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.LowerExpression) {
			print_org_sintef_thingml_LowerExpression((org.sintef.thingml.LowerExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.GreaterExpression) {
			print_org_sintef_thingml_GreaterExpression((org.sintef.thingml.GreaterExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.EqualsExpression) {
			print_org_sintef_thingml_EqualsExpression((org.sintef.thingml.EqualsExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.PlusExpression) {
			print_org_sintef_thingml_PlusExpression((org.sintef.thingml.PlusExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.MinusExpression) {
			print_org_sintef_thingml_MinusExpression((org.sintef.thingml.MinusExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.TimesExpression) {
			print_org_sintef_thingml_TimesExpression((org.sintef.thingml.TimesExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.DivExpression) {
			print_org_sintef_thingml_DivExpression((org.sintef.thingml.DivExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ModExpression) {
			print_org_sintef_thingml_ModExpression((org.sintef.thingml.ModExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.UnaryMinus) {
			print_org_sintef_thingml_UnaryMinus((org.sintef.thingml.UnaryMinus) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.NotExpression) {
			print_org_sintef_thingml_NotExpression((org.sintef.thingml.NotExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.StreamParamReference) {
			print_org_sintef_thingml_StreamParamReference((org.sintef.thingml.StreamParamReference) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Reference) {
			print_org_sintef_thingml_Reference((org.sintef.thingml.Reference) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ExpressionGroup) {
			print_org_sintef_thingml_ExpressionGroup((org.sintef.thingml.ExpressionGroup) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.PropertyReference) {
			print_org_sintef_thingml_PropertyReference((org.sintef.thingml.PropertyReference) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.IntegerLiteral) {
			print_org_sintef_thingml_IntegerLiteral((org.sintef.thingml.IntegerLiteral) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.StringLiteral) {
			print_org_sintef_thingml_StringLiteral((org.sintef.thingml.StringLiteral) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.BooleanLiteral) {
			print_org_sintef_thingml_BooleanLiteral((org.sintef.thingml.BooleanLiteral) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.EnumLiteralRef) {
			print_org_sintef_thingml_EnumLiteralRef((org.sintef.thingml.EnumLiteralRef) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ArrayIndex) {
			print_org_sintef_thingml_ArrayIndex((org.sintef.thingml.ArrayIndex) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.FunctionCallExpression) {
			print_org_sintef_thingml_FunctionCallExpression((org.sintef.thingml.FunctionCallExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ExternExpression) {
			print_org_sintef_thingml_ExternExpression((org.sintef.thingml.ExternExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.State) {
			print_org_sintef_thingml_State((org.sintef.thingml.State) element, globaltab, out);
			return;
		}
		
		addWarningToResource("The printer can not handle " + element.eClass().getName() + " elements", element);
	}
	
	protected org.sintef.thingml.resource.thingml.mopp.ThingmlReferenceResolverSwitch getReferenceResolverSwitch() {
		return (org.sintef.thingml.resource.thingml.mopp.ThingmlReferenceResolverSwitch) new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().getReferenceResolverSwitch();
	}
	
	protected void addWarningToResource(final String errorMessage, org.eclipse.emf.ecore.EObject cause) {
		org.sintef.thingml.resource.thingml.IThingmlTextResource resource = getResource();
		if (resource == null) {
			// the resource can be null if the printer is used stand alone
			return;
		}
		resource.addProblem(new org.sintef.thingml.resource.thingml.mopp.ThingmlProblem(errorMessage, org.sintef.thingml.resource.thingml.ThingmlEProblemType.PRINT_PROBLEM, org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity.WARNING), cause);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		this.options = options;
	}
	
	public java.util.Map<?,?> getOptions() {
		return options;
	}
	
	public void setEncoding(String encoding) {
		if (encoding != null) {
			this.encoding = encoding;
		}
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlTextResource getResource() {
		return resource;
	}
	
	/**
	 * Calls {@link #doPrint(EObject, PrintWriter, String)} and writes the result to
	 * the underlying output stream.
	 */
	public void print(org.eclipse.emf.ecore.EObject element) throws java.io.IOException {
		java.io.PrintWriter out = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.BufferedOutputStream(outputStream), encoding));
		doPrint(element, out, "");
		out.flush();
		out.close();
	}
	
	public void print_org_sintef_thingml_ThingMLModel(org.sintef.thingml.ThingMLModel element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(3);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__TYPES));
		printCountingMap.put("types", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__IMPORTS));
		printCountingMap.put("imports", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__CONFIGS));
		printCountingMap.put("configs", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_ThingMLModel_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_ThingMLModel_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_ThingMLModel_0(org.sintef.thingml.ThingMLModel element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("import");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("imports");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__IMPORTS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("STRING_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getThingMLModelImportsReferenceResolver().deResolve((org.sintef.thingml.ThingMLModel) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__IMPORTS)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__IMPORTS), element));
			}
			printCountingMap.put("imports", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_ThingMLModel_1(org.sintef.thingml.ThingMLModel element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CompoundDefinition)
		print_org_sintef_thingml_ThingMLModel_1_0(element, localtab, out, printCountingMap);
	}
	
	public void print_org_sintef_thingml_ThingMLModel_1_0(org.sintef.thingml.ThingMLModel element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"types"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"configs"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("configs");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__CONFIGS));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("configs", count - 1);
				}
			}
			break;
			default:			// DEFINITION PART BEGINS (Containment)
			count = printCountingMap.get("types");
			if (count > 0) {
				Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__TYPES));
				java.util.List<?> list = (java.util.List<?>) o;
				int index = list.size() - count;
				if (index >= 0) {
					o = list.get(index);
				} else {
					o = null;
				}
				if (o != null) {
					doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
				}
				printCountingMap.put("types", count - 1);
			}
		}
	}
	
	
	public void print_org_sintef_thingml_Message(org.sintef.thingml.Message element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(3);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE__PARAMETERS));
		printCountingMap.put("parameters", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("message");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Message_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Message_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(";");
	}
	
	public void print_org_sintef_thingml_Message_0(org.sintef.thingml.Message element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("parameters");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE__PARAMETERS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("parameters", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Message_0_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_Message_0_0(org.sintef.thingml.Message element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("parameters");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE__PARAMETERS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("parameters", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Message_1(org.sintef.thingml.Message element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_Function(org.sintef.thingml.Function element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(7);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__TYPE));
		printCountingMap.put("type", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__CARDINALITY));
		printCountingMap.put("cardinality", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__IS_ARRAY));
		printCountingMap.put("isArray", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__PARAMETERS));
		printCountingMap.put("parameters", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__BODY));
		printCountingMap.put("body", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("function");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Function_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Function_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Function_2(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("body");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__BODY));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("body", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Function_0(org.sintef.thingml.Function element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("parameters");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__PARAMETERS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("parameters", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Function_0_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_Function_0_0(org.sintef.thingml.Function element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("parameters");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__PARAMETERS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("parameters", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Function_1(org.sintef.thingml.Function element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Function_2(org.sintef.thingml.Function element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("type");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__TYPE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getTypedElementTypeReferenceResolver().deResolve((org.sintef.thingml.Type) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__TYPE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__TYPE), element));
			}
			printCountingMap.put("type", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Function_2_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
	}
	
	public void print_org_sintef_thingml_Function_2_0(org.sintef.thingml.Function element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"cardinality"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"isArray"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				// DEFINITION PART BEGINS (CompoundDefinition)
				print_org_sintef_thingml_Function_2_0_1(element, localtab, out, printCountingMap);
			}
			break;
			default:			// DEFINITION PART BEGINS (CompoundDefinition)
			print_org_sintef_thingml_Function_2_0_0(element, localtab, out, printCountingMap);
		}
	}
	
	public void print_org_sintef_thingml_Function_2_0_0(org.sintef.thingml.Function element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("[");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("cardinality");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__CARDINALITY));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("cardinality", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("]");
	}
	
	public void print_org_sintef_thingml_Function_2_0_1(org.sintef.thingml.Function element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("isArray");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__IS_ARRAY));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("T_ARRAY");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION__IS_ARRAY), element));
			}
			printCountingMap.put("isArray", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_Thing(org.sintef.thingml.Thing element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(12);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__PROPERTIES));
		printCountingMap.put("properties", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__FRAGMENT));
		printCountingMap.put("fragment", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__PORTS));
		printCountingMap.put("ports", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__BEHAVIOUR));
		printCountingMap.put("behaviour", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__INCLUDES));
		printCountingMap.put("includes", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__ASSIGN));
		printCountingMap.put("assign", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__MESSAGES));
		printCountingMap.put("messages", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__FUNCTIONS));
		printCountingMap.put("functions", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__STREAMS));
		printCountingMap.put("streams", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__OPERATORS));
		printCountingMap.put("operators", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("thing");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Thing_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Thing_1(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Thing_2(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("{");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Thing_3(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("}");
	}
	
	public void print_org_sintef_thingml_Thing_0(org.sintef.thingml.Thing element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("fragment");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__FRAGMENT));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("T_ASPECT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__FRAGMENT), element));
			}
			printCountingMap.put("fragment", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Thing_1(org.sintef.thingml.Thing element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("includes");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("includes");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__INCLUDES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getThingIncludesReferenceResolver().deResolve((org.sintef.thingml.Thing) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__INCLUDES)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__INCLUDES), element));
			}
			printCountingMap.put("includes", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Thing_1_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_Thing_1_0(org.sintef.thingml.Thing element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("includes");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__INCLUDES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getThingIncludesReferenceResolver().deResolve((org.sintef.thingml.Thing) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__INCLUDES)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__INCLUDES), element));
			}
			printCountingMap.put("includes", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Thing_2(org.sintef.thingml.Thing element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Thing_3(org.sintef.thingml.Thing element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"messages"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"functions"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"properties"		));
		if (tempMatchCount > matches) {
			alt = 2;
			matches = tempMatchCount;
		}
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"assign"		));
		if (tempMatchCount > matches) {
			alt = 3;
			matches = tempMatchCount;
		}
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"ports"		));
		if (tempMatchCount > matches) {
			alt = 4;
			matches = tempMatchCount;
		}
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"behaviour"		));
		if (tempMatchCount > matches) {
			alt = 5;
			matches = tempMatchCount;
		}
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"streams"		));
		if (tempMatchCount > matches) {
			alt = 6;
			matches = tempMatchCount;
		}
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"operators"		));
		if (tempMatchCount > matches) {
			alt = 7;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("functions");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__FUNCTIONS));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("functions", count - 1);
				}
			}
			break;
			case 2:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("properties");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__PROPERTIES));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("properties", count - 1);
				}
			}
			break;
			case 3:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("assign");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__ASSIGN));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("assign", count - 1);
				}
			}
			break;
			case 4:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("ports");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__PORTS));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("ports", count - 1);
				}
			}
			break;
			case 5:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("behaviour");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__BEHAVIOUR));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("behaviour", count - 1);
				}
			}
			break;
			case 6:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("streams");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__STREAMS));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("streams", count - 1);
				}
			}
			break;
			case 7:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("operators");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__OPERATORS));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("operators", count - 1);
				}
			}
			break;
			default:			// DEFINITION PART BEGINS (Containment)
			count = printCountingMap.get("messages");
			if (count > 0) {
				Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__MESSAGES));
				java.util.List<?> list = (java.util.List<?>) o;
				int index = list.size() - count;
				if (index >= 0) {
					o = list.get(index);
				} else {
					o = null;
				}
				if (o != null) {
					doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
				}
				printCountingMap.put("messages", count - 1);
			}
		}
	}
	
	
	public void print_org_sintef_thingml_RequiredPort(org.sintef.thingml.RequiredPort element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(6);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__OWNER));
		printCountingMap.put("owner", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__RECEIVES));
		printCountingMap.put("receives", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__SENDS));
		printCountingMap.put("sends", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__OPTIONAL));
		printCountingMap.put("optional", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_RequiredPort_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("required");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("port");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_RequiredPort_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("{");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_RequiredPort_2(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("}");
	}
	
	public void print_org_sintef_thingml_RequiredPort_0(org.sintef.thingml.RequiredPort element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("optional");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__OPTIONAL));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("T_OPTIONAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__OPTIONAL), element));
			}
			printCountingMap.put("optional", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_RequiredPort_1(org.sintef.thingml.RequiredPort element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_RequiredPort_2(org.sintef.thingml.RequiredPort element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"receives"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"sends"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				boolean iterate = true;
				java.io.StringWriter sWriter = null;
				java.io.PrintWriter out1 = null;
				java.util.Map<String, Integer> printCountingMap1 = null;
				// DEFINITION PART BEGINS (CsString)
				out.print("sends");
				// DEFINITION PART BEGINS (WhiteSpaces)
				out.print(" ");
				// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
				count = printCountingMap.get("sends");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__SENDS));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
						resolver.setOptions(getOptions());
						out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortSendsReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__SENDS)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__SENDS), element));
					}
					printCountingMap.put("sends", count - 1);
				}
				// DEFINITION PART BEGINS (CompoundDefinition)
				iterate = true;
				while (iterate) {
					sWriter = new java.io.StringWriter();
					out1 = new java.io.PrintWriter(sWriter);
					printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
					print_org_sintef_thingml_RequiredPort_2_1(element, localtab, out1, printCountingMap1);
					if (printCountingMap.equals(printCountingMap1)) {
						iterate = false;
						out1.close();
					} else {
						out1.flush();
						out1.close();
						out.print(sWriter.toString());
						printCountingMap.putAll(printCountingMap1);
					}
				}
			}
			break;
			default:			boolean iterate = true;
			java.io.StringWriter sWriter = null;
			java.io.PrintWriter out1 = null;
			java.util.Map<String, Integer> printCountingMap1 = null;
			// DEFINITION PART BEGINS (CsString)
			out.print("receives");
			// DEFINITION PART BEGINS (WhiteSpaces)
			out.print(" ");
			// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
			count = printCountingMap.get("receives");
			if (count > 0) {
				Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__RECEIVES));
				java.util.List<?> list = (java.util.List<?>) o;
				int index = list.size() - count;
				if (index >= 0) {
					o = list.get(index);
				} else {
					o = null;
				}
				if (o != null) {
					org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
					resolver.setOptions(getOptions());
					out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortReceivesReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__RECEIVES)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__RECEIVES), element));
				}
				printCountingMap.put("receives", count - 1);
			}
			// DEFINITION PART BEGINS (CompoundDefinition)
			iterate = true;
			while (iterate) {
				sWriter = new java.io.StringWriter();
				out1 = new java.io.PrintWriter(sWriter);
				printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
				print_org_sintef_thingml_RequiredPort_2_0(element, localtab, out1, printCountingMap1);
				if (printCountingMap.equals(printCountingMap1)) {
					iterate = false;
					out1.close();
				} else {
					out1.flush();
					out1.close();
					out.print(sWriter.toString());
					printCountingMap.putAll(printCountingMap1);
				}
			}
		}
	}
	
	public void print_org_sintef_thingml_RequiredPort_2_0(org.sintef.thingml.RequiredPort element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("receives");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__RECEIVES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortReceivesReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__RECEIVES)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__RECEIVES), element));
			}
			printCountingMap.put("receives", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_RequiredPort_2_1(org.sintef.thingml.RequiredPort element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("sends");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__SENDS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortSendsReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__SENDS)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__SENDS), element));
			}
			printCountingMap.put("sends", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_ProvidedPort(org.sintef.thingml.ProvidedPort element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(5);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__OWNER));
		printCountingMap.put("owner", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__RECEIVES));
		printCountingMap.put("receives", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__SENDS));
		printCountingMap.put("sends", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("provided");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("port");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_ProvidedPort_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("{");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_ProvidedPort_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("}");
	}
	
	public void print_org_sintef_thingml_ProvidedPort_0(org.sintef.thingml.ProvidedPort element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_ProvidedPort_1(org.sintef.thingml.ProvidedPort element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"receives"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"sends"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				boolean iterate = true;
				java.io.StringWriter sWriter = null;
				java.io.PrintWriter out1 = null;
				java.util.Map<String, Integer> printCountingMap1 = null;
				// DEFINITION PART BEGINS (CsString)
				out.print("sends");
				// DEFINITION PART BEGINS (WhiteSpaces)
				out.print(" ");
				// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
				count = printCountingMap.get("sends");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__SENDS));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
						resolver.setOptions(getOptions());
						out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortSendsReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__SENDS)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__SENDS), element));
					}
					printCountingMap.put("sends", count - 1);
				}
				// DEFINITION PART BEGINS (CompoundDefinition)
				iterate = true;
				while (iterate) {
					sWriter = new java.io.StringWriter();
					out1 = new java.io.PrintWriter(sWriter);
					printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
					print_org_sintef_thingml_ProvidedPort_1_1(element, localtab, out1, printCountingMap1);
					if (printCountingMap.equals(printCountingMap1)) {
						iterate = false;
						out1.close();
					} else {
						out1.flush();
						out1.close();
						out.print(sWriter.toString());
						printCountingMap.putAll(printCountingMap1);
					}
				}
			}
			break;
			default:			boolean iterate = true;
			java.io.StringWriter sWriter = null;
			java.io.PrintWriter out1 = null;
			java.util.Map<String, Integer> printCountingMap1 = null;
			// DEFINITION PART BEGINS (CsString)
			out.print("receives");
			// DEFINITION PART BEGINS (WhiteSpaces)
			out.print(" ");
			// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
			count = printCountingMap.get("receives");
			if (count > 0) {
				Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__RECEIVES));
				java.util.List<?> list = (java.util.List<?>) o;
				int index = list.size() - count;
				if (index >= 0) {
					o = list.get(index);
				} else {
					o = null;
				}
				if (o != null) {
					org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
					resolver.setOptions(getOptions());
					out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortReceivesReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__RECEIVES)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__RECEIVES), element));
				}
				printCountingMap.put("receives", count - 1);
			}
			// DEFINITION PART BEGINS (CompoundDefinition)
			iterate = true;
			while (iterate) {
				sWriter = new java.io.StringWriter();
				out1 = new java.io.PrintWriter(sWriter);
				printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
				print_org_sintef_thingml_ProvidedPort_1_0(element, localtab, out1, printCountingMap1);
				if (printCountingMap.equals(printCountingMap1)) {
					iterate = false;
					out1.close();
				} else {
					out1.flush();
					out1.close();
					out.print(sWriter.toString());
					printCountingMap.putAll(printCountingMap1);
				}
			}
		}
	}
	
	public void print_org_sintef_thingml_ProvidedPort_1_0(org.sintef.thingml.ProvidedPort element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("receives");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__RECEIVES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortReceivesReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__RECEIVES)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__RECEIVES), element));
			}
			printCountingMap.put("receives", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_ProvidedPort_1_1(org.sintef.thingml.ProvidedPort element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("sends");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__SENDS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortSendsReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__SENDS)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__SENDS), element));
			}
			printCountingMap.put("sends", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_Property(org.sintef.thingml.Property element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(7);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__TYPE));
		printCountingMap.put("type", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__CARDINALITY));
		printCountingMap.put("cardinality", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__IS_ARRAY));
		printCountingMap.put("isArray", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__INIT));
		printCountingMap.put("init", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__CHANGEABLE));
		printCountingMap.put("changeable", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Property_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("property");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("type");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__TYPE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getTypedElementTypeReferenceResolver().deResolve((org.sintef.thingml.Type) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__TYPE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__TYPE), element));
			}
			printCountingMap.put("type", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Property_1(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Property_2(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Property_3(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_Property_0(org.sintef.thingml.Property element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("changeable");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__CHANGEABLE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("T_READONLY");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__CHANGEABLE), element));
			}
			printCountingMap.put("changeable", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Property_1(org.sintef.thingml.Property element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"cardinality"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"isArray"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				// DEFINITION PART BEGINS (CompoundDefinition)
				print_org_sintef_thingml_Property_1_1(element, localtab, out, printCountingMap);
			}
			break;
			default:			// DEFINITION PART BEGINS (CompoundDefinition)
			print_org_sintef_thingml_Property_1_0(element, localtab, out, printCountingMap);
		}
	}
	
	public void print_org_sintef_thingml_Property_1_0(org.sintef.thingml.Property element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("[");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("cardinality");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__CARDINALITY));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("cardinality", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("]");
	}
	
	public void print_org_sintef_thingml_Property_1_1(org.sintef.thingml.Property element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("isArray");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__IS_ARRAY));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("T_ARRAY");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__IS_ARRAY), element));
			}
			printCountingMap.put("isArray", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Property_2(org.sintef.thingml.Property element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("=");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("init");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__INIT));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("init", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Property_3(org.sintef.thingml.Property element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_Parameter(org.sintef.thingml.Parameter element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(5);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__TYPE));
		printCountingMap.put("type", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__CARDINALITY));
		printCountingMap.put("cardinality", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__IS_ARRAY));
		printCountingMap.put("isArray", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("type");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__TYPE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getTypedElementTypeReferenceResolver().deResolve((org.sintef.thingml.Type) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__TYPE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__TYPE), element));
			}
			printCountingMap.put("type", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Parameter_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
	}
	
	public void print_org_sintef_thingml_Parameter_0(org.sintef.thingml.Parameter element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"cardinality"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"isArray"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				// DEFINITION PART BEGINS (CompoundDefinition)
				print_org_sintef_thingml_Parameter_0_1(element, localtab, out, printCountingMap);
			}
			break;
			default:			// DEFINITION PART BEGINS (CompoundDefinition)
			print_org_sintef_thingml_Parameter_0_0(element, localtab, out, printCountingMap);
		}
	}
	
	public void print_org_sintef_thingml_Parameter_0_0(org.sintef.thingml.Parameter element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("[");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("cardinality");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__CARDINALITY));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("cardinality", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("]");
	}
	
	public void print_org_sintef_thingml_Parameter_0_1(org.sintef.thingml.Parameter element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("isArray");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__IS_ARRAY));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("T_ARRAY");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__IS_ARRAY), element));
			}
			printCountingMap.put("isArray", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_PrimitiveType(org.sintef.thingml.PrimitiveType element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PRIMITIVE_TYPE__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PRIMITIVE_TYPE__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("datatype");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PRIMITIVE_TYPE__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PRIMITIVE_TYPE__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_PrimitiveType_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(";");
	}
	
	public void print_org_sintef_thingml_PrimitiveType_0(org.sintef.thingml.PrimitiveType element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PRIMITIVE_TYPE__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_Enumeration(org.sintef.thingml.Enumeration element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(3);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION__LITERALS));
		printCountingMap.put("literals", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("enumeration");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Enumeration_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("{");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Enumeration_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("}");
	}
	
	public void print_org_sintef_thingml_Enumeration_0(org.sintef.thingml.Enumeration element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Enumeration_1(org.sintef.thingml.Enumeration element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("literals");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION__LITERALS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("literals", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_EnumerationLiteral(org.sintef.thingml.EnumerationLiteral element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(3);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION_LITERAL__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION_LITERAL__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION_LITERAL__ENUM));
		printCountingMap.put("enum", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION_LITERAL__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION_LITERAL__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_EnumerationLiteral_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_EnumerationLiteral_0(org.sintef.thingml.EnumerationLiteral element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION_LITERAL__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_PlatformAnnotation(org.sintef.thingml.PlatformAnnotation element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLATFORM_ANNOTATION__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLATFORM_ANNOTATION__VALUE));
		printCountingMap.put("value", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLATFORM_ANNOTATION__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("ANNOTATION");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLATFORM_ANNOTATION__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("value");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLATFORM_ANNOTATION__VALUE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("STRING_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLATFORM_ANNOTATION__VALUE), element));
			}
			printCountingMap.put("value", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_StateMachine(org.sintef.thingml.StateMachine element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(12);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__OUTGOING));
		printCountingMap.put("outgoing", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__INCOMING));
		printCountingMap.put("incoming", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__ENTRY));
		printCountingMap.put("entry", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__EXIT));
		printCountingMap.put("exit", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__PROPERTIES));
		printCountingMap.put("properties", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__INTERNAL));
		printCountingMap.put("internal", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__SUBSTATE));
		printCountingMap.put("substate", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__INITIAL));
		printCountingMap.put("initial", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__HISTORY));
		printCountingMap.put("history", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__REGION));
		printCountingMap.put("region", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("statechart");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_StateMachine_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("init");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("initial");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__INITIAL));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getRegionInitialReferenceResolver().deResolve((org.sintef.thingml.State) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__INITIAL)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__INITIAL), element));
			}
			printCountingMap.put("initial", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_StateMachine_1(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_StateMachine_2(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("{");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_StateMachine_3(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_StateMachine_4(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_StateMachine_5(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_StateMachine_6(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_StateMachine_7(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("}");
	}
	
	public void print_org_sintef_thingml_StateMachine_0(org.sintef.thingml.StateMachine element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_StateMachine_1(org.sintef.thingml.StateMachine element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("keeps");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("history");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__HISTORY));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("T_HISTORY");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__HISTORY), element));
			}
			printCountingMap.put("history", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_StateMachine_2(org.sintef.thingml.StateMachine element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_StateMachine_3(org.sintef.thingml.StateMachine element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("properties");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__PROPERTIES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("properties", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_StateMachine_4(org.sintef.thingml.StateMachine element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("on");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("entry");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("entry");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__ENTRY));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("entry", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_StateMachine_5(org.sintef.thingml.StateMachine element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("on");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("exit");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("exit");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__EXIT));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("exit", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_StateMachine_6(org.sintef.thingml.StateMachine element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"substate"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"internal"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("internal");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__INTERNAL));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("internal", count - 1);
				}
			}
			break;
			default:			// DEFINITION PART BEGINS (CompoundDefinition)
			print_org_sintef_thingml_StateMachine_6_0(element, localtab, out, printCountingMap);
		}
	}
	
	public void print_org_sintef_thingml_StateMachine_6_0(org.sintef.thingml.StateMachine element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("substate");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__SUBSTATE));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("substate", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_StateMachine_7(org.sintef.thingml.StateMachine element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("region");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__REGION));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("region", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_State(org.sintef.thingml.State element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(8);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__OUTGOING));
		printCountingMap.put("outgoing", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__INCOMING));
		printCountingMap.put("incoming", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__ENTRY));
		printCountingMap.put("entry", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__EXIT));
		printCountingMap.put("exit", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__PROPERTIES));
		printCountingMap.put("properties", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__INTERNAL));
		printCountingMap.put("internal", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("state");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_State_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("{");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_State_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_State_2(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_State_3(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_State_4(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("}");
	}
	
	public void print_org_sintef_thingml_State_0(org.sintef.thingml.State element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_State_1(org.sintef.thingml.State element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("properties");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__PROPERTIES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("properties", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_State_2(org.sintef.thingml.State element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("on");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("entry");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("entry");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__ENTRY));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("entry", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_State_3(org.sintef.thingml.State element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("on");
		// DEFINITION PART BEGINS (CsString)
		out.print("exit");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("exit");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__EXIT));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("exit", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_State_4(org.sintef.thingml.State element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"outgoing"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"internal"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("internal");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__INTERNAL));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("internal", count - 1);
				}
			}
			break;
			default:			// DEFINITION PART BEGINS (Containment)
			count = printCountingMap.get("outgoing");
			if (count > 0) {
				Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__OUTGOING));
				java.util.List<?> list = (java.util.List<?>) o;
				int index = list.size() - count;
				if (index >= 0) {
					o = list.get(index);
				} else {
					o = null;
				}
				if (o != null) {
					doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
				}
				printCountingMap.put("outgoing", count - 1);
			}
		}
	}
	
	
	public void print_org_sintef_thingml_CompositeState(org.sintef.thingml.CompositeState element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(12);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__OUTGOING));
		printCountingMap.put("outgoing", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__INCOMING));
		printCountingMap.put("incoming", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__ENTRY));
		printCountingMap.put("entry", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__EXIT));
		printCountingMap.put("exit", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__PROPERTIES));
		printCountingMap.put("properties", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__INTERNAL));
		printCountingMap.put("internal", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__SUBSTATE));
		printCountingMap.put("substate", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__INITIAL));
		printCountingMap.put("initial", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__HISTORY));
		printCountingMap.put("history", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__REGION));
		printCountingMap.put("region", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("composite");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("state");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("init");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("initial");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__INITIAL));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getRegionInitialReferenceResolver().deResolve((org.sintef.thingml.State) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__INITIAL)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__INITIAL), element));
			}
			printCountingMap.put("initial", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_CompositeState_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_CompositeState_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("{");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_CompositeState_2(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_CompositeState_3(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_CompositeState_4(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_CompositeState_5(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_CompositeState_6(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("}");
	}
	
	public void print_org_sintef_thingml_CompositeState_0(org.sintef.thingml.CompositeState element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("keeps");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("history");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__HISTORY));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("T_HISTORY");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__HISTORY), element));
			}
			printCountingMap.put("history", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_CompositeState_1(org.sintef.thingml.CompositeState element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_CompositeState_2(org.sintef.thingml.CompositeState element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("properties");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__PROPERTIES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("properties", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_CompositeState_3(org.sintef.thingml.CompositeState element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("on");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("entry");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("entry");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__ENTRY));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("entry", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_CompositeState_4(org.sintef.thingml.CompositeState element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("on");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("exit");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("exit");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__EXIT));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("exit", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_CompositeState_5(org.sintef.thingml.CompositeState element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"outgoing"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"internal"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"substate"		));
		if (tempMatchCount > matches) {
			alt = 2;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("internal");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__INTERNAL));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("internal", count - 1);
				}
			}
			break;
			case 2:			{
				// DEFINITION PART BEGINS (CompoundDefinition)
				print_org_sintef_thingml_CompositeState_5_0(element, localtab, out, printCountingMap);
			}
			break;
			default:			// DEFINITION PART BEGINS (Containment)
			count = printCountingMap.get("outgoing");
			if (count > 0) {
				Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__OUTGOING));
				java.util.List<?> list = (java.util.List<?>) o;
				int index = list.size() - count;
				if (index >= 0) {
					o = list.get(index);
				} else {
					o = null;
				}
				if (o != null) {
					doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
				}
				printCountingMap.put("outgoing", count - 1);
			}
		}
	}
	
	public void print_org_sintef_thingml_CompositeState_5_0(org.sintef.thingml.CompositeState element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("substate");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__SUBSTATE));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("substate", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_CompositeState_6(org.sintef.thingml.CompositeState element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("region");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__REGION));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("region", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_ParallelRegion(org.sintef.thingml.ParallelRegion element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(5);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__SUBSTATE));
		printCountingMap.put("substate", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__INITIAL));
		printCountingMap.put("initial", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__HISTORY));
		printCountingMap.put("history", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("region");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("init");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("initial");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__INITIAL));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getRegionInitialReferenceResolver().deResolve((org.sintef.thingml.State) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__INITIAL)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__INITIAL), element));
			}
			printCountingMap.put("initial", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_ParallelRegion_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_ParallelRegion_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("{");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_ParallelRegion_2(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("}");
	}
	
	public void print_org_sintef_thingml_ParallelRegion_0(org.sintef.thingml.ParallelRegion element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("keeps");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("history");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__HISTORY));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("T_HISTORY");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__HISTORY), element));
			}
			printCountingMap.put("history", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_ParallelRegion_1(org.sintef.thingml.ParallelRegion element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_ParallelRegion_2(org.sintef.thingml.ParallelRegion element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("substate");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__SUBSTATE));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("substate", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_Transition(org.sintef.thingml.Transition element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(9);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__EVENT));
		printCountingMap.put("event", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__GUARD));
		printCountingMap.put("guard", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__ACTION));
		printCountingMap.put("action", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__TARGET));
		printCountingMap.put("target", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__SOURCE));
		printCountingMap.put("source", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__AFTER));
		printCountingMap.put("after", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__BEFORE));
		printCountingMap.put("before", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("transition");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Transition_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("->");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("target");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__TARGET));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getTransitionTargetReferenceResolver().deResolve((org.sintef.thingml.State) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__TARGET)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__TARGET), element));
			}
			printCountingMap.put("target", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Transition_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Transition_2(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Transition_3(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Transition_4(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Transition_5(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Transition_6(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
	}
	
	public void print_org_sintef_thingml_Transition_0(org.sintef.thingml.Transition element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Transition_1(org.sintef.thingml.Transition element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Transition_2(org.sintef.thingml.Transition element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("event");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("event");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__EVENT));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("event", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Transition_3(org.sintef.thingml.Transition element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("guard");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("guard");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__GUARD));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("guard", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Transition_4(org.sintef.thingml.Transition element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("action");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("action");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__ACTION));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("action", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Transition_5(org.sintef.thingml.Transition element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("before");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("before");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__BEFORE));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("before", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Transition_6(org.sintef.thingml.Transition element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("after");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("after");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__AFTER));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("after", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_InternalTransition(org.sintef.thingml.InternalTransition element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(5);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__EVENT));
		printCountingMap.put("event", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__GUARD));
		printCountingMap.put("guard", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__ACTION));
		printCountingMap.put("action", temp == null ? 0 : 1);
		// print collected hidden tokens
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("internal");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_InternalTransition_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_InternalTransition_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_InternalTransition_2(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_InternalTransition_3(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_InternalTransition_4(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
	}
	
	public void print_org_sintef_thingml_InternalTransition_0(org.sintef.thingml.InternalTransition element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_InternalTransition_1(org.sintef.thingml.InternalTransition element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_InternalTransition_2(org.sintef.thingml.InternalTransition element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("event");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("event");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__EVENT));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("event", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_InternalTransition_3(org.sintef.thingml.InternalTransition element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("guard");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("guard");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__GUARD));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("guard", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_InternalTransition_4(org.sintef.thingml.InternalTransition element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("action");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("action");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__ACTION));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("action", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_ReceiveMessage(org.sintef.thingml.ReceiveMessage element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(3);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__MESSAGE));
		printCountingMap.put("message", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__PORT));
		printCountingMap.put("port", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_ReceiveMessage_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("port");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__PORT));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getReceiveMessagePortReferenceResolver().deResolve((org.sintef.thingml.Port) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__PORT)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__PORT), element));
			}
			printCountingMap.put("port", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("?");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("message");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__MESSAGE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getReceiveMessageMessageReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__MESSAGE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__MESSAGE), element));
			}
			printCountingMap.put("message", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_ReceiveMessage_0(org.sintef.thingml.ReceiveMessage element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
	}
	
	
	public void print_org_sintef_thingml_PropertyAssign(org.sintef.thingml.PropertyAssign element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(5);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGN__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGN__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGN__INIT));
		printCountingMap.put("init", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGN__PROPERTY));
		printCountingMap.put("property", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGN__INDEX));
		printCountingMap.put("index", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("set");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("property");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGN__PROPERTY));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPropertyAssignPropertyReferenceResolver().deResolve((org.sintef.thingml.Property) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGN__PROPERTY)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGN__PROPERTY), element));
			}
			printCountingMap.put("property", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_PropertyAssign_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("=");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("init");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGN__INIT));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("init", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_PropertyAssign_0(org.sintef.thingml.PropertyAssign element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("[");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("index");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGN__INDEX));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("index", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("]");
	}
	
	
	public void print_org_sintef_thingml_Configuration(org.sintef.thingml.Configuration element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(7);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__INSTANCES));
		printCountingMap.put("instances", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__CONNECTORS));
		printCountingMap.put("connectors", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__FRAGMENT));
		printCountingMap.put("fragment", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__CONFIGS));
		printCountingMap.put("configs", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__PROPASSIGNS));
		printCountingMap.put("propassigns", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("configuration");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Configuration_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Configuration_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("{");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Configuration_2(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("}");
	}
	
	public void print_org_sintef_thingml_Configuration_0(org.sintef.thingml.Configuration element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("fragment");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__FRAGMENT));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("T_ASPECT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__FRAGMENT), element));
			}
			printCountingMap.put("fragment", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Configuration_1(org.sintef.thingml.Configuration element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Configuration_2(org.sintef.thingml.Configuration element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"instances"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"connectors"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"configs"		));
		if (tempMatchCount > matches) {
			alt = 2;
			matches = tempMatchCount;
		}
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"propassigns"		));
		if (tempMatchCount > matches) {
			alt = 3;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("connectors");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__CONNECTORS));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("connectors", count - 1);
				}
			}
			break;
			case 2:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("configs");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__CONFIGS));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("configs", count - 1);
				}
			}
			break;
			case 3:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("propassigns");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__PROPASSIGNS));
					java.util.List<?> list = (java.util.List<?>) o;
					int index = list.size() - count;
					if (index >= 0) {
						o = list.get(index);
					} else {
						o = null;
					}
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("propassigns", count - 1);
				}
			}
			break;
			default:			// DEFINITION PART BEGINS (Containment)
			count = printCountingMap.get("instances");
			if (count > 0) {
				Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__INSTANCES));
				java.util.List<?> list = (java.util.List<?>) o;
				int index = list.size() - count;
				if (index >= 0) {
					o = list.get(index);
				} else {
					o = null;
				}
				if (o != null) {
					doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
				}
				printCountingMap.put("instances", count - 1);
			}
		}
	}
	
	
	public void print_org_sintef_thingml_ConfigInclude(org.sintef.thingml.ConfigInclude element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(3);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_INCLUDE__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_INCLUDE__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_INCLUDE__CONFIG));
		printCountingMap.put("config", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("group");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_INCLUDE__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_INCLUDE__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("config");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_INCLUDE__CONFIG));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getConfigIncludeConfigReferenceResolver().deResolve((org.sintef.thingml.Configuration) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_INCLUDE__CONFIG)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_INCLUDE__CONFIG), element));
			}
			printCountingMap.put("config", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_ConfigInclude_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
	}
	
	public void print_org_sintef_thingml_ConfigInclude_0(org.sintef.thingml.ConfigInclude element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_INCLUDE__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_Instance(org.sintef.thingml.Instance element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(4);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__TYPE));
		printCountingMap.put("type", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__ASSIGN));
		printCountingMap.put("assign", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("instance");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Instance_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("type");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__TYPE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getInstanceTypeReferenceResolver().deResolve((org.sintef.thingml.Thing) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__TYPE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__TYPE), element));
			}
			printCountingMap.put("type", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Instance_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_Instance_0(org.sintef.thingml.Instance element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
	}
	
	public void print_org_sintef_thingml_Instance_1(org.sintef.thingml.Instance element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_Connector(org.sintef.thingml.Connector element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(6);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__SRV));
		printCountingMap.put("srv", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__CLI));
		printCountingMap.put("cli", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__REQUIRED));
		printCountingMap.put("required", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__PROVIDED));
		printCountingMap.put("provided", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("connector");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Connector_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("cli");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__CLI));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("cli", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(".");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("required");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__REQUIRED));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getConnectorRequiredReferenceResolver().deResolve((org.sintef.thingml.RequiredPort) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__REQUIRED)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__REQUIRED), element));
			}
			printCountingMap.put("required", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("=>");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("srv");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__SRV));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("srv", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(".");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("provided");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__PROVIDED));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getConnectorProvidedReferenceResolver().deResolve((org.sintef.thingml.ProvidedPort) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__PROVIDED)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__PROVIDED), element));
			}
			printCountingMap.put("provided", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Connector_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_Connector_0(org.sintef.thingml.Connector element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
	}
	
	public void print_org_sintef_thingml_Connector_1(org.sintef.thingml.Connector element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_ConfigPropertyAssign(org.sintef.thingml.ConfigPropertyAssign element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(6);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_PROPERTY_ASSIGN__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_PROPERTY_ASSIGN__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_PROPERTY_ASSIGN__INIT));
		printCountingMap.put("init", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_PROPERTY_ASSIGN__PROPERTY));
		printCountingMap.put("property", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_PROPERTY_ASSIGN__INSTANCE));
		printCountingMap.put("instance", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_PROPERTY_ASSIGN__INDEX));
		printCountingMap.put("index", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("set");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("instance");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_PROPERTY_ASSIGN__INSTANCE));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("instance", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(".");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("property");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_PROPERTY_ASSIGN__PROPERTY));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getConfigPropertyAssignPropertyReferenceResolver().deResolve((org.sintef.thingml.Property) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_PROPERTY_ASSIGN__PROPERTY)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_PROPERTY_ASSIGN__PROPERTY), element));
			}
			printCountingMap.put("property", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_ConfigPropertyAssign_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("=");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("init");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_PROPERTY_ASSIGN__INIT));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("init", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_ConfigPropertyAssign_0(org.sintef.thingml.ConfigPropertyAssign element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("[");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("index");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIG_PROPERTY_ASSIGN__INDEX));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("index", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("]");
	}
	
	
	public void print_org_sintef_thingml_InstanceRef(org.sintef.thingml.InstanceRef element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE_REF__CONFIG));
		printCountingMap.put("config", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE_REF__INSTANCE));
		printCountingMap.put("instance", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_InstanceRef_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("instance");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE_REF__INSTANCE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getInstanceRefInstanceReferenceResolver().deResolve((org.sintef.thingml.Instance) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE_REF__INSTANCE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE_REF__INSTANCE), element));
			}
			printCountingMap.put("instance", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_InstanceRef_0(org.sintef.thingml.InstanceRef element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("config");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE_REF__CONFIG));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getInstanceRefConfigReferenceResolver().deResolve((org.sintef.thingml.ConfigInclude) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE_REF__CONFIG)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE_REF__CONFIG), element));
			}
			printCountingMap.put("config", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(".");
	}
	
	
	public void print_org_sintef_thingml_SendAction(org.sintef.thingml.SendAction element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(3);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__PARAMETERS));
		printCountingMap.put("parameters", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__MESSAGE));
		printCountingMap.put("message", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__PORT));
		printCountingMap.put("port", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("port");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__PORT));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getSendActionPortReferenceResolver().deResolve((org.sintef.thingml.Port) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__PORT)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__PORT), element));
			}
			printCountingMap.put("port", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("!");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("message");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__MESSAGE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getSendActionMessageReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__MESSAGE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__MESSAGE), element));
			}
			printCountingMap.put("message", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_SendAction_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
	}
	
	public void print_org_sintef_thingml_SendAction_0(org.sintef.thingml.SendAction element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("parameters");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__PARAMETERS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("parameters", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_SendAction_0_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_SendAction_0_0(org.sintef.thingml.SendAction element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("parameters");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__PARAMETERS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("parameters", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_VariableAssignment(org.sintef.thingml.VariableAssignment element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(3);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.VARIABLE_ASSIGNMENT__PROPERTY));
		printCountingMap.put("property", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.VARIABLE_ASSIGNMENT__EXPRESSION));
		printCountingMap.put("expression", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.VARIABLE_ASSIGNMENT__INDEX));
		printCountingMap.put("index", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("property");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.VARIABLE_ASSIGNMENT__PROPERTY));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getVariableAssignmentPropertyReferenceResolver().deResolve((org.sintef.thingml.Variable) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.VARIABLE_ASSIGNMENT__PROPERTY)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.VARIABLE_ASSIGNMENT__PROPERTY), element));
			}
			printCountingMap.put("property", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_VariableAssignment_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("=");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("expression");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.VARIABLE_ASSIGNMENT__EXPRESSION));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("expression", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_VariableAssignment_0(org.sintef.thingml.VariableAssignment element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("[");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("index");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.VARIABLE_ASSIGNMENT__INDEX));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("index", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("]");
	}
	
	
	public void print_org_sintef_thingml_ActionBlock(org.sintef.thingml.ActionBlock element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ACTION_BLOCK__ACTIONS));
		printCountingMap.put("actions", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("do");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_ActionBlock_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("end");
	}
	
	public void print_org_sintef_thingml_ActionBlock_0(org.sintef.thingml.ActionBlock element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("actions");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ACTION_BLOCK__ACTIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("actions", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_LocalVariable(org.sintef.thingml.LocalVariable element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(7);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__TYPE));
		printCountingMap.put("type", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__CARDINALITY));
		printCountingMap.put("cardinality", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__IS_ARRAY));
		printCountingMap.put("isArray", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__INIT));
		printCountingMap.put("init", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__CHANGEABLE));
		printCountingMap.put("changeable", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_LocalVariable_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("var");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("type");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__TYPE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getTypedElementTypeReferenceResolver().deResolve((org.sintef.thingml.Type) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__TYPE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__TYPE), element));
			}
			printCountingMap.put("type", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_LocalVariable_1(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_LocalVariable_2(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_LocalVariable_3(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_LocalVariable_0(org.sintef.thingml.LocalVariable element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("changeable");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__CHANGEABLE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("T_READONLY");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__CHANGEABLE), element));
			}
			printCountingMap.put("changeable", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_LocalVariable_1(org.sintef.thingml.LocalVariable element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"cardinality"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"isArray"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				// DEFINITION PART BEGINS (CompoundDefinition)
				print_org_sintef_thingml_LocalVariable_1_1(element, localtab, out, printCountingMap);
			}
			break;
			default:			// DEFINITION PART BEGINS (CompoundDefinition)
			print_org_sintef_thingml_LocalVariable_1_0(element, localtab, out, printCountingMap);
		}
	}
	
	public void print_org_sintef_thingml_LocalVariable_1_0(org.sintef.thingml.LocalVariable element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("[");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("cardinality");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__CARDINALITY));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("cardinality", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("]");
	}
	
	public void print_org_sintef_thingml_LocalVariable_1_1(org.sintef.thingml.LocalVariable element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("isArray");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__IS_ARRAY));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("T_ARRAY");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__IS_ARRAY), element));
			}
			printCountingMap.put("isArray", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_LocalVariable_2(org.sintef.thingml.LocalVariable element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("=");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("init");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__INIT));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("init", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_LocalVariable_3(org.sintef.thingml.LocalVariable element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOCAL_VARIABLE__ANNOTATIONS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("annotations", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_ExternStatement(org.sintef.thingml.ExternStatement element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXTERN_STATEMENT__STATEMENT));
		printCountingMap.put("statement", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXTERN_STATEMENT__SEGMENTS));
		printCountingMap.put("segments", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("statement");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXTERN_STATEMENT__STATEMENT));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("STRING_EXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXTERN_STATEMENT__STATEMENT), element));
			}
			printCountingMap.put("statement", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_ExternStatement_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_ExternStatement_0(org.sintef.thingml.ExternStatement element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("&");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("segments");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXTERN_STATEMENT__SEGMENTS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("segments", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_ConditionalAction(org.sintef.thingml.ConditionalAction element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(3);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONDITIONAL_ACTION__ACTION));
		printCountingMap.put("action", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONDITIONAL_ACTION__CONDITION));
		printCountingMap.put("condition", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONDITIONAL_ACTION__ELSE_ACTION));
		printCountingMap.put("elseAction", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("if");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("condition");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONDITIONAL_ACTION__CONDITION));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("condition", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("action");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONDITIONAL_ACTION__ACTION));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("action", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_ConditionalAction_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
	}
	
	public void print_org_sintef_thingml_ConditionalAction_0(org.sintef.thingml.ConditionalAction element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("else");
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("elseAction");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONDITIONAL_ACTION__ELSE_ACTION));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("elseAction", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_LoopAction(org.sintef.thingml.LoopAction element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOOP_ACTION__ACTION));
		printCountingMap.put("action", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOOP_ACTION__CONDITION));
		printCountingMap.put("condition", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("while");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("condition");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOOP_ACTION__CONDITION));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("condition", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("action");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOOP_ACTION__ACTION));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("action", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_PrintAction(org.sintef.thingml.PrintAction element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PRINT_ACTION__MSG));
		printCountingMap.put("msg", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("print");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("msg");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PRINT_ACTION__MSG));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("msg", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_ErrorAction(org.sintef.thingml.ErrorAction element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ERROR_ACTION__MSG));
		printCountingMap.put("msg", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("error");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("msg");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ERROR_ACTION__MSG));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("msg", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_ReturnAction(org.sintef.thingml.ReturnAction element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RETURN_ACTION__EXP));
		printCountingMap.put("exp", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("return");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("exp");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RETURN_ACTION__EXP));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("exp", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_FunctionCallStatement(org.sintef.thingml.FunctionCallStatement element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION_CALL_STATEMENT__PARAMETERS));
		printCountingMap.put("parameters", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION_CALL_STATEMENT__FUNCTION));
		printCountingMap.put("function", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("function");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION_CALL_STATEMENT__FUNCTION));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getFunctionCallFunctionReferenceResolver().deResolve((org.sintef.thingml.Function) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION_CALL_STATEMENT__FUNCTION)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION_CALL_STATEMENT__FUNCTION), element));
			}
			printCountingMap.put("function", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_FunctionCallStatement_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
	}
	
	public void print_org_sintef_thingml_FunctionCallStatement_0(org.sintef.thingml.FunctionCallStatement element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("parameters");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION_CALL_STATEMENT__PARAMETERS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("parameters", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_FunctionCallStatement_0_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_FunctionCallStatement_0_0(org.sintef.thingml.FunctionCallStatement element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("parameters");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION_CALL_STATEMENT__PARAMETERS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("parameters", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_SglMsgParamOperator(org.sintef.thingml.SglMsgParamOperator element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(6);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__TYPE));
		printCountingMap.put("type", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__CARDINALITY));
		printCountingMap.put("cardinality", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__IS_ARRAY));
		printCountingMap.put("isArray", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__BODY));
		printCountingMap.put("body", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__PARAMETER));
		printCountingMap.put("parameter", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("operator");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("parameter");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__PARAMETER));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("parameter", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("type");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__TYPE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getTypedElementTypeReferenceResolver().deResolve((org.sintef.thingml.Type) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__TYPE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__TYPE), element));
			}
			printCountingMap.put("type", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_SglMsgParamOperator_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("body");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__BODY));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("body", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_SglMsgParamOperator_0(org.sintef.thingml.SglMsgParamOperator element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"cardinality"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"isArray"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				// DEFINITION PART BEGINS (CompoundDefinition)
				print_org_sintef_thingml_SglMsgParamOperator_0_1(element, localtab, out, printCountingMap);
			}
			break;
			default:			// DEFINITION PART BEGINS (CompoundDefinition)
			print_org_sintef_thingml_SglMsgParamOperator_0_0(element, localtab, out, printCountingMap);
		}
	}
	
	public void print_org_sintef_thingml_SglMsgParamOperator_0_0(org.sintef.thingml.SglMsgParamOperator element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("[");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("cardinality");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__CARDINALITY));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("cardinality", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("]");
	}
	
	public void print_org_sintef_thingml_SglMsgParamOperator_0_1(org.sintef.thingml.SglMsgParamOperator element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("isArray");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__IS_ARRAY));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("T_ARRAY");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR__IS_ARRAY), element));
			}
			printCountingMap.put("isArray", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_MessageParameter(org.sintef.thingml.MessageParameter element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE_PARAMETER__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE_PARAMETER__MSG_REF));
		printCountingMap.put("msgRef", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE_PARAMETER__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE_PARAMETER__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("msgRef");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE_PARAMETER__MSG_REF));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getMessageParameterMsgRefReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE_PARAMETER__MSG_REF)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE_PARAMETER__MSG_REF), element));
			}
			printCountingMap.put("msgRef", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_SglMsgParamOperatorCall(org.sintef.thingml.SglMsgParamOperatorCall element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__OPERATOR_REF));
		printCountingMap.put("operatorRef", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__PARAMETER));
		printCountingMap.put("parameter", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("operatorRef");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__OPERATOR_REF));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getSglMsgParamOperatorCallOperatorRefReferenceResolver().deResolve((org.sintef.thingml.SglMsgParamOperator) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__OPERATOR_REF)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__OPERATOR_REF), element));
			}
			printCountingMap.put("operatorRef", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("parameter");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__PARAMETER));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getSglMsgParamOperatorCallParameterReferenceResolver().deResolve((org.sintef.thingml.Source) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__PARAMETER)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__PARAMETER), element));
			}
			printCountingMap.put("parameter", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
	}
	
	
	public void print_org_sintef_thingml_StreamExpression(org.sintef.thingml.StreamExpression element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_EXPRESSION__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_EXPRESSION__EXPRESSION));
		printCountingMap.put("expression", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_EXPRESSION__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_EXPRESSION__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("expression");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_EXPRESSION__EXPRESSION));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("expression", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_StreamOutput(org.sintef.thingml.StreamOutput element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(3);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__PARAMETERS));
		printCountingMap.put("parameters", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__MESSAGE));
		printCountingMap.put("message", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__PORT));
		printCountingMap.put("port", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("port");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__PORT));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getStreamOutputPortReferenceResolver().deResolve((org.sintef.thingml.Port) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__PORT)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__PORT), element));
			}
			printCountingMap.put("port", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("!");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("message");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__MESSAGE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getStreamOutputMessageReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__MESSAGE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__MESSAGE), element));
			}
			printCountingMap.put("message", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_StreamOutput_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
	}
	
	public void print_org_sintef_thingml_StreamOutput_0(org.sintef.thingml.StreamOutput element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("parameters");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__PARAMETERS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getStreamOutputParametersReferenceResolver().deResolve((org.sintef.thingml.StreamExpression) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__PARAMETERS)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__PARAMETERS), element));
			}
			printCountingMap.put("parameters", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_StreamOutput_0_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_StreamOutput_0_0(org.sintef.thingml.StreamOutput element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("parameters");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__PARAMETERS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getStreamOutputParametersReferenceResolver().deResolve((org.sintef.thingml.StreamExpression) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__PARAMETERS)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_OUTPUT__PARAMETERS), element));
			}
			printCountingMap.put("parameters", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_Filter(org.sintef.thingml.Filter element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FILTER__FILTER_OP));
		printCountingMap.put("filterOp", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("filter");
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("filterOp");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FILTER__FILTER_OP));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("filterOp", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
	}
	
	
	public void print_org_sintef_thingml_LengthWindow(org.sintef.thingml.LengthWindow element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LENGTH_WINDOW__NB_EVENTS));
		printCountingMap.put("nbEvents", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("lengthWindow");
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("nbEvents");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LENGTH_WINDOW__NB_EVENTS));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LENGTH_WINDOW__NB_EVENTS), element));
			}
			printCountingMap.put("nbEvents", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
	}
	
	
	public void print_org_sintef_thingml_TimeWindow(org.sintef.thingml.TimeWindow element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TIME_WINDOW__STEP));
		printCountingMap.put("step", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TIME_WINDOW__SIZE));
		printCountingMap.put("size", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("timeWindow");
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("step");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TIME_WINDOW__STEP));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TIME_WINDOW__STEP), element));
			}
			printCountingMap.put("step", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("size");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TIME_WINDOW__SIZE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TIME_WINDOW__SIZE), element));
			}
			printCountingMap.put("size", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
	}
	
	
	public void print_org_sintef_thingml_SimpleSource(org.sintef.thingml.SimpleSource element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(3);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SIMPLE_SOURCE__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SIMPLE_SOURCE__OPERATORS));
		printCountingMap.put("operators", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SIMPLE_SOURCE__MESSAGE));
		printCountingMap.put("message", temp == null ? 0 : 1);
		// print collected hidden tokens
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CompoundDefinition)
		print_org_sintef_thingml_SimpleSource_0(element, localtab, out, printCountingMap);
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_SimpleSource_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_SimpleSource_0(org.sintef.thingml.SimpleSource element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"name"		,
		"message"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"message"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				// DEFINITION PART BEGINS (Containment)
				count = printCountingMap.get("message");
				if (count > 0) {
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SIMPLE_SOURCE__MESSAGE));
					if (o != null) {
						doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
					}
					printCountingMap.put("message", count - 1);
				}
			}
			break;
			default:			// DEFINITION PART BEGINS (CompoundDefinition)
			print_org_sintef_thingml_SimpleSource_0_0(element, localtab, out, printCountingMap);
		}
	}
	
	public void print_org_sintef_thingml_SimpleSource_0_0(org.sintef.thingml.SimpleSource element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SIMPLE_SOURCE__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SIMPLE_SOURCE__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (CsString)
		out.print("[");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("message");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SIMPLE_SOURCE__MESSAGE));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("message", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("]");
	}
	
	public void print_org_sintef_thingml_SimpleSource_1(org.sintef.thingml.SimpleSource element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("::");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("operators");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SIMPLE_SOURCE__OPERATORS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("operators", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_JoinSources(org.sintef.thingml.JoinSources element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(5);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__OPERATORS));
		printCountingMap.put("operators", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__SOURCES));
		printCountingMap.put("sources", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__RESULT_MESSAGE));
		printCountingMap.put("resultMessage", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__RULES));
		printCountingMap.put("rules", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_JoinSources_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("[");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("sources");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__SOURCES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("sources", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("&");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("sources");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__SOURCES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("sources", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("->");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("resultMessage");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__RESULT_MESSAGE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getSourceCompositionResultMessageReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__RESULT_MESSAGE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__RESULT_MESSAGE), element));
			}
			printCountingMap.put("resultMessage", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_JoinSources_1(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
		// DEFINITION PART BEGINS (CsString)
		out.print("]");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_JoinSources_2(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_JoinSources_0(org.sintef.thingml.JoinSources element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
	}
	
	public void print_org_sintef_thingml_JoinSources_1(org.sintef.thingml.JoinSources element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("rules");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__RULES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("rules", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_JoinSources_1_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_JoinSources_1_0(org.sintef.thingml.JoinSources element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("rules");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__RULES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("rules", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_JoinSources_2(org.sintef.thingml.JoinSources element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("::");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("operators");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.JOIN_SOURCES__OPERATORS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("operators", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_MergeSources(org.sintef.thingml.MergeSources element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(5);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__OPERATORS));
		printCountingMap.put("operators", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__SOURCES));
		printCountingMap.put("sources", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__RESULT_MESSAGE));
		printCountingMap.put("resultMessage", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__RULES));
		printCountingMap.put("rules", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_MergeSources_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("[");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("sources");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__SOURCES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("sources", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CompoundDefinition)
		print_org_sintef_thingml_MergeSources_1(element, localtab, out, printCountingMap);
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_MergeSources_1(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("->");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("resultMessage");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__RESULT_MESSAGE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getSourceCompositionResultMessageReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__RESULT_MESSAGE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__RESULT_MESSAGE), element));
			}
			printCountingMap.put("resultMessage", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_MergeSources_2(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
		// DEFINITION PART BEGINS (CsString)
		out.print("]");
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_MergeSources_3(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_MergeSources_0(org.sintef.thingml.MergeSources element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
	}
	
	public void print_org_sintef_thingml_MergeSources_1(org.sintef.thingml.MergeSources element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("|");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("sources");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__SOURCES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("sources", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
	}
	
	public void print_org_sintef_thingml_MergeSources_2(org.sintef.thingml.MergeSources element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("rules");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__RULES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("rules", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_MergeSources_2_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_MergeSources_2_0(org.sintef.thingml.MergeSources element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("rules");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__RULES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("rules", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_MergeSources_3(org.sintef.thingml.MergeSources element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("::");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("operators");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MERGE_SOURCES__OPERATORS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("operators", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_Stream(org.sintef.thingml.Stream element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(4);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM__SELECTION));
		printCountingMap.put("selection", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM__OUTPUT));
		printCountingMap.put("output", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM__INPUT));
		printCountingMap.put("input", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("stream");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("do");
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("from");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("input");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM__INPUT));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("input", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Stream_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("action");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("output");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM__OUTPUT));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("output", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("end");
	}
	
	public void print_org_sintef_thingml_Stream_0(org.sintef.thingml.Stream element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("select");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Stream_0_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
	}
	
	public void print_org_sintef_thingml_Stream_0_0(org.sintef.thingml.Stream element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("selection");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM__SELECTION));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("selection", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Stream_0_0_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_Stream_0_0_0(org.sintef.thingml.Stream element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("selection");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM__SELECTION));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("selection", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_SimpleParamRef(org.sintef.thingml.SimpleParamRef element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SIMPLE_PARAM_REF__PARAMETER_REF));
		printCountingMap.put("parameterRef", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("parameterRef");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SIMPLE_PARAM_REF__PARAMETER_REF));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getParamReferenceParameterRefReferenceResolver().deResolve((org.sintef.thingml.Parameter) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SIMPLE_PARAM_REF__PARAMETER_REF)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SIMPLE_PARAM_REF__PARAMETER_REF), element));
			}
			printCountingMap.put("parameterRef", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_ArrayParamRef(org.sintef.thingml.ArrayParamRef element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ARRAY_PARAM_REF__PARAMETER_REF));
		printCountingMap.put("parameterRef", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("parameterRef");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ARRAY_PARAM_REF__PARAMETER_REF));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getParamReferenceParameterRefReferenceResolver().deResolve((org.sintef.thingml.Parameter) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ARRAY_PARAM_REF__PARAMETER_REF)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ARRAY_PARAM_REF__PARAMETER_REF), element));
			}
			printCountingMap.put("parameterRef", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("[]");
	}
	
	
	public void print_org_sintef_thingml_LengthArray(org.sintef.thingml.LengthArray element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		// print collected hidden tokens
		// DEFINITION PART BEGINS (CsString)
		out.print("length");
	}
	
	
	public void print_org_sintef_thingml_OrExpression(org.sintef.thingml.OrExpression element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.OR_EXPRESSION__LHS));
		printCountingMap.put("lhs", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.OR_EXPRESSION__RHS));
		printCountingMap.put("rhs", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("lhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.OR_EXPRESSION__LHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("lhs", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("or");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("rhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.OR_EXPRESSION__RHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("rhs", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_AndExpression(org.sintef.thingml.AndExpression element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.AND_EXPRESSION__LHS));
		printCountingMap.put("lhs", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.AND_EXPRESSION__RHS));
		printCountingMap.put("rhs", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("lhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.AND_EXPRESSION__LHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("lhs", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("and");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("rhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.AND_EXPRESSION__RHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("rhs", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_LowerExpression(org.sintef.thingml.LowerExpression element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOWER_EXPRESSION__LHS));
		printCountingMap.put("lhs", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOWER_EXPRESSION__RHS));
		printCountingMap.put("rhs", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("lhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOWER_EXPRESSION__LHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("lhs", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("<");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("rhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOWER_EXPRESSION__RHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("rhs", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_GreaterExpression(org.sintef.thingml.GreaterExpression element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.GREATER_EXPRESSION__LHS));
		printCountingMap.put("lhs", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.GREATER_EXPRESSION__RHS));
		printCountingMap.put("rhs", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("lhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.GREATER_EXPRESSION__LHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("lhs", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(">");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("rhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.GREATER_EXPRESSION__RHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("rhs", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_EqualsExpression(org.sintef.thingml.EqualsExpression element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EQUALS_EXPRESSION__LHS));
		printCountingMap.put("lhs", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EQUALS_EXPRESSION__RHS));
		printCountingMap.put("rhs", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("lhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EQUALS_EXPRESSION__LHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("lhs", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("==");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("rhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EQUALS_EXPRESSION__RHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("rhs", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_PlusExpression(org.sintef.thingml.PlusExpression element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLUS_EXPRESSION__LHS));
		printCountingMap.put("lhs", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLUS_EXPRESSION__RHS));
		printCountingMap.put("rhs", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("lhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLUS_EXPRESSION__LHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("lhs", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("+");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("rhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLUS_EXPRESSION__RHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("rhs", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_MinusExpression(org.sintef.thingml.MinusExpression element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MINUS_EXPRESSION__LHS));
		printCountingMap.put("lhs", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MINUS_EXPRESSION__RHS));
		printCountingMap.put("rhs", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("lhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MINUS_EXPRESSION__LHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("lhs", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("-");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("rhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MINUS_EXPRESSION__RHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("rhs", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_TimesExpression(org.sintef.thingml.TimesExpression element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TIMES_EXPRESSION__LHS));
		printCountingMap.put("lhs", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TIMES_EXPRESSION__RHS));
		printCountingMap.put("rhs", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("lhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TIMES_EXPRESSION__LHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("lhs", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("*");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("rhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TIMES_EXPRESSION__RHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("rhs", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_DivExpression(org.sintef.thingml.DivExpression element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DIV_EXPRESSION__LHS));
		printCountingMap.put("lhs", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DIV_EXPRESSION__RHS));
		printCountingMap.put("rhs", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("lhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DIV_EXPRESSION__LHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("lhs", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("/");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("rhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DIV_EXPRESSION__RHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("rhs", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_ModExpression(org.sintef.thingml.ModExpression element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MOD_EXPRESSION__LHS));
		printCountingMap.put("lhs", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MOD_EXPRESSION__RHS));
		printCountingMap.put("rhs", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("lhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MOD_EXPRESSION__LHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("lhs", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("%");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("rhs");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MOD_EXPRESSION__RHS));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("rhs", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_UnaryMinus(org.sintef.thingml.UnaryMinus element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.UNARY_MINUS__TERM));
		printCountingMap.put("term", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("-");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("term");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.UNARY_MINUS__TERM));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("term", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_NotExpression(org.sintef.thingml.NotExpression element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.NOT_EXPRESSION__TERM));
		printCountingMap.put("term", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("not");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("term");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.NOT_EXPRESSION__TERM));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("term", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_StreamParamReference(org.sintef.thingml.StreamParamReference element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_PARAM_REFERENCE__INDEX_PARAM));
		printCountingMap.put("indexParam", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("#");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("indexParam");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_PARAM_REFERENCE__INDEX_PARAM));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STREAM_PARAM_REFERENCE__INDEX_PARAM), element));
			}
			printCountingMap.put("indexParam", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_Reference(org.sintef.thingml.Reference element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REFERENCE__REFERENCE));
		printCountingMap.put("reference", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REFERENCE__PARAMETER));
		printCountingMap.put("parameter", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("reference");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REFERENCE__REFERENCE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getReferenceReferenceReferenceResolver().deResolve((org.sintef.thingml.ReferencedElmt) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REFERENCE__REFERENCE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REFERENCE__REFERENCE), element));
			}
			printCountingMap.put("reference", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(".");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("parameter");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REFERENCE__PARAMETER));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("parameter", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_ExpressionGroup(org.sintef.thingml.ExpressionGroup element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXPRESSION_GROUP__EXP));
		printCountingMap.put("exp", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("exp");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXPRESSION_GROUP__EXP));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("exp", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
	}
	
	
	public void print_org_sintef_thingml_PropertyReference(org.sintef.thingml.PropertyReference element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_REFERENCE__PROPERTY));
		printCountingMap.put("property", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("property");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_REFERENCE__PROPERTY));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPropertyReferencePropertyReferenceResolver().deResolve((org.sintef.thingml.Variable) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_REFERENCE__PROPERTY)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_REFERENCE__PROPERTY), element));
			}
			printCountingMap.put("property", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_IntegerLiteral(org.sintef.thingml.IntegerLiteral element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTEGER_LITERAL__INT_VALUE));
		printCountingMap.put("intValue", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("intValue");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTEGER_LITERAL__INT_VALUE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTEGER_LITERAL__INT_VALUE), element));
			}
			printCountingMap.put("intValue", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_StringLiteral(org.sintef.thingml.StringLiteral element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STRING_LITERAL__STRING_VALUE));
		printCountingMap.put("stringValue", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("stringValue");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STRING_LITERAL__STRING_VALUE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("STRING_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STRING_LITERAL__STRING_VALUE), element));
			}
			printCountingMap.put("stringValue", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_BooleanLiteral(org.sintef.thingml.BooleanLiteral element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.BOOLEAN_LITERAL__BOOL_VALUE));
		printCountingMap.put("boolValue", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("boolValue");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.BOOLEAN_LITERAL__BOOL_VALUE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("BOOLEAN_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.BOOLEAN_LITERAL__BOOL_VALUE), element));
			}
			printCountingMap.put("boolValue", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_EnumLiteralRef(org.sintef.thingml.EnumLiteralRef element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUM_LITERAL_REF__ENUM));
		printCountingMap.put("enum", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUM_LITERAL_REF__LITERAL));
		printCountingMap.put("literal", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("enum");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUM_LITERAL_REF__ENUM));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getEnumLiteralRefEnumReferenceResolver().deResolve((org.sintef.thingml.Enumeration) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUM_LITERAL_REF__ENUM)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUM_LITERAL_REF__ENUM), element));
			}
			printCountingMap.put("enum", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("literal");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUM_LITERAL_REF__LITERAL));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getEnumLiteralRefLiteralReferenceResolver().deResolve((org.sintef.thingml.EnumerationLiteral) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUM_LITERAL_REF__LITERAL)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUM_LITERAL_REF__LITERAL), element));
			}
			printCountingMap.put("literal", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_ArrayIndex(org.sintef.thingml.ArrayIndex element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ARRAY_INDEX__ARRAY));
		printCountingMap.put("array", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ARRAY_INDEX__INDEX));
		printCountingMap.put("index", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("array");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ARRAY_INDEX__ARRAY));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("array", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("[");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("index");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ARRAY_INDEX__INDEX));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("index", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("]");
	}
	
	
	public void print_org_sintef_thingml_FunctionCallExpression(org.sintef.thingml.FunctionCallExpression element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION_CALL_EXPRESSION__PARAMETERS));
		printCountingMap.put("parameters", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION_CALL_EXPRESSION__FUNCTION));
		printCountingMap.put("function", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("function");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION_CALL_EXPRESSION__FUNCTION));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getFunctionCallFunctionReferenceResolver().deResolve((org.sintef.thingml.Function) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION_CALL_EXPRESSION__FUNCTION)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION_CALL_EXPRESSION__FUNCTION), element));
			}
			printCountingMap.put("function", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("(");
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_FunctionCallExpression_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(")");
	}
	
	public void print_org_sintef_thingml_FunctionCallExpression_0(org.sintef.thingml.FunctionCallExpression element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("parameters");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION_CALL_EXPRESSION__PARAMETERS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("parameters", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_FunctionCallExpression_0_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_FunctionCallExpression_0_0(org.sintef.thingml.FunctionCallExpression element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("parameters");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.FUNCTION_CALL_EXPRESSION__PARAMETERS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("parameters", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_ExternExpression(org.sintef.thingml.ExternExpression element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXTERN_EXPRESSION__EXPRESSION));
		printCountingMap.put("expression", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXTERN_EXPRESSION__SEGMENTS));
		printCountingMap.put("segments", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("expression");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXTERN_EXPRESSION__EXPRESSION));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("STRING_EXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXTERN_EXPRESSION__EXPRESSION), element));
			}
			printCountingMap.put("expression", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_ExternExpression_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_sintef_thingml_ExternExpression_0(org.sintef.thingml.ExternExpression element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("&");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("segments");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXTERN_EXPRESSION__SEGMENTS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("segments", count - 1);
		}
	}
	
	
}
