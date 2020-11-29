package com.example.mediumcloneandroid.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Create Story Fragment"
    }
    val text: LiveData<String> = _text
}