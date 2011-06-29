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

public class ThingmlPrinter implements org.sintef.thingml.resource.thingml.IThingmlTextPrinter {
	
	protected org.sintef.thingml.resource.thingml.IThingmlTokenResolverFactory tokenResolverFactory = new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenResolverFactory();
	
	protected java.io.OutputStream outputStream;
	
	/**
	 * Holds the resource that is associated with this printer. This may be null if
	 * the printer is used stand alone.
	 */
	private org.sintef.thingml.resource.thingml.IThingmlTextResource resource;
	
	private java.util.Map<?, ?> options;
	
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
		if (element instanceof org.sintef.thingml.Thing) {
			print_org_sintef_thingml_Thing((org.sintef.thingml.Thing) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Port) {
			print_org_sintef_thingml_Port((org.sintef.thingml.Port) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Dictionary) {
			print_org_sintef_thingml_Dictionary((org.sintef.thingml.Dictionary) element, globaltab, out);
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
		if (element instanceof org.sintef.thingml.SendAction) {
			print_org_sintef_thingml_SendAction((org.sintef.thingml.SendAction) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.PropertyAssignment) {
			print_org_sintef_thingml_PropertyAssignment((org.sintef.thingml.PropertyAssignment) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ActionBlock) {
			print_org_sintef_thingml_ActionBlock((org.sintef.thingml.ActionBlock) element, globaltab, out);
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
		if (element instanceof org.sintef.thingml.EventReference) {
			print_org_sintef_thingml_EventReference((org.sintef.thingml.EventReference) element, globaltab, out);
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
		if (element instanceof org.sintef.thingml.IntegerLitteral) {
			print_org_sintef_thingml_IntegerLitteral((org.sintef.thingml.IntegerLitteral) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.StringLitteral) {
			print_org_sintef_thingml_StringLitteral((org.sintef.thingml.StringLitteral) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.BooleanLitteral) {
			print_org_sintef_thingml_BooleanLitteral((org.sintef.thingml.BooleanLitteral) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.ExternExpression) {
			print_org_sintef_thingml_ExternExpression((org.sintef.thingml.ExternExpression) element, globaltab, out);
			return;
		}
		if (element instanceof org.sintef.thingml.Property) {
			print_org_sintef_thingml_Property((org.sintef.thingml.Property) element, globaltab, out);
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
	
	public org.sintef.thingml.resource.thingml.IThingmlTextResource getResource() {
		return resource;
	}
	
	/**
	 * Calls {@link #doPrint(EObject, PrintWriter, String)} and writes the result to
	 * the underlying output stream.
	 */
	public void print(org.eclipse.emf.ecore.EObject element) {
		java.io.PrintWriter out = new java.io.PrintWriter(new java.io.BufferedOutputStream(outputStream));
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
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__MESSAGES));
		printCountingMap.put("messages", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
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
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"messages"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"types"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				// DEFINITION PART BEGINS (Containment)
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
			break;
			default:			// DEFINITION PART BEGINS (Containment)
			count = printCountingMap.get("messages");
			if (count > 0) {
				Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__MESSAGES));
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
	
	
	public void print_org_sintef_thingml_Thing(org.sintef.thingml.Thing element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(7);
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
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"properties"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"ports"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"behaviour"		));
		if (tempMatchCount > matches) {
			alt = 2;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
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
			case 2:			{
				// DEFINITION PART BEGINS (CompoundDefinition)
				print_org_sintef_thingml_Thing_3_0(element, localtab, out, printCountingMap);
			}
			break;
			default:			// DEFINITION PART BEGINS (Containment)
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
	}
	
	public void print_org_sintef_thingml_Thing_3_0(org.sintef.thingml.Thing element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
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
	
	
	public void print_org_sintef_thingml_Port(org.sintef.thingml.Port element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(5);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__OWNER));
		printCountingMap.put("owner", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__RECEIVES));
		printCountingMap.put("receives", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__SENDS));
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
		out.print("port");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Port_0(element, localtab, out1, printCountingMap1);
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
			print_org_sintef_thingml_Port_1(element, localtab, out1, printCountingMap1);
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
	
	public void print_org_sintef_thingml_Port_0(org.sintef.thingml.Port element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__ANNOTATIONS));
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
	
	public void print_org_sintef_thingml_Port_1(org.sintef.thingml.Port element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
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
					Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__SENDS));
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
						out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortSendsReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__SENDS)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__SENDS), element));
					}
					printCountingMap.put("sends", count - 1);
				}
				// DEFINITION PART BEGINS (CompoundDefinition)
				iterate = true;
				while (iterate) {
					sWriter = new java.io.StringWriter();
					out1 = new java.io.PrintWriter(sWriter);
					printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
					print_org_sintef_thingml_Port_1_1(element, localtab, out1, printCountingMap1);
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
				Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__RECEIVES));
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
					out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortReceivesReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__RECEIVES)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__RECEIVES), element));
				}
				printCountingMap.put("receives", count - 1);
			}
			// DEFINITION PART BEGINS (CompoundDefinition)
			iterate = true;
			while (iterate) {
				sWriter = new java.io.StringWriter();
				out1 = new java.io.PrintWriter(sWriter);
				printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
				print_org_sintef_thingml_Port_1_0(element, localtab, out1, printCountingMap1);
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
	
	public void print_org_sintef_thingml_Port_1_0(org.sintef.thingml.Port element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("receives");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__RECEIVES));
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
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortReceivesReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__RECEIVES)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__RECEIVES), element));
			}
			printCountingMap.put("receives", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Port_1_1(org.sintef.thingml.Port element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print(",");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("sends");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__SENDS));
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
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortSendsReferenceResolver().deResolve((org.sintef.thingml.Message) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__SENDS)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__SENDS), element));
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
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(6);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__TYPE));
		printCountingMap.put("type", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__CHANGEABLE));
		printCountingMap.put("changeable", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__LOWER_BOUND));
		printCountingMap.put("lowerBound", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__UPPER_BOUND));
		printCountingMap.put("upperBound", temp == null ? 0 : 1);
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
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("type");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__TYPE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPropertyTypeReferenceResolver().deResolve((org.sintef.thingml.Type) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__TYPE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__TYPE), element));
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
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_sintef_thingml_Property_2(element, localtab, out1, printCountingMap1);
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
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("[");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("lowerBound");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__LOWER_BOUND));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__LOWER_BOUND), element));
			}
			printCountingMap.put("lowerBound", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("..");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("upperBound");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__UPPER_BOUND));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__UPPER_BOUND), element));
			}
			printCountingMap.put("upperBound", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("]");
	}
	
	public void print_org_sintef_thingml_Property_2(org.sintef.thingml.Property element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
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
	
	
	public void print_org_sintef_thingml_Dictionary(org.sintef.thingml.Dictionary element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(7);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__TYPE));
		printCountingMap.put("type", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__CHANGEABLE));
		printCountingMap.put("changeable", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__LOWER_BOUND));
		printCountingMap.put("lowerBound", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__UPPER_BOUND));
		printCountingMap.put("upperBound", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__INDEX_TYPE));
		printCountingMap.put("indexType", temp == null ? 0 : 1);
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
		print_org_sintef_thingml_Dictionary_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("dictionary");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__NAME));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("indexType");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__INDEX_TYPE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getDictionaryIndexTypeReferenceResolver().deResolve((org.sintef.thingml.Type) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__INDEX_TYPE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__INDEX_TYPE), element));
			}
			printCountingMap.put("indexType", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("->");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("type");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__TYPE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPropertyTypeReferenceResolver().deResolve((org.sintef.thingml.Type) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__TYPE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__TYPE), element));
			}
			printCountingMap.put("type", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_sintef_thingml_Dictionary_1(element, localtab, out1, printCountingMap1);
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
			print_org_sintef_thingml_Dictionary_2(element, localtab, out1, printCountingMap1);
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
	
	public void print_org_sintef_thingml_Dictionary_0(org.sintef.thingml.Dictionary element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("changeable");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__CHANGEABLE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("T_READONLY");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__CHANGEABLE), element));
			}
			printCountingMap.put("changeable", count - 1);
		}
	}
	
	public void print_org_sintef_thingml_Dictionary_1(org.sintef.thingml.Dictionary element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("[");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("lowerBound");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__LOWER_BOUND));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__LOWER_BOUND), element));
			}
			printCountingMap.put("lowerBound", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("..");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("upperBound");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__UPPER_BOUND));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__UPPER_BOUND), element));
			}
			printCountingMap.put("upperBound", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("]");
	}
	
	public void print_org_sintef_thingml_Dictionary_2(org.sintef.thingml.Dictionary element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("annotations");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__ANNOTATIONS));
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
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__TYPE));
		printCountingMap.put("type", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
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
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getParameterTypeReferenceResolver().deResolve((org.sintef.thingml.Type) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__TYPE)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__TYPE), element));
			}
			printCountingMap.put("type", count - 1);
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
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION_LITERAL__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION_LITERAL__ANNOTATIONS));
		printCountingMap.put("annotations", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
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
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(7);
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
	
	
	public void print_org_sintef_thingml_PropertyAssignment(org.sintef.thingml.PropertyAssignment element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGNMENT__PROPERTY));
		printCountingMap.put("property", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGNMENT__EXPRESSION));
		printCountingMap.put("expression", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("property");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGNMENT__PROPERTY));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPropertyAssignmentPropertyReferenceResolver().deResolve((org.sintef.thingml.Property) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGNMENT__PROPERTY)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGNMENT__PROPERTY), element));
			}
			printCountingMap.put("property", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("=");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("expression");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGNMENT__EXPRESSION));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("expression", count - 1);
		}
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
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONDITIONAL_ACTION__ACTION));
		printCountingMap.put("action", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONDITIONAL_ACTION__CONDITION));
		printCountingMap.put("condition", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
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
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
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
	
	
	public void print_org_sintef_thingml_EventReference(org.sintef.thingml.EventReference element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EVENT_REFERENCE__MSG_REF));
		printCountingMap.put("msgRef", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EVENT_REFERENCE__PARAM_REF));
		printCountingMap.put("paramRef", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("msgRef");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EVENT_REFERENCE__MSG_REF));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getEventReferenceMsgRefReferenceResolver().deResolve((org.sintef.thingml.ReceiveMessage) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EVENT_REFERENCE__MSG_REF)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EVENT_REFERENCE__MSG_REF), element));
			}
			printCountingMap.put("msgRef", count - 1);
		}
		// DEFINITION PART BEGINS (CsString)
		out.print(".");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("paramRef");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EVENT_REFERENCE__PARAM_REF));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getEventReferenceParamRefReferenceResolver().deResolve((org.sintef.thingml.Parameter) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EVENT_REFERENCE__PARAM_REF)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EVENT_REFERENCE__PARAM_REF), element));
			}
			printCountingMap.put("paramRef", count - 1);
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
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPropertyReferencePropertyReferenceResolver().deResolve((org.sintef.thingml.Property) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_REFERENCE__PROPERTY)), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_REFERENCE__PROPERTY), element));
			}
			printCountingMap.put("property", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_IntegerLitteral(org.sintef.thingml.IntegerLitteral element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTEGER_LITTERAL__INT_VALUE));
		printCountingMap.put("intValue", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("intValue");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTEGER_LITTERAL__INT_VALUE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTEGER_LITTERAL__INT_VALUE), element));
			}
			printCountingMap.put("intValue", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_StringLitteral(org.sintef.thingml.StringLitteral element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STRING_LITTERAL__STRING_VALUE));
		printCountingMap.put("stringValue", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("stringValue");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STRING_LITTERAL__STRING_VALUE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("STRING_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STRING_LITTERAL__STRING_VALUE), element));
			}
			printCountingMap.put("stringValue", count - 1);
		}
	}
	
	
	public void print_org_sintef_thingml_BooleanLitteral(org.sintef.thingml.BooleanLitteral element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.BOOLEAN_LITTERAL__BOOL_VALUE));
		printCountingMap.put("boolValue", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("boolValue");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.BOOLEAN_LITTERAL__BOOL_VALUE));
			if (o != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver = tokenResolverFactory.createTokenResolver("BOOLEAN_LITERAL");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.BOOLEAN_LITTERAL__BOOL_VALUE), element));
			}
			printCountingMap.put("boolValue", count - 1);
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
