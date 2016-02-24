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
package org.sintef.thingml.impl;

import org.eclipse.emf.ecore.EClass;

import org.sintef.thingml.ParallelRegion;
import org.sintef.thingml.ThingmlPackage;
import org.sintef.thingml.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parallel Region</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ParallelRegionImpl extends RegionImpl implements ParallelRegion {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParallelRegionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.PARALLEL_REGION;
	}

	@Override
	public List<Session> allContainedSessions() {
		List<Session> result = new ArrayList<Session>();
		if (this instanceof CompositeState) {
			for(Region r : ((CompositeState)this).getRegion()) {
				if (r instanceof Session) {
					result.add((Session)r);
				}
				result.addAll(r.allContainedSessions());
			}
		}
		for (State s : getSubstate()) {
			if (s instanceof Session) {
				result.addAll(((Session)s).allContainedSessions());
			}
		}
		return result;
	}

	@Override
	public List<Session> directSubSessions() {
		List<Session> result = new ArrayList<Session>();
		if (this instanceof CompositeState) {
			for (Region r : ((CompositeState)this).getRegion()) {
				if (r instanceof Session)
					result.add((Session) r);
				result.addAll(r.allContainedSessions());
			}
		}
		return result;
	}

} //ParallelRegionImpl
