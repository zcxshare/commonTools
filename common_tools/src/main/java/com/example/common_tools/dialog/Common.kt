package com.example.common_tools.dialog

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.example.common_tools.R

class Common{
    fun show(context: Context){
        MaterialDialog(context).show {
            setContentView(R.layout.dialog_common)

        }
    }
}