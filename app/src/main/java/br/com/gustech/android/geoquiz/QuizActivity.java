package br.com.gustech.android.geoquiz;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {
            private Button mBtnVerdadeiro;
            private Button mButtonVerdadeiro;
            private Button mButtonFalso;
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
                setContentView(R.layout.activity_quiz);

                Collections.shuffle(Arrays.asList(mBancoPergustas));
                mQuestoesTextView = (TextView) findViewById(R.id.questao);

                mButtonVerdadeiro = (Button) findViewById(R.id.verdadeiro);
                mButtonVerdadeiro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v ) {
                        checarQuetao(true);
                    }
                });

                mButtonFalso = (Button) findViewById(R.id.falso);
                mButtonFalso.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v ) {
                        checarQuetao(true);
                    }

                });

                mButtonAvancar = (ImageButton) findViewById(R.id.avancar);
                mButtonAvancar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCurrentIndex = (mCurrentIndex + 1) % mBancoPergustas.length;
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
                        atualizarQuestao();
                    }
                });
            }

            private void atualizarQuestao() {
                int question = mBancoPergustas[mCurrentIndex].getmTextResId();
                mQuestoesTextView.setText(question);
            }

            private void checarQuetao(boolean pressionarButton) {
                boolean perguntaVerdadeira = mBancoPergustas[mCurrentIndex].ismAnswerTrue();
                if(perguntaVerdadeira == pressionarButton) {
                    Toast.makeText(this, R.string.correto_toast, Toast.LENGTH_SHORT).show();
                    mPontuacao++;
                    mQuantidadeRespondida++;
                    mostrarPontuacao();
                } else {
                    Toast.makeText(this, R.string.incorreto_toast, Toast.LENGTH_SHORT).show();
                }

            }

            private void mostrarPontuacao() {
                if(mQuantidadeRespondida == 5) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setTitle("Pontuação");
                    alertDialog.setMessage("Voce acertou "+mPontuacao);
                }
            }

        }
