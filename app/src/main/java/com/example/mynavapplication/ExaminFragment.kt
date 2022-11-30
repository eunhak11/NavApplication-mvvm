package com.example.mynavapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mynavapplication.databinding.FragmentExaminBinding
import com.example.mynavapplication.viewmodel.MbtiViewModel


class ExaminFragment : Fragment() {

    val viewModel: MbtiViewModel by activityViewModels()
    // Activity에 물려있는 viewmodel을 불러온다.
    var binding: FragmentExaminBinding ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExaminBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding?.root
    }

    fun examinMBTI(): String {
        binding?.let {
            val ieStr = if(it.chkE.isChecked) "E" else "I"
            val snStr = if(it.chkN.isChecked) "N" else "S"
            val tfStr = if(it.chkF.isChecked) "F" else "T"
            val jpStr = if(it.chkJ.isChecked) "J" else "P"

            return ieStr + snStr + tfStr + jpStr
        }

        return ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewModel에 있는 mbti 값이 변하면
        viewModel.mbti.observe(viewLifecycleOwner) {
        // UI에 있는 check값이 변한다.
            binding?.chkE?.isChecked = viewModel.isE
            binding?.chkN?.isChecked = viewModel.isN
            binding?.chkF?.isChecked = viewModel.isF
            binding?.chkJ?.isChecked = viewModel.isJ
        }
        // UI에 있는 check 값이 변하면 viewModel의 MBTI값이 변한다.
        binding?.chkE?.setOnClickListener {
            viewModel.setE( binding?.chkE?.isChecked ?: false)
        }
        binding?.chkN?.setOnClickListener {
            viewModel.setN( binding?.chkN?.isChecked ?: false)
        }
        binding?.chkF?.setOnClickListener {
            viewModel.setF( binding?.chkF?.isChecked ?: false)
        }
        binding?.chkJ?.setOnClickListener {
            viewModel.setJ( binding?.chkJ?.isChecked ?: false)
        }

        binding?.btnResult?.setOnClickListener{
            val result = examinMBTI()
            val bundle = bundleOf("MBTI" to result)
            findNavController().navigate(R.id.action_examinFragment_to_resultFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}