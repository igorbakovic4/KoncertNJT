# Aplikacija za upravljanje koncertima

Web aplikacija za administraciju koncerata, lokacija, izvodjaca i karata, sa podrskom za kupovinu karata od strane korisnika.

---

## Tehnologije

### Backend
- Java 21
- Spring Boot 4
- Spring Security + JWT autentifikacija
- Spring Data JPA + Hibernate
- MySQL
- Maven
- JUnit 5 + Mockito (testiranje)

### Frontend
- Angular 21
- TypeScript

---

## Domenske klase

| Klasa | Opis |
|---|---|
| `Grad` | Grad u kome se nalazi lokacija |
| `Lokacija` | Mesto odrzavanja koncerta |
| `Zanr` | Muzicki zanr izvodjaca |
| `Izvodjac` | Apstraktna klasa — nadklasa za Muzicara i Bend |
| `Muzicar` | Pojedinacni muzicki izvodjac |
| `Bend` | Muzicka grupa |
| `Koncert` | Muzicki dogadjaj na odredjenoj lokaciji |
| `Karta` | Ulaznica za konkretan koncert |
| `Korisnik` | Registrovani korisnik sistema |

---

## Sistemske operacije

**Grad:** findAll, findById, save, update, delete

**Lokacija:** findAll, findById, save, update, delete

**Koncert:** findAll, findById, findByDatum, findByStatus, save, update, updateIzvodjaci, delete, generisiKarte

**Karta:** dohvatiSveZaKoncert, kupiKartu, stornirajKartu, getSummary

**Izvodjac:** dohvatiSve, dohvatiPoId, sacuvajMuzicara, sacuvajBend, obrisiIzvodjaca

**Zanr:** dohvatiSve, dohvatiPoId, sacuvajZanr, obrisiZanr

**Korisnik:** registruj, loadUserByUsername

---

## Pokretanje aplikacije

### Preduslovi

- Java 21
- Maven 3.x
- MySQL 8.x
- Node.js 20.x
- Angular CLI 21.x

### Backend

1. Kopirati `backend/src/main/resources/application.properties.example` u `application.properties`
2. Uneti MySQL kredencijale
3. Pokrenuti:

```bash
cd backend
mvn spring-boot:run
```

Backend je dostupan na: `http://localhost:9000`

### Frontend

```bash
cd frontend
npm install
ng serve
```

Frontend je dostupan na: `http://localhost:4200`

### Kreiranje admin naloga

```sql
INSERT INTO korisnik (ime, prezime, email, lozinka, uloga)
VALUES ('Admin', 'Admin', 'admin@koncert.com',
'$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhu', 'ADMIN');
```

Lozinka za ovaj hash je: `password`

---

## Pokretanje testova

```bash
cd backend
mvn test
```

---

## Struktura projekta

```
koncert-app-repo/
├── backend/                        <- Spring Boot aplikacija
│   ├── src/
│   │   ├── main/java/rs/fon/koncert_app/
│   │   │   ├── config/             <- Security, CORS, Exception handler
│   │   │   ├── controller/         <- REST API kontroleri
│   │   │   ├── dto/                <- Data Transfer Objects
│   │   │   ├── entity/             <- Domenske klase
│   │   │   ├── repository/         <- Spring Data JPA repozitorijumi
│   │   │   ├── security/           <- JWT filter i servis
│   │   │   └── service/            <- Sistemske operacije
│   │   └── test/                   <- JUnit testovi
│   └── pom.xml
├── frontend/                       <- Angular aplikacija
│   ├── src/app/
│   │   ├── components/             <- Angular komponente
│   │   ├── models/                 <- TypeScript interfejsi
│   │   ├── services/               <- HTTP servisi
│   │   ├── guards/                 <- Auth guard
│   │   └── interceptors/           <- JWT interceptor
│   └── package.json
└── README.md
```

---

## Git verzionisanje

| Tag | Opis |
|---|---|
| `v1.0` | Prva stabilna verzija sa JUnit testovima |
| `v1.1` | Nova verzija sa JavaDoc dokumentacijom |

---

## Autor

Igor Bakovic — Fakultet organizacionih nauka, Beograd
