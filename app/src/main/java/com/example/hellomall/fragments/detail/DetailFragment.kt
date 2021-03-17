package com.example.hellomall.fragments.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.hellomall.R
import com.example.hellomall.fragments.cart.CircleTextView
import com.example.malllibrary.fragments.MallFragment
import com.example.malllibrary.net.RestClient
import com.example.malllibrary.net.callback.ISuccess
import com.example.malllibrary.ui.banner.BannerCreator
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter

class DetailFragment : MallFragment(),AppBarLayout.OnOffsetChangedListener
,View.OnClickListener{



    private var mGoodsId = -1
    private lateinit var mBanner: Banner<String, BannerImageAdapter<String>>
    private lateinit var mTabLayout: TabLayout
    private lateinit var mPager: ViewPager
    private lateinit var mBtnAddCart:Button
    private lateinit var mCircleTvCount:CircleTextView
    private var mGoodsCount=0

    companion object {
        private const val GOODS_ARG = "goods_arg"

        private val options = RequestOptions
            .diskCacheStrategyOf(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate()
            .override(100, 100)

        //简单工厂模式创建，根据goods_id返回不同fragment
        fun create(goodsId: Int): DetailFragment {
            val arg = Bundle()
            arg.putInt(GOODS_ARG, goodsId)
            val fragment = DetailFragment()
            fragment.arguments = arg
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arg = arguments
        if (arg != null) {
            mGoodsId = arg.getInt(GOODS_ARG)
        }
    }

    override fun setLayout(): Any {
        return R.layout.activity_detail
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mBanner = findView(R.id.detail_banner)
        mTabLayout = findView(R.id.tab_layout)
        mPager = findView(R.id.view_pager)
        mBtnAddCart=findView(R.id.btn_add_shop_cart)
        mBtnAddCart.setOnClickListener(this)
        mCircleTvCount=findView(R.id.tv_shopping_cart_amount)
        val mCollapsingToolbarLayout=findView<CollapsingToolbarLayout>(R.id.collapsing_toolbar_detail)
        val mAppBarLayout=findView<AppBarLayout>(R.id.app_bar_detail)
        //设置粘性上拉
        mCollapsingToolbarLayout.setContentScrimColor(Color.WHITE)
        mAppBarLayout.addOnOffsetChangedListener(this)

        //设置circle的颜色
        mCircleTvCount.setCircleColor(Color.RED)

        initData()
        initTabLayout()
    }

    private fun initData() {
        RestClient.builder()
            .url("goods_detail.php")
            .params("goods_id", mGoodsId)
            .loader(context!!)
            .success(object : ISuccess {
                override fun onSuccess(response: String) {
                    val dataObject = JSON.parseObject(response)
                    if (dataObject != null) {
                        val data = dataObject.getJSONObject("data")
                        initBanner(data)
                        initGoodsInfo(data)
                        initViewPager(data)
                    }
                }
            })
            .build()
            .get()
    }

    private fun initTabLayout() {
        mTabLayout.tabMode = TabLayout.MODE_FIXED
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(context!!, R.color.app_main))
        mTabLayout.tabTextColors = ColorStateList.valueOf(Color.BLACK)
        mTabLayout.setBackgroundColor(Color.WHITE)
        mTabLayout.setupWithViewPager(mPager)
    }

    private fun initViewPager(data: JSONObject) {
        mPager.adapter = ViewPagerAdapter(fragmentManager!!, data)
    }


    private fun initGoodsInfo(data: JSONObject) {
        val dataJson = data.toJSONString()
        //载入GoodsInfoFragment
        supportDelegate.loadRootFragment(R.id.frame_goods_info, GoodsInfoFragment.create(dataJson))
    }

    private fun initBanner(data: JSONObject) {
        val dataArray = data.getJSONArray("banners")
        val size = dataArray.size
        val images = ArrayList<String>()
        for (i in 0 until size) {
            images.add(dataArray.getString(i))
        }
        BannerCreator.setDefault(context!!, images, mBanner)
    }

    /**
     * AppBar滑动监听
     */
    override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_add_shop_cart->{
                addShopCart()
            }
        }
    }


    private fun addShopCart() {
        //加入动画
        //加入购物车操作，提交至数据库
        RestClient
            .builder()
            .url("add_shop_cart_count.php")
            .loader(context!!)
            .success(object : ISuccess {
                override fun onSuccess(response: String) {
                    val isAdded = JSON.parseObject(response).getBoolean("data")
                    if (isAdded) {
                        mGoodsCount++
                        mCircleTvCount.visibility = View.VISIBLE
                        mCircleTvCount.text = mGoodsCount.toString()
                    }
                }
            })
            .params("count", mGoodsCount)
            .build()
            .post()

    }
}