package com.example.equipmentinspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.equipmentinspection.BaseApp;
import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.database.repository.EquipmentRepository;
import com.example.equipmentinspection.util.OnAsyncEventListener;

public class EquipmentDetailsViewModel extends AndroidViewModel {
    private EquipmentRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<EquipmentEntity> observableEquipment;

    public EquipmentDetailsViewModel(@NonNull Application application,
                                     final String idEquipment, EquipmentRepository equipmentRepository) {
        super(application);

        repository = equipmentRepository;

        observableEquipment = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableEquipment.setValue(null);

        LiveData<EquipmentEntity> equipment = repository.getEquipment(idEquipment);

        // observe the changes of the client entity from the database and forward them
        observableEquipment.addSource(equipment, observableEquipment::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String idEquipment;

        private final EquipmentRepository repository;

        public Factory(@NonNull Application application, String idEquipment) {
            this.application = application;
            this.idEquipment = idEquipment;
            repository = ((BaseApp) application).getEquipmentRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new EquipmentDetailsViewModel(application, idEquipment, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<EquipmentEntity> getEquipment() {
        return observableEquipment;
    }

    public void updateEquipment(EquipmentEntity equipment, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getEquipmentRepository()
                .update(equipment, callback);
    }

    public void deleteEquipment(EquipmentEntity equipment, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getEquipmentRepository()
                .delete(equipment, callback);
    }
}
