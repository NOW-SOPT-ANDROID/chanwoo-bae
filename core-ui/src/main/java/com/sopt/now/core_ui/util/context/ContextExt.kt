package com.sopt.now.core_ui.util.context

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.snackBar(
    anchorView: View,
    message: String
) {
    Snackbar.make(anchorView, message, Snackbar.LENGTH_SHORT).show()
}

fun Context.colorOf(
    @ColorRes resId: Int
) = ContextCompat.getColor(this, resId)

fun Context.drawableOf(
    @DrawableRes resId: Int
) = ContextCompat.getDrawable(this, resId)

fun Context.pxToDp(px: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        px.toFloat(),
        resources.displayMetrics
    ).toInt()
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.hideKeyboardOnTouch(event: MotionEvent): Boolean {
    if (event.action == MotionEvent.ACTION_DOWN) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow((this as Activity).currentFocus?.windowToken, 0)
    }
    return false
}


