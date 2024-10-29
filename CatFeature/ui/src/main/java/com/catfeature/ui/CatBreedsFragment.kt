package com.catfeature.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catfeature.ui.databinding.FragmentCatBreedsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatBreedsFragment : Fragment() {

    lateinit var navigator: FragmentNavigator

    private var _binding:  FragmentCatBreedsBinding? = null
    private lateinit var adapter: CatBreedsDataAdapter

    val binding get() = _binding!!
    private  val viewModel: CatBreedsViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigator) {
            navigator = context
        } else {
            throw RuntimeException("$context must implement FragmentNavigator")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatBreedsBinding.inflate(inflater, container, false)
        binding.model = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        adapter = CatBreedsDataAdapter{ itemClicked ->
            viewModel.setSelectedBreed(itemClicked)
            navigator.openBreedDetailFragment()
        }
        binding.recv.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Cat Breeds"
        observeCatBreedData()
        observeLoadMoreData()
    }

    private fun observeCatBreedData() {
        viewModel.catBreeds.observe(viewLifecycleOwner) { list ->
                adapter.submitList(list)
        }
    }

    private fun observeLoadMoreData() {
        binding.recv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager =  binding.recv.layoutManager as LinearLayoutManager
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                if(viewModel.canLoadMore(System.currentTimeMillis())) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        viewModel.loadData(System.currentTimeMillis())
                    }
                }

            }
        })

    }
}