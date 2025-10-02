package com.jmadrigal.hackernews.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import androidx.core.graphics.drawable.toDrawable
import com.jmadrigal.hackernews.databinding.DialogLoadingBinding

class LoadingDialog() {

    companion object {

        private var dialogLoading: Dialog? = null

        fun show(activity: Activity) {
            Handler(Looper.getMainLooper()).post {
                val builder = AlertDialog.Builder(activity)
                val binding: DialogLoadingBinding by lazy { DialogLoadingBinding.inflate(activity.layoutInflater) }
                builder.setView(binding.root)

                dialogLoading = builder.create()
                dialogLoading?.show()
                dialogLoading?.window!!.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
                dialogLoading?.setCancelable(false)
            }
        }

        fun hide() {
            if (dialogLoading != null) {
                dialogLoading?.dismiss()
            }
        }
    }
}