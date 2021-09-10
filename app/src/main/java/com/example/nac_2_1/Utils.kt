package com.example.nac_2_1

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity



 fun hideKeyboard(activity: FragmentActivity) {
    // Check if no view has focus:
    val view = activity.currentFocus
    if (view != null) {
        val inputManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}
