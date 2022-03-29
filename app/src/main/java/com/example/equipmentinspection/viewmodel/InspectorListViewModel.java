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
import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.example.equipmentinspection.database.repository.InspectorRepository;
import com.example.equipmentinspection.util.OnAsyncEventListener;

public class InspectorListViewModel extends AndroidViewModel {

    private InspectorRepository repository;

    private Context applicationContext;

    private final MediatorLiveData<List<InspectorEntity>> observableInspectors;

    public InspectorListViewModel(@NonNull Application application, InspectorRepository inspectorRepository) {
        super(application);

        repository = inspectorRepository;

        applicationContext = application.getApplicationContext();

        observableInspectors = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableInspectors.setValue(null);

        LiveData<List<InspectorEntity>> inspectors = repository.getAllInspector(applicationContext);

        // observe the changes of the entities from the database and forward them
        observableInspectors.addSource(inspectors, observableInspectors::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final InspectorRepository inspectorRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            inspectorRepository = InspectorRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new InspectorListViewModel(application, inspectorRepository);
        }
    }

    /**
     * Expose the LiveData inspectorEntities query so the UI can observe it.
     */
    public LiveData<List<InspectorEntity>> getInspector() {
        return observableInspectors;
    }

    public void deleteInspector(InspectorEntity inspector, OnAsyncEventListener callback) {
        repository.delete(inspector, callback, applicationContext);
    }
}
