package com.example.docegelato.ui.home.categorias.destaques

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.docegelato.databinding.FragmentDestaquesBinding
import com.example.docegelato.extensions.navMainToSacola
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.ui.home.adapters.DestaqueAdapter
import com.squareup.picasso.Picasso

class DestaquesFragment : Fragment() {

    private var _binding: FragmentDestaquesBinding? = null
    private val binding get() = _binding!!
    private lateinit var destaqueAdapter: DestaqueAdapter
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val imageList = ArrayList<SlideModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDestaquesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        loadBanners()
        startObservers()
    }

    fun prepareRecyclerView() {
        destaqueAdapter = DestaqueAdapter {
            homeViewModel.idLiveData.value = it
            findNavController().navMainToSacola()
        }
        binding.rvDestaques.apply {
            adapter = destaqueAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addOnItemTouchListener(object : OnItemTouchListener {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    when (e.action) {
                        MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(true)
                    }
                    return false
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

            })
        }
    }

    fun loadBanners() {
        imageList.add(SlideModel("https://derleymad.github.io/DoceGelato/images/banner1.png"))
        imageList.add(SlideModel("https://derleymad.github.io/DoceGelato/images/banner2.png"))
        imageList.add(SlideModel("https://derleymad.github.io/DoceGelato/images/banner3.png"))
        binding.imageSlider.setImageList(imageList)
        Picasso
            .get()
            .load("https://derleymad.github.io/DoceGelato/images/banner2.png")
            .into(binding.imgBannerCupom)
    }

    fun startObservers() {
        homeViewModel.destaquesLiveData.observe(viewLifecycleOwner, Observer {
            destaqueAdapter.setDestaquesList(it)
        })

        homeViewModel.isLoadingContent.observe(viewLifecycleOwner) {
            if(it) shimmerStart() else shimmerStop()
        }
    }

    private fun shimmerStart() {
        binding.shimmerLayout.apply {
            startShimmer()
            visibility = View.VISIBLE
        }
    }

    private fun shimmerStop() {
        binding.shimmerLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}