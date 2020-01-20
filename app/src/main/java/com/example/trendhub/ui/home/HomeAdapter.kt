package com.example.trendhub.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trendhub.data.model.Repo
import com.example.trendhub.databinding.ItemRepoListBinding

class HomeAdapter(
    private var repoList: List<Repo>,
    private var listener: OnRepoClickListener
) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRepoListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = repoList.size

    fun setRepoList(repoList: List<Repo>) {
        this.repoList = repoList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(repoList[position], listener, position, holder.itemView)
    }


    class ViewHolder(private val binding: ItemRepoListBinding) :
        RecyclerView.ViewHolder(binding.rootLayout) {
        fun bindView(repo: Repo, listener: OnRepoClickListener, position: Int, view: View) {
            binding.tvAuthor.text = repo.author
            binding.tvRepoName.text = repo.repoName
            binding.tvDescription.text = repo.description
            binding.tvStarsCount.text = repo.stars.toString()
            binding.tvForksCount.text = repo.forks.toString()
            binding.tvLanguage.text = repo.language

            Glide.with(view.context)
                .load(repo.repoAvatar)
                .centerCrop()
                .into(binding.civAvatar)

            binding.rootLayout.setOnClickListener {
                listener.onRepoClick(position)
            }
        }
    }

    interface OnRepoClickListener {
        fun onRepoClick(position: Int)
    }
}
