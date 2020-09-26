package com.khoa.toeicvocabulary

import android.app.Application
import android.speech.tts.TextToSpeech
import com.khoa.toeicvocabulary.di.component.AppComponent
import com.khoa.toeicvocabulary.di.component.DaggerAppComponent
import java.util.*

class MyApplication : Application() {

    val textToSpeech: TextToSpeech by lazy {
        TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.UK
            }
        }
    }

    val appComponent: AppComponent by lazy {
        initComponent()
    }

    private fun initComponent(): AppComponent {
        return DaggerAppComponent.builder().application(this).build()
    }

    fun speak(text: String?) {
        text?.let {
            val startIndex = text.indexOf("(")
            var word = text
            if (startIndex >= 0) word = text.substring(0, startIndex)
            textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, "tts")
        }
    }

    override fun onTerminate() {
        textToSpeech.shutdown()
        super.onTerminate()
    }
}