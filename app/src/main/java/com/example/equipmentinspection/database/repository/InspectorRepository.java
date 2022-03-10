package com.example.equipmentinspection.database.repository;

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

//    public LiveData<InspectorEntity> getInspector(final int id, Context context) {
//        return AppDatabase.getInstance(context).InspectorDao().getById(id);
//    }
//
//    public LiveData<InspectorEntity> getInspector(final String email, final String password, Context context) {
//        return AppDatabase.getInstance(context).InspectorDao().getByLogin(email, password);
//    }
//
//    public LiveData<List<InspectorEntity>> getAllInspector(Context context) {
//        return AppDatabase.getInstance(context).InspectorDao().getAll();
//    }
//
//    public void insert(final InspectorEntity inspector, OnAsyncEventListener callback, Context context) {
//        new InspectorCreate(context, callback).execute(inspector);
//    }
//
//    public void update(final InspectorEntity inspector, OnAsyncEventListener callback, Context context) {
//        new InspectorUpdate(context, callback).execute(inspector);
//    }
//
//    public void delete(final InspectorEntity inspector, OnAsyncEventListener callback, Context context) {
//        new InspectorDelete(context, callback).execute(inspector);
//    }
}
