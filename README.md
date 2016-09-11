# O projektu
Cilj ovog projekta je upoređivanje performansi različitih klasifikatora u konktekstu analize teksta, konkretno za klasifikaciju sentimenta poruka sa Twitter servisa (tvitova) na srpskom jeziku. Sentiment tvita može biti pozitivan ili negativan, na čije postojanje ukazuje postojanje emotikona u samom tvitu. Na osnovu ovoga može se zaključiti da su od interesa za ovaj projekat bili samo tvitovi koji sadrže emotikone.

# Podaci
Skup podataka koji se koristio u ovom projektu prikupljan je sa [Twitter Streaming APi-ja](https://dev.twitter.com/streaming/overview), u periodu od 25.08.2016. do 30.08.2016. tj. tačno 120 sati. Podaci su skupljani po dva kriterijuma: da su tvitovi 
na srpskom jeziku i da sadrže srećan (":)", ":-)", ":D", ":-D") ili tužan emotikon (":(", ":-("). Na osnovu emotikona, svaki tvit je klasifikovan kao pozitivan ili negativan. Takođe, izvršena je normalizacija nad delovima tvita koji predstavljaju korisničko ime ili link. Umesto tih reči korišćene su reči USERNAME i URL. Nad podacima je primenjen StringToWordVector filter, koji služi da originalan tekst prevede u reči (zavisno od tokena), na osnovu kojih se kasnije vrši klasifikacija. Prikaz dela [arff](http://www.cs.waikato.ac.nz/ml/weka/arff.html) fajla (tip fajla koji koristi [Weka](http://www.cs.waikato.ac.nz/ml/weka/) biblioteka) može se videti u Listingu 1.
```
@relation selectedData

@attribute tweet STRING
@attribute sentiment {positive, negative}

@data
'USERNAME Премијер би рекао \"олош\"  ', positive
'USERNAME што доаѓа прво љубовта или револуцијата ?  ', positive
'USERNAME Ја сум таму  (читај: најпаметен, најзгоден, најчист, нај нај) ', positive
'Али када питаш Сири да ти нађе најближу апотеку па нађе неку 4 км од тебе  ', positive
'USERNAME USERNAME Зато летите у Микију или Новаку  ', positive
'USERNAME значи ти си више за два плус прво, два плус друго  ', positive
'USERNAME јас? Пффф, тој вчера што дојде кај тебе е најголемиот инвалид  ', positive
'USERNAME во секој случај рано е за џин  ', positive
'Неделна ноќна прошетка која започнува со дегустација на смути за енергија  URL URL ', positive
'USERNAME Не е цинизиам Антонио, озбилна е понудата!  Не сакаме да бидеш незадоволен, не е тоа идејата зад промоцијата... Welcome. ', positive
'USERNAME да не знаш!? Ево, са\'ћу, да не знамо заједно.  ', positive
'USERNAME значи значи и на фб решиле да ги зачистат. Му прават овертинкинг на #вмРОБОТ.от забавно вкрај  ', positive
'Ракоплескање  ', positive
'USERNAME хахаха дај ти се молам, од сите овој ли? Па најглуп е, само личен вкус искажувам  ', positive
'USERNAME и јас  ', negative
'Ровно год назад  URL ', positive
'USERNAME фала фала  Демир Хисар ', positive
'USERNAME абе и во битола имает ама нашево со најмногу стаж е  ', positive
'USERNAME купи 50 евра и тргувај на берза со акции од мајкрософт  ', positive
'USERNAME Хвала на препоруци  Водио сам је у Топчидерски парк па је јахала и понија и великог коња  ', positive
'3 литра спирта  ', positive
```
Listing 1 - Prikaz dela normalizovanih podataka

Za skup podataka koji je od interesa za aplikaciju uzeto je po 400 tvitova sa pozitivnim i negativnim sentimentom i nad njima je vršena analiza. Podaci su podeljeni u 3 grupe: podaci za trening, podaci za validaciju i podaci za testiranje, gde prva grupa zauzima 60% svih podataka, a druge dve po 20%.

# Algoritmi za klasifikaciju

U projektu su korišćene tri različita algoritma: Naive Bayes, J48 i Support Vector Machines (SVM).

## Naive Bayes
Naive Bayes metoda zasnovana je na Bajesovoj teoriji verovatnoće. Jedan od razloga zašto je ova metoda izabrana je taj što iako prosta, ova metoda je brza, sa dobrim rezultatima na problemima srednje veličine vezanim za klasifikaciju na osnovu teksta.[1] Bayes metoda funkcioniše tako što posmatra tekst kao skup reči i na osnovu njih određuje verovatnoću pripadnosti teksta određenoj klasi [2].

## J48 
J48 metoda je implementacija stabla odlučivanja. Razlog odabira ove metode je njena mogućnost uspostavljanja korelacije između više reči. Takođe, mogućnost vizuelizacije podataka je jedna od prednosti korišćenja ove metode.[1] 

## Support Vector Machines
SVM metoda predtstavlja jednu od najuspešnijih metoda u klasifikaciji teksta. Njena najveća prednost je mogućnost da se fokusira na 
najrelevantnije podatke u cilju klasifikovanja u odgovarajuću klasu [1]. Takođe, ova metoda je dobra za retke skupove podataka.

#Tehnička realizacija projekta 
Implementacija projekta rađena je u [Java](https://www.java.com/en/) programskom jeziku. Za potrebe prikupljanja podataka i povezivanja sa pomenutim Twitter API-jem, korišćena je biblioteka [twitter4j](http://twitter4j.org/en/index.html), koja, iako nije zvanična biblioteka, značajno olakašava komunikaciju i konfigurisanje parametara za Twitter streaming API. Što se tiče algoritama za mašinsko učenje, oni su implementirani u [Weka](http://www.cs.waikato.ac.nz/ml/weka/) biblioteci.

# Analiza
Za analizu rezultata klasifikatora koristi se više veličina koje opisuju rezultate same klasifikacije.

## Validacija
Validacija modela vrši se metodom unakrsne validacije. Ova metoda funkcioniše tako što se skup podataka podeli na N jednakih delova, gde jedan deo predstavlja podatke za testiranje, a ostalih N-1 podatke za trening. Validacija se vrši za svaki od N delova i na kraju se računaju prosečni rezultati. U našem slučaju, skup podataka je deljen na 10 delova, a rezultati validacije dati su u sledećim fajlovima: [BayesValidation.txt](https://github.com/zlatkela/twitterAnaliza/blob/master/BayesValidation.txt), [J48Validation.txt](https://github.com/zlatkela/twitterAnaliza/blob/master/J48Validation.txt),[SupportVectorMachinesValidation.txt](https://github.com/zlatkela/twitterAnaliza/blob/master/SupportVectorMachineValidation.txt)

## Tačnost
Tačnost (Accuracy) predstavlja procenat slučajeva (instanci) koji su uspešno (korektno) klasifikovani.[3] Računa se kao količnik sume svih korektno klasifikovanih instanci i ukupnog broja instanci. Jedan od preduslova za korišćenje ove mere je ujednačena količina podataka svih klasa u skupu podataka. Kako korišćeni skup podataka sadrži po 400 instanci za tvitove sa pozitivnim i negativnim sentimentom, ova mera uspešnosti klasifikatora jeste merodavna.

## Preciznost, odziv, F-mera
Preciznost predstavlja količnik uspešno klasifikovanih instanci jedne klase i ukupan broj klasifikovanih instanci te klase.[3] Odziv predstavlja količnik uspešno klasifikovanih instanci jedne klase i ukupan broj instaci koje stavrno pripadaju toj klasi.[3] Ove dve mere su recipročne, tj. ukoliko se jedna poveća, druga se mora smanjiti. Upravo iz tog razloga uvedena je F mera, koja predstavlja kombinaciju prethodne dve i omogućuje jednostavnije poređenje dva ili više algoritama[3].

## Površina ispod ROC krive
Ova veličina meri diskriminacionu moć klasfikatora tj. sposobnost da razlikuje instance koje pripadaju različitim klasama.[3] Koristi se kao mera uspešnosti binarnih klasifikatora, što je ovde slučaj, te je to jedan od razloga zašto je uzeta u razmatranje.

## Upoređivanje rezultata
U sledećoj tabeli dat je uporedni prikaz rezultata navedenih mera za korišćene klasifikatore.

|Klasifikator|Tačnost|F-mera|Površina ispod ROC krive|
|------------|-------|------|------------------------|
|Naive Bayes|62.5%|0.643|0.665|
|J48|63.75%|0.681|0.635|
|SVM|60.625%|0.604|0.607|

Iz priloženog se može videti da su rezultati svih klasifikatora nezadovoljavajući. Nijedan pd klasifikatora nema veću uspešnost od 64%. Najslabije rezultate ima SVM klasifikator, koji ima procenat uspešnosti od 60,625%. I ostali parametri su u skladu sa tim, te ovaj algoritam nije pouzdan kao klasifikator tvitova. Jedna zanimljivost vezana za ovaj algoritam je da je pri testiranju sa  podacima iz skupa podataka za trenining imao procenat uspešnosti 99%, što pokazuje da je ovakav model sklon overfittingu, situaciji u kojoj algoritam pokazuje sjajne rezultate nad podacima nad kojima je i treniran, ali na drugačijem skupu podataka procenat uspešnosti klasifikatora značajno pada. Vredi napomenuti i da je prilikom validacije ovaj algoritam imao najslabije rezultate (tačnost 47.5%).

Naive Bayes klasifikator ima nešto veći procenat uspešnosti ali je i to na nezadovoljavajućem nivou. Ono što je zanimljivo je da ovaj algoritam ima najveću ROC vrednost, što pokazuje da je ovaj algoritam primenjiv na probleme binarne klasifikacije. Takođe, treba uzeti u obzir i da je skup podataka mali i da Naive Bayes alogritam daje najbolje rezultate na problemima srednje veličine, kao i da je za očekivati da bi na većem uzorku ovaj algoritam imao bolje rezultate.

Što se J48 algoritma tiče, rezultati su najbolji, ali i dalje na niskom nivou. Značajnija razlika između klasifikatora izražena je u F meri, što znači da ovaj algoritam ima bolju kombinaciju preciznosti i odziva od druga dva.

Po prikazanim rezultatima može se zaključiti da je najbolji klasifikator J48, ali da nijedan klasifikator nije dostigao nivo uspešnosti potreban za prelaznu ocenu. Najveći uzrok tome je mala količina podataka koju je bilo moguće prikupiti. U prilog ovome ide i činjenica da su rezultati pri testiranju bolji nego pri validaciji, a pošto je skup podatak za učenje pri validaciji manji, može se uspostaviti korelacija između ova dva.

Detaljni rezultati za svaki od klasifikatora mogu se naći u fajlovima [BayesResults.txt](https://github.com/zlatkela/twitterAnaliza/blob/master/BayesResults.txt), [J48Results.txt](https://github.com/zlatkela/twitterAnaliza/blob/master/J48Results.txt),[SupportVectorMachineResults.txt](https://github.com/zlatkela/twitterAnaliza/blob/master/SupportVectorMachinesResults.txt)

#Reference
[1] Jose Maria Gomez blog, link: http://jmgomezhidalgo.blogspot.rs/2013/05/language-identification-as-text.html, datum pristupa: 2.9.2016.
<br>
[2] Stanford materijal, link: https://web.stanford.edu/class/cs124/lec/naivebayes.pdf, datum pristupa: 2.9.2016.
<br>
[3] FON materijal, laboratorija za veštačku inteligenciju, link: http://ai.fon.bg.ac.rs/wp-content/uploads/2015/04/Klasifikacija-osnove-2015.pdf, datum pristupa: 2.9.2016.
<br>
Dodatna literatura:
<br>
[4] Rad na temu analize sentimenta tviter poruka, link: http://s3.eddieoz.com/docs/sentiment_analysis/Twitter_Sentiment_Classification_using_Distant_Supervision.pdf, datum pristupa 2.9.2016.
<br>
[5] FON materijal, laboratorija za veštačku inteligenciju, link:http://ai.fon.bg.ac.rs/wp-content/uploads/2015/04/WekaTextMining2015.zip, datum pristupa 2.9.2016.


