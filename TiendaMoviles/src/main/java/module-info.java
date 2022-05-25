module com.alvaromorenogil.tiendamoviles {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.instrument;
    requires java.persistence;
    requires java.sql;
    requires java.base;
    
    opens com.alvaromorenogil.tiendamoviles.entities;
    opens com.alvaromorenogil.tiendamoviles to javafx.fxml;
    exports com.alvaromorenogil.tiendamoviles;
}
