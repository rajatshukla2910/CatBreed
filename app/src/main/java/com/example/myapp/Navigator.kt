package com.example.myapp

import androidx.fragment.app.FragmentManager
import com.catfeature.ui.BreedDetailFragment
import com.catfeature.ui.CatBreedsFragment
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class Navigator @Inject constructor(
    private val fragmentManager: FragmentManager
) {

    fun openFragmentCatBreedsFragment() {
        val fragmentA = CatBreedsFragment()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment, fragmentA)
            .commit()
    }

    fun openFragmentBreedDetailFragment() {
        val fragmentB = BreedDetailFragment()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment, fragmentB)
            .addToBackStack(null)
            .commit()
    }
}