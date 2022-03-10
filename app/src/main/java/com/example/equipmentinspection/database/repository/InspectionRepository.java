package com.example.equipmentinspection.database.repository;

public class InspectionRepository {

    private static InspectionRepository instance;

    private InspectionRepository() {}

    public static InspectionRepository getInstance() {
        if (instance == null) {
            synchronized (InspectionRepository.class) {
                if (instance == null) {
                    instance = new InspectionRepository();
                }
            }
        }
        return instance;
    }

//    public LiveData<InspectionEntity> getInspection(final int id, Context context) {
//        return AppDatabase.getInstance(context).InspectionDao().getById(id);
//    }
//
//    public LiveData<InspectionEntity> getInspection(final int idInspector, Context context) {
//        return AppDatabase.getInstance(context).InspectionDao().getByInspector(id);
//    }
//
//    public LiveData<InspectionEntity> getInspection(final int idInspection, Context context) {
//        return AppDatabase.getInstance(context).InspectionDao().getByInspector(id);
//    }
//
//    public LiveData<InspectionEntity> getInspection(final String status, Context context) {
//        return AppDatabase.getInstance(context).InspectionDao().getByStatus(status);
//    }
//
//    public LiveData<List<InspectionEntity>> getAllInspection(Context context) {
//        return AppDatabase.getInstance(context).InspectionDao().getAll();
//    }
//
//    public void insert(final InspectionEntity inspection, OnAsyncEventListener callback, Context context) {
//        new InspectionCreate(context, callback).execute(inspection);
//    }
//
//    public void update(final InspectionEntity inspection, OnAsyncEventListener callback, Context context) {
//        new InspectionUpdate(context, callback).execute(inspection);
//    }
//
//    public void delete(final InspectionEntity inspection, OnAsyncEventListener callback, Context context) {
//        new InspectionDelete(context, callback).execute(inspection);
//    }
}
