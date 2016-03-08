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
package org.thingml.compilers.thing;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;

public class ThingActionCompiler {

    public void generate(Object o, StringBuilder builder, Context ctx) {
        if (o instanceof Action) {
            generate((Action) o, builder, ctx);
        } else if (o instanceof Expression) {
            generate((Expression) o, builder, ctx);
        } else {
            throw (new UnsupportedOperationException("This action/Expression (" + o.getClass().getName() + ") is unknown... Please update your action compilers as a new action/expression might have been introduced in ThingML"));
        }
    }


    // ThingML Actions
    public void generate(Action action, StringBuilder builder, Context ctx) {
        if (action instanceof SendAction)
            generate((SendAction) action, builder, ctx);
        else if (action instanceof VariableAssignment)
            generate((VariableAssignment) action, builder, ctx);
        else if (action instanceof ActionBlock)
            generate((ActionBlock) action, builder, ctx);
        else if (action instanceof ExternStatement)
            generate((ExternStatement) action, builder, ctx);
        else if (action instanceof ConditionalAction)
            generate((ConditionalAction) action, builder, ctx);
        else if (action instanceof LoopAction)
            generate((LoopAction) action, builder, ctx);
        else if (action instanceof PrintAction)
            generate((PrintAction) action, builder, ctx);
        else if (action instanceof ErrorAction)
            generate((ErrorAction) action, builder, ctx);
        else if (action instanceof ReturnAction)
            generate((ReturnAction) action, builder, ctx);
        else if (action instanceof LocalVariable)
            generate((LocalVariable) action, builder, ctx);
        else if (action instanceof FunctionCallStatement)
            generate((FunctionCallStatement) action, builder, ctx);
        else if (action instanceof Increment)
            generate ((Increment) action, builder, ctx);
        else if (action instanceof Decrement)
            generate ((Decrement) action, builder, ctx);
        else if (action instanceof StartSession) {
            generate ((StartSession) action, builder, ctx);
        } else if (action instanceof StartStream) {
            generate((StartStream) action, builder, ctx);
        } else if (action instanceof StopStream) {
            generate((StopStream) action, builder, ctx);
        } else {
            throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is unknown... Please update your action compilers as a new action/expression might have been introduced in ThingML"));
        }
    }

    public void generate(SendAction action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(StartSession action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(StartStream action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(StopStream action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(VariableAssignment action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ActionBlock action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ExternStatement action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ConditionalAction action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(LoopAction action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(PrintAction action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ErrorAction action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ReturnAction action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(LocalVariable action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(FunctionCallStatement action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(Increment action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(Decrement action, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This action (" + action.getClass().getName() + ") is platform-specific and should be refined!"));
    }


    //ThingML Expressions
    public void generate(Expression expression, StringBuilder builder, Context ctx) {
        if (expression instanceof ArrayIndex) {
            generate((ArrayIndex) expression, builder, ctx);
        } else if (expression instanceof OrExpression) {
            generate((OrExpression) expression, builder, ctx);
        } else if (expression instanceof AndExpression) {
            generate((AndExpression) expression, builder, ctx);
        } else if (expression instanceof LowerExpression) {
            generate((LowerExpression) expression, builder, ctx);
        } else if (expression instanceof GreaterExpression) {
            generate((GreaterExpression) expression, builder, ctx);
        } else if (expression instanceof LowerOrEqualExpression) {
            generate((LowerOrEqualExpression) expression, builder, ctx);
        } else if (expression instanceof GreaterOrEqualExpression) {
            generate((GreaterOrEqualExpression) expression, builder, ctx);
        } else if (expression instanceof EqualsExpression) {
            generate((EqualsExpression) expression, builder, ctx);
        } else if (expression instanceof NotEqualsExpression) {
            generate((NotEqualsExpression) expression, builder, ctx);
        } else if (expression instanceof PlusExpression) {
            generate((PlusExpression) expression, builder, ctx);
        } else if (expression instanceof MinusExpression) {
            generate((MinusExpression) expression, builder, ctx);
        } else if (expression instanceof TimesExpression) {
            generate((TimesExpression) expression, builder, ctx);
        } else if (expression instanceof DivExpression) {
            generate((DivExpression) expression, builder, ctx);
        } else if (expression instanceof ModExpression) {
            generate((ModExpression) expression, builder, ctx);
        } else if (expression instanceof UnaryMinus) {
            generate((UnaryMinus) expression, builder, ctx);
        } else if (expression instanceof NotExpression) {
            generate((NotExpression) expression, builder, ctx);
        } else if (expression instanceof Reference) {
            generate((Reference) expression, builder, ctx);
        } else if (expression instanceof ExpressionGroup) {
            generate((ExpressionGroup) expression, builder, ctx);
        } else if (expression instanceof PropertyReference) {
            generate((PropertyReference) expression, builder, ctx);
        } else if (expression instanceof IntegerLiteral) {
            generate((IntegerLiteral) expression, builder, ctx);
        } else if (expression instanceof DoubleLiteral) {
            generate((DoubleLiteral) expression, builder, ctx);
        } else if (expression instanceof StringLiteral) {
            generate((StringLiteral) expression, builder, ctx);
        } else if (expression instanceof BooleanLiteral) {
            generate((BooleanLiteral) expression, builder, ctx);
        } else if (expression instanceof EnumLiteralRef) {
            generate((EnumLiteralRef) expression, builder, ctx);
        } else if (expression instanceof ExternExpression) {
            generate((ExternExpression) expression, builder, ctx);
        } else if (expression instanceof FunctionCallExpression) {
            generate((FunctionCallExpression) expression, builder, ctx);
        } else {
            throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is unknown... Please update your action compilers as a new action/expression might have been introduced in ThingML"));
        }
    }

    public void generate(ArrayIndex expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(OrExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(AndExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(LowerExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(GreaterExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(LowerOrEqualExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(GreaterOrEqualExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(EqualsExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(NotEqualsExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(PlusExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(MinusExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(TimesExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(DivExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ModExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(UnaryMinus expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(NotExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }


    public void generate(Reference expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ExpressionGroup expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(PropertyReference expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(IntegerLiteral expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(DoubleLiteral expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(StringLiteral expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(BooleanLiteral expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(EnumLiteralRef expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(ExternExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }

    public void generate(FunctionCallExpression expression, StringBuilder builder, Context ctx) {
        throw (new UnsupportedOperationException("This expression (" + expression.getClass().getName() + ") is platform-specific and should be refined!"));
    }
}