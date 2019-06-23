package xl1712114143.lvyouzhinan_client.LogAndReg;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xl1712114143.lvyouzhinan_client.MainActivity;
import xl1712114143.lvyouzhinan_client.R;
import xl1712114143.lvyouzhinan_client.bean.User;

import static android.content.ContentValues.TAG;

public class Fragment_log extends Fragment {
    private EditText id;
    private EditText passwd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_log, container, false);
        id = view.findViewById(R.id.logNum);
        passwd = view.findViewById(R.id.logPasswd);
        view.findViewById(R.id.logButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText id = getView().findViewById(R.id.logNum);
                EditText passwd = getView().findViewById(R.id.logPasswd);
                String userphone = id.getText().toString();
                String userpasswd = passwd.getText().toString();
                if (userphone.isEmpty()){
                    Toast.makeText(getContext(), "手机号不为空！", Toast.LENGTH_SHORT).show();
                } else if (userphone.length()!=11){
                    Toast.makeText(getContext(), "请输入正确的11位手机号！", Toast.LENGTH_SHORT).show();
                }else if (userpasswd.isEmpty()){
                    Toast.makeText(getContext(), "密码不为空！", Toast.LENGTH_SHORT).show();
                } else
                   login(userphone, userpasswd);
            }
        });
        view.findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id.setText("");
                passwd.setText("");
            }
        });
        return view;
    }


    private boolean login(String phone,String password) {
        //把参数传进Map中
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("phone",phone);
        paramsMap.put("password",password);
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            //追加表单信息
            builder.add(key, paramsMap.get(key));
        }
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody formBody=builder.build();
        Request request=new   Request.Builder().url("http://192.168.199.155:8080/user/getuser").build();
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
                List<User> userList=gson.fromJson(result,new TypeToken<List<User>>(){}.getType());
                for (User user:userList){
                    if (phone.equals(user.getPhone())){
                        if (password.equals(user.getPassword())){
                            getActivity().runOnUiThread(() -> {
                                Toast.makeText(getContext(), "欢迎您：" + user.getName(), Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getContext(),MainActivity.class);
                                startActivity(intent);
                            });
                        } else {
                            getActivity().runOnUiThread(() -> {
                                Toast.makeText(getContext(), "用户信息错误，请重新输入！", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
                }
            }
        });
        return false;
    }
}
