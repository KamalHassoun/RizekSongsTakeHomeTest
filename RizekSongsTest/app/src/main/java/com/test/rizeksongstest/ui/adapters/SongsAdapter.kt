package com.test.rizeksongstest.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.shape.CornerFamily
import com.test.rizeksongstest.R
import com.test.rizeksongstest.databinding.ItemSongBinding
import com.test.rizeksongstest.models.Track

class SongsAdapter(private val onEventListener: OnEventListener)
    : RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {
    private val tracks = arrayListOf<Track>()

    inner class SongViewHolder(private val itemSongBinding: ItemSongBinding)
        : RecyclerView.ViewHolder(itemSongBinding.root) {
        fun bind(song: Track, finalPos: Boolean = false) {
            itemSongBinding.song = song
            itemSongBinding.executePendingBindings()

            if (finalPos) {
                itemSongBinding.viewDividerSongItem.visibility = View.INVISIBLE
            } else {
                itemSongBinding.viewDividerSongItem.visibility = View.VISIBLE
            }

            if (song.album.images.isNotEmpty()) {
                itemSongBinding.ivImageSongsItem.visibility = View.VISIBLE
                Glide.with(itemSongBinding.root.context)
                    .load(song.album.images[0].url)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(itemSongBinding.ivImageSongsItem)
            } else {
                itemSongBinding.ivImageSongsItem.visibility = View.INVISIBLE
            }

            itemSongBinding.clMainSongItem.setOnClickListener {
                onEventListener.onSongClick(song)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemSongBinding.inflate(layoutInflater, parent, false)
        val roundedValue = parent.context.resources.getDimension(R.dimen.spotifyImageRoundedStandard)
        viewBinding.ivImageSongsItem.shapeAppearanceModel = viewBinding.ivImageSongsItem.shapeAppearanceModel
            .toBuilder()
            .setBottomLeftCorner(CornerFamily.ROUNDED, roundedValue)
            .setBottomRightCorner(CornerFamily.ROUNDED, roundedValue)
            .setTopLeftCorner(CornerFamily.ROUNDED, roundedValue)
            .setTopRightCorner(CornerFamily.ROUNDED, roundedValue)
            .build()
        return SongViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(tracks[position], position == tracks.size - 1)
    }

    override fun getItemCount(): Int = tracks.size

    fun setListings(tracks: List<Track>) {
        val oldCount = this.tracks.size
        this.tracks.clear()
        notifyItemRangeRemoved(0, oldCount)
        this.tracks.addAll(tracks)
        notifyItemRangeInserted(0, this.tracks.size)
    }

    interface OnEventListener {
        fun onSongClick(song: Track)
    }
}