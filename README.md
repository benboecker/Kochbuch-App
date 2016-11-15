# Kochbuch 2.0
Mit der App *Kochbuch 2.0* können Nutzer ihre persönlichen Lieblingsrezepte speichern und immer wieder nachkochen. Die App arbeitet offline und ist nicht zum direkten Austausch von Rezepten gedacht, sondern vielmehr, um für sich persönlich Kochwissen festzuhalten, welches nicht in Büchern oder Online-Foren steht. So sollen sich auf einfache Art und Weise Rezepte abspeichern und bei Bedarf ergänzen lassen. Als besonderes Feature, kann man sich die Zutaten direkt als Einkaufsliste speichern oder als Textnachricht an eine andere Person verschicken.

Im diesem Konzept werden die Anforderungen an die App beschrieben. Zum einen, welche Informationen ein Rezept ausmachen und in der App dargestellt werden können und zum anderen, wie der Benutzer mit der App interagieren kann und wie die Benutzeroberfläche gestaltet werden sollte.

## Anforderungen

Die grundsätzliche Anforderung an die App lässt sich in einer simplen Aussage tzusammenfassen: *Erfassen und Anzeigen von Rezepten mit benötigten Zutaten und Anweisungsschritten auf möglichst einfache und benutzerfreundliche Art.*

### Rezepte und deren Bestandteile
Rezepte sind unterschiedlich kompliziert, es gibt einfache Rezepte mit wenigen Zutaten und Zubereitungsschritten und wesentlich aufwändigere. Trotzdem muss es möglich sein jedes Rezept konzeptuell zu beschreiben, welche Daten dazu benötigt werden, ist im Abschnitt *Anforderungen an die Daten* genauer beschrieben.

### Gestaltung der Benutzeroberfläche der App
Die Benutzeroberfläche soll so gestaltet werden, dass sowohl simple als auch aufwändige Rezepte so einfach wie möglich eingegeben werden können. Außerdem sollte die Führung des Benutzers durch die App nicht unnötig kompliziert sein, sprich möglichst wenige Ansichten und eindeutige Beschriftungen, Icons und Aktionen.

### Anforderungen an die Daten
Die App soll Rezepte speichern und darstellen, dazu muss zunächst definiert werden, was ein Rezept im Kontext dieser App beinhaltet.

#### Rezept
Ein Rezept besteht in *Omas Kochbuch* aus den folgenden Bausteinen:

##### Name
Das Rezept muss einen Namen haben, welcher beliebig lang sein kann. Dieser wird in der Übersichtsliste angezeigt und dient zum direkten Erkennen des Rezepts. Es können mehrere Rezepte denselben Namen haben, diese werden dann durch andere Merkmale unterschieden (Zutaten, Zubereitung, etc.).

##### Zubereitungsdauer
Die Gesamtdauer der Zubereitung in Minuten und zusätzlich, falls angegeben, die Dauer der einzelnen Zubereitungsschritte.

##### Personenanzahl
Die Anzahl der Personen für die das fertige Gericht reicht, wenn man die angegebenen Zutatenmengen verwendet.

##### Fotos
Ein oder mehrere Bilder des fertig gekochten Gerichts. Die Angabe ist optional.

##### Liste der Zutaten
Eine Liste der benötigten Zutaten mit Mengenangabe. Zusätzlich lassen sich Zutaten als *Geheimzutat* kennzeichnen, diese werden dann besonders hervorgehoben.

##### Zubereitung / Anweisungsschritte
Die Zubereitung eines Gerichts kann auf verschiedene Arten angegeben werden, welche sich von Rezept zu Rezept unterscheiden. Manche Rezepte benötigen besondere Vorbereitung, andere sind so einfach, dass keine besondere Aufteilung auf mehrere Arbeitsschritte notwendig ist. Zu jedem Schritt kann optional auch eine Zeitdauer in Minuten angegeben werden. Außerdem kann ein Zubereitungsschritt auch als Geheimtipp gekennzeichnet werden, um ihn im Rezept besonders hervorzuheben.

##### Benötigte Ausstattung
Eine optionale Liste an benötigter Hardware wie Töpfe, Stampfer, etc. So kann man bereits im Vorfeld sehen, welche Küchenausstattung eventuell noch fehlt.

##### Besondere Kennzeichnungen
Ein Rezept kann besondere Kennzeichnungen erhalten wie zum Beispiel *kalt* / *warm* oder *vegetarisch* / *vegan*. Diese werden auch in der Übersichtsliste angezeigt und bieten so einen schnellen Indikator bei der Auswahl.

##### Lieblingsrezept
Ein Rezept lässt sich als Lieblingsrezept kennzeichnen, dies entspricht einer Favoriten-Funktion um die gekennzeichneten Rezepte gesondert darzustellen und schneller wiederzufinden.

#### Zutaten
Die App enthält eine Standard-Datenbank mit vielen gebräuchlichen Lebensmitteln. Diese werden bei der Eingabe der Zutatenliste eines Rezepts benötigt und erleichtern somit das Anlegen eines neuen Rezepts. Die Lebensmittel werden während der Entwicklung anhand einer bereits existierenden Liste in eine Datenbank gespeichert und mit der fertigen App ausgeliefert.

##### Autovervollständigung und Zutatenvorschläge
Die Angabe der Zutaten wird durch eine Autovervollständigung der Eingabe unterstützt. Dafür ist in der App eine umfangreiche Liste mit Lebensmitteln hinterlegt, welche gefiltert angezeigt werden, sobald man eine Zutat zu einem Rezept hinzufügen möchte. Wenn der Benutzer beispielsweise *Tomaten* als Zutat hinzufügen möchte und `To` eingibt, so werden ihm bereits sowohl `Tofu`, als auch `Tomaten` als mögliche Eingabe vorgeschlagen.

##### Intelligente Mengeneinheiten
Die Zutaten sind an Mengeneinheiten gekoppelt, sodass die App bereits weiß, dass Milch meist in Millilitern und Hackfleisch in Gramm angegeben wird. Für größere Mengen, passen sich die Einheiten automatisch an, sodass aus `1500ml Milch` dann `1,5l Milch` werden. Die Zuordnung der Zutaten merkt die App sich allerdings erst nach und nach, da nicht für alle Lebensmittel eine Standardzuordnung zu Einheiten bekannt ist. Diese mit müsste vom Entwickler manuell in der Datenbank gespeichert und mit ausgeliefert werden, was einen zu großen Aufwand darstellt.

### Anforderungen an die Benutzeroberfläche
Die Gestaltung der Benutzeroberfläche soll so weit es geht mit Standard-Controls des Android-UI-Frameworks realisiert werden. Außerdem wird die App aus möglichst wenig unterschiedlichen Ansichten bestehen um die Komplexität so gering wie möglich zu halten. Prinzipiell gibt es 3 Hauptansichten:

- Liste der Rezepte
- Rezept-Detailansicht
- Rezept-Bearbeitungsansicht

Eventuell sollte die Zutatenliste eines Rezepts ebenfalls in einer eigenen Ansicht dargestellt werden, falls ein Rezept zu viele Zutaten enthält.

#### Liste der Rezepte
Die Rezepte sollen in einer Liste angezeigt werden, auf Geräten mit größerem Bildschirm eventuell auch als Kacheln. In der Liste lassen sich Rezepte löschen (mit Sicherheitsnachfrage) und zum Bearbeiten öffnen. Außer dem Namen des Rezepts und eventueller Besonderheiten (*warmes Gericht*, *vegetarisch*, siehe Kapitel *Anforderungen an die Daten*) soll in der Liste der benötigte Zeitaufwand und die Standard-Personenanzahl stehen. Wenn für ein Rezept ein Foto hinterlegt ist, so wird dieses auch in der Liste angezeigt.

Durch Antippen eines Rezepts wird dieses in einer Detailansicht dargestellt.

#### Rezept-Detailansicht
Die Detailansicht eines Rezepts zeigt die zugehörigen Informationen in einer bestimmten Reihenfolge an:

1. Foto
2. Name des Rezepts
3. Personenzahl / Dauer
4. Besonderheiten
5. Zutatenliste / Benötigte Ausstattung
6. Zubereitungsschritte

Ganz oben ist – falls vorhanden – ein Foto des zubereiteten Gerichts abgebildet. Darunter erscheinen die generellen Informationen zu Name und Art des Gerichts, darunter dann die Zutaten und Zubereitung. Die Personenanzahl lässt sich anpassen wodurch sich die benötigten Zutatenmengen automatisch ändern. Ebenfalls ist in dieser Ansicht ein Button zu finden, welcher den Export einer Einkaufsliste vorbereitet. Diese lässt sich dann als Notiz speichern oder an Freunde versenden.

Die dargestellten Informationen sind nicht editierbar, ein zusätzlicher Button in der Navigationsleiste versetzt das Rezept in den Bearbeitungsmodus.

#### Rezept-Bearbeitungsansicht
In der Bearbeitungsansicht werden alle Information editierbar. Der Name des Rezepts wird zu einem Textfeld, die Besonderheiten wie *warm* / *kalt*, *vegetarisch* / *vegan* lassen sich mit einem Pop-up festlegen, genau wie die Anzahl der Personen.

Wählt man die Zutatenliste aus, so wird man auf eine besondere Listenansicht aller Zutaten geleitet, siehe *Zutatenliste* weiter unten.

Auch die einzelnen Zubereitungsschritte sind in Listenform angegeben, pro Zubereitungsschritt eine Listenzeile. Am Ende der Liste befindet sich ein Button mit dem sich ein neuer Eintrag zur Liste hinzufügen lässt. Es öffnet sich ein Popup mit einem Textfeld über das man den neuen Eintrag hinzufügen kann.

#### Zutatenliste
Die Zutatenliste wird zur besseren Übersicht in einer gesonderten Ansicht dargestellt.

Die einzelnen Zutaten sowie die benötigte Ausstattung lässt sich mittels einem Button am Ende der Liste erweitern, Einträge lassen sich durch Gedrückthalten entfernen. Eine neue Zutat wird über ein Pop-up hinzugefügt, dort befindet sich ein Textfeld mit Autovervollständigung zur Eingabe und außerdem die Möglichkeit die benötigte Menge einzugeben.

## Gestaltung

In diesem Kapitel werden einige Mockups für die Benutzeroberfläche präsentiert, sowie einige grundsätzliche Gedanken zur Gestaltung.

### Allgemeine Gedanken zur Gestaltung

- Systemfont (Roboto)
- Farben auf 2 Textfarben und eine Akzentfarbe beschränken
- Optionale Fotos zur Aufhübschung

### Rezepteliste

![Rezepte-Liste](https://github.com/benboecker/Kochbuch-App/blob/master/concept-images/rezepte_liste.jpg)

### Rezeptdetail 1

![Rezepte-Detail-1](https://github.com/benboecker/Kochbuch-App/blob/master/concept-images/rezepte_detail_1.jpg)

### Rezeptdetail 2

![Rezepte-Detail-2](https://github.com/benboecker/Kochbuch-App/blob/master/concept-images/rezepte_detail_2.jpg)

### Zutatenliste

![Zutaten-Liste](https://github.com/benboecker/Kochbuch-App/blob/master/concept-images/zutaten_liste.jpg)


