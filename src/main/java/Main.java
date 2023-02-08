import dataBase.ConectDB;
import jsonParse.JsonLocalParser;
import jsonParse.JsonOnlineParser;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {


        //Creamos la instancia de la clase que conecta a DB
        ConectDB con = new ConectDB();
        //Ejecutamos el metodo predefinido para crear las tablas solicitadas
        //con.createTables();
        // Creamos la instancia de la clase que maneja el JSON
        //JsonOnlineParser json = new JsonOnlineParser();
        //Ejecutamos el metodo predefinido para instroducir en la DB los elementos de un JSON
        //con.addJson(json.getJson());
        //Ejecutamos el metodo predefinido para introducir empleados
        //con.addEmpleados();
        //Ejecutamos el metodo predefinido para introducir pedidos
        //con.addPedidos();
        //Ejecutamos el metodo predefinido para obtener todos los empleados
        //con.queryTodosEmpleados();
        //Ejecutamos el metodo predefinido para obtener todos los productos
        //con.queryTodosProductos();
        //Ejecutamos el metodo predefinido para obtener todos los productos de menos de 600e
        //con.queryProductosMenos600();
        //Ejecutamos el metodo predefinido para obtener todos los productos de mas de 1000e e introductirlos en productos_fav
        con.insertProductosFavoritos();


    }


}
