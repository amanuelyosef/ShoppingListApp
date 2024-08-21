package com.example.shoppinglistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglistapp.ui.theme.ShoppingListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation(){
    val navController= rememberNavController()
    val viewModel:LocationViewModel= viewModel()
    val context= LocalContext.current
    val locationUtils=LocationUtils(context)

    NavHost(navController = navController, startDestination = "shoppinglist") {
        composable("shoppinglist"){
            ShoppingListApp(
                locationUtils = locationUtils,
                viewModel = viewModel,
                navController = navController,
                context = context,
                address = viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address"
            )
        }

        dialog("locationscreen"){

            viewModel.location.value?.let{locationData->
                LocationSelectionScreen(location = locationData) {
                    viewModel.fetchAddress("${it.latitude},${it.longitude}")
                }
            }

        }
    }
}



