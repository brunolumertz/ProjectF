package lul.myapplication.ui.minhalista.tabelas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_quero_ver.*
import kotlinx.android.synthetic.main.item_minhalista.*
import lul.myapplication.MyApplication
import lul.myapplication.NÃO
import lul.myapplication.R
import lul.myapplication.SIM
import lul.myapplication.models.Filme
import lul.myapplication.ui.adapter.FilmeLocalAdapter
import lul.myapplication.ui.minhalista.MinhaListaViewModel
import lul.myapplication.ui.minhalista.MinhaListaViewModelFactory

class QueroVerFragment: Fragment() {

    private val viewModel: MinhaListaViewModel by viewModels {
        MinhaListaViewModelFactory((activity?.application as MyApplication).repository)
    }

    private val adapter by lazy {
        context?.let {
            FilmeLocalAdapter(context = it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getListaQueroVer()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_quero_ver,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraQueroVer()
    }

    private fun configuraQueroVer(){
        lista_querover.adapter = adapter
        lista_querover.layoutManager = LinearLayoutManager(context)
        adapter?.onClickDeleta = {
            deletaFilmesQueroVer(it)
        }
        adapter?.onClickMarca = {
            vaiParaJaVi(it)
        }
    }

    private fun getListaQueroVer(){
        viewModel.filmesQueroVer.observe(this,{
            it?.let {
                it.sortedBy { it.tittle }
                adapter?.append(it)
            }
        })
    }

    private fun deletaFilmesQueroVer(filme: Filme){
        MaterialAlertDialogBuilder(requireContext(),R.style.Theme_MaterialComponents_DayNight)
            .setTitle("Remover ${filme.tittle} ?")
            .setMessage("Deseja remover '${filme.tittle}' da sua lista?")
            .setPositiveButton("SIM") { dialog, _ ->
                viewModel.deletaFilmeQueroVer(filme)
                adapter?.deletaFilme(filme)
                dialog.dismiss()
            }
            .setNeutralButton("NÃO") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun vaiParaJaVi(filme: Filme){
        MaterialAlertDialogBuilder(requireContext(), R.style.Theme_MaterialComponents_DayNight)
            .setTitle("Mover ${filme.tittle}?")
            .setMessage("Deseja mover '${filme.tittle}' para Quero Ver?")
            .setPositiveButton(SIM) { dialog, _ ->
                viewModel.salvaListaJavi(filme)
                adapter?.deletaFilme(filme)
                dialog.dismiss()
                Toast.makeText(requireContext(),
                    "'${filme.tittle}' movido para Ja Vi.",
                    Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton(NÃO) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}