package com.example.companies.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment(private val layoutId: Int) : Fragment() {

    lateinit var inflatedView: View

    abstract fun findViewsById()
    abstract fun viewsInitialization()
    abstract fun informationInitialization()
    abstract fun injectFragment()

    override fun onAttach(context: Context) {
        injectFragment()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflatedView = inflater.inflate(layoutId, container, false)
        return inflatedView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViewsById()
        viewsInitialization()
        informationInitialization()
    }

}