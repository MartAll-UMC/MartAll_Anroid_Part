package com.org.martall.view.likelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.org.martall.adapter.BookMarkRVAdapter
import com.org.martall.databinding.FragmentBookmarkBinding
import com.org.martall.models.BookMarkManager
import com.org.martall.models.BookMarkResponseDTO
import com.org.martall.models.FollowResponseDTO
import com.org.martall.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookMarkFragment : Fragment() {
    lateinit var binding: FragmentBookmarkBinding
    private lateinit var api: ApiService
    private val martList: ArrayList<BookMarkResponseDTO.DibsMarts> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        getLikedMarts() //단골마트 불러오기
        return binding.root
    }

    private fun getLikedMarts() {
        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            api.getDibsMart().enqueue(object : Callback<BookMarkResponseDTO> {
                override fun onResponse(
                    call: Call<BookMarkResponseDTO>,
                    response: Response<BookMarkResponseDTO>,
                ) {
                    if (response.isSuccessful) {
                        martList.clear() //현재 목록을 초기화하고 새 데이터로 업데이트
                        martList.addAll(response.body()?.followedMarts ?: emptyList())
                        updateRecyclerView(martList) // RecyclerView를 업데이트
                    } else {
                        // 에러 처리
                    }
                }

                // API 요청이 실패했을 때 호출
                override fun onFailure(call: Call<BookMarkResponseDTO>, t: Throwable) {
                    Log.d("check", "failed")
                    showToast("단골 마트 목록을 가져오는 데 실패했습니다.")
                }
            })
        }

    }

    // RecyclerView를 업데이트
    private fun updateRecyclerView(dibsMarts: ArrayList<BookMarkResponseDTO.DibsMarts>) {
        if (dibsMarts.isEmpty()) {
            // 단골마트 없을 경우
            binding.bookmarkLayout.root.visibility = View.VISIBLE
            binding.rvBookmarkList.visibility = View.GONE
        } else {
            // 단골마트 있을 경우
            binding.bookmarkLayout.root.visibility = View.GONE
            binding.rvBookmarkList.visibility = View.VISIBLE
            binding.rvBookmarkList.adapter = BookMarkRVAdapter(dibsMarts) { martShopId ->
                unfollowMart(martShopId) // 관심 목록에서 제거하는 함수를 호출
            }
        }
    }

    private fun unfollowMart(martShopId: Int) {
        Log.d("DibsMartFragment", "Unfollowing mart with ID: $martShopId")
        val apiService = BookMarkManager.dibsMartApiService
        apiService.unfollowMart(martShopId).enqueue(object : Callback<FollowResponseDTO> {
            override fun onResponse(
                call: Call<FollowResponseDTO>,
                response: Response<FollowResponseDTO>,
            ) {
                if (response.isSuccessful) {
                    removeMartFromList(martShopId) // 목록에서 제거
                    Log.d("DibsMartFragment", "Unfollow: $martShopId succeeded")
                } else {
                    Log.d("DibsMartFragment", "Unfollow: $martShopId failed")
                }
            }

            // 요청 실패 시
            override fun onFailure(call: Call<FollowResponseDTO>, t: Throwable) {

            }
        })
    }

    private fun removeMartFromList(martShopId: Int) {
        val position = martList.indexOfFirst { it.martshopId == martShopId }
        if (position != -1) {
            martList.removeAt(position)
            binding.rvBookmarkList.adapter?.notifyItemRemoved(position) //아이템 제거
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
