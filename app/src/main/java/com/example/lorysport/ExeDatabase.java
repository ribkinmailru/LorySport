package com.example.lorysport;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExeDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "Exe";
    private static final int DB_VERSION = 1;

    ExeDatabase(Context context){
     super(context, DB_NAME, null, DB_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        updateMydatabase(db, 0, DB_VERSION);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
       updateMydatabase(db, oldVersion, newVersion);
    }


    public static void insertExe(SQLiteDatabase db , String name,
                                 String description, int imageId, int type,
                                 int sub){
        ContentValues exeValues = new ContentValues();
        exeValues.put("NAME", name);
        exeValues.put("DESCRIPTION", description);
        exeValues.put("IMAGEID", imageId);
        exeValues.put("TYPE", type);
        exeValues.put("SUBTYPE", sub);
        exeValues.put("FAVORITE", 0);

        db.insert("EXE", null, exeValues);
    }


        public void updateMydatabase (SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion<1){
            db.execSQL("CREATE TABLE EXE (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            +"NAME TEXT, "
            +"DESCRIPTION TEXT, "
            +"IMAGEID INTEGER, "
            +"FAVORITE NUMERIC, "
            +"TYPE INTEGER, "
            +"SUBTYPE INTEGER, "
            +"DOWNLOAD INTEGER, "
            +"DIFICULTY TEXT);");
            insertExe(db, "Выпады с гантелями", "Поднимание и опускание штанги", R.drawable.ooo ,0, 0);
            insertExe(db, "Гакк-приседания", "Поднимание и опускание штанги", R.drawable.hack ,0 , 0);
            insertExe(db, "Жим плиты ногами", "Поднимание и опускание штанги", R.drawable.legpress ,0, 0);
            insertExe(db, "Приседания с гантелями", "Поднимание и опускание штанги", R.drawable.dumbell ,0, 0);
            insertExe(db, "Приседания со штангой", "Поднимание и опускание штанги", R.drawable.squats ,0 , 0);
            insertExe(db, "Разгибание ног в тренажёре", "Поднимание и опускание штанги", R.drawable.legexe ,0, 0);
            insertExe(db, "Сисси приседания", "Поднимание и опускание штанги", R.drawable.sissi ,0, 0);

            insertExe(db, "Становая тяга со штангой", "Поднимание и опускание штанги", R.drawable.stidd ,0, 1);
            insertExe(db, "Тяга с гантелями", "Поднимание и опускание штанги", R.drawable.hamster ,0 , 1);
            insertExe(db, "Сгибание ног в тренажере", "Поднимание и опускание штанги", R.drawable.lying ,0, 1);

            insertExe(db, "Подъем на носки сидя в тренажере", "Поднимание и опускание штанги", R.drawable.seated ,0, 2);
            insertExe(db, "Подъемы на носках стоя", "Поднимание и опускание штанги", R.drawable.standing ,0 , 2);

            insertExe(db, "Жим гантелей на наклонной скамье", "Поднимание и опускание штанги", R.drawable.pr ,1, 0);
            insertExe(db, "Жим штанги на наклонной скамье", "Поднимание и опускание штанги", R.drawable.pr1 ,1 , 0);
            insertExe(db, "Пулловер", "Поднимание и опускание штанги", R.drawable.pr2 ,1, 0);
            insertExe(db, "Разведение рук с гантелями", "Поднимание и опускание штанги", R.drawable.pr3 ,1, 0);

            insertExe(db, "Жим гантелей на горизонтальной скамье", "Поднимание и опускание штанги", R.drawable.mid ,1, 1);
            insertExe(db, "Жим штанги на горизонтальной скамье", "Поднимание и опускание штанги", R.drawable.mid2 ,1, 1);
            insertExe(db, "Отжимания на брусьях", "Поднимание и опускание штанги", R.drawable.mid3 ,1 , 1);
            insertExe(db, "Разведение рук с гантелями на горизонтальной скамье", "Поднимание и опускание штанги", R.drawable.mid4 ,1, 1);
            insertExe(db, "Сведение рук на кроссовере", "Поднимание и опускание штанги", R.drawable.mid5 ,1, 1);

            insertExe(db, "Жим штанги на скамье с наклоном вниз", "Поднимание и опускание штанги", R.drawable.l1 ,1 , 2);
            insertExe(db, "Отжимания на брусьях", "Поднимание и опускание штанги", R.drawable.l2 ,1, 2);
            insertExe(db, "Сведение рук на кроссовере", "Поднимание и опускание штанги", R.drawable.l3 ,1, 2);

            insertExe(db, "Жим Арнольда", "Поднимание и опускание штанги", R.drawable.pd ,2 , 0);
            insertExe(db, "Жим гантелей сидя", "Поднимание и опускание штанги", R.drawable.pd2 ,2, 0);
            insertExe(db, "Жим гантелей сидя", "Поднимание и опускание штанги", R.drawable.pd3 ,2, 0);
            insertExe(db, "Подъем рук с гантелями перед собой", "Поднимание и опускание штанги", R.drawable.pd4 ,2, 0);

            insertExe(db, "Махи гантелями в стороны", "Поднимание и опускание штанги", R.drawable.md ,2 , 1);
            insertExe(db, "Подъём гантели в сторону одной рукой", "Поднимание и опускание штанги", R.drawable.md2 ,2, 1);
            insertExe(db, "Подъемы одной руки в сторону на блоке", "Поднимание и опускание штанги", R.drawable.md3 ,2, 1);
            insertExe(db, "Тяга гантелей к подбородку", "Поднимание и опускание штанги", R.drawable.md4 ,2, 1);

            insertExe(db, "Махи гантелями в стороны", "Поднимание и опускание штанги", R.drawable.ld ,2 , 2);
            insertExe(db, "Подъём гантели в сторону одной рукой", "Поднимание и опускание штанги", R.drawable.ld2 ,2, 2);


            insertExe(db, "Гиперэкстензия", "Поднимание и опускание штанги", R.drawable.mvp1 ,4 , 0);
            insertExe(db, "Наклоны вперед со штангой", "Поднимание и опускание штанги", R.drawable.mvp2 ,4, 0);
            insertExe(db, "Становая тяга с гантелями", "Поднимание и опускание штанги", R.drawable.mvp3 ,4 , 0);
            insertExe(db, "Становая тяга со штангой", "Поднимание и опускание штанги", R.drawable.mvp4 ,4, 0);

            insertExe(db, "Шраги", "Поднимание и опускание штанги", R.drawable.trapezi ,4, 1);

            insertExe(db, "Гиперэкстензия", "Поднимание и опускание штанги", R.drawable.shm1 ,4 , 2);
            insertExe(db, "Наклоны вперед со штангой", "Поднимание и опускание штанги", R.drawable.shm2 ,4, 2);
            insertExe(db, "Становая тяга с гантелями", "Поднимание и опускание штанги", R.drawable.shm3 ,4 , 2);
            insertExe(db, "Становая тяга со штангой", "Поднимание и опускание штанги", R.drawable.chm4 ,4, 2);
            insertExe(db, "Становая тяга с гантелями", "Поднимание и опускание штанги", R.drawable.chm5 ,4 , 2);
            insertExe(db, "Становая тяга со штангой", "Поднимание и опускание штанги", R.drawable.chm6 ,4, 2);

            insertExe(db, "Концентрированное сгибание руки на бицепс", "Поднимание и опускание штанги", R.drawable.bi1 ,5 , 0);
            insertExe(db, "Обратные сгибания рук со штангой", "Поднимание и опускание штанги", R.drawable.bi2 ,5, 0);
            insertExe(db, "Подтягивания обратным хватом на турнике", "Поднимание и опускание штанги", R.drawable.bi3 ,5, 0);
            insertExe(db, "Поочередное cгибание рук с гантелями стоя", "Поднимание и опускание штанги", R.drawable.bi4 ,5, 0);
            insertExe(db, "Поочередное сгибание рук с гантелями", "Поднимание и опускание штанги", R.drawable.bi5 ,5 , 0);
            insertExe(db, "Сгибание рук на скамье Скотта", "Поднимание и опускание штанги", R.drawable.bi6 ,5, 0);
            insertExe(db, "Сгибание рук с гантелями лёжа на скамье", "Поднимание и опускание штанги", R.drawable.bi7 ,5 , 0);
            insertExe(db, "Сгибание рук со штангой", "Поднимание и опускание штанги", R.drawable.bi8 ,5, 0);

            insertExe(db, "Жим лежа узким хватом", "Поднимание и опускание штанги", R.drawable.tr1 ,6 , 0);
            insertExe(db, "Разгибание одной руки с гантелью из-за головы", "Поднимание и опускание штанги", R.drawable.tr2 ,6, 0);
            insertExe(db, "Разгибание руки с гантелью в наклоне", "Поднимание и опускание штанги", R.drawable.tr3 ,6, 0);
            insertExe(db, "Разгибания рук на верхнем блокее", "Поднимание и опускание штанги", R.drawable.tr4 ,6, 0);
            insertExe(db, "Разгибания рук на верхнем блоке в наклоне", "Поднимание и опускание штанги", R.drawable.tr5 ,6 , 0);
            insertExe(db, "Французский жим сидя с гантелью", "Поднимание и опускание штанги", R.drawable.tr6 ,6, 0);
            insertExe(db, "Французский жим со штангой лежа", "Поднимание и опускание штанги", R.drawable.tr7 ,6 , 0);
            insertExe(db, "Французский жим со штангой сидя", "Поднимание и опускание штанги", R.drawable.tr8 ,6, 0);


        }
                }
                }
