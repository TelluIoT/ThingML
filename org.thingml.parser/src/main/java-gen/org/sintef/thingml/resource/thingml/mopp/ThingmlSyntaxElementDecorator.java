/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.mopp;

public class ThingmlSyntaxElementDecorator {
	
	/**
	 * the syntax element to be decorated
	 */
	private org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement decoratedElement;
	
	/**
	 * an array of child decorators (one decorator per child of the decorated syntax
	 * element
	 */
	private ThingmlSyntaxElementDecorator[] childDecorators;
	
	/**
	 * a list of the indices that must be printed
	 */
	private java.util.List<Integer> indicesToPrint = new java.util.ArrayList<Integer>();
	
	public ThingmlSyntaxElementDecorator(org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement decoratedElement, ThingmlSyntaxElementDecorator[] childDecorators) {
		super();
		this.decoratedElement = decoratedElement;
		this.childDecorators = childDecorators;
	}
	
	public void addIndexToPrint(Integer index) {
		indicesToPrint.add(index);
	}
	
	public org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement getDecoratedElement() {
		return decoratedElement;
	}
	
	public ThingmlSyntaxElementDecorator[] getChildDecorators() {
		return childDecorators;
	}
	
	public Integer getNextIndexToPrint() {
		if (indicesToPrint.size() == 0) {
			return null;
		}
		return indicesToPrint.remove(0);
	}
	
	public String toString() {
		return "" + getDecoratedElement();
	}
	
}
