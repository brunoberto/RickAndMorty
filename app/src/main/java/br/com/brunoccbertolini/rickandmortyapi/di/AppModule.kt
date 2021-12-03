package br.com.brunoccbertolini.rickandmortyapi.di

import br.com.brunoccbertolini.rickandmortyapi.RickandMortyApplication
import br.com.brunoccbertolini.rickandmortyapi.data.api.RickAndMortyAPI
import br.com.brunoccbertolini.rickandmortyapi.repository.CharactersRepository
import br.com.brunoccbertolini.rickandmortyapi.repository.DefaultCharactersRepository
import br.com.brunoccbertolini.rickandmortyapi.util.Constants
import br.com.brunoccbertolini.rickandmortyapi.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCharacterApi(): RickAndMortyAPI =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()

    @Singleton
    @Provides
    fun provideCharactersRepository (api: RickAndMortyAPI): CharactersRepository = DefaultCharactersRepository(api)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

}