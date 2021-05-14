package com.example.finalinterngumi.ui.blank

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalinterngumi.data.model.Detail
import com.example.finalinterngumi.data.model.Fruit
import com.example.finalinterngumi.data.model.Results
import com.example.finalinterngumi.data.repository.Repository
import kotlinx.coroutines.launch

class BlankViewmodel(val repository: Repository) :ViewModel(){
    val userList :MutableLiveData<MutableList<Results>?> = MutableLiveData(null)
    var text = ""

    fun onClick(v: String){
        viewModelScope.launch {
            repository.getData{
                Log.d("quocbao",it.data.results.toString())
               userList.postValue(it.data.results)
            }
        }
    }



}

