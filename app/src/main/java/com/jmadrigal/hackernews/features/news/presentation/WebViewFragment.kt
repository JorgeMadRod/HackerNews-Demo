package com.jmadrigal.hackernews.features.news.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.jmadrigal.hackernews.R
import com.jmadrigal.hackernews.databinding.FragmentWebviewBinding
import com.jmadrigal.hackernews.utils.MyWebViewClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment(R.layout.fragment_webview) {

    private var _binding: FragmentWebviewBinding? = null
    private val binding get() = _binding!!
    private val args: WebViewFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    this.isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWebviewBinding.bind(view)
        setupWebView()
    }

    private fun setupWebView() {
        binding.webView.apply {
            webViewClient = MyWebViewClient { pageLoaded ->
                if (!pageLoaded) {
                    showPageLoadedWithError()
                }
            }
            settings.javaScriptEnabled = true
            loadUrl(args.url)
        }
    }

    private fun showPageLoadedWithError() {
        Toast.makeText(requireContext(), "Error al cargar la página. Verifica tu conexión a internet.", Toast.LENGTH_LONG).show()
        parentFragmentManager.popBackStack()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}