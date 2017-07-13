package turma_android.com.br.appbanco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Repositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        repositorio = new Repositorio(this);

        ListView listView = (ListView) findViewById(R.id.listagemPessoa);

        PessoaAdapter adapter = new PessoaAdapter(this, repositorio.listar());

        listView.setAdapter(adapter);
    }

    private void listar() {
        List<Pessoa> pessoas = repositorio.listar();

        for(Pessoa p : pessoas) {
            Log.i("BANCO", "-------------------");
            Log.i("BANCO", p.get_id().toString());
            Log.i("BANCO", p.getNome()          );
            Log.i("BANCO", p.getEmail()         );
        }
    }
}
