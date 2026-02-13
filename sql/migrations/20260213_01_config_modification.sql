CREATE TYPE config_environment AS ENUM ('ANY', 'DEV', 'PROD');

ALTER TABLE public.config 
    ALTER COLUMN environment TYPE public.config_environment 
    USING environment::public.config_environment;

ALTER TABLE public.config 
    ALTER COLUMN environment SET DEFAULT 'ANY'::public.config_environment;

INSERT INTO public.config (name, value, environment) VALUES
('JAVA_EXTERNAL_URL', 'http://localhost:8080', 'DEV'),
('PHP_EXTERNAL_URL', 'http://localhost:8080', 'DEV'),
('JAVA_EXTERNAL_URL', 'https://spring.braindribbler.com', 'PROD'),
('PHP_EXTERNAL_URL', 'http://braindribbler.com', 'PROD');