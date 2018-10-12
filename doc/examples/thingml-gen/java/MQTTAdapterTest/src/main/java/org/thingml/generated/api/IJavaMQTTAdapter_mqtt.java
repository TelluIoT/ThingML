/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.thingml.generated.api;

import org.thingml.generated.api.*;

public interface IJavaMQTTAdapter_mqtt{
void mqtt_connect_via_mqtt(String MQTTAdapterMsgs_mqtt_connect_client_id_var, String MQTTAdapterMsgs_mqtt_connect_host_var, int MQTTAdapterMsgs_mqtt_connect_portno_var, boolean MQTTAdapterMsgs_mqtt_connect_tls_var);
void mqtt_disconnect_via_mqtt();
void mqtt_publish_via_mqtt(String MQTTAdapterMsgs_mqtt_publish_topic_var,  byte[] MQTTAdapterMsgs_mqtt_publish_payload_var, long MQTTAdapterMsgs_mqtt_publish_size_var);
void mqtt_subscribe_via_mqtt(String MQTTAdapterMsgs_mqtt_subscribe_topic_var);
void mqtt_set_credentials_via_mqtt(String MQTTAdapterMsgs_mqtt_set_credentials_usr_var, String MQTTAdapterMsgs_mqtt_set_credentials_pwd_var);
void mqtt_set_prefix_via_mqtt(String MQTTAdapterMsgs_mqtt_set_prefix_prefix_var);
}