package xl1712114143.lvyouzhinan_client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xl1712114143.lvyouzhinan_client.bean.Article;
import xl1712114143.lvyouzhinan_client.bean.MyAdapter;
import xl1712114143.lvyouzhinan_client.bean.User;


public class Fragment_main extends Fragment implements OnBannerListener {
    private Banner banner;
    private ArrayList<Integer> list_path;
    private ArrayList<String> list_title;
    private WrapContentListView myList;
    private SearchView searchView;
    private MyAdapter adapter;
    private List<Article> lists;
    private MyAdapter adapter1;
    private ListView listView;
    private List<Article> articleList = new ArrayList<>();
    int[] images=null;
    String[] title = null;
    String[] briefs = null;
    String[] contents = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        myList = view.findViewById(R.id.viewList);
        banner = view.findViewById(R.id.banner);
        searchView = view.findViewById(R.id.searchView);
        myList.setTextFilterEnabled(true);


        //SearchView控制
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    myList.setFilterText(newText);
                } else {
                    myList.clearTextFilter();
                }
                return false;
            }
        });

        myList.setOnItemClickListener(new myOnItemClickListener());
            FormBody.Builder builder = new FormBody.Builder();
            OkHttpClient okHttpClient=new OkHttpClient();
            RequestBody formBody=builder.build();
            Request request=new   Request.Builder().url("http://192.168.199.155:8080/article/getarticle").build();
            Call call=okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(() ->{
                        Toast.makeText(getContext(), "与服务器连接失败，请检查网络连接！", Toast.LENGTH_SHORT).show();
                    });
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ResponseBody body = response.body();
                    String result = body.string();
                    Gson gson = new Gson();
                     articleList=gson.fromJson(result,new TypeToken<List<Article>>(){}.getType());
                     title = new String[articleList.size()];
                    for (int i = 0; i < articleList.size(); i++) {
                        title[i] = articleList.get(i).getTitle();
                    }
                    adapter = new MyAdapter(articleList,getActivity());
                    getActivity().runOnUiThread(() ->{
                        myList.setAdapter(adapter);
                        });
                }
            });
        initView();
        return view;
    }


    private class myOnItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Article article = (Article) myList.getAdapter().getItem(position);
            Intent intent = new Intent();
            intent.setClass(getActivity(), ShowActivity.class);
            intent.putExtra("id",article.getId());
            intent.putExtra("image", article.getImage());
            intent.putExtra("content", article.getContent());
            startActivity(intent);
        }
    }

    //轮播图控制

    private void initView() {
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();
        list_path.add(R.drawable.gulangyu);
        list_path.add(R.drawable.fangte);
        list_path.add(R.drawable.yunshuiyao);
        list_path.add(R.drawable.fengdongshi);
        list_path.add(R.drawable.sanfangqixiang);
        list_title.add("鼓浪屿风景区");
        list_title.add("方特乐园欢迎您");
        list_title.add("漳州云水谣，品风土人情");
        list_title.add("东山风动石风景区");
        list_title.add("三坊七巷，福州之景");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
    }
    //轮播图的监听
    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getContext(), "这是第" + position + "张轮播图", Toast.LENGTH_SHORT).show();
        Log.i("tag", "你点了第" + position + "张轮播图");
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Picasso.with(context).load((int) path).into(imageView);
        }
    }
}

