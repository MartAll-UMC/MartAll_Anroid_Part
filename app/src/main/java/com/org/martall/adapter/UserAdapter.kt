package com.org.martall.adapter

class UserAdapter { //: ListAdapter<MartDTO,UserAdapter.UserViewHolder>(diffUtil) {

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
//        return UserViewHolder(
//            ItemUserInfoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//        holder.bind(currentList[position])
//    }
//
//
//
//    inner class UserViewHolder(val binding: ItemUserInfoBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        @SuppressLint("SetTextI18n")
//        fun bind(item: MartDTO) {
//            with(binding) {
//                tvNumberOfFollower.text="팔로워 수 ${item.followerCount}명"
//                tvNumberOfVisitor.text="방문자 수 ${item.visitorCount}명"
//                tvUserName.text=item.name
//                tvTag.text=item.hashTag
//                rvImage.apply {
//                    layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//                    adapter=UserPostAdapter(dummyPosts)
//                }
//            }
//        }
//    }
//
//
//    companion object {
//      val diffUtil= object: DiffUtil.ItemCallback<MartDTO>() {
//            override fun areItemsTheSame(oldItem: MartDTO, newItem: MartDTO): Boolean {
//                return oldItem.name == newItem.name
//            }
//
//            override fun areContentsTheSame(oldItem: MartDTO, newItem: MartDTO): Boolean {
//                return oldItem==newItem
//            }
//        }
//    }
}

