package com.example.equipmentinspection.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.database.entity.EquipmentEntity;
//import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.example.equipmentinspection.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<EquipmentEntity> equipmentData;
    private RecyclerViewItemClickListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;

        ViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> listener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            listener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        EquipmentEntity item = equipmentData.get(position);
        holder.textView.setText(item.toString());
    }

    @Override
    public int getItemCount() {
        if (equipmentData != null) {
            return equipmentData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<EquipmentEntity> data) {
        if (this.equipmentData == null) {
            this.equipmentData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return RecyclerAdapter.this.equipmentData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

                    if (RecyclerAdapter.this.equipmentData instanceof EquipmentEntity) {
                        //getNameEquipment ?
                        return (RecyclerAdapter.this.equipmentData.get(oldItemPosition)).getNameEquipment().equals(
                                (data.get(newItemPosition)).getNameEquipment());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (RecyclerAdapter.this.equipmentData instanceof EquipmentEntity) {
                        EquipmentEntity newEquipment = data.get(newItemPosition);
                        EquipmentEntity oldEquipment = RecyclerAdapter.this.equipmentData.get(newItemPosition);
                        return Objects.equals(newEquipment.getNameEquipment(), oldEquipment.getNameEquipment())
                                && Objects.equals(newEquipment.getPriceEquipment(), oldEquipment.getPriceEquipment())
                                && Objects.equals(newEquipment.getWarrantyDateEquipment(), oldEquipment.getWarrantyDateEquipment())
                                && Objects.equals(newEquipment.getLastInspectorEquipment(), oldEquipment.getLastInspectorEquipment())
                                && Objects.equals(newEquipment.getLastInspectionDateEquipment(), oldEquipment.getLastInspectionDateEquipment())
                                && Objects.equals(newEquipment.getNextInspectionDateEquipment(), oldEquipment.getNextInspectionDateEquipment())
                                && Objects.equals(newEquipment.getStatusEquipment(), oldEquipment.getStatusEquipment());
                    }
                    return false;
                }
            });
            this.equipmentData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}