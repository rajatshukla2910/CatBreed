package com.example.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.catfeature.ui.FragmentNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentNavigator {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "Cat Breeds"
            setDisplayHomeAsUpEnabled(true)
        }

        if (savedInstanceState == null) {

            navigator.openFragmentCatBreedsFragment()
        }
    }

    override fun openBreedDetailFragment() {
        navigator.openFragmentBreedDetailFragment()
    }
}