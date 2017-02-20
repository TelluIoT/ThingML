/**
 * *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *  *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.thingml.xtext.thingML.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.thingml.xtext.thingML.AbstractConnector;
import org.thingml.xtext.thingML.AnnotatedElement;
import org.thingml.xtext.thingML.ConfigPropertyAssign;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.ThingMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.impl.ConfigurationImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ConfigurationImpl#getInstances <em>Instances</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ConfigurationImpl#getConnectors <em>Connectors</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ConfigurationImpl#getPropassigns <em>Propassigns</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConfigurationImpl extends NamedElementImpl implements Configuration
{
  /**
   * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAnnotations()
   * @generated
   * @ordered
   */
  protected EList<PlatformAnnotation> annotations;

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
  protected ConfigurationImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return ThingMLPackage.Literals.CONFIGURATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<PlatformAnnotation> getAnnotations()
  {
    if (annotations == null)
    {
      annotations = new EObjectContainmentEList<PlatformAnnotation>(PlatformAnnotation.class, this, ThingMLPackage.CONFIGURATION__ANNOTATIONS);
    }
    return annotations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Instance> getInstances()
  {
    if (instances == null)
    {
      instances = new EObjectContainmentEList<Instance>(Instance.class, this, ThingMLPackage.CONFIGURATION__INSTANCES);
    }
    return instances;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<AbstractConnector> getConnectors()
  {
    if (connectors == null)
    {
      connectors = new EObjectContainmentEList<AbstractConnector>(AbstractConnector.class, this, ThingMLPackage.CONFIGURATION__CONNECTORS);
    }
    return connectors;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ConfigPropertyAssign> getPropassigns()
  {
    if (propassigns == null)
    {
      propassigns = new EObjectContainmentEList<ConfigPropertyAssign>(ConfigPropertyAssign.class, this, ThingMLPackage.CONFIGURATION__PROPASSIGNS);
    }
    return propassigns;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case ThingMLPackage.CONFIGURATION__ANNOTATIONS:
        return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
      case ThingMLPackage.CONFIGURATION__INSTANCES:
        return ((InternalEList<?>)getInstances()).basicRemove(otherEnd, msgs);
      case ThingMLPackage.CONFIGURATION__CONNECTORS:
        return ((InternalEList<?>)getConnectors()).basicRemove(otherEnd, msgs);
      case ThingMLPackage.CONFIGURATION__PROPASSIGNS:
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
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case ThingMLPackage.CONFIGURATION__ANNOTATIONS:
        return getAnnotations();
      case ThingMLPackage.CONFIGURATION__INSTANCES:
        return getInstances();
      case ThingMLPackage.CONFIGURATION__CONNECTORS:
        return getConnectors();
      case ThingMLPackage.CONFIGURATION__PROPASSIGNS:
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
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case ThingMLPackage.CONFIGURATION__ANNOTATIONS:
        getAnnotations().clear();
        getAnnotations().addAll((Collection<? extends PlatformAnnotation>)newValue);
        return;
      case ThingMLPackage.CONFIGURATION__INSTANCES:
        getInstances().clear();
        getInstances().addAll((Collection<? extends Instance>)newValue);
        return;
      case ThingMLPackage.CONFIGURATION__CONNECTORS:
        getConnectors().clear();
        getConnectors().addAll((Collection<? extends AbstractConnector>)newValue);
        return;
      case ThingMLPackage.CONFIGURATION__PROPASSIGNS:
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
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case ThingMLPackage.CONFIGURATION__ANNOTATIONS:
        getAnnotations().clear();
        return;
      case ThingMLPackage.CONFIGURATION__INSTANCES:
        getInstances().clear();
        return;
      case ThingMLPackage.CONFIGURATION__CONNECTORS:
        getConnectors().clear();
        return;
      case ThingMLPackage.CONFIGURATION__PROPASSIGNS:
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
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case ThingMLPackage.CONFIGURATION__ANNOTATIONS:
        return annotations != null && !annotations.isEmpty();
      case ThingMLPackage.CONFIGURATION__INSTANCES:
        return instances != null && !instances.isEmpty();
      case ThingMLPackage.CONFIGURATION__CONNECTORS:
        return connectors != null && !connectors.isEmpty();
      case ThingMLPackage.CONFIGURATION__PROPASSIGNS:
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
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
  {
    if (baseClass == AnnotatedElement.class)
    {
      switch (derivedFeatureID)
      {
        case ThingMLPackage.CONFIGURATION__ANNOTATIONS: return ThingMLPackage.ANNOTATED_ELEMENT__ANNOTATIONS;
        default: return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
  {
    if (baseClass == AnnotatedElement.class)
    {
      switch (baseFeatureID)
      {
        case ThingMLPackage.ANNOTATED_ELEMENT__ANNOTATIONS: return ThingMLPackage.CONFIGURATION__ANNOTATIONS;
        default: return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

} //ConfigurationImpl
