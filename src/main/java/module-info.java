module com.x86.prozex86 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires net.synedra.validatorfx;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.bootstrapfx.core;
	requires eu.hansolo.tilesfx;
	requires com.almasb.fxgl.all;
	requires java.desktop;
	requires javafx.media;

	opens com.x86.prozex86 to javafx.fxml;
	exports com.x86.prozex86;
	exports com.x86.prozex86.minigames.cpu;
}