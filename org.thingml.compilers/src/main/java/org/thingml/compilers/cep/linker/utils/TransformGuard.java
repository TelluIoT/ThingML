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
package org.thingml.compilers.cep.linker.utils;

import com.sun.org.apache.xpath.internal.operations.And;
import org.sintef.thingml.*;

/**
 * @author ludovic
 */
public class TransformGuard {


    public static Expression copyAndTransformGuard(Expression guard) {
        //Expression result = (Expression) ThingmlFactory.eINSTANCE.create(guard.eClass());

        /*EqualsExpression result = (EqualsExpression) ThingmlFactory.eINSTANCE.create(guard.eClass());
        EqualsExpression hGuard = (EqualsExpression) guard;

        Expression rhs;
        if(hGuard.getRhs() instanceof IntegerLiteral) {
            IntegerLiteral integerLiteral = ThingmlFactory.eINSTANCE.createIntegerLiteral();
            integerLiteral.setIntValue(((IntegerLiteral) hGuard.getRhs()).getIntValue());
            rhs = integerLiteral;
        } else if (hGuard.getRhs() instanceof ExternExpression){
            ExternExpression externExpression = ThingmlFactory.eINSTANCE.createExternExpression();
            externExpression.setExpression(((ExternExpression)hGuard.getRhs()).getExpression());
            rhs = externExpression;
        } else {
            throw new UnsupportedOperationException("Not good...");
        }
        result.setRhs(rhs);
        EventReference eventReference = ThingmlFactory.eINSTANCE.createEventReference();
        eventReference.setMsgRef(((EventReference)hGuard.getLhs()).getMsgRef());
        eventReference.setParamRef(((EventReference) hGuard.getLhs()).getParamRef());
        result.setLhs(eventReference);*/

       //result = copyAndTransformGuard(guard);

        if (guard instanceof  ArrayIndex) {
            return copyAndTransformGuard((ArrayIndex) guard);
        } else if(guard instanceof BinaryExpression) {
            return copyAndTransformGuard((BinaryExpression)guard);
        } else if(guard instanceof UnaryExpression) {
            return copyAndTransformGuard((UnaryExpression)guard);
        }/*else if(guard instanceof  OrExpression) {
            return copyAndTransformGuard((OrExpression) guard);
        } else if(guard instanceof  AndExpression) {
            return copyAndTransformGuard((AndExpression) guard);
        }else if(guard instanceof  LowerExpression) {
            return copyAndTransformGuard((LowerExpression) guard);
        }else if(guard instanceof  GreaterExpression) {
            return copyAndTransformGuard((GreaterExpression) guard);
        }else if(guard instanceof  EqualsExpression) {
            return copyAndTransformGuard((EqualsExpression) guard);
        }else if(guard instanceof  PlusExpression) {
            return copyAndTransformGuard((PlusExpression) guard);
        }else if(guard instanceof  MinusExpression) {
            return copyAndTransformGuard((MinusExpression) guard);
        }else if(guard instanceof  TimesExpression) {
            return copyAndTransformGuard((TimesExpression) guard);
        }else if(guard instanceof  DivExpression) {
            return copyAndTransformGuard((DivExpression) guard);
        }else if(guard instanceof  ModExpression) {
            return copyAndTransformGuard((ModExpression) guard);
        }*//*else if(guard instanceof  UnaryMinus) {
            return copyAndTransformGuard((UnaryMinus) guard);
        }else if(guard instanceof  NotExpression) {
            return copyAndTransformGuard((NotExpression) guard);
        }*/else if(guard instanceof  EventReference) {
            return copyAndTransformGuard((EventReference) guard);
        }else if(guard instanceof  ExpressionGroup) {
            return copyAndTransformGuard((ExpressionGroup) guard);
        }else if(guard instanceof  PropertyReference) {
            return copyAndTransformGuard((PropertyReference) guard);
        } else if(guard instanceof  IntegerLiteral) {
            return copyAndTransformGuard((IntegerLiteral) guard);
        } else if(guard instanceof  DoubleLiteral) {
            return copyAndTransformGuard((DoubleLiteral) guard);
        }else if(guard instanceof  StringLiteral) {
            return copyAndTransformGuard((StringLiteral) guard);
        }else if(guard instanceof  BooleanLiteral) {
            return copyAndTransformGuard((BooleanLiteral) guard);
        }else if(guard instanceof  EnumLiteralRef) {
            return copyAndTransformGuard((EnumLiteralRef) guard);
        }else if(guard instanceof  ExternExpression) {
            return copyAndTransformGuard((ExternExpression) guard);
        }else if(guard instanceof  FunctionCallExpression) {
            return copyAndTransformGuard((FunctionCallExpression) guard);
        }
        throw new UnsupportedOperationException("Expression type " + guard.getClass() + "is unknowm. We cannot copy it.");
    }


    private static ArrayIndex copyAndTransformGuard(ArrayIndex guard) {
        ArrayIndex result = ThingmlFactory.eINSTANCE.createArrayIndex();
        result.setArray(copyAndTransformGuard(guard.getArray()));
        result.setIndex(copyAndTransformGuard(guard.getIndex()));
       return result;
    }

    private static BinaryExpression copyAndTransformGuard(BinaryExpression guard) {
        BinaryExpression binaryExpression = (BinaryExpression) ThingmlFactory.eINSTANCE.create(guard.eClass());
        binaryExpression.setLhs(copyAndTransformGuard(guard.getLhs()));
        binaryExpression.setRhs(copyAndTransformGuard(guard.getRhs()));
        return binaryExpression;
    }

    /*private static OrExpression copyAndTransformGuard(OrExpression guard) {
        OrExpression orExpression = ThingmlFactory.eINSTANCE.createOrExpression();
        orExpression.setLhs(copyAndTransformGuard(guard.getLhs()));
        orExpression.setRhs(copyAndTransformGuard(guard.getRhs()));
        return orExpression;
    }

    private static AndExpression copyAndTransformGuard(AndExpression guard) {
        AndExpression andExpression = ThingmlFactory.eINSTANCE.createAndExpression();
        andExpression.setLhs(guard.getLhs());
        andExpression.setRhs(guard.getRhs());
        return andExpression;
    }

    private static LowerExpression copyAndTransformGuard(LowerExpression guard) {
        LowerExpression lowerExpression = ThingmlFactory.eINSTANCE.createLowerExpression();
        lowerExpression.setLhs(guard.getLhs());
        lowerExpression.setRhs(guard.getRhs());
        return lowerExpression;
    }
    private static GreaterExpression copyAndTransformGuard(GreaterExpression guard) {

        return null;
    }

    private static EqualsExpression copyAndTransformGuard(EqualsExpression guard) {

        return null;
    }

    private static PlusExpression copyAndTransformGuard(PlusExpression guard) {

        return null;
    }

    private static MinusExpression copyAndTransformGuard(MinusExpression guard) {
        return null;
    }

    private static TimesExpression copyAndTransformGuard(TimesExpression guard) {

        return null;
    }

    private static DivExpression copyAndTransformGuard(DivExpression guard) {

        return null;
    }

    private static ModExpression copyAndTransformGuard(ModExpression guard) {

        return null;
    }*/

    private static UnaryExpression copyAndTransformGuard(UnaryExpression guard) {
        UnaryExpression unaryExpression = (UnaryExpression) ThingmlFactory.eINSTANCE.create(guard.eClass());
        unaryExpression.setTerm(copyAndTransformGuard(guard.getTerm()));
        return unaryExpression;
    }

    /*private static UnaryMinus copyAndTransformGuard(UnaryMinus guard) {

        return null;
    }

    private static NotExpression copyAndTransformGuard(NotExpression guard) {
        return null;
    }*/

    private static EventReference copyAndTransformGuard(EventReference guard) {
        EventReference eventReference = ThingmlFactory.eINSTANCE.createEventReference();
        eventReference.setMsgRef(guard.getMsgRef());
        eventReference.setParamRef(guard.getParamRef());
        return eventReference;
    }

    private static ExpressionGroup copyAndTransformGuard(ExpressionGroup guard) {
        ExpressionGroup expressionGroup = ThingmlFactory.eINSTANCE.createExpressionGroup();
        expressionGroup.setExp(copyAndTransformGuard(guard.getExp()));
        return expressionGroup;
    }

    private static PropertyReference copyAndTransformGuard(PropertyReference guard) {
        PropertyReference propertyReference = ThingmlFactory.eINSTANCE.createPropertyReference();
        propertyReference.setProperty(guard.getProperty());
        return propertyReference;
    }

    private static IntegerLiteral copyAndTransformGuard(IntegerLiteral guard) {
        IntegerLiteral integerLiteral = ThingmlFactory.eINSTANCE.createIntegerLiteral();
        integerLiteral.setIntValue(guard.getIntValue());
        return integerLiteral;
    }

    private static DoubleLiteral copyAndTransformGuard(DoubleLiteral guard) {
        DoubleLiteral doubleLiteral = ThingmlFactory.eINSTANCE.createDoubleLiteral();
        doubleLiteral.setDoubleValue(guard.getDoubleValue());
        return doubleLiteral;
    }

    private static StringLiteral copyAndTransformGuard(StringLiteral guard) {
        StringLiteral stringLiteral = ThingmlFactory.eINSTANCE.createStringLiteral();
        stringLiteral.setStringValue(guard.getStringValue());
        return stringLiteral;
    }

    private static BooleanLiteral copyAndTransformGuard(BooleanLiteral guard) {
        BooleanLiteral booleanLiteral = ThingmlFactory.eINSTANCE.createBooleanLiteral();
        booleanLiteral.setBoolValue(guard.isBoolValue());
        return booleanLiteral;
    }

    private static EnumLiteralRef copyAndTransformGuard(EnumLiteralRef guard) {
        EnumLiteralRef enumLiteralRef = ThingmlFactory.eINSTANCE.createEnumLiteralRef();
        enumLiteralRef.setEnum(guard.getEnum());
        enumLiteralRef.setLiteral(guard.getLiteral());
        return enumLiteralRef;
    }

    private static ExternExpression copyAndTransformGuard(ExternExpression guard) {
        ExternExpression externExpression = ThingmlFactory.eINSTANCE.createExternExpression();
        externExpression.setExpression(guard.getExpression());
        return externExpression;
    }

    private static FunctionCallExpression copyAndTransformGuard(FunctionCallExpression guard) {
        FunctionCallExpression functionCallExpression = ThingmlFactory.eINSTANCE.createFunctionCallExpression();
        functionCallExpression.setFunction(guard.getFunction());
        functionCallExpression.getParameters().addAll(guard.getParameters());
        return functionCallExpression;
    }

}
