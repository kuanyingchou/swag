package swag.swag.data;

import android.provider.BaseColumns;

public final class WordContract {
    public final class WordEntry implements BaseColumns {
        public static final String TABLE_NAME = "words";
        public static final String COLUMN_CHARACTER = "char";
        public static final String COLUMN_SKETCH = "sketch";
        public static final String COLUMN_DATE = "date";

        public final static String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_CHARACTER+ " TEXT, " +
                COLUMN_SKETCH + " TEXT NOT NULL, " +
                COLUMN_DATE + " TEXT NOT NULL " +
                " );";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
