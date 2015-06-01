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
package org.thingml.compilers.cep.helper;

import org.sintef.thingml.*;

/**
 * @author ludovic
 */
public class CopyExpression {
    public static final CopyExpression instance = new CopyExpression();

    protected CopyExpression(){}

    public Expression copyExpression(Expression expression) {
        if (expression instanceof  ArrayIndex) {
            return copyExpression((ArrayIndex) expression);
        } else if(expression instanceof BinaryExpression) {
            return copyExpression((BinaryExpression) expression);
        } else if(expression instanceof UnaryExpression) {
            return copyExpression((UnaryExpression) expression);
        }else if(expression instanceof  EventReference) {
            return copyExpression((EventReference) expression);
        }else if(expression instanceof  ExpressionGroup) {
            return copyExpression((ExpressionGroup) expression);
        }else if(expression instanceof  PropertyReference) {
            return copyExpression((PropertyReference) expression);
        } else if(expression instanceof  IntegerLiteral) {
            return copyExpression((IntegerLiteral) expression);
        } else if(expression instanceof  DoubleLiteral) {
            return copyExpression((DoubleLiteral) expression);
        }else if(expression instanceof  StringLiteral) {
            return copyExpression((StringLiteral) expression);
        }else if(expression instanceof  BooleanLiteral) {
            return copyExpression((BooleanLiteral) expression);
        }else if(expression instanceof  EnumLiteralRef) {
            return copyExpression((EnumLiteralRef) expression);
        }else if(expression instanceof  ExternExpression) {
            return copyExpression((ExternExpression) expression);
        }else if(expression instanceof  FunctionCallExpression) {
            return copyExpression((FunctionCallExpression) expression);
        }
        throw new UnsupportedOperationException("Expression type " + expression.getClass() + "is unknowm. We cannot copy it.");
    }


    public Expression copyExpression(ArrayIndex expression) {
        ArrayIndex result = ThingmlFactory.eINSTANCE.createArrayIndex();
        result.setArray(copyExpression(expression.getArray()));
        result.setIndex(copyExpression(expression.getIndex()));
        return result;
    }

    public Expression copyExpression(BinaryExpression expression) {
        BinaryExpression binaryExpression = (BinaryExpression) ThingmlFactory.eINSTANCE.create(expression.eClass());
        binaryExpression.setLhs(copyExpression(expression.getLhs()));
        binaryExpression.setRhs(copyExpression(expression.getRhs()));
        return binaryExpression;
    }



    public Expression copyExpression(UnaryExpression expression) {
        UnaryExpression unaryExpression = (UnaryExpression) ThingmlFactory.eINSTANCE.create(expression.eClass());
        unaryExpression.setTerm(copyExpression(expression.getTerm()));
        return unaryExpression;
    }


    public Expression copyExpression(EventReference expression) {
        EventReference eventReference = ThingmlFactory.eINSTANCE.createEventReference();
        eventReference.setMsgRef(expression.getMsgRef());
        eventReference.setParamRef(expression.getParamRef());
        return eventReference;
    }

    public Expression copyExpression(ExpressionGroup expression) {
        ExpressionGroup expressionGroup = ThingmlFactory.eINSTANCE.createExpressionGroup();
        expressionGroup.setExp(copyExpression(expression.getExp()));
        return expressionGroup;
    }

    public Expression copyExpression(PropertyReference expression) {
        PropertyReference propertyReference = ThingmlFactory.eINSTANCE.createPropertyReference();
        propertyReference.setProperty(expression.getProperty());
        return propertyReference;
    }

    public Expression copyExpression(IntegerLiteral expression) {
        IntegerLiteral integerLiteral = ThingmlFactory.eINSTANCE.createIntegerLiteral();
        integerLiteral.setIntValue(expression.getIntValue());
        return integerLiteral;
    }

    public Expression copyExpression(DoubleLiteral expression) {
        DoubleLiteral doubleLiteral = ThingmlFactory.eINSTANCE.createDoubleLiteral();
        doubleLiteral.setDoubleValue(expression.getDoubleValue());
        return doubleLiteral;
    }

    public Expression copyExpression(StringLiteral expression) {
        StringLiteral stringLiteral = ThingmlFactory.eINSTANCE.createStringLiteral();
        stringLiteral.setStringValue(expression.getStringValue());
        return stringLiteral;
    }

    public Expression copyExpression(BooleanLiteral expression) {
        BooleanLiteral booleanLiteral = ThingmlFactory.eINSTANCE.createBooleanLiteral();
        booleanLiteral.setBoolValue(expression.isBoolValue());
        return booleanLiteral;
    }

    public Expression copyExpression(EnumLiteralRef expression) {
        EnumLiteralRef enumLiteralRef = ThingmlFactory.eINSTANCE.createEnumLiteralRef();
        enumLiteralRef.setEnum(expression.getEnum());
        enumLiteralRef.setLiteral(expression.getLiteral());
        return enumLiteralRef;
    }

    public Expression copyExpression(ExternExpression expression) {
        ExternExpression externExpression = ThingmlFactory.eINSTANCE.createExternExpression();
        externExpression.setExpression(expression.getExpression());
        return externExpression;
    }

    public Expression copyExpression(FunctionCallExpression expression) {
        FunctionCallExpression functionCallExpression = ThingmlFactory.eINSTANCE.createFunctionCallExpression();
        functionCallExpression.setFunction(expression.getFunction());
        for(Expression e : expression.getParameters()) {
            functionCallExpression.getParameters().add(copyExpression(e));
        }
        return functionCallExpression;
    }
}
