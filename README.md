htwg-scala-seed
=========================

This is a seed project to create a basic scala project as used in the
class Software Engineering at the University of Applied Science HTWG Konstanz

* Has a folder structure prepared for a MVC-style application
* Has *ScalaTest* and *ScalaMock* at their latest versions as dependencies.
* Has *sbt-scalariform*, *sbt-scapegoat*, *scalastyle-sbt-plugin* and *sbt-scoverage* sbt plugins
* Has .gitignore defaults

Spielregeln
=========================

1. Es wird eine **Anfangskarte** bestimmt. Der Spieler der diese nach dem Austeilen hält, **fängt an**.
2. Spieler kann nun eine **einzelne** oder **mehrere Karten (gleicher Wertigkeit!)** ausspielen.
3. Die anderen Spieler müssen mit **derselben Anzahl** und **höherwertigen** Karten stechen oder passen.
4. Der Spieler der den **Stich gewonnen** hat, legt nun **als erster** ab. -> *Wdh. ab Schritt 2*
5. Das Spiel ist zuende wenn **nur noch ein Spieler** Karten auf der Hand hat (siehe darunter).


>**Arschloch** ist der Spieler der
>  - ein Ass als letzte Karte ablegt (Immer sofort Arschloch!)
>  - als letztes Karten auf der Hand hat
>  - beschummelt

>**Vizearschloch** ist der Spieler der
>  - legal, direkt vor dem Arschloch die letzten Karten abgelegt hat

>**König** ist der Spieler der
>  - als erstes alle Karten von der Hand hat (Achtung: Er darf natürlich nicht mit einem Ass beendet haben)

>**Vizekönig** ist der Spieler der
>  - legal, direkt nach dem König die letzten Karten abgelegt hat

>Die **restlichen Spieler** werden zum **Pöbel** deklariert.

6. Die neue Runde beginnt nun das Arschloch

>Bevor die Runde beginnt müssen mind. Vize-Arschloch und Arschloch ihre **höchsten Karten** an Vize-König und König abgeben.
Das Arschloch muss hierbei seine Asse NICHT abgeben.

7.  Das Spiel beginnt erneut. -> *Wdh. ab Schritt 2*