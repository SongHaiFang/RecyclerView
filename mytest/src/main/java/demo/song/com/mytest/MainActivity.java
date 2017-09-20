package demo.song.com.mytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static android.R.string.ok;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private List<Bean.TopStoriesBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        okGet();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void okGet() {
        OkHttpUtils.get()
                .url("http://news-at.zhihu.com/api/4/news/latest")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        String string = response.toString();
                        Gson gson = new Gson();
                        Bean bean = gson.fromJson(string, Bean.class);
                        list = bean.getTop_stories();
                        adapter = new HomeAdapter(list,MainActivity.this);
                        recyclerView.setAdapter(adapter);

                        adapter.setOnItemClickLitener(new HomeAdapter.OnItemClickLitener() {
                            @Override
                            public void onItemClick(View view, int position) {
//                                Toast.makeText(MainActivity.this,list.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, WebAct.class);
                                intent.putExtra("path","http://www.baidu.com");
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                Toast.makeText(MainActivity.this,list.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.myre);
    }
}
