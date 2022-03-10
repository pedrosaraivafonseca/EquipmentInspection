package com.example.equipmentinspection.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.database.entity.InspectorEntity;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addInspector(final AppDatabase db, final String name, final String firstName,
                                  final String email, final String password) {
        InspectorEntity inspector = new InspectorEntity(name, firstName, email, password);
        db.inspectorDao().insert(inspector);
    }

//    private static void addInspection(final AppDatabase db, final String name, final String firstName,
//                                      final String email, final String password) {
//        InspectorEntity inspector = new InspectorEntity(name, firstName, email, password);
//        db.inspectorDao().insert(inspector);
//    }

//    private static void addEquipment(final AppDatabase db, final String name, final String firstName,
//                                     final String email, final String password) {
//        InspectorEntity inspector = new InspectorEntity(name, firstName, email, password);
//        db.inspectorDao().insert(inspector);
//    }

    private static void populateWithTestData(AppDatabase db) {
        db.equipmentDao().deleteAll();

        addInspector(db, "Platini", "Michel", "michel.platini@fifa.com", "michel");
        addInspector(db, "Blatter", "Sepp", "sepp.blatter@fifa.com", "sepp");
        addInspector(db, "Schwarz", "Ebbe", "ebbe.schwartz@fifa.com", "ebbe");
        addInspector(db, "Ceferin", "Aleksander", "aleksander.ceferin@fifa.com", "aleksander");
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}
