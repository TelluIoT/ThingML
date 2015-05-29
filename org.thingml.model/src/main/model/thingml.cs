SYNTAXDEF thingml
FOR <http://thingml>
START ThingMLModel


OPTIONS {
	
	reloadGeneratorModel = "true"; 
	memoize = "true";
	tokenspace = "0";
	usePredefinedTokens = "false";
	srcFolder = "src/main/java";
	
	
	// PROCESS TO RE-GENERATE THE THINGML COMPILER FROM THIS FILE
	//
	// The code should be generated *2 times*, once for eclipse and once for standalone
	// The generated code shares all the code that is manually edited in (src/main/java) so there is no need to create the resolvers twice.
	// Bellow are the different options to use.
	// Generate the standalone version first and then the eclipse version.
	
	// 1. FOR STANDALONE
	srcGenFolder = "src/main/java-gen-standalone";
	generateUIPlugin = "false";
	removeEclipseDependentCode = "true";
	
	// 2. FOR ECLIPSE Comment the lines bellow
	//srcGenFolder = "src/main/java-gen";
	
	// IMPORTANT: In the generated eclipse plugins it is required to change the Vendor to SINTEF and the Version from "1.0.0" to "0.x.0.qualifier"
}

TOKENS{
		DEFINE SL_COMMENT $'//'(~('\n'|'\r'|'\uffff'))* $ ;
		DEFINE ML_COMMENT $'/*'.*'*/'$ ;
		
		DEFINE ANNOTATION $'@'('A'..'Z' | 'a'..'z' | '0'..'9' | '_' )+$;
		
		DEFINE BOOLEAN_LITERAL $'true'|'false'$;
		
		DEFINE INTEGER_LITERAL $('1'..'9') ('0'..'9')* | '0'$;
		//DEFINE REAL_LITERAL $ (('1'..'9') ('0'..'9')* | '0') '.' ('0'..'9')+ (('e'|'E') ('+'|'-')? ('0'..'9')*)?$;
		DEFINE STRING_LITERAL $'"'('\\'('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')|('\\''u'('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F'))|'\\'('0'..'7')|~('\\'|'"'))*'"'$;

		DEFINE STRING_EXT $'\''('\\'('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')|('\\''u'('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F'))|'\\'('0'..'7')|~('\\'|'\''))*'\''$;
		
		DEFINE T_READONLY $'readonly'$;
		
		DEFINE T_OPTIONAL $'optional'$;
		
		DEFINE T_ASPECT $'fragment'$;
		DEFINE T_HISTORY $'history'$;
		
		DEFINE WHITESPACE $(' '|'\t'|'\f')$;
		DEFINE LINEBREAKS $('\r\n'|'\r'|'\n')$;
		
		//DEFINE MULTIPLICITY $(('0'..'9')+) '\.' '\.' ( ('*') | (('1'..'9')+) )$;
		//DEFINE MULTIPLICITY $( ('*') | (('1'..'9')+) )$;
		
		DEFINE TEXT $('A'..'Z' | 'a'..'z' | '0'..'9' | '_' )+$;
		//DEFINE TEXT $('A'..'Z' | 'a'..'z' | '0'..'9' | '_' )+ (':' ':' ('A'..'Z' | 'a'..'z' | '0'..'9' | '_')+ )* $;
}

TOKENSTYLES{
	
	// Default text
	"TEXT" COLOR #222222;
	
	// Comments
	"SL_COMMENT"  COLOR #666666;
	"ML_COMMENT"  COLOR #666666;
	
	// Annotations & ext
	"ANNOTATION" COLOR #0055bb , BOLD;
	"STRING_EXT" COLOR #0055bb;
	"&" COLOR #0055bb, BOLD;
	
	// Literals
	"STRING_LITERAL" COLOR #0055bb;
	"INTEGER_LITERAL" COLOR #0055bb;
	"BOOLEAN_LITERAL" COLOR #0055bb, BOLD;
	
	// Definition of types and messages
	"T_READONLY" COLOR #CC8000, BOLD;
	"thing" COLOR #CC8000, BOLD;
	"includes" COLOR #CC8000, BOLD;
	"datatype" COLOR #CC8000, BOLD;
	"enumeration" COLOR #CC8000, BOLD;
	"sends" COLOR #CC8000, BOLD;
	"receives" COLOR #CC8000, BOLD;
	"port" COLOR #CC8000, BOLD;
	"provided" COLOR #CC8000, BOLD;	
	"required" COLOR #CC8000, BOLD;
	"T_OPTIONAL" COLOR #CC8000, BOLD;
	"message" COLOR #CC8000, BOLD;	
	"property" COLOR #CC8000, BOLD;	
	
	
	// State machines
	"state" COLOR #A22000, BOLD;
	"composite" COLOR #A22000, BOLD;
	"statechart" COLOR #A22000, BOLD;
	"event" COLOR #A22000, BOLD;	
	"guard" COLOR #A22000, BOLD;
	"action" COLOR #A22000, BOLD;
	"before" COLOR #A22000, BOLD;
	"after" COLOR #A22000, BOLD;
	"on" COLOR #A22000, BOLD;
	"entry" COLOR #A22000, BOLD;
	"exit" COLOR #A22000, BOLD;
	"region" COLOR #A22000, BOLD;
	"internal" COLOR #A22000, BOLD;
	"transition" COLOR #A22000, BOLD;
	"init" COLOR #A22000, BOLD;
	"keeps" COLOR #A22000, BOLD;
	"T_HISTORY" COLOR #A22000, BOLD;
	"->" COLOR #A22000, BOLD;
	
	// Action language
	"var" COLOR #444444, BOLD;
	"function" COLOR #444444, BOLD;
	"return" COLOR #444444, BOLD;
	"do" COLOR #444444, BOLD;
	"end" COLOR #444444, BOLD;
	"if" COLOR #444444, BOLD;
	"while" COLOR #444444, BOLD;
	"print" COLOR #444444, BOLD;
	"error" COLOR #444444, BOLD;
	"not" COLOR #444444, BOLD;
	"and" COLOR #444444, BOLD;
	"or" COLOR #444444, BOLD;
	
	// Configurations and Instances
	"configuration" COLOR #007F55, BOLD;
	"instance" COLOR #007F55, BOLD;	
	"connector" COLOR #007F55, BOLD;
	"group" COLOR #007F55, BOLD;	
	"=>" COLOR #007F55, BOLD;

	// Special keywords
	"T_ASPECT" COLOR #444444, BOLD;
	"includes" COLOR #444444, BOLD;
	"import" COLOR #444444, BOLD;
	"set" COLOR #444444, BOLD;
	
	// CEP
	"stream" COLOR #444444, BOLD;
	"input" COLOR #A22000, BOLD;
	"output" COLOR #A22000, BOLD;
	
	"(" COLOR #444444, BOLD;
	")" COLOR #444444, BOLD;
	"{" COLOR #444444, BOLD;
	"}" COLOR #444444, BOLD;
	"[" COLOR #444444, BOLD;
	"]" COLOR #444444, BOLD;
	
	"!" COLOR #444444, BOLD;
	"?" COLOR #444444, BOLD;
	"." COLOR #444444, BOLD;
	":" COLOR #444444, BOLD;
	
}


RULES {
	
	ThingMLModel::= ( !0 "import" #1 imports[STRING_LITERAL] )* ( !0 (types | configs) )* ;
		
	Message ::= "message" #1 name[]  "(" (parameters ("," #1  parameters)* )? ")"(annotations)* ";"  ;
	
	Function ::= "function" #1 name[]  "(" (parameters ("," #1  parameters)* )? ")"(annotations)* ( #1 ":" #1 type[] ( "[" cardinality "]")? )? #1 body ;
	
	Thing::= "thing" (#1 fragment[T_ASPECT])? #1 name[] (#1 "includes" #1 includes[] (","  #1 includes[])* )? (annotations)*  !0 "{" (  messages | functions | properties | assign | ports | behaviour | streams)* !0 "}" ;
	
	RequiredPort ::= !1 (optional[T_OPTIONAL])? "required" #1 "port" #1 name[] (annotations)* !0 "{" ( "receives" #1 receives[] (","  #1 receives[])* | "sends" #1 sends[] (","  #1 sends[])* )* !0 "}" ;

	ProvidedPort ::= !1  "provided" #1 "port" #1 name[] (annotations)* !0 "{" ( "receives" #1 receives[] (","  #1 receives[])* | "sends" #1 sends[] (","  #1 sends[])* )* !0 "}" ;
	
	Property::= !1 (changeable[T_READONLY])? "property" #1 name[] #1 ":" #1 type[]  ( "[" cardinality "]")? (#1 "=" #1 init)?  (annotations)*;
	
	//("[" lowerBound[INTEGER_LITERAL] ".." upperBound[INTEGER_LITERAL] "]")?
	
//	Dictionary::= !1 (changeable[T_READONLY])? "dictionary" #1 name[]  ":"  indexType[] "->" type[] ("[" lowerBound[INTEGER_LITERAL] ".." upperBound[INTEGER_LITERAL] "]")?(annotations)*;
	
	Parameter::= name[]  ":"  type[] ( "[" cardinality "]")?;
	
	PrimitiveType::= "datatype" #1 name[] (annotations)* ";" ;
	
	Enumeration::= "enumeration" #1 name[] (annotations)* !0 "{" (literals)* "}" ;
	
	EnumerationLiteral ::= !1 name[] (annotations)* ;
	 
	PlatformAnnotation ::= !1 name[ANNOTATION] #1 value[STRING_LITERAL] ;
	
	StateMachine::= !1 "statechart" (#1 name[])? #1 "init" #1 initial[] ("keeps" #1 history[T_HISTORY])? (annotations)* #1 "{" ( !1 properties )* ( !1 "on" #1 "entry" #1 entry )? ( !1 "on" #1 "exit" #1 exit )?  ((!1 substate) | internal)* (!1 region)* !0 "}"  ;
	
	State::= "state" #1 name[] (annotations)* #1 "{" ( !1 properties )* ( !1 "on" #1 "entry" entry )? ( !1 "on" "exit" exit )? ( outgoing | internal )* !0 "}"  ;
	
	CompositeState::= "composite" #1 "state" #1 name[] #1 "init" #1 initial[] ("keeps" #1 history[T_HISTORY])? (annotations)* #1 "{" ( !1 properties )* ( !1 "on" #1 "entry" #1 entry )? ( !1 "on" #1 "exit" #1 exit )? ( outgoing | internal | (!1 substate))* (!1 region)* !0 "}"  ;
	
	ParallelRegion ::= "region" #1 name[] #1 "init" #1 initial[] ("keeps" #1 history[T_HISTORY])? (annotations)* #1 "{"(!1 substate)* !0 "}"  ;
	
	Transition::= !1 "transition" (#1 name[])? #1 "->" #1 target[] (annotations)* ( !1 "event" #1 event )*  ( !1 "guard" #1 guard)? (!1 "action" #1 action)? (!1 "before" #1 before)? (!1 "after" #1 after)? ;

	InternalTransition ::= !1 "internal" (#1 name[])? (annotations)* ( !1 "event" #1 event )*  ( !1 "guard" #1 guard)? (!1 "action" #1 action)?  ;

	ReceiveMessage ::= (name[] #1 ":" #1)? port[] "?" message[] ;
	
	PropertyAssign ::= "set" #1 property[] ("[" index "]")* #1 "=" #1 init ; 
	
	// *******************************
	// * CEP
	// *******************************
	StreamExpression ::= name[] ":" expression;
	StreamOutput ::= port[] "!" message[] "(" (parameters[] ("," #1 parameters[])*)? ")";
	
	//Stream ::= "stream" #1 name[] "do" 
	//			(!1 "select" #1 ( selection ("," #1 selection)* )?)?
	//			!1 "from" #1 inputs ("," #1 inputs)*
	//			!1 "action" #1 output
	//			"end";
				
	SimpleStream ::= "stream" #1 name[] #1 "do"
					 (!1 "select" #1 ( selection ("," #1 selection)* )?)?
					 !1 "from" #1 inputs
					 !1 "action" #1 output
					 "end";

	MergedStream ::= "stream" #1 name[] #1 "do"
					 (!1 "select" #1 ( selection ("," #1 selection)* )?)?
					 !1 "from" #1 inputs (#1 "|" #1 inputs)*
					 !1 "action" #1 output
					 "end";
					 
	JoinedStream ::= "stream" #1 name[] #1 "do"
					 (!1 "select" #1 ( selection ("," #1 selection)* )?)?
					 !1 "from" #1 inputs (#1 "&" #1 inputs)*
					 !1 "action" #1 output
					 "end";
	// *******************************
	// * Configurations and Instances
	// *******************************
	
	Configuration ::= "configuration" (#1 fragment[T_ASPECT])? #1 name[] (annotations)*  !0 "{" (  instances | connectors | configs | propassigns )* !0 "}" ;
	
	ConfigInclude ::= "group" #1 name[] #1 ":" #1 config[] (annotations)* !0 ;
	
	Instance ::= "instance" #1 (name[] #1)? ":" #1 type[] (annotations)*  ; // !0 (  assign )* !0
	
	Connector ::= "connector" #1 (name[] #1)? cli "." required[] "=>" srv "." provided[] (!0 annotations)*;
	
	ConfigPropertyAssign ::= "set" instance "." property[] ("[" index "]")* #1 "=" #1 init;
	
	InstanceRef ::= (config[] ".")* instance[];

	// *********************
	// * Actions
	// *********************
	
	SendAction::= port[] "!" message[] "(" (parameters ("," #1 parameters)* )? ")";
	
	VariableAssignment ::= property[] #1 ("[" index "]")* "=" #1 expression ; 
	
	ActionBlock::= "do" ( !1 actions  )* !0 "end"  ;
	
	LocalVariable::= !1 (changeable[T_READONLY])? "var" #1 name[] #1 ":" #1 type[] ( "[" cardinality "]")? (#1 "=" #1 init)?  (annotations)*;
	
	ExternStatement::= statement[STRING_EXT] ("&" segments)*;
	
	ConditionalAction ::= "if" #1 "(" #1 condition #1 ")" !1 action (!1 "else" !1 elseAction)?;
	
	LoopAction ::= "while" #1 "(" #1 condition #1 ")" !1 action;
	
	PrintAction ::= "print" #1 msg;
	
	ErrorAction ::= "error" #1 msg;
	
	ReturnAction ::= "return" #1 exp;
	
	FunctionCallStatement ::= function[] "(" (parameters ("," #1 parameters)* )? ")";
	
	// *********************
	// * The Expressions
	// *********************
	
	@Operator(type="binary_left_associative", weight="1", superclass="Expression")
	OrExpression ::= lhs #1 "or" #1 rhs;
	
	@Operator(type="binary_left_associative", weight="2", superclass="Expression")
	AndExpression ::= lhs #1 "and" #1 rhs;
	
	
	@Operator(type="binary_left_associative", weight="3", superclass="Expression")
	LowerExpression ::= lhs #1 "<" #1  rhs;
	
	@Operator(type="binary_left_associative", weight="3", superclass="Expression")
	GreaterExpression ::= lhs #1 ">" #1  rhs;
	
	@Operator(type="binary_left_associative", weight="3", superclass="Expression")
	EqualsExpression ::= lhs #1 "==" #1  rhs;
	
	@Operator(type="binary_left_associative", weight="4", superclass="Expression")
	PlusExpression ::= lhs #1 "+" #1  rhs;
	
	@Operator(type="binary_left_associative", weight="4", superclass="Expression")
	MinusExpression ::= lhs #1 "-" #1 rhs;
	
	@Operator(type="binary_left_associative", weight="5", superclass="Expression")
	TimesExpression ::= lhs #1 "*" #1 rhs;
	
	@Operator(type="binary_left_associative", weight="5", superclass="Expression")
	DivExpression ::= lhs #1 "/" #1 rhs;
	
	@Operator(type="binary_right_associative", weight="6", superclass="Expression")
	ModExpression ::= lhs #1 "%" #1 rhs;
	
 	@Operator(type="unary_prefix", weight="7", superclass="Expression")	
	UnaryMinus ::= "-" term;
	
	@Operator(type="unary_prefix", weight="7", superclass="Expression")	
	NotExpression ::= "not" #1 term;
	
	@Operator(type="primitive", weight="9", superclass="Expression")
	EventReference ::= msgRef[] "." paramRef[];	
	
	@Operator(type="primitive", weight="9", superclass="Expression")
	ExpressionGroup ::= "(" exp ")";
	 
	@Operator(type="primitive", weight="9", superclass="Expression")
	PropertyReference ::= property[] ;

	@Operator(type="primitive", weight="9", superclass="Expression")
	IntegerLiteral ::= intValue[INTEGER_LITERAL];
	
	@Operator(type="primitive", weight="9", superclass="Expression")
	StringLiteral ::= stringValue[STRING_LITERAL];
	
	@Operator(type="primitive", weight="9", superclass="Expression")
	BooleanLiteral ::= boolValue[BOOLEAN_LITERAL];
	
	@Operator(type="primitive", weight="9", superclass="Expression")
	EnumLiteralRef ::= enum[] ":" literal[];
	
	@Operator(type="unary_postfix", weight="8", superclass="Expression")
	ArrayIndex ::= array "[" index "]";
	
	@Operator(type="primitive", weight="9", superclass="Expression")
	FunctionCallExpression ::= function[] "(" (parameters ("," #1 parameters)* )? ")";
	
	@Operator(type="primitive", weight="9", superclass="Expression")
	ExternExpression::= expression[STRING_EXT] ("&" segments)*;
	
	
	
}