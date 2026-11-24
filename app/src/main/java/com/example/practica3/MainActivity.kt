package com.example.practica3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AddLink
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.practica3.R
import com.example.practica3.ui.theme.Practica3Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            Practica3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "",
                            modifier = Modifier.fillMaxWidth().padding(32.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    private var keepSplash = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplash }
        lifecycleScope.launch {
            delay(3000)
            keepSplash = false
        }
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        showToast("Función onCreate()")
        enableEdgeToEdge()
        setContent {
            Practica3Theme {
                ViewContainer()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        showToast("Función onStart()")
    }

    private fun showToast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
        Log.i(TAG, message)
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, locale = "es-rMX")
@Composable
fun ViewContainer() {
    val intentContext = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.home_screen)) },
                colors = topAppBarColors(
                    containerColor = Color.Gray,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_btn_desc),
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { openWebScreen(intentContext) }) {
                        Icon(
                            imageVector = Icons.Default.AddLink,
                            contentDescription = "Marcar como favorito",
                            tint = Color.White
                        )
                    }

                    // Se quitó el corazón blanco aquí intermedio

                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:4494698350"))
                        intentContext.startActivity(intent)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Abrir teléfono",
                            tint = Color.White
                        )
                    }
                    DropDownMenu()
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.bottom_app_bar),
                    color = Color.White,
                    fontSize = 18.sp,
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Mostrar snackbar") },
                icon = { Icon(Icons.Filled.Image, contentDescription = "") },
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Snackbar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = Color.Cyan
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                var counter by rememberSaveable { mutableIntStateOf(0) }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                ) {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.android),
                            contentDescription = "ilustracion de android",
                            modifier = Modifier
                                .fillMaxSize()
                                .height(400.dp)
                        )
                        Row(modifier = Modifier.padding(top = 2.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_favorite_24),
                                contentDescription = "Like",
                                modifier = Modifier
                                    .clickable { counter++ }
                                    .padding(start = 12.dp)
                            )
                            Text(
                                text = counter.toString(),
                                fontSize = 20.sp,
                                color = Color.White,
                                modifier = Modifier.padding(start = 8.dp),
                            )
                        }
                        Text(
                            text = stringResource(R.string.materia),
                            fontSize = 24.sp,
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(R.string.nombre),
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(R.string.apellido),
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            item {
                                Image(
                                    modifier = Modifier
                                        .size(200.dp)
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    painter = painterResource(id = R.drawable.imagen1),
                                    contentDescription = "Mobile Apps"
                                )
                                Image(
                                    modifier = Modifier
                                        .size(200.dp)
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    painter = painterResource(id = R.drawable.imagen2),
                                    contentDescription = "Mobile Apps"
                                )
                                Image(
                                    modifier = Modifier
                                        .size(200.dp)
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    painter = painterResource(id = R.drawable.imagen3),
                                    contentDescription = "Mobile Apps"
                                )
                                Image(
                                    modifier = Modifier
                                        .size(200.dp)
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    painter = painterResource(id = R.drawable.imagen4),
                                    contentDescription = "Mobile Apps"
                                )
                                Image(
                                    modifier = Modifier
                                        .size(200.dp)
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    painter = painterResource(id = R.drawable.imagen5),
                                    contentDescription = "Mobile Apps"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun DropDownMenu() {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.options_menu),
                tint = Color.White
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Calendario") },
                onClick = {
                    expanded = false
                    val intent = Intent(Intent.ACTION_MAIN).apply {
                        addCategory(Intent.CATEGORY_APP_CALENDAR)
                    }
                    context.startActivity(intent)
                },
                leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) }
            )
            DropdownMenuItem(
                text = { Text("Alarma") },
                onClick = {
                    expanded = false
                    val intent = Intent(android.provider.AlarmClock.ACTION_SHOW_ALARMS)
                    context.startActivity(intent)
                },
                leadingIcon = { Icon(Icons.Default.Notifications, contentDescription = null) }
            )
            HorizontalDivider()
            DropdownMenuItem(
                text = { Text("Archivos y descargas") },
                onClick = {
                    expanded = false
                    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                        type = "*/*"
                        addCategory(Intent.CATEGORY_OPENABLE)
                    }
                    context.startActivity(intent)
                },
                leadingIcon = { Icon(Icons.Default.Folder, contentDescription = null) }
            )
        }
    }
}

fun openWebScreen(context: Context) {
    val url = Uri.parse("https://aguascalientes.tecnm.mx")
    val intent = Intent(Intent.ACTION_VIEW, url)
    context.startActivity(intent)
}
