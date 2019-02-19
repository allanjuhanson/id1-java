# id1-java
Java implementation to interact with new estonian id card (ID1) over PCSC channel

## Simple example to read document number and name from card

Get first terminal
```
CardTerminal terminal = TerminalFactory.getDefault().terminals().list().get(0);
```
Get terminal by name
```
CardTerminal terminal = TerminalFactory.getDefault().terminals().getTerminal("OMNIKEY CardMan 1021");
```

Connect and read data
```
PcscChannel channel = new PcscChannel(terminal);
EstIdCard card = new ID1(channel);

if (terminal.isCardPresent()) {
    channel.connect();
    System.out.println("doc number: " + card.readDocumentNumber());
    PersonalData personalData = card.readPersonalData(PersonalDataRecord.LAST_NAME, PersonalDataRecord.FIRST_NAME);
    System.out.println("name: " + personalData.getFirstName() + " " + personalData.getLastName());
}

```
