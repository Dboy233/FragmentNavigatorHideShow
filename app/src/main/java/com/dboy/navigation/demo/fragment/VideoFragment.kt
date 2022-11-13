package com.dboy.navigation.demo.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dboy.navigation.demo.R
import com.dboy.navigation.demo.base.BaseFragment

class VideoFragment : BaseFragment() {


    override val layoutId: Int
        get() = R.layout.fragment_video

    override fun initViewAndData(view: View) {

        val title = VideoFragmentArgs.fromBundle(requireArguments()).title

        view.findViewById<TextView>(R.id.videoView).text = title

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.adapter = object : RecyclerView.Adapter<VideoViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
                val itemView =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
                return VideoViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
                holder.itemView.findViewById<TextView>(R.id.item_title).text = "推荐视频$position"
                holder.itemView.setOnClickListener {
                    findNavController().navigate(VideoFragmentDirections.actionVideoFragmentSelf("推荐视频$position"))
                }
            }

            override fun getItemCount(): Int = 4

        }


        view.findViewById<Button>(R.id.backSetting).setOnClickListener {
            //返回设置页面，中间打开的所有VideoFragment全部移除
            findNavController().navigate(VideoFragmentDirections.actionVideoFragmentToSettingFragment())
        }

    }

    class VideoViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView)

}