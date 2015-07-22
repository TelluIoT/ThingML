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
package org.thingml.compilers;

import org.sintef.thingml.*;

/**
 * @author ludovic
 */
public class ThingMLCopyExpression {
    //ThingML Expressions
    public static Expression copy(Expression expression) {
        if (expression instanceof ArrayIndex) {
            return copy((ArrayIndex) expression);
        }  else if(expression instanceof BinaryExpression) {
            return copy((BinaryExpression) expression);
        } else if(expression instanceof UnaryExpression) {
            return copy((UnaryExpression)expression);
        } else if (expression instanceof EventReference) {
            return copy((EventReference) expression);
        } else if (expression instanceof ExpressionGroup) {
            return copy((ExpressionGroup) expression);
        } else if (expression instanceof PropertyReference) {
            return copy((PropertyReference) expression);
        } else if (expression instanceof IntegerLiteral) {
            return copy((IntegerLiteral) expression);
        } else if (expression instanceof DoubleLiteral) {
            return copy((DoubleLiteral) expression);
        } else if (expression instanceof StringLiteral) {
            return copy((StringLiteral) expression);
        } else if (expression instanceof BooleanLiteral) {
            return copy((BooleanLiteral) expression);
        } else if (expression instanceof EnumLiteralRef) {
            return copy((EnumLiteralRef) expression);
        } else if (expression instanceof ExternExpression) {
            return copy((ExternExpression) expression);
        } else if (expression instanceof FunctionCallExpression) {
            return copy((FunctionCallExpression) expression);
        } else if(expression instanceof StreamParamReference) {
            return copy((StreamParamReference) expression);
        } else {
            throw new UnsupportedOperationException(("This expression (" + expression.getClass().getName() + ") is unknown... Please update your action compilers as a new action/expression might have been introduced in ThingML"));
        }
    }

    public static ArrayIndex copy(ArrayIndex expression) {
        ArrayIndex newExpression = ThingmlFactory.eINSTANCE.createArrayIndex();
        newExpression.setArray(copy(expression.getArray()));
        newExpression.setIndex(copy(expression.getIndex()));
        return newExpression;
    }

    public static BinaryExpression copy(BinaryExpression expression) {
        BinaryExpression binaryExpression = (BinaryExpression) ThingmlFactory.eINSTANCE.create(expression.eClass());
        binaryExpression.setLhs(copy(expression.getLhs()));
        binaryExpression.setRhs(copy(expression.getRhs()));
        return binaryExpression;
    }

    public static UnaryExpression copy(UnaryExpression expression) {
        UnaryExpression unaryExpression = (UnaryExpression) ThingmlFactory.eINSTANCE.create(expression.eClass());
        unaryExpression.setTerm(copy(expression.getTerm()));
        return unaryExpression;
    }

    public static EventReference copy(EventReference expression) {
        EventReference eventReference = ThingmlFactory.eINSTANCE.createEventReference();
        eventReference.setMsgRef(expression.getMsgRef());
        eventReference.setParamRef(expression.getParamRef());
        return eventReference;
    }

    public static ExpressionGroup copy(ExpressionGroup expression) {
        ExpressionGroup expressionGroup = ThingmlFactory.eINSTANCE.createExpressionGroup();
        expressionGroup.setExp(copy(expression.getExp()));
        return expressionGroup;
    }

    public static PropertyReference copy(PropertyReference expression) {
        PropertyReference propertyReference = ThingmlFactory.eINSTANCE.createPropertyReference();
        propertyReference.setProperty(expression.getProperty());
        return propertyReference;
    }

    public static IntegerLiteral copy(IntegerLiteral expression) {
        IntegerLiteral integerLiteral = ThingmlFactory.eINSTANCE.createIntegerLiteral();
        integerLiteral.setIntValue(expression.getIntValue());
        return integerLiteral;
    }

    public static DoubleLiteral copy(DoubleLiteral expression) {
        DoubleLiteral doubleLiteral = ThingmlFactory.eINSTANCE.createDoubleLiteral();
        doubleLiteral.setDoubleValue(expression.getDoubleValue());
        return doubleLiteral;
    }

    public static StringLiteral copy(StringLiteral expression) {
        StringLiteral stringLiteral = ThingmlFactory.eINSTANCE.createStringLiteral();
        stringLiteral.setStringValue(expression.getStringValue());
        return stringLiteral;
    }

    public static BooleanLiteral copy(BooleanLiteral expression) {
        BooleanLiteral booleanLiteral = ThingmlFactory.eINSTANCE.createBooleanLiteral();
        booleanLiteral.setBoolValue(expression.isBoolValue());
        return booleanLiteral;
    }

    public static EnumLiteralRef copy(EnumLiteralRef expression) {
        EnumLiteralRef enumLiteralRef = ThingmlFactory.eINSTANCE.createEnumLiteralRef();
        enumLiteralRef.setEnum(expression.getEnum());
        enumLiteralRef.setLiteral(expression.getLiteral());
        return enumLiteralRef;
    }

    public static ExternExpression copy(ExternExpression expression) {
        ExternExpression externExpression = ThingmlFactory.eINSTANCE.createExternExpression();
        expression.setExpression(expression.getExpression());
        for(Expression e : expression.getSegments()) {
            expression.getSegments().add(copy(e));
        }
        return externExpression;
    }

    public static FunctionCallExpression copy(FunctionCallExpression expression) {
        FunctionCallExpression functionCallExpression = ThingmlFactory.eINSTANCE.createFunctionCallExpression();
        functionCallExpression.setFunction(expression.getFunction());
        for(Expression e : expression.getParameters()) {
            functionCallExpression.getParameters().add(copy(e));
        }
        return functionCallExpression;
    }

    public static StreamParamReference copy(StreamParamReference expression) {
        StreamParamReference streamParamReference = ThingmlFactory.eINSTANCE.createStreamParamReference();
        streamParamReference.setIndexParam(expression.getIndexParam());
        return streamParamReference;
    }




}
