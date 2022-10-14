package com.nash.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.model.Status;
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
