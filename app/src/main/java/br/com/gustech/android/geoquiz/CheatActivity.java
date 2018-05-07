package br.com.gustech.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_PERGUNTA_VERDADEIRA = "br.com.gustech.android.geoquiz.pergunta_verdadeira";
    private static final String EXTRA_MOSTRANDO_RESPOSTA = "br.com.gustech.android.geoquiz.mostrando_resposta";

    private boolean mPerguntaVerdadeira;
    private TextView mTextView2;
    private Button mBtnMostrarResposta;

    private void setResultadoResposta(boolean respostaView) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_MOSTRANDO_RESPOSTA, respostaView);
        setResult(RESULT_OK, intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mPerguntaVerdadeira = getIntent().getBooleanExtra(EXTRA_PERGUNTA_VERDADEIRA,false);

        mTextView2 = (TextView) findViewById(R.id.textView2);

        mBtnMostrarResposta = (Button) findViewById(R.id.trapacear);
        mBtnMostrarResposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPerguntaVerdadeira) {
                    mTextView2.setText(R.string.verdadeiro);
                } else {
                    mTextView2.setText(R.string.falso);
                }
                setResultadoResposta(true);
            }
        });

    }

    public static boolean resposta(Intent resultado) {
        return resultado.getBooleanExtra(EXTRA_MOSTRANDO_RESPOSTA, false);
    }

    public static Intent novoIntent(Context packageContext, boolean perguntaVerdadeira) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_PERGUNTA_VERDADEIRA, perguntaVerdadeira);
        return intent;
    }
}

 