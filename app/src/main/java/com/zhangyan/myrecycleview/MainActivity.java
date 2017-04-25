package com.zhangyan.myrecycleview;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> mArrayList;
    private MyRecyclerViewAdapter mMyRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        //找到控件,
        initView();
        //并设置adapter
        initData();
    }

    private void initData() {
        //准备一些数据出来 ,一个List
        mArrayList = new ArrayList<>();
        for (char i = 'A'; i < 'Z'; i++) {
            mArrayList.add(i+"");
        }
        //通过构造方法,将数据传到Adapter中
        mMyRecyclerViewAdapter = new MyRecyclerViewAdapter(mArrayList);
        mRecyclerView.setAdapter(mMyRecyclerViewAdapter);

        //设置item点击事件
        mMyRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int positon) {
                Toast.makeText(MainActivity.this, "你点击了条目:"+positon, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //需要指定控件的展示方式,不然出不来效果的
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //给控件设置动画效果
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        //给控件设置分割线
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this).paint(paint).build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_list_view) {
            Toast.makeText(MainActivity.this, "切换为ListView", Toast.LENGTH_SHORT).show();
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            return true;
        }else if (id == R.id.action_grid_view) {
            Toast.makeText(MainActivity.this, "切换为GridView", Toast.LENGTH_SHORT).show();
            mLayoutManager = new GridLayoutManager(getApplicationContext(),3);
            mRecyclerView.setLayoutManager(mLayoutManager);
            return true;
        }else if (id == R.id.action_hor_grid_view) {
            Toast.makeText(MainActivity.this, "切换为横向的GridView", Toast.LENGTH_SHORT).show();
            mLayoutManager = new GridLayoutManager(getApplicationContext(),3,GridLayoutManager.HORIZONTAL,false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            return true;
        }else if (id == R.id.action_stagger_view) {
            Toast.makeText(MainActivity.this, "切换为交错的瀑布流", Toast.LENGTH_SHORT).show();
            initStaggerData();
            mLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
            return true;
        }else if (id == R.id.action_add) {
            Toast.makeText(MainActivity.this, "添加一个item", Toast.LENGTH_SHORT).show();
            mMyRecyclerViewAdapter.addItem("C",2);
            return true;
        }else if (id == R.id.action_delete) {
            Toast.makeText(MainActivity.this, "删除一个item", Toast.LENGTH_SHORT).show();
            mMyRecyclerViewAdapter.deleteItem(2);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initStaggerData() {
        //先清除掉所有数据
        mArrayList.clear();
        //添加不同长度的数据
        StringBuilder sb = new StringBuilder();
        for (char i = 'A'; i < 'Z'; i++) {
            sb.append(i+"");
            mArrayList.add(sb.toString());
        }
        //通过adapter更新
        if(mMyRecyclerViewAdapter!=null){
            mMyRecyclerViewAdapter.notifyDataSetChanged();
        }else{
            mMyRecyclerViewAdapter = new MyRecyclerViewAdapter(mArrayList);
            mRecyclerView.setAdapter(mMyRecyclerViewAdapter);
        }
    }
}
