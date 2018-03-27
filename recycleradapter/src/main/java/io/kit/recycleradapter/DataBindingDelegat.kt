package io.kit.recycleradapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import io.tchop.tchopcore.BR

class DataBindingDelegat<T>(
        @LayoutRes val res: Int,
        val comparator: (T) -> Boolean,
        val binder: (binding: ViewDataBinding, item: T) -> Unit = { binding, item -> binding.setVariable(BR.item, item) }
) : AdapterDelegat<T>() {

    override fun createVH(parent: ViewGroup): DelegatVH<T> {
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), res, parent, false)
        return VH(binding, binder)

    }


    override fun isDelegatForItem(item: T): Boolean {
        return comparator(item)
    }

    class VH<in T>(val binding: ViewDataBinding, val binder: (binding: ViewDataBinding, item: T) -> Unit) : DelegatVH<T>(binding.root) {
        override fun onBind(item: T, position: Int) {
            binder(binding, item)
            binding.executePendingBindings()
        }
    }

}

