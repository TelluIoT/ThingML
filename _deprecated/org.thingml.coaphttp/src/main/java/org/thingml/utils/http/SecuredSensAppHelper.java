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
package org.thingml.utils.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 *
 * @author bmori Inspired by
 * http://www.gitpaste.com/paste/727/E1kSdBqXD7efNO4x5LlZ2T
 */
public class SecuredSensAppHelper {

    public static void main(String[] args) throws Exception {
        authenticate("C:\\Program Files\\Java\\jdk1.6.0_29\\jre\\lib\\security\\cacerts", "sensappuser", "sensapp");
        URL regURL = new URL("https://ec2-54-247-156-147.eu-west-1.compute.amazonaws.com/sensapp/registry/sensors");
        URL pushURL = new URL("https://ec2-54-247-156-147.eu-west-1.compute.amazonaws.com/sensapp/registry/sensors/testpl");
        String status = SecuredSensAppHelper.registerSensor(regURL, "testpl2", "just a test", "raw", "Numerical");
        String data = SecuredSensAppHelper.pushData(pushURL, "{\"tags\":{\"owner\":\"Franck Fleurey\",\"license\":\"LGPL\"}}");
        System.out.println(data);
    }

    /*
     * private static String server = "localhost"; private static int port =
     * 8080;
     */
    public static void authenticate(final String cacerts, final String login, final String pwd) {
        System.setProperty("javax.net.ssl.trustStore", cacerts);
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, pwd.toCharArray());
            }
        });
    }

    public static class MyHostnameVerifier implements HostnameVerifier {

        public boolean verify(String hostname, SSLSession session) {
            // verification of hostname is switched off
            return true;
        }
    }

    public static String registerSensor(URL target, String id, String descr,
            String backend, String tpl) throws Exception {
        StringBuilder req = new StringBuilder();
        req.append("{\"id\": \"" + id + "\", \"descr\": \"" + descr + "\",");
        req.append("\"schema\": { \"backend\": \"" + backend
                + "\", \"template\": \"" + tpl + "\"}}");

        System.out.println(req.toString());

        // URL target = new URL("http", server, port, "/registry/sensors");
        HttpsURLConnection c = (HttpsURLConnection) target.openConnection();
        c.setDoOutput(true);
        c.setRequestMethod("POST");
        c.addRequestProperty("Content-type", "application/json");
        //c.setRequestProperty("Authorization", "Basic c2Vuc2FwcHVzZXI6c2Vuc2FwcA==");
        c.setHostnameVerifier(new MyHostnameVerifier());
        OutputStreamWriter wr = new OutputStreamWriter(c.getOutputStream());
        wr.write(req.toString());
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(
                c.getInputStream()));
        String result = rd.readLine();
        rd.close();
        wr.close();
        return result;
    }

    public static String getSensorDetails(URL target, String url)
            throws Exception {
        // URL target = new URL("http", server, port, url);
        HttpsURLConnection c = (HttpsURLConnection) target.openConnection();
        //c.setRequestProperty("Authorization", "Basic c2Vuc2FwcHVzZXI6c2Vuc2FwcA==");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                c.getInputStream()));
        String inputLine;
        StringWriter result = new StringWriter();
        while ((inputLine = in.readLine()) != null) {
            result.append(inputLine + "\n");
        }
        in.close();
        return result.toString();
    }

    public static String pushData(URL target, String data) throws Exception {
        // URL target = new URL("http", server, port, "/dispatch");
        HttpsURLConnection c = (HttpsURLConnection) target.openConnection();
        c.setDoOutput(true);
        c.setRequestMethod("PUT");
        c.addRequestProperty("Content-type", "application/json");
        //c.setRequestProperty("Authorization", "Basic c2Vuc2FwcHVzZXI6c2Vuc2FwcA==");
        OutputStreamWriter wr = new OutputStreamWriter(c.getOutputStream());
        wr.write(data);
        wr.flush();
        BufferedReader rd = new BufferedReader(new InputStreamReader(
                c.getInputStream()));
        String result = rd.readLine();
        rd.close();
        wr.close();
        return result;
    }

    public static String getData(URL target, String contentType)
            throws Exception {
        // URL target = new URL("http", server, port, url);

        HttpsURLConnection c = (HttpsURLConnection) target.openConnection();
        c.addRequestProperty("Accept", contentType);
        //c.setRequestProperty("Authorization", "Basic c2Vuc2FwcHVzZXI6c2Vuc2FwcA==");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                c.getInputStream()));
        String inputLine;
        StringWriter result = new StringWriter();
        while ((inputLine = in.readLine()) != null) {
            result.append(inputLine + "\n");
        }
        in.close();
        return result.toString();
    }
}
