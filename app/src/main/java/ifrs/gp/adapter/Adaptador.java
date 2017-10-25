package ifrs.gp.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ifrs.gp.R;
import ifrs.gp.model.*;

public class Adaptador extends BaseAdapter {

    private Activity atividade;
    private List<Item> lista = new ArrayList<Item>();

    public Adaptador(Activity atividade, int tipo) {
        this.atividade = atividade;
        for (Item item : Item.LISTAO.values()) {
            if (tipo == item.getTipo()) {
                lista.add(item);
            }
        }
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int posicao) {
        return lista.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return lista.get(posicao).getId();
    }

    @Override
    public View getView(final int posicao, View convertView, ViewGroup parent) {
        final View linha = this.atividade.getLayoutInflater().inflate(R.layout.activity_adaptador, null);
        Item item = this.lista.get(posicao);

        TextView produto = (TextView) linha.findViewById(R.id.produto);
        TextView quantidade = (TextView) linha.findViewById(R.id.quantidade);

        produto.setText(item.getNome());
        quantidade.setText(String.valueOf(item.getQuantidade()));
        quantidade.setId(item.getId());

        linha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Item item = lista.get(posicao);
                Item.LISTAO.get(item.getId()).incQuantidade();
                int id = linha.getResources().getIdentifier(String.valueOf(item.getId()), "id", "gp1.magico5");
                TextView quantidade = (TextView) linha.findViewById(id);
                quantidade.setText(String.valueOf(item.getQuantidade()));
            }
        });
        return linha;
    }

}
