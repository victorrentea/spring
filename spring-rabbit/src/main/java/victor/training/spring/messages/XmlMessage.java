package victor.training.spring.messages;

public sealed interface XmlMessage permits Order, Invoice, Notification {
}

