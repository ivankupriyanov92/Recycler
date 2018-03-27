package io.kit.recycleradapter

import android.view.View

interface ItemClicker<T> {
    fun onClick(v: View, holder: DelegatVH<T>, position: Int, item: T)
}