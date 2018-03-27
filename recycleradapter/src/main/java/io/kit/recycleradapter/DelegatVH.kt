package io.kit.recycleradapter

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class DelegatVH<in T>(v: View) : RecyclerView.ViewHolder(v) {

    private var clicker: IClicker<T>? = null

    init {
        setClicker(itemView)
    }

    abstract fun onBind(item: T, position: Int)

    fun setClicker(clicker: IClicker<T>?) {
        this.clicker = clicker
    }

    private fun clearClicker() {
        clicker = null
    }

    fun setClicker(v: View) {
        v.setOnClickListener { clicker?.onClick(v, this, adapterPosition) }
    }

}