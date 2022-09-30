package com.melvin.ongandroid.utils

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.melvin.ongandroid.R

object Extensions {
    /*
     * Reusable snack bar for all fragments.
     * Call without message value to show default error message or set your custom message
     */
    fun errorSnackBar(view: View, customMessage: String = "", reloadData: () -> Unit) {
        var message = view.context.getString(R.string.error_message_getting_data)
        if (customMessage != "") message = customMessage
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(AppConstants.POSITIVE_BUTTON) {
                reloadData()
            }
            .show()
    }

    // show toast extension function
    fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(requireContext(),message,duration).show()
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.gone() {
        visibility = View.GONE
    }

}