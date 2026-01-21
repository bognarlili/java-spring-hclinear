## Általános tudnivalók és elvárások

A feladat célja az általános backend fejlesztői tudás felmérése , különös tekintettel a problémamegoldó képességre, 
az üzleti logika kezelésére és a kód minőségére. Nem egy „helyes megoldás” elkészítése a cél, hanem annak bemutatása,
hogy a jelölt hogyan gondolkodik, tervez és valósít meg egy összetettebb funkcionális követelményrendszert.

A feladat megoldása során törekedjen az önálló munkavégzésre. Amennyiben elakadás történik, lehetőség 
van segítséget kérni e-mailben, azonban javasolt először saját megoldást keresni és döntéseket hozni.

A feladat értékelése során nem a funkcionalitás mennyisége, hanem a megoldás minősége, következetessége és átgondoltsága kerül előtérbe.

A feladatok egy része egymásra épül, azonban nem mindegyik. 
Amennyiben egy adott részfeladat megoldása során elakad, javasolt továbblépni, és a további feladatokat elvégezni a lehetőségekhez mérten.

A feladat megoldását Git verziókezelő rendszerben kell elkészíteni és beadni.
Kérjük, hogy a megoldás során figyeljen a tudatos és következetes commitolásra: a feladat előrehaladását több, kisebb, jól elkülöníthető commitban rögzítse.

A feladat megoldásához az API-k használatát dokumentálni kell. 
Ennek érdekében az alábbi lehetőségek közül legalább az egyiket szükséges megvalósítani:
- Swagger / OpenAPI 
- egy Postman collection csatolása

---

## Környezet

- A projekt tartalmaz egy előre konfigurált development környezetet, amely a tesztfeladat
megoldásához szükséges infrastruktúrát biztosítja. A repository gyökerében található a 'development' könyvtárban egy docker-compose.yml fájl.
Ennek elindításával egy PostgreSQL adatbázis környezet indul el. A tesztfeladat során elvárás, hogy az alkalmazás ehhez az adatbázishoz csatlakozzon.

- A projektben előre be vannak állítva olyan Spring Boot és kiegészítő függőségek, amelyek használata a feladat megoldása során ajánlott.
pl.:
  - Flyway
  - MapStruct
  - Lombok
  - ...

---

## Feladat leírás

A feladat célja egy egyszerű Spring Boot alapú backend alkalmazás elkészítése, 
amelyben focicsapatok és az azokhoz tartozó játékosok kezelésére van lehetőség.

A rendszernek alkalmasnak kell lennie csapatok és játékosok létrehozására, módosítására, lekérdezésére, 
valamint a köztük lévő kapcsolatok kezelésére.

A megoldás során elvárás az átlátható architektúra és a jól elkülönített rétegek.
A hangsúly nem a funkcionalitás mennyiségén, hanem a megoldás minőségén, bővíthetőségén és karbantarthatóságán van.

---

## Feladatok:

### 1. Feladat:
Indítsa el és konfigurálja az alkalmazást úgy, hogy a lokális környezetén futó PostgreSQL adatbázishoz csatlakozzon
(a development könyvtárban található docker-compose.yml segítségével).

A konfiguráció során ügyeljen arra, hogy az adatbázis-kapcsolati beállítások kizárólag fejlesztői környezetben legyenek érvényesek
és ne befolyásolják a későbbi, nem fejlesztői futtatást. (használj profilt)

###  2. Feladat:
Konfigurálja az alkalmazást úgy, hogy Flyway használatával a src/main/resources alatt található 
migrációs fájlok alapján felépítse az adatbázis kezdeti állapotát.

###  3. Feladat:
Készítse el a Csapatok (Team) és Játékosok (Player) kezelésére szolgáló adatbázis táblákat, 
valamint a hozzájuk tartozó Java osztályokat (Entity-ket).

Csapat:
- name
- city
- foundedYear
- budget
...

Játékos:
- firstName
- lastName
- position: Éréke következőkből választható: GK, DF, MF, FW
- shirtNumber
- birthDate
- marketValue
...

### 4. Feladat:
A. Készítse el a csapatok (Team) és játékosok (Player) kezelésére szolgáló CRUD funkcionalitást. Az alábbi API műveletek implementálása szükséges mindkét entitásra

- Create – új erőforrás létrehozása
- List – erőforrások listázása
- Load – egy konkrét erőforrás lekérdezése azonosító alapján
- Delete – erőforrás törlése

Játékosoknál listázásnál a vissza adott objektum tartalmazza a csapat nevét (ha van neki), teljes játékos nevet összefűzve.
Csapatok esetén tartalmazza a játékosok számát és csapat kapítány teljes nevét.

Team (csapat) szabályok:
- Csapatnév egyedi: két csapat nem hozható létre azonos name értékkel.
- Alapítás éve érvényes: foundedYear nem lehet a jövőben (és legyen életszerű minimum).

Player (játékos) szabályok:
- Mezszám tartomány: shirtNumber csak érvényes tartományban lehet (pl. 1–99).
- Életkor érvényesítés: birthDate alapján a játékos legyen legalább egy minimális életkor (pl. 15 év).
- Piaci érték: marketValue nem lehet negatív.

B. A fenti szabályok megsértése esetén az alkalmazásnak egységes API válaszformátumot kell visszaadnia. Ez a késöbbiekben minden 
hibakezelésre igaz.
Válasznak tartalmaznia kell a következőket:
- Hiba Típusa
- Üzenet
- Részletek

### 5. Feladat:
Egészítse ki a játékos kezelést egy olyan API val amiben a játék átigazolása kezelhető. 

Kérelmi folyamat:

Az átigazolás első lépése egy átigazolási kérelem létrehozása. A kérelemnek a következőket mindenképpen kell tartalmaznia:
- Keletkezés dátuma
- Kérelem lejárati dátuma: Ezt követően már nem lehet elfogadni
- Erintett játékos
- Forrás csapat (opcionális, lehet üres),
- Cél csapat.
- Átigazolási összes: ha játékosnak nincs csapata akkor lehet 0
- ...

A kérelem leadását követően az átigazolási kérelem elfogadható vagy elutasítható:
- Elutasítás esetén az átigazolás nem történik meg.
- Elfogadás esetén az átigazolás végrehajtásra kerül.

Amennyiben egy adott játékoshoz tartozó átigazolási kérelmet a rendszer elfogad, akkor minden más, ugyanarra a játékosra vonatkozó ,
még le nem zárt kérelem automatikusan lezárásra kerül.

A kérelmeket játékosonként, cél és forrás csapatonként is lehessen listázni.

Átigazolás végrehajtása:

Az átigazolás végrehajtása során az alkalmazásnak több üzleti szabályt kell érvényesítenie. 
A művelet csak akkor hajtható végre, ha az átigazolni kívánt játékos létezik a rendszerben, valamint a célcsapat is létező csapat.
Meg kell vizsgálni, hogy a cél csapat rendelkezik-e megfelelő mennyiségü kerettel. Egy csapatnak maximálisan 25 játékosa lehet.
Sikeres átigazolás esetén a célcsapat költségkerete csökken az átigazolás értékével, míg a forráscsapat költségkerete ugyanennyivel növekszik.
A folyamat végén a játékos csapat-hozzárendelése frissül, és a játékos a célcsapat tagjává válik.

### 6. Feladat:
Egészítse ki az átigazolás (transfer) funkcionalitást úgy, hogy sikeres átigazolás esetén a rendszer automatikusan hozzon létre egy átigazolást érvényesítő dokumentumot. 
A dokumentumot adatbázisban kell tárolni (külön táblában), és később lekérdezhetőnek, listázhatónak kell lennie. A dokumentum egyedi azonosítóval kell rendelkeznie.

- Egyedi azonosító
- Átigazólás dátuma
- Erintett játékos
- Forrás csapat (opcionális, lehet üres),
- Cél csapat.
- Átigazolási összes
- ...

A dokumentum rendelkezzen egy emberileg is olvasható, egyedi azonosítóval, amely:

- prefix + futó sorszám + suffix felépítésű
- a futó sorszám növekvő, folyamatos sorszámozás (pl. 000014)
- fix hosszúságú (nullákkal feltöltve)
- példa: ATIG000014HU

### 7. Feladat:
Készítsen egy olyan apit ami az előző feladatban létrehozott dokumentumot PDF formátumba ki tudja nyomtatni. (PDF kinézete nem számít). 
Az adattartalomnak a következőnek kell lennie:
- Játékos teljes név
- Forrás csapat adatok 
- Cél csapat adatok
- Átigazolási összeg (a játékos marketValue értéke)
- létrehozás ideje

pl.:
```
ÁTIGAZOLÁSI DOKUMENTUM

Játékos:
Név: John Smith

Forrás csapat:
Név: FC Example
Város: Budapest

Cél csapat:
Név: United FC
Város: Manchester

Átigazolási összeg:
25 000 000

Létrehozás ideje:
2024-03-18 14:32
```