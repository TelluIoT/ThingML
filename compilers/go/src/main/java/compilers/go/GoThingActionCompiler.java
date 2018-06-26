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
import org.thingml.compilers.thing.common.CommonThingActionCompiler;
import org.thingml.xtext.thingML.ArrayIndex;
import org.thingml.xtext.thingML.ArrayInit;
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
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.Variable;
import org.thingml.xtext.thingML.VariableAssignment;

public class GoThingActionCompiler extends CommonThingActionCompiler {
	public void variable(Variable variable, StringBuilder builder, Context ctx) {	
		GoContext gctx = (GoContext)ctx;
		if (variable instanceof LocalVariable)
			builder.append(variable.getName());
		else if (variable instanceof Property) {
			if (gctx.currentThingContext != null) gctx.currentThingContext.instanceUsedInInitialisation = true;
			builder.append(gctx.getCurrentInstanceStateName()).append(".").append(variable.getName());
		}
		else if (variable instanceof Parameter)
			builder.append(variable.getName());
	}
	
	@Override
	public void generate(PrintAction action, StringBuilder builder, Context ctx) {
		GoContext gctx = (GoContext) ctx;
		gctx.currentThingContext.addImports("fmt");
		for (Expression msg : action.getMsg()) {
			builder.append("fmt.Print(");
			generate(msg, builder, ctx);
			builder.append(")\n");
		}
		if (action.isLine())
			builder.append("fmt.Println()\n");
	}
	
	
	@Override
	public void generate(ErrorAction action, StringBuilder builder, Context ctx) {
		GoContext gctx = (GoContext) ctx;
		gctx.currentThingContext.addImports("fmt");
		gctx.currentThingContext.addImports("os");
		for (Expression msg : action.getMsg()) {
			builder.append("fmt.Fprint(os.Stderr, ");
			generate(msg, builder, ctx);
			builder.append(")\n");
		}
		if (action.isLine())
			builder.append("fmt.Fprintln(os.Stderr)\n");
	}
	
	@Override
	public void generate(SendAction action, StringBuilder builder, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		builder.append("state.Send(");
		builder.append(gctx.getPortName(action.getPort())).append(", ");
		builder.append(gctx.getMessageName(action.getMessage())).append("{");
		List<Parameter> parameters = action.getMessage().getParameters();
		List<Expression> values = action.getParameters();
		if (parameters.size() > 0) builder.append("\n");
		for (int i = 0; i < parameters.size(); i++) {
			builder.append("\t");
			builder.append(parameters.get(i).getName()).append(": ");
			if (gctx.shouldAutocast) {
				builder.append(gctx.getTypeRef(parameters.get(i).getTypeRef()));
				builder.append("(");
			}
			generate(values.get(i), builder, ctx);
			if (gctx.shouldAutocast) builder.append(")");
			builder.append(",\n");
		}
		builder.append("})\n");
	}
	
	@Override
	public void generate(StartSession action, StringBuilder builder, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		builder.append("state.fork");
		builder.append(gctx.getStateContainerName(action.getSession()));
		builder.append("()\n");
	}
	
	@Override
	public void generate(Increment action, StringBuilder builder, Context ctx) {
		variable(action.getVar(), builder, ctx);
		builder.append("++\n");
	}
	
	@Override
	public void generate(Decrement action, StringBuilder builder, Context ctx) {
		variable(action.getVar(), builder, ctx);
		builder.append("--\n");
	}
	
	@Override
	public void generate(LocalVariable action, StringBuilder builder, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		// If variable is not used, don't declare it (Go is very strict)
		if (!gctx.checkIfLocalVariableIsUsed(action))
			return;
		
		builder.append("var ");
		builder.append(action.getName());
		builder.append(" ");
		builder.append(gctx.getTypeRef(action.getTypeRef()));
		if (action.getInit() != null) {
			builder.append(" = ");
			gctx.setCurrentVariableAssignmentType(action.getTypeRef());
			generate(action.getInit(), builder, ctx);
			gctx.resetCurrentVariableAssignmentType();
		} else if (action.getTypeRef().isIsArray()) {
			builder.append(" = make(");
			builder.append(gctx.getTypeRef(action.getTypeRef()));
			builder.append(", ");
			generate(action.getTypeRef().getCardinality(), builder, gctx);
			builder.append(")");
		}
		builder.append("\n");
	}
	
	@Override
	public void generate(ReturnAction action, StringBuilder builder, Context ctx) {
		builder.append("return");
		if (action.getExp() != null) {
			builder.append(" ");
			generate(action.getExp(), builder, ctx);
		}
		builder.append("\n");
	}
	
	@Override
	public void generate(VariableAssignment action, StringBuilder builder, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		variable(action.getProperty(), builder, ctx);
		if(action.getIndex() != null) {
			builder.append("[");
			generate(action.getIndex(), builder, ctx);
			builder.append("]");
		}
		builder.append(" = ");
		gctx.setCurrentVariableAssignmentType(action.getProperty().getTypeRef());
		generate(action.getExpression(), builder, ctx);
		gctx.resetCurrentVariableAssignmentType();
		builder.append("\n");
	}
	
	@Override
	public void generate(ConditionalAction action, StringBuilder builder, Context ctx) {
		builder.append("if ");
		generate(action.getCondition(), builder, ctx);
		builder.append(" {\n");
		generate(action.getAction(), builder, ctx);
		builder.append("}");
		if (action.getElseAction() != null) {
            builder.append(" else {\n");
            generate(action.getElseAction(), builder, ctx);
            builder.append("}");
        }
		builder.append("\n");
	}
	
	@Override
	public void generate(LoopAction action, StringBuilder builder, Context ctx) {
		builder.append("for ");
		generate(action.getCondition(), builder, ctx);
		builder.append(" {\n");
		generate(action.getAction(), builder, ctx);
		builder.append("}\n");
	}
	
	@Override
	public void generate(FunctionCallStatement action, StringBuilder builder, Context ctx) {
		builder.append("state.");
		builder.append(action.getFunction().getName());
		builder.append("(");
		boolean first = true;
		for (Expression p : action.getParameters()) {
			if (!first) builder.append(", ");
			first = false;
			generate(p, builder, ctx);
		}
		builder.append(")\n");
	}
	
	@Override
	public void generate(FunctionCallExpression expression, StringBuilder builder, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		builder.append(gctx.getCurrentInstanceStateName());
		builder.append(".");
		builder.append(expression.getFunction().getName());
		builder.append("(");
		boolean first = true;
		for (Expression p : expression.getParameters()) {
			if (!first) builder.append(", ");
			first = false;
			generate(p, builder, ctx);
		}
		builder.append(")");
	}
	
	@Override
	public void generate(EventReference expression, StringBuilder builder, Context ctx) {
		GoContext gctx = (GoContext)ctx;
		if (gctx.currentThingContext != null) gctx.currentThingContext.messageUsedInTransition = true;
		builder.append(expression.getReceiveMsg().getName());
		builder.append(".");
		builder.append(expression.getParameter().getName());
	}
	
	@Override
	public void generate(PropertyReference expression, StringBuilder builder, Context ctx) {
		variable(expression.getProperty(), builder, ctx);
	}
	
	@Override
	public void generate(EnumLiteralRef expression, StringBuilder builder, Context ctx) {
		builder.append(expression.getEnum().getName());
		builder.append(expression.getLiteral().getName());
	}
	
	@Override
	public void generate(ArrayIndex expression, StringBuilder builder, Context ctx) {
		generate(expression.getArray(), builder, ctx);
        builder.append("[");
        generate(expression.getIndex(), builder, ctx);
        builder.append("]");
	}
	
	@Override
	public void generate(CharLiteral expression, StringBuilder builder, Context ctx) {
		if (expression.getCharValue() == 0)
			builder.append("'\\x00'");
		else
			super.generate(expression, builder, ctx);
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
	
	protected void castComparisonIfNeccessary(Expression lhs, Expression rhs, StringBuilder builder, String operator, Context ctx) {
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
				builder.append(gctx.getTypeRef(rht));
				builder.append("(");
				generate(lhs, builder, gctx);
				builder.append(")");
				builder.append(operator);
				generate(rhs, builder, gctx);
			} else {
				generate(lhs, builder, gctx);
				builder.append(operator);
				builder.append(gctx.getTypeRef(lht));
				builder.append("(");
				generate(rhs, builder, gctx);
				builder.append(")");
			}
		} else {
			generate(lhs, builder, ctx);
			builder.append(operator);
			generate(rhs, builder, ctx);
		}
	}
	
	@Override
    public void generate(LowerExpression expression, StringBuilder builder, Context ctx) {
		castComparisonIfNeccessary(expression.getLhs(), expression.getRhs(), builder, " < ", ctx);
    }

    @Override
    public void generate(GreaterExpression expression, StringBuilder builder, Context ctx) {
    	castComparisonIfNeccessary(expression.getLhs(), expression.getRhs(), builder, " > ", ctx);
    }

    @Override
    public void generate(LowerOrEqualExpression expression, StringBuilder builder, Context ctx) {
    	castComparisonIfNeccessary(expression.getLhs(), expression.getRhs(), builder, " <= ", ctx);
    }

    @Override
    public void generate(GreaterOrEqualExpression expression, StringBuilder builder, Context ctx) {
    	castComparisonIfNeccessary(expression.getLhs(), expression.getRhs(), builder, " >= ", ctx);
    }

    @Override
    public void generate(EqualsExpression expression, StringBuilder builder, Context ctx) {
    	castComparisonIfNeccessary(expression.getLhs(), expression.getRhs(), builder, " == ", ctx);
    }
    
    @Override
    public void generate(NotEqualsExpression expression, StringBuilder builder, Context ctx) {
    	castComparisonIfNeccessary(expression.getLhs(), expression.getRhs(), builder, " != ", ctx);
    }
    
    @Override
    public void generate(ArrayInit expression, StringBuilder builder, Context ctx) {
    	GoContext gctx = (GoContext)ctx;
    	builder.append(gctx.getTypeRef(gctx.getCurrentVariableAssignmentType()));
    	builder.append("{");
    	boolean first = true;
    	for (Expression val : expression.getValues()) {
    		if (first) first = false;
    		else builder.append(", ");
    		generate(val, builder, ctx);
    	}
    	builder.append("}");
    }
    
    @Override
    public void generate(ForAction action, StringBuilder builder, Context ctx) {
    	// TODO: What about types
    	// FIXME: Check that the index and value is actually being used
    	// Check if index is used
    	String indexName = "_";
    	if (action.getIndex() != null) indexName = action.getIndex().getName();
    	// Generate for range
    	builder.append("for ").append(indexName).append(", ").append(action.getVariable().getName());
    	builder.append(" := range ");
    	variable(action.getArray().getProperty(), builder, ctx);
    	builder.append(" {\n");
    	generate(action.getAction(), builder, ctx);
    	builder.append("}\n");
    }
}
