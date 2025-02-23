package sw.swayni.rickandmorty.data.repository


import kotlinx.coroutines.flow.Flow
import sw.swayni.rickandmorty.core.ResultData
import sw.swayni.rickandmorty.data.model.AllCharactersModel
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.data.model.Episode

interface IRepository {
    suspend fun getAllCharacters() : Flow<ResultData<AllCharactersModel>>
    suspend fun getCharacter(id:Int) : Flow<ResultData<Character>>
    suspend fun getEpisodeList(ids:String) : Flow<ResultData<List<Episode>>>

    suspend fun getFavoriteCharacters() : Flow<ResultData<List<Character>>>
    suspend fun getFavoriteCharacterWithId(id:Int) : Flow<ResultData<Boolean>>
    suspend fun addFavoriteCharacter(character: Character) : Flow<ResultData<Boolean>>
    suspend fun removeFavoriteCharacter(character: Character) : Flow<ResultData<Boolean>>
}