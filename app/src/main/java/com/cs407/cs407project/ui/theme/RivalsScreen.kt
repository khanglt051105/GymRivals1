package com.cs407.cs407project.ui.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RivalsScreen() {
    val headerGradient = Brush.horizontalGradient(listOf(Color(0xFF0EA5E9), Color(0xFF7C3AED)))
    val categories = listOf(
        ChallengeCategory.PushUps,
        ChallengeCategory.PullUps,
        ChallengeCategory.DistanceRan,
        ChallengeCategory.GymTime
    )

    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    val selected = categories[selectedIndex]
    val data = remember(selected) { sampleChallengeFor(selected) }

    Surface(color = Color(0xFFF6F7FB)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            // Top gradient header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerGradient)
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Column {
                    Text("GymRivals", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Text("Track. Compete. Dominate.", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                item { Spacer(Modifier.height(14.dp)) }

                // Section title
                item {
                    Text("Leaderboards", fontWeight = FontWeight.SemiBold, color = Color(0xFF111827))
                    Spacer(Modifier.height(10.dp))
                }

                // ---- Category switching via TabRow ----
                item {
                    TabRow(
                        selectedTabIndex = selectedIndex,
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF2563EB),
                        divider = {}
                    ) {
                        categories.forEachIndexed { i, c ->
                            Tab(
                                selected = selectedIndex == i,
                                onClick = { selectedIndex = i },
                                selectedContentColor = Color(0xFF2563EB),
                                unselectedContentColor = Color(0xFF6B7280),
                                text = {
                                    Text(
                                        text = c.label,
                                        fontSize = 13.sp,
                                        fontWeight = if (selectedIndex == i) FontWeight.SemiBold else FontWeight.Normal,
                                        maxLines = 1
                                    )
                                }
                            )
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                }

                // Decorative scrubber to match mock (optional)
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(18.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(8.dp)
                                .clip(RoundedCornerShape(999.dp))
                                .background(Color(0xFFE5E7EB))
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.35f)
                                .height(8.dp)
                                .clip(RoundedCornerShape(999.dp))
                                .background(Color(0xFF9CA3AF))
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                }

                // Highlight card for the selected challenge
                item {
                    ChallengeHighlightCard(data)
                    Spacer(Modifier.height(12.dp))
                }

                // Rankings list
                item {
                    RankingsCard(
                        you = data.you,
                        entries = data.leaderboard
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

/* ---------------------------- UI Pieces ---------------------------- */

@Composable
private fun ChallengeHighlightCard(data: ChallengeData) {
    val gradient = Brush.horizontalGradient(listOf(Color(0xFFFF7A18), Color(0xFFFFB45A)))
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(16.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        data.title,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text("🏆", fontSize = 18.sp)
                }
                Spacer(Modifier.height(4.dp))
                Text(data.subtitle, color = Color.White.copy(alpha = 0.95f), fontSize = 12.sp)

                Spacer(Modifier.height(16.dp))
                Row {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Your Rank", color = Color.White.copy(alpha = 0.85f), fontSize = 12.sp)
                        Text("#${data.yourRank} of ${data.totalParticipants}", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(1f)) {
                        Text("Ends in", color = Color.White.copy(alpha = 0.85f), fontSize = 12.sp)
                        Text(data.endsIn, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun RankingsCard(you: RivalEntry, entries: List<RivalEntry>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Rankings", fontWeight = FontWeight.SemiBold, color = Color(0xFF111827))
            Spacer(Modifier.height(10.dp))

            val sorted = entries.sortedByDescending { it.points }
            sorted.forEachIndexed { i, r ->
                if (i > 0) Divider(Modifier.padding(vertical = 2.dp), thickness = 1.dp, color = Color(0xFFE8ECF3))
                RivalRow(
                    rank = i + 1,
                    entry = r,
                    highlight = r.id == you.id
                )
            }
        }
    }
}

@Composable
private fun RivalRow(rank: Int, entry: RivalEntry, highlight: Boolean) {
    val bg = if (highlight) Color(0xFFEFF6FF) else Color.White
    val border = if (highlight) Color(0xFFBFDBFE) else Color(0xFFE8ECF3)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .border(1.dp, border, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Rank medal/emoji
        val crown = when (rank) {
            1 -> "👑"
            2 -> "🥈"
            3 -> "🥉"
            else -> "🏃"
        }
        Text(crown, fontSize = 16.sp)
        Spacer(Modifier.width(10.dp))

        // Avatar initials
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(Color(0xFFEDE9FE)),
            contentAlignment = Alignment.Center
        ) {
            Text(entry.initials, color = Color(0xFF7C3AED), fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
        Spacer(Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(entry.name, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis, color = Color(0xFF111827))
            val subtitle = when (entry.metricType) {
                MetricType.Reps -> "${entry.points} reps"
                MetricType.Miles -> "${"%.2f".format(entry.points / 100.0)} mi"
                MetricType.Minutes -> "${entry.points} min"
                MetricType.Points -> "${entry.points} pts"
            }
            Text(subtitle, fontSize = 12.sp, color = Color(0xFF6B7280))
        }

        Text(
            when (entry.metricType) {
                MetricType.Reps -> "${entry.points} reps"
                MetricType.Miles -> "${"%.2f".format(entry.points / 100.0)} mi"
                MetricType.Minutes -> "${entry.points} min"
                MetricType.Points -> "${entry.points} pts"
            },
            color = Color(0xFF2563EB),
            fontWeight = FontWeight.SemiBold
        )
    }
}

/* ---------------------------- Data models ---------------------------- */

private enum class MetricType { Points, Reps, Miles, Minutes }

private data class RivalEntry(
    val id: String,
    val initials: String,
    val name: String,
    val points: Int,          // for miles we store *100 (e.g., 425 -> 4.25 mi)
    val metricType: MetricType
)

private sealed class ChallengeCategory(val label: String) {
    object PushUps : ChallengeCategory("Push-up Challenge")
    object PullUps : ChallengeCategory("Pull-up Challenge")
    object DistanceRan : ChallengeCategory("Distance Runners")
    object GymTime : ChallengeCategory("Time in Gym")
}

private data class ChallengeData(
    val title: String,
    val subtitle: String,
    val yourRank: Int,
    val totalParticipants: Int,
    val endsIn: String,
    val you: RivalEntry,
    val leaderboard: List<RivalEntry>
)

/* ---------------------------- Sample data ---------------------------- */

private fun sampleChallengeFor(cat: ChallengeCategory): ChallengeData = when (cat) {
    ChallengeCategory.PushUps -> ChallengeData(
        title = "Push-up Challenge",
        subtitle = "Most push-ups in a week wins!",
        yourRank = 2,
        totalParticipants = 8,
        endsIn = "6 days",
        you = RivalEntry("you", "AJ", "You (Alex)", 425, MetricType.Reps),
        leaderboard = listOf(
            RivalEntry("1", "SC", "Sarah Chen", 450, MetricType.Reps),
            RivalEntry("you", "AJ", "You (Alex)", 425, MetricType.Reps),
            RivalEntry("3", "MT", "Mike Torres", 380, MetricType.Reps),
            RivalEntry("4", "EM", "Emma Nguyen", 365, MetricType.Reps),
            RivalEntry("5", "RB", "Riley Brooks", 310, MetricType.Reps)
        )
    )
    ChallengeCategory.PullUps -> ChallengeData(
        title = "Pull-up Challenge",
        subtitle = "Max total pull-ups this week.",
        yourRank = 3,
        totalParticipants = 8,
        endsIn = "4 days",
        you = RivalEntry("you", "AJ", "You (Alex)", 72, MetricType.Reps),
        leaderboard = listOf(
            RivalEntry("1", "SC", "Sarah Chen", 88, MetricType.Reps),
            RivalEntry("2", "MT", "Mike Torres", 81, MetricType.Reps),
            RivalEntry("you", "AJ", "You (Alex)", 72, MetricType.Reps),
            RivalEntry("4", "EM", "Emma Nguyen", 60, MetricType.Reps),
            RivalEntry("5", "RB", "Riley Brooks", 44, MetricType.Reps)
        )
    )
    ChallengeCategory.DistanceRan -> ChallengeData(
        title = "Distance Runners",
        subtitle = "Total miles this week.",
        yourRank = 2,
        totalParticipants = 12,
        endsIn = "2 days",
        you = RivalEntry("you", "AJ", "You (Alex)", 425, MetricType.Miles), // 4.25 mi
        leaderboard = listOf(
            RivalEntry("1", "SC", "Sarah Chen", 530, MetricType.Miles), // 5.30 mi
            RivalEntry("you", "AJ", "You (Alex)", 425, MetricType.Miles),
            RivalEntry("3", "MT", "Mike Torres", 380, MetricType.Miles),
            RivalEntry("4", "EM", "Emma Nguyen", 365, MetricType.Miles),
            RivalEntry("5", "RB", "Riley Brooks", 310, MetricType.Miles)
        )
    )
    ChallengeCategory.GymTime -> ChallengeData(
        title = "Time in the Gym",
        subtitle = "Total minutes spent working out this week.",
        yourRank = 4,
        totalParticipants = 10,
        endsIn = "5 days",
        you = RivalEntry("you", "AJ", "You (Alex)", 255, MetricType.Minutes),
        leaderboard = listOf(
            RivalEntry("1", "SC", "Sarah Chen", 420, MetricType.Minutes),
            RivalEntry("2", "MT", "Mike Torres", 360, MetricType.Minutes),
            RivalEntry("3", "EM", "Emma Nguyen", 300, MetricType.Minutes),
            RivalEntry("you", "AJ", "You (Alex)", 255, MetricType.Minutes),
            RivalEntry("5", "RB", "Riley Brooks", 210, MetricType.Minutes)
        )
    )
}
