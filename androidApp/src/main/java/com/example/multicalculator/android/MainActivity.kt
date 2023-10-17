package com.example.multicalculator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DefaultPreview()
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        CalcView()
    }
}

@Composable
fun CalcView() {
    val displayText = remember { mutableStateOf("0") }

    //state variable
    val leftNumber = rememberSaveable { mutableStateOf(0) }
    val rightNumber = rememberSaveable { mutableStateOf(0) }
    val operation = rememberSaveable { mutableStateOf("") }
    val complete = rememberSaveable { mutableStateOf(false) }

    // Check if complete is true and operation is not an empty string
    if (complete.value && operation.value.isNotEmpty()) {
        var answer = 0

        when (operation.value) {
            "+" -> answer = leftNumber.value + rightNumber.value
            "-" -> answer = leftNumber.value - rightNumber.value
            "*" -> answer = leftNumber.value * rightNumber.value
            "/" -> if (rightNumber.value != 0) {
                answer = leftNumber.value / rightNumber.value
            } else {
                displayText.value = "Error: Division by zero"
                return
            }
        }

        displayText.value = answer.toString()
    }
    else if (operation.value.isNotEmpty() && !complete.value) {
        displayText.value = rightNumber.value.toString()
    }
    else {
        displayText.value = leftNumber.value.toString()
    }

    // Three empty functions
    fun numberPress(btnNum: Int) {
        if (complete.value) {
            leftNumber.value = 0
            rightNumber.value = 0
            operation.value = ""
            complete.value = false
        }
        else if (operation.value.isNotBlank() && !complete.value) {
            rightNumber.value = rightNumber.value * 10 + btnNum
        }
        else if (operation.value.isBlank() && !complete.value) {
            leftNumber.value = leftNumber.value * 10 + btnNum
        }
    }

    fun operationPress(op: String) {
        if (!complete.value) {
            operation.value = op
        }
    }

    fun equalsPress() {
        complete.value = true
    }

    Column(modifier = Modifier.background(Color.LightGray))
    {
        Row {
            CalcDisplay(displayText)
        }
        Row {
            Column {
                for (i in 7 downTo 1 step 3) {
                    CalcRow(onPress = { number -> numberPress(number) },startNum = i, 3)
                }
                Row {
                    CalcNumericButton(0, onPress = { number -> numberPress(number) })
                    CalcEqualsButton(onPress = { equalsPress() })
                }
            }
            Column {
                CalcOperationButton("+", onPress = { op -> operationPress(op) })
                CalcOperationButton("-", onPress = { op -> operationPress(op) })
                CalcOperationButton("*", onPress = { op -> operationPress(op) })
                CalcOperationButton("/", onPress = { op -> operationPress(op) })
            }
        }

    }
}

@Composable
fun CalcRow(onPress: (number: Int) -> Unit, startNum: Int, numButtons: Int) {
    val endNum = startNum + numButtons
    Row(modifier = Modifier.padding(0.dp)) {
        for (i in startNum until endNum) {
            CalcNumericButton(i, onPress)
        }
    }
}

@Composable
fun CalcDisplay(display: MutableState<String>) {
    Text(
        text = display.value,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(5.dp),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CalcNumericButton(number: Int, onPress: (number: Int) -> Unit) {
    Button(
        onClick = { onPress(number) },
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = number.toString())
    }
}

@Composable
fun CalcOperationButton(operation: String, onPress: (operation: String) -> Unit) {
    Button(onClick = {onPress(operation)}, modifier = Modifier.padding(4.dp)) {
        Text(text = operation)
    }
}

@Composable
fun CalcEqualsButton(onPress: () -> Unit) {
    Button(
        onClick = { onPress() },
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = "=")
    }
}
