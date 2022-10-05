package jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.util.Properties;

public class ConnectionHolder {
    private static final String DB_PATH = "db.properties";

    private static DataSource instance;

    private ConnectionHolder() {};

    public static DataSource getInstance() {
        if (instance == null) {
            instance = readProperties(DB_PATH);
        }

        return instance;
    }

    private static DataSource readProperties(String path) {
        MysqlDataSource source = new MysqlDataSource();
        Properties props = new Properties();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(path);
            props.load(fis);

            source.setUrl(props.getProperty("DB_URL"));
            source.setUser(props.getProperty("DB_USER"));
            source.setPassword(props.getProperty("DB_PASSWORD"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return source;
    }
}
