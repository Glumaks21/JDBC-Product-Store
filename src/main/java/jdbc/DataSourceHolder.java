package jdbc;

import javax.sql.DataSource;

public interface DataSourceHolder {
    DataSource getDataSource();
    void refresh();
}
