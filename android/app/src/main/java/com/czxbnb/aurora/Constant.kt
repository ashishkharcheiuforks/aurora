package com.czxbnb.aurora

/**
 * Base URL of the backend server
 */
const val BASE_URL: String = "https://68.183.183.235:3000/"

/**
 * URL of news API
 */
const val NEWS_URL: String = "https://newsapi.org/"

/**
 * Error attribute tag in the response body
 * For example, an API returns {status: 400, message: "Error information"}
 * The tag that contains error information is "message"
 */
const val ERROR_TAG: String = "message"
