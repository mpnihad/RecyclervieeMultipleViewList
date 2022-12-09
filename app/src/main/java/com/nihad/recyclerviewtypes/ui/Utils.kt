package com.nihad.recyclerviewtypes.ui

import android.app.Activity
import android.net.Uri
import android.view.View
import com.facebook.drawee.view.SimpleDraweeView

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun SimpleDraweeView.loadImage(url: String) {
    this.setImageURI(Uri.parse(url))
}

