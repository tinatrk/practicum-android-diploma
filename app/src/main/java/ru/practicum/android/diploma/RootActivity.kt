package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.practicum.android.diploma.ui.root.AppRoot
import ru.practicum.android.diploma.ui.theme.AppTheme
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.compose.KoinAndroidContext
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.ui.theme.AppTheme

class RootActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                KoinAndroidContext {
                    AppRoot()
                }
            }
        }

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.API_ACCESS_TOKEN)
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

}
