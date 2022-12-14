package com.androidDev.dockital.screens.profileNav

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import com.androidDev.dockital.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.androidDev.dockital.MainActivity
import com.androidDev.dockital.navigations.NavigationItem
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


@Preview
@Composable
fun MainProfilePreview(){
    MainProfile(
        context = MainActivity().context,
        navController = rememberNavController(),
        dbConnect = FirebaseDatabase.getInstance(),
        localStorageRef = MainActivity().getSharedPreferences(
            " ",
            Context.MODE_PRIVATE
        ),
        dbStorageConnect = FirebaseStorage.getInstance()
    )
}

@Composable
fun MainProfile(context : Context , navController: NavController, dbConnect: FirebaseDatabase,  localStorageRef: SharedPreferences, dbStorageConnect: FirebaseStorage){
    var configurationDetails = LocalConfiguration.current
    val profileData = localStorageRef.all
    if(profileData.isEmpty()){
        localStorageRef.edit().clear().commit()
        navController.navigate(NavigationItem.Login.route)
    }
    else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(32, 15, 52)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val profileName = localStorageRef.getString(R.string.name.toString(), "Name Not Found")
            val profileEmail = localStorageRef.getString(R.string.userName.toString(), "Email Id Not Found")
            val upiId = localStorageRef.getString(R.string.upiId.toString(), "UPI Id Not Found")
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray,
                    )
                },
                backgroundColor = Color(32, 15, 52),
                elevation = 4.dp,
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("Home")
                    }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Go back",
                        )
                    }
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id =R.drawable.insan),
                    contentDescription = null,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(shape = CircleShape)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(weight = 3f, fill = false)
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            text = "$profileName",
                            style = TextStyle(
                                fontSize = 22.sp,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "$profileEmail",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White,
                                letterSpacing = (0.8).sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "$upiId",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White,
                                letterSpacing = (0.8).sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    IconButton(
                        modifier = Modifier
                            .weight(weight = 1f, fill = false),
                        onClick = {
                            Toast.makeText(context, "Edit Button", Toast.LENGTH_SHORT).show()
                        }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Edit Details",
                            tint = MaterialTheme.colors.primary
                        )
                    }

                }
            }

            val ctx = LocalContext.current
            val activity = (LocalContext.current as? Activity)
            val context = LocalContext.current
            var intent = context.packageManager
                .getLaunchIntentForPackage("io.metamask")
            var packageName = "org.walletconnect.samples";
            // val inten = context.packageManager.getLaunchIntentForPackage("com.application.zomato")
            if (intent == null) {
                intent = try {
                    // if play store installed, open play store, else open browser
                    Intent(Intent.ACTION_VIEW, Uri.parse("android-app://$packageName"))
                } catch (e: java.lang.Exception) {
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = true) {
//                        Toast
//                            .makeText(context, "Account", Toast.LENGTH_SHORT)
//                            .show()
                        /// MetaMask
                        if (intent != null) {
                            startActivity(context, intent, null)
                        }
                        else{
                            Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
                        }
                    }
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Account",
                    tint = MaterialTheme.colors.primary
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(weight = 3f, fill = false)
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            text = "Account",
                            color = Color.Gray,
                            style = TextStyle(
                                fontSize = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Manage your account",
                            style = TextStyle(
                                fontSize = 14.sp,
                                letterSpacing = (0.8).sp,
                                color = Color.Gray
                            )
                        )

                    }

                    Icon(
                        modifier = Modifier
                            .weight(weight = 1f, fill = false),
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = "Account",
                        tint = Color.Black.copy(alpha = 0.70f)
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = true) {
                        Toast
                            .makeText(context, "History", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "History",
                    tint = MaterialTheme.colors.primary
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(weight = 3f, fill = false)
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            text = "History",
                            color = Color.Gray,
                            style = TextStyle(
                                fontSize = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Activity History",
                            style = TextStyle(
                                fontSize = 14.sp,
                                letterSpacing = (0.8).sp,
                                color = Color.Gray
                            )
                        )

                    }

                    Icon(
                        modifier = Modifier
                            .weight(weight = 1f, fill = false),
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = "History",
                        tint = Color.Black.copy(alpha = 0.70f)
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = true) {
                        Toast
                            .makeText(context, "Addresses", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    imageVector = Icons.Outlined.Navigation,
                    contentDescription = "Addresses",
                    tint = MaterialTheme.colors.primary
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(weight = 3f, fill = false)
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            text = "Addresses",
                            color = Color.Gray,
                            style = TextStyle(
                                fontSize = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Your saved addresses",
                            style = TextStyle(
                                fontSize = 14.sp,
                                letterSpacing = (0.8).sp,
                                color = Color.Gray
                            )
                        )

                    }

                    Icon(
                        modifier = Modifier
                            .weight(weight = 1f, fill = false),
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = "Addresses",
                        tint = Color.Black.copy(alpha = 0.70f)
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = true) {
                        Toast
                            .makeText(context, "Settings", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colors.primary
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(weight = 3f, fill = false)
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            text = "Settings",
                            color = Color.Gray,
                            style = TextStyle(
                                fontSize = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "App notification settings",
                            style = TextStyle(
                                fontSize = 14.sp,
                                letterSpacing = (0.8).sp,
                                color = Color.Gray
                            )
                        )

                    }

                    Icon(
                        modifier = Modifier
                            .weight(weight = 1f, fill = false),
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = "Settings",
                        tint = Color.Black.copy(alpha = 0.70f)
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = true) {
                        Toast
                            .makeText(context, "Help & Feedback", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    imageVector = Icons.Outlined.Help,
                    contentDescription = "Help & Feedback",
                    tint = MaterialTheme.colors.primary
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(weight = 3f, fill = false)
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            text = "Help & Feedback",
                            color = Color.Gray,
                            style = TextStyle(
                                fontSize = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "FAQs and customer support",
                            style = TextStyle(
                                fontSize = 14.sp,
                                letterSpacing = (0.8).sp,
                                color = Color.Gray
                            )
                        )

                    }

                    Icon(
                        modifier = Modifier
                            .weight(weight = 1f, fill = false),
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = "Help & Feedback",
                        tint = Color.Black.copy(alpha = 0.70f)
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = true) {
                        Toast
                            .makeText(context, "Liked NFTs", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Liked NFTs",
                    tint = MaterialTheme.colors.primary
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(weight = 3f, fill = false)
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            text = "Liked NFTs",
                            color = Color.Gray,
                            style = TextStyle(
                                fontSize = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Items you saved",
                            style = TextStyle(
                                fontSize = 14.sp,
                                letterSpacing = (0.8).sp,
                                color = Color.Gray
                            )
                        )

                    }

                    Icon(
                        modifier = Modifier
                            .weight(weight = 1f, fill = false),
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = "Liked NFTs",
                        tint = Color.Black.copy(alpha = 0.70f)
                    )
                }

            }
        }
    }
}