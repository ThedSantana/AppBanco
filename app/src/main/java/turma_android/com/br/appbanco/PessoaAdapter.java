package turma_android.com.br.appbanco;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class PessoaAdapter extends BaseAdapter {
    private boolean visivelChecks;
    private List<Pessoa> pessoas;
    private Context context;

    public PessoaAdapter(Context context, List<Pessoa> pessoas) {
        this.context = context;
        this.pessoas = pessoas;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pessoas.size();
    }

    @Override
    public Object getItem(int i) {
        return pessoas.get(i);
    }

    @Override
    public long getItemId(int i) {
        Pessoa p = pessoas.get(i);
        return p.get_id();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Pessoa p = pessoas.get(i);

        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.pessoa_item_layout, viewGroup, false);
            view.setTag(new ViewHolder(view));
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.atualizarViews(p);

        return view;
    }

    public int getTotalSelecionados() {
        int total = 0;

        for(Pessoa p : pessoas) {
            if(p.isSelecionado()) {
                total++;
            }
        }

        return total;
    }

    private class ViewHolder {
        private TextView txtNome;
        private TextView txtEmail;
        private CheckBox chkSelecionado;

        public ViewHolder(View v) {
            txtNome = (TextView) v.findViewById(R.id.nomePessoa);
            txtEmail = (TextView) v.findViewById(R.id.emailPessoa);
            chkSelecionado = (CheckBox) v.findViewById(R.id.checkPessoa);

            chkSelecionado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox c = (CheckBox)v;
                    Pessoa p = (Pessoa)c.getTag();
                    p.setSelecionado(c.isChecked());
                    notifyDataSetChanged();
                }
            });
        }

        public void atualizarViews(Pessoa p) {
            txtNome.setText(p.getNome());
            txtEmail.setText(p.getEmail());
            chkSelecionado.setChecked(p.isSelecionado());
            chkSelecionado.setTag(p);
            chkSelecionado.setVisibility(visivelChecks ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setSelecionado(boolean b) {
        for(Pessoa p : pessoas) {
            p.setSelecionado(b);
        }

        //Notifica aos observadores deste adaptador que seus dados
        //foram alterados. O próprio ListView observa este objeto e
        //deve ser redesenhado
        notifyDataSetChanged();
    }

    public void setVisivelChecks(boolean visivelChecks) {
        this.visivelChecks = visivelChecks;
    }
}
