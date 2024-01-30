package com.example.reminderyou.ui.core.util.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reminderyou.ui.screen.addtask.AddCategory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryBottomSheet(
    categoryName: String,
    onCategoryNameChange: (String) -> Unit,
    onSaveCategoryClicked: () -> Unit,
    onAddCategoryDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            onAddCategoryDismiss()
            scope.launch {
                sheetState.hide()
            }
        },
        modifier = modifier,
        sheetState = sheetState
    ) {
        AddCategory(
            categoryName = categoryName,
            onCategoryNameChange = onCategoryNameChange,
            onSaveCategoryClicked = onSaveCategoryClicked,
            modifier = Modifier.padding(24.dp)
        )
    }
}