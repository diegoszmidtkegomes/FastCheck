package ifrs.gp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ifrs.gp.R;

public class CadastrarUsuario extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);
        setToolbar("Usuário");
        criaMenu(this);
    }
}
