package com.nash.assignment.services.interfaces;

import com.nash.assignment.modal.Status;

public interface StatusService {
    Status insertStatus(Status status);

    Iterable<Status> getAllStatus();
}
