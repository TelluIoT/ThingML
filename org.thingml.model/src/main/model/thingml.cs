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
SYNTAXDEF thingml
FOR <http://thingml>
START ThingMLModel


OPTIONS {
	memoize = "true";
	tokenspace = "0";
	usePredefinedTokens = "false";
}

TOKENS{
		DEFINE SL_COMMENT $'//'(~('\n'|'\r'|'\uffff'))* $ ;
		DEFINE ML_COMMENT $'/*'.*'*/'$ ;
		
		DEFINE ANNOTATION $'@'('A'..'Z' | 'a'..'z' | '0'..'9' | '_' | '-' )+$;
		
		DEFINE BOOLEAN_LITERAL $'true'|'false'$;
		
		DEFINE INTEGER_LITERAL $('1'..'9') ('0'..'9')* | '0'$;
		//DEFINE REAL_LITERAL $ (('1'..'9') ('0'..'9')* | '0') '.' ('0'..'9')+ (('e'|'E') ('+'|'-')? ('0'..'9')*)?$;
		DEFINE STRING_LITERAL $'"'('\\'('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')|('\\''u'('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F'))|'\\'('0'..'7')|~('\\'|'"'))*'"'$;

		DEFINE STRING_EXT $'\''('\\'('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')|('\\''u'('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F'))|'\\'('0'..'7')|~('\\'|'\''))*'\''$;
		
		DEFINE T_MULTICAST $'multicast'$;
		DEFINE T_READONLY $'readonly'$;
		
		DEFINE T_ASPECT $'fragment'$;
		DEFINE T_HISTORY $'history'$;
		DEFINE T_SINGLETON $'singleton'$;
		
		DEFINE WHITESPACE $(' '|'\t'|'\f')$;
		DEFINE LINEBREAKS $('\r\n'|'\r'|'\n')$;
		
		//DEFINE MULTIPLICITY $(('0'..'9')+) '\.' '\.' ( ('*') | (('1'..'9')+) )$;
		//DEFINE MULTIPLICITY $( ('*') | (('1'..'9')+) )$;
		
		//DEFINE TEXT $('A'..'Z' | 'a'..'z' | '0'..'9' | '_' | '-' )+$;
		DEFINE TEXT $('A'..'Z' | 'a'..'z' | '0'..'9' | '_' )+ (':' ':' ('A'..'Z' | 'a'..'z' | '0'..'9' | '_')+ )* $;
}

TOKENSTYLES{
	"TEXT" COLOR #000000;
	
	"T_MULTICAST" COLOR #7F0055, BOLD;
	"T_READONLY" COLOR #7F0055, BOLD;

	"SL_COMMENT"  COLOR #00aa33;
	"ML_COMMENT"  COLOR #00aa33;
	
	"ANNOTATION" COLOR #0055bb , BOLD;
	"STRING_LITERAL" COLOR #0055bb;
	
	"thing" COLOR #CC8000, BOLD;
	"datatype" COLOR #CC8000, BOLD;
	"enumeration" COLOR #CC8000, BOLD;
	"T_ASPECT" COLOR #CC8000, BOLD;
	"T_SINGLETON" COLOR #CC8000, BOLD;
	
	"sends" COLOR #7F0055, BOLD;
	"receives" COLOR #7F0055, BOLD;
	
	"STRING_EXT" COLOR #0055bb;
	
	"state" COLOR #CC8000, BOLD;
	"composite" COLOR #CC8000, BOLD;
	"statechart" COLOR #CC8000, BOLD;
	"component" COLOR #CC8000, BOLD;
	"event" COLOR #7F0055, BOLD;	
	"eventref" COLOR #7F0055, BOLD;
	"guard" COLOR #7F0055, BOLD;
	"action" COLOR #7F0055, BOLD;
	"on" COLOR #7F0055, BOLD;
	"entry" COLOR #7F0055, BOLD;
	"exit" COLOR #7F0055, BOLD;
	"send" COLOR #7F0055, BOLD;
	"receive" COLOR #7F0055, BOLD;
	"transition" COLOR #CC8000, BOLD;
	"init" COLOR #CC8000, BOLD;
	"keeps" COLOR #CC8000, BOLD;
	"T_HISTORY" COLOR #CC8000, BOLD;
	"port" COLOR #CC8000, BOLD;	
	"import" COLOR #7F0055, BOLD;
	"->" COLOR #CC8000, BOLD;
	
	"(" COLOR #444444, BOLD;
	")" COLOR #444444, BOLD;
	"{" COLOR #444444, BOLD;
	"}" COLOR #444444, BOLD;
	"[" COLOR #444444, BOLD;
	"]" COLOR #444444, BOLD;
}


RULES{
	
	ThingMLModel::= ( !0 "import" #1 imports[STRING_LITERAL] )* ( !0 (messages | types) )* ;
		
	Message ::= "message" #1 name[]  "(" (parameters (","  parameters)* )? ")"(annotations)* ";"  ;
	
	Thing::= "thing" (#1 fragment[T_ASPECT])? #1 name[] (#1 "includes" #1 includes[] (","  #1 includes[])* )? (annotations)*  !0 "{" (  properties | ports | (behaviour) )* !0 "}" ;
	
	Port ::= !1 "port" #1 name[] (annotations)* !0 "{" ( "receives" #1 receives[] (","  #1 receives[])* | "sends" #1 sends[] (","  #1 sends[])* )* !0 "}" ;
	
//	SoftwareComponent ::= (partial[T_ASPECT])? (singleton[T_SINGLETON])? "component" #1 name[] (annotations)*  !0 "{" (ports | connectors | messages | properties | "receives" #1 receives[] (","  #1 receives[])* | "sends" #1 sends[] (","  #1 sends[])* | (!1 compositeComponent) )* (!1 behaviour)? !0 "}" ;
	
//	SoftwareComponent ::= (partial[T_ASPECT])? (singleton[T_SINGLETON])? "component" #1 name[] (annotations)*  !0 "{" ( messages | properties | "receives" #1 receives[] (","  #1 receives[])* | "sends" #1 sends[] (","  #1 sends[])* | (!1 compositeComponent) )* (!1 behaviour)? !0 "}" ;
	
//	Simulator ::= "simulator" #1 name[] "for" device[] (annotations)* !0 "{" ( messages | properties |  "receives" #1 receives[] (","  #1 receives[])* | "sends" #1 sends[] (","  #1 sends[])* | (!1 compositeComponent) )* (!1 behaviour) !0 "}" ;

	Property::= !1 (changeable[T_READONLY])? "property" #1 name[]  ":"  type[] ("[" lowerBound[INTEGER_LITERAL] ".." upperBound[INTEGER_LITERAL] "]")?(annotations)*;
	
	Dictionary::= !1 (changeable[T_READONLY])? "dictionary" #1 name[]  ":"  indexType[] "->" type[] ("[" lowerBound[INTEGER_LITERAL] ".." upperBound[INTEGER_LITERAL] "]")?(annotations)*;
	
	Parameter::= name[]  ":"  type[];
	
	PrimitiveType::= "datatype" #1 name[] (annotations)* ";" ;
	
	Enumeration::= "enumeration" #1 name[] (annotations)* !0 "{" (literals)* "}" ;
	
	EnumerationLiteral ::= !1 name[] (annotations)* ;
	
	PlatformAnnotation ::= !1 name[ANNOTATION] #1 value[STRING_LITERAL] ;
	
	StateMachine::= !1 "statechart" (#1 name[])? #1 "init" #1 initial[] ("keeps" #1 history[T_HISTORY])? (annotations)* #1 "{" ( !1 properties )* ( !1 "on" #1 "entry" #1 entry )? ( !1 "on" #1 "exit" #1 exit )?  ((!1 substate) | internal)* (!1 region)* !0 "}"  ;
	
	State::= "state" #1 name[] (annotations)* #1 "{" ( !1 properties )* ( !1 "on" #1 "entry" entry )? ( !1 "on" "exit" exit )? ( outgoing | internal )* !0 "}"  ;
	
	CompositeState::= "composite" #1 "state" #1 name[] #1 "init" #1 initial[] ("keeps" #1 history[T_HISTORY])? (annotations)* #1 "{" ( !1 properties )* ( !1 "on" #1 "entry" #1 entry )? ( !1 "on" #1 "exit" #1 exit )? ( outgoing | internal | (!1 substate))* (!1 region)* !0 "}"  ;
	
	ParallelRegion ::= "region" #1 name[] #1 "init" #1 initial[] ("keeps" #1 history[T_HISTORY])? (annotations)* #1 "{"(!1 substate)* !0 "}"  ;
	
	// ComponentReference ::= component[];
		
	//Broadcast::= "broadcast" #1 target "#" message[] "(" (parameters ("," #1 parameters)* )? ")"  ;	

	Transition::= !1 "transition" (#1 name[])? #1 "->" #1 target[] (annotations)* ( !1 "event" #1 event )*  ( !1 "guard" #1 guard)? (!1 "action" #1 action)?  ;

	InternalTransition ::= !1 "internal" (#1 name[])? (annotations)* ( !1 "event" #1 event )*  ( !1 "guard" #1 guard)? (!1 "action" #1 action)?  ;

	ReceiveMessage ::= (name[] #1 ":" #1)? port[] "?" message[] ;
	
	// Connector ::= "connector" #1 "(" port1[] "," #1 port2[] ")" (annotations)* ;
	
	
	
	// CreateAction ::= "create" #1 ref (annotations)* ;
	
	// *********************
	// * Actions
	// *********************
	
	SendAction::= port[] "!" message[] "(" (parameters ("," #1 parameters)* )? ")";
	
	PropertyAssignment ::= property[] #1 "=" #1 expression ; 
	
	ActionBlock::= "do" ( !1 actions )* !0 "end"  ;
	
	ExternStatement::= statement[STRING_EXT] ("&" segments)*;
	
	ConditionalAction ::= "if" #1 "(" #1 condition #1 ")" !1 action;
	
	LoopAction ::= "while" #1 "(" #1 condition #1 ")" !1 action;
	
	PrintAction ::= "print" #1 msg;
	
	ErrorAction ::= "error" #1 msg;
	
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
	
	@Operator(type="binary_right_associative", weight="5", superclass="Expression")
	ModExpression ::= lhs #1 "%" #1 rhs;
	
 	@Operator(type="unary_prefix", weight="6", superclass="Expression")	
	UnaryMinus ::= "-" #1 term;
	
	@Operator(type="unary_prefix", weight="6", superclass="Expression")	
	NotExpression ::= "not" #1 term;
	
	//@Operator(type="primitive", weight="6", superclass="Expression")
	//DictionaryNavigation ::= target "." property[] "[" index "]";
	
	//@Operator(type="unary_postfix", weight="7", superclass="Expression")
	//PropertyNavigation ::= target #0 ("." #0 property[]) ;
	
	@Operator(type="primitive", weight="8", superclass="Expression")
	EventReference ::= msgRef[] "." paramRef[];	
	
	@Operator(type="primitive", weight="8", superclass="Expression")
	ExpressionGroup ::= "(" exp ")";
	 
	@Operator(type="primitive", weight="8", superclass="Expression")
	PropertyReference ::= property[] ;

	@Operator(type="primitive", weight="8", superclass="Expression")
	IntegerLitteral ::= intValue[INTEGER_LITERAL];

	//@Operator(type="primitive", weight="7", superclass="Expression")
	//DoubleLitteral ::= doubleValue[REAL_LITERAL];
	
	@Operator(type="primitive", weight="8", superclass="Expression")
	StringLitteral ::= stringValue[STRING_LITERAL];
	
	@Operator(type="primitive", weight="8", superclass="Expression")
	BooleanLitteral ::= boolValue[BOOLEAN_LITERAL];
	
	@Operator(type="primitive", weight="8", superclass="Expression")
	ExternExpression::= expression[STRING_EXT] ("&" segments)*;
	
	
	
}