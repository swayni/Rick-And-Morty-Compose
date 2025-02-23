package sw.swayni.rickandmorty.data

import retrofit2.http.GET
import retrofit2.http.Path
import sw.swayni.rickandmorty.data.model.AllCharactersModel
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.data.model.Episode

interface Api {

    @GET("character")
    suspend fun getAllCharacter() : AllCharactersModel

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id:Int): Character

    @GET("episode/{pathIds}")
    suspend fun getEpisodeInfo(@Path("pathIds") pathIds:String) : List<Episode>
}