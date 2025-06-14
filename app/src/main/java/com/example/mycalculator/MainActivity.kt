package com.example.mycalculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycalculator.ui.theme.MyCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    CalculatorScreen(Modifier.padding(paddingValues))
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<String?>(null) }
    var lastOperation by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    // Background Surface
    androidx.compose.material3.Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            TextField(
                value = num1,
                onValueChange = { num1 = it },
                label = { Text("Number 1") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = num2,
                onValueChange = { num2 = it },
                label = { Text("Number 2") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
            ) {
                Button(
                    onClick = {
                        result = calculateResult(num1, num2, "+", context)
                        lastOperation = "+"
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Add")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        result = calculateResult(num1, num2, "-", context)
                        lastOperation = "-"
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Sub")
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
            ) {
                Button(
                    onClick = {
                        result = calculateResult(num1, num2, "*", context)
                        lastOperation = "*"
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Mul")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        result = calculateResult(num1, num2, "/", context)
                        lastOperation = "/"
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Div")
                }
            }

            Button(
                onClick = {
                    num1 = ""
                    num2 = ""
                    result = null
                    lastOperation = null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Clear")
            }

            result?.let {
                Text(
                    text = "Result: $it",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            lastOperation?.let {
                Text(
                    text = "Last Operation: $it",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


fun calculateResult(num1Str: String, num2Str: String, operation: String, context: android.content.Context): String? {
    val num1 = num1Str.toDoubleOrNull()
    val num2 = num2Str.toDoubleOrNull()

    if (num1 == null || num2 == null) {
        Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT).show()
        return null
    }

    val result = when (operation) {
        "+" -> num1 + num2
        "-" -> num1 - num2
        "*" -> num1 * num2
        "/" -> {
            if (num2 == 0.0) {
                Toast.makeText(context, "Division by zero", Toast.LENGTH_SHORT).show()
                return null
            }
            num1 / num2
        }
        else -> 0.0
    }

    return String.format("%.2f", result)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyCalculatorTheme {
        CalculatorScreen()
    }
}

