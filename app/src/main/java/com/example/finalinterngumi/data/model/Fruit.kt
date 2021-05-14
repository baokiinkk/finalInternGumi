package com.example.finalinterngumi.data.model

import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.finalinterngumi.R
import com.example.finalinterngumi.ui.blank.FruitAdapter
import com.google.gson.annotations.SerializedName

data class Detail(val results:MutableList<Results>)
data class Results(val title:String)
data class Fruit(val data:Detail){
    companion object {
        @BindingAdapter("android:profileImage")
        @JvmStatic
        fun loadImage(view: ImageView, image: String?) {
            image?.let {
                view.load(it) {
                    size(700, 200)
                    placeholder(R.drawable.ic_launcher_background)
                }
            }
        }
        @BindingAdapter("android:adapter")
        @JvmStatic
        fun loadData(view: RecyclerView, data: MutableLiveData<MutableList<Results>?>) {
            data.value?.let {
                val adapter = FruitAdapter{
                }
                view.adapter = adapter
                view.layoutManager = LinearLayoutManager(view.context)
                adapter.submitList(data.value)
            }
        }
    }
}


