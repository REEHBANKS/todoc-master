package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

public class ProjectDataRepository {
    private final ProjectDao projectDao;

        // Constructor à partir du DAO Project
    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }
        //Methode obtenir un projet
    public LiveData<Project>getProject(long projectId) {
        return this.projectDao.getProject(projectId);
    }
}
