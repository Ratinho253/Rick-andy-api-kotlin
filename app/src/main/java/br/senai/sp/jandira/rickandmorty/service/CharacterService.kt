package br.senai.sp.jandira.rickandmorty.service

import br.senai.sp.jandira.rickandmorty.model.characterList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    // https://rickandmortyapi.com/api/

    @GET("character")
    fun getCharacter(): Call<characterList>

    @GET("character/{id}")
    fun getSingleCharacterById ( @Path("id") id : Long) : Call<br.senai.sp.jandira.rickandmorty.model.Character>

    @GET("character/")
    fun getCharacterByPages(@Query("page") pageNumber : Int): Call<characterList>

    @GET("character/")
    fun  getCharacterByNameAndStatus(@Query("name") name : String, @Query("status") status : String  ): Call<characterList>

}