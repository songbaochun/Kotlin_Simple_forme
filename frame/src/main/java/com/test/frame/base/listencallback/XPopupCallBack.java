package com.test.frame.base.listencallback;

import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.XPopupCallback;

public abstract class XPopupCallBack implements XPopupCallback {
    @Override
    public void onCreated(BasePopupView popupView) {

    }

    @Override
    public void beforeShow(BasePopupView popupView) {

    }

    @Override
    public void onShow(BasePopupView popupView) {
        onPopupShow(popupView);
    }

    @Override
    public void onDismiss(BasePopupView popupView) {
        onPopupDismiss(popupView);
    }

    @Override
    public void beforeDismiss(BasePopupView popupView) {

    }

    @Override
    public boolean onBackPressed(BasePopupView popupView) {
        return false;
    }

    @Override
    public void onKeyBoardStateChanged(BasePopupView popupView, int height) {

    }

    @Override
    public void onDrag(BasePopupView popupView, int value, float percent, boolean upOrLeft) {

    }

    abstract public void onPopupShow(BasePopupView popupView);

    abstract public void onPopupDismiss(BasePopupView popupView);
}
