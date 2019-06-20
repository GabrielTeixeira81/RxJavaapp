package br.com.digitalhouse.rxjavaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(view -> comecandoComRxJava());
        comecandoComRxJava();
    }

        public void comecandoComRxJava() {

            Observable.range(1, 100)
                    .map(integer -> integer * 3)
                    .filter(integer -> integer % 2 == 0)
                    .skip(20)
                    .subscribe(numero -> {
                        System.out.println(numero);
                    });

            Observable.just(10)
                    .subscribe(System.out::println);

            Observable.fromIterable(Arrays.asList("Tairo", "Jessica", "Mateus", "Person"))
                    .filter(s -> s.contains("M"))
                    .subscribe(System.out::println);

            Observable<String> stringObservable = Observable.create(emitter -> {

                try {
                    emitter.onNext("Tairo :)");
                    emitter.onNext("Jessica :)");

                    emitter.onError(new Exception("Erro não manipulável"));

                    emitter.onNext("Viny :)");
                    emitter.onComplete();


                } catch (Exception e) {
                    emitter.onError(e);
                }

            });

            stringObservable.subscribe(s -> {
                System.out.println(s);
            }, throwable -> {
                System.out.println(throwable.getMessage());
            }, () -> {
                System.out.println("Completou...!!");
            });

            getUsuariosOnServer()
                    .subscribe(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        System.out.println(s);
                    }, throwable -> {
                        System.out.println(throwable.getMessage());
                    });

            new Thread(() -> {

                runOnUiThread(() -> {;
                btnEnviar.setText("BT");
            });
            }).start();

        }

        Observable<String> getUsuariosOnServer() {
            return Observable.create(emitter -> {
                // Chamada no banco
                emitter.onNext("Tairo :)");
                emitter.onNext("Jessica :)");
                emitter.onNext("Viny :)");
                emitter.onComplete();
            });

        }

}
