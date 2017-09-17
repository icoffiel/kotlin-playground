package com.nerdery.kotlin.playground.nat.xbox.exceptions.domain

/**
 * @param status The HTTP status expressed as a string
 * @param title A short title for the error
 * @param detail A more detailed explanation of the error
 */
data class ErrorResponse(
        val status: String,
        val title: String,
        val detail: String
)