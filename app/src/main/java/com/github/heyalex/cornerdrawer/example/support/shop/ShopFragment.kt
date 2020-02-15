package com.github.heyalex.cornerdrawer.example.support.shop

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.github.heyalex.CornerDrawer
import com.github.heyalex.cornersheet.behavior.CornerSheetHeaderBehavior
import com.github.heyalex.cornerdrawer.example.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.shop_fragment.*

class ShopFragment : Fragment() {

    private lateinit var behavior: CornerSheetHeaderBehavior<CornerDrawer>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shop_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        behavior =
            BottomSheetBehavior.from(requireActivity().findViewById<CornerDrawer>(R.id.corner_drawer))
                as CornerSheetHeaderBehavior

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            toolbar.setOnApplyWindowInsetsListener { v, insets ->
                v.updatePadding(top = insets.systemWindowInsetTop)
                insets
            }
        }

        toolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }

        view.findViewById<RecyclerView>(R.id.shop_recyclerview).apply {
            adapter = ShopAdapter(object : ShopItemClickListener {
                override fun onClick(image: View, text: View, shopItemId: Long) {

                    val manager: FragmentManager = requireActivity().supportFragmentManager
                    val currentFragment: Fragment = manager.findFragmentByTag("shop")!!
                    val intoContainerId = currentFragment.id

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manager.beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack("shared")
                            .add(
                                intoContainerId,
                                DetailShopFragment.newInstance(shopItemId),
                                "detail"
                            )
                            .commit()
                    }
                }
            }).also {
                it.submitList(items)
            }
            clipToPadding = false

            setPadding(
                0,
                0,
                0,
                behavior.peekHeight
            )

            behavior.peekHeightListener = object : CornerSheetHeaderBehavior.OnPeekHeightListener {
                override fun onPeekHeightChanged(newPeekHeight: Int) {
                    setPadding(
                        0,
                        0,
                        0,
                        newPeekHeight
                    )
                }
            }
        }
    }


}