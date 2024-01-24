package com.pamela.flashcards.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pamela.flashcards.R
import com.pamela.flashcards.util.defaultTimeFormat
import java.time.LocalTime

@Composable
fun AlertText(text: String, color: Color) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = color,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TextFieldWithLabel(label: String, value: String, onChangeValue: (String) -> Unit) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelLarge
    )
    TextField(
        value = value,
        onValueChange = onChangeValue,
        shape = RoundedCornerShape(4.dp),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun TextAreaWithLabel(label: String, value: String, onChangeValue: (String) -> Unit) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelLarge
    )
    TextField(
        value = value,
        onValueChange = onChangeValue,
        shape = RoundedCornerShape(4.dp),
        minLines = 4,
        maxLines = 4,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun PopUpFieldWithLabel(label: String, value: String, onClick: () -> Unit) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelLarge
    )
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Text(
            text = value,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun SwitchWithTextLabel(
    label: String,
    value: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(1F)
        )
        Box(modifier = Modifier.width(120.dp), contentAlignment = Alignment.Center) {
            Switch(
                checked = value,
                onCheckedChange = onClick
            )
        }
    }
}

@Composable
fun IncrementerWithTextLabel(
    label: String,
    value: Int,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(1F)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.width(120.dp)
        ) {
            IconButton(
                onClick = { onChange(value - 1) },
                colors = IconButtonDefaults.filledIconButtonColors(),
                modifier = Modifier.weight(1F)
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = stringResource(id = R.string.decrease)
                )
            }
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .weight(1F),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value.toString(),
                    modifier = Modifier
                        .padding(8.dp)

                )
            }
            IconButton(
                onClick = { onChange(value + 1) },
                colors = IconButtonDefaults.filledIconButtonColors(),
                modifier = Modifier.weight(1F)
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowUp,
                    contentDescription = stringResource(id = R.string.increase)
                )
            }
        }
    }
}

@Composable
fun HourPickerWithLabel(
    label: String,
    value: Int,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween
) {
    var showHourPicker by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(1F)
        )
        Box(modifier = Modifier.width(120.dp), contentAlignment = Alignment.Center) {
            TextButton(
                onClick = { showHourPicker = true },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(
                    text = LocalTime.of(value, 0).format(defaultTimeFormat),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1F),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    if (showHourPicker) {
        Dialog(onDismissRequest = { showHourPicker = false }) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.select_hour),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxHeight(0.5F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(24) {
                            Divider()
                            StyledTextButton(
                                onClick = { onChange(it); showHourPicker = false },
                                text = LocalTime.of(it, 0).format(
                                    defaultTimeFormat
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}