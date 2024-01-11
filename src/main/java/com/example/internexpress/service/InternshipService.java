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
        Internship internship = new Internship(title, duration, domain, internshipType, startDate, description, detailsLink, createdBy);
        checkId();
        internship.setId(freeId);
        repositoryInternship.save(internship);
    }


    public List<Internship> getAll() {
        return repositoryInternship.findAll();
    }

    public void deleteInternship(Integer id) {
        repositoryInternship.delete(id);
    }

    public Internship getInternship(Integer id) {
        return repositoryInternship.findOne(id).orElse(null);
    }

    public List<Internship> getInternshipsByDomain(String domain) {
        return repositoryInternship.findByDomain(domain);
    }

    public void addAppliance(Internship entity, User user) {
        entity.addApplicant(user);
        repositoryInternship.saveAppliance(entity, user);
    }

    public List<Internship> getInternshipsByCreator(Long id){
        return repositoryInternship.findByCreator(id);
    };

    public void handleAcceptAppliance(User selected, Internship selectedInternship, String status) {
        selectedInternship.addAcceptedUser(selected);
        repositoryInternship.changeStatus(selectedInternship, selected, status);
    }


}
