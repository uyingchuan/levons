import { Component, Input, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: 'lib-form-label',
  exportAs: 'libFormLabel',
  template: `
    <label
      [attr.for]="appFor"
      [class.app-form-item-required]="required"
      class="app-form-label"
    >
      <span class="app-form-label-text"><ng-content></ng-content></span>
    </label>
  `,
  host: {
    class: 'app-form-item-label',
  },
  imports: [],
  standalone: true,
})
export class NzFormLabelComponent implements OnDestroy {
  @Input() appFor?: string;
  @Input() required = false;

  private destroy$ = new Subject<boolean>();

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.complete();
  }
}
