package com.example.equipmentinspection.ui.equipment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.adapter.RecyclerAdapter;
import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.util.RecyclerViewItemClickListener;
import com.example.equipmentinspection.viewmodel.EquipmentListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class EquipmentFragment extends Fragment {

    private List<EquipmentEntity> equipmentEntityList;
    private RecyclerAdapter<EquipmentEntity> recyclerAdapter;
    private EquipmentListViewModel equipmentListViewModel;
    private FloatingActionButton addButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addButton = (FloatingActionButton) getActivity().findViewById(R.id.main_add_buttom);
        RecyclerView recyclerView = getActivity().findViewById(R.id.equipment_recyclerView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EquipmentAdd.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipment, container, false);

//        Fragment fragment = new EquipmentDetails();
//        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
//        Bundle bundle = new Bundle();

        ArrayList<String> list = new ArrayList<>();
        list.add("AAA");
        list.add("AAA");
        list.add("AAA");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);

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
                intent.putExtra("equipmentId", equipmentEntityList.get(position).getIdEquipment());
                startActivity(intent);
            }
        });
        return view;
    }
}