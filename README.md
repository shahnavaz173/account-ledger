# account-ledger


Account ledger for mosque to manage Expenses and Income


#Build jar file
mvn clean install

#Create Installer
jpackage --type exe --input target --dest target --main-jar NoorAccount.jar --main-class org.noor.account.App --name "NoorAccount" --win-dir-chooser --icon .\mosque.ico --win-menu
