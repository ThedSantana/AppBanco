package turma_android.com.br.appbanco;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PessoaDialogFragment.OuvintePessoaDialog {
    private Repositorio repositorio;
    private ActionMode actionMode;
    private PessoaAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        repositorio = new Repositorio(this);
        listView = (ListView) findViewById(R.id.listagemPessoa);
        adapter = new PessoaAdapter(this, repositorio.listar());
        adapter.registerDataSetObserver(observerAdapter);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(itemClick);
        listView.setOnItemLongClickListener(itemLongClick);
    }

    private DataSetObserver observerAdapter = new DataSetObserver() {
        @Override
        public void onChanged() {
            if(actionMode != null) {
                actionMode.setTitle("" + adapter.getTotalSelecionados());
            }
        }
    };

    @Override
    public void incluir(Pessoa p) {
        repositorio.salvarPessoa(p);
        adapter.setPessoas(repositorio.listar());
    }

    @Override
    public void atualizar(Pessoa p) {
        repositorio.salvarPessoa(p);
        adapter.setPessoas(repositorio.listar());
    }

    private AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(actionMode == null) {
                Pessoa p = (Pessoa)listView.getItemAtPosition(i);

                PessoaDialogFragment dialog = PessoaDialogFragment.criarDialog(p);
                dialog.show(getSupportFragmentManager(), "PESSOA_DIALOG");
            }
        }
    };

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_exclusao, menu);

            MenuItem itemTodos = menu.findItem(R.id.itemTodos);
            final CheckBox checkTodos = (CheckBox) itemTodos.getActionView();
            checkTodos.setPadding(0, 0, 48, 0);

            checkTodos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.setSelecionado(checkTodos.isChecked());
                }
            });

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if(item.getItemId() == R.id.itemExcluir) {
                List<Pessoa> pessoas = adapter.getPessoas();

                Iterator<Pessoa> iterator = pessoas.iterator();

                while(iterator.hasNext()) {
                    Pessoa p = iterator.next();
                    if(p.isSelecionado()) {
                        iterator.remove();
                        repositorio.excluirPessoa(p);
                    }
                }

                actionMode.finish();
                adapter.notifyDataSetChanged();
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.setVisivelChecks(false);
            adapter.setSelecionado(false);
            actionMode = null;
        }
    };

    private AdapterView.OnItemLongClickListener itemLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(actionMode != null) {
                return false;
            }

            actionMode = startSupportActionMode(actionModeCallback);
            Pessoa p = (Pessoa)listView.getItemAtPosition(i);
            p.setSelecionado(true);
            adapter.setVisivelChecks(true);
            adapter.notifyDataSetChanged();

            return true;
        }
    };

    public void criarNovaPessoa(View view) {
        PessoaDialogFragment dialog = PessoaDialogFragment.criarDialog(null);
        dialog.show(getSupportFragmentManager(), "PESSOA_DIALOG");
    }
}
