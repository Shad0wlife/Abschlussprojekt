# Rotationsinvariante Erkennung von Objekten mit einer generalisierten Zirkulartranformation und einem Fuzzy-Pattern-Klassifikator

Das Projekt enthält 2 ImageJ-Plugins.
- Beim Plugin "RecognitionPlugin" handelt es sich um ein PlugInFilter, welches das aktuell in ImageJ geöffnete Bild klassifiziert.
- Beim Plugin "RecognitionPluginBulk" handelt es sich um ein PlugIn, welches erlaubt, einen ganzen Ordner mit Bildern zur Stapelverarbeitung zu öffnen.

Die Bedienung des Plugins geschieht folgendermaßen:
  Während beim PlugInFilter mithilfe von ImageJ zuerst das Zielbild geöffnet werden muss, wird der Benutzer beim Plugin zur Stapelverarbeitung
  aufgefordert, einen Ordner mit Testbildern, welche klassifiziert werden sollen zu öffnen.
  Dabei ist zu beachten, dass sich in dem Ordner **NUR** Bilddateien befinden. Auch sollte beachtet werden, dass sich im gewählten 
  Ordner nur Bilder befinden, die klassifiziert werden sollen, da keine weitere überprüfung der geöffneten Bilder durchgeführt wird.
  
  ![Das Fenster zur Wahl der Bilder sieht so aus.](https://github.com/Shad0wlife/Abschlussprojekt/blob/master/images/BulkSelection.JPG)
  
  Nach dem Öffnen der Testbilder wird dem Benutzer die Option angeboten, die Bilddaten durch eine Erosion oder Dilation mit wählbarer 
  Größe der Maske vorzuverarbeiten.
  Sollte sich der Benutzer zu einer Vorverarbeitung entscheiden, kann er beliebig viele einzeln Konfigurierbare Erosionen und Dilationen 
  durchführen lassen.
  
  ![Das Fenster zur Wahl der Vorverarbeitung sieht so aus.](https://github.com/Shad0wlife/Abschlussprojekt/blob/master/images/Vorverarbeitung.JPG)
  
  Nach der Konfiguration der Vorverarbeitung öffnet sich die primäre Grafikoberfläche des Programms, welche der Parametrierung des 
  Klassifikators sowie der Merkmalsextraktion dient. Darin kann der Benutzer folgende Parameter wählen:
  - Den Typ des Klassifikators (MFPC/OAMFPC)
  - Den Exponenten D für die Distanzfunktion
  - Die prozentuale Elementarunschärfe p_c_e
  - Die Kreismaske für die Merkmalsextraktion
  - Die generalisierte Zirkluartransformation für die Merkmalsextraktion (SWT/GZTA1)
  
  ![Das Fenster zur Konfiguration sieht so aus.](https://github.com/Shad0wlife/Abschlussprojekt/blob/master/images/SettingsGui.JPG)
  
  Durch einen Klick auf "OK" werden die Einstellungen übernommen, und der Benutzer wird aufgefordert, die Quellordner für die Lerndaten 
  anzugeben. Die Oberfläche und Beschränkungen entsprechen dabei denen beim Öffnen der Testdaten zur Stapelverarbeitung.
  Es ist durch den Benutzer dringend darauf zu achten, dass der gewählte Ordner einer Klasse wirklich nur die Lerndaten dieser Klasse enthält.
  
  ![Das Fenster zur Wahl der Bilder sieht so aus.](https://github.com/Shad0wlife/Abschlussprojekt/blob/master/images/LearningSelection.JPG)
  
  Nach der Wahl eines Ordners mit Lerndaten wird der Benutzer gefragt, ob er eine weitere Klasse an Lerndaten spezifizieren möchte. 
  Bei Bestätigung wird der vorherige Dialog erneut angezeigt. Durch Wiederholung dieser Abfrage kann der Benutzer beliebig 
  viele Klassen anlernen.
  
  ![Das Fenster zur Abfrage, ob weitere Klassen angelernt werden sollen sieht so aus.](https://github.com/Shad0wlife/Abschlussprojekt/blob/master/images/MoreClasses.JPG)
  
  Sollte als Klassifikator der OAMFPC gewählt worden sein, wird der Benutzer nun aufgefordert, die Berechnungsparameter für den Gewichtsvektor 
  einzustellen.
  
  ![Das Fenster zur Parameterisierung des Gewichtsvektors sieht so aus.](https://github.com/Shad0wlife/Abschlussprojekt/blob/master/images/WeightsGui.JPG)
  
  Nach dem Anlernen und eventueller Parametrierung des OAMFPC führen beide Plugins die Klassifizierung durch. 
  Nachdem die Klassifizierung beendet wurde, wird der Benutzer aufgefordert, einen Speicherort für die CSV-Datei, welche das Klassifikationsergebnis 
  enthält, anzugeben.
  
  ![Das Fenster zur Wahl des Speicherorts sieht so aus.](https://github.com/Shad0wlife/Abschlussprojekt/blob/master/images/SavingGui.JPG)
  
  In der CSV-Datei ist dann für jedes Testbild eine Zeile und für jede Klasse eine Spalte zu finden. Wenn ein Testbild einer Klasse zugeordnet 
  wurde, steht an der entsprechenden Stelle in der Tabelle eine 1, an allen anderen Stellen der Zeile befindet sich eine 0.

  
  Diese Datei kann auch Online in der Quellcodeverwaltung des Projekts eingesehen werden:
  https://github.com/Shad0wlife/Abschlussprojekt