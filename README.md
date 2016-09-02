# O projektu
Cilj ovog projekta je upoređivanje različitih klasifikatora za analizu sadržaja teksta,
konkretno analizu sentimenta twitter poruka(tvitova) na srpskom jeziku. Sentiment tvita može biti pozitivan ili negativan, 
što se određuje na osnovu smajlija u samom tvitu. Na osnovu ovoga može se zaključiti da su od interesa za ovaj projekat bili samo tvitovi koji sadrže smajli.
# Podaci
Skup podataka koji se koristio u ovom projektu prikupljan je sa [Twitter Streaming APi-ja](https://dev.twitter.com/streaming/overview), u periodu od 25.08.2016. do 30.08.2016. tj. 
tačno 120 sati. Podaci su skupljani po dva kriterijuma, i to da su tvitovi 
na srpskom jeziku i da sadrže srećan ili tužan smajli. Na osnovu smajlija, svaki tvit je klasifikovan kao pozitivan ili negativan. 
Za skup podataka koji je od interesa za aplikaciju uzeto je po 400 tvitova sa pozitivnim i negativnim sentimentom
i nad njima je vršena analiza. Takođe, izvršena je normalizacija nad delovima tvita koji predstavljaju korisničko ime ili link. Umesto tih reči korišćene su reči USERNAME i URL. Nad podacima je primenjen StringToWordVector filter, koji služi da originalan tekst prevede u reči (zavisno od tokena), na osnovu kojih se kasnije vrši klasifikacija. Prikaz dela arff fajla(tip fajla koji koristi [Weka](http://www.cs.waikato.ac.nz/ml/weka/) biblioteka) može se videti ovde:
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
# Klasifikacione metode
U projektu su korišćene tri različite metode: Naive Bayes, J48 i Support Vector Machines (SVM).
## Naive Bayes
Naive Bayes metoda zasnovana je na Bajesovoj teoriji verovatnoće. Jedan od razloga zašto je ova metoda izabrana je taj što iako prosta, ova metoda je brza, sa dobrim rezultatima na problemima srednje veličine vezanim za klasifikaciju na osnovu teksta.[1]
Bayes metoda funkcioniše tako što posmatra tekst kao vreću reči, i na osnovu njih određuje verovatnoću pripadnosti teksta određenoj klasi[2].
## J48 
J48 metoda predstavlja stablo odlučivanja. Razlog odabira ove metode je njena mogućnost uspostavljanja korelacije između više reči.
Takođe, mogućnost vizuelizacije podataka je jedna od prednosti korišćenja ove metode.[1] 
## Support Vector Machines 
SVM metoda predtstavlja jednu od najuspešnijih metoda u klasifikaciji teksta. Njena najveća prednost je mogućnost da se fokusira na 
najrelevantnije podatke u cilju klasifikovanja u odgovarajuću klasu[1]. Takođe, ova metoda je dobra za retke skupove podataka.
#Tehnička realizacija projekta 
Implementacija projekta rađena je u [Java](https://www.java.com/en/) programskom jeziku. Za potrebe prikupljanja podataka i povezivanja sa pomenutim Twitter API-jem, korišćena je biblioteka [twitter4j](http://twitter4j.org/en/index.html), koja, iako nezvanična, značajno olakašava komunikaciju i konfigurisanje parametara za Twitter streaming API. Što se tiče algoritama za mašinsko učenje, oni su implementirani u [Weka](http://www.cs.waikato.ac.nz/ml/weka/) biblioteci.
# Analiza
Za analizu rezultata klasifikatora koristi se više veličina koje opisuju rezultate same klasifikacije. 
## Tačnost
Tačnost (Accuracy) predstavlja procenat slučajeva (instanci) koji su uspešno (korektno) klasifikovani.[3] Računa se kao količnik sume svih korektno klasifikovanih instanci i ukupnog broja instanci. Jedan od preduslova za korišćenje ove mere je ujednačena količina podataka svih klasa u skupu podataka. Kako korišćeni skup podataka sadrži po 400 instanci za tvitove sa pozitivnim i negativnim sentimentom, ova mera uspešnosti klasifikatora jeste merodavna.
## Preciznost, odziv, F-mera
Preciznost predstavlja količnik uspešno klasifikovanih instanci jedne klase i ukupan broj klasifikovanih instanci te klase.[3] 
Odziv predstavlja količnik uspešno klasifikovanih instanci jedne klase i ukupan broj instaci koje stavrno pripadaju toj klasi.[3] Ove dve mere su recipročne, tj. ukoliko se jedna poveća, druga se mora smanjiti. Upravo iz tog razloga uvedena je F mera, koja predstavlja kombinaciju prethodne dve i omogućuje jednostavnije poređenje dva ili više algoritama[3]. 
## Površina ispod ROC krive
Ova veličina meri diskriminacionu moć klasfikatora tj. sposobnost da razlikuje instance koje pripadaju različitim klasama.[3] Koristi se kao mera uspešnosti binarnih klasifikatora, što je ovde slučaj, te je to jedan od razloga zašto je uzeta u razmatranje.
## Upoređivanje rezultata
U sledećoj tabeli dat je uporedni prikaz rezultata navedenih mera za korišćene klasifikatore.

|Klasifikator|Tačnost|F-mera|Površina ispod ROC krive|
|------------|-------|------|------------------------|
|Naive Bayes|70.125%|0.696|0.771|
|J48|76.75%|0.773|0.827|
|SVM|99.875%|0.999|0.999|

Iz priloženog se može videti da je odnos svih mera isti, tj. da je po svim merama redosled uspešnosti algoritama isti. Najslabije rezultate ima Naive Bayes klasifikator, koji ima procenat uspešnosti od 70.125%. Ipak, površina ispod ROC krive iznosi 0.771 što ovaj klasifikator svrstava u grupu prihvatljivih. Takođe, treba uzeti u obzir i da je skup podataka mali, i da Naive Bayes alogritam daje najbolje rezultate na problemima srednje veličine, i da je za očekivati da bi na većem uzorku ovaj algoritam imao bolje rezultate.

Što se J48 algoritma tiče, rezultati su bolji nego kod Naive Bayes metode, ali i ovde je procenat uspešnosti algoritma manji od 80%, tačnije iznosi 76.75%. Najveća razlika između ova dva kriterijuma je u F-meri, što znači da ovaj J48 metoda ima bolju kombinaciju preciznosti i odziva nego Naive Bayes. Kako se površina ispod ROC krive nalazi između vrednosti 0.8 i 0.9, ovaj algoritam se svrstava u jako dobre klasifikatore.

Kod Support Vector Machines alogritma, procenat tačnosti je daleko bolji u odnosu na prva dva algoritma, i iznosi 99,875%, što znači da je od 800 instanci algoritam samo jednu pogrešno klasifikovao. Ovakvi rezultati i jesu očekivani s obzirom na to sposobnost ovog algoritma da najveću težinu dodeli najrelevantnijim podacima. Ipak, ovde treba uzeti u obzir i mogućnost problema overfitting-a, situacije u kojoj algoritam pokazuje sjajne rezultate nad podacima nad kojima je i treniran, ali na drugačijem skupu podataka procenat uspešnosti klasifikatora značajno pada. Po prikazanim rezultatima može se zaključiti da je najbolji klasifikator upravo SVM, uz napomenu da je potrebno testirati algoritme na više različitih grupa skupova podataka, što u ovom radu nije bilo moguće zbog nedostatka samih podataka.

Detaljni rezultati za svaki od klasifikatora mogu se naći u fajlovima [BayesResults.txt](https://github.com/zlatkela/twitterAnaliza/blob/master/BayesResults.txt), [J48Results.txt](https://github.com/zlatkela/twitterAnaliza/blob/master/J48Results.txt),[SupportVectorMachineResults.txt](https://github.com/zlatkela/twitterAnaliza/blob/master/SupportVectorMachineResults.txt)
#Reference
[1] Jose Maria Gomez blog, link: http://jmgomezhidalgo.blogspot.rs/2013/05/language-identification-as-text.html, datum pristupa: 2.9.2016.
[2] Stanford materijal, link: https://web.stanford.edu/class/cs124/lec/naivebayes.pdf, datum pristupa: 2.9.2016.
[3] FON materijal, laboratorija za veštačku inteligenciju, link: http://ai.fon.bg.ac.rs/wp-content/uploads/2015/04/Klasifikacija-osnove-2015.pdf, datum pristupa: 2.9.2016.
Dodatna literatura:
[4] Rad na temu analize sentimenta tviter poruka, link: http://s3.eddieoz.com/docs/sentiment_analysis/Twitter_Sentiment_Classification_using_Distant_Supervision.pdf, datum pristupa 2.9.2016.
[5] FON materijal, laboratorija za veštačku inteligenciju, link:http://ai.fon.bg.ac.rs/wp-content/uploads/2015/04/WekaTextMining2015.zip, datum pristupa 2.9.2016.


