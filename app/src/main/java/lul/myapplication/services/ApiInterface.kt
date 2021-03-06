package lul.myapplication.services

import lul.myapplication.models.FilmeDetalhes
import lul.myapplication.models.FilmeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("movie/popular")
    suspend fun getListaFilme(): Response<FilmeResponse>

    @GET("movie/{movie_id}")
    suspend fun getDetalhes(@Path("movie_id") id: Int): Response<FilmeDetalhes>

    @GET("search/movie")
    suspend fun getPesquisaFilme(@Query("query") keyword : String): Response<FilmeResponse>

}