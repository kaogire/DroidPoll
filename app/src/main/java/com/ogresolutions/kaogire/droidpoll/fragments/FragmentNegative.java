package com.ogresolutions.kaogire.droidpoll.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ogresolutions.kaogire.droidpoll.R;
import com.ogresolutions.kaogire.droidpoll.activities.MyReader;
import com.ogresolutions.kaogire.droidpoll.model.ChatMessage;
import com.ogresolutions.kaogire.droidpoll.model.MyAdapter;

import java.util.ArrayList;

/**
 * Created by Njuguna on 4/22/2016.
 */
public class FragmentNegative extends Fragment {
    ArrayList myArray;

    public FragmentNegative(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_negative, container, false);
        ListView lvSMS   = (ListView) view.findViewById(R.id.lvNeg);
        ArrayList<ChatMessage> myArray = MyReader.smsNegative;
        MyAdapter adapter = new MyAdapter(getActivity().getBaseContext(), myArray);
        lvSMS.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}

