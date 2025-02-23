package sw.swayni.rickandmorty.data.local

import kotlinx.coroutines.flow.Flow
import sw.swayni.rickandmorty.core.ResultData
import sw.swayni.rickandmorty.data.model.Character

interface ILocalDataSource {

    suspend fun getFavoriteCharacters(): Flow<ResultData<List<Character>>>
    suspend fun getFavoriteCharacterWithId(id: Int): Flow<ResultData<Boolean>>
    suspend fun addFavoriteCharacter(character: Character) : Flow<ResultData<Boolean>>
    suspend fun removeFavoriteCharacter(character: Character): Flow<ResultData<Boolean>>
}