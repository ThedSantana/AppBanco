package turma_android.com.br.appbanco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoHelper extends SQLiteOpenHelper {
    private static final int VERSAO = 1;
    private static final String BANCO = "DB_AGENDA";

    public BancoHelper(Context context) {
        super(context, BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE Pessoa (");
        sb.append("  _ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
        sb.append("  NOME TEXT(80) NOT NULL, ");
        sb.append("  EMAIL TEXT(100) NOT NULL");
        sb.append(")");

        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
