const OriginalStateJS = require('state.js');
import React from 'react';
import {observer} from 'mobx-react';

const extendRenderable = (target) => {
  target._template = () => null;
  target.template = (template) => {
    target._template = template;
  };
  target.render = () => target._template(target);
};

class Region extends OriginalStateJS.Region {
  constructor(name, parent) {
    super(name, parent);
    extendRenderable(this);
  }
}

class PseudoState extends OriginalStateJS.PseudoState {
  constructor(name, parent, kind) {
    super(name, parent, kind);
    extendRenderable(this);
  }
}

class State extends OriginalStateJS.State {
  constructor(name, parent) {
    super(name, parent);
    extendRenderable(this);
  }
}

class FinalState extends OriginalStateJS.FinalState {
  constructor(name, parent) {
    super(name, parent);
    extendRenderable(this);
  }
}

class StateMachine extends OriginalStateJS.StateMachine {
  constructor(name) {
    super(name);
    extendRenderable(this);
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

const Wrapper = observer(class extends React.Component {
	render() {
		return this.props.instance.render();
	}
});

export {StateJS, Wrapper}
