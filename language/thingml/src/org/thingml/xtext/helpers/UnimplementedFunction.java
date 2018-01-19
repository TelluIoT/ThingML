package org.thingml.xtext.helpers;
public class UnimplementedFunction extends Exception {
	private static final long serialVersionUID = -8520348913238239555L;

	public UnimplementedFunction(String msg) {
		super(msg);
	}
}