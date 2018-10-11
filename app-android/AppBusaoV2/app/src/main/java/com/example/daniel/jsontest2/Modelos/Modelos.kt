package com.example.daniel.jsontest2.Modelos

//AS CHAVES DEVEM SER IGUAIS nas listas !

//lista MASTER
class PontosFeed(val pontos: List<Pontos>, val linhas: MutableList<Linhas>)

//Lista de pontos
class Pontos(var PontoID: String, var Latitude: String, var Longitude: String, var Distancia: Long)

//Lista de Linhas
//OBS: RoteiroPontos contém a lista de pontos de cada linha para fácil encontrar se ela passa nele ou não
// os valores de PONTOS a serem salvos são as IDs dos Pontos da classa anterior
class Linhas(var LinhaID: String, var RoteiroInicio: String, var RoteiroFim: String, var RoteiroPontos: String)

//Lista de horarioLinhas
//Horaris cadastrados das linhas (saída ou chegada a pontos extremos)
class HorarioLinhas(var LinhaID: String, var RoteiroInicio: String, var RoteiroFim: String, var DiaSemana: String, var Hora: String)

//Lista de pontos
//Tabela de horários da linhas
class HoraLinhaPontos(var LinhaID: String, var RoteiroInicio: String, var RoteiroFim: String, var PontoID: String, var Hora: String)

//{"LinhaID":"Cohab I","RoteiroInicio":"Terminal Urbano","RoteiroFim":"Bairro Cohab I","DiaSemana":"2, 3, 4, 5, 6","Hora":"6:05"},
//Falta implementar: lista horários