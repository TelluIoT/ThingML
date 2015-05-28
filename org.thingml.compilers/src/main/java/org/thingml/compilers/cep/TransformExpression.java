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
package org.thingml.compilers.cep;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;

/**
 * @author ludovic
 */
public class TransformExpression {
    public static Expression copyExpression(Expression guard) {
        if (guard instanceof  ArrayIndex) {
            return copyExpression((ArrayIndex) guard);
        } else if(guard instanceof BinaryExpression) {
            return copyExpression((BinaryExpression) guard);
        } else if(guard instanceof UnaryExpression) {
            return copyExpression((UnaryExpression) guard);
        }else if(guard instanceof  EventReference) {
            return copyExpression((EventReference) guard);
        }else if(guard instanceof  ExpressionGroup) {
            return copyExpression((ExpressionGroup) guard);
        }else if(guard instanceof  PropertyReference) {
            return copyExpression((PropertyReference) guard);
        } else if(guard instanceof  IntegerLiteral) {
            return copyExpression((IntegerLiteral) guard);
        } else if(guard instanceof  DoubleLiteral) {
            return copyExpression((DoubleLiteral) guard);
        }else if(guard instanceof  StringLiteral) {
            return copyExpression((StringLiteral) guard);
        }else if(guard instanceof  BooleanLiteral) {
            return copyExpression((BooleanLiteral) guard);
        }else if(guard instanceof  EnumLiteralRef) {
            return copyExpression((EnumLiteralRef) guard);
        }else if(guard instanceof  ExternExpression) {
            return copyExpression((ExternExpression) guard);
        }else if(guard instanceof  FunctionCallExpression) {
            return copyExpression((FunctionCallExpression) guard);
        }
        throw new UnsupportedOperationException("Expression type " + guard.getClass() + "is unknowm. We cannot copy it.");
    }


    private static Expression copyExpression(ArrayIndex guard) {
        ArrayIndex result = ThingmlFactory.eINSTANCE.createArrayIndex();
        result.setArray(copyExpression(guard.getArray()));
        result.setIndex(copyExpression(guard.getIndex()));
        return result;
    }

    private static Expression copyExpression(BinaryExpression guard) {
        BinaryExpression binaryExpression = (BinaryExpression) ThingmlFactory.eINSTANCE.create(guard.eClass());
        binaryExpression.setLhs(copyExpression(guard.getLhs()));
        binaryExpression.setRhs(copyExpression(guard.getRhs()));
        return binaryExpression;
    }



    private static Expression copyExpression(UnaryExpression guard) {
        UnaryExpression unaryExpression = (UnaryExpression) ThingmlFactory.eINSTANCE.create(guard.eClass());
        unaryExpression.setTerm(copyExpression(guard.getTerm()));
        return unaryExpression;
    }


    private static Expression copyExpression(EventReference guard) {
        /*EventReference eventReference = ThingmlFactory.eINSTANCE.createEventReference();
        eventReference.setMsgRef(guard.getMsgRef());
        eventReference.setParamRef(guard.getParamRef());
        return eventReference;*/
        ExternExpression externExpression = ThingmlFactory.eINSTANCE.createExternExpression();
        externExpression.setExpression( guard.getMsgRef().getName() + "J." + guard.getParamRef().getName()); //fixme
        return externExpression;
    }

    private static Expression copyExpression(ExpressionGroup guard) {
        ExpressionGroup expressionGroup = ThingmlFactory.eINSTANCE.createExpressionGroup();
        expressionGroup.setExp(copyExpression(guard.getExp()));
        return expressionGroup;
    }

    private static Expression copyExpression(PropertyReference guard) {
        PropertyReference propertyReference = ThingmlFactory.eINSTANCE.createPropertyReference();
        propertyReference.setProperty(guard.getProperty());
        return propertyReference;
    }

    private static Expression copyExpression(IntegerLiteral guard) {
        IntegerLiteral integerLiteral = ThingmlFactory.eINSTANCE.createIntegerLiteral();
        integerLiteral.setIntValue(guard.getIntValue());
        return integerLiteral;
    }

    private static Expression copyExpression(DoubleLiteral guard) {
        DoubleLiteral doubleLiteral = ThingmlFactory.eINSTANCE.createDoubleLiteral();
        doubleLiteral.setDoubleValue(guard.getDoubleValue());
        return doubleLiteral;
    }

    private static Expression copyExpression(StringLiteral guard) {
        StringLiteral stringLiteral = ThingmlFactory.eINSTANCE.createStringLiteral();
        stringLiteral.setStringValue(guard.getStringValue());
        return stringLiteral;
    }

    private static Expression copyExpression(BooleanLiteral guard) {
        BooleanLiteral booleanLiteral = ThingmlFactory.eINSTANCE.createBooleanLiteral();
        booleanLiteral.setBoolValue(guard.isBoolValue());
        return booleanLiteral;
    }

    private static Expression copyExpression(EnumLiteralRef guard) {
        EnumLiteralRef enumLiteralRef = ThingmlFactory.eINSTANCE.createEnumLiteralRef();
        enumLiteralRef.setEnum(guard.getEnum());
        enumLiteralRef.setLiteral(guard.getLiteral());
        return enumLiteralRef;
    }

    private static Expression copyExpression(ExternExpression guard) {
        ExternExpression externExpression = ThingmlFactory.eINSTANCE.createExternExpression();
        externExpression.setExpression(guard.getExpression());
        return externExpression;
    }

    private static Expression copyExpression(FunctionCallExpression guard) {
        FunctionCallExpression functionCallExpression = ThingmlFactory.eINSTANCE.createFunctionCallExpression();
        functionCallExpression.setFunction(guard.getFunction());
        for(Expression expression : guard.getParameters()) {
            functionCallExpression.getParameters().add(copyExpression(expression));
        }
        return functionCallExpression;
    }
}
