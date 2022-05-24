package com.android.aop.part2.useditemsellingapp.mypage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.android.aop.part2.useditemsellingapp.R
import com.android.aop.part2.useditemsellingapp.databinding.FragmentMypageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyPageFragment : Fragment(R.layout.fragment_mypage) {

    private lateinit var binding: FragmentMypageBinding
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            signInOutButton.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                if (auth.currentUser == null) {

                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                successSignIn()
                            } else {
                                Toast.makeText(
                                    context,
                                    "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    auth.signOut()
                    emailEditText.text.clear()
                    emailEditText.isEnabled = true
                    passwordEditText.text.clear()
                    passwordEditText.isEnabled = true

                    signInOutButton.text = "로그인"
                    signInOutButton.isEnabled = false
                    signUpButton.isEnabled = false
                }
            }
            signUpButton.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context,
                                "회원가입에 성공했습니다. 로그인 버튼을 눌러주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "회원가입에 실패했습니다. 이미 가입한 이메일일 수 있습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
            }

            emailEditText.addTextChangedListener {
                val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
                signInOutButton.isEnabled = enable
                signUpButton.isEnabled = enable
            }

            passwordEditText.addTextChangedListener {
                val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
                signInOutButton.isEnabled = enable
                signUpButton.isEnabled = enable
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            binding.let { binding ->
                binding.emailEditText.text.clear()
                binding.passwordEditText.text.clear()
                binding.emailEditText.isEnabled = true
                binding.passwordEditText.isEnabled = true
                binding.signInOutButton.text = "로그인"
                binding.signInOutButton.isEnabled = false
                binding.signUpButton.isEnabled = false
            }
        } else {
            binding.let { binding ->
                binding.emailEditText.setText(auth.currentUser!!.email)
                binding.passwordEditText.setText("***********")
                binding.emailEditText.isEnabled = false
                binding.passwordEditText.isEnabled = false
                binding.signInOutButton.text = "로그아웃"
                binding.signInOutButton.isEnabled = true
                binding.signUpButton.isEnabled = false
            }
        }
    }


    private fun successSignIn() {
        if (auth.currentUser == null) {
            Toast.makeText(context, "로그인에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        binding.emailEditText.isEnabled = false
        binding.passwordEditText.isEnabled = false
        binding.signUpButton.isEnabled = false
        binding.signInOutButton.text = "로그아웃"

    }
}