
package kz.talipovsn.retrofit_demo_micro;

import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import static kz.talipovsn.retrofit_demo_micro.MainActivity.ApiInterface.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    // Наш интерфес передачи параметров сайту
    interface ApiInterface {
        String URL = "https://api.coindesk.com";
        String API = "/v1/bpi/currentprice.json";

        @GET(API)
        Call<DataModel> getData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Разрешаем запуск в общем потоке выполнеия длительных задач (например, чтение с сети)
        // ЭТО ТОЛЬКО ДЛЯ ПРИМЕРА, ПО-НОРМАЛЬНОМУ НАДО ВСЕ В ОТДЕЛЬНЫХ ПОТОКАХ
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        textView = findViewById(R.id.textView);

        onClick(null); // Нажмем на кнопку "Обновить"
    }

    // Кнопка "Обновить"
    public void onClick(View view) {
        try {
            // Подключаем технологию Retrofit2 для работы с сайтом
            Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
            // Делаем обращение к сайту через Retrofit2 (преобразование json -> java classes) синхронным доступом
            Response<DataModel> response = retrofit.create(ApiInterface.class).getData().execute();
            // Считываем полученные с сайта данные в java-форме от Retrofit2
            DataModel data = response.body();
            // Считываем нужные поля данных
            String rate = data.getBpi().getUSD().getRate();
            String code = data.getBpi().getUSD().getCode();
            // Выводим результат в текстовый компонент
            textView.setText("");
            textView.append("1 BTC = " + rate + " " + code);
            textView.append("\n");
        } catch (Exception e) {
            textView.setText(R.string.error);
        }
    }

}

