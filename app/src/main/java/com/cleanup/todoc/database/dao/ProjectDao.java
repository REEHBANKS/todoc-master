package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao

public interface ProjectDao {

    /**
     * Method against duplicate projects with the same Id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)

    /**
     * Create a Project
     */
    void createProject(Project projetTartampion, Project projet_tartampion, Project project);

    /**
     * get all project
     */
    @Query("SELECT * FROM projects")
    LiveData<List<Project>> getAllProject();
}

