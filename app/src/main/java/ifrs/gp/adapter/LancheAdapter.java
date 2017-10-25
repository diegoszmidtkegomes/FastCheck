package ifrs.gp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ifrs.gp.R;
import ifrs.gp.model.Item;

/**
 * Adapter para a lista de checklists  concluídos
 *
 * @author diego.gomes
 * @version 1.0.0
 */
public class LancheAdapter extends RecyclerView.Adapter<LancheAdapter.ViewHolder> {

    /**
     * Lista de _avaliacoes recebida por parâmetro
     */
    private List<Item> _avaliacoes;
    /**
     * Fonte do FontAwesome
     */
    /**
     * Construtor da classe
     *
     * @param avaliacoes the avaliacoes
     */
    public LancheAdapter(List<Item> avaliacoes) {
        this._avaliacoes = avaliacoes;
    }

    /**
     * Método implementando para a criação do ViewHolder da RecyclerView. Chamado uma vez.
     *
     * @param parent   Usado para realizar o inflate da View
     * @param viewType tipo da View
     * @return ViewHolder específico para a RecyClerView
     */
    @Override
    public LancheAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_lanches, parent, false);

        return new ViewHolder(view);
    }

    /**
     * Método implementando para a criação do ViewHolder da RecyclerView. Chamado uma vez para cada item.
     *
     * @param holder Holder que será usado na RecyclerView.
     * @param position Posição do item que está sendo gerado
     */
    @Override
    public void onBindViewHolder(LancheAdapter.ViewHolder holder, int position) {

        Item aval = _avaliacoes.get(position);

        String _titulo = aval.getNome();
        holder.tvProduto.setText(_titulo);
        holder.tvQuant.setText("0");
    }

    /**
     * Método implementando para a criação do ViewHolder da RecyclerView. Retorno o tamanho da lista de avaliações
     *
     * @return Inteiro com o tamanho da lista
     */
    @Override
    public int getItemCount() {
        return _avaliacoes.size();
    }

    /**
     * Classe privada para exibição dos itens na RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * TextView da imagem do item da lista
         */
        public TextView tvProduto;
        /**
         * TextView do status do item da lista
         */
        public TextView tvQuant;


        /**
         * Construtor da classe
         *
         * @param v the v
         */
        public ViewHolder(View v) {
            super(v);
            tvProduto = (TextView) v.findViewById(R.id.produto_lanche);
            tvQuant = (TextView) v.findViewById(R.id.quantidade_lanche);
        }
    }
}
