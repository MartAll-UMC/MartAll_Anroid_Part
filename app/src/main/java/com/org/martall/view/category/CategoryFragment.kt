import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.org.martall.adapter.CategoryViewPagerAdapter
import com.org.martall.databinding.FragmentCategoryBinding
import com.org.martall.view.cart.CartActivity
import com.org.martall.view.search.SearchActivity


class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding  // 탭 레이아웃을 포함하는 레이아웃 바인딩

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        binding.sortTv.visibility = View.GONE // 삭제
        binding.filterTv.visibility = View.GONE // 삭제

        binding.tbCategory.searchIc.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("isProductSearch", true)
            startActivity(intent)
        }
        binding.tbCategory.cartIc.setOnClickListener {
            val intent = Intent(context, CartActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰페이저와 어댑터 초기화
        val viewPager: ViewPager2 = binding.categoryVp
        val pagerAdapter = CategoryViewPagerAdapter(requireActivity())
        viewPager.adapter = pagerAdapter

        // 탭 레이아웃과 뷰페이저를 연결
        val tabs: TabLayout = binding.categoryTl
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            // 각 탭에 표시할 텍스트 설정
            tab.text = when (position) {
                0 -> "전체"
                1 -> "과일&채소"
                2 -> "정육"
                3 -> "수산"
                4 -> "간식"
                else -> "생활용품"
            }
        }.attach()
    }
}
