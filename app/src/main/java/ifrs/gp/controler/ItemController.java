package ifrs.gp.controler;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import java.util.HashMap;

import ifrs.gp.dao.DaoLista;
import ifrs.gp.model.Item;
import ifrs.gp.model.SincronizaResposta;
import ifrs.gp.view.MainActivity;

public class ItemController implements SincronizaResposta {
    private ProgressDialog dialogo;
    private MainActivity atividade;
    private Context contexto;

    public ItemController(MainActivity atividade) {
        this.atividade = atividade;
        this.contexto = atividade.getApplicationContext();
    }

    public void carrega() {
        dialogo = new ProgressDialog(atividade);
        dialogo.setTitle("Carregando Produtos...");
        dialogo.setMessage("Aguarde ...");
        dialogo.setCancelable(false);
        dialogo.show();
        chamaDao();
    }

    @Override
    public void processoEncerrado(Object obj) {
        if (obj instanceof Item[]) {
            Log.e("Airton", "Retornou WS");
            Item[] lista = (Item[]) obj;
            Item.LISTAO = new HashMap<Integer, Item>();
            for (Item item : lista) {
                item.setNome(item.getNome().toUpperCase());
                Item.LISTAO.put(item.getId(), item);
            }
            if ((dialogo != null) && (dialogo.isShowing())) {
                dialogo.dismiss();
            }
            /*((ListView) atividade.findViewById(R.id.bebidas)).setAdapter(new Adaptador(atividade, 1));
            ((ListView) atividade.findViewById(R.id.lanches)).setAdapter(new Adaptador(atividade, 2));*/
            atividade.f.carregaLista();
            atividade.f2.carregaLista();

        }
    }

    public void chamaDao() {
        new DaoLista().executa(contexto, this);
    }

}
