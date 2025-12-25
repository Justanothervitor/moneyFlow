CREATE TYPE "AuthProvider" AS ENUM(
    'GOOGLE',
    'FACEBOOK',
    'LOCAL'
);

ALTER TABLE "Users"
    ADD COLUMN "provider" "AuthProvider" ,
    ADD COLUMN "enabled" BOOLEAN NOT NULL,
    ADD COLUMN "providerId" VARCHAR(255) NOT NULL,
    ADD COLUMN "emailVerified" BOOLEAN NOT NULL;