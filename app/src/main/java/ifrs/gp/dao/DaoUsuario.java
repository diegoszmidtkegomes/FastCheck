package ifrs.gp.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import ifrs.gp.model.SincronizaResposta;
import ifrs.gp.model.request.LoginRequest;

/**
 * Created by diego on 27/09/2017.
 */

public class DaoUsuario {

    public void executa(Context contexto, SincronizaResposta _delegate, LoginRequest req) {
        new DaoUsuario.Ws(contexto, _delegate, req).execute();
    }

    public class Ws extends AsyncTask<Object, Object, String> {
        private final String URL = "https://restaurant-integration-dev-cloned-gabdevilshunter.c9users.io/index.php/Webservice";
        private final String NAMESPACE = "urn:server";
        private final String METHOD = "cadastroUsuarios";
        private final String SOAP_ACTION_PREFIX = "urn:server#cadastroUsuarios";

        private Object resposta;
        private SincronizaResposta _delegate = null;
        private Context contexto;
        private String pedido;
        private LoginRequest req;

        public Ws(Context contexto, SincronizaResposta _delegate, LoginRequest req) {
            this.contexto = contexto;
            this._delegate = _delegate;
            this.req = req;
        }

        @Override
        protected String doInBackground(Object... params) {
            PropertyInfo param1 = new PropertyInfo();
            param1.setName("email");
            param1.setValue(req.getEmail());
            param1.setType(String.class);

            PropertyInfo param2 = new PropertyInfo();
            param2.setName("senha");
            param2.setValue(req.getSenha());
            param2.setType(String.class);

            SoapObject request = new SoapObject(NAMESPACE, METHOD);
            request.addProperty(param1);
            request.addProperty(param2);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(URL, 10000);
            transporte.debug = true;

            ArrayList<HeaderProperty> header_p = new ArrayList<HeaderProperty>();

            header_p.add(new HeaderProperty("php_auth_user", "admin"));
            header_p.add(new HeaderProperty("php_auth_pw", "admin"));

            try {
                transporte.call(SOAP_ACTION_PREFIX, envelope, header_p);
//                new Debug(transporte.requestDump, contexto);
//                System.exit(0);
                resposta = envelope.getResponse();
                Log.i("responsejson", resposta.toString());
                transporte.getServiceConnection().disconnect();
            } catch (Exception e) {
                Log.i("responsejson", e.toString());
            }
            String confirma = null;
            if (resposta != null)
                confirma = resposta.toString();

            return confirma;
        }

        @Override
        protected void onPostExecute(String lista) {

                _delegate.processoEncerrado(lista);

        }
    }


}
