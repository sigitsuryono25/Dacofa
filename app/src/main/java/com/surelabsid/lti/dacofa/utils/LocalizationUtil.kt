package com.surelabsid.lti.dacofa.utils

import android.content.Context
import java.util.*

object LocalizationUtil {
    fun applyLang(context: Context, locale: Locale): Context {
        val config = context.resources.configuration
        Locale.setDefault(locale)
        config.setLocale(locale)
        context.createConfigurationContext(config)
        return context

    }
}