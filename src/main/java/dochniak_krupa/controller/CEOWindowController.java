package dochniak_krupa.controller;

import com.smattme.MysqlExportService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.log4j.BasicConfigurator;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class CEOWindowController {
    @FXML
    void onTakeBackupAction(){
        BasicConfigurator.configure();
        Properties properties = new Properties();
        properties.setProperty(MysqlExportService.DB_NAME, "bank");
        properties.setProperty(MysqlExportService.DB_USERNAME, "root");
        properties.setProperty(MysqlExportService.DB_PASSWORD, "root");
        properties.setProperty(MysqlExportService.ADD_IF_NOT_EXISTS, "true");
        properties.setProperty(MysqlExportService.JDBC_CONNECTION_STRING, "jdbc:mysql://localhost/bank?serverTimezone=UTC&amp");

        properties.getProperty(MysqlExportService.TEMP_DIR, new File("folder").getAbsolutePath());
        properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");
        MysqlExportService mysqlExportService = new MysqlExportService(properties);

        File file = mysqlExportService.getGeneratedZipFile();

        try{
            mysqlExportService.export();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
