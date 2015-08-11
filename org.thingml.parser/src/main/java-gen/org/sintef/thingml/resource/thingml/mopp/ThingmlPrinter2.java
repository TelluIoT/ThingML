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

public class ThingmlPrinter2 implements org.sintef.thingml.resource.thingml.IThingmlTextPrinter {
	
	protected class PrintToken {
		
		private String text;
		private String tokenName;
		private org.eclipse.emf.ecore.EObject container;
		
		public PrintToken(String text, String tokenName, org.eclipse.emf.ecore.EObject container) {
			this.text = text;
			this.tokenName = tokenName;
			this.container = container;
		}
		
		public String getText() {
			return text;
		}
		
		public String getTokenName() {
			return tokenName;
		}
		
		public org.eclipse.emf.ecore.EObject getContainer() {
			return container;
		}
		
		public String toString() {
			return "'" + text + "' [" + tokenName + "]";
		}
		
	}
	
	/**
	 * The PrintCountingMap keeps tracks of the values that must be printed for each
	 * feature of an EObject. It is also used to store the indices of all values that
	 * have been printed. This knowledge is used to avoid printing values twice. We
	 * must store the concrete indices of the printed values instead of basically
	 * counting them, because values may be printed in an order that differs from the
	 * order in which they are stored in the EObject's feature.
	 */
	protected class PrintCountingMap {
		
		private java.util.Map<String, java.util.List<Object>> featureToValuesMap = new java.util.LinkedHashMap<String, java.util.List<Object>>();
		private java.util.Map<String, java.util.Set<Integer>> featureToPrintedIndicesMap = new java.util.LinkedHashMap<String, java.util.Set<Integer>>();
		
		public void setFeatureValues(String featureName, java.util.List<Object> values) {
			featureToValuesMap.put(featureName, values);
			// If the feature does not have values it won't be printed. An entry in
			// 'featureToPrintedIndicesMap' is therefore not needed in this case.
			if (values != null) {
				featureToPrintedIndicesMap.put(featureName, new java.util.LinkedHashSet<Integer>());
			}
		}
		
		public java.util.Set<Integer> getIndicesToPrint(String featureName) {
			return featureToPrintedIndicesMap.get(featureName);
		}
		
		public void addIndexToPrint(String featureName, int index) {
			featureToPrintedIndicesMap.get(featureName).add(index);
		}
		
		public int getCountLeft(org.sintef.thingml.resource.thingml.grammar.ThingmlTerminal terminal) {
			org.eclipse.emf.ecore.EStructuralFeature feature = terminal.getFeature();
			String featureName = feature.getName();
			java.util.List<Object> totalValuesToPrint = featureToValuesMap.get(featureName);
			java.util.Set<Integer> printedIndices = featureToPrintedIndicesMap.get(featureName);
			if (totalValuesToPrint == null) {
				return 0;
			}
			if (feature instanceof org.eclipse.emf.ecore.EAttribute) {
				// for attributes we do not need to check the type, since the CS languages does
				// not allow type restrictions for attributes.
				return totalValuesToPrint.size() - printedIndices.size();
			} else if (feature instanceof org.eclipse.emf.ecore.EReference) {
				org.eclipse.emf.ecore.EReference reference = (org.eclipse.emf.ecore.EReference) feature;
				if (!reference.isContainment()) {
					// for non-containment references we also do not need to check the type, since the
					// CS languages does not allow type restrictions for these either.
					return totalValuesToPrint.size() - printedIndices.size();
				}
			}
			// now we're left with containment references for which we check the type of the
			// objects to print
			java.util.List<Class<?>> allowedTypes = getAllowedTypes(terminal);
			java.util.Set<Integer> indicesWithCorrectType = new java.util.LinkedHashSet<Integer>();
			int index = 0;
			for (Object valueToPrint : totalValuesToPrint) {
				for (Class<?> allowedType : allowedTypes) {
					if (allowedType.isInstance(valueToPrint)) {
						indicesWithCorrectType.add(index);
					}
				}
				index++;
			}
			indicesWithCorrectType.removeAll(printedIndices);
			return indicesWithCorrectType.size();
		}
		
		public int getNextIndexToPrint(String featureName) {
			int printedValues = featureToPrintedIndicesMap.get(featureName).size();
			return printedValues;
		}
		
	}
	
	public final static String NEW_LINE = java.lang.System.getProperties().getProperty("line.separator");
	
	private final org.sintef.thingml.resource.thingml.util.ThingmlEClassUtil eClassUtil = new org.sintef.thingml.resource.thingml.util.ThingmlEClassUtil();
	
	/**
	 * Holds the resource that is associated with this printer. May be null if the
	 * printer is used stand alone.
	 */
	private org.sintef.thingml.resource.thingml.IThingmlTextResource resource;
	
	private java.util.Map<?, ?> options;
	private java.io.OutputStream outputStream;
	private String encoding = System.getProperty("file.encoding");
	protected java.util.List<PrintToken> tokenOutputStream;
	private org.sintef.thingml.resource.thingml.IThingmlTokenResolverFactory tokenResolverFactory = new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenResolverFactory();
	private boolean handleTokenSpaceAutomatically = false;
	private int tokenSpace = 0;
	/**
	 * A flag that indicates whether tokens have already been printed for some object.
	 * The flag is set to false whenever printing of an EObject tree is started. The
	 * status of the flag is used to avoid printing default token space in front of
	 * the root object.
	 */
	private boolean startedPrintingObject = false;
	/**
	 * The number of tab characters that were printed before the current line. This
	 * number is used to calculate the relative indentation when printing contained
	 * objects, because all contained objects must start with this indentation
	 * (tabsBeforeCurrentObject + currentTabs).
	 */
	private int currentTabs;
	/**
	 * The number of tab characters that must be printed before the current object.
	 * This number is used to calculate the indentation of new lines, when line breaks
	 * are printed within one object.
	 */
	private int tabsBeforeCurrentObject;
	/**
	 * This flag is used to indicate whether the number of tabs before the current
	 * object has been set for the current object. The flag is needed, because setting
	 * the number of tabs must be performed when the first token of the contained
	 * element is printed.
	 */
	private boolean startedPrintingContainedObject;
	
	public ThingmlPrinter2(java.io.OutputStream outputStream, org.sintef.thingml.resource.thingml.IThingmlTextResource resource) {
		super();
		this.outputStream = outputStream;
		this.resource = resource;
	}
	
	public void print(org.eclipse.emf.ecore.EObject element) throws java.io.IOException {
		tokenOutputStream = new java.util.ArrayList<PrintToken>();
		currentTabs = 0;
		tabsBeforeCurrentObject = 0;
		startedPrintingObject = true;
		startedPrintingContainedObject = false;
		java.util.List<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement>  formattingElements = new java.util.ArrayList<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement>();
		doPrint(element, formattingElements);
		// print all remaining formatting elements
		java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> layoutInformations = getCopyOfLayoutInformation(element);
		org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation eofLayoutInformation = getLayoutInformation(layoutInformations, null, null, null);
		printFormattingElements(element, formattingElements, layoutInformations, eofLayoutInformation);
		java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.BufferedOutputStream(outputStream), encoding));
		if (handleTokenSpaceAutomatically) {
			printSmart(writer);
		} else {
			printBasic(writer);
		}
		writer.flush();
	}
	
	protected void doPrint(org.eclipse.emf.ecore.EObject element, java.util.List<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement> foundFormattingElements) {
		if (element == null) {
			throw new java.lang.IllegalArgumentException("Nothing to write.");
		}
		if (outputStream == null) {
			throw new java.lang.IllegalArgumentException("Nothing to write on.");
		}
		
		if (element instanceof org.sintef.thingml.ThingMLModel) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_0, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.Message) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_1, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.Function) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.Thing) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.RequiredPort) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.ProvidedPort) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.Property) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_6, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.Parameter) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_7, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.PrimitiveType) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_8, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.Enumeration) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_9, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.EnumerationLiteral) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_10, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.PlatformAnnotation) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.StateMachine) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.CompositeState) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_14, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.ParallelRegion) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.Transition) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_16, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.InternalTransition) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_17, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.ReceiveMessage) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_18, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.PropertyAssign) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.Configuration) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_20, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.ConfigInclude) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_21, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.Instance) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_22, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.Connector) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_23, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.ConfigPropertyAssign) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_24, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.InstanceRef) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_25, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.SendAction) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_26, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.VariableAssignment) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_27, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.ActionBlock) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_28, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.LocalVariable) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_29, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.ExternStatement) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_30, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.ConditionalAction) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_31, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.LoopAction) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_32, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.PrintAction) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_33, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.ErrorAction) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_34, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.ReturnAction) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_35, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.FunctionCallStatement) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_36, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.SglMsgParamOperator) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_37, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.MessageParameter) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_38, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.SglMsgParamOperatorCall) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_39, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.StreamExpression) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_40, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.StreamOutput) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_41, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.Filter) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_42, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.LengthWindow) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_43, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.TimeWindow) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_44, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.SimpleSource) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_45, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.JoinSources) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_46, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.MergeSources) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_47, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.Stream) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_48, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.OrExpression) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_49, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.AndExpression) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_50, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.LowerExpression) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_51, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.GreaterExpression) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_52, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.EqualsExpression) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_53, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.PlusExpression) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_54, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.MinusExpression) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_55, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.TimesExpression) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_56, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.DivExpression) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_57, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.ModExpression) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_58, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.UnaryMinus) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_59, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.NotExpression) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_60, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.StreamParamReference) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_61, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.Reference) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_62, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.ExpressionGroup) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_63, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.PropertyReference) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_64, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.IntegerLiteral) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_65, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.StringLiteral) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_66, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.BooleanLiteral) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_67, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.EnumLiteralRef) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_68, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.ArrayIndex) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_69, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.FunctionCallExpression) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_70, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.ExternExpression) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_71, foundFormattingElements);
			return;
		}
		if (element instanceof org.sintef.thingml.State) {
			printInternal(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13, foundFormattingElements);
			return;
		}
		
		addWarningToResource("The printer can not handle " + element.eClass().getName() + " elements", element);
	}
	
	public void printInternal(org.eclipse.emf.ecore.EObject eObject, org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement ruleElement, java.util.List<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement> foundFormattingElements) {
		java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> layoutInformations = getCopyOfLayoutInformation(eObject);
		org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator decoratorTree = getDecoratorTree(ruleElement);
		decorateTree(decoratorTree, eObject);
		printTree(decoratorTree, eObject, foundFormattingElements, layoutInformations);
	}
	
	/**
	 * creates a tree of decorator objects which reflects the syntax tree that is
	 * attached to the given syntax element
	 */
	public org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator getDecoratorTree(org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement syntaxElement) {
		org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement[] children = syntaxElement.getChildren();
		int childCount = children.length;
		org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator[] childDecorators = new org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator[childCount];
		for (int i = 0; i < childCount; i++) {
			childDecorators[i] = getDecoratorTree(children[i]);
		}
		org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator decorator = new org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator(syntaxElement, childDecorators);
		return decorator;
	}
	
	public void decorateTree(org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator decorator, org.eclipse.emf.ecore.EObject eObject) {
		PrintCountingMap printCountingMap = initializePrintCountingMap(eObject);
		java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator> keywordsToPrint = new java.util.ArrayList<org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator>();
		decorateTreeBasic(decorator, eObject, printCountingMap, keywordsToPrint);
		for (org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator keywordToPrint : keywordsToPrint) {
			// for keywords the concrete index does not matter, but we must add one to
			// indicate that the keyword needs to be printed here. Thus, we use 0 as index.
			keywordToPrint.addIndexToPrint(0);
		}
	}
	
	/**
	 * Tries to decorate the decorator with an attribute value, or reference held by
	 * the given EObject. Returns true if an attribute value or reference was found.
	 */
	public boolean decorateTreeBasic(org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator decorator, org.eclipse.emf.ecore.EObject eObject, PrintCountingMap printCountingMap, java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator> keywordsToPrint) {
		boolean foundFeatureToPrint = false;
		org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement syntaxElement = decorator.getDecoratedElement();
		org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality cardinality = syntaxElement.getCardinality();
		boolean isFirstIteration = true;
		while (true) {
			java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator> subKeywordsToPrint = new java.util.ArrayList<org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator>();
			boolean keepDecorating = false;
			if (syntaxElement instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlKeyword) {
				subKeywordsToPrint.add(decorator);
			} else if (syntaxElement instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlTerminal) {
				org.sintef.thingml.resource.thingml.grammar.ThingmlTerminal terminal = (org.sintef.thingml.resource.thingml.grammar.ThingmlTerminal) syntaxElement;
				org.eclipse.emf.ecore.EStructuralFeature feature = terminal.getFeature();
				if (feature == org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.ANONYMOUS_FEATURE) {
					return false;
				}
				String featureName = feature.getName();
				int countLeft = printCountingMap.getCountLeft(terminal);
				if (countLeft > terminal.getMandatoryOccurencesAfter()) {
					// normally we print the element at the next index
					int indexToPrint = printCountingMap.getNextIndexToPrint(featureName);
					// But, if there are type restrictions for containments, we must choose an index
					// of an element that fits (i.e., which has the correct type)
					if (terminal instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlContainment) {
						org.sintef.thingml.resource.thingml.grammar.ThingmlContainment containment = (org.sintef.thingml.resource.thingml.grammar.ThingmlContainment) terminal;
						indexToPrint = findElementWithCorrectType(eObject, feature, printCountingMap.getIndicesToPrint(featureName), containment);
					}
					if (indexToPrint >= 0) {
						decorator.addIndexToPrint(indexToPrint);
						printCountingMap.addIndexToPrint(featureName, indexToPrint);
						keepDecorating = true;
					}
				}
			}
			if (syntaxElement instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlChoice) {
				// for choices we do print only the choice which does print at least one feature
				org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator childToPrint = null;
				for (org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator childDecorator : decorator.getChildDecorators()) {
					// pick first choice as default, will be overridden if a choice that prints a
					// feature is found
					if (childToPrint == null) {
						childToPrint = childDecorator;
					}
					if (doesPrintFeature(childDecorator, eObject, printCountingMap)) {
						childToPrint = childDecorator;
						break;
					}
				}
				keepDecorating |= decorateTreeBasic(childToPrint, eObject, printCountingMap, subKeywordsToPrint);
			} else {
				// for all other syntax element we do print all children
				for (org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator childDecorator : decorator.getChildDecorators()) {
					keepDecorating |= decorateTreeBasic(childDecorator, eObject, printCountingMap, subKeywordsToPrint);
				}
			}
			foundFeatureToPrint |= keepDecorating;
			// only print keywords if a feature was printed or the syntax element is mandatory
			if (cardinality == org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality.ONE) {
				keywordsToPrint.addAll(subKeywordsToPrint);
			} else if (cardinality == org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality.PLUS) {
				if (isFirstIteration) {
					keywordsToPrint.addAll(subKeywordsToPrint);
				} else {
					if (keepDecorating) {
						keywordsToPrint.addAll(subKeywordsToPrint);
					}
				}
			} else if (keepDecorating && (cardinality == org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality.STAR || cardinality == org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality.QUESTIONMARK)) {
				keywordsToPrint.addAll(subKeywordsToPrint);
			}
			if (cardinality == org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality.ONE || cardinality == org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality.QUESTIONMARK) {
				break;
			} else if (!keepDecorating) {
				break;
			}
			isFirstIteration = false;
		}
		return foundFeatureToPrint;
	}
	
	private int findElementWithCorrectType(org.eclipse.emf.ecore.EObject eObject, org.eclipse.emf.ecore.EStructuralFeature feature, java.util.Set<Integer> indicesToPrint, org.sintef.thingml.resource.thingml.grammar.ThingmlContainment containment) {
		// By default the type restrictions that are defined in the CS definition are
		// considered when printing models. You can change this behavior by setting the
		// 'ignoreTypeRestrictionsForPrinting' option to true.
		boolean ignoreTypeRestrictions = false;
		org.eclipse.emf.ecore.EClass[] allowedTypes = containment.getAllowedTypes();
		Object value = eObject.eGet(feature);
		if (value instanceof java.util.List<?>) {
			java.util.List<?> valueList = (java.util.List<?>) value;
			int listSize = valueList.size();
			for (int index = 0; index < listSize; index++) {
				if (indicesToPrint.contains(index)) {
					continue;
				}
				Object valueAtIndex = valueList.get(index);
				if (eClassUtil.isInstance(valueAtIndex, allowedTypes) || ignoreTypeRestrictions) {
					return index;
				}
			}
		} else {
			if (eClassUtil.isInstance(value, allowedTypes) || ignoreTypeRestrictions) {
				return 0;
			}
		}
		return -1;
	}
	
	/**
	 * Checks whether decorating the given node will use at least one attribute value,
	 * or reference held by eObject. Returns true if a printable attribute value or
	 * reference was found. This method is used to decide which choice to pick, when
	 * multiple choices are available. We pick the choice that prints at least one
	 * attribute or reference.
	 */
	public boolean doesPrintFeature(org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator decorator, org.eclipse.emf.ecore.EObject eObject, PrintCountingMap printCountingMap) {
		org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement syntaxElement = decorator.getDecoratedElement();
		if (syntaxElement instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlTerminal) {
			org.sintef.thingml.resource.thingml.grammar.ThingmlTerminal terminal = (org.sintef.thingml.resource.thingml.grammar.ThingmlTerminal) syntaxElement;
			org.eclipse.emf.ecore.EStructuralFeature feature = terminal.getFeature();
			if (feature == org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.ANONYMOUS_FEATURE) {
				return false;
			}
			int countLeft = printCountingMap.getCountLeft(terminal);
			if (countLeft > terminal.getMandatoryOccurencesAfter()) {
				// found a feature to print
				return true;
			}
		}
		for (org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator childDecorator : decorator.getChildDecorators()) {
			if (doesPrintFeature(childDecorator, eObject, printCountingMap)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean printTree(org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator decorator, org.eclipse.emf.ecore.EObject eObject, java.util.List<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement> foundFormattingElements, java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> layoutInformations) {
		org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement printElement = decorator.getDecoratedElement();
		org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality cardinality = printElement.getCardinality();
		java.util.List<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement> cloned = new java.util.ArrayList<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement>();
		cloned.addAll(foundFormattingElements);
		boolean foundSomethingAtAll = false;
		boolean foundSomethingToPrint;
		while (true) {
			foundSomethingToPrint = false;
			Integer indexToPrint = decorator.getNextIndexToPrint();
			if (indexToPrint != null) {
				if (printElement instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlKeyword) {
					printKeyword(eObject, (org.sintef.thingml.resource.thingml.grammar.ThingmlKeyword) printElement, foundFormattingElements, layoutInformations);
					foundSomethingToPrint = true;
				} else if (printElement instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlPlaceholder) {
					org.sintef.thingml.resource.thingml.grammar.ThingmlPlaceholder placeholder = (org.sintef.thingml.resource.thingml.grammar.ThingmlPlaceholder) printElement;
					printFeature(eObject, placeholder, indexToPrint, foundFormattingElements, layoutInformations);
					foundSomethingToPrint = true;
				} else if (printElement instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlContainment) {
					org.sintef.thingml.resource.thingml.grammar.ThingmlContainment containment = (org.sintef.thingml.resource.thingml.grammar.ThingmlContainment) printElement;
					printContainedObject(eObject, containment, indexToPrint, foundFormattingElements, layoutInformations);
					foundSomethingToPrint = true;
				} else if (printElement instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlBooleanTerminal) {
					org.sintef.thingml.resource.thingml.grammar.ThingmlBooleanTerminal booleanTerminal = (org.sintef.thingml.resource.thingml.grammar.ThingmlBooleanTerminal) printElement;
					printBooleanTerminal(eObject, booleanTerminal, indexToPrint, foundFormattingElements, layoutInformations);
					foundSomethingToPrint = true;
				} else if (printElement instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlEnumerationTerminal) {
					org.sintef.thingml.resource.thingml.grammar.ThingmlEnumerationTerminal enumTerminal = (org.sintef.thingml.resource.thingml.grammar.ThingmlEnumerationTerminal) printElement;
					printEnumerationTerminal(eObject, enumTerminal, indexToPrint, foundFormattingElements, layoutInformations);
					foundSomethingToPrint = true;
				}
			}
			if (foundSomethingToPrint) {
				foundSomethingAtAll = true;
			}
			if (printElement instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlWhiteSpace) {
				foundFormattingElements.add((org.sintef.thingml.resource.thingml.grammar.ThingmlWhiteSpace) printElement);
			}
			if (printElement instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlLineBreak) {
				foundFormattingElements.add((org.sintef.thingml.resource.thingml.grammar.ThingmlLineBreak) printElement);
			}
			for (org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxElementDecorator childDecorator : decorator.getChildDecorators()) {
				foundSomethingToPrint |= printTree(childDecorator, eObject, foundFormattingElements, layoutInformations);
				org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement decoratedElement = decorator.getDecoratedElement();
				if (foundSomethingToPrint && decoratedElement instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlChoice) {
					break;
				}
			}
			if (cardinality == org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality.ONE || cardinality == org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality.QUESTIONMARK) {
				break;
			} else if (!foundSomethingToPrint) {
				break;
			}
		}
		// only print formatting elements if a feature was printed or the syntax element
		// is mandatory
		if (!foundSomethingAtAll && (cardinality == org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality.STAR || cardinality == org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality.QUESTIONMARK)) {
			foundFormattingElements.clear();
			foundFormattingElements.addAll(cloned);
		}
		return foundSomethingToPrint;
	}
	
	public void printKeyword(org.eclipse.emf.ecore.EObject eObject, org.sintef.thingml.resource.thingml.grammar.ThingmlKeyword keyword, java.util.List<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement> foundFormattingElements, java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> layoutInformations) {
		org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation keywordLayout = getLayoutInformation(layoutInformations, keyword, null, eObject);
		printFormattingElements(eObject, foundFormattingElements, layoutInformations, keywordLayout);
		String value = keyword.getValue();
		tokenOutputStream.add(new PrintToken(value, "'" + org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.escapeToANTLRKeyword(value) + "'", eObject));
	}
	
	public void printFeature(org.eclipse.emf.ecore.EObject eObject, org.sintef.thingml.resource.thingml.grammar.ThingmlPlaceholder placeholder, int count, java.util.List<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement> foundFormattingElements, java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> layoutInformations) {
		org.eclipse.emf.ecore.EStructuralFeature feature = placeholder.getFeature();
		if (feature instanceof org.eclipse.emf.ecore.EAttribute) {
			printAttribute(eObject, (org.eclipse.emf.ecore.EAttribute) feature, placeholder, count, foundFormattingElements, layoutInformations);
		} else {
			printReference(eObject, (org.eclipse.emf.ecore.EReference) feature, placeholder, count, foundFormattingElements, layoutInformations);
		}
	}
	
	public void printAttribute(org.eclipse.emf.ecore.EObject eObject, org.eclipse.emf.ecore.EAttribute attribute, org.sintef.thingml.resource.thingml.grammar.ThingmlPlaceholder placeholder, int index, java.util.List<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement> foundFormattingElements, java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> layoutInformations) {
		String result = null;
		Object attributeValue = org.sintef.thingml.resource.thingml.util.ThingmlEObjectUtil.getFeatureValue(eObject, attribute, index);
		org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation attributeLayout = getLayoutInformation(layoutInformations, placeholder, attributeValue, eObject);
		String visibleTokenText = getVisibleTokenText(attributeLayout);
		// if there is text for the attribute we use it
		if (visibleTokenText != null) {
			result = visibleTokenText;
		}
		
		if (result == null) {
			// if no text is available, the attribute is deresolved to obtain its textual
			// representation
			org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver(placeholder.getTokenName());
			tokenResolver.setOptions(getOptions());
			String deResolvedValue = tokenResolver.deResolve(attributeValue, attribute, eObject);
			result = deResolvedValue;
		}
		
		if (result != null && !"".equals(result)) {
			printFormattingElements(eObject, foundFormattingElements, layoutInformations, attributeLayout);
			// write result to the output stream
			tokenOutputStream.add(new PrintToken(result, placeholder.getTokenName(), eObject));
		}
	}
	
	
	public void printBooleanTerminal(org.eclipse.emf.ecore.EObject eObject, org.sintef.thingml.resource.thingml.grammar.ThingmlBooleanTerminal booleanTerminal, int index, java.util.List<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement> foundFormattingElements, java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> layoutInformations) {
		org.eclipse.emf.ecore.EAttribute attribute = booleanTerminal.getAttribute();
		String result = null;
		Object attributeValue = org.sintef.thingml.resource.thingml.util.ThingmlEObjectUtil.getFeatureValue(eObject, attribute, index);
		org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation attributeLayout = getLayoutInformation(layoutInformations, booleanTerminal, attributeValue, eObject);
		String visibleTokenText = getVisibleTokenText(attributeLayout);
		// if there is text for the attribute we use it
		if (visibleTokenText != null) {
			result = visibleTokenText;
		}
		
		if (result == null) {
			// if no text is available, the boolean attribute is converted to its textual
			// representation using the literals of the boolean terminal
			if (Boolean.TRUE.equals(attributeValue)) {
				result = booleanTerminal.getTrueLiteral();
			} else {
				result = booleanTerminal.getFalseLiteral();
			}
		}
		
		if (result != null && !"".equals(result)) {
			printFormattingElements(eObject, foundFormattingElements, layoutInformations, attributeLayout);
			// write result to the output stream
			tokenOutputStream.add(new PrintToken(result, "'" + org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.escapeToANTLRKeyword(result) + "'", eObject));
		}
	}
	
	
	public void printEnumerationTerminal(org.eclipse.emf.ecore.EObject eObject, org.sintef.thingml.resource.thingml.grammar.ThingmlEnumerationTerminal enumTerminal, int index, java.util.List<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement> foundFormattingElements, java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> layoutInformations) {
		org.eclipse.emf.ecore.EAttribute attribute = enumTerminal.getAttribute();
		String result = null;
		Object attributeValue = org.sintef.thingml.resource.thingml.util.ThingmlEObjectUtil.getFeatureValue(eObject, attribute, index);
		org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation attributeLayout = getLayoutInformation(layoutInformations, enumTerminal, attributeValue, eObject);
		String visibleTokenText = getVisibleTokenText(attributeLayout);
		// if there is text for the attribute we use it
		if (visibleTokenText != null) {
			result = visibleTokenText;
		}
		
		if (result == null) {
			// if no text is available, the enumeration attribute is converted to its textual
			// representation using the literals of the enumeration terminal
			assert attributeValue instanceof org.eclipse.emf.common.util.Enumerator;
			result = enumTerminal.getText(((org.eclipse.emf.common.util.Enumerator) attributeValue).getName());
		}
		
		if (result != null && !"".equals(result)) {
			printFormattingElements(eObject, foundFormattingElements, layoutInformations, attributeLayout);
			// write result to the output stream
			tokenOutputStream.add(new PrintToken(result, "'" + org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.escapeToANTLRKeyword(result) + "'", eObject));
		}
	}
	
	
	public void printContainedObject(org.eclipse.emf.ecore.EObject eObject, org.sintef.thingml.resource.thingml.grammar.ThingmlContainment containment, int index, java.util.List<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement> foundFormattingElements, java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> layoutInformations) {
		org.eclipse.emf.ecore.EStructuralFeature reference = containment.getFeature();
		Object o = org.sintef.thingml.resource.thingml.util.ThingmlEObjectUtil.getFeatureValue(eObject, reference, index);
		// save current number of tabs to restore them after printing the contained object
		int oldTabsBeforeCurrentObject = tabsBeforeCurrentObject;
		int oldCurrentTabs = currentTabs;
		// use current number of tabs to indent contained object. we do not directly set
		// 'tabsBeforeCurrentObject' because the first element of the new object must be
		// printed with the old number of tabs.
		startedPrintingContainedObject = false;
		currentTabs = 0;
		doPrint((org.eclipse.emf.ecore.EObject) o, foundFormattingElements);
		// restore number of tabs after printing the contained object
		tabsBeforeCurrentObject = oldTabsBeforeCurrentObject;
		currentTabs = oldCurrentTabs;
	}
	
	public void printFormattingElements(org.eclipse.emf.ecore.EObject eObject, java.util.List<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement> foundFormattingElements, java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> layoutInformations, org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation layoutInformation) {
		String hiddenTokenText = getHiddenTokenText(layoutInformation);
		if (hiddenTokenText != null) {
			// removed used information
			if (layoutInformations != null) {
				layoutInformations.remove(layoutInformation);
			}
			tokenOutputStream.add(new PrintToken(hiddenTokenText, null, eObject));
			foundFormattingElements.clear();
			startedPrintingObject = false;
			setTabsBeforeCurrentObject(0);
			return;
		}
		int printedTabs = 0;
		if (foundFormattingElements.size() > 0) {
			for (org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement foundFormattingElement : foundFormattingElements) {
				if (foundFormattingElement instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlWhiteSpace) {
					int amount = ((org.sintef.thingml.resource.thingml.grammar.ThingmlWhiteSpace) foundFormattingElement).getAmount();
					for (int i = 0; i < amount; i++) {
						tokenOutputStream.add(createSpaceToken(eObject));
					}
				}
				if (foundFormattingElement instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlLineBreak) {
					currentTabs = ((org.sintef.thingml.resource.thingml.grammar.ThingmlLineBreak) foundFormattingElement).getTabs();
					printedTabs += currentTabs;
					tokenOutputStream.add(createNewLineToken(eObject));
					for (int i = 0; i < tabsBeforeCurrentObject + currentTabs; i++) {
						tokenOutputStream.add(createTabToken(eObject));
					}
				}
			}
			foundFormattingElements.clear();
			startedPrintingObject = false;
		} else {
			if (startedPrintingObject) {
				// if no elements have been printed yet, we do not add the default token space,
				// because spaces before the first element are not desired.
				startedPrintingObject = false;
			} else {
				if (!handleTokenSpaceAutomatically) {
					tokenOutputStream.add(new PrintToken(getWhiteSpaceString(tokenSpace), null, eObject));
				}
			}
		}
		// after printing the first element, we can use the new number of tabs.
		setTabsBeforeCurrentObject(printedTabs);
	}
	
	private void setTabsBeforeCurrentObject(int tabs) {
		if (startedPrintingContainedObject) {
			return;
		}
		tabsBeforeCurrentObject = tabsBeforeCurrentObject + tabs;
		startedPrintingContainedObject = true;
	}
	
	@SuppressWarnings("unchecked")	
	public void printReference(org.eclipse.emf.ecore.EObject eObject, org.eclipse.emf.ecore.EReference reference, org.sintef.thingml.resource.thingml.grammar.ThingmlPlaceholder placeholder, int index, java.util.List<org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement> foundFormattingElements, java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> layoutInformations) {
		String tokenName = placeholder.getTokenName();
		Object referencedObject = org.sintef.thingml.resource.thingml.util.ThingmlEObjectUtil.getFeatureValue(eObject, reference, index, false);
		// first add layout before the reference
		org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation referenceLayout = getLayoutInformation(layoutInformations, placeholder, referencedObject, eObject);
		printFormattingElements(eObject, foundFormattingElements, layoutInformations, referenceLayout);
		// proxy objects must be printed differently
		String deresolvedReference = null;
		if (referencedObject instanceof org.eclipse.emf.ecore.EObject) {
			org.eclipse.emf.ecore.EObject eObjectToDeResolve = (org.eclipse.emf.ecore.EObject) referencedObject;
			if (eObjectToDeResolve.eIsProxy()) {
				deresolvedReference = ((org.eclipse.emf.ecore.InternalEObject) eObjectToDeResolve).eProxyURI().fragment();
				// If the proxy was created by EMFText, we can try to recover the identifier from
				// the proxy URI
				if (deresolvedReference != null && deresolvedReference.startsWith(org.sintef.thingml.resource.thingml.IThingmlContextDependentURIFragment.INTERNAL_URI_FRAGMENT_PREFIX)) {
					deresolvedReference = deresolvedReference.substring(org.sintef.thingml.resource.thingml.IThingmlContextDependentURIFragment.INTERNAL_URI_FRAGMENT_PREFIX.length());
					deresolvedReference = deresolvedReference.substring(deresolvedReference.indexOf("_") + 1);
				}
			}
		}
		if (deresolvedReference == null) {
			// NC-References must always be printed by deresolving the reference. We cannot
			// use the visible token information, because deresolving usually depends on
			// attribute values of the referenced object instead of the object itself.
			@SuppressWarnings("rawtypes")			
			org.sintef.thingml.resource.thingml.IThingmlReferenceResolver referenceResolver = getReferenceResolverSwitch().getResolver(reference);
			referenceResolver.setOptions(getOptions());
			deresolvedReference = referenceResolver.deResolve((org.eclipse.emf.ecore.EObject) referencedObject, eObject, reference);
		}
		org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver(tokenName);
		tokenResolver.setOptions(getOptions());
		String deresolvedToken = tokenResolver.deResolve(deresolvedReference, reference, eObject);
		// write result to output stream
		tokenOutputStream.add(new PrintToken(deresolvedToken, tokenName, eObject));
	}
	
	@SuppressWarnings("unchecked")	
	public PrintCountingMap initializePrintCountingMap(org.eclipse.emf.ecore.EObject eObject) {
		// The PrintCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		PrintCountingMap printCountingMap = new PrintCountingMap();
		java.util.List<org.eclipse.emf.ecore.EStructuralFeature> features = eObject.eClass().getEAllStructuralFeatures();
		for (org.eclipse.emf.ecore.EStructuralFeature feature : features) {
			// We get the feature value without resolving it, because resolving is not
			// required to count the number of elements that are referenced by the feature.
			// Moreover, triggering reference resolving is not desired here, because we'd also
			// like to print models that contain unresolved references.
			Object featureValue = eObject.eGet(feature, false);
			if (featureValue != null) {
				if (featureValue instanceof java.util.List<?>) {
					printCountingMap.setFeatureValues(feature.getName(), (java.util.List<Object>) featureValue);
				} else {
					printCountingMap.setFeatureValues(feature.getName(), java.util.Collections.singletonList(featureValue));
				}
			} else {
				printCountingMap.setFeatureValues(feature.getName(), null);
			}
		}
		return printCountingMap;
	}
	
	public java.util.Map<?,?> getOptions() {
		return options;
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		this.options = options;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public void setEncoding(String encoding) {
		if (encoding != null) {
			this.encoding = encoding;
		}
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlTextResource getResource() {
		return resource;
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
	
	protected org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformationAdapter getLayoutInformationAdapter(org.eclipse.emf.ecore.EObject element) {
		for (org.eclipse.emf.common.notify.Adapter adapter : element.eAdapters()) {
			if (adapter instanceof org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformationAdapter) {
				return (org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformationAdapter) adapter;
			}
		}
		org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformationAdapter newAdapter = new org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformationAdapter();
		element.eAdapters().add(newAdapter);
		return newAdapter;
	}
	
	private org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation getLayoutInformation(java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> layoutInformations, org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement syntaxElement, Object object, org.eclipse.emf.ecore.EObject container) {
		for (org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation layoutInformation : layoutInformations) {
			if (syntaxElement == layoutInformation.getSyntaxElement()) {
				if (object == null) {
					return layoutInformation;
				}
				// The layout information adapter must only try to resolve the object it refers
				// to, if we compare with a non-proxy object. If we're printing a resource that
				// contains proxy objects, resolving must not be triggered.
				boolean isNoProxy = true;
				if (object instanceof org.eclipse.emf.ecore.EObject) {
					org.eclipse.emf.ecore.EObject eObject = (org.eclipse.emf.ecore.EObject) object;
					isNoProxy = !eObject.eIsProxy();
				}
				if (isSame(object, layoutInformation.getObject(container, isNoProxy))) {
					return layoutInformation;
				}
			}
		}
		return null;
	}
	
	public java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> getCopyOfLayoutInformation(org.eclipse.emf.ecore.EObject eObject) {
		org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformationAdapter layoutInformationAdapter = getLayoutInformationAdapter(eObject);
		java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> originalLayoutInformations = layoutInformationAdapter.getLayoutInformations();
		// create a copy of the original list of layout information object in order to be
		// able to remove used informations during printing
		java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation> layoutInformations = new java.util.ArrayList<org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation>(originalLayoutInformations.size());
		layoutInformations.addAll(originalLayoutInformations);
		return layoutInformations;
	}
	
	private String getHiddenTokenText(org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation layoutInformation) {
		if (layoutInformation != null) {
			return layoutInformation.getHiddenTokenText();
		} else {
			return null;
		}
	}
	
	private String getVisibleTokenText(org.sintef.thingml.resource.thingml.mopp.ThingmlLayoutInformation layoutInformation) {
		if (layoutInformation != null) {
			return layoutInformation.getVisibleTokenText();
		} else {
			return null;
		}
	}
	
	protected String getWhiteSpaceString(int count) {
		return getRepeatingString(count, ' ');
	}
	
	private String getRepeatingString(int count, char character) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < count; i++) {
			result.append(character);
		}
		return result.toString();
	}
	
	public void setHandleTokenSpaceAutomatically(boolean handleTokenSpaceAutomatically) {
		this.handleTokenSpaceAutomatically = handleTokenSpaceAutomatically;
	}
	
	public void setTokenSpace(int tokenSpace) {
		this.tokenSpace = tokenSpace;
	}
	
	/**
	 * Prints the current tokenOutputStream to the given writer (as it is).
	 */
	public void printBasic(java.io.PrintWriter writer) throws java.io.IOException {
		for (PrintToken nextToken : tokenOutputStream) {
			writer.write(nextToken.getText());
		}
	}
	
	/**
	 * Prints the current tokenOutputStream to the given writer.
	 * 
	 * This methods implements smart whitespace printing. It does so by writing output
	 * to a token stream instead of printing the raw token text to a PrintWriter.
	 * Tokens in this stream hold both the text and the type of the token (i.e., its
	 * name).
	 * 
	 * To decide where whitespace is needed, sequences of successive tokens are
	 * searched that can be printed without separating whitespace. To determine such
	 * groups we start with two successive non-whitespace tokens, concatenate their
	 * text and use the generated ANTLR lexer to split the text. If the resulting
	 * token sequence of the concatenated text is exactly the same as the one that is
	 * to be printed, no whitespace is needed. The tokens in the sequence are checked
	 * both regarding their type and their text. If two tokens successfully form a
	 * group a third one is added and so on.
	 */
	public void printSmart(java.io.PrintWriter writer) throws java.io.IOException {
		// stores the text of the current group of tokens. this text is given to the lexer
		// to check whether it can be correctly scanned.
		StringBuilder currentBlock = new StringBuilder();
		// stores the index of the first token of the current group.
		int currentBlockStart = 0;
		// stores the text that was already successfully checked (i.e., is can be scanned
		// correctly and can thus be printed).
		String validBlock = "";
		char lastCharWritten = ' ';
		for (int i = 0; i < tokenOutputStream.size(); i++) {
			PrintToken tokenI = tokenOutputStream.get(i);
			currentBlock.append(tokenI.getText());
			// if declared or preserved whitespace is found - print block
			if (tokenI.getTokenName() == null) {
				char[] charArray = currentBlock.toString().toCharArray();
				writer.write(charArray);
				if (charArray.length > 0) {
					lastCharWritten = charArray[charArray.length - 1];
				}
				// reset all values
				currentBlock = new StringBuilder();
				currentBlockStart = i + 1;
				validBlock = "";
				continue;
			}
			// now check whether the current block can be scanned
			org.sintef.thingml.resource.thingml.IThingmlTextScanner scanner = new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().createLexer();
			scanner.setText(currentBlock.toString());
			// retrieve all tokens from scanner and add them to list 'tempTokens'
			java.util.List<org.sintef.thingml.resource.thingml.IThingmlTextToken> tempTokens = new java.util.ArrayList<org.sintef.thingml.resource.thingml.IThingmlTextToken>();
			org.sintef.thingml.resource.thingml.IThingmlTextToken nextToken = scanner.getNextToken();
			while (nextToken != null && nextToken.getText() != null) {
				tempTokens.add(nextToken);
				nextToken = scanner.getNextToken();
			}
			boolean sequenceIsValid = true;
			// check whether the current block was scanned to the same token sequence
			for (int t = 0; t < tempTokens.size(); t++) {
				PrintToken printTokenT = tokenOutputStream.get(currentBlockStart + t);
				org.sintef.thingml.resource.thingml.IThingmlTextToken tempToken = tempTokens.get(t);
				if (!tempToken.getText().equals(printTokenT.getText())) {
					sequenceIsValid = false;
					break;
				}
				String commonTokenName = tempToken.getName();
				String printTokenName = printTokenT.getTokenName();
				if (printTokenName.length() > 2 && printTokenName.startsWith("'") && printTokenName.endsWith("'")) {
					printTokenName = printTokenName.substring(1, printTokenName.length() - 1);
				}
				if (!commonTokenName.equals(printTokenName)) {
					sequenceIsValid = false;
					break;
				}
			}
			if (sequenceIsValid) {
				// sequence is still valid, try adding one more token in the next iteration of the
				// loop
				validBlock += tokenI.getText();
			} else {
				// sequence is not valid, must print whitespace to separate tokens
				// print text that is valid so far
				char[] charArray = validBlock.toString().toCharArray();
				writer.write(charArray);
				if (charArray.length > 0) {
					lastCharWritten = charArray[charArray.length - 1];
				}
				// print separating whitespace
				// if no whitespace (or tab or linebreak) is already there
				if (lastCharWritten != ' ' && lastCharWritten != '\t' && lastCharWritten != '\n' && lastCharWritten != '\r') {
					lastCharWritten = ' ';
					writer.write(lastCharWritten);
				}
				// add current token as initial value for next iteration
				currentBlock = new StringBuilder(tokenI.getText());
				currentBlockStart = i;
				validBlock = tokenI.getText();
			}
		}
		// flush remaining valid text to writer
		writer.write(validBlock);
	}
	
	private boolean isSame(Object o1, Object o2) {
		if (o1 instanceof String || o1 instanceof Integer || o1 instanceof Long || o1 instanceof Byte || o1 instanceof Short || o1 instanceof Float || o2 instanceof Double) {
			return o1.equals(o2);
		}
		return o1 == o2;
	}
	
	protected java.util.List<Class<?>> getAllowedTypes(org.sintef.thingml.resource.thingml.grammar.ThingmlTerminal terminal) {
		java.util.List<Class<?>> allowedTypes = new java.util.ArrayList<Class<?>>();
		allowedTypes.add(terminal.getFeature().getEType().getInstanceClass());
		if (terminal instanceof org.sintef.thingml.resource.thingml.grammar.ThingmlContainment) {
			org.sintef.thingml.resource.thingml.grammar.ThingmlContainment printingContainment = (org.sintef.thingml.resource.thingml.grammar.ThingmlContainment) terminal;
			org.eclipse.emf.ecore.EClass[] typeRestrictions = printingContainment.getAllowedTypes();
			if (typeRestrictions != null && typeRestrictions.length > 0) {
				allowedTypes.clear();
				for (org.eclipse.emf.ecore.EClass eClass : typeRestrictions) {
					allowedTypes.add(eClass.getInstanceClass());
				}
			}
		}
		return allowedTypes;
	}
	
	protected PrintToken createSpaceToken(org.eclipse.emf.ecore.EObject container) {
		return new PrintToken(" ", null, container);
	}
	
	protected PrintToken createTabToken(org.eclipse.emf.ecore.EObject container) {
		return new PrintToken("\t", null, container);
	}
	
	protected PrintToken createNewLineToken(org.eclipse.emf.ecore.EObject container) {
		return new PrintToken(NEW_LINE, null, container);
	}
	
}
