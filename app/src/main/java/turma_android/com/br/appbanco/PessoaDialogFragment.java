package turma_android.com.br.appbanco;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PessoaDialogFragment extends DialogFragment {
    private OuvintePessoaDialog ouvinte;
    private Pessoa pessoa;
    private EditText txtNome;
    private EditText txtEmail;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ouvinte = (OuvintePessoaDialog) context;
        if(ouvinte == null) {
            throw new RuntimeException("O context não é ouvinte do Dialog Pessoa.");
        }
    }

    public static PessoaDialogFragment criarDialog(Pessoa p) {
        PessoaDialogFragment dialog = new PessoaDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("PESSOA", p);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pessoa = (Pessoa)getArguments().getSerializable("PESSOA");

        if(pessoa == null) {
            pessoa = new Pessoa();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pessoa_dialog_layout, container);


        txtNome = (EditText) view.findViewById(R.id.nomePessoa);
        txtEmail = (EditText) view.findViewById(R.id.emailPessoa);

        txtNome.setText(pessoa.getNome());
        txtEmail.setText(pessoa.getEmail());

        Button btnCancelar = (Button)view.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Button btnSalvar = (Button)view.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processar();
            }
        });

        return view;
    }

    private void processar() {
        pessoa.setNome(txtNome.getText().toString());
        pessoa.setEmail(txtEmail.getText().toString());

        int erros = 0;

        if(isVazio(pessoa.getNome())) {
            txtNome.setError("Nome obrigatório");
            erros++;
        }

        if(isVazio(pessoa.getEmail())) {
            txtEmail.setError("E-mail obrigatório");
            erros++;
        }

        if(erros > 0) {
            return;
        }

        if(pessoa.get_id() == null) {
            ouvinte.incluir(pessoa);
        } else {
            ouvinte.atualizar(pessoa);
        }

        dismiss();
    }

    interface OuvintePessoaDialog {
        void incluir(Pessoa p);

        void atualizar(Pessoa p);
    }

    private boolean isVazio(CharSequence c) {
        return c == null || c.toString().trim().length() == 0;
    }
}
