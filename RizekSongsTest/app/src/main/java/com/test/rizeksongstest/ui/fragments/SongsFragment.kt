package com.test.rizeksongstest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.rizeksongstest.R
import com.test.rizeksongstest.databinding.FragmentSongsBinding
import com.test.rizeksongstest.models.Track
import com.test.rizeksongstest.ui.MainActivity
import com.test.rizeksongstest.ui.adapters.SongsAdapter
import com.test.rizeksongstest.viewmodels.SongsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_songs.*
import javax.inject.Inject

class SongsFragment : DaggerFragment(), SongsAdapter.OnEventListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var songsViewModel: SongsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songsViewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(SongsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentSongsBinding.inflate(inflater, container, false)
        binding.songsViewModel = songsViewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val songsAdapter = SongsAdapter(this)
        rv_result_songsFragment.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        rv_result_songsFragment.adapter = songsAdapter

        et_search_songsFragment.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (et_search_songsFragment.text?.isNotEmpty() == true)
                    songsViewModel.search()
                (requireActivity() as MainActivity).closeKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        et_search_songsFragment.doOnTextChanged { _, _, _, _ ->
            if (et_search_songsFragment.text?.isNotEmpty() == true) {
                iv_exit_songsFragment.visibility = View.VISIBLE
            } else {
                iv_exit_songsFragment.visibility = View.INVISIBLE
            }
        }

        iv_exit_songsFragment.setOnClickListener {
            songsViewModel.clearSearch()
        }

        songsViewModel.getResultObserver().observe(viewLifecycleOwner, {
            songsAdapter.setListings(it.result.tracks)
            if (it.result.tracks.isEmpty()) {
                val value = getString(R.string.songsFragment_not_found) +
                    songsViewModel.getLastSearchedString() +
                    getString(R.string.songsFragment_quotes)
                tv_not_found_songsFragment.text = value
                cl_no_result_songsFragment.visibility = View.VISIBLE
            } else {
                cl_no_result_songsFragment.visibility = View.GONE
            }
        })
    }

    override fun onSongClick(song: Track) {
        val viewSongFragment = ViewSongFragment()
        val bundle = Bundle()
        bundle.putParcelable(ViewSongFragment.TRACK_VALUE, song)
        viewSongFragment.arguments = bundle
        (requireActivity() as MainActivity).openView(viewSongFragment)
    }
}