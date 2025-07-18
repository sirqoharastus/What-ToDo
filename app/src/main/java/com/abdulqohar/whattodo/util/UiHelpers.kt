package com.abdulqohar.whattodo.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.abdulqohar.whattodo.R
import com.google.android.material.snackbar.Snackbar

class UiHelpers {
    companion object {
        var dialog: Dialog? = null

        fun Fragment.makeSnackbar(
            context: Context,
            view: View,
            message: String,
            isError: Boolean,
            duration: Int = Snackbar.LENGTH_LONG
        ) {
            val snackbar = Snackbar.make(requireView(), message, 10000)

            // Set background color based on message type
            val snackbarView = snackbar.view
            val bgColor = if (isError) {
                ContextCompat.getColor(
                    context,
                    R.color.red
                ) // Replace with your desired error color
            } else {
                ContextCompat.getColor(
                    context,
                    R.color.green
                ) // Replace with your desired success color
            }
            val cornerRadius = context.resources.getDimensionPixelSize(R.dimen.snackbar_corner_radius) // Replace with your desired corner radius
            snackbarView.background = getRoundedBackgroundDrawable(bgColor, cornerRadius)

            // Set drawable on the left side
            val textView =
                snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                if (isError) context.getDrawable(R.drawable.ic_info_icon) else context.getDrawable(R.drawable.ic_snackbar_check),
                null,
                null,
                null
            )
            textView.compoundDrawablePadding =
                context.resources.getDimensionPixelOffset(R.dimen.snackbar_drawable_padding) // Replace with your desired padding value
            textView.gravity = Gravity.CENTER_VERTICAL
            textView.maxLines = Int.MAX_VALUE
            textView.ellipsize = null
            snackbar.setAction("Dismiss") {
                snackbar.dismiss()
            }
            if (isAdded) {
                try {
                    snackbar.show()
                } catch (e: Exception) {e.printStackTrace()}
            }
        }

        private fun getRoundedBackgroundDrawable(color: Int, cornerRadius: Int): Drawable {
            val shape = GradientDrawable()
            shape.shape = GradientDrawable.RECTANGLE
            shape.cornerRadius = cornerRadius.toFloat()
            shape.setColor(color)
            return shape
        }

        fun showProgressDialog(activity: Activity?, isInternational: Boolean = false) {
            try {
                if (dialog != null && dialog!!.isShowing) {
                    dialog!!.dismiss()
                    dialog = null
                }
//        {
                dialog = Dialog(activity as Context , android.R.style.Theme_Black_NoTitleBar_Fullscreen)
                if (!dialog!!.isShowing) {
                    dialog!!.setContentView(R.layout.whattodo_loader_layout)
                    dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog!!.setCancelable(false)
                    dialog!!.show()
                }
//        }
            }catch (e: Exception){}
        }

        fun dismissProgressDialog() {
            try {
                if (dialog != null && dialog!!.isShowing) {
                    dialog!!.dismiss()
                    dialog = null
                }
            }catch (e: Exception){}
        }
    }
}