package com.example.brianb.demo1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by BrianB on 29-Apr-17.
 */
public class ProjectsFragment extends Fragment implements View.OnClickListener{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.projects_tab, container, false);

        Button btn1 = (Button)v.findViewById(R.id.addproperty);
        Button btn2 = (Button)v.findViewById(R.id.myproperties);
        Button btn3 = (Button)v.findViewById(R.id.buttonListings);
        Button btn4 = (Button)v.findViewById(R.id.msgbutton);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        //Add a project -> Activity add project page  -> On AddButtonClick -> Form for add details(ImageView, Address, Units, unit name) -Save> List with projects added
        // -> Activity Choose type of Property i.e Land or House -> House Activity page
    return v;
    }

   @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addproperty:
                Intent intent = new Intent(getActivity(), ProjectForm.class);
                startActivity(intent);
                break;
            case R.id.myproperties:
                Intent il = new Intent(getActivity(), DisplayProject.class);
                startActivity(il);
                break;
            case R.id.buttonListings:
                startActivity(new Intent(getActivity(), ListingsMain.class));
                break;
            case R.id.msgbutton:
                startActivity(new Intent(getActivity(), ConversationListActivity.class));
        }
    }
}
