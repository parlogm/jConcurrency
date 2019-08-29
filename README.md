**Java Concurrency**
_Universitatea Titu Maiorescu - Proiect licenta_

Student : Parlog Mihai

* Bibliografia - pot sa caut to felul de articole si sa le notez eu pe acelea care mi se par relevante pentru subiectul respectiv ( si care ma ajuta bineinteles );
* Arhitectura - este ok cum am proiectat pana acum, trebuie doar sa am grija sa am si metodele documentate ( scop, folosire );
* Structura de date pentru procesare :
    * Documente cu volum mare de date ( de preferat sa fie liste cu persoane cu tot felul de date legate de persoanele respective : date personale, date de angajare, eventual blob-uri cu certificate / poze atasate - aici trebuie sa gandesc eu mai mult structura de date ca sa fie ceva ce dureaza ca timp de procesare );
    * Eventual poate fi si ceva pe compresie de date ( atat date ca informatii cat si poze/filme );
    * Eventual pot fi si procesari pe string-uri / siruri lungi de caractere ( cand se pune sau nu lock pe string );
* Time line : prin ianuarie - februarie cand e si sesiunea ar trebui sa am ceva prezentabil ( concret );
* Numar pagini / font / model / incadrare / forma bibliografie imi va trimite pe mail;
* Studiul de caz :
    * pentru partea teoretica cat si pentru studiul de caz trebuie o pondere egala;
        * adica la partea teoretica trebuie sa am bine documentat codul si ideile din spatele concurentei ( eventual pot sa ma inspir din cateva articole legate de procesarea in concurenta );
        * pentru studiul de caz trebuie sa caut eu ceva fezabil ( un exemplu ar fi procesare de date ale clientilor unei banci - cum ar fi reconcilierea cu alte banci - procesare excel-uri cu tranzactii );
    * posibil scenariu : un utilizator incarca un fisier csv/excel cu date referitoare la clienti cu tranzactiile din ziua respectiva, datele urmand a fi procesate ulterior pentru a se stabili balanta corecta a conturilor clientilor ( e doar un exemplu, din asta pot sa ma inspir );
* Nu se stie 100% daca se da si examen, asta voi afla la anul;
* se prezinta lucrarea ( trebuie un pptx pregatit si eventual un mic demo );
* trebuie si cd atasat cu codul si aplicatia ( eventual ar trebui un container de docker sa se poata porni usor aplicatia fara a necesita multe configurari );


Links :

- http://localhost:4200/#/login Live development app
- http://localhost:9119/redoc/index.html	This page has ReDoc documentation for the rest services available
- http://localhost:9119/swagger-ui.html	This page has Swagger UI for the rest services available

Google Cloud Platform :

- https://java-concurrency.appspot.com/ Main page
- https://java-concurrency.appspot.com/redoc/index.html ReDoc
- https://java-concurrency.appspot.com/swagger-ui.html Swagger

