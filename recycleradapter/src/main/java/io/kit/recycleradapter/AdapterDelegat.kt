package io.kit.recycleradapter

import android.view.ViewGroup

abstract class AdapterDelegat<T>(var clicker: ItemClicker<T>? = null) {
    abstract fun createVH(parent: ViewGroup): DelegatVH<T>
    abstract fun isDelegatForItem(item: T): Boolean
}