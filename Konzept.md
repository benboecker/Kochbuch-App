# Omas Kochbuch

Mit der App *Omas Kochbuch* können Nutzer ihre persönlichen Lieblingsrezepte speichern und immer wieder nachkochen. Die App arbeitet offline und ist nicht zum Austausch von Rezepten gedacht, sondern vielmehr, um Kochwissen festzuhalten, welches nicht in Kochbüchern oder Online-Foren steht. So sollen sich auf einfache Art und Weise Rezepte abspeichern und bei Bedarf ergänzen lassen. Als besonderes Feature, kann man sich die Zutaten direkt als Einkaufsliste speichern oder an eine andere Person verschicken.

Im diesem Konzept werden die Anforderungen an die App beschrieben. Also zum einen, welche Informationen ein Rezept ausmachen und in der App dargestellt werden können und zum anderen, wie der Benutzer mit der App interagieren kann und wie die Benutzeroberfläche gestaltet werden sollte.

## Anforderungen

Die grundsätzliche Anforderung an die App lässt sich in einer simplen Aussage zusammenfassen:

> Erfassen und Anzeigen von Rezepten mit benötigten Zutaten und Anweisungsschritten auf möglichst einfache und benutzerfreundliche Art.

Was das genau bedeutet lässt sich im Endeffekt auf zwei Kategorien aufteilen:

- Rezepte und deren Bestandteile
- Optimierung der Benutzeroberfläche der App

### Anforderungen an die Benutzeroberfläche
Die Rezepte sollen in einer Liste angezeigt werden, auf Geräten mit größerem Bildschirm eventuell auch als Kacheln. In der Liste lassen sich Rezepte löschen (mit Sicherheitsnachfrage) und zum Bearbeiten öffnen. Durch Antippen eines Rezepts wird dieses in einer Detailansicht dargestellt.

Das Erfassen eines Rezeptes läuft in mehreren Schritten ab.



Die genauen Bestandteile eines Rezeptes sind im folgenden Abschnitt genau beschrieben.







Zubereitung 
Im Grunde wird aber nur die Eingabe von einem oder mehreren Textabschnitten benötigt. Die können dann als 

### Rezepte
Ein Rezept besteht in Omas Kochbuch aus den folgenden Bausteinen:

#### Name
Das Rezept muss einen Namen haben, welcher beliebig lang sein kann. Dieser wird in der Übersichtsliste angezeigt und dient zum direkten Erkennen des Rezepts. Es können mehrere Rezepte denselben Namen haben, diese werden dann durch andere Merkmale unterschieden (Zutaten, Zubereitung, etc.).

#### Zubereitungsdauer
Die Gesamtdauer der Zubereitung in Minuten und zusätzlich, falls angegeben, die Dauer der einzelnen Zubereitungsschritte.

#### Personenanzahl
Die Anzahl der Personen für die das fertige Gericht reicht, wenn man die angegebenen Zutatenmengen verwendet.

#### Fotos
Ein oder mehrere Bilder des fertig gekochten Gerichts. Die Angabe ist optional.

#### Liste der Zutaten
Eine Liste der benötigten Zutaten mit Mengenangabe. Zusätzlich lassen sich Zutaten als *Geheimzutat* kennzeichnen, diese werden dann besonders hervorgehoben.

#### Zubereitung / Anweisungsschritte
Die Zubereitung eines Gerichts kann auf verschiedene Arten angegeben werden, welche sich von Rezept zu Rezept unterscheiden. Manche Rezepte benötigen besondere Vorbereitung, andere sind so einfach, dass keine besondere Aufteilung auf mehrere Arbeitsschritte notwendig ist. Zu jedem Schritt kann optional auch eine Zeitdauer in Minuten angegeben werden. Außerdem kann ein Zubereitungsschritt auch als Geheimtipp gekennzeichnet werden, um ihn im Rezept besonders hervorzuheben.

#### Benötigte Ausstattung
Eine optionale Liste an benötigter Hardware wie Töpfe, Stampfer, etc. So kann man bereits im Vorfeld sehen, welche Küchenausstattung eventuell noch fehlt.

#### Besondere Kennzeichnungen
Ein Rezept kann besondere Kennzeichnungen erhalten wie zum Beispiel *kalt* / *warm* oder *vegetarisch* / *vegan*. Diese werden auch in der Übersichtsliste angezeigt und bieten so einen schnellen Indikator bei der Auswahl.

#### Lieblingsrezept
Ein Rezept lässt sich als Lieblingsrezept kennzeichnen, dies entspricht einer Favoriten-Funktion um die gekennzeichneten Rezepte gesondert darzustellen und schneller wiederzufinden.

### Zutaten
Die App enthält eine Standard-Datenbank mit vielen gebräuchlichen Lebensmitteln. Diese werden bei der Eingabe der Zutatenliste eines Rezepts benötigt und erleichtern somit das Anlegen eines neuen Rezepts.

#### Autovervollständigung und Zutatenvorschläge
Die Angabe der Zutaten wird durch eine Autovervollständigung der Eingabe unterstützt. Dafür ist in der App eine umfangreiche Liste mit Lebensmitteln hinterlegt, welche gefiltert angezeigt werden, sobald man eine Zutat zu einem Rezept hinzufügen möchte. Wenn der Benutzer beispielsweise *Tomaten* als Zutat hinzufügen möchte und `To` eingibt, so werden ihm bereits sowohl `Tofu`, als auch `Tomaten` als mögliche Eingabe vorgeschlagen.

#### Intelligente Mengeneinheiten
Die Zutaten sind an Mengeneinheiten gekoppelt, sodass die App bereits weiß, dass Milch meist in Millilitern und Hackfleisch in Gramm angegeben wird. Für größere Mengen, passen sich die Einheiten automatisch an, sodass aus `1500ml Milch` dann `1,5l Milch` werden. Die Zuordnung der Zutaten merkt die App sich allerdings erst nach und nach, da nicht für alle Lebensmittel eine Standardzuordnung zu Einheiten bekannt ist. Diese mit müsste vom Entwickler manuell in der Datenbank gespeichert und mit ausgeliefert werden, was einen zu großen Aufwand darstellt.

## Gestaltung
