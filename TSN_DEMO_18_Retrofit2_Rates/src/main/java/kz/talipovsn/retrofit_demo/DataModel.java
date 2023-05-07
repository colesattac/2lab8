package kz.talipovsn.retrofit_demo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


// КЛАСС-МОДЕЛЬ ДАННЫХ С САЙТА  https://data.egov.kz/api/v4/valutalar_bagamdary4/v708
// Создается через сайт: http://www.jsonschema2pojo.org/
// с настройками: Java - JSON - Gson

public class DataModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name_kaz")
    @Expose
    private String nameKaz;
    @SerializedName("edinica_izmerenia")
    @Expose
    private String edinicaIzmerenia;
    @SerializedName("sootnowenie")
    @Expose
    private String sootnowenie;
    @SerializedName("name_rus")
    @Expose
    private String nameRus;
    @SerializedName("kurs")
    @Expose
    private String kurs;
    @SerializedName("kod")
    @Expose
    private String kod;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameKaz() {
        return nameKaz;
    }

    public void setNameKaz(String nameKaz) {
        this.nameKaz = nameKaz;
    }

    public String getEdinicaIzmerenia() {
        return edinicaIzmerenia;
    }

    public void setEdinicaIzmerenia(String edinicaIzmerenia) {
        this.edinicaIzmerenia = edinicaIzmerenia;
    }

    public String getSootnowenie() {
        return sootnowenie;
    }

    public void setSootnowenie(String sootnowenie) {
        this.sootnowenie = sootnowenie;
    }

    public String getNameRus() {
        return nameRus;
    }

    public void setNameRus(String nameRus) {
        this.nameRus = nameRus;
    }

    public String getKurs() {
        return kurs;
    }

    public void setKurs(String kurs) {
        this.kurs = kurs;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

}
