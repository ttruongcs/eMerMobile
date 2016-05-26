// Generated code from Butter Knife. Do not modify!
package com.banvien.fcv.mobile;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PrepareActivity$$ViewBinder<T extends com.banvien.fcv.mobile.PrepareActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624107, "field 'prepareStep'");
    target.prepareStep = finder.castView(view, 2131624107, "field 'prepareStep'");
    view = finder.findRequiredView(source, 2131624110, "field 'actionStep'");
    target.actionStep = finder.castView(view, 2131624110, "field 'actionStep'");
    view = finder.findRequiredView(source, 2131624113, "field 'endStep'");
    target.endStep = finder.castView(view, 2131624113, "field 'endStep'");
  }

  @Override public void unbind(T target) {
    target.prepareStep = null;
    target.actionStep = null;
    target.endStep = null;
  }
}
