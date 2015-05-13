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
package org.sintef.thingml.resource.thingml.mopp;

/**
 * A basic implementation of the ILocationMap interface. Instances store
 * information about element locations using four maps.
 * <p>
 * The set-methods can be called multiple times by the parser that may visit
 * multiple children from which it copies the localization information for the
 * parent element (i.e., the element for which set-method is called). It
 * implements the following behavior:
 * <p>
 * Line:   The lowest of all sources is used for target<br>
 * Column: The lowest of all sources is used for target<br>
 * Start:  The lowest of all sources is used for target<br>
 * End:    The highest of all sources is used for target<br>
 */
public class ThingmlLocationMap implements org.sintef.thingml.resource.thingml.IThingmlLocationMap {
	
	/**
	 * A basic interface that can be implemented to select EObjects based of their
	 * location in a text resource.
	 */
	public interface ISelector {
		boolean accept(int startOffset, int endOffset);
	}
	
	protected java.util.Map<org.eclipse.emf.ecore.EObject, Integer> columnMap = new java.util.IdentityHashMap<org.eclipse.emf.ecore.EObject, Integer>();
	protected java.util.Map<org.eclipse.emf.ecore.EObject, Integer> lineMap = new java.util.IdentityHashMap<org.eclipse.emf.ecore.EObject, Integer>();
	protected java.util.Map<org.eclipse.emf.ecore.EObject, Integer> charStartMap = new java.util.IdentityHashMap<org.eclipse.emf.ecore.EObject, Integer>();
	protected java.util.Map<org.eclipse.emf.ecore.EObject, Integer> charEndMap = new java.util.IdentityHashMap<org.eclipse.emf.ecore.EObject, Integer>();
	
	public void setLine(org.eclipse.emf.ecore.EObject element, int line) {
		setMapValueToMin(lineMap, element, line);
	}
	
	public int getLine(org.eclipse.emf.ecore.EObject element) {
		return getMapValue(lineMap, element);
	}
	
	public void setColumn(org.eclipse.emf.ecore.EObject element, int column) {
		setMapValueToMin(columnMap, element, column);
	}
	
	public int getColumn(org.eclipse.emf.ecore.EObject element) {
		return getMapValue(columnMap, element);
	}
	
	public void setCharStart(org.eclipse.emf.ecore.EObject element, int charStart) {
		setMapValueToMin(charStartMap, element, charStart);
	}
	
	public int getCharStart(org.eclipse.emf.ecore.EObject element) {
		return getMapValue(charStartMap, element);
	}
	
	public void setCharEnd(org.eclipse.emf.ecore.EObject element, int charEnd) {
		setMapValueToMax(charEndMap, element, charEnd);
	}
	
	public int getCharEnd(org.eclipse.emf.ecore.EObject element) {
		return getMapValue(charEndMap, element);
	}
	
	private int getMapValue(java.util.Map<org.eclipse.emf.ecore.EObject, Integer> map, org.eclipse.emf.ecore.EObject element) {
		if (!map.containsKey(element)) return -1;
		Integer value = map.get(element);
		return value == null ? -1 : value.intValue();
	}
	
	private void setMapValueToMin(java.util.Map<org.eclipse.emf.ecore.EObject, Integer> map, org.eclipse.emf.ecore.EObject element, int value) {
		// We need to synchronize the write access, because other threads may iterate over
		// the map concurrently.
		synchronized (this) {
			if (element == null || value < 0) return;
			if (map.containsKey(element) && map.get(element) < value) return;
			map.put(element, value);
		}
	}
	
	private void setMapValueToMax(java.util.Map<org.eclipse.emf.ecore.EObject, Integer> map, org.eclipse.emf.ecore.EObject element, int value) {
		// We need to synchronize the write access, because other threads may iterate over
		// the map concurrently.
		synchronized (this) {
			if (element == null || value < 0) return;
			if (map.containsKey(element) && map.get(element) > value) return;
			map.put(element, value);
		}
	}
	
	public java.util.List<org.eclipse.emf.ecore.EObject> getElementsAt(final int documentOffset) {
		java.util.List<org.eclipse.emf.ecore.EObject> result = getElements(new ISelector() {
			public boolean accept(int start, int end) {
				return start <= documentOffset && end >= documentOffset;
			}
		});
		// sort elements according to containment hierarchy
		java.util.Collections.sort(result, new java.util.Comparator<org.eclipse.emf.ecore.EObject>() {
			public int compare(org.eclipse.emf.ecore.EObject objectA, org.eclipse.emf.ecore.EObject objectB) {
				if (org.eclipse.emf.ecore.util.EcoreUtil.isAncestor(objectA, objectB)) {
					return 1;
				} else {
					if (org.eclipse.emf.ecore.util.EcoreUtil.isAncestor(objectB, objectA)) {
						return -1;
					} else {
						return 0;
					}
				}
			}
		});
		return result;
	}
	
	public java.util.List<org.eclipse.emf.ecore.EObject> getElementsBetween(final int startOffset, final int endOffset) {
		java.util.List<org.eclipse.emf.ecore.EObject> result = getElements(new ISelector() {
			public boolean accept(int start, int end) {
				return start >= startOffset && end <= endOffset;
			}
		});
		return result;
	}
	
	private java.util.List<org.eclipse.emf.ecore.EObject> getElements(ISelector s) {
		// There might be more than one element at the given offset. Thus, we collect all
		// of them and sort them afterwards.
		java.util.List<org.eclipse.emf.ecore.EObject> result = new java.util.ArrayList<org.eclipse.emf.ecore.EObject>();
		
		// We need to synchronize the write access, because other threads may iterate over
		// the map concurrently.
		synchronized (this) {
			for (org.eclipse.emf.ecore.EObject next : charStartMap.keySet()) {
				Integer start = charStartMap.get(next);
				Integer end = charEndMap.get(next);
				if (start == null || end == null) {
					continue;
				}
				if (s.accept(start, end)) {
					result.add(next);
				}
			}
		}
		java.util.Collections.sort(result, new java.util.Comparator<org.eclipse.emf.ecore.EObject>() {
			public int compare(org.eclipse.emf.ecore.EObject objectA, org.eclipse.emf.ecore.EObject objectB) {
				int lengthA = getCharEnd(objectA) - getCharStart(objectA);
				int lengthB = getCharEnd(objectB) - getCharStart(objectB);
				return lengthA - lengthB;
			}
		});
		return result;
	}
}
