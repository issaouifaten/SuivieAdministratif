package com.example.suivieadministratif.ui.workflow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.ui.notifications.NotificationsViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class WorkflowFragment extends Fragment {

    private WorkfowViewModel workfowViewModel  ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        workfowViewModel = ViewModelProviders.of(this).get(WorkfowViewModel.class);
        View root  =  inflater . inflate ( R.layout.fragment_workfow , container, false) ;
        final TextView textView = root.findViewById(R.id.text_workflow);

        workfowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
}