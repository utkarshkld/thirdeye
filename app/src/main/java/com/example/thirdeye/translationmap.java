package com.example.thirdeye;
import static com.example.thirdeye.MainActivity.translationMap;
import static com.example.thirdeye.MainActivity.commandmap;
import static com.example.thirdeye.MainActivity.langnamemap;
import static com.example.thirdeye.appsettings.languageLocalMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class translationmap {

     public static void initializetransMap() {
             List<String> objectTranslations = new ArrayList<>();
             objectTranslations.add("voorwerp"); // Afrikaans
             objectTranslations.add("objekt"); // Albanian
             objectTranslations.add("كائن"); // Arabic
             objectTranslations.add("বস্তু"); // Bengali
             objectTranslations.add("বাস্তু");
             objectTranslations.add("தோடு");// Tamil
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
             objectTranslations.add("ఆబ్జెక్ట్");//telugu 2
             objectTranslations.add("অবজেক্ট"); //ben 2
             objectTranslations.add("ઑબજેક્ટ");// guj 2
             objectTranslations.add("ಆಬ್ಜೆಕ್ಟ್");// kannada 2
             objectTranslations.add("ஆப்ஜெக்ட்");// tamil2
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
             objectTranslations.add("பொருள்"); // Tamil
             objectTranslations.add("వస్తువు"); // Telugu
             objectTranslations.add("วัตถุ"); // Thai
             objectTranslations.add("nesne"); // Turkish
             objectTranslations.add("об'єкт"); // Ukrainian
             objectTranslations.add("اشیاء"); // Urdu
             objectTranslations.add("đối tượng");//Vietnamese
             commandmap.put("object",objectTranslations);
             List<String> magnifierTranslations = new ArrayList<>();
             magnifierTranslations.add("मैग्नीफायर");// hindi marathi 2
             magnifierTranslations.add("మ్యాగ్నిఫైర్"); // telugu 2
             magnifierTranslations.add("ম্যাগনিফায়ার্");// bengali 2
             magnifierTranslations.add("મેગ્નિફાયર");// guj 2
             magnifierTranslations.add("ಮ್ಯಾಗ್ನಿಫಯರ್");// kannada 2
             magnifierTranslations.add("ಮ್ಯಾಗ್ನಿಫಿ");
             magnifierTranslations.add("மாக்னிஃபய");// tamil2
             magnifierTranslations.add("vergroter"); // Afrikaans
             magnifierTranslations.add("lupë"); // Albanian
             magnifierTranslations.add("مكبر"); // Arabic
             magnifierTranslations.add("বৃদ্ধি"); // Bengali
             magnifierTranslations.add("увеличител"); // Bulgarian
             magnifierTranslations.add("放大镜"); // Chinese
             magnifierTranslations.add("zvětšovač"); // Czech
             magnifierTranslations.add("forstørrelsesglas"); // Danish
             magnifierTranslations.add("vergrootglas"); // Dutch
             magnifierTranslations.add("magnifier"); // English
             magnifierTranslations.add("suurennuslasi"); // Finnish
             magnifierTranslations.add("loupe"); // French
             magnifierTranslations.add("magnificador"); // Galician
             magnifierTranslations.add("საზომი"); // Georgian
             magnifierTranslations.add("Vergrößerungsglas"); // German
             magnifierTranslations.add("μεγεθυντικός φακός"); // Greek
             magnifierTranslations.add("મેગ્નિફિયર"); // Gujarati
             magnifierTranslations.add("loup"); // Haitian
             magnifierTranslations.add("מגדיל"); // Hebrew
             magnifierTranslations.add("आवर्धक"); // Hindi
             magnifierTranslations.add("nagyító"); // Hungarian
             magnifierTranslations.add("stærraðargler"); // Icelandic
             magnifierTranslations.add("pembesar"); // Indonesian
             magnifierTranslations.add("ingranditore"); // Italian
             magnifierTranslations.add("拡大鏡"); // Japanese
             magnifierTranslations.add("ವರ್ಧಕ"); // Kannada
             magnifierTranslations.add("확대기"); // Korean
             magnifierTranslations.add("palielinātājs"); // Latvian
             magnifierTranslations.add("didinamasis stiklas"); // Lithuanian
             magnifierTranslations.add("магнификација"); // Macedonian
             magnifierTranslations.add("pembesar"); // Malay
             magnifierTranslations.add("വലിപ്പം വർദ്ധിപ്പിക്കാൻ"); // Malayalam
             magnifierTranslations.add("magnifier"); // Maltese
             magnifierTranslations.add("वाढवणारा"); // Marathi
             magnifierTranslations.add("forstørrelsesglass"); // Norwegian
             magnifierTranslations.add("lupa"); // Polish
             magnifierTranslations.add("lupa"); // Portuguese
             magnifierTranslations.add("lupă"); // Romanian
             magnifierTranslations.add("увеличител"); // Russian
             magnifierTranslations.add("zväčšovač"); // Slovak
             magnifierTranslations.add("povečevalno steklo"); // Slovenian
             magnifierTranslations.add("lupa"); // Spanish
             magnifierTranslations.add("kuza"); // Swahili
             magnifierTranslations.add("förstoringsglas"); // Swedish
             magnifierTranslations.add("magnifying"); // Tagalog
             magnifierTranslations.add("பெரிய"); // Tamil
             magnifierTranslations.add("వృద్ధి"); // Telugu
             magnifierTranslations.add("เพิ่มขนาด"); // Thai
             magnifierTranslations.add("büyüteç"); // Turkish
             magnifierTranslations.add("збільшувач"); // Ukrainian
             magnifierTranslations.add("مگنیفائر"); // Urdu
             magnifierTranslations.add("kính lúp"); // Vietnamese
             magnifierTranslations.add("đại bàng"); // Chinese
             commandmap.put("magnifier",magnifierTranslations);
             List<String> readTranslations = new ArrayList<>();
             readTranslations.add("lees"); // Afrikaans
             readTranslations.add("lexoni"); // Albanian
             readTranslations.add("اقرأ"); // Arabic
             readTranslations.add("পড়া"); // Bengali
             readTranslations.add("পাড়া");
             readTranslations.add("পাড়া");
             readTranslations.add("रीड"); // hindi 2
             readTranslations.add("రీడ్");//telugu 2
             readTranslations.add("রিড");//bengali2
             readTranslations.add("રીડ"); // gujrati 2
             readTranslations.add("ರೀಡ್"); // kannada 2
             readTranslations.add("ரீட்");// tamil2
             readTranslations.add("வீடு");
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
             readTranslations.add("ಒದ್ದು");
             readTranslations.add("ಓಡು");
             readTranslations.add("ಕೊಡು");
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
             readTranslations.add("படி"); // Tamil
             readTranslations.add("பாடி");
             readTranslations.add("చదవండి"); // Telugu
             readTranslations.add("อ่าน"); // Thai
             readTranslations.add("okumak"); // Turkish
             readTranslations.add("читати"); // Ukrainian
             readTranslations.add("پڑھنا"); // Urdu
             readTranslations.add("đọc"); // Vietnamese
             commandmap.put("read",readTranslations);
             List<String> translateTranslations = new ArrayList<>();
             translateTranslations.add("vertaal"); // Afrikaans
             translateTranslations.add("ಟ್ರಾನ್ಸ್ಲಟೆ");//tamil
             translateTranslations.add("ட்ரான்ஸ்லேட்");
             translateTranslations.add("përkthe"); // Albanian
             translateTranslations.add("ترجمة"); // Arabic
             translateTranslations.add("অনুবাদ"); // Bengali
             translateTranslations.add("превеждам"); // Bulgarian
             translateTranslations.add("ट्रांसलेशन");// hindi marathi 2
             translateTranslations.add("翻译"); // Chinese
             translateTranslations.add("ట్రాన్స్లేషన్");//teleugu 2
             translateTranslations.add("ট্রান্সলেশন");// bengali 2
             translateTranslations.add("ટ્રાન્સલેશન");//guj 2
             translateTranslations.add("ಟ್ರಾನ್ಸ್ಲೇಶನ್");// kannada 2
             translateTranslations.add("டிரான்ஸ்லேஷன்"); // tamil 2
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
             settingsTranslations.add("सेटिंग");// hindi eng 2
             settingsTranslations.add("సెట్టింగ్"); //telugu 2
             settingsTranslations.add("সেটিং"); // bengali 2
             settingsTranslations.add("સેટિંગ");//Guj 2
             settingsTranslations.add("ಸೆಟ್ಟಿಂಗ್");// kannad 2
             settingsTranslations.add("செட்டிங்");//tamil 2
             settingsTranslations.add("instellings"); // Afrikaans
             settingsTranslations.add("parametrat"); // Albanian
             settingsTranslations.add("الإعدادات"); // Arabic
             settingsTranslations.add("বিন্যাস"); // Bengali
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
             settingsTranslations.add("அமைத்தல்");
             settingsTranslations.add("సెట్టింగులు"); // Telugu
             settingsTranslations.add("การตั้งค่า"); // Thai
             settingsTranslations.add("ayarlar"); // Turkish
             settingsTranslations.add("налаштування"); // Ukrainian
             settingsTranslations.add("ترتیبات"); // Urdu
             settingsTranslations.add("cài đặt"); // Vietnamese
             commandmap.put("settings", settingsTranslations);
             List<String> languageTranslations = new ArrayList<>();
             // Add translations for each language
             languageTranslations.add("taal");         // अफ्रीकान्स (Afrikaans)
             languageTranslations.add("gjuhë");        // अल्बेनियन (Albanian)
             languageTranslations.add("لغة");          // अरबी (Arabic)
             languageTranslations.add("ভাষা");        // बांग्लা (Bengali)
             languageTranslations.add("език");         // बल्गेरियाई (Bulgarian)
             languageTranslations.add("语言");         // चीनी (Chinese)
             languageTranslations.add("jazyk");        // चेक (Czech)
             languageTranslations.add("sprog");        // डेनिश (Danish)
             languageTranslations.add("taal");         // डच (Dutch)
             languageTranslations.add("language");     // अंग्रेज़ी (English)
             languageTranslations.add("kieli");        // फ़िनिश (Finnish)
             languageTranslations.add("langue");       // फ्रेंच (French)
             languageTranslations.add("Sprache");      // जर्मन (German)
             languageTranslations.add("γλώσσα");       // यूनानी (Greek)
             languageTranslations.add("भाषा");        // गुजराती (Gujarati)
             languageTranslations.add("हाईशियन");     // हाईशियन (Haitian)
             languageTranslations.add("यहूदी");      // यहूदी (Hebrew)
             languageTranslations.add("भाषा");        // हिन्दी (Hindi)
             languageTranslations.add("भाषा");        // हंगेरियन (Hungarian)
             languageTranslations.add("Íslenska");     // आइसलैंडिक (Icelandic)
             languageTranslations.add("Bahasa");      // इंडोनेशियाई (Indonesian)
             languageTranslations.add("lingua");       // इतालवी (Italian)
             languageTranslations.add("言語");         // जापानी (Japanese)
             languageTranslations.add("ಭಾಷೆ");       // कन्नड़ (Kannada)
             languageTranslations.add("언어");         // कोरियाई (Korean)
             languageTranslations.add("valoda");       // लातवियाई (Latvian)
             languageTranslations.add("kalba");        // लिथुआनियन (Lithuanian)
             languageTranslations.add("язык");         // मैसिडोनियन (Macedonian)
             languageTranslations.add("Bahasa");       // मलय (Malay)
             languageTranslations.add("ഭാഷ");         // मलयालम (Malayalam)
             languageTranslations.add("ilsien");       // माल्टीज़ (Maltese)
             languageTranslations.add("भाषा");        // मराठी (Marathi)
             languageTranslations.add("språk");        // नॉर्वेजियन (Norwegian)
             languageTranslations.add("język");        // पोलिश (Polish)
             languageTranslations.add("língua");       // पुर्तगाली (Portuguese)
             languageTranslations.add("limba");        // रोमानियाई (Romanian)
             languageTranslations.add("язык");         // रूसी (Russian)
             languageTranslations.add("jazyk");        // स्लोवाक (Slovak)
             languageTranslations.add("jezik");        // स्लोवेनियन (Slovenian)
             languageTranslations.add("idioma");       // स्पेनिश (Spanish)
             languageTranslations.add("lugha");        // स्वाहिली (Swahili)
             languageTranslations.add("språk");        // स्वीडिश (Swedish)
             languageTranslations.add("wika");         // तागालोग (Tagalog)
             languageTranslations.add("மொழி");       // तमिल (Tamil)
             languageTranslations.add("భాషా");       // तेलुगु (Telugu)
             languageTranslations.add("ภาษา");       // थाई (Thai)
             languageTranslations.add("dil");          // तुर्की (Turkish)
             languageTranslations.add("мова");        // यूक्रेनी (Ukrainian)
             languageTranslations.add("زبان");        // उर्दू (Urdu)
             languageTranslations.add("ngôn ngữ");    // वियतनामी (Vietnamese)
             commandmap.put("Language",languageTranslations);
             List<String> directionTranslations = new ArrayList<>();
             directionTranslations.add("richting"); // Afrikaans
             directionTranslations.add("drejtim"); // Albanian
             directionTranslations.add("الاتجاه"); // Arabic
             directionTranslations.add("দিশা"); // Bengali
             directionTranslations.add("डायरेक्शन");// hindi  marathi 2
             directionTranslations.add("డైరెక్షన్");//telegu 2
             directionTranslations.add("ডায়রেকশন্");//bengali 2
             directionTranslations.add("ડાયરેકશન");// guj 2
             directionTranslations.add("ಡೈರೆಕ್ಷನ್");// kannada 2
             directionTranslations.add("டைரெக்ஷன்");//Tamil 2
             directionTranslations.add("насока"); // Bulgarian
             directionTranslations.add("方向"); // Chinese
             directionTranslations.add("směr"); // Czech
             directionTranslations.add("retning"); // Danish
             directionTranslations.add("richting"); // Dutch
             directionTranslations.add("direction"); // English
             directionTranslations.add("suunta"); // Finnish
             directionTranslations.add("direction"); // French
             directionTranslations.add("drección"); // Galician
             directionTranslations.add("მიმართულება"); // Georgian
             directionTranslations.add("Richtung"); // German
             directionTranslations.add("κατεύθυνση"); // Greek
             directionTranslations.add("દિશા"); // Gujarati
             directionTranslations.add("diksyon"); // Haitian
             directionTranslations.add("כיוון"); // Hebrew
             directionTranslations.add("दिशा"); // Hindi
             directionTranslations.add("irány"); // Hungarian
             directionTranslations.add("átt"); // Icelandic
             directionTranslations.add("arahan"); // Indonesian
             directionTranslations.add("direzione"); // Italian
             directionTranslations.add("方向"); // Japanese
             directionTranslations.add("ದಿಕ್ಕು"); // Kannada
             directionTranslations.add("방향"); // Korean
             directionTranslations.add("virziens"); // Latvian
             directionTranslations.add("kryptys"); // Lithuanian
             directionTranslations.add("насоки"); // Macedonian
             directionTranslations.add("arah"); // Malay
             directionTranslations.add("ദിശ"); // Malayalam
             directionTranslations.add("direzzjoni"); // Maltese
             directionTranslations.add("दिशा"); // Marathi
             directionTranslations.add("retning"); // Norwegian
             directionTranslations.add("kierunek"); // Polish
             directionTranslations.add("direção"); // Portuguese
             directionTranslations.add("direcție"); // Romanian
             directionTranslations.add("направление"); // Russian
             directionTranslations.add("smer"); // Slovak
             directionTranslations.add("smer"); // Slovenian
             directionTranslations.add("dirección"); // Spanish
             directionTranslations.add("mwelekeo"); // Swahili
             directionTranslations.add("riktning"); // Swedish
             directionTranslations.add("direksyon"); // Tagalog
             directionTranslations.add("அமைப்பு"); // Tamil
             directionTranslations.add("దిశ"); // Telugu
             directionTranslations.add("ทิศทาง"); // Thai
             directionTranslations.add("yön"); // Turkish
             directionTranslations.add("напрямок"); // Ukrainian
             directionTranslations.add("سمت"); // Urdu
             directionTranslations.add("hướng"); // Vietnamese

             commandmap.put("direction", directionTranslations);
             Map<String, String> languageNamesEnglishToEnglish = new HashMap<>();

             // Adding language names with English as both keys and values to the map
             languageNamesEnglishToEnglish.put("Afrikaans", "Afrikaans");
             languageNamesEnglishToEnglish.put("Albanian", "Albanian");
             languageNamesEnglishToEnglish.put("Arabic", "Arabic");
             languageNamesEnglishToEnglish.put("Bengali", "Bengali");
             languageNamesEnglishToEnglish.put("Bulgarian", "Bulgarian");
             languageNamesEnglishToEnglish.put("Chinese", "Chinese");
             languageNamesEnglishToEnglish.put("Czech", "Czech");
             languageNamesEnglishToEnglish.put("Danish", "Danish");
             languageNamesEnglishToEnglish.put("Dutch", "Dutch");
             languageNamesEnglishToEnglish.put("English", "English");
             languageNamesEnglishToEnglish.put("Finnish", "Finnish");
             languageNamesEnglishToEnglish.put("French", "French");
             languageNamesEnglishToEnglish.put("Galician", "Galician");
             languageNamesEnglishToEnglish.put("Georgian", "Georgian");
             languageNamesEnglishToEnglish.put("German", "German");
             languageNamesEnglishToEnglish.put("Greek", "Greek");
             languageNamesEnglishToEnglish.put("Gujarati", "Gujarati");
             languageNamesEnglishToEnglish.put("Haitian", "Haitian");
             languageNamesEnglishToEnglish.put("Hebrew", "Hebrew");
             languageNamesEnglishToEnglish.put("Hindi", "Hindi");
             languageNamesEnglishToEnglish.put("Hungarian", "Hungarian");
             languageNamesEnglishToEnglish.put("Icelandic", "Icelandic");
             languageNamesEnglishToEnglish.put("Indonesian", "Indonesian");
             languageNamesEnglishToEnglish.put("Italian", "Italian");
             languageNamesEnglishToEnglish.put("Japanese", "Japanese");
             languageNamesEnglishToEnglish.put("Kannada", "Kannada");
             languageNamesEnglishToEnglish.put("Korean", "Korean");
             languageNamesEnglishToEnglish.put("Latvian", "Latvian");
             languageNamesEnglishToEnglish.put("Lithuanian", "Lithuanian");
             languageNamesEnglishToEnglish.put("Macedonian", "Macedonian");
             languageNamesEnglishToEnglish.put("Malay", "Malay");
             languageNamesEnglishToEnglish.put("Malayalam", "Malayalam");
             languageNamesEnglishToEnglish.put("Maltese", "Maltese");
             languageNamesEnglishToEnglish.put("Marathi", "Marathi");
             languageNamesEnglishToEnglish.put("Norwegian", "Norwegian");
             languageNamesEnglishToEnglish.put("Polish", "Polish");
             languageNamesEnglishToEnglish.put("Portuguese", "Portuguese");
             languageNamesEnglishToEnglish.put("Romanian", "Romanian");
             languageNamesEnglishToEnglish.put("Russian", "Russian");
             languageNamesEnglishToEnglish.put("Slovak", "Slovak");
             languageNamesEnglishToEnglish.put("Slovenian", "Slovenian");
             languageNamesEnglishToEnglish.put("Spanish", "Spanish");
             languageNamesEnglishToEnglish.put("Swahili", "Swahili");
             languageNamesEnglishToEnglish.put("Swedish", "Swedish");
             languageNamesEnglishToEnglish.put("Tagalog", "Tagalog");
             languageNamesEnglishToEnglish.put("Tamil", "Tamil");
             languageNamesEnglishToEnglish.put("Telugu", "Telugu");
             languageNamesEnglishToEnglish.put("Thai", "Thai");
             languageNamesEnglishToEnglish.put("Turkish", "Turkish");
             languageNamesEnglishToEnglish.put("Ukrainian", "Ukrainian");
             languageNamesEnglishToEnglish.put("Urdu", "Urdu");
             languageNamesEnglishToEnglish.put("Vietnamese", "Vietnamese");
             langnamemap.put("English",languageNamesEnglishToEnglish);
             Map<String, String> languageNamesHindiToEnglish = new HashMap<>();

             // Adding language names with Hindi as keys and English as values to the map
             languageNamesHindiToEnglish.put("अफ्रीकान्स", "Afrikaans");
             languageNamesHindiToEnglish.put("अल्बेनियन", "Albanian");
             languageNamesHindiToEnglish.put("अरबी", "Arabic");
             languageNamesHindiToEnglish.put("बांग्ला", "Bengali");
             languageNamesHindiToEnglish.put("बल्गेरियाई", "Bulgarian");
             languageNamesHindiToEnglish.put("चीनी", "Chinese");
             languageNamesHindiToEnglish.put("चेक", "Czech");
             languageNamesHindiToEnglish.put("डेनिश", "Danish");
             languageNamesHindiToEnglish.put("डच", "Dutch");
             languageNamesHindiToEnglish.put("अंग्रेज़ी", "English");
             languageNamesHindiToEnglish.put("फ़िनिश", "Finnish");
             languageNamesHindiToEnglish.put("फ्रेंच", "French");
             languageNamesHindiToEnglish.put("गैलिशियन", "Galician");
             languageNamesHindiToEnglish.put("जॉर्जियन", "Georgian");
             languageNamesHindiToEnglish.put("जर्मन", "German");
             languageNamesHindiToEnglish.put("यूनानी", "Greek");
             languageNamesHindiToEnglish.put("गुजराती", "Gujarati");
             languageNamesHindiToEnglish.put("हाईशियन", "Haitian");
             languageNamesHindiToEnglish.put("यहूदी", "Hebrew");
             languageNamesHindiToEnglish.put("हिन्दी", "Hindi");
             languageNamesHindiToEnglish.put("हंगेरियन", "Hungarian");
             languageNamesHindiToEnglish.put("आइसलैंडिक", "Icelandic");
             languageNamesHindiToEnglish.put("इंडोनेशियाई", "Indonesian");
             languageNamesHindiToEnglish.put("इतालवी", "Italian");
             languageNamesHindiToEnglish.put("जापानी", "Japanese");
             languageNamesHindiToEnglish.put("कन्नड़", "Kannada");
             languageNamesHindiToEnglish.put("कोरियाई", "Korean");
             languageNamesHindiToEnglish.put("लातवियाई", "Latvian");
             languageNamesHindiToEnglish.put("लिथुआनियन", "Lithuanian");
             languageNamesHindiToEnglish.put("मैसिडोनियन", "Macedonian");
             languageNamesHindiToEnglish.put("मलय", "Malay");
             languageNamesHindiToEnglish.put("मलयालम", "Malayalam");
             languageNamesHindiToEnglish.put("माल्टीज़", "Maltese");
             languageNamesHindiToEnglish.put("मराठी", "Marathi");
             languageNamesHindiToEnglish.put("नॉर्वेजियन", "Norwegian");
             languageNamesHindiToEnglish.put("पोलिश", "Polish");
             languageNamesHindiToEnglish.put("पुर्तगाली", "Portuguese");
             languageNamesHindiToEnglish.put("रोमानियाई", "Romanian");
             languageNamesHindiToEnglish.put("रूसी", "Russian");
             languageNamesHindiToEnglish.put("स्लोवाक", "Slovak");
             languageNamesHindiToEnglish.put("स्लोवेनियन", "Slovenian");
             languageNamesHindiToEnglish.put("स्पेनिश", "Spanish");
             languageNamesHindiToEnglish.put("स्वाहिली", "Swahili");
             languageNamesHindiToEnglish.put("स्वीडिश", "Swedish");
             languageNamesHindiToEnglish.put("तागालोग", "Tagalog");
             languageNamesHindiToEnglish.put("तमिल", "Tamil");
             languageNamesHindiToEnglish.put("तेलुगु", "Telugu");
             languageNamesHindiToEnglish.put("थाई", "Thai");
             languageNamesHindiToEnglish.put("तुर्की", "Turkish");
             languageNamesHindiToEnglish.put("यूक्रेनी", "Ukrainian");
             languageNamesHindiToEnglish.put("उर्दू", "Urdu");
             languageNamesHindiToEnglish.put("वियतनामी", "Vietnamese");
             langnamemap.put("Hindi",languageNamesHindiToEnglish);
             langnamemap.put("Marathi",languageNamesHindiToEnglish);
             Map<String, String> languageNamesBengaliToEnglish = new HashMap<>();
             languageNamesBengaliToEnglish.put("আফ্রিকান্স", "Afrikaans");
             languageNamesBengaliToEnglish.put("আলবেনীয়", "Albanian");
             languageNamesBengaliToEnglish.put("আরবি", "Arabic");
             languageNamesBengaliToEnglish.put("বাংলা", "Bengali");
             languageNamesBengaliToEnglish.put("বুলগেরিয়ান", "Bulgarian");
             languageNamesBengaliToEnglish.put("চীনা", "Chinese");
             languageNamesBengaliToEnglish.put("চেক", "Czech");
             languageNamesBengaliToEnglish.put("ড্যানিশ", "Danish");
             languageNamesBengaliToEnglish.put("ডাচ", "Dutch");
             languageNamesBengaliToEnglish.put("ইংরেজি", "English");
             languageNamesBengaliToEnglish.put("ফিনিশ", "Finnish");
             languageNamesBengaliToEnglish.put("ফরাসি", "French");
             languageNamesBengaliToEnglish.put("গ্যালিশিয়ান", "Galician");
             languageNamesBengaliToEnglish.put("জর্জিয়ান", "Georgian");
             languageNamesBengaliToEnglish.put("জার্মান", "German");
             languageNamesBengaliToEnglish.put("গ্রিক", "Greek");
             languageNamesBengaliToEnglish.put("গুজরাটি", "Gujarati");
             languageNamesBengaliToEnglish.put("হাইতিয়ান", "Haitian");
             languageNamesBengaliToEnglish.put("হিব্রু", "Hebrew");
             languageNamesBengaliToEnglish.put("হিন্দি", "Hindi");
             languageNamesBengaliToEnglish.put("হাঙ্গেরীয়", "Hungarian");
             languageNamesBengaliToEnglish.put("আইসল্যান্ডিক", "Icelandic");
             languageNamesBengaliToEnglish.put("ইন্দোনেশীয়", "Indonesian");
             languageNamesBengaliToEnglish.put("ইতালীয়", "Italian");
             languageNamesBengaliToEnglish.put("জাপানি", "Japanese");
             languageNamesBengaliToEnglish.put("কন্নড়", "Kannada");
             languageNamesBengaliToEnglish.put("কোরিয়ান", "Korean");
             languageNamesBengaliToEnglish.put("লাত্ভিয়ান", "Latvian");
             languageNamesBengaliToEnglish.put("লিথুয়ানিয়ান", "Lithuanian");
             languageNamesBengaliToEnglish.put("মেসিডোনিয়ান", "Macedonian");
             languageNamesBengaliToEnglish.put("মালয়", "Malay");
             languageNamesBengaliToEnglish.put("মালয়ালম", "Malayalam");
             languageNamesBengaliToEnglish.put("মল্টিজ", "Maltese");
             languageNamesBengaliToEnglish.put("মারাঠি", "Marathi");
             languageNamesBengaliToEnglish.put("নরওয়েজিয়ান", "Norwegian");
             languageNamesBengaliToEnglish.put("পোলিশ", "Polish");
             languageNamesBengaliToEnglish.put("পর্তুগিজ", "Portuguese");
             languageNamesBengaliToEnglish.put("রোমানিয়ান", "Romanian");
             languageNamesBengaliToEnglish.put("রাশিয়ান", "Russian");
             languageNamesBengaliToEnglish.put("স্লোভাক", "Slovak");
             languageNamesBengaliToEnglish.put("স্লোভেনীয়", "Slovenian");
             languageNamesBengaliToEnglish.put("স্পেনীয়", "Spanish");
             languageNamesBengaliToEnglish.put("সোয়াহিলি", "Swahili");
             languageNamesBengaliToEnglish.put("সুইডিশ", "Swedish");
             languageNamesBengaliToEnglish.put("তাগালোগ", "Tagalog");
             languageNamesBengaliToEnglish.put("তামিল", "Tamil");
             languageNamesBengaliToEnglish.put("তেলেগু", "Telugu");
             languageNamesBengaliToEnglish.put("থাই", "Thai");
             languageNamesBengaliToEnglish.put("তুর্কি", "Turkish");
             languageNamesBengaliToEnglish.put("ইউক্রেনীয়", "Ukrainian");
             languageNamesBengaliToEnglish.put("উর্দু", "Urdu");
             languageNamesBengaliToEnglish.put("ভিয়েতনামী", "Vietnamese");
             langnamemap.put("Bengali", languageNamesBengaliToEnglish);
             Map<String, String> languageNamesTamilToEnglish = new HashMap<>();

// Adding language names with Tamil as keys and English as values to the map
             languageNamesTamilToEnglish.put("ஆஃப்ரிகான்ஸ்", "Afrikaans");
             languageNamesTamilToEnglish.put("அல்பேனியன்", "Albanian");
             languageNamesTamilToEnglish.put("அரபு", "Arabic");
             languageNamesTamilToEnglish.put("பெங்காலி", "Bengali");
             languageNamesTamilToEnglish.put("பல்கேரியன்", "Bulgarian");
             languageNamesTamilToEnglish.put("சீனம்", "Chinese");
             languageNamesTamilToEnglish.put("செக்", "Czech");
             languageNamesTamilToEnglish.put("டேனிஷ்", "Danish");
             languageNamesTamilToEnglish.put("டச்சு", "Dutch");
             languageNamesTamilToEnglish.put("ஆங்கிலம்", "English");
             languageNamesTamilToEnglish.put("பின்னிஷ்", "Finnish");
             languageNamesTamilToEnglish.put("பிரஞ்சு", "French");
             languageNamesTamilToEnglish.put("கலீசியன்", "Galician");
             languageNamesTamilToEnglish.put("ஜார்ஜியன்", "Georgian");
             languageNamesTamilToEnglish.put("ஜெர்மன்", "German");
             languageNamesTamilToEnglish.put("கிரேக்கம்", "Greek");
             languageNamesTamilToEnglish.put("குஜராத்தி", "Gujarati");
             languageNamesTamilToEnglish.put("ஹைடியின்", "Haitian");
             languageNamesTamilToEnglish.put("ஹீப்ரூ", "Hebrew");
             languageNamesTamilToEnglish.put("ஹிந்தி", "Hindi");
             languageNamesTamilToEnglish.put("ஹங்கேரியன்", "Hungarian");
             languageNamesTamilToEnglish.put("ஐஸ்லெண்டிக்", "Icelandic");
             languageNamesTamilToEnglish.put("இந்தோனேஷியன்", "Indonesian");
             languageNamesTamilToEnglish.put("இத்தாலியன்", "Italian");
             languageNamesTamilToEnglish.put("ஜப்பானிய", "Japanese");
             languageNamesTamilToEnglish.put("கன்னட", "Kannada");
             languageNamesTamilToEnglish.put("கொரியன்", "Korean");
             languageNamesTamilToEnglish.put("லத்வியன்", "Latvian");
             languageNamesTamilToEnglish.put("லிதுவேனியன்", "Lithuanian");
             languageNamesTamilToEnglish.put("மாசிடோனியன்", "Macedonian");
             languageNamesTamilToEnglish.put("மலாய்", "Malay");
             languageNamesTamilToEnglish.put("மலையாளம்", "Malayalam");
             languageNamesTamilToEnglish.put("மால்டிஸ்", "Maltese");
             languageNamesTamilToEnglish.put("மராத்தி", "Marathi");
             languageNamesTamilToEnglish.put("நார்வே", "Norwegian");
             languageNamesTamilToEnglish.put("போலிஷ்", "Polish");
             languageNamesTamilToEnglish.put("போர்த்துகீசியன்", "Portuguese");
             languageNamesTamilToEnglish.put("ரோமானியன்", "Romanian");
             languageNamesTamilToEnglish.put("ரஷ்யன்", "Russian");
             languageNamesTamilToEnglish.put("சுலோவாக்", "Slovak");
             languageNamesTamilToEnglish.put("சுலோவேனியன்", "Slovenian");
             languageNamesTamilToEnglish.put("ஸ்பானிஷ்", "Spanish");
             languageNamesTamilToEnglish.put("சுவாஹிலி", "Swahili");
             languageNamesTamilToEnglish.put("சுவீடிஷ்", "Swedish");
             languageNamesTamilToEnglish.put("டாகாலோக்", "Tagalog");
             languageNamesTamilToEnglish.put("தமிழ்", "Tamil");
             languageNamesTamilToEnglish.put("தெலுங்கு", "Telugu");
             languageNamesTamilToEnglish.put("தாய்", "Thai");
             languageNamesTamilToEnglish.put("துருக்கி", "Turkish");
             languageNamesTamilToEnglish.put("உக்ரைனியன்", "Ukrainian");
             languageNamesTamilToEnglish.put("உருது", "Urdu");
             languageNamesTamilToEnglish.put("வியட்னாமீஸ்", "Vietnamese");
             langnamemap.put("Tamil", languageNamesTamilToEnglish);
             Map<String, String> languageNamesKannadaToEnglish = new HashMap<>();

// Adding language names with Kannada as keys and English as values to the map
             languageNamesKannadaToEnglish.put("ಆಫ್ರಿಕಾನ್ಸ್", "Afrikaans");
             languageNamesKannadaToEnglish.put("ಅಲ್ಬೇನಿಯನ್", "Albanian");
             languageNamesKannadaToEnglish.put("ಅರಬಿಕ್", "Arabic");
             languageNamesKannadaToEnglish.put("ಬೆಂಗಾಲಿ", "Bengali");
             languageNamesKannadaToEnglish.put("ಬಲ್ಗೇರಿಯನ್", "Bulgarian");
             languageNamesKannadaToEnglish.put("ಚೈನೀಸ್", "Chinese");
             languageNamesKannadaToEnglish.put("ಚೆಕ್", "Czech");
             languageNamesKannadaToEnglish.put("ಡಾನಿಷ್", "Danish");
             languageNamesKannadaToEnglish.put("ಡಚ್", "Dutch");
             languageNamesKannadaToEnglish.put("ಇಂಗ್ಲಿಷ್", "English");
             languageNamesKannadaToEnglish.put("ಫಿನ್ನಿಷ್", "Finnish");
             languageNamesKannadaToEnglish.put("ಫ್ರೆಂಚ್", "French");
             languageNamesKannadaToEnglish.put("ಗ್ಯಾಲಿಶಿಯನ್", "Galician");
             languageNamesKannadaToEnglish.put("ಜಾರ್ಜಿಯನ್", "Georgian");
             languageNamesKannadaToEnglish.put("ಜರ್ಮನ್", "German");
             languageNamesKannadaToEnglish.put("ಗ್ರೀಕ್", "Greek");
             languageNamesKannadaToEnglish.put("ಗುಜರಾತಿ", "Gujarati");
             languageNamesKannadaToEnglish.put("ಹೈತಿಯನ್", "Haitian");
             languageNamesKannadaToEnglish.put("ಹೀಬ್ರೂ", "Hebrew");
             languageNamesKannadaToEnglish.put("ಹಿಂದಿ", "Hindi");
             languageNamesKannadaToEnglish.put("ಹಂಗೇರಿಯನ್", "Hungarian");
             languageNamesKannadaToEnglish.put("ಐಸ್‌ಲ್ಯಾಂಡಿಕ್", "Icelandic");
             languageNamesKannadaToEnglish.put("ಇಂಡೋನೇಷಿಯನ್", "Indonesian");
             languageNamesKannadaToEnglish.put("ಇಟಾಲಿಯನ್", "Italian");
             languageNamesKannadaToEnglish.put("ಜಾಪನೀಸ್", "Japanese");
             languageNamesKannadaToEnglish.put("ಕನ್ನಡ", "Kannada");
             languageNamesKannadaToEnglish.put("ಕೊರಿಯನ್", "Korean");
             languageNamesKannadaToEnglish.put("ಲಾತ್ವಿಯನ್", "Latvian");
             languageNamesKannadaToEnglish.put("ಲಿಥುವೇನಿಯನ್", "Lithuanian");
             languageNamesKannadaToEnglish.put("ಮ್ಯాసಿಡೋನಿಯನ್", "Macedonian");
             languageNamesKannadaToEnglish.put("ಮಲಯ್", "Malay");
             languageNamesKannadaToEnglish.put("ಮಲಯಾಳಂ", "Malayalam");
             languageNamesKannadaToEnglish.put("ಮಾಲ್ಟೀಸ್", "Maltese");
             languageNamesKannadaToEnglish.put("ಮರಾಠಿ", "Marathi");
             languageNamesKannadaToEnglish.put("ನಾರ್ವೇಜಿಯನ್", "Norwegian");
             languageNamesKannadaToEnglish.put("ಪೋಲಿಷ್", "Polish");
             languageNamesKannadaToEnglish.put("ಪೋರ್ಚುಗೀಸ್", "Portuguese");
             languageNamesKannadaToEnglish.put("ರೊಮೇನಿಯನ್", "Romanian");
             languageNamesKannadaToEnglish.put("ರಷ್ಯನ್", "Russian");
             languageNamesKannadaToEnglish.put("ಸ್ಲೋವಾಕ್", "Slovak");
             languageNamesKannadaToEnglish.put("ಸ್ಲೋವೇನಿಯನ್", "Slovenian");
             languageNamesKannadaToEnglish.put("ಸ್ಪ್ಯಾನಿಷ್", "Spanish");
             languageNamesKannadaToEnglish.put("ಸ್ವಾಹಿಲಿ", "Swahili");
             languageNamesKannadaToEnglish.put("ಸ್ವೀಡಿಷ್", "Swedish");
             languageNamesKannadaToEnglish.put("ಟಾಗಾಲಾಗ್", "Tagalog");
             languageNamesKannadaToEnglish.put("ತಮಿಳು", "Tamil");
             languageNamesKannadaToEnglish.put("ತೆಲುಗು", "Telugu");
             languageNamesKannadaToEnglish.put("ಥಾಯ್", "Thai");
             languageNamesKannadaToEnglish.put("ಟರ್ಕಿಷ್", "Turkish");
             languageNamesKannadaToEnglish.put("ಉಕ್ರೇನಿಯನ್", "Ukrainian");
             languageNamesKannadaToEnglish.put("ಉರ್ದು", "Urdu");
             languageNamesKannadaToEnglish.put("ವಿಯೆಟ್ನಾಮೀಸ್", "Vietnamese");

             langnamemap.put("Kannada", languageNamesKannadaToEnglish);
             Map<String, String> languageNamesTeluguToEnglish = new HashMap<>();

// Adding language names with Telugu as keys and English as values to the map
             languageNamesTeluguToEnglish.put("ఆఫ్రికాన్స్", "Afrikaans");
             languageNamesTeluguToEnglish.put("అల్బేనియన్", "Albanian");
             languageNamesTeluguToEnglish.put("అరబిక్", "Arabic");
             languageNamesTeluguToEnglish.put("బెంగాలీ", "Bengali");
             languageNamesTeluguToEnglish.put("బల్గేరియన్", "Bulgarian");
             languageNamesTeluguToEnglish.put("చైనీస్", "Chinese");
             languageNamesTeluguToEnglish.put("చెక్", "Czech");
             languageNamesTeluguToEnglish.put("డానిష్", "Danish");
             languageNamesTeluguToEnglish.put("డచ్", "Dutch");
             languageNamesTeluguToEnglish.put("ఇంగ్లీష్", "English");
             languageNamesTeluguToEnglish.put("ఫిన్నిష్", "Finnish");
             languageNamesTeluguToEnglish.put("ఫ్రెంచ్", "French");
             languageNamesTeluguToEnglish.put("గాలిషియన్", "Galician");
             languageNamesTeluguToEnglish.put("జార్జియన్", "Georgian");
             languageNamesTeluguToEnglish.put("జర్మన్", "German");
             languageNamesTeluguToEnglish.put("గ్రీక్", "Greek");
             languageNamesTeluguToEnglish.put("గుజరాతీ", "Gujarati");
             languageNamesTeluguToEnglish.put("హైతియన్", "Haitian");
             languageNamesTeluguToEnglish.put("హీబ్రూ", "Hebrew");
             languageNamesTeluguToEnglish.put("హిందీ", "Hindi");
             languageNamesTeluguToEnglish.put("హంగేరియన్", "Hungarian");
             languageNamesTeluguToEnglish.put("ఐస్‌లాండిక్", "Icelandic");
             languageNamesTeluguToEnglish.put("ఇండోనేషియన్", "Indonesian");
             languageNamesTeluguToEnglish.put("ఇటాలియన్", "Italian");
             languageNamesTeluguToEnglish.put("జపనీస్", "Japanese");
             languageNamesTeluguToEnglish.put("కన్నడ", "Kannada");
             languageNamesTeluguToEnglish.put("కొరియన్", "Korean");
             languageNamesTeluguToEnglish.put("లాత్వియన్", "Latvian");
             languageNamesTeluguToEnglish.put("లిథువేనియన్", "Lithuanian");
             languageNamesTeluguToEnglish.put("మాసిడోనియన్", "Macedonian");
             languageNamesTeluguToEnglish.put("మలయ్", "Malay");
             languageNamesTeluguToEnglish.put("మలయాళం", "Malayalam");
             languageNamesTeluguToEnglish.put("మాల్టీస్", "Maltese");
             languageNamesTeluguToEnglish.put("మరాఠీ", "Marathi");
             languageNamesTeluguToEnglish.put("నార్వేజియన్", "Norwegian");
             languageNamesTeluguToEnglish.put("పోలిష్", "Polish");
             languageNamesTeluguToEnglish.put("పోర్చుగీస్", "Portuguese");
             languageNamesTeluguToEnglish.put("రోమేనియన్", "Romanian");
             languageNamesTeluguToEnglish.put("రష్యన్", "Russian");
             languageNamesTeluguToEnglish.put("స్లోవాక్", "Slovak");
             languageNamesTeluguToEnglish.put("స్లోవేనియన్", "Slovenian");
             languageNamesTeluguToEnglish.put("స్పానిష్", "Spanish");
             languageNamesTeluguToEnglish.put("స్వాహిలీ", "Swahili");
             languageNamesTeluguToEnglish.put("స్వీడిష్", "Swedish");
             languageNamesTeluguToEnglish.put("టాగాలోగ్", "Tagalog");
             languageNamesTeluguToEnglish.put("తమిళం", "Tamil");
             languageNamesTeluguToEnglish.put("తెలుగు", "Telugu");
             languageNamesTeluguToEnglish.put("తాయ్", "Thai");
             languageNamesTeluguToEnglish.put("టర్కిష్", "Turkish");
             languageNamesTeluguToEnglish.put("ఉక్రేనియన్", "Ukrainian");
             languageNamesTeluguToEnglish.put("ఉర్దూ", "Urdu");
             languageNamesTeluguToEnglish.put("వియత్నామీస్", "Vietnamese");

             langnamemap.put("Telugu", languageNamesTeluguToEnglish);
             Map<String, String> languageNamesGujaratiToEnglish = new HashMap<>();

// Adding language names with Gujarati as keys and English as values to the map
             languageNamesGujaratiToEnglish.put("આફ્રિકાન્સ", "Afrikaans");
             languageNamesGujaratiToEnglish.put("અલ્બેનિયન", "Albanian");
             languageNamesGujaratiToEnglish.put("અરબી", "Arabic");
             languageNamesGujaratiToEnglish.put("બંગાળી", "Bengali");
             languageNamesGujaratiToEnglish.put("બલ્ગેરિયન", "Bulgarian");
             languageNamesGujaratiToEnglish.put("ચાઇનીઝ", "Chinese");
             languageNamesGujaratiToEnglish.put("ચેક", "Czech");
             languageNamesGujaratiToEnglish.put("ડેનિશ", "Danish");
             languageNamesGujaratiToEnglish.put("ડચ", "Dutch");
             languageNamesGujaratiToEnglish.put("અંગ્રેજી", "English");
             languageNamesGujaratiToEnglish.put("ફિનિશ", "Finnish");
             languageNamesGujaratiToEnglish.put("ફ્રેંચ", "French");
             languageNamesGujaratiToEnglish.put("ગેલિશિયન", "Galician");
             languageNamesGujaratiToEnglish.put("જ્યોર્જિયન", "Georgian");
             languageNamesGujaratiToEnglish.put("જર્મન", "German");
             languageNamesGujaratiToEnglish.put("ગ્રીક", "Greek");
             languageNamesGujaratiToEnglish.put("ગુજરાતી", "Gujarati");
             languageNamesGujaratiToEnglish.put("હૈતિયન", "Haitian");
             languageNamesGujaratiToEnglish.put("હીબ્રુ", "Hebrew");
             languageNamesGujaratiToEnglish.put("હિન્દી", "Hindi");
             languageNamesGujaratiToEnglish.put("હંગેરિયન", "Hungarian");
             languageNamesGujaratiToEnglish.put("આઇસલેન્ડિક", "Icelandic");
             languageNamesGujaratiToEnglish.put("ઇન્ડોનેશિયન", "Indonesian");
             languageNamesGujaratiToEnglish.put("ઇટાલિયન", "Italian");
             languageNamesGujaratiToEnglish.put("જાપાનીઝ", "Japanese");
             languageNamesGujaratiToEnglish.put("કન્નડ", "Kannada");
             languageNamesGujaratiToEnglish.put("કોરિયન", "Korean");
             languageNamesGujaratiToEnglish.put("લાત્વિયન", "Latvian");
             languageNamesGujaratiToEnglish.put("લિથુનિયન", "Lithuanian");
             languageNamesGujaratiToEnglish.put("મેસેડોનિયન", "Macedonian");
             languageNamesGujaratiToEnglish.put("મલય", "Malay");
             languageNamesGujaratiToEnglish.put("મલયાલમ", "Malayalam");
             languageNamesGujaratiToEnglish.put("માલ્ટિઝ", "Maltese");
             languageNamesGujaratiToEnglish.put("મરાઠી", "Marathi");
             languageNamesGujaratiToEnglish.put("નોર્વેજિયન", "Norwegian");
             languageNamesGujaratiToEnglish.put("પોલિશ", "Polish");
             languageNamesGujaratiToEnglish.put("પોર્ચુગીઝ", "Portuguese");
             languageNamesGujaratiToEnglish.put("રોમાનિયન", "Romanian");
             languageNamesGujaratiToEnglish.put("રશિયન", "Russian");
             languageNamesGujaratiToEnglish.put("સ્લોવાક", "Slovak");
             languageNamesGujaratiToEnglish.put("સ્લોવેનિયન", "Slovenian");
             languageNamesGujaratiToEnglish.put("સ્પેનિશ", "Spanish");
             languageNamesGujaratiToEnglish.put("સ્વાહિલી", "Swahili");
             languageNamesGujaratiToEnglish.put("સ્વીડિશ", "Swedish");
             languageNamesGujaratiToEnglish.put("ટાગાલોગ", "Tagalog");
             languageNamesGujaratiToEnglish.put("તમિલ", "Tamil");
             languageNamesGujaratiToEnglish.put("તેલુગુ", "Telugu");
             languageNamesGujaratiToEnglish.put("થાઈ", "Thai");
             languageNamesGujaratiToEnglish.put("તુર્કી", "Turkish");
             languageNamesGujaratiToEnglish.put("યુક્રેનિયન", "Ukrainian");
             languageNamesGujaratiToEnglish.put("ઉર્દુ", "Urdu");
             languageNamesGujaratiToEnglish.put("વિયેટનામીઝ", "Vietnamese");

             langnamemap.put("Gujarati", languageNamesGujaratiToEnglish);



             translationMap.put("English_North", "You are heading towards North");
             translationMap.put("Telugu_North", "ఉత్తరంలో వెళ్ళుతున్నారు");
             translationMap.put("Hindi_North", "आप उत्तर की ओर जा रहे हैं");

             translationMap.put("Afrikaans_North", "Jy is oppad noord toe");
             translationMap.put("Albanian_North", "Ju po drejtoheni drejt Veriut");
             translationMap.put("Bengali_North", "আপনি উত্তরের দিকে অগ্রসর হচ্ছেন");
             translationMap.put("Bulgarian_North", "Вие се движите на север");
             translationMap.put("Catalan_North", "Esteu avançant cap al Nord");
             translationMap.put("Chinese_North", "你正朝北方前进");
             translationMap.put("Croatian_North", "Vi se krećete prema sjeveru");
             translationMap.put("Czech_North", "Směřujete na sever");
             translationMap.put("Danish_North", "Du er på vej mod nord");
             translationMap.put("Dutch_North", "Je bent op weg naar het noorden");
             translationMap.put("Finnish_North", "Olet matkalla kohti pohjoista");
             translationMap.put("French_North", "Vous vous dirigez vers le Nord");
             translationMap.put("Galician_North", "Estás indo cara ao norte");
             translationMap.put("Georgian_North", "თქვენ ჩამოსული ხვალთან");
             translationMap.put("German_North", "Sie fahren nach Norden");
             translationMap.put("Greek_North", "Κατευθύνεστε προς το Βόρειο");
             translationMap.put("Gujarati_North", "તમે ઉત્તર દિશામાં ચાલી રહ્યા છો");
             translationMap.put("Haitian_North", "Ou ap mache nan direksyon Nò");
             translationMap.put("Hebrew_North", "אתה הולך לצפון");
             translationMap.put("Hungarian_North", "Észak felé tartasz");
             translationMap.put("Icelandic_North", "Þú ert á leiðinni í norður");
             translationMap.put("Indonesian_North", "Anda sedang menuju ke utara");
             translationMap.put("Italian_North", "Ti stai dirigendo verso Nord");
             translationMap.put("Japanese_North", "あなたは北に向かっています");
             translationMap.put("Kannada_North", "ನೀವು ಉತ್ತರದ ದಿಕ್ಕಿಗೆ ಹೋಗುತ್ತಿದ್ದೀರಿ");
             translationMap.put("Korean_North", "북쪽으로 향하고 있습니다");
             translationMap.put("Latvian_North", "Jūs virzāties uz ziemeļiem");
             translationMap.put("Lithuanian_North", "Jūs keliuojate į šiaurę");
             translationMap.put("Macedonian_North", "Вие одамнапред на север");
             translationMap.put("Malay_North", "Anda sedang menuju ke utara");
             translationMap.put("Malayalam_North", "നിങ്ങൾ വടക്ക് തിരിച്ചുപോകുന്നു");
             translationMap.put("Maltese_North", "Int qed tmur il-quddiem lejn it-Tramuntana");
             translationMap.put("Marathi_North", "तुम्ही उत्तरात जात आहात");
             translationMap.put("Norwegian_North", "Du er på vei mot nord");
             translationMap.put("Polish_North", "Jedziesz na północ");
             translationMap.put("Portuguese_North", "Você está indo para o norte");
             translationMap.put("Romanian_North", "Te îndrepți spre nord");
             translationMap.put("Russian_North", "Вы направляетесь на север");
             translationMap.put("Slovak_North", "Smerujete na sever");
             translationMap.put("Slovenian_North", "Odpravljate se na sever");
             translationMap.put("Spanish_North", "Te diriges hacia el norte");
             translationMap.put("Swahili_North", "Unaelekea kaskazini");
             translationMap.put("Swedish_North", "Du är på väg mot norr");
             translationMap.put("Tagalog_North", "Pumapunta ka sa hilaga");
             translationMap.put("Tamil_North", "நீங்கள் வடக்கு செல்கின்றீர்கள்");
             translationMap.put("Telugu_North", "మీరు ఉత్తరంలో వెళ్లుతున్నారు");
             translationMap.put("Thai_North", "คุณกำลังไปทางทิศเหนือ");
             translationMap.put("Turkish_North", "Kuzeye gidiyorsun");
             translationMap.put("Ukrainian_North", "Ви рухаєтеся на північ");
             translationMap.put("Urdu_North", "آپ شمال کی طرف جا رہے ہیں");
             translationMap.put("Vietnamese_North", "Bạn đang đi về phía bắc");

//South Translations
             translationMap.put("Afrikaans_South", "Jy is oppad suid toe");
             translationMap.put("Albanian_South", "Ju po drejtoheni drejt jugut");
             translationMap.put("Bengali_South", "আপনি দক্ষিণের দিকে অগ্রসর হচ্ছেন");
             translationMap.put("Bulgarian_South", "Вие се движите на юг");
             translationMap.put("Catalan_South", "Esteu avançant cap al Sud");
             translationMap.put("Chinese_South", "你正朝南方前进");
             translationMap.put("Croatian_South", "Vi se krećete prema jugu");
             translationMap.put("Czech_South", "Směřujete na jih");
             translationMap.put("Danish_South", "Du er på vej mod syd");
             translationMap.put("Dutch_South", "Je bent op weg naar het zuiden");
             translationMap.put("English_South", "You are heading towards South");
             translationMap.put("Finnish_South", "Olet matkalla etelään");
             translationMap.put("French_South", "Vous vous dirigez vers le Sud");
             translationMap.put("Galician_South", "Estás indo cara ao sur");
             translationMap.put("Georgian_South", "თქვენ ჩამოსული სამხრეთთან");
             translationMap.put("German_South", "Sie fahren nach Süden");
             translationMap.put("Greek_South", "Κατευθύνεστε προς το Νότιο");
             translationMap.put("Gujarati_South", "તમે દક્ષિણ દિશામાં ચાલી રહ્યા છો");
             translationMap.put("Haitian_South", "Ou ap mache nan direksyon Sid");
             translationMap.put("Hebrew_South", "אתה הולך לדרום");
             translationMap.put("Hindi_South", "आप दक्षिण की ओर जा रहे हैं");
             translationMap.put("Hungarian_South", "Dél felé tartasz");
             translationMap.put("Icelandic_South", "Þú ert á leiðinni í suður");
             translationMap.put("Indonesian_South", "Anda sedang menuju ke selatan");
             translationMap.put("Italian_South", "Ti stai dirigendo verso Sud");
             translationMap.put("Japanese_South", "あなたは南に向かっています");
             translationMap.put("Kannada_South", "ನೀವು ದಕ್ಷಿಣದ ದಿಕ್ಕಿಗೆ ಹೋಗುತ್ತಿದ್ದೀರಿ");
             translationMap.put("Korean_South", "남쪽으로 향하고 있습니다");
             translationMap.put("Latvian_South", "Jūs virzāties uz dienvidiem");
             translationMap.put("Lithuanian_South", "Jūs keliuojate į pietus");
             translationMap.put("Macedonian_South", "Вие одамнапред на југ");
             translationMap.put("Malay_South", "Anda sedang menuju ke selatan");
             translationMap.put("Malayalam_South", "നിങ്ങൾ തെക്കേക്ക് പോകുന്നു");
             translationMap.put("Maltese_South", "Int qed tmur il-quddiem lejn is-Sud");
             translationMap.put("Marathi_South", "तुम्ही दक्षिणेला जात आहात");
             translationMap.put("Norwegian_South", "Du er på vei mot sør");
             translationMap.put("Polish_South", "Jedziesz na południe");
             translationMap.put("Portuguese_South", "Você está indo para o sul");
             translationMap.put("Romanian_South", "Te îndrepți spre sud");
             translationMap.put("Russian_South", "Вы движетесь на юг");
             translationMap.put("Slovak_South", "Smerujete na juh");
             translationMap.put("Slovenian_South", "Usmerjeni ste proti jugu");
             translationMap.put("Spanish_South", "Te diriges hacia el Sur");
             translationMap.put("Swahili_South", "Unaelekea kusini");
             translationMap.put("Swedish_South", "Du är på väg mot söder");
             translationMap.put("Tagalog_South", "Ikaw ay patungo sa Timog");
             translationMap.put("Tamil_South", "நீங்கள் தெற்கு செல்லுகின்றீர்கள்");
             translationMap.put("Telugu_South", "మీరు దక్షిణానికి వెళ్లుతున్నారు");
             translationMap.put("Thai_South", "คุณกำลังเดินทางไปทางใต้");
             translationMap.put("Turkish_South", "Güneye doğru ilerliyorsunuz");
             translationMap.put("Ukrainian_South", "Ви рухаєтеся на південь");
             translationMap.put("Urdu_South", "آپ جنوب کی طرف جا رہے ہیں");
             translationMap.put("Vietnamese_South", "Bạn đang đi về hướng Nam");

// East translations
             translationMap.put("Afrikaans_East", "Jy gaan oos");
             translationMap.put("Albanian_East", "Ju po shkoni në lindje");
             translationMap.put("Bengali_East", "আপনি পূর্বে যাচ্ছেন");
             translationMap.put("Bulgarian_East", "Вие отивате на изток");
             translationMap.put("Catalan_East", "Esteu anant cap a l'est");
             translationMap.put("Chinese_East", "你正朝东走");
             translationMap.put("Croatian_East", "Idete prema istoku");
             translationMap.put("Czech_East", "Jdete na východ");
             translationMap.put("Danish_East", "Du går mod øst");
             translationMap.put("Dutch_East", "Je gaat naar het oosten");
             translationMap.put("English_East", "You are heading East");
             translationMap.put("Finnish_East", "Menet itään");
             translationMap.put("French_East", "Vous vous dirigez vers l'est");
             translationMap.put("Galician_East", "Estás indo cara ao leste");
             translationMap.put("Georgian_East", "თქვენ წინააღმდეგებთ აღმოსავლეთის მხარეს");
             translationMap.put("German_East", "Sie fahren nach Osten");
             translationMap.put("Greek_East", "Πηγαίνετε προς την Ανατολή");
             translationMap.put("Gujarati_East", "તમે પૂર્વ દિશામાં ચાલી રહ્યા છો");
             translationMap.put("Haitian_East", "Ou ap mache nan direksyon lès");
             translationMap.put("Hebrew_East", "אתה הולך למזרח");
             translationMap.put("Hindi_East", "आप पूर्व की ओर जा रहे हैं");
             translationMap.put("Gujarati_East", "તમે પૂર્વ દિશામાં ચાલી રહ્યા છો");
             translationMap.put("Haitian_East", "Ou ap mache nan direksyon lès");
             translationMap.put("Hebrew_East", "אתה הולך למזרח");
             translationMap.put("Hindi_East", "आप पूर्व की ओर जा रहे हैं");
             translationMap.put("Hungarian_East", "Kelet felé tartasz");
             translationMap.put("Icelandic_East", "Þú ert á leiðinni í austur");
             translationMap.put("Indonesian_East", "Anda sedang menuju ke timur");
             translationMap.put("Italian_East", "Ti stai dirigendo verso Est");
             translationMap.put("Japanese_East", "あなたは東に向かっています");
             translationMap.put("Kannada_East", "ನೀವು ಪೂರ್ವಕ್ಕೆ ಹೋಗುತ್ತಿದ್ದೀರಿ");
             translationMap.put("Korean_East", "동쪽으로 향하고 있습니다");
             translationMap.put("Latvian_East", "Jūs virzāties uz austrumiem");
             translationMap.put("Lithuanian_East", "Jūs keliuojate į rytus");
             translationMap.put("Macedonian_East", "Вие одамнапред на исток");
             translationMap.put("Malay_East", "Anda sedang menuju ke timur");
             translationMap.put("Malayalam_East", "നിങ്ങൾ കിഴക്കക്കും പോകുന്നു");
             translationMap.put("Maltese_East", "Int qed tmur il-quddiem lejn l-Lvant");
             translationMap.put("Marathi_East", "तुम्ही पूर्वेला जात आहात");
             translationMap.put("Norwegian_East", "Du er på vei mot øst");
             translationMap.put("Polish_East", "Jedziesz na wschód");
             translationMap.put("Portuguese_East", "Você está indo para o leste");
             translationMap.put("Romanian_East", "Te îndrepți spre est");
             translationMap.put("Russian_East", "Вы движетесь на восток");
             translationMap.put("Slovak_East", "Smerujete na východ");
             translationMap.put("Slovenian_East", "Usmerjeni ste proti vzhodu");
             translationMap.put("Spanish_East", "Te diriges hacia el Este");
             translationMap.put("Swahili_East", "Unaelekea kuelekea Mashariki");
             translationMap.put("Swedish_East", "Du är på väg mot öster");
             translationMap.put("Tagalog_East", "Ikaw ay patungo sa Silangan");
             translationMap.put("Tamil_East", "நீங்கள் கிழக்கு செல்லுகின்றீர்கள்");
             translationMap.put("Telugu_East", "మీరు తూర్పుకు వెళ్ళుతున్నారు");
             translationMap.put("Thai_East", "คุณกำลังเดินทางไปทางตะวันออก");
             translationMap.put("Turkish_East", "Doğuya doğru ilerliyorsunuz");
             translationMap.put("Ukrainian_East", "Ви рухаєтеся на схід");
             translationMap.put("Urdu_East", "آپ مشرق کی طرف جا رہے ہیں");
             translationMap.put("Vietnamese_East", "Bạn đang đi về hướng Đông");

// West translations
             translationMap.put("Afrikaans_West", "Jy gaan wes");
             translationMap.put("Albanian_West", "Ju shkoni në perëndim");
             translationMap.put("Bengali_West", "আপনি পশ্চিমে যাচ্ছেন");
             translationMap.put("Bulgarian_West", "Ви тръгвате на запад");
             translationMap.put("Catalan_West", "Esteu anant cap a l'oest");
             translationMap.put("Chinese_West", "你往西走");
             translationMap.put("Croatian_West", "Idete na zapad");
             translationMap.put("Czech_West", "Jdete na západ");
             translationMap.put("Danish_West", "Du går mod vest");
             translationMap.put("Dutch_West", "Je gaat naar het westen");
             translationMap.put("English_West", "You are heading west");
             translationMap.put("Finnish_West", "Olet menossa länteen");
             translationMap.put("French_West", "Vous allez vers l'ouest");
             translationMap.put("Galician_West", "Estás indo cara ao oeste");
             translationMap.put("Georgian_West", "თქვენ დაბრუნებული გრაძის");
             translationMap.put("German_West", "Du gehst nach Westen");
             translationMap.put("Greek_West", "Κατευθύνεστε δυτικά");
             translationMap.put("Gujarati_West", "તમે પશ્ચિમ દિશામાં જઈ રહ્યા છો");
             translationMap.put("Haitian_West", "Ou ap mache nan direksyon Lwès");
             translationMap.put("Hebrew_West", "אתה הולך למערב");
             translationMap.put("Hindi_West", "आप पश्चिम की ओर जा रहे हैं");
             translationMap.put("Hungarian_West", "Nyugat felé tartasz");
             translationMap.put("Icelandic_West", "Þú ert á leiðinni vestur");
             translationMap.put("Indonesian_West", "Anda sedang menuju ke barat");
             translationMap.put("Italian_West", "Ti stai dirigendo verso Ovest");
             translationMap.put("Japanese_West", "あなたは西に向かっています");
             translationMap.put("Kannada_West", "ನೀವು ಪಶ್ಚಿಮದ ದಿಕ್ಕಿಗೆ ಹೋಗುತ್ತಿದ್ದೀರಿ");
             translationMap.put("Korean_West", "서쪽으로 향하고 있습니다");
             translationMap.put("Latvian_West", "Jūs dodaties uz rietumiem");
             translationMap.put("Lithuanian_West", "Jūs keliuojate į vakarus");
             translationMap.put("Macedonian_West", "Вие одамнапред на запад");
             translationMap.put("Malay_West", "Anda sedang menuju ke barat");
             translationMap.put("Malayalam_West", "നിങ്ങൾ പടിഞ്ഞാറേക്ക് പോകുന്നു");
             translationMap.put("Maltese_West", "Int qed tmur il-barra lejn il-Għarb");
             translationMap.put("Marathi_West", "तुम्ही पश्चिम दिशेला जात आहात");
             translationMap.put("Norwegian_West", "Du er på vei mot vest");
             translationMap.put("Polish_West", "Jedziesz na zachód");
             translationMap.put("Portuguese_West", "Você está indo para o oeste");
             translationMap.put("Romanian_West", "Te îndrepți spre vest");
             translationMap.put("Russian_West", "Вы движетесь на запад");
             translationMap.put("Slovak_West", "Smerujete na západ");
             translationMap.put("Slovenian_West", "Usmerjeni ste proti zahodu");
             translationMap.put("Spanish_West", "Te diriges hacia el Oeste");
             translationMap.put("Swahili_West", "Unaelekea kuelekea Magharibi");
             translationMap.put("Swedish_West", "Du är på väg mot väst");
             translationMap.put("Tagalog_West", "Ikaw ay patungo sa Kanluran");
             translationMap.put("Tamil_West", "நீங்கள் மேற்கு செல்லுகின்றீர்கள்");
             translationMap.put("Telugu_West", "మీరు పశ్చిమానికి వెళ్ళుతున్నారు");
             translationMap.put("Thai_West", "คุณกำลังเดินทางไปทางตะวันตก");
             translationMap.put("Turkish_West", "Batıya doğru ilerliyorsunuz");
             translationMap.put("Ukrainian_West", "Ви рухаєтеся на захід");
             translationMap.put("Urdu_West", "آپ مغرب کی طرف جا رہے ہیں");
             translationMap.put("Vietnamese_West", "Bạn đang đi về hướng Tây");

// North-East translations
             translationMap.put("Afrikaans_North-East", "Jy gaan noordoos");
             translationMap.put("Albanian_North-East", "Ju po shkoni në verilindje");
             translationMap.put("Bengali_North-East", "আপনি উত্তর-পূর্বে যাচ্ছেন");
             translationMap.put("Bulgarian_North-East", "Вие отивате на североизток");
             translationMap.put("Catalan_North-East", "Vas cap al nord-est");
             translationMap.put("Chinese_North-East", "您正前往东北方");
             translationMap.put("Croatian_North-East", "Idete na sjeveroistok");
             translationMap.put("Czech_North-East", "Jdete na severovýchod");
             translationMap.put("Danish_North-East", "Du går nordøst");
             translationMap.put("Dutch_North-East", "Je gaat naar het noordoosten");
             translationMap.put("English_North-East", "You are heading North-East");
             translationMap.put("Finnish_North-East", "Olet matkalla koilliseen");
             translationMap.put("French_North-East", "Vous vous dirigez vers le nord-est");
             translationMap.put("Galician_North-East", "Estás a dirixirte ao nordeste");
             translationMap.put("Georgian_North-East", "თქვენ ხართ ჩრდილო-აღმოსავლეთში");
             translationMap.put("German_North-East", "Du gehst nach Nordosten");
             translationMap.put("Greek_North-East", "Πηγαίνετε βορειοανατολικά");
             translationMap.put("Gujarati_North-East", "તમે ઉત્તર-પૂર્વ દિશામાં જાઓ છો");
             translationMap.put("Haitian_North-East", "Ou ap mache nan direksyon Norès-Est");
             translationMap.put("Hebrew_North-East", "אתה הולך לצפון-מזרח");
             translationMap.put("Hindi_North-East", "आप उत्तर-पूर्व की ओर जा रहे हैं");
             translationMap.put("Hungarian_North-East", "Északkelet felé tartasz");
             translationMap.put("Icelandic_North-East", "Þú ert á leiðinni norðaustur");
             translationMap.put("Indonesian_North-East", "Anda sedang menuju ke arah timur laut");
             translationMap.put("Italian_North-East", "Stai andando a nord-est");
             translationMap.put("Japanese_North-East", "あなたは北東に向かっています");
             translationMap.put("Kannada_North-East", "ನೀವು ಉತ್ತರ-ಪೂರ್ವ ದಿಕ್ಕಿಗೆ ಹೋಗುತ್ತಿದ್ದೀರಿ");
             translationMap.put("Korean_North-East", "북동쪽으로 향하고 있습니다");
             translationMap.put("Latvian_North-East", "Jūs dodaties uz ziemeļaustrumiem");
             translationMap.put("Lithuanian_North-East", "Jūs einate į šiaurės rytus");
             translationMap.put("Macedonian_North-East", "Отидувате на североисток");
             translationMap.put("Malay_North-East", "Anda menuju ke arah timur laut");
             translationMap.put("Malayalam_North-East", "നിങ്ങൾ ഉത്തര-കിഴക്ക് പോകുന്നു");
             translationMap.put("Maltese_North-East", "Int qed tmur il-lvant-Nofsinhar");
             translationMap.put("Marathi_North-East", "तुम्ही उत्तर-पूर्व कडे जात आहात");
             translationMap.put("Norwegian_North-East", "Du går mot nordøst");
             translationMap.put("Polish_North-East", "Idziesz na północny wschód");
             translationMap.put("Portuguese_North-East", "Você está indo para o nordeste");
             translationMap.put("Romanian_North-East", "Te îndrepți spre nord-est");
             translationMap.put("Russian_North-East", "Вы движетесь на северо-восток");
             translationMap.put("Slovak_North-East", "Smerujete na severovýchod");
             translationMap.put("Slovenian_North-East", "Usmerjeni ste proti severovzhodu");
             translationMap.put("Spanish_North-East", "Te diriges hacia el Noreste");
             translationMap.put("Swahili_North-East", "Unaelekea kuelekea Kaskazini-Mashariki");
             translationMap.put("Swedish_North-East", "Du är på väg mot nordost");
             translationMap.put("Tagalog_North-East", "Ikaw ay patungo sa Hilagang-Silangan");
             translationMap.put("Tamil_North-East", "நீங்கள் வடக்கு-கிழக்கு செல்லுகின்றீர்கள்");
             translationMap.put("Telugu_North-East", "మీరు ఉత్తర తూర్పు దిశగా వెళ్ళుతున్నారు");
             translationMap.put("Thai_North-East", "คุณกำลังเดินทางไปทางตะวันออกเฉียงเหนือ");
             translationMap.put("Turkish_North-East", "Kuzeydoğuya doğru ilerliyorsunuz");
             translationMap.put("Ukrainian_North-East", "Ви рухаєтеся на північний схід");
             translationMap.put("Urdu_North-East", "آپ شمال مشرق کی طرف جا رہے ہیں");
             translationMap.put("Vietnamese_North-East", "Bạn đang đi về phía đông-bắc");

// South-East translations
             translationMap.put("Afrikaans_South-East", "Jy gaan suidoos");
             translationMap.put("Albanian_South-East", "Ju po shkoni në jug-lindje");
             translationMap.put("Bengali_South-East", "আপনি পূর্ব-দক্ষিণ দিকে যাচ্ছেন");
             translationMap.put("Bulgarian_South-East", "Вие отивате на югоизток");
             translationMap.put("Catalan_South-East", "Esteu anant cap al sud-est");
             translationMap.put("Chinese_South-East", "您正前往东南");
             translationMap.put("Croatian_South-East", "Idete na jugoistok");
             translationMap.put("Czech_South-East", "Jdete na jihovýchod");
             translationMap.put("Danish_South-East", "Du går mod sydøst");
             translationMap.put("Dutch_South-East", "Je gaat naar het zuidoosten");
             translationMap.put("English_South-East", "You are heading southeast");
             translationMap.put("Finnish_South-East", "Olet matkalla kaakkoon");
             translationMap.put("French_South-East", "Vous vous dirigez vers le sud-est");
             translationMap.put("Galician_South-East", "Estás a dirixirte ao sueste");
             translationMap.put("Georgian_South-East", "თქვენ გახსნილი მხედრის სიმართლე");
             translationMap.put("German_South-East", "Sie gehen nach Südosten");
             translationMap.put("Greek_South-East", "Πηγαίνετε νοτιοανατολικά");
             translationMap.put("Gujarati_South-East", "તમે દક્ષિણ-પૂર્વ દિશામાં જાઓ છો");
             translationMap.put("Haitian_South-East", "Ou ap mache nan direksyon Sidès-Est");
             translationMap.put("Hebrew_South-East", "אתה הולך לדרום-מזרח");
             translationMap.put("Hindi_South-East", "आप दक्षिण-पूर्व की ओर जा रहे हैं");
             translationMap.put("Hungarian_South-East", "Délkelet felé tartasz");
             translationMap.put("Icelandic_South-East", "Þú ert á leiðinni suðaustur");
             translationMap.put("Indonesian_South-East", "Anda sedang menuju ke arah tenggara");
             translationMap.put("Italian_South-East", "Stai andando a sud-est");
             translationMap.put("Japanese_South-East", "あなたは南東に向かっています");
             translationMap.put("Kannada_South-East", "ನೀವು ದಕ್ಷಿಣ-ಪೂರ್ವ ದಿಕ್ಕಿಗೆ ಹೋಗುತ್ತಿದ್ದೀರಿ");
             translationMap.put("Korean_South-East", "남동쪽으로 향하고 있습니다");
             translationMap.put("Latvian_South-East", "Jūs dodaties uz dienvidaustrumiem");
             translationMap.put("Lithuanian_South-East", "Jūs einate į pietryčius");
             translationMap.put("Macedonian_South-East", "Отидувате на југоисток");
             translationMap.put("Malay_South-East", "Anda menuju ke arah tenggara");
             translationMap.put("Malayalam_South-East", "നിങ്ങൾ തെക്കു-കിഴക്ക് പോകുന്നു");
             translationMap.put("Maltese_South-East", "Int qed tmur is-sud-est");
             translationMap.put("Marathi_South-East", "तुम्ही दक्षिण-पूर्व कडे जात आहात");
             translationMap.put("Norwegian_South-East", "Du går mot sørøst");
             translationMap.put("Polish_South-East", "Idziesz na południowy wschód");
             translationMap.put("Portuguese_South-East", "Você está indo para o sudeste");
             translationMap.put("Romanian_South-East", "Te îndrepți spre sud-est");
             translationMap.put("Russian_South-East", "Вы движетесь на юго-восток");
             translationMap.put("Slovak_South-East", "Smerujete na juhovýchod");
             translationMap.put("Slovenian_South-East", "Usmerjeni ste proti jugovzhodu");
             translationMap.put("Spanish_South-East", "Te diriges hacia el sureste");
             translationMap.put("Swahili_South-East", "Unaelekea kuelekea Kusini-Mashariki");
             translationMap.put("Swedish_South-East", "Du är på väg mot sydost");
             translationMap.put("Tagalog_South-East", "Ikaw ay patungo sa Timog-Silangan");
             translationMap.put("Tamil_South-East", "நீங்கள் தென்கிழக்கு செல்லுகின்றீர்கள்");
             translationMap.put("Telugu_South-East", "మీరు దక్షిణ తూర్పు దిశగా వెళ్ళుతున్నారు");
             translationMap.put("Thai_South-East", "คุณกำลังเดินทางไปทางตะวันออกเฉียงใต้");
             translationMap.put("Turkish_South-East", "Güneydoğuya doğru ilerliyorsunuz");
             translationMap.put("Ukrainian_South-East", "Ви рухаєтеся на південний схід");
             translationMap.put("Urdu_South-East", "آپ جنوب مشرق کی طرف جا رہے ہیں");
             translationMap.put("Vietnamese_South-East", "Bạn đang đi về phía đông-nam");

// North-West translations
             translationMap.put("Afrikaans_North-West", "Jy gaan noordwes");
             translationMap.put("Albanian_North-West", "Ju po shkoni në veri-perëndim");
             translationMap.put("Bengali_North-West", "আপনি উত্তর-পশ্চিম দিকে যাচ্ছেন");
             translationMap.put("Bulgarian_North-West", "Вие отивате на северозапад");
             translationMap.put("Catalan_North-West", "Esteu anant cap al nord-oest");
             translationMap.put("Chinese_North-West", "您正前往西北");
             translationMap.put("Croatian_North-West", "Idete na sjeverozapad");
             translationMap.put("Czech_North-West", "Jdete na severozápad");
             translationMap.put("Danish_North-West", "Du går mod nordvest");
             translationMap.put("Dutch_North-West", "Je gaat naar het noordwesten");
             translationMap.put("English_North-West", "You are heading northwest");
             translationMap.put("Finnish_North-West", "Olet matkalla luoteeseen");
             translationMap.put("French_North-West", "Vous vous dirigez vers le nord-ouest");
             translationMap.put("Galician_North-West", "Estás a dirixirte ao noroeste");
             translationMap.put("Georgian_North-West", "თქვენ გახსნილი ჩრდილოეთ დასავლეთ");
             translationMap.put("German_North-West", "Sie gehen nach Nordwesten");
             translationMap.put("Greek_North-West", "Πηγαίνετε βορειοδυτικά");
             translationMap.put("Gujarati_North-West", "તમે ઉત્તર-પશ્ચિમ દિશામાં જાઓ છો");
             translationMap.put("Haitian_North-West", "Ou ap mache nan direksyon Nòdwès");
             translationMap.put("Hebrew_North-West", "אתה הולך לצפון-מערב");
             translationMap.put("Hindi_North-West", "आप उत्तर-पश्चिम की ओर जा रहे हैं");
             translationMap.put("Hungarian_North-West", "Északnyugat felé tartasz");
             translationMap.put("Icelandic_North-West", "Þú ert á leiðinni norðvestur");
             translationMap.put("Indonesian_North-West", "Anda sedang menuju ke arah barat laut");
             translationMap.put("Italian_North-West", "Stai andando a nord-ovest");
             translationMap.put("Japanese_North-West", "あなたは北西に向かっています");
             translationMap.put("Kannada_North-West", "ನೀವು ಉತ್ತರ-ಪಶ್ಚಿಮ ದಿಕ್ಕಿಗೆ ಹೋಗುತ್ತಿದ್ದೀರಿ");
             translationMap.put("Korean_North-West", "북서쪽으로 향하고 있습니다");
             translationMap.put("Latvian_North-West", "Jūs dodaties uz ziemeļrietumiem");
             translationMap.put("Lithuanian_North-West", "Jūs einate į šiaurės vakarus");
             translationMap.put("Macedonian_North-West", "Отидувате на северозапад");
             translationMap.put("Malay_North-West", "Anda menuju ke arah barat laut");
             translationMap.put("Malayalam_North-West", "നിങ്ങൾ വടക്കു-പടിഞ്ഞാറുകൾ പോകുന്നു");
             translationMap.put("Maltese_North-West", "Int qed tmur il-lvant-Lil-ħarġa");
             translationMap.put("Marathi_North-West", "तुम्ही उत्तर-पश्चिम कडे जात आहात");
             translationMap.put("Norwegian_North-West", "Du går mot nordvest");
             translationMap.put("Polish_North-West", "Idziesz na północny zachód");
             translationMap.put("Portuguese_North-West", "Você está indo para o noroeste");
             translationMap.put("Romanian_North-West", "Te îndrepți spre nord-vest");
             translationMap.put("Russian_North-West", "Вы движетесь на северо-запад");
             translationMap.put("Slovak_North-West", "Smerujete na severozápad");
             translationMap.put("Slovenian_North-West", "Usmerjeni ste proti severozahodu");
             translationMap.put("Spanish_North-West", "Te diriges hacia el Noroeste");
             translationMap.put("Swahili_North-West", "Unaelekea kuelekea Kaskazini-Magharibi");
             translationMap.put("Swedish_North-West", "Du är på väg mot nordväst");
             translationMap.put("Tagalog_North-West", "Ikaw ay patungo sa Hilagang-Kanluran");
             translationMap.put("Tamil_North-West", "நீங்கள் வடக்கு-மேற்கு செல்லுகின்றீர்கள்");
             translationMap.put("Telugu_North-West", "మీరు ఉత్తర-పశ్చిమ దిశగా వెళ్ళుతున్నారు");
             translationMap.put("Thai_North-West", "คุณกำลังเดินทางไปทางตะวันตกเฉียงเหนือ");
             translationMap.put("Turkish_North-West", "Kuzeybatıya doğru ilerliyorsunuz");
             translationMap.put("Ukrainian_North-West", "Ви рухаєтеся на північний захід");
             translationMap.put("Urdu_North-West", "آپ شمال مغرب کی طرف جا رہے ہیں");
             translationMap.put("Vietnamese_North-West", "Bạn đang đi về phía tây-bắc");


//South-West Translations
             translationMap.put("Afrikaans_South-West", "Jy gaan suidwes");
             translationMap.put("Albanian_South-West", "Ju po shkoni në jug-perëndim");
             translationMap.put("Bengali_South-West", "আপনি দক্ষিণ-পশ্চিম দিকে যাচ্ছেন");
             translationMap.put("Bulgarian_South-West", "Вие отивате на югозапад");
             translationMap.put("Catalan_South-West", "Esteu anant cap al sud-oest");
             translationMap.put("Chinese_South-West", "您正前往西南");
             translationMap.put("Croatian_South-West", "Idete na jugozapad");
             translationMap.put("Czech_South-West", "Jdete na jihozápad");
             translationMap.put("Danish_South-West", "Du går mod sydvest");
             translationMap.put("Dutch_South-West", "Je gaat naar het zuidwesten");
             translationMap.put("English_South-West", "You are heading southwest");
             translationMap.put("Finnish_South-West", "Olet matkalla lounaaseen");
             translationMap.put("French_South-West", "Vous vous dirigez vers le sud-ouest");
             translationMap.put("Galician_South-West", "Estás a dirixirte ao sudoeste");
             translationMap.put("Georgian_South-West", "თქვენ გახსნილი სამხრეთ-დასავლეთ");
             translationMap.put("German_South-West", "Sie gehen nach Südwesten");
             translationMap.put("Greek_South-West", "Πηγαίνετε νοτιοδυτικά");
             translationMap.put("Gujarati_South-West", "તમે દક્ષિણ-પશ્ચિમ દિશામાં જાઓ છો");
             translationMap.put("Haitian_South-West", "Ou ap mache nan direksyon Sidès");
             translationMap.put("Hebrew_South-West", "אתה הולך לדרום-מערב");
             translationMap.put("Hindi_South-West", "आप दक्षिण-पश्चिम की ओर जा रहे हैं");
             translationMap.put("Hungarian_South-West", "Dél-nyugat felé tartasz");
             translationMap.put("Icelandic_South-West", "Þú ert á leiðinni suð-vestur");
             translationMap.put("Indonesian_South-West", "Anda sedang menuju ke arah barat daya");
             translationMap.put("Italian_South-West", "Stai andando a sud-ovest");
             translationMap.put("Japanese_South-West", "あなたは南西に向かっています");
             translationMap.put("Kannada_South-West", "ನೀವು ದಕ್ಷಿಣ-ಪಶ್ಚಿಮ ದಿಕ್ಕಿಗೆ ಹೋಗುತ್ತಿದ್ದೀರಿ");
             translationMap.put("Korean_South-West", "남서쪽으로 향하고 있습니다");
             translationMap.put("Latvian_South-West", "Jūs dodaties uz dienvidrietumiem");
             translationMap.put("Lithuanian_South-West", "Jūs einate į pietvakarus");
             translationMap.put("Macedonian_South-West", "Отидувате на југозапад");
             translationMap.put("Malay_South-West", "Anda menuju ke arah barat daya");
             translationMap.put("Malayalam_South-West", "നിങ്ങൾ തെക്കു-പശ്ചിമ കിഴക്കിൽ പോകുന്നു");
             translationMap.put("Maltese_South-West", "Int qed tmur il-għarb-Lil-ħarġa");
             translationMap.put("Marathi_South-West", "तुम्ही दक्षिण-पश्चिम कडे जात आहात");
             translationMap.put("Norwegian_South-West", "Du går mot sørvest");
             translationMap.put("Polish_South-West", "Idziesz na południowy zachód");
             translationMap.put("Portuguese_South-West", "Você está indo para o sudoeste");
             translationMap.put("Romanian_South-West", "Te îndrepți spre sud-vest");
             translationMap.put("Russian_South-West", "Вы движетесь на юго-запад");
             translationMap.put("Slovak_South-West", "Smerujete na juhozápad");
             translationMap.put("Slovenian_South-West", "Usmerjeni ste proti jugozahodu");
             translationMap.put("Spanish_South-West", "Te diriges hacia el Suroeste");
             translationMap.put("Swahili_South-West", "Unaelekea kuelekea Kusini-Magharibi");
             translationMap.put("Swedish_South-West", "Du är på väg mot sydväst");
             translationMap.put("Tagalog_South-West", "Ikaw ay patungo sa Timog-Kanluran");
             translationMap.put("Tamil_South-West", "நீங்கள் தெற்கு-மேற்கு செல்லுகின்றீர்கள்");
             translationMap.put("Telugu_South-West", "మీరు దక్షిణ-పశ్చిమ దిశగా వెళ్ళుతున్నారు");
             translationMap.put("Thai_South-West", "คุณกำลังเดินทางไปทางตะวันตกเฉียงใต้");
             translationMap.put("Turkish_South-West", "Güneybatıya doğru ilerliyorsunuz");
             translationMap.put("Ukrainian_South-West", "Ви рухаєтеся на південний захід");
             translationMap.put("Urdu_South-West", "آپ جنوب مغرب کی طرف جا رہے ہیں");
             translationMap.put("Vietnamese_South-West", "Bạn đang đi về phía tây-nam");



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

             translationMap.put("Afrikaans_opening magnifier", "open vergrootglas");
             translationMap.put("Arabic_opening magnifier", "المكبر الافتتاحي");
             translationMap.put("Hindi_opening magnifier", "ओपनिंग मैग्नीफायर");
             translationMap.put("English_opening magnifier", "opening magnifier");
             translationMap.put("Marathi_opening magnifier", "उघडणारे म्याग्निफायर");
             translationMap.put("Belarusian_opening magnifier", "адкрыццё магніфікатара");
             translationMap.put("Bulgarian_opening magnifier", "отваряне увеличител");
             translationMap.put("Bengali_opening magnifier", "উদ্বারণ বৃদ্ধি");
             translationMap.put("Catalan_opening magnifier", "ampliador d'obertura");
             translationMap.put("Czech_opening magnifier", "otevírací zvětšovací sklo");
             translationMap.put("Welsh_opening magnifier", "magnifydd agoriadol");
             translationMap.put("Danish_opening magnifier", "åbning forstørrelsesglas");
             translationMap.put("German_opening magnifier", "Öffnungsvergrößerer");
             translationMap.put("Greek_opening magnifier", "ανοίγοντας μεγεθυντικό");
             translationMap.put("Esperanto_opening magnifier", "malferma lupo");
             translationMap.put("Spanish_opening magnifier", "ampliador de apertura");
             translationMap.put("Estonian_opening magnifier", "ava suurendusklaas");
             translationMap.put("Persian_opening magnifier", "بزرگنمایی افتتاحیه");
             translationMap.put("Finnish_opening magnifier", "avaus suurennuslasi");
             translationMap.put("French_opening magnifier", "loupe d'ouverture");
             translationMap.put("Irish_opening magnifier", "maigneola oscailte");
             translationMap.put("Galician_opening magnifier", "ampliador de apertura");
             translationMap.put("Gujarati_opening magnifier", "ઓપનિંગ મેગ્નિફાયર");
             translationMap.put("Hebrew_opening magnifier", "מגדיל פתיחה");
             translationMap.put("Croatian_opening magnifier", "otvaranje lupe");
             translationMap.put("Haitian_opening magnifier", "gwo loup ouvèti");
             translationMap.put("Hungarian_opening magnifier", "nyitó nagyító");
             translationMap.put("Indonesian_opening magnifier", "pembesar pembukaan");
             translationMap.put("Icelandic_opening magnifier", "opnun stærri");
             translationMap.put("Italian_opening magnifier", "ingranditore di apertura");
             translationMap.put("Japanese_opening magnifier", "オープニングマグニファイヤー");
             translationMap.put("Georgian_opening magnifier", "გახსნის მაგნიფიკატორი");
             translationMap.put("Kannada_opening magnifier", "ಆರಂಭಿಕ ಮ್ಯಾಗ್ನಿಫೈಯರ್");
             translationMap.put("Korean_opening magnifier", "오프닝 확대경");
             translationMap.put("Lithuanian_opening magnifier", "atidarymo didinamasis stiklas");
             translationMap.put("Latvian_opening magnifier", "atvēršanas palielinātājs");
             translationMap.put("Macedonian_opening magnifier", "отварање лупа");
             translationMap.put("Malay_opening magnifier", "pembesar pembukaan");
             translationMap.put("Maltese_opening magnifier", "mgħaġġel ta' ftuħ");
             translationMap.put("Dutch_opening magnifier", "openingsloep");
             translationMap.put("Norwegian_opening magnifier", "åpning forstørrelsesglass");
             translationMap.put("Polish_opening magnifier", "lupa otwarcia");
             translationMap.put("Portuguese_opening magnifier", "ampliador de abertura");
             translationMap.put("Romanian_opening magnifier", "lupă de deschidere");
             translationMap.put("Russian_opening magnifier", "открывающий увеличитель");
             translationMap.put("Slovak_opening magnifier", "otvárací zväčšovací sklo");
             translationMap.put("Slovenian_opening magnifier", "odpiranje povečevalnega stekla");
             translationMap.put("Albanian_opening magnifier", "lupa e hapjes");
             translationMap.put("Swedish_opening magnifier", "öppning förstoringsglas");
             translationMap.put("Swahili_opening magnifier", "kioo cha ufunguzi");
             translationMap.put("Tamil_opening magnifier", "திறக்கும் மேக்னிபையர்");
             translationMap.put("Telugu_opening magnifier", "ఓపెనింగ్ మ్యాగ్నిఫైర్");
             translationMap.put("Thai_opening magnifier", "เลนส์ขยายเปิด");
             translationMap.put("Tagalog_opening magnifier", "bukas na malaking kagamitan");
             translationMap.put("Turkish_opening magnifier", "açılış büyüteç");
             translationMap.put("Ukrainian_opening magnifier", "відкриваючий збільшувач");
             translationMap.put("Urdu_opening magnifier", "خواندہ میگنیفائر کھولنا");
             translationMap.put("Vietnamese_opening magnifier", "kính lúp mở đầu");
             translationMap.put("Chinese_opening magnifier", "开放放大镜");


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

//             Map<String, String> languageLocalMap = new HashMap<>();
             languageLocalMap.put("Afrikaans", "Afrikaans");
             languageLocalMap.put("Arabic", "العربية");
             languageLocalMap.put("Bengali", "বাংলা");
             languageLocalMap.put("Belarusian", "беларуская");
             languageLocalMap.put("Bulgarian", "български");
             languageLocalMap.put("Catalan", "català");
             languageLocalMap.put("Chinese", "中文");
             languageLocalMap.put("Croatian", "hrvatski");
             languageLocalMap.put("Czech", "čeština");
             languageLocalMap.put("Danish", "dansk");
             languageLocalMap.put("Dutch", "Nederlands");
             languageLocalMap.put("English", "English");
             languageLocalMap.put("Estonian", "eesti");
             languageLocalMap.put("Finnish", "suomi");
             languageLocalMap.put("French", "français");
             languageLocalMap.put("German", "Deutsch");
             languageLocalMap.put("Greek", "ελληνικά");
             languageLocalMap.put("Gujarati", "ગુજરાતી");
             languageLocalMap.put("Haitian", "kreyòl ayisyen");
             languageLocalMap.put("Hebrew", "עברית");
             languageLocalMap.put("Hindi", "हिन्दी");
             languageLocalMap.put("Hungarian", "magyar");
             languageLocalMap.put("Icelandic", "íslenska");
             languageLocalMap.put("Indonesian", "Indonesia");
             languageLocalMap.put("Irish", "Gaeilge");
             languageLocalMap.put("Italian", "italiano");
             languageLocalMap.put("Japanese", "日本語");
             languageLocalMap.put("Kannada", "ಕನ್ನಡ");
             languageLocalMap.put("Korean", "한국어");
             languageLocalMap.put("Latvian", "latviešu");
             languageLocalMap.put("Lithuanian", "lietuvių");
             languageLocalMap.put("Macedonian", "македонски");
             languageLocalMap.put("Malay", "Bahasa Melayu");
             languageLocalMap.put("Maltese", "Malti");
             languageLocalMap.put("Norwegian", "norsk");
             languageLocalMap.put("Persian", "فارسی");
             languageLocalMap.put("Polish", "polski");
             languageLocalMap.put("Portuguese", "português");
             languageLocalMap.put("Romanian", "română");
             languageLocalMap.put("Russian", "русский");
             languageLocalMap.put("Slovak", "slovenčina");
             languageLocalMap.put("Slovenian", "slovenščina");
             languageLocalMap.put("Spanish", "español");
             languageLocalMap.put("Swahili", "Kiswahili");
             languageLocalMap.put("Swedish", "svenska");
             languageLocalMap.put("Tagalog", "Tagalog");
             languageLocalMap.put("Tamil", "தமிழ்");
             languageLocalMap.put("Telugu", "తెలుగు");
             languageLocalMap.put("Thai", "ไทย");
             languageLocalMap.put("Turkish", "Türkçe");
             languageLocalMap.put("Ukrainian", "українська");
             languageLocalMap.put("Urdu", "اردو");
             languageLocalMap.put("Vietnamese", "Tiếng Việt");
             languageLocalMap.put("Welsh", "Cymraeg");
     }

}
