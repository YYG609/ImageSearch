package com.android.imagesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.android.imagesearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            btnImageSearch.setOnClickListener {
                setFragment(SearchFragment())
            }

            btnCollection.setOnClickListener {
                setFragment(CollectionFragment())
            }
        }
        setFragment(SearchFragment())
    }

    private fun setFragment(frag: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fl_main, frag)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }
}