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

import ifrs.gp.model.Pedido;
import ifrs.gp.model.SincronizaResposta;
import ifrs.gp.teste.Debug;


public class DaoPedido {

    public void executa(Context contexto, SincronizaResposta _delegate) {
        new Ws(contexto, _delegate).execute();
    }


    public class Ws extends AsyncTask<Object, Object, String> {
        private final String URL = "https://restaurant-integration-dev-cloned-gabdevilshunter.c9users.io/index.php/Webservice";
        private final String NAMESPACE = "urn:server";
        private final String METHOD = "new_order";
        private final String SOAP_ACTION_PREFIX = "urn:server#new_order";

        private Object resposta;
        private SincronizaResposta _delegate = null;
        private Context contexto;
        private String pedido;

        public Ws(Context contexto, SincronizaResposta _delegate) {
            this.contexto = contexto;
            this._delegate = _delegate;
        }

        @Override
        protected String doInBackground(Object... params) {
            PropertyInfo pedido = new PropertyInfo();
            pedido.setName("pedido");
            pedido.setValue(Pedido.PEDIDO);
            pedido.setType(String.class);

            SoapObject request = new SoapObject(NAMESPACE, METHOD);
            request.addProperty(pedido);

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
            if (_delegate != null)
                _delegate.processoEncerrado(lista);
        }

    }
}



