package com.jmadrigal.hackernews.features.news.presentation.ui.fragment

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
import com.jmadrigal.hackernews.features.news.data.model.Hit
import com.jmadrigal.hackernews.features.news.presentation.ui.adapter.NewsAdapter
import com.jmadrigal.hackernews.features.news.presentation.viewmodel.NewsViewModel
import com.jmadrigal.hackernews.utils.GenericDataState
import com.jmadrigal.hackernews.utils.LoadingDialog
import com.jmadrigal.hackernews.utils.SwipeCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter() { onItemSelected(it) } }
    private var newsJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsBinding.bind(view)
        setupRecycler()
        setupObserver()
        setupRefreshListener()
    }

    private fun setupObserver() {
        newsJob = lifecycleScope.launch {
            viewModel.hits.collect {
                when(it){
                    is GenericDataState.Error<*> -> {
                        LoadingDialog.hide()
                    }
                    is GenericDataState.IDLE<*> -> {
                        LoadingDialog.hide()
                    }
                    is GenericDataState.Loading<*> -> {
                        LoadingDialog.show(requireActivity())
                    }
                    is GenericDataState.Success<*> -> {
                        LoadingDialog.hide()
                        it.data?.let {
                            fillLatestNews(it)
                        }
                    }
                }
            }
        }
        viewModel.getLatestNews()
    }

    private fun setupRecycler(){
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    private fun setupRefreshListener(){
        binding.swRefresh.setOnRefreshListener {
            setupObserver()
        }
    }

    private fun fillLatestNews(hits: List<Hit>) {
        newsJob?.cancel()
        val itemTouchHelper = ItemTouchHelper(SwipeCallback(requireContext()) { position ->

            val currentList = newsAdapter.currentList.toMutableList()
            viewModel.deleteHit(currentList[position].objectID?:"")
            currentList.removeAt(position)
            newsAdapter.submitList(currentList)
            if (currentList.isEmpty()){
                binding.imgNoData.visibility = View.VISIBLE
            } else {
                binding.imgNoData.visibility = View.GONE
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recycler)

        binding.swRefresh.isRefreshing = false

        if (hits.isEmpty()) {
            binding.imgNoData.visibility = View.VISIBLE
        } else {
            binding.imgNoData.visibility = View.GONE
            newsAdapter.submitList(hits)
        }
    }

    private fun onItemSelected(item: Hit) {
        navToDetail(item.storyUrl)
    }

    private fun navToDetail(url: String?) {
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(requireContext(), getString(R.string.no_data), Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        } else {
            findNavController().navigate(NewsFragmentDirections.actionDetail(url!!))
        }
    }

    override fun onPause() {
        super.onPause()
        newsJob?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}