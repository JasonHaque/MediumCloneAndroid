package com.example.mediumcloneandroid.ui.interests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InterestsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Interests Fragment"
    }
    val text: LiveData<String> = _text
}