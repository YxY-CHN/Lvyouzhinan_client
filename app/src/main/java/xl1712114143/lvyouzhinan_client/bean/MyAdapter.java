package xl1712114143.lvyouzhinan_client.bean;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import xl1712114143.lvyouzhinan_client.R;

public class MyAdapter extends BaseAdapter implements Filterable {
    private List<Article> Datas;
    List<Article> backData;
    private Context mContext;
    MyFilter mFilter;

    public MyAdapter(List<Article> datas, Context mContext) {
        Datas = datas;
        this.mContext = mContext;
        backData = Datas;
    }

    /**
     * 返回item的个数
     *
     * @return
     */
    @Override
    public int getCount() {
        return Datas.size();
    }

    /**
     * 返回每一个item对象
     *
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return Datas.get(i);
    }

    /**
     * 返回每一个item的id
     *
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * 暂时不做优化处理，后面会专门整理BaseAdapter的优化
     *
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.show_item, viewGroup, false);
        ImageView imageView = view.findViewById(R.id.imageview);
        TextView textView1 = view.findViewById(R.id.name);
        TextView textView2 = view.findViewById(R.id.brief);
        Picasso.with(mContext).load(Datas.get(i).getImage()).into(imageView);
        textView1.setText(Datas.get(i).getTitle());
        textView2.setText(Datas.get(i).getArea());
        return view;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new MyFilter();
        }
        return mFilter;
    }

//  搜索模块
    class MyFilter extends Filter {
        //我们在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            List<Article> list;
            if (TextUtils.isEmpty(charSequence)) {//当过滤的关键字为空的时候，我们则显示所有的数据
                list = backData;
            } else {//否则把符合条件的数据对象添加到集合中
                list = new ArrayList<>();
                for (Article article : backData) {
                    if (article.getTitle().contains(charSequence) || article.getArea().contains(charSequence)) {
                        list.add(article);
                    }

                }
            }
            result.values = list; //将得到的集合保存到FilterResults的value变量中
            result.count = list.size();//将集合的大小保存到FilterResults的count变量中

            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            Datas = (List<Article>) filterResults.values;
            if (filterResults.count > 0) {
                notifyDataSetChanged();//通知数据发生了改变
            } else {
                notifyDataSetInvalidated();//通知数据失效
            }
        }
    }

}


