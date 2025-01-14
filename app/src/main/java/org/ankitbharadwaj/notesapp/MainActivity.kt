package org.ankitbharadwaj.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import org.ankitbharadwaj.notesapp.data.NoteDatabase
import org.ankitbharadwaj.notesapp.presentation.AddNoteScreen
import org.ankitbharadwaj.notesapp.presentation.NoteScreen
import org.ankitbharadwaj.notesapp.presentation.NoteState
import org.ankitbharadwaj.notesapp.presentation.NoteViewModel
import org.ankitbharadwaj.notesapp.ui.theme.NotesAppTheme

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(applicationContext, NoteDatabase::class.java, "notes.db").build()
    }

    private  val viewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                val state by viewModel.state.collectAsState()
                val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "NoteScreen", modifier = Modifier.padding(it)) {

                        composable("NotesScreen") {
                            NoteScreen(state = state, navController = navController, onEvent = viewModel::onEvent)
                        }
                        composable("AddNotesScreen") {
                            AddNoteScreen(state = state, navController = navController, onEvent = viewModel::onEvent)
                        }

                    }

                }
            }
        }
    }
}