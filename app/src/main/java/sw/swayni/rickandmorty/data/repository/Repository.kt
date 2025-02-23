package sw.swayni.rickandmorty.data.repository

import kotlinx.coroutines.flow.Flow
import sw.swayni.rickandmorty.core.ResultData
import sw.swayni.rickandmorty.data.local.ILocalDataSource
import sw.swayni.rickandmorty.data.model.AllCharactersModel
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.data.model.Episode
import sw.swayni.rickandmorty.data.remote.IRemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(private val remoteDataSource: IRemoteDataSource, private val localDataSource: ILocalDataSource): IRepository {

    override suspend fun getAllCharacters(): Flow<ResultData<AllCharactersModel>> = remoteDataSource.getAllCharacters()

    override suspend fun getCharacter(id: Int): Flow<ResultData<Character>> = remoteDataSource.getCharacterInfo(id)

    override suspend fun getEpisodeList(ids: String): Flow<ResultData<List<Episode>>> = remoteDataSource.getEpisodeList(ids)

    override suspend fun getFavoriteCharacters(): Flow<ResultData<List<Character>>> = localDataSource.getFavoriteCharacters()

    override suspend fun getFavoriteCharacterWithId(id: Int): Flow<ResultData<Boolean>> = localDataSource.getFavoriteCharacterWithId(id)

    override suspend fun addFavoriteCharacter(character: Character): Flow<ResultData<Boolean>> = localDataSource.addFavoriteCharacter(character)

    override suspend fun removeFavoriteCharacter(character: Character): Flow<ResultData<Boolean>> = localDataSource.removeFavoriteCharacter(character)

}