/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package compilers.go;

import java.util.List;

import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Element;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.thing.common.NewCommonThingActionCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.ArrayIndex;
import org.thingml.xtext.thingML.ArrayInit;
import org.thingml.xtext.thingML.CastExpression;
import org.thingml.xtext.thingML.CharLiteral;
import org.thingml.xtext.thingML.ConditionalAction;
import org.thingml.xtext.thingML.Decrement;
import org.thingml.xtext.thingML.EnumLiteralRef;
import org.thingml.xtext.thingML.EqualsExpression;
import org.thingml.xtext.thingML.ErrorAction;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ForAction;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.FunctionCallStatement;
import org.thingml.xtext.thingML.GreaterExpression;
import org.thingml.xtext.thingML.GreaterOrEqualExpression;
import org.thingml.xtext.thingML.Increment;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.LoopAction;
import org.thingml.xtext.thingML.LowerExpression;
import org.thingml.xtext.thingML.LowerOrEqualExpression;
import org.thingml.xtext.thingML.NotEqualsExpression;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.PrintAction;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReturnAction;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.StartSession;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.Variable;
import org.thingml.xtext.thingML.VariableAssignment;
import org.thingml.xtext.validation.TypeChecker;

public class GoThingActionCompiler extends NewCommonThingActionCompiler {
	
	private void stringify(Expression expression, Section section, Context ctx) {
		final GoContext gctx = (GoContext) ctx;
		Section cst = section.section("tostring");
		final TypeRef t = TyperHelper.getBroadType(TypeChecker.computeTypeOf(expression));
		if (t == Types.STRING_TYPEREF) {
			generate(expression, cst.section("expression").surroundWith("(", ")"), ctx);
		} else if (t == Types.INTEGER_TYPEREF || t == Types.BYTE_TYPEREF) {
			cst.append("fmt.Sprintf(\"%d\", int64(");				
			generate(expression, cst.section("expression"), ctx);
			cst.append("))");
		} else if (t == Types.BOOLEAN_TYPEREF) {
			cst.append("fmt.Sprintf(\"%t\", ");				
			generate(expression, cst.section("expression"), ctx);
			cst.append(")");
		} else if (t == Types.REAL_TYPEREF) {
			cst.append("fmt.Sprintf(\"%f\", float64(");				
			generate(expression, cst.section("expression"), ctx);
			cst.append("))");
		} else {//hope for the best
			cst.append(gctx.getNameFor(t));
			generate(expression, cst.section("expression").surroundWith("(", ")"), ctx);
		}
	}
	
	@Override
	public void generate(PrintAction action, Section section, Context ctx) {
		GoContext gctx = (GoContext) ctx;
		String pack = "fmt";
		final Thing t = ThingMLHelpers.findContainingThing(action);
		if (AnnotatedElementHelper.isDefined(t, "stdout_sync", "true")) {
			gctx.currentThingContext.addImports("log");
			pack = "log";
		} else {
			gctx.currentThingContext.addImports("fmt");
		}
		Section print = section.section("print");
		if (action.isLine())
			print.append(pack + ".Println(\"\"");
		else
			print.append(pack + ".Print(\"\"");
		for (Expression msg : action.getMsg()) {
			print.append("+");
			stringify(msg, print, gctx);
			//generate(msg, print.section("expression"), ctx);
		}
		print.append(")");					
	}
	
	@Override
	public void generate(ErrorAction action, Section section, Context ctx) {
		GoContext gctx = (GoContext) ctx;
		gctx.currentThingContext.addImports("fmt");
		gctx.currentThingContext.addImports("os");
		Section print = section.section("printerror");
		if (action.isLine())
			print.append("fmt.Fprintln(os.Stderr, \"\"");
		else
			print.append("fmt.Fprint(os.Stderr, \"\"");
		for (Expression msg : action.getMsg()) {
			print.append(" + ");
			stringify(msg, print, gctx);
			//generate(msg, print.section("expression"), ctx);			
		}
		print.append(")");		
	}
	
	@Override
	public void generate(SendAction action, Section section, Context ctx) {
		GoContext gctx = (GoContext)ctx;
				
		final String port_name = gctx.currentThingContext.getThing().getName() + "_" + ThingMLHelpers.findContainingThing(action.getPort()).getName() + "_" + action.getPort().getName();
		
		Section send = section.section("send").lines();
		Section before = send.section("before");
		before.append("state.Send(");
		before.append(port_name).append(", ");
		before.append(gctx.getNameFor(action.getMessage())).append("{");
		List<Parameter> parameters = action.getMessage().getParameters();
		List<Expression> values = action.getParameters();
		if (parameters.size() > 0) {
			Section parsec = send.section("parameters").lines().indent();
			send.section("after").append("})");
			for (int i = 0; i < parameters.size(); i++) {
				Section psec = parsec.section("parameter");
				psec.append(gctx.getNameFor(parameters.get(i))).append(": ");
				if (gctx.shouldAutocast) {
					psec.append(gctx.getNameFor(parameters.get(i).getTypeRef()));
					psec.append("(");
				}
				generate(values.get(i), psec.section("expression"), ctx);
				if (gctx.shouldAutocast) psec.append(")");
				psec.append(",");
			}
		} else {
			before.append("})");
		}
	}
	
	@Override
	public void generate(Increment action, Section section, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		Section line = section.section("increment");
		gctx.variable(action.getVar(), line, ctx);
		line.append("++");
	}
	
	@Override
	public void generate(Decrement action, Section section, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		Section line = section.section("decrement");
		gctx.variable(action.getVar(), line, ctx);
		line.append("--");
	}
	
	@Override
	public void generate(StartSession action, Section section, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		Section line = section.section("startsession");
		line.append("state.fork");
		line.append(gctx.getNameFor(action.getSession()));
		line.append("()");
	}
	
	@Override
	public void generate(FunctionCallStatement action, Section section, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		Section call = section.section("functioncall");
		call.append("state.");
		call.append(gctx.getNameFor(action.getFunction()));
		Section parameters = call.section("parameters").surroundWith("(", ")").joinWith(", ");
		for (Expression p : action.getParameters()) {
			generate(p, parameters.section("expression"), ctx);
		}
	}
	
	@Override
	public void generate(LocalVariable action, Section section, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		// If variable is not used, don't declare it (Go is very strict)
		if (!gctx.checkIfLocalVariableIsUsed(action))
			return;
		
		Section line = section.section("localvariable");
		line.append("var ");
		line.append(gctx.getNameFor(action));
		line.append(" ");
		line.append(gctx.getNameFor(action.getTypeRef()));
		if (action.getInit() != null) {
			line.append(" = ");
			gctx.setCurrentVariableAssignmentType(action.getTypeRef());
			generate(action.getInit(), line.section("initexpression"), ctx);
			gctx.resetCurrentVariableAssignmentType();
		} else if (action.getTypeRef().isIsArray()) {
			line.append(" = make(");
			line.append(gctx.getNameFor(action.getTypeRef()));
			line.append(", ");
			generate(action.getTypeRef().getCardinality(), line.section("arraysizeexpression"), gctx);
			line.append(")");
		}
	}
	
	@Override
	public void generate(ReturnAction action, Section section, Context ctx) {
		Section line = section.section("return");
		line.append("return");
		if (action.getExp() != null) {
			line.append(" ");
			generate(action.getExp(), line.section("expression"), ctx);
		}
	}
	
	@Override
	public void generate(VariableAssignment action, Section section, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		Section line = section.section("variableassignment");
		gctx.variable(action.getProperty(), line, ctx);
		if(action.getIndex() != null) {
			line.append("[");
			generate(action.getIndex(), line.section("indexexpression"), ctx);
			line.append("]");
		}
		line.append(" = ");
		gctx.setCurrentVariableAssignmentType(action.getProperty().getTypeRef());
		generate(action.getExpression(), line.section("expression"), ctx);
		gctx.resetCurrentVariableAssignmentType();
	}
	
	@Override
	public void generate(ConditionalAction action, Section section, Context ctx) {
		Section conditional = section.section("conditional").lines();
		Section ifsection = conditional.section("if");
		ifsection.append("if ");
		generate(action.getCondition(), ifsection.section("expression"), ctx);
		ifsection.append(" {");
		generate(action.getAction(), conditional.section("ifaction").lines().indent(), ctx);
		if (action.getElseAction() != null) {
			Section elsesection = conditional.section("else");
			elsesection.append("} else {");
			generate(action.getElseAction(), conditional.section("elseaction").lines().indent(), ctx);
		}
		conditional.append("}");
	}
	
	@Override
	public void generate(LoopAction action, Section section, Context ctx) {
		Section loop = section.section("loop").lines();
		Section condition = loop.section("condition");
		condition.append("for ");
		generate(action.getCondition(), condition.section("expression"), ctx);
		condition.append(" {");
		generate(action.getAction(), loop.section("loopaction").lines().indent(), ctx);
		loop.append("}");
	}

	
	
	
	
	
	
	
	@Override
	public void generate(FunctionCallExpression expression, Section section, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		Section call = section.section("functioncall");
		call.append(gctx.getCurrentInstanceStateName());
		call.append(".");
		call.append(gctx.getNameFor(expression.getFunction()));
		Section parameters = call.section("parameters").surroundWith("(", ")").joinWith(", ");
		for (Expression p : expression.getParameters()) {
			generate(p, parameters.section("parameter"), ctx);
		}
	}
	
	@Override
	public void generate(EventReference expression, Section section, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		if (gctx.currentThingContext != null) gctx.currentThingContext.messageUsedInTransition = true;
		section.append(gctx.getNameFor(expression.getReceiveMsg()));
		section.append(".");
		section.append(gctx.getNameFor(expression.getParameter()));
	}
	
	@Override
	public void generate(PropertyReference expression, Section section, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		gctx.variable(expression.getProperty(), section, ctx);
	}
	
	@Override
	public void generate(EnumLiteralRef expression, Section section, Context ctx) {
		section.append(expression.getEnum().getName() + "_" + expression.getLiteral().getName());
	}
	
	@Override
	public void generate(ArrayIndex expression, Section section, Context ctx) {
		generate(expression.getArray(), section.section("array"), ctx);
		section.append("[");
		generate(expression.getIndex(), section.section("index"), ctx);
		section.append("]");
	}
	
	@Override
	public void generate(CharLiteral expression, Section section, Context ctx) {
		if (expression.getCharValue() == 0)
			section.append("'\\x00'");
		else
			super.generate(expression, section, ctx);
	}
	
	protected TypeRef tryGetExpressionType(Expression exp, GoContext gctx) {
		// TODO: Some more work might be needed here
		TypeRef result = null;
		if (exp instanceof PropertyReference) {
			result = ((PropertyReference)exp).getProperty().getTypeRef();
		} else if (exp instanceof FunctionCallExpression) {
			result = ((FunctionCallExpression)exp).getFunction().getTypeRef();
		}
		// We can only do logic on primitive types
		if (result != null && !(result.getType() instanceof PrimitiveType)) {
			result = null;
		}
		return result;
	}
	
	protected void castComparisonIfNeccessary(Expression lhs, Expression rhs, Section section, String operator, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		TypeRef lht = tryGetExpressionType(lhs, gctx);
		TypeRef rht = tryGetExpressionType(rhs, gctx);
		if (gctx.shouldAutocast && (lht != null || rht != null)) {
			boolean castLeft;
			// Figure out which type to use
			if (rht == null) castLeft = false;
			else if (lht == null) castLeft = true;
			else {
				// Pick largest type
				long lsize = ((PrimitiveType)lht.getType()).getByteSize();
				long rsize = ((PrimitiveType)rht.getType()).getByteSize();
				if (lsize >= rsize) castLeft = false;
				else castLeft = true;
			}
			// Cast the correct side
			if (castLeft) {
				section.append(gctx.getNameFor(rht));
				section.append("(");
				generate(lhs, section.section("lhs"), gctx);
				section.append(")");
				section.append(operator);
				generate(rhs, section.section("rhs"), gctx);
			} else {
				generate(lhs, section.section("lhs"), gctx);
				section.append(operator);
				section.append(gctx.getNameFor(lht));
				section.append("(");
				generate(rhs, section.section("rhs"), gctx);
				section.append(")");
			}
		} else {
			generate(lhs, section.section("lhs"), gctx);
			section.append(operator);
			generate(rhs, section.section("rhs"), gctx);
		}
	}
	
	@Override
	public void generate(LowerExpression expression, Section section, Context ctx) {
		castComparisonIfNeccessary(expression.getLhs(), expression.getRhs(), section, " < ", ctx);
	}
	
	@Override
	public void generate(GreaterExpression expression, Section section, Context ctx) {
		castComparisonIfNeccessary(expression.getLhs(), expression.getRhs(), section, " > ", ctx);
	}
	
	@Override
	public void generate(LowerOrEqualExpression expression, Section section, Context ctx) {
		castComparisonIfNeccessary(expression.getLhs(), expression.getRhs(), section, " <= ", ctx);
	}
	
	@Override
	public void generate(GreaterOrEqualExpression expression, Section section, Context ctx) {
		castComparisonIfNeccessary(expression.getLhs(), expression.getRhs(), section, " >= ", ctx);
	}
	
	@Override
	public void generate(EqualsExpression expression, Section section, Context ctx) {
		castComparisonIfNeccessary(expression.getLhs(), expression.getRhs(), section, " == ", ctx);
	}
	
	@Override
	public void generate(NotEqualsExpression expression, Section section, Context ctx) {
		castComparisonIfNeccessary(expression.getLhs(), expression.getRhs(), section, " != ", ctx);
	}
	
	@Override
	public void generate(ArrayInit expression, Section section, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		section.append(gctx.getNameFor(gctx.getCurrentVariableAssignmentType()));
		section.append("{ ");
		Section expressions = section.section("expressions").joinWith(", ");
		for (Expression val : expression.getValues()) {
			generate(val, expressions.section("expression"), ctx);
		}
		section.append(" }");
	}
	
	@Override
	public void generate(ForAction action, Section section, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		// TODO: What about types
    	// FIXME: Check that the index and value is actually being used
    	// Check if index and value is used
		Element indexName = new Element("_");
		if (action.getIndex() != null) indexName = gctx.getNameFor(action.getIndex());
    	// Generate for range
    	Section forrange = section.section("forloop").lines();
    	Section before = forrange.section("for");
    	before.append("for ").append(indexName).append(", ").append(gctx.getNameFor(action.getVariable()));
    	before.append(" := range ");
    	gctx.variable(action.getArray().getProperty(), before.section("array"), ctx);
    	before.append(" {");
    	generate(action.getAction(), forrange.section("action").lines().indent(), ctx);
    	forrange.append("}");
	}
	
	@Override
	public void generate(CastExpression expression, Section section, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		Section cst = section.section("cast");
		if (expression.isIsArray())
			cst.append("[]");
		final TypeRef cast = TypeChecker.computeTypeOf(expression);
		if (cast == Types.STRING_TYPEREF) {
			final TypeRef t = TyperHelper.getBroadType(TypeChecker.computeTypeOf(expression.getTerm()));
			if (t == Types.INTEGER_TYPEREF || t == Types.BYTE_TYPEREF) {
				cst.append("fmt.Sprintf(\"%d\", int64(");				
				generate(expression.getTerm(), cst.section("expression"), ctx);
				cst.append("))");
			} else if (t == Types.BOOLEAN_TYPEREF) {
				cst.append("fmt.Sprintf(\"%t\", ");				
				generate(expression.getTerm(), cst.section("expression"), ctx);
				cst.append(")");
			} else if (t == Types.REAL_TYPEREF) {
				cst.append("fmt.Sprintf(\"%f\", float64(");				
				generate(expression.getTerm(), cst.section("expression"), ctx);
				cst.append("))");
			} else {//hope for the best
				cst.append(gctx.getNameFor(expression.getType()));
				generate(expression.getTerm(), cst.section("expression").surroundWith("(", ")"), ctx);
			}			
		} else {		
			cst.append(gctx.getNameFor(expression.getType()));
			generate(expression.getTerm(), cst.section("expression").surroundWith("(", ")"), ctx);
		}
	}
}
