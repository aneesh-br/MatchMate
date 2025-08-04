package com.example.matchmate.model

data class MatchProfile(
    val user: User,
    val status: MatchStatus = MatchStatus.PENDING
)