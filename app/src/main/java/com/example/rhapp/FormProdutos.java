package com.example.rhapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FormProdutos extends AppCompatActivity {

    private EditText produtoEditText;
    private EditText quantidadeEditText;
    private TextView resultadoTextView;
    private List<Produto> produtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_produtos);

        produtoEditText = findViewById(R.id.produtoEditTextForm);
        quantidadeEditText = findViewById(R.id.quantidadeEditTextForm);
        resultadoTextView = findViewById(R.id.resultadoTextViewForm);

        produtos = new ArrayList<>();

        Button cadastrarButton = findViewById(R.id.cadastrarButtonForm);
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarProduto();
            }
        });

        Button mostrarButton = findViewById(R.id.mostrarButtonForm);
        mostrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTodos();
            }
        });
    }

    private void cadastrarProduto() {
        String nome = produtoEditText.getText().toString();
        String quantidadeStr = quantidadeEditText.getText().toString();

        if (nome.isEmpty() || quantidadeStr.isEmpty()) {
            // Verifica se os campos estão vazios
            resultadoTextView.setText("Por favor, preencha todos os campos.");
            return;
        }

        int quantidade = Integer.parseInt(quantidadeStr);
        Produto produto = new Produto(nome, quantidade);
        produtos.add(produto);
        produtoEditText.setText("");
        quantidadeEditText.setText("");

        resultadoTextView.setText("Produto cadastrado com sucesso!");
    }

    private void mostrarTodos() {
        if (produtos.isEmpty()) {
            resultadoTextView.setText("Nenhum produto cadastrado.");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Produto produto : produtos) {
            stringBuilder.append("Produto: ").append(produto.getNome()).append(", Quantidade: ").append(produto.getQuantidade()).append("\n");
        }
        resultadoTextView.setText(stringBuilder.toString());
    }
}
