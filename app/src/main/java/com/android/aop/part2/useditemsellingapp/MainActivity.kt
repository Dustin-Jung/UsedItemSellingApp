package com.android.aop.part2.useditemsellingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.aop.part2.useditemsellingapp.chatlist.ChatListFragment
import com.android.aop.part2.useditemsellingapp.databinding.ActivityMainBinding
import com.android.aop.part2.useditemsellingapp.home.HomeFragment
import com.android.aop.part2.useditemsellingapp.mypage.MyPageFragment


class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

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
