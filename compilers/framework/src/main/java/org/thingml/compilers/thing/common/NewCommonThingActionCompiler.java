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
package org.thingml.compilers.thing.common;

import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.thing.NewThingActionCompiler;
import org.thingml.xtext.ByteValueConverter;
import org.thingml.xtext.CharValueConverter;
import org.thingml.xtext.StringValueConverter;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.AndExpression;
import org.thingml.xtext.thingML.BooleanLiteral;
import org.thingml.xtext.thingML.ByteLiteral;
import org.thingml.xtext.thingML.CharLiteral;
import org.thingml.xtext.thingML.DivExpression;
import org.thingml.xtext.thingML.DoubleLiteral;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExpressionGroup;
import org.thingml.xtext.thingML.ExternExpression;
import org.thingml.xtext.thingML.ExternStatement;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.MinusExpression;
import org.thingml.xtext.thingML.ModExpression;
import org.thingml.xtext.thingML.NotExpression;
import org.thingml.xtext.thingML.OrExpression;
import org.thingml.xtext.thingML.PlusExpression;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.TimesExpression;
import org.thingml.xtext.thingML.UnaryMinus;

public class NewCommonThingActionCompiler extends NewThingActionCompiler {
	
	@Override
	public void generate(ActionBlock action, Section section, Context ctx) {
		for (Action a : action.getActions()) {
			generate(a, section, ctx);
		}
	}
	
	@Override
	public void generate(ExternStatement action, Section section, Context ctx) {
		Section line = section.section("externstatement");
		line.append(action.getStatement());
		for (Expression exp : action.getSegments()) {
			generate(exp, line, ctx);
		}
	}
	
	
	@Override
	public void generate(ExternExpression expression, Section section, Context ctx) {
		section.append(expression.getExpression());
		for (Expression e : expression.getSegments()) {
			generate(e, section, ctx);
		}
	}
	
	@Override
	public void generate(ExpressionGroup expression, Section section, Context ctx) {
		generate(expression.getTerm(), section.section("expressiongroup").surroundWith("(", ")"), ctx);
	}
	
	@Override
	public void generate(PlusExpression expression, Section section, Context ctx) {
		generate(expression.getLhs(), section.section("lhs"), ctx);
		section.append(" + ");
		generate(expression.getRhs(), section.section("rhs"), ctx);
	}
	
	@Override
	public void generate(MinusExpression expression, Section section, Context ctx) {
		generate(expression.getLhs(), section.section("lhs"), ctx);
		section.append(" - ");
		generate(expression.getRhs(), section.section("rhs"), ctx);
	}
	
	@Override
	public void generate(TimesExpression expression, Section section, Context ctx) {
		generate(expression.getLhs(), section.section("lhs"), ctx);
		section.append(" * ");
		generate(expression.getRhs(), section.section("rhs"), ctx);
	}
	
	@Override
	public void generate(DivExpression expression, Section section, Context ctx) {
		generate(expression.getLhs(), section.section("lhs"), ctx);
		section.append(" / ");
		generate(expression.getRhs(), section.section("rhs"), ctx);
	}
	
	@Override
	public void generate(ModExpression expression, Section section, Context ctx) {
		generate(expression.getLhs(), section.section("lhs"), ctx);
		section.append(" % ");
		generate(expression.getRhs(), section.section("rhs"), ctx);
	}
	
	@Override
	public void generate(UnaryMinus expression, Section section, Context ctx) {
		section.append(" -");
		generate(expression.getTerm(), section.section("term").surroundWith("(", ")"), ctx);
	}
	
	@Override
	public void generate(NotExpression expression, Section section, Context ctx) {
		section.append(" !");
		generate(expression.getTerm(), section.section("term").surroundWith("(", ")"), ctx);
	}
	
	@Override
	public void generate(OrExpression expression, Section section, Context ctx) {
		generate(expression.getLhs(), section.section("lhs"), ctx);
		section.append(" || ");
		generate(expression.getRhs(), section.section("rhs"), ctx);
	}
	
	@Override
	public void generate(AndExpression expression, Section section, Context ctx) {
		generate(expression.getLhs(), section.section("lhs"), ctx);
		section.append(" && ");
		generate(expression.getRhs(), section.section("rhs"), ctx);
	}
	
	@Override
	public void generate(ByteLiteral expression, Section section, Context ctx) {
		section.append(ByteValueConverter.ToString(expression.getByteValue()));
	}
	
	@Override
	public void generate(CharLiteral expression, Section section, Context ctx) {
		section.append(CharValueConverter.ToString(expression.getCharValue()));
	}
	
	@Override
	public void generate(IntegerLiteral expression, Section section, Context ctx) {
		section.append(expression.getIntValue());
	}
	
	@Override
	public void generate(DoubleLiteral expression, Section section, Context ctx) {
		section.append(expression.getDoubleValue());
	}
	
	@Override
	public void generate(StringLiteral expression, Section section, Context ctx) {
		section.append(StringValueConverter.ToString(expression.getStringValue()));
	}
	
	@Override
	public void generate(BooleanLiteral expression, Section section, Context ctx) {
		section.append(Boolean.toString(expression.isBoolValue()));
	}
}
