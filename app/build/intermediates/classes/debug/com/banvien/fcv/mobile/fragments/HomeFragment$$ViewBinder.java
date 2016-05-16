// Generated code from Butter Knife. Do not modify!
package com.banvien.fcv.mobile.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HomeFragment$$ViewBinder<T extends com.banvien.fcv.mobile.fragments.HomeFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624093, "field 'initStep'");
    target.initStep = finder.castView(view, 2131624093, "field 'initStep'");
    view = finder.findRequiredView(source, 2131624094, "field 'actionStep'");
    target.actionStep = finder.castView(view, 2131624094, "field 'actionStep'");
    view = finder.findRequiredView(source, 2131624097, "field 'endStep'");
    target.endStep = finder.castView(view, 2131624097, "field 'endStep'");
  }

  @Override public void unbind(T target) {
    target.initStep = null;
    target.actionStep = null;
    target.endStep = null;
  }
}
