package xl1712114143.lvyouzhinan_client.LogAndReg;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xl1712114143.lvyouzhinan_client.R;

public class Fragment_reg extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View reg = inflater.inflate(R.layout.activity_reg, container, false);

        reg.findViewById(R.id.regButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText editText1 = getView().findViewById(R.id.regName);
                EditText editText2 = getView().findViewById(R.id.regNum);
                EditText editText3 = getView().findViewById(R.id.regPasswd);
                String regname = editText1.getText().toString();
                String regnum = editText2.getText().toString();
                String regpasswd = editText3.getText().toString();
                if (regname.isEmpty()){
                    Toast.makeText(getContext(), "用户名不为空！", Toast.LENGTH_SHORT).show();
                } else if (regnum.length()!=11){
                    Toast.makeText(getContext(), "请输入正确的11位手机号！", Toast.LENGTH_SHORT).show();
                } else if (regpasswd.isEmpty()){
                    Toast.makeText(getContext(), "密码不为空！", Toast.LENGTH_SHORT).show();
                }
                else{
                    register(regname, regnum,regpasswd);
                        Toast.makeText(getContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                        editText1.setText("");
                        editText2.setText("");
                        editText3.setText("");
                }
            }
        });
        reg.findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1 = getView().findViewById(R.id.regName);
                EditText editText2 = getView().findViewById(R.id.regNum);
                EditText editText3 = getView().findViewById(R.id.regPasswd);
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
            }
        });
        return reg;
    }

    //向数据库插入数据
    public boolean register(String name, String phone, String passwd) {

        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("phone", phone)
                .add("password", passwd)
                .build();
        final Request request = new Request.Builder()
                .url("http://192.168.199.155:8080/user/add")//请求的url
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
        return true;
    }
}
