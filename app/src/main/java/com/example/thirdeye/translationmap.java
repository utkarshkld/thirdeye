package com.example.thirdeye;
import static com.example.thirdeye.MainActivity.translationMap;
import static com.example.thirdeye.MainActivity.commandmap;

import java.util.ArrayList;
import java.util.List;

public class translationmap {

     public static void initializetransMap() {
             List<String> objectTranslations = new ArrayList<>();
             objectTranslations.add("voorwerp"); // Afrikaans
             objectTranslations.add("objekt"); // Albanian
             objectTranslations.add("كائن"); // Arabic
             objectTranslations.add("অবজেক্ট"); // Bengali
             objectTranslations.add("обект"); // Bulgarian
             objectTranslations.add("对象"); // Chinese
             objectTranslations.add("česky"); // Czech
             objectTranslations.add("objekt"); // Danish
             objectTranslations.add("object"); // Dutch
             objectTranslations.add("object"); // English
             objectTranslations.add("objekti"); // Finnish
             objectTranslations.add("objet"); // French
             objectTranslations.add("obxecto"); // Galician
             objectTranslations.add("ობიექტი"); // Georgian
             objectTranslations.add("Objekt"); // German
             objectTranslations.add("αντικείμενο"); // Greek
             objectTranslations.add("વસ્તુ"); // Gujarati
             objectTranslations.add("objè"); // Haitian
             objectTranslations.add("אובייקט"); // Hebrew
             objectTranslations.add("वस्तु"); // Hindi
             objectTranslations.add("objektum"); // Hungarian
             objectTranslations.add("hlutur"); // Icelandic
             objectTranslations.add("objek"); // Indonesian
             objectTranslations.add("oggetto"); // Italian
             objectTranslations.add("オブジェクト"); // Japanese
             objectTranslations.add("ವಸ್ತು"); // Kannada
             objectTranslations.add("객체"); // Korean
             objectTranslations.add("objekts"); // Latvian
             objectTranslations.add("objektas"); // Lithuanian
             objectTranslations.add("објект"); // Macedonian
             objectTranslations.add("objek"); // Malay
//             objectTranslations.add("वास्तु");
             objectTranslations.add("വസ്തുവിന്റെ"); // Malayalam
             objectTranslations.add("oġġett"); // Maltese
             objectTranslations.add("ऑब्जेक्ट"); // Marathi
             objectTranslations.add("objekt"); // Norwegian
             objectTranslations.add("obiekt"); // Polish
             objectTranslations.add("objeto"); // Portuguese
             objectTranslations.add("obiect"); // Romanian
             objectTranslations.add("объект"); // Russian
             objectTranslations.add("objekt"); // Slovak
             objectTranslations.add("predmet"); // Slovenian
             objectTranslations.add("objeto"); // Spanish
             objectTranslations.add("kitu"); // Swahili
             objectTranslations.add("objekt"); // Swedish
             objectTranslations.add("bagay"); // Tagalog
             objectTranslations.add("பொருள்"); // Tamil
             objectTranslations.add("వస్తువు"); // Telugu
             objectTranslations.add("วัตถุ"); // Thai
             objectTranslations.add("nesne"); // Turkish
             objectTranslations.add("об'єкт"); // Ukrainian
             objectTranslations.add("اشیاء"); // Urdu
             objectTranslations.add("đối tượng");//Vietnamese
             commandmap.put("object",objectTranslations);
             List<String> readTranslations = new ArrayList<>();
             readTranslations.add("lees"); // Afrikaans
             readTranslations.add("lexoni"); // Albanian
             readTranslations.add("اقرأ"); // Arabic
             readTranslations.add("পড়া"); // Bengali
             readTranslations.add("чета"); // Bulgarian
             readTranslations.add("阅读"); // Chinese
             readTranslations.add("číst"); // Czech
             readTranslations.add("læse"); // Danish
             readTranslations.add("lezení"); // Dutch
             readTranslations.add("read"); // English
             readTranslations.add("lue"); // Finnish
             readTranslations.add("lire"); // French
             readTranslations.add("ler"); // Galician
             readTranslations.add("წაიკითხეთ"); // Georgian
             readTranslations.add("lesen"); // German
             readTranslations.add("διαβάζω"); // Greek
             readTranslations.add("વાંચો"); // Gujarati
             readTranslations.add("li"); // Haitian
             readTranslations.add("קריאה"); // Hebrew
             readTranslations.add("पढ़ना"); // Hindi
             readTranslations.add("olvas"); // Hungarian
             readTranslations.add("lesa"); // Icelandic
             readTranslations.add("membaca"); // Indonesian
             readTranslations.add("leggere"); // Italian
             readTranslations.add("読む"); // Japanese
             readTranslations.add("ಓದು"); // Kannada
             readTranslations.add("읽기"); // Korean
             readTranslations.add("lasīt"); // Latvian
             readTranslations.add("skaityti"); // Lithuanian
             readTranslations.add("читај"); // Macedonian
             readTranslations.add("membaca"); // Malay
             readTranslations.add("വായിക്കുക"); // Malayalam
             readTranslations.add("taqra"); // Maltese
             readTranslations.add("वाचा"); // Marathi
             readTranslations.add("lese"); // Norwegian
             readTranslations.add("czytać"); // Polish
             readTranslations.add("ler"); // Portuguese
             readTranslations.add("citi"); // Romanian
             readTranslations.add("читать"); // Russian
             readTranslations.add("čítaj"); // Slovak
             readTranslations.add("beri"); // Slovenian
             readTranslations.add("leer"); // Spanish
             readTranslations.add("soma"); // Swahili
             readTranslations.add("läsa"); // Swedish
             readTranslations.add("magbasa"); // Tagalog
             readTranslations.add("படிக்க"); // Tamil
             readTranslations.add("చదువు"); // Telugu
             readTranslations.add("อ่าน"); // Thai
             readTranslations.add("okumak"); // Turkish
             readTranslations.add("читати"); // Ukrainian
             readTranslations.add("پڑھنا"); // Urdu
             readTranslations.add("đọc"); // Vietnamese
             commandmap.put("read",readTranslations);
             List<String> translateTranslations = new ArrayList<>();
             translateTranslations.add("vertaal"); // Afrikaans
             translateTranslations.add("përkthe"); // Albanian
             translateTranslations.add("ترجمة"); // Arabic
             translateTranslations.add("অনুবাদ"); // Bengali
             translateTranslations.add("превеждам"); // Bulgarian
             translateTranslations.add("翻译"); // Chinese
             translateTranslations.add("přeložit"); // Czech
             translateTranslations.add("oversætte"); // Danish
             translateTranslations.add("vertalen"); // Dutch
             translateTranslations.add("translate"); // English
             translateTranslations.add("kääntää"); // Finnish
             translateTranslations.add("traduire"); // French
             translateTranslations.add("traducir"); // Galician
             translateTranslations.add("თარგმნა"); // Georgian
             translateTranslations.add("übersetzen"); // German
             translateTranslations.add("μεταφράζω"); // Greek
             translateTranslations.add("અનુવાદ"); // Gujarati
             translateTranslations.add("tradui"); // Haitian
             translateTranslations.add("תרגם"); // Hebrew
             translateTranslations.add("अनुवाद करें"); // Hindi
             translateTranslations.add("fordít"); // Hungarian
             translateTranslations.add("þýða"); // Icelandic
             translateTranslations.add("menerjemahkan"); // Indonesian
             translateTranslations.add("tradurre"); // Italian
             translateTranslations.add("翻訳する"); // Japanese
             translateTranslations.add("ಅನುವಾದಿಸಿ"); // Kannada
             translateTranslations.add("번역"); // Korean
             translateTranslations.add("tulkot"); // Latvian
             translateTranslations.add("vertėjas"); // Lithuanian
             translateTranslations.add("преведува"); // Macedonian
             translateTranslations.add("terjemahkan"); // Malay
             translateTranslations.add("അനുവാദം"); // Malayalam
             translateTranslations.add("jtraduċi"); // Maltese
             translateTranslations.add("भाषांतर करा"); // Marathi
             translateTranslations.add("oversette"); // Norwegian
             translateTranslations.add("tłumaczyć"); // Polish
             translateTranslations.add("traduzir"); // Portuguese
             translateTranslations.add("traduce"); // Romanian
             translateTranslations.add("перевести"); // Russian
             translateTranslations.add("preložiť"); // Slovak
             translateTranslations.add("prevedi"); // Slovenian
             translateTranslations.add("traducir"); // Spanish
             translateTranslations.add("tafsiri"); // Swahili
             translateTranslations.add("översätta"); // Swedish
             translateTranslations.add("isalin"); // Tagalog
             translateTranslations.add("மொழிபெயர்"); // Tamil
             translateTranslations.add("అనువాదించు"); // Telugu
             translateTranslations.add("แปล"); // Thai
             translateTranslations.add("çevir"); // Turkish
             translateTranslations.add("перекладати"); // Ukrainian
             translateTranslations.add("ترجمہ کریں"); // Urdu
             translateTranslations.add("dịch"); // Vietnamese
             commandmap.put("translate",translateTranslations);
             List<String> settingsTranslations = new ArrayList<>();
             settingsTranslations.add("instellings"); // Afrikaans
             settingsTranslations.add("parametrat"); // Albanian
             settingsTranslations.add("الإعدادات"); // Arabic
             settingsTranslations.add("সেটিংস"); // Bengali
             settingsTranslations.add("настройки"); // Bulgarian
             settingsTranslations.add("设置"); // Chinese
             settingsTranslations.add("nastavení"); // Czech
             settingsTranslations.add("indstillinger"); // Danish
             settingsTranslations.add("instellingen"); // Dutch
             settingsTranslations.add("settings"); // English
             settingsTranslations.add("asetukset"); // Finnish
             settingsTranslations.add("paramètres"); // French
             settingsTranslations.add("configuración"); // Galician
             settingsTranslations.add("პარამეტრები"); // Georgian
             settingsTranslations.add("Einstellungen"); // German
             settingsTranslations.add("ρυθμίσεις"); // Greek
             settingsTranslations.add("સેટિંગ્સ"); // Gujarati
             settingsTranslations.add("paramèt"); // Haitian
             settingsTranslations.add("הגדרות"); // Hebrew
             settingsTranslations.add("सेटिंग्स"); // Hindi
             settingsTranslations.add("beállítások"); // Hungarian
             settingsTranslations.add("stillingar"); // Icelandic
             settingsTranslations.add("pengaturan"); // Indonesian
             settingsTranslations.add("impostazioni"); // Italian
             settingsTranslations.add("設定"); // Japanese
             settingsTranslations.add("ಸೆಟ್ಟಿಂಗುಗಳು"); // Kannada
             settingsTranslations.add("설정"); // Korean
             settingsTranslations.add("iestatījumi"); // Latvian
             settingsTranslations.add("nustatymai"); // Lithuanian
             settingsTranslations.add("поставувања"); // Macedonian
             settingsTranslations.add("tetapan"); // Malay
             settingsTranslations.add("ക്രമീകരണങ്ങൾ"); // Malayalam
             settingsTranslations.add("settings"); // Maltese
             settingsTranslations.add("सेटिंग्ज"); // Marathi
             settingsTranslations.add("innstillinger"); // Norwegian
             settingsTranslations.add("ustawienia"); // Polish
             settingsTranslations.add("configurações"); // Portuguese
             settingsTranslations.add("setări"); // Romanian
             settingsTranslations.add("настройки"); // Russian
             settingsTranslations.add("nastavenia"); // Slovak
             settingsTranslations.add("nastavitve"); // Slovenian
             settingsTranslations.add("ajustes"); // Spanish
             settingsTranslations.add("mipangilio"); // Swahili
             settingsTranslations.add("inställningar"); // Swedish
             settingsTranslations.add("mga setting"); // Tagalog
             settingsTranslations.add("அமைப்புகள்"); // Tamil
             settingsTranslations.add("సెట్టింగులు"); // Telugu
             settingsTranslations.add("การตั้งค่า"); // Thai
             settingsTranslations.add("ayarlar"); // Turkish
             settingsTranslations.add("налаштування"); // Ukrainian
             settingsTranslations.add("ترتیبات"); // Urdu
             settingsTranslations.add("cài đặt"); // Vietnamese

             // Add translations list to the map
             commandmap.put("settings", settingsTranslations);
             translationMap.put("Hindi_opening setting", "शुरुआत सेटिंग");

             translationMap.put("English_opening setting", "opening setting");

             translationMap.put("Marathi_opening setting", "उघडा सेटिंग");

             translationMap.put("Afrikaans_opening setting", "openingsinstelling");

             translationMap.put("Arabic_opening setting", "إعداد البداية");

             translationMap.put("Belarusian_opening setting", "налады адкрыцця");

             translationMap.put("Bulgarian_opening setting", "начална настройка");

             translationMap.put("Bengali_opening setting", "উদ্বোধন সেটিং");
             translationMap.put("Catalan_opening setting", "configuració d'obertura");
             translationMap.put("Czech_opening setting", "otevírací nastavení");
             translationMap.put("Welsh_opening setting", "gorsedd agor");
             translationMap.put("Danish_opening setting", "åbningsindstilling");

             translationMap.put("German_opening setting", "Starteinstellung");
             translationMap.put("Greek_opening setting", "Ρύθμιση έναρξης");
             translationMap.put("Esperanto_opening setting", "Malferma agordo");
             translationMap.put("Spanish_opening setting", "configuración de apertura");
             translationMap.put("Estonian_opening setting", "avamise seade");
             translationMap.put("Persian_opening setting", "تنظیمات شروع");
             translationMap.put("Finnish_opening setting", "avausasetus");
             translationMap.put("French_opening setting", "paramètres d'ouverture");
             translationMap.put("Irish_opening setting", "socrú oscailte");
             translationMap.put("Galician_opening setting", "axuste de apertura");
             translationMap.put("Gujarati_opening setting", "ખોલવાની સેટિંગ");
             translationMap.put("Hebrew_opening setting", "הגדרת פתיחה");
             translationMap.put("Croatian_opening setting", "postavke otvaranja");
             translationMap.put("Haitian_opening setting", "plajman an apati");
             translationMap.put("Hungarian_opening setting", "nyitási beállítás");
             translationMap.put("Indonesian_opening setting", "pengaturan pembukaan");
             translationMap.put("Icelandic_opening setting", "opnunarstilling");
             translationMap.put("Italian_opening setting", "impostazioni di apertura");
             translationMap.put("Japanese_opening setting", "オープニング設定");
             translationMap.put("Georgian_opening setting", "გახსნის პარამეტრები");

             translationMap.put("Kannada_opening setting", "ಆರಂಭಿಕ ಸೆಟ್ಟಿಂಗ್");
             translationMap.put("Korean_opening setting", "오프닝 설정");
             translationMap.put("Lithuanian_opening setting", "pradžios nustatymas");
             translationMap.put("Latvian_opening setting", "atsākuma iestatījums");
             translationMap.put("Macedonian_opening setting", "поставување на отворање");
             translationMap.put("Malay_opening setting", "tetapan pembukaan");
             translationMap.put("Maltese_opening setting", "settings ta' ftuħ");
             translationMap.put("Dutch_opening setting", "opening instelling");
             translationMap.put("Norwegian_opening setting", "åpningsinnstilling");
             translationMap.put("Polish_opening setting", "ustawienia otwierania");
             translationMap.put("Portuguese_opening setting", "configuração de abertura");
             translationMap.put("Romanian_opening setting", "setare de deschidere");
             translationMap.put("Russian_opening setting", "настройка открытия");
             translationMap.put("Slovak_opening setting", "otváracie nastavenie");

             translationMap.put("Slovenian_opening setting", "odpiranje nastavitve");
             translationMap.put("Albanian_opening setting", "konfigurimi i hapjes");
             translationMap.put("Swedish_opening setting", "öppningsinställning");
             translationMap.put("Swahili_opening setting", "mazingira ya kufungua");
             translationMap.put("Tamil_opening setting", "திறக்க அமைப்பு");
             translationMap.put("Telugu_opening setting", "ఓపెనింగ్ సెట్టింగ్");
             translationMap.put("Thai_opening setting", "การตั้งค่าการเปิด");
             translationMap.put("Tagalog_opening setting", "setting ng pagbubukas");
             translationMap.put("Turkish_opening setting", "açılış ayarı");
             translationMap.put("Ukrainian_opening setting", "відкриття налаштувань");
             translationMap.put("Urdu_opening setting", "کھولنے کی ترتیب");
             translationMap.put("Vietnamese_opening setting", "cài đặt mở");
             translationMap.put("Chinese_opening setting", "开头设置");

             translationMap.put("Afrikaans_opening object", "opening voorwerp");
             translationMap.put("Arabic_opening object", "كائن الافتتاح");
             translationMap.put("Hindi_opening object", "ओपनिंग ऑब्जेक्ट");
             translationMap.put("English_opening object", "opening object");
             translationMap.put("Marathi_opening object", "उघडणारे ऑब्जेक्ट");
             translationMap.put("Belarusian_opening object", "аб'ект адкрыцця");
             translationMap.put("Bulgarian_opening object", "обект на отваряне");
             translationMap.put("Bengali_opening object", "ওপেনিং অবজেক্ট");
             translationMap.put("Catalan_opening object", "objecte d'obertura");
             translationMap.put("Czech_opening object", "otevírací objekt");
             translationMap.put("Welsh_opening object", "gwrthrych agor");
             translationMap.put("Danish_opening object", "åbningsobjekt");
             translationMap.put("German_opening object", "Öffnungsobjekt");
             translationMap.put("Greek_opening object", "αντικείμενο έναρξης");
             translationMap.put("Esperanto_opening object", "malferma objekto");
             translationMap.put("Spanish_opening object", "objeto de apertura");
             translationMap.put("Estonian_opening object", "avamisobjekt");
             translationMap.put("Persian_opening object", "شیء افتتاحیه");
             translationMap.put("Finnish_opening object", "avauksen kohde");
             translationMap.put("French_opening object", "objet d'ouverture");
             translationMap.put("Irish_opening object", "ábhar oscailte");
             translationMap.put("Galician_opening object", "obxecto de apertura");
             translationMap.put("Gujarati_opening object", "ખોલવાની વસ્તુ");
             translationMap.put("Hebrew_opening object", "אובייקט פתיחה");
             translationMap.put("Croatian_opening object", "objekt otvaranja");
             translationMap.put("Haitian_opening object", "objè aperiti");
             translationMap.put("Hungarian_opening object", "nyitási tárgy");
             translationMap.put("Indonesian_opening object", "objek pembukaan");
             translationMap.put("Icelandic_opening object", "opnunarfyrirbæri");
             translationMap.put("Italian_opening object", "oggetto di apertura");
             translationMap.put("Japanese_opening object", "オープニングオブジェクト");
             translationMap.put("Georgian_opening object", "გახსნის ობიექტი");
             translationMap.put("Kannada_opening object", "ಆರಂಭಿಕ ವಸ್ತು");
             translationMap.put("Korean_opening object", "오프닝 오브젝트");
             translationMap.put("Lithuanian_opening object", "atidarymo objektas");
             translationMap.put("Latvian_opening object", "atvēršanas objekts");
             translationMap.put("Macedonian_opening object", "објект за отварање");
             translationMap.put("Malay_opening object", "objek pembukaan");
             translationMap.put("Maltese_opening object", "oġġett ta' ftuħ");
             translationMap.put("Dutch_opening object", "opening object");
             translationMap.put("Norwegian_opening object", "åpningsobjekt");
             translationMap.put("Polish_opening object", "obiekt otwierania");
             translationMap.put("Portuguese_opening object", "objeto de abertura");
             translationMap.put("Romanian_opening object", "obiect de deschidere");
             translationMap.put("Russian_opening object", "объект открытия");
             translationMap.put("Slovak_opening object", "otvárací objekt");
             translationMap.put("Slovenian_opening object", "objekt odpiranja");
             translationMap.put("Albanian_opening object", "objekt i hapjes");
             translationMap.put("Swedish_opening object", "öppningsobjekt");
             translationMap.put("Swahili_opening object", "kitu cha ufunguzi");
             translationMap.put("Tamil_opening object", "திறக்க பொருள்");
             translationMap.put("Telugu_opening object", "ఓపెనింగ్ ఆబ్జెక్ట్");
             translationMap.put("Thai_opening object", "วัตถุเปิด");
             translationMap.put("Tagalog_opening object", "object ng pagbubukas");
             translationMap.put("Turkish_opening object", "açılış nesnesi");
             translationMap.put("Ukrainian_opening object", "об'єкт відкриття");
             translationMap.put("Urdu_opening object", "اوپننگ اشیاء");
             translationMap.put("Vietnamese_opening object", "đối tượng mở");
             translationMap.put("Chinese_opening object", "开头对象");


             translationMap.put("Afrikaans_opening read text", "open leesteks");
             translationMap.put("Arabic_opening read text", "قراءة النص الافتتاحي");
             translationMap.put("Hindi_opening read text", "ओपनिंग पठन टेक्स्ट");
             translationMap.put("English_opening read text", "opening read text");
             translationMap.put("Marathi_opening read text", "उघडणारे वाचा टेक्स्ट");
             translationMap.put("Belarusian_opening read text", "адкрыццё тэксту");
             translationMap.put("Bulgarian_opening read text", "четене на отваряне текст");
             translationMap.put("Bengali_opening read text", "উদ্বারণ পঠন টেক্সট");
             translationMap.put("Catalan_opening read text", "lectura de text d'obertura");
             translationMap.put("Czech_opening read text", "otevírací text čtení");
             translationMap.put("Welsh_opening read text", "darllen testun agoriadol");
             translationMap.put("Danish_opening read text", "åbning læst tekst");
             translationMap.put("German_opening read text", "Öffnungstext lesen");
             translationMap.put("Greek_opening read text", "ανάγνωση ανοίγματος κειμένου");
             translationMap.put("Esperanto_opening read text", "legi malferman tekston");
             translationMap.put("Spanish_opening read text", "lectura de texto de apertura");
             translationMap.put("Estonian_opening read text", "avamise teksti lugemine");
             translationMap.put("Persian_opening read text", "خواندن متن افتتاحیه");
             translationMap.put("Finnish_opening read text", "avaustekstin lukeminen");
             translationMap.put("French_opening read text", "lecture du texte d'ouverture");
             translationMap.put("Irish_opening read text", "léamh téacs oscailte");
             translationMap.put("Galician_opening read text", "lectura do texto de apertura");
             translationMap.put("Gujarati_opening read text", "ઓપનિંગ વાંચો ટેક્સ્ટ");
             translationMap.put("Hebrew_opening read text", "קריאת טקסט פתיחה");
             translationMap.put("Croatian_opening read text", "čitanje otvarajućeg teksta");
             translationMap.put("Haitian_opening read text", "li lekti tèks ouvèti");
             translationMap.put("Hungarian_opening read text", "nyitott szöveg olvasása");
             translationMap.put("Indonesian_opening read text", "membaca teks pembukaan");
             translationMap.put("Icelandic_opening read text", "opinn texti lesinn");
             translationMap.put("Italian_opening read text", "lettura del testo di apertura");
             translationMap.put("Japanese_opening read text", "オープニング読み取りテキスト");
             translationMap.put("Georgian_opening read text", "ტექსტის გახსნის კითხვა");
             translationMap.put("Kannada_opening read text", "ಆರಂಭಿಕ ಓದುವ ವಚನ");
             translationMap.put("Korean_opening read text", "오프닝 텍스트 읽기");
             translationMap.put("Lithuanian_opening read text", "atidarymo teksto skaitymas");
             translationMap.put("Latvian_opening read text", "atvēršanas teksta lasīšana");
             translationMap.put("Macedonian_opening read text", "читање на отварањето на текстот");
             translationMap.put("Malay_opening read text", "membaca teks pembukaan");
             translationMap.put("Maltese_opening read text", "taqra test ta' ftuħ");
             translationMap.put("Dutch_opening read text", "lees openings tekst");
             translationMap.put("Norwegian_opening read text", "lesing av åpningstekst");
             translationMap.put("Polish_opening read text", "czytanie tekstu otwarcia");
             translationMap.put("Portuguese_opening read text", "leitura de texto de abertura");
             translationMap.put("Romanian_opening read text", "citirea textului de deschidere");
             translationMap.put("Russian_opening read text", "чтение открывающего текста");
             translationMap.put("Slovak_opening read text", "čítanie otváracieho textu");
             translationMap.put("Slovenian_opening read text", "branje odpirajočega besedila");
             translationMap.put("Albanian_opening read text", "leximi i tekstit të hapjes");
             translationMap.put("Swedish_opening read text", "läsning av öppningstext");
             translationMap.put("Swahili_opening read text", "kusoma maandishi ya ufunguzi");
             translationMap.put("Tamil_opening read text", "திறக்கும் உரையை படிக்க");
             translationMap.put("Telugu_opening read text", "ఓపెనింగ్ పాఠం చదవడం");
             translationMap.put("Thai_opening read text", "อ่านข้อความเปิด");
             translationMap.put("Tagalog_opening read text", "pagbasa ng bukas na teksto");
             translationMap.put("Turkish_opening read text", "açılış metni okuma");
             translationMap.put("Ukrainian_opening read text", "читання відкриваючого тексту");
             translationMap.put("Urdu_opening read text", "خواندہ کھولنے کا متن");
             translationMap.put("Vietnamese_opening read text", "đọc văn bản mở đầu");
             translationMap.put("Chinese_opening read text", "阅读开头文字");

             translationMap.put("Afrikaans_opening translate", "opening vertaal");
             translationMap.put("Arabic_opening translate", "ترجمة الافتتاح");
             translationMap.put("Hindi_opening translate", "आरंभ अनुवाद");
             translationMap.put("English_opening translate", "opening translate");
             translationMap.put("Marathi_opening translate", "उघडणारे भाषांतर");
             translationMap.put("Belarusian_opening translate", "адкрыццё пераклад");
             translationMap.put("Bulgarian_opening translate", "отваряне превод");
             translationMap.put("Bengali_opening translate", "উদ্বারণ অনুবাদ");
             translationMap.put("Catalan_opening translate", "traducció d'obertura");
             translationMap.put("Czech_opening translate", "překlad otevírání");
             translationMap.put("Welsh_opening translate", "cyfieithiad agor");
             translationMap.put("Danish_opening translate", "åbning oversætte");
             translationMap.put("German_opening translate", "Eröffnung übersetzen");
             translationMap.put("Greek_opening translate", "μετάφραση ανοίγματος");
             translationMap.put("Esperanto_opening translate", "traduko de malfermo");
             translationMap.put("Spanish_opening translate", "traducción de apertura");
             translationMap.put("Estonian_opening translate", "avamise tõlge");
             translationMap.put("Persian_opening translate", "ترجمه افتتاح");
             translationMap.put("Finnish_opening translate", "avaa käännös");
             translationMap.put("French_opening translate", "traduction d'ouverture");
             translationMap.put("Irish_opening translate", "aistriú oscailte");
             translationMap.put("Galician_opening translate", "tradución de apertura");
             translationMap.put("Gujarati_opening translate", "ખોલ અનુવાદ");
             translationMap.put("Hebrew_opening translate", "תרגום פתיחה");
             translationMap.put("Croatian_opening translate", "prijevod otvaranja");
             translationMap.put("Haitian_opening translate", "tradiksyon ouvèti");
             translationMap.put("Hungarian_opening translate", "nyitó fordítás");
             translationMap.put("Indonesian_opening translate", "terjemahan pembuka");
             translationMap.put("Icelandic_opening translate", "þýðing á opnun");
             translationMap.put("Italian_opening translate", "traduzione di apertura");
             translationMap.put("Japanese_opening translate", "オープニングの翻訳");
             translationMap.put("Georgian_opening translate", "გახსნის თარგმანი");
             translationMap.put("Kannada_opening translate", "ಆರಂಭ ಅನುವಾದ");
             translationMap.put("Korean_opening translate", "오프닝 번역");
             translationMap.put("Lithuanian_opening translate", "atidarymo vertimas");
             translationMap.put("Latvian_opening translate", "atvēršanas tulkojums");
             translationMap.put("Macedonian_opening translate", "превод на отворање");
             translationMap.put("Malay_opening translate", "terjemahan pembukaan");
             translationMap.put("Maltese_opening translate", "traduzzjoni tal-ftuħ");
             translationMap.put("Dutch_opening translate", "vertaling van opening");
             translationMap.put("Norwegian_opening translate", "åpningsoversettelse");
             translationMap.put("Polish_opening translate", "tłumaczenie otwarcia");
             translationMap.put("Portuguese_opening translate", "tradução de abertura");
             translationMap.put("Romanian_opening translate", "traducere de deschidere");
             translationMap.put("Russian_opening translate", "перевод открытия");
             translationMap.put("Slovak_opening translate", "preklad otvorenia");
             translationMap.put("Slovenian_opening translate", "prevod odprtja");
             translationMap.put("Albanian_opening translate", "përkthim hapjeje");
             translationMap.put("Swedish_opening translate", "öppning översättning");
             translationMap.put("Swahili_opening translate", "tafsiri ya ufunguzi");
             translationMap.put("Tamil_opening translate", "திறப்பு மொழிபெயர்ப்பு");
             translationMap.put("Telugu_opening translate", "ఓపెనింగ్ అనువాదం");
             translationMap.put("Thai_opening translate", "การแปลเปิด");
             translationMap.put("Tagalog_opening translate", "pagsasalin wika ng pagbubukas");
             translationMap.put("Turkish_opening translate", "açılış tercümesi");
             translationMap.put("Ukrainian_opening translate", "переклад відкриття");
             translationMap.put("Urdu_opening translate", "آغاز ترجمہ");
             translationMap.put("Vietnamese_opening translate", "dịch mở đầu");
             translationMap.put("Chinese_opening translate", "开场翻译");
     }

}
