# masi-STP
MASI - Short-Term Pelicans

## Uruchomienie projektu springowego
### java sdk
Na początku należy upewnić się, że kompilator javy jest zainstalowany i ustawiona jest ścieżka do java home. Patrz
https://confluence.atlassian.com/doc/setting-the-java_home-variable-in-windows-8895.html
### zbudowanie projektu
Po pobraniu projektu z repozytorium należy w konsoli wejść do katalogu `masi-STP-backend` i wywołać komendę
```bash
./gradlew build (linux/osx)
```
na windowsie należy wywołać plik `gradlew.bat` z parametrem `build`
### uruchomienie projektu
Po wywołaniu powyższej komendy projekt uruchomić można komendą
```bash
./gradlew bootRun (linux/osx)
```
na windowsie należy wywołać plik `gradlew.bat` z parametrem `bootRun`

## Uruchomienie aplikacji Angular
* Instalacja najnowszego Node.js. Patrz https://nodejs.org/en/
* Po pobraniu projektu z repozytorium należy w konsoli wejść do katalogu `masi-STP-interface`.
* Należy wywołać komendę:
```bash
npm install
```
* Po zainstalowaniu wszystkich pakietów można uruchomić projekt za pomocą polecenia:
```bash
npm start
```
* Projekt uruchamia się pod adresem: localhost:4200
