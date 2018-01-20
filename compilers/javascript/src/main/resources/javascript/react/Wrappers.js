import React from 'react';
import {extendObservable} from 'mobx';
import {observer} from 'mobx-react';
const OriginalStateJS = require('state.js');

const Wrapper = observer(class extends React.Component {
	render() {
		return this.props.instance.render() || null;
	}
});

const extendRenderable = (target) => {
	let _template = () => null;
  target.template = (template) => { _template = template; }
  target.render = () => _template(target) || null;
};

const extendContainer = (target, type) => {
	const list = [];
	target['add'+type] = (child) => { list.push(child); }
	target['get'+type+'s'] = (...names) => {
		const result = [];
		if (names.length == 0) {
			for (let child of list) {
				result.push(<Wrapper key={child.name} instance={child}/>);
			}
		} else {
			for (let name of names) {
				const child = list.find(child => name == child.name);
				if (child) {
					result.push(<Wrapper key={child.name} instance={child}/>);
				}
			}
		}
		return result;
	}
};

const extendActive = (target, state) => {
	extendObservable(target, { isactive: false });
	state.entry(() => { target.isactive = true; });
	state.exit(() => { target.isactive = false; });
};

class Region extends OriginalStateJS.Region {
  constructor(name, parent) {
    super(name, parent);
    extendRenderable(this);
		extendContainer(this,'substate');
		extendActive(this, parent);
		parent.addregion(this);
  }
}

class State extends OriginalStateJS.State {
  constructor(name, parent) {
    super(name, parent);
    extendRenderable(this);
		extendContainer(this,'substate');
		extendContainer(this,'region');
		extendActive(this, this);
		parent.addsubstate(this);
  }
}

class FinalState extends OriginalStateJS.FinalState {
  constructor(name, parent) {
    super(name, parent);
    extendRenderable(this);
		extendActive(this, this);
		parent.addsubstate(this);
  }
}

class StateMachine extends OriginalStateJS.StateMachine {
  constructor(name) {
    super(name);
    extendRenderable(this);
		extendContainer(this,'substate');
		extendContainer(this,'region');
		extendActive(this, this);
  }
}

const StateJS = {
  PseudoStateKind: OriginalStateJS.PseudoStateKind,
  Region: Region,
  PseudoState: OriginalStateJS.PseudoState,
  State: State,
  FinalState: FinalState,
  StateMachine: StateMachine,
  StateMachineInstance: OriginalStateJS.StateMachineInstance,
  evaluate: OriginalStateJS.evaluate,
  initialise: OriginalStateJS.initialise
};

export {StateJS, Wrapper, extendContainer}
