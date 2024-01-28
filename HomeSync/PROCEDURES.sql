/*FROM 1-1 UNTIL 2-13 Mohamed Gad 10001181 */

Go
create proc display
@user_id int
as 
begin
    select * from RoomSchedule
    where creator_id = @user_id
end

--  Number : 1-1 
-- Description: Register on the website with a unique email along with the needed information. Choose which type of user you will be using @usertype (Admin).
--Name: UserRegister

select * from Calender

Go
create proc UserRegister
 @usertype VARCHAR(20), @email VARCHAR(50), @first_name VARCHAR(20), @last_name VARCHAR(20), @birth_date DATETIME, @password VARCHAR(10),
    @user_id INT OUTPUT
    as
    begin
    if not exists (SELECT 1 FROM Users WHERE email = @email)
    begin
    insert into Users(type, f_name, l_name,email,birth_date,password)
    values(@usertype,@first_name,@last_name,@email,@birth_date,@password)
    end
    else
    begin
    Set @user_id = -1
    print('error')
    end
    end

-- Number: 2-1
-- Name: UserLogin
-- Description: Login using email and password. If the user is not registered, the @user_id value will be (-1).


    go
    create proc UserLogin -- 2-1
    @email varchar(50), @password varchar(10), @success bit output, @user_id int output
    as 
    begin
    if exists (select 1 from Users where email = @email and password =@password)
    begin
    set @success = 1;
    set @user_id = (select id from Users where email=@email);
    end
    else 
    begin
    Set @success = 0
    set @user_id = -1
    end
    end

-- Number: 2-2
-- Name: ViewProfile
-- Description: View all details of the user's profile.

    go
    create proc Viewprofile -- 2-2
    @user_id int
    as
    select * from Users where id = @user_id


-- Number: 2-4
-- Name: ViewMyTask
-- Description: View their task. (You should check if the deadline has passed or not if it passed set the status to done)




   GO
    CREATE PROC  ViewMyTask
     @user_id int
    AS 
    BEGIN
    UPDATE Task 
    SET status = 'done'
    WHERE CURRENT_TIMESTAMP>due_date;
    SELECT T.*
    FROM Task T INNER JOIN Assigned_to A 
    ON T.Task_id = A.task_id
    WHERE A.users_id = @user_id;
    END;

-- Number: 2-5
-- Name: FinishMyTask
-- Description: Finish their task.


    go
    create proc FinishMyTask -- 2-5
    @user_id int, @title varchar(30)
    as
    begin
    update Task 
    set status = 'Done'
    from Task t
     inner join Assigned_to ast on t.Task_id = ast.task_id
    inner join Users u on u.id = ast.users_id
    where (u.id = @user_id) and (t.name = @title)
    end

-- Number: 2-6
-- Name: ViewTask
-- Description: View task status given the @user_id and the @creator of the task. Recently created reports should be shown first.



    go
    Create proc ViewTask-- 2-6
    @user_id int , @creator int
    as 
    begin 
    select t.* from Task t 
    inner join Assigned_to ast on t.Task_id = ast.task_id
    
    where(t.creator = @creator) and (ast.users_id = @user_id)
    order by t.due_date desc
    end

-- Number: 2-7
-- Name: ViewMyDeviceCharge
-- Description: View device charge.

    go
    create proc ViewMyDeviceCharge -- 2-7
    @device_id int , @charge int output , @loction int output
    as 
    begin 
     if exists (select 1 from Device d where device_id = @device_id )
    begin
    set @loction = (select room from Device where device_id = @device_id );
    set @charge = (select battery_status from Device where device_id = @device_id );
    end
    else
    begin
    set @loction = -1
    set @charge = -1
    print('cant find the device')
    end
    end

-- Number: 2-8
-- Name: AssignRoom
-- Description: Book a room with other users.


    go
    create proc AssignRoom-- 2-8
    @user_id int , @room_id int
    as
    begin 
     if  (exists (SELECT 1 FROM Users WHERE id = @user_id)) and  (exists (SELECT 1 FROM Room WHERE room_id = @room_id))
     begin 
     insert into RoomSchedule(creator_id, room, start_time)
     values (@user_id,@room_id, getdate())
     end
     else 
     print('invalid user id or room id')
     end


-- Number: 2-9
-- Name: CreateEvent
-- Description: Create events on the system.


go
create proc CreateEvent -- 2-9
@event_id Int, @user_id int, @name varchar(50),
@description varchar(200), @location varchar(40),
@reminder_date datetime ,@other_user_id int
as 
begin 
     IF EXISTS(SELECT * FROM Users U WHERE U.id = @user_id)
     BEGIN
         if not exists (select 1 from Calender where event_id = @event_id)
         BEGIN 
             insert into Calender( event_id,user_assigned_to , name, description, location, reminder_date )
             values (@event_id, @user_id, @name, @description, @location, @reminder_date)
             insert into Calender( event_id,user_assigned_to , name, description, location, reminder_date )
             values (@event_id, @other_user_id, @name, @description, @location, @reminder_date)
         END 
         ELSE 
            RAISERROR('EVENT ALREADY EXISTS',16,1)
      END
      ELSE
        RAISERROR('USER DOES NOT EXIST',16,1)
END


-- Number: 2-10
-- Name: AssignUser
-- Description: Assign a user to attend an event.
GO

--DROP PROC AssignUser

CREATE PROC AssignUser
@user_id INT, @event_id INT, @users_id INT OUTPUT
AS
BEGIN
    IF EXISTS(SELECT * FROM Calender C WHERE C.event_id = @event_id)
        BEGIN
           INSERT INTO Calender (event_id, user_assigned_to) VALUES (@event_id, @user_id)

           UPDATE Calender
           SET
           name = (SELECT DISTINCT name FROM Calender WHERE event_id = @event_id AND user_assigned_to != @user_id),
           description = (SELECT DISTINCT description FROM Calender WHERE event_id = @event_id AND user_assigned_to != @user_id),
           location = (SELECT DISTINCT location FROM Calender WHERE event_id = @event_id AND user_assigned_to != @user_id),
           reminder_date = (SELECT DISTINCT reminder_date FROM Calender WHERE event_id = @event_id AND user_assigned_to != @user_id)
           WHERE
           event_id = @event_id AND user_assigned_to = @user_id

           SET @users_id = @user_id

           SELECT *
           FROM Calender C
           WHERE C.event_id = @event_id
       END
    ELSE
        RAISERROR('NON EXISTENT EVENT OR USER',16,1)
END

SELECT DISTINCT name FROM Calender WHERE event_id = 1 AND user_assigned_to != 3

DELETE FROM Calender WHERE event_id = 1 AND user_assigned_to = 3

SELECT * FROM Calender 




    -- Number: 2-11
-- Name: AddReminder
-- Description: Add a reminder to a task.


    go
    create proc AddReminder -- 2-11
    @task_id int , @reminder datetime
    as
    begin 
    if exists (SELECT 1 FROM Task WHERE task_id = @task_id)
    begin
    update Task
    set reminder_date = @reminder
    where  task_id = @task_id
    end 
    else 
    print('invalid task id')
    end

    -- Number: 2-12
-- Name: Uninvited
-- Description: Uninvite a specific user to an event.




    GO

    CREATE PROC Uninvited -- 2-12
    @event_id int, @user_id int
    AS
    begin
        IF EXISTS(SELECT * FROM Calender WHERE event_id = @event_id AND user_assigned_to = @user_id)
            DELETE FROM Calender WHERE event_id = @event_id AND user_assigned_to = @user_id
        ELSE
            RAISERROR('INVALID USER ID OR EVENT ID',16,1)
    end


    -- Number: 2-13
-- Name: UpdateTaskDeadline
-- Description: Update the deadline of a specific task.

    go
     create proc UpdateTaskDeadline -- 2-13
    @task_id int , @deadline datetime
    as
    begin 
    if exists (SELECT 1 FROM Task WHERE task_id = @task_id)
    begin
    update Task
    set due_date = @deadline
    from Task
    where  Task_id = @task_id
    end 
    else 
    begin
    print('invalid task id')
    end
    end
    drop proc UpdateTaskDeadline
    /*FROM 2-14 UNTIL 3-3 Adam khaled 10001535 */



-- Number: 2-14
-- Name: ViewEvent
-- Description: View events given the @user_id and @event_id. If @event_id is empty, view all events belonging to the user, ordered by date.

 go
/* 2-14 */

CREATE PROCEDURE ViewEvent
@User_id INT, @Event_id INT
AS
BEGIN
	IF @Event_id = 0
		BEGIN
            IF EXISTS(SELECT * FROM Calender C WHERE C.user_assigned_to = @User_id)
            BEGIN
		        SELECT *
		        FROM Calender C
		        WHERE C.user_assigned_to = @User_id
            END
            ELSE
                RAISERROR('INVALID USER ID',16,1)
		END
	ELSE
		BEGIN
            IF EXISTS(SELECT * FROM Calender C WHERE C.user_assigned_to = @User_id AND C.event_id = @Event_id)
            BEGIN
		        SELECT *
		        FROM Calender C
		        WHERE C.user_assigned_to = @User_id AND C.event_id = @Event_id
		    END
            ELSE
                RAISERROR('INVALID USER ID OR EVENT ID',16,1)
        END
END


-- Number: 2-15
-- Name: ViewRecommendation
-- Description: View users that have no recommendations.


/* 2-15 */
GO
CREATE PROCEDURE ViewRecommendation
AS
BEGIN
	SELECT U.f_name, U.l_name
	FROM Users U LEFT OUTER JOIN Recommendation R ON U.id = R.user_id
	WHERE R.recommendation_id is null
END


-- Number: 2-16
-- Name: CreateNote
-- Description: Create a new note.

/* 2-16 */
GO
CREATE PROCEDURE CreateNote
@User_id INT, @note_id INT, @title VARCHAR(50), @Content VARCHAR(500), @creation_date DATETIME
AS
BEGIN
	IF NOT EXISTS(SELECT * FROM Notes WHERE id = @note_id)
		BEGIN
		INSERT INTO Notes(id, user_id, content, creation_date, title)
		VALUES (@note_id, @User_id, @Content, @creation_date, @title)
		END
	ELSE
		PRINT 'A note with this ID already exists!'
END


-- Number: 2-17
-- Name: ReceiveMoney
-- Description: Receive a transaction.


/* 2-17 */
GO
CREATE PROCEDURE ReceiveMoney
@receiver_id INT, @type VARCHAR(30), @amount DECIMAL(13,2), @status VARCHAR(10), @date DATETIME
AS
BEGIN
IF NOT EXISTS (SELECT 1 FROM Users WHERE id = @receiver_id)
    BEGIN
        RAISERROR('SENDER ID DOES NOT EXIST', 16, 1);
    END
    ELSE
BEGIN
    INSERT INTO Finance (user_id, type, amount, status, date) VALUES (@receiver_id, @type, @amount, @status, @date)
    END
END


-- Number: 2-18
-- Name: PlanPayment
-- Description: Create a payment on a specific date from one user to the other  each with their separate records(Based on the nature of the schema this can only be done by inserting the record twice, once for each user).



GO
/* 2-18 */
CREATE PROCEDURE PlanPayment
@sender_id INT, @receiver_id INT, @amount DECIMAL(13,2), @status VARCHAR(10), @deadline DATETIME
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Users WHERE id= @sender_id)
    BEGIN
        RAISERROR('SENDER ID DOES NOT EXIST', 16, 1);
    END
    ELSE IF NOT EXISTS (SELECT 1 FROM Users WHERE id = @receiver_id)
    BEGIN
        RAISERROR('RECEIVER ID DOES NOT EXIST', 16, 1);
    END
    ELSE
    BEGIN
        -- Insert outgoing transaction for sender
        INSERT INTO Finance (user_id, type, amount, status, deadline)
        VALUES (@sender_id, 'outgoing', @amount, @status, @deadline);

        -- Insert incoming transaction for receiver
        INSERT INTO Finance (user_id, type, amount, status, deadline)
        VALUES (@receiver_id, 'incoming', @amount, @status, @deadline);
    END
END


-- Number: 2-19
-- Name: SendMessage
-- Description: Send a message to a user.

GO
/* 2-19 */
CREATE PROCEDURE SendMessage
@sender_id INT, @receiver_id INT, @title VARCHAR(30), @content VARCHAR(200), @timesent TIME, @timereceived TIME
AS
BEGIN
IF NOT EXISTS (SELECT 1 FROM Users WHERE id= @sender_id)
    BEGIN
        RAISERROR('SENDER ID DOES NOT EXIST', 16, 1);
    END
    ELSE IF NOT EXISTS (SELECT 1 FROM Users WHERE id = @receiver_id)
    BEGIN
        RAISERROR('RECEIVER ID DOES NOT EXIST', 16, 1);
    END
    ELSE
    BEGIN
    INSERT INTO Communication (sender_id, receiver_id, content, time_sent, time_received, title) VALUES (@sender_id, @receiver_id, @content, @timesent, @timereceived,@title)
    END
END


-- Number: 2-20
-- Name: NoteTitle
-- Description: Change note title for all notes user created.


GO
/* 2-20 */
CREATE PROCEDURE NoteTitle
@user_id INT, @note_title VARCHAR(50)
AS
BEGIN
	IF EXISTS(SELECT * FROM Notes WHERE user_id = @user_id)
		BEGIN
		UPDATE Notes
		SET title = @note_title
		WHERE user_id = @user_id
		END
	ELSE
		PRINT 'This user ID has not Notes!'
END


-- Number: 2-21
-- Name: ShowMessages
-- Description: Show all messages received from a specific user.


GO
/* 2-21 */
CREATE PROCEDURE ShowMessages
@user_id INT, @sender_id INT
AS
BEGIN
    IF EXISTS(SELECT * FROM Communication WHERE sender_id = @sender_id AND receiver_id = @user_id)
        BEGIN
        SELECT *
        FROM Communication
        WHERE sender_id = @sender_id AND receiver_id = @user_id
        END
    ELSE
        RAISERROR ('THERE IS NO MESSAGE WITH THIS SENDER AND RECEIVER',16,1)
END


-- Number: 3-1
-- Name: ViewUsers
-- Description: See details of all users and filter them by @user_type

GO
/* 3-1 */
CREATE PROCEDURE ViewUsers
@user_type VARCHAR(20)
AS
BEGIN
	IF @user_type = 'admin'
		BEGIN
		SELECT *
		FROM Users U INNER JOIN Admin A ON U.id = A.admin_id
		END
	ELSE
		BEGIN
		SELECT *
		FROM Users U INNER JOIN Guest G ON U.id = G.guest_id
		END
END


-- Number: 3-2
-- Name: RemoveEvent
-- Description: Remove an event from the system.

GO
/* 3-2 */

--DROP PROC RemoveEvent

CREATE PROCEDURE RemoveEvent
@event_id INT, @user_id INT
AS
BEGIN
	IF NOT EXISTS(SELECT * FROM Calender WHERE event_id = @event_id)
		RAISERROR('EVENT ID OR USER ID NOT FOUND',16,1)
	ELSE
        IF EXISTS(SELECT * FROM Admin WHERE admin_id = @user_id)
		    BEGIN
		    DELETE FROM Calender
		    WHERE event_id = @event_id
		    END
        ELSE
            RAISERROR('THIS USER ID DOES NOT HAVE PERMISSION TO REMOVE EVENTS',16,1)
END

-- Number: 3-3
-- Name: CreateSchedule
-- Description: Create schedule for the rooms.

GO
/* 3-3 */
CREATE PROCEDURE CreateSchedule
@creator_id INT, @room_id INT, @start_time DATETIME, @end_time DATETIME, @action VARCHAR(20)
AS
BEGIN
	IF EXISTS (SELECT * FROM RoomSchedule WHERE creator_id = @creator_id AND room = @room_id AND start_time = @start_time)
		PRINT 'This room has already been scheduled for this time'
	ELSE
		BEGIN
			INSERT INTO RoomSchedule (creator_id, action, room, start_time, end_time) VALUES (@creator_id, @action, @room_id, @start_time, @end_time)
		END
END

GO
/*FROM 2-14 UNTIL 3-3 Hussein Mansour 10005024 */

-- Number: 3-4
-- Name: GuestRemove
-- Description: Remove a guest from the system.

CREATE PROC GuestRemove -- 3-4
@guest_id int, @admin_id int, @number_of_allowed_guests int OUTPUT
AS
BEGIN
IF EXISTS (SELECT * FROM Guest WHERE Guest.guest_id = @guest_id)
	BEGIN

	DELETE FROM Users WHERE id = @guest_id

	UPDATE Admin
	SET Admin.no_of_guests_allowed = Admin.no_of_guests_allowed + 1
	WHERE Admin.admin_id = @admin_id

	SELECT @number_of_allowed_guests = Admin.no_of_guests_allowed
	FROM Admin
	WHERE Admin.admin_id = @admin_id

	END
ELSE 
	RAISERROR('No such guest exists in the database.',16,1)
END

SELECT * FROM Users
SELECT * FROM Guest

-- Number: 3-5
-- Name: RecommendTD
-- Description: Recommend travel destinations for guests under certain age.

GO
CREATE PROC RecommentTD-- 3-5
@guest_id int, @destination varchar(10), @age int, @preference_no int
AS
BEGIN
declare @trip_no int
declare @guest_age int
SEt @trip_no = NULL

IF EXISTS(SELECT 1 FROM Travel WHERE Travel.destination = @destination)
	BEGIN
	SELECT @trip_no = Travel.trip_no
	FROM Travel
	WHERE Travel.destination = @destination
	END

IF @trip_no IS NOT NULL
	BEGIN
	IF NOT EXISTS(SELECT 1 FROM Preferences WHERE Preferences.preferences_no = @preference_no)
		BEGIN

		IF EXISTS(SELECT 1 FROM Guest WHERE Guest.guest_id = @guest_id)
			BEGIN
			SELECT  u.age
			FROM Guest INNER JOIN Users u ON Guest.guest_id = u.id
			WHERE Guest.guest_id = @guest_id and  @guest_age = u.age

			IF @guest_age < @age

				BEGIN

				INSERT INTO Preferences
				VALUES(@guest_id, 'Travel', @preference_no, @destination)

				INSERT INTO Recommendation(user_id, category, preferences_no, content)
				VALUES(@guest_id, 'Travel', @preference_no, @destination)

				END

			ELSE

				PRINT 'This guest is over the specified age'

			END

		ELSE

			PRINT 'This guest does not exist!'

		END

	ELSE

		PRINT 'A preference with this preference number already exists!'

	END

ELSE

	PRINT 'This destination does not exist'

END


-- Number: 3-6
-- Name: Servailance
-- Description: Access cameras in the house.

-- 3-6
GO
CREATE PROC Servailance
@user_id int, @location int, @camera_id int
AS
BEGIN
IF EXISTS(SELECT 1 FROM	Users WHERE Users.id = @user_id) AND EXISTS(SELECT 1 FROM Camera WHERE Camera.camera_id = @camera_id AND 
	Camera.room_id = @location)
	BEGIN

	UPDATE Camera
	SET Camera.monitor_id = @user_id
	WHERE Camera.camera_id = @camera_id AND Camera.room_id = @location

	END
ELSE
	PRINT 'Something is wrong with your inputs.'
END



-- Number: 3-7
-- Name: RoomAvailability
-- Description: Change status of room.


GO
CREATE PROC RoomAvailability-- 3-7
@location int, @status varchar(40)
AS
BEGIN
IF EXISTS (SELECT 1 FROM Room WHERE Room.room_id = @location)
	BEGIN

	UPDATE Room
	SET Room.status = @status
	WHERE Room.room_id = @location

	END
ELSE
	PRINT 'This room does not exist!'
END



-- Number: 3-8
-- Name: Sp_Inventory
-- Description: Create an inventory for a specific item.

-- 3-8
GO
CREATE PROC Sp_Inventory
@item_id int, @name varchar(30), @quantity int, @expirydate datetime, @price decimal(10,2),
@manufacturer varchar(30), @category varchar(20)
AS
BEGIN
IF NOT EXISTS (SELECT 1 FROM Inventory WHERE supply_id = @item_id)
	BEGIN

	INSERT INTO Inventory (supply_id,name,quantity,expiry_date,price,manufacturer,category)
	VALUES (@item_id, @name, @quantity, @expirydate, @price, @manufacturer, @category)

	END
ELSE
	PRINT 'An inventory with this ID already exists!'
END


-- Number: 3-9
-- Name: Shopping
-- Description: Calculate price of purchasing a certain item.

GO
CREATE PROC Shopping-- 3-9
@id int, @quantity int, @total_price decimal(10,2) OUTPUT
AS
BEGIN
IF EXISTS (SELECT 1 FROM Inventory WHERE Inventory.supply_id = @id)
	BEGIN

	SELECT @total_price = Inventory.price * @quantity
	FROM Inventory
	WHERE Inventory.supply_id = @id

	END
ELSE
	PRINT 'This ID is not associated with any inventory items.'
END


-- Number: 3-10
-- Name: LogActivityDuration
-- Description: If current user had an activity set its duration to 1 hour.

GO
CREATE PROC LogActivityDuration-- 3-10
@room_id int, @device_id int, @user_id int, @date datetime, @duration varchar(50)
AS
BEGIN
IF EXISTS (SELECT 1 FROM Log WHERE Log.room_id = @room_id AND Log.device_id = @device_id AND Log.user_id = @user_id AND 
Log.date = @date)
	BEGIN

	UPDATE Log
	SET Log.duration = @duration
	WHERE Log.room_id = @room_id AND Log.device_id = @device_id AND Log.user_id = @user_id AND Log.date = @date

	END
ELSE
	PRINT 'No log exists with these parameters!'
END


-- Number: 3-11
-- Name: TabletConsumption
-- Description: Set device consumption for all tablets.

GO
CREATE PROC TabletConsumption-- 3-11
@consumption int
AS
BEGIN

UPDATE Consumption 
SET consumption = @consumption
FROM Consumption C INNER JOIN Device D ON C.device_id = D.device_id
WHERE D.type = 'Tablet'

END


-- Number: 3-12
-- Name: MakePreferencesRoomTemp
-- Description: Make preferences for Room temperature to be 30 if a user is older then 40 

GO
CREATE PROC MakePreferencesRoomTemp-- 3-12
@user_id int, @category varchar(20), @preferences_number int
AS
BEGIN

IF EXISTS(SELECT 1 FROM Users WHERE Users.id = @user_id)

	BEGIN
    declare @user_age int
	SELECT @user_age = Users.age
	FROM Users
	WHERE Users.id = @user_id

	IF @user_age > 40
		BEGIN

		IF NOT EXISTS(SELECT 1 FROM Preferences WHERE Preferences.preferences_no = @preferences_number)
			BEGIN

			INSERT INTO Preferences(user_ID,category,preferences_no,content)
			VALUES(@user_id, @category, @preferences_number, 'room temperature is 30')

			END
		
		ELSE

			PRINT 'A preference with this preference number already exists!'

		END

	ELSE

		PRINT 'This user is under 40!'

	END

ELSE
	
	PRINT 'This user does not exist in the database'

END

-- Number: 3-13
-- Name: ViewMyLogEntry
-- Description: View Log entries involving the user.

GO
CREATE PROC ViewMyLogEntry-- 3-13
@user_id int
AS
BEGIN

IF EXISTS (SELECT 1 FROM Users WHERE Users.id = @user_id)
	BEGIN

	SELECT * FROM Log
	WHERE Log.user_id = @user_id

	END
ELSE
	PRINT 'There is no such user in the database'
END


-- Number: 3-14
-- Name: UpdateLogEntry
-- Description: Update log entries involving the user.

GO
CREATE PROC UpdateLogEntry-- 3-14
@user_id int, @room_id int, @device_id int, @activity varchar(30)
AS
BEGIN

IF EXISTS (SELECT 1 FROM Users WHERE Users.id = @user_id)
AND EXISTS (SELECT 1 FROM Log WHERE Log.room_id = @room_id AND Log.device_id = @device_id AND Log.user_id = @user_id)
	BEGIN

	UPDATE Log
	SET activity = @activity, room_id = @room_id, device_id = @device_id
	WHERE Log.room_id = @room_id AND Log.device_id = @device_id AND Log.user_id = @user_id

	END
ELSE
	PRINT 'Something is wrong with your inputs'
END
/*FROM 3-15 UNTIL 3-24 Mamdouh Hazem 10001816 */


-- Number: 3-15
-- Name: ViewRoom
-- Description: View rooms that are not being used

go
CREATE PROCEDURE ViewRoom -- 3-15
AS 
BEGIN
SELECT *
FROM Room
WHERE status = 'free' --ask what are the two status of rooms;
END;

-- Number: 3-16
-- Name: ViewMeeting
-- Description: View the details of the booked rooms given @user_id and @room_id . (If @room_id is not booked then show all rooms that are booked by this user).

go
CREATE PROCEDURE ViewMeeting  -- 3-16 finish it later 3-16 asks for two things
 @room_id int,
 @user_id int
 AS
 BEGIN
 IF NOT EXISTS(SELECT 1 FROM Room WHERE room_id = @room_id)
 BEGIN
 PRINT 'This room does not exist.';
 END
 IF exists (SELECT 1 FROM RoomSchedule WHERE creator_id = @user_id AND room = @room_id)
 BEGIN
 SELECT *
 FROM RoomSchedule
 Where room = @room_id AND creator_id = @user_id;
 END
 ELSE
 BEGIN
 SELECT *
 FROM RoomSchedule
 WHERE creator_id = @user_id;
 END
 END;
 
 -- Number: 3-17
-- Name: AdminAddTask
-- Description: Add to the tasks

 go
CREATE PROCEDURE AdminAddTask -- 3-17
@user_id INT,
@creator INT,
@name VARCHAR(30),
@category VARCHAR(20),
@priority INT,
@status VARCHAR(20),
@reminder DATETIME,
@deadline DATETIME,
@other_user INT
AS
BEGIN
INSERT INTO Task (name,due_date, category, creator, priority, status, reminder_date )
VALUES (@name , @deadline, @category, @creator, @priority, @status, @reminder ); 
DECLARE @task_id int;
SET @task_id = SCOPE_IDENTITY();
INSERT INTO Assigned_to VALUES (@creator,@task_id,@user_id);
IF EXISTS(Select 1 from Users WHERE id = @other_user)
BEGIN
INSERT INTO Assigned_to VALUES (@creator, @task_id, @other_user);
END
ELSE
BEGIN
Print 'The other user does not exist'
END
END;


-- Number: 3-18
-- Name: AddGuest
-- Description: Add Guests to the system , generate passwords for themand reserve rooms under their name.

go
Create proc AddGuest
    @email VARCHAR(30),
    @first_name VARCHAR(10),
    @address VARCHAR(30),
    @password VARCHAR(30),
    @guest_of INT,
    @room_id INT,
    @number_of_allowed_guests INT OUTPUT
AS
BEGIN
   If not exists(Select 1 from Admin WHERE admin_id = @guest_of)
   Begin
   RAISERROR('ENTER A VALID ADMIN ID',16,1)
   End
   else If not exists(Select 1 from Room WHERE room_id = @room_id)
   Begin
   RAISERROR('ENTER A VALID ADMIN ID',16,1)
   End

   Else IF EXISTS (SELECT 1 FROM Admin WHERE admin_id = @guest_of AND no_of_guests_allowed > 0)
    BEGIN
        DECLARE @Guest_id int;
        INSERT INTO Users(f_name, password, email, room)
        VALUES (@first_name, @password, @email, @room_id);
        SET @Guest_id = SCOPE_IDENTITY();
        INSERT INTO Guest (guest_ID, guest_of, address)
        VALUES (@Guest_id, @guest_of, @address);

        UPDATE Admin
        SET no_of_guests_allowed = no_of_guests_allowed - 1
        WHERE admin_id = @guest_of;


        SET @number_of_allowed_guests = (SELECT no_of_guests_allowed FROM Admin WHERE admin_id = @guest_of);
    END
    ELSE
    BEGIN

        RAISERROR ('Number of allowed guests exceeded the limit', 16, 1);
    END
END;


-- Number: 3-19
-- Name: AssignTask
-- Description: Assign task to a specific User

go
CREATE PROCEDURE AssignTask  -- 3-19
    @user_id INT,
    @task_id INT,
    @creator_id INT
AS
BEGIN

IF NOT EXISTS (SELECT 1 FROM Users WHERE id = @user_id)
BEGIN
PRINT 'User does not exist'
END
IF NOT EXISTS (SELECT 1 FROM Admin WHERE admin_id = @creator_id)
BEGIN
PRINT 'Admin does not exist'
END
IF NOT EXISTS (SELECT 1 FROM Task WHERE Task_id = @task_id)
BEGIN
PRINT 'Task does not exist'
END
 IF EXISTS (SELECT 1 FROM Users WHERE id = @user_id) AND
       EXISTS (SELECT 1 FROM Task WHERE Task_id = @task_id) AND
       EXISTS (SELECT 1 FROM Admin WHERE admin_id = @creator_id)
    BEGIN
       
        IF EXISTS (SELECT 1 FROM Task WHERE Task_id = @task_id AND creator = @creator_id)
        BEGIN
           
            INSERT INTO Assigned_to (admin_id, Task_id, users_id)
            VALUES (@creator_id, @task_id, @user_id);

           
        END
        ELSE
        BEGIN
            PRINT 'The task does exist for the specified user';
        END
    END

END;


-- Number: 3-20
-- Name: DeleteMsg
-- Description: Delete last message sent

go
CREATE PROCEDURE DeleteMsg -- 3-20
AS
BEGIN
IF (SELECT COUNT(*) FROM Communication) = 0
BEGIN
    RAISERROR ('THERE ARE NO EXISTING MESSAGES TO BE DELETED',16,1)
END
ELSE
BEGIN
DELETE FROM Communication
WHERE message_id = (
SELECT TOP 1 message_id
FROM Communication
ORDER BY time_sent DESC
 );
 END
END;


-- Number: 3-21
-- Name: AddItinerary
-- Description: Add outgoing flight itinerary for a specific flight

go
CREATE PROCEDURE AddItinerary  -- 3-21
@trip_no INT,
@flight_num VARCHAR(30),
@flight_date DATETIME,
@destination VARCHAR(40)
AS
BEGIN
IF EXISTS (SELECT 1 from Travel WHERE trip_no = @trip_no)
BEGIN
UPDATE TRAVEL 
SET outgoing_flight_date = @flight_date, outgoing_flight_num = @flight_num,destination = @destination
Where trip_no = @trip_no;
END
ELSE
BEGIN
PRINT 'This flight does not exist';
END
END;


-- Number: 3-22
-- Name: ChangeFlight
-- Description: Change flight date to next year for all flights in the current year


go
CREATE PROCEDURE ChangeFlight -- 3-22
AS 
BEGIN
UPDATE Travel
SET ingoing_flight_date = DATEADD(YEAR, 1, ingoing_flight_date),
outgoing_flight_date = DATEADD(YEAR, 1, outgoing_flight_date)
WHERE YEAR(ingoing_flight_date) = YEAR(GETDATE()) OR YEAR(outgoing_flight_date) = YEAR(GETDATE());
END;

-- Number: 3-23
-- Name: UpdateFlight
-- Description: Update incoming flights

go
CREATE PROCEDURE UpdateFlight -- 3-23
@date DATETIME,
@trip_no INT,
@destination VARCHAR(15)
AS 
BEGIN
IF EXISTS(SELECT 1 FROM Travel WHERE trip_no = @trip_no)
BEGIN
UPDATE Travel
SET ingoing_flight_date = @date,
destination = @destination
WHERE trip_no = @trip_no;
END
ELSE
BEGIN
PRINT 'This flight does not exist.';
END
END;

-- Number: 3-24
-- Name: AddDevice
-- Description: Add a new device

go
CREATE PROCEDURE AddDevice -- 3-24
@device_id int, 
@status varchar(20),
@battery int,
@location int, 
@type varchar(20)
AS
BEGIN
IF EXISTS(SELECT 1 FROM Room WHERE room_id = @location)
BEGIN
Set Identity_insert device on;
INSERT INTO Device (device_id, room, type, status, battery_status)
VALUES (@device_id, @location, @type, @status, @battery);
Set Identity_insert device off;
END
ELSE
BEGIN
PRINT 'This room does not exist.';
END
END;



-- Number: 3-25
-- Name: OutOfBattery
-- Description: Find the location of all devices out of battery

go
CREATE PROCEDURE OutOfBattery -- 3-25
AS 
BEGIN
Select DISTINCT Room 
FROM Device 
WHERE battery_status = 0;  --ask what status of the battery will be available to choose froom
END;

/* FROM 3-26 UNTIL 3-34 Ali Amr 10000652 */



-- Number: 3-26
-- Name: Charging
-- Description: Set the status of all devices out of battery to charging

--26 CHARGING
GO
CREATE PROC Charging
AS
BEGIN
UPDATE Device
SET status = 'charging'
WHERE battery_status = 0
END


-- Number: 3-27
-- Name: GuestsAllowed
-- Description: Set the number of allowed guests for an admin

GO
--27 GUESTS ALLOWED
CREATE PROC  GuestsAllowed
 @admin_id int,@number_of_guests int
AS
BEGIN
UPDATE Admin
SET  no_of_guests_allowed = @number_of_guests
WHERE admin_id = @admin_id
END

-- Number: 3-28
-- Name: Penalize
-- Description: Add a penalty for all unpaid transactions where the deadline has passed.

--28 PENALIZE
GO
CREATE PROC Penalize
 @Penalty_amount int
 AS 
 BEGIN
 UPDATE Finance
 SET penalty =  @Penalty_amount
 WHERE date>deadline
 END
 
 -- Number: 3-29
-- Name: GuestNumber
-- Description: Get the number of all guests currently present for a certain admin

 --29 GUEST NUMBER
 Go
 Go
 CREATE PROC  GuestNumber
  @admin_id int,@no_of_guests int OUTPUT
  AS
  BEGIN  --COUNT(G.guest_of )
  if exists(select 1 from Admin where admin_id = @admin_id)
  Begin
  SELECT  @no_of_guests = COUNT(G.guest_of) 
  FROM Admin A INNER JOIN Guest G
  ON A.admin_id = G.guest_of
  WHERE A.admin_id = @admin_id;
  print @no_of_guests;
  End
  else 
  begin
  RAISERROR('ADMIN ID IS INVALID',16,1)
  end
  END
  
-- Number: 3-30
-- Name: Youngest
-- Description: Get the youngest user in the system

  GO
 CREATE PROC  Youngest--30
 AS
 BEGIN
 SELECT *
 FROM Users
 WHERE birth_date = (SELECT MAX(birth_date) FROM Users);
 END

 -- Number: 3-31
-- Name: AveragaPayment
-- Description: Get the users whose average income per month is greater than a specific amount

  --31 AVERAGE PAYMENT  
  GO
	CREATE PROC AveragePayment
	@amount decimal(10,2)
	AS
	BEGIN
	SELECT U.f_Name,U.l_Name 
	FROM Users U INNER JOIN Admin A
	ON A.admin_id = U.id
	WHERE A.salary>@amount
	END

-- Number: 3-32
-- Name: Purchase
-- Description: Get the sum of all purchases needed in the home inventory (assuming you need only 1 of each missing item)

  --32 SUM OF PURCHASES
  GO
  CREATE PROC Purchase
 @amount int OUTPUT 
  AS
  BEGIN 
  SELECT @amount = SUM(price)
  FROM Inventory
  WHERE quantity =0
  print @amount
  END

  -- Number: 3-33
-- Name: NeedCharge
-- Description: Get the location where more than two devices have a dead battery

  --33 LOCATIONS WHERE MORE THAN 2 DEVICES HAVE A DEAD BATTERTY
  GO
 CREATE PROC NeedCharge
  AS
  BEGIN
  SELECT room
  FROM (
    SELECT room, COUNT(device_id) AS device_count
    FROM Device
    WHERE battery_status = 0
    GROUP BY room
    HAVING COUNT(device_id) > 1
    ) AS S
  END
  

-- Number: 3-34
-- Name: Admins
-- Description: Get the admin with more than 2 guests

  --34 ADMINS THAT HAVE MORE THAN 2 GUESTS 
 go
  CREATE PROC Admins
  AS 
  BEGIN
  SELECT U.f_name,U.l_name
  FROM Admin A INNER JOIN Guest G ON A.admin_ID = G.guest_of
  INNER JOIN Guest G2 ON G.guest_of = G2.guest_of 
  INNER JOIN Guest G3 ON G3.guest_of = G2.guest_of
  INNER JOIN Users U ON A.admin_ID = U.iD
  WHERE G.guest_ID>G2.guest_ID AND G3.guest_ID<G2.guest_ID
  END

  GO
CREATE PROC addAdmin
@admin_id int,@no_of_guests_allowed int,@salary decimal(10,2)
AS
BEGIN
INSERT INTO Admin 
VALUES(@admin_id,@no_of_guests_allowed ,@salary)
END

GO 
CREATE PROC addGuestInput
@guest_id int ,@guest_of int,@adress VARCHAR(30),@arrival_date DATETIME,@departure_date DATETIME,@residential VARCHAR (50),@isValid BIT OUTPUT
AS 
BEGIN
--VALUES OF ALLOWED GUEST AND TOTAL GUESTS OF ADMIN FOR COMPARISON
DECLARE @allowedGuests int
EXEC GuestsAllowed @guest_of,@allowedGuests OUTPUT;
DECLARE @totalGuests int
EXEC GuestNumber @guest_of,@allowedGuests OUTPUT;
--COMPARISON
	IF(@totalGuests<@allowedGuests)
		BEGIN
			INSERT INTO Guest VALUES(@guest_id,@guest_of,@adress ,@arrival_date,@departure_date ,@residential)
			SET @isValid = 1;
		END
	ELSE
		BEGIN
			SET @isValid = 0;
		END
END;

GO

CREATE PROC getUserType
@email varchar(50),@password varchar(10),@usertype varchar(20) OUTPUT
AS
BEGIN
SELECT  @usertype = u.type
FROM Users u
WHERE u.email = @email AND u.password = @password
END