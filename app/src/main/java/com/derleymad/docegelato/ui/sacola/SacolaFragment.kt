package com.derleymad.docegelato.ui.sacola

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.derleymad.docegelato.R
import com.derleymad.docegelato.databinding.FragmentSacolaBinding
import com.derleymad.docegelato.model.categorias.Adicionais
import com.derleymad.docegelato.model.categorias.Comida
import com.derleymad.docegelato.ui.assets.BottomSheetEditFragment
import com.derleymad.docegelato.ui.home.HomeViewModel
import com.derleymad.docegelato.ui.home.adapters.DestaqueAdapter
import com.derleymad.docegelato.ui.sacola.adapter.AdicionaisAdapter
import com.derleymad.docegelato.util.Utils.format
import com.squareup.picasso.Picasso

class SacolaFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentSacolaBinding? = null
    private val binding get() = _binding!!
    private lateinit var adicionaisAdapter : AdicionaisAdapter
    private lateinit var compreTambem : DestaqueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSacolaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startOnClickListeners()
        startObserver()
    }

    private fun startOnClickListeners() {
        binding.btnMinor.setOnClickListener {
            if (homeViewModel.quantityLiveData.value != 1) {
                homeViewModel.diminuirQuantidade()
            }
        }
        binding.addObs.setOnClickListener {
            BottomSheetEditFragment().show(requireActivity().supportFragmentManager, "Bottom sheet")
        }

        binding.btnPlus.setOnClickListener {
            homeViewModel.aumentarQuantidade()
        }
        binding.btnBackToBaseFragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun startObserver() {
        homeViewModel.nameLiveData.observe(viewLifecycleOwner) { name->
            for (i in homeViewModel.categoriaLiveData.value!!) {
                for (j in i.comidas) {
                    if (j.comida_title==name) {
                        // SE A CATEGORIA FOR MONTE(O ID TEM NUMERO 5)
                        if(i.id == 5){
                            binding.spinner.visibility = View.GONE
                        }else{
                            binding.spinner.visibility = View.VISIBLE
                        }

                        //SE QUALQUER COMIDA OS HASMAPS DE PRECO NAO FOREM NULOS(SE TIVEREM ALGO)
                        if(j.precos!=null){
                            val hasmaplist = j.precos
                            val otherlist = ArrayList<String>()

                            for(hashmap in j.precos){
                                otherlist.add("${hashmap?.values?.last()}, ${hashmap?.values?.first()}")
                            }
                            val adapterSpinner = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,
                                otherlist
                            )
                            binding.spinner.adapter = adapterSpinner
                            binding.spinner.onItemSelectedListener = object : OnItemSelectedListener{
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    p1: View?,
                                    p2: Int,
                                    p3: Long
                                ) {
                                    val precoDaPosicaoClicada = hasmaplist[p2]?.values?.first()
                                    homeViewModel.precoAtual.value = (precoDaPosicaoClicada as Double).toFloat()
                                    val teste=  hasmaplist[p2]
                                    homeViewModel.comida_preco_tamanho.value = teste
                                }
                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                }
                            }
                        }
                        homeViewModel.precoAtual.value = j.comida_preco
                        homeViewModel.precoAtual.observe(viewLifecycleOwner){
                            binding.tvSacolaPreco.text = format(it)
                        }
                        if(j.adicionais !=null){
                            startAdicionalRecyclerView(j.adicionais)
                            startAdicionaisObservers(j)
                        }else{
                        }

                        //Fim DO IF
                        binding.apply {
                            tvSacolaTitle.text = j.comida_title
                            tvSacolaDescricao.text = j.comida_desc
                            tvSacolaPreco.text = format(j.comida_preco!!)

                            Picasso.get().load(j.image).error(R.drawable.banner)
                                .placeholder(R.drawable.banner).into(imgSacola)

                            btnBottomAdicionar.setOnClickListener {
                                homeViewModel.obsLiveData.value = tvObs.text.toString()
                                homeViewModel.setComidaToPedidos(j, homeViewModel.user.value!!, homeViewModel.address.value!!)
                                requireActivity().findNavController(R.id.nav_host_fragment_activity_main).popBackStack()
                                homeViewModel.isPedidoFeitoLiveData.value = true
                            }
                        }

                        homeViewModel.quantityLiveData.observe(viewLifecycleOwner) { quantity ->
                            homeViewModel.precoAtual.observe(viewLifecycleOwner){ preco ->
                                binding.editBottomQuantity.text =
                                    homeViewModel.quantityLiveData.value.toString()
                                binding.btnBottomAdicionar.text = getString(
                                    R.string.adicionar_pedido,
                                    //O VALOR DO HASMAP ESTA AKI
                                    format(quantity.times(preco!!))
                                )
                            }
                        }
                        homeViewModel.obsLiveData.observe(viewLifecycleOwner) {
                            binding.tvObs.text = it
                        }
                    } else {
                    }
                }
            }
        }
    }

    private fun startAdicionalRecyclerView(list:ArrayList<Adicionais>){
        adicionaisAdapter = AdicionaisAdapter({
            homeViewModel.totalAdicionais.value = homeViewModel.totalAdicionais.value?.plus(1)
            homeViewModel.adiconais.value?.add(it)
        },
            {
            homeViewModel.totalAdicionais.value = homeViewModel.totalAdicionais.value?.minus(1)
            homeViewModel.adiconais.value?.remove(it)
        })
        binding.rvAdicionais.adapter = adicionaisAdapter
        binding.rvAdicionais.layoutManager = LinearLayoutManager(requireContext())
        adicionaisAdapter.addToRecyclerViewList(list)
    }

    fun startAdicionaisObservers(comida: Comida){
        homeViewModel.totalAdicionais.observe(viewLifecycleOwner){
            when{
                it==4 -> {
                    //5.0
                    val precoFisrt = comida.precos?.first()?.values
                    binding.btnBottomAdicionar.isEnabled = true
                    homeViewModel.precoAtual.value = precoFisrt?.first().toString().toFloat()
                    homeViewModel.comida_preco_tamanho.value = comida.precos?.first()
                }
                it>=5 -> {
                    //7.0
                    val precoLast= comida.precos?.last()?.values
                    binding.btnBottomAdicionar.isEnabled = true
                    homeViewModel.precoAtual.value = precoLast?.first().toString().toFloat()
                    homeViewModel.comida_preco_tamanho.value = comida.precos?.last()
                }
                else ->{
                    binding.btnBottomAdicionar.isEnabled = false
                }
            }
        }
    }

    override fun onDestroyView() {
        homeViewModel.destroyData()
        super.onDestroyView()
    }
}


