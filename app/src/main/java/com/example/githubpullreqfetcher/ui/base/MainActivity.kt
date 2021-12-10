package com.example.githubpullreqfetcher.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubpullreqfetcher.ui.main.view.PullReqFragment
import com.example.githubpullreqfetcher.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(
                R.id.fragment_container,
                PullReqFragment.newInstance(),
                PullReqFragment.TAG
            )
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }
}