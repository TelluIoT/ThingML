/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.util;

public class ThingmlCopiedEList<E> implements org.eclipse.emf.common.util.EList<E> {
	
	private org.eclipse.emf.common.util.EList<E> original;
	private org.eclipse.emf.common.util.EList<E> copy;
	
	@SuppressWarnings("unchecked")	
	public ThingmlCopiedEList(org.eclipse.emf.common.util.EList<E> original) {
		super();
		this.original = original;
		this.copy = new org.eclipse.emf.common.util.BasicEList<E>();
		Object[] originalContent = this.original.toArray();
		for (Object next : originalContent) {
			this.copy.add((E) next);
		}
	}
	
	public void move(int newPosition, E object) {
		original.move(newPosition, object);
		copy.move(newPosition, object);
	}
	
	public E move(int newPosition, int oldPosition) {
		copy.move(newPosition, oldPosition);
		return original.move(newPosition, oldPosition);
	}
	
	public boolean add(E o) {
		copy.add(o);
		return original.add(o);
	}
	
	public void add(int index, E element) {
		copy.add(index, element);
		original.add(index, element);
	}
	
	public boolean addAll(java.util.Collection<? extends E> c) {
		copy.addAll(c);
		return original.addAll(c);
	}
	
	public boolean addAll(int index, java.util.Collection<? extends E> c) {
		copy.addAll(index, c);
		return original.addAll(index, c);
	}
	
	public void clear() {
		copy.clear();
		original.clear();
	}
	
	public boolean contains(Object o) {
		return copy.contains(o);
	}
	
	public boolean containsAll(java.util.Collection<?> c) {
		return copy.containsAll(c);
	}
	
	public E get(int index) {
		return copy.get(index);
	}
	
	public int indexOf(Object o) {
		return copy.indexOf(o);
	}
	
	public boolean isEmpty() {
		return copy.isEmpty();
	}
	
	public java.util.Iterator<E> iterator() {
		return copy.iterator();
	}
	
	public int lastIndexOf(Object o) {
		return copy.lastIndexOf(o);
	}
	
	public java.util.ListIterator<E> listIterator() {
		return copy.listIterator();
	}
	
	public java.util.ListIterator<E> listIterator(int index) {
		return copy.listIterator(index);
	}
	
	public boolean remove(Object o) {
		copy.remove(o);
		return original.remove(o);
	}
	
	public E remove(int index) {
		copy.remove(index);
		return original.remove(index);
	}
	
	public boolean removeAll(java.util.Collection<?> c) {
		copy.removeAll(c);
		return original.removeAll(c);
	}
	
	public boolean retainAll(java.util.Collection<?> c) {
		copy.retainAll(c);
		return original.retainAll(c);
	}
	
	public E set(int index, E element) {
		copy.set(index, element);
		return original.set(index, element);
	}
	
	public int size() {
		return copy.size();
	}
	
	public java.util.List<E> subList(int fromIndex, int toIndex) {
		return copy.subList(fromIndex, toIndex);
	}
	
	public Object[] toArray() {
		return copy.toArray();
	}
	
	public <T> T[] toArray(T[] a) {
		return copy.toArray(a);
	}
	
}
