define(["ace/lib/oop", "ace/mode/text", "ace/mode/text_highlight_rules"], function(oop, mText, mTextHighlightRules) {
	var HighlightRules = function() {
		var keywords = "action|and|buffer|by|composite|configuration|connector|datatype|do|during|else|end|entry|enumeration|error|event|exit|false|final|fork|fragment|from|function|guard|history|if|import|includes|init|instance|internal|join|keep|keeps|length|merge|message|not|object|on|optional|or|over|port|print|produce|property|protocol|provided|readonly|receives|region|required|return|select|sends|session|set|state|statechart|stream|thing|transition|true|var|while";
		this.$rules = {
			"start": [
				{token: "lparen", regex: "[\\[({]"},
				{token: "rparen", regex: "[\\])}]"},
				{token: "keyword", regex: "\\b(?:" + keywords + ")\\b"}
			]
		};
	};
	oop.inherits(HighlightRules, mTextHighlightRules.TextHighlightRules);
	
	var Mode = function() {
		this.HighlightRules = HighlightRules;
	};
	oop.inherits(Mode, mText.Mode);
	Mode.prototype.$id = "xtext/thingml";
	Mode.prototype.getCompletions = function(state, session, pos, prefix) {
		return [];
	}
	
	return {
		Mode: Mode
	};
});
