package com.example.nac_2_1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.nac_2_1.databinding.FragmentHomeBinding
import com.example.nac_2_1.models.Techs
import com.example.nac_2_1.webservices.TechsConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var bindings: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindings = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)

        bindings.btnTechSearch.setOnClickListener {
            val id = bindings.etIdTech.text.toString().toInt()
            searcTech(id)
        }
        return bindings.root
    }


    private fun searcTech(id: Int){
        val callback = object : Callback<Techs> {
            override fun onResponse(call: Call<Techs>, response: Response<Techs>) {
                val tech = response.body()!!
                Log.i("info",tech.toString())
                bindings.tvTechTitle.text = tech.tech
                bindings.tvTechDescription.text = tech.ulr_imag

                Glide
                    .with(this@HomeFragment)
                    .load(tech.ulr_imag)
                    .into(bindings.imageView)

            }

            override fun onFailure(call: Call<Techs>, t: Throwable) {
                Toast.makeText(activity, "Erro ao buscar a Tech", Toast.LENGTH_SHORT).show()
                t.message?.let { Log.e("erro", it) }
            }

        }

        TechsConnection().techsService.getTech(id).enqueue(callback)

    }
}