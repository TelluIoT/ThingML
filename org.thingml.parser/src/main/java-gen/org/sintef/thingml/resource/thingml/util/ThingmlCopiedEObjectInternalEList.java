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
package org.sintef.thingml.resource.thingml.util;

public class ThingmlCopiedEObjectInternalEList extends org.sintef.thingml.resource.thingml.util.ThingmlCopiedEList<org.eclipse.emf.ecore.EObject> implements org.eclipse.emf.ecore.util.InternalEList<org.eclipse.emf.ecore.EObject> {
	
	private org.eclipse.emf.ecore.util.InternalEList<org.eclipse.emf.ecore.EObject> original;
	private org.eclipse.emf.ecore.util.InternalEList<org.eclipse.emf.ecore.EObject> copy;
	
	public ThingmlCopiedEObjectInternalEList(org.eclipse.emf.ecore.util.InternalEList<org.eclipse.emf.ecore.EObject> original) {
		super(original);
		this.original = original;
		this.copy = new org.eclipse.emf.ecore.util.BasicInternalEList<org.eclipse.emf.ecore.EObject>(org.eclipse.emf.ecore.EObject.class);
		this.copy.addAll(this.original);
	}
	
	public boolean basicContains(Object object) {
		return copy.basicContains(object);
	}
	
	public boolean basicContainsAll(java.util.Collection<?> collection) {
		return copy.basicContainsAll(collection);
	}
	
	public org.eclipse.emf.ecore.EObject basicGet(int index) {
		return copy.basicGet(index);
	}
	
	public int basicIndexOf(Object object) {
		return copy.basicIndexOf(object);
	}
	
	public java.util.Iterator<org.eclipse.emf.ecore.EObject> basicIterator() {
		return copy.basicIterator();
	}
	
	public int basicLastIndexOf(Object object) {
		return copy.basicLastIndexOf(object);
	}
	
	public java.util.List<org.eclipse.emf.ecore.EObject> basicList() {
		return copy.basicList();
	}
	
	public java.util.ListIterator<org.eclipse.emf.ecore.EObject> basicListIterator() {
		return copy.basicListIterator();
	}
	
	public java.util.ListIterator<org.eclipse.emf.ecore.EObject> basicListIterator(int index) {
		return copy.basicListIterator(index);
	}
	
	public Object[] basicToArray() {
		return copy.basicToArray();
	}
	
	public <T> T[] basicToArray(T[] array) {
		return copy.basicToArray(array);
	}
	
	public boolean equals(Object o) {
		return copy.equals(o);
	}
	
	public int hashCode() {
		return copy.hashCode();
	}
	
	public boolean addAllUnique(java.util.Collection<? extends org.eclipse.emf.ecore.EObject> collection) {
		copy.addAllUnique(collection);
		return original.addAllUnique(collection);
	}
	
	public boolean addAllUnique(int index, java.util.Collection<? extends org.eclipse.emf.ecore.EObject> collection) {
		copy.addAllUnique(index, collection);
		return original.addAllUnique(index, collection);
	}
	
	public void addUnique(org.eclipse.emf.ecore.EObject object) {
		copy.addUnique(object);
		original.addUnique(object);
	}
	
	public void addUnique(int index, org.eclipse.emf.ecore.EObject object) {
		copy.addUnique(index, object);
		original.addUnique(index, object);
	}
	
	public org.eclipse.emf.common.notify.NotificationChain basicAdd(org.eclipse.emf.ecore.EObject object, org.eclipse.emf.common.notify.NotificationChain notifications) {
		copy.basicAdd(object, notifications);
		return original.basicAdd(object, notifications);
	}
	
	public org.eclipse.emf.common.notify.NotificationChain basicRemove(Object object,
	org.eclipse.emf.common.notify.NotificationChain notifications) {
		copy.basicRemove(object, notifications);
		return original.basicRemove(object, notifications);
	}
	
	public org.eclipse.emf.ecore.EObject setUnique(int index, org.eclipse.emf.ecore.EObject object) {
		copy.setUnique(index, object);
		return original.setUnique(index, object);
	}
}
