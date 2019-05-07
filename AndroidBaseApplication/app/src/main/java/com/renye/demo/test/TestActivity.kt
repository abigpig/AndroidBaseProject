package com.renye.demo.test

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.renye.demo.R
import com.renye.demo.activity.BaseActivity
import com.scwang.smartrefresh.header.WaveSwipeHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity() {

    private lateinit var mAdapter: MyRecyclerAdapter
    private lateinit var mDatas: MutableList<Int>


    class MyRecyclerAdapter : RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

        var mContext: Context
        var mDatas: List<Int>

        constructor(context: Context, list: List<Int>) {
            this.mContext = context
            this.mDatas = list
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView: View = LayoutInflater.from(mContext).inflate(R.layout.activity_test_recyclerview_item, null)
            return MyViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return mDatas.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            Glide.with(mContext).load(R.mipmap.launch_iocn).into(holder.iv)
        }

        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var iv: ImageView = itemView.findViewById(R.id.test_recyclerview_item_iv)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()
    }

    fun initView() {
        //var header = BezierRadarHeader(this)
        //header.setEnableHorizontalDrag(true)
        var header = WaveSwipeHeader(this)

        test_refreshLayout.setRefreshHeader(header)
        var footer = BallPulseFooter(this)
        footer.setSpinnerStyle(SpinnerStyle.Scale)
        test_refreshLayout.setRefreshFooter(footer)

        mDatas = mutableListOf()
        for (i in 30 downTo 0 step 1) {
            mDatas.add(i)
        }
        mAdapter = MyRecyclerAdapter(this, mDatas)
        //设置layoutManager
        test_recyclerView.layoutManager = LinearLayoutManager(this)
        test_recyclerView.adapter = mAdapter

    }

    @OnClick(R.id.test_btn)
    fun onClick(view: View) {
        Toast.makeText(this,"one",Toast.LENGTH_SHORT).show()
    }

}
