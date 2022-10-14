package com.nash.assignment.service;

import com.nash.assignment.modal.Status;

public interface StatusService {
    Status insertStatus(Status status);

    Iterable<Status> getAllStatus();
}
