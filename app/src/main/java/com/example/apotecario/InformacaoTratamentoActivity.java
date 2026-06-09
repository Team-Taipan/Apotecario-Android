package com.example.apotecario;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;

public class InformacaoTratamentoActivity extends AppCompatActivity {

    private int quantidade = 1;
    private TextView tvQuantidade;
    private TextView tvDataInicio, tvHorarioUso, tvDataTermino, tvIntervalo;

    private Medicamento medicamentoSelecionado;
    private int frequenciaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_informacao_tratamento);

        medicamentoSelecionado = (Medicamento) getIntent().getSerializableExtra("MEDICAMENTO");

        frequenciaSelecionada = getIntent().getIntExtra("FREQUENCIA_SELECIONADA", 0);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recuperar a frequência selecionada
        int frequencia = frequenciaSelecionada;

        // Referências da UI
        tvQuantidade = findViewById(R.id.tvQuantidade);
        tvDataInicio = findViewById(R.id.tvDataInicio);
        tvHorarioUso = findViewById(R.id.tvHorarioUso);
        tvDataTermino = findViewById(R.id.tvDataTermino);
        tvIntervalo = findViewById(R.id.tvIntervalo);

        ImageButton btnMenos = findViewById(R.id.btnMenos);
        ImageButton btnMais = findViewById(R.id.btnMais);

        LinearLayout llDataInicio = findViewById(R.id.llDataInicio);
        LinearLayout llHorarioUso = findViewById(R.id.llHorarioUso);
        LinearLayout llDataTermino = findViewById(R.id.llDataTermino);
        LinearLayout llIntervalo = findViewById(R.id.llIntervalo);
        LinearLayout llSelectIntervalo = findViewById(R.id.llSelectIntervalo);

        // Inicialização com data e hora do dispositivo
        Calendar calendar = Calendar.getInstance();

        // (DD/MM/AAAA)
        String dataAtual = String.format(Locale.getDefault(), "%02d/%02d/%d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
        tvDataInicio.setText(dataAtual);

        // (HH:MM)
        String horaAtual = String.format(Locale.getDefault(), "%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        tvHorarioUso.setText(horaAtual);

        // Lógica de Visibilidade: Se for 1 vez ao dia, esconde o intervalo
        if (frequencia == 1) {
            llIntervalo.setVisibility(View.GONE);
        } else {
            llIntervalo.setVisibility(View.VISIBLE);
        }

        // Lógica do Contador
        btnMenos.setOnClickListener(v -> {
            if (quantidade > 1) {
                quantidade--;
                tvQuantidade.setText(String.valueOf(quantidade));
            }
        });

        btnMais.setOnClickListener(v -> {
            quantidade++;
            tvQuantidade.setText(String.valueOf(quantidade));
        });

        // Seletores de Data e Hora nativos
        llDataInicio.setOnClickListener(v -> showDatePicker(tvDataInicio));
        llDataTermino.setOnClickListener(v -> showDatePicker(tvDataTermino));
        llHorarioUso.setOnClickListener(v -> showTimePicker());

        // Drop-down de Intervalo
        llSelectIntervalo.setOnClickListener(v -> showIntervalMenu());

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());

        findViewById(R.id.btnProximo).setOnClickListener(v -> {
            // Navega para a tela de estoque
            Intent intent = new Intent(this, EstoqueMedicamentoActivity.class);

            intent.putExtra("MEDICAMENTO", medicamentoSelecionado);

            intent.putExtra("FREQUENCIA_SELECIONADA", frequenciaSelecionada);

            intent.putExtra("QTD_DOSE", quantidade);

            intent.putExtra("DATA_INICIO", tvDataInicio.getText().toString());

            intent.putExtra("DATA_FIM", tvDataTermino.getText().toString());

            intent.putExtra("HORARIO", tvHorarioUso.getText().toString());

            intent.putExtra("INTERVALO", tvIntervalo.getText().toString());

            startActivity(intent);
        });
    }

    private void showIntervalMenu() {
        PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.llSelectIntervalo));
        popupMenu.getMenu().add("De 4 em 4 horas");
        popupMenu.getMenu().add("De 6 em 6 horas");
        popupMenu.getMenu().add("De 8 em 8 horas");
        popupMenu.getMenu().add("De 12 em 12 horas");
        popupMenu.getMenu().add("Uma vez por semana");

        popupMenu.setOnMenuItemClickListener(item -> {
            tvIntervalo.setText(item.getTitle());
            tvIntervalo.setTextColor(getResources().getColor(android.R.color.black));
            return true;
        });
        popupMenu.show();
    }

    private void showDatePicker(TextView targetTextView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String date = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
            targetTextView.setText(date);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            String time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
            tvHorarioUso.setText(time);
        }, hour, minute, true);
        timePickerDialog.show();
    }
}