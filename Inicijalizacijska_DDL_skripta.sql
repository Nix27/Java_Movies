CREATE DATABASE JavaMovies
GO
USE JavaMovies
GO

create table AppUser
(
	IDUser int primary key identity,
	Username nvarchar(100),
	Password nvarchar(100),
	Role nvarchar(50)
)

GO

create table Director
(
	IDDirector int primary key identity,
	FirstName nvarchar(100),
	LastName nvarchar(100)
)

GO

create table Actor
(
	IDActor int primary key identity,
	FirstName nvarchar(100),
	LastName nvarchar(100)
)

GO

create table Movie
(
	IDMovie int primary key identity,
	Title nvarchar(100),
	PublishedDate nvarchar(100),
	Description nvarchar(max),
	OriginalTitle nvarchar(100),
	Duration int,
	YearOfRelease int,
	Genre nvarchar(100),
	Poster nvarchar(1000),
	Link nvarchar(1000),
	Reservation nvarchar(1000),
	Trailer nvarchar(100)
)

GO

create table MovieDirector
(
	IDMovieDirector int primary key identity,
	MovieId int not null,
	DirectorId int not null
)

GO

create table MovieActor
(
	IDMovieActor int primary key identity,
	MovieId int not null,
	ActorId int not null
)

GO

ALTER TABLE [dbo].[MovieDirector]  WITH CHECK ADD CONSTRAINT [FK_MovieDirector_Movie_MovieId] FOREIGN KEY([MovieId])
REFERENCES [dbo].[Movie] ([IDMovie])
ON DELETE CASCADE

GO

ALTER TABLE [dbo].[MovieDirector] CHECK CONSTRAINT [FK_MovieDirector_Movie_MovieId]

GO

ALTER TABLE [dbo].[MovieDirector]  WITH CHECK ADD CONSTRAINT [FK_MovieDirector_Director_DirectorId] FOREIGN KEY([DirectorId])
REFERENCES [dbo].[Director] ([IDDirector])
ON DELETE CASCADE

GO

ALTER TABLE [dbo].[MovieDirector] CHECK CONSTRAINT [FK_MovieDirector_Director_DirectorId]

GO

ALTER TABLE [dbo].[MovieActor]  WITH CHECK ADD CONSTRAINT [FK_MovieActor_Movie_MovieId] FOREIGN KEY([MovieId])
REFERENCES [dbo].[Movie] ([IDMovie])
ON DELETE CASCADE

GO

ALTER TABLE [dbo].[MovieActor] CHECK CONSTRAINT [FK_MovieActor_Movie_MovieId]

GO

ALTER TABLE [dbo].[MovieActor] WITH CHECK ADD CONSTRAINT [FK_MovieActor_Actor_ActorId] FOREIGN KEY([ActorId])
REFERENCES [dbo].[Actor] ([IDActor])
ON DELETE CASCADE

GO

ALTER TABLE [dbo].[MovieActor] CHECK CONSTRAINT [FK_MovieActor_Actor_ActorId]

GO

insert into AppUser (Username, Password, Role) values ('admin', 'admin123', 'Admin')
