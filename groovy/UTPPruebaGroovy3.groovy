#!/usr/bin/env groovy

import groovy.sql.Sql
import groovy.util.logging.*
import java.sql.SQLException
import org.apache.log4j.*
import BGUTPConexion
import BGUTPLogger



@Log4j
class UTPPruebaGroovy3 {


    static void main(String[] args) {
        Logger log = BGUTPLogger.getLogger(this.getName())


        def String i_estudiante = args[0]
        println "Estudiante:" + i_estudiante

        def Sql sqlSample = Sql.newInstance(BGUTPConexion.getDataSource('dbSample'))
        calculaInteres(sqlSample, i_estudiante)
    }

    static private void calculaInteres(Sql sqlSample, String i_estudiante) {
        try {
            sqlSample.call ( "call sp_calculo_interes_groovy (${i_estudiante},${Sql.INTEGER} )")
            {retorno ->
                if (retorno != 0){
                    log.error "calculaInteres SQL: Error Calculando intereses ${i_estudiante}"
                }
            }
       }catch (SQLException sqlex){
            while (sqlex != null){
                log.error "calculaInteres SQL: ${sqlex.getErrorCode()} ${sqlex.getMessage()}"
                sqlex = sqlex.getNextException()
            }
            System.exit(1)
        }catch(Exception ex) {
                log.error "calculaInteres: ${ex}"
                System.exit(1)
        }
    }
}

