package com.example.equipmentinspection.database.repository;

import androidx.lifecycle.LiveData;

import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.example.equipmentinspection.database.firebase.InspectorListLiveData;
import com.example.equipmentinspection.database.firebase.InspectorLiveData;
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
        DatabaseReference reference = FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
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
        FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
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
        DatabaseReference reference = FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("inspectors");
        return new InspectorListLiveData(reference);
    }


    public void update(final InspectorEntity inspector, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("inspector")
                .child(inspector.getIdInspector())
                .updateChildren(inspector.map(), (databadeError, databaseReference) -> {
                    if (databadeError != null){
                        callback.onFailure(databadeError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final InspectorEntity inspector, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("inspector")
                .child(inspector.getIdInspector())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null){
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
