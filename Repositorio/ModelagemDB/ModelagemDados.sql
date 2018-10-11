drop DATABASE if EXISTS teste1;
create database teste1;
use teste1;

CREATE TABLE IF NOT EXISTS Linha (
    LinhaID INT PRIMARY KEY AUTO_INCREMENT,
    LinhaNome VARCHAR(255)  
#    LinhaInicio VARCHAR(255),
#    LinhaFim VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Ponto (
    PontoID INT PRIMARY KEY AUTO_INCREMENT,
    PontoNome VARCHAR(255),
    PontoRua VARCHAR(255),
    PontoBairro VARCHAR(255),
    PontoX INT,
    PontoY INT,
    PontoZ INT
);

CREATE TABLE IF NOT EXISTS Semana (
    DiaID INT PRIMARY KEY AUTO_INCREMENT,
    DiaNome VARCHAR(255) UNIQUE
);


create table Intervalo(
	IntervaloID int PRIMARY key AUTO_INCREMENT,
	Origem int, 
	Destino int,
	Valor Datetime,
    
    FOREIGN KEY (Origem) References Ponto(PontoID),
    FOREIGN KEY (Destino) References Ponto(PontoID)
);

CREATE TABLE Sentido (
    SentidoID INT PRIMARY KEY AUTO_INCREMENT,
    LinhaID INT,
    PontoInicio INT,
    PontoFim INT,
    FOREIGN KEY (LinhaID)
        REFERENCES Linha (LinhaID),
    FOREIGN KEY (PontoInicio)
        REFERENCES Ponto (PontoID),
    FOREIGN KEY (PontoFim)
        REFERENCES Ponto (PontoID)
);

CREATE TABLE IF NOT EXISTS HorarioInicio (
    HoraID INT PRIMARY KEY AUTO_INCREMENT,
    LinhaID INT,
    SentidoID INT,
    DiaID INT,
    HoraInicial DATETIME,
    FOREIGN KEY (LinhaID)
        REFERENCES Linha (LinhaID),
    FOREIGN KEY (SentidoID)
        REFERENCES Sentido (SentidoID),
    FOREIGN KEY (DiaID)
        REFERENCES Semana (DiaID)
);

INSERT INTO semana (DiaNome)VALUE('Segunda-Feira');
INSERT INTO semana (DiaNome)VALUE('Terça-Feira');
INSERT INTO semana (DiaNome)VALUE('Quarta-Feira');
INSERT INTO semana (DiaNome)VALUE('Quinta-Feira');
INSERT INTO semana (DiaNome)VALUE('Sexta-Feira');
INSERT INTO semana (DiaNome)VALUE('Sábado');
INSERT INTO semana (DiaNome)VALUE('Domingo');
INSERT INTO semana (DiaNome)VALUE('Feriado');

SELECT * from semana;
