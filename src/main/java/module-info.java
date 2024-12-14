module com.antedeguemon.testepokemon {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    requires com.rabbitmq.client;
    requires static lombok;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    opens com.antedeguemon.testepokemon to javafx.fxml;
    exports com.antedeguemon.testepokemon;
}