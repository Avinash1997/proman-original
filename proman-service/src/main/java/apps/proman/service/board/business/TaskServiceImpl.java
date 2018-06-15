package apps.proman.service.board.business;

import static apps.proman.service.board.exception.TaskErrorCode.TSK_003;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apps.proman.service.board.dao.ProjectDao;
import apps.proman.service.board.dao.TaskDao;
import apps.proman.service.board.entity.ProjectMemberEntity;
import apps.proman.service.board.entity.TaskEntity;
import apps.proman.service.board.entity.TaskWatcherEntity;
import apps.proman.service.board.exception.ProjectErrorCode;
import apps.proman.service.board.model.TaskStatus;
import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.common.exception.EntityNotFoundException;
import apps.proman.service.common.model.SearchResult;
import apps.proman.service.user.dao.UserDao;
import apps.proman.service.user.entity.UserEntity;

@Service
public class TaskServiceImpl implements  TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;

    @Override
    public SearchResult<TaskEntity> findTasks(String boardUuid, String projectUuid, Integer page, Integer limit) {
        return taskDao.find(boardUuid, projectUuid, page, limit);
    }

    @Override
    public SearchResult<TaskEntity> findTasks(String boardUuid, String projectUuid, TaskStatus taskStatus, Integer page, Integer limit) {
        return taskDao.find(boardUuid, projectUuid, taskStatus, page, limit);
    }

    @Override
    public TaskEntity findTask(String boardUuid, String projectUuid, String taskUuid) throws ApplicationException {
        return findExistingTask(boardUuid, projectUuid, taskUuid);
    }

    @Override
    public TaskEntity createTask(TaskEntity newTask) throws ApplicationException {
        newTask.setStatus(TaskStatus.OPEN.getCode());
        return taskDao.create(newTask);
    }

    @Override
    public void updateTask(String boardUuid, String projectUuid, String taskUuid, TaskEntity updatedTask) throws ApplicationException {

        final TaskEntity existingTask = findExistingTask(boardUuid, projectUuid, taskUuid);
        if (StringUtils.isNotEmpty(updatedTask.getName())) {
            existingTask.setName(updatedTask.getName());
        }
        if (StringUtils.isNotEmpty(updatedTask.getDescription())) {
            existingTask.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getOwner() != null) {
            existingTask.setOwner(updatedTask.getOwner());
        }
        if (updatedTask.getStartAt() != null) {
            existingTask.setStartAt(updatedTask.getStartAt());
        }
        if (updatedTask.getEndAt() != null) {
            existingTask.setEndAt(updatedTask.getEndAt());
        }
        if (updatedTask.getOriginalEffort() != null) {
            existingTask.setOriginalEffort(updatedTask.getOriginalEffort());
        }
        if (updatedTask.getLoggedEffort() != null) {
            existingTask.setLoggedEffort(updatedTask.getLoggedEffort());
        }
        if (updatedTask.getRemainingEffort() != null) {
            existingTask.setRemainingEffort(updatedTask.getRemainingEffort());
        }

        taskDao.update(existingTask);
    }

    @Override
    public void deleteTask(String boardUuid, String projectUuid, String taskUuid) throws ApplicationException {

        final TaskEntity existingTask = findExistingTask(boardUuid, projectUuid, taskUuid);
        existingTask.setStatus(TaskStatus.DELETED.getCode());
        taskDao.update(existingTask);
    }

    @Override
    public void changeTaskOwner(String boardUuid, String projectUuid, String taskUuid, String ownerUuid) throws ApplicationException {

        final TaskEntity existingTask = findExistingTask(boardUuid, projectUuid, taskUuid);
        if(!existingTask.getOwner().getMember().getUuid().equals(ownerUuid)) {
            final ProjectMemberEntity newOwner = projectDao.findMember(projectUuid, ownerUuid);
            if(newOwner == null) {
                throw new EntityNotFoundException(ProjectErrorCode.PRJ_005, ownerUuid, projectUuid);
            }
            existingTask.setOwner(newOwner);
            taskDao.update(existingTask);
        }
    }

    @Override
    public void changeTaskStatus(String boardUuid, String projectUuid, String taskUuid, TaskStatus newTaskStatus) throws ApplicationException {

        final TaskEntity existingTask = findExistingTask(boardUuid, projectUuid, taskUuid);
        if (existingTask.getStatus() != newTaskStatus.getCode()) {
            existingTask.setStatus(newTaskStatus.getCode());
            taskDao.update(existingTask);
        }
    }

    @Override
    public SearchResult<TaskWatcherEntity> getWatchers(String boardUuid, String projectUuid, String taskUuid) throws ApplicationException {

        final TaskEntity existingTask = findExistingTask(boardUuid, projectUuid, taskUuid);
        List<TaskWatcherEntity> taskWatchers = existingTask.getWatchers();
        return new SearchResult(taskWatchers.size(), taskWatchers);
    }

    @Override
    public void addWatchers(String boardUuid, String projectUuid, String taskUuid, Set<String> watcherUuids) throws ApplicationException {

        final TaskEntity existingTask = findExistingTask(boardUuid, projectUuid, taskUuid);
        for (final String watcherUuid : watcherUuids) {
            final UserEntity existingWatcher = userDao.findByUUID(watcherUuid);
            if (existingWatcher != null && taskDao.findWatcher(taskUuid, watcherUuid) == null) {
                final TaskWatcherEntity taskWatcherEntity = new TaskWatcherEntity();
                taskWatcherEntity.setTask(existingTask);
                taskWatcherEntity.setWatcher(existingWatcher);
                taskDao.addWatcher(taskWatcherEntity);
            }
        }
    }

    @Override
    public void removeWatchers(String boardUuid, String projectUuid, String taskUuid, Set<String> watcherUuids) throws ApplicationException {

        final TaskEntity existingTask = taskDao.findByUUID(boardUuid, projectUuid, taskUuid);
        if (existingTask == null) {
            throw new EntityNotFoundException(TSK_003, projectUuid, boardUuid);
        }
        for (final String watcherUuid : watcherUuids) {
            TaskWatcherEntity existingTaskWatcher = taskDao.findWatcher(taskUuid, watcherUuid);
            if (existingTaskWatcher != null) {
                taskDao.removeWatcher(existingTaskWatcher);
            }
        }

    }

    private TaskEntity findExistingTask(String boardUuid, String projectUuid, String taskUuid) throws EntityNotFoundException {
        final TaskEntity existingTask = taskDao.findByUUID(boardUuid, projectUuid, taskUuid);
        if (existingTask == null) {
            throw new EntityNotFoundException(TSK_003, projectUuid, boardUuid);
        }
        return existingTask;
    }

}
