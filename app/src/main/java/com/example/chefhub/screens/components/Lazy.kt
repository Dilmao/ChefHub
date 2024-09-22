package com.example.chefhub.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chefhub.ui.AppViewModel

@Composable
fun PersonalizedLazyColumn(
    list: MutableList<String>,  // COMENTARIO.
    listName: String,           // COMENTARIO.
    label: String,              // COMENTARIO.
    appViewModel: AppViewModel  // COMENTARIO.
) {
    // COMENTARIO.
    LazyColumn(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // COMENTARIO.
        items(list.size) { index ->
            SimpleTextField(
                value = list[index],
                onValueChange = { appViewModel.onMutableListChanged(it, index, list, listName) },
                label = "${index + 1}$label",
                required = false
            )
        }
    }
}