package com.nihad.recyclerviewtypes.ui

import android.util.Log
import androidx.lifecycle.*
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

    //         var homeListItemsLiveData = MutableLiveData<Resource<List<HomeRecyclerViewItem>>>()
    lateinit var _movieLiveData: LiveData<Resource<List<HomeRecyclerViewItem.Movie>>>
    lateinit var _directorLiveData: LiveData<Resource<List<HomeRecyclerViewItem.Director>>>

    val homeListItemsMediatorLiveData = MediatorLiveData<Resource<List<HomeRecyclerViewItem>>>()


    init {
        getHomeListItems()
    }



    private fun combineDetails(
        moviesLivedata: LiveData<Resource<List<HomeRecyclerViewItem.Movie>>>?,
        directorLivedata: LiveData<Resource<List<HomeRecyclerViewItem.Director>>>?
    ): Resource<List<HomeRecyclerViewItem>> {

        val movies = moviesLivedata?.value
        val director = directorLivedata?.value
        if (movies == null || director == null) {
            return Resource.Loading
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
                return Resource.Success(homeItemList)
            } else {
                return Resource.Failure(
                    isNetworkError = true,
                    errorCode = 404,
                    errorBody = null
                )

            }

        }
    }


    private fun getHomeListItems() = viewModelScope.launch {
        homeListItemsMediatorLiveData.value = (Resource.Loading)


        val moviesDiffered = async {
            repository.getMovies()
        }

        val directorDiffered = async {
            repository.getDirectors()
        }


        //Usually get the live data from Repository / Other source
        _movieLiveData = MutableLiveData(moviesDiffered.await())
        _directorLiveData = MutableLiveData(directorDiffered.await())






        homeListItemsMediatorLiveData.addSource(_movieLiveData) { value ->
            homeListItemsMediatorLiveData.value = combineDetails(_movieLiveData, _directorLiveData)
        }
        homeListItemsMediatorLiveData.addSource(_directorLiveData) { value ->
            homeListItemsMediatorLiveData.value = combineDetails(_movieLiveData, _directorLiveData)
        }


    }
}