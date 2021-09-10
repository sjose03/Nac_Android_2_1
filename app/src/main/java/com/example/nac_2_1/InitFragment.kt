package com.example.nac_2_1

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.nac_2_1.databinding.FragmentInitBinding

class InitFragment : Fragment() {

    private lateinit var bindings: FragmentInitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindings = DataBindingUtil.inflate(inflater,R.layout.fragment_init,container,false)
        return bindings.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            val action =  InitFragmentDirections.actionInitFragmentToHomeFragment()
            findNavController().navigate(action)
        }, 5300)


    }

}