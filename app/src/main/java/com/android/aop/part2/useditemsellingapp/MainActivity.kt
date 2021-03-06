package com.android.aop.part2.useditemsellingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.aop.part2.useditemsellingapp.base.BaseActivity
import com.android.aop.part2.useditemsellingapp.databinding.ActivityMainBinding
import com.android.aop.part2.useditemsellingapp.ui.chatlist.ChatListFragment
import com.android.aop.part2.useditemsellingapp.ui.home.HomeFragment
import com.android.aop.part2.useditemsellingapp.ui.mypage.MyPageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val chatListFragment = ChatListFragment()
        val myPageFragment = MyPageFragment()

        replaceFragment(homeFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                R.id.chatList -> replaceFragment(chatListFragment)
                R.id.myPage -> replaceFragment(myPageFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer, fragment)
                commit()
            }


    }



}
