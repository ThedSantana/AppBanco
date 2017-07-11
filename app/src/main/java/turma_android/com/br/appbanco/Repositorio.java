package turma_android.com.br.appbanco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {
    private BancoHelper helper;

    public Repositorio(Context context) {
        helper = new BancoHelper(context);
    }

    public void salvarPessoa(Pessoa p) {
        SQLiteDatabase db = helper.getWritableDatabase();

        if(p.get_id() == null) {

            ContentValues cv = new ContentValues();
            cv.put("NOME", p.getNome());
            cv.put("EMAIL", p.getEmail());

            long novoID = db.insert("Pessoa", null, cv);
            p.set_id(novoID);

        } else {

            ContentValues cv = new ContentValues();
            cv.put("NOME", p.getNome());
            cv.put("EMAIL", p.getEmail());

            db.update("Pessoa", cv, "_ID = ?",
                                new String[]{p.get_id().toString()});
        }

        db.close();
    }

    public void excluirPessoa(Pessoa p) {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete("Pessoa", "_ID=" + p.get_id(), null);

        db.close();
    }

    public List<Pessoa> listar() {
        List<Pessoa> resposta = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();

        String[] COLUNAS = {"_ID", "NOME", "EMAIL"};

        Cursor cursor = db.query("Pessoa", COLUNAS, null, null, null, null, "NOME");

        int _ID_IDX = cursor.getColumnIndex("_ID");
        int NOM_IDX = cursor.getColumnIndex("NOME");
        int EMA_IDX = cursor.getColumnIndex("EMAIL");

        while(cursor.moveToNext()) {
            Pessoa p = new Pessoa();

            p.set_id(   cursor.getLong(_ID_IDX)   );
            p.setNome(  cursor.getString(NOM_IDX) );
            p.setEmail( cursor.getString(EMA_IDX) );

            resposta.add(p);
        }

        db.close();

        return resposta;
    }

}
