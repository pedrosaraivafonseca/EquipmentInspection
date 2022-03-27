package com.example.equipmentinspection.ui.inspection;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.adapter.RecyclerAdapter;
import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.example.equipmentinspection.database.repository.EquipmentRepository;
import com.example.equipmentinspection.database.repository.InspectionRepository;
import com.example.equipmentinspection.ui.equipment.EquipmentAdd;
import com.example.equipmentinspection.ui.equipment.EquipmentDetails;
import com.example.equipmentinspection.util.OnAsyncEventListener;
import com.example.equipmentinspection.util.RecyclerViewItemClickListener;
import com.example.equipmentinspection.viewmodel.EquipmentListViewModel;
import com.example.equipmentinspection.viewmodel.InspectionListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class InspectionFragment extends Fragment {

    private List<InspectionEntity> inspections;
    private RecyclerAdapter<InspectionEntity> adapter;
    private InspectionListViewModel inspectionVM;
    FloatingActionButton addButton;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inspection, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.inspection_recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        addButton = (FloatingActionButton) view.findViewById(R.id.inspectionfrag_add_buttom);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InspectionAdd.class);
                startActivity(intent);
            }
        });

        inspections = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), InspectionDetails.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("inspectionId", inspections.get(position).getIdInspection());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                createDeleteDialog(position);
            }
        });

        InspectionListViewModel.Factory factory = new InspectionListViewModel.Factory(
                getActivity().getApplication());
        inspectionVM = new InspectionListViewModel(this.getActivity().getApplication(), InspectionRepository.getInstance());
        inspectionVM.getInspections().observe(getViewLifecycleOwner(), inspectionEntities -> {
            if (inspectionEntities != null) {
                inspections = inspectionEntities;
                adapter.setData(inspections);
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void createDeleteDialog(final int position) {
        final InspectionEntity inspection = inspections.get(position);
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        final View view = inflater.inflate(R.layout.row_delete_item, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this.getContext()).create();
        alertDialog.setTitle(getString(R.string.title_activity_delete_inspection));
        alertDialog.setCancelable(false);

        final TextView deleteMessage = view.findViewById(R.id.tv_delete_item);
        deleteMessage.setText(String.format(getString(R.string.inspection_delete_msg), inspection.toString()));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_accept), (dialog, which) -> {
            inspectionVM.deleteInspection(inspection, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Toast toast = Toast.makeText(getActivity(), "Inspection successfully deleted", Toast.LENGTH_LONG);
                    toast.show();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast toast = Toast.makeText(getActivity(), "There was an error", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }
}