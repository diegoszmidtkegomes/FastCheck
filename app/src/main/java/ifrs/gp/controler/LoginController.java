package ifrs.gp.controler;

import android.content.Context;

import ifrs.gp.dao.DaoLogin;
import ifrs.gp.model.request.LoginRequest;
import ifrs.gp.view.LoginActivity;

/**
 * Created by diego on 11/10/2017.
 */

public class LoginController {

    public void login(Context contexto, LoginActivity act, String usuario, String senha){
        new DaoLogin().executa(contexto, act, criaRequest(usuario, senha));
    }

    private LoginRequest criaRequest(String usuario, String senha) {
        LoginRequest req = new LoginRequest();
        req.setEmail(usuario);
        req.setSenha(senha);
        return  req;
    }

}
