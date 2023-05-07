package kz.proffix4.firebase_firestore;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db; // База данных
    private final String collectionPath = "my_table"; // Имя таблицы в БД

    private TextView textView;
    private String lastId;
    private EditText EditTextName, EditTextPrice;


    /**
     * Запуск программы
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = FirebaseFirestore.getInstance(); // Подключение к БД

        textView = findViewById(R.id.textView);

        EditTextName = findViewById(R.id.editTextName);
        EditTextPrice = findViewById(R.id.editTextPrice);
    }

    /**
     * Добавление новой записи в таблицу БД
     */
//    public String name = ;
//    public String price = ;

    public void onAdd(View view) {
        textView.setText("   добавление данных в облако...");

        Map<String, Object> record = new HashMap<>(); // Переменная - запись для таблицы БД
//        record.put("currentDate", new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
//        record.put("currentTime", new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));



        record.put("name", String.valueOf(EditTextName.getText()));
        record.put("price", String.valueOf(EditTextPrice.getText()));

        db.collection(collectionPath)
                .add(record)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        textView.setText("Добавлена запись в таблицу БД с ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        textView.setText("Ошибка при добавлении записи в БД: " + e.getMessage());
                    }
                });

    }


    /**
     * Загрузка данных с таблицы БД
     */
    public void onLoad(View view) {
        textView.setText("   загрузка базы данных с облака...");

        db.collection(collectionPath)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            textView.setText("");
                            // цикл по записям таблицы БД
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lastId = document.getId();
                                textView.append(lastId + " => " + document.getData() + "\n");
                            }
                        } else {
                            textView.setText("Ошибка получения записей: " + task.getException().getMessage());
                        }
                    }
                });
    }


    /**
     * Удаление последней записи в таблице БД
     */
    public void onDelete(View view) {
        db.document(collectionPath + "/" + lastId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Последняя запись удалена", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Ошибка при удалении записи " + e, Toast.LENGTH_SHORT).show();
                    }
                });

        onLoad(null);
    }

    /**
     * Изменение последней записи в таблице БД
     */
    public void onModify(View view) {
        Map<String, Object> record = new HashMap<>(); // Переменная - запись для таблицы БД
        record.put("name", "TSN");
        record.put("os", "linux");

//        db.document(collectionPath + "/" + lastId).set(record); // Так заменяются все данные на новые
        db.document(collectionPath+"/" + lastId).update(record); // Так изменяются старые/добавляются новые данные

        Toast.makeText(MainActivity.this, "Последняя запись изменена", Toast.LENGTH_SHORT).show();

        onLoad(null);
    }


    /**
     * Выход из программы
     */
    public void onExit(View view) {
        finish();
    }

}
