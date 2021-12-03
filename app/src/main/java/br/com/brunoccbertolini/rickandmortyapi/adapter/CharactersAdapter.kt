package br.com.brunoccbertolini.rickandmortyapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.brunoccbertolini.rickandmortyapi.data.model.Character
import br.com.brunoccbertolini.rickandmortyapi.databinding.CharacterItemBinding
import com.bumptech.glide.Glide

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    private var onItemClickListener: ((Character) -> Unit)? = null

    private val differCallback = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    inner class ViewHolder(itemViewBind: CharacterItemBinding) :
        RecyclerView.ViewHolder(itemViewBind.root) {
        private val tvName = itemViewBind.tvName
        private val tvStatus = itemViewBind.tvStatus
        private val image = itemViewBind.ivRmCharacter

        fun bind(item: Character) {
            tvName.text = item.name
            tvStatus.text = item.status
            Glide.with(itemView).load(item.image).into(image)

            itemView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }

        }

    }

    fun setOnItemClickListener(listener: (Character) -> Unit) {
        onItemClickListener = listener
    }

}
