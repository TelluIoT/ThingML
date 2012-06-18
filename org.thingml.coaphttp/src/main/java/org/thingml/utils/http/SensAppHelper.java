/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.utils.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author bmori
 * Inspired by http://www.gitpaste.com/paste/727/E1kSdBqXD7efNO4x5LlZ2T
 */
public class SensAppHelper {
    
    
    /*private static String server = "localhost";
    private static int port = 8080;*/

    public static String registerSensor(URL target, String id, String descr, String backend, String tpl) throws Exception {
	StringBuilder req = new StringBuilder();
	req.append("{\"id\": \""+id+"\", \"descr\": \""+descr+"\",");
	req.append("\"schema\": { \"backend\": \""+backend+"\", \"template\": \""+ tpl +"\"}}");
	//URL target = new URL("http", server, port, "/registry/sensors");
	HttpURLConnection c = (HttpURLConnection) target.openConnection();
	c.setDoOutput(true);
	c.setRequestMethod("POST");
	c.addRequestProperty("Content-type", "application/json");
	OutputStreamWriter wr = new OutputStreamWriter(c.getOutputStream());
	wr.write(req.toString());
	wr.flush();

	BufferedReader rd = new BufferedReader(new InputStreamReader(c.getInputStream()));
	String result = rd.readLine();
	rd.close();
	wr.close();
	return result;
    }

    public static String getSensorDetails(URL target, String url)  throws Exception {
	//URL target = new URL("http", server, port, url);
	HttpURLConnection c = (HttpURLConnection) target.openConnection();
	BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
	String inputLine;
	StringWriter result = new StringWriter();
	while ((inputLine = in.readLine()) != null)
            result.append(inputLine +"\n");
        in.close();
	return result.toString();
    }

    public static String pushData(URL target, String data) throws Exception {
	//URL target = new URL("http", server, port, "/dispatch");
	HttpURLConnection c = (HttpURLConnection) target.openConnection();
	c.setDoOutput(true);
	c.setRequestMethod("PUT");
	c.addRequestProperty("Content-type", "application/json");
	OutputStreamWriter wr = new OutputStreamWriter(c.getOutputStream());
	wr.write(data);
	wr.flush();
	BufferedReader rd = new BufferedReader(new InputStreamReader(c.getInputStream()));
	String result = rd.readLine();
	rd.close();
	wr.close();
	return result;
    }

    public static String getData(URL target, String contentType) throws Exception {
	//URL target = new URL("http", server, port, url);
	HttpURLConnection c = (HttpURLConnection) target.openConnection();
	c.addRequestProperty("Accept", contentType);
	BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
	String inputLine;
	StringWriter result = new StringWriter();
	while ((inputLine = in.readLine()) != null)
	    result.append(inputLine+"\n");
        in.close();
	return result.toString();
    }    
    
}
