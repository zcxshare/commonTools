package com.example.common_tools.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BottomSelectDialog extends BaseBottomDialog {

    private boolean mSelectedDismiss;

    private int mLayoutId;

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    private int mCheckedPosition;

    private String mTitle;

    private List<String> mItems;

    private List<OnItemClickListener> mItemClickListeners = new ArrayList<>();

    public BottomSelectDialog(@NonNull Context context) {
        super(context);
    }

    public boolean isSelectedDismiss() {
        return mSelectedDismiss;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public BottomSelectDialog setLayoutId(int layoutId) {
        this.mLayoutId = layoutId;
        return this;
    }

    public BottomSelectDialog setSelectedDismiss(boolean selectedDismiss) {
        this.mSelectedDismiss = selectedDismiss;
        return this;
    }

    public BaseQuickAdapter<String, BaseViewHolder> getAdapter() {
        return mAdapter;
    }

    public BottomSelectDialog setAdapter(BaseQuickAdapter<String, BaseViewHolder> adapter) {
        this.mAdapter = adapter;
        return this;
    }

    public int getCheckedPosition() {
        return mCheckedPosition;
    }

    public BottomSelectDialog setCheckedPosition(int checkedPosition) {
        this.mCheckedPosition = checkedPosition;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public BottomSelectDialog setTitle(String mTitle) {
        this.mTitle = mTitle;
        return this;
    }

    public List<String> getItems() {
        return mItems;
    }

    public BottomSelectDialog setItems(List<String> items) {
        this.mItems = items;
        return this;
    }

    public List<OnItemClickListener> getItemClickListeners() {
        return mItemClickListeners;
    }

    public BottomSelectDialog addItemClickListener(OnItemClickListener mItemClickListener) {
        mItemClickListeners.add(mItemClickListener);
        return this;
    }

    public void removeItemClickListener(OnItemClickListener mItemClickListener) {
        mItemClickListeners.remove(mItemClickListener);
    }

    public interface OnItemClickListener {
        void onItemClick(BottomSelectDialog dialog, View view, int position);
    }

}
