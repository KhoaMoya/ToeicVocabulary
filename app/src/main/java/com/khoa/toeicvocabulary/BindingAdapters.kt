package com.khoa.toeicvocabulary

import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.khoa.toeicvocabulary.models.Category

@BindingAdapter("setCategoryProgress")
fun setCategoryProgress(progressBar: ProgressBar, category: Category) {
    if (category.wordCount == 0) progressBar.progress = 0
    else progressBar.progress = category.known * 100 / category.wordCount
}

@BindingAdapter("setCategoryProgressText")
fun setCategoryProgressText(textView: TextView, category: Category) {
    textView.text = "${category.known}/${category.wordCount}"
}

@BindingAdapter("addItemDecoration")
fun addItemDecoration(rcv: RecyclerView, direction: Int) {
    val decoration = DividerItemDecoration(rcv.context, LinearLayout.VERTICAL)
    rcv.addItemDecoration(decoration)
}

@BindingAdapter("setVisible")
fun setVisible(view: View, isVisible: Boolean) {
    if (isVisible) view.visibility = View.VISIBLE
    else view.visibility = View.GONE
}

@BindingAdapter("checked")
fun setChecked(checkbox: CheckBox, status: Int) {
    checkbox.isChecked = status == 1
}

@BindingAdapter("setTextMultiline")
fun setTextMultiline(txt: TextView, text: String) {
    val splits = text.split("|")
    txt.text = ""
    splits.forEachIndexed { index, s ->
        txt.append(s)
        if (index < splits.size-1) {
            txt.append(System.getProperty("line.separator"))
        }
    }
}

