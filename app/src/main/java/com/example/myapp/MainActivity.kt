package com.example.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.catfeature.ui.BreedDetailFragment
import com.catfeature.ui.CatBreedsFragment
import com.catfeature.ui.FragmentNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = getString(R.string.cat_breed_title)
            setDisplayHomeAsUpEnabled(true)
        }

        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, CatBreedsFragment())
                .commit()
        }
    }

    override fun openBreedDetailFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, BreedDetailFragment())
            .addToBackStack(null)
            .commit()
    }
}