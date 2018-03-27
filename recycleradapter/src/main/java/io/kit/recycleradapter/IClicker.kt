package io.kit.recycleradapter

import android.view.View

interface IClicker<out T> {
    fun onClick(v: View, holder: DelegatVH<T>, position: Int)
}