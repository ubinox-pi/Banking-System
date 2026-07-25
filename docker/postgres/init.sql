DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'accounts') THEN
        CREATE DATABASE accounts;
    END IF;
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'banking') THEN
        CREATE DATABASE banking;
    END IF;
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'cards') THEN
        CREATE DATABASE cards;
    END IF;
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'loan') THEN
        CREATE DATABASE loan;
    END IF;
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'otp') THEN
        CREATE DATABASE otp;
    END IF;
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'transactions') THEN
        CREATE DATABASE transactions;
    END IF;
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'Users') THEN
        CREATE DATABASE "Users";
    END IF;
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'Employee') THEN
        CREATE DATABASE "Employee";
    END IF;
END $$;

