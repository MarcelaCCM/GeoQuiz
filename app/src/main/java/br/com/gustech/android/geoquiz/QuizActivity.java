package br.com.gustech.android.geoquiz;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final int REQUEST_CHEAT_CODE = 0;

    private boolean mCheater;
    private Button mButtonVerdadeiro;
    private Button mButtonFalso;
    private Button mTrapacear;
    private ImageButton mButtonAvancar;
    private ImageButton mButtonVoltar;
    private TextView mQuestoesTextView;
    private Questao[] mBancoPergustas = new Questao[] {
            new Questao(R.string.pergunta1, true),
            new Questao(R.string.pergunta2, true),
            new Questao(R.string.pergunta3, false),
            new Questao(R.string.pergunta4, false),
            new Questao(R.string.pergunta5, true)
    };

    int mCurrentIndex = 0;
    int mPontuacao = 0;
    int mQuantidadeRespondida = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Erro ao contruir a Activity.");
        setContentView(R.layout.activity_quiz);

        Collections.shuffle(Arrays.asList(mBancoPergustas));
        mQuestoesTextView = (TextView) findViewById(R.id.questao);

        mButtonVerdadeiro = (Button) findViewById(R.id.verdadeiro);
        mButtonVerdadeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                checarQuetao(true);
                mostrarPontuacao();
            }
        });

        mButtonFalso = (Button) findViewById(R.id.falso);
        mButtonFalso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                checarQuetao(true);
                mostrarPontuacao();
            }

        });

        mButtonAvancar = (ImageButton) findViewById(R.id.avancar);
        mButtonAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mBancoPergustas.length;
                mCheater = false;
                atualizarQuestao();
            }
        });

        mButtonVoltar = (ImageButton) findViewById(R.id.voltar);
        mButtonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrentIndex == 0) {
                    mCurrentIndex = mBancoPergustas.length - 1;
                } else {
                    mCurrentIndex--;
                }
                mCheater = false;
                atualizarQuestao();
            }
        });

        mTrapacear = (Button) findViewById(R.id.trapacear);
        mTrapacear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean resposta = mBancoPergustas[mCurrentIndex].ismAnswerTrue();
                Intent intent = CheatActivity.novoIntent(QuizActivity.this, resposta);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);

            }
        });
    }

    private void atualizarQuestao() {
        int question = mBancoPergustas[mCurrentIndex].getmTextResId();
        mQuestoesTextView.setText(question);
    }

    private void checarQuetao(boolean pressionarButton) {
        boolean perguntaVerdadeira = mBancoPergustas[mCurrentIndex].ismAnswerTrue();
        int valor;

        if(mCheater) {
            valor = R.string.judgment_toast;
            mPontuacao++;
        } else {
            if(perguntaVerdadeira == pressionarButton) {
                valor = R.string.correto_toast;
                mPontuacao++;
            } else {
                valor = R.string.incorreto_toast;
            }
        }
        Toast.makeText(this, valor, Toast.LENGTH_SHORT).show();
        mQuantidadeRespondida++;

    }

    private void desativarBotoes() {
        mButtonVerdadeiro.setEnabled(false);
        mButtonFalso.setEnabled(false);
        mButtonAvancar.setEnabled(false);
        mButtonVoltar.setEnabled(false);
    }

    private void mostrarPontuacao() {
        if(mQuantidadeRespondida == 5) {
            desativarBotoes();
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Pontuação");
            alertDialog.setMessage("Voce acertou "+mPontuacao);
            alertDialog.setButton(Dialog.BUTTON_NEUTRAL,"Fechar", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i){
                    finish();
                }
            });
            alertDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dados) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CHEAT_CODE) {
            if(dados == null) {
                return;
            }
        }
        mCheater = CheatActivity.resposta(dados);
    }

}
