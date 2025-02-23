package sw.swayni.rickandmorty.data.remote

import kotlinx.coroutines.flow.Flow
import sw.swayni.rickandmorty.core.ResultData
import sw.swayni.rickandmorty.data.model.AllCharactersModel
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.data.model.Episode

interface IRemoteDataSource {
    suspend fun getAllCharacters() : Flow<ResultData<AllCharactersModel>>
    suspend fun getCharacterInfo(id:Int) : Flow<ResultData<Character>>
    suspend fun getEpisodeList(ids:String) : Flow<ResultData<List<Episode>>>
}