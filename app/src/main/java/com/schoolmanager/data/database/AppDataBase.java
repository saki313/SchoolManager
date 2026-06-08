package com.schoolmanager.data.database;



import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.schoolmanager.data.dao.CoursDao;
import com.schoolmanager.data.dao.EtudiantDao;
import com.schoolmanager.data.dao.NoteDao;
import com.schoolmanager.data.dao.PresenceDao;
import com.schoolmanager.data.entity.Cours;
import com.schoolmanager.data.entity.Etudiant;
import com.schoolmanager.data.entity.Note;
import com.schoolmanager.data.entity.Presence;

@Database(entities = {Etudiant.class, Note.class, Presence.class, Cours.class},
        version = 3, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract EtudiantDao etudiantDao();
    public abstract NoteDao noteDao();
    public abstract PresenceDao presenceDao();
    public abstract CoursDao coursDao();

    private static volatile AppDataBase INSTANCE;

    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDataBase.class, "schoolmanager.db")
                            .addMigrations(MIGRATION_2_3)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE etudiant ADD COLUMN filiere TEXT");
            database.execSQL("ALTER TABLE etudiant ADD COLUMN niveau TEXT");
        }
    };
}