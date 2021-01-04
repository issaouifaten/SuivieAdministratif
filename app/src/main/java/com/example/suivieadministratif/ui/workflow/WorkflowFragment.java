package com.example.suivieadministratif.ui.workflow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.WorkFlowTask;


import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WorkflowFragment extends Fragment {

    private WorkfowViewModel workfowViewModel  ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        workfowViewModel = ViewModelProviders.of(this).get(WorkfowViewModel.class);
        View root  =  inflater . inflate ( R.layout.fragment_workfow , container, false) ;


        final RecyclerView   rv_list_work_flow  = root.findViewById(R.id.rv_list_workflow) ;

        rv_list_work_flow.setHasFixedSize(true);
        rv_list_work_flow.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        final ProgressBar pb  = root.findViewById(R.id.progress_bar) ;
        workfowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                WorkFlowTask  workFlowTask = new WorkFlowTask(getActivity() ,new Date() , new Date(), rv_list_work_flow , pb) ;
                workFlowTask.execute();

            }
        });

        return root;
    }
}