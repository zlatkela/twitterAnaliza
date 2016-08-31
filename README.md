# O projektu
Cilj ovog projekta je upoređivanje različitih klasifikatora za analizu sadržaja teksta,
konkretno analizu sentimenta twitter poruka(tvitova) na srpskom jeziku. Sentiment poruke može biti pozitivan ili negativan, 
što se određuje na osnovu smajlija u samom tvitu. Na osnovu ovoga može se zaključiti da su od interesa za ovaj projekat bili samo tvitovi
koji su imali smajli.
# Podaci
Skup podataka koji se koristio u ovom projektu prikupljan je sa Twitter Streaming APi-ja, u periodu od 25.08.2016. do 30.08.2016. tj. 
tačno 120 sati. Za konekciju na twitter API korišćena je biblioteka j4twitter[1]. Podaci su skupljani po dva kriterijuma, i to da su tvitovi 
na srpskom jeziku i da sadrže srećan ili tužan smajli. Na osnovu smajlija, svaki tvit je klasifikovan kao pozitivan ili negativan. 
Za skup podataka koji je od interesa za aplikaciju uzeto je po 400 tvitova sa pozitivnim i negativnim sentimentom
i nad njima je vršena analiza. Nad podacima je primenjen StringToWordVector filter, koji slu\i da originalan tekst prevede u reči 
(zavisno od tokena), na osnovu kojih se kasnije vrši klasifikacija.
# Klasifikacione metode
U projektu su korišćene tri različite metode: Naive Bayes, J48 i Support Vector Machines (SVM).
Ove metode implementirane su u Weka[2] biblioteci, koja se koristi u ovom projektu.
## Naive Bayes
Naive Bayes metoda zasnovana je na Bajesovoj teoriji verovatnoće. Jedan od razloga zašto je ova metoda izabrana je taj što iako prosta, 
ova metoda je brza, sa dobrim rezultatima na problemima srednje veličine vezanim za klasifikaciju na osnovu teksta.[3]
Bayes metoda funkcioniše tako što posmatra tekst kao vreću reči, i na osnovu njih određuje verovatnoću pripadnosti teksta određenoj klasi[4].
## J48 
J48 metoda predstavlja stablo odlučivanja. Razlog odabira ove metode je njena mogućnost uspostavljanja korelacije između više reči.
Takođe, mogućnost vizuelizacije podataka je jedna od prednosti korišćenja ove metode.[3] 
## Support Vector Machines 
SVM metoda predtstavlja jednu od najuspešnijih metoda u klasifikaciji teksta. Njena najveća prednost je mogućnost da se fokusira na 
najrelevantnije podatke u cilju klasifikovanja u odgovarajuću klasu[3]. Takođe, ova metoda je dobra za retke skupove podataka.
