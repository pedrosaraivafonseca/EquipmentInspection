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

import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.example.equipmentinspection.database.repository.InspectionRepository;

public class InspectionListViewModel extends AndroidViewModel {

    private InspectionRepository repository;

    private Context applicationContext;

    private final MediatorLiveData<List<InspectionEntity>> observableInspections;

    public InspectionListViewModel(@NonNull Application application, InspectionRepository inspectionRepository) {
        super(application);

        repository = inspectionRepository;

        applicationContext = application.getApplicationContext();

        observableInspections = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableInspections.setValue(null);

        LiveData<List<InspectionEntity>> inspections = repository.getAllInspection(applicationContext);

        // observe the changes of the entities from the database and forward them
        observableInspections.addSource(inspections, observableInspections::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final InspectionRepository inspectionRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            inspectionRepository = InspectionRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new InspectionListViewModel(application, inspectionRepository);
        }
    }

    /**
     * Expose the LiveData inspectionEntities query so the UI can observe it.
     */
    public LiveData<List<InspectionEntity>> getInspections() {
        return observableInspections;
    }
}
