package ifrs.gp.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import ifrs.gp.model.Item;
import ifrs.gp.model.SincronizaResposta;

public class DaoLista {

    public void executa(Context contexto, SincronizaResposta _delegate) {
        new Ws(contexto, _delegate).execute();
    }


    public class Ws extends AsyncTask<String, Void, Item[]> {
        private final String URL = "https://restaurant-integration-dev-cloned-gabdevilshunter.c9users.io/index.php/Webservice";
        private final String NAMESPACE = "urn:server";
        private final String METHOD = "get_all_itens";
        private final String SOAP_ACTION_PREFIX = NAMESPACE + "#" + METHOD;

        private Object resposta;
        private SincronizaResposta _delegate = null;
        private Context contexto;

        public Ws(Context contexto, SincronizaResposta _delegate) {
            this.contexto = contexto;
            this._delegate = _delegate;
        }

        @Override
        protected Item[] doInBackground(String... params) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL, 10000);
            transporte.debug = true;

            List<HeaderProperty> header_p = new ArrayList<HeaderProperty>();

            header_p.add(new HeaderProperty("php_auth_user", "admin"));
            header_p.add(new HeaderProperty("php_auth_pw", "admin"));

            try {
                transporte.call(SOAP_ACTION_PREFIX, envelope, header_p);
                resposta = envelope.getResponse();
                Log.i("responsejson", resposta.toString());
                transporte.getServiceConnection().disconnect();
            } catch (Exception e) {
                Log.i("responsejson", e.toString());
            }

            Item[] lista = null;
            if (resposta != null)
                lista = new Gson().fromJson(resposta.toString(), Item[].class);

            return lista;
        }

        @Override
        protected void onPostExecute(Item[] lista) {
            if (_delegate != null)
                _delegate.processoEncerrado(lista);
        }

    }
}



