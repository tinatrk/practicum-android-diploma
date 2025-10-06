package ru.practicum.android.diploma.di

import androidx.room.Room
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.converter.VacancyConverter
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.network.AuthInterceptor
import ru.practicum.android.diploma.data.network.DiplomaApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.util.NetworkInfoProvider
import ru.practicum.android.diploma.util.ResourceProvider

val dataModule = module {

    single {
        AuthInterceptor()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .build()
    }

    single<DiplomaApi> {
        Retrofit.Builder()
            .baseUrl("https://practicum-diploma-8bc38133faba.herokuapp.com/")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DiplomaApi::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(
            diplomaApi = get(),
            networkInfoProvider = get()
        )
    }

    factory {
        Gson()
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database")
            .build()
    }

    single {
        NetworkInfoProvider(
            context = androidContext()
        )
    }

    single {
        VacancyConverter(
            gson = get()
        )
    }

    single {
        ResourceProvider(
            context = androidContext()
        )
    }
}
