package com.derleymad.docegelato.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.derleymad.docegelato.R
import com.derleymad.docegelato.databinding.FragmentSearchBinding
import com.derleymad.docegelato.extensions.navSearchToSacola
import com.derleymad.docegelato.ui.home.HomeViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val homeViewModel : HomeViewModel by activityViewModels()

    private lateinit var arrayAdapter : ArrayAdapter<String>
    private val binding get() = _binding!!
    private var list = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        homeViewModel.categoriaLiveData.observe(viewLifecycleOwner){categorias ->
            list.clear()
            for(categoriaItem in categorias){
                for(comida in categoriaItem.comidas){
                    list.add(comida.comida_title)
                }
            }
           arrayAdapter = ArrayAdapter(requireContext(),R.layout.search,list)
           binding.rvListview.adapter = arrayAdapter
            if(binding.searchView.query.toString().isEmpty() || binding.searchView.query.toString().isBlank()){
                clearList()
            }else{
                arrayAdapter.filter.filter(binding.searchView.query.toString())
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.rvListview.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, Id: Long) {
                homeViewModel.nameLiveData.value = arrayAdapter.getItem(position)
                navSearchToSacola()
            }
        })

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query?.isEmpty()==true){
                    arrayAdapter.filter.filter("")
                }
                if(list.contains(query)){
                    arrayAdapter.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                    for(comida in list){
                        arrayAdapter.filter.filter(newText)
                    }

                return false
            }

        })
    }
    fun clearList(){
        arrayAdapter.filter.filter("none")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}