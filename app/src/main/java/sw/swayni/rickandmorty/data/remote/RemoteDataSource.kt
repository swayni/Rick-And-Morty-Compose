package sw.swayni.rickandmorty.data.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import sw.swayni.rickandmorty.core.ResultData
import sw.swayni.rickandmorty.data.Api
import sw.swayni.rickandmorty.data.model.AllCharactersModel
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.data.model.Episode
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: Api) : IRemoteDataSource {

    override suspend fun getAllCharacters(): Flow<ResultData<AllCharactersModel>> = flow {
        try {
            val response = api.getAllCharacter()
            emit(ResultData.Success(response))
        }catch (e : Exception){
            emit(ResultData.Error(e))
        }
    }

    override suspend fun getCharacterInfo(id: Int): Flow<ResultData<Character>> = flow{
        try {
            val response = api.getCharacter(id)
            emit(ResultData.Success(response))
        }catch (e : Exception){
            emit(ResultData.Error(e))
        }
    }

    override suspend fun getEpisodeList(ids: String): Flow<ResultData<List<Episode>>> = flow {
        try {
            val response = api.getEpisodeInfo(ids)
            emit(ResultData.Success(response))
        }catch (e : Exception){
            emit(ResultData.Error(e))
        }
    }
}