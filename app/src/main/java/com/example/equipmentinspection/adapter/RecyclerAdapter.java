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
            holder.mTextView.setText(((EquipmentEntity) item).getNameEquipment());
        if (item.getClass().equals(InspectorEntity.class))
            holder.mTextView.setText(((InspectorEntity) item).getFirstNameInspector() + " " + ((InspectorEntity) item).getNameInspector());
        if(item.getClass().equals(InspectionEntity.class))
            holder.mTextView.setText("Inspection #" + ((InspectionEntity) item).getIdInspection() + " " + ((InspectionEntity) item).getDateInspection());
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
                        return ((EquipmentEntity) mData.get(oldItemPosition)).getIdEquipment().equals(
                                (((EquipmentEntity) data.get(newItemPosition)).getIdEquipment()));
                    }
                    if (mData instanceof InspectorEntity) {
                        return ((InspectorEntity) mData.get(oldItemPosition)).getIdInspector() ==
                                (((InspectorEntity) data.get(newItemPosition)).getIdInspector());
                    }
                    if (mData instanceof InspectionEntity) {
                        return ((InspectionEntity) mData.get(oldItemPosition)).getIdInspection() ==
                                (((InspectionEntity) data.get(newItemPosition)).getIdInspection());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof EquipmentEntity) {
                        EquipmentEntity newEquipment = (EquipmentEntity) data.get(newItemPosition);
                        EquipmentEntity oldEquipment = (EquipmentEntity) mData.get(newItemPosition);
                        return newEquipment.getIdEquipment().equals(oldEquipment.getIdEquipment())
                                && Objects.equals(newEquipment.getNameEquipment(), oldEquipment.getNameEquipment())
                                && Objects.equals(newEquipment.getPriceEquipment(), oldEquipment.getPriceEquipment())
                                && newEquipment.getLastInspectionDateEquipment().equals(oldEquipment.getNextInspectionDateEquipment())
                                && newEquipment.getLastInspectorEquipment().equals(oldEquipment.getLastInspectorEquipment())
                                && newEquipment.getStatusEquipment().equals(oldEquipment.getStatusEquipment())
                                && newEquipment.getPurchaseDateEquipment().equals(oldEquipment.getPurchaseDateEquipment())
                                && newEquipment.getWarrantyDateEquipment().equals(oldEquipment.getWarrantyDateEquipment())
                                && newEquipment.getNextInspectionDateEquipment().equals(oldEquipment.getNextInspectionDateEquipment());
                    }

                    if (mData instanceof InspectorEntity) {
                        InspectorEntity newInspector = (InspectorEntity) data.get(newItemPosition);
                        InspectorEntity oldInspector = (InspectorEntity) mData.get(newItemPosition);
                        return newInspector.getIdInspector().equals(oldInspector.getIdInspector())
                                && Objects.equals(newInspector.getEmailInspector(), oldInspector.getEmailInspector())
                                && Objects.equals(newInspector.getFirstNameInspector(), oldInspector.getFirstNameInspector())
                                && Objects.equals(newInspector.getNameInspector(), oldInspector.getNameInspector())
                                && newInspector.getPasswordInspector().equals(oldInspector.getPasswordInspector());
                    }

                    if (mData instanceof InspectionEntity) {
                        InspectionEntity newInspection = (InspectionEntity) data.get(newItemPosition);
                        InspectionEntity oldInspection = (InspectionEntity) mData.get(newItemPosition);
                        return newInspection.getIdInspection().equals(oldInspection.getIdInspection())
                                && Objects.equals(newInspection.getDateInspection(), oldInspection.getDateInspection())
                                && Objects.equals(newInspection.getIdInspectorInspection(), oldInspection.getIdInspectorInspection())
                                && Objects.equals(newInspection.getIdEquipmentInspection(), oldInspection.getIdEquipmentInspection())
                                && newInspection.getStatusInspection().equals(oldInspection.getStatusInspection());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}