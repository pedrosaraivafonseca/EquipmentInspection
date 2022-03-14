package com.example.equipmentinspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.example.equipmentinspection.database.repository.InspectionRepository;
import com.example.equipmentinspection.util.OnAsyncEventListener;

public class InspectionDetailsViewModel extends AndroidViewModel {
    private InspectionRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<InspectionEntity> observableInspection;

    public InspectionDetailsViewModel(@NonNull Application application,
                                     final int idInspection, InspectionRepository inspectionRepository) {
        super(application);

        repository = inspectionRepository;

        applicationContext = application.getApplicationContext();

        observableInspection = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableInspection.setValue(null);

        LiveData<InspectionEntity> inspection = repository.getInspection(idInspection, applicationContext);

        // observe the changes of the client entity from the database and forward them
        observableInspection.addSource(inspection, observableInspection::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int idInspection;

        private final InspectionRepository repository;

        public Factory(@NonNull Application application, int idInspection) {
            this.application = application;
            this.idInspection = idInspection;
            repository = InspectionRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new InspectionDetailsViewModel(application, idInspection, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<InspectionEntity> getInspection() {
        return observableInspection;
    }

    public void createInspection(InspectionEntity inspection, OnAsyncEventListener callback) {
        repository.insert(inspection, callback, applicationContext);
    }

    public void updateInspection(InspectionEntity inspection, OnAsyncEventListener callback) {
        repository.update(inspection, callback, applicationContext);
    }

    public void deleteInspection(InspectionEntity inspection, OnAsyncEventListener callback) {
        repository.delete(inspection, callback, applicationContext);
    }
}
