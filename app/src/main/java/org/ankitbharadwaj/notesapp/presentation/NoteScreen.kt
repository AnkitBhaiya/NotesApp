package org.ankitbharadwaj.notesapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.ankitbharadwaj.notesapp.ui.theme.Pink80

@Composable
fun NoteScreen(
    state: NoteState,
    navController: NavController,
    onEvent: (NotesEvent) -> Unit
) {

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(Pink80)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Notes App", modifier = Modifier.weight(1f), fontWeight = FontWeight.SemiBold, color = Color.White)
                IconButton(onClick = {onEvent(NotesEvent.SortNotes)}) {
                Icon(imageVector = Icons.Rounded.Menu, contentDescription = null, tint = Pink80, modifier = Modifier.size(35.dp))
                }
            }
        }, floatingActionButton = {
            FloatingActionButton(
                containerColor = Pink80,
                onClick = {
                    state.title.value =""
                    state.content.value = ""
                    navController.navigate("AddNoteScreen")
                }
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null, tint = Color.White)
            }
        }
    ) {
        LazyColumn(contentPadding = it, modifier = Modifier.fillMaxSize().padding(8.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(state.notes.size){
                NoteItem(state = state,
                    index = it,
                    onEvent = onEvent)
            }
        }
    }

}

@Composable
fun NoteItem(state: NoteState, index: Int, onEvent: (NotesEvent) -> Unit) {

    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(Pink80)){

        Column(modifier = Modifier.weight(1f)) {
            Text(text = state.notes[index].title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = state.notes[index].content)

        }
        Icon(imageVector = Icons.Rounded.Delete, contentDescription = null, tint = Color.White, modifier = Modifier.size(35.dp))

    }

}
