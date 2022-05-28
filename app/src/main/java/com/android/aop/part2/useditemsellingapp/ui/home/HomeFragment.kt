package com.android.aop.part2.useditemsellingapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.aop.part2.useditemsellingapp.DBKey.Companion.CHILD_CHAT
import com.android.aop.part2.useditemsellingapp.R
import com.android.aop.part2.useditemsellingapp.base.BaseFragment
import com.android.aop.part2.useditemsellingapp.base.ViewState
import com.android.aop.part2.useditemsellingapp.ui.chatlist.ChatListItem
import com.android.aop.part2.useditemsellingapp.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel by viewModels<HomeViewModel>()

    private lateinit var articleAdapter: ArticleRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        articleAdapter = ArticleRecyclerViewAdapter(onItemClicked = { articleModel ->
            homeViewModel.createChatRoom(articleModel)
        })
        binding.articleRecyclerView.adapter = articleAdapter
    }


    private fun initViewModel() {
        binding.viewModel = homeViewModel

        homeViewModel.viewState.observe(viewLifecycleOwner) { viewState ->
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