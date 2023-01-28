package com.codechallenge.recipeapp.presentation.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.codechallenge.recipeapp.data.util.UnexpectedException
import com.codechallenge.recipeapp.data.util.UnknownHostException

open class BaseFragment: Fragment() {


    protected fun showMessageByException(
        exception: Exception
    ) {
        //TODO : Manage error by Exception
        Toast.makeText(requireContext(),exception.message,Toast.LENGTH_SHORT).show()
    }


}