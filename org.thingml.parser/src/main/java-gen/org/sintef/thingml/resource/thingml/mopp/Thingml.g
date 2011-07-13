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
		int followSetID = 341;
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_5, 0, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_6, 1);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_5, 2, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_0, 3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 3, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 3, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 3, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 3, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_5, 3, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
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
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_5, 4, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
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
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_5, 5, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
				}
				
				
				|				(
					a4_0 = parse_org_sintef_thingml_Configuration					{
						if (terminateParsing) {
							throw new org.sintef.thingml.resource.thingml.mopp.ThingmlTerminateParsingException();
						}
						if (element == null) {
							element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThingMLModel();
							incompleteObjects.push(element);
						}
						if (a4_0 != null) {
							if (a4_0 != null) {
								Object value = a4_0;
								addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING_ML_MODEL__CONFIGS, value);
								completedElement(value, true);
							}
							collectHiddenTokens(element);
							retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_0_0_0_1_0_0_1_0_2_0, a4_0, true);
							copyLocalizationInfos(a4_0, element);
						}
					}
				)
				{
					// expected elements (follow set)
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 6, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 6, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 6, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 6, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_5, 6, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
				}
				
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 7, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 7, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 7, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 7, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_5, 7, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 8, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 8, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 8, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 8, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_5, 8, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_7, 9);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_8, 10);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_9, 11, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 11);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 12);
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
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_9, 13, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_3);
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
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 14);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 14);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 15);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 15);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 16);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 17, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_13, 17);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 18, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_13, 18);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 19, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_13, 19);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 20, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 20, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 20, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 20, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_5, 20, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_14, 21);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 21);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 22);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_15, 23);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_16, 24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 24, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 24);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_18, 25);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 26);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 26, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 26);
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
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_20, 27);
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
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 28);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 28, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 28);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_19, 29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 29, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 29);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 30, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 30);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 31, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 31);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 32, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 32);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 33, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 33);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 34, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 34);
			}
			
			
			|			(
				a10_0 = parse_org_sintef_thingml_PropertyAssign				{
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
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING__ASSIGN, value);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 35, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 35);
			}
			
			
			|			(
				a11_0 = parse_org_sintef_thingml_Port				{
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
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING__PORTS, value);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 36, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 36);
			}
			
			
			|			(
				a12_0 = parse_org_sintef_thingml_StateMachine				{
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
							addObjectToList(element, org.sintef.thingml.ThingmlPackage.THING__BEHAVIOUR, value);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 37, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 37);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 38, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 38);
	}
	
	a13 = '}' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createThing();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_2_0_0_10, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a13, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 39, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 39, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 39, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 39, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_5, 39, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 42, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 43, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_30, 43);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 44, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 57, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 57, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 57, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 57, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 57, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 57, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 57);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 60, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 61, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 61);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 62, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 75, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 75, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 75, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 75, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 75, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 75, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 75);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 76);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 77);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_57, 82);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_58, 83);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_59, 84);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_60, 85);
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
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_56, 87);
	}
	
	a10 = '=' {
		if (element == null) {
			element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createProperty();
			incompleteObjects.push(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_9, null, true);
		copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a10, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 88, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_10);
	}
	
	(
		a11_0 = parse_org_sintef_thingml_Expression		{
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
				retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_11, a11_0, true);
				copyLocalizationInfos(a11_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 89);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 89);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 89);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 89);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 89);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 89);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 89, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 89);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 89);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 89);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 89);
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
						retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_5_0_0_12_0_0_0, a12_0, true);
						copyLocalizationInfos(a12_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 90);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 90);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 90);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 90);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 90);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 90);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 90, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 90);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 90);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 90);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 90);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 91);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 91);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 91);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 91);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 91);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 91);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 91, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 91);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 91);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 91);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 91);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_84, 92);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_85, 93);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_11, 94);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_10, 94);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_86, 95);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 96, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_87, 96);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 97, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_87, 97);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 98, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_87, 98);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 99, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 99, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 99, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 99, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_5, 99, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_88, 100);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 101, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_89, 101);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 102, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_89, 102);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 103, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_89, 103);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 104, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_91, 104);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 105, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_91, 105);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 106, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_91, 106);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 107, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 107, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 107, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 107, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_5, 107, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 108, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 108, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_91, 108);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 109, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 109, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_91, 109);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 110, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_90, 110, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_15);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_91, 110);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_92, 111);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 112, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_13, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_17, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_30, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_42, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 112, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 112, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 112, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 112, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 112, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 112, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 112, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 112, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_87, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_89, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 112);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 112, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_101, 113);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 113);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 114);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_102, 115);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_103, 116);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_104, 117);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 117, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_105, 117);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_106, 118);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 119, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_105, 119);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 120, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_105, 120);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 121, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_105, 121);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 122, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_105, 122);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 123, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 123, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 123);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 123);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 123, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 123, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 123, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 123, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 123, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 123);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 124, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 124, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 124);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 124);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 124, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 124, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 124, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 124, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 124, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 124);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 125);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 125);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 125, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 125);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_107, 126);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 127, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 127, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 127, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 127, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 127, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 127, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 127, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 127, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 128);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 128, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 128);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 129);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 129, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 129);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_116, 130);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 131, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 131, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 131, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 131, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 131, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 131, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 131, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 131, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 132, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 132, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 132, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 132, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 132, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 132);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 133, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 133, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 133, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 133, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 133, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 133);
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
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 134, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 134);
				}
				
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 135, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 135, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 135, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 135, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 135, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 135);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 136, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 136, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 136, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 136, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 136, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 136);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 137, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 137, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 137, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 137, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 137, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 137);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 138, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 138);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 139, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 139);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 140);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 140);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 140, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 140);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 140);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_118, 141);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 142, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_119, 142);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 143, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_119, 143);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 144, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_119, 144);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 145, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 145, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 145);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 145);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 145, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 145, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 145);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 146, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 146, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 146);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 146);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 146, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 146, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 146);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 147);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 147);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 147, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 147);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_120, 148);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 149, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 149, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 149, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 149, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 149, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 149, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 149, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 149, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 150);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 150, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 150, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 150);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 151);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 151, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 151, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 151);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_121, 152);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 153, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 153, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 153, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 153, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 153, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 153, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 153, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 153, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 154, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 154, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 154);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 155, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 155, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 155);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 156, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 156, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 156);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 157, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 157, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 157);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 158, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 158, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 158);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 159, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 159, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 159, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 159, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 159, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 159);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 159, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 159);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 159);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_122, 160);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_123, 161);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_124, 162);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_125, 163);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_126, 164);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 164, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 164);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_128, 165);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 166, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 166);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 167, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 167);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 168, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 168);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 169, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_127, 169);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 170);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 170);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 170, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 170);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 171);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 171);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 171, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 171);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_16);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 172);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 172);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 172, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 172);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_129, 173);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 174, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 174, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 174, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 174, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 174, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 174, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 174, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 174, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_18);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 175);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 175, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 175);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 176);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 176, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 176);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_130, 177);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 178, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 178, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 178, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 178, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 178, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 178, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 178, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 178, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_19);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 179, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 179);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 180, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 180, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 180, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 180, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 180, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 180, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 180);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 181, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 181, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 181, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 181, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 181, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 181, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 181);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 182, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 182);
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
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 183, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 183, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 183, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 183, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 183, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 183, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
					addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 183);
				}
				
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 184, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 184, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 184, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 184, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 184, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 184, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 184);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 185, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 185, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 185, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 185, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 185, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 185, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 185);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 186, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 186);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 187, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 187);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 188, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 188, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 188, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 188, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 188, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 188);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 188, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 188);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 188);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_131, 189);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_132, 190);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_133, 191);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_134, 192);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 192, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 192);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_135, 193);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 194, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 194);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 195, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 195);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 196, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 196);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 197, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_93, 197);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 198, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 198, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 198, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 198);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 199, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 199, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 199, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 199);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 200, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 200, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 200, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_117, 200);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 201, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 201);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 201);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_136, 202);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_137, 202);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_137, 203);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_137, 204);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_138, 205);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 206);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 206);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 206);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 206);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 206, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 206);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 207);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 207);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 207);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 207);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 207, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 207);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 208);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 208);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 208);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 208);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 208, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 208);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_139, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_140, 209, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 210);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 210);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 210);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 210, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 210, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 210);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 210, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 210, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 210, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 210, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 210);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 211);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 211);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 211);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 211);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 211, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 211);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 212, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 213);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 213);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 213, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 213);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 214);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 214);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 214, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 214);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 215, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 216);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 216, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 216);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 217, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 217, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 217);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 217, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 217, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 217, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 217, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 217);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_141, 218);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 218);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 218);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 218);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 218);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 218, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 218);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 218);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 219);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 219);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 219);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 219);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 219, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 219);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 219);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 220);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 220);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 220);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 220);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 220, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 220);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 220);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 221);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 221);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 221);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 221);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 221, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 221);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 221);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 222);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 222);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 222);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 222);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 222, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 222);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 222);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_139, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_140, 223, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_20);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 224);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 224);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 224);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 224);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 224, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 224);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 224);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 225);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 225);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 225);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 225);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 225, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 225);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 225);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 226, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_21);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 227);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 227, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 227, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 227, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 227, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 227, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 227);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 227, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 227);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 227);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 228);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 228, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 228, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 228, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 228, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 228, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 228);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 228, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 228);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 228);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 229, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_22);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 230, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 230, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 230, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 230, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 230, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 230);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 230, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 230);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 230);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 231);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 231, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 231);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 231);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_142, 232);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_140, 233);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_140, 234);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_143, 235);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_144, 236);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_94, 237);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_95, 237);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 237);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 237);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 237, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 237);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_97, 237);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_98, 237);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 237);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 237);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_145, 238);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_146, 239);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 240, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 240, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 240, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 240, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 240, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 240, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 240, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 240, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 240, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_23);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 241);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 241, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 241);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_150, 242);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 242);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 243);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_151, 244);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_152, 245);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 245, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 245);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_153, 246);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 247);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 247, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 247);
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
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_155, 248);
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
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 249);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 249, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 249);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_154, 250);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 250, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 250);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 251, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 251);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 252, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 252);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 253, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_100, 253);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 254, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 254, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 254);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 255, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 255, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 255);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 256, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 256, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 256);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 257, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 257);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_1, 258, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_0);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_2, 258, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_3, 258, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_4, 258, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_1);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_5, 258, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_2);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_156, 259);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 259);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 260);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_157, 261);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_158, 262);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 263, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 263);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 264, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 264);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 265, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 265);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 266, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 266);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 267, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_17);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 267, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 267, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 267);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_159, 268);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 268);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 269);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_160, 270);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_161, 271);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_162, 272);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_163, 273);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_164, 274);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_165, 275);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_166, 276);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 277, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 277, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 277, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 277);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 278, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 278, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 278, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 278);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 279, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 279, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 279, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 279);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_167, 280);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_168, 281);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_169, 282);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 283, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 283, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 283, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 283, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 283, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 283, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 283, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 283, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 283, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 283);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_171, 284);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 284);
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
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 285, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_26);
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
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_171, 286);
						addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 286);
					}
					
				)
				
			)*			{
				// expected elements (follow set)
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_171, 287);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 287);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 288);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 289);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 289);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 289);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 289);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 289);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 289);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 289, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 289);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_173, 290);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 291, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_28);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 292);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 292);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 292);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 292);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 292);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 292);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 292, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 292);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 293, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 293, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 293, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 293, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 293, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 293, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 293, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 293, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 293);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 294, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 294, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 294, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 294, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 294, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 294, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 294, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 294, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 294);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 295, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 295, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 295, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 295, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 295, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 295, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 295, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 295, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 295);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 296);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 296);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 296);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 296);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 296);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 296);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 296, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 296);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 297);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 297);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 297, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 297, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 297, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 297, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 297, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 297);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 297);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 297, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 297);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 297);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 297);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 297, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 297, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 297, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 297, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 297, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 297, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 297, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 297, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 297);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 298, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_29);
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
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 299);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 299);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 299);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 299);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 299);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 299);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 299);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 299, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
				addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 299);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 300);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 300);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 300);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 300);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 300);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 300);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 300);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 300, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 300);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_175, 301);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 302, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_176, 303);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 304, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 305);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 305);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 305);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 305);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 305);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 305);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 305, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 305);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_177, 306);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 307, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_30);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_178, 308);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 309, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_31);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 310);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 310, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 310, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 310, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 310, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 310, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 310);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 310);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 310, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 310);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 310);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 310);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 310, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 310, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 310, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 310, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 310, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 310, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 310, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 310, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 310);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 311, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_32);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 312);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 312);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 312);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 312);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 312);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 312);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 312, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 312);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 313, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 313, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 313, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 313, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 313, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 313, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 313, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 313, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 313, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_33);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 314);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 314);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 314);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 314);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 314);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 314);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 314, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 314);
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
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 315, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 315, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 315, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 315, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 315, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 315, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 315, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 315, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
			addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 315, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
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
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
		addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 316, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
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
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 317, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
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
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 318, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
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
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
	addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 319, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 320, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 321, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 322, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 323, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 324, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_34);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 325, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 326, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_35);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_42_0_0_0, proxy, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, proxy);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_189, 327);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_190, 328);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_171, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 329, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_179, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_180, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_181, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 329);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_192, 329);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 330, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_36);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 331);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_171, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 332, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_179, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_180, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_181, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 332);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_192, 332);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_171, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 333, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_179, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_180, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_181, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 333);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_192, 333);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_171, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 334, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_179, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_180, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_181, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 334);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_192, 334);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_171, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 335, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_179, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_180, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_181, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 335);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_192, 335);
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
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_171, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 336, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_179, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_180, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_181, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 336);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_192, 336);
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
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_48_0_0_0, resolved, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken) a0, element);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_192, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_171, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 337, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_179, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_180, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_181, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 337);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 337);
}

(
(
a1 = '&' {
if (element == null) {
element = org.sintef.thingml.ThingmlFactory.eINSTANCE.createExternExpression();
incompleteObjects.push(element);
}
collectHiddenTokens(element);
retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_48_0_0_1_0_0_0, null, true);
copyLocalizationInfos((org.antlr.runtime3_3_0.CommonToken)a1, element);
}
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_61, 338, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_62, 338, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_63, 338, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_64, 338, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_65, 338, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_66, 338, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_67, 338, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_68, 338, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_69, 338, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_9, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_37);
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
	retrieveLayoutInformation(element, org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.THINGML_48_0_0_1_0_0_1, a2_0, true);
	copyLocalizationInfos(a2_0, element);
}
}
)
{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_192, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_171, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 339, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_179, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_180, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_181, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 339);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 339);
}

)

)*{
// expected elements (follow set)
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_192, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_12, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_4);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_21, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_22, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_5);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_23, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_6);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_24, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_25, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_7);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_26, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_8);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_27, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_70, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_71, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_72, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_73, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_11);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_74, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_12);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_75, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_13);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_76, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_77, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_78, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_79, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_14);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_80, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_81, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_82, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_83, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_96, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_99, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_147, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_24);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_148, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_25);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_149, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_171, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_170, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_108, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_109, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_110, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_111, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_112, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_113, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_114, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_115, 340, org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.FEATURE_27);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_172, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_174, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_179, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_180, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_181, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_182, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_183, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_184, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_185, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_186, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_187, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_188, 340);
addExpectedElement(org.sintef.thingml.resource.thingml.grammar.ThingmlFollowSetProvider.TERMINAL_191, 340);
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

