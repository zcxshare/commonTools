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

public class NormalBottomDialog extends BaseBottomDialog {

    private int mLayout;

    private SparseArray<View> mViewMap = new SparseArray<>();
    private SparseArray<CharSequence> mSetTextViewMap;
    private SparseIntArray mSetCheckMap;
    private SparseIntArray mSetProgressMap;
    private SparseIntArray mSetSeekBarMaxMap;
    private SparseArray<View.OnClickListener> mClickListenerMap;
    private SparseArray<CompoundButton.OnCheckedChangeListener> mCompoundCheckedChangeListenerMap;
    private SparseArray<RadioGroup.OnCheckedChangeListener> mRadioCheckedChangeListenerMap;
    private SparseArray<SeekBar.OnSeekBarChangeListener> mSeekBarChangeListenerMap;
    private LifecycleListener mLifecycleListener;
    private boolean mIsDismissOnBackPressed = true;
    private boolean mIsDismissOnTouchOutside = true;

    public NormalBottomDialog(@NonNull Context context) {
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
    public NormalBottomDialog show() {
        XPopup.Builder builder = new XPopup.Builder(getContext())
                .dismissOnBackPressed(mIsDismissOnBackPressed)
                .dismissOnTouchOutside(mIsDismissOnTouchOutside);
        return show(builder);
    }

    public NormalBottomDialog show(XPopup.Builder builder) {
        builder.asCustom(this);
        return (NormalBottomDialog) super.show();
    }

    public NormalBottomDialog setLayout(@LayoutRes int layout) {
        mLayout = layout;
        return this;
    }

    public NormalBottomDialog setText(@IdRes int textView, CharSequence text) {
        if (mSetTextViewMap == null) {
            mSetTextViewMap = new SparseArray<>();
        }
        mSetTextViewMap.put(textView, text);
        if (isShow()) {
            ((TextView) getView(textView)).setText(text);
        }
        return this;
    }

    public NormalBottomDialog setSeekBarMax(@IdRes int seekBar, int max) {
        if (mSetSeekBarMaxMap == null) {
            mSetSeekBarMaxMap = new SparseIntArray();
        }
        mSetSeekBarMaxMap.put(seekBar, max);
        return this;
    }

    public NormalBottomDialog setOnClickListener(@IdRes int view, View.OnClickListener clickListener) {
        if (mClickListenerMap == null) {
            mClickListenerMap = new SparseArray<>();
        }
        mClickListenerMap.put(view, clickListener);
        if (isCreated) {
            initViewMap(mClickListenerMap, View::setOnClickListener);
        }
        return this;
    }


    public NormalBottomDialog setSwitchOnCheckedChangeListener(@IdRes int switchView, CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        if (mCompoundCheckedChangeListenerMap == null) {
            mCompoundCheckedChangeListenerMap = new SparseArray<>();
        }
        mCompoundCheckedChangeListenerMap.put(switchView, checkedChangeListener);
        return this;
    }

    public NormalBottomDialog setOnSeekBarChangeListener(@IdRes int seekBar, SeekBar.OnSeekBarChangeListener seekBarChangeListener) {
        if (mSeekBarChangeListenerMap == null) {
            mSeekBarChangeListenerMap = new SparseArray<>();
        }
        mSeekBarChangeListenerMap.put(seekBar, seekBarChangeListener);
        return this;
    }

    public NormalBottomDialog setRadioGroupOnCheckedChangeListener(@IdRes int radioGroup, RadioGroup.OnCheckedChangeListener checkedChangeListener) {
        if (mRadioCheckedChangeListenerMap == null) {
            mRadioCheckedChangeListenerMap = new SparseArray<>();
        }
        mRadioCheckedChangeListenerMap.put(radioGroup, checkedChangeListener);
        return this;
    }


    public NormalBottomDialog setCheck(@IdRes int radioGroup, int id) {
        if (mSetCheckMap == null) {
            mSetCheckMap = new SparseIntArray();
        }
        mSetCheckMap.put(radioGroup, id);
        return this;
    }

    public NormalBottomDialog setProgress(@IdRes int progressBar, int progress) {
        if (mSetProgressMap == null) {
            mSetProgressMap = new SparseIntArray();
        }
        mSetProgressMap.put(progressBar, progress);
        return this;
    }

    public NormalBottomDialog setDismissOnBackPressed(boolean isDismiss) {
        mIsDismissOnBackPressed = isDismiss;
        return this;
    }

    public NormalBottomDialog setDismissOnTouchOutside(boolean isDismiss) {
        mIsDismissOnTouchOutside = isDismiss;
        return this;
    }

    public void setLifecycleListener(LifecycleListener lifecycleListener) {
        mLifecycleListener = lifecycleListener;
    }

    public interface ViewEventFunction<V, D> {
        void apply(V view, D data);
    }

    public static class LifecycleListener {
        public void onCreate(NormalBottomDialog dialog) {
        }

        public void onDismiss(NormalBottomDialog dialog) {
        }
    }
}
