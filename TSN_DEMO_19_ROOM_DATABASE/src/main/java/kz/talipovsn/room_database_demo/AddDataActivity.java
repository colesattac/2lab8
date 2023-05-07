package kz.talipovsn.room_database_demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// АКТИВНОСТЬ ДЛЯ ДОБАВЛЕНИЯ НОВОЙ ЗАПИСИ В ROOM-БД
// ИСПОЛЬЗУЕМ ТЕХНОЛОГИЮ BUTTERKNIFE ДЛЯ БИНДИНГА КОМПОНЕНТ С ПЕРЕМЕННЫМИ
public class AddDataActivity extends AppCompatActivity {

    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.description)
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.save)
    public void onSaveClick() {
        RoomDatabase database = Database.getInstance().getDatabaseInstance();

        try {
            DataModel model = new DataModel();
            model.setTitle(title.getText().toString());
            model.setDescription(description.getText().toString());
            database.getDao().insert(model);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Нельзя создавать записи с одинаковым названием!", Toast.LENGTH_LONG).show();
        }

        finish();
    }
}
