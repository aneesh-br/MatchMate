package com.example.matchmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.matchmate.data.AppDatabase
import com.example.matchmate.data.MatchDatabase
import com.example.matchmate.network.RetrofitInstance
import com.example.matchmate.repository.UserRepositoryImpl
import com.example.matchmate.ui.screens.MatchListScreen
import com.example.matchmate.ui.theme.MatchMateTheme
import com.example.matchmate.viewmodel.MainViewModel
import com.example.matchmate.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = MatchDatabase.getDatabase(this)
        val dao = database.matchProfileDao()
        val apiService = RetrofitInstance.api
        val repository = UserRepositoryImpl(apiService, dao)
        val factory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setContent {
            MatchMateTheme {
                MatchListScreen(viewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MatchMateTheme {
        Greeting("Android")
    }
}