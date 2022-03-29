package com.example.equipmentinspection.viewmodel;

import android.app.Application;
import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.database.repository.EquipmentRepository;
import com.example.equipmentinspection.util.OnAsyncEventListener;

public class EquipmentListViewModel extends AndroidViewModel {

    private EquipmentRepository repository;

    private Context applicationContext;

    private final MediatorLiveData<List<EquipmentEntity>> observableEquipments;

    public EquipmentListViewModel(@NonNull Application application, EquipmentRepository equipmentRepository) {
        super(application);

        repository = equipmentRepository;

        applicationContext = application.getApplicationContext();

        observableEquipments = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableEquipments.setValue(null);

        LiveData<List<EquipmentEntity>> equipments = repository.getAllEquipment(applicationContext);

        // observe the changes of the entities from the database and forward them
        observableEquipments.addSource(equipments, observableEquipments::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final EquipmentRepository equipmentRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            equipmentRepository = EquipmentRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new EquipmentListViewModel(application, equipmentRepository);
        }
    }

    /**
     * Expose the LiveData equipmentEntities query so the UI can observe it.
     */
    public LiveData<List<EquipmentEntity>> getEquipments() {
        return observableEquipments;
    }

    public void deleteEquipment(EquipmentEntity equipment, OnAsyncEventListener callback) {
        repository.delete(equipment, callback, applicationContext);
    }
}
