package com.example.proyekuts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proyekuts.ui.navigation.NavItem
import com.example.proyekuts.ui.screens.biodata.BiodataScreen
import com.example.proyekuts.ui.screens.calculator.CalculatorScreen
import com.example.proyekuts.ui.screens.ContactScreen
import com.example.proyekuts.ui.screens.NewsScreen
import com.example.proyekuts.ui.screens.weather.WeatherScreen

@Composable
fun DashboardScreen() {
    val navController = rememberNavController()
    val navItems = listOf(
        NavItem.Biodata,
        NavItem.Contact,
        NavItem.Calculator,
        NavItem.Weather,
        NavItem.News
    )

    val darkBlueBase = Color(0xFF013674)
    val navbarColor = Color(0xFFFFFFFF)

    Scaffold(
        bottomBar = {
            Box {
                // Bottom Navigation Bar
                BottomAppBar(
                    containerColor = navbarColor,
                    tonalElevation = 8.dp,
                    modifier = Modifier.height(80.dp) // Tinggi navigation bar diperbesar
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    val itemsForBar = navItems.filter { it.route != NavItem.Calculator.route }

                    itemsForBar.forEachIndexed { index, screen ->
                        if (index == 2) { // Spacer untuk posisi FAB
                            Spacer(Modifier.weight(1f))
                        }

                        NavigationBarItem(
                            modifier = Modifier.weight(1f),
                            icon = {
                                Icon(
                                    screen.icon,
                                    contentDescription = screen.title,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = { Text(screen.title, maxLines = 1) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = darkBlueBase,
                                selectedTextColor = darkBlueBase,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }

                // Floating Action Button yang menimpa navigation bar
                FloatingActionButton(
                    onClick = {
                        navController.navigate(NavItem.Calculator.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    shape = CircleShape,
                    containerColor = darkBlueBase,
                    modifier = Modifier
                        .size(70.dp) // Ukuran FAB lebih besar
                        .align(Alignment.TopCenter)
                        .offset(y = (-25).dp) // Posisi FAB setengah di atas navigation bar
                ) {
                    Icon(
                        imageVector = NavItem.Calculator.icon,
                        contentDescription = NavItem.Calculator.title,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp) // Ukuran icon lebih besar
                    )
                }
            }
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Biodata.route,
        modifier = modifier
    ) {
        composable(NavItem.Biodata.route) { BiodataScreen() }
        composable(NavItem.Contact.route) { ContactScreen() }
        composable(NavItem.Calculator.route) { CalculatorScreen() }
        composable(NavItem.Weather.route) { WeatherScreen() }
        composable(NavItem.News.route) { NewsScreen() }
    }
}