package com.example.common_tools.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.AbsSeekBar;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.example.common_tools.utils.SpareArrayUtils;
import com.lxj.xpopup.XPopup;

public class NormalCenterDialog extends BaseCenterDialog {

    private int mLayout;

    private SparseArray<View> mViewMap = new SparseArray<>();
    private SparseArray<CharSequence> mSetTextViewMap;
    private SparseIntArray mSetCheckMap;
    private SparseIntArray mSetVisibilityMap;
    private SparseIntArray mSetProgressMap;
    private SparseIntArray mSetSeekBarMaxMap;
    private SparseArray<OnClickListener> mClickListenerMap;
    private SparseArray<CompoundButton.OnCheckedChangeListener> mCompoundCheckedChangeListenerMap;
    private SparseArray<RadioGroup.OnCheckedChangeListener> mRadioCheckedChangeListenerMap;
    private SparseArray<SeekBar.OnSeekBarChangeListener> mSeekBarChangeListenerMap;
    private LifecycleListener mLifecycleListener;
    private boolean mIsDismissOnBackPressed = true;
    private boolean mIsDismissOnTouchOutside = true;

    public NormalCenterDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return mLayout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initEvent();
        if (mLifecycleListener != null) {
            mLifecycleListener.onCreate(this);
        }
    }

    private void initEvent() {
        initViewMap(mSetTextViewMap, (ViewEventFunction<TextView, CharSequence>) TextView::setText);
        initViewMap(mSetCheckMap, RadioGroup::check);
        initViewMap(mSetVisibilityMap, View::setVisibility);
        initViewMap(mSetProgressMap, (ViewEventFunction<ProgressBar, Integer>) ProgressBar::setProgress);
        initViewMap(mSetSeekBarMaxMap, AbsSeekBar::setMax);
        initViewMap(mClickListenerMap, View::setOnClickListener);
        initViewMap(mCompoundCheckedChangeListenerMap, CompoundButton::setOnCheckedChangeListener);
        initViewMap(mRadioCheckedChangeListenerMap, RadioGroup::setOnCheckedChangeListener);
        initViewMap(mSeekBarChangeListenerMap, SeekBar::setOnSeekBarChangeListener);
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        if (mLifecycleListener != null) {
            mLifecycleListener.onDismiss(this);
        }
    }

    private <V extends View, D> void initViewMap(SparseArray<D> map, ViewEventFunction<V, D> function) {
        if (!SpareArrayUtils.isEmpty(map)) {
            for (int i = 0; i < map.size(); i++) {
                int viewId = map.keyAt(i);
                V view = getView(viewId);
                if (view != null) {
                    function.apply(view, map.get(viewId));
                }
            }
        }
    }

    private <V extends View> void initViewMap(SparseIntArray map, ViewEventFunction<V, Integer> function) {
        if (!SpareArrayUtils.isEmpty(map)) {
            for (int i = 0; i < map.size(); i++) {
                int viewId = map.keyAt(i);
                V view = getView(viewId);
                if (view != null) {
                    function.apply(view, map.get(viewId));
                }
            }
        }
    }

    public <V extends View> V getView(int viewId) {
        View view = mViewMap.get(viewId);
        if (view == null) {
            view = findViewById(viewId);
            if (view != null) {
                mViewMap.put(viewId, view);
            }
        }
        return (V) view;
    }


    @Override
    public NormalCenterDialog show() {
        XPopup.Builder builder = new XPopup.Builder(getContext())
                .dismissOnBackPressed(mIsDismissOnBackPressed)
                .dismissOnTouchOutside(mIsDismissOnTouchOutside);
        return show(builder);
    }

    public NormalCenterDialog show(XPopup.Builder builder) {
        builder.asCustom(this);
        return (NormalCenterDialog) super.show();
    }

    public NormalCenterDialog setLayout(@LayoutRes int layout) {
        mLayout = layout;
        return this;
    }

    public NormalCenterDialog setText(@IdRes int textView, CharSequence text) {
        if (mSetTextViewMap == null) {
            mSetTextViewMap = new SparseArray<>();
        }
        mSetTextViewMap.put(textView, text);
        return this;
    }

    public NormalCenterDialog setSeekBarMax(@IdRes int seekBar, int max) {
        if (mSetSeekBarMaxMap == null) {
            mSetSeekBarMaxMap = new SparseIntArray();
        }
        mSetSeekBarMaxMap.put(seekBar, max);
        return this;
    }

    public NormalCenterDialog setOnClickListener(@IdRes int view, OnClickListener clickListener) {
        if (mClickListenerMap == null) {
            mClickListenerMap = new SparseArray<>();
        }
        mClickListenerMap.put(view, clickListener);
        if (isCreated) {
            initViewMap(mClickListenerMap, View::setOnClickListener);
        }
        return this;
    }


    public NormalCenterDialog setSwitchOnCheckedChangeListener(@IdRes int switchView, CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        if (mCompoundCheckedChangeListenerMap == null) {
            mCompoundCheckedChangeListenerMap = new SparseArray<>();
        }
        mCompoundCheckedChangeListenerMap.put(switchView, checkedChangeListener);
        return this;
    }

    public NormalCenterDialog setOnSeekBarChangeListener(@IdRes int seekBar, SeekBar.OnSeekBarChangeListener seekBarChangeListener) {
        if (mSeekBarChangeListenerMap == null) {
            mSeekBarChangeListenerMap = new SparseArray<>();
        }
        mSeekBarChangeListenerMap.put(seekBar, seekBarChangeListener);
        return this;
    }

    public NormalCenterDialog setRadioGroupOnCheckedChangeListener(@IdRes int radioGroup, RadioGroup.OnCheckedChangeListener checkedChangeListener) {
        if (mRadioCheckedChangeListenerMap == null) {
            mRadioCheckedChangeListenerMap = new SparseArray<>();
        }
        mRadioCheckedChangeListenerMap.put(radioGroup, checkedChangeListener);
        return this;
    }

    public NormalCenterDialog setCheck(@IdRes int radioGroup, int id) {
        if (mSetCheckMap == null) {
            mSetCheckMap = new SparseIntArray();
        }
        mSetCheckMap.put(radioGroup, id);
        return this;
    }

    public NormalCenterDialog setVisibility(@IdRes int view, int visibility) {
        if (mSetVisibilityMap == null) {
            mSetVisibilityMap = new SparseIntArray();
        }
        mSetVisibilityMap.put(view, visibility);
        return this;
    }

    public NormalCenterDialog setProgress(@IdRes int progressBar, int progress) {
        if (mSetProgressMap == null) {
            mSetProgressMap = new SparseIntArray();
        }
        mSetProgressMap.put(progressBar, progress);
        return this;
    }

    public NormalCenterDialog setDismissOnBackPressed(boolean isDismiss) {
        mIsDismissOnBackPressed = isDismiss;
        return this;
    }

    public NormalCenterDialog setDismissOnTouchOutside(boolean isDismiss) {
        mIsDismissOnTouchOutside = isDismiss;
        return this;
    }

    public NormalCenterDialog dismissDialog() {
        super.dismiss();
        return this;
    }

    public NormalCenterDialog setLifecycleListener(LifecycleListener lifecycleListener) {
        mLifecycleListener = lifecycleListener;
        return this;
    }

    public interface ViewEventFunction<V, D> {
        void apply(V view, D data);
    }

    public static class LifecycleListener {
        public void onCreate(NormalCenterDialog dialog) {
        }

        public void onDismiss(NormalCenterDialog dialog) {
        }
    }
}
