package victor.training.spring.transactions;

import java.util.List;

public interface MessageRepoCustom {
   List<MessageDto> search(MessageSearchCriteria searchCriteria);
}
