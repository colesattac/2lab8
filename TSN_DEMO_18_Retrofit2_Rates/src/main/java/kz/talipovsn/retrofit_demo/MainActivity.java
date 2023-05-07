package kz.talipovsn.retrofit_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DataModel> dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataset = new ArrayList<>();

        recyclerView = findViewById(R.id.posts_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DataAdapter(dataset));

        RestAdapter.getApiClient().getData("").enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                try {
                    dataset.addAll(response.body());
                } catch (Exception ignore) {
                    emptyData();
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                emptyData();
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            void emptyData() {
                DataModel dm = new DataModel();
                dm.setKurs("Проверьте доступность сайта и сети");
                dm.setNameRus("Нет данных");
                dataset.add(dm);
            }

        });
    }
}
