package ifrs.gp.teste;


import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Debug {
    private File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    private File txt = new File(sd, "magic_log.txt");
    private String entrada;
    private Context contexto;

    public Debug(String entrada, Context contexto) {
        this.contexto = contexto;
        this.entrada = entrada;

        if (txt.exists()) {
            txt.delete();
        }
        try {
            FileWriter fw = new FileWriter(txt, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(entrada);
            bw.close();
            fw.close();
        } catch (IOException e) {
            Toast toast = Toast.makeText(contexto, e.toString(), Toast.LENGTH_LONG);
            toast.show();
        }
    }


}
