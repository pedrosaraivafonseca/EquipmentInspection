package com.example.equipmentinspection.ui.inspection;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.adapter.RecyclerAdapter;
import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.example.equipmentinspection.ui.equipment.EquipmentAdd;
import com.example.equipmentinspection.ui.equipment.EquipmentDetails;
import com.example.equipmentinspection.util.RecyclerViewItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class InspectionFragment extends Fragment {

    private RecyclerAdapter<InspectionEntity> recyclerAdapter;
    FloatingActionButton addButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = getActivity().findViewById(R.id.equipment_recyclerView);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inspection, container, false);

        addButton = (FloatingActionButton) view.findViewById(R.id.inspectionfrag_add_buttom);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InspectionAdd.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.equipment_recyclerView);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), EquipmentDetails.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
//                intent.putExtra("inspectionId", inspectionEntityList.get(position).getIdEquipment());
//                startActivity(intent);
            }
        });
        return view;
    }
}