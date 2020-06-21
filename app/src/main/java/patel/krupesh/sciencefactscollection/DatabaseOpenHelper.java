package patel.krupesh.sciencefactscollection;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "sciencefactscollectionupdate2.db";
    private static final int DATABASE_VERSION = 6;
    public static String tbl_name = "tblFacts";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

}
