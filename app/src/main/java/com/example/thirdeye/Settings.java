package com.example.thirdeye;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Define the Settings entity for the Room database with the table name "settings"
@Entity(tableName = "settings")
public class Settings {

    // Primary key for the settings table, auto-generated
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    // Column for the speech rate
    @ColumnInfo(name = "rate")
    public float rate;

    // Column for the output speech language
    @ColumnInfo(name = "output_speech")
    public String output_speech;

    // Column for the input language
    @ColumnInfo(name = "input_lang")
    public String input_lang;

    // Column for the blindness flag
    @ColumnInfo(name = "blindness")
    public boolean blindness;

    // Column for the translated input
    @ColumnInfo(name = "trans_input")
    public String trans_input;

    // Default constructor required for Room
    public Settings() {
    }

    // Constructor to initialize the Settings object
    public Settings(String optlanguage, String input_lang, String trans_input, boolean blindness, float speechrate) {
        this.output_speech = optlanguage;
        this.blindness = blindness;
        this.rate = speechrate;
        this.input_lang = input_lang;
        this.trans_input = trans_input;
    }
}
// entity created
