package com.example.docegelato.ui.assets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.docegelato.databinding.FragmentBottomSheetEditBinding
import com.example.docegelato.ui.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetEditFragment : BottomSheetDialogFragment() {
    private val homeViewModel : HomeViewModel by activityViewModels()

    private var _binding: FragmentBottomSheetEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheetEditBinding.inflate(inflater, container, false)
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startOnClickEventListeners()
    }

    private fun startOnClickEventListeners() {

        binding.btnSendObs.setOnClickListener{
            homeViewModel.obsLiveData.value = binding.editObservacao.text.toString()
            this@BottomSheetEditFragment.dismiss()
        }

    }
}