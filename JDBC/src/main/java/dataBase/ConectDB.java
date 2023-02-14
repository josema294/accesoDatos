package dataBase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.Map;

public class ConectDB implements SchemaDB {

     private Connection conn;


    public ConectDB()  {

        getConection();
    }

    public void  closeConection () throws SQLException {

        conn.close();
    }

    /**
     *
     * Metodo que establece una conexion con la base de datos
     *
     */
    private void getConection ()  {

        try {
            String driver = "jdbc:mariadb://";
            String host = "localhost:3306/";
            String db = "productos";
            String url = String.format("%s%s%s",driver,host,db);
            String user = "root";
            String password = "";
            conn =  DriverManager.getConnection(url,user,password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTables () {
        Statement stm;

        try {


            stm = conn.createStatement();


            stm.execute("CREATE TABLE IF NOT EXISTS Productos (id int AUTO_INCREMENT PRIMARY KEY , nombre varchar (50), descripcion varchar (255),cantidad varchar(50), precio varchar(50))");
            System.out.println("Ejecutado comando SQL CREATE TABLE IF NOT EXIST Productos");
            stm.execute("CREATE TABLE IF NOT EXISTS Empleados (id int AUTO_INCREMENT PRIMARY KEY, nombre varchar (50), apellidos varchar (50), correo varchar(50))");
            System.out.println("Ejecutado comando SQL CREATE TABLE IF NOT EXIST Empleados");
            stm.execute("CREATE TABLE IF NOT EXISTS Pedidos(id int AUTO_INCREMENT PRIMARY KEY, id_producto int, descripcion varchar (255), precio varchar(50), FOREIGN KEY (id_producto) REFERENCES Productos(id))");
            System.out.println("Ejecutado comando SQL CREATE TABLE IF NOT EXIST Pedidos");
            stm.execute("CREATE TABLE IF NOT EXISTS Productos_Fav(id int AUTO_INCREMENT PRIMARY KEY, id_producto int, FOREIGN KEY (id_producto) REFERENCES Productos (id))");
            System.out.println("Ejecutado comando SQL CREATE TABLE IF NOT EXIST Productos_Fav");

            stm.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addJson (JSONObject json) {

        Statement stm;

        try {
            stm = conn.createStatement();
            int updatesRealizadas =0;

            //Sacamos el array de productos
            JSONArray jsonArray = json.getJSONArray("products");


            for (int i = 0; i < jsonArray.length() ; i++) {

                JSONObject objetoTemp = jsonArray.getJSONObject(i);
                String nombreTemp = objetoTemp.getString("title");
                int precioTemp = objetoTemp.getInt("price");
                String descTemp = objetoTemp.getString("description").replace("'", "\\'");
                int cantiTemp = objetoTemp.getInt("stock");


            updatesRealizadas += stm.executeUpdate(String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES ('%s',%d,'%s',%d)", SchemaDB.NOMBRE_TABLA_PRODUCTOS,
            SchemaDB.COLUMNA_NOMBRE,SchemaDB.COLUMNA_PRECIO,SchemaDB.COLUMNA_DESCRIPCION,SchemaDB.COLUMNA_CANTIDAD,
            nombreTemp,precioTemp,descTemp,cantiTemp)) ;




            }

            System.out.println("Se han realizado " + updatesRealizadas + " inserciones en la DB");
            stm.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public void addEmpleados () {
        Statement stm;
        int updatesRealizadas = 0;

        try {
            stm = conn.createStatement();

            //En mi caso hare un bucle de 10 iteraciones para insertar empleados

            for (int i = 0; i < 10; i++) {

                updatesRealizadas += stm.executeUpdate(String.format("INSERT INTO %s (%s,%s,%s) VALUES ('%s','%s','%s')", SchemaDB.NOMBRE_TABLA_EMPLEADOS,
                SchemaDB.COLUMNA_NOMBRE,SchemaDB.COLUMNA_APELLIDOS,SchemaDB.COLUMNA_CORREO,
                        String.format("Empleado %d", i),
                        String.format("Apellido%d",i),
                        String.format("Empleado%d@AAA.com", i)));
            }

            System.out.println("Se han realizado " + updatesRealizadas + " inserciones en la DB");
            stm.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void addPedidos () {

        Statement stm;
        int updatesRealizadas = 0;

        try {
            stm = conn.createStatement();

            //En mi caso hare un bucle de 10 iteraciones para insertar pedidos

            for (int i = 0; i < 10; i++) {

                updatesRealizadas += stm.executeUpdate(String.format("INSERT INTO %s (%s,%s,%s) VALUES ('%s','%s','%f')", SchemaDB.NOMBRE_TABLA_PEDIDOS,
                        SchemaDB.COLUMNA_ID_PRODUCTO,SchemaDB.COLUMNA_DESCRIPCION,SchemaDB.COLUMNA_PRECIO,
                        String.format("%d",i+3),
                        String.format("Producto%d",i),
                        Math.random()*100));
            }

            System.out.println("Se han realizado " + updatesRealizadas + " inserciones en la DB");
            stm.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void queryTodosEmpleados () {

        Statement stm;

        try {
            stm = conn.createStatement();
            ResultSet queryResultado =  stm.executeQuery(String.format("SELECT * FROM %s", SchemaDB.NOMBRE_TABLA_EMPLEADOS));

            System.out.println("\nLa consulta arroja los siguientes resultados: \n");

            while (queryResultado.next()) {

                System.out.println("ID: " + queryResultado.getInt("id") +
                        " Nombre: " + queryResultado.getString("nombre") +
                        " Apellido: " + queryResultado.getString("apellidos"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void queryTodosProductos () {

        Statement stm;

        try {
            stm = conn.createStatement();
            ResultSet queryResultado =  stm.executeQuery(String.format("SELECT * FROM %s", SchemaDB.NOMBRE_TABLA_PRODUCTOS));

            System.out.println("\nLa consulta arroja los siguientes resultados: \n");

            while (queryResultado.next()) {

                System.out.println("ID: " + queryResultado.getInt("id") +
                        " Nombre: " + queryResultado.getString("nombre") +
                        " precio: " + queryResultado.getString("precio") +
                        " Descripcion: " + queryResultado.getString("descripcion"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void queryProductosMenos600 () {

        Statement stm;

        try {
            stm = conn.createStatement();
            ResultSet queryResultado =  stm.executeQuery(String.format("SELECT * FROM %s WHERE %s <600", SchemaDB.NOMBRE_TABLA_PRODUCTOS,SchemaDB.COLUMNA_PRECIO));

            System.out.println("\nLa consulta arroja los siguientes resultados: \n");

            while (queryResultado.next()) {

                System.out.println("ID: " + queryResultado.getInt("id") +
                        " Nombre: " + queryResultado.getString("nombre") +
                        " precio: " + queryResultado.getString("precio") +
                        " Descripcion: " + queryResultado.getString("descripcion"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void insertProductosFavoritos () {

        Statement stm;

        try {
            //con sql directamente hacemos el insert del select
            stm = conn.createStatement();
            String query = "INSERT INTO `Productos_Fav`(`id_producto`) SELECT Productos.id from Productos WHERE precio > 1000" ;
            ResultSet queryResultado =  stm.executeQuery(query);
            System.out.println("Los productos de mas de 1000e se han agregado a la tabla resultados");

            stm.close();






        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }




}


