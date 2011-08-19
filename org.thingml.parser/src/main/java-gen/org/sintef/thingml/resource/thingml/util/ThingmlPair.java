/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.util;

/**
 * A typed pair of objects.
 * 
 * @param <T1> the type of the first (left) object
 * @param <T2> the type of the second (right) object
 */
public class ThingmlPair<T1, T2> {
	
	private T1 left;
	private T2 right;
	
	public ThingmlPair(T1 left, T2 right) {
		this.left = left;
		this.right = right;
	}
	
	public T1 getLeft() {
		return left;
	}
	
	public T2 getRight() {
		return right;
	}
	
	@Override	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}
	
	@Override	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ThingmlPair<?,?> other = (ThingmlPair<?,?>) obj;
		if (left == null) {
			if (other.left != null) {
				return false;
			}
		} else if (!left.equals(other.left)) {
			return false;
		}
		if (right == null) {
			if (other.right != null) {
				return false;
			}
		} else if (!right.equals(other.right)) {
			return false;
		}
		return true;
	}
	
}
