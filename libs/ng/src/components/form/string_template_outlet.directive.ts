/**
 * Use of this source code is governed by an MIT-style license that can be
 * found in the LICENSE file at https://github.com/NG-ZORRO/ng-zorro-antd/blob/master/LICENSE
 */

import {
  Directive,
  EmbeddedViewRef,
  Input,
  OnChanges,
  SimpleChange,
  SimpleChanges,
  TemplateRef,
  ViewContainerRef,
} from '@angular/core';
import { SafeAny } from '@levons/common';

@Directive({
  selector: '[libStringTemplateOutlet]',
  exportAs: 'lib-string-template-outlet',
  standalone: true,
})
// eslint-disable-next-line @typescript-eslint/no-unused-vars
export class StringTemplateOutletDirective<_T = unknown> implements OnChanges {
  private embeddedViewRef: EmbeddedViewRef<SafeAny> | null = null;
  private context = new LibStringTemplateOutletContext();
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  @Input() libStringTemplateOutletContext: any = null;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  @Input() libStringTemplateOutlet: any = null;

  static ngTemplateContextGuard<T>(
    _dir: StringTemplateOutletDirective<T>,
    _ctx: SafeAny
  ): _ctx is LibStringTemplateOutletContext {
    return true;
  }

  private recreateView(): void {
    this.viewContainer.clear();
    const isTemplateRef = this.libStringTemplateOutlet instanceof TemplateRef;
    const templateRef = (
      isTemplateRef ? this.libStringTemplateOutlet : this.templateRef
    ) as SafeAny;
    this.embeddedViewRef = this.viewContainer.createEmbeddedView(
      templateRef,
      isTemplateRef ? this.libStringTemplateOutletContext : this.context
    );
  }

  private updateContext(): void {
    const isTemplateRef = this.libStringTemplateOutlet instanceof TemplateRef;
    const newCtx = isTemplateRef
      ? this.libStringTemplateOutletContext
      : this.context;
    const oldCtx = this.embeddedViewRef?.context as SafeAny;
    if (newCtx) {
      for (const propName of Object.keys(newCtx)) {
        oldCtx[propName] = newCtx[propName];
      }
    }
  }

  constructor(
    private viewContainer: ViewContainerRef,
    private templateRef: TemplateRef<SafeAny>
  ) {}

  ngOnChanges(changes: SimpleChanges): void {
    const { nzStringTemplateOutletContext, nzStringTemplateOutlet } = changes;
    const shouldRecreateView = (): boolean => {
      let shouldOutletRecreate = false;
      if (nzStringTemplateOutlet) {
        if (nzStringTemplateOutlet.firstChange) {
          shouldOutletRecreate = true;
        } else {
          const isPreviousOutletTemplate =
            nzStringTemplateOutlet.previousValue instanceof TemplateRef;
          const isCurrentOutletTemplate =
            nzStringTemplateOutlet.currentValue instanceof TemplateRef;
          shouldOutletRecreate =
            isPreviousOutletTemplate || isCurrentOutletTemplate;
        }
      }
      const hasContextShapeChanged = (ctxChange: SimpleChange): boolean => {
        const prevCtxKeys = Object.keys(ctxChange.previousValue || {});
        const currCtxKeys = Object.keys(ctxChange.currentValue || {});
        if (prevCtxKeys.length === currCtxKeys.length) {
          for (const propName of currCtxKeys) {
            if (prevCtxKeys.indexOf(propName) === -1) {
              return true;
            }
          }
          return false;
        } else {
          return true;
        }
      };
      const shouldContextRecreate =
        nzStringTemplateOutletContext &&
        hasContextShapeChanged(nzStringTemplateOutletContext);
      return shouldContextRecreate || shouldOutletRecreate;
    };

    if (nzStringTemplateOutlet) {
      this.context.$implicit = nzStringTemplateOutlet.currentValue;
    }

    const recreateView = shouldRecreateView();
    if (recreateView) {
      /** recreate view when context shape or outlet change **/
      this.recreateView();
    } else {
      /** update context **/
      this.updateContext();
    }
  }
}

export class LibStringTemplateOutletContext {
  public $implicit: SafeAny;
}
