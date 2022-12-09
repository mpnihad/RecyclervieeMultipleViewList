package com.nihad.recyclerviewtypes.data.repository

import com.nihad.recyclerviewtypes.data.network.SafeApiCall
import com.nihad.recyclerviewtypes.data.network.Api
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Api
) : SafeApiCall {
    suspend fun getMovies() = safeApiCall { api.getMovies() }
    suspend fun getDirectors() = safeApiCall { api.getDirectors() }
}