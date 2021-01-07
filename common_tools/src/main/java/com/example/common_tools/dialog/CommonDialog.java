package com.example.common_tools.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogBehavior;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.main.DialogLayout;
import com.example.common_tools.R;

public class CommonDialog {
    private Context mContext;
    @LayoutRes
    private int mLayoutId;
    private int mThemeResId;
    private MaterialDialog mMaterialDialog;

    private SparseArray<View> mViewArray = new SparseArray<>();


    public CommonDialog(@NonNull Context context) {
        this(context, R.layout.dialog_common);
    }

    public CommonDialog(@NonNull Context context, @LayoutRes int layoutId) {
        this(context, layoutId, 0);
    }

    public CommonDialog(@NonNull Context context, @LayoutRes int layoutId, int themeResId) {
        mContext = context;
        mLayoutId = layoutId;
        mThemeResId = themeResId;

    }

    public CommonDialog setText(int id, CharSequence charSequence) {
        return this;
    }

    private <T>View getView(int id) {
        mViewArray
        return null;
    }


    public CommonDialog show() {
        DialogBehavior dialogBehavior = new DialogBehavior() {

            @Override
            public void setWindowConstraints(Context context, Window window, DialogLayout dialogLayout, Integer integer) {

            }

            @Override
            public void setBackgroundColor(DialogLayout dialogLayout, int i, float v) {

            }

            @Override
            public void onPreShow(MaterialDialog materialDialog) {

            }

            @Override
            public void onPostShow(MaterialDialog materialDialog) {

            }

            @Override
            public boolean onDismiss() {
                return false;
            }

            @Override
            public int getThemeRes(boolean b) {
                return mThemeResId;
            }

            @Override
            public DialogLayout getDialogLayout(ViewGroup viewGroup) {
                return null;
            }

            @Override
            public ViewGroup createView(Context context, Window window, LayoutInflater layoutInflater, MaterialDialog materialDialog) {
                return null;
            }
        };
        mMaterialDialog = new MaterialDialog(mContext, dialogBehavior);
        mMaterialDialog
                .setContentView(mLayoutId);
        mMaterialDialog.show();
        return this;
    }

    public Dialog getDialog() {
        return mMaterialDialog;
    }

}
