package com.google.android.gms.samples.vision.face.facetracker;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.util.Log;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Choose extends Fragment {


    public Fragment_Choose() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fragment__choose, container, false);

        View rootView = inflater.inflate(R.layout.fragment_fragment__choose, container, false);
        Button b = (Button)rootView.findViewById(R.id.btn1);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.w("btn1","I am clicked");
                Intent intent = new Intent(getActivity(), FaceTrackerActivity.class);
                //((NavigationActivity) getActivity()).startActivity(intent);
                getActivity().startActivity(intent);
            }
        });
        return rootView;

    }

  /*  public void goToCamera()
    {
        Intent intent = new Intent(getActivity(), FaceTrackerActivity.class);
        getActivity().startActivity(intent);


    }*/

}
