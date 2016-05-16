// Generated code from Butter Knife. Do not modify!
package com.banvien.fcv.mobile;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BaseActivity$$ViewBinder<T extends com.banvien.fcv.mobile.BaseActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findOptionalView(source, 2131624101, null);
    target.toolbar = finder.castView(view, 2131624101, "field 'toolbar'");
  }

  @Override public void unbind(T target) {
    target.toolbar = null;
  }
}
