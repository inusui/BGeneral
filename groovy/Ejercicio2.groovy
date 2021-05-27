#!/usr/bin/env groovy

import groovy.sql.Sql
import groovy.util.logging.*
import java.sql.SQLException
import org.apache.log4j.*
import BGUTPConexion
import BGUTPLogger



@Log4j
class Ejercicio2 {


    static void main(String[] args) {
        Logger log = BGUTPLogger.getLogger(this.getName())


        def String i_estudiante = args[0]
        println "Estudiante:" + i_estudiante

        def Sql sqlSample = Sql.newInstance(BGUTPConexion.getDataSource('dbSample'))
        executeMySQL(sqlSample, i_estudiante)
    }

    static private void executeMySQL(Sql sqlSample, String i_estudiante) {
        try {
            sqlSample.eachRow ( "call sp_prueba_groovy (?)", [i_estudiante] )
            {itc ->
                    println itc.t_entrada + "~ID: " + itc.id +"\nDescripcion--> " + itc.descripcion  //Se agrego la descripcion
                    /*Insertando a la cuenta. */
                    insertAccount(sqlSample,i_estudiante,itc.id)
            }
       }catch (SQLException sqlex){
            while (sqlex != null){
                log.error "executeMySQL SQL: ${sqlex.getErrorCode()} ${sqlex.getMessage()}"
                sqlex = sqlex.getNextException()
            }
            System.exit(1)
        }catch(Exception ex) {
                log.error "executeMySQL: ${ex}"
                System.exit(1)
        }
    }

    /*Ejercicio #2*/
    static private void insertAccount(Sql sqlSample, String i_estudiante, String productoid){

        try{
            sqlSample.call("call sp_insert_prueba_groovy ($i_estudiante, $productoid, ${Sql.INTEGER})")
            {retorno ->
                if(retorno != 0){
                    log.error "insertAccount SQL: Error insertando ${i_estudiante}, Producto ${producto}"
                }
            }
        }catch (SQLException sqlex){
            while (sqlex !=null){
                log.error "insertAccount SQL:${sqlex.getErrorCode()} ${sqlex.getMessage()}"
                sqlex = sqlex.getNextException()

            }
            System.exit(1)
        }catch (Exception ex){
            log.error "insertAccount:${ex}"
            System.exit(1)
        }
    }

            

}

