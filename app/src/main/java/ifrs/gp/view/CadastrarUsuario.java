package ifrs.gp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ifrs.gp.R;
import ifrs.gp.dao.DaoItem;
import ifrs.gp.dao.DaoUsuario;
import ifrs.gp.model.SincronizaResposta;
import ifrs.gp.model.request.CadastroUsuario;

public class CadastrarUsuario extends BaseActivity implements SincronizaResposta {

    private AppCompatButton btOnk;
    private EditText nome;
    private EditText email;
    private EditText senha;
    private Spinner spinner;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);
        setToolbar("Usuário");
        criaMenu(this);

        btOnk = (AppCompatButton) findViewById(R.id.activity_usuario_btok);
        nome = (EditText) findViewById(R.id.activity_usuario_nome);
        email = (EditText) findViewById(R.id.activity_usuario_email);
        senha = (EditText) findViewById(R.id.activity_usuario_senha);
        spinner = (Spinner) findViewById(R.id.activity_usuario_tipo);

        nome.requestFocus();

        List<String> categories = new ArrayList<>();
        categories.add("Normal");
        categories.add("Master");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        btOnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DaoUsuario().executa(CadastrarUsuario.this, CadastrarUsuario.this, montaRequest());            }
        }
        );
    }

    private CadastroUsuario montaRequest() {
        CadastroUsuario c = new CadastroUsuario();
        c.setEmail(email.getText().toString());
        c.setNome(nome.getText().toString());
        c.setSenha(senha.getText().toString());

        String tipo = spinner.getSelectedItem().toString();
        int tipoEnum=1;
        if(tipo.equals("Master")){
            tipoEnum =2;
        }
        else{
            tipoEnum =1;
        }
        c.setTipo(tipoEnum);
        return c;
    }

    @Override
    public void processoEncerrado(Object obj) {
        Toast.makeText(this, "Usuário Adicionado", Toast.LENGTH_SHORT).show();
    }
}
