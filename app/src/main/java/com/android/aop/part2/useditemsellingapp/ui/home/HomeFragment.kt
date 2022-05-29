package com.android.aop.part2.useditemsellingapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.android.aop.part2.useditemsellingapp.R
import com.android.aop.part2.useditemsellingapp.base.BaseFragment
import com.android.aop.part2.useditemsellingapp.base.ViewState
import com.android.aop.part2.useditemsellingapp.databinding.FragmentHomeBinding
import com.android.aop.part2.useditemsellingapp.ui.adapter.ArticleRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel by viewModels<HomeViewModel>()

    private val articleAdapter  = ArticleRecyclerViewAdapter(onItemClicked = { articleModel ->
        homeViewModel.createChatRoom(articleModel)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        binding.articleRecyclerView.adapter = articleAdapter
    }


    private fun initViewModel() {
        binding.viewModel = homeViewModel

        homeViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? HomeViewState)?.let {
                onChangedHomeViewState(viewState)
            }
        }
    }

    private fun onChangedHomeViewState(viewState: ViewState) {
        when (viewState) {

            is HomeViewState.GetArticleList -> {
                articleAdapter.addAll(viewState.list)
            }

            is HomeViewState.Message -> {
                Snackbar.make(binding.root, viewState.message, Snackbar.LENGTH_LONG).show()
            }

            is HomeViewState.RouteAddArticle -> {
                startActivity(Intent(requireContext(), AddArticleActivity::class.java))
            }

        }
    }
}