package com.example.common_tools.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;

import com.example.common_tools.utils.CollectionUtils;
import com.example.common_tools.utils.FunctionNot;
import com.example.common_tools.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class StateLayout extends FrameLayout {
    /**
     * 静态为全局
     */
    private static SparseIntArray mGlobalLayoutArray = new SparseIntArray();

    private SparseArray<View> mLocalViewArray = new SparseArray<>();

    private SparseArray<View> mRootViewArray = new SparseArray<>();

    private SparseArray<View> mChildViewArray = new SparseArray<>();

    private List<View> mContentViewGone = new ArrayList<>();

    private boolean isContentViewShow;
    private View mCurrView;

    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();//初始化View
    }

    /**
     * 添加那4个子View：加载中的，加载成功的，加载没有数据，加载失败的
     */
    private void initView() {
        isContentViewShow = true;
        for (int i = 0; i < mGlobalLayoutArray.size(); i++) {
            int globalLayoutId = mGlobalLayoutArray.valueAt(i);
            View localView = View.inflate(getContext(), globalLayoutId, null);
            addView(localView);
            mRootViewArray.put(localView.getId(), localView);
        }
        hideAll();
    }

    public static void addGlobalRootView(int code, int layoutId) {
        mGlobalLayoutArray.put(code, layoutId);
    }

    public void addRootView(int code, View view) {
        addView(view);
        mLocalViewArray.put(code, view);
        mRootViewArray.put(view.getId(), view);
    }

    public View getRootViewByCode(int code) {
        View view = mLocalViewArray.get(code);
        if (view == null) {
            int layoutId = mGlobalLayoutArray.get(code);
            view = mRootViewArray.get(layoutId);
            if (view == null) {
                view = View.inflate(getContext(), layoutId, null);
            }
        }

        if (view != null) {
            int id = view.getId();
            if (mRootViewArray.indexOfKey(id) < 0) {
                mRootViewArray.put(id, view);
            }
        }
        return view;
    }

    public View getRootView(int layoutId) {
        View view = mRootViewArray.get(layoutId);
        if (view == null) {
            for (int i = 0; i < mLocalViewArray.size(); i++) {
                View localView = mLocalViewArray.valueAt(i);
                int id = localView.getId();
                if (mRootViewArray.indexOfKey(id) < 0) {
                    mRootViewArray.put(layoutId, localView);
                }
                if (layoutId == id) {
                    view = localView;
                }
            }

            if (view == null) {
                for (int i = 0; i < mGlobalLayoutArray.size(); i++) {
                    int globalLayoutId = mGlobalLayoutArray.valueAt(i);
                    if (mRootViewArray.indexOfKey(globalLayoutId) < 0) {
                        View localView = View.inflate(getContext(), globalLayoutId, null);
                        mRootViewArray.put(layoutId, localView);
                        if (layoutId == globalLayoutId) {
                            view = localView;
                        }
                    }
                }
            }
        }
        return view;
    }

    public List<View> getAllRootView() {
        for (int i = 0; i < mLocalViewArray.size(); i++) {
            View localView = mLocalViewArray.valueAt(i);
            int id = localView.getId();
            if (mRootViewArray.indexOfKey(id) < 0) {
                mRootViewArray.put(id, localView);
            }
        }

        for (int i = 0; i < mGlobalLayoutArray.size(); i++) {
            int globalLayoutId = mGlobalLayoutArray.valueAt(i);
            if (mRootViewArray.indexOfKey(globalLayoutId) < 0) {
                View localView = View.inflate(getContext(), globalLayoutId, null);
                mRootViewArray.put(localView.getId(), localView);
            }
        }
        ArrayList<View> views = new ArrayList<>();
        for (int i = 0; i < mRootViewArray.size(); i++) {
            View view = mRootViewArray.valueAt(i);
            views.add(view);
        }
        return views;
    }

    public View getChildViewByCode(int code, int id) {
        return getChildView(id, () -> getRootViewByCode(code));
    }

    public View getChildView(@LayoutRes int layoutId, int id) {
        return getChildView(id, () -> getRootView(layoutId));
    }


    public View getChildView(int id, FunctionNot<View> function) {
        View childView = mChildViewArray.get(id);
        if (childView != null) {
            return childView;
        }
        View rootView = function.apply();
        if (rootView == null) {
            return null;
        }
        childView = rootView.findViewById(id);
        if (childView != null) {
            mChildViewArray.put(id, childView);
        }
        return childView;
    }

    public void setOnClickListenerByCode(int code, int id, OnClickListener listener) {
        View childView = getChildViewByCode(code, id);
        childView.setOnClickListener(listener);
    }

    /**
     * 隐藏所有的View
     */
    public void hideAll() {
        //设置各界面不可见，同时让他们不重新layout，要用的时候直接show就行了
        for (View view : getAllRootView()) {
            view.setVisibility(View.GONE);
        }
    }

    public void showView(int code) {
        View rootViewByCode = getRootViewByCode(code);
        if (rootViewByCode == null) {
            LogUtils.e(new Throwable("the code not view"), "the code not view");
            return;
        }
        rootViewByCode.setVisibility(VISIBLE);
        mCurrView = rootViewByCode;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == View.VISIBLE && !rootViewByCode.equals(childAt)) {
                childAt.setVisibility(View.GONE);
                mContentViewGone.add(childAt);
            }
        }
        isContentViewShow = false;
    }

    public void showContentView() {
        if (!CollectionUtils.isEmpty(mContentViewGone)) {
            for (int i = 0; i < mContentViewGone.size(); i++) {
                View remove = mContentViewGone.remove(i);
                remove.setVisibility(View.VISIBLE);
            }
        }
        mCurrView = null;
        isContentViewShow = true;
    }

}
