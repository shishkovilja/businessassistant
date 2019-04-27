package io.khasang.ba.web.config.io.khasang.ba.web.service;

import org.springframework.jdbc.core.JdbcTemplate;

public class CreateTable {
    private JdbcTemplate jdbcTemplate;

    public CreateTable(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getTableCreationStatus() {
        try {
            jdbcTemplate.execute("DROP TABLE IF EXISTS cats");

            jdbcTemplate.execute("CREATE TABLE public.cats\n" +
                    "(\n" +
                    "    id integer NOT NULL,\n" +
                    "    name character varying(255) COLLATE pg_catalog.\"default\",\n" +
                    "    description character varying(255) COLLATE pg_catalog.\"default\",\n" +
                    "    color_id integer,\n" +
                    "    CONSTRAINT cats_pkey PRIMARY KEY (id)\n" +
                    ")");

            return "table created";
        } catch (Exception e) {
            return "Table creation failed " + e;
        }
    }
}
