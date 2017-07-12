package turma_android.com.br.appbanco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PessoaAdapter extends BaseAdapter {
    private Context context;
    private List<Pessoa> pessoas;

    public PessoaAdapter(Context context, List<Pessoa> pessoas) {
        this.context = context;
        this.pessoas = pessoas;
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
            view = LayoutInflater.from(context).
                    inflate(R.layout.pessoa_item_layout,
                            viewGroup, false);

            view.setTag(new ViewHolder(view));
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.atualizarViews(p);

        return view;
    }

    private class ViewHolder {
        private TextView txtNome;
        private TextView txtEmail;

        public ViewHolder(View v) {
            txtNome = v.findViewById(R.id.nomePessoa);
            txtEmail = v.findViewById(R.id.emailPessoa);
        }

        public void atualizarViews(Pessoa p) {
            txtNome.setText(p.getNome());
            txtEmail.setText(p.getEmail());
        }
    }
}
