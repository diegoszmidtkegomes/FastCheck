package ifrs.gp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by diego on 18/10/2017.
 */

public class Usuario {

    @SerializedName("id_usuario")
    private String idUsuario;
    @SerializedName("nome_usuario")
    private String nomeUsuario;
    private String email;
    private String senha;
    @SerializedName("tipo_usuario")
    private String tipoUsuario;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
