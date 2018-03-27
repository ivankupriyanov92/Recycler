package io.kit.recycleradapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

@Suppress("unused")
class DelegatAdapter<T : Any> : RecyclerView.Adapter<DelegatVH<T>>(), IClicker<T> {

    var clicker: ItemClicker<T>? = null
    private val data: ArrayList<T> by lazy { ArrayList<T>() }
    private val delegats: HashMap<Int, AdapterDelegat<T>> = HashMap()

    fun register(delegat: AdapterDelegat<T>) {
        delegats[delegats.size + 1] = delegat
    }

    override fun onClick(v: View, holder: DelegatVH<T>, position: Int) {
        val item = getItem(position)
        if (delegats[getItemViewType(position)]?.clicker != null) {
            delegats[getItemViewType(position)]?.clicker?.onClick(v, holder, position, item)
        } else {
            clicker?.onClick(v, holder, position, item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DelegatVH<T> {
        return delegats[viewType]!!.createVH(parent)
    }

    override fun getItemViewType(position: Int): Int {
        for (delegat in delegats) {
            if (delegat.value.isDelegatForItem(getItem(position))) {
                return delegat.key
            }
        }
        throw NullPointerException("No delegat for this item${getItem(position).toString()}")
    }

    override fun onBindViewHolder(holder: DelegatVH<T>, position: Int) {
        holder.onBind(getItem(position), position)
    }

    override fun onViewAttachedToWindow(holder: DelegatVH<T>) {
        super.onViewAttachedToWindow(holder)
        holder.setClicker(this)
    }

    override fun onViewDetachedFromWindow(holder: DelegatVH<T>) {
        super.onViewDetachedFromWindow(holder)
        holder.setClicker(this)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    fun getItem(position: Int): T {
        return data[position]
    }

    fun addItem(item: T) {
        data.add(item)
    }

    fun addItem(item: T, position: Int) {
        data.add(position, item)
    }

    fun addItems(items: List<T>) {
        data.addAll(items)
    }

    fun clear() {
        data.clear()
    }

    fun getItems(): ArrayList<T> {
        return ArrayList(data)
    }

    fun replace(newData: List<T>, areItemsTheSame: (old: T, new: T) -> Boolean, areContentsTheSame: ((old: T, new: T) -> Boolean) = { o, n -> o == n }) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = data.size

            override fun getNewListSize(): Int = newData.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = data[oldItemPosition]
                val newItem = newData[newItemPosition]
                return areItemsTheSame(oldItem, newItem)
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = data[oldItemPosition]
                val newItem = newData[newItemPosition]
                return areContentsTheSame(oldItem, newItem)
            }
        }, false)
        data.clear()
        data.addAll(newData)
        result.dispatchUpdatesTo(this)
    }

}


