package com.example.sunrisemoonriseapp.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sunrisemoonriseapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment: Fragment() {

    lateinit var backButtonView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButtonView = view.findViewById(R.id.back)
        backButtonView.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

}