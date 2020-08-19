package com.example.calculator.services.imp;

import com.example.calculator.model.Operation;
import com.example.calculator.repository.OperationRepository;
import com.example.calculator.services.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OperationServiceImp implements OperationService {

    private final OperationRepository operationRepository;

    @Override
    public Operation save(Operation operation) {
        return operationRepository.save(operation);
    }


}
