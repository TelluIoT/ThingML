/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.mopp;

/**
 * The ThingmlTaskItemDetector is used to find task items in text documents. The
 * current implementation searches for specific keywords to detect task items. The
 * ThingmlTaskItemDetector is used both by the TaskItemBuilder and the editor.
 */
public class ThingmlTaskItemDetector {
	
	public static String[] TASK_ITEM_KEYWORDS = new String[] {"TODO", "FIXME", "XXX"};
	
	public java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlTaskItem> findTaskItems(String text, int line, int charStart) {
		java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlTaskItem> foundItems = new java.util.ArrayList<org.sintef.thingml.resource.thingml.mopp.ThingmlTaskItem>();
		String remainingText = text;
		boolean continueSearch = true;
		int localCharStart = charStart;
		while (remainingText != null && continueSearch) {
			continueSearch = false;
			for (String keyword : TASK_ITEM_KEYWORDS) {
				int index = remainingText.indexOf(keyword);
				if (index >= 0) {
					continueSearch = true;
					String message = remainingText.substring(index);
					// stop at end of line and check whether the next lines do also contain task items
					int eolIndex = remainingText.indexOf("\n", index);
					if (eolIndex < 0) {
						eolIndex = remainingText.indexOf("\r", index);
					}
					if (eolIndex > 0) {
						message = remainingText.substring(index, eolIndex);
						if (message.startsWith("\r")) {
							message = message.substring(1);
						}
						if (message.startsWith("\n")) {
							message = message.substring(1);
						}
						message = message.trim();
						remainingText = remainingText.substring(eolIndex);
					} else {
						remainingText = null;
					}
					// This is a somewhat arbitrary heuristics to remove the end delimiters from
					// multi-line comments. Since comments are usually implemented using hidden
					// (unused) tokens, there are no token resolvers that could be used to strip
					// delimiters. Thus, this is a reasonable default which reflects the fact that
					// many languages use Java-style multi-line comments.
					if (message.endsWith("*/")) {
						message = message.substring(0, message.length() - 2);
					}
					message = message.trim();
					
					int offset = index + localCharStart;
					int end = offset + keyword.length();
					int localLine = line + text.substring(0, offset - charStart).split("(\r\n|\r|\n)").length - 1;
					foundItems.add(new org.sintef.thingml.resource.thingml.mopp.ThingmlTaskItem(keyword, message, localLine, offset, end));
					localCharStart += eolIndex;
					// stop looping over the keywords, we've found one
					break;
				}
			}
		}
		return foundItems;
	}
	
}
