package com.example.finalinterngumi.ui.blank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalinterngumi.data.model.Detail
import com.example.finalinterngumi.data.model.Results
import com.example.finalinterngumi.databinding.ItemBinding


class FruitAdapter(private val onClick: (Results) -> Unit) :
    ListAdapter<Results, FruitAdapter.ViewHolder>(
        FruitDIff()
    ) {

    class ViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(
                    binding
                )
            }
        }

        fun bind(item: Results, onClick: ((Results) -> Unit)? = null) {
            binding.data = item

            binding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),onClick)
    }
}

class FruitDIff: DiffUtil.ItemCallback<Results>() {// cung cấp thông tin về cách xác định phần
override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean { // cho máy biết 2 item khi nào giống
    return oldItem == newItem // dung
}

    override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean { // cho biết item khi nào cùng nội dung
        return oldItem == newItem
    }

}
