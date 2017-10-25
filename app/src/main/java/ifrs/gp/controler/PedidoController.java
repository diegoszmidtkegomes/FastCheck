package ifrs.gp.controler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import ifrs.gp.R;
import ifrs.gp.dao.DaoLogin;
import ifrs.gp.dao.DaoPedido;
import ifrs.gp.model.Item;
import ifrs.gp.model.Pedido;
import ifrs.gp.model.SincronizaResposta;
import ifrs.gp.model.request.LoginRequest;
import ifrs.gp.view.MainActivity;

public class PedidoController implements SincronizaResposta {
    private MainActivity atividade;
    private View view;
    private Context contexto;
    private Pedido pedido;
    private ProgressDialog dialogo;
    private ItemController itemCont;

    public PedidoController(MainActivity atividade) {
        this.atividade = atividade;
        this.contexto = atividade.getApplicationContext();
        this.itemCont = new ItemController(atividade);
    }

    public void testeLogin(){
        new DaoLogin().executa(contexto, this, criaRequest());

    }

    private LoginRequest criaRequest() {
        LoginRequest req = new LoginRequest();
        req.setEmail("diego");
        req.setSenha("1234");
        return  req;
    }


    public void fecha_pedido() {
        pedido = new Pedido();
        for (Item item : Item.LISTAO.values()) {
            pedido.addItens(item);
        }
        if (pedido.getItens().size() == 0) {
            Toast.makeText(contexto, "Nenhum produto pedido", Toast.LENGTH_SHORT).show();
        } else {
            view = atividade.getLayoutInflater().inflate(R.layout.retorno, null, false);
            AlertDialog.Builder builder = new AlertDialog.Builder(atividade);
            builder.setTitle("Fechamento da Comanda");
            builder.setView(view);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText e_mesa = (EditText) view.findViewById(R.id.retorno_mesa);
                    EditText e_observacao = (EditText) view.findViewById(R.id.retorno_observacao);
                    String mesa = e_mesa.getText().toString();
                    String observacao = e_observacao.getText().toString();

                    if (mesa.length() < 1) {
                        Toast toast = Toast.makeText(contexto, "Informe a mesa", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        pedido.setMesa(Integer.valueOf(mesa));
                        pedido.setObs(observacao);
                        Pedido.PEDIDO = new Gson().toJson(pedido);
                        chamaDao();
                    }
                }
            });
            builder.setNegativeButton("Cancelar", null);

            AlertDialog alerta = builder.create();
            alerta.show();
        }
    }

    @Override
    public void processoEncerrado(Object obj) {
        dialogo = new ProgressDialog(atividade);
        dialogo.setTitle("Enviando Pedido");
        dialogo.setMessage("Aguarde...");
        dialogo.setCancelable(false);
        dialogo.show();

        if (obj instanceof String) {
            String retorno = (String) obj;
            Log.e("Airton", "Retornou WS");

            if ((dialogo != null) && (dialogo.isShowing())) {
                dialogo.dismiss();
            }
            Toast toast = Toast.makeText(contexto, retorno, Toast.LENGTH_LONG);
            toast.show();
            itemCont.carrega();
        }
    }

    public void chamaDao() {
        new DaoPedido().executa(contexto, this);
    }
}
