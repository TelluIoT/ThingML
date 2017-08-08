/*
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
$(function() {
	var showButton = $('button#thingml-only-failures');
	var suiteNoFailures = $('div.thingml-suite:not(.has-failure)');
	var testNoFailures = $('th.thingml-test:not(.has-failure),td.thingml-test:not(.has-failure)');
	
	var showOnlyFailures = false;
	var updateFilter = function() {
		if (showOnlyFailures) {
			suiteNoFailures.hide();
			testNoFailures.hide();
			showButton.addClass('active');
		} else {
			suiteNoFailures.show();
			testNoFailures.show();
			showButton.removeClass('active');
		}
	};
	updateFilter();
	
	// Trigger filtering
	showButton.click(function(event) {
		showOnlyFailures = !showOnlyFailures;
		updateFilter();
	});
	
	$(document).keypress(function(event) {
		if (event.key == 'f') {
			showOnlyFailures = !showOnlyFailures;
			updateFilter();
		}
	});
});