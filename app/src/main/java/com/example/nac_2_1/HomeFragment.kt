package com.example.nac_2_1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.nac_2_1.databinding.FragmentHomeBinding
import com.example.nac_2_1.models.Techs
import com.example.nac_2_1.webservices.TechsConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {


    private lateinit var bindings: FragmentHomeBinding
    lateinit var img_url: String
    lateinit var preferences : SharedPreferences
    lateinit var preferencesEditor : SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindings = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Shared Preferences
        preferences = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        preferencesEditor = preferences.edit()

        super.onViewCreated(view, savedInstanceState)
        // Valida o checkbox de favorito


        // Valida se o usuario já escolheu uma tech, se sim renderiza ela
        if (preferences.getBoolean("favorite", false)) {
            bindings.tvTechTitle.text = preferences.getString("tech", "").toString()
            bindings.tvTechDescription.text = preferences.getString("descricao", "").toString()
            Glide
                .with(this@HomeFragment)
                .load(preferences.getString("imag", "").toString())
                .into(bindings.imageView)
            bindings.checkBox.isChecked = true
            changeCheckText(bindings.checkBox.isChecked)
            bindings.checkBox.visibility = View.VISIBLE
        }


        // Escuta o click do check box para salvar os dados no shared preferences ou limpar eles
            bindings.checkBox.setOnClickListener {
                if (bindings.checkBox.isChecked) {

                    val tech = bindings.tvTechTitle.text.toString()
                    val descTech = bindings.tvTechDescription.text.toString()
                    val imag = img_url
                    changeCheckText(bindings.checkBox.isChecked)
                    preferencesEditor.putString("tech", tech)
                    preferencesEditor.putString("descricao", descTech)
                    preferencesEditor.putString("imag", imag)
                    preferencesEditor.putBoolean("favorite", bindings.checkBox.isChecked)
                    preferencesEditor.apply()
                } else {
                    changeCheckText(bindings.checkBox.isChecked)
                    preferencesEditor.clear().apply()
                }

            }
            bindings.btnTechSearch.setOnClickListener {
                hideKeyboard(requireActivity())
                val id = bindings.etIdTech.text.toString().toInt()
                searcTech(id)
            }




    }

    private fun changeCheckText(state: Boolean){
        if (state) {
            bindings.checkBox.text = "Favorita"
        }  else {
            bindings.checkBox.text = "Favoritar"
        }
    }
    private fun searcTech(id: Int) {
        val callback = object : Callback<Techs> {
            override fun onResponse(call: Call<Techs>, response: Response<Techs>) {
                val tech = response.body()!!

                bindings.tvTechTitle.text = tech.tech
                bindings.tvTechDescription.text = tech.descricao

                img_url =
                    "https://cdn.jsdelivr.net/npm/programming-languages-logos@0.0.3/src/${tech.tech.lowercase()}/${tech.tech.lowercase()}_256x256.png"
                Glide
                    .with(this@HomeFragment)
                    .load(img_url)
                    .into(bindings.imageView)
                if (preferences.getString("tech", "").toString() != tech.tech){
                    bindings.checkBox.isChecked = false
                    changeCheckText(false)
                }
                bindings.checkBox.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<Techs>, t: Throwable) {
                Toast.makeText(activity, "Erro ao buscar a Tech", Toast.LENGTH_SHORT).show()
                t.message?.let { Log.e("erro", it) }
            }

        }

        TechsConnection().techsService.getTech(id).enqueue(callback)

    }
}