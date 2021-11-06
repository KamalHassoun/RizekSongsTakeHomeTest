package com.test.rizeksongstest.ui.fragments

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.test.rizeksongstest.R
import com.test.rizeksongstest.databinding.FragmentViewSongBinding
import com.test.rizeksongstest.models.Track
import com.test.rizeksongstest.ui.MainActivity
import com.test.rizeksongstest.viewmodels.ViewSongViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_view_song.*
import javax.inject.Inject

class ViewSongFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewSongViewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewSongViewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(ViewSongViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentViewSongBinding.inflate(inflater, container, false)
        binding.song = requireArguments().getParcelable(TRACK_VALUE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.visibility = View.INVISIBLE
        val song = requireArguments().getParcelable<Track>(TRACK_VALUE)
        if (song?.album?.images?.isNotEmpty() == true) {
            Glide.with(requireContext())
                .asBitmap()
                .load(song.album.images[0].url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        iv_image_viewSongFragment.setImageBitmap(resource)
                        Palette.Builder(resource).generate {
                            it?.let {  palette ->
                                val dominantColor = palette.getDominantColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.dusty_gray
                                    )
                                )

                                sb_player_viewSongFragment.progressDrawable.colorFilter =
                                    BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                                        dominantColor,
                                        BlendModeCompat.SRC_ATOP
                                    )
                                sb_player_viewSongFragment.thumb.colorFilter =
                                    BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                                        dominantColor,
                                        BlendModeCompat.SRC_ATOP
                                    )
                                cv_play_viewSongFragment.setCardBackgroundColor(dominantColor)
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                                    cv_play_viewSongFragment.outlineSpotShadowColor = dominantColor
                                    cv_play_viewSongFragment.outlineAmbientShadowColor = dominantColor
                                }
                                view.visibility = View.VISIBLE
                            }
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                })
        } else {
            view.visibility = View.VISIBLE
        }

        iv_back_viewSongFragment.setOnClickListener {
            (requireActivity() as MainActivity).onBackPressed()
        }
    }

    companion object {
        const val TRACK_VALUE = "track_value"
    }
}