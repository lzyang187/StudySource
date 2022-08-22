package com.lzy.studysource.fold

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.lzy.studysource.R

/**
 * Created by zhaoyang.li5 on 2022/8/17 16:49
 */
class FoldFragment1 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView: ")
        return inflater.inflate(R.layout.fragment_fold1, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.tv).setOnClickListener {
//            val navController = findNavController()
//            val navController = it.findNavController()
            val navController = activity?.findNavController(R.id.tv)

            // 使用 Safe Args 实现类型安全的导航
            val action = FoldFragment1Directions.actionFoldFragment1ToJetPackFragment(4)
            navController?.navigate(action)
            // 使用 ID 导航
//            val bundle = bundleOf()
//            bundle.putInt("myArg", 2)
//            navController?.navigate(R.id.action_foldFragment1_to_jetPackFragment, bundle)

        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }

    companion object {
        private const val TAG = "FoldFragment1"
    }
}