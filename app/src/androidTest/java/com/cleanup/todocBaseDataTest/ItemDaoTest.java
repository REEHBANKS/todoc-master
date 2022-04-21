package com.cleanup.todocBaseDataTest;

import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.dao.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ItemDaoTest {

    private TodocDatabase database;
    /**
     * Project data testing
     */
    private static final long USER_ID = 33;
    private static final Project PROJECT_DEMO = new Project(USER_ID, "Projet toto", 0xFFA3CED2);

    /**
     *  Tasks data testing
     */
    private static Task VITRES_BATIMENT = new Task(3, USER_ID, "nettoyer les vitres", 18);
    private static Task SOL_HALL = new Task(9, USER_ID,"balayer le sol", 10);
    private static Task MUR = new Task(4, USER_ID,"Laver le mur", 11);
    private static Task PLAFOND = new Task(10, USER_ID,"Inspecter", 12);

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }
    @After
    public void closeDb() throws Exception {
        database.close();
    }

    /**
     * Test on the Project_Demo to see if it is visible on the DataBase
     */
    @Test
    public void insertAndGetter() throws InterruptedException {
        this.database.projectDao().createProject(new Project(5L, "Projet Tartampion", 0xFFEADAD1), new Project(1, "Projet Tartampion", 0xFFEADAD1), PROJECT_DEMO);
        Project project = (Project) LiveDataTestUtil.getValue(this.database.projectDao().getAllProject());
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == USER_ID);
    }

    /**
     * Check if the list of tasks for Project_Demo is empty in the Database
     */
    @Test
    public void getTasksWhenNoItemInserted() throws InterruptedException{
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(USER_ID));
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTasks() throws  InterruptedException {
        // Adding 1 Project & 4 tasks.
        this.database.projectDao().createProject(new Project(5L, "Projet Tartampion", 0xFFEADAD1), new Project(1, "Projet Tartampion", 0xFFEADAD1), PROJECT_DEMO);
        this.database.taskDao().insertTask(VITRES_BATIMENT);
        this.database.taskDao().insertTask(SOL_HALL);
        this.database.taskDao().insertTask(PLAFOND);
        this.database.taskDao().insertTask(MUR);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(USER_ID));
        assertTrue(tasks.size() == 4);
    }
    @Test
    public void insertAndUpdateTasks() throws InterruptedException{
        this.database.projectDao().createProject(new Project(5L, "Projet Tartampion", 0xFFEADAD1), new Project(1, "Projet Tartampion", 0xFFEADAD1), PROJECT_DEMO);
        this.database.taskDao().insertTask(VITRES_BATIMENT);

        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(USER_ID)).get(0);
        this.database.taskDao().updateTask(taskAdded);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(USER_ID));

       // TODO Completez test get(0)
        assertTrue(tasks.size()  == 1 && VITRES_BATIMENT.getId() == 3);
    }
    @Test

    public void insertAndDeleteTasks() throws InterruptedException {
        this.database.projectDao().createProject(new Project(5L, "Projet Tartampion", 0xFFEADAD1), new Project(1, "Projet Tartampion", 0xFFEADAD1), PROJECT_DEMO);
        this.database.taskDao().insertTask(VITRES_BATIMENT);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(USER_ID)).get(0);
        this.database.taskDao().deleteTask(taskAdded.getId());

        List<Task>tasks =LiveDataTestUtil.getValue(this.database.taskDao().getTasks(USER_ID));
        assertTrue(tasks.isEmpty());
    }
}
