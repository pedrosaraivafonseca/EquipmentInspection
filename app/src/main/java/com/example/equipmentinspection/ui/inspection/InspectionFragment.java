package com.example.equipmentinspection.ui.inspection;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.ui.equipment.EquipmentAdd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class InspectionFragment extends Fragment {

    private FloatingActionButton addButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addButton = (FloatingActionButton) getActivity().findViewById(R.id.main_add_buttom);
        RecyclerView recyclerView = getActivity().findViewById(R.id.equipment_recyclerView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InspectionAdd.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inspection, container, false);
    }
}