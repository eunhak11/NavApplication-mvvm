package com.example.mynavapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynavapplication.repositiry.MbtiRepository

const val UNCHECKED_MBTI = "ISNP"
class MbtiViewModel: ViewModel() {


    // MutableLiveData : fragent에서 obserb할 수 있는 종류의 String.
    // -> 밖에서 볼때는 Mutable속성이 있으면 안된다 -> Private로 선언
    // view(fragment)에서 관찰하다가 변경사항이 생기면 바로 적용 가능한 String.
    // 관찰은 가능하지만 함부로 변경을 불가능하게 해야한다.
    // 하나의 형식으로 기억하자.
    // 외부에서는 mbti에 접근, 처리는 mbti를 받아온 _mbti로.
    private val _mbti = MutableLiveData<String>(UNCHECKED_MBTI)
    // private을 변수 앞에 _를 붙이는 경우가 많다.
    val mbti : LiveData<String> get() = _mbti

    // 위,아래 순서가 중요하다
    private val repository = MbtiRepository()
    init {
        repository.observeMbti(_mbti)
    }


    private fun modifyMbti(index: Int, newValue: Char) {
        val newMbti = _mbti.value?.let {
            val chArr = it.toCharArray()
            chArr[index] = newValue
            String(chArr)  // chArr.String()하면 안된다.
        } ?: UNCHECKED_MBTI

        repository.postMbit(newMbti)
    }

    //  문자열의 0번째 글자를 가져올 떄는 문자열이 0이 될수도 있으므로 ?.get(n) 사용.
    val isE get() = _mbti.value?.get(0) == 'E' // true & false 를 return 한다.
    val isN get() = _mbti.value?.get(1) == 'N'
    val isF get() = _mbti.value?.get(2) == 'F'
    val isJ get() = _mbti.value?.get(3) == 'J'

    fun setE(newValue: Boolean) {
        modifyMbti(0, if(newValue) 'E' else 'I')
    }
    fun setN(newValue: Boolean) {
        modifyMbti(1, if(newValue) 'N' else 'S')
    }
    fun setF(newValue: Boolean) {
        modifyMbti(2, if(newValue) 'F' else 'T')
    }
    fun setJ(newValue: Boolean) {
        modifyMbti(3, if(newValue) 'J' else 'P')
    }

}