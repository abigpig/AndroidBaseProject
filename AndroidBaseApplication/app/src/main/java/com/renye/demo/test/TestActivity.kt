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
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.renye.demo.R
import com.renye.demo.activity.BaseActivity
import com.scwang.smartrefresh.header.WaveSwipeHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import kotlinx.android.synthetic.main.activity_test.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class TestActivity : BaseActivity() {

    private lateinit var mAdapter: MyRecyclerAdapter
    private lateinit var mDatas: MutableList<String>


    class MyRecyclerAdapter : RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

        var mContext: Context
        var mDatas: List<String>

        constructor(context: Context, list: List<String>) {
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
            holder.tv.setText(mDatas[position])
        }


        class MyViewHolder : RecyclerView.ViewHolder {
            constructor(itemView: View) : super(itemView) {
                ButterKnife.bind(this, itemView)
            }

            @BindView(R.id.test_recyclerview_item_iv)
            lateinit var iv: ImageView

            @BindView(R.id.test_recyclerview_item_tv)
            lateinit var tv: TextView
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
        mAdapter = MyRecyclerAdapter(this, mDatas)
        //设置layoutManager
        test_recyclerView.layoutManager = LinearLayoutManager(this)
        test_recyclerView.adapter = mAdapter

    }

    @OnClick(R.id.test_btn)
    fun onClick(view: View) {
        getAsyncTaskData()
    }

    fun getAsyncTaskData() {
        val retrofit = Retrofit.Builder().baseUrl("http://fanyi.youdao.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        var service = retrofit.create(GetCarsService::class.java)
        var call = service.fetchCar()
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var jsonArray = response.body()!!.getAsJsonArray("web")
                for (jsonElement: JsonElement in jsonArray) {
                    var value: String = (jsonElement as JsonObject).get("key").toString()
                    value = value.replace("\"", "")
                    mDatas.add(value)
                }
                mAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                System.out.println(t)
            }
        })
    }

    interface GetCarsService {
        @GET("openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
        fun fetchCar(): Call<JsonObject>
    }

}

/*

    {
        "translation": ["车"],
        "basic": {
            "us-phonetic": "kɑr",
            "phonetic": "kɑː",
            "uk-phonetic": "kɑː",
            "explains": ["n. 汽车；车厢", "n. (Car)人名；(土)贾尔；(法、西)卡尔；(塞)察尔"]
        },
        "query": "car",
        "errorCode": 0,
        "web": [{
            "value": ["汽车炸弹", "爆炸车", "表演者"],
            "key": "car bomb"
        }, {
            "value": ["都市车", "城市车", "城市用车"],
            "key": "City Car"
        }, {
            "value": ["电缆车", "叮当车", "缆车"],
            "key": "cable car"
        }]
    }

*/