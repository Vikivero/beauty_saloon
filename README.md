# Rejestracja w Beauty Saloon - Backend i Frontend

## Opis
Repozytorium zawiera kod źródłowy prostego systemu do rezerwacji wizyt w salonie urody. Backend aplikacji - w języku Java przy użyciu frameworka Spring Boot, wykorzystując bazę danych MongoDB. Frontend korzysta z biblioteki Bootstrap i języka JavaScript w NodeJs. Aplikacja udostępnia REST API do komunikacji między backendem a frontendem.

## Funkcjonalności
- Rejestracja wizyty użytkownika w systemie
- Wybór dostępnych dni z kalendarza
- Wybór specjalisty
- Rezerwacja wizyty przez wpisanie danych użytkownika
- Potwierdzenie rezerwacji i otrzymanie numeru wizyty
- Sprawdzenie daty wizyty za pomocą numeru
- Opcjonalne odwołanie wizyty

## Technologie
- Backend: Spring Boot, MongoDB
- Frontend: Nodejs, Bootstrap, Pug, JavaScript

## Uruchomienie
Aby uruchomić aplikację, należy postępować zgodnie z instrukcjami dla backendu i frontendu - 

### Backend
1. Przejdź do katalogu `backend`
2. Otworz aplikacje w IDE (Intellij Idea)
3. Uruchom aplikacje
4. Backend udostepnia REST API pod adresem http://localhost:8080/

### Frontend
1. W terminalu przejdź do katalogu `frontend`
2. odpal `npm install`
3. Uruchom aplikację - npm run dev
4. Otworz stronę w przegladarce http://localhost:3000/
