package org.example.View;

import com.sun.jdi.PrimitiveValue;
import org.example.Model.Employee;
import org.example.Repository.EmployeeRepository;
import org.example.Repository.RepositoryInter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SwingApp extends JFrame {

    private final RepositoryInter<Employee> employeeRepositoryInter;
    private final JTable employeeTable;

    public SwingApp(){
        //Configuracion ventana
        setTitle("Gestion de empleados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,230);

        //Creacion tabla empleados
        employeeTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        //Acciones
        JButton agregarButton = new JButton("Agregar");
        JButton actualizarButton = new JButton("Actualizar");
        JButton eliminarButton = new JButton("Eliminar");

        //Configurar botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(agregarButton);
        buttonPanel.add(actualizarButton);
        buttonPanel.add(eliminarButton);
        add(buttonPanel, BorderLayout.SOUTH);

        //Estilos botones
        agregarButton.setBackground(new Color(59,2,110));
        agregarButton.setForeground(Color.WHITE);
        agregarButton.setFocusPainted(false);

        actualizarButton.setBackground(new Color(52,152,219));
        actualizarButton.setForeground(Color.WHITE);
        actualizarButton.setFocusPainted(false);

        eliminarButton.setBackground(new Color(231,76,60));
        eliminarButton.setForeground(Color.WHITE);
        eliminarButton.setFocusPainted(false);

        //Crear objeto repository para acceder a la BD
        employeeRepositoryInter = new EmployeeRepository();

        //Cargar los empleados iniciales en la tabla
        refreshEmployeeTable();

        //Agregar actionlistener para botones
        agregarButton.addActionListener(e -> {
            try{
                agregarEmpleado();
            }catch (SQLException ex){
                throw  new RuntimeException(ex);
            }
        });

        actualizarButton.addActionListener(e -> actualizarEmpleado());
        eliminarButton.addActionListener(e -> eliminarEmpleado());
    }

    private void refreshEmployeeTable(){
        try {
            List<Employee> employees = employeeRepositoryInter.findAll();

            //Crear modelo tabla y datos empleados

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Apellido paterno");
            model.addColumn("Apellido materno");
            model.addColumn("Email");
            model.addColumn("Salario");

            for(Employee employee : employees){
                Object[] rowData = {
                        employee.getId(),
                        employee.getFirst_name(),
                        employee.getPa_surname(),
                        employee.getMa_surname(),
                        employee.getEmail(),
                        employee.getSalary()
                };
                model.addRow(rowData);
            }

            //Modelo de tabla actualizado
            employeeTable.setModel(model);
        }catch (SQLException e){
            JOptionPane.showMessageDialog(this, "Error al obtener los datos");
        }
    }

    private void agregarEmpleado() throws SQLException{
        JTextField nombreField = new JTextField();
        JTextField paternoField = new JTextField();
        JTextField maternoField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField salaryField = new JTextField();

        Object[] fields = {
                "Nombre", nombreField,
                "Apellido paterno", paternoField,
                "Apellido materno", maternoField,
                "Email", emailField,
                "Salario", salaryField
        };

        int result = JOptionPane.showConfirmDialog(this, fields,"Agregar empleado", JOptionPane.OK_CANCEL_OPTION);

        if(result == JOptionPane.OK_OPTION){
            Employee employee = new Employee();
            employee.setFirst_name(nombreField.getText());
            employee.setPa_surname(paternoField.getText());
            employee.setMa_surname(maternoField.getText());
            employee.setEmail(emailField.getText());
            employee.setSalary(Double.parseDouble(salaryField.getText()));

            //Guardar el nuevo empleado en la BD
            employeeRepositoryInter.save(employee);

            //Actualizar la tabla de empleados actualizados
            refreshEmployeeTable();

            JOptionPane.showMessageDialog(this, "Empleado agregado correctamente", "Exito", JOptionPane.OK_CANCEL_OPTION);

        }
    }

    private void actualizarEmpleado(){
    //Obtener ID empleado a actualizar
        String empleadoIDStr = JOptionPane.showInputDialog(this, "Ingresar id empleado a actualizar", "Actualizar empleado", JOptionPane.OK_CANCEL_OPTION);

        if(empleadoIDStr != null){
            try {
                int empleadoId = Integer.parseInt(empleadoIDStr);

                //Obtener empleado desde la BD
                Employee empleado = employeeRepositoryInter.getByID(empleadoId);

                if (empleado != null){
                    //Crear formulario con datos de empleado
                    JTextField nombreField = new JTextField(empleado.getFirst_name());
                    JTextField apellidpPaternoField = new JTextField(empleado.getPa_surname());
                    JTextField apellidoMaternoField = new JTextField(empleado.getMa_surname());
                    JTextField emailField = new JTextField(empleado.getEmail());
                    JTextField salarioField = new JTextField(String.valueOf(empleado.getSalary()));

                    Object[] fields = {
                            "Nombre", nombreField,
                            "Apellido paterno", apellidpPaternoField,
                            "Apellido materno", apellidoMaternoField,
                            "Email", emailField,
                            "Salario", salarioField
                    };

                    int confirmResult = JOptionPane.showConfirmDialog(this, fields, "Actualizar empleado", JOptionPane.OK_CANCEL_OPTION);
                    if(confirmResult == JOptionPane.OK_OPTION){
                        //Actualizar valores ingresados
                        empleado.setFirst_name(nombreField.getText());
                        empleado.setPa_surname(apellidpPaternoField.getText());
                        empleado.setMa_surname(apellidoMaternoField.getText());
                        empleado.setEmail(emailField.getText());
                        empleado.setSalary(Double.parseDouble(salarioField.getText()));

                        //Guardar cambios BD
                        employeeRepositoryInter.save(empleado);

                        //Actualizar tabla de empleados interfaz
                        refreshEmployeeTable();
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "NO se encontro ningun id relacionado", "Error", JOptionPane.OK_CANCEL_OPTION);
                }
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Ingrese valor numerico", "Error", JOptionPane.OK_CANCEL_OPTION);
            }catch (SQLException e){
                JOptionPane.showMessageDialog(this, "Error al obtener los datos", "Error", JOptionPane.OK_CANCEL_OPTION);
            }
        }
    }
    private void eliminarEmpleado(){
        //Obtener id empleado
        String empleadoIdStr = JOptionPane.showInputDialog(this, "Ingresar id del empleado", "Error", JOptionPane.OK_CANCEL_OPTION);
        if(empleadoIdStr != null){
            try {
                int empleadoId = Integer.parseInt(empleadoIdStr);

                //Confirmar eliminacion empleado
                int confirmResult = JOptionPane.showConfirmDialog(this, "Esta seguro que quiere eliminar", "Confirmar para eliminar", JOptionPane.OK_CANCEL_OPTION);
                if (confirmResult == JOptionPane.YES_OPTION){
                    //Eliminar empleado de BD
                    employeeRepositoryInter.delete(empleadoId);

                    //Actualizar tabla de empleados interfaz
                    refreshEmployeeTable();
                }
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Ingresar valor numerico valido", "Error", JOptionPane.OK_CANCEL_OPTION);
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }

    }
}
