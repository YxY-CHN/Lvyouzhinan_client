package xl1712114143.lvyouzhinan_client;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xl1712114143.lvyouzhinan_client.bean.Article;
import xl1712114143.lvyouzhinan_client.bean.Comment;
import xl1712114143.lvyouzhinan_client.bean.MyAdapter;

public class ShowActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView content;
    private ArrayAdapter<String> adapters;
    private ListView listView;
    private List<Comment> commentList = new ArrayList<>();
    public List<String> dates = new ArrayList<>();
    private Button button;
    public EditText editText;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_show);


        imageView = findViewById(R.id.imageview);
        content = findViewById(R.id.content);
        button=findViewById(R.id.cbutton);
        editText=findViewById(R.id.editcomment);



        String image = getIntent().getStringExtra("image");
        String show = getIntent().getStringExtra("content");
        int articleid = getIntent().getIntExtra("id", 1);
        System.out.println(show);
        Picasso.with(this).load(image).resize(800, 480).into(imageView);
        content.setText(show);
        listView = findViewById(R.id.comment);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://192.168.199.155:8080/comment/getcomment").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("---------------------------------------error---------------------------------");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String result = body.string();
                Gson gson = new Gson();
                commentList = gson.fromJson(result, new TypeToken<List<Comment>>() {
                }.getType());
                int n = 0;
                for (i = 0; i < commentList.size(); i++) {
                    if (commentList.get(i).getArticle_id().equals(articleid)) {
                        dates.add(commentList.get(i).getMessage());
                        n++;
                    }
                }

                runOnUiThread(() -> {
                    adapters = new ArrayAdapter<>(ShowActivity.this, android.R.layout.simple_expandable_list_item_1, dates);
                    listView.setAdapter(adapters);
                });

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=editText.getText().toString();
                String article_id=Integer.toString(articleid);
                OkHttpClient okHttpClient=new OkHttpClient();
                FormBody formBody = new FormBody.Builder()
                        .add("article_id",article_id)
                        .add("message", message)
                        .build();
                editText.setText("");
                final Request request = new Request.Builder()
                        .url("http://192.168.199.155:8080/comment/add")//请求的url
                        .post(formBody)
                        .build();
                //创建/Call
                Call call = okHttpClient.newCall(request);
                //加入队列 异步操作
                call.enqueue(new Callback() {
                    //请求错误回调方法
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("连接失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.code()==200) {
                            System.out.println(response.body().string());
                        }
                    }
                });
            }
        });
    }
}

