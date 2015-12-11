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
import org.sintef.thingml.impl.ThingmlPackageImpl;
import org.sintef.thingml.util.ThingmlSwitch;


public class TypeChecker extends ThingmlSwitch<Type> {
	
	public Type computeTypeOf(Expression exp) {
		Type result = null;
		try {
			result = doSwitch(exp);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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
	public Type caseNotExpression(NotExpression object) {
		Type t = computeTypeOf(object.getTerm());
		if (t.equals(Types.ANY_TYPE))
			return Types.ANY_TYPE;
		if (!t.equals(Types.BOOLEAN_TYPE)) {
			return Types.ERROR_TYPE;
		}
		return Types.BOOLEAN_TYPE;
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

	@Override
	public Type casePlusExpression(PlusExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		if (t1.equals(Types.ANY_TYPE) || t2.equals(Types.ANY_TYPE))
			return Types.ANY_TYPE;
		if (!t1.equals(Types.INTEGER_TYPE) && !t1.equals(Types.REAL_TYPE) && !t2.equals(Types.INTEGER_TYPE) && !t2.equals(Types.REAL_TYPE)) {
			return Types.ERROR_TYPE;
		}
		if (!t1.getBroadType().getName().equals(t2.getBroadType().getName())) //One Integer and one Real
			return Types.REAL_TYPE;
		return t1;
	}

	@Override
	public Type caseMinusExpression(MinusExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		if (t1.equals(Types.ANY_TYPE) || t2.equals(Types.ANY_TYPE))
			return Types.ANY_TYPE;
		if (!t1.equals(Types.INTEGER_TYPE) && !t1.equals(Types.REAL_TYPE) && !t2.equals(Types.INTEGER_TYPE) && !t2.equals(Types.REAL_TYPE)) {
			return Types.ERROR_TYPE;
		}
		if (!t1.getBroadType().getName().equals(t2.getBroadType().getName())) //One Integer and one Real
			return Types.REAL_TYPE;
		return t1;
	}

	@Override
	public Type caseTimesExpression(TimesExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		if (t1.equals(Types.ANY_TYPE) || t2.equals(Types.ANY_TYPE))
			return Types.ANY_TYPE;
		if (!t1.equals(Types.INTEGER_TYPE) && !t1.equals(Types.REAL_TYPE) && !t2.equals(Types.INTEGER_TYPE) && !t2.equals(Types.REAL_TYPE)) {
			return Types.ERROR_TYPE;
		}
		if (!t1.getBroadType().getName().equals(t2.getBroadType().getName())) //One Integer and one Real
			return Types.REAL_TYPE;
		return t1;
	}

	@Override
	public Type caseDivExpression(DivExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		if (t1.equals(Types.ANY_TYPE) || t2.equals(Types.ANY_TYPE))
			return Types.ANY_TYPE;
		if (!t1.equals(Types.INTEGER_TYPE) && !t1.equals(Types.REAL_TYPE) && !t2.equals(Types.INTEGER_TYPE) && !t2.equals(Types.REAL_TYPE)) {
			return Types.ERROR_TYPE;
		}
		if (!t1.getBroadType().getName().equals(t2.getBroadType().getName())) //One Integer and one Real
			return Types.REAL_TYPE;
		return t1;
	}

	@Override
	public Type caseModExpression(ModExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		if (t1.equals(Types.ANY_TYPE) || t2.equals(Types.ANY_TYPE))
			return Types.ANY_TYPE;
		if (!t1.equals(Types.INTEGER_TYPE) && !t2.equals(Types.INTEGER_TYPE)) {
			return Types.ERROR_TYPE;
		}
		return Types.INTEGER_TYPE;
	}

	@Override
	public Type caseEqualsExpression(EqualsExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		// TODO: Check that the types are compatible
		return Types.BOOLEAN_TYPE;
	}

	@Override
	public Type caseGreaterExpression(GreaterExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		// TODO: Check that the types are compatible
		return Types.BOOLEAN_TYPE;
	}

	@Override
	public Type caseAndExpression(AndExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		if (t1.equals(Types.ANY_TYPE) || t2.equals(Types.ANY_TYPE))
			return Types.ANY_TYPE;
		if (!t1.equals(Types.BOOLEAN_TYPE) && !t2.equals(Types.BOOLEAN_TYPE)) {
			return Types.ERROR_TYPE;
		}
		return Types.BOOLEAN_TYPE;
	}

	@Override
	public Type caseOrExpression(OrExpression object) {
		Type t1 = computeTypeOf(object.getLhs());
		Type t2 = computeTypeOf(object.getRhs());
		if (t1.equals(Types.ANY_TYPE) || t2.equals(Types.ANY_TYPE))
			return Types.ANY_TYPE;
		if (!t1.equals(Types.BOOLEAN_TYPE) && !t2.equals(Types.BOOLEAN_TYPE)) {
			return Types.ERROR_TYPE;
		}
		return Types.BOOLEAN_TYPE;
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
		return Types.BOOLEAN_TYPE;
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
		return Types.ANY_TYPE;
	}

	@Override
	public Type caseFunctionCallExpression(FunctionCallExpression object) {
		Type t = object.getFunction().getType().getBroadType();
		return t;
	}
}
