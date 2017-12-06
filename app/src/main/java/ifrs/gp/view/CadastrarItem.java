package ifrs.gp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ifrs.gp.R;
import ifrs.gp.dao.DaoItem;
import ifrs.gp.model.SincronizaResposta;
import ifrs.gp.model.request.CadastrarItemRequest;

public class CadastrarItem extends BaseActivity implements SincronizaResposta {

    private AppCompatButton btOnk;
    private EditText nome;
    private EditText preco;
    private Spinner spinner;
    private Spinner spinner2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_item);
        setToolbar("Itens");
        criaMenu(this);
        //activity_item_ok
        btOnk = (AppCompatButton) findViewById(R.id.activity_item_ok);
        nome = (EditText) findViewById(R.id.activity_item_nome);
        preco = (EditText) findViewById(R.id.activity_item_preco);

        spinner = (Spinner) findViewById(R.id.activity_item_tipo);
        List<String> categories = new ArrayList<>();
        categories.add("Bebida");
        categories.add("Comida");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner2 = (Spinner) findViewById(R.id.activity_item_disp);
        List<String> disp = new ArrayList<>();
        disp.add("Sim");
        disp.add("Não");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, disp);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

        btOnk.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                new DaoItem().executa(CadastrarItem.this, CadastrarItem.this, montaRequest());
            }
        });

    }

    private CadastrarItemRequest montaRequest() {
        CadastrarItemRequest cad = new CadastrarItemRequest();
        String disp = spinner2.getSelectedItem().toString();
        int dispEnum=1;
        if(disp.equals("Sim")){
            dispEnum =1;
        }
        else{
            dispEnum =0;
        }

        cad.setDisponibilidade(dispEnum);
        cad.setNome(nome.getText().toString());
        cad.setPreco(Integer.parseInt(preco.getText().toString()));

        String tipo = spinner.getSelectedItem().toString();
        int tipoEnum=1;
        if(tipo.equals("Bebida")){
            tipoEnum = 1;
        }
        else{
            tipoEnum = 2;
        }

        cad.setTipo(tipoEnum);
        return cad;
    }

    @Override
    public void processoEncerrado(Object obj) {
        Toast.makeText(this, "Item Adicionado", Toast.LENGTH_SHORT).show();

    }
}
