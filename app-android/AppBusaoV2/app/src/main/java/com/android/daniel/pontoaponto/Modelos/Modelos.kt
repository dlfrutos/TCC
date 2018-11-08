package com.android.daniel.pontoaponto.Modelos

//AS CHAVES DEVEM SER IGUAIS nas listas !

//lista MASTER
class PontosFeed(val pontos: List<Pontos>, val linhas: MutableList<Linhas>, val horaLinha: MutableList<HoraLinha>, val horaLinhaPontos: MutableList<HoraLinhaPontos>, val versao: Versao)

//Lista de pontos
class Pontos(var PontoID: String, var Latitude: String, var Longitude: String, var Distancia: Int, var Comentario: String, var Sentido: String)

//Lista de Linhas
//OBS: RoteiroPontos contém a lista de pontos de cada linha para fácil encontrar se ela passa nele ou não
// os valores de PONTOS a serem salvos são as IDs dos Pontos da classa anterior
class Linhas(var LinhaID: String, var RoteiroInicio: String, var RoteiroFim: String, var RoteiroPontos: String)

//Lista de horarioLinhas
//Horaris cadastrados das linhas (saída ou chegada a pontos extremos)
class HoraLinha(var LinhaID: String, var RoteiroInicio: String, var RoteiroFim: String, var DiaSemana: String, var Hora: String)

//Lista de pontos
//Tabela de horários da linhas
class HoraLinhaPontos(var LinhaID: String, var RoteiroInicio: String, var SentidoLinha: String, var PontoID: String, var Hora: String, var horaCalc: String, Comentario:String, val  IntervaloMin:Int, val IntervaloSeg: Int)

//Insere a versão do DB
class Versao(var DataHora:String,var ID:String,var OBS:String )

//{"LinhaID":"Cohab I","RoteiroInicio":"Terminal Urbano","RoteiroFim":"Bairro Cohab I","DiaSemana":"2, 3, 4, 5, 6","Hora":"6:05"},
//Falta implementar: lista horários