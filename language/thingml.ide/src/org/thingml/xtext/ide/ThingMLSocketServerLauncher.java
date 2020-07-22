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
package org.thingml.xtext.ide;

import java.io.PrintWriter;

import org.eclipse.xtext.ide.server.SocketServerLauncher;

/*
 * Just a workaround because of:
 * java.lang.NoSuchMethodError: org.eclipse.xtext.xbase.lib.ArrayExtensions.contains([Ljava/lang/Object;Ljava/lang/Object;)Z
        at org.eclipse.xtext.ide.server.SocketServerLauncher.getTrace(SocketServerLauncher.java:85)
        at org.eclipse.xtext.ide.server.SocketServerLauncher.launch(SocketServerLauncher.java:64)
        at org.eclipse.xtext.ide.server.SocketServerLauncher.main(SocketServerLauncher.java:52)
 */

public class ThingMLSocketServerLauncher extends SocketServerLauncher {

	public static void main(String[] args) {
		new ThingMLSocketServerLauncher().launch(args);
	}
	
	@Override
	protected PrintWriter getTrace(String... args) {
		return new PrintWriter(System.out);
	}

	@Override
	protected boolean shouldValidate(String... args) {
		return false;
	}

}
