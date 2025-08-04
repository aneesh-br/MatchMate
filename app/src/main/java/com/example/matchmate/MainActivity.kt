package com.example.matchmate

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
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

        val database = MatchDatabase.getDatabase(applicationContext)
        val dao = database.matchProfileDao()
        val apiService = RetrofitInstance.api
        val repository = UserRepositoryImpl(apiService, dao)
        val factory = MainViewModelFactory(repository, applicationContext)

        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        //Network callback to update Accept and Decline actions if online
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                viewModel.updatePendingIfOnline()
            }
        })

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