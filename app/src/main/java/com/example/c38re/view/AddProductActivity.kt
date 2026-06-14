package com.example.c38re.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.c38re.model.ProductModel
import com.example.c38re.repo.ProductRepoImpl
import com.example.c38re.view.ui.theme.C38reTheme
import com.example.c38re.viewmodel.ProductViewModel

class AddProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AddProductBody()
        }
    }
}

@Composable
fun AddProductBody() {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }

    val productViewModel = remember { ProductViewModel(ProductRepoImpl()) }

    var loading by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(25.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                placeholder = {
                    Text("Enter Product Name")
                },
                onValueChange = {
                    name = it
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = price,
                placeholder = {
                    Text("Enter Product price")
                },
                onValueChange = {
                    price = it
                }
            )

            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                placeholder = {
                    Text("Enter Product description")
                },
                onValueChange = {
                    description = it
                }
            )

            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = quantity,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                placeholder = {
                    Text("Enter Product quantity")
                },
                onValueChange = {
                    quantity = it
                }
            )

            Spacer(modifier = Modifier.height(15.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isActive,
                    onCheckedChange = {
                        isActive = it
                    }
                )
                Text("Is Active")
            }

            Spacer(modifier = Modifier.height(15.dp))

            ElevatedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    loading = true
                    val model = ProductModel(
                        productId = "",
                        productName = name,
                        price = price.toDouble(),
                        description = description,
                        quantity = quantity.toInt(),
                        isActive = isActive
                    )
                    productViewModel.addProduct(model) { success, messsage ->
                        if (success) {
                            loading = false
                        } else {
                            loading = false
                        }
                    }
                }) {
                if (loading) {
                    CircularProgressIndicator()
                } else {
                    Text("Add Product")
                }
            }
        }
    }
}

@Preview
@Composable
fun AddProductPreview() {
    AddProductBody()
}