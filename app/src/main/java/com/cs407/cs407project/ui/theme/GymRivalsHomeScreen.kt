package com.cs407.cs407project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun GymRivalsHomeScreen(userName: String = "Alex") {
    val appGradient = Brush.horizontalGradient(
        listOf(Color(0xFF0EA5E9), Color(0xFF7C3AED))
    )

    Surface(color = Color(0xFFF6F7FB)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            // Top app bar area (gradient)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(appGradient)
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Tiny logo chip (emoji so no icon deps)
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) { Text("🏋️", fontSize = 16.sp) }

                        Spacer(Modifier.width(8.dp))
                        Text(
                            "GymRivals",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        "Track. Compete. Dominate.",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp
                    )
                }
            }

            // Scrollable content
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                // Welcome gradient card
                item { Spacer(Modifier.height(12.dp)) }
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color(0xFF6366F1), Color(0xFF8B5CF6))
                                    )
                                )
                                .padding(16.dp)
                        ) {
                            Column {
                                Text(
                                    "Welcome back, $userName! 💪",
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    "You’re on a 5 day streak. Keep it up!",
                                    color = Color.White.copy(alpha = 0.95f),
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }

                // Stats grid
                item { Spacer(Modifier.height(10.dp)) }
                item {
                    StatsGrid(
                        listOf(
                            StatCardData("🔥", "1,250", "Weekly Points", Color(0xFFFFEDD5)),
                            StatCardData("📅", "5", "Day Streak", Color(0xFFEFFDEE)),
                            StatCardData("🧩", "4", "Workouts", Color(0xFFEFF6FF)),
                            StatCardData("💓", "2,800", "Calories", Color(0xFFFFF1F2))
                        )
                    )
                }

                // Recent workouts
                item { Spacer(Modifier.height(10.dp)) }
                item {
                    SectionCard(title = "Recent Workouts") {
                        RecentWorkoutRow(
                            title = "Upper Body Strength",
                            subtitle = "Today • 8 exercises",
                            points = 300
                        )
                        HorizontalDivider(thickness = 1.dp, color = Color(0xFFE8ECF3))
                        RecentWorkoutRow(
                            title = "Morning Run",
                            subtitle = "Yesterday • 5.2 km",
                            points = 250
                        )
                        HorizontalDivider(thickness = 1.dp, color = Color(0xFFE8ECF3))
                        RecentWorkoutRow(
                            title = "Leg Day",
                            subtitle = "2 days ago • 6 exercises",
                            points = 350
                        )
                    }
                }

                item { Spacer(Modifier.height(18.dp)) }
            }
        }
    }
}

/* ---------------- Components (same visuals as before) ---------------- */

private data class StatCardData(
    val emoji: String,
    val value: String,
    val label: String,
    val badgeColor: Color
)

@Composable
private fun StatsGrid(items: List<StatCardData>) {
    Column {
        Row(Modifier.fillMaxWidth()) {
            StatCard(items[0], Modifier.weight(1f))
            Spacer(Modifier.width(12.dp))
            StatCard(items[1], Modifier.weight(1f))
        }
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth()) {
            StatCard(items[2], Modifier.weight(1f))
            Spacer(Modifier.width(12.dp))
            StatCard(items[3], Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatCard(item: StatCardData, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(item.badgeColor),
                contentAlignment = Alignment.Center
            ) { Text(item.emoji, fontSize = 18.sp) }

            Spacer(Modifier.height(8.dp))
            Text(item.value, fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF111827))
            Text(item.label, fontSize = 12.sp, color = Color(0xFF6B7280))
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color(0xFF111827))
            Spacer(Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
private fun RecentWorkoutRow(
    title: String,
    subtitle: String,
    points: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Medium, color = Color(0xFF111827))
            Text(subtitle, fontSize = 12.sp, color = Color(0xFF6B7280), maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        PointsPill(points)
    }
}

@Composable
private fun PointsPill(points: Int) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .border(1.dp, Color(0xFFDDE3ED), RoundedCornerShape(999.dp))
            .background(Color(0xFFF3F6FB))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("+$points pts", fontSize = 12.sp, color = Color(0xFF2563EB), fontWeight = FontWeight.SemiBold)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewGymRivalsHome() {
    MaterialTheme { GymRivalsHomeScreen() }
}
