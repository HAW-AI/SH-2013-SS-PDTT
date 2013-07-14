# Smart Home Control
`<repository>` steht im Folgenden fuer den Ordner, in dem sich diese Datei befindet.

# Installation mit APK
1. Auf dem Geraet unter `Einstellungen/Sicherheit/Geräteverwaltung/Unbekannte Herkunft` erlauben
2. https://dl.dropboxusercontent.com/u/12640802/LPXMPPPushMQIntermediate.zip herunterladen und entpacken. Den entpackte Ordner via `Eclipse/File/Import…/General/Existing Projects into Workspace` in den Workspace importieren.
3. Damit das Smartphone sich mit dem LPXMPPPushMQIntermediate-Server verbinden kann, muss im conf-Verzeichnis des Intermediate Servers die config.properties-Datei angepasst werden. Dort müssen alle IP-Adressen auf die Living Place-Sensornetz-IP-Adresse des PCs gesetzt werden.
4. In Eclipse auf dem PC, der als Intermediate Server dienen soll, den LPXMPPPushMQIntermediate-Server starten (main-Methode in Klasse LPPushServerStarter)
5. In Eclipse die SmartHomeControl-App auf einem Gerät starten
6. Das Geraet mittels USB an einen PC mit den installierten Android Developer Tools (adb) anschließen und `adb install <repository>/SmartHomeControl.apk` ausfuehren.


# Installation ohne APK
1. `git clone --recursive git@github.com:HAW-AI/SH-2013-SS-PDTT.git`
2. `Eclipse/File/Switch Workplace/Other…` mit `Workspace = <repository>`
3. `Eclipse/File/New/Other…/Android/Android Project from Existing Source/` mit `Root Directory = <repository>/dependencies/ActionBarSherlock/actionbarsherlock`
4. `Eclipse/File/New/Other…/Android/Android Project from Existing Source/` mit `Root Directory = <repository>/dependencies/HoloColorPicker`
5. `Eclipse/File/Import…/General/Existing Projects into Workspace` mit `Root Directory = <repository>/SmartHomeControl`
6. https://dl.dropboxusercontent.com/u/12640802/LPXMPPPushMQIntermediate.zip herunterladen und entpacken. Den entpackte Ordner via `Eclipse/File/Import…/General/Existing Projects into Workspace` in den Workspace importieren.
7. Damit das Smartphone sich mit dem LPXMPPPushMQIntermediate-Server verbinden kann, muss im conf-Verzeichnis des Intermediate Servers die config.properties-Datei angepasst werden. Dort müssen alle IP-Adressen auf die Living Place-Sensornetz-IP-Adresse des PCs gesetzt werden.
8. In Eclipse auf dem PC, der als Intermediate Server dienen soll, den LPXMPPPushMQIntermediate-Server starten (main-Methode in Klasse LPPushServerStarter)
9. In Eclipse die SmartHomeControl-App auf einem Gerät starten
