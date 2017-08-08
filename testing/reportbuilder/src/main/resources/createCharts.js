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
	$('canvas.thingml-summary').each(function(i, canvas) {
		var summary = $(canvas).data('summary');
		// Parse the data
		var labels = [];
		var counts = [];
		var colors = [];
		
		summary.results.forEach(function(res) {
			labels.push(res.name);
			counts.push(res.count);
			colors.push(res.color);
		});
		
		// Create a chart
		new Chart(canvas, {
			type: 'doughnut',
			data: {
				datasets: [{
					data: counts,
					backgroundColor: colors,
				}],
				labels: labels,
			},
			options: {
				legend: { display: false },
				cutoutPercentage: 70,
				middleText: summary.text,
			}
		});
	});
});

// Add text in the middle of 'doughnut' charts
Chart.pluginService.register({
  beforeDraw: function(chart) {
	if (chart.chart.config.type != 'doughnut') return;
	if (!chart.chart.config.options.middleText) return;
	  
    var width = chart.chart.width,
        height = chart.chart.height,
        ctx = chart.chart.ctx;

    ctx.restore();
    var fontSize = (height / 114).toFixed(2);
    ctx.font = fontSize + "em sans-serif";
    ctx.textBaseline = "middle";

    var text = chart.chart.config.options.middleText,
        textX = Math.round((width - ctx.measureText(text).width) / 2),
        textY = height / 2;

    ctx.fillText(text, textX, textY);
    ctx.save();
  }
});
/*
"",
		"  Array.prototype.forEach.call(document.getElementsByClassName('thingml-summary'), function(canvas) {",
		"    var chart = new Chart(canvas, {",
		"      type: 'doughnut',",
		"      data: {",
		"        datasets: [{",
		"          data: [10, 20],",
		"          backgroundColor: ['red','blue'],",
		"        }],",
		"        labels: ['Success','Failure']",
		"      },",
		"      options: {",
		"        legend: { display: false },",
		"        cutoutPercentage: 80,",
		"      }",
		"    });",
		"})});"
*/