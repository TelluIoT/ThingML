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
			if (type.getInstanceClass() == org.sintef.thingml.RequiredPort.class) {
				return parse_org_sintef_thingml_RequiredPort();
			}
			if (type.getInstanceClass() == org.sintef.thingml.ProvidedPort.class) {
				return parse_org_sintef_thingml_ProvidedPort();
			}
			if (type.getInstanceClass() == org.sintef.thingml.Property.class) {
				return parse_org_sintef_thingml_Property();
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
			if (type.getInstanceClass() == org.sintef.thingml.PropertyAssign.class) {
				return parse_org_sintef_thingml_PropertyAssign();
			}
			if (type.getInstanceClass() == org.sintef.thingml.Configuration.class) {
				return parse_org_sintef_thingml_Configuration();
			}
			if (type.getInstanceClass() == org.sintef.thingml.Instance.class) {
				return parse_org_sintef_thingml_Instance();
			}
			if (type.getInstanceClass() == org.sintef.thingml.Connector.class) {
				return parse_org_sintef_thingml_Connector();
			}
			if (type.getInstanceClass() == org.sintef.thingml.SendAction.class) {
				return parse_org_sintef_thingml_SendAction();
			}
			if (type.getInstanceClass() == org.sintef.thingml.VariableAssignment.class) {
				return parse_org_sintef_thingml_VariableAssignment();
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
		int followSetID = 351;
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 0, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 0, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 2, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 2, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 2, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_0, 3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 3, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 3, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 3, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 3, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
	}
	
	(
		(
			(
				(
					a2_0 = parse_org_sintef_thingml_Type					{
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
								addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__TYPES, value);
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
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 4, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 4, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 4, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
				}
				
				
				|				(
					a3_0 = parse_org_sintef_thingml_Configuration					{
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
								addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__CONFIGS, value);
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
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 5, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 5, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 5, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
				}
				
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 6, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 6, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 6, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 6, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 7, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 7, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 7, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_7, 8);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_8, 9);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_9, 10, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 10);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 11);
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
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_9, 12, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
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
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 13);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 13);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 14);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 15);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 16, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_13, 16);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 17, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_13, 17);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 18, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_13, 18);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 19, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 19, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 19, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 19, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 19, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 19, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 19, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 19);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 20);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 20);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 21);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 22);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 23, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 23);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 24);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 25);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 25, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 25);
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
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 26);
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
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 27);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 27, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 27);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 28);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 28, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 28);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 29, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 29);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 30, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 30);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 31, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 31);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 32, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 32, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 32, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 32, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 32, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 32, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 32, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 32);
	}
	
	(
		(
			(
				a9_0 = parse_org_sintef_thingml_Message				{
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
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING__MESSAGES, value);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 33);
			}
			
			
			|			(
				a10_0 = parse_org_sintef_thingml_Property				{
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
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING__PROPERTIES, value);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 34);
			}
			
			
			|			(
				a11_0 = parse_org_sintef_thingml_PropertyAssign				{
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
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING__ASSIGN, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_8_0_2_0, a11_0, true);
						copyLocalizationInfos(a11_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 35);
			}
			
			
			|			(
				a12_0 = parse_org_sintef_thingml_Port				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
						incompleteObjects.push(element);
					}
					if (a12_0 != null) {
						if (a12_0 != null) {
							Object value = a12_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING__PORTS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_8_0_3_0, a12_0, true);
						copyLocalizationInfos(a12_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 36);
			}
			
			
			|			(
				a13_0 = parse_org_sintef_thingml_StateMachine				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
						incompleteObjects.push(element);
					}
					if (a13_0 != null) {
						if (a13_0 != null) {
							Object value = a13_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING__BEHAVIOUR, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_8_0_4_0, a13_0, true);
						copyLocalizationInfos(a13_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 37);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 38);
	}
	
	a14 = '}' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_10, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a14, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 39, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 39, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 39, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 39, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
	}
	
;

parse_org_sintef_thingml_RequiredPort returns [org.sintef.thingml.RequiredPort element = null]
@init{
}
:
	a0 = 'required' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_1, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_28, 40);
	}
	
	a1 = 'port' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_3, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_29, 41);
	}
	
	(
		a2 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
				incompleteObjects.push(element);
			}
			if (a2 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_5, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 42, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_30, 42);
	}
	
	(
		(
			(
				a3_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
						incompleteObjects.push(element);
					}
					if (a3_0 != null) {
						if (a3_0 != null) {
							Object value = a3_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_6_0_0_0, a3_0, true);
						copyLocalizationInfos(a3_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 43, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_30, 43);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 44, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_30, 44);
	}
	
	a4 = '{' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_8, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a4, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 45);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_32, 45);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_33, 45);
	}
	
	(
		(
			a5 = 'receives' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_9_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_34, 46);
			}
			
			(
				a6 = TEXT				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
						incompleteObjects.push(element);
					}
					if (a6 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a6.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__RECEIVES), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a6).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStopIndex());
						}
						String resolved = (String) resolvedObject;
						org.sintef.thingml.Message proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
						collectHiddenTokens(element);
						registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Port, org.sintef.thingml.Message>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortReceivesReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__RECEIVES), resolved, proxy);
						if (proxy != null) {
							Object value = proxy;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__RECEIVES, value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_9_0_0_2, proxy, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, element);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, proxy);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_35, 47);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 47);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_32, 47);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_33, 47);
			}
			
			(
				(
					a7 = ',' {
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
							incompleteObjects.push(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_9_0_0_3_0_0_0, null, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a7, element);
					}
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_36, 48);
					}
					
					(
						a8 = TEXT						
						{
							if (terminateParsing) {
								throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
							}
							if (element == null) {
								element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
								incompleteObjects.push(element);
							}
							if (a8 != null) {
								org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
								tokenResolver.setOptions(getOptions());
								org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
								tokenResolver.resolve(a8.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__RECEIVES), result);
								Object resolvedObject = result.getResolvedToken();
								if (resolvedObject == null) {
									addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a8).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a8).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a8).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a8).getStopIndex());
								}
								String resolved = (String) resolvedObject;
								org.sintef.thingml.Message proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
								collectHiddenTokens(element);
								registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Port, org.sintef.thingml.Message>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortReceivesReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__RECEIVES), resolved, proxy);
								if (proxy != null) {
									Object value = proxy;
									addObjectToList(element, org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__RECEIVES, value);
									completedElement(value, false);
								}
								collectHiddenTokens(element);
								retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_9_0_0_3_0_0_2, proxy, true);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a8, element);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a8, proxy);
							}
						}
					)
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_35, 49);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 49);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_32, 49);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_33, 49);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_35, 50);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 50);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_32, 50);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_33, 50);
			}
			
			
			|			a9 = 'sends' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_9_0_1_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a9, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_37, 51);
			}
			
			(
				a10 = TEXT				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
						incompleteObjects.push(element);
					}
					if (a10 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a10.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__SENDS), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a10).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a10).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a10).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a10).getStopIndex());
						}
						String resolved = (String) resolvedObject;
						org.sintef.thingml.Message proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
						collectHiddenTokens(element);
						registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Port, org.sintef.thingml.Message>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortSendsReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__SENDS), resolved, proxy);
						if (proxy != null) {
							Object value = proxy;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__SENDS, value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_9_0_1_2, proxy, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a10, element);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a10, proxy);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_38, 52);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 52);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_32, 52);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_33, 52);
			}
			
			(
				(
					a11 = ',' {
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
							incompleteObjects.push(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_9_0_1_3_0_0_0, null, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a11, element);
					}
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_39, 53);
					}
					
					(
						a12 = TEXT						
						{
							if (terminateParsing) {
								throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
							}
							if (element == null) {
								element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
								incompleteObjects.push(element);
							}
							if (a12 != null) {
								org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
								tokenResolver.setOptions(getOptions());
								org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
								tokenResolver.resolve(a12.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__SENDS), result);
								Object resolvedObject = result.getResolvedToken();
								if (resolvedObject == null) {
									addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a12).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a12).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a12).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a12).getStopIndex());
								}
								String resolved = (String) resolvedObject;
								org.sintef.thingml.Message proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
								collectHiddenTokens(element);
								registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Port, org.sintef.thingml.Message>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortSendsReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__SENDS), resolved, proxy);
								if (proxy != null) {
									Object value = proxy;
									addObjectToList(element, org.sintef.thingml.ThingmlPackage.REQUIRED_PORT__SENDS, value);
									completedElement(value, false);
								}
								collectHiddenTokens(element);
								retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_9_0_1_3_0_0_2, proxy, true);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a12, element);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a12, proxy);
							}
						}
					)
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_38, 54);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 54);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_32, 54);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_33, 54);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_38, 55);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 55);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_32, 55);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_33, 55);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_31, 56);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_32, 56);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_33, 56);
	}
	
	a13 = '}' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_3_0_0_11, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a13, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 57, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 57, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 57, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 57, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 57, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 57, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 57, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 57);
	}
	
;

parse_org_sintef_thingml_ProvidedPort returns [org.sintef.thingml.ProvidedPort element = null]
@init{
}
:
	a0 = 'provided' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_1, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_40, 58);
	}
	
	a1 = 'port' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_3, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_41, 59);
	}
	
	(
		a2 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
				incompleteObjects.push(element);
			}
			if (a2 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_5, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 60, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 60);
	}
	
	(
		(
			(
				a3_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
						incompleteObjects.push(element);
					}
					if (a3_0 != null) {
						if (a3_0 != null) {
							Object value = a3_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_6_0_0_0, a3_0, true);
						copyLocalizationInfos(a3_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 61, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 61);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 62, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 62);
	}
	
	a4 = '{' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_8, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a4, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 63);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 63);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 63);
	}
	
	(
		(
			a5 = 'receives' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_9_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_46, 64);
			}
			
			(
				a6 = TEXT				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
						incompleteObjects.push(element);
					}
					if (a6 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a6.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__RECEIVES), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a6).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStopIndex());
						}
						String resolved = (String) resolvedObject;
						org.sintef.thingml.Message proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
						collectHiddenTokens(element);
						registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Port, org.sintef.thingml.Message>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortReceivesReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__RECEIVES), resolved, proxy);
						if (proxy != null) {
							Object value = proxy;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__RECEIVES, value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_9_0_0_2, proxy, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, element);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, proxy);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 65);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 65);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 65);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 65);
			}
			
			(
				(
					a7 = ',' {
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
							incompleteObjects.push(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_9_0_0_3_0_0_0, null, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a7, element);
					}
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_48, 66);
					}
					
					(
						a8 = TEXT						
						{
							if (terminateParsing) {
								throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
							}
							if (element == null) {
								element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
								incompleteObjects.push(element);
							}
							if (a8 != null) {
								org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
								tokenResolver.setOptions(getOptions());
								org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
								tokenResolver.resolve(a8.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__RECEIVES), result);
								Object resolvedObject = result.getResolvedToken();
								if (resolvedObject == null) {
									addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a8).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a8).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a8).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a8).getStopIndex());
								}
								String resolved = (String) resolvedObject;
								org.sintef.thingml.Message proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
								collectHiddenTokens(element);
								registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Port, org.sintef.thingml.Message>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortReceivesReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__RECEIVES), resolved, proxy);
								if (proxy != null) {
									Object value = proxy;
									addObjectToList(element, org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__RECEIVES, value);
									completedElement(value, false);
								}
								collectHiddenTokens(element);
								retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_9_0_0_3_0_0_2, proxy, true);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a8, element);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a8, proxy);
							}
						}
					)
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 67);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 67);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 67);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 67);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_47, 68);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 68);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 68);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 68);
			}
			
			
			|			a9 = 'sends' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_9_0_1_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a9, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_49, 69);
			}
			
			(
				a10 = TEXT				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
						incompleteObjects.push(element);
					}
					if (a10 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a10.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__SENDS), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a10).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a10).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a10).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a10).getStopIndex());
						}
						String resolved = (String) resolvedObject;
						org.sintef.thingml.Message proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
						collectHiddenTokens(element);
						registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Port, org.sintef.thingml.Message>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortSendsReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__SENDS), resolved, proxy);
						if (proxy != null) {
							Object value = proxy;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__SENDS, value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_9_0_1_2, proxy, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a10, element);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a10, proxy);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 70);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 70);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 70);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 70);
			}
			
			(
				(
					a11 = ',' {
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
							incompleteObjects.push(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_9_0_1_3_0_0_0, null, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a11, element);
					}
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_51, 71);
					}
					
					(
						a12 = TEXT						
						{
							if (terminateParsing) {
								throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
							}
							if (element == null) {
								element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
								incompleteObjects.push(element);
							}
							if (a12 != null) {
								org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
								tokenResolver.setOptions(getOptions());
								org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
								tokenResolver.resolve(a12.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__SENDS), result);
								Object resolvedObject = result.getResolvedToken();
								if (resolvedObject == null) {
									addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a12).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a12).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a12).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a12).getStopIndex());
								}
								String resolved = (String) resolvedObject;
								org.sintef.thingml.Message proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createMessage();
								collectHiddenTokens(element);
								registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Port, org.sintef.thingml.Message>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPortSendsReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__SENDS), resolved, proxy);
								if (proxy != null) {
									Object value = proxy;
									addObjectToList(element, org.sintef.thingml.ThingmlPackage.PROVIDED_PORT__SENDS, value);
									completedElement(value, false);
								}
								collectHiddenTokens(element);
								retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_9_0_1_3_0_0_2, proxy, true);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a12, element);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a12, proxy);
							}
						}
					)
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 72);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 72);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 72);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 72);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_50, 73);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 73);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 73);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 73);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_43, 74);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_44, 74);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_45, 74);
	}
	
	a13 = '}' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_4_0_0_11, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a13, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 75, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 75, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 75, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 75, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 75, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 75, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 75, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 75);
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
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_1_0_0_0, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 76);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 77);
	}
	
	a1 = 'property' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_52, 78);
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
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_4, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_53, 79);
	}
	
	a3 = ':' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_5, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_54, 80);
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
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_6, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_55, 81);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_56, 81);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 81, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 81, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 81, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 81, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 81, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 81, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 81, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 81, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 81);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 81);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 81);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 81, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 81, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 81, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 81, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 81);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 81);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 81);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 81, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 81);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 81);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 81);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 81);
	}
	
	(
		(
			a5 = '[' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_7_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 82);
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
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_7_0_0_1, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 83);
			}
			
			a7 = '..' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_7_0_0_2, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a7, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 84);
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
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_7_0_0_3, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a8, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 85);
			}
			
			a9 = ']' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_7_0_0_4, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a9, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_56, 86);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 86, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 86, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 86, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 86, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 86, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 86, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 86, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 86, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 86);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 86);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 86);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 86, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 86, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 86, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 86, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 86);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 86);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 86);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 86, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 86);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 86);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 86);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 86);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_56, 87);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 87, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 87, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 87, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 87, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 87, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 87, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 87, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 87, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 87);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 87);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 87);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 87, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 87, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 87, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 87, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 87);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 87);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 87);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 87, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 87);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 87);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 87);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 87);
	}
	
	(
		(
			a10 = '=' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_8_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a10, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
			}
			
			(
				a11_0 = parse_org_sintef_thingml_Expression				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
						incompleteObjects.push(element);
					}
					if (a11_0 != null) {
						if (a11_0 != null) {
							Object value = a11_0;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY__INIT), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_8_0_0_3, a11_0, true);
						copyLocalizationInfos(a11_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 89);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 89);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 89);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 89);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 89);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 89);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 89);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 89);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 89);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 89);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 90);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 90);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 90);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 90);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 90);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 90);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 90);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 90);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 90);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 90);
	}
	
	(
		(
			(
				a12_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
						incompleteObjects.push(element);
					}
					if (a12_0 != null) {
						if (a12_0 != null) {
							Object value = a12_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.PROPERTY__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_9_0_0_0, a12_0, true);
						copyLocalizationInfos(a12_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 91);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 91);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 91);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 91);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 91);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 91);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 91);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 91);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 91);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 91);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 92, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 92, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 92, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 92, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 92, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 92, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 92, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 92, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 92);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 92);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 92);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 92, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 92, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 92, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 92, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 92);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 92);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 92);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 92, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 92);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 92);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 92);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 92);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 93);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_86, 94);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 95);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 95);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_87, 96);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 97, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_88, 97);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 98, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_88, 98);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 99, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_88, 99);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 100, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 100, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 100, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 100, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_89, 101);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 102, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 102);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 103, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 103);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 104, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 104);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_91, 105, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_92, 105);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_91, 106, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_92, 106);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_91, 107, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_92, 107);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 108, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 108, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 108, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 108, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 109, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_91, 109, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_92, 109);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 110, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_91, 110, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_92, 110);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 111, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_91, 111, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_92, 111);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 112);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 113, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_13, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_30, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 113, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 113, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 113, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 113, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 113, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 113, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 113, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 113, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_88, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_101, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_103, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 113, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_104, 114);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_105, 114);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_105, 115);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_105, 116);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_106, 117);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_107, 118);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 118, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 118);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 119);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 120, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 120);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 121, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 121);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 122, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 122);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 123, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 123);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 124, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 124, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 124);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 124);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 124, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 124, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 124, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 124, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 124, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 124);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 125);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 125);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 125);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 126, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 126, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 126);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 126);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 126, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 126, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 126, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 126, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 126, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 126);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 127);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 129);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 129);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 130);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 130, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 130, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 130, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 130, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 130, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 130);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_119, 131);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 132, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 132, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 132, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 132, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 132, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 132, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 132, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 132, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 133, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 133, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 133, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 133, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 133, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 133);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 134);
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
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 135, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 135, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 135, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 135, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 135, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 135);
				}
				
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 136, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 136, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 136, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 136, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 136, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 136);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 137, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 137, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 137, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 137, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 137, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 137);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 138, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 138, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 138, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 138, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 138, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 138);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 139, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 139);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 140);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 141);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 141);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 141, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 141);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_120, 141);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_121, 142);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 143, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_122, 143);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 144, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_122, 144);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 145, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_122, 145);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 146, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 146, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 146);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 146);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 146, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 146, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 146);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 147);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 147);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 147);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 148, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 148, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 148);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 148);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 148, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 148, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 148);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_123, 149);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 150, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 150, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 150, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 150, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 150, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 150, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 150, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 150, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 151);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 151, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 151, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 151);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 152);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 152, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 152, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 152);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_124, 153);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 154, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 154, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 154, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 154, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 154, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 154, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 154, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 154, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 155, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 155, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 155);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 156, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 156, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 156);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 157, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 157, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 157);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 158, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 158, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 158);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 159, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 159, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 159);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 160, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 160, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 160, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 160, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 160, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 160);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 160, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 160);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_120, 160);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_125, 161);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 162);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 163);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 164);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 165);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 165, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 165);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 166);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 167, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 167);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 168, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 168);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 169, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 169);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 170);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 171);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 171);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 171);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 172);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 172);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 172);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 173, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 173, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 173);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 173);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 173, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 173, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 173, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 173, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 173, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 173, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 173);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 174);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 176);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 176);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 177);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 177, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 177, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 177, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 177, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 177, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 177, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 177);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 178);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 180, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 180, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 180, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 180, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 180, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 180, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 180);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 181, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 181, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 181, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 181, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 181, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 181, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 181);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 182);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 183, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 183, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 183, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 183, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 183, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 183, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 183);
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
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 184, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 184, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 184, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 184, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 184, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 184, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 184);
				}
				
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 185, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 185, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 185, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 185, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 185, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 185, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 185);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 186, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 186, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 186, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 186, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 186, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 186, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 186);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 187, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 187);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 188, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 188);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 189, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 189, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 189, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 189, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 189, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 189);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 189, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 189);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_120, 189);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 190);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_135, 191);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_136, 192);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_137, 193);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 193, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 193);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_138, 194);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 195, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 195);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 196, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 196);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 197, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 197);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 198, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 198);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 199, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 199, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 199, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_120, 199);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 200, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 200, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 200, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_120, 200);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 201, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 201, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 201, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_120, 201);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 202, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 202);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 202);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_139, 203);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_140, 203);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_140, 204);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_140, 205);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_141, 206);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 207);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 207);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 207);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 207);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 207);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 207);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 207);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 208);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 208);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 208);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 208);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 208);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 208);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 208);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 209);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 209);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 209);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 209);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 209);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 209);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 209);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 210, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 210, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 211);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 211);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 211);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 211);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 211);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 211);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 211);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 212);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 212);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 212);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 212);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 212);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 212);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 212);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 214);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 214);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 214);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 214);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 214);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 215);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 215);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 215);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 215);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 215);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 217);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 217);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 217, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 217, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 217);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 217, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 217, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 217, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 217, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 217);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 218);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 218);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 218);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 218);
	}
	
	(
		(
			a11 = 'before' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_11_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a11, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
			}
			
			(
				a12_0 = parse_org_sintef_thingml_Action				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
						incompleteObjects.push(element);
					}
					if (a12_0 != null) {
						if (a12_0 != null) {
							Object value = a12_0;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__BEFORE), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_11_0_0_3, a12_0, true);
						copyLocalizationInfos(a12_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 220);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 220);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 220);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 221);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 221);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 221);
	}
	
	(
		(
			a13 = 'after' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_12_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a13, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
			}
			
			(
				a14_0 = parse_org_sintef_thingml_Action				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createTransition();
						incompleteObjects.push(element);
					}
					if (a14_0 != null) {
						if (a14_0 != null) {
							Object value = a14_0;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.TRANSITION__AFTER), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_15_0_0_12_0_0_3, a14_0, true);
						copyLocalizationInfos(a14_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 223);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 223);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 224);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 224);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 225);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 225);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_101, 225);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 225);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 225);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 225);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 225);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 226);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_101, 226);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 226);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 226);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 226);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 226);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 227, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 227);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_101, 227);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 227);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 227, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 227, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 227, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 227, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 227, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 227);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 227, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 227);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 227);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 228, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 228);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_101, 228);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 228);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 228, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 228, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 228, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 228, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 228, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 228);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 228, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 228);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 228);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 229);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_101, 229);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 229);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 229);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 229);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 229);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 230, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 230, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 231);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_101, 231);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 231);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 231);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 231);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 231);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 232);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_101, 232);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 232);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 232, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 232, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 232, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 232, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 232, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 232);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 232, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 232);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 232);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 233, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 233, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 233, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 233, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 233, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 233, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 233, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 233, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 233, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 233, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 234);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 234, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 234, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 234, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 234, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 234, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 234);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 234, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 234);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 234);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 235);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 235);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 235, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 235);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 235);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 236, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 236, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 236, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 236, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 236, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 236, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 236, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 236, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 237);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 237);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 237);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 238, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 238, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 238, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 238, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 238, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 238);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 238, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 238);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 238);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_145, 239);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 240);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 241);
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
				org.sintef.thingml.Port proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_146, 242);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 243);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 244, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_101, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 244);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 244);
	}
	
;

parse_org_sintef_thingml_PropertyAssign returns [org.sintef.thingml.PropertyAssign element = null]
@init{
}
:
	a0 = 'set' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPropertyAssign();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_18_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 245);
	}
	
	(
		a1 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPropertyAssign();
				incompleteObjects.push(element);
			}
			if (a1 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGN__PROPERTY), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.Property proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.PropertyAssign, org.sintef.thingml.Property>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getPropertyAssignPropertyReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGN__PROPERTY), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGN__PROPERTY), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_18_0_0_2, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 246);
	}
	
	a2 = '=' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPropertyAssign();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_18_0_0_4, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a2, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
	}
	
	(
		a3_0 = parse_org_sintef_thingml_Expression		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createPropertyAssign();
				incompleteObjects.push(element);
			}
			if (a3_0 != null) {
				if (a3_0 != null) {
					Object value = a3_0;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.PROPERTY_ASSIGN__INIT), value);
					completedElement(value, true);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_18_0_0_6, a3_0, true);
				copyLocalizationInfos(a3_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 248);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 248, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 248);
	}
	
;

parse_org_sintef_thingml_Configuration returns [org.sintef.thingml.Configuration element = null]
@init{
}
:
	a0 = 'configuration' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConfiguration();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_153, 249);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 249);
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
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConfiguration();
						incompleteObjects.push(element);
					}
					if (a1 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("T_ASPECT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__FRAGMENT), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
						}
						java.lang.Boolean resolved = (java.lang.Boolean)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__FRAGMENT), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_1_0_0_1, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 250);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 251);
	}
	
	(
		a2 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConfiguration();
				incompleteObjects.push(element);
			}
			if (a2 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String)resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_3, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_155, 252);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_103, 252);
	}
	
	(
		(
			a3 = 'includes' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConfiguration();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_4_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_156, 253);
			}
			
			(
				a4 = TEXT				
				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConfiguration();
						incompleteObjects.push(element);
					}
					if (a4 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a4.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__INCLUDES), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a4).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a4).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a4).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a4).getStopIndex());
						}
						String resolved = (String) resolvedObject;
						org.sintef.thingml.Configuration proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConfiguration();
						collectHiddenTokens(element);
						registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Configuration, org.sintef.thingml.Configuration>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getConfigurationIncludesReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__INCLUDES), resolved, proxy);
						if (proxy != null) {
							Object value = proxy;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.CONFIGURATION__INCLUDES, value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_4_0_0_3, proxy, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, element);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, proxy);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 254);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 254, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_103, 254);
			}
			
			(
				(
					a5 = ',' {
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConfiguration();
							incompleteObjects.push(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_4_0_0_4_0_0_0, null, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
					}
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_158, 255);
					}
					
					(
						a6 = TEXT						
						{
							if (terminateParsing) {
								throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
							}
							if (element == null) {
								element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConfiguration();
								incompleteObjects.push(element);
							}
							if (a6 != null) {
								org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
								tokenResolver.setOptions(getOptions());
								org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
								tokenResolver.resolve(a6.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__INCLUDES), result);
								Object resolvedObject = result.getResolvedToken();
								if (resolvedObject == null) {
									addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a6).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStopIndex());
								}
								String resolved = (String) resolvedObject;
								org.sintef.thingml.Configuration proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConfiguration();
								collectHiddenTokens(element);
								registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Configuration, org.sintef.thingml.Configuration>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getConfigurationIncludesReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONFIGURATION__INCLUDES), resolved, proxy);
								if (proxy != null) {
									Object value = proxy;
									addObjectToList(element, org.sintef.thingml.ThingmlPackage.CONFIGURATION__INCLUDES, value);
									completedElement(value, false);
								}
								collectHiddenTokens(element);
								retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_4_0_0_4_0_0_2, proxy, true);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, element);
								copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, proxy);
							}
						}
					)
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 256);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 256, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_103, 256);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 257);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_103, 257);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 258, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_103, 258);
	}
	
	(
		(
			(
				a7_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConfiguration();
						incompleteObjects.push(element);
					}
					if (a7_0 != null) {
						if (a7_0 != null) {
							Object value = a7_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.CONFIGURATION__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_5_0_0_0, a7_0, true);
						copyLocalizationInfos(a7_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 259, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_103, 259);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 260, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_103, 260);
	}
	
	a8 = '{' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConfiguration();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_7, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a8, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 261, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 261, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 261);
	}
	
	(
		(
			(
				a9_0 = parse_org_sintef_thingml_Instance				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConfiguration();
						incompleteObjects.push(element);
					}
					if (a9_0 != null) {
						if (a9_0 != null) {
							Object value = a9_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.CONFIGURATION__INSTANCES, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_8_0_0_0, a9_0, true);
						copyLocalizationInfos(a9_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 262, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 262);
			}
			
			
			|			(
				a10_0 = parse_org_sintef_thingml_Connector				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConfiguration();
						incompleteObjects.push(element);
					}
					if (a10_0 != null) {
						if (a10_0 != null) {
							Object value = a10_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.CONFIGURATION__CONNECTORS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_8_0_1_0, a10_0, true);
						copyLocalizationInfos(a10_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 263);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 264);
	}
	
	a11 = '}' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConfiguration();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_19_0_0_10, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a11, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
	}
	
;

parse_org_sintef_thingml_Instance returns [org.sintef.thingml.Instance element = null]
@init{
}
:
	a0 = 'instance' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInstance();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_20_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_159, 266);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 266);
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
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInstance();
						incompleteObjects.push(element);
					}
					if (a1 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__NAME), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
						}
						java.lang.String resolved = (java.lang.String)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__NAME), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_20_0_0_2_0_0_0, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 267);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 268);
	}
	
	a2 = ':' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInstance();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_20_0_0_3, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a2, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_161, 269);
	}
	
	(
		a3 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInstance();
				incompleteObjects.push(element);
			}
			if (a3 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a3.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__TYPE), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a3).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a3).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a3).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a3).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.Thing proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Instance, org.sintef.thingml.Thing>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getInstanceTypeReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__TYPE), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INSTANCE__TYPE), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_20_0_0_5, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a3, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a3, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 270, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 270, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 270, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 270, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 270);
	}
	
	(
		(
			(
				a4_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInstance();
						incompleteObjects.push(element);
					}
					if (a4_0 != null) {
						if (a4_0 != null) {
							Object value = a4_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.INSTANCE__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_20_0_0_6_0_0_0, a4_0, true);
						copyLocalizationInfos(a4_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 271, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 271, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 271, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 271, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 271);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 272, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 272, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 272, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 272, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 272);
	}
	
	(
		(
			(
				a5_0 = parse_org_sintef_thingml_PropertyAssign				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInstance();
						incompleteObjects.push(element);
					}
					if (a5_0 != null) {
						if (a5_0 != null) {
							Object value = a5_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.INSTANCE__ASSIGN, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_20_0_0_8_0_0_0, a5_0, true);
						copyLocalizationInfos(a5_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 273, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 273, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 273, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 273);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 274, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 274, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 274, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 274);
	}
	
;

parse_org_sintef_thingml_Connector returns [org.sintef.thingml.Connector element = null]
@init{
}
:
	a0 = 'connector' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConnector();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_21_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_162, 275);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_163, 275);
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
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConnector();
						incompleteObjects.push(element);
					}
					if (a1 != null) {
						org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
						tokenResolver.setOptions(getOptions());
						org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__NAME), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a1).getStopIndex());
						}
						java.lang.String resolved = (java.lang.String)resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__NAME), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_21_0_0_2_0_0_0, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a1, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_163, 276);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_163, 277);
	}
	
	(
		a2 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConnector();
				incompleteObjects.push(element);
			}
			if (a2 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__CLIENT), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.Instance proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInstance();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Connector, org.sintef.thingml.Instance>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getConnectorClientReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__CLIENT), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__CLIENT), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_21_0_0_3, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_164, 278);
	}
	
	a3 = '.' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConnector();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_21_0_0_4, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_165, 279);
	}
	
	(
		a4 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConnector();
				incompleteObjects.push(element);
			}
			if (a4 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a4.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__REQUIRED), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a4).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a4).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a4).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a4).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.RequiredPort proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Connector, org.sintef.thingml.RequiredPort>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getConnectorRequiredReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__REQUIRED), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__REQUIRED), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_21_0_0_5, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a4, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_166, 280);
	}
	
	a5 = '=>' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConnector();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_21_0_0_6, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_167, 281);
	}
	
	(
		a6 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConnector();
				incompleteObjects.push(element);
			}
			if (a6 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a6.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__SERVER), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a6).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a6).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.Instance proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createInstance();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Connector, org.sintef.thingml.Instance>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getConnectorServerReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__SERVER), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__SERVER), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_21_0_0_7, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a6, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_168, 282);
	}
	
	a7 = '.' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConnector();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_21_0_0_8, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a7, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_169, 283);
	}
	
	(
		a8 = TEXT		
		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConnector();
				incompleteObjects.push(element);
			}
			if (a8 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a8.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__PROVIDED), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a8).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a8).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a8).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a8).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.ProvidedPort proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProvidedPort();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.Connector, org.sintef.thingml.ProvidedPort>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getConnectorProvidedReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__PROVIDED), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.CONNECTOR__PROVIDED), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_21_0_0_9, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a8, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a8, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 284, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 284);
	}
	
	(
		(
			(
				a9_0 = parse_org_sintef_thingml_PlatformAnnotation				{
					if (terminateParsing) {
						throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
					}
					if (element == null) {
						element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConnector();
						incompleteObjects.push(element);
					}
					if (a9_0 != null) {
						if (a9_0 != null) {
							Object value = a9_0;
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.CONNECTOR__ANNOTATIONS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_21_0_0_10_0_0_1, a9_0, true);
						copyLocalizationInfos(a9_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 285);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 286, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 286);
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
				org.sintef.thingml.Port proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createRequiredPort();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.SendAction, org.sintef.thingml.Port>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getSendActionPortReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__PORT), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.SEND_ACTION__PORT), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_22_0_0_0, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 287);
	}
	
	a1 = '!' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createSendAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_22_0_0_1, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_171, 288);
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
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_22_0_0_2, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 289);
	}
	
	a3 = '(' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createSendAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_22_0_0_3, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 290, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 290);
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
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_22_0_0_4_0_0_0, a4_0, true);
						copyLocalizationInfos(a4_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 291);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 291);
			}
			
			(
				(
					a5 = ',' {
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createSendAction();
							incompleteObjects.push(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_22_0_0_4_0_0_1_0_0_0, null, true);
						copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a5, element);
					}
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
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
								retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_22_0_0_4_0_0_1_0_0_2, a6_0, true);
								copyLocalizationInfos(a6_0, element);
							}
						}
					)
					{
						// expected elements (follow set)
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 293);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 293);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 294);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 294);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 295);
	}
	
	a7 = ')' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createSendAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_22_0_0_5, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a7, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 296);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 296);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 296);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 296);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 296);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 296);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 296);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 296);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 296);
	}
	
;

parse_org_sintef_thingml_VariableAssignment returns [org.sintef.thingml.VariableAssignment element = null]
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
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createVariableAssignment();
				incompleteObjects.push(element);
			}
			if (a0 != null) {
				org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.VARIABLE_ASSIGNMENT__PROPERTY), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
				}
				String resolved = (String) resolvedObject;
				org.sintef.thingml.Property proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
				collectHiddenTokens(element);
				registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.VariableAssignment, org.sintef.thingml.Property>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getVariableAssignmentPropertyReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.VARIABLE_ASSIGNMENT__PROPERTY), resolved, proxy);
				if (proxy != null) {
					Object value = proxy;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.VARIABLE_ASSIGNMENT__PROPERTY), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_23_0_0_0, proxy, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, proxy);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_176, 297);
	}
	
	a1 = '=' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createVariableAssignment();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_23_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
	}
	
	(
		a2_0 = parse_org_sintef_thingml_Expression		{
			if (terminateParsing) {
				throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
			}
			if (element == null) {
				element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createVariableAssignment();
				incompleteObjects.push(element);
			}
			if (a2_0 != null) {
				if (a2_0 != null) {
					Object value = a2_0;
					element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.VARIABLE_ASSIGNMENT__EXPRESSION), value);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 299);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 299);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 299);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 299);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 299);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 299);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 299);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 299);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 299);
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
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_24_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 300);
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
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_24_0_0_1_0_0_1, a1_0, true);
						copyLocalizationInfos(a1_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 301, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 301, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 301, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 301, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 301, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 301, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 301, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 301, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 301);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 302);
	}
	
	a2 = 'end' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createActionBlock();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_24_0_0_3, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a2, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 303);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 303, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 303, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 303, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 303, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 303, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 303);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 303);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 303, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 303);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 303);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 303);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 303);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 303);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 303, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 303, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 303, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 303, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 303, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 303, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 303, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 303, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 303);
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
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_25_0_0_0, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_177, 304);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 304);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 304);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 304);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 304);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 304);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 304);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 304);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 304);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 304);
	}
	
	(
		(
			a1 = '&' {
				if (element == null) {
					element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createExternStatement();
					incompleteObjects.push(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_25_0_0_1_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
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
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_25_0_0_1_0_0_1, a2_0, true);
						copyLocalizationInfos(a2_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_177, 306);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 306);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 306, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 306, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 306, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 306, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 306, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 306);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 306);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 306, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 306);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 306);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 306);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 306);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 306);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 306, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 306, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 306, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 306, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 306, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 306, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 306, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 306, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 306);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_177, 307);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 307);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 307);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 307);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 307);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 307);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 307);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 307);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 307);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 307);
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
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_26_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_178, 308);
	}
	
	a1 = '(' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConditionalAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_26_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
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
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_26_0_0_4, a2_0, true);
				copyLocalizationInfos(a2_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_179, 310);
	}
	
	a3 = ')' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createConditionalAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_26_0_0_6, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
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
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_26_0_0_8, a4_0, true);
				copyLocalizationInfos(a4_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 312);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 312);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 312);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 312);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 312);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 312);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 312);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 312);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 312);
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
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_27_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_180, 313);
	}
	
	a1 = '(' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createLoopAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_27_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
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
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_27_0_0_4, a2_0, true);
				copyLocalizationInfos(a2_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_181, 315);
	}
	
	a3 = ')' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createLoopAction();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_27_0_0_6, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a3, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
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
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_27_0_0_8, a4_0, true);
				copyLocalizationInfos(a4_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 317);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 317);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 317);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 317);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 317);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 317);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 317);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 317);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 317);
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
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_28_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
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
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_28_0_0_2, a1_0, true);
				copyLocalizationInfos(a1_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 319);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 319);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 319);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 319);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 319);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 319);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 319);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 319);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 319);
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
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_29_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
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
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_29_0_0_2, a1_0, true);
				copyLocalizationInfos(a1_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 321);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 321);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 321);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 321);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 321);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 321);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 321);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 321);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 321);
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
			retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_30_0_0_2, null, true);
			copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
		}
		{
			// expected elements (follow set)
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
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
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_30_0_0_0, leftArg, true);
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
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_30_0_0_4, rightArg, true);
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
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_31_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
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
			retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_31_0_0_0, leftArg, true);
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
			retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_31_0_0_4, rightArg, true);
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
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_32_0_0_2, null, true);
	copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
	// expected elements (follow set)
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
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
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_32_0_0_0, leftArg, true);
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
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_32_0_0_4, rightArg, true);
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
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_33_0_0_2, null, true);
	copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
	// expected elements (follow set)
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
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
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_33_0_0_0, leftArg, true);
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
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_33_0_0_4, rightArg, true);
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
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_34_0_0_2, null, true);
	copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
	// expected elements (follow set)
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
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
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_34_0_0_0, leftArg, true);
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
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_34_0_0_4, rightArg, true);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_35_0_0_2, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 327, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 327, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 327, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 327, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 327, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 327, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 327, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 327, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 327, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 327, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
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
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_35_0_0_0, leftArg, true);
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
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_35_0_0_4, rightArg, true);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_36_0_0_2, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 328, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 328, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 328, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 328, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 328, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 328, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 328, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 328, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 328, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 328, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
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
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_36_0_0_0, leftArg, true);
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
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_36_0_0_4, rightArg, true);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_37_0_0_2, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_37_0_0_0, leftArg, true);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_37_0_0_4, rightArg, true);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_38_0_0_2, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_38_0_0_0, leftArg, true);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_38_0_0_4, rightArg, true);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_39_0_0_2, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 331, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 331, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 331, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 331, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 331, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 331, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 331, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 331, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 331, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 331, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_39_0_0_0, leftArg, true);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_39_0_0_4, rightArg, true);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_40_0_0_0, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_40_0_0_2, arg, true);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_41_0_0_0, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_41_0_0_2, arg, true);
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
|c3 = parse_org_sintef_thingml_IntegerLiteral{ element = c3; /* this is a subclass or primitive expression choice */ }
|c4 = parse_org_sintef_thingml_StringLiteral{ element = c4; /* this is a subclass or primitive expression choice */ }
|c5 = parse_org_sintef_thingml_BooleanLiteral{ element = c5; /* this is a subclass or primitive expression choice */ }
|c6 = parse_org_sintef_thingml_EnumLiteralRef{ element = c6; /* this is a subclass or primitive expression choice */ }
|c7 = parse_org_sintef_thingml_ExternExpression{ element = c7; /* this is a subclass or primitive expression choice */ }
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_42_0_0_0, proxy, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, proxy);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_192, 334);
}

a1 = '.' {
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEventReference();
incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_42_0_0_1, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_193, 335);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_42_0_0_2, proxy, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, proxy);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_177, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_189, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_190, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_194, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_195, 336);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_43_0_0_0, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a0, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_38);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_38);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_38);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_38);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_38);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_38);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_38);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_38);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_38);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_38);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_43_0_0_1, a1_0, true);
copyLocalizationInfos(a1_0, element);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_194, 338);
}

a2 = ')' {
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createExpressionGroup();
incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_43_0_0_2, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a2, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_177, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_189, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_190, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_194, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_195, 339);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_44_0_0_0, proxy, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, proxy);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_177, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_189, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_190, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_194, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_195, 340);
}

;

parse_org_sintef_thingml_IntegerLiteral returns [org.sintef.thingml.IntegerLiteral element = null]
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
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createIntegerLiteral();
incompleteObjects.push(element);
}
if (a0 != null) {
org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("INTEGER_LITERAL");
tokenResolver.setOptions(getOptions());
org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTEGER_LITERAL__INT_VALUE), result);
Object resolvedObject = result.getResolvedToken();
if (resolvedObject == null) {
addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
}
java.lang.Integer resolved = (java.lang.Integer)resolvedObject;
if (resolved != null) {
Object value = resolved;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.INTEGER_LITERAL__INT_VALUE), value);
completedElement(value, false);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_45_0_0_0, resolved, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 341, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_177, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_189, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_190, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_194, 341);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_195, 341);
}

;

parse_org_sintef_thingml_StringLiteral returns [org.sintef.thingml.StringLiteral element = null]
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
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createStringLiteral();
incompleteObjects.push(element);
}
if (a0 != null) {
org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("STRING_LITERAL");
tokenResolver.setOptions(getOptions());
org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STRING_LITERAL__STRING_VALUE), result);
Object resolvedObject = result.getResolvedToken();
if (resolvedObject == null) {
addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
}
java.lang.String resolved = (java.lang.String)resolvedObject;
if (resolved != null) {
Object value = resolved;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.STRING_LITERAL__STRING_VALUE), value);
completedElement(value, false);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_46_0_0_0, resolved, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 342, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_177, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_189, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_190, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_194, 342);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_195, 342);
}

;

parse_org_sintef_thingml_BooleanLiteral returns [org.sintef.thingml.BooleanLiteral element = null]
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
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createBooleanLiteral();
incompleteObjects.push(element);
}
if (a0 != null) {
org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("BOOLEAN_LITERAL");
tokenResolver.setOptions(getOptions());
org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.BOOLEAN_LITERAL__BOOL_VALUE), result);
Object resolvedObject = result.getResolvedToken();
if (resolvedObject == null) {
addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
}
java.lang.Boolean resolved = (java.lang.Boolean)resolvedObject;
if (resolved != null) {
Object value = resolved;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.BOOLEAN_LITERAL__BOOL_VALUE), value);
completedElement(value, false);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_47_0_0_0, resolved, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 343, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_177, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_189, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_190, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_194, 343);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_195, 343);
}

;

parse_org_sintef_thingml_EnumLiteralRef returns [org.sintef.thingml.EnumLiteralRef element = null]
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
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEnumLiteralRef();
incompleteObjects.push(element);
}
if (a0 != null) {
org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
tokenResolver.setOptions(getOptions());
org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
tokenResolver.resolve(a0.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUM_LITERAL_REF__ENUM), result);
Object resolvedObject = result.getResolvedToken();
if (resolvedObject == null) {
addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a0).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a0).getStopIndex());
}
String resolved = (String) resolvedObject;
org.sintef.thingml.Enumeration proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEnumeration();
collectHiddenTokens(element);
registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.EnumLiteralRef, org.sintef.thingml.Enumeration>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getEnumLiteralRefEnumReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUM_LITERAL_REF__ENUM), resolved, proxy);
if (proxy != null) {
Object value = proxy;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUM_LITERAL_REF__ENUM), value);
completedElement(value, false);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_48_0_0_0, proxy, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, proxy);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_196, 344);
}

a1 = ':' {
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEnumLiteralRef();
incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_48_0_0_1, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_197, 345);
}

(
a2 = TEXT
{
if (terminateParsing) {
throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
}
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEnumLiteralRef();
incompleteObjects.push(element);
}
if (a2 != null) {
org.sintef.thingml.resource.thingml.IThingmlTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
tokenResolver.setOptions(getOptions());
org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result = getFreshTokenResolveResult();
tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUM_LITERAL_REF__LITERAL), result);
Object resolvedObject = result.getResolvedToken();
if (resolvedObject == null) {
addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_3_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_3_0.CommonToken) a2).getStopIndex());
}
String resolved = (String) resolvedObject;
org.sintef.thingml.EnumerationLiteral proxy = org.sintef.thingml.ThingmlFactory.eINSTANCE.createEnumerationLiteral();
collectHiddenTokens(element);
registerContextDependentProxy(new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragmentFactory<org.sintef.thingml.EnumLiteralRef, org.sintef.thingml.EnumerationLiteral>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getEnumLiteralRefLiteralReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUM_LITERAL_REF__LITERAL), resolved, proxy);
if (proxy != null) {
Object value = proxy;
element.eSet(element.eClass().getEStructuralFeature(org.sintef.thingml.ThingmlPackage.ENUM_LITERAL_REF__LITERAL), value);
completedElement(value, false);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_48_0_0_2, proxy, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, element);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a2, proxy);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 346, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_177, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_189, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_190, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_194, 346);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_195, 346);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_49_0_0_0, resolved, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_195, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 347, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_177, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_189, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_190, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 347);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_194, 347);
}

(
(
a1 = '&' {
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createExternExpression();
incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_49_0_0_1_0_0_0, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 348, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_39);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 348, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_39);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 348, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_39);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 348, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_39);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 348, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_39);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 348, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_39);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 348, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_39);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 348, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_39);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 348, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_39);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 348, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_39);
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
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_49_0_0_1_0_0_1, a2_0, true);
	copyLocalizationInfos(a2_0, element);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_195, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 349, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_177, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_189, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_190, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 349);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_194, 349);
}

)

)*{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_195, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 350, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_177, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_189, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_190, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 350);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_194, 350);
}

;

parse_org_sintef_thingml_Type returns [org.sintef.thingml.Type element = null]
:
c0 = parse_org_sintef_thingml_Thing{ element = c0; /* this is a subclass or primitive expression choice */ }
|c1 = parse_org_sintef_thingml_PrimitiveType{ element = c1; /* this is a subclass or primitive expression choice */ }
|c2 = parse_org_sintef_thingml_Enumeration{ element = c2; /* this is a subclass or primitive expression choice */ }

;

parse_org_sintef_thingml_Port returns [org.sintef.thingml.Port element = null]
:
c0 = parse_org_sintef_thingml_RequiredPort{ element = c0; /* this is a subclass or primitive expression choice */ }
|c1 = parse_org_sintef_thingml_ProvidedPort{ element = c1; /* this is a subclass or primitive expression choice */ }

;

parse_org_sintef_thingml_Expression returns [org.sintef.thingml.Expression element = null]
:
c = parseop_Expression_level_1{ element = c; /* this rule is an expression root */ }

;

parse_org_sintef_thingml_Action returns [org.sintef.thingml.Action element = null]
:
c0 = parse_org_sintef_thingml_SendAction{ element = c0; /* this is a subclass or primitive expression choice */ }
|c1 = parse_org_sintef_thingml_VariableAssignment{ element = c1; /* this is a subclass or primitive expression choice */ }
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
T_READONLY:
('readonly')
;
T_ASPECT:
('fragment')
;
T_HISTORY:
('history')
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
(('A'..'Z' | 'a'..'z' | '0'..'9' | '_' | '-' )+)
;

