package com.catfeature.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.catfeature.ui.databinding.FragmentBreedDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BreedDetailFragment : Fragment() {

    private var _binding:  FragmentBreedDetailBinding? = null

    val binding get() = _binding!!

    private  val viewModel: CatBreedsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBreedDetailBinding.inflate(inflater, container, false)
        binding.model = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.selectedbreed.observe(viewLifecycleOwner) { selectedBreed ->
            (requireActivity() as AppCompatActivity).supportActionBar?.title = selectedBreed.name
            Picasso.get()
                .load(selectedBreed.image?.url)
                .into(binding.imageView)
        }

    }

}