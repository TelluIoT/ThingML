const OriginalStateJS = require('state.js');

class Region extends OriginalStateJS.Region {
  constructor(name, parent) {
    super(name, parent)
  }
}

class PseudoState extends OriginalStateJS.PseudoState {
  constructor(name, parent, kind) {
    super(name, parent, kind)
  }
}

class State extends OriginalStateJS.State {
  constructor(name, parent) {
    super(name, parent)
  }
}

class FinalState extends OriginalStateJS.FinalState {
  constructor(name, parent) {
    super(name, parent)
  }
}

class StateMachine extends OriginalStateJS.StateMachine {
  constructor(name) {
    super(name)
  }
}

const StateJS = {
  PseudoStateKind: OriginalStateJS.PseudoStateKind,
  Region: Region,
  PseudoState: PseudoState,
  State: State,
  FinalState: FinalState,
  StateMachine: StateMachine,
  StateMachineInstance: OriginalStateJS.StateMachineInstance,
  evaluate: OriginalStateJS.evaluate,
  initialise: OriginalStateJS.initialise
};

export default StateJS
