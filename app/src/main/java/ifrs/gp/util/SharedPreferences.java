package ifrs.gp.util;

import android.content.Context;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by diego.gomes on 13/12/2016.
 */
public class SharedPreferences {

    private static final String MY_PREFS_NAME = "Preference";

    /**
     * Salva valor.
     *
     * @param contexto the contexto
     * @param campo    the campo
     * @param valor    the valor
     */
    public void salvaValor(Context contexto,  String campo, String valor){
        android.content.SharedPreferences.Editor editor = contexto.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(campo, valor);
        editor.apply();
    }

    /**
     * Retorna valor string.
     *
     * @param contexto the contexto
     * @param campo    the campo
     * @return the string
     */
    public String retornaValor(Context contexto,  String campo){
        android.content.SharedPreferences prefs =   contexto.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(campo, null);
    }

    /**
     * Remove valor.
     *
     * @param contexto the contexto
     * @param campo    the campo
     */
    public void removeValor(Context contexto, String campo){
        android.content.SharedPreferences.Editor editor = contexto.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(campo);
        editor.apply();
    }

}
