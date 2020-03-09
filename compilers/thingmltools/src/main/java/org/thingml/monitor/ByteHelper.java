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
package org.thingml.monitor;

import java.io.File;
import java.io.IOException;
import java.io.NotSerializableException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.SaveOptions;
import org.thingml.xtext.ThingMLStandaloneSetup;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.ArrayInit;
import org.thingml.xtext.thingML.CastExpression;
import org.thingml.xtext.thingML.ConditionalAction;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EnumerationLiteral;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExpressionGroup;
import org.thingml.xtext.thingML.ExternExpression;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.Variable;
import org.thingml.xtext.thingML.VariableAssignment;

public class ByteHelper {

	private static byte thingID = 0;
	private static byte functionID = 0;
	private static byte varID = 0;
	private static byte handlerID = 0;
	private static byte messageID = 0;
	private static byte portID = 0;
	
	public static byte thingID() {return thingID++;} 
	public static byte functionID() {return functionID++;}
	public static byte varID() {return varID++;}
	public static byte handlerID() {return handlerID++;}
	public static byte messageID() {return messageID++;}
	public static byte portID() {return portID++;}
	
	public static void reset() {
		thingID = 0;
		functionID = 0;
		varID = 0;
		handlerID = 0;
		messageID = 0;
		portID = 0;
	}
	
	public static EnumerationLiteral getLogLiteral(Thing thing, String name) {
		EnumerationLiteral result = null;
		for(Type t : ThingMLHelpers.findContainingModel(thing).getTypes()) {
			if (t instanceof Enumeration && t.getName().equals("LogType")) {
				for(EnumerationLiteral l : ((Enumeration)t).getLiterals()) {
					if (l.getName().equals(name)) {
						result = l;
						break;
					}
				}				
			}
		}
		return result;
	}
	
	public static VariableAssignment insertAt(Variable array, int index, Expression e) {
		final VariableAssignment pa_ = ThingMLFactory.eINSTANCE.createVariableAssignment();
		pa_.setProperty(array);
		final IntegerLiteral e_ = ThingMLFactory.eINSTANCE.createIntegerLiteral();
		e_.setIntValue(index);
		pa_.setIndex(e_);
		pa_.setExpression(e);
		return pa_;
	}
	
	private static LocalVariable createArray(String name, int size, ArrayInit arrayInit, TypeRef t) {
		final IntegerLiteral s = ThingMLFactory.eINSTANCE.createIntegerLiteral();
		s.setIntValue(size);
		final LocalVariable array = ThingMLFactory.eINSTANCE.createLocalVariable();
		array.setName(name);
		final TypeRef byteArray = EcoreUtil.copy(t);
		byteArray.setIsArray(true);
		byteArray.setCardinality(s);
		array.setTypeRef(byteArray);
		array.setInit(arrayInit);
		return array;
	}
	
	public static LocalVariable arrayInit(String name, TypeRef t, List<Expression> inits) {
		final ArrayInit arrayInit = ThingMLFactory.eINSTANCE.createArrayInit();
		for(Expression e : inits) {
    		arrayInit.getValues().add(e);
		}
		final LocalVariable array = createArray(name, inits.size(), arrayInit, t);
		array.setReadonly(true);
		return array;
	}
	
	public static LocalVariable arrayInit(int size, String name, TypeRef t) {
		final ArrayInit arrayInit = ThingMLFactory.eINSTANCE.createArrayInit();
		for(int i = 0; i < size; i++) {
			final IntegerLiteral s = ThingMLFactory.eINSTANCE.createIntegerLiteral();
    		s.setIntValue(0);
    		arrayInit.getValues().add(s);
		}    	
		return createArray(name, size, arrayInit, t);		
	}
	
    //FIXME: this has nothing to do here. load/save is currently in compiler framework, not accessible from here. This should be part of the thingml project, together with metamodel, etc
    public static void save(ThingMLModel model, String location) throws IOException {
    	ThingMLStandaloneSetup.doSetup();    	
    	if (!model.getImports().isEmpty())
    		throw new Error("Only models without imports can be saved with this method. Use the 'flattenModel' method first.");
    	
        ResourceSet rs = new ResourceSetImpl();
        Resource res = rs.createResource(URI.createFileURI(location));

        res.getContents().add(model);
        EcoreUtil.resolveAll(res);
        
        SaveOptions opt = SaveOptions.newBuilder().format().noValidation().getOptions();
        res.save(opt.toOptionsMap());
    }

    //FIXME: this has nothing to do here. load/save is currently in compiler framework, not accessible from here. This should be part of the thingml project, together with metamodel, etc
    public static ThingMLModel load(File file) throws IOException {
    	ThingMLStandaloneSetup.doSetup();    	
        ResourceSet rs = new ResourceSetImpl();
        URI xmiuri = URI.createFileURI(file.getAbsolutePath());
        Resource model = rs.createResource(xmiuri);
        model.load(null);
        EcoreUtil.resolveAll(model);
        ThingMLModel m = (ThingMLModel) model.getContents().get(0);
        return m;        
    }
	
    
    public static List<Expression> serializeParam(TypeRef byteTypeRef, Expression param, long size) {
    	final List<Expression> sParam = new ArrayList<Expression>();
		if (size == 1) {
			final CastExpression c = ThingMLFactory.eINSTANCE.createCastExpression();
			c.setType(byteTypeRef.getType());								
			c.setTerm(param);							
			sParam.add(c);			
		} else {
			for (int j = 0; j < size; j++) {																		
				final CastExpression cast = ThingMLFactory.eINSTANCE.createCastExpression();
				cast.setType(byteTypeRef.getType());
				final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();	                			
				final ExternExpression expr = ThingMLFactory.eINSTANCE.createExternExpression();
				expr.setExpression("((");
				expr.getSegments().add(EcoreUtil.copy(param));
				final ExternExpression bitshift = ThingMLFactory.eINSTANCE.createExternExpression();
				bitshift.setExpression(" >> "+8*(size-1-j)+") & 0xFF)");
				expr.getSegments().add(bitshift);
				group.setTerm(expr);
				cast.setTerm(group);		
				sParam.add(cast);
			}
		}
		return sParam;
	}
    
    public static List<Expression> serializeParam(TypeRef byteTypeRef, PropertyReference param) {
    	long size = 0;
    	if (param.getProperty().getTypeRef().getType() instanceof PrimitiveType)
    		size = ((PrimitiveType)param.getProperty().getTypeRef().getType()).getByteSize();
    	else if (param.getProperty().getTypeRef().getType() instanceof Enumeration)
    		size = ((PrimitiveType)((Enumeration)param.getProperty().getTypeRef().getType()).getTypeRef().getType()).getByteSize();
    	else {
			try { //FIXME: somehow ugly error management :-)
				throw new NotSerializableException("Property " + param.getProperty().getName() + " cannot be serialized. Its type is " + param.getProperty().getTypeRef().getType().getName());
			} catch (NotSerializableException nse) {
				nse.printStackTrace();
			}
		}
		return serializeParam(byteTypeRef, param, size);
    }
    
    public static List<Expression> serializeParam(TypeRef byteTypeRef, Variable param) {
    	long size = 0;
    	if (param.getTypeRef().getType() instanceof PrimitiveType)
    		size = ((PrimitiveType)param.getTypeRef().getType()).getByteSize();
    	else if (param.getTypeRef().getType() instanceof Enumeration)
    		size = ((PrimitiveType)((Enumeration)param.getTypeRef().getType()).getTypeRef().getType()).getByteSize();
    	else {
			try { //FIXME: somehow ugly error management :-)
				throw new NotSerializableException("Variable " + param.getName() + " cannot be serialized. Its type is " + param.getTypeRef().getType().getName());
			} catch (NotSerializableException nse) {
				nse.printStackTrace();
			}
		}
		final PropertyReference pr = ThingMLFactory.eINSTANCE.createPropertyReference();
		pr.setProperty(param);			
		return serializeParam(byteTypeRef, pr, size);
	}
    
    
	public static void serializeParam(TypeRef byteTypeRef, Variable param, ActionBlock block, int blockIndex, int index, Variable array) {
		final long size = ((PrimitiveType)param.getTypeRef().getType()).getByteSize();
		if (size == 1) {
			final TypeRef tr = TyperHelper.getBroadType(param.getTypeRef());
			if (tr == Types.BOOLEAN_TYPEREF) {
				final LocalVariable lv = ThingMLFactory.eINSTANCE.createLocalVariable();
				final IntegerLiteral l = ThingMLFactory.eINSTANCE.createIntegerLiteral();
				l.setIntValue(0);
				lv.setTypeRef(EcoreUtil.copy(byteTypeRef));
				lv.setInit(l);
				lv.setName(param.getName() + "_byte");
				final VariableAssignment pa = ThingMLFactory.eINSTANCE.createVariableAssignment();
				final IntegerLiteral l2 = ThingMLFactory.eINSTANCE.createIntegerLiteral();
				l2.setIntValue(1);
				pa.setProperty(lv);
				pa.setExpression(l2);
				final ConditionalAction c = ThingMLFactory.eINSTANCE.createConditionalAction();
				final PropertyReference r = ThingMLFactory.eINSTANCE.createPropertyReference();
				r.setProperty(param);
				c.setCondition(r);
				c.setAction(pa);
				block.getActions().add(blockIndex++, lv);
				block.getActions().add(blockIndex++, c);
								
				final PropertyReference pr = ThingMLFactory.eINSTANCE.createPropertyReference();
				pr.setProperty(lv);				
				final VariableAssignment pa2 = insertAt(array, index++, pr);				
				block.getActions().add(blockIndex++, pa2);
			} else {	
				final CastExpression c = ThingMLFactory.eINSTANCE.createCastExpression();
				c.setType(byteTypeRef.getType());
				final PropertyReference pr = ThingMLFactory.eINSTANCE.createPropertyReference();
				pr.setProperty(param);								
				c.setTerm(pr);								
				final VariableAssignment pa = insertAt(array, index++, c);		
				block.getActions().add(blockIndex++, pa);
			}
		} else {
			for (int j = 0; j < size; j++) {
				final PropertyReference pr = ThingMLFactory.eINSTANCE.createPropertyReference();
				pr.setProperty(param);																		
				final CastExpression cast = ThingMLFactory.eINSTANCE.createCastExpression();
				cast.setType(byteTypeRef.getType());
				final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();	                			
				final ExternExpression expr = ThingMLFactory.eINSTANCE.createExternExpression();
				expr.setExpression("((");
				expr.getSegments().add(EcoreUtil.copy(pr));
				final ExternExpression bitshift = ThingMLFactory.eINSTANCE.createExternExpression();
				bitshift.setExpression(" >> "+8*(size-1-j)+") & 0xFF)");
				expr.getSegments().add(bitshift);
				group.setTerm(expr);
				cast.setTerm(group);								
				final VariableAssignment pa = insertAt(array, index++, cast);		
				block.getActions().add(blockIndex++, pa);
			}
		}
	}

}
