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
package org.thingml.xtext.validation;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.AndExpression;
import org.thingml.xtext.thingML.ArrayIndex;
import org.thingml.xtext.thingML.ArrayInit;
import org.thingml.xtext.thingML.BooleanLiteral;
import org.thingml.xtext.thingML.ByteLiteral;
import org.thingml.xtext.thingML.CastExpression;
import org.thingml.xtext.thingML.CharLiteral;
import org.thingml.xtext.thingML.DivExpression;
import org.thingml.xtext.thingML.DoubleLiteral;
import org.thingml.xtext.thingML.EnumLiteralRef;
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
import org.thingml.xtext.thingML.NotEqualsExpression;
import org.thingml.xtext.thingML.NotExpression;
import org.thingml.xtext.thingML.OrExpression;
import org.thingml.xtext.thingML.PlusExpression;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.TimesExpression;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.UnaryMinus;
import org.thingml.xtext.thingML.util.ThingMLSwitch;


public class TypeChecker extends ThingMLSwitch<TypeRef> {
	
	public static TypeChecker INSTANCE = new TypeChecker();
	
    public static TypeRef computeTypeOf(Expression exp) {
        TypeRef result = null;
        if (exp == null) {
            return Types.ANY_TYPEREF;
        }
        result = INSTANCE.doSwitch(exp);
        if (result == null) {
            System.out.println("TODO: Type checking for " + exp.getClass().getName());
            return Types.ANY_TYPEREF;
        }
        return result;
    }
    
    @Override
	public TypeRef caseExpressionGroup(ExpressionGroup object) {
        return TyperHelper.getBroadType(computeTypeOf(object.getTerm()));
	}

      
    @Override
	public TypeRef caseCastExpression(CastExpression object) {
    	final TypeRef tr = ThingMLFactory.eINSTANCE.createTypeRef();
    	tr.setType(object.getType());
    	if (object.isIsArray()) {
    		final IntegerLiteral il = ThingMLFactory.eINSTANCE.createIntegerLiteral();
    		il.setIntValue(0);
    		tr.setCardinality(il);
    		tr.setIsArray(true);
    	}
        return TyperHelper.getBroadType(tr);
	}

	@Override
    public TypeRef caseExternExpression(ExternExpression object) {
        return Types.ANY_TYPEREF;
    }
	
	@Override
	public TypeRef caseByteLiteral(ByteLiteral object) {
		return Types.BYTE_TYPEREF;
	}
	
	@Override
	public TypeRef caseCharLiteral(CharLiteral object) {
		return Types.BYTE_TYPEREF;
	}

    @Override
    public TypeRef caseIntegerLiteral(IntegerLiteral object) {
        return Types.INTEGER_TYPEREF;
    }

    @Override
    public TypeRef caseBooleanLiteral(BooleanLiteral object) {
        return Types.BOOLEAN_TYPEREF;
    }

    @Override
    public TypeRef caseStringLiteral(StringLiteral object) {
        return Types.STRING_TYPEREF;
    }

    @Override
    public TypeRef caseDoubleLiteral(DoubleLiteral object) {
        return Types.REAL_TYPEREF;
    }

    @Override
    public TypeRef caseUnaryMinus(UnaryMinus object) {
        TypeRef t = computeTypeOf(object.getTerm());
        if (t.equals(Types.ANY_TYPEREF))
            return Types.ANY_TYPEREF;
        if (!t.equals(Types.BYTE_TYPEREF) && !t.equals(Types.INTEGER_TYPEREF) && !t.equals(Types.REAL_TYPEREF)) {
            return Types.ERROR_TYPEREF;
        }
        return t;
    }

    private TypeRef caseBinaryNumericalOperator(TypeRef t1, TypeRef t2) {
        if (t1.equals(Types.BYTE_TYPEREF) && t2.equals(Types.BYTE_TYPEREF))
        	return Types.BYTE_TYPEREF;
        if (TyperHelper.isA(t1, Types.INTEGER_TYPEREF) && TyperHelper.isA(t2, Types.INTEGER_TYPEREF))
        	return Types.INTEGER_TYPEREF;
        if (t1.equals(Types.ANY_TYPEREF) || t2.equals(Types.ANY_TYPEREF))
            return Types.ANY_TYPEREF;
        if ((!t1.equals(Types.INTEGER_TYPEREF) && !t1.equals(Types.REAL_TYPEREF)) || (!t2.equals(Types.INTEGER_TYPEREF) && !t2.equals(Types.REAL_TYPEREF))) {
            return Types.ERROR_TYPEREF;
        }
        if (!(TyperHelper.getBroadType(t1) == TyperHelper.getBroadType(t2))) //One Integer and one Real
            return Types.REAL_TYPEREF;
        return t1;
    }

    @Override
    public TypeRef casePlusExpression(PlusExpression object) {
        TypeRef t1 = computeTypeOf(object.getLhs());
        TypeRef t2 = computeTypeOf(object.getRhs());
        //This is to support string concatenation with +, which is somehow supported in ThingML, at least in Java and JS...
        //TODO: Decide if we should use + or a dedicated concatenation operator like ..
        if (t1.equals(Types.STRING_TYPEREF) && !t2.equals(Types.ERROR_TYPEREF) || t2.equals(Types.STRING_TYPEREF) && !t2.equals(Types.ERROR_TYPEREF)) {
        	return Types.STRING_TYPEREF;
        }
        return caseBinaryNumericalOperator(t1, t2);
    }

    @Override
    public TypeRef caseMinusExpression(MinusExpression object) {
        TypeRef t1 = computeTypeOf(object.getLhs());
        TypeRef t2 = computeTypeOf(object.getRhs());
        return caseBinaryNumericalOperator(t1, t2);
    }

    @Override
    public TypeRef caseTimesExpression(TimesExpression object) {
        TypeRef t1 = computeTypeOf(object.getLhs());
        TypeRef t2 = computeTypeOf(object.getRhs());
        return caseBinaryNumericalOperator(t1, t2);
    }

    @Override
    public TypeRef caseDivExpression(DivExpression object) {
        TypeRef t1 = computeTypeOf(object.getLhs());
        TypeRef t2 = computeTypeOf(object.getRhs());
        return caseBinaryNumericalOperator(t1, t2);
    }

    @Override
    public TypeRef caseModExpression(ModExpression object) {
        TypeRef t1 = computeTypeOf(object.getLhs());
        TypeRef t2 = computeTypeOf(object.getRhs());
        return caseBinaryNumericalOperator(t1, t2);
    }

    private TypeRef caseComparison(TypeRef t1, TypeRef t2) {
    	if (TyperHelper.isA(t1, t2) || TyperHelper.isA(t2, t1))
    		return Types.BOOLEAN_TYPEREF;
    	return Types.ERROR_TYPEREF;
    }

    @Override
    public TypeRef caseEqualsExpression(EqualsExpression object) {
        TypeRef t1 = computeTypeOf(object.getLhs());
        TypeRef t2 = computeTypeOf(object.getRhs());
        return caseComparison(t1, t2);
    }
    
    @Override
    public TypeRef caseNotEqualsExpression(NotEqualsExpression object) {
        TypeRef t1 = computeTypeOf(object.getLhs());
        TypeRef t2 = computeTypeOf(object.getRhs());
        return caseComparison(t1, t2);
    }    

    @Override
    public TypeRef caseGreaterExpression(GreaterExpression object) {
        TypeRef t1 = computeTypeOf(object.getLhs());
        TypeRef t2 = computeTypeOf(object.getRhs());
        return caseComparison(t1, t2);
    }

    @Override
    public TypeRef caseGreaterOrEqualExpression(GreaterOrEqualExpression object) {
        TypeRef t1 = computeTypeOf(object.getLhs());
        TypeRef t2 = computeTypeOf(object.getRhs());
        return caseComparison(t1, t2);
    }

    @Override
    public TypeRef caseLowerExpression(LowerExpression object) {
        TypeRef t1 = computeTypeOf(object.getLhs());
        TypeRef t2 = computeTypeOf(object.getRhs());
        return caseComparison(t1, t2);
    }

    @Override
    public TypeRef caseLowerOrEqualExpression(LowerOrEqualExpression object) {
        TypeRef t1 = computeTypeOf(object.getLhs());
        TypeRef t2 = computeTypeOf(object.getRhs());
        return caseComparison(t1, t2);
    }

    //Boolean
    private TypeRef caseBooleanOperator(TypeRef t1, TypeRef t2) {
    	if (t1.equals(Types.BOOLEAN_TYPEREF) && t2.equals(Types.BOOLEAN_TYPEREF))
    		return Types.BOOLEAN_TYPEREF;
    	if (TyperHelper.isA(t1, Types.BOOLEAN_TYPEREF) && TyperHelper.isA(t2, Types.BOOLEAN_TYPEREF))    	
            return Types.ANY_TYPEREF;        
        return Types.ERROR_TYPEREF;        
    }

    @Override
    public TypeRef caseAndExpression(AndExpression object) {
        TypeRef t1 = computeTypeOf(object.getLhs());
        TypeRef t2 = computeTypeOf(object.getRhs());
        return caseBooleanOperator(t1, t2);
    }

    @Override
    public TypeRef caseOrExpression(OrExpression object) {
        TypeRef t1 = computeTypeOf(object.getLhs());
        TypeRef t2 = computeTypeOf(object.getRhs());
        return caseBooleanOperator(t1, t2);
    }

    @Override
    public TypeRef caseNotExpression(NotExpression object) {
        TypeRef t = computeTypeOf(object.getTerm());
        if (t.equals(Types.BOOLEAN_TYPEREF))
            return Types.BOOLEAN_TYPEREF;        
        if (TyperHelper.isA(t, Types.BOOLEAN_TYPEREF))
            return Types.ANY_TYPEREF;        
        return Types.ERROR_TYPEREF;
    }
    //End Boolean

    @Override
    public TypeRef casePropertyReference(PropertyReference object) {
        return TyperHelper.getBroadType(object.getProperty().getTypeRef());
    }
    
    
    @Override
	public TypeRef caseEventReference(EventReference object) {
    	return TyperHelper.getBroadType(object.getParameter().getTypeRef());
	}
    
    @Override
	public TypeRef caseEnumLiteralRef(EnumLiteralRef object) {
    	//return TyperHelper.getBroadType(object.getEnum().getTypeRef());
    	if (object.getEnum().getTypeRef() != null)
    		return object.getEnum().getTypeRef();
    	return Types.ANY_TYPEREF;
	}    

    @Override
    public TypeRef caseFunctionCallExpression(FunctionCallExpression object) {
        if (object.getFunction().getTypeRef() == null || object.getFunction().getTypeRef().getType() == null)
            return Types.VOID_TYPEREF;
        return TyperHelper.getBroadType(object.getFunction().getTypeRef());
    }

    @Override
    public TypeRef caseArrayIndex(ArrayIndex object) {
        TypeRef t = computeTypeOf(object.getIndex());
        if (TyperHelper.isA(t, Types.INTEGER_TYPEREF)) {
            return Types.getTypeRef(computeTypeOf(object.getArray()), false);
        }
        return Types.ERROR_TYPEREF;
    }
    
    @Override
    public TypeRef caseArrayInit(ArrayInit object) {
    	final Map<String, TypeRef> types = new HashMap<String, TypeRef>();
    	for(Expression e : object.getValues()) {
    		final TypeRef tr = computeTypeOf(e);
    		if (tr.isIsArray()) return Types.ERROR_TYPEREF;
    		types.put(tr.getType().getName(), tr);
    	}
    	if (types.size() > 1) {
    		if (types.containsValue(Types.ANY_TYPEREF)) {
    			return Types.ANY_TYPEREF;
    		}
    		if (types.containsValue(Types.REAL_TYPEREF) 
    				&& !types.containsValue(Types.BOOLEAN_TYPEREF) 
    				&& !types.containsValue(Types.CHARACTER_TYPEREF)
    				&& !types.containsValue(Types.OBJECT_TYPEREF)
    				&& !types.containsValue(Types.STRING_TYPEREF)
    				&& !types.containsValue(Types.VOID_TYPEREF)
    			) return Types.REAL_TYPEREF;
    		if (types.containsValue(Types.INTEGER_TYPEREF) 
    				&& !types.containsValue(Types.BOOLEAN_TYPEREF) 
    				&& !types.containsValue(Types.CHARACTER_TYPEREF)
    				&& !types.containsValue(Types.OBJECT_TYPEREF)
    				&& !types.containsValue(Types.STRING_TYPEREF)
    				&& !types.containsValue(Types.VOID_TYPEREF)
    			) return Types.INTEGER_TYPEREF;
    		return Types.ERROR_TYPEREF;
    	}
    	
    	if (types.size() == 0 || types.size() > 1)
    		return Types.ERROR_TYPEREF;
    	return Types.getTypeRef(types.values().iterator().next(), true);
    }
    
}
