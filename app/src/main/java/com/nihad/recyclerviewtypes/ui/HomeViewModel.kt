package com.nihad.recyclerviewtypes.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nihad.recyclerviewtypes.data.network.Resource
import com.nihad.recyclerviewtypes.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

        private val _homeListItemsLiveData = MutableLiveData<Resource<List<HomeRecyclerViewItem>>>()
    private val _movieLiveData = MutableLiveData<Resource<List<HomeRecyclerViewItem.Movie>>>()
    private val _directorLiveData = MutableLiveData<Resource<List<HomeRecyclerViewItem.Director>>>()
    val homeListItemsLiveData: LiveData<Resource<List<HomeRecyclerViewItem>>>
        get() = _homeListItemsLiveData

    private var _mediatorLiveData = MediatorLiveData<Resource<MutableList<HomeRecyclerViewItem>>>()
    val mediatorLiveData: MediatorLiveData<Resource<MutableList<HomeRecyclerViewItem>>>
        get() = _mediatorLiveData

    init {
        getHomeListItems()

        _mediatorLiveData.addSource(
            _movieLiveData
        )
        { movies ->

            combineDetails(movies, _directorLiveData.value)



        }

        _mediatorLiveData.addSource(_directorLiveData) {director ->
            combineDetails(_movieLiveData.value, director)


        }
    }

    private fun combineDetails(
        movies: Resource<List<HomeRecyclerViewItem.Movie>>?,
        director: Resource<List<HomeRecyclerViewItem.Director>>?
    ) {

        if (movies == null || director == null) {
            return
        } else {

            val homeItemList = mutableListOf<HomeRecyclerViewItem>()

            if (movies is Resource.Success && director is Resource.Success) {
                homeItemList.add(HomeRecyclerViewItem.Title(1, "Recommended Movies") {
                    Log.d("Title Pressed", "Recommended movies")
                })
                homeItemList.addAll(movies.value)
                homeItemList.add(HomeRecyclerViewItem.Title(1, "Director List") {
                    Log.d("Title Pressed", "Director List")

                })
                homeItemList.addAll(director.value)
                _homeListItemsLiveData.postValue(Resource.Success(homeItemList))
            } else {
                _homeListItemsLiveData.postValue(
                    Resource.Failure(
                        isNetworkError = true,
                        errorCode = 404,
                        errorBody = null
                    )
                )
            }

        }
    }


    private fun getHomeListItems() = viewModelScope.launch {

        _homeListItemsLiveData.postValue(Resource.Loading)
        val moviesDiffered = async {
            repository.getMovies()
        }

        val directorDiffered = async {
            repository.getDirectors()
        }

        _movieLiveData.postValue(moviesDiffered.await())
        _directorLiveData.postValue(directorDiffered.await())


    }
}