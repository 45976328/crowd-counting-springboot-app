CREATE EXTENSION IF NOT EXISTS hstore;

CREATE TABLE IF NOT EXISTS zones(
    id INT PRIMARY KEY,
    category TEXT,  --  "square", "station", "campus"
    properties JSONB,
    updated_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS zone_thresholds(
    zone_id INT REFERENCES zones(id),
    level TEXT CHECK(level IN ('normal', 'busy', 'high')),
    min INT NOT NULL,
    max INT,
    PRIMARY KEY (zone_id)
);

CREATE TABLE IF NOT EXISTS nodes(
    id INT PRIMARY KEY,
    zone_id INT REFERENCES zones(id),
    mac TEXT, -- if connected to a network wirelessly
    lat TEXT,
    longitude TEXT,
    config JSONB,
    updated_at timestamptz NOT NULL DEFAULT now()
);