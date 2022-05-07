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
    private static final long USER_ID_2 = 55;
    private static final long USER_ID_3 = 77;
    private static final Project PROJECT_DEMO = new Project(USER_ID, "Projet toto", 0xFFA3CED2);
    private static final Project PROJECT_DEMO_2 = new Project(USER_ID_2, "Projet Tartampion", 0xFFEADAD1);
    private static final Project PROJECT_DEMO_3 = new Project(USER_ID_3, "Projet Tartampion", 0xFFEADAD1);

    /**
     *  Tasks data testing
     */
    private static Task cleaningWindows = new Task( USER_ID, "nettoyer les vitres", 18);
    private static Task sweepTheFloor = new Task( USER_ID_2,"balayer le sol", 10);
    private static Task washTheWalls = new Task( USER_ID,"Laver le mur", 11);
    private static Task checkTheWorks = new Task( USER_ID,"Inspecter", 12);

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
        this.database.projectDao().createProject(PROJECT_DEMO_2, PROJECT_DEMO,PROJECT_DEMO_3);
        List<Project> project=  LiveDataTestUtil.getValue(this.database.projectDao().getAllProject());
        assertTrue(project.size() == 3);

    }

    /**
     * Check if the list of tasks for Project_Demo is empty in the Database
     */
    @Test
    public void getTasksWhenNoItemInserted() throws InterruptedException{
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(USER_ID));
        assertTrue(tasks.isEmpty());
    }

    /**
     *
     * Check if each task is well related to its project
     */
    @Test
    public void insertAndGetTasks() throws  InterruptedException {
        // Adding 3 Project & 4 tasks.
        this.database.projectDao().createProject(PROJECT_DEMO_2, PROJECT_DEMO,PROJECT_DEMO_3);
        this.database.taskDao().insertTask(cleaningWindows);
        this.database.taskDao().insertTask(sweepTheFloor);
        this.database.taskDao().insertTask(checkTheWorks);
        this.database.taskDao().insertTask(washTheWalls);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(USER_ID));
        assertTrue(tasks.size() == 3);

        List<Task> tasks_2 = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(USER_ID_2));
        assertTrue(tasks_2.size() == 1);

        List<Task> tasks_3 = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(USER_ID_3));
        assertTrue(tasks_3.size() == 0);

    }
    @Test
    public void insertAndUpdateTasks() throws InterruptedException{
        this.database.projectDao().createProject(PROJECT_DEMO_2, PROJECT_DEMO,PROJECT_DEMO_3);
        this.database.taskDao().insertTask(cleaningWindows);

        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(USER_ID)).get(0);
        this.database.taskDao().updateTask(taskAdded);
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(USER_ID));
        assertTrue(tasks.size()  == 1 );
    }
    @Test

    public void insertAndDeleteTasks() throws InterruptedException {
        this.database.projectDao().createProject(PROJECT_DEMO_2, PROJECT_DEMO,PROJECT_DEMO_3);
        this.database.taskDao().insertTask(cleaningWindows);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(USER_ID)).get(0);
        this.database.taskDao().deleteTask(taskAdded.getId());

        List<Task>tasks =LiveDataTestUtil.getValue(this.database.taskDao().getTasks(USER_ID));
        assertTrue(tasks.isEmpty());
    }
}
