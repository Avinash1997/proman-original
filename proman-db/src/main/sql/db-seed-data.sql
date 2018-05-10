-- Copyright 2018-2019, https://beingtechie.io

-- Script: db-seed.sql
-- Description: Setup seed data that is essential to bring up applications in running state.
-- Version: 1.0
-- Author: Thribhuvan Krishnamurthy

INSERT INTO proman.GROUPS (ID, UUID, NAME, DESCRIPTION, CREATED_BY)
		VALUES 
		(1, 101, 'Manage Users', 'User Management', CURRENT_USER),
		(2, 102, 'Manage Teams', 'Team Management', CURRENT_USER),
		(3, 103, 'Manage Boards', 'BoardManagement', CURRENT_USER),
		(4, 104, 'Manage Reports', 'Report Management', CURRENT_USER);

INSERT INTO proman.PERMISSIONS (ID, UUID, NAME, DESCRIPTION, GROUP_ID, CREATED_BY)
		VALUES 
		(1, 101, 'Add User', 'Add new user', 1, CURRENT_USER),
		(2, 102, 'Modify User', 'Modify an existing user', 1, CURRENT_USER),
		(3, 103, 'Remove User', 'Remove an existing user', 1, CURRENT_USER),
		(4, 104, 'Add Team', 'Add new team', 2, CURRENT_USER),
		(5, 105, 'Modify Team', 'Modify an existing team', 2, CURRENT_USER),
		(6, 106, 'Remove Team', 'Remove an existing team', 2, CURRENT_USER),
		(7, 107, 'Add Member(s) to Team', 'Add team member(s) to the team', 2, CURRENT_USER),
        (8, 108, 'Remove Member(s) from Team', 'Remove existing member(s) from the team', 2, CURRENT_USER),
		(9, 109, 'Add Board', 'Add new board', 3, CURRENT_USER),
		(10, 110, 'Modify Board', 'Modify an existing board', 3, CURRENT_USER),
		(11, 111, 'Remove Board', 'Remove an existing board', 3, CURRENT_USER),
		(12, 112, 'Add Team to Board', 'Add Team to an existing board', 3, CURRENT_USER),
		(13, 113, 'Remove Team from Board', 'Remove team from an existing board', 3, CURRENT_USER),
		(14, 114, 'Add Member(s) to Board', 'Add team member(s) to the board', 3, CURRENT_USER),
        (15, 115, 'Remove Member(s) from Board', 'Remove existing member(s) from the board', 3, CURRENT_USER),
		(16, 116, 'Add Task to Board', 'Add new task to the board', 3, CURRENT_USER),
		(17, 117, 'Modify Task', 'Modify an existing task in the board', 3, CURRENT_USER),
		(18, 118, 'Remove Task from Board', 'Remove an existing task from the board', 3, CURRENT_USER),
		(19, 119, 'Add Workflow Template', 'Add new workflow template', 3, CURRENT_USER),
		(20, 120, 'Modify Workflow Template', 'Modify an existing workflow template', 3, CURRENT_USER),
		(21, 121, 'Remove Workflow Template', 'Remove an existing workflow template', 3, CURRENT_USER),
		(22, 122, 'View Reports', 'View various reports', 4, CURRENT_USER);

INSERT INTO proman.ROLES (ID, UUID, NAME, DESCRIPTION, STATUS, CREATED_BY)
		VALUES 
		(1, 100, 'Administrator', 'Administrator', true, CURRENT_USER);

INSERT INTO proman.ROLE_PERMISSIONS (ROLE_ID, PERMISSION_ID, CREATED_BY)
		VALUES 
		(1,1, CURRENT_USER), (1,2, CURRENT_USER), (1,3, CURRENT_USER), (1,4, CURRENT_USER), (1,5, CURRENT_USER), (1,6, CURRENT_USER), (1,7, CURRENT_USER),
		(1,8, CURRENT_USER), (1,9, CURRENT_USER), (1,10, CURRENT_USER), (1,11, CURRENT_USER), (1,12, CURRENT_USER), (1,13, CURRENT_USER), (1,14, CURRENT_USER),
		(1,15, CURRENT_USER), (1,16, CURRENT_USER), (1,17, CURRENT_USER), (1,18, CURRENT_USER), (1,19, CURRENT_USER), (1,20, CURRENT_USER), (1,21, CURRENT_USER),
		(1,22, CURRENT_USER);

-- ********** End of Roles and Permissions setup **********

-- ********** Begin of Users setup **********

INSERT INTO proman.USERS (ROLE_ID, UUID, EMAIL, PASSWORD, SALT, FIRST_NAME, LAST_NAME,
					MOBILE_PHONE, STATUS, FAILED_LOGIN_COUNT, LAST_LOGIN_AT, LAST_PASSWORD_CHANGE_AT, CREATED_BY)
		VALUES 
		(1, '7d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'admin@proman.io', '9Iq6DK91fqoDL3CEkokuXGT+TJsGifvADnXiw3yBD4I=', 'D29DC89B3A7CB404', 'Proman', 'Administrator',
		'(111) 111-1111', true, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_USER);
		
-- ********** End of Users setup **********	