package com.nash.assignment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.modal.Status;
import com.nash.assignment.repositories.StatusRepositories;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    StatusRepositories statusRepositories;

    @Override
    public Status insertStatus(Status status) {
        Status insert = statusRepositories.save(status);
        return insert;
    }

    @Override
    public Iterable<Status> getAllStatus() {
        Iterable<Status> list = statusRepositories.findAll();
        return list;
    }

}
