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


import org.sintef.thingml.*;
import org.sintef.thingml.constraints.Types;
import org.sintef.thingml.helpers.TyperHelper;
import org.sintef.thingml.util.ThingmlSwitch;


public class TypeChecker extends ThingmlSwitch<Type> {

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
        return TyperHelper.getBroadType(object.getProperty().getType());
    }

    @Override
    public Type caseExpressionGroup(ExpressionGroup object) {
        return computeTypeOf(object.getExp());
    }

    @Override
    public Type caseReference(Reference object) {
        if (object.getReference() instanceof ReceiveMessage) {
            ReceiveMessage rm = (ReceiveMessage) object.getReference();
            if (object.getParameter() instanceof SimpleParamRef) {
                SimpleParamRef ref = (SimpleParamRef) object.getParameter();
                if (ref.getParameterRef().getType() == null)
                    return Types.ERROR_TYPE;
                return TyperHelper.getBroadType(ref.getParameterRef().getType());
            }
        } else if (object instanceof PropertyReference) {
            PropertyReference pr = (PropertyReference) object;
            if (pr.getProperty().getType() == null)
                return Types.ERROR_TYPE;
            return TyperHelper.getBroadType(pr.getProperty().getType());
        }
        return Types.ANY_TYPE;
    }

    @Override
    public Type caseFunctionCallExpression(FunctionCallExpression object) {
        if (object.getFunction().getType() == null)
            return Types.VOID_TYPE;
        return TyperHelper.getBroadType(object.getFunction().getType());
    }

    @Override
    public Type caseArrayIndex(ArrayIndex object) {
        Type t = computeTypeOf(object.getIndex());
        if (t.equals(Types.INTEGER_TYPE) || t.equals(Types.ANY_TYPE))
            return computeTypeOf(object.getArray());
        return Types.ERROR_TYPE;
    }
}
