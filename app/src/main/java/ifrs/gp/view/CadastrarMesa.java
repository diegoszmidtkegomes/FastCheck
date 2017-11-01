package ifrs.gp.view;

import android.os.Bundle;

import ifrs.gp.R;

public class CadastrarMesa extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_mesa);
        setToolbar("Usuário");
        criaMenu(this);
    }
}
