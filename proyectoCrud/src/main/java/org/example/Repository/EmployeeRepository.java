package org.example.Repository;

import org.example.Model.Employee;
import org.example.Util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements RepositoryInter<Employee> {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }

    @Override
    public List<Employee> findAll() throws SQLException {

        List<Employee> employees = new ArrayList<>();
        try(Statement myStan = getConnection().createStatement();
                ResultSet myRes = myStan.executeQuery("SELECT * FROM employees")){
            while (myRes.next()){
                Employee e = createEmployee(myRes);
                employees.add(e);
            }
        }
        return employees;
    }



    @Override
    public Employee getByID(Integer id) throws SQLException {
        Employee employee = null;
        try (PreparedStatement myStan = getConnection().prepareStatement("SELECT * FROM employees WHERE id = ?")){
            myStan.setInt(1,id);
            try(ResultSet myRes = myStan.executeQuery()) {
                if(myRes.next()){//next: para busacrlo en la lista de objetos
                    employee = createEmployee(myRes);
                }
            }
        }
        return employee;
    }

    @Override
    public void save(Employee employeeRepository) throws SQLException {

        String sql = "";

        if (employeeRepository.getId() != null && employeeRepository.getId() > 0) {
            sql = "UPDATE employees SET first_name = ?, ma_surname = ?, pa_surname = ?, email =?, salary=? WHERE id = ?";
        }else{
            sql = "INSERT INTO employees(first_name, pa_surname, ma_surname, email, salary) VALUES(?,?,?,?,?)";
        }
        try(PreparedStatement myStan = getConnection().prepareStatement(sql)) {
            myStan.setString(1, employeeRepository.getFirst_name());
            myStan.setString(2, employeeRepository.getPa_surname());
            myStan.setString(3, employeeRepository.getMa_surname());
            myStan.setString(4, employeeRepository.getEmail());
            myStan.setDouble(5,employeeRepository.getSalary());

            if(employeeRepository.getId() != null && employeeRepository.getId() > 0){
                myStan.setInt(6, employeeRepository.getId());
            }
            myStan.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id) throws SQLException {

        try (PreparedStatement mystan = getConnection().prepareStatement("DELETE FROM  employees WHERE id=?")){
            mystan.setInt(1,id);
            mystan.executeUpdate();
        }
    }

    private Employee createEmployee(ResultSet myRes) throws SQLException {
        Employee e = new Employee();
        e.setId((myRes.getInt("id")));
        e.setFirst_name(myRes.getString("first_name"));
        e.setMa_surname(myRes.getString("ma_surname"));
        e.setPa_surname(myRes.getString("pa_surname"));
        e.setEmail(myRes.getString("email"));
        e.setSalary(myRes.getDouble("salary"));
        return e;
    }
}
