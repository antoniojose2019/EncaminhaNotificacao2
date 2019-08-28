package br.com.madadevmobile.encaminhanotificacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText titulo, mensagem, empresa, urlImagem, site, autorizacao, tipo_conteudo, topico;
    private Button button_enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Formulário de notificações");


        titulo = findViewById(R.id.editText_titulo);
        mensagem = findViewById(R.id.editText_mensagem);
        empresa = findViewById(R.id.editText_empresa);
        urlImagem = findViewById(R.id.editText_url_imagem);
        site = findViewById(R.id.editText_site);
        autorizacao = findViewById(R.id.editText_Autorizacao);
        tipo_conteudo = findViewById(R.id.editText_tipo_conteudo);
        topico = findViewById(R.id.editText_topico);

        button_enviar = findViewById(R.id.bt_enviar_notificacao);


        button_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iniciarNotifiacao();

            }
        });

    }

    private void iniciarNotifiacao(){

        JSONObject notificacao = new JSONObject();
        JSONObject dados = new JSONObject();

        String titutoString = titulo.getText().toString();
        String mensagemString = mensagem.getText().toString();
        String urlImagemString = urlImagem.getText().toString();
        String empresaString = empresa.getText().toString();
        String topicoString = topico.getText().toString();
        String siteString = site.getText().toString();
        String autoriazacaoString = autorizacao.getText().toString();
        String tipoConteudoString = tipo_conteudo.getText().toString();

        try {

            notificacao.put("to", "/topics/"+topicoString);

            dados.put("titulo", titutoString);
            dados.put("mensagem", mensagemString);
            dados.put("urlimagem", urlImagemString);
            dados.put("nome", empresaString);

            notificacao.put("data", dados);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*metodo que enviar a notificação*/
        enviarNotificacao(siteString, notificacao, autoriazacaoString, tipoConteudoString);


    }

    private void enviarNotificacao(String site, JSONObject notificacao, final String autorizacao, final String content_type){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, site, notificacao, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Noiticação enviado com sucesso", Toast.LENGTH_SHORT).show();
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Erro . . .", Toast.LENGTH_SHORT).show();

            }
        })

        {
            public Map<String, String> getHeaders(){

                Map<String, String> header = new HashMap<>();

                header.put("Authorization", autorizacao);

                header.put("Content-Type", content_type);

                return header;
            }

        };

        requestQueue.add( jsonObjectRequest );

    }
}
