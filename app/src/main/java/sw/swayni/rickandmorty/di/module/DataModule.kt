package sw.swayni.rickandmorty.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import sw.swayni.rickandmorty.BuildConfig
import sw.swayni.rickandmorty.data.Api
import sw.swayni.rickandmorty.data.local.ILocalDataSource
import sw.swayni.rickandmorty.data.local.LocalDataSource
import sw.swayni.rickandmorty.data.realm_model.FavoriteCharacterModel
import sw.swayni.rickandmorty.data.remote.IRemoteDataSource
import sw.swayni.rickandmorty.data.remote.RemoteDataSource
import sw.swayni.rickandmorty.data.repository.IRepository
import sw.swayni.rickandmorty.data.repository.Repository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun okHttp() : OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun retrofit(okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder().client(okHttpClient).addCallAdapterFactory(
        RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).baseUrl(BuildConfig.BASE_API_STRING)
        .build()

    @Singleton
    @Provides
    fun api(retrofit: Retrofit) : Api = retrofit.create(Api::class.java)

    @Singleton
    @Provides
    fun realmConfiguration() : RealmConfiguration = RealmConfiguration.Builder(schema = setOf(
        FavoriteCharacterModel::class)).name(BuildConfig.DATABASE_NAME).compactOnLaunch().schemaVersion(1).build()

    @Singleton
    @Provides
    fun realm(realmConfiguration: RealmConfiguration) : Realm = Realm.open(realmConfiguration)

    @Singleton
    @Provides
    fun remoteDataSourceProvider(api: Api) : IRemoteDataSource= RemoteDataSource(api)

    @Singleton
    @Provides
    fun localDataSourceProvider(realm : Realm) : ILocalDataSource = LocalDataSource(realm)

    @Singleton
    @Provides
    fun repositoryProvider(remoteDataSource: IRemoteDataSource, localDataSource: ILocalDataSource) : IRepository = Repository(remoteDataSource, localDataSource)
}