package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.converter.AddressConverter
import ru.practicum.android.diploma.data.converter.ContactsConverter
import ru.practicum.android.diploma.data.converter.EmployerConverter
import ru.practicum.android.diploma.data.converter.FilterAreaConverter
import ru.practicum.android.diploma.data.converter.SalaryConverter
import ru.practicum.android.diploma.data.converter.VacancyConverter
import ru.practicum.android.diploma.data.converter.filters.FilterAreaExtractor
import ru.practicum.android.diploma.data.converter.filters.FilterIndustryConverter
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.dao.VacancyDao
import ru.practicum.android.diploma.data.network.AuthInterceptor
import ru.practicum.android.diploma.data.network.DiplomaApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.util.NetworkInfoProvider
import ru.practicum.android.diploma.util.ResourceProvider

private const val BASE_URL_API = "https://practicum-diploma-8bc38133faba.herokuapp.com/"
private const val FILTER_SHARED_PREFERENCES_FILE = "shared_preferences_filter"
const val DI_GSON = "gson"
const val DI_GSON_WITH_NULL = "gson_with_null"

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
            .baseUrl(BASE_URL_API)
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

    factory(named(DI_GSON)) {
        Gson()
    }

    factory(named(DI_GSON_WITH_NULL)) {
        GsonBuilder().serializeNulls().create()
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database")
            .build()
    }

    single<VacancyDao> {
        val database = get<AppDatabase>()
        database.vacancyDao()
    }

    single {
        NetworkInfoProvider(
            context = androidContext()
        )
    }

    single {
        AddressConverter()
    }

    single {
        ContactsConverter(
            gson = get(named(DI_GSON))
        )
    }

    single {
        EmployerConverter()
    }

    single {
        FilterAreaConverter(
            gson = get(named(DI_GSON))
        )
    }

    single {
        SalaryConverter()
    }

    single {
        VacancyConverter(
            gson = get(named(DI_GSON)),
            salaryConverter = get(),
            addressConverter = get(),
            contactsConverter = get(),
            employerConverter = get(),
            filterAreaConverter = get()
        )
    }

    single {
        ResourceProvider(
            context = androidContext()
        )
    }

    single {
        androidApplication().getSharedPreferences(
            FILTER_SHARED_PREFERENCES_FILE,
            Context.MODE_PRIVATE
        )
    }

    single {
        FilterAreaExtractor(
            resourceProvider = get()
        )
    }

    single {
        FilterIndustryConverter(
            resourceProvider = get()
        )
    }
}
