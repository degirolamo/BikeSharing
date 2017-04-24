package com.example.daniel.bikesharing.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daniel on 04.04.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dbBikeSharing";

    // Table Names
    private final String TABLE_CANTON = "canton";
    private final String TABLE_TOWN = "town";
    private final String TABLE_PERSON = "person";
    private final String TABLE_PLACE = "place";
    private final String TABLE_BIKE = "bike";
    private final String TABLE_RENT = "rent";

    // Common column names
    private final String KEY_ID = "id";

    // Canton Table - column names
    private final String KEY_CANTON_NAME = "name";
    private final String KEY_CANTON_PICTURE = "picture";

    // Towns Table - column names
    private final String KEY_TOWN_NAME = "name";
    private final String KEY_NPA = "npa";
    private final String KEY_CANTONID = "idCanton";

    // Place Table - column names
    private final String KEY_PLACE_NAME = "name";
    private final String KEY_PLACE_PICTURE = "picture";
    private final String KEY_PLACE_NBPLACES = "nbPlaces";
    private final String KEY_PLACE_ADDRESS = "address";
    private final String KEY_PLACE_TOWNID = "idTown";

    //Bike Table - column names
    private final String KEY_PLACEID = "idPlace";

    //Person Table - Column names
    private final String KEY_PERSON_TOWNID= "idTown";
    private final String KEY_EMAIL = "email";
    private final String KEY_PASSWORD = "password";
    private final String KEY_FIRSTNAME = "firstname";
    private final String KEY_LASTNAME = "lastname";
    private final String KEY_ADDRESS = "adress";
    private final String KEY_ISADMIN = "isAdmin";

    //Rent Table - Column names
    private final String KEY_PERSONID= "idPerson";
    private final String KEY_BIKEID = "idBike";
    private final String KEY_BEGINDATE = "beginDate";
    private final String KEY_ENDDATE = "endDate";

    // Table Create Statements
    // Canton table create statement
    private  final String CREATE_TABLE_CANTON =
            "CREATE TABLE " + TABLE_CANTON
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_CANTON_NAME + " TEXT NOT NULL,"
            + KEY_CANTON_PICTURE + " TEXT NOT NULL)";

    // Towns table create statement
    private final String CREATE_TABLE_TOWN =
            "CREATE TABLE " + TABLE_TOWN
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_TOWN_NAME + " TEXT NOT NULL,"
            + KEY_NPA + " INTEGER NOT NULL,"
            + KEY_CANTONID + " INTEGER NOT NULL, "
            + "FOREIGN KEY ("+KEY_CANTONID+") REFERENCES "+TABLE_CANTON+"("+KEY_ID+ "))";

    // Person table create statement
    private final String CREATE_TABLE_PERSON =
            "CREATE TABLE " + TABLE_PERSON
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_EMAIL + " TEXT NOT NULL,"
                    + KEY_PASSWORD + " TEXT NOT NULL,"
                    + KEY_FIRSTNAME + " TEXT NOT NULL,"
                    + KEY_LASTNAME + " TEXT NOT NULL, "
                    + KEY_ADDRESS + " TEXT NOT NULL,"
                    + KEY_ISADMIN + " TINYINT NOT NULL DEFAULT 0,"
                    + KEY_PERSON_TOWNID + " INTEGER NOT NULL, "
                    + "FOREIGN KEY ("+KEY_PERSON_TOWNID+") REFERENCES "+TABLE_TOWN+"("+KEY_ID+ "))";


    // Places table create statement
    private final String CREATE_TABLE_PLACE =
            "CREATE TABLE " + TABLE_PLACE
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_PLACE_NAME + " TEXT NOT NULL,"
                    + KEY_PLACE_PICTURE + " TEXT,"
                    + KEY_PLACE_NBPLACES + " INTEGER NOT NULL,"
                    + KEY_PLACE_ADDRESS + " TEXT NOT NULL,"
                    + KEY_PLACE_TOWNID + " INTEGER NOT NULL, "
                    + "FOREIGN KEY ("+KEY_PLACE_TOWNID+") REFERENCES "+TABLE_TOWN+"("+KEY_ID+ "))";

    // Bike table create statement
    private final String CREATE_TABLE_BIKE =
            "CREATE TABLE " + TABLE_BIKE
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_PLACEID + " INTEGER, "
                    + "FOREIGN KEY ("+KEY_PLACEID+") REFERENCES "+TABLE_PLACE+"("+KEY_ID+ "))";

    // Rent table create statement
    private final String CREATE_TABLE_RENT =
            "CREATE TABLE " + TABLE_RENT
                    + "(" + KEY_PERSONID + " INTEGER NOT NULL, "
                    + KEY_BIKEID + " INTEGER NOT NULL, "
                    + KEY_BEGINDATE + " DATETIME NOT NULL,"
                    + KEY_ENDDATE + " DATETIME,"
                    + "PRIMARY KEY(" + KEY_BIKEID + ", " + KEY_PERSONID + "),"
                    + "FOREIGN KEY ("+KEY_PERSONID+") REFERENCES "+TABLE_PERSON+"("+KEY_ID+ "),"
                    + "FOREIGN KEY ("+KEY_BIKEID+") REFERENCES "+ TABLE_BIKE+"("+KEY_ID+"))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_CANTON);
        db.execSQL(CREATE_TABLE_TOWN);
        db.execSQL(CREATE_TABLE_PERSON);
        db.execSQL(CREATE_TABLE_PLACE);
        db.execSQL(CREATE_TABLE_BIKE);
        db.execSQL(CREATE_TABLE_RENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CANTON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOWN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIKE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RENT);

        // create new tables
        onCreate(db);
    }

    //getters

    public String getTABLE_CANTON() {
        return TABLE_CANTON;
    }

    public String getTABLE_TOWN() {
        return TABLE_TOWN;
    }

    public String getTABLE_PERSON() {
        return TABLE_PERSON;
    }

    public String getTABLE_PLACE() {
        return TABLE_PLACE;
    }

    public String getTABLE_BIKE() {
        return TABLE_BIKE;
    }

    public String getTABLE_RENT() {
        return TABLE_RENT;
    }

    public String getKEY_ID() {
        return KEY_ID;
    }

    public String getKEY_CANTON_NAME() { return KEY_CANTON_NAME; }

    public String getKEY_CANTON_PICTURE() {
        return KEY_CANTON_PICTURE;
    }

    public String getKEY_TOWN_NAME() {
        return KEY_TOWN_NAME;
    }

    public String getKEY_NPA() {
        return KEY_NPA;
    }

    public String getKEY_CANTONID() {
        return KEY_CANTONID;
    }

    public String getKEY_PLACE_TOWNID() {
        return KEY_PLACE_TOWNID;
    }

    public String getKEY_PLACE_NAME() {
        return KEY_PLACE_NAME;
    }

    public String getKEY_PLACE_PICTURE() {
        return KEY_PLACE_PICTURE;
    }

    public String getKEY_PLACE_NBPLACES() {
        return KEY_PLACE_NBPLACES;
    }

    public String getKEY_PLACE_ADDRESS() { return KEY_PLACE_ADDRESS; }

    public String getKEY_PLACEID() {
        return KEY_PLACEID;
    }

    public String getKEY_PERSON_TOWNID() {
        return KEY_PERSON_TOWNID;
    }

    public String getKEY_EMAIL() {
        return KEY_EMAIL;
    }

    public String getKEY_PASSWORD() {
        return KEY_PASSWORD;
    }

    public String getKEY_FIRSTNAME() {
        return KEY_FIRSTNAME;
    }

    public String getKEY_LASTNAME() {
        return KEY_LASTNAME;
    }

    public String getKEY_ADDRESS() {
        return KEY_ADDRESS;
    }

    public String getKEY_ISADMIN() {
        return KEY_ISADMIN;
    }

    public String getKEY_PERSONID() {
        return KEY_PERSONID;
    }

    public String getKEY_BIKEID() {
        return KEY_BIKEID;
    }

    public String getKEY_BEGINDATE() {
        return KEY_BEGINDATE;
    }

    public String getKEY_ENDDATE() {
        return KEY_ENDDATE;
    }

    public void reloadDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null)
            onUpgrade(db, 0, 0);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
