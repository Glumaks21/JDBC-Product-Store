package jdbc.dao;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.util.Objects;
import java.util.Properties;

public class FileDataSourceHolder implements DataSourceHolder {
    private static String path;
    private static DataSource source;
    public FileDataSourceHolder(String path) {
       if (source == null) {
           setPath(path);
           refresh();
       }
    }

    @Override
    public DataSource getDataSource() {
        return source;
    }

    public static void setPath(String newPath) {
        Objects.requireNonNull(newPath);
        path = newPath;
    }

    public static void refresh() {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(path);
            props.load(fis);

            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setUrl(props.getProperty("DB_URL"));
            mysqlDataSource.setUser(props.getProperty("DB_USER"));
            mysqlDataSource.setPassword(props.getProperty("DB_PASSWORD"));
            source = mysqlDataSource;
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
