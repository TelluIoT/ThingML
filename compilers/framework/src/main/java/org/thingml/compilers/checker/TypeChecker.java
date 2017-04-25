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
package org.thingml.compilers.checker;


import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.AndExpression;
import org.thingml.xtext.thingML.ArrayIndex;
import org.thingml.xtext.thingML.BooleanLiteral;
import org.thingml.xtext.thingML.CastExpression;
import org.thingml.xtext.thingML.DivExpression;
import org.thingml.xtext.thingML.DoubleLiteral;
import org.thingml.xtext.thingML.EqualsExpression;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExpressionGroup;
import org.thingml.xtext.thingML.ExternExpression;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.GreaterExpression;
import org.thingml.xtext.thingML.GreaterOrEqualExpression;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.LowerExpression;
import org.thingml.xtext.thingML.LowerOrEqualExpression;
import org.thingml.xtext.thingML.MinusExpression;
import org.thingml.xtext.thingML.ModExpression;
import org.thingml.xtext.thingML.NotExpression;
import org.thingml.xtext.thingML.OrExpression;
import org.thingml.xtext.thingML.PlusExpression;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.TimesExpression;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.UnaryMinus;
import org.thingml.xtext.thingML.util.ThingMLSwitch;


public class TypeChecker extends ThingMLSwitch<Type> {

    public Type computeTypeOf(Expression exp) {
        Type result = null;
        if (exp == null) {
            return Types.ANY_TYPE;
        }
        result = doSwitch(exp);
        if (result == null) {
            System.out.println("TODO: Type checking for " + exp.getClass().getName());
            return Types.ANY_TYPE;
        }
        return result;
    }
    
    @Override
	public Type caseExpressionGroup(ExpressionGroup object) {
        return TyperHelper.getBroadType(computeTypeOf(object.getTerm()));
	}

      
    @Override
	public Type caseCastExpression(CastExpression object) {
        return TyperHelper.getBroadType(object.getType());
	}

	@Override
    public Type caseExternExpression(ExternExpression object) {
        return Types.ANY_TYPE;
    }

    @Override
    public Type caseIntegerLiteral(IntegerLiteral object) {
        return Types.INTEGER_TYPE;
    }

    @Override
    public Type caseBooleanLiteral(BooleanLiteral object) {
        return Types.BOOLEAN_TYPE;
    }

    @Override
    public Type caseStringLiteral(StringLiteral object) {
        return Types.STRING_TYPE;
    }

    @Override
    public Type caseDoubleLiteral(DoubleLiteral object) {
        return Types.REAL_TYPE;
    }

    @Override
    public Type caseUnaryMinus(UnaryMinus object) {
        Type t = computeTypeOf(object.getTerm());
        if (t.equals(Types.ANY_TYPE))
            return Types.ANY_TYPE;
        if (!t.equals(Types.INTEGER_TYPE) && !t.equals(Types.REAL_TYPE)) {
            return Types.ERROR_TYPE;
        }
        return t;
    }

    private Type caseBinaryNumericalOperator(Type t1, Type t2) {
        if (t1.equals(Types.ANY_TYPE) || t2.equals(Types.ANY_TYPE))
            return Types.ANY_TYPE;
        if ((!t1.equals(Types.INTEGER_TYPE) && !t1.equals(Types.REAL_TYPE)) || (!t2.equals(Types.INTEGER_TYPE) && !t2.equals(Types.REAL_TYPE))) {
            return Types.ERROR_TYPE;
        }
        if (!TyperHelper.getBroadType(t1).getName().equals(TyperHelper.getBroadType(t2).getName())) //One Integer and one Real
            return Types.REAL_TYPE;
        return t1;
    }

    @Override
    public Type casePlusExpression(PlusExpression object) {
        Type t1 = computeTypeOf(object.getLhs());
        Type t2 = computeTypeOf(object.getRhs());
        return caseBinaryNumericalOperator(t1, t2);
    }

    @Override
    public Type caseMinusExpression(MinusExpression object) {
        Type t1 = computeTypeOf(object.getLhs());
        Type t2 = computeTypeOf(object.getRhs());
        return caseBinaryNumericalOperator(t1, t2);
    }

    @Override
    public Type caseTimesExpression(TimesExpression object) {
        Type t1 = computeTypeOf(object.getLhs());
        Type t2 = computeTypeOf(object.getRhs());
        return caseBinaryNumericalOperator(t1, t2);
    }

    @Override
    public Type caseDivExpression(DivExpression object) {
        Type t1 = computeTypeOf(object.getLhs());
        Type t2 = computeTypeOf(object.getRhs());
        return caseBinaryNumericalOperator(t1, t2);
    }

    @Override
    public Type caseModExpression(ModExpression object) {
        Type t1 = computeTypeOf(object.getLhs());
        Type t2 = computeTypeOf(object.getRhs());
        if (t1.equals(Types.ANY_TYPE) || t2.equals(Types.ANY_TYPE))
            return Types.ANY_TYPE;
        if (!t1.equals(Types.INTEGER_TYPE) || !t2.equals(Types.INTEGER_TYPE)) {
            return Types.ERROR_TYPE;
        }
        return Types.INTEGER_TYPE;
    }

    private Type caseComparison(Type t1, Type t2) {
        if ((t1.equals(Types.INTEGER_TYPE) || t1.equals(Types.REAL_TYPE) || t1.equals(Types.ANY_TYPE)) && (t2.equals(Types.INTEGER_TYPE) || t2.equals(Types.REAL_TYPE) || t2.equals(Types.ANY_TYPE)))
            return Types.BOOLEAN_TYPE;
        if ((t1.equals(Types.BOOLEAN_TYPE) || t1.equals(Types.ANY_TYPE)) && (t2.equals(Types.BOOLEAN_TYPE) || t2.equals(Types.ANY_TYPE)))
            return Types.BOOLEAN_TYPE;
        if (TyperHelper.isA(t1, Types.ANY_TYPE) && TyperHelper.isA(t2, Types.ANY_TYPE))
            return Types.BOOLEAN_TYPE;
        return Types.ERROR_TYPE;
    }

    @Override
    public Type caseEqualsExpression(EqualsExpression object) {
        Type t1 = computeTypeOf(object.getLhs());
        Type t2 = computeTypeOf(object.getRhs());
        return caseComparison(t1, t2);
    }

    @Override
    public Type caseGreaterExpression(GreaterExpression object) {
        Type t1 = computeTypeOf(object.getLhs());
        Type t2 = computeTypeOf(object.getRhs());
        return caseComparison(t1, t2);
    }

    @Override
    public Type caseGreaterOrEqualExpression(GreaterOrEqualExpression object) {
        Type t1 = computeTypeOf(object.getLhs());
        Type t2 = computeTypeOf(object.getRhs());
        return caseComparison(t1, t2);
    }

    @Override
    public Type caseLowerExpression(LowerExpression object) {
        Type t1 = computeTypeOf(object.getLhs());
        Type t2 = computeTypeOf(object.getRhs());
        return caseComparison(t1, t2);
    }

    @Override
    public Type caseLowerOrEqualExpression(LowerOrEqualExpression object) {
        Type t1 = computeTypeOf(object.getLhs());
        Type t2 = computeTypeOf(object.getRhs());
        return caseComparison(t1, t2);
    }

    //Boolean
    private Type caseBooleanOperator(Type t1, Type t2) {
        if (t1.equals(Types.ANY_TYPE) || t2.equals(Types.ANY_TYPE))
            return Types.ANY_TYPE;
        if (!t1.equals(Types.BOOLEAN_TYPE) || !t2.equals(Types.BOOLEAN_TYPE)) {
            return Types.ERROR_TYPE;
        }
        return Types.BOOLEAN_TYPE;
    }

    @Override
    public Type caseAndExpression(AndExpression object) {
        Type t1 = computeTypeOf(object.getLhs());
        Type t2 = computeTypeOf(object.getRhs());
        return caseBooleanOperator(t1, t2);
    }

    @Override
    public Type caseOrExpression(OrExpression object) {
        Type t1 = computeTypeOf(object.getLhs());
        Type t2 = computeTypeOf(object.getRhs());
        return caseBooleanOperator(t1, t2);
    }

    @Override
    public Type caseNotExpression(NotExpression object) {
        Type t = computeTypeOf(object.getTerm());
        if (t.equals(Types.ANY_TYPE))
            return Types.ANY_TYPE;
        if (!t.equals(Types.BOOLEAN_TYPE)) {
            return Types.ERROR_TYPE;
        }
        return Types.BOOLEAN_TYPE;
    }
    //End Boolean

    @Override
    public Type casePropertyReference(PropertyReference object) {
        return TyperHelper.getBroadType(object.getProperty().getTypeRef().getType());
    }
    
    
    @Override
	public Type caseEventReference(EventReference object) {
    	return TyperHelper.getBroadType(object.getParameter().getTypeRef().getType());
	}

    @Override
    public Type caseFunctionCallExpression(FunctionCallExpression object) {
        if (object.getFunction().getTypeRef().getType() == null)
            return Types.VOID_TYPE;
        return TyperHelper.getBroadType(object.getFunction().getTypeRef().getType());
    }

    @Override
    public Type caseArrayIndex(ArrayIndex object) {
        Type t = computeTypeOf(object.getIndex());
        if (t.equals(Types.INTEGER_TYPE) || t.equals(Types.ANY_TYPE))
            return computeTypeOf(object.getArray());
        return Types.ERROR_TYPE;
    }
}
