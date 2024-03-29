package com.org.martall.view.likelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.org.martall.adapter.LikeItemRVAdapter
import com.org.martall.databinding.FragmentLikeItemBinding
import com.org.martall.models.LikeItemResponseDTO
import com.org.martall.models.ItemLikedResponseDTO
import com.org.martall.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikeItemFragment : Fragment() {
    private lateinit var binding: FragmentLikeItemBinding
    private lateinit var api: ApiService
    private val productList: ArrayList<LikeItemResponseDTO.DibsProducts> = ArrayList()
    private var adapter: LikeItemRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLikeItemBinding.inflate(inflater, container, false)
        initializeRecyclerView()
        getLikedItems()
        return binding.root
    }

    private fun initializeRecyclerView() {
        adapter = LikeItemRVAdapter(productList) { itemId ->
            onCancelDibsProduct(itemId)
        }
        binding.rvLikeList.adapter = adapter
    }

    private fun getLikedItems() {
        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            api.getDibsProduct().enqueue(object : Callback<LikeItemResponseDTO> {
                override fun onResponse(
                    call: Call<LikeItemResponseDTO>, response: Response<LikeItemResponseDTO>,
                ) {
                    if (response.isSuccessful) {
                        val dibsProducts = response.body()?.result?.item ?: emptyList()
                        updateRecyclerView(dibsProducts)
                    } else {
                        Log.e("[PRINT/DibsProductFragment]", "찜한 상품 표시 실패")
                        updateRecyclerView(emptyList())
                    }
                }

                override fun onFailure(call: Call<LikeItemResponseDTO>, t: Throwable) {
                    Log.d("DibsProductFragment", "Failed to get liked items: ${t.message}")
                    showToast("찜한 상품을 가져오는 데 실패했습니다.")
                }
            })
        }

    }

    private fun updateRecyclerView(dibsProducts: List<LikeItemResponseDTO.DibsProducts>) {
        productList.clear()
        productList.addAll(dibsProducts)

        if (productList.isEmpty()) {
            binding.likeLayout.root.visibility = View.VISIBLE
            binding.rvLikeList.visibility = View.GONE
        } else {
            binding.likeLayout.root.visibility = View.GONE
            binding.rvLikeList.visibility = View.VISIBLE
            adapter?.notifyDataSetChanged()
        }
    }

    private fun onCancelDibsProduct(itemId: Int) {
        GlobalScope.launch {
            api.unLikedItem(itemId).enqueue(object : Callback<ItemLikedResponseDTO> {
                override fun onResponse(
                    call: Call<ItemLikedResponseDTO>,
                    response: Response<ItemLikedResponseDTO>,
                ) {
                    if (response.isSuccessful) {
                        removeProductFromList(itemId)
                    } else {
                        // Handle error case
                    }
                }

                override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                    Log.d("DibsProductFragment", "Failed to cancel dibs: ${t.message}")
                    showToast("찜 취소에 실패했습니다.")
                }
            })
        }
    }

    private fun removeProductFromList(itemId: Int) {
        val position = productList.indexOfFirst { it.itemId == itemId }
        if (position != -1) {
            productList.removeAt(position)
            adapter?.notifyItemRemoved(position)
            adapter?.notifyItemRangeChanged(position, productList.size)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
