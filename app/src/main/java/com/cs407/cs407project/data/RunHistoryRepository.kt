package com.cs407.cs407project.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class RunEntry(
    val timestampMs: Long,        // when the run finished
    val distanceMeters: Double,   // total distance
    val elapsedMillis: Long       // total time (moving time as tracked)
) {
    val miles: Double get() = distanceMeters / 1609.344
    val avgPaceSecPerMile: Int?
        get() {
            if (miles <= 0.0) return null
            val sec = elapsedMillis / 1000.0
            return (sec / miles).toInt().coerceAtLeast(0)
        }
}

object RunHistoryRepository {
    private val _runs = MutableStateFlow<List<RunEntry>>(emptyList())
    val runs: StateFlow<List<RunEntry>> = _runs

    fun addRun(entry: RunEntry) {
        _runs.value = _runs.value + entry
    }
}