CREATE or alter PROCEDURE createUser
	@Username NVARCHAR(100),
	@Password NVARCHAR(100),
	@Role NVARCHAR(100),
	@IDUser INT OUTPUT
AS 
BEGIN 
	INSERT INTO AppUser (Username, Password, Role) VALUES(@Username, @Password, @Role)
	SET @IDUser = SCOPE_IDENTITY()
END

GO

CREATE or alter PROCEDURE authenticateUser
	@Username NVARCHAR(100),
	@Password NVARCHAR(100)
AS 
BEGIN 
	select * from AppUser
	where Username = @Username and Password = @Password
END

GO

CREATE PROCEDURE createMovie
	@Title NVARCHAR(100),
	@PublishedDate NVARCHAR(100),
	@Description NVARCHAR(max),
	@OriginalTitle NVARCHAR(100),
	@Duration int,
	@YearOfRelease int,
	@Genre NVARCHAR(100),
	@Poster NVARCHAR(1000),
	@TypeOfMovie NVARCHAR(50),
	@Link NVARCHAR(1000),
	@Reservation NVARCHAR(1000),
	@DateOfDisplay NVARCHAR(100),
	@Sort int,
	@Trailer NVARCHAR(100),
	@IDMovie INT OUTPUT
AS 
BEGIN 
	INSERT INTO Movie(Title, PublishedDate, Description, OriginalTitle, Duration, YearOfRelease, Genre, Poster, TypeOfMovie, Link, Reservation, DateOfDisplay, Sort, Trailer) 
	VALUES(@Title, @PublishedDate, @Description, @OriginalTitle, @Duration, @YearOfRelease, @Genre, @Poster, @TypeOfMovie, @Link, @Reservation, @DateOfDisplay, @Sort, @Trailer)
	SET @IDMovie = SCOPE_IDENTITY()
END

GO

CREATE PROCEDURE updateMovie
	@Title NVARCHAR(100),
	@PublishedDate NVARCHAR(100),
	@Description NVARCHAR(max),
	@OriginalTitle NVARCHAR(100),
	@Duration int,
	@YearOfRelease int,
	@Genre NVARCHAR(100),
	@Poster NVARCHAR(1000),
	@TypeOfMovie NVARCHAR(50),
	@Link NVARCHAR(1000),
	@Reservation NVARCHAR(1000),
	@DateOfDisplay NVARCHAR(100),
	@Sort int,
	@Trailer NVARCHAR(100),
	@IDMovie INT
	 
AS 
BEGIN 
	UPDATE Movie SET 
		Title = @Title,
		PublishedDate = @PublishedDate,
		Description = @Description,
		OriginalTitle = @OriginalTitle,
		Duration = @Duration,
		YearOfRelease = @YearOfRelease,
		Genre = @Genre,
		Poster = @Poster,
		TypeOfMovie = @TypeOfMovie,
		Link = @Link,
		Reservation = @Reservation,
		DateOfDisplay = @DateOfDisplay,
		Sort = @Sort,
		Trailer = @Trailer
	WHERE 
		IDMovie = @IDMovie
END

GO

CREATE PROCEDURE deleteMovie
	@IDMovie int	 
AS 
BEGIN 
	DELETE FROM Movie
	WHERE IDMovie = @IDMovie
END

GO

CREATE PROCEDURE selectMovie
	@IDMovie int
AS 
BEGIN 
	SELECT *  FROM Movie
	WHERE IDMovie = @IDMovie
END

GO

CREATE PROCEDURE selectMovies
AS 
BEGIN 
	SELECT * FROM Movie
END

GO

CREATE PROCEDURE createDirector
	@FirstName NVARCHAR(100),
	@LastName NVARCHAR(100),
	@IDDirector INT OUTPUT
AS 
BEGIN 
	INSERT INTO Director (FirstName, LastName) VALUES(@FirstName, @LastName)
	SET @IDDirector = SCOPE_IDENTITY()
END

GO

CREATE PROCEDURE updateDirector
	@FirstName NVARCHAR(100),
	@LastName NVARCHAR(100),
	@IDDirector INT
	 
AS 
BEGIN 
	UPDATE Director SET 
		FirstName = @FirstName, 
		LastName = @LastName
	WHERE 
		IDDirector = @IDDirector
END

GO

CREATE PROCEDURE deleteDirector
	@IDDirector INT	 
AS 
BEGIN 
	DELETE FROM Director
	WHERE IDDirector = @IDDirector
END

GO

CREATE PROCEDURE selectDirector
	@IDDirector INT
AS 
BEGIN 
	SELECT * FROM Director
	WHERE IDDirector = @IDDirector
END

GO

CREATE PROCEDURE selectDirectors
AS 
BEGIN 
	SELECT * FROM Director
END

GO

CREATE PROCEDURE createActor
	@FirstName NVARCHAR(100),
	@LastName NVARCHAR(100),
	@IDActor INT OUTPUT
AS 
BEGIN 
	INSERT INTO Actor (FirstName, LastName) VALUES(@FirstName, @LastName)
	SET @IDActor = SCOPE_IDENTITY()
END

GO

CREATE PROCEDURE updateActor
	@FirstName NVARCHAR(100),
	@LastName NVARCHAR(100),
	@IDActor INT
	 
AS 
BEGIN 
	UPDATE Actor SET 
		FirstName = @FirstName, 
		LastName = @LastName
	WHERE 
		IDActor = @IDActor
END

GO

CREATE PROCEDURE deleteActor
	@IDActor INT	 
AS 
BEGIN 
	DELETE FROM Actor
	WHERE IDActor = @IDActor
END

GO

CREATE PROCEDURE selectActor
	@IDActor INT
AS 
BEGIN 
	SELECT * FROM Actor
	WHERE IDActor = @IDActor
END

GO

CREATE PROCEDURE selectActors
AS 
BEGIN 
	SELECT * FROM Actor
END

GO

CREATE PROCEDURE createMovieDirector
	@MovieId int,
	@DirectorId int,
	@IDMovieDirector INT OUTPUT
AS 
BEGIN 
	INSERT INTO MovieDirector (MovieId, DirectorId) VALUES(@MovieId, @DirectorId)
	SET @IDMovieDirector = SCOPE_IDENTITY()
END

GO

CREATE PROCEDURE updateMovieDirector
	@MovieId int,
	@DirectorId int,
	@IDMovieDirector INT
	 
AS 
BEGIN 
	UPDATE MovieDirector SET 
		MovieId = @MovieId, 
		DirectorId = @DirectorId
	WHERE 
		IDMovieDirector = @IDMovieDirector
END

GO

CREATE PROCEDURE deleteMovieDirector
	@IDMovieDirector INT	 
AS 
BEGIN 
	DELETE FROM MovieDirector
	WHERE IDMovieDirector = @IDMovieDirector
END

GO

CREATE PROCEDURE selectMovieDirector
	@IDMovieDirector INT
AS 
BEGIN 
	SELECT * FROM MovieDirector
	WHERE IDMovieDirector = @IDMovieDirector
END

GO

CREATE PROCEDURE selectMovieDirectors
AS 
BEGIN 
	SELECT * FROM MovieDirector
END

GO

CREATE PROCEDURE createMovieActor
	@MovieId int,
	@ActorId int,
	@IDMovieActor INT OUTPUT
AS 
BEGIN 
	INSERT INTO MovieActor (MovieId, ActorId) VALUES(@MovieId, @ActorId)
	SET @IDMovieActor = SCOPE_IDENTITY()
END

GO

CREATE PROCEDURE updateMovieActor
	@MovieId int,
	@ActorId int,
	@IDMovieActor INT
	 
AS 
BEGIN 
	UPDATE MovieActor SET 
		MovieId = @MovieId, 
		ActorId = @ActorId
	WHERE 
		IDMovieActor = @IDMovieActor
END

GO

CREATE PROCEDURE deleteMovieActor
	@IDMovieActor INT	 
AS 
BEGIN 
	DELETE FROM MovieActor
	WHERE IDMovieActor = @IDMovieActor
END

GO

CREATE PROCEDURE selectMovieActor
	@IDMovieActor INT
AS 
BEGIN 
	SELECT * FROM MovieActor
	WHERE IDMovieActor = @IDMovieActor
END

GO

CREATE PROCEDURE selectMovieActors
AS 
BEGIN 
	SELECT * FROM MovieActor
END

GO

CREATE PROCEDURE deleteAll
AS 
BEGIN 
	delete from MovieDirector
	delete from MovieActor
	delete from Director
	delete from Actor
	delete from Movie
END
