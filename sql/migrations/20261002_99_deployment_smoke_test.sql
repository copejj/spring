BEGIN;

-- 1. Create a temporary smoke-test table to verify structural permissions
CREATE TABLE IF NOT EXISTS deployment_smoke_test (
    test_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    executed_at TIMESTAMPTZ DEFAULT NOW() NOT NULL,
    status TEXT NOT NULL
);

-- 2. Insert a temporary tracking row
INSERT INTO deployment_smoke_test (status) VALUES ('SUCCESS');

-- 3. Clean up after ourselves so your database remains perfectly clean
DROP TABLE deployment_smoke_test;

COMMIT;
