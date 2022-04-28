##### Termín: 25. 5. 2022 #####
###### Semestrální práce ######
# Zápisník letů

## Zadání práce

 - ### **Popis problému (motivace)**
   - Zápisník letu bude sloužit pro piloty, který si budou chtít své lety zapisovat elektronicky
   - Záznamník letů bude umožňovat uživateli si zapisovat lety, které budou ukládány do textové souboru. A následně je bude moc vyexportovat do PDF. 
     - Místo startu / přistání
     - Čas vzletu / přistání
     - Celková doba letu
     - Počet startů
     - Typ statu
     - Typ letu
     - Velitel letadla
     - Typ letadla
     - Registrace letadla
   - Uživatel může lety načíst, filtrovat, řadit, vyhledávat apod.
   - Uživatel může vytvářet své zápisníky letů např. pro vetroně, ultralighty nebo motorové letadla.
   - Celkové součty letů, hodin a startů
   - Užitel může načíst deníky letadel ze souboru
     - Údaje v deníkách letadel budou různé od typu letadla (větroň, ultralight, motorové letadlo)
 
 - ### **Popis řešení**
   - 
## Řešení

 - ### **Funkční specifikace**
    - 
 - ### **Popis struktury vstupních a výstupních souborů**
   - 
 - ### **Class diagram**
   - 

## Testování

## Popis fungování externích knihoven
- [LEADTOOLS Document Converter](https://www.leadtools.com/sdk/document/document-converter)

## Funkční a technické požadavky na aplikaci

Vámi definované zadání a následně vypracovaná semestrální práce musí minimálně obsahovat:	
1. Menu, které umožní opakovaný výběr funkcí aplikácie a ukončení aplikace
2. Přehledný výpis výsledků na konzoli - použijte alespoň jednou String.format() a StringBuilder
3. Načítání vstupních dat z minimálně dvou souborů 
4. Zápis výstupních dat do souboru
5. Možnosť práce s textovými a binárními soubory (alespoň někde)
6. Ideálně využití reálných otevřených dat
7. Adresář data se všemi datovými soubory a případně třídu Datastore se statickými metodami, které budou poskytovat další statická data.
8. Tri balíčky: 	
    - a. 	**ui** – třídy, tvořící uživatelské rozhraní - komunikaci s uživatelem
    - b. 	**app** – třídy, tvořící logiku s daty aplikácie - modely, kontrolery
    - c. 	**utils** – pomocné třídy např. vlastní výjimky, vlastní rozhraní
9. Programování vůči rozhraní a použití vlastního rozhraní
10. Použití java.time API pro práci s časem
11. Použít enum typ
12. Použití kontejnerové třídy jazyka Java (ArrayList, LinkedList, HashMap ...) z Collections frameworku.
13. Alespoň dvě možnosti třídění s využitím rozhraní Comparable a Comparator 
14. Použití regulárního výrazu
15.Ošetření vstupů, aby chybné vstupy nezpůsobily pád programu - pomocí existujících a vlastních výjimek
16. Vhodné ošetření povinně ošetřovaných výjimek
17. Použití Vámi vybrané externí knihovny (audio, posílání emailů, práce s obrázkem, junit testování, jiné formáty uložení dat ...)
18. Javadoc - každá třída a metoda musí mít javadoc popis, abyste mohli na závěr vygenerovat javadoc dokumentaci. 

## Dokumentace
Dokumentace obsahuje zjednodušené fáze analýzy, designu a přípravy na testování, které sa píšou před samotným kódováním. Dokumentaci vytvořte jako **README** ve Vašem GitHub projektu.
Struktura:
 * **Zadání práce**
    - **Popis problému (motivace)**
    - **Popis řešení**
 * **Řešení**
    - **Funkční specifikace** – seznam funkcí z pohledu uživatele, které bude Váš program poskytovat např. formou větveného seznamu (stromu). Může sloužit následně jako podklad pro menu.
    - **Popis struktury vstupních a výstupních souborů** – jaké datové typy bude obsahovat, čím budou odděleny jednotlivé údaje, jestli je požadovaný určitý formát názvů souborů a pod.
    - **Class diagram** - pomocí nějakého nástroje např. diagram.net
 * **Testování**
    - Vytvořte testovací soubory. Vstupní soubory naplňte testovacími daty v minimálním rozsahu 20 (řádků). Napište sadu 10 testů - při zadaných konkrétních vstupech uživatele, jaké jsou očekávané konzolové výstupy. Sada by měla obsahovat testy funkcionality programu pro běžné i limitní stavy jako i testy ošetření vstupů.
 * **Popis fungování externí knihovny.**

 * **Zdrojový kód**
    - Snažte se dodržet a využít principy objektového programování.
    - Důraz klaďte na funkčnost a přehlednost kódu. KISS - Keep It Simple and Smart.
    - Používejte smysluplné názvy proměnných (nemusíte ich pak komentovat).
    - Vymažte z programu nepotřebný kód (alternativní řešení) kromě testovacích main metod.
 
## Prezentace
 - V zápočtovém týdnu předvedete Vaší aplikaci a spolužáci Vám ji otestují.  
 - Připravte si slidy, kde představíte 
    - zadání
    - objektový návrh - rozdělení do tříd (zjednodušený class diagram)
    - způsob použití externí neprobírané  knihovny.


