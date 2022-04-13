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
import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.example.equipmentinspection.database.repository.InspectorRepository;
import com.example.equipmentinspection.util.OnAsyncEventListener;

import java.util.List;

public class InspectorDetailsViewModel extends AndroidViewModel{
    private InspectorRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<InspectorEntity> observableInspector;

    public InspectorDetailsViewModel(@NonNull Application application,
                           final String idInspector, InspectorRepository inspectorRepository) {
        super(application);

        repository = inspectorRepository;

        observableInspector = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableInspector.setValue(null);

        LiveData<InspectorEntity> inspector = repository.getInspector(idInspector);

        // observe the changes of the client entity from the database and forward them
        observableInspector.addSource(inspector, observableInspector::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String idInspector;

        private final InspectorRepository repository;

        public Factory(@NonNull Application application, String idInspector) {
            this.application = application;
            this.idInspector = idInspector;
            repository = ((BaseApp) application).getInspectorRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new InspectorDetailsViewModel(application, idInspector, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<InspectorEntity> getInspector() {
        return observableInspector;
    }


    public void updateInspector(InspectorEntity inspector, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getInspectorRepository()
                .update(inspector, callback);
    }

    public void deleteInspector(InspectorEntity inspector, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getInspectorRepository()
                .delete(inspector, callback);
    }
}
