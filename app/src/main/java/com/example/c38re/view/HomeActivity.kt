package com.example.c38re.view

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.c38re.model.ProductModel
import com.example.c38re.repo.ProductRepoImpl
import com.example.c38re.view.ui.theme.C38reTheme
import com.example.c38re.viewmodel.ProductViewModel

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeBody()
        }
    }
}

@Composable
fun HomeBody() {
    val context = LocalContext.current
    val activity = context as Activity

    val productViewModel = remember { ProductViewModel(ProductRepoImpl()) }

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }

    val allProducts = productViewModel
        .allProducts.observeAsState(initial = emptyList())

    val loading = productViewModel
        .loading.observeAsState(initial = false)

    val products = productViewModel.products.observeAsState(initial = null)


    LaunchedEffect(products.value) {
        productViewModel.getAllProduct()

        if (products.value != null) {
            name = products.value!!.productName
            price = products.value!!.price.toString()
        }

    }



    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val intent = Intent(
                    context,
                    AddProductActivity::class.java
                )
//                intent.putExtra("id","jdajkda")
                context.startActivity(intent)
            }) {
                Text("Add Product")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (showDialog) {
                item {
                    AlertDialog(
                        title = {
                            Text("Update Product")
                        },
                        onDismissRequest = {
                            showDialog = false
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                val model = ProductModel(
                                    productId = "",
                                    productName = name,
                                    price = price.toDouble(),
                                    description = description,
                                    quantity = quantity.toInt(),
                                    isActive = isActive
                                )
                                productViewModel.updateProduct(model){
                                    success,msg->
                                    if(success){

                                    }else{

                                    }
                                }
                            }) {
                                Text("Update")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDialog = false
                            }) {
                                Text("Cancel")
                            }
                        },
                        text = {
                            Column {
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

                            }
                        }
                    )
                }
            }
            if (loading.value) {
                item {
                    CircularProgressIndicator()
                }
            } else {
                items(allProducts.value!!.size) { index ->
                    val product = allProducts.value!![index]
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            product.productName, style = TextStyle(
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(product.price.toString())
                        Text(product.description)
                        Text(product.quantity.toString())
                        Checkbox(
                            checked = product.isActive,
                            onCheckedChange = {
                                product.isActive = it
                            }
                        )
                        Row {
                            TextButton(onClick = {
                                showDialog = true
                                productViewModel.getProductById(product.productId)

                            }) { Text("Edit") }

                            TextButton(onClick = {
                                productViewModel.deleteProduct(product.productId) { success, msg ->
                                    if (success) {

                                    } else {
                                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                                    }
                                }
                            }) { Text("Delete") }
                        }

                    }
                }
            }


        }
    }
}

@Preview
@Composable
fun HomePreview() {
    HomeBody()
}