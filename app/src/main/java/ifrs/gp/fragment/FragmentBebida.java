package ifrs.gp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ifrs.gp.R;
import ifrs.gp.adapter.Adaptador;
import ifrs.gp.adapter.BebidaAdapter;
import ifrs.gp.model.Item;
import ifrs.gp.view.RecyclerView.ItemClickSupport;


/**
 * Created by diego on 01/01/2017.
 */
public class FragmentBebida extends Fragment {

    private RecyclerView listaItens;
    private View view;
    private RecyclerView.LayoutManager mLayoutManager;
    //private List<Avaliacao> avals;
    private SwipeRefreshLayout swipeContainer;
    List<Item> lista = new ArrayList<Item>();


    /**
     * Instantiates a new Fragment check list concluido.
     */
    public FragmentBebida() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_bebida, container, false);

        //swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.fragment_checklist_realizado_swipe);
        listaItens = (RecyclerView) view.findViewById(R.id.fragment_bebida_lista);
        listaItens.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mLayoutManager.scrollToPosition(0);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        listaItens.addItemDecoration(itemDecoration);
        listaItens.setLayoutManager(mLayoutManager);

        ItemClickSupport.addTo(listaItens).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Item item = lista.get(position);
                item.setQuantidade(item.getQuantidade() + 1);
                carregaListaAtualizada();
            }
        });

        /*swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //buscar checklists concluidos pendentes de integração
                /*new AvaliacaoRepositorio().selectTodos();

                new CheckListHelper().processaPendente(getContext());
                carregaLista();
                swipeContainer.setRefreshing(false);

            }
        });*/
        //carregaLista();
        registerForContextMenu(listaItens);
        return view;
    }

    public void carregaLista() {
        try{
            for (Item item : Item.LISTAO.values()) {
                if(item.getTipo() == 1)
                    lista.add(item);
            }
            BebidaAdapter adapter = new BebidaAdapter(lista);
            listaItens.setAdapter(adapter);
        }
        catch (Exception ex){

        }

    }

    public void carregaListaAtualizada() {
        try{
            BebidaAdapter adapter = new BebidaAdapter(lista);
            listaItens.setAdapter(adapter);
        }
        catch (Exception ex){

        }

    }
}
