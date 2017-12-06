package ifrs.gp.view;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ifrs.gp.R;
import ifrs.gp.controler.LoginController;
import ifrs.gp.model.SincronizaResposta;
import ifrs.gp.model.Usuario;
import ifrs.gp.model.interfaces.iAsyncResponse;
import ifrs.gp.util.ConexaoInternet;
import ifrs.gp.util.MD5;
import ifrs.gp.util.SharedPreferences;


/**
 * Activty para login
 *
 * @author diego.gomes
 * @version 1.0.0
 */
public class LoginActivity extends AppCompatActivity implements iAsyncResponse, SincronizaResposta {

    private static final int REQUEST_SIGNUP = 0;

    /**
     * The Campo usuario.
     */
    @BindView(R.id.login_usuario)
    EditText _campoUsuario;
    /**
     * The Campo senha.
     */
    @BindView(R.id.login_senha)
    EditText _campoSenha;
    /**
     * The Btn login.
     */
    @BindView(R.id.login_btnlogin)
    Button _btnLogin;


    private String mensagemLogin;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actv_login);
        ButterKnife.bind(this);
        _campoUsuario.setError(null);
        _btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    /**
     * Login.
     */
    public void login() {
        if (!validarDados()) {
            onLoginFailed();
            return;
        }

        _btnLogin.setEnabled(false);

        progressDialog = new ProgressDialog(this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.act_login_msg_aut_us));
        progressDialog.show();
        progressDialog.setCancelable(false);

        final String usuario = _campoUsuario.getText().toString();
        final String senha = _campoSenha.getText().toString();

        ConexaoInternet cd = new ConexaoInternet(this);

        if (cd.possuiConexao()) {

            new android.os.Handler().post(
                    new Runnable() {
                        public void run() {
                            onLoginSuccess();
                            new LoginController().login(LoginActivity.this, LoginActivity.this, usuario, senha);
                            /*new ValidaLoginWS().realizarConexao(getApplicationContext(),
                                    new ValidaLoginRequest(usuario, senha), LoginActivity.this);*/
                        }
                    });

        } else {
                Toast.makeText(this, "Usuário incorreto", Toast.LENGTH_LONG).show();
                dismissProgressDialog();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                if (mensagemLogin.isEmpty())
                    this.finish();
                else
                    Toast.makeText(LoginActivity.this, mensagemLogin, Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * On login success.
     */
    public void onLoginSuccess() {
        _btnLogin.setEnabled(true);
    }

    /**
     * On login failed.
     */
    public void onLoginFailed() {
        //Toast.makeText(getBaseContext(), R.string.act_login_msg_erro_log, Toast.LENGTH_LONG).show();
        _btnLogin.setEnabled(true);
    }

    /**
     * Validar dados boolean.
     *
     * @return the boolean
     */
    public boolean validarDados() {
        boolean valid = true;

        String usuario = _campoUsuario.getText().toString();
        String senha = _campoSenha.getText().toString();

        if (usuario.isEmpty()) {
            _campoUsuario.setError("insira um usuário");
            valid = false;
        } else {
            _campoUsuario.setError(null);
        }

        if (senha.isEmpty()) {
            _campoSenha.setError("insira a sua senha");
            valid = false;
        } else {
            _campoSenha.setError(null);
        }

        return valid;
    }

    @Override
    public void processoEncerrado(String mensagem) {
        this.mensagemLogin = mensagem;
        if (this.mensagemLogin.equals("")) {
            String usuario = _campoUsuario.getText().toString();
            String senha = _campoSenha.getText().toString();
            this.mensagemLogin = getString(R.string.act_login_msg_erro_serv);
        }
        if (mensagemLogin.equals("CPF e Senha corretos")) {
            //new LoginHelper().processaLogin(this);
            new SharedPreferences().salvaValor(getApplicationContext(), "usuario", "diego");
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("processarLogin", true);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, mensagemLogin, Toast.LENGTH_LONG).show();
        }
        dismissProgressDialog();

    }

    @Override
    public void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void processoEncerrado(Object obj) {
        if(obj == null){
            obj = "";
        }

        this.mensagemLogin = (String) obj;
        if (this.mensagemLogin.equals("")) {
            this.mensagemLogin = getString(R.string.act_login_msg_erro_serv);
        }

        if (mensagemLogin.contains("id")) {
            //new LoginHelper().processaLogin(this);
            Usuario usu = new Gson().fromJson(this.mensagemLogin, Usuario.class);

            new SharedPreferences().salvaValor(getApplicationContext(), "usuario", usu.getIdUsuario());
            new SharedPreferences().salvaValor(getApplicationContext(), "email", usu.getEmail());
            new SharedPreferences().salvaValor(getApplicationContext(), "tipo_usuario", usu.getTipoUsuario());

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("processarLogin", true);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, mensagemLogin, Toast.LENGTH_LONG).show();
        }
        dismissProgressDialog();
    }
}
