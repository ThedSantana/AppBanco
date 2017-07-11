package turma_android.com.br.appbanco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Repositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        repositorio = new Repositorio(this);

        Pessoa p = new Pessoa();
        p.setNome("Maria da Silva");
        p.setEmail("maria@maria.com.br");

        repositorio.salvarPessoa(p);

        listar();
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
