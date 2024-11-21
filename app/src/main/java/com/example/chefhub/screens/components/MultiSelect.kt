package com.example.chefhub.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MultiSelect(
    items: List<String>,
    selectedItems: Set<String>,
    onSelectionChange: (Set<String>) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val newSelectedItems = if (item in selectedItems) {
                            selectedItems - item // Quitar si ya está seleccionado
                        } else {
                            selectedItems + item // Agregar si no está seleccionado
                        }
                        onSelectionChange(newSelectedItems)
                    }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = item in selectedItems,
                    onCheckedChange = {
                        val newSelectedItems = if (item in selectedItems) {
                            selectedItems - item
                        } else {
                            selectedItems + item
                        }
                        onSelectionChange(newSelectedItems)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = item)
            }
        }
    }
}
