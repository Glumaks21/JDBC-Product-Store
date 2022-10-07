package jdbc.dao;

import javax.sql.DataSource;

public interface DataSourceHolder {
    DataSource getDataSource();
}
