package com.example.studentcrud.ui;

import com.example.studentcrud.entity.Student;
import com.example.studentcrud.service.StudentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.dao.DataIntegrityViolationException;

@Route("")
@PageTitle("Student Directory")
public class MainView extends VerticalLayout {

    private final StudentService studentService;
    private final Grid<Student> grid = new Grid<>(Student.class, false);
    private final TextField searchField = new TextField("Search by first or last name");

    private final TextField studentId = new TextField("Student ID");
    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final EmailField email = new EmailField("Email");

    private final Button saveButton = new Button("Save");
    private final Button updateButton = new Button("Update");
    private final Button deleteButton = new Button("Delete");
    private final Button clearButton = new Button("Clear");

    private final BeanValidationBinder<Student> binder = new BeanValidationBinder<>(Student.class);

    private Student selectedStudent;

    public MainView(StudentService studentService) {
        this.studentService = studentService;

        add(new H2("Student Directory"));
        configureSearch();
        configureGrid();
        configureForm();

        add(searchField, grid, buildFormLayout());

        refreshGrid();
        clearSelection();
    }

    private void configureSearch() {
        searchField.setPlaceholder("Type a name to filter students");
        searchField.setClearButtonVisible(true);
        searchField.addValueChangeListener(event -> grid.setItems(studentService.searchByName(event.getValue())));
    }

    private void configureGrid() {
        grid.addColumn(Student::getStudentId).setHeader("Student ID").setAutoWidth(true);
        grid.addColumn(Student::getFirstName).setHeader("First name").setAutoWidth(true);
        grid.addColumn(Student::getLastName).setHeader("Last name").setAutoWidth(true);
        grid.addColumn(Student::getEmail).setHeader("Email").setAutoWidth(true).setFlexGrow(1);
        grid.setHeight("320px");
        grid.asSingleSelect().addValueChangeListener(event -> setSelectedStudent(event.getValue()));
    }

    private void configureForm() {
        binder.bindInstanceFields(this);

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        updateButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        saveButton.addClickListener(event -> saveStudent());
        updateButton.addClickListener(event -> updateStudent());
        deleteButton.addClickListener(event -> deleteStudent());
        clearButton.addClickListener(event -> clearSelection());
    }

    private FormLayout buildFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(studentId, firstName, lastName, email,
                new HorizontalLayout(saveButton, updateButton, deleteButton, clearButton));
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        return formLayout;
    }

    private void setSelectedStudent(Student student) {
        selectedStudent = student;
        if (student == null) {
            clearSelection();
            return;
        }
        binder.readBean(student);
        saveButton.setEnabled(false);
        updateButton.setEnabled(true);
        deleteButton.setEnabled(true);
    }

    private void saveStudent() {
        Student newStudent = new Student();
        if (!writeFormToStudent(newStudent)) {
            return;
        }
        try {
            studentService.save(newStudent);
            showSuccess("Student added successfully.");
            refreshGrid();
            clearSelection();
        } catch (DataIntegrityViolationException ex) {
            showError("Student ID and email must be unique.");
        }
    }

    private void updateStudent() {
        if (selectedStudent == null) {
            showError("Please select a student to update.");
            return;
        }
        if (!writeFormToStudent(selectedStudent)) {
            return;
        }
        try {
            studentService.save(selectedStudent);
            showSuccess("Student updated successfully.");
            refreshGrid();
            clearSelection();
        } catch (DataIntegrityViolationException ex) {
            showError("Student ID and email must be unique.");
        }
    }

    private void deleteStudent() {
        if (selectedStudent == null || selectedStudent.getId() == null) {
            showError("Please select a student to delete.");
            return;
        }
        studentService.deleteById(selectedStudent.getId());
        showSuccess("Student deleted successfully.");
        refreshGrid();
        clearSelection();
    }

    private boolean writeFormToStudent(Student student) {
        try {
            binder.writeBean(student);
            return true;
        } catch (ValidationException ex) {
            showError("Please fill all required fields correctly.");
            return false;
        }
    }

    private void refreshGrid() {
        grid.setItems(studentService.searchByName(searchField.getValue()));
    }

    private void clearSelection() {
        selectedStudent = null;
        grid.deselectAll();
        binder.readBean(null);
        saveButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    private void showSuccess(String message) {
        Notification notification = Notification.show(message, 2500, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void showError(String message) {
        Notification notification = Notification.show(message, 3000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}
