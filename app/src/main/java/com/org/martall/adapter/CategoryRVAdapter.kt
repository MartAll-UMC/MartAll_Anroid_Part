import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.martall.R
import com.org.martall.databinding.ItemCategoryProductBinding
import com.org.martall.models.ItemLikedResponseDTO
import com.org.martall.models.SecondItem
import com.org.martall.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class CategoryRVAdapter(
    private val itemList: List<SecondItem>,
    private val api: ApiService,
) : RecyclerView.Adapter<CategoryRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(private val binding: ItemCategoryProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            GlobalScope.launch {
                binding.likeBtn.setOnClickListener {
                    val item = itemList[adapterPosition]
                    val itemId = item.itemId

                    item.like = !item.like
                    updateLikeButton(item.like)

                    if (item.like) {
                        api.likedItem(itemId).enqueue(object : Callback<ItemLikedResponseDTO> {
                            override fun onResponse(
                                call: Call<ItemLikedResponseDTO>,
                                response: Response<ItemLikedResponseDTO>,
                            ) {
                                if (!response.isSuccessful) {

                                } else {
                                    // 성공 시 로그로 상태 변경 확인
                                    Log.d("retrofit", "Like status changed: $item")
                                }
                            }

                            override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                                // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                                item.like = !item.like
                                updateLikeButton(item.like)
                            }
                        })
                    } else {
                        api.unLikedItem(itemId).enqueue(object : Callback<ItemLikedResponseDTO> {
                            override fun onResponse(
                                call: Call<ItemLikedResponseDTO>,
                                response: Response<ItemLikedResponseDTO>,
                            ) {
                                if (!response.isSuccessful) {

                                } else {
                                    // 성공 시 로그로 상태 변경 확인
                                    Log.d("retrofit", "Like status changed: $item")
                                }
                            }

                            override fun onFailure(call: Call<ItemLikedResponseDTO>, t: Throwable) {
                                // 실패 시 처리: 클릭 상태를 이전 상태로 변경
                                item.like = !item.like
                                updateLikeButton(item.like)
                            }
                        })
                    }
                }
            }
        }

        fun bind(item: SecondItem) {
            binding.apply {
                Glide.with(itemView).load(item.pic).into(binding.itemImgIv)
                itemNameTv.text = item.itemName
                martNameTv.text = item.martShopName
                val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(item.price)
                itemPriceTv.text = "${formattedPrice}원"
                // 초기 버튼 상태 설정
                updateLikeButton(item.like)
            }
        }

        private fun updateLikeButton(isLiked: Boolean) {
            if (isLiked) {
                binding.likeBtn.setImageResource(R.drawable.ic_like_filled_20dp)
            } else {
                binding.likeBtn.setImageResource(R.drawable.ic_like_unfilled_20dp)
            }
        }
    }
}
