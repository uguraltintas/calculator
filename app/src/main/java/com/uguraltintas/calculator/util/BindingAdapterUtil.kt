package com.uguraltintas.calculator.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("setVisibility")
fun View.setVisibility(value: Boolean) {
    visibility = if (value) View.VISIBLE else View.INVISIBLE
}