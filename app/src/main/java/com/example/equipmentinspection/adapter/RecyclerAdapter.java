package com.example.equipmentinspection.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.example.equipmentinspection.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextView;
        ViewHolder(TextView textView) {
            super(textView);
            mTextView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        T item = mData.get(position);
        if (item.getClass().equals(EquipmentEntity.class))
            holder.mTextView.setText(((EquipmentEntity) item).getName());
        if (item.getClass().equals(InspectorEntity.class))
            holder.mTextView.setText(((InspectorEntity) item).getFirstName() + " " + ((InspectorEntity) item).getLastName());
        if(item.getClass().equals(InspectionEntity.class))
            holder.mTextView.setText("Inspection #" + ((InspectionEntity) item).getId() + " " + ((InspectionEntity) item).getDate());
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<T> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof EquipmentEntity) {
                        return ((EquipmentEntity) mData.get(oldItemPosition)).getId().equals(
                                (((EquipmentEntity) data.get(newItemPosition)).getId()));
                    }
                    if (mData instanceof InspectorEntity) {
                        return ((InspectorEntity) mData.get(oldItemPosition)).getId() ==
                                (((InspectorEntity) data.get(newItemPosition)).getId());
                    }
                    if (mData instanceof InspectionEntity) {
                        return ((InspectionEntity) mData.get(oldItemPosition)).getId() ==
                                (((InspectionEntity) data.get(newItemPosition)).getId());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof EquipmentEntity) {
                        EquipmentEntity newEquipment = (EquipmentEntity) data.get(newItemPosition);
                        EquipmentEntity oldEquipment = (EquipmentEntity) mData.get(newItemPosition);
                        return newEquipment.getId().equals(oldEquipment.getId())
                                && Objects.equals(newEquipment.getName(), oldEquipment.getName())
                                && Objects.equals(newEquipment.getPrice(), oldEquipment.getPrice())
                                && newEquipment.getLastInspectionDate().equals(oldEquipment.getNextInspectionDate())
                                && newEquipment.getLastInspector().equals(oldEquipment.getLastInspector())
                                && newEquipment.getStatus().equals(oldEquipment.getStatus())
                                && newEquipment.getPurchaseDate().equals(oldEquipment.getPurchaseDate())
                                && newEquipment.getWarrantyDate().equals(oldEquipment.getWarrantyDate())
                                && newEquipment.getNextInspectionDate().equals(oldEquipment.getNextInspectionDate());
                    }

                    if (mData instanceof InspectorEntity) {
                        InspectorEntity newInspector = (InspectorEntity) data.get(newItemPosition);
                        InspectorEntity oldInspector = (InspectorEntity) mData.get(newItemPosition);
                        return newInspector.getId().equals(oldInspector.getId())
                                && Objects.equals(newInspector.getEmail(), oldInspector.getEmail())
                                && Objects.equals(newInspector.getFirstName(), oldInspector.getFirstName())
                                && Objects.equals(newInspector.getLastName(), oldInspector.getLastName())
                                && newInspector.getPassword().equals(oldInspector.getPassword());
                    }

                    if (mData instanceof InspectionEntity) {
                        InspectionEntity newInspection = (InspectionEntity) data.get(newItemPosition);
                        InspectionEntity oldInspection = (InspectionEntity) mData.get(newItemPosition);
                        return newInspection.getId().equals(oldInspection.getId())
                                && Objects.equals(newInspection.getDate(), oldInspection.getDate())
                                && Objects.equals(newInspection.getIdInspector(), oldInspection.getIdInspector())
                                && Objects.equals(newInspection.getIdEquipment(), oldInspection.getIdEquipment())
                                && newInspection.getStatus().equals(oldInspection.getStatus());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}