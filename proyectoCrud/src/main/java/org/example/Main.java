package org.example;

import org.example.Model.Employee;
import org.example.Repository.EmployeeRepository;
import org.example.Repository.RepositoryInter;
import org.example.Util.DatabaseConnection;
import org.example.View.SwingApp;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

/*
        try(Connection myCon = DatabaseConnection.getInstance()){
            RepositoryInter<Employee> repositoryInter = new EmployeeRepository();


            //Encontrar todos los registros por id
            repositoryInter.findAll().forEach(System.out::println);

            //Encontrar registros por un id esepcifico
            System.out.println(repositoryInter.getByID(3));

            //Agregar resgistros
            System.out.println("Insertar un empleado");
            Employee employee = new Employee();
            employee.setFirst_name("Alejandra");
            employee.setPa_surname("Sanchez");
            employee.setMa_surname("Prieto");
            employee.setEmail("aleja@gmail.com");
            employee.setSalary(234.2);
            repositoryInter.save(employee);

            //Eliminar empleado
            repositoryInter.delete(8);
            System.out.println("Base de datos actualizada");
            repositoryInter.findAll().forEach(System.out::println);



            //Actualizando un empleado
            Employee employee = new Employee();
            employee.setId(4);
            employee.setFirst_name("Alejandra");
            employee.setPa_surname("Perez");
            employee.setMa_surname("Prieto");
            employee.setEmail("aleja@gmail.com");
            employee.setSalary(525554.2);
            repositoryInter.save(employee);

            repositoryInter.findAll().forEach(System.out::println);

             */
        // Prueba Swing app

        SwingApp app = new SwingApp();
        app.setVisible(true);

        }

            /*
            Statement myState = myCon.createStatement();
            ResultSet myResult = myState.executeQuery("SELECT * FROM employees");){


            while (myResult.next()){
                System.out.println(myResult.getString("first_name"));
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("No se puede conectar");
        }

             */


    }
