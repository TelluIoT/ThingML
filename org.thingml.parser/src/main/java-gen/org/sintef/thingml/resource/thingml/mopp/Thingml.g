grammar Thingml;

options {
	superClass = ThingmlANTLRParserBase;
	backtrack = true;
	memoize = true;
}

@lexer::header {
	package org.sintef.thingml.resource.thingml.mopp;
}

@lexer::members {
	public java.util.List<org.antlr.runtime3_3_0.RecognitionException> lexerExceptions  = new java.util.ArrayList<org.antlr.runtime3_3_0.RecognitionException>();
	public java.util.List<Integer> lexerExceptionsPosition = new java.util.ArrayList<Integer>();
	
	public void reportError(org.antlr.runtime3_3_0.RecognitionException e) {
		lexerExceptions.add(e);
		lexerExceptionsPosition.add(((org.antlr.runtime3_3_0.ANTLRStringStream) input).index());
	}
}
@header{
	package org.sintef.thingml.resource.thingml.mopp;
}

@members{
	private org.sintef.thingml.resource.thingml.IThingmlTokenResolverFactory tokenResolverFactory = new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenResolverFactory();
	
	/**
	 * the index of the last token that was handled by collectHiddenTokens()
	 */
	private int lastPosition;
	
	/**
	 * A flag that indicates whether the parser should remember all expected elements.
	 * This flag is set to true when using the parse for code completion. Otherwise it
	 * is set to false.
	 */
	private boolean rememberExpectedElements = false;
	
	private Object parseToIndexTypeObject;
	private int lastTokenIndex = 0;
	
	/**
	 * A list of expected elements the were collected while parsing the input stream.
	 * This list is only filled if <code>rememberExpectedElements</code> is set to
	 * true.
	 */
	private java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal> expectedElements = new java.util.ArrayList<org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal>();
	
	private int mismatchedTokenRecoveryTries = 0;
	/**
	 * A helper list to allow a lexer to pass errors to its parser
	 */
	protected java.util.List<org.antlr.runtime3_3_0.RecognitionException> lexerExceptions = java.util.Collections.synchronizedList(new java.util.ArrayList<org.antlr.runtime3_3_0.RecognitionException>());
	
	/**
	 * Another helper list to allow a lexer to pass positions of errors to its parser
	 */
	protected java.util.List<Integer> lexerExceptionsPosition = java.util.Collections.synchronizedList(new java.util.ArrayList<Integer>());
	
	/**
	 * A stack for incomplete objects. This stack is used filled when the parser is
	 * used for code completion. Whenever the parser starts to read an object it is
	 * pushed on the stack. Once the element was parser completely it is popped from
	 * the stack.
	 */
	protected java.util.Stack<org.eclipse.emf.ecore.EObject> incompleteObjects = new java.util.Stack<org.eclipse.emf.ecore.EObject>();
	
	private int stopIncludingHiddenTokens;
	private int stopExcludingHiddenTokens;
	private int tokenIndexOfLastCompleteElement;
	
	private int expectedElementsIndexOfLastCompleteElement;
	
	/**
	 * The offset indicating the cursor position when the parser is used for code
	 * completion by calling parseToExpectedElements().
	 */
	private int cursorOffset;
	
	/**
	 * The offset of the first hidden token of the last expected element. This offset
	 * is used to discard expected elements, which are not needed for code completion.
	 */
	private int lastStartIncludingHidden;
	
	protected void addErrorToResource(final String errorMessage, final int column, final int line, final int startIndex, final int stopIndex) {
		postParseCommands.add(new org.sintef.thingml.resource.thingml.IThingmlCommand<org.sintef.thingml.resource.thingml.IThingmlTextResource>() {
			public boolean execute(org.sintef.thingml.resource.thingml.IThingmlTextResource resource) {
				if (resource == null) {
					// the resource can be null if the parser is used for code completion
					return true;
				}
				resource.addProblem(new org.sintef.thingml.resource.thingml.IThingmlProblem() {
					public org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity getSeverity() {
						return org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity.ERROR;
					}
					public org.sintef.thingml.resource.thingml.ThingmlEProblemType getType() {
						return org.sintef.thingml.resource.thingml.ThingmlEProblemType.SYNTAX_ERROR;
					}
					public String getMessage() {
						return errorMessage;
					}
					public java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlQuickFix> getQuickFixes() {
						return null;
					}
				}, column, line, startIndex, stopIndex);
				return true;
			}
		});
	}
	
	public void addExpectedElement(org.sintef.thingml.resource.thingml.IThingmlExpectedElement terminal, int followSetID, org.eclipse.emf.ecore.EStructuralFeature... containmentTrace) {
		if (!this.rememberExpectedElements) {
			return;
		}
		org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal expectedElement = new org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal(terminal, followSetID, containmentTrace);
		setPosition(expectedElement, input.index());
		int startIncludingHiddenTokens = expectedElement.getStartIncludingHiddenTokens();
		if (lastStartIncludingHidden >= 0 && lastStartIncludingHidden < startIncludingHiddenTokens && cursorOffset > startIncludingHiddenTokens) {
			// clear list of expected elements
			this.expectedElements.clear();
		}
		lastStartIncludingHidden = startIncludingHiddenTokens;
		this.expectedElements.add(expectedElement);
	}
	
	protected void collectHiddenTokens(org.eclipse.emf.ecore.EObject element) {
	}
	
	protected void copyLocalizationInfos(final org.eclipse.emf.ecore.EObject source, final org.eclipse.emf.ecore.EObject target) {
		postParseCommands.add(new org.sintef.thingml.resource.thingml.IThingmlCommand<org.sintef.thingml.resource.thingml.IThingmlTextResource>() {
			public boolean execute(org.sintef.thingml.resource.thingml.IThingmlTextResource resource) {
				org.sintef.thingml.resource.thingml.IThingmlLocationMap locationMap = resource.getLocationMap();
				if (locationMap == null) {
					// the locationMap can be null if the parser is used for code completion
					return true;
				}
				locationMap.setCharStart(target, locationMap.getCharStart(source));
				locationMap.setCharEnd(target, locationMap.getCharEnd(source));
				locationMap.setColumn(target, locationMap.getColumn(source));
				locationMap.setLine(target, locationMap.getLine(source));
				return true;
			}
		});
	}
	
	protected void copyLocalizationInfos(final org.antlr.runtime3_3_0.CommonToken source, final org.eclipse.emf.ecore.EObject target) {
		postParseCommands.add(new org.sintef.thingml.resource.thingml.IThingmlCommand<org.sintef.thingml.resource.thingml.IThingmlTextResource>() {
			public boolean execute(org.sintef.thingml.resource.thingml.IThingmlTextResource resource) {
				org.sintef.thingml.resource.thingml.IThingmlLocationMap locationMap = resource.getLocationMap();
				if (locationMap == null) {
					// the locationMap can be null if the parser is used for code completion
					return true;
				}
				if (source == null) {
					return true;
				}
				locationMap.setCharStart(target, source.getStartIndex());
				locationMap.setCharEnd(target, source.getStopIndex());
				locationMap.setColumn(target, source.getCharPositionInLine());
				locationMap.setLine(target, source.getLine());
				return true;
			}
		});
	}
	
	/**
	 * Sets the end character index and the last line for the given object in the
	 * location map.
	 */
	protected void setLocalizationEnd(java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlCommand<org.sintef.thingml.resource.thingml.IThingmlTextResource>> postParseCommands , final org.eclipse.emf.ecore.EObject object, final int endChar, final int endLine) {
		postParseCommands.add(new org.sintef.thingml.resource.thingml.IThingmlCommand<org.sintef.thingml.resource.thingml.IThingmlTextResource>() {
			public boolean execute(org.sintef.thingml.resource.thingml.IThingmlTextResource resource) {
				org.sintef.thingml.resource.thingml.IThingmlLocationMap locationMap = resource.getLocationMap();
				if (locationMap == null) {
					// the locationMap can be null if the parser is used for code completion
					return true;
				}
				locationMap.setCharEnd(object, endChar);
				locationMap.setLine(object, endLine);
				return true;
			}
		});
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlTextParser createInstance(java.io.InputStream actualInputStream, String encoding) {
		try {
			if (encoding == null) {
				return new ThingmlParser(new org.antlr.runtime3_3_0.CommonTokenStream(new ThingmlLexer(new org.antlr.runtime3_3_0.ANTLRInputStream(actualInputStream))));
			} else {
				return new ThingmlParser(new org.antlr.runtime3_3_0.CommonTokenStream(new ThingmlLexer(new org.antlr.runtime3_3_0.ANTLRInputStream(actualInputStream, encoding))));
			}
		} catch (java.io.IOException e) {
			org.sintef.thingml.resource.thingml.mopp.ThingmlPlugin.logError("Error while creating parser.", e);
			return null;
		}
	}
	
	/**
	 * This default constructor is only used to call createInstance() on it.
	 */
	public ThingmlParser() {
		super(null);
	}
	
	protected org.eclipse.emf.ecore.EObject doParse() throws org.antlr.runtime3_3_0.RecognitionException {
		this.lastPosition = 0;
		// required because the lexer class can not be subclassed
		((ThingmlLexer) getTokenStream().getTokenSource()).lexerExceptions = lexerExceptions;
		((ThingmlLexer) getTokenStream().getTokenSource()).lexerExceptionsPosition = lexerExceptionsPosition;
		Object typeObject = getTypeObject();
		if (typeObject == null) {
			return start();
		} else if (typeObject instanceof org.eclipse.emf.ecore.EClass) {
			org.eclipse.emf.ecore.EClass type = (org.eclipse.emf.ecore.EClass) typeObject;
			if (type.getInstanceClass() == org.sintef.thingml.ThingMLModel.class) {
				return parse_org_sintef_thingml_ThingMLModel();
			}
			if (type.getInstanceClass() == org.sintef.thingml.Message.class) {
				return parse_org_sintef_thingml_Message();
			}
			if (type.getInstanceClass() == org.sintef.thingml.Thing.class) {
				return parse_org_sintef_thingml_Thing();
			}
			if (type.getInstanceClass() == org.sintef.thingml.Port.class) {
				return parse_org_sintef_thingml_Port();
			}
			if (type.getInstanceClass() == org.sintef.thingml.Property.class) {
				return parse_org_sintef_thingml_Property();
			}
			if (type.getInstanceClass() == org.sintef.thingml.Dictionary.class) {
				return parse_org_sintef_thingml_Dictionary();
			}
			if (type.getInstanceClass() == org.sintef.thingml.Parameter.class) {
				return parse_org_sintef_thingml_Parameter();
			}
			if (type.getInstanceClass() == org.sintef.thingml.PrimitiveType.class) {
				return parse_org_sintef_thingml_PrimitiveType();
			}
			if (type.getInstanceClass() == org.sintef.thingml.Enumeration.class) {
				return parse_org_sintef_thingml_Enumeration();
			}
			if (type.getInstanceClass() == org.sintef.thingml.EnumerationLiteral.class) {
				return parse_org_sintef_thingml_EnumerationLiteral();
			}
			if (type.getInstanceClass() == org.sintef.thingml.PlatformAnnotation.class) {
				return parse_org_sintef_thingml_PlatformAnnotation();
			}
			if (type.getInstanceClass() == org.sintef.thingml.StateMachine.class) {
				return parse_org_sintef_thingml_StateMachine();
			}
			if (type.getInstanceClass() == org.sintef.thingml.State.class) {
				return parse_org_sintef_thingml_State();
			}
			if (type.getInstanceClass() == org.sintef.thingml.CompositeState.class) {
				return parse_org_sintef_thingml_CompositeState();
			}
			if (type.getInstanceClass() == org.sintef.thingml.ParallelRegion.class) {
				return parse_org_sintef_thingml_ParallelRegion();
			}
			if (type.getInstanceClass() == org.sintef.thingml.Transition.class) {
				return parse_org_sintef_thingml_Transition();
			}
			if (type.getInstanceClass() == org.sintef.thingml.InternalTransition.class) {
				return parse_org_sintef_thingml_InternalTransition();
			}
			if (type.getInstanceClass() == org.sintef.thingml.ReceiveMessage.class) {
				return parse_org_sintef_thingml_ReceiveMessage();
			}
			if (type.getInstanceClass() == org.sintef.thingml.SendAction.class) {
				return parse_org_sintef_thingml_SendAction();
			}
			if (type.getInstanceClass() == org.sintef.thingml.PropertyAssignment.class) {
				return parse_org_sintef_thingml_PropertyAssignment();
			}
			if (type.getInstanceClass() == org.sintef.thingml.ActionBlock.class) {
				return parse_org_sintef_thingml_ActionBlock();
			}
			if (type.getInstanceClass() == org.sintef.thingml.ExternStatement.class) {
				return parse_org_sintef_thingml_ExternStatement();
			}
			if (type.getInstanceClass() == org.sintef.thingml.ConditionalAction.class) {
				return parse_org_sintef_thingml_ConditionalAction();
			}
			if (type.getInstanceClass() == org.sintef.thingml.LoopAction.class) {
				return parse_org_sintef_thingml_LoopAction();
			}
			if (type.getInstanceClass() == org.sintef.thingml.PrintAction.class) {
				return parse_org_sintef_thingml_PrintAction();
			}
			if (type.getInstanceClass() == org.sintef.thingml.ErrorAction.class) {
				return parse_org_sintef_thingml_ErrorAction();
			}
		}
		throw new org.sintef.thingml.resource.thingml.mopp.ThingmlUnexpectedContentTypeException(typeObject);
	}
	
	public int getMismatchedTokenRecoveryTries() {
		return mismatchedTokenRecoveryTries;
	}
	
	public Object getMissingSymbol(org.antlr.runtime3_3_0.IntStream arg0, org.antlr.runtime3_3_0.RecognitionException arg1, int arg2, org.antlr.runtime3_3_0.BitSet arg3) {
		mismatchedTokenRecoveryTries++;
		return super.getMissingSymbol(arg0, arg1, arg2, arg3);
	}
	
	public Object getParseToIndexTypeObject() {
		return parseToIndexTypeObject;
	}
	
	protected Object getTypeObject() {
		Object typeObject = getParseToIndexTypeObject();
		if (typeObject != null) {
			return typeObject;
		}
		java.util.Map<?,?> options = getOptions();
		if (options != null) {
			typeObject = options.get(org.sintef.thingml.resource.thingml.IThingmlOptions.RESOURCE_CONTENT_TYPE);
		}
		return typeObject;
	}
	
	/**
	 * Implementation that calls {@link #doParse()} and handles the thrown
	 * RecognitionExceptions.
	 */
	public org.sintef.thingml.resource.thingml.IThingmlParseResult parse() {
		terminateParsing = false;
		postParseCommands = new java.util.ArrayList<org.sintef.thingml.resource.thingml.IThingmlCommand<org.sintef.thingml.resource.thingml.IThingmlTextResource>>();
		org.sintef.thingml.resource.thingml.mopp.ThingmlParseResult parseResult = new org.sintef.thingml.resource.thingml.mopp.ThingmlParseResult();
		try {
			org.eclipse.emf.ecore.EObject result =  doParse();
			if (lexerExceptions.isEmpty()) {
				parseResult.setRoot(result);
			}
		} catch (org.antlr.runtime3_3_0.RecognitionException re) {
			reportError(re);
		} catch (java.lang.IllegalArgumentException iae) {
			if ("The 'no null' constraint is violated".equals(iae.getMessage())) {
				// can be caused if a null is set on EMF models where not allowed. this will just
				// happen if other errors occurred before
			} else {
				iae.printStackTrace();
			}
		}
		for (org.antlr.runtime3_3_0.RecognitionException re : lexerExceptions) {
			reportLexicalError(re);
		}
		parseResult.getPostParseCommands().addAll(postParseCommands);
		return parseResult;
	}
	
	public java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal> parseToExpectedElements(org.eclipse.emf.ecore.EClass type, org.sintef.thingml.resource.thingml.IThingmlTextResource dummyResource, int cursorOffset) {
		this.rememberExpectedElements = true;
		this.parseToIndexTypeObject = type;
		this.cursorOffset = cursorOffset;
		this.lastStartIncludingHidden = -1;
		final org.antlr.runtime3_3_0.CommonTokenStream tokenStream = (org.antlr.runtime3_3_0.CommonTokenStream) getTokenStream();
		org.sintef.thingml.resource.thingml.IThingmlParseResult result = parse();
		for (org.eclipse.emf.ecore.EObject incompleteObject : incompleteObjects) {
			org.antlr.runtime3_3_0.Lexer lexer = (org.antlr.runtime3_3_0.Lexer) tokenStream.getTokenSource();
			int endChar = lexer.getCharIndex();
			int endLine = lexer.getLine();
			setLocalizationEnd(result.getPostParseCommands(), incompleteObject, endChar, endLine);
		}
		if (result != null) {
			org.eclipse.emf.ecore.EObject root = result.getRoot();
			if (root != null) {
				dummyResource.getContentsInternal().add(root);
			}
			for (org.sintef.thingml.resource.thingml.IThingmlCommand<org.sintef.thingml.resource.thingml.IThingmlTextResource> command : result.getPostParseCommands()) {
				command.execute(dummyResource);
			}
		}
		// remove all expected elements that were added after the last complete element
		expectedElements = expectedElements.subList(0, expectedElementsIndexOfLastCompleteElement + 1);
		int lastFollowSetID = expectedElements.get(expectedElementsIndexOfLastCompleteElement).getFollowSetID();
		java.util.Set<org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal> currentFollowSet = new java.util.LinkedHashSet<org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal>();
		java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal> newFollowSet = new java.util.ArrayList<org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal>();
		for (int i = expectedElementsIndexOfLastCompleteElement; i >= 0; i--) {
			org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal expectedElementI = expectedElements.get(i);
			if (expectedElementI.getFollowSetID() == lastFollowSetID) {
				currentFollowSet.add(expectedElementI);
			} else {
				break;
			}
		}
		int followSetID = 293;
		int i;
		for (i = tokenIndexOfLastCompleteElement; i < tokenStream.size(); i++) {
			org.antlr.runtime3_3_0.CommonToken nextToken = (org.antlr.runtime3_3_0.CommonToken) tokenStream.get(i);
			if (nextToken.getType() < 0) {
				break;
			}
			if (nextToken.getChannel() == 99) {
				// hidden tokens do not reduce the follow set
			} else {
				// now that we have found the next visible token the position for that expected
				// terminals can be set
				for (org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal nextFollow : newFollowSet) {
					lastTokenIndex = 0;
					setPosition(nextFollow, i);
				}
				newFollowSet.clear();
				// normal tokens do reduce the follow set - only elements that match the token are
				// kept
				for (org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal nextFollow : currentFollowSet) {
					if (nextFollow.getTerminal().getTokenNames().contains(getTokenNames()[nextToken.getType()])) {
						// keep this one - it matches
						java.util.Collection<org.sintef.thingml.resource.thingml.util.ThingmlPair<org.sintef.thingml.resource.thingml.IThingmlExpectedElement, org.eclipse.emf.ecore.EStructuralFeature[]>> newFollowers = nextFollow.getTerminal().getFollowers();
						for (org.sintef.thingml.resource.thingml.util.ThingmlPair<org.sintef.thingml.resource.thingml.IThingmlExpectedElement, org.eclipse.emf.ecore.EStructuralFeature[]> newFollowerPair : newFollowers) {
							org.sintef.thingml.resource.thingml.IThingmlExpectedElement newFollower = newFollowerPair.getLeft();
							org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal newFollowTerminal = new org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal(newFollower, followSetID, newFollowerPair.getRight());
							newFollowSet.add(newFollowTerminal);
							expectedElements.add(newFollowTerminal);
						}
					}
				}
				currentFollowSet.clear();
				currentFollowSet.addAll(newFollowSet);
			}
			followSetID++;
		}
		// after the last token in the stream we must set the position for the elements
		// that were added during the last iteration of the loop
		for (org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal nextFollow : newFollowSet) {
			lastTokenIndex = 0;
			setPosition(nextFollow, i);
		}
		return this.expectedElements;
	}
	
	public void setPosition(org.sintef.thingml.resource.thingml.mopp.ThingmlExpectedTerminal expectedElement, int tokenIndex) {
		int currentIndex = Math.max(0, tokenIndex);
		for (int index = lastTokenIndex; index < currentIndex; index++) {
			if (index >= input.size()) {
				break;
			}
			org.antlr.runtime3_3_0.CommonToken tokenAtIndex = (org.antlr.runtime3_3_0.CommonToken) input.get(index);
			stopIncludingHiddenTokens = tokenAtIndex.getStopIndex() + 1;
			if (tokenAtIndex.getChannel() != 99) {
				stopExcludingHiddenTokens = tokenAtIndex.getStopIndex() + 1;
			}
		}
		lastTokenIndex = Math.max(0, currentIndex);
		expectedElement.setPosition(stopExcludingHiddenTokens, stopIncludingHiddenTokens);
	}
	
	public Object recoverFromMismatchedToken(org.antlr.runtime3_3_0.IntStream input, int ttype, org.antlr.runtime3_3_0.BitSet follow) throws org.antlr.runtime3_3_0.RecognitionException {
		if (!rememberExpectedElements) {
			return super.recoverFromMismatchedToken(input, ttype, follow);
		} else {
			return null;
		}
	}
	
	/**
	 * Translates errors thrown by the parser into human readable messages.
	 */
	public void reportError(final org.antlr.runtime3_3_0.RecognitionException e)  {
		String message = e.getMessage();
		if (e instanceof org.antlr.runtime3_3_0.MismatchedTokenException) {
			org.antlr.runtime3_3_0.MismatchedTokenException mte = (org.antlr.runtime3_3_0.MismatchedTokenException) e;
			String expectedTokenName = formatTokenName(mte.expecting);
			String actualTokenName = formatTokenName(e.token.getType());
			message = "Syntax error on token \"" + e.token.getText() + " (" + actualTokenName + ")\", \"" + expectedTokenName + "\" expected";
		} else if (e instanceof org.antlr.runtime3_3_0.MismatchedTreeNodeException) {
			org.antlr.runtime3_3_0.MismatchedTreeNodeException mtne = (org.antlr.runtime3_3_0.MismatchedTreeNodeException) e;
			String expectedTokenName = formatTokenName(mtne.expecting);
			message = "mismatched tree node: " + "xxx" + "; tokenName " + expectedTokenName;
		} else if (e instanceof org.antlr.runtime3_3_0.NoViableAltException) {
			message = "Syntax error on token \"" + e.token.getText() + "\", check following tokens";
		} else if (e instanceof org.antlr.runtime3_3_0.EarlyExitException) {
			message = "Syntax error on token \"" + e.token.getText() + "\", delete this token";
		} else if (e instanceof org.antlr.runtime3_3_0.MismatchedSetException) {
			org.antlr.runtime3_3_0.MismatchedSetException mse = (org.antlr.runtime3_3_0.MismatchedSetException) e;
			message = "mismatched token: " + e.token + "; expecting set " + mse.expecting;
		} else if (e instanceof org.antlr.runtime3_3_0.MismatchedNotSetException) {
			org.antlr.runtime3_3_0.MismatchedNotSetException mse = (org.antlr.runtime3_3_0.MismatchedNotSetException) e;
			message = "mismatched token: " +  e.token + "; expecting set " + mse.expecting;
		} else if (e instanceof org.antlr.runtime3_3_0.FailedPredicateException) {
			org.antlr.runtime3_3_0.FailedPredicateException fpe = (org.antlr.runtime3_3_0.FailedPredicateException) e;
			message = "rule " + fpe.ruleName + " failed predicate: {" +  fpe.predicateText + "}?";
		}
		// the resource may be null if the parser is used for code completion
		final String finalMessage = message;
		if (e.token instanceof org.antlr.runtime3_3_0.CommonToken) {
			final org.antlr.runtime3_3_0.CommonToken ct = (org.antlr.runtime3_3_0.CommonToken) e.token;
			addErrorToResource(finalMessage, ct.getCharPositionInLine(), ct.getLine(), ct.getStartIndex(), ct.getStopIndex());
		} else {
			addErrorToResource(finalMessage, e.token.getCharPositionInLine(), e.token.getLine(), 1, 5);
		}
	}
	
	/**
	 * Translates errors thrown by the lexer into human readable messages.
	 */
	public void reportLexicalError(final org.antlr.runtime3_3_0.RecognitionException e)  {
		String message = "";
		if (e instanceof org.antlr.runtime3_3_0.MismatchedTokenException) {
			org.antlr.runtime3_3_0.MismatchedTokenException mte = (org.antlr.runtime3_3_0.MismatchedTokenException) e;
			message = "Syntax error on token \"" + ((char) e.c) + "\", \"" + (char) mte.expecting + "\" expected";
		} else if (e instanceof org.antlr.runtime3_3_0.NoViableAltException) {
			message = "Syntax error on token \"" + ((char) e.c) + "\", delete this token";
		} else if (e instanceof org.antlr.runtime3_3_0.EarlyExitException) {
			org.antlr.runtime3_3_0.EarlyExitException eee = (org.antlr.runtime3_3_0.EarlyExitException) e;
			message = "required (...)+ loop (decision=" + eee.decisionNumber + ") did not match anything; on line " + e.line + ":" + e.charPositionInLine + " char=" + ((char) e.c) + "'";
		} else if (e instanceof org.antlr.runtime3_3_0.MismatchedSetException) {
			org.antlr.runtime3_3_0.MismatchedSetException mse = (org.antlr.runtime3_3_0.MismatchedSetException) e;
			message = "mismatched char: '" + ((char) e.c) + "' on line " + e.line + ":" + e.charPositionInLine + "; expecting set " + mse.expecting;
		} else if (e instanceof org.antlr.runtime3_3_0.MismatchedNotSetException) {
			org.antlr.runtime3_3_0.MismatchedNotSetException mse = (org.antlr.runtime3_3_0.MismatchedNotSetException) e;
			message = "mismatched char: '" + ((char) e.c) + "' on line " + e.line + ":" + e.charPositionInLine + "; expecting set " + mse.expecting;
		} else if (e instanceof org.antlr.runtime3_3_0.MismatchedRangeException) {
			org.antlr.runtime3_3_0.MismatchedRangeException mre = (org.antlr.runtime3_3_0.MismatchedRangeException) e;
			message = "mismatched char: '" + ((char) e.c) + "' on line " + e.line + ":" + e.charPositionInLine + "; expecting set '" + (char) mre.a + "'..'" + (char) mre.b + "'";
		} else if (e instanceof org.antlr.runtime3_3_0.FailedPredicateException) {
			org.antlr.runtime3_3_0.FailedPredicateException fpe = (org.antlr.runtime3_3_0.FailedPredicateException) e;
			message = "rule " + fpe.ruleName + " failed predicate: {" + fpe.predicateText + "}?";
		}
		addErrorToResource(message, e.charPositionInLine, e.line, lexerExceptionsPosition.get(lexerExceptions.indexOf(e)), lexerExceptionsPosition.get(lexerExceptions.indexOf(e)));
	}
	
	protected void completedElement(Object object, boolean isContainment) {
		if (isContainment && !this.incompleteObjects.isEmpty()) {
			this.incompleteObjects.pop();
		}
		if (object instanceof org.eclipse.emf.ecore.EObject) {
			this.tokenIndexOfLastCompleteElement = getTokenStream().index();
			this.expectedElementsIndexOfLastCompleteElement = expectedElements.size() - 1;
		}
	}
	
}

start returns [ org.eclipse.emf.ecore.EObject element = null]
:
	{
		// follow set for start rule(s)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_0, 0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 0, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 0, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 0, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 0, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		expectedElementsIndexOfLastCompleteElement = 0;
	}
	(
		c0 = parse_org_sintef_thingml_ThingMLModel{ element = c0; }
	)
	EOF	{
		retrieveLayoutInformation(element, null, null, false);
	}
	
;

parse_org_sintef_thingml_ThingMLModel returns [org.sintef.thingml.ThingMLModel element = null]
@init{
}
:
	(
		(
			a0 = 'import' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThingMLModel();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_0_0_0_0_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_5, 1);
			}
			
			(
				a1 = STRING_LITERAL				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThingMLModel();
						incompleteObjects.push(element);
					}
					if (a1 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("STRING_LITERAL");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__IMPORTS), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
						}
						String resolved = (String) resolvedObject;
						org.sintef.thingml.ThingMLModel proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThingMLModel();
						collectHiddenTokens(element);
						registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.ThingMLModel, org.sintef.thingml.ThingMLModel>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getThingMLModelImportsReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__IMPORTS), resolved, proxy);
						if (proxy != null) {
							Object value = proxy;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__IMPORTS, value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_0_0_0_0_0_0_3, proxy, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, proxy);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_0, 2);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 2, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 2, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 2, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 2, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_0, 3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 3, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 3, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 3, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 3, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
	}
	
	(
		(
			(
				(
					a2_0 = parse_org_sintef_thingml_Message					{
						if (terminateParsing) {
							throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
						}
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThingMLModel();
							incompleteObjects.push(element);
						}
						if (a2_0 != null) {
							if (a2_0 != null) {
								Object value = a2_0;
								addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__MESSAGES, value);
								completedElement(value, true);
							}
							collectHiddenTokens(element);
							retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_0_0_0_1_0_0_1_0_0_0, a2_0, true);
							copyLocalizationInfos(a2_0, element);
						}
					}
				)
				{
					// expected elements (follow set)
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 4, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 4, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 4, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 4, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
				}
				
				
				|				(
					a3_0 = parse_org_sintef_thingml_Type					{
						if (terminateParsing) {
							throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
						}
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThingMLModel();
							incompleteObjects.push(element);
						}
						if (a3_0 != null) {
							if (a3_0 != null) {
								Object value = a3_0;
								addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__TYPES, value);
								completedElement(value, true);
							}
							collectHiddenTokens(element);
							retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_0_0_0_1_0_0_1_0_1_0, a3_0, true);
							copyLocalizationInfos(a3_0, element);
						}
					}
				)
				{
					// expected elements (follow set)
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 5, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 5, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 5, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 5, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
				}
				
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 6, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 6, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 6, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 6, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 7, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 7, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 7, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 7, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
	}
	
;

parse_org_sintef_thingml_Message returns [org.sintef.thingml.Message element = null]
@init{
}
:
	a0 = 'message' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_1_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 8);
	}
	
	(
		a1 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
				incompleteObjects.push(element);
			}
			if (a1 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MESSAGE__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_1_0_0_2, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_7, 9);
	}
	
	a2 = '(' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_1_0_0_3, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a2, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_8, 10, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_9, 10);
	}
	
	(
		(
			(
				a3_0 = parse_org_sintef_thingml_Parameter				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
						incompleteObjects.push(element);
					}
					if (a3_0 != null) {
						if (a3_0 != null) {
							Object value = a3_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.MESSAGE__PARAMETERS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_1_0_0_4_0_0_0, a3_0, true);
						copyLocalizationInfos(a3_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_9, 11);
			}
			
			(
				(
					a4 = ',' {
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
							incompleteObjects.push(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_1_0_0_4_0_0_1_0_0_0, null, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a4, element);
					}
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_8, 12, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
					}
					
					(
						a5_0 = parse_org_sintef_thingml_Parameter						{
							if (terminateParsing) {
								throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
							}
							if (element == null) {
								element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
								incompleteObjects.push(element);
							}
							if (a5_0 != null) {
								if (a5_0 != null) {
									Object value = a5_0;
									addObjectToList(element, org.sintef.thingml.ThingmlPackage.MESSAGE__PARAMETERS, value);
									completedElement(value, true);
								}
								collectHiddenTokens(element);
								retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_1_0_0_4_0_0_1_0_0_1, a5_0, true);
								copyLocalizationInfos(a5_0, element);
							}
						}
					)
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 13);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_9, 13);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_9, 14);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_9, 15);
	}
	
	a6 = ')' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_1_0_0_5, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a6, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 16);
	}
	
	(
		(
			(
				a7_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
						incompleteObjects.push(element);
					}
					if (a7_0 != null) {
						if (a7_0 != null) {
							Object value = a7_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.MESSAGE__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_1_0_0_6_0_0_0, a7_0, true);
						copyLocalizationInfos(a7_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 17, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 17);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 18, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 18);
	}
	
	a8 = ';' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_1_0_0_7, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a8, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 19, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 19, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 19, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 19, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
	}
	
;

parse_org_sintef_thingml_Thing returns [org.sintef.thingml.Thing element = null]
@init{
}
:
	a0 = 'thing' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_13, 20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 20);
	}
	
	(
		(
			(
				a1 = T_ASPECT				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
						incompleteObjects.push(element);
					}
					if (a1 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("T_ASPECT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__FRAGMENT), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
						}
						java.lang.Boolean resolved = (java.lang.Boolean)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__FRAGMENT), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_1_0_0_1, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 21);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 22);
	}
	
	(
		a2 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
				incompleteObjects.push(element);
			}
			if (a2 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_3, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 23, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 23);
	}
	
	(
		(
			a3 = 'includes' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_4_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 24);
			}
			
			(
				a4 = TEXT				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
						incompleteObjects.push(element);
					}
					if (a4 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a4.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__INCLUDES), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a4).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a4).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a4).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a4).getStopIndex());
						}
						String resolved = (String) resolvedObject;
						org.sintef.thingml.Thing proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
						collectHiddenTokens(element);
						registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Thing, org.sintef.thingml.Thing>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getThingIncludesReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__INCLUDES), resolved, proxy);
						if (proxy != null) {
							Object value = proxy;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING__INCLUDES, value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_4_0_0_3, proxy, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, element);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, proxy);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 25);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 25, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 25);
			}
			
			(
				(
					a5 = ',' {
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
							incompleteObjects.push(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_4_0_0_4_0_0_0, null, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
					}
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 26);
					}
					
					(
						a6 = TEXT						
						{
							if (terminateParsing) {
								throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
							}
							if (element == null) {
								element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
								incompleteObjects.push(element);
							}
							if (a6 != null) {
								org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
								tokenResolver.setOptions(getOptions());
								org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
								tokenResolver.resolve(a6.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__INCLUDES), result);
								Object resolvedObject = result.getResolvedToken();
								if (resolvedObject == null) {
									addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a6).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStopIndex());
								}
								String resolved = (String) resolvedObject;
								org.sintef.thingml.Thing proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
								collectHiddenTokens(element);
								registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Thing, org.sintef.thingml.Thing>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getThingIncludesReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.THING__INCLUDES), resolved, proxy);
								if (proxy != null) {
									Object value = proxy;
									addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING__INCLUDES, value);
									completedElement(value, false);
								}
								collectHiddenTokens(element);
								retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_4_0_0_4_0_0_2, proxy, true);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, element);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, proxy);
							}
						}
					)
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 27);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 27, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 27);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 28);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 28, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 28);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 29, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 29);
	}
	
	(
		(
			(
				a7_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
						incompleteObjects.push(element);
					}
					if (a7_0 != null) {
						if (a7_0 != null) {
							Object value = a7_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_5_0_0_0, a7_0, true);
						copyLocalizationInfos(a7_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 30, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 30);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 31, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 31);
	}
	
	a8 = '{' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_7, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a8, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 32, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 32, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 32, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 32, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 32, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 32, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 32);
	}
	
	(
		(
			(
				a9_0 = parse_org_sintef_thingml_Property				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
						incompleteObjects.push(element);
					}
					if (a9_0 != null) {
						if (a9_0 != null) {
							Object value = a9_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING__PROPERTIES, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_8_0_0_0, a9_0, true);
						copyLocalizationInfos(a9_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 33);
			}
			
			
			|			(
				a10_0 = parse_org_sintef_thingml_Port				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
						incompleteObjects.push(element);
					}
					if (a10_0 != null) {
						if (a10_0 != null) {
							Object value = a10_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING__PORTS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_8_0_1_0, a10_0, true);
						copyLocalizationInfos(a10_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 34);
			}
			
			
			|			(
				(
					a11_0 = parse_org_sintef_thingml_StateMachine					{
						if (terminateParsing) {
							throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
						}
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
							incompleteObjects.push(element);
						}
						if (a11_0 != null) {
							if (a11_0 != null) {
								Object value = a11_0;
								addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING__BEHAVIOUR, value);
								completedElement(value, true);
							}
							collectHiddenTokens(element);
							retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_8_0_2_0_0_0_0, a11_0, true);
							copyLocalizationInfos(a11_0, element);
						}
					}
				)
				{
					// expected elements (follow set)
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 35);
				}
				
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 36);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 37);
	}
	
	a12 = '}' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_10, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a12, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
	}
	
;

parse_org_sintef_thingml_Port returns [org.sintef.thingml.Port element = null]
@init{
}
:
	a0 = 'port' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_1, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 39);
	}
	
	(
		a1 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
				incompleteObjects.push(element);
			}
			if (a1 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_3, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 40, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_28, 40);
	}
	
	(
		(
			(
				a2_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
						incompleteObjects.push(element);
					}
					if (a2_0 != null) {
						if (a2_0 != null) {
							Object value = a2_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.PORT__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_4_0_0_0, a2_0, true);
						copyLocalizationInfos(a2_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 41, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_28, 41);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 42, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_28, 42);
	}
	
	a3 = '{' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_6, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_29, 43);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_30, 43);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 43);
	}
	
	(
		(
			a4 = 'receives' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_7_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a4, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_32, 44);
			}
			
			(
				a5 = TEXT				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
						incompleteObjects.push(element);
					}
					if (a5 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a5.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__RECEIVES), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a5).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a5).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a5).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a5).getStopIndex());
						}
						String resolved = (String) resolvedObject;
						org.sintef.thingml.Message proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
						collectHiddenTokens(element);
						registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Port, org.sintef.thingml.Message>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortReceivesReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__RECEIVES), resolved, proxy);
						if (proxy != null) {
							Object value = proxy;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.PORT__RECEIVES, value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_7_0_0_2, proxy, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a5, element);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a5, proxy);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_33, 45);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_29, 45);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_30, 45);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 45);
			}
			
			(
				(
					a6 = ',' {
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
							incompleteObjects.push(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_7_0_0_3_0_0_0, null, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a6, element);
					}
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_34, 46);
					}
					
					(
						a7 = TEXT						
						{
							if (terminateParsing) {
								throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
							}
							if (element == null) {
								element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
								incompleteObjects.push(element);
							}
							if (a7 != null) {
								org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
								tokenResolver.setOptions(getOptions());
								org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
								tokenResolver.resolve(a7.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__RECEIVES), result);
								Object resolvedObject = result.getResolvedToken();
								if (resolvedObject == null) {
									addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a7).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a7).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a7).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a7).getStopIndex());
								}
								String resolved = (String) resolvedObject;
								org.sintef.thingml.Message proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
								collectHiddenTokens(element);
								registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Port, org.sintef.thingml.Message>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortReceivesReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__RECEIVES), resolved, proxy);
								if (proxy != null) {
									Object value = proxy;
									addObjectToList(element, org.sintef.thingml.ThingmlPackage.PORT__RECEIVES, value);
									completedElement(value, false);
								}
								collectHiddenTokens(element);
								retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_7_0_0_3_0_0_2, proxy, true);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a7, element);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a7, proxy);
							}
						}
					)
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_33, 47);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_29, 47);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_30, 47);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 47);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_33, 48);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_29, 48);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_30, 48);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 48);
			}
			
			
			|			a8 = 'sends' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_7_0_1_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a8, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_35, 49);
			}
			
			(
				a9 = TEXT				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
						incompleteObjects.push(element);
					}
					if (a9 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a9.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__SENDS), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a9).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a9).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a9).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a9).getStopIndex());
						}
						String resolved = (String) resolvedObject;
						org.sintef.thingml.Message proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
						collectHiddenTokens(element);
						registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Port, org.sintef.thingml.Message>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortSendsReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__SENDS), resolved, proxy);
						if (proxy != null) {
							Object value = proxy;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.PORT__SENDS, value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_7_0_1_2, proxy, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a9, element);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a9, proxy);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_36, 50);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_29, 50);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_30, 50);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 50);
			}
			
			(
				(
					a10 = ',' {
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
							incompleteObjects.push(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_7_0_1_3_0_0_0, null, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a10, element);
					}
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_37, 51);
					}
					
					(
						a11 = TEXT						
						{
							if (terminateParsing) {
								throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
							}
							if (element == null) {
								element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
								incompleteObjects.push(element);
							}
							if (a11 != null) {
								org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
								tokenResolver.setOptions(getOptions());
								org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
								tokenResolver.resolve(a11.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__SENDS), result);
								Object resolvedObject = result.getResolvedToken();
								if (resolvedObject == null) {
									addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a11).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a11).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a11).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a11).getStopIndex());
								}
								String resolved = (String) resolvedObject;
								org.sintef.thingml.Message proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
								collectHiddenTokens(element);
								registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Port, org.sintef.thingml.Message>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortSendsReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PORT__SENDS), resolved, proxy);
								if (proxy != null) {
									Object value = proxy;
									addObjectToList(element, org.sintef.thingml.ThingmlPackage.PORT__SENDS, value);
									completedElement(value, false);
								}
								collectHiddenTokens(element);
								retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_7_0_1_3_0_0_2, proxy, true);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a11, element);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a11, proxy);
							}
						}
					)
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_36, 52);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_29, 52);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_30, 52);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 52);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_36, 53);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_29, 53);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_30, 53);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 53);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_29, 54);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_30, 54);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 54);
	}
	
	a12 = '}' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_9, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a12, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 55, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 55, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 55, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 55, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 55, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 55, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 55);
	}
	
;

parse_org_sintef_thingml_Property returns [org.sintef.thingml.Property element = null]
@init{
}
:
	(
		(
			(
				a0 = T_READONLY				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
						incompleteObjects.push(element);
					}
					if (a0 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("T_READONLY");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__CHANGEABLE), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
						}
						java.lang.Boolean resolved = (java.lang.Boolean)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__CHANGEABLE), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_1_0_0_0, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 56);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 57);
	}
	
	a1 = 'property' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_38, 58);
	}
	
	(
		a2 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
				incompleteObjects.push(element);
			}
			if (a2 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_4, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_39, 59);
	}
	
	a3 = ':' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_5, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_40, 60);
	}
	
	(
		a4 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
				incompleteObjects.push(element);
			}
			if (a4 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a4.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__TYPE), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a4).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a4).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a4).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a4).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.Type proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Property, org.sintef.thingml.Type>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPropertyTypeReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__TYPE), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__TYPE), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_6, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_41, 61);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 61, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 61, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 61, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 61, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 61, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 61, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 61, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 61);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 61);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 61);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 61, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 61, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 61, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 61, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 61);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 61);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 61);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 61, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 61);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 61);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 61);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 61);
	}
	
	(
		(
			a5 = '[' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_7_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_56, 62);
			}
			
			(
				a6 = INTEGER_LITERAL				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
						incompleteObjects.push(element);
					}
					if (a6 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a6.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__LOWER_BOUND), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a6).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStopIndex());
						}
						java.lang.Integer resolved = (java.lang.Integer)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__LOWER_BOUND), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_7_0_0_1, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 63);
			}
			
			a7 = '..' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_7_0_0_2, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a7, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 64);
			}
			
			(
				a8 = INTEGER_LITERAL				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
						incompleteObjects.push(element);
					}
					if (a8 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a8.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__UPPER_BOUND), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a8).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a8).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a8).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a8).getStopIndex());
						}
						java.lang.Integer resolved = (java.lang.Integer)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__UPPER_BOUND), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_7_0_0_3, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a8, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 65);
			}
			
			a9 = ']' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_7_0_0_4, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a9, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 66, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 66, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 66, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 66, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 66, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 66, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 66, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 66);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 66);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 66);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 66, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 66, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 66, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 66, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 66);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 66);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 66);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 66, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 66);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 66);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 66);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 66);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 67, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 67, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 67, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 67, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 67, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 67, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 67, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 67);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 67);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 67);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 67, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 67, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 67, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 67, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 67);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 67);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 67);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 67, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 67);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 67);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 67);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 67);
	}
	
	(
		(
			(
				a10_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
						incompleteObjects.push(element);
					}
					if (a10_0 != null) {
						if (a10_0 != null) {
							Object value = a10_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.PROPERTY__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_8_0_0_0, a10_0, true);
						copyLocalizationInfos(a10_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 68, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 68, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 68, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 68, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 68, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 68, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 68, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 68);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 68);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 68);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 68, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 68, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 68, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 68, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 68);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 68);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 68);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 68, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 68);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 68);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 68);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 68);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 69, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 69, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 69, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 69, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 69, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 69, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 69, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 69);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 69);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 69);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 69, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 69, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 69, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 69, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 69);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 69);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 69);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 69, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 69);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 69);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 69);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 69);
	}
	
	|//derived choice rules for sub-classes: 
	
	c0 = parse_org_sintef_thingml_Dictionary{ element = c0; /* this is a subclass or primitive expression choice */ }
	
;

parse_org_sintef_thingml_Dictionary returns [org.sintef.thingml.Dictionary element = null]
@init{
}
:
	(
		(
			(
				a0 = T_READONLY				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDictionary();
						incompleteObjects.push(element);
					}
					if (a0 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("T_READONLY");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__CHANGEABLE), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
						}
						java.lang.Boolean resolved = (java.lang.Boolean)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__CHANGEABLE), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_1_0_0_0, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 70);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 71);
	}
	
	a1 = 'dictionary' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDictionary();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 72);
	}
	
	(
		a2 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDictionary();
				incompleteObjects.push(element);
			}
			if (a2 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_4, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 73);
	}
	
	a3 = ':' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDictionary();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_5, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 74);
	}
	
	(
		a4 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDictionary();
				incompleteObjects.push(element);
			}
			if (a4 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a4.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__INDEX_TYPE), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a4).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a4).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a4).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a4).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.Type proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Dictionary, org.sintef.thingml.Type>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getDictionaryIndexTypeReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__INDEX_TYPE), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__INDEX_TYPE), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_6, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 75);
	}
	
	a5 = '->' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDictionary();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_7, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 76);
	}
	
	(
		a6 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDictionary();
				incompleteObjects.push(element);
			}
			if (a6 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a6.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__TYPE), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a6).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.Type proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Property, org.sintef.thingml.Type>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPropertyTypeReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__TYPE), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__TYPE), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_8, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 77);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 77, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 77, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 77, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 77, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 77, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 77, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 77, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 77);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 77);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 77);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 77, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 77, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 77, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 77, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 77);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 77);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 77);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 77, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 77);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 77);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 77);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 77);
	}
	
	(
		(
			a7 = '[' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDictionary();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_9_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a7, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 78);
			}
			
			(
				a8 = INTEGER_LITERAL				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDictionary();
						incompleteObjects.push(element);
					}
					if (a8 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a8.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__LOWER_BOUND), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a8).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a8).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a8).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a8).getStopIndex());
						}
						java.lang.Integer resolved = (java.lang.Integer)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__LOWER_BOUND), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_9_0_0_1, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a8, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 79);
			}
			
			a9 = '..' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDictionary();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_9_0_0_2, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a9, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 80);
			}
			
			(
				a10 = INTEGER_LITERAL				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDictionary();
						incompleteObjects.push(element);
					}
					if (a10 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a10.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__UPPER_BOUND), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a10).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a10).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a10).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a10).getStopIndex());
						}
						java.lang.Integer resolved = (java.lang.Integer)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DICTIONARY__UPPER_BOUND), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_9_0_0_3, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a10, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 81);
			}
			
			a11 = ']' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDictionary();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_9_0_0_4, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a11, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 82, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 82, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 82, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 82, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 82, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 82, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 82, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 82);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 82);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 82);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 82, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 82, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 82, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 82, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 82);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 82);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 82);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 82, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 82);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 82);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 82);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 82);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 83, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 83, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 83, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 83, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 83, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 83, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 83, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 83);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 83);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 83);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 83, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 83, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 83, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 83, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 83);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 83);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 83);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 83, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 83);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 83);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 83);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 83);
	}
	
	(
		(
			(
				a12_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDictionary();
						incompleteObjects.push(element);
					}
					if (a12_0 != null) {
						if (a12_0 != null) {
							Object value = a12_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.DICTIONARY__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_10_0_0_0, a12_0, true);
						copyLocalizationInfos(a12_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 84, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 84, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 84, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 84, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 84, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 84, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 84, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 84);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 84);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 84);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 84, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 84, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 84, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 84, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 84);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 84);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 84);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 84, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 84);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 84);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 84);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 84);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 85, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 85, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 85, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 85, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 85, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 85, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 85, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 85);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 85);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 85);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 85, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 85, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 85, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 85, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 85);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 85);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 85);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 85, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 85);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 85);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 85);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 85);
	}
	
;

parse_org_sintef_thingml_Parameter returns [org.sintef.thingml.Parameter element = null]
@init{
}
:
	(
		a0 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createParameter();
				incompleteObjects.push(element);
			}
			if (a0 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_6_0_0_0, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 86);
	}
	
	a1 = ':' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createParameter();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_6_0_0_1, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 87);
	}
	
	(
		a2 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createParameter();
				incompleteObjects.push(element);
			}
			if (a2 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__TYPE), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.Type proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Parameter, org.sintef.thingml.Type>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getParameterTypeReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__TYPE), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARAMETER__TYPE), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_6_0_0_2, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 88);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_9, 88);
	}
	
;

parse_org_sintef_thingml_PrimitiveType returns [org.sintef.thingml.PrimitiveType element = null]
@init{
}
:
	a0 = 'datatype' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPrimitiveType();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_7_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 89);
	}
	
	(
		a1 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPrimitiveType();
				incompleteObjects.push(element);
			}
			if (a1 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PRIMITIVE_TYPE__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PRIMITIVE_TYPE__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_7_0_0_2, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 90);
	}
	
	(
		(
			(
				a2_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPrimitiveType();
						incompleteObjects.push(element);
					}
					if (a2_0 != null) {
						if (a2_0 != null) {
							Object value = a2_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.PRIMITIVE_TYPE__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_7_0_0_3_0_0_0, a2_0, true);
						copyLocalizationInfos(a2_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 91);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 92, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 92);
	}
	
	a3 = ';' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPrimitiveType();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_7_0_0_4, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 93, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 93, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 93, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 93, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
	}
	
;

parse_org_sintef_thingml_Enumeration returns [org.sintef.thingml.Enumeration element = null]
@init{
}
:
	a0 = 'enumeration' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEnumeration();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_8_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 94);
	}
	
	(
		a1 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEnumeration();
				incompleteObjects.push(element);
			}
			if (a1 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_8_0_0_2, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 95, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 95);
	}
	
	(
		(
			(
				a2_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEnumeration();
						incompleteObjects.push(element);
					}
					if (a2_0 != null) {
						if (a2_0 != null) {
							Object value = a2_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.ENUMERATION__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_8_0_0_3_0_0_0, a2_0, true);
						copyLocalizationInfos(a2_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 96, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 96);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 97, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 97);
	}
	
	a3 = '{' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEnumeration();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_8_0_0_5, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 98, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 98);
	}
	
	(
		(
			(
				a4_0 = parse_org_sintef_thingml_EnumerationLiteral				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEnumeration();
						incompleteObjects.push(element);
					}
					if (a4_0 != null) {
						if (a4_0 != null) {
							Object value = a4_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.ENUMERATION__LITERALS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_8_0_0_6_0_0_0, a4_0, true);
						copyLocalizationInfos(a4_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 99, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 99);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 100, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 100);
	}
	
	a5 = '}' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEnumeration();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_8_0_0_7, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 101, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 101, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 101, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 101, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
	}
	
;

parse_org_sintef_thingml_EnumerationLiteral returns [org.sintef.thingml.EnumerationLiteral element = null]
@init{
}
:
	(
		a0 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEnumerationLiteral();
				incompleteObjects.push(element);
			}
			if (a0 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION_LITERAL__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUMERATION_LITERAL__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_9_0_0_1, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 102, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 102, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 102);
	}
	
	(
		(
			(
				a1_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEnumerationLiteral();
						incompleteObjects.push(element);
					}
					if (a1_0 != null) {
						if (a1_0 != null) {
							Object value = a1_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.ENUMERATION_LITERAL__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_9_0_0_2_0_0_0, a1_0, true);
						copyLocalizationInfos(a1_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 103, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 103, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 103);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 104, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 104, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 104);
	}
	
;

parse_org_sintef_thingml_PlatformAnnotation returns [org.sintef.thingml.PlatformAnnotation element = null]
@init{
}
:
	(
		a0 = ANNOTATION		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPlatformAnnotation();
				incompleteObjects.push(element);
			}
			if (a0 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("ANNOTATION");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLATFORM_ANNOTATION__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLATFORM_ANNOTATION__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_10_0_0_1, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 105);
	}
	
	(
		a1 = STRING_LITERAL		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPlatformAnnotation();
				incompleteObjects.push(element);
			}
			if (a1 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("STRING_LITERAL");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLATFORM_ANNOTATION__VALUE), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLATFORM_ANNOTATION__VALUE), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_10_0_0_3, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 106, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_28, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 106, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 106, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 106, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 106, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 106, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 106, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 106, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 106, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 106, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 106, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 106);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 106);
	}
	
;

parse_org_sintef_thingml_StateMachine returns [org.sintef.thingml.StateMachine element = null]
@init{
}
:
	a0 = 'statechart' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_1, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_86, 107);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_87, 107);
	}
	
	(
		(
			(
				a1 = TEXT				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
						incompleteObjects.push(element);
					}
					if (a1 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__NAME), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
						}
						java.lang.String resolved = (java.lang.String)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__NAME), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_2_0_0_1, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_87, 108);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_87, 109);
	}
	
	a2 = 'init' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_4, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a2, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_88, 110);
	}
	
	(
		a3 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
				incompleteObjects.push(element);
			}
			if (a3 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a3.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__INITIAL), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a3).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a3).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a3).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a3).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.State proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Region, org.sintef.thingml.State>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getRegionInitialReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__INITIAL), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__INITIAL), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_6, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a3, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a3, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_89, 111);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 111, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 111);
	}
	
	(
		(
			a4 = 'keeps' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_7_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a4, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_91, 112);
			}
			
			(
				a5 = T_HISTORY				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
						incompleteObjects.push(element);
					}
					if (a5 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("T_HISTORY");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a5.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__HISTORY), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a5).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a5).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a5).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a5).getStopIndex());
						}
						java.lang.Boolean resolved = (java.lang.Boolean)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__HISTORY), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_7_0_0_2, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a5, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 113, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 113);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 114, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 114);
	}
	
	(
		(
			(
				a6_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
						incompleteObjects.push(element);
					}
					if (a6_0 != null) {
						if (a6_0 != null) {
							Object value = a6_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.STATE_MACHINE__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_8_0_0_0, a6_0, true);
						copyLocalizationInfos(a6_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 115, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 115);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 116, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 116);
	}
	
	a7 = '{' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_10, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a7, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 117, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 117, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 117, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 117, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 117);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 117);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 117, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 117, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 117, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 117, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 117, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 117);
	}
	
	(
		(
			(
				a8_0 = parse_org_sintef_thingml_Property				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
						incompleteObjects.push(element);
					}
					if (a8_0 != null) {
						if (a8_0 != null) {
							Object value = a8_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.STATE_MACHINE__PROPERTIES, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_11_0_0_1, a8_0, true);
						copyLocalizationInfos(a8_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 118, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 118, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 118, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 118, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 118);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 118);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 118, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 118, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 118, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 118, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 118, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 118);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 119, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 119, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 119, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 119, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 119);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 119);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 119, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 119, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 119, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 119, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 119, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 119);
	}
	
	(
		(
			a9 = 'on' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_12_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a9, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_92, 120);
			}
			
			a10 = 'entry' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_12_0_0_3, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a10, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 121, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 121, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 121, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 121, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 121, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 121, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 121, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 121, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
			}
			
			(
				a11_0 = parse_org_sintef_thingml_Action				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
						incompleteObjects.push(element);
					}
					if (a11_0 != null) {
						if (a11_0 != null) {
							Object value = a11_0;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__ENTRY), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_12_0_0_5, a11_0, true);
						copyLocalizationInfos(a11_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 122);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 122, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 122, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 122, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 122, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 122, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 122);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 123);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 123, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 123, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 123, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 123, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 123, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 123);
	}
	
	(
		(
			a12 = 'on' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_13_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a12, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_101, 124);
			}
			
			a13 = 'exit' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_13_0_0_3, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a13, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
			}
			
			(
				a14_0 = parse_org_sintef_thingml_Action				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
						incompleteObjects.push(element);
					}
					if (a14_0 != null) {
						if (a14_0 != null) {
							Object value = a14_0;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE_MACHINE__EXIT), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_13_0_0_5, a14_0, true);
						copyLocalizationInfos(a14_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 126, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 126, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 126, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 126, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 126, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 126);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 127, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 127, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 127, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 127, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 127, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 127);
	}
	
	(
		(
			(
				(
					a15_0 = parse_org_sintef_thingml_State					{
						if (terminateParsing) {
							throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
						}
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
							incompleteObjects.push(element);
						}
						if (a15_0 != null) {
							if (a15_0 != null) {
								Object value = a15_0;
								addObjectToList(element, org.sintef.thingml.ThingmlPackage.STATE_MACHINE__SUBSTATE, value);
								completedElement(value, true);
							}
							collectHiddenTokens(element);
							retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_14_0_0_0_0_0_1, a15_0, true);
							copyLocalizationInfos(a15_0, element);
						}
					}
				)
				{
					// expected elements (follow set)
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 128);
				}
				
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 129);
			}
			
			
			|			(
				a16_0 = parse_org_sintef_thingml_InternalTransition				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
						incompleteObjects.push(element);
					}
					if (a16_0 != null) {
						if (a16_0 != null) {
							Object value = a16_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.STATE_MACHINE__INTERNAL, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_14_0_1_0, a16_0, true);
						copyLocalizationInfos(a16_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 130, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 130, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 130, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 130, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 130, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 130);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 131, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 131, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 131, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 131, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 131, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 131);
	}
	
	(
		(
			(
				a17_0 = parse_org_sintef_thingml_ParallelRegion				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
						incompleteObjects.push(element);
					}
					if (a17_0 != null) {
						if (a17_0 != null) {
							Object value = a17_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.STATE_MACHINE__REGION, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_15_0_0_1, a17_0, true);
						copyLocalizationInfos(a17_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 132, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 132);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 133, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 133);
	}
	
	a18 = '}' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStateMachine();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_11_0_0_17, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a18, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 134);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 134);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 134);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 134);
	}
	
;

parse_org_sintef_thingml_State returns [org.sintef.thingml.State element = null]
@init{
}
:
	a0 = 'state' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_103, 135);
	}
	
	(
		a1 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
				incompleteObjects.push(element);
			}
			if (a1 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12_0_0_2, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 136, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_104, 136);
	}
	
	(
		(
			(
				a2_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
						incompleteObjects.push(element);
					}
					if (a2_0 != null) {
						if (a2_0 != null) {
							Object value = a2_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.STATE__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12_0_0_3_0_0_0, a2_0, true);
						copyLocalizationInfos(a2_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 137, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_104, 137);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 138, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_104, 138);
	}
	
	a3 = '{' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12_0_0_5, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 139, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 139, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 139, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 139, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 139);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 139);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 139, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 139, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 139);
	}
	
	(
		(
			(
				a4_0 = parse_org_sintef_thingml_Property				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
						incompleteObjects.push(element);
					}
					if (a4_0 != null) {
						if (a4_0 != null) {
							Object value = a4_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.STATE__PROPERTIES, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12_0_0_6_0_0_1, a4_0, true);
						copyLocalizationInfos(a4_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 140);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 140);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 140);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 141);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 141);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 141);
	}
	
	(
		(
			a5 = 'on' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12_0_0_7_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_105, 142);
			}
			
			a6 = 'entry' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12_0_0_7_0_0_3, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a6, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 143, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 143, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 143, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 143, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 143, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 143, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 143, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 143, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
			}
			
			(
				a7_0 = parse_org_sintef_thingml_Action				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
						incompleteObjects.push(element);
					}
					if (a7_0 != null) {
						if (a7_0 != null) {
							Object value = a7_0;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__ENTRY), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12_0_0_7_0_0_4, a7_0, true);
						copyLocalizationInfos(a7_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 144);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 144, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 144, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 144);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 145);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 145, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 145, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 145);
	}
	
	(
		(
			a8 = 'on' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12_0_0_8_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a8, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_106, 146);
			}
			
			a9 = 'exit' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12_0_0_8_0_0_2, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a9, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
			}
			
			(
				a10_0 = parse_org_sintef_thingml_Action				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
						incompleteObjects.push(element);
					}
					if (a10_0 != null) {
						if (a10_0 != null) {
							Object value = a10_0;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STATE__EXIT), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12_0_0_8_0_0_3, a10_0, true);
						copyLocalizationInfos(a10_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 148, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 148, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 148);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 149, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 149, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 149);
	}
	
	(
		(
			(
				a11_0 = parse_org_sintef_thingml_Transition				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
						incompleteObjects.push(element);
					}
					if (a11_0 != null) {
						if (a11_0 != null) {
							Object value = a11_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.STATE__OUTGOING, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12_0_0_9_0_0_0, a11_0, true);
						copyLocalizationInfos(a11_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 150, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 150, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 150);
			}
			
			
			|			(
				a12_0 = parse_org_sintef_thingml_InternalTransition				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
						incompleteObjects.push(element);
					}
					if (a12_0 != null) {
						if (a12_0 != null) {
							Object value = a12_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.STATE__INTERNAL, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12_0_0_9_0_1_0, a12_0, true);
						copyLocalizationInfos(a12_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 151, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 151, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 151);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 152, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 152, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 152);
	}
	
	a13 = '}' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_12_0_0_11, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a13, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 153, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 153, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 153, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 153, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 153, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 153);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 153, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 153);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 153);
	}
	
	|//derived choice rules for sub-classes: 
	
	c0 = parse_org_sintef_thingml_StateMachine{ element = c0; /* this is a subclass or primitive expression choice */ }
	|	c1 = parse_org_sintef_thingml_CompositeState{ element = c1; /* this is a subclass or primitive expression choice */ }
	
;

parse_org_sintef_thingml_CompositeState returns [org.sintef.thingml.CompositeState element = null]
@init{
}
:
	a0 = 'composite' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_107, 154);
	}
	
	a1 = 'state' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 155);
	}
	
	(
		a2 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
				incompleteObjects.push(element);
			}
			if (a2 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_4, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 156);
	}
	
	a3 = 'init' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_6, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 157);
	}
	
	(
		a4 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
				incompleteObjects.push(element);
			}
			if (a4 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a4.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__INITIAL), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a4).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a4).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a4).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a4).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.State proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Region, org.sintef.thingml.State>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getRegionInitialReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__INITIAL), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__INITIAL), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_8, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 158);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 158, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 158);
	}
	
	(
		(
			a5 = 'keeps' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_9_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 159);
			}
			
			(
				a6 = T_HISTORY				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
						incompleteObjects.push(element);
					}
					if (a6 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("T_HISTORY");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a6.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__HISTORY), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a6).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStopIndex());
						}
						java.lang.Boolean resolved = (java.lang.Boolean)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__HISTORY), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_9_0_0_2, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 160, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 160);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 161, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 161);
	}
	
	(
		(
			(
				a7_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
						incompleteObjects.push(element);
					}
					if (a7_0 != null) {
						if (a7_0 != null) {
							Object value = a7_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_10_0_0_0, a7_0, true);
						copyLocalizationInfos(a7_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 162, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 162);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 163, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 163);
	}
	
	a8 = '{' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_12, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a8, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 164, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 164, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 164, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 164, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 164);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 164);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 164, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 164, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 164, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 164, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 164, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 164, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 164);
	}
	
	(
		(
			(
				a9_0 = parse_org_sintef_thingml_Property				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
						incompleteObjects.push(element);
					}
					if (a9_0 != null) {
						if (a9_0 != null) {
							Object value = a9_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__PROPERTIES, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_13_0_0_1, a9_0, true);
						copyLocalizationInfos(a9_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 165, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 165, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 165, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 165, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 165);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 165);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 165, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 165, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 165, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 165, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 165, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 165, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 165);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 166, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 166, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 166, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 166, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 166);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 166);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 166, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 166, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 166, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 166, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 166, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 166, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 166);
	}
	
	(
		(
			a10 = 'on' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_14_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a10, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 167);
			}
			
			a11 = 'entry' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_14_0_0_3, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a11, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 168, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 168, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 168, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 168, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 168, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 168, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 168, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 168, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
			}
			
			(
				a12_0 = parse_org_sintef_thingml_Action				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
						incompleteObjects.push(element);
					}
					if (a12_0 != null) {
						if (a12_0 != null) {
							Object value = a12_0;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__ENTRY), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_14_0_0_5, a12_0, true);
						copyLocalizationInfos(a12_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 169);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 169, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 169, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 169, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 169, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 169, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 169, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 169);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 170);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 170);
	}
	
	(
		(
			a13 = 'on' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_15_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a13, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 171);
			}
			
			a14 = 'exit' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_15_0_0_3, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a14, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
			}
			
			(
				a15_0 = parse_org_sintef_thingml_Action				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
						incompleteObjects.push(element);
					}
					if (a15_0 != null) {
						if (a15_0 != null) {
							Object value = a15_0;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__EXIT), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_15_0_0_5, a15_0, true);
						copyLocalizationInfos(a15_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 173, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 173, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 173, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 173, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 173, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 173, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 173);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 174, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 174, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 174, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 174, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 174, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 174, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 174);
	}
	
	(
		(
			(
				a16_0 = parse_org_sintef_thingml_Transition				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
						incompleteObjects.push(element);
					}
					if (a16_0 != null) {
						if (a16_0 != null) {
							Object value = a16_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__OUTGOING, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_16_0_0_0, a16_0, true);
						copyLocalizationInfos(a16_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 175);
			}
			
			
			|			(
				a17_0 = parse_org_sintef_thingml_InternalTransition				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
						incompleteObjects.push(element);
					}
					if (a17_0 != null) {
						if (a17_0 != null) {
							Object value = a17_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__INTERNAL, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_16_0_1_0, a17_0, true);
						copyLocalizationInfos(a17_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 176);
			}
			
			
			|			(
				(
					a18_0 = parse_org_sintef_thingml_State					{
						if (terminateParsing) {
							throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
						}
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
							incompleteObjects.push(element);
						}
						if (a18_0 != null) {
							if (a18_0 != null) {
								Object value = a18_0;
								addObjectToList(element, org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__SUBSTATE, value);
								completedElement(value, true);
							}
							collectHiddenTokens(element);
							retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_16_0_2_0_0_0_1, a18_0, true);
							copyLocalizationInfos(a18_0, element);
						}
					}
				)
				{
					// expected elements (follow set)
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 177, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 177, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 177, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 177, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 177, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 177, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 177);
				}
				
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 178, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 178, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 178, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 178, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 178, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 178, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 178);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 179);
	}
	
	(
		(
			(
				a19_0 = parse_org_sintef_thingml_ParallelRegion				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
						incompleteObjects.push(element);
					}
					if (a19_0 != null) {
						if (a19_0 != null) {
							Object value = a19_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.COMPOSITE_STATE__REGION, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_17_0_0_1, a19_0, true);
						copyLocalizationInfos(a19_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 180, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 180);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 181, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 181);
	}
	
	a20 = '}' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createCompositeState();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_13_0_0_19, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a20, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 182);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 182);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 182);
	}
	
	|//derived choice rules for sub-classes: 
	
	c0 = parse_org_sintef_thingml_StateMachine{ element = c0; /* this is a subclass or primitive expression choice */ }
	
;

parse_org_sintef_thingml_ParallelRegion returns [org.sintef.thingml.ParallelRegion element = null]
@init{
}
:
	a0 = 'region' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createParallelRegion();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_14_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 183);
	}
	
	(
		a1 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createParallelRegion();
				incompleteObjects.push(element);
			}
			if (a1 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_14_0_0_2, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 184);
	}
	
	a2 = 'init' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createParallelRegion();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_14_0_0_4, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a2, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 185);
	}
	
	(
		a3 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createParallelRegion();
				incompleteObjects.push(element);
			}
			if (a3 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a3.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__INITIAL), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a3).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a3).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a3).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a3).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.State proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Region, org.sintef.thingml.State>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getRegionInitialReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__INITIAL), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__INITIAL), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_14_0_0_6, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a3, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a3, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_119, 186);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 186, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 186);
	}
	
	(
		(
			a4 = 'keeps' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createParallelRegion();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_14_0_0_7_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a4, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_120, 187);
			}
			
			(
				a5 = T_HISTORY				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createParallelRegion();
						incompleteObjects.push(element);
					}
					if (a5 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("T_HISTORY");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a5.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__HISTORY), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a5).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a5).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a5).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a5).getStopIndex());
						}
						java.lang.Boolean resolved = (java.lang.Boolean)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__HISTORY), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_14_0_0_7_0_0_2, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a5, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 188, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 188);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 189, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 189);
	}
	
	(
		(
			(
				a6_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createParallelRegion();
						incompleteObjects.push(element);
					}
					if (a6_0 != null) {
						if (a6_0 != null) {
							Object value = a6_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_14_0_0_8_0_0_0, a6_0, true);
						copyLocalizationInfos(a6_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 190, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 190);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 191, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 191);
	}
	
	a7 = '{' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createParallelRegion();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_14_0_0_10, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a7, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 192, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 192, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 192, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 192);
	}
	
	(
		(
			(
				a8_0 = parse_org_sintef_thingml_State				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createParallelRegion();
						incompleteObjects.push(element);
					}
					if (a8_0 != null) {
						if (a8_0 != null) {
							Object value = a8_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.PARALLEL_REGION__SUBSTATE, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_14_0_0_11_0_0_1, a8_0, true);
						copyLocalizationInfos(a8_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 193, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 193, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 193, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 193);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 194, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 194, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 194, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 194);
	}
	
	a9 = '}' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createParallelRegion();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_14_0_0_13, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a9, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 195, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 195);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 195);
	}
	
;

parse_org_sintef_thingml_Transition returns [org.sintef.thingml.Transition element = null]
@init{
}
:
	a0 = 'transition' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_1, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_121, 196);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_122, 196);
	}
	
	(
		(
			(
				a1 = TEXT				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
						incompleteObjects.push(element);
					}
					if (a1 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__NAME), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
						}
						java.lang.String resolved = (java.lang.String)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__NAME), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_2_0_0_1, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_122, 197);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_122, 198);
	}
	
	a2 = '->' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_4, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a2, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_123, 199);
	}
	
	(
		a3 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
				incompleteObjects.push(element);
			}
			if (a3 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a3.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__TARGET), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a3).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a3).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a3).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a3).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.State proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createState();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Transition, org.sintef.thingml.State>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getTransitionTargetReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__TARGET), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__TARGET), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_6, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a3, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a3, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 200, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 200);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 200);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 200);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 200, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 200, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 200);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 200, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 200, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 200, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 200, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 200);
	}
	
	(
		(
			(
				a4_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
						incompleteObjects.push(element);
					}
					if (a4_0 != null) {
						if (a4_0 != null) {
							Object value = a4_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.TRANSITION__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_7_0_0_0, a4_0, true);
						copyLocalizationInfos(a4_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 201, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 201);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 201);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 201);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 201, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 201, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 201);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 201, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 201, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 201, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 201, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 201);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 202, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 202);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 202);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 202);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 202, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 202, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 202);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 202, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 202, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 202, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 202, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 202);
	}
	
	(
		(
			a5 = 'event' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_8_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_124, 203, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_125, 203, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
			}
			
			(
				a6_0 = parse_org_sintef_thingml_Event				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
						incompleteObjects.push(element);
					}
					if (a6_0 != null) {
						if (a6_0 != null) {
							Object value = a6_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.TRANSITION__EVENT, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_8_0_0_3, a6_0, true);
						copyLocalizationInfos(a6_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 204);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 204);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 204);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 204, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 204, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 204);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 204, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 204, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 204, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 204, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 204);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 205);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 205);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 205);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 205, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 205, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 205);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 205, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 205, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 205, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 205, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 205);
	}
	
	(
		(
			a7 = 'guard' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_9_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a7, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
			}
			
			(
				a8_0 = parse_org_sintef_thingml_Expression				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
						incompleteObjects.push(element);
					}
					if (a8_0 != null) {
						if (a8_0 != null) {
							Object value = a8_0;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__GUARD), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_9_0_0_3, a8_0, true);
						copyLocalizationInfos(a8_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 207);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 207);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 207);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 208);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 208);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 208);
	}
	
	(
		(
			a9 = 'action' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_10_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a9, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
			}
			
			(
				a10_0 = parse_org_sintef_thingml_Action				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
						incompleteObjects.push(element);
					}
					if (a10_0 != null) {
						if (a10_0 != null) {
							Object value = a10_0;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__ACTION), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_10_0_0_3, a10_0, true);
						copyLocalizationInfos(a10_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 210, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 210, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 210);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 210, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 210, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 210, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 210, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 210);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 211);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 211);
	}
	
;

parse_org_sintef_thingml_InternalTransition returns [org.sintef.thingml.InternalTransition element = null]
@init{
}
:
	a0 = 'internal' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInternalTransition();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_16_0_0_1, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_135, 212);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 212);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 212);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 212);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 212);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 212);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 212);
	}
	
	(
		(
			(
				a1 = TEXT				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInternalTransition();
						incompleteObjects.push(element);
					}
					if (a1 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__NAME), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
						}
						java.lang.String resolved = (java.lang.String)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__NAME), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_16_0_0_2_0_0_1, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 213);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 213);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 213);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 213);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 213);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 213);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 214);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 214);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 214);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 214);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 214);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 214);
	}
	
	(
		(
			(
				a2_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInternalTransition();
						incompleteObjects.push(element);
					}
					if (a2_0 != null) {
						if (a2_0 != null) {
							Object value = a2_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_16_0_0_3_0_0_0, a2_0, true);
						copyLocalizationInfos(a2_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 215);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 215);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 215);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 215);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 215);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 215);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 216);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 216);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 216);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 216);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 216);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 216);
	}
	
	(
		(
			a3 = 'event' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInternalTransition();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_16_0_0_4_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_124, 217, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_125, 217, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
			}
			
			(
				a4_0 = parse_org_sintef_thingml_Event				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInternalTransition();
						incompleteObjects.push(element);
					}
					if (a4_0 != null) {
						if (a4_0 != null) {
							Object value = a4_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__EVENT, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_16_0_0_4_0_0_3, a4_0, true);
						copyLocalizationInfos(a4_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 218);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 218);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 218);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 218);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 218);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 218);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 219);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 219);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 219);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 219);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 219);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 219);
	}
	
	(
		(
			a5 = 'guard' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInternalTransition();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_16_0_0_5_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
			}
			
			(
				a6_0 = parse_org_sintef_thingml_Expression				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInternalTransition();
						incompleteObjects.push(element);
					}
					if (a6_0 != null) {
						if (a6_0 != null) {
							Object value = a6_0;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__GUARD), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_16_0_0_5_0_0_3, a6_0, true);
						copyLocalizationInfos(a6_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 221);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 221);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 221);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 221);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 222);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 222);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 222);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 222);
	}
	
	(
		(
			a7 = 'action' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInternalTransition();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_16_0_0_6_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a7, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
			}
			
			(
				a8_0 = parse_org_sintef_thingml_Action				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInternalTransition();
						incompleteObjects.push(element);
					}
					if (a8_0 != null) {
						if (a8_0 != null) {
							Object value = a8_0;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTERNAL_TRANSITION__ACTION), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_16_0_0_6_0_0_3, a8_0, true);
						copyLocalizationInfos(a8_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 224);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 224);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 224);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 225);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 225);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 225);
	}
	
;

parse_org_sintef_thingml_ReceiveMessage returns [org.sintef.thingml.ReceiveMessage element = null]
@init{
}
:
	(
		(
			(
				a0 = TEXT				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createReceiveMessage();
						incompleteObjects.push(element);
					}
					if (a0 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__NAME), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
						}
						java.lang.String resolved = (java.lang.String)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__NAME), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_17_0_0_0_0_0_0, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_136, 226);
			}
			
			a1 = ':' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createReceiveMessage();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_17_0_0_0_0_0_2, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_125, 227);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_125, 228);
	}
	
	(
		a2 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createReceiveMessage();
				incompleteObjects.push(element);
			}
			if (a2 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__PORT), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.Port proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.ReceiveMessage, org.sintef.thingml.Port>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getReceiveMessagePortReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__PORT), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__PORT), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_17_0_0_1, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_137, 229);
	}
	
	a3 = '?' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createReceiveMessage();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_17_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_138, 230);
	}
	
	(
		a4 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createReceiveMessage();
				incompleteObjects.push(element);
			}
			if (a4 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a4.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__MESSAGE), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a4).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a4).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a4).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a4).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.Message proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.ReceiveMessage, org.sintef.thingml.Message>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getReceiveMessageMessageReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__MESSAGE), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.RECEIVE_MESSAGE__MESSAGE), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_17_0_0_3, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 231);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 231);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 231);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 231);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 231);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 231);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 231);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 231);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 231);
	}
	
;

parse_org_sintef_thingml_SendAction returns [org.sintef.thingml.SendAction element = null]
@init{
}
:
	(
		a0 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createSendAction();
				incompleteObjects.push(element);
			}
			if (a0 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__PORT), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.Port proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPort();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.SendAction, org.sintef.thingml.Port>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getSendActionPortReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__PORT), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__PORT), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_18_0_0_0, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_139, 232);
	}
	
	a1 = '!' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createSendAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_18_0_0_1, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_140, 233);
	}
	
	(
		a2 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createSendAction();
				incompleteObjects.push(element);
			}
			if (a2 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__MESSAGE), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.Message proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.SendAction, org.sintef.thingml.Message>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getSendActionMessageReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__MESSAGE), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__MESSAGE), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_18_0_0_2, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_141, 234);
	}
	
	a3 = '(' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createSendAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_18_0_0_3, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 235);
	}
	
	(
		(
			(
				a4_0 = parse_org_sintef_thingml_Expression				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createSendAction();
						incompleteObjects.push(element);
					}
					if (a4_0 != null) {
						if (a4_0 != null) {
							Object value = a4_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.SEND_ACTION__PARAMETERS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_18_0_0_4_0_0_0, a4_0, true);
						copyLocalizationInfos(a4_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 236);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 236);
			}
			
			(
				(
					a5 = ',' {
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createSendAction();
							incompleteObjects.push(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_18_0_0_4_0_0_1_0_0_0, null, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
					}
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
					}
					
					(
						a6_0 = parse_org_sintef_thingml_Expression						{
							if (terminateParsing) {
								throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
							}
							if (element == null) {
								element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createSendAction();
								incompleteObjects.push(element);
							}
							if (a6_0 != null) {
								if (a6_0 != null) {
									Object value = a6_0;
									addObjectToList(element, org.sintef.thingml.ThingmlPackage.SEND_ACTION__PARAMETERS, value);
									completedElement(value, true);
								}
								collectHiddenTokens(element);
								retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_18_0_0_4_0_0_1_0_0_2, a6_0, true);
								copyLocalizationInfos(a6_0, element);
							}
						}
					)
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 238);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 238);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 239);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 239);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 240);
	}
	
	a7 = ')' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createSendAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_18_0_0_5, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a7, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 241);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 241);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 241);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 241);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 241);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 241);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 241);
	}
	
;

parse_org_sintef_thingml_PropertyAssignment returns [org.sintef.thingml.PropertyAssignment element = null]
@init{
}
:
	(
		a0 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPropertyAssignment();
				incompleteObjects.push(element);
			}
			if (a0 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGNMENT__PROPERTY), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.Property proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.PropertyAssignment, org.sintef.thingml.Property>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPropertyAssignmentPropertyReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGNMENT__PROPERTY), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGNMENT__PROPERTY), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_0, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_145, 242);
	}
	
	a1 = '=' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPropertyAssignment();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 243, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 243, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 243, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 243, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 243, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 243, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 243, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 243, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 243, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
	}
	
	(
		a2_0 = parse_org_sintef_thingml_Expression		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPropertyAssignment();
				incompleteObjects.push(element);
			}
			if (a2_0 != null) {
				if (a2_0 != null) {
					Object value = a2_0;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGNMENT__EXPRESSION), value);
					completedElement(value, true);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_4, a2_0, true);
				copyLocalizationInfos(a2_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 244);
	}
	
;

parse_org_sintef_thingml_ActionBlock returns [org.sintef.thingml.ActionBlock element = null]
@init{
}
:
	a0 = 'do' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createActionBlock();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_20_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 245, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 245, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 245, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 245, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 245, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 245, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 245, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 245, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 245);
	}
	
	(
		(
			(
				a1_0 = parse_org_sintef_thingml_Action				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createActionBlock();
						incompleteObjects.push(element);
					}
					if (a1_0 != null) {
						if (a1_0 != null) {
							Object value = a1_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.ACTION_BLOCK__ACTIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_20_0_0_1_0_0_1, a1_0, true);
						copyLocalizationInfos(a1_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 246, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 246, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 246, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 246, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 246, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 246, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 246, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 246, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 246);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 247);
	}
	
	a2 = 'end' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createActionBlock();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_20_0_0_3, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a2, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 248);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 248);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 248);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 248);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 248);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 248);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 248);
	}
	
;

parse_org_sintef_thingml_ExternStatement returns [org.sintef.thingml.ExternStatement element = null]
@init{
}
:
	(
		a0 = STRING_EXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createExternStatement();
				incompleteObjects.push(element);
			}
			if (a0 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("STRING_EXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXTERN_STATEMENT__STATEMENT), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXTERN_STATEMENT__STATEMENT), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_21_0_0_0, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_146, 249);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 249);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 249);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 249);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 249);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 249);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 249);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 249);
	}
	
	(
		(
			a1 = '&' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createExternStatement();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_21_0_0_1_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 250, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 250, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 250, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 250, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 250, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 250, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 250, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 250, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 250, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
			}
			
			(
				a2_0 = parse_org_sintef_thingml_Expression				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createExternStatement();
						incompleteObjects.push(element);
					}
					if (a2_0 != null) {
						if (a2_0 != null) {
							Object value = a2_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.EXTERN_STATEMENT__SEGMENTS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_21_0_0_1_0_0_1, a2_0, true);
						copyLocalizationInfos(a2_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_146, 251);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 251);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 251);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 251);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 251);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 251);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 251);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 251);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_146, 252);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 252);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 252);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 252);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 252);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 252);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 252);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 252);
	}
	
;

parse_org_sintef_thingml_ConditionalAction returns [org.sintef.thingml.ConditionalAction element = null]
@init{
}
:
	a0 = 'if' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConditionalAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_22_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 253);
	}
	
	a1 = '(' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConditionalAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_22_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 254, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 254, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 254, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 254, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 254, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 254, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 254, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 254, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 254, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
	}
	
	(
		a2_0 = parse_org_sintef_thingml_Expression		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConditionalAction();
				incompleteObjects.push(element);
			}
			if (a2_0 != null) {
				if (a2_0 != null) {
					Object value = a2_0;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONDITIONAL_ACTION__CONDITION), value);
					completedElement(value, true);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_22_0_0_4, a2_0, true);
				copyLocalizationInfos(a2_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 255);
	}
	
	a3 = ')' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConditionalAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_22_0_0_6, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 256, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 256, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 256, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 256, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 256, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 256, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 256, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 256, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
	}
	
	(
		a4_0 = parse_org_sintef_thingml_Action		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConditionalAction();
				incompleteObjects.push(element);
			}
			if (a4_0 != null) {
				if (a4_0 != null) {
					Object value = a4_0;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONDITIONAL_ACTION__ACTION), value);
					completedElement(value, true);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_22_0_0_8, a4_0, true);
				copyLocalizationInfos(a4_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 257);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 257);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 257);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 257);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 257);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 257);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 257);
	}
	
;

parse_org_sintef_thingml_LoopAction returns [org.sintef.thingml.LoopAction element = null]
@init{
}
:
	a0 = 'while' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createLoopAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_23_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 258);
	}
	
	a1 = '(' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createLoopAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_23_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 259, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 259, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 259, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 259, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 259, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 259, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 259, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 259, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 259, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
	}
	
	(
		a2_0 = parse_org_sintef_thingml_Expression		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createLoopAction();
				incompleteObjects.push(element);
			}
			if (a2_0 != null) {
				if (a2_0 != null) {
					Object value = a2_0;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOOP_ACTION__CONDITION), value);
					completedElement(value, true);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_23_0_0_4, a2_0, true);
				copyLocalizationInfos(a2_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 260);
	}
	
	a3 = ')' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createLoopAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_23_0_0_6, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 261, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 261, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 261, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 261, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 261, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 261, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 261, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 261, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
	}
	
	(
		a4_0 = parse_org_sintef_thingml_Action		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createLoopAction();
				incompleteObjects.push(element);
			}
			if (a4_0 != null) {
				if (a4_0 != null) {
					Object value = a4_0;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOOP_ACTION__ACTION), value);
					completedElement(value, true);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_23_0_0_8, a4_0, true);
				copyLocalizationInfos(a4_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 262);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 262);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 262);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 262);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 262);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 262);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 262);
	}
	
;

parse_org_sintef_thingml_PrintAction returns [org.sintef.thingml.PrintAction element = null]
@init{
}
:
	a0 = 'print' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPrintAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_24_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
	}
	
	(
		a1_0 = parse_org_sintef_thingml_Expression		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPrintAction();
				incompleteObjects.push(element);
			}
			if (a1_0 != null) {
				if (a1_0 != null) {
					Object value = a1_0;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PRINT_ACTION__MSG), value);
					completedElement(value, true);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_24_0_0_2, a1_0, true);
				copyLocalizationInfos(a1_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 264);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 264);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 264);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 264);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 264);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 264);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 264);
	}
	
;

parse_org_sintef_thingml_ErrorAction returns [org.sintef.thingml.ErrorAction element = null]
@init{
}
:
	a0 = 'error' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createErrorAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_25_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
	}
	
	(
		a1_0 = parse_org_sintef_thingml_Expression		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createErrorAction();
				incompleteObjects.push(element);
			}
			if (a1_0 != null) {
				if (a1_0 != null) {
					Object value = a1_0;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ERROR_ACTION__MSG), value);
					completedElement(value, true);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_25_0_0_2, a1_0, true);
				copyLocalizationInfos(a1_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 266);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 266);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 266);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 266);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 266);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 266);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 266);
	}
	
;

parseop_Expression_level_1 returns [org.sintef.thingml.Expression element = null]
@init{
}
:
	leftArg = parseop_Expression_level_2	((
		()
		{ element = null; }
		a0 = 'or' {
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createOrExpression();
				incompleteObjects.push(element);
			}
			collectHiddenTokens(element);
			retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_26_0_0_2, null, true);
			copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
		}
		{
			// expected elements (follow set)
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 267, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 267, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 267, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 267, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 267, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 267, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 267, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 267, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 267, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		}
		
		rightArg = parseop_Expression_level_2		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createOrExpression();
				incompleteObjects.push(element);
			}
			if (leftArg != null) {
				if (leftArg != null) {
					Object value = leftArg;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.OR_EXPRESSION__LHS), value);
					completedElement(value, true);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_26_0_0_0, leftArg, true);
				copyLocalizationInfos(leftArg, element);
			}
		}
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createOrExpression();
				incompleteObjects.push(element);
			}
			if (rightArg != null) {
				if (rightArg != null) {
					Object value = rightArg;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.OR_EXPRESSION__RHS), value);
					completedElement(value, true);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_26_0_0_4, rightArg, true);
				copyLocalizationInfos(rightArg, element);
			}
		}
		{ leftArg = element; /* this may become an argument in the next iteration */ }
	)+ | /* epsilon */ { element = leftArg; }
	
)
;

parseop_Expression_level_2 returns [org.sintef.thingml.Expression element = null]
@init{
}
:
leftArg = parseop_Expression_level_3((
	()
	{ element = null; }
	a0 = 'and' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createAndExpression();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_27_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 268, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 268, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 268, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 268, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 268, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 268, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 268, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 268, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 268, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	}
	
	rightArg = parseop_Expression_level_3	{
		if (terminateParsing) {
			throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
		}
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createAndExpression();
			incompleteObjects.push(element);
		}
		if (leftArg != null) {
			if (leftArg != null) {
				Object value = leftArg;
				element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.AND_EXPRESSION__LHS), value);
				completedElement(value, true);
			}
			collectHiddenTokens(element);
			retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_27_0_0_0, leftArg, true);
			copyLocalizationInfos(leftArg, element);
		}
	}
	{
		if (terminateParsing) {
			throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
		}
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createAndExpression();
			incompleteObjects.push(element);
		}
		if (rightArg != null) {
			if (rightArg != null) {
				Object value = rightArg;
				element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.AND_EXPRESSION__RHS), value);
				completedElement(value, true);
			}
			collectHiddenTokens(element);
			retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_27_0_0_4, rightArg, true);
			copyLocalizationInfos(rightArg, element);
		}
	}
	{ leftArg = element; /* this may become an argument in the next iteration */ }
)+ | /* epsilon */ { element = leftArg; }

)
;

parseop_Expression_level_3 returns [org.sintef.thingml.Expression element = null]
@init{
}
:
leftArg = parseop_Expression_level_4((
()
{ element = null; }
a0 = '<' {
	if (element == null) {
		element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createLowerExpression();
		incompleteObjects.push(element);
	}
	collectHiddenTokens(element);
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_28_0_0_2, null, true);
	copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
	// expected elements (follow set)
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 269, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 269, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 269, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 269, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 269, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 269, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 269, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 269, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 269, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
}

rightArg = parseop_Expression_level_4{
	if (terminateParsing) {
		throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
	}
	if (element == null) {
		element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createLowerExpression();
		incompleteObjects.push(element);
	}
	if (leftArg != null) {
		if (leftArg != null) {
			Object value = leftArg;
			element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOWER_EXPRESSION__LHS), value);
			completedElement(value, true);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_28_0_0_0, leftArg, true);
		copyLocalizationInfos(leftArg, element);
	}
}
{
	if (terminateParsing) {
		throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
	}
	if (element == null) {
		element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createLowerExpression();
		incompleteObjects.push(element);
	}
	if (rightArg != null) {
		if (rightArg != null) {
			Object value = rightArg;
			element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.LOWER_EXPRESSION__RHS), value);
			completedElement(value, true);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_28_0_0_4, rightArg, true);
		copyLocalizationInfos(rightArg, element);
	}
}
{ leftArg = element; /* this may become an argument in the next iteration */ }
|
()
{ element = null; }
a0 = '>' {
	if (element == null) {
		element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createGreaterExpression();
		incompleteObjects.push(element);
	}
	collectHiddenTokens(element);
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_29_0_0_2, null, true);
	copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
	// expected elements (follow set)
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 270, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 270, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 270, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 270, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 270, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 270, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 270, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 270, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 270, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
}

rightArg = parseop_Expression_level_4{
	if (terminateParsing) {
		throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
	}
	if (element == null) {
		element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createGreaterExpression();
		incompleteObjects.push(element);
	}
	if (leftArg != null) {
		if (leftArg != null) {
			Object value = leftArg;
			element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.GREATER_EXPRESSION__LHS), value);
			completedElement(value, true);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_29_0_0_0, leftArg, true);
		copyLocalizationInfos(leftArg, element);
	}
}
{
	if (terminateParsing) {
		throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
	}
	if (element == null) {
		element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createGreaterExpression();
		incompleteObjects.push(element);
	}
	if (rightArg != null) {
		if (rightArg != null) {
			Object value = rightArg;
			element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.GREATER_EXPRESSION__RHS), value);
			completedElement(value, true);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_29_0_0_4, rightArg, true);
		copyLocalizationInfos(rightArg, element);
	}
}
{ leftArg = element; /* this may become an argument in the next iteration */ }
|
()
{ element = null; }
a0 = '==' {
	if (element == null) {
		element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEqualsExpression();
		incompleteObjects.push(element);
	}
	collectHiddenTokens(element);
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_30_0_0_2, null, true);
	copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
	// expected elements (follow set)
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 271, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 271, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 271, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 271, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 271, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 271, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 271, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 271, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 271, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
}

rightArg = parseop_Expression_level_4{
	if (terminateParsing) {
		throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
	}
	if (element == null) {
		element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEqualsExpression();
		incompleteObjects.push(element);
	}
	if (leftArg != null) {
		if (leftArg != null) {
			Object value = leftArg;
			element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EQUALS_EXPRESSION__LHS), value);
			completedElement(value, true);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_30_0_0_0, leftArg, true);
		copyLocalizationInfos(leftArg, element);
	}
}
{
	if (terminateParsing) {
		throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
	}
	if (element == null) {
		element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEqualsExpression();
		incompleteObjects.push(element);
	}
	if (rightArg != null) {
		if (rightArg != null) {
			Object value = rightArg;
			element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EQUALS_EXPRESSION__RHS), value);
			completedElement(value, true);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_30_0_0_4, rightArg, true);
		copyLocalizationInfos(rightArg, element);
	}
}
{ leftArg = element; /* this may become an argument in the next iteration */ }
)+ | /* epsilon */ { element = leftArg; }

)
;

parseop_Expression_level_4 returns [org.sintef.thingml.Expression element = null]
@init{
}
:
leftArg = parseop_Expression_level_5((
()
{ element = null; }
a0 = '+' {
if (element == null) {
	element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPlusExpression();
	incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_31_0_0_2, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 272, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 272, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 272, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 272, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 272, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 272, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 272, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 272, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 272, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
}

rightArg = parseop_Expression_level_5{
if (terminateParsing) {
	throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
	element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPlusExpression();
	incompleteObjects.push(element);
}
if (leftArg != null) {
	if (leftArg != null) {
		Object value = leftArg;
		element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLUS_EXPRESSION__LHS), value);
		completedElement(value, true);
	}
	collectHiddenTokens(element);
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_31_0_0_0, leftArg, true);
	copyLocalizationInfos(leftArg, element);
}
}
{
if (terminateParsing) {
	throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
	element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPlusExpression();
	incompleteObjects.push(element);
}
if (rightArg != null) {
	if (rightArg != null) {
		Object value = rightArg;
		element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PLUS_EXPRESSION__RHS), value);
		completedElement(value, true);
	}
	collectHiddenTokens(element);
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_31_0_0_4, rightArg, true);
	copyLocalizationInfos(rightArg, element);
}
}
{ leftArg = element; /* this may become an argument in the next iteration */ }
|
()
{ element = null; }
a0 = '-' {
if (element == null) {
	element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMinusExpression();
	incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_32_0_0_2, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 273, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 273, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 273, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 273, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 273, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 273, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 273, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 273, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 273, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
}

rightArg = parseop_Expression_level_5{
if (terminateParsing) {
	throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
	element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMinusExpression();
	incompleteObjects.push(element);
}
if (leftArg != null) {
	if (leftArg != null) {
		Object value = leftArg;
		element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MINUS_EXPRESSION__LHS), value);
		completedElement(value, true);
	}
	collectHiddenTokens(element);
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_32_0_0_0, leftArg, true);
	copyLocalizationInfos(leftArg, element);
}
}
{
if (terminateParsing) {
	throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
	element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMinusExpression();
	incompleteObjects.push(element);
}
if (rightArg != null) {
	if (rightArg != null) {
		Object value = rightArg;
		element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MINUS_EXPRESSION__RHS), value);
		completedElement(value, true);
	}
	collectHiddenTokens(element);
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_32_0_0_4, rightArg, true);
	copyLocalizationInfos(rightArg, element);
}
}
{ leftArg = element; /* this may become an argument in the next iteration */ }
)+ | /* epsilon */ { element = leftArg; }

)
;

parseop_Expression_level_5 returns [org.sintef.thingml.Expression element = null]
@init{
}
:
leftArg = parseop_Expression_level_6((
()
{ element = null; }
a0 = '*' {
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTimesExpression();
incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_33_0_0_2, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 274, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 274, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 274, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 274, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 274, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 274, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 274, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 274, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 274, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
}

rightArg = parseop_Expression_level_6{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTimesExpression();
incompleteObjects.push(element);
}
if (leftArg != null) {
if (leftArg != null) {
	Object value = leftArg;
	element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TIMES_EXPRESSION__LHS), value);
	completedElement(value, true);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_33_0_0_0, leftArg, true);
copyLocalizationInfos(leftArg, element);
}
}
{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTimesExpression();
incompleteObjects.push(element);
}
if (rightArg != null) {
if (rightArg != null) {
	Object value = rightArg;
	element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TIMES_EXPRESSION__RHS), value);
	completedElement(value, true);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_33_0_0_4, rightArg, true);
copyLocalizationInfos(rightArg, element);
}
}
{ leftArg = element; /* this may become an argument in the next iteration */ }
|
()
{ element = null; }
a0 = '/' {
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDivExpression();
incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_34_0_0_2, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 275, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 275, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 275, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 275, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 275, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 275, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 275, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 275, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 275, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
}

rightArg = parseop_Expression_level_6{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDivExpression();
incompleteObjects.push(element);
}
if (leftArg != null) {
if (leftArg != null) {
	Object value = leftArg;
	element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DIV_EXPRESSION__LHS), value);
	completedElement(value, true);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_34_0_0_0, leftArg, true);
copyLocalizationInfos(leftArg, element);
}
}
{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createDivExpression();
incompleteObjects.push(element);
}
if (rightArg != null) {
if (rightArg != null) {
	Object value = rightArg;
	element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.DIV_EXPRESSION__RHS), value);
	completedElement(value, true);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_34_0_0_4, rightArg, true);
copyLocalizationInfos(rightArg, element);
}
}
{ leftArg = element; /* this may become an argument in the next iteration */ }
|
()
{ element = null; }
a0 = '\u0025' {
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createModExpression();
incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_35_0_0_2, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 276, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 276, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 276, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 276, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 276, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 276, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 276, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 276, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 276, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
}

rightArg = parseop_Expression_level_6{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createModExpression();
incompleteObjects.push(element);
}
if (leftArg != null) {
if (leftArg != null) {
	Object value = leftArg;
	element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MOD_EXPRESSION__LHS), value);
	completedElement(value, true);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_35_0_0_0, leftArg, true);
copyLocalizationInfos(leftArg, element);
}
}
{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createModExpression();
incompleteObjects.push(element);
}
if (rightArg != null) {
if (rightArg != null) {
	Object value = rightArg;
	element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.MOD_EXPRESSION__RHS), value);
	completedElement(value, true);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_35_0_0_4, rightArg, true);
copyLocalizationInfos(rightArg, element);
}
}
{ leftArg = element; /* this may become an argument in the next iteration */ }
)+ | /* epsilon */ { element = leftArg; }

)
;

parseop_Expression_level_6 returns [org.sintef.thingml.Expression element = null]
@init{
}
:
a0 = '-' {
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createUnaryMinus();
incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_36_0_0_0, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 277, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 277, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 277, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 277, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 277, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 277, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 277, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 277, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 277, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
}

arg = parseop_Expression_level_8{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createUnaryMinus();
incompleteObjects.push(element);
}
if (arg != null) {
if (arg != null) {
Object value = arg;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.UNARY_MINUS__TERM), value);
completedElement(value, true);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_36_0_0_2, arg, true);
copyLocalizationInfos(arg, element);
}
}
|
a0 = 'not' {
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createNotExpression();
incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_37_0_0_0, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 278, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 278, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 278, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 278, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 278, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 278, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 278, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 278, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 278, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
}

arg = parseop_Expression_level_8{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createNotExpression();
incompleteObjects.push(element);
}
if (arg != null) {
if (arg != null) {
Object value = arg;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.NOT_EXPRESSION__TERM), value);
completedElement(value, true);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_37_0_0_2, arg, true);
copyLocalizationInfos(arg, element);
}
}
|

arg = parseop_Expression_level_8{ element = arg; }
;

parseop_Expression_level_8 returns [org.sintef.thingml.Expression element = null]
@init{
}
:
c0 = parse_org_sintef_thingml_EventReference{ element = c0; /* this is a subclass or primitive expression choice */ }
|c1 = parse_org_sintef_thingml_ExpressionGroup{ element = c1; /* this is a subclass or primitive expression choice */ }
|c2 = parse_org_sintef_thingml_PropertyReference{ element = c2; /* this is a subclass or primitive expression choice */ }
|c3 = parse_org_sintef_thingml_IntegerLitteral{ element = c3; /* this is a subclass or primitive expression choice */ }
|c4 = parse_org_sintef_thingml_StringLitteral{ element = c4; /* this is a subclass or primitive expression choice */ }
|c5 = parse_org_sintef_thingml_BooleanLitteral{ element = c5; /* this is a subclass or primitive expression choice */ }
|c6 = parse_org_sintef_thingml_ExternExpression{ element = c6; /* this is a subclass or primitive expression choice */ }
;

parse_org_sintef_thingml_EventReference returns [org.sintef.thingml.EventReference element = null]
@init{
}
:
(
a0 = TEXT
{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEventReference();
incompleteObjects.push(element);
}
if (a0 != null) {
org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
tokenResolver.setOptions(getOptions());
org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EVENT_REFERENCE__MSG_REF), result);
Object resolvedObject = result.getResolvedToken();
if (resolvedObject == null) {
addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
}
String resolved = (String) resolvedObject;
org.sintef.thingml.ReceiveMessage proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createReceiveMessage();
collectHiddenTokens(element);
registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.EventReference, org.sintef.thingml.ReceiveMessage>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getEventReferenceMsgRefReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EVENT_REFERENCE__MSG_REF), resolved, proxy);
if (proxy != null) {
Object value = proxy;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EVENT_REFERENCE__MSG_REF), value);
completedElement(value, false);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_38_0_0_0, proxy, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, proxy);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_161, 279);
}

a1 = '.' {
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEventReference();
incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_38_0_0_1, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_162, 280);
}

(
a2 = TEXT
{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEventReference();
incompleteObjects.push(element);
}
if (a2 != null) {
org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
tokenResolver.setOptions(getOptions());
org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EVENT_REFERENCE__PARAM_REF), result);
Object resolvedObject = result.getResolvedToken();
if (resolvedObject == null) {
addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStopIndex());
}
String resolved = (String) resolvedObject;
org.sintef.thingml.Parameter proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createParameter();
collectHiddenTokens(element);
registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.EventReference, org.sintef.thingml.Parameter>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getEventReferenceParamRefReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EVENT_REFERENCE__PARAM_REF), resolved, proxy);
if (proxy != null) {
Object value = proxy;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EVENT_REFERENCE__PARAM_REF), value);
completedElement(value, false);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_38_0_0_2, proxy, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, proxy);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 281, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 281, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 281, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 281, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 281, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 281, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 281, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 281, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 281, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 281, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 281, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 281, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 281, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 281, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_146, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_153, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_155, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_156, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_158, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_159, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_163, 281);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_164, 281);
}

;

parse_org_sintef_thingml_ExpressionGroup returns [org.sintef.thingml.ExpressionGroup element = null]
@init{
}
:
a0 = '(' {
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createExpressionGroup();
incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_39_0_0_0, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 282, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 282, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 282, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 282, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 282, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 282, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 282, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 282, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 282, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
}

(
a1_0 = parse_org_sintef_thingml_Expression{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createExpressionGroup();
incompleteObjects.push(element);
}
if (a1_0 != null) {
if (a1_0 != null) {
Object value = a1_0;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXPRESSION_GROUP__EXP), value);
completedElement(value, true);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_39_0_0_1, a1_0, true);
copyLocalizationInfos(a1_0, element);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_163, 283);
}

a2 = ')' {
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createExpressionGroup();
incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_39_0_0_2, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a2, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_146, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_153, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_155, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_156, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_158, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_159, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_163, 284);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_164, 284);
}

;

parse_org_sintef_thingml_PropertyReference returns [org.sintef.thingml.PropertyReference element = null]
@init{
}
:
(
a0 = TEXT
{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPropertyReference();
incompleteObjects.push(element);
}
if (a0 != null) {
org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
tokenResolver.setOptions(getOptions());
org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_REFERENCE__PROPERTY), result);
Object resolvedObject = result.getResolvedToken();
if (resolvedObject == null) {
addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
}
String resolved = (String) resolvedObject;
org.sintef.thingml.Property proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
collectHiddenTokens(element);
registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.PropertyReference, org.sintef.thingml.Property>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPropertyReferencePropertyReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_REFERENCE__PROPERTY), resolved, proxy);
if (proxy != null) {
Object value = proxy;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_REFERENCE__PROPERTY), value);
completedElement(value, false);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_40_0_0_0, proxy, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, proxy);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_146, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_153, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_155, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_156, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_158, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_159, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_163, 285);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_164, 285);
}

;

parse_org_sintef_thingml_IntegerLitteral returns [org.sintef.thingml.IntegerLitteral element = null]
@init{
}
:
(
a0 = INTEGER_LITERAL
{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createIntegerLitteral();
incompleteObjects.push(element);
}
if (a0 != null) {
org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
tokenResolver.setOptions(getOptions());
org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTEGER_LITTERAL__INT_VALUE), result);
Object resolvedObject = result.getResolvedToken();
if (resolvedObject == null) {
addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
}
java.lang.Integer resolved = (java.lang.Integer)resolvedObject;
if (resolved != null) {
Object value = resolved;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTEGER_LITTERAL__INT_VALUE), value);
completedElement(value, false);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_41_0_0_0, resolved, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_146, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_153, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_155, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_156, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_158, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_159, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_163, 286);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_164, 286);
}

;

parse_org_sintef_thingml_StringLitteral returns [org.sintef.thingml.StringLitteral element = null]
@init{
}
:
(
a0 = STRING_LITERAL
{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStringLitteral();
incompleteObjects.push(element);
}
if (a0 != null) {
org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("STRING_LITERAL");
tokenResolver.setOptions(getOptions());
org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STRING_LITTERAL__STRING_VALUE), result);
Object resolvedObject = result.getResolvedToken();
if (resolvedObject == null) {
addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
}
java.lang.String resolved = (java.lang.String)resolvedObject;
if (resolved != null) {
Object value = resolved;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STRING_LITTERAL__STRING_VALUE), value);
completedElement(value, false);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_42_0_0_0, resolved, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 287, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 287, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 287, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 287, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 287, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 287, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 287, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 287, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 287, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 287, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 287, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 287, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 287, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 287, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_146, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_153, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_155, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_156, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_158, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_159, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_163, 287);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_164, 287);
}

;

parse_org_sintef_thingml_BooleanLitteral returns [org.sintef.thingml.BooleanLitteral element = null]
@init{
}
:
(
a0 = BOOLEAN_LITERAL
{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createBooleanLitteral();
incompleteObjects.push(element);
}
if (a0 != null) {
org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("BOOLEAN_LITERAL");
tokenResolver.setOptions(getOptions());
org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.BOOLEAN_LITTERAL__BOOL_VALUE), result);
Object resolvedObject = result.getResolvedToken();
if (resolvedObject == null) {
addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
}
java.lang.Boolean resolved = (java.lang.Boolean)resolvedObject;
if (resolved != null) {
Object value = resolved;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.BOOLEAN_LITTERAL__BOOL_VALUE), value);
completedElement(value, false);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_43_0_0_0, resolved, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 288, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 288, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 288, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 288, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 288, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 288, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 288, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 288, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 288, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 288, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 288, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 288, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 288, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 288, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_146, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_153, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_155, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_156, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_158, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_159, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_163, 288);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_164, 288);
}

;

parse_org_sintef_thingml_ExternExpression returns [org.sintef.thingml.ExternExpression element = null]
@init{
}
:
(
a0 = STRING_EXT
{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createExternExpression();
incompleteObjects.push(element);
}
if (a0 != null) {
org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("STRING_EXT");
tokenResolver.setOptions(getOptions());
org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXTERN_EXPRESSION__EXPRESSION), result);
Object resolvedObject = result.getResolvedToken();
if (resolvedObject == null) {
addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
}
java.lang.String resolved = (java.lang.String)resolvedObject;
if (resolved != null) {
Object value = resolved;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.EXTERN_EXPRESSION__EXPRESSION), value);
completedElement(value, false);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_44_0_0_0, resolved, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_164, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_146, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_153, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_155, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_156, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_158, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_159, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 289);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_163, 289);
}

(
(
a1 = '&' {
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createExternExpression();
incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_44_0_0_1_0_0_0, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
}

(
a2_0 = parse_org_sintef_thingml_Expression{
if (terminateParsing) {
	throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
	element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createExternExpression();
	incompleteObjects.push(element);
}
if (a2_0 != null) {
	if (a2_0 != null) {
		Object value = a2_0;
		addObjectToList(element, org.sintef.thingml.ThingmlPackage.EXTERN_EXPRESSION__SEGMENTS, value);
		completedElement(value, true);
	}
	collectHiddenTokens(element);
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_44_0_0_1_0_0_1, a2_0, true);
	copyLocalizationInfos(a2_0, element);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_164, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_146, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_153, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_155, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_156, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_158, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_159, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 291);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_163, 291);
}

)

)*{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_164, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_146, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_153, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_155, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_156, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_158, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_159, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 292);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_163, 292);
}

;

parse_org_sintef_thingml_Type returns [org.sintef.thingml.Type element = null]
:
c0 = parse_org_sintef_thingml_Thing{ element = c0; /* this is a subclass or primitive expression choice */ }
|c1 = parse_org_sintef_thingml_PrimitiveType{ element = c1; /* this is a subclass or primitive expression choice */ }
|c2 = parse_org_sintef_thingml_Enumeration{ element = c2; /* this is a subclass or primitive expression choice */ }

;

parse_org_sintef_thingml_Action returns [org.sintef.thingml.Action element = null]
:
c0 = parse_org_sintef_thingml_SendAction{ element = c0; /* this is a subclass or primitive expression choice */ }
|c1 = parse_org_sintef_thingml_PropertyAssignment{ element = c1; /* this is a subclass or primitive expression choice */ }
|c2 = parse_org_sintef_thingml_ActionBlock{ element = c2; /* this is a subclass or primitive expression choice */ }
|c3 = parse_org_sintef_thingml_ExternStatement{ element = c3; /* this is a subclass or primitive expression choice */ }
|c4 = parse_org_sintef_thingml_ConditionalAction{ element = c4; /* this is a subclass or primitive expression choice */ }
|c5 = parse_org_sintef_thingml_LoopAction{ element = c5; /* this is a subclass or primitive expression choice */ }
|c6 = parse_org_sintef_thingml_PrintAction{ element = c6; /* this is a subclass or primitive expression choice */ }
|c7 = parse_org_sintef_thingml_ErrorAction{ element = c7; /* this is a subclass or primitive expression choice */ }

;

parse_org_sintef_thingml_Event returns [org.sintef.thingml.Event element = null]
:
c0 = parse_org_sintef_thingml_ReceiveMessage{ element = c0; /* this is a subclass or primitive expression choice */ }

;

parse_org_sintef_thingml_Expression returns [org.sintef.thingml.Expression element = null]
:
c = parseop_Expression_level_1{ element = c; /* this rule is an expression root */ }

;

SL_COMMENT:
('//'(~('\n'|'\r'|'\uffff'))* )
{ _channel = 99; }
;
ML_COMMENT:
('/*'.*'*/')
{ _channel = 99; }
;
ANNOTATION:
('@'('A'..'Z' | 'a'..'z' | '0'..'9' | '_' | '-' )+)
;
BOOLEAN_LITERAL:
('true'|'false')
;
INTEGER_LITERAL:
(('1'..'9') ('0'..'9')* | '0')
;
STRING_LITERAL:
('"'('\\'('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')|('\\''u'('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F'))|'\\'('0'..'7')|~('\\'|'"'))*'"')
;
STRING_EXT:
('\''('\\'('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')|('\\''u'('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F'))|'\\'('0'..'7')|~('\\'|'\''))*'\'')
;
T_MULTICAST:
('multicast')
{ _channel = 99; }
;
T_READONLY:
('readonly')
;
T_ASPECT:
('fragment')
;
T_HISTORY:
('history')
;
T_SINGLETON:
('singleton')
{ _channel = 99; }
;
WHITESPACE:
((' '|'\t'|'\f'))
{ _channel = 99; }
;
LINEBREAKS:
(('\r\n'|'\r'|'\n'))
{ _channel = 99; }
;
TEXT:
(('A'..'Z' | 'a'..'z' | '0'..'9' | '_' )+ (':' ':' ('A'..'Z' | 'a'..'z' | '0'..'9' | '_')+ )* )
;

