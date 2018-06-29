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
package org.thingml.compilers.thing;

import org.eclipse.emf.ecore.EObject;
import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Section;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.AndExpression;
import org.thingml.xtext.thingML.ArrayIndex;
import org.thingml.xtext.thingML.ArrayInit;
import org.thingml.xtext.thingML.BooleanLiteral;
import org.thingml.xtext.thingML.ByteLiteral;
import org.thingml.xtext.thingML.CastExpression;
import org.thingml.xtext.thingML.CharLiteral;
import org.thingml.xtext.thingML.ConditionalAction;
import org.thingml.xtext.thingML.Decrement;
import org.thingml.xtext.thingML.DivExpression;
import org.thingml.xtext.thingML.DoubleLiteral;
import org.thingml.xtext.thingML.EnumLiteralRef;
import org.thingml.xtext.thingML.EqualsExpression;
import org.thingml.xtext.thingML.ErrorAction;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExpressionGroup;
import org.thingml.xtext.thingML.ExternExpression;
import org.thingml.xtext.thingML.ExternStatement;
import org.thingml.xtext.thingML.ForAction;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.FunctionCallStatement;
import org.thingml.xtext.thingML.GreaterExpression;
import org.thingml.xtext.thingML.GreaterOrEqualExpression;
import org.thingml.xtext.thingML.Increment;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.LoopAction;
import org.thingml.xtext.thingML.LowerExpression;
import org.thingml.xtext.thingML.LowerOrEqualExpression;
import org.thingml.xtext.thingML.MinusExpression;
import org.thingml.xtext.thingML.ModExpression;
import org.thingml.xtext.thingML.NotEqualsExpression;
import org.thingml.xtext.thingML.NotExpression;
import org.thingml.xtext.thingML.OrExpression;
import org.thingml.xtext.thingML.PlusExpression;
import org.thingml.xtext.thingML.PrintAction;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReturnAction;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.StartSession;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.TimesExpression;
import org.thingml.xtext.thingML.UnaryMinus;
import org.thingml.xtext.thingML.VariableAssignment;

public class NewThingActionCompiler {
	
	public void generate(EObject object, Section section, Context ctx) {
		if (object == null) {
			return;
		} else if (object instanceof Action) {
			generate((Action) object, section, ctx);
		} else if (object instanceof Expression) {
			generate((Expression) object, section, ctx);
		} else {
			throw (new UnsupportedOperationException("This action/expression (" + object.getClass().getName() + ") is unknown... Please update your action compilers as a new action/expression might have been introduced in ThingML"));
		}
	}
	
	// ThingML Actions
    public void generate(Action action, Section section, Context ctx) {
        if (action == null)
            return;
        if (action instanceof SendAction)
            generate((SendAction) action, section, ctx);
        else if (action instanceof VariableAssignment)
            generate((VariableAssignment) action, section, ctx);
        else if (action instanceof ActionBlock)
            generate((ActionBlock) action, section, ctx);
        else if (action instanceof ExternStatement)
            generate((ExternStatement) action, section, ctx);
        else if (action instanceof ConditionalAction)
            generate((ConditionalAction) action, section, ctx);
        else if (action instanceof LoopAction)
            generate((LoopAction) action, section, ctx);
        else if (action instanceof PrintAction)
            generate((PrintAction) action, section, ctx);
        else if (action instanceof ErrorAction)
            generate((ErrorAction) action, section, ctx);
        else if (action instanceof ReturnAction)
            generate((ReturnAction) action, section, ctx);
        else if (action instanceof LocalVariable)
            generate((LocalVariable) action, section, ctx);
        else if (action instanceof FunctionCallStatement)
            generate((FunctionCallStatement) action, section, ctx);
        else if (action instanceof Increment)
            generate((Increment) action, section, ctx);
        else if (action instanceof Decrement)
            generate((Decrement) action, section, ctx);
        else if (action instanceof StartSession) 
            generate((StartSession) action, section, ctx);
        else if (action instanceof ForAction) 
            generate((ForAction) action, section, ctx);        
        else {
            throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is unknown... Please update your action compilers as a new action/expression might have been introduced in ThingML"));
        }
    }
    
    public void generate(SendAction action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(StartSession action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }
   
    public void generate(VariableAssignment action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ActionBlock action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ExternStatement action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ConditionalAction action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(LoopAction action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(PrintAction action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ErrorAction action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ReturnAction action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(LocalVariable action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(FunctionCallStatement action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(Increment action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(Decrement action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }
    
    public void generate(ForAction action, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }
    
    //ThingML Expressions
    public void generate(Expression expression, Section section, Context ctx) {
        if (expression instanceof ArrayIndex) {
            generate((ArrayIndex) expression, section, ctx);
        } else if (expression instanceof ArrayInit) {
            generate((ArrayInit) expression, section, ctx);
        } else if (expression instanceof OrExpression) {
            generate((OrExpression) expression, section, ctx);
        } else if (expression instanceof AndExpression) {
            generate((AndExpression) expression, section, ctx);
        } else if (expression instanceof LowerExpression) {
            generate((LowerExpression) expression, section, ctx);
        } else if (expression instanceof GreaterExpression) {
            generate((GreaterExpression) expression, section, ctx);
        } else if (expression instanceof LowerOrEqualExpression) {
            generate((LowerOrEqualExpression) expression, section, ctx);
        } else if (expression instanceof GreaterOrEqualExpression) {
            generate((GreaterOrEqualExpression) expression, section, ctx);
        } else if (expression instanceof EqualsExpression) {
            generate((EqualsExpression) expression, section, ctx);
        } else if (expression instanceof NotEqualsExpression) {
            generate((NotEqualsExpression) expression, section, ctx);
        } else if (expression instanceof PlusExpression) {
            generate((PlusExpression) expression, section, ctx);
        } else if (expression instanceof MinusExpression) {
            generate((MinusExpression) expression, section, ctx);
        } else if (expression instanceof TimesExpression) {
            generate((TimesExpression) expression, section, ctx);
        } else if (expression instanceof DivExpression) {
            generate((DivExpression) expression, section, ctx);
        } else if (expression instanceof ModExpression) {
            generate((ModExpression) expression, section, ctx);
        } else if (expression instanceof UnaryMinus) {
            generate((UnaryMinus) expression, section, ctx);
        } else if (expression instanceof NotExpression) {
            generate((NotExpression) expression, section, ctx);
        } else if (expression instanceof PropertyReference) {
            generate((PropertyReference) expression, section, ctx);
        } else if (expression instanceof ByteLiteral) {
        	generate((ByteLiteral) expression, section, ctx);
        } else if (expression instanceof CharLiteral) {
        	generate((CharLiteral) expression, section, ctx);
        } else if (expression instanceof IntegerLiteral) {
            generate((IntegerLiteral) expression, section, ctx);
        } else if (expression instanceof DoubleLiteral) {
            generate((DoubleLiteral) expression, section, ctx);
        } else if (expression instanceof StringLiteral) {
            generate((StringLiteral) expression, section, ctx);
        } else if (expression instanceof BooleanLiteral) {
            generate((BooleanLiteral) expression, section, ctx);
        } else if (expression instanceof EnumLiteralRef) {
            generate((EnumLiteralRef) expression, section, ctx);
        } else if (expression instanceof ExternExpression) {
            generate((ExternExpression) expression, section, ctx);
        } else if (expression instanceof FunctionCallExpression) {
            generate((FunctionCallExpression) expression, section, ctx);
        } else if (expression instanceof EventReference) {
        	generate((EventReference) expression, section, ctx);
        } else if (expression instanceof CastExpression) {
        	generate((CastExpression) expression, section, ctx);
        } else if (expression instanceof ExpressionGroup)  {
            generate((ExpressionGroup) expression, section, ctx);
        } else {
            throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is unknown... Please update your action compilers as a new action/expression might have been introduced in ThingML"));
        }
    }
    
    public void generate(ArrayInit expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }
    
    public void generate(CastExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }
    
    public void generate(ExpressionGroup expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ArrayIndex expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(OrExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(AndExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(LowerExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(GreaterExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(LowerOrEqualExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(GreaterOrEqualExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(EqualsExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(NotEqualsExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(PlusExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(MinusExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(TimesExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(DivExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ModExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(UnaryMinus expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(NotExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(PropertyReference expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }
    
    public void generate(ByteLiteral expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }
    
    public void generate(CharLiteral expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(IntegerLiteral expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(DoubleLiteral expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(StringLiteral expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(BooleanLiteral expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(EnumLiteralRef expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ExternExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(FunctionCallExpression expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }
    
    public void generate(EventReference expression, Section section, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }
}
