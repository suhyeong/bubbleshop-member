package com.bubbleshop.member.application.internal.commandservice;

import com.bubbleshop.member.domain.model.aggregate.ExternalInterfaceHistory;
import com.bubbleshop.member.domain.repository.ExternalInterfaceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalInterfaceHistoryCommandService {
    private final ExternalInterfaceHistoryRepository externalInterfaceHistoryRepository;

    public void saveExternalInterfaceHistory(ExternalInterfaceHistory history) {
        externalInterfaceHistoryRepository.save(history);
    }
}
