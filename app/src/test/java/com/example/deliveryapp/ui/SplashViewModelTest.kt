package com.example.deliveryapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.deliveryapp.data.local.entities.User
import com.example.deliveryapp.data.local.repository.UserRepository
import com.example.deliveryapp.data.remote.NetworkState
import com.example.deliveryapp.ui.splash.SplashViewModel
import com.example.deliveryapp.utils.DispatcherProvider
import com.nhaarman.mockito_kotlin.whenever
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.regex.Pattern

class SplashViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var userRepository: UserRepository

    private lateinit var splashViewModel: SplashViewModel

    private val user = User().apply {
        name = "Ephraim Nartey"
        email = "narteyephraim@gmail.com"
        phone = "+233501384237"
        profilePicUrl = ""
    }

    private val testProvider = DispatcherProvider(
        IO = Dispatchers.Unconfined,
        Main = Dispatchers.Unconfined
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(userRepository.getCurrentUser()).thenReturn(MutableLiveData(user))
        splashViewModel = SplashViewModel(userRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `validate current user`(){
        assertNotNull(splashViewModel.getCurrentUser())
        assertEquals("Ephraim Nartey", splashViewModel.getCurrentUser()!!.value!!.name)
    }

}