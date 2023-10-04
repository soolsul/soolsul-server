package com.soolsul.soolsulserver.acceptance;

import java.sql.Connection;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class AcceptanceTestExecutionListener extends AbstractTestExecutionListener {

	@Override
	public void afterTestMethod(final TestContext testContext) {
		final JdbcTemplate jdbcTemplate = getJdbcTemplate(testContext);
		final List<String> truncateQueries = getTruncateQueries(jdbcTemplate);
		truncateTables(jdbcTemplate, truncateQueries);
	}

	private List<String> getTruncateQueries(final JdbcTemplate jdbcTemplate) {
		try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
			String schema = connection.getCatalog();
			return jdbcTemplate.queryForList(
				"SELECT concat('TRUNCATE TABLE ', TABLE_NAME) "
					+ "FROM INFORMATION_SCHEMA.TABLES "
					+ "WHERE TABLE_SCHEMA = '" + schema + "'", String.class);
		} catch (Exception exception) {
			throw new RuntimeException();
		}
	}

	private JdbcTemplate getJdbcTemplate(final TestContext testContext) {
		return testContext.getApplicationContext().getBean(JdbcTemplate.class);
	}

	private void truncateTables(final JdbcTemplate jdbcTemplate, final List<String> truncateQueries) {
		execute(jdbcTemplate, "SET FOREIGN_KEY_CHECKS = FALSE");
		truncateQueries.forEach(v -> execute(jdbcTemplate, v));
		execute(jdbcTemplate, "SET FOREIGN_KEY_CHECKS = TRUE");
	}

	private void execute(final JdbcTemplate jdbcTemplate, final String query) {
		jdbcTemplate.execute(query);
	}
}
