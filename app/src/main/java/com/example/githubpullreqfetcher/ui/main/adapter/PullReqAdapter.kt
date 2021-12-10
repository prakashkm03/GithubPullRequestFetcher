package com.example.githubpullreqfetcher.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubpullreqfetcher.R
import com.example.githubpullreqfetcher.data.model.PullResponse
import com.squareup.picasso.Picasso
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class PullReqAdapter :
    RecyclerView.Adapter<PullReqAdapter.ItemViewHolder>() {

    private var pullReqList: MutableList<PullResponse> = mutableListOf()

    fun updateData(list: List<PullResponse>) {
        pullReqList.clear()
        list.let { pullReqList.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pull_req_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(pullReqList[position])
    }

    override fun getItemCount(): Int {
        return pullReqList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.avatar)
        private val name = itemView.findViewById<TextView>(R.id.name)
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val createdAt = itemView.findViewById<TextView>(R.id.createdAt)
        private val closedAt = itemView.findViewById<TextView>(R.id.closedAt)

        fun bind(item: PullResponse) {
            Picasso.get().load(item.user?.avatar_url).into(image)
            name.text = item.user?.login
            title.text = item.title

            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            createdAt.text = String.format(
                "Created : %s",
                formatter.format(ZonedDateTime.parse(item.created_at))
            )
            closedAt.text =
                String.format("Closed : %s", formatter.format(ZonedDateTime.parse(item.closed_at)))
        }
    }

}