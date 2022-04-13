package com.example.equipmentinspection.database.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.equipmentinspection.BaseApp;
import com.example.equipmentinspection.database.AppDatabase;
import com.example.equipmentinspection.database.async.InspectorCreate;
import com.example.equipmentinspection.database.async.InspectorDelete;
import com.example.equipmentinspection.database.async.InspectorUpdate;
import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.example.equipmentinspection.ui.mgmt.RegisterActivity;
import com.example.equipmentinspection.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class InspectorRepository {

    private static InspectorRepository instance;

    private InspectorRepository() {}

    public static InspectorRepository getInstance() {
        if (instance == null) {
            synchronized (InspectorRepository.class) {
                if (instance == null) {
                    instance = new InspectorRepository();
                }
            }
        }
        return instance;
    }


    public LiveData<InspectorEntity> getInspector(final String inspectorId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("inspector")
                .child(inspectorId);
        return new InspectorLiveData(reference);
    }


    public void signIn(final String mail, final String password, final OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(listener);
    }

//    public LiveData<InspectorEntity> getInspectorByLogin(final String mail, String password, Application application) {
//        return ((BaseApp) application).getDatabase().inspectorDao().getByLogin(mail, password);
//    }

    public void register(final InspectorEntity inspector, final OnAsyncEventListener callback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                inspector.getEmailInspector(),
                inspector.getPasswordInspector()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                inspector.setIdInspector(FirebaseAuth.getInstance().getCurrentUser().getUid());
                insert(inspector, callback);
            } else {
                callback.onFailure(task.getException());
            }
        });
    }

    private void insert(final InspectorEntity client, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(client, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                        FirebaseAuth.getInstance().getCurrentUser().delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        callback.onFailure(null);
                                    } else {
                                        callback.onFailure(task.getException());
                                                task.getException();
                                    }
                                });
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public LiveData<List<InspectorEntity>> getAllInspector() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("inspectors");
        return new InspectorLiveData();
    }

    public void insert(final InspectorEntity inspector, OnAsyncEventListener callback, Context context) {
        new InspectorCreate(context, callback).execute(inspector);
    }

    public void update(final InspectorEntity inspector, OnAsyncEventListener callback, Context context) {
        new InspectorUpdate(context, callback).execute(inspector);
    }

    public void delete(final InspectorEntity inspector, OnAsyncEventListener callback, Context context) {
        new InspectorDelete(context, callback).execute(inspector);
    }
}
