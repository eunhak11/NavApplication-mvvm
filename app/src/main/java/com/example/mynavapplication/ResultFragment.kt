package com.example.mynavapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mynavapplication.databinding.FragmentResultBinding
import com.example.mynavapplication.viewmodel.MbtiViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment() {

    /*
    val viewModel: MbtiViewModel by viewModels ()
    바로 초기화 불가능 -> Fragment에 view를 create한 후에 초기화 가능
    viewModel을 어떻게 초기화 할지를 viewModels() 함수에 위임.
    viewModel의 lifecycle이 자신을 소유한 fragment에 종속될 때 사용.
    현재 viewModel은 MainActivity에 종속되므로 사용하면 안된다.
     */
    val viewModel: MbtiViewModel by activityViewModels()
    // MainActivity에 종속된 ViewModel을 불러온다.

    var binding: FragmentResultBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.mbti.observe(viewLifecycleOwner) {
            binding?.txtResult?.text = viewModel.mbti.value
        }
        /*
         ResultFragment의 text는 viewModel의 mbti property (Livedata)를 보고있다가
         해당 property의 내용을 가져다가 반영해서 사용.
         */

        binding?.btnReexamin?.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_examinFragment)
        }
    }
}