package com.example.internexpress.service;

import com.example.internexpress.domain.Internship;
import com.example.internexpress.domain.User;
import com.example.internexpress.repository.Repository;

import java.util.List;

public class InternshipService {
    private final Repository<Integer, Internship> repositoryInternship;
    private Integer freeId;

    public InternshipService(Repository<Integer, Internship> repo) {
        this.repositoryInternship = repo;
        freeId = 0;
    }


    private void checkId() {
        freeId = 0;
        int nr = 0;
        for (Internship internship : repositoryInternship.findAll()) {
            freeId++;
            nr++;
            if (!freeId.equals(internship.getId())) {
                break;
            }

        }
        if (nr == repositoryInternship.findAll().size())
            freeId++;
    }


    public void addInternship(String title, String duration, String domain, String internshipType, String startDate, String description, String detailsLink, User createdBy) {
        //to do
    }


    public List<Internship> getAll() {
        return repositoryInternship.findAll();
    }

    public void deleteInternship(String id1) {

    }

    public Internship getInternship(Integer id) {
        //todo
        return null;
    }

    public List<Internship> getInternshipsByCreator(Long id){
        return null;
    };
}
