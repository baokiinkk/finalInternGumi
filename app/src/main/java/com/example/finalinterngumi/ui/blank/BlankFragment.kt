package com.example.finalinterngumi.ui.blank

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.databinding.adapters.SearchViewBindingAdapter
import com.example.finalinterngumi.R
import com.example.finalinterngumi.databinding.FragmentBlankBinding
import com.example.finalinterngumi.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BlankFragment : BaseFragment<FragmentBlankBinding>() {
    val viewmodel: BlankViewmodel by viewModel()    
    override fun getLayoutRes(): Int = R.layout.fragment_blank
    override fun onCreateViews() {
        Log.d("quocbao","oncreateViews")
        baseBinding.viewmodel = viewmodel
        baseBinding.txt.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewmodel.onClick(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewmodel.onClick(it) }
                return false
            }
        })
    }
}