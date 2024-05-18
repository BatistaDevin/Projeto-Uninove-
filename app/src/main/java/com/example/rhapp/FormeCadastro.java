package com.example.rhapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormeCadastro extends AppCompatActivity {

    private EditText edit_nome,edit_email,edit_senha;
    private Button bt_cadastrar;
    String[] mensagens = {"Preencha todos os campos", "Cadastro realizado com sucesso"};
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forme_cadastro);

        IniciarComponentes();

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = edit_nome.getText().toString();
                String email = edit_email.getText().toString();
                String senha = edit_senha.getText().toString();

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setAnchorView(findViewById(R.id.title_cadastrese));
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else {
                    CadastrarUsuario(v);

                }
            }
        });

    }
    private void CadastrarUsuario(View v){
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){

                if (task.isSuccessful()){

                    SalvarDadosUsuario();

                    Snackbar snackbar = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_LONG);
                    snackbar.setAnchorView(findViewById(R.id.title_cadastrese));
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }

            }
        });
    }

       private void SalvarDadosUsuario(){
         String nome = edit_nome.getText().toString();

           FirebaseFirestore db = FirebaseFirestore.getInstance();

           Map<String,Object> usuarios = new HashMap<>();
           usuarios.put("nome",nome);

           usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

           DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
           documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void aVoid) {
                   Log.d("db", "Sucesso ao salvar os dados");
               }
           })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Log.d("db error", "Erro ao salvar os dados" + e.toString());
                       }
                   });

       }


       private void IniciarComponentes(){
        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        bt_cadastrar = findViewById(R.id.bt_cadastrar);
        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
      }
}