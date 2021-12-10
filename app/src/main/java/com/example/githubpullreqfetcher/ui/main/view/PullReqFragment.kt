package com.example.githubpullreqfetcher.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubpullreqfetcher.R
import com.example.githubpullreqfetcher.data.api.ApiHelper
import com.example.githubpullreqfetcher.data.api.RetrofitBuilder
import com.example.githubpullreqfetcher.data.model.PullResponse
import com.example.githubpullreqfetcher.data.repository.PullReqRepo
import com.example.githubpullreqfetcher.databinding.FragmentPullReqBinding
import com.example.githubpullreqfetcher.ui.main.adapter.PullReqAdapter
import com.example.githubpullreqfetcher.ui.main.viewmodel.PullReqViewModel
import com.example.githubpullreqfetcher.utils.Status

class PullReqFragment : Fragment() {

    private var adapter: PullReqAdapter? = null
    private lateinit var binding: FragmentPullReqBinding

    private val viewModel: PullReqViewModel by lazy {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PullReqViewModel(PullReqRepo(ApiHelper(RetrofitBuilder.apiService))) as T
            }
        })[PullReqViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pull_req, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initializeUI()
        return binding.root
    }

    private fun initializeUI() {
        adapter = PullReqAdapter()
        binding.listRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.listRv.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        fetchData()
    }

    private fun setupObservers() {
        viewModel.eventStream.observe(viewLifecycleOwner, { interaction ->
            when (interaction) {
                is PullReqViewModel.PullReqViewModelInteraction.RetryEvent -> fetchData()
            }
        })
    }

    private fun fetchData() {
        viewModel.getUsers().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        onSuccessStatus(resource.data)
                    }
                    Status.ERROR -> {
                        onErrorStatus(it.message)
                    }
                    Status.LOADING -> {
                        onLoadingStatus()
                    }
                }
            }
        })
    }

    private fun onSuccessStatus(list: List<PullResponse>?) {
        binding.dataGrp.visibility = View.VISIBLE
        binding.errorGrp.visibility = View.GONE
        binding.loadingGrp.visibility = View.GONE
        list?.let { adapter?.updateData(it) }
    }

    private fun onErrorStatus(msg: String?) {
        binding.errorText.text = msg
        binding.dataGrp.visibility = View.GONE
        binding.errorGrp.visibility = View.VISIBLE
        binding.loadingGrp.visibility = View.GONE
    }

    private fun onLoadingStatus() {
        binding.dataGrp.visibility = View.GONE
        binding.errorGrp.visibility = View.GONE
        binding.loadingGrp.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.finish()
    }

    companion object {
        @JvmStatic
        fun newInstance(): PullReqFragment {
            return PullReqFragment()
        }

        const val TAG = "PullReqFragment"
    }
}