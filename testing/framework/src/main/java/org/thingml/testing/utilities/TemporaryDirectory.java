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
package org.thingml.testing.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class TemporaryDirectory {
	public static File create() throws AssertionError {
		try {
			// Check if an output-directory is defined in system properties
			String outDir = System.getProperty("outDir", "");
			
			File dir;
			if (outDir.isEmpty()) dir = Files.createTempDirectory("thingml-testing-").toFile();
			else {
				File parent = new File(outDir);
				parent.mkdirs();
				dir = Files.createTempDirectory(parent.toPath(), "thingml-testing-").toFile();
			}

			return dir;
		} catch (IOException e) {
			throw new AssertionError("Couldn't create temporary directory", e);
		}
	}
	
	public static void delete(File dir) {
		if (dir == null) return;
		if (!System.getProperty("outDir", "").isEmpty()) return;
		try {
			Files.walkFileTree(dir.toPath(), new FileVisitor<Path>() {
				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) { return FileVisitResult.CONTINUE; }

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) { return FileVisitResult.TERMINATE; }
			});
		} catch (IOException e) {} // We don't really care
	}
}
