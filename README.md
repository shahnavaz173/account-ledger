# account-ledger


Account ledger for mosque to manage Expenses and Income

<br />
Build jar file:<br />
mvn clean install


Create Installer:<br />
jpackage --type exe --input target --dest target --main-jar NoorAccount.jar --main-class org.noor.account.App --name "NoorAccount" --win-dir-chooser --icon .\mosque.ico --win-menu
