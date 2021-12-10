package com.example.githubpullreqfetcher.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.githubpullreqfetcher.data.repository.PullReqRepo
import com.example.githubpullreqfetcher.utils.Resource
import kotlinx.coroutines.Dispatchers

class PullReqViewModel(private val mainRepository: PullReqRepo) : ViewModel() {

    private val _eventStream = MutableLiveData<PullReqViewModelInteraction>()
    val eventStream: LiveData<PullReqViewModelInteraction>
        get() = _eventStream

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun onRetryClicked() {
        _eventStream.value = PullReqViewModelInteraction.RetryEvent
    }

    sealed class PullReqViewModelInteraction {

        object RetryEvent : PullReqViewModelInteraction()

    }

}