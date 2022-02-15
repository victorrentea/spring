package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaygroundLazy {
    private final MessageRepo repo;
    private Long messageId;

    public void transactionOne() {
        messageId = repo.save(new Message("aa").addTag("One")).getId();
    }

    @Transactional
    public void transactionTwo() {
        Message message = repo.findById(messageId).get();
        System.out.println("Loaded from DB");

        System.out.println(message.getTags());
    }

}

