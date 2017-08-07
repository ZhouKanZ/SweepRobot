package com.gps.sweeprobot.model.main.view.frag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.model.main.view.activity.IpActivity;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/12 0012
 * @Descriptiong : xxx
 */

public class WelcomeFragment extends Fragment implements View.OnClickListener {

    private View view;
    private String position;
    private int[] resIds = {
            R.mipmap.welcome_1,
            R.mipmap.welcome_2,
            R.mipmap.welcome_3
    };

    public static WelcomeFragment newInstance(String position) {
        
        Bundle args = new Bundle();
        args.putString(WelcomeFragment.class.getSimpleName(),position);
        WelcomeFragment fragment = new WelcomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            String pos = args.getString(WelcomeFragment.class.getSimpleName());
            if (pos != null) {
                position =  pos;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_welcome,container,false);

        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        Button btn_enter = (Button) view.findViewById(R.id.btn_enter);
        Bitmap bitmap = null;
        switch (position){
            case "0":
                bitmap = BitmapFactory.decodeResource(getResources(),resIds[0]);
                break;
            case "1":
                bitmap = BitmapFactory.decodeResource(getResources(),resIds[1]);
                break;
            case "2":
                bitmap = BitmapFactory.decodeResource(getResources(),resIds[2]);
                btn_enter.setVisibility(View.VISIBLE);
                btn_enter.setOnClickListener(this);
                break;
        }

        iv.setImageBitmap(bitmap);
        return view;
    }


    @Override
    public void onClick(View view) {

        IpActivity.startSelf(this.getActivity(),IpActivity.class,null);
        this.getActivity().finish();
    }
}
