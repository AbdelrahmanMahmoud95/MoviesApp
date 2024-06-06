package com.abdelrahman.paymob.base.presentation.ui_handlers

interface HandlerRequest : Handlers {
    fun startRequest()
    fun endRequest(errorMessage: String? = null)
}