# id1-java
Java implementation to interact with new estonian id card (ID1) over PCSC channel

## Simple example to read document number and last name using first available terminal

```
//get first terminal
CardTerminal terminal = TerminalFactory.getDefault().terminals().list().get(0);

PcscChannel channel = new PcscChannel(terminal);
EstIdCard card = new ID1(channel);

if (terminal.isCardPresent()) {
    channel.connect();
    System.out.println("doc number: " + card.readDocumentNumber());
    System.out.println("last name: " + card.readPersonalData(PersonalDataRecord.LAST_NAME).getLastName());
}

```
