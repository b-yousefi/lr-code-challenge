import { FrameworkConfiguration, PLATFORM } from 'aurelia-framework';

export function configure(config: FrameworkConfiguration) {
  // make custom elements globally available
  config.globalResources([
    PLATFORM.moduleName('./add-items-dialog/add-items-dialog'),
    PLATFORM.moduleName('./item-card/item-card'),
    PLATFORM.moduleName('./item-version-card/item-version-card'),
  ]);
}
