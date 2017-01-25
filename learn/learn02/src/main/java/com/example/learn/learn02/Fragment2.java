package com.example.learn.learn02;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {
    private static final String TAG="Fragment2";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank2, container, false);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
        Button btn=(Button) getActivity().findViewById(R.id.frag2_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                TextView text=(TextView)getActivity().findViewById(R.id.frag1_text);
                if(null==text){
                    Log.d(TAG, "onClick:  text view is null");
                    return ;
                }

                if(text.getText()==null){
                    Log.d(TAG, "onClick: frag1_text is null");
                    return;
                }
                String textString= text.getText().toString();
                if(textString.isEmpty()){
                    Log.d(TAG, "onClick: text is empty");
                    return;
                }else{
                    Log.d(TAG, "onClick: text String"+textString);
                }
                Toast.makeText(getActivity(), textString, Toast.LENGTH_LONG).show();
            }
        });

        SharedPreferences sharedPref=
                getActivity().getPreferences(Context.MODE_PRIVATE);
        int sharedValue=sharedPref.getInt(getString(R.string.shared_preferences_key_1), 102);
        Log.d(TAG, "onActivityCreated: sharedValue:"+sharedValue);
        SharedPreferences.Editor edit=sharedPref.edit();
        edit.putInt(getString(R.string.shared_preferences_key_2), sharedValue*sharedValue);
        edit.commit();

    }


    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume: "+this.toString());
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause: "+this.toString());
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach(){
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }


}
