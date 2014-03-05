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
package org.sintef.thingml.constraints;


import org.sintef.thingml.*;
import org.sintef.thingml.impl.ThingmlPackageImpl;
import org.sintef.thingml.util.ThingmlSwitch;


public class TypeChecker extends ThingmlSwitch<Type> {
	
	public static Type ANY_TYPE;
	public static Type INTEGER_TYPE;
	public static Type BOOLEAN_TYPE;
	public static Type STRING_TYPE;
	public static Type REAL_TYPE;
	
	static {
		ThingmlFactory factory = ThingmlPackageImpl.init().getThingmlFactory();
		ANY_TYPE = factory.createPrimitiveType();
		ANY_TYPE.setName("Any");
		INTEGER_TYPE = factory.createPrimitiveType();
		INTEGER_TYPE.setName("Integer");
		BOOLEAN_TYPE = factory.createPrimitiveType();
		BOOLEAN_TYPE.setName("Boolean");
		STRING_TYPE = factory.createPrimitiveType();
		STRING_TYPE.setName("String");
		REAL_TYPE = factory.createPrimitiveType();
		REAL_TYPE.setName("Real");
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
//		if (!t.equals(BOOLEAN_TYPE)) {
//			errors.put(object.getTerm(), "Expected Boolean, found " + t.getName() + ".");
//		}
		return BOOLEAN_TYPE;
	}

	@Override
	public Type caseUnaryMinus(UnaryMinus object) {
		Type t = computeTypeOf(object.getTerm());
//		if (!t.equals(INTEGER_TYPE)) {
//			errors.put(object.getTerm(), "Expected Integer, found " + t.getName() + ".");
//		}
		return INTEGER_TYPE;
	}

	@Override
	public Type casePlusExpression(PlusExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
//		if (!t1.equals(INTEGER_TYPE)) {
//			errors.put(object.getLhs(), "Expected Integer, found " + t1.getName() + ".");
//		}
//		if (!t2.equals(INTEGER_TYPE)) {
//			errors.put(object.getRhs(), "Expected Integer, found " + t2.getName() + ".");
//		}
		return INTEGER_TYPE;
	}

	@Override
	public Type caseMinusExpression(MinusExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
//		if (!t1.equals(INTEGER_TYPE)) {
//			errors.put(object.getLhs(), "Expected Integer, found " + t1.getName() + ".");
//		}
//		if (!t2.equals(INTEGER_TYPE)) {
//			errors.put(object.getRhs(), "Expected Integer, found " + t2.getName() + ".");
//		}
		return INTEGER_TYPE;
	}

	@Override
	public Type caseTimesExpression(TimesExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
//		if (!t1.equals(INTEGER_TYPE)) {
//			errors.put(object.getLhs(), "Expected Integer, found " + t1.getName() + ".");
//		}
//		if (!t2.equals(INTEGER_TYPE)) {
//			errors.put(object.getRhs(), "Expected Integer, found " + t2.getName() + ".");
//		}
		return INTEGER_TYPE;
	}

	@Override
	public Type caseDivExpression(DivExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
//		if (!t1.equals(INTEGER_TYPE)) {
//			errors.put(object.getLhs(), "Expected Integer, found " + t1.getName() + ".");
//		}
//		if (!t2.equals(INTEGER_TYPE)) {
//			errors.put(object.getRhs(), "Expected Integer, found " + t2.getName() + ".");
//		}
		return INTEGER_TYPE;
	}

	@Override
	public Type caseModExpression(ModExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
//		if (!t1.equals(INTEGER_TYPE)) {
//			errors.put(object.getLhs(), "Expected Integer, found " + t1.getName() + ".");
//		}
//		if (!t2.equals(INTEGER_TYPE)) {
//			errors.put(object.getRhs(), "Expected Integer, found " + t2.getName() + ".");
//		}
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
//		if (!t1.equals(INTEGER_TYPE)) {
//			errors.put(object.getLhs(), "Expected Integer, found " + t1.getName() + ".");
//		}
//		if (!t2.equals(INTEGER_TYPE)) {
//			errors.put(object.getRhs(), "Expected Integer, found " + t2.getName() + ".");
//		}
		return BOOLEAN_TYPE;
	}

	@Override
	public Type caseAndExpression(AndExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
//		if (!t1.equals(BOOLEAN_TYPE)) {
//			errors.put(object.getLhs(), "Expected Integer, found " + t1.getName() + ".");
//		}
//		if (!t2.equals(BOOLEAN_TYPE)) {
//			errors.put(object.getRhs(), "Expected Integer, found " + t2.getName() + ".");
//		}
		return BOOLEAN_TYPE;
	}

	@Override
	public Type caseOrExpression(OrExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
//		if (!t1.equals(BOOLEAN_TYPE)) {
//			errors.put(object.getLhs(), "Expected Integer, found " + t1.getName() + ".");
//		}
//		if (!t2.equals(BOOLEAN_TYPE)) {
//			errors.put(object.getRhs(), "Expected Integer, found " + t2.getName() + ".");
//		}
		return BOOLEAN_TYPE;
	}

	@Override
	public Type casePropertyReference(PropertyReference object) {
		return object.getProperty().getType();
	}

	@Override
	public Type caseExpressionGroup(ExpressionGroup object) {
		return computeTypeOf(object.getExp());
	}

	@Override
	public Type caseLowerExpression(LowerExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
//		if (!t1.equals(INTEGER_TYPE)) {
//			errors.put(object.getLhs(), "Expected Integer, found " + t1.getName() + ".");
//		}
//		if (!t2.equals(INTEGER_TYPE)) {
//			errors.put(object.getRhs(), "Expected Integer, found " + t2.getName() + ".");
//		}
		return BOOLEAN_TYPE;
	}
	
	

}
