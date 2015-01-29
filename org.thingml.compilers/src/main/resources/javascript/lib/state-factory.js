var State = require('state.js');
module.exports.buildStateMachine = function buildStateMachine(name) {
return new State.StateMachine(name);
}

module.exports.buildRegion = function buildRegion(name, container){
return new State.Region(name, container);
}

module.exports.buildInitialState = function buildInitialState(name, container){
return new State.PseudoState(name, State.PseudoStateKind.Initial, container);
}

module.exports.buildFinalState = function buildFinalState(name, container){
return new State.PseudoState(name, State.PseudoStateKind.Final, container);
}

module.exports.buildHistoryState = function buildHistoryState(name, container){
return new State.PseudoState(name, State.PseudoStateKind.ShallowHistory, container);
}

module.exports.buildSimpleState = function buildSimpleState(name, container){
return new State.SimpleState(name, container);
}

module.exports.buildCompositeState = function buildCompositeState(name, container){
return new State.CompositeState(name, container);
}

module.exports.buildOrthogonalState = function buildOrthogonalState(name, container){
return new State.OrthogonalState(name, container);
}

module.exports.buildEmptyTransition = function buildEmptyTransition(source, target){
return new State.Transition(source, target);
}

module.exports.buildTransition = function buildTransition(source, target, guard){
return new State.Transition(source, target, guard);
}

