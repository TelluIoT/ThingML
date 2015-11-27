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

import java.util.Collection;
import java.util.*;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sintef.thingml.AbstractConnector;
import org.sintef.thingml.ConfigInclude;
import org.sintef.thingml.ConfigPropertyAssign;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.Instance;
import org.sintef.thingml.ThingmlPackage;
import org.sintef.thingml.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.ConfigurationImpl#getInstances <em>Instances</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ConfigurationImpl#getConnectors <em>Connectors</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ConfigurationImpl#isFragment <em>Fragment</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ConfigurationImpl#getConfigs <em>Configs</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ConfigurationImpl#getPropassigns <em>Propassigns</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConfigurationImpl extends AnnotatedElementImpl implements Configuration {
    /**
	 * The cached value of the '{@link #getInstances() <em>Instances</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getInstances()
	 * @generated
	 * @ordered
	 */
    protected EList<Instance> instances;

    /**
	 * The cached value of the '{@link #getConnectors() <em>Connectors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getConnectors()
	 * @generated
	 * @ordered
	 */
    protected EList<AbstractConnector> connectors;

    /**
	 * The default value of the '{@link #isFragment() <em>Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isFragment()
	 * @generated
	 * @ordered
	 */
    protected static final boolean FRAGMENT_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isFragment() <em>Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isFragment()
	 * @generated
	 * @ordered
	 */
    protected boolean fragment = FRAGMENT_EDEFAULT;

    /**
	 * The cached value of the '{@link #getConfigs() <em>Configs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getConfigs()
	 * @generated
	 * @ordered
	 */
    protected EList<ConfigInclude> configs;

    /**
	 * The cached value of the '{@link #getPropassigns() <em>Propassigns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPropassigns()
	 * @generated
	 * @ordered
	 */
    protected EList<ConfigPropertyAssign> propassigns;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected ConfigurationImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return ThingmlPackage.Literals.CONFIGURATION;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<Instance> getInstances() {
		if (instances == null) {
			instances = new EObjectContainmentEList<Instance>(Instance.class, this, ThingmlPackage.CONFIGURATION__INSTANCES);
		}
		return instances;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<AbstractConnector> getConnectors() {
		if (connectors == null) {
			connectors = new EObjectContainmentEList<AbstractConnector>(AbstractConnector.class, this, ThingmlPackage.CONFIGURATION__CONNECTORS);
		}
		return connectors;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isFragment() {
		return fragment;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setFragment(boolean newFragment) {
		boolean oldFragment = fragment;
		fragment = newFragment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.CONFIGURATION__FRAGMENT, oldFragment, fragment));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<ConfigInclude> getConfigs() {
		if (configs == null) {
			configs = new EObjectContainmentEList<ConfigInclude>(ConfigInclude.class, this, ThingmlPackage.CONFIGURATION__CONFIGS);
		}
		return configs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<ConfigPropertyAssign> getPropassigns() {
		if (propassigns == null) {
			propassigns = new EObjectContainmentEList<ConfigPropertyAssign>(ConfigPropertyAssign.class, this, ThingmlPackage.CONFIGURATION__PROPASSIGNS);
		}
		return propassigns;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ThingmlPackage.CONFIGURATION__INSTANCES:
				return ((InternalEList<?>)getInstances()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.CONFIGURATION__CONNECTORS:
				return ((InternalEList<?>)getConnectors()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.CONFIGURATION__CONFIGS:
				return ((InternalEList<?>)getConfigs()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.CONFIGURATION__PROPASSIGNS:
				return ((InternalEList<?>)getPropassigns()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ThingmlPackage.CONFIGURATION__INSTANCES:
				return getInstances();
			case ThingmlPackage.CONFIGURATION__CONNECTORS:
				return getConnectors();
			case ThingmlPackage.CONFIGURATION__FRAGMENT:
				return isFragment();
			case ThingmlPackage.CONFIGURATION__CONFIGS:
				return getConfigs();
			case ThingmlPackage.CONFIGURATION__PROPASSIGNS:
				return getPropassigns();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ThingmlPackage.CONFIGURATION__INSTANCES:
				getInstances().clear();
				getInstances().addAll((Collection<? extends Instance>)newValue);
				return;
			case ThingmlPackage.CONFIGURATION__CONNECTORS:
				getConnectors().clear();
				getConnectors().addAll((Collection<? extends AbstractConnector>)newValue);
				return;
			case ThingmlPackage.CONFIGURATION__FRAGMENT:
				setFragment((Boolean)newValue);
				return;
			case ThingmlPackage.CONFIGURATION__CONFIGS:
				getConfigs().clear();
				getConfigs().addAll((Collection<? extends ConfigInclude>)newValue);
				return;
			case ThingmlPackage.CONFIGURATION__PROPASSIGNS:
				getPropassigns().clear();
				getPropassigns().addAll((Collection<? extends ConfigPropertyAssign>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID) {
			case ThingmlPackage.CONFIGURATION__INSTANCES:
				getInstances().clear();
				return;
			case ThingmlPackage.CONFIGURATION__CONNECTORS:
				getConnectors().clear();
				return;
			case ThingmlPackage.CONFIGURATION__FRAGMENT:
				setFragment(FRAGMENT_EDEFAULT);
				return;
			case ThingmlPackage.CONFIGURATION__CONFIGS:
				getConfigs().clear();
				return;
			case ThingmlPackage.CONFIGURATION__PROPASSIGNS:
				getPropassigns().clear();
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ThingmlPackage.CONFIGURATION__INSTANCES:
				return instances != null && !instances.isEmpty();
			case ThingmlPackage.CONFIGURATION__CONNECTORS:
				return connectors != null && !connectors.isEmpty();
			case ThingmlPackage.CONFIGURATION__FRAGMENT:
				return fragment != FRAGMENT_EDEFAULT;
			case ThingmlPackage.CONFIGURATION__CONFIGS:
				return configs != null && !configs.isEmpty();
			case ThingmlPackage.CONFIGURATION__PROPASSIGNS:
				return propassigns != null && !propassigns.isEmpty();
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (fragment: ");
		result.append(fragment);
		result.append(')');
		return result.toString();
	}


    /**
     * @generated NOT
     */
    public static class MergedConfigurationCache {

        static Map<Configuration, Configuration> cache = new HashMap<Configuration, Configuration>();

        static Configuration getMergedConfiguration(Configuration c) {
            return cache.get(c);
        }

        static void cacheMergedConfiguration(Configuration c, Configuration mc)  {
            cache.put(c, mc);
        }

        public static void clearCache() {
            cache.clear();
        }

    }

    /**
     *
     * @return
     * @generated NOT
     */
    private Configuration merge() {

        if (MergedConfigurationCache.getMergedConfiguration(this) != null)
            return MergedConfigurationCache.getMergedConfiguration(this);

        final Configuration copy = EcoreUtil.copy(this);
        final Map<String, Instance> instances = new HashMap<String, Instance>();
        final List<AbstractConnector> connectors = new ArrayList<AbstractConnector>();
        final Map<String, ConfigPropertyAssign> assigns = new HashMap<String, ConfigPropertyAssign>();
        final String prefix = getName();

        _merge(instances, connectors, assigns, prefix);

        copy.getConfigs().clear();
        copy.getInstances().clear();
        copy.getConnectors().clear();
        copy.getPropassigns().clear();

        copy.getInstances().addAll(instances.values());
        copy.getConnectors().addAll(connectors);
        copy.getPropassigns().addAll(assigns.values());

        MergedConfigurationCache.cacheMergedConfiguration(this, copy);

        return copy;
    }

    /**
     *
     * @param instances
     * @param connectors
     * @param assigns
     * @param prefix
     * @generated NOT
     */
    private void _merge(Map<String, Instance> instances, List<AbstractConnector> connectors, Map<String, ConfigPropertyAssign> assigns, String prefix) {
        // Recursively deal with all groups first
        for(ConfigInclude g : getConfigs()) {
            ((ConfigurationImpl)g.getConfig())._merge(instances, connectors, assigns, prefix + "_" + g.getName());
        }

        // Add the instances of this configuration (actually a copy)
        for(Instance inst : getInstances()) {

            final String key = prefix + "_" + inst.getName();
            Instance copy = null;

            if (inst.getType().isSingleton()) {
                // TODO: This could become slow if we have a large number of instances
                List<Instance> others = new ArrayList<Instance>();
                for(Instance i : instances.values()) {
                    if (EcoreUtil.equals(i.getType(), inst.getType())) {
                        others.add(i);
                    }
                }
                if (others.isEmpty()) {
                    copy = EcoreUtil.copy(inst);
                    copy.setName(inst.getName()); // no prefix needed
                }
                else copy = others.get(0); // There will be only one in the list
            }
            else {
                copy = EcoreUtil.copy(inst);
                copy.setName(key); // rename the instance with the prefix
            }

            instances.put(key, copy);
        }

        // Add the connectors
        for(Connector c : getInternalConnectors()) {
            Connector copy = EcoreUtil.copy(c);
            // look for the instances:
            Instance cli = instances.get(getInstanceMergedName(prefix, c.getCli()));
            Instance srv = instances.get(getInstanceMergedName(prefix, c.getSrv()));

            copy.getCli().getConfig().clear();
            copy.getCli().setInstance(cli);

            copy.getSrv().getConfig().clear();
            copy.getSrv().setInstance(srv);

            connectors.add(copy);
        }
        
        for(ExternalConnector c : getExternalConnectors()) {
            ExternalConnector copy = EcoreUtil.copy(c);
            // look for the instances:
            Instance cli = instances.get(getInstanceMergedName(prefix, c.getInst()));

            copy.getInst().getConfig().clear();
            copy.getInst().setInstance(cli);

            connectors.add(copy);
        }

        for(ConfigPropertyAssign a : getPropassigns()) {
            ConfigPropertyAssign copy = EcoreUtil.copy(a);

            String inst_name = getInstanceMergedName(prefix, a.getInstance());

            Instance inst = instances.get(inst_name);
            copy.getInstance().getConfig().clear();
            copy.getInstance().setInstance(inst);

            String id = inst_name + "_" + a.getProperty().getName();

            if (a.getIndex().size() > 0)  { // It is an array
                id += a.getIndex().get(0);
                //println(id)
            }

            assigns.put(id, copy); // This will replace any previous initialization of the variable
        }

    }

    /**
     *
     * @param prefix
     * @param ref
     * @return
     * @generated NOT
     */
    private String getInstanceMergedName(String prefix, InstanceRef ref) {
        String result = prefix;
        for (ConfigInclude c : ref.getConfig()) {
            result += "_" + c.getName();
        }
        result += "_" + ref.getInstance().getName();
        return result;
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public Map<Instance, String[]> allRemoteInstances() {
        Map<Instance, String[]> result = new HashMap<Instance, String[]>();
        for (PlatformAnnotation a : getAnnotations()) {
            if (a.getName().equals("remote")) {
                final String[] regex = a.getValue().split("::");
                for(Instance i : allInstances()) {
                    if (i.getName().matches(getName()+"_"+regex[0]) && i.getType().getName().matches(regex[1]) ) {
                        result.put(i, regex);
                    }
                }
            }
        }
        return result;
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public Set<Instance> allInstances() {
        MergedConfigurationCache.clearCache();
        Set<Instance> result = new HashSet<Instance>();
        result.addAll(merge().getInstances());
        return result;
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public Set<Connector> allConnectors() {
        Set<Connector> result = new HashSet<Connector>();
        MergedConfigurationCache.clearCache();
        result.addAll(merge().getInternalConnectors());
        return result;
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public Set<ConfigPropertyAssign> allPropAssigns() {
        Set<ConfigPropertyAssign> result = new HashSet<ConfigPropertyAssign>();
        MergedConfigurationCache.clearCache();
        result.addAll(merge().getPropassigns());
        return result;
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public Map<Port, AbstractMap.SimpleImmutableEntry<List<Message>, List<Message>>> allRemoteMessages() {
        Map<Port, AbstractMap.SimpleImmutableEntry<List<Message>, List<Message>>> result = new HashMap<Port, AbstractMap.SimpleImmutableEntry<List<Message>, List<Message>>>();
        for(Map.Entry<Instance, String[]> entry : allRemoteInstances().entrySet()) {
            for(Port p : entry.getKey().getType().getPorts()) {
                if (p.getName().matches(entry.getValue()[2])) {
                    List<Message> send = new ArrayList<Message>();
                    for(Message m : p.getSends()) {
                        if (m.getName().matches(entry.getValue()[3])) {
                            send.add(m);
                        }
                    }

                    List<Message> rec = new ArrayList<Message>();
                    for(Message m : p.getReceives()) {
                        if (m.getName().matches(entry.getValue()[3])) {
                            rec.add(m);
                        }
                    }

                    result.put(p, new AbstractMap.SimpleImmutableEntry<List<Message>, List<Message>>(send, rec));
                }
            }
        }
        return result;
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<Thing> allThings() {
        List<Thing> result = new ArrayList<Thing>();
        for(Instance i : allInstances()) {
            if (!result.contains(i.getType()))  //TODO: maybe we should return a set instead?
                result.add(i.getType());
        }
        return result;
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public Set<Message> allMessages() {
        Set<Message> result = new HashSet<Message>();
        for(Thing t : allThings()) {
            result.addAll(t.allMessages());
        }
        return result;
    }

    /**
     *
     * @param i
     * @return
     * @generated NOT
     */
    public List<Property> allArrays(Instance i) {
        List<Property> result = new ArrayList<Property>();
        for(Property p : i.getType().allPropertiesInDepth()) {
            if (p.getCardinality() != null)
                result.add(p);
        }
        return result;
    }

    /**
     *
     * @param i
     * @return
     * @generated NOT
     */
    public List<Expression> initExpressions(Instance i, Property p) {
        List<Expression> result = new ArrayList<Expression>();
        for(AbstractMap.SimpleImmutableEntry<Property, Expression> entry : initExpressionsForInstance(i)) {
            if (EcoreUtil.equals(entry.getKey(), p)) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

        /**
         *
         * @param i
         * @return
         * @generated NOT
         */
    public List<AbstractMap.SimpleImmutableEntry<Property, Expression>> initExpressionsForInstance(Instance i) {
        List<AbstractMap.SimpleImmutableEntry<Property, Expression>> result = new ArrayList<AbstractMap.SimpleImmutableEntry<Property, Expression>>();

        //println("init instance " + i.getName + " " + i.toString)

        for(Property p : i.getType().allPropertiesInDepth()) {

            Set<ConfigPropertyAssign> confassigns = new HashSet<ConfigPropertyAssign>();
            for(ConfigPropertyAssign a : allPropAssigns()) {
                if (EcoreUtil.equals(a.getInstance().getInstance(), i) && EcoreUtil.equals(a.getProperty(), p)) {
                    confassigns.add(a);
                }
            }

            if (confassigns.size() > 0) {  // There is an assignment for this property
                result.add(new AbstractMap.SimpleImmutableEntry<Property, Expression>(p, ((ConfigPropertyAssign)confassigns.toArray()[0]).getInit()));
            }
            else { // Look on the instance and in the type to find an init expression
                // get the init from the instance if there is an assignment

                Set<PropertyAssign> assigns = new HashSet<PropertyAssign>();
                for(PropertyAssign a : i.getAssign()) {
                    if (EcoreUtil.equals(a.getProperty(), p)) {
                        assigns.add(a);
                    }
                }
                if (assigns.size() > 1)
                    System.out.println("Error: Instance " + i.getName() + " contains several assignments for property " + p.getName());

                if (assigns.size() > 0) {
                    result.add(new AbstractMap.SimpleImmutableEntry<Property, Expression>(p, ((PropertyAssign)assigns.toArray()[0]).getInit()));
                }
                else {
                    result.add(new AbstractMap.SimpleImmutableEntry<Property, Expression>(p, i.getType().initExpression(p)));
                }
            }
        }
        return result;
    }

    /**
     * Inits array for the given instance, array[index] = value, where array is the Property, index the first Expression and Value the second Expression of the pair
     *
     * @param i
     * @return
     * @generated NOT
     */
    public Map<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> initExpressionsForInstanceArrays(Instance i) {

        Map<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> result = new HashMap<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>>();

        for(Property p : allArrays(i)) {
            // look for assignements in the things:

            for(PropertyAssign a : i.getType().initExpressionsForArray(p)) {
                initExpressionsForInstanceArraysHelper(result, "in thing " + ((Thing)a.eContainer()).getName(), p, a.getIndex().size(), (Expression)a.getIndex().toArray()[0], a.getInit());
            }
            for(ConfigPropertyAssign a : allPropAssigns()) {
                if(EcoreUtil.equals(a.getProperty(), p)) {
                    initExpressionsForInstanceArraysHelper(result, "in instance " + i.getName(), p, a.getIndex().size(), (Expression) a.getIndex().toArray()[0], a.getInit());
                }
            }
        }
        return result;
    }

    /**
     * @param result
     * @param errMsg
     * @param p
     * @param size
     * @param index
     * @param init
     * @generated NOT
     */
    private void  initExpressionsForInstanceArraysHelper(Map<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> result, String errMsg, Property p, int size, Expression index, Expression init) {
        List<AbstractMap.SimpleImmutableEntry<Expression, Expression>> assigns = result.get(p);
        if (assigns == null) {
            assigns = new ArrayList<AbstractMap.SimpleImmutableEntry<Expression, Expression>>();
            result.put(p, assigns);
        }
        if (size == 1) {
            assigns.add(new AbstractMap.SimpleImmutableEntry<Expression, Expression>(index, init));
            //result.put(p, new AbstractMap.SimpleImmutableEntry<Expression, Expression>(index, init));
        }
        else if (size == 0)
            System.err.println("ERROR: Malformed array initialization for property " + p.getName() + " " + errMsg + ". No initialization is possible!");
        else {
            System.err.println("WARNING: Malformed array initialization for property " + p.getName() + " " + errMsg +". Multiple initializations are possible! We're going to take one among them...");
            assigns.add(new AbstractMap.SimpleImmutableEntry<Expression, Expression>(index, init));
            //result.put(p, new AbstractMap.SimpleImmutableEntry<Expression, Expression>(index, init));
        }
    }

    /**
     Returns the set of destination for messages sent through the port p
     For each outgoing message the results gives the list of destinations
     sorted by source instance as a list of target instances+port
     message* -> source instance* -> (target instance, port)*

     TODO: WTF?! We need to return a proper structure that one can exploit intuitively, not that mess of Map<Message, ...>!!!

     * @generated NOT
     */
    public Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch(Thing t, Port p) {
        Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> result = new HashMap<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>>();
        for(Instance i : allInstances()) {
            if (EcoreUtil.equals(i.getType(), t)) {
                for(Connector c : allConnectors()) {
                    if(EcoreUtil.equals(c.getCli().getInstance(), i) && EcoreUtil.equals(c.getRequired(), p)) {
                        for(Message m : p.getSends()) {
                            MSGLOOP: for(Message m2 : c.getProvided().getReceives()) { //TODO: we should implement a derived property on Thing to compute input and output messages, to avoid duplicating code (see below)
                                if (EcoreUtil.equals(m, m2)) {
                                    Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>> mtable = result.get(m);
                                    if (mtable == null) {
                                        mtable = new HashMap<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>();
                                        result.put(m, mtable);
                                    }
                                    List<AbstractMap.SimpleImmutableEntry<Instance, Port>> itable = mtable.get(i);
                                    if (itable == null) {
                                        itable = new ArrayList<AbstractMap.SimpleImmutableEntry<Instance, Port>>();
                                        mtable.put(i, itable);
                                    }
                                    itable.add(new AbstractMap.SimpleImmutableEntry<Instance, Port>(c.getSrv().getInstance(), c.getProvided()));
                                    //break MSGLOOP;
                                }
                            }
                        }
                    }
                }
                for(Connector c : allConnectors()) {
                    if(EcoreUtil.equals(c.getSrv().getInstance(), i) && EcoreUtil.equals(c.getProvided(), p)) {
                        for(Message m : p.getSends()) {
                            MSGLOOP: for(Message m2 : c.getRequired().getReceives()) { //TODO: remove duplicated code
                                if (EcoreUtil.equals(m, m2)) {
                                    Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>> mtable = result.get(m);
                                    if (mtable == null) {
                                        mtable = new HashMap<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>();
                                        result.put(m, mtable);
                                    }
                                    List<AbstractMap.SimpleImmutableEntry<Instance, Port>> itable = mtable.get(i);
                                    if (itable == null) {
                                        itable = new ArrayList<AbstractMap.SimpleImmutableEntry<Instance, Port>>();
                                        mtable.put(i, itable);
                                    }
                                    itable.add(new AbstractMap.SimpleImmutableEntry<Instance, Port>(c.getCli().getInstance(), c.getRequired()));
                                    //break MSGLOOP;
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * @generated NOT
     * @return
     */
    public List<Connector> getInternalConnectors() {
    	List<Connector> result = new ArrayList<Connector>();
    	for(AbstractConnector c : getConnectors()) {
    		if (c instanceof Connector) {
    			result.add((Connector)c);
    		}
    	}
    	
    	return result;
    }
    
    /**
     * @generated NOT
     * @return
     */
    public List<ExternalConnector> getExternalConnectors() {
    	List<ExternalConnector> result = new ArrayList<ExternalConnector>();
    	for(AbstractConnector c : getConnectors()) {
    		if (c instanceof ExternalConnector) {
    			result.add((ExternalConnector)c);
    		}
    	}
    	
    	return result;
    }

    /**
     * @generated NOT
     * @return
     */
    public List<Instance> getClients(Instance i) {
        List<Instance> result = new ArrayList<Instance>();
        for(Connector c : this.allConnectors()) {
            if (EcoreUtil.equals(c.getSrv().getInstance(), i)) {
                result.add(c.getCli().getInstance());
            }
        }
        return result;
    }

    /**
     * @generated NOT
     * @return
     */
    public List<Instance> getServers(Instance i) {
        List<Instance> result = new ArrayList<Instance>();
        for(Connector c : this.allConnectors()) {
            if (EcoreUtil.equals(c.getCli().getInstance(), i)) {
                result.add(c.getSrv().getInstance());
            }
        }
        return result;
    }

    /**
     * @generated NOT
     * @return
     */
    public Map<Instance, List<Port>> danglingPorts() {
        Map<Instance, List<Port>> result = new HashMap<Instance, List<Port>>();
        for(Instance i : allInstances()) {
            List<Port> ports = new ArrayList<Port>();
            for (Port p : i.getType().allPorts()) {
                boolean connected = false;
                for(Connector c : allConnectors()) {
                    if ((EcoreUtil.equals(c.getCli().getInstance(), i) || EcoreUtil.equals(c.getSrv().getInstance(), i)) && (EcoreUtil.equals(c.getProvided(), p) || EcoreUtil.equals(c.getRequired(), p))) {
                        connected = true;
                        break;
                    }
                }
                if (!connected) {
                    ports.add(p);
                }
            }
            result.put(i, ports);
        }
        return result;
    }


    /**
     * Returns all internal ports, for all instances
     * @generated NOT
     * @return
     */
    public Map<Instance, List<InternalPort>> allInternalPorts() {
        Map<Instance, List<InternalPort>> result = new HashMap<Instance, List<InternalPort>>();
        for(Instance i : allInstances()) {
            List<InternalPort> iports = new ArrayList<InternalPort>();
            for(Port p : i.getType().allPorts()) {
                if (p instanceof InternalPort) {
                    InternalPort iport = (InternalPort) p;
                    iports.add(iport);
                }
            }
            if(!iports.isEmpty()) {
                result.put(i, iports);
            }
        }
        return result;
    }

    /**
     * @generated NOT
     * @param cur
     * @param Cos
     * @param Instances
     * @return
     */
    private List<Instance> isRequiredBy(Instance cur, List<Connector> Cos, List<Instance> Instances) {
        System.out.println("I: " + cur.getName());
        List<Instance> res = new LinkedList<Instance>();
        //List<Connector> toBeRemoved = new LinkedList<Connector>();
        Instance needed;
        for (Connector co : Cos) {
            if(co.getCli().getInstance().getName().compareTo(cur.getName()) == 0) {
                needed = co.getSrv().getInstance();
                for(Instance inst : Instances) {
                    if(inst.getName().compareTo(needed.getName()) == 0) {
                        Instances.remove(inst);
                        //Cos.remove(co);
                        res.addAll(0, isRequiredBy(inst,Cos,Instances));
                        res.add(0, inst);
                        break;
                    }
                }
            }
        }
        return res;
    }

    /**
     * @generated NOT
     * @return
     */
    public List<Instance> orderInstanceInit() {
        List<Instance> Instances = new LinkedList<Instance>(this.allInstances());
        List<Connector> Cos = new LinkedList<Connector>(this.allConnectors());
        List<Instance> res = new LinkedList<Instance>();
        Instance cur;
        while(!Instances.isEmpty()) {
            cur = Instances.get(0);
            Instances.remove(cur);
            res.addAll(0, isRequiredBy(cur, Cos, Instances));
            res.add(0, cur);
        }
        return res;
    }

} //ConfigurationImpl
