package com.example.thirdeye;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "settings")
public class Settings {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "rate")
    public float rate;
    @ColumnInfo(name = "output_speech")
    public String output_speech;
    @ColumnInfo(name = "input_lang")
    public String input_lang;
    @ColumnInfo(name = "blindness")
    public boolean blindness;
    @ColumnInfo(name = "trans_input")
    public String trans_input;
    public Settings(){}
    public Settings(String optlanguage, String input_lang,String trans_input, boolean blindness,float speechrate) {
        this.output_speech = optlanguage;
        this.blindness = blindness;
        this.rate = speechrate;
        this.input_lang = input_lang;
        this.trans_input = trans_input;
    }
}
// entity created