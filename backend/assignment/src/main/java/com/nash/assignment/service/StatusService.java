package com.nash.assignment.service;

import com.nash.assignment.model.Status;

public interface StatusService {
    Status insertStatus(Status status);

    Iterable<Status> getAllStatus();
}
