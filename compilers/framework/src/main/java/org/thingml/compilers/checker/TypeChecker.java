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
import org.sintef.thingml.impl.ThingmlPackageImpl;
import org.sintef.thingml.util.ThingmlSwitch;


public class TypeChecker extends ThingmlSwitch<Type> {
	
	public static Type ANY_TYPE;
	public static Type ERROR_TYPE;
	public static Type INTEGER_TYPE;
	public static Type BOOLEAN_TYPE;
	public static Type STRING_TYPE;
	public static Type REAL_TYPE;
	
	static {
		ThingmlFactory factory = ThingmlPackageImpl.init().getThingmlFactory();
		ANY_TYPE = factory.createPrimitiveType();
		ANY_TYPE.setName("Any");
		PlatformAnnotation any = factory.createPlatformAnnotation();
		any.setName("type_checker");
		any.setValue("Any");
		ANY_TYPE.getAnnotations().add(any);
		ERROR_TYPE = factory.createPrimitiveType();
		ERROR_TYPE.setName("Error");
		PlatformAnnotation error = factory.createPlatformAnnotation();
		error.setName("type_checker");
		error.setValue("Error");
		ERROR_TYPE.getAnnotations().add(error);
		INTEGER_TYPE = factory.createPrimitiveType();
		INTEGER_TYPE.setName("Integer");
		PlatformAnnotation integer = factory.createPlatformAnnotation();
		integer.setName("type_checker");
		integer.setValue("Integer");
		INTEGER_TYPE.getAnnotations().add(integer);
		BOOLEAN_TYPE = factory.createPrimitiveType();
		BOOLEAN_TYPE.setName("Boolean");
		PlatformAnnotation bool = factory.createPlatformAnnotation();
		bool.setName("type_checker");
		bool.setValue("Boolean");
		BOOLEAN_TYPE.getAnnotations().add(bool);
		STRING_TYPE = factory.createPrimitiveType();
		STRING_TYPE.setName("String");
		PlatformAnnotation string = factory.createPlatformAnnotation();
		string.setName("type_checker");
		string.setValue("String");
		STRING_TYPE.getAnnotations().add(string);
		REAL_TYPE = factory.createPrimitiveType();
		REAL_TYPE.setName("Real");
		PlatformAnnotation real = factory.createPlatformAnnotation();
		real.setName("type_checker");
		real.setValue("Real");
		REAL_TYPE.getAnnotations().add(real);
	}
	
	public Type computeTypeOf(Expression exp) {
		Type result = null;
		try {
			result = doSwitch(exp);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Type caseExternExpression(ExternExpression object) {
		return ANY_TYPE;
	}

	@Override
	public Type caseIntegerLiteral(IntegerLiteral object) {
		return INTEGER_TYPE;
	}

	@Override
	public Type caseBooleanLiteral(BooleanLiteral object) {
		return BOOLEAN_TYPE;
	}

	@Override
	public Type caseStringLiteral(StringLiteral object) {
		return STRING_TYPE;
	}

	@Override
	public Type caseDoubleLiteral(DoubleLiteral object) {
		return REAL_TYPE;
	}

	@Override
	public Type caseNotExpression(NotExpression object) {
		Type t = computeTypeOf(object.getTerm());
		if (t.equals(ANY_TYPE))
			return ANY_TYPE;
		if (!t.equals(BOOLEAN_TYPE)) {
			return ERROR_TYPE;
		}
		return BOOLEAN_TYPE;
	}

	@Override
	public Type caseUnaryMinus(UnaryMinus object) {
		Type t = computeTypeOf(object.getTerm());
		if (t.equals(ANY_TYPE))
			return ANY_TYPE;
		if (!t.equals(INTEGER_TYPE) && !t.equals(REAL_TYPE)) {
			return ERROR_TYPE;
		}
		return t;
	}

	@Override
	public Type casePlusExpression(PlusExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		if (t1.equals(ANY_TYPE) || t2.equals(ANY_TYPE))
			return ANY_TYPE;
		if (!t1.equals(INTEGER_TYPE) && !t1.equals(REAL_TYPE) && !t2.equals(INTEGER_TYPE) && !t2.equals(REAL_TYPE)) {
			return ERROR_TYPE;
		}
		if (!t1.getBroadType().getName().equals(t2.getBroadType().getName())) //One Integer and one Real
			return REAL_TYPE;
		return t1;
	}

	@Override
	public Type caseMinusExpression(MinusExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		if (t1.equals(ANY_TYPE) || t2.equals(ANY_TYPE))
			return ANY_TYPE;
		if (!t1.equals(INTEGER_TYPE) && !t1.equals(REAL_TYPE) && !t2.equals(INTEGER_TYPE) && !t2.equals(REAL_TYPE)) {
			return ERROR_TYPE;
		}
		if (!t1.getBroadType().getName().equals(t2.getBroadType().getName())) //One Integer and one Real
			return REAL_TYPE;
		return t1;
	}

	@Override
	public Type caseTimesExpression(TimesExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		if (t1.equals(ANY_TYPE) || t2.equals(ANY_TYPE))
			return ANY_TYPE;
		if (!t1.equals(INTEGER_TYPE) && !t1.equals(REAL_TYPE) && !t2.equals(INTEGER_TYPE) && !t2.equals(REAL_TYPE)) {
			return ERROR_TYPE;
		}
		if (!t1.getBroadType().getName().equals(t2.getBroadType().getName())) //One Integer and one Real
			return REAL_TYPE;
		return t1;
	}

	@Override
	public Type caseDivExpression(DivExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		if (t1.equals(ANY_TYPE) || t2.equals(ANY_TYPE))
			return ANY_TYPE;
		if (!t1.equals(INTEGER_TYPE) && !t1.equals(REAL_TYPE) && !t2.equals(INTEGER_TYPE) && !t2.equals(REAL_TYPE)) {
			return ERROR_TYPE;
		}
		if (!t1.getBroadType().getName().equals(t2.getBroadType().getName())) //One Integer and one Real
			return REAL_TYPE;
		return t1;
	}

	@Override
	public Type caseModExpression(ModExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		if (t1.equals(ANY_TYPE) || t2.equals(ANY_TYPE))
			return ANY_TYPE;
		if (!t1.equals(INTEGER_TYPE) && !t2.equals(INTEGER_TYPE)) {
			return ERROR_TYPE;
		}
		return INTEGER_TYPE;
	}

	@Override
	public Type caseEqualsExpression(EqualsExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		// TODO: Check that the types are compatible
		return BOOLEAN_TYPE;
	}

	@Override
	public Type caseGreaterExpression(GreaterExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		// TODO: Check that the types are compatible
		return BOOLEAN_TYPE;
	}

	@Override
	public Type caseAndExpression(AndExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		if (t1.equals(ANY_TYPE) || t2.equals(ANY_TYPE))
			return ANY_TYPE;
		if (!t1.equals(BOOLEAN_TYPE) && !t2.equals(BOOLEAN_TYPE)) {
			return ERROR_TYPE;
		}
		return BOOLEAN_TYPE;
	}

	@Override
	public Type caseOrExpression(OrExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		if (t1.equals(ANY_TYPE) || t2.equals(ANY_TYPE))
			return ANY_TYPE;
		if (!t1.equals(BOOLEAN_TYPE) && !t2.equals(BOOLEAN_TYPE)) {
			return ERROR_TYPE;
		}
		return BOOLEAN_TYPE;
	}

	@Override
	public Type casePropertyReference(PropertyReference object) {
		return object.getProperty().getType().getBroadType();
	}

	@Override
	public Type caseExpressionGroup(ExpressionGroup object) {
		return computeTypeOf(object.getExp());
	}

	@Override
	public Type caseLowerExpression(LowerExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		// TODO: Check that the types are compatible
		return BOOLEAN_TYPE;
	}

	@Override
	public Type caseReference(Reference object) {
		if (object.getReference() instanceof ReceiveMessage) {
			ReceiveMessage rm = (ReceiveMessage) object.getReference();
			if (object.getParameter() instanceof SimpleParamRef) {
				SimpleParamRef ref = (SimpleParamRef) object.getParameter();
				return ref.getParameterRef().getType().getBroadType();
			}
		}
		return ANY_TYPE;
	}
}
