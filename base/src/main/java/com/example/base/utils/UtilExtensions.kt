package com.example.base.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog

/**
**  This file contains reusable code in the form of extensions
**/

fun Activity.hideKeyboard(){
    CommonUtils.hideKeyboard(this)
}

fun Activity.showMessageDialog(title: String, message: String, buttonTitle: String){
    val mBuilder:AlertDialog.Builder = AlertDialog.Builder(this)
    CommonUtils.showMessageDialog(mBuilder,title,message,buttonTitle)
}