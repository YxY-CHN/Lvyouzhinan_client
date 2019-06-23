package xl1712114143.lvyouzhinan_client;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private ArrayList<Fragment> data;

    public MyFragmentStatePagerAdapter(FragmentManager fm, Context context, ArrayList<Fragment> data){
        super(fm);
        this.context=context;
        this.data=data;
    }
    @Override
    public Fragment getItem(int position){
        return data.get(position);
    }
    @Override
    public int getCount(){
        return data.size();
    }
}
