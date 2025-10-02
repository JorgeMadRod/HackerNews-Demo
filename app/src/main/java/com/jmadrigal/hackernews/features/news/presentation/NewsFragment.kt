package com.jmadrigal.hackernews.features.news.presentation

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmadrigal.hackernews.R
import com.jmadrigal.hackernews.databinding.FragmentNewsBinding
import com.jmadrigal.hackernews.features.news.data.Hit
import com.jmadrigal.hackernews.utils.SwipeCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter() { onItemSelected(it) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsBinding.bind(view)
        setupObserver()
        setupRefreshListener()

    }

    private fun setupObserver() {
        Log.v("--->", "setupObserver")
        lifecycleScope.launch {
            viewModel.hits.collect {
                Log.v("--->", "collect")
                setupRecycler(it)
            }
        }
        viewModel.getLatestNews()
    }

    private fun setupRefreshListener(){
        Log.v("--->", "setupRefreshListener")
        binding.swRefresh.setOnRefreshListener {
            //viewModel.getLatestNews()
            setupObserver()
        }
    }

    private fun setupRecycler(hits: List<Hit>) {

        val itemTouchHelper = ItemTouchHelper(SwipeCallback(requireContext()) { position ->

            val currentList = newsAdapter.currentList.toMutableList()
            viewModel.deleteHit(currentList[position].objectID?:"")
            currentList.removeAt(position)
            newsAdapter.submitList(currentList)
            if (currentList.isEmpty()){
                binding.imgNoData.visibility = View.VISIBLE
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recycler)

        binding.swRefresh.isRefreshing = false
        if (hits.isEmpty()) {
            binding.imgNoData.visibility = View.VISIBLE
        } else {
            binding.imgNoData.visibility = View.GONE
            newsAdapter.submitList(hits)
            binding.recycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                adapter = newsAdapter
            }
        }
    }

    private fun onItemSelected(item: Hit) {
        navToDetail(item.storyUrl)
    }

    private fun navToDetail(url: String?) {
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(requireContext(), "Detalle no disponible.", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        } else {
            findNavController().navigate(NewsFragmentDirections.actionDetail(url!!))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}