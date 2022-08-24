package com.lzy.studysource.fold

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.lzy.studysource.R
import com.lzy.studysource.jetpack.navigation.NavigationActivity

/**
 * Created by zhaoyang.li5 on 2022/8/17 16:49
 */
class FoldFragment1 : Fragment() {

    private val mViewModel: FoldViewModel by activityViewModels()

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
        val myArg = arguments?.getString("myArg") ?: "空"
        val tv = view.findViewById<TextView>(R.id.tv)
        tv.text = myArg
        tv.setOnClickListener {
            if (activity is NavigationActivity) {
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
        mViewModel.mSelect.observe(viewLifecycleOwner, {
            tv.text = it
        })
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