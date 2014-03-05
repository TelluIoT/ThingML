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
package org.thingml.model.scalaimpl

import org.sintef.thingml._
import org.thingml.model.scalaimpl.aspects._


/**
 * Created by IntelliJ IDEA.
 * User: ffl
 * Date: 04.07.11
 * Time: 10:30
 * To change this template use File | Settings | File Templates.
 */

object ThingMLScalaImpl {

  implicit def scalaImplAspect(self:ThingMLModel) = ThingMLModelScalaImpl(self)
  implicit def scalaImplAspect(self:Thing) = ThingScalaImpl(self)
  implicit def scalaImplAspect(self:State) = StateScalaImpl(self)
  implicit def scalaImplAspect(self:ThingMLElement) = ThingMLElementScalaImpl(self)
  implicit def scalaImplAspect(self:Region) = RegionScalaImpl(self)
  implicit def scalaImplAspect(self:Configuration) = ConfigurationScalaImpl(self)
  implicit def scalaImplAspect(self:Connector) = ConnectorScalaImpl(self)
  implicit def scalaImplAspect(self:Instance) = InstanceScalaImpl(self)
  implicit def scalaImplAspect(self:Handler) = HandlerScalaImpl(self)
  implicit def scalaImplAspect(self:Message) = MessageScalaImpl(self)
}