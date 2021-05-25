package com.example.mediumcloneandroid.ui.my_story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyStoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Interests Fragment"
    }
    val text: LiveData<String> = _text
}