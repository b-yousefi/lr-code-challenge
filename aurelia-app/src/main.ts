import { Aurelia } from 'aurelia-framework';
import * as environment from '../config/environment.json';
import { PLATFORM } from 'aurelia-pal';
import { LogLevel, PerformanceMeasurement } from "aurelia-store";
import { initialState } from "./store/item/state";

export function configure(aurelia: Aurelia) {
  aurelia.use
    .standardConfiguration()
    .feature(PLATFORM.moduleName('resources/index'));

  aurelia.use.developmentLogging(environment.debug ? 'debug' : 'warn');

  if (environment.testing) {
    aurelia.use.plugin(PLATFORM.moduleName('aurelia-testing'));
  }

  aurelia.use.plugin(PLATFORM.moduleName("aurelia-store"), {
    initialState,
    measurePerformance: PerformanceMeasurement.All,
    logDispatchedActions: true,
    logDefinitions: {
      dispatchedActions: LogLevel.debug,
    },
  });

  aurelia.start().then(() => aurelia.setRoot(PLATFORM.moduleName('app')));
}
