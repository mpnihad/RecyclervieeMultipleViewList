package com.nihad.recyclerviewtypes.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.nihad.recyclerviewtypes.data.network.Resource
import com.nihad.recyclerviewtypes.ui.adapter.HomeRecyclerViewAdapter
import com.nihad.recyclerviewtypes.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<HomeViewModel>()

    private val homeAdapter = HomeRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = homeAdapter
        }

        viewModel.homeListItemsMediatorLiveData.observe(this){ result ->
            when  (result){
                is Resource.Failure -> {

                    binding.progressBar.hide()
                }
                Resource.Loading -> {

                    binding.progressBar.show()
                }
                is Resource.Success -> {
                    binding.progressBar.hide()
                    homeAdapter.list = result.value
                }
            }
        }
    }
}